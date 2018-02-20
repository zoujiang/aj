package com.aj.pay.service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.pay.util.HttpUtil;
import com.aj.pay.util.JsonXmlUtil;
import com.aj.pay.util.SignUtil;
import com.aj.pay.vo.TShopAlbumOrder;
import com.frame.core.constant.FtpConstant;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


/**
 * 微信支付订单查询
 * */

@Service("wxPayOrderQuery")
public class WxPayOrderQueryService implements PublishService{

	private Logger log = Logger.getLogger(WxPayOrderQueryService.class);
	
	
	private static String WX_PAY_ORDER_QUERY_ADDRESS = SystemConfig.getValue("wx.pay.order.query.address");//微信接口地址
	private static String WX_PAY_APP_ID = SystemConfig.getValue("wx.pay.appId");//微信APPID
	private static String WX_PAY_MCH_ID = SystemConfig.getValue("wx.pay.mchId");//微信商户ID
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
		
		
		//查询是否有生成过微信订单
		List<TShopAlbumOrder> albumOrderList =  baseDAO.getGenericByHql("from TShopAlbumOrder where albumId = ? and payType = '1' and status != 3", albumId);
		TShopAlbumOrder albumOrder = null;
		if(albumOrderList != null && albumOrderList.size() > 0){
			albumOrder = albumOrderList.get(0);
			if(albumOrder != null){
				if(albumOrder.getStatus() == 1 ||albumOrder.getStatus() ==2 ){
					result.put("orderStatus", albumOrder.getStatus() +"");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					return returnJSON.toString();
				}
				SortedMap<String, Object> parm = new TreeMap<String, Object>();
				parm.put("appid", WX_PAY_APP_ID);
				parm.put("mch_id", WX_PAY_MCH_ID);
				//parm.put("transaction_id", albumOrder.getTransactionId());
				parm.put("out_trade_no", albumOrder.getOrderNo());
				parm.put("nonce_str", RandomGUID.getRandomString(32).toUpperCase());
				String sign = SignUtil.createMD5Sign(parm, WX_PAY_APP_SECRET);
				parm.put("sign", sign);
				
				String xml = JsonXmlUtil.getRequestXml(parm);
				String resultMsg = null;
				try {
					resultMsg = HttpUtil.doPostSSL(WX_PAY_ORDER_QUERY_ADDRESS, xml);
				} catch (Exception e) {
					log.info("发起微信支付异常："+e);
				}
				if(resultMsg != null && !"".equals(resultMsg)){
					
					JSONObject resultJson = JsonXmlUtil.xml2JSONObject(resultMsg);
					String return_code = resultJson.getString("return_code");
					String return_msg = resultJson.getString("return_msg");
					log.info("调用微信支付，返回结果：return_code："+return_code +"；return_msg："+return_msg);
					if(return_code != null && "SUCCESS".equals(return_code)){
						//调用成功
						String result_code = resultJson.getString("result_code");
						String err_code = resultJson.getString("err_code");
						String err_code_des = resultJson.getString("err_code_des");
						String back_sign = resultJson.getString("sign"); //返回的签名
						log.info("调用微信支付，调用成功，业务处理结果：result_code："+result_code +"；err_code："+err_code+";err_code_des:"+err_code_des);
						if("SUCCESS".equals(result_code)){
							//交易状态SUCCESS—支付成功
							/*REFUND—转入退款
							NOTPAY—未支付
							CLOSED—已关闭
							REVOKED—已撤销（刷卡支付）
							USERPAYING--用户支付中
							PAYERROR--支付失败(其他原因，如银行返回失败)*/
							String trade_state = resultJson.getString("trade_state");
							int orderStatus = 1;
							if("SUCCESS".equals(trade_state)){
								orderStatus = 1;
							}else if("REFUND".equals(trade_state) || "USERPAYING".equals(trade_state)){
								orderStatus = 4;
							}else if("NOTPAY".equals(trade_state)){
								orderStatus = 0;
							}else if("PAYERROR".equals(trade_state)){
								orderStatus = 2;
							}else{
								orderStatus = 3;
							}
							updateShopAlbumPayStatus(albumId, orderStatus);
							
							result.put("orderStatus", orderStatus+"");
							returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "");
							return returnJSON.toString();
						}else{
							result.put("orderStatus", albumOrder.getStatus());
							returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "");
							return returnJSON.toString();
						}
					}else{
						result.put("orderStatus", albumOrder.getStatus());
						returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "");
						return returnJSON.toString();
					}
					
				}else{
					result.put("orderStatus", albumOrder.getStatus());
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
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
