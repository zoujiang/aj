package com.aj.pay.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.pay.vo.TVipOrder;
import com.aj.sys.vo.TVipPackage;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.UUIDUtil;

import net.sf.json.JSONObject;


/**
 * 开通或续费VIP支付宝支付
 * */

@Service("vipOrderAliPay")
public class VipOrderAlipayPayService implements PublishService{

	private Logger log = Logger.getLogger(VipOrderAlipayPayService.class);
	
	static String API_TYPE ="viprenew_";
	
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
		String packageId = params.optString("packageId");
		
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
		if(packageId == null || "".equals(packageId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "packageId为空！");
			return returnJSON.toString();
		}
		try{
			TVipPackage vipPackage = baseDAO.get(TVipPackage.class, Integer.parseInt(packageId));
			if(vipPackage == null){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "错误的VIP套餐内容！");
				return returnJSON.toString();
			}
			TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
			String goodsName = "";
			if(user.getIsVip() == 0){
				//非VIP
				goodsName = "购买"+vipPackage.getPackageName()+"VIP";
			}else{
				goodsName = "续费"+vipPackage.getPackageName()+"VIP";
			}
			
			//AlipayClient alipayClient = new DefaultAlipayClient(url, ALI_PAY_APP_ID,  ALI_PAY_APP_PRIVATE_KEY,  ALI_PAY_FORMAT,  ALI_PAY_CHARSET,  ALI_PAY_PUBLIC_KEY,  ALI_PAY_SIGN_TYPE);
			String date = DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2);
			Map<String, String> map = new HashMap<String, String>();
			String out_trade_no = UUIDUtil.uuid();
			map.put("out_trade_no", out_trade_no);
			map.put("product_code", ALI_PAY_PRODUCT_CODE);
			map.put("subject", API_TYPE + goodsName);
			map.put("total_amount", vipPackage.getFavorablePrice());
			
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
		
			//保存订单信息
			TVipOrder order = new TVipOrder();
			order.setAmountMoney(vipPackage.getFavorablePrice());
			order.setIsPay(0);
			order.setOrderId(out_trade_no);
			order.setOrderTime(date);
			order.setPayType(0);
			order.setUserId(Integer.parseInt(userId));
			order.setVipPackageId(Integer.parseInt(packageId));
			baseDAO.save(order);
			
			returnJSON.put("result", result);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		
		}catch(Exception e){
			log.info("调用支付宝支付异常："+e);
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "支付宝支付异常");
			return returnJSON;
		}
		
		
		return returnJSON.toString();
	}
}
