package com.aj.pay.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.pay.vo.TShopAlbumOrder;
import com.aj.pay.vo.TVipOrder;
import com.aj.sys.vo.TVipPackage;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.frame.core.constant.Constant;
import com.frame.core.constant.FtpConstant;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.FileZipUtil;


/**
 * 支付宝支付回调
 * */

@Service("alipaynotify")
public class AliPayCallBackNotify implements PublishService{

	private Logger log = Logger.getLogger(AliPayCallBackNotify.class);
	
	private static String ALI_PAY_APP_ID = SystemConfig.getValue("ali.pay.appId");//支付宝APPID
	private static String ALI_PAY_PUBLIC_KEY = SystemConfig.getValue("ali.pay.ali.public.key");  //支付宝公钥
	private static String ALI_PAY_CHARSET = "utf-8";  //
	private String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
	private String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
	private String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
	private String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
	private int port =  SystemConfig.getValue(FtpConstant.PORT) == null ? 21 : Integer.parseInt(SystemConfig.getValue(FtpConstant.PORT).toString());
	
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		String success = "success";
		log.info("支付宝支付回调请求参数:"+obj);
		//obj = "total_amount=0.01&buyer_id=2088612401448101&trade_no=2017052121001004100321838306&notify_time=2017-05-21 00:04:49&subject=购买动感相册112相册&sign_type=RSA&buyer_logon_id=226***@qq.com&auth_app_id=2017032206342414&charset=utf-8&notify_type=trade_status_sync&invoice_amount=0.01&out_trade_no=a02c7281806f4f13996baf17c163052e&trade_status=TRADE_SUCCESS&gmt_payment=2017-05-21 00:04:49&version=1.0&point_amount=0.00&sign=IJeINPfoN8H4FyGNHZSPlT5U4f3+/IScM/Y5SsWInNqX3ux7xTAV79Ybbb24cu3v1vGLpZf8l7tNW0y6ztfS0poud3MRJ8Dbw3w2iaC9RuqezBWQIu2hSs3b3PNaXA8bMM9cx8wBu3iNy1AnBkbE7/3jY77+li5gWBzVsQaHkc8=&gmt_create=2017-05-21 00:04:49&buyer_pay_amount=0.01&receipt_amount=0.01&fund_bill_list=[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]&app_id=2017032206342414&seller_id=2088621567366921&notify_id=59e4ca465419ddb9d35f74365beeebcgrv&seller_email=545027295@qq.com";
		//SortedMap<String, String> resultMap = new TreeMap<String, String>();
		if(obj != null && !"".equals(obj)){
			
			/*String[] params = obj.toString().split("&");
			for(String param : params){
					//resultMap.put(param.split("=")[0],  URLDecoder.decode(param.split("=")[1], "utf-8") );
					resultMap.put(param.split("=")[0],  param.split("=")[1]);
			}*/
			//Map<String, Object> origMap = (Map<String, Object>) obj;
			Map<String,String> resultMap = (Map<String, String>) obj;
			//for(Entry<String, Object>  entry : origMap.entrySet()){
			//	resultMap.put(entry.getKey(), entry.getValue().toString());
			//}
			
			//1.验证app_id
			if(!ALI_PAY_APP_ID.equals(resultMap.get("app_id"))){
				log.info("支付宝支付回调请求验证签名不通过！"+resultMap.get("app_id"));
				return success;
			}
			boolean b = false;
			try {
				b = AlipaySignature.rsaCheckV1(resultMap, ALI_PAY_PUBLIC_KEY, ALI_PAY_CHARSET);
			} catch (AlipayApiException e) {
				e.printStackTrace();
				log.info("支付宝支付回调请求验证签名异常："+e);
			}
			//2.验签
			if(b){
				return dealRequest(resultMap);
			}else{
				log.info("支付宝支付回调请求验证签名不通过！"+resultMap.get("sign"));
				return success;
			}
		}
		
		return "failure";
	}

	private String dealRequest(Map<String, String> resultMap) {
		
		String out_trade_no = resultMap.get("out_trade_no");
		String payType = resultMap.get("subject").substring(0, resultMap.get("subject").indexOf("_"));
		//payType支付类型，有3种： 购买照片：AlipayPayService.API_TYPE  ;vip开通或续费：VipOrderAlipayPayService.API_TYPE; 商户续费： 暂未实现
		if(AlipayPayService.API_TYPE.equals(payType)){
			
			List<TShopAlbumOrder> orderList = baseDAO.getGenericByHql("from TShopAlbumOrder where orderNo = ?", out_trade_no);
			if(orderList != null && orderList.size() > 0){
				TShopAlbumOrder order = orderList.get(0); 
				//待支付状态才处理
				if(order.getStatus() == 0){
					//判断支付总额和实际支付的总额是否相等,防止数据被更改，实际支付金额不等于需要支付的金额
					//由于支付宝返回的金额不是不填， 这里不验证金额
					//if(order.getTotalFee()* 0.01 == resultMap.get("total_amount")){}
					order.setBankType(resultMap.get("buyer_logon_id")); //支付宝帐号
					order.setPayComplateTime(resultMap.get("gmt_close"));
					String trade_status = resultMap.get("trade_status"); //交易状态
					/*枚举名称	枚举说明
				WAIT_BUYER_PAY	交易创建，等待买家付款
				TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
				TRADE_SUCCESS	交易支付成功
				TRADE_FINISHED	交易结束，不可退款*/
					
					if("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)){
						order.setStatus(1); //成功
						updateShopAlbumPayStatus(order.getAlbumId(), 1);
					}else{
						order.setStatus(2); //失败
					}
					order.setTransactionId(resultMap.get("trade_no"));
					baseDAO.update(order);
				}
				return "success";
			}else{
				log.info("支付宝支付回调请求验证不通过！不存在订单号："+resultMap.get("out_trade_no"));
				return "failure";
			}
		}else if(VipOrderAlipayPayService.API_TYPE.equals(payType)){
			List<TVipOrder> orderList = baseDAO.getGenericByHql("from TVipOrder where orderId = ? and isPay = 0 ", out_trade_no);
			if(orderList != null && orderList.size() > 0){
				TVipOrder order = orderList.get(0);
				TUser user = baseDAO.get(TUser.class, order.getUserId());
				TVipPackage pack = baseDAO.get(TVipPackage.class, order.getVipPackageId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				if(user != null){
					if(user.getVipExpiredDate() == null || "".equals(user.getVipExpiredDate())){
						int second = (int) (System.currentTimeMillis() /1000 + (pack.getPackageDays() * 24 * 60 * 60));
						user.setVipExpiredDate(format.format(second* 1000L));
						user.setIsVip(1);
						baseDAO.update(user);
					}else{
						String expiredDate = user.getVipExpiredDate();
						try {
							if(format.parse(expiredDate).getTime() < (int) (System.currentTimeMillis()/ 1000)){
								//过期时间小于当前时间，那么过期时间从今天开始
								int second = (int) (System.currentTimeMillis() /1000 + (pack.getPackageDays() * 24 * 60 * 60));
								user.setVipExpiredDate(format.format(second* 1000L));
								user.setIsVip(1);
								baseDAO.update(user);
							}else{
								//在原过期时间上累加
								int second = (int) (format.parse(expiredDate).getTime() /1000 + (pack.getPackageDays() * 24 * 60 * 60));
								user.setVipExpiredDate(format.format(second* 1000L));
								user.setIsVip(1);
								baseDAO.update(user);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				order.setIsPay(1);
				order.setPayTime(DateUtil.now());
				order.setTradeNo(resultMap.get("trade_no"));
				baseDAO.update(order);
				return "success";
			}else{
				log.info("支付宝支付回调请求验证不通过！不存在订单号："+resultMap.get("out_trade_no"));
				return "failure";
			}
		}
		//防止一直回调，直接返回成功
		return "success";
	}

	private void updateShopAlbumPayStatus(String albumId, int status) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i = baseDAO.execteNativeBulk("update t_shop_album set had_paid = ? where id = ?", new Object[]{status, albumId});
		if(i > 0){
			long fileSize = 0;
			FtpUtil ftp = new FtpUtil(ftpAddress, port, username, password);
			String path = SystemConfig.getValue("ftp.path.root");
			try {
				ftp.login();
				//生成片源下载地址
				List<Map<String, Object>> list = baseDAO.getGenericBySQL("select photo_url from t_shop_photo where album_id = ?", new Object[]{albumId});
				List<String> urls = new ArrayList<String>();
				if(list != null && list.size() > 0){
					for(Map<String, Object> map : list){
						urls.add(path + map.get("photo_url").toString());
						fileSize += ftp.getFile(path + map.get("photo_url") ).getSize();
					} 
				}
				
				List<Map<String, Object>> albumList = baseDAO.getGenericBySQL("SELECT a.shop_id shopId, a.user_id userId, a.album_name albumNmae FROM t_shop_album a WHERE id = ?", new Object[]{ albumId});
				if(albumList != null && albumList.size() > 0){
					Map<String, Object> album = albumList.get(0);
					String to ="/"+ album.get("shopId") +"/"+ album.get("userId") +"/Album/" + albumId;
					String[] files = new String[urls.size()];
					boolean b =FileZipUtil.zip(urls.toArray(files), Constant.resPath + to+".zip");
					if(b){
						log.info("生成压缩包成功,地址："+to +".zip");
						baseDAO.execteNativeBulk("update t_shop_album set download_address = ?, download_secret = ?, pay_time = ? where id = ?", to +".zip",RandomGUID.getRandomString(4), format.format(new Date()) ,  albumId);
					}
					//更新商户空间的使用情况 
					if(fileSize != 0){
						baseDAO.execteNativeBulk("update t_shop_info set zone_used = zone_used - ? where id = ?", fileSize , album.get("shopId").toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				ftp.closeServer();
			}
		}
	}
}
