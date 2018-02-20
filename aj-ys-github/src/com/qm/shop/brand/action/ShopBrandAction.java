package com.qm.shop.brand.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.qm.shop.brand.service.ShopBrandService;
import com.qm.shop.brand.vo.ShopBrandVO;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopBrandAction extends FtpImgDownUploadAction {
	@Autowired
	private ShopBrandService shopBrandService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/brand/list")
	@ResponseBody
	public DataModel<Map<String, Object>> list(ShopBrandVO limitKey) {
		
	/*	if(limitKey != null && limitKey.getBrandName() != null && !"".equals(limitKey.getBrandName())){
			try {
				limitKey.setBrandName(new String( limitKey.getBrandName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}*/
		DataModel<Map<String, Object>> dataModel = shopBrandService.getList(limitKey);
		return dataModel;
	}
	@RequestMapping("/brand/add")
	@ResponseBody
	public String add(@RequestParam(value = "file") MultipartFile file,String brandName, Integer status, Integer sortIndex, Integer type, Integer isRecommend, HttpServletResponse response) {
		String icon = "";
		JSONObject modelMap = new JSONObject();  
		//判断是否有相同名称的品牌存在
		List<Map<String, Object>> brandList = shopBrandService.findByBrandName(brandName, type);
		if(brandList != null && brandList.size() > 0){
			modelMap.put("success", false);
			modelMap.put("message", "已存在相同名称的品牌");
			return modelMap.toString();
		}
		if(file != null && !file.isEmpty()){
			
			try {
				icon = fileUpload("shopBrandIcon", (CommonsMultipartFile)file);
			} catch (Exception e) {
				logger.info("新增品牌时，上传图标文件出错："+e);
				modelMap.put("success", false);
				modelMap.put("message", "上传图片失败");
				return modelMap.toString();
			}
		}
		String id = GUID.generateID("SC");
		int i = shopBrandService.addBrand(id, brandName, sortIndex, icon, status, type, isRecommend);
		if(i > 0){
			modelMap.put("success", true);
			modelMap.put("message", "保存成功");
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "保存失败");
		}
		System.out.println(modelMap.toString());
		return modelMap.toString();
	}
	@RequestMapping("/brand/modifyState")
	@ResponseBody
	public String modifyState(ShopBrandVO limitKey) {
		JSONObject modelMap = new JSONObject();  
		if(limitKey != null && limitKey.getId() != null && !"".equals(limitKey.getId()) && limitKey.getStatus() != null){
			
			int i = shopBrandService.updateBrandStatus(limitKey);
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
	@RequestMapping("/brand/find")
	@ResponseBody
	public String find(String id) {
		JSONObject modelMap = new JSONObject();  
		if(id != null  && !"".equals(id)){
			
			Map<String, Object> category = shopBrandService.findBrandById(id);
			if(category != null && !category.isEmpty()){
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
	@RequestMapping("/brand/update")
	@ResponseBody
	public String update(@RequestParam(value = "file") MultipartFile file,String id, String brandName,  Integer status, Integer sortIndex, Integer type, Integer isRecommend, HttpServletResponse response) {
		String icon = "";
		JSONObject modelMap = new JSONObject();  
		if(file != null && !file.isEmpty()){
			
			try {
				icon = fileUpload("shopBrandIcon", (CommonsMultipartFile)file);
			} catch (Exception e) {
				logger.info("新增商户品牌时，上传图标文件出错："+e);
				modelMap.put("success", false);
				modelMap.put("message", "上传图片失败");
				return modelMap.toString();
			}
		}
		int i = shopBrandService.updateBrand(id, brandName, sortIndex, icon, status, type, isRecommend);
		if(i > 0){
			modelMap.put("success", true);
			modelMap.put("message", "更新成功");
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "更新失败");
		}
		return modelMap.toString();
	}
	@RequestMapping("/brand/all")
	@ResponseBody
	public String all(Integer type) {
		JSONObject modelMap = new JSONObject();  
		List<Map<String, Object>> list = shopBrandService.selectAll(type);
		modelMap.put("success", true);
		modelMap.put("data", list);
		return modelMap.toString();
	}
	
}
