package com.qm.shop.payable.action;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.DateUtil;
import com.frame.core.util.GUID;
import com.qm.shop.payable.service.ShopPayAbleService;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopPayAbleAction extends FtpImgDownUploadAction {
	@Autowired
	private ShopPayAbleService shopPayAbleService;
	
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/payAble/add")
	@ResponseBody
	public String add(String id ,String accountZFB, String secretZFB,String accountWX, String secretWX, String payTypeAndroidZFB, String payTypeAndroidWX, String payTypeIOSZFB, String payTypeIOSWX, 
			String Android_zfb,String Android_zfb_v, String Android_wx, String Android_wx_v, String IOS_zfb, String IOS_zfb_v, String IOS_wx, String IOS_wx_v ) {
		
		String date = DateUtil.dateFromatYYYYMMddHHmmss(new Date());
		String msg = "更新";
		if(id == null || "".equals(id)){
			id = GUID.generateID("PAY");
			msg = "保存";
		}
		String createTime = date;
		String updateTime = date;
		String payTypeAndroid = "";
		if(payTypeAndroidZFB != null && !"".equals(payTypeAndroidZFB)){
			payTypeAndroid ="00";
		}else{
			payTypeAndroid ="01";
		}
		if(payTypeAndroidWX != null && !"".equals(payTypeAndroidWX)){
			payTypeAndroid +="10";
		}else{
			payTypeAndroid +="11";
		}
		String payTypeIOS = "";
		if(payTypeIOSZFB != null && !"".equals(payTypeIOSZFB)){
			payTypeIOS ="00";
		}else{
			payTypeIOS ="01";
		}
		if(payTypeIOSWX != null && !"".equals(payTypeIOSWX)){
			payTypeIOS +="10";
		}else{
			payTypeIOS +="11";
		}
		String accessAndroidZFB = Android_zfb+"|"+Android_zfb_v;
		String accessAndroidWX = Android_wx+"|"+Android_wx_v;
		String accessIOSZFB = IOS_zfb+"|"+IOS_zfb_v;
		String accessIOSWX = IOS_wx+"|"+IOS_wx_v;
		
		
		
		int i = shopPayAbleService.saveOrUpdate(id, accountZFB, secretZFB, accountWX, secretWX, payTypeAndroid, payTypeIOS, accessAndroidZFB, accessAndroidWX,accessIOSZFB, accessIOSWX, createTime, updateTime);
		JSONObject modelMap = new JSONObject();  
		if(i > 0){
			modelMap.put("success", true);
			modelMap.put("message", msg+"成功");
		}else{
			modelMap.put("success", false);
			modelMap.put("message", msg+"失败");
		}
		return modelMap.toString();
	}
	
	@RequestMapping("/payAble/init")
	@ResponseBody
	public String init() {
		
		Map<String, Object> map = shopPayAbleService.init();
		if(map == null || map.isEmpty()){
			return null;
		}
		Object payTypeAndroid = map.get("pay_type_android");
		if(payTypeAndroid != null && !"".equals(payTypeAndroid)){
			
			String payTypeAndroidZFB = payTypeAndroid.toString().substring(1, 2);
			String payTypeAndroidWX = payTypeAndroid.toString().substring(3, 4);
			map.put("payTypeAndroidZFB", payTypeAndroidZFB);
			map.put("payTypeAndroidWX", payTypeAndroidWX);
		}
		Object payTypeIos = map.get("pay_type_ios");
		if(payTypeIos != null && !"".equals(payTypeIos)){
			
			String payTypeIOSZFB = payTypeIos.toString().substring(1, 2);
			String payTypeIOSWX = payTypeIos.toString().substring(3, 4);
			map.put("payTypeIOSZFB", payTypeIOSZFB);
			map.put("payTypeIOSWX", payTypeIOSWX);
		}
		Object accessAndroidZfb = map.get("access_android_zfb");
		if(accessAndroidZfb != null && !"".equals(accessAndroidZfb)){
			String[] ss = accessAndroidZfb.toString().split("\\|");
			String Android_zfb = ss[0];
			String AndroidZfbV = ss[1];
			map.put("Android_zfb", Android_zfb);
			map.put("AndroidZfbV", AndroidZfbV);
		}
		Object accessAndroidWx = map.get("access_android_wx");
		if(accessAndroidWx != null && !"".equals(accessAndroidWx)){
			String[] ss = accessAndroidWx.toString().split("\\|");
			String Android_wx = ss[0];
			String AndroidWxV = ss[1];
			map.put("Android_wx", Android_wx);
			map.put("AndroidWxV", AndroidWxV);
		}
		Object accessIosZfb = map.get("access_ios_zfb");
		if(accessIosZfb != null && !"".equals(accessIosZfb)){
			String[] ss = accessIosZfb.toString().split("\\|");
			String Ios_zfb = ss[0];
			String IosZfbV = ss[1];
			map.put("Ios_zfb", Ios_zfb);
			map.put("IosZfbV", IosZfbV);
		}
		Object accessIosWx = map.get("access_ios_wx");
		if(accessIosWx != null && !"".equals(accessIosWx)){
			String[] ss = accessIosWx.toString().split("\\|");
			String Ios_wx = ss[0];
			String IosWxV = ss[1];
			map.put("Ios_wx", Ios_wx);
			map.put("IosWxV", IosWxV);
		}
		
		JSONObject modelMap = new JSONObject();  
		if(map != null && !map.isEmpty()){
			modelMap.put("success", true);
			modelMap.put("data", JSONObject.fromObject(map).toString());
		}else{
			modelMap.put("success", false);
		}
		return modelMap.toString();
	}
	
}
