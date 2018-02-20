package com.qm.shop.comment.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
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
import com.frame.core.vo.DataModel;
import com.qm.shop.Constant;
import com.qm.shop.comment.service.ShopCommentService;
import com.qm.shop.comment.vo.ShopCommentVO;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopCommentAction extends FtpImgDownUploadAction {
	@Autowired
	private ShopCommentService shopCommentService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/cmt/list")
	@ResponseBody
	public DataModel<Map<String, Object>> list(ShopCommentVO limitKey,HttpServletRequest request) {
		String shopId = (String)request.getSession().getAttribute(com.frame.core.constant.Constant.Login_USER_SHOP_ID);
		limitKey.setShopId(shopId);
		if(limitKey != null && limitKey.getShopName() != null && !"".equals(limitKey.getShopName())){
			try {
				limitKey.setShopName(new String( limitKey.getShopName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(limitKey != null && limitKey.getCmtUserName() != null && !"".equals(limitKey.getCmtUserName())){
			try {
				limitKey.setCmtUserName(new String( limitKey.getCmtUserName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		DataModel<Map<String, Object>> dataModel = shopCommentService.getList(limitKey);
		List<Map<String, Object>> data = dataModel.getRows();
		if(data != null && data.size() > 0){
			for(Map<String, Object> map : data){
				if(map.get("cmtImg") != null && !"".equals(map.get("cmtImg").toString())){
					String[] imgs = map.get("cmtImg").toString().split(",");
					String full = "";
					for(String img : imgs){
						full += Constant.imgPrefix + img +",";
					}
					map.put("cmtImg", full.substring(0, full.length() -1));
				}
			}
		}
		return dataModel;
	}
	@RequestMapping("/cmt/modifyState")
	@ResponseBody
	public String modifyState(String id, String status) {
		JSONObject modelMap = new JSONObject();  
		if(id != null && !"".equals(id) && status != null && !"".equals(status)){
			
			int i = shopCommentService.updateCommentStatus(id, status);
			if(i > 0){
				modelMap.put("success", true);
				modelMap.put("message", "更新成功");
			}else{
				modelMap.put("success", false);
				modelMap.put("message", "更新失败");
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "参数错误");
		}
		return modelMap.toString();
	}
}
