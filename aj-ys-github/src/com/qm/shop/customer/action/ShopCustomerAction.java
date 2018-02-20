package com.qm.shop.customer.action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.frame.core.util.HttpClient;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.DataModel;
import com.qm.shop.account.service.ShopAccountService;
import com.qm.shop.account.vo.ShopAccountVO;
import com.qm.shop.album.service.ShopAlbumService;
import com.qm.shop.album.service.ShopDynamicAlbumService;
import com.qm.shop.baseinfo.service.ShopBaseinfoService;
import com.qm.shop.customer.service.ShopCustomerService;
import com.qm.shop.customer.vo.ShopCustomerVO;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopCustomerAction extends FtpImgDownUploadAction {
	@Autowired
	private ShopAccountService shopAccountService;
	@Autowired
	private ShopCustomerService shopCustomerService;
	@Autowired
	private ShopBaseinfoService shopBaseinfoService;
	@Autowired
	private ShopAlbumService shopAlbumService;
	@Autowired
	private ShopDynamicAlbumService shopDynamicAlbumService;
	
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/customer/list")
	@ResponseBody
	public DataModel<Map<String, Object>> list(ShopCustomerVO limitKey) {
		
		if(limitKey != null && limitKey.getShopName() != null && !"".equals(limitKey.getShopName())){
			try {
				limitKey.setShopName(new String( limitKey.getShopName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(limitKey != null && limitKey.getUserName() != null && !"".equals(limitKey.getUserName())){
			try {
				limitKey.setUserName(new String( limitKey.getUserName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		DataModel<Map<String, Object>> dataModel = shopCustomerService.getList(limitKey);
		List<Map<String, Object>> data = dataModel.getRows();
		if(data != null && data.size() > 0){
			for(Map<String, Object> map : data){
				String userId = map.get("id").toString();
				String shopId = map.get("shopId").toString();
				//获取相册信息
				List<Map<String, Object>> albumList = shopAlbumService.queryListByShopIdAndUserId(shopId, userId);
				map.put("albumInfo", albumList);
				//获取动感影集信息
				List<Map<String, Object>> dynamicAlbumList = shopDynamicAlbumService.queryListByShopIdAndUserId(shopId, userId);
				map.put("dynamicAlbumInfo", dynamicAlbumList);
				long totalZoneSize = 0;
				long payAlbumZoneSize = 0;
				
				for(Map<String, Object> album : albumList){
					if(album.get("isPay") != null && "1".equals(album.get("isPay")+"") && !"1".equals(album.get("hadPaid")+"")){
						//付费 并且未支付
						//计费相册占用空间, 动感影集不需要付费， 只需要查询普通相册中需要付费而又没有被购买的相册信息，如果相册已经被购买则排除
						payAlbumZoneSize += album.get("albumSize") == null ? 0 : Long.parseLong(album.get("albumSize")+"");
					}
					totalZoneSize += album.get("albumSize") == null ? 0 : Long.parseLong(album.get("albumSize")+"");
				}
				map.put("totalZoneSize", totalZoneSize / (1024 * 1024  * 1.0));
				map.put("payAlbumZoneSize", payAlbumZoneSize / (1024 * 1024  * 1.0));
			}
		}
		return dataModel;
	}
	@RequestMapping("/customer/add")
	@ResponseBody
	public String add(ShopCustomerVO limitKey) {
		JSONObject modelMap = new JSONObject();  
		Map<String, Object> shopInfo = shopBaseinfoService.findById(limitKey.getShopId());
		String content = "恭喜您在["+shopInfo.get("shopName")+"]开户成功，您可以下载亲脉APP进入生活我的生活中查看赠送服务亲脉下载地址：http://qm.dbfish.net/d";
		//后台开户 调用开户
		//根据用户手机号查询用户ID
		Map<String, Object> userInfo = shopCustomerService.findAppUserByUserTel(limitKey.getUserTel());
		String appUserId = "";
		String password = 100000 +new Random().nextInt(899999) +"";
		if(userInfo == null){
			//该手机号没注册， 后台给注册一个
			boolean b = registAppUser(limitKey.getUserTel(), password);
			if(b){
				userInfo = shopCustomerService.findAppUserByUserTel(limitKey.getUserTel());
				content = "恭喜您在["+shopInfo.get("shopName")+"]开户成功，账号"+limitKey.getUserTel()+"，密码"+password+"，您可以下载亲脉APP进入生活我的生活中查看赠送服务亲脉下载地址：http://qm.dbfish.net/d";
			}else{
				modelMap.put("success", false);
				modelMap.put("message", "开户失败");
				return modelMap.toString();
			}
		}
		appUserId = userInfo.get("id").toString();
		limitKey.setId(GUID.generateID("SC"));
		limitKey.setUserId(appUserId);
		limitKey.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
		int i = shopCustomerService.save(limitKey);
		if(i > 0){
			sendMsg(limitKey.getUserTel(), content);
			modelMap.put("success", true);
			modelMap.put("message", "开户成功");
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "开户失败");
		}
		return modelMap.toString();
	}
	
	public static boolean sendMsg(String userTel, String content){
		
		JSONObject jo = new JSONObject();
		jo.put("serviceName", "aj_send_sms");
		jo.put("callType", "001");
		JSONObject param = new JSONObject();
		param.put("userTel",  userTel);
		param.put("content", content);
		jo.put("params", param);
		String returnStr = HttpClient.doJSONPostMethod(SystemConfig.getValue("qm.app.interface.url"), jo.toString());
		if(returnStr != null && !"".equals(returnStr)){
			
			JSONObject returnJson = JSONObject.fromObject(returnStr);
			String returnCode =  returnJson.optString("returnCode");
			if("000000".equals(returnCode)){
				//注册成功
				return true;
			}
		}
		return false;
	}
	
	private  boolean registAppUser(String userTel, String password) {
		//生成验证信息
		String id = GUID.generateID("SID_");
		String tokenId = GUID.generateID("STKN");
		
		int i = shopCustomerService.saveRegistRegInfo(id, tokenId, userTel);
		if(i > 0){
			
			JSONObject jo = new JSONObject();
			jo.put("serviceName", "aj_forget_pwd");
			jo.put("callType", "001");
			JSONObject param = new JSONObject();
			param.put("newPssword",  password);
			param.put("smsValidateToken", tokenId);
			param.put("ucode", "1111");
			jo.put("params", param);
			String returnStr = HttpClient.doJSONPostMethod(SystemConfig.getValue("qm.app.interface.url"), jo.toString());
			JSONObject returnJson = JSONObject.fromObject(returnStr);
			String returnCode =  returnJson.optString("returnCode");
			if("000000".equals(returnCode)){
				//注册成功
				return true;
			}
		}
		return false;
	}
	@RequestMapping("/customer/findByUserTel")
	@ResponseBody
	public String findByUserTel(String userTel, String shopId) {
		JSONObject modelMap = new JSONObject();  
		if(userTel != null  && !"".equals(userTel) && shopId != null  && !"".equals(shopId)){
			
			Map<String, Object> account = shopCustomerService.findAccountByUserTelAndShopId(userTel, shopId);
			if(account != null && !account.isEmpty()){
				modelMap.put("success", true);
				modelMap.put("message", account);
			}else{
				modelMap.put("success", false);
				modelMap.put("message", "初始化失败");
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "参数错误");
		}
		return modelMap.toString();
	}
	@RequestMapping("/customer/update")
	@ResponseBody
	public String update(ShopAccountVO limitKey) {
		int i = shopAccountService.update(limitKey);
		JSONObject modelMap = new JSONObject();  
		if(i > 0){
			modelMap.put("success", true);
			modelMap.put("message", "更新成功");
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "更新失败");
		}
		return modelMap.toString();
	}
	
	
	@RequestMapping("/customer/all")
	@ResponseBody
	public String all(String shopId, String userTel) {
		
		List<Map<String, Object>> list = shopCustomerService.getAll(shopId, userTel);
		JSONObject result = new JSONObject();
		result.put("message", "");
		result.put("value", list);
		return result.toString();
	}
	public static void main(String[] args) {
		System.out.println(2/100.0);
	}
}
