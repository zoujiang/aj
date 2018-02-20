package com.aj.pay.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.pay.vo.TShopAlbumOrder;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.frame.core.constant.FtpConstant;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 支付宝支付订单查询
 * */

@Service("aliPayOrderQuery")
public class AliPayOrderQueryService implements PublishService{

	private Logger log = Logger.getLogger(AliPayOrderQueryService.class);
	
	
	private static String ALI_PAY_ISDEBUG = SystemConfig.getValue("ali.pay.isdebug");//是否调试
	private static String ALI_PAY_ADDRESS = SystemConfig.getValue("ali.pay.address");//支付宝接口地址
	private static String ALI_PAY_ADDRESS_DEBUG = SystemConfig.getValue("ali.pay.address.debug");//支付宝支付测试地址
	private static String ALI_PAY_APP_ID = SystemConfig.getValue("ali.pay.appId");//支付宝APPID
	private static String ALI_PAY_FORMAT = "json";
	private static String ALI_PAY_APP_PRIVATE_KEY = SystemConfig.getValue("ali.pay.app.private.key");//密钥
	private static String ALI_PAY_PUBLIC_KEY = SystemConfig.getValue("ali.pay.app.private.key");  //支付宝公钥
	private static String ALI_PAY_SIGN_TYPE = "RSA";  //签名类型
	private static String ALI_PAY_CHARSET = "utf-8";  //
	private static String ALI_PAY_ORDER_QUERY = "alipay.trade.query";
	private static String ALI_PAY_VERSION = "1.0";//固定
	private String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
	private String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
	private String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
	private String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
	private int port =  SystemConfig.getValue(FtpConstant.PORT) == null ? 21 : Integer.parseInt(SystemConfig.getValue(FtpConstant.PORT).toString());
	
			
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		String callType = json.optString("callType");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String albumId = params.optString("albumId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		
		JSONObject result = new JSONObject();
		if(callType == null || "".equals(callType)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "callType为空！");
			return returnJSON.toString();
		}
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(albumId == null || "".equals(albumId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "albumId为空！");
			return returnJSON.toString();
		}
		
		
		//查询是否有生成过支付宝订单
		List<TShopAlbumOrder> albumOrderList =  baseDAO.getGenericByHql("from TShopAlbumOrder where albumId = ? and payType = '0' and status != 3", albumId);
		TShopAlbumOrder albumOrder = null;
		if(albumOrderList != null && albumOrderList.size() > 0){
			albumOrder = albumOrderList.get(0);
			if(albumOrder != null){
				if(albumOrder.getStatus() == 1 || albumOrder.getStatus() == 2){
					result.put("orderStatus", albumOrder.getStatus()+"");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					return returnJSON.toString();
				}
				
				String date = DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2);
				Map<String, String> map = new HashMap<String, String>();
				map.put("out_trade_no", albumOrder.getOrderNo());
				map.put("trade_no", albumOrder.getTransactionId());
				
				String sign;
				try {
					sign = AlipaySignature.rsaSign(map, ALI_PAY_APP_PRIVATE_KEY, ALI_PAY_CHARSET);
				} catch (AlipayApiException e) {
					log.info("支付宝支付签名异常："+e);
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "支付宝支付签名异常");
					return returnJSON;
				}	
				
				result.put("app_id", ALI_PAY_APP_ID);
				result.put("method", ALI_PAY_ORDER_QUERY);
				result.put("charset", ALI_PAY_CHARSET);
				result.put("sign_type", ALI_PAY_SIGN_TYPE);
				result.put("sign", sign);
				result.put("timestamp", date);
				result.put("version", ALI_PAY_VERSION);
				result.put("biz_content", JSONObject.fromObject(map).toString());
				

				String url = "";
				if(ALI_PAY_ISDEBUG != null && Boolean.valueOf(ALI_PAY_ISDEBUG)){
					url = ALI_PAY_ADDRESS_DEBUG;
				}else{
					url = ALI_PAY_ADDRESS;
				}
				AlipayClient alipayClient = new DefaultAlipayClient(url, ALI_PAY_APP_ID,  ALI_PAY_APP_PRIVATE_KEY,  ALI_PAY_FORMAT,  ALI_PAY_CHARSET,  ALI_PAY_PUBLIC_KEY,  ALI_PAY_SIGN_TYPE);
				AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
				request.setBizContent(JSONObject.fromObject(map).toString());
				AlipayTradeQueryResponse response = null;
				try {
					response = alipayClient.execute(request);
				} catch (AlipayApiException e) {
					log.info("支付宝订单支付状态查询调用支付宝接口异常："+e);
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "支付宝订单支付状态查询调用支付宝接口异常");
					return returnJSON;
				}
				if(response.isSuccess()){
					albumOrder.setStatus(1); //成功
					updateShopAlbumPayStatus(albumOrder.getAlbumId(), 1);
					//调用成功，判断业务状态是否成功
					String trade_status = response.getTradeStatus();
					/*枚举名称	枚举说明
					WAIT_BUYER_PAY	交易创建，等待买家付款
					TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
					TRADE_SUCCESS	交易支付成功
					TRADE_FINISHED	交易结束，不可退款*/
					
					if("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)){
						albumOrder.setStatus(1); //成功
						result.put("orderStatus", "1");
						updateShopAlbumPayStatus(albumOrder.getAlbumId(), 1);
					}else{
						albumOrder.setStatus(2); //失败
						result.put("orderStatus", "2");
					}
					albumOrder.setTransactionId(response.getTradeNo());
					baseDAO.update(albumOrder);
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					return returnJSON.toString();
				} else {
					log.info("调用支付宝订单查询失败：code:"+response.getCode() +";msg:"+response.getMsg()+";sub_code:"+response.getSubCode()+";sub_msg:"+response.getSubMsg());
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "调用支付宝订单查询失败："+response.getMsg());
					return returnJSON.toString();
				}
			}else{
				result.put("orderStatus", "0");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}	
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有找到对应订单");
			return returnJSON.toString();
		}
	}

	
	private void updateShopAlbumPayStatus(String albumId, int status) {
		int i = baseDAO.execteNativeBulk("update t_shop_album set had_paid = ? where id = ?", new Object[]{status, albumId});
		if(i > 0 && status == 1){
			//生成片源下载地址
			List<Map<String, Object>> list = baseDAO.query("SELECT a.shop_id shopId, a.user_id userId, a.album_name albumNmae FROM t_shop_album a WHERE id = ?", albumId);
			if(list != null && list.size() > 0){
				Map<String, Object> album = list.get(0);
				
				FtpUtil ftp = new FtpUtil(ftpAddress, port, username, password);
				try {
					ftp.login();
					String from = path +"/"+ album.get("shopId") +"/"+ album.get("userId") +"/Album/" + albumId;
					String to ="/"+ album.get("shopId") +"/"+ album.get("userId") +"/Album/" + albumId;
					boolean b = ftp.zip(from,  com.frame.core.constant.Constant.resPath + to, album.get("albumNmae")+".zip");
					if(b){
						log.info("生成压缩包成功,地址："+to +"/"+album.get("albumNmae")+".zip");
						String d_url =(to +"/"+album.get("albumNmae")+".zip");
						String d_sec =RandomGUID.getRandomString(4);
						baseDAO.execteNativeBulk("update t_shop_album set download_address = ? ,download_secret = ? where id = ?", d_url, d_sec, albumId);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					ftp.closeServer();
				}
			}
		}
	}
}
