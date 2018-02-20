package com.aj.pay.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.pay.util.HttpUtil;
import com.aj.pay.util.JsonXmlUtil;
import com.aj.pay.util.SignUtil;
import com.aj.pay.vo.TShopAlbumOrder;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.UUIDUtil;


/**
 * 微信支付
 * */

@Service("wxPay")
public class WxPayService implements PublishService{

	private Logger log = Logger.getLogger(WxPayService.class);
	
	
	private static String WX_PAY_ADDRESS = SystemConfig.getValue("wx.pay.address");//微信接口地址
	private static String WX_PAY_APP_ID = SystemConfig.getValue("wx.pay.appId");//微信APPID
	private static String WX_PAY_MCH_ID = SystemConfig.getValue("wx.pay.mchId");//微信商户ID
	private static String WX_PAY_APP_SECRET = SystemConfig.getValue("wx.pay.appsecret");//密钥
	private static String WX_PAY_DEVICE = "WEB";  //设备号 默认WEB
	private static String WX_PAY_SIGN_TYPE = "MD5";  //签名类型
	private static String WX_PAY_NOTIFY_URL = SystemConfig.getValue("wx.pay.notify.url");  //通知地址： 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private static String WX_PAY_TRADE_TYPE = "APP";  //交易类型
	private static String PAY_TYPE_WX = "1";  //微信支付类型
	private static String WX_PAY_PACKAGE = "Sign=WXPay";  //扩展字段 固定Sign=WXPay
			
	
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
		String ipAddress = params.optString("ipAddress");
		
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
		if(ipAddress == null || "".equals(ipAddress)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "ipAddress为空！");
			return returnJSON.toString();
		}
		String sql = "SELECT s.shop_name shopName, a.album_name albumName, a.is_pay isPay, a.original_price originalPrice,a.create_time createTime, a.pay_type payType FROM t_shop_album a, t_shop_info s WHERE a.shop_id = s.id AND a.had_paid != 1 AND a.id = ?";
		List<Map<String, Object>> albumList = baseDAO.getGenericBySQL(sql , new Object[]{albumId});
		if(albumList != null && albumList.size() > 0){
			Map<String, Object> album = albumList.get(0);
			String createTime = album.get("createTime").toString();
			String prepay_id = null;
			String goodsName = "购买"+album.get("albumName")+"相册";
			
			int month = DateUtil.monthSpace(DateUtil.stringToTimestamp(createTime, DateUtil.DATE_TIME_PATTERN2), new Date());
			int  total_fee = 0; //总金额，单位：分
			if(album.get("originalPrice") == null || "".equals(album.get("originalPrice").toString())){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "原始价格有误！");
				return returnJSON.toString();
			}
			double od = Double.parseDouble(album.get("originalPrice").toString());
			if(album.get("payType") == null || "".equals(album.get("payType").toString())){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "付款方式有误！");
				return returnJSON.toString();
			}
			String[] payTypes = album.get("payType").toString().split("\\|");
			// 原价od * 折扣百分比 * 0.01  * 100（分）
			if(month <= 3){
				total_fee = (int)(od * Integer.parseInt(payTypes[0]));
			}else if(month <= 6){
				total_fee = (int)(od * Integer.parseInt(payTypes[1]));
			}else if(month <= 9){
				total_fee = (int)(od * Integer.parseInt(payTypes[2]));
			}else if(month <= 12){
				total_fee = (int)(od * Integer.parseInt(payTypes[3]));
			}else if(month <= 24){
				total_fee = (int)(od * Integer.parseInt(payTypes[4]));
			}else{
				total_fee = (int)(od * Integer.parseInt(payTypes[5]));
			}
			//查询是否有生成过微信订单
			List<TShopAlbumOrder> albumOrderList =  baseDAO.getGenericByHql("from TShopAlbumOrder where albumId = ? and payType = '1' and status = 0", albumId);
			TShopAlbumOrder albumOrder = null;
			if(albumOrderList != null && albumOrderList.size() > 0){
				albumOrder = albumOrderList.get(0);
				if(albumOrder != null){
					if(albumOrder.getTotalFee() == total_fee && albumOrder.getPrePayId() != null && !"".equals(albumOrder.getPrePayId())){
						//两次支付金额一致,沿用之前的预支付ID,微信支付有默认的过期时间，所以每次都生成id
					/*	Map<String, Object> payInfo = createAppPayInfo(albumOrder.getPrePayId());
						returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
						returnJSON.put("result", payInfo);
						returnJSON.put("errorMsg", "");
						return returnJSON.toString(); */
					}
				}
			}	
			
			String nonce_str = RandomGUID.getRandomString(32).toUpperCase();
			String out_trade_no = UUIDUtil.uuid();
			
			SortedMap<String, Object> parm = new TreeMap<String, Object>();
			parm.put("appid", WX_PAY_APP_ID);
			parm.put("mch_id", WX_PAY_MCH_ID);
			parm.put("device_info", WX_PAY_DEVICE);
			parm.put("nonce_str", nonce_str);
			parm.put("sign_type", WX_PAY_SIGN_TYPE);
			parm.put("body", goodsName);
			parm.put("out_trade_no", out_trade_no);
			parm.put("total_fee", total_fee+"");  //单位：分
			parm.put("spbill_create_ip", ipAddress); 
			parm.put("notify_url", WX_PAY_NOTIFY_URL); 
			parm.put("trade_type", WX_PAY_TRADE_TYPE); 
			String sign = SignUtil.createMD5Sign(parm, WX_PAY_APP_SECRET);
			parm.put("sign", sign);
			String xml = JsonXmlUtil.getRequestXml(parm);
			log.info("调用微信支付请求参数："+ xml);
			String resultMsg = null;
			try {
				resultMsg = HttpUtil.doPostSSL(WX_PAY_ADDRESS, xml);
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
					String result_code = resultJson.optString("result_code");
					String err_code = resultJson.optString("err_code");
					String err_code_des = resultJson.optString("err_code_des");
					String back_sign = resultJson.optString("sign"); //返回的签名
					log.info("调用微信支付，调用成功，业务处理结果：result_code："+result_code +"；err_code："+err_code+";err_code_des:"+err_code_des);
					if("SUCCESS".equals(result_code)){
						//预支付ID，返回给客户端
						prepay_id = resultJson.optString("prepay_id");
						//生成客户端支付信息
						Map<String, Object> payInfoMap = createAppPayInfo(prepay_id);
						returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
						returnJSON.put("result", payInfoMap);
						returnJSON.put("errorMsg", "");
					}else{
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "请求支付失败，错误码："+err_code+"；原因："+err_code_des);
					}
				}else{
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "请求支付失败，原因："+return_msg);
				}
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "请求支付超时");
			}
			//保存订单信息
			TShopAlbumOrder order = new TShopAlbumOrder();
			order.setId(UUIDUtil.uuid());
			order.setAlbumId(albumId);
			order.setCreateTime(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
			order.setGoodsName(goodsName);
			order.setOrderNo(out_trade_no);
			order.setPayType(PAY_TYPE_WX);
			order.setPrePayId(prepay_id);
			order.setStatus(0);
			order.setTotalFee(total_fee);
			baseDAO.save(order);
			
			if(albumOrder != null){
				//把旧的支付订单作废
				albumOrder.setStatus(3);
				baseDAO.update(albumOrder);
			}
			
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "相册不存在");
		}
		
		return returnJSON.toString();
	}

	private Map<String, Object> createAppPayInfo(String prepay_id) {
		
		SortedMap<String, Object> map = new TreeMap<String, Object>();
		map.put("appid", WX_PAY_APP_ID);
		map.put("partnerid", WX_PAY_MCH_ID);
		map.put("prepayid", prepay_id);
		map.put("package", WX_PAY_PACKAGE);
		map.put("noncestr", RandomGUID.getRandomString(32).toUpperCase());
		map.put("timestamp", (int) (System.currentTimeMillis() / 1000)+"");
		String sign = SignUtil.createMD5Sign(map, WX_PAY_APP_SECRET);
		map.put("sign", sign);
		return map;
	}

}
