package com.qm.shop.category.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.GUID;
import com.frame.core.vo.DataModel;
import com.qm.shop.Constant;
import com.qm.shop.category.service.ShopCategoryService;
import com.qm.shop.category.vo.ShopCategoryVO;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopCategoryAction extends FtpImgDownUploadAction {
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/category/list")
	@ResponseBody
	public DataModel<Map<String, Object>> list(ShopCategoryVO limitKey) {
		
		/*if(limitKey != null && limitKey.getName() != null && !"".equals(limitKey.getName())){
			try {
				limitKey.setName(new String( limitKey.getName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}*/
		DataModel<Map<String, Object>> dataModel = shopCategoryService.getList(limitKey);
		return dataModel;
	}
	@RequestMapping("/category/add")
	@ResponseBody
	public String add(@RequestParam(value = "file") MultipartFile file,String name, String description, Integer status, Integer sort, Integer type, HttpServletResponse response) {
		String icon = "";
		JSONObject modelMap = new JSONObject();  
		//判断同类型分类名称是否存在
		Map<String, Object> cate = shopCategoryService.selectByNameAndType(name, type);
		if(cate != null && !cate.isEmpty()){
			modelMap.put("success", false);
			modelMap.put("message", "相同类型下已存在名称相同的分类");
			return modelMap.toString();
		}
		
		if(file != null && !file.isEmpty()){
			
			try {
				/*ResponseVo responseVo = (ResponseVo) fileUploadServiceImpl.fileUpload(new JSONObject(), (CommonsMultipartFile) file);
				if(ResponseVo.SUCCESS.equals(responseVo.getReturnCode())){
					FileUploadResultVo fileUploadResultVo = (FileUploadResultVo) responseVo.getResult();
					icon = fileUploadResultVo.getShortPicPath();
				}*/
				icon = fileUpload("shopcateicon", (CommonsMultipartFile)file);
			} catch (Exception e) {
				logger.info("新增商户类型时，上传图标文件出错："+e);
				modelMap.put("success", false);
				modelMap.put("message", "上传图片失败");
				return modelMap.toString();
			}
		}
		String id = GUID.generateID("SC");
		int i = shopCategoryService.addCategory(id, name, description, sort, icon, status, type);
		if(i > 0){
			modelMap.put("success", true);
			modelMap.put("message", "保存成功");
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "保存失败");
		}
		return modelMap.toString();
	}
	@RequestMapping("/category/modifyState")
	@ResponseBody
	public String modifyState(ShopCategoryVO limitKey) {
		JSONObject modelMap = new JSONObject();  
		if(limitKey != null && limitKey.getId() != null && !"".equals(limitKey.getId()) && limitKey.getStatus() != null){
			
			int i = shopCategoryService.updateCategoryStatus(limitKey);
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
	@RequestMapping("/category/find")
	@ResponseBody
	public String find(String id) {
		JSONObject modelMap = new JSONObject();  
		if(id != null  && !"".equals(id)){
			
			Map<String, Object> category = shopCategoryService.findCategoryById(id);
			if(category != null && !category.isEmpty()){
				if(category.get("icon") != null && !"".equals(category.get("icon"))){
					category.put("icon", Constant.imgPrefix + category.get("icon"));
				}
				modelMap.put("success", true);
				modelMap.put("message", category);
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
	@RequestMapping("/category/update")
	@ResponseBody
	public String update(@RequestParam(value = "file") MultipartFile file,String id, String name, String description, Integer status, Integer sort, Integer type, HttpServletResponse response) {
		String icon = "";
		JSONObject modelMap = new JSONObject();  
		//判断同类型分类名称是否存在
		Map<String, Object> cate = shopCategoryService.selectByNameAndType(name, type);
		if(cate != null && !cate.isEmpty() && !id.equals(cate.get("id"))){
			modelMap.put("success", false);
			modelMap.put("message", "相同类型下已存在名称相同的分类");
			return modelMap.toString();
		}
		
		if(file != null && !file.isEmpty()){
			
			try {
				/*ResponseVo responseVo = (ResponseVo) fileUploadServiceImpl.fileUpload(new JSONObject(), (CommonsMultipartFile) file);
				if(ResponseVo.SUCCESS.equals(responseVo.getReturnCode())){
					FileUploadResultVo fileUploadResultVo = (FileUploadResultVo) responseVo.getResult();
					icon = fileUploadResultVo.getShortPicPath();
				}*/
				icon = fileUpload("shopcateicon", (CommonsMultipartFile)file);
			} catch (Exception e) {
				logger.info("新增商户类型时，上传图标文件出错："+e);
				modelMap.put("success", false);
				modelMap.put("message", "上传图片失败");
				return modelMap.toString();
			}
		}
		int i = shopCategoryService.updateCategory(id, name, description, sort, icon, status, type);
		if(i > 0){
			modelMap.put("success", true);
			modelMap.put("message", "更新成功");
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "更新失败");
		}
		return modelMap.toString();
	}
	@RequestMapping("/category/all")
	@ResponseBody
	public String all(Integer type) {
		JSONObject modelMap = new JSONObject();  
		List<Map<String, Object>> list = shopCategoryService.selectAll(type);
		modelMap.put("success", true);
		modelMap.put("data", list);
		return modelMap.toString();
	}
	
}
