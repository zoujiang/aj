package com.qm.shop.feedback.action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.constant.Constant;
import com.frame.core.util.DateUtil;
import com.frame.core.util.GUID;
import com.frame.core.vo.DataModel;
import com.jhlabs.image.FeedbackFilter;
import com.qm.shop.feedback.service.ShopFeedbackService;
import com.qm.shop.feedback.vo.ShopFeedbackVO;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopFeedbackAction extends FtpImgDownUploadAction {
	@Autowired
	private ShopFeedbackService shopFeedbackService;
	
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/feedback/list")
	@ResponseBody
	public DataModel<Map<String, Object>> list(ShopFeedbackVO limitKey, HttpServletRequest request) {
		String shopId = (String)request.getSession().getAttribute(Constant.Login_USER_SHOP_ID);
		limitKey.setShopId(shopId);
		if(limitKey != null && limitKey.getShopName() != null && !"".equals(limitKey.getShopName())){
			try {
				limitKey.setShopName(new String( limitKey.getShopName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		DataModel<Map<String, Object>> dataModel = shopFeedbackService.getList(limitKey);
		return dataModel;
	}
	@RequestMapping("/feedback/find")
	@ResponseBody
	public String find(String id) {
		JSONObject modelMap = new JSONObject();  
		if(id != null && !"".equals(id)){
			
			Map<String, Object> fb  = shopFeedbackService.findById(id);
			if(fb != null && !fb.isEmpty()){
				modelMap.put("success", true);
				modelMap.put("message", fb);
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
	@RequestMapping("/feedback/cancel")
	@ResponseBody
	public String cancel(String id) {
		JSONObject modelMap = new JSONObject();  
		if(id != null && !"".equals(id)){
			
			int i = shopFeedbackService.updateNotShow(id);
			if(i > 0){
				modelMap.put("success", true);
				modelMap.put("message", "取消成功");
			}else{
				modelMap.put("success", false);
				modelMap.put("message", "取消失败");
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "参数错误");
		}
		return modelMap.toString();
	}
	@RequestMapping("/feedback/add")
	@ResponseBody
	public String add(String fbCategory, String fbContent, HttpServletRequest request) {
		JSONObject modelMap = new JSONObject();  
		if(fbCategory != null && !"".equals(fbCategory) && fbContent != null && !"".equals(fbContent)){
			String userid = (String)request.getSession().getAttribute(Constant.Login);
			String shopId = (String)request.getSession().getAttribute(Constant.Login_USER_SHOP_ID);
			int i = shopFeedbackService.add(GUID.generateID("FB"), fbCategory, fbContent,DateUtil.dateFromatYYYYMMddHHmmss(new Date()), shopId,userid );
			if(i > 0){
				modelMap.put("success", true);
				modelMap.put("message", "反馈成功");
			}else{
				modelMap.put("success", false);
				modelMap.put("message", "反馈失败");
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "参数错误");
		}
		return modelMap.toString();
	}
	@RequestMapping("/feedback/update")
	@ResponseBody
	public String update(String id,String fbCategory, String fbContent, HttpServletRequest request) {
		JSONObject modelMap = new JSONObject();  
		if(fbCategory != null && !"".equals(fbCategory) && fbContent != null && !"".equals(fbContent)){
			
			int i = shopFeedbackService.update(id, fbCategory, fbContent);
			if(i > 0){
				modelMap.put("success", true);
				modelMap.put("message", "编辑反馈成功");
			}else{
				modelMap.put("success", false);
				modelMap.put("message", "编辑反馈失败");
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "参数错误");
		}
		return modelMap.toString();
	}
}
