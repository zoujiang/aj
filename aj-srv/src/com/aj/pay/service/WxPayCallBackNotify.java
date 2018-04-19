package com.aj.pay.service;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.pay.util.JsonXmlUtil;
import com.aj.pay.util.SignUtil;
import com.aj.pay.vo.TShopAlbumOrder;
import com.aj.pay.vo.TVipOrder;
import com.aj.shop.vo.TShopInfo;
import com.aj.sms.SingletonClient;
import com.aj.sys.vo.TVipPackage;
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

import net.sf.json.JSONObject;


/**
 * 微信支付回调
 * */

@Service("wxpaynotify")
public class WxPayCallBackNotify implements PublishService{

	private Logger log = Logger.getLogger(WxPayCallBackNotify.class);
	
	private static String WX_PAY_APP_SECRET = SystemConfig.getValue("wx.pay.appsecret");//密钥
	private String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
	private String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
	private String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
	private String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
	private int port =  SystemConfig.getValue(FtpConstant.PORT) == null ? 21 : Integer.parseInt(SystemConfig.getValue(FtpConstant.PORT).toString());
	
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		log.info("微信支付回调请求参数:"+obj);
		SortedMap<String, Object> resultMap = new TreeMap<String, Object>();
		if(obj != null && !"".equals(obj)){
			
			boolean b = SignUtil.validateSign(obj.toString(), WX_PAY_APP_SECRET);
			if(b){
				
				JSONObject json = JsonXmlUtil.xml2JSONObject(obj.toString());
				dealRequest(json);
			}else{
				log.info("微信支付回调请求验证签名不通过！");
			}
			resultMap.put("return_code", "SUCCESS");
			resultMap.put("return_msg", "<![CDATA[OK]]>");
		}else{
			resultMap.put("return_code", "FAIL");
			resultMap.put("return_msg", "<![CDATA[Parameter is empty!]]>");
		}
		
		return JsonXmlUtil.createXml(resultMap);
	}

	private void dealRequest(JSONObject json) {
		
		String return_code = json.optString("return_code");
		String return_msg = json.optString("return_msg");
		if("SUCCESS".equals(return_code)){
			//成功
			String result_code = json.optString("result_code");
			String out_trade_no = json.optString("out_trade_no"); //商户订单号 
			//存在3种订单，一种是用户购买相册， 一种是商户购买服务时间（该种情况会有attach夹带shopId字段），另一种是VIP开通或续费该种情况会有attach夹带vip字段）
			if(json.optString("attach") != null && json.optString("attach").indexOf("shopId:::") != -1){
				String shopId = json.optString("attach").substring(json.optString("attach").indexOf("shopId:::")+9);
				String transaction_id = json.optString("transaction_id");
				String sql = "update t_shop_renewal set transaction_id = ?, result_code = ?,return_code=?,return_msg=?,update_time = ? where out_trade_no = ?";
				if("SUCCESS".equals(result_code)){
					dealServer(shopId);
				}
				baseDAO.execteNativeBulk(sql, transaction_id, result_code, return_code, return_msg, DateUtil.now(), out_trade_no);
			
			}else if(json.optString("attach") != null && json.optString("attach").indexOf("vip:::") != -1){
				//VIP开通或续费
				List<TVipOrder> orderList = baseDAO.getGenericByHql("from TVipOrder where orderId = ? and isPay = 0", out_trade_no);
				if(orderList != null && orderList.size() > 0){
					TVipOrder order = orderList.get(0);
					TUser user = baseDAO.get(TUser.class, order.getUserId());
					TVipPackage pack = baseDAO.get(TVipPackage.class, order.getVipPackageId());
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if(user != null){
						if(user.getVipExpiredDate() == null || "".equals(user.getVipExpiredDate())){
							int second = (int) (System.currentTimeMillis() /1000 + (pack.getPackageDays() * 24 * 60 * 60));
							user.setVipExpiredDate(format.format(second));
							user.setIsVip(1);
							baseDAO.update(user);
						}else{
							String expiredDate = user.getVipExpiredDate();
							try {
								if(format.parse(expiredDate).getTime() < (int) (System.currentTimeMillis()/ 1000)){
									//过期时间小于当前时间，那么过期时间从今天开始
									int second = (int) (System.currentTimeMillis() /1000 + (pack.getPackageDays() * 24 * 60 * 60));
									user.setVipExpiredDate(format.format(second));
									user.setIsVip(1);
									baseDAO.update(user);
								}else{
									//在原过期时间上累加
									int second = (int) (format.parse(expiredDate).getTime() + (pack.getPackageDays() * 24 * 60 * 60));
									user.setVipExpiredDate(format.format(second));
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
					order.setTradeNo(json.optString("transaction_id"));
					baseDAO.update(order);
				}
			} else{
				
				List<TShopAlbumOrder> orderList = baseDAO.getGenericByHql("from TShopAlbumOrder where orderNo = ?", out_trade_no);
				if(orderList != null && orderList.size() > 0){
					TShopAlbumOrder order = orderList.get(0); 
					//待支付状态才处理
					if(order.getStatus() == 0){
						//判断支付总额和实际支付的总额是否相等,防止数据被更改，实际支付金额不等于需要支付的金额
						if(order.getTotalFee() == json.optInt("total_fee")){
							
							order.setBankType(json.optString("bank_type"));
							order.setErrCode(json.optString("err_code"));
							order.setErrCodeDes(json.optString("err_code_des"));
							order.setPayComplateTime(json.optString("time_end"));
							if("SUCCESS".equals(result_code)){
								order.setStatus(1); //成功
								updateShopAlbumPayStatus(order.getAlbumId(), 1);
							}else{
								order.setStatus(2); //失败
							}
							order.setTransactionId(json.optString("transaction_id"));
							baseDAO.update(order);
						}
					}
				}else{
					//result.put("code", "FAIL");
					//result.put("msg", "Order is not exist!");
					log.info("商户订单不存在！");
				}
			}
			
		}else{
			log.info("微信支付回调返回结果：FAIL，return_msg："+return_msg);
		}
		
	}

	private void dealServer(String shopId) {
		
		TShopInfo info = baseDAO.get(TShopInfo.class, shopId);
		String endTime = info.getServiceEndTime();
		if(endTime != null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				long timeL = format.parse(endTime).getTime();
				info.setServiceEndTime(format.format(timeL + 365l * 24 * 60 * 60 * 1000));
				baseDAO.update(info);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//下发短信给系统管理员
		String adminTel = SystemConfig.getValue("adminTel");
		if(adminTel != null && !"".equals(adminTel)){
			
			try {
				SingletonClient.getClient().sendSMS(new String[] {adminTel}, info.getShopName()+" "+  SystemConfig.getValue("msg.shop.buy.service.content")+" 微信支付", "",5);
			} catch (RemoteException e) {
				log.info("短信发送失败：接收号码："+adminTel +" 短信内容："+info.getShopName()+" "+  SystemConfig.getValue("msg.shop.buy.service.content")+" 微信支付");
			}
		}
		
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
					boolean b =FileZipUtil.zip(urls.toArray(files), Constant.resPath + to +".zip");
					if(b){
						log.info("生成压缩包成功,地址："+to +".zip");
						baseDAO.execteNativeBulk("update t_shop_album set download_address = ?, download_secret = ?, pay_time = ? where id = ?", to +".zip",RandomGUID.getRandomString(4), format.format(new Date()), albumId);
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
