package com.qm.shop.renewal.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.constant.Constant;
import com.frame.core.util.DateUtil;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.DataModel;
import com.qm.shop.account.service.ShopAccountService;
import com.qm.shop.baseinfo.service.ShopBaseinfoService;
import com.qm.shop.payable.service.ShopPayAbleService;
import com.qm.shop.renewal.service.ShopRenewalService;
import com.qm.shop.util.HttpUtil;
import com.qm.shop.util.JsonXmlUtil;
import com.qm.shop.util.SignUtil;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopRenewal extends FtpImgDownUploadAction {
	
	@Autowired
	private ShopPayAbleService shopPayAbleService;
	
	@Autowired
	private ShopBaseinfoService shopBaseinfoService;
	@Autowired
	private ShopRenewalService shopRenewalService;
	
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
	
	
	private static String WX_PAY_ADDRESS = SystemConfig.getValue("wx.pay.address");//微信接口地址
	private static String WX_PAY_APP_ID = SystemConfig.getValue("wx.pay.appId");//微信APPID
	private static String WX_PAY_MCH_ID = SystemConfig.getValue("wx.pay.mchId");//微信商户ID
	private static String WX_PAY_APP_SECRET = SystemConfig.getValue("wx.pay.appsecret");//密钥
	private static String WX_PAY_DEVICE = "WEB";  //设备号 默认WEB
	private static String WX_PAY_SIGN_TYPE = "MD5";  //签名类型
	private static String WX_PAY_NOTIFY_URL = SystemConfig.getValue("wx.pay.notify.url");  //通知地址： 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private static String WX_PAY_TRADE_TYPE = "NATIVE";  //交易类型 扫码
	private static String PAY_TYPE_WX = "1";  //微信支付类型
	private static String WX_PAY_PACKAGE = "Sign=WXPay";  //扩展字段 固定Sign=WXPay
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping("/renewal/initAli")
	@ResponseBody
	public DataModel<Map<String, Object>> initAli(HttpServletRequest request) {
		
		//String shopId = (String) request.getSession().getAttribute(Constant.Login_USER_SHOP_ID);
		String shopId ="SP001";
		String prepay_id = null;
		String goodsName = "续费一年商家服务";
		
		int  total_fee = 100; //总金额，单位：分
	
		//AlipayClient alipayClient = new DefaultAlipayClient(url, ALI_PAY_APP_ID,  ALI_PAY_APP_PRIVATE_KEY,  ALI_PAY_FORMAT,  ALI_PAY_CHARSET,  ALI_PAY_PUBLIC_KEY,  ALI_PAY_SIGN_TYPE);
		String date = DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2);
		Map<String, String> map = new HashMap<String, String>();
		String out_trade_no = UUID.randomUUID().toString().replaceAll("-", "");
		map.put("out_trade_no", out_trade_no);
		map.put("total_amount", total_fee * 0.01 +"");
		map.put("subject", goodsName);
		map.put("store_id",  shopId);
		map.put("timeout_express",  "120m");
		
		AlipayClient alipayClient = new DefaultAlipayClient(ALI_PAY_ADDRESS, ALI_PAY_APP_ID, ALI_PAY_APP_PRIVATE_KEY, ALI_PAY_FORMAT, ALI_PAY_CHARSET, ALI_PAY_PUBLIC_KEY, ALI_PAY_SIGN_TYPE); //获得初始化的AlipayClient
		AlipayTradePrecreateRequest atpr = new AlipayTradePrecreateRequest();//创建API对应的request类
		atpr.setBizContent(JSON.toJSONString(map));//设置业务参数
		AlipayTradePrecreateResponse response;
		try {
			response = alipayClient.execute(atpr);
			System.out.print(response.getBody());
			//根据response中的结果继续业务逻辑处理
		} catch (AlipayApiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}
	@RequestMapping("/renewal/initWx")
	@ResponseBody
	public String initWx(HttpServletRequest request) {
		
		JSONObject jo = new JSONObject();
		String shopId = (String) request.getSession().getAttribute(Constant.Login_USER_SHOP_ID);
		
		//String shopId = "SP001";
		String prepay_id = null;
		
		String goodsName = "续费一年商家服务";
		int  total_fee = 1; //总金额，单位：分
		Map<String,Object> pable = shopPayAbleService.init();
		if(pable != null && pable.get("service_amount") !=null && !"".equals(pable.get("service_amount").toString())){
			//数据库存储的金额是元，微信支付的单位是分
			total_fee = (int)(Double.parseDouble(pable.get("service_amount").toString().trim()) * 100);
			String nonce_str = RandomGUID.getRandomString(32).toUpperCase();
			String out_trade_no = RandomGUID.getRandomString(32).toUpperCase();
			jo.put("out_trade_no", out_trade_no);
			SortedMap<String, Object> parm = new TreeMap<String, Object>();
			parm.put("appid", WX_PAY_APP_ID);
			parm.put("mch_id", WX_PAY_MCH_ID);
			parm.put("device_info", WX_PAY_DEVICE);
			parm.put("nonce_str", nonce_str);
			parm.put("sign_type", WX_PAY_SIGN_TYPE);
			parm.put("body", goodsName);
			parm.put("out_trade_no", out_trade_no);
			parm.put("attach", "shopId:::"+shopId);  //附加数据，把店铺ID传过去
			parm.put("total_fee", total_fee+"");  //单位：分
			parm.put("spbill_create_ip", request.getRemoteAddr()); 
			//parm.put("spbill_create_ip", "172.0.0.1"); 
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
			log.info("发起微信支付,返回结果：" +resultMsg);
			jo.put("success", false);
			jo.put("msg", "初始化微信扫码支付失败");
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
						String code_url = resultJson.optString("code_url");
						//生成客户端支付信息
						System.out.println(code_url);
						jo.put("success", true);
						jo.put("code_url", code_url);
						String uuid = UUID.randomUUID().toString().replaceAll("-", "");
						String date = DateUtil.getNowDate();
						//记录本次生成的支付信息
						shopRenewalService.save(uuid,out_trade_no,shopId, total_fee*0.01, prepay_id,code_url, 2, date);
					}
				}
				
			}
		}else{
			jo.put("success", false);
			jo.put("msg", "初始化微信扫码支付失败");
			log.info("初始化微信支付二维码失败，运营端未设置年服务费金额。");
		}
		
		return jo.toString();
				
	}	
	@RequestMapping("/renewal/initShopInfo")
	@ResponseBody
	public String initShopInfo(HttpServletRequest request) {
		
		JSONObject jo = new JSONObject();
		String shopId = (String) request.getSession().getAttribute(Constant.Login_USER_SHOP_ID);
		
		Map<String,Object> shopInfo =  shopBaseinfoService.findById(shopId);
		jo.put("serviceEndTime", shopInfo.get("serviceEndTime"));
		jo.put("shopName",  shopInfo.get("shopName"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long left =	DateUtil.getDaysBetween(new Date(),format.parse(shopInfo.get("serviceEndTime").toString()));
			jo.put("left", left);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		Map<String,Object> pable = shopPayAbleService.init();
		if(pable != null && pable.get("service_amount") !=null && !"".equals(pable.get("service_amount").toString())){
			//数据库存储的金额是元，微信支付的单位是分
			jo.put("total_fee",  pable.get("service_amount").toString().trim());
			jo.put("success", true);
		}else{
			jo.put("success", false);
			jo.put("msg", "初始化失败");
			log.info("初始化微信支付二维码失败，运营端未设置年服务费金额。");
		}
		
		return jo.toString();
		
	}	
	@RequestMapping("/renewal/queryOrderStatus")
	@ResponseBody
	public String queryOrderStatus(HttpServletRequest request,String out_trade_no) {
		
		JSONObject jo = new JSONObject();
		if(out_trade_no != null && !"".equals(out_trade_no)){
			
			Map<String,Object> result = shopRenewalService.queryByOutTradeNo(out_trade_no);
			if(result != null && result.get("result_code") != null && "SUCCESS".equalsIgnoreCase(result.get("result_code").toString())
					&& result.get("return_code") != null && "SUCCESS".equalsIgnoreCase(result.get("return_code").toString())){
				jo.put("success", true);
			}else{
				jo.put("success", false);
			}
		}else{
			jo.put("success", false);
		}
		return jo.toString();
		
	}	
	
	public static void main(String[] args) {
		ShopRenewal renew = new ShopRenewal();
		renew.initAli(null);
	}		
}
