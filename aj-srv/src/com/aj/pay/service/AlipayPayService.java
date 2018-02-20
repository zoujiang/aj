package com.aj.pay.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.alipay.api.internal.util.AlipaySignature;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.UUIDUtil;


/**
 * 支付宝支付
 * */

@Service("aliPay")
public class AlipayPayService implements PublishService{

	private Logger log = Logger.getLogger(AlipayPayService.class);
	
	
	private static String ALI_PAY_ISDEBUG = SystemConfig.getValue("ali.pay.isdebug");//是否调试
	private static String ALI_PAY_ADDRESS = SystemConfig.getValue("ali.pay.address");//支付宝接口地址
	private static String ALI_PAY_ADDRESS_DEBUG = SystemConfig.getValue("ali.pay.address.debug");//支付宝支付测试地址
	private static String ALI_PAY_APP_ID = SystemConfig.getValue("ali.pay.appId");//支付宝APPID
	private static String ALI_PAY_FORMAT = "json";
	private static String ALI_PAY_APP_PRIVATE_KEY = SystemConfig.getValue("ali.pay.app.private.key");//密钥
	private static String ALI_PAY_PUBLIC_KEY = SystemConfig.getValue("ali.pay.app.public.key");  //支付宝公钥
	private static String ALI_PAY_SIGN_TYPE = "RSA";  //签名类型
	private static String ALI_PAY_NOTIFY_URL = SystemConfig.getValue("ali.pay.notify.url");  //通知地址： 接收支付宝支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private static String PAY_TYPE = "0";  //支付类型
	private static String ALI_PAY_CHARSET = "utf-8";  //
	private static String ALI_PAY_PRODUCT_CODE = "QUICK_MSECURITY_PAY";  //固定
	private static String ALI_PAY_METHOD = "alipay.trade.app.pay";
	private static String ALI_PAY_VERSION = "1.0";//固定
			
	
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
		
			//把之前的支付宝支付订单作废
			baseDAO.update("update TShopAlbumOrder set status = 3 where albumId = ? and payType = '0'", albumId);
			
			/*String url = "";
			if(ALI_PAY_ISDEBUG != null && Boolean.valueOf(ALI_PAY_ISDEBUG)){
				url = ALI_PAY_ADDRESS_DEBUG;
			}else{
				url = ALI_PAY_ADDRESS;
			}*/
			
			//AlipayClient alipayClient = new DefaultAlipayClient(url, ALI_PAY_APP_ID,  ALI_PAY_APP_PRIVATE_KEY,  ALI_PAY_FORMAT,  ALI_PAY_CHARSET,  ALI_PAY_PUBLIC_KEY,  ALI_PAY_SIGN_TYPE);
			String date = DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2);
			Map<String, String> map = new HashMap<String, String>();
			String out_trade_no = UUIDUtil.uuid();
			map.put("out_trade_no", out_trade_no);
			map.put("product_code", ALI_PAY_PRODUCT_CODE);
			map.put("subject", goodsName);
			map.put("total_amount", total_fee * 0.01+"");
			
			Map<String,String> signMap = new HashMap<String, String>();
			signMap.put("app_id", ALI_PAY_APP_ID);
			signMap.put("biz_content", JSONObject.fromObject(map).toString());
			signMap.put("charset", ALI_PAY_CHARSET);
			signMap.put("method", ALI_PAY_METHOD);
			signMap.put("sign_type", ALI_PAY_SIGN_TYPE);
			signMap.put("timestamp", date);
			signMap.put("version", ALI_PAY_VERSION);
			signMap.put("notify_url", ALI_PAY_NOTIFY_URL);
			
			
			String sign;
			try {
				sign = AlipaySignature.rsaSign(signMap, ALI_PAY_APP_PRIVATE_KEY, ALI_PAY_CHARSET);
			} catch (AlipayApiException e) {
				log.info("支付宝支付签名异常："+e);
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "支付宝支付签名异常");
				return returnJSON;
			}	
			try {
				String stStr = "app_id="+ALI_PAY_APP_ID
						+"&biz_content="+ URLEncoder.encode( JSONObject.fromObject(map).toString(),"utf-8")
						+"&charset="+ALI_PAY_CHARSET
						+"&method="+ALI_PAY_METHOD
						+"&notify_url="+  URLEncoder.encode(ALI_PAY_NOTIFY_URL,"utf-8")
						+"&sign_type="+ALI_PAY_SIGN_TYPE
						+"&timestamp="+ URLEncoder.encode(date,"utf-8")
						+"&version="+ALI_PAY_VERSION
						+"&sign="+ URLEncoder.encode(sign,"utf-8");
						result.put("full_param", stStr);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
			}
			log.info("调用支付宝签名结果：sign:"+sign);
			result.put("app_id", ALI_PAY_APP_ID);
			result.put("method", ALI_PAY_METHOD);
			result.put("charset", ALI_PAY_CHARSET);
			result.put("sign_type", ALI_PAY_SIGN_TYPE);
			result.put("sign", sign);
			result.put("timestamp", date);
			result.put("version", ALI_PAY_VERSION);
			result.put("notify_url", ALI_PAY_NOTIFY_URL);
			result.put("biz_content", JSONObject.fromObject(map).toString());
		
			//保存订单信息
			TShopAlbumOrder order = new TShopAlbumOrder();
			order.setId(UUIDUtil.uuid());
			order.setAlbumId(albumId);
			order.setCreateTime(date);
			order.setGoodsName(goodsName);
			order.setOrderNo(out_trade_no);
			order.setPayType(PAY_TYPE);
			order.setPrePayId(prepay_id);
			order.setStatus(0);
			order.setTotalFee(total_fee);
			baseDAO.save(order);
			returnJSON.put("result", result);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "相册不存在");
		}
		
		return returnJSON.toString();
	}
}
