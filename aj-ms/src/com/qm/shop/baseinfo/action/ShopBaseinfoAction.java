package com.qm.shop.baseinfo.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.DateUtil;
import com.frame.core.util.ExportExcelUtils;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.GUID;
import com.frame.core.vo.DataModel;
import com.frame.ifpr.service.FileUploadService;
import com.qm.shop.Constant;
import com.qm.shop.baseinfo.service.ShopBaseinfoService;
import com.qm.shop.baseinfo.vo.ShopInfoVO;
import com.qm.shop.baseinfo.vo.TShopInfo;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopBaseinfoAction extends FtpImgDownUploadAction {
	@Autowired
	private ShopBaseinfoService shopBaseinfoService;
	@Autowired
	private FileUploadService fileUploadServiceImpl;
	
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	
	@RequestMapping("/baseinfo/list")
	@ResponseBody
	public DataModel<Map<String, Object>> list(ShopInfoVO limitKey) {
		
		if(limitKey != null && limitKey.getShopName() != null && !"".equals(limitKey.getShopName())){
			try {
				limitKey.setShopName(new String( limitKey.getShopName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		DataModel<Map<String, Object>> dataModel = shopBaseinfoService.getList(limitKey);
		return dataModel;
	}
	@RequestMapping("/baseinfo/all")
	@ResponseBody
	public String all() {
		
		List<Map<String, Object>> list = shopBaseinfoService.getAll();
		JSONObject result = new JSONObject();
		result.put("data", list);
		return JSON.toJSONString(result);
	}
	@RequestMapping("/baseinfo/add")
	@ResponseBody
	public String add(String registName, String registTel, String shopCategoryId, String gps, String bankCardName, String percentage,
						String bankId, String cardNo, String idCard, String tel, String serviceBeginTime, String serviceEndTime, String sort, String zoneSize,
						@RequestParam(value = "showPics") MultipartFile[] showPics, @RequestParam(value = "logo") MultipartFile logo,
						String description, String shopName, String brandId, Integer isRecommend, String address ) {
		
		String icon = "";
		JSONObject modelMap = new JSONObject();  
		if(logo != null && !logo.isEmpty()){
			
			try {
				icon = fileUpload("shoplogo", (CommonsMultipartFile)logo);
			} catch (Exception e) {
				logger.info("新增商户时，上传LOGO文件出错："+e);
				modelMap.put("success", false);
				modelMap.put("message", "上传图片失败");
				return modelMap.toString();
			}
		}
		String showPicsUrl = "";
		if(showPics != null && showPics.length > 0){
			
			try {
				for(MultipartFile pic : showPics){
					if(!pic.isEmpty()){

						String url = fileUpload("shoplogo", (CommonsMultipartFile)pic);
						showPicsUrl += url +",";
					}
				}
			} catch (Exception e) {
				logger.info("新增商户时，上传展示图片文件出错："+e);
				modelMap.put("success", false);
				modelMap.put("message", "上传图片失败");
				return modelMap.toString();
			}
		}
		TShopInfo info = new TShopInfo();
		info.setId(GUID.generateID("SP"));
		info.setBankId(bankId);
		info.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
		info.setDescription(description);
		info.setGps(gps);
		info.setIdCard(idCard);
		info.setBankCardNo(cardNo);
		info.setLogo(icon);
		info.setPercentage((percentage == null ||"".equals(percentage)) ? 0 : Float.parseFloat(percentage));
		info.setServiceBeginTime(serviceBeginTime);
		info.setServiceEndTime(serviceEndTime);
		info.setShopCategoryId(shopCategoryId);
		info.setShopName(shopName);
		info.setShowPic(showPicsUrl);
		info.setSort((sort== null || "".equals(sort)) ? 99 : Integer.parseInt(sort));
		info.setTel(tel);
		info.setZoneSize((zoneSize == null || "".equals(zoneSize)) ? 10 : Integer.parseInt(zoneSize));
		info.setIsRecommend(isRecommend);
		info.setBrandId(brandId);
		info.setAddress(address);
		info.setRegistName(registName);
		info.setRegistTel(registTel);
		info.setStatus(0);
		info.setBankCardName(bankCardName);
		JSONObject result = new JSONObject();
		
		try {
			int i = shopBaseinfoService.save(info);
			if(i > 0){
				result.put("success", true);
				result.put("message", "保存成功");
			}else{
				result.put("success", false);
				result.put("message", "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "保存失败");
		}
		
		return JSON.toJSONString(result);
	}
	@RequestMapping("/baseinfo/modifyState")
	@ResponseBody
	public String modifyState(String id, String status ) {
		
		
		JSONObject result = new JSONObject();
		try {
			int i = shopBaseinfoService.modifyState(id, status);
			if(i > 0){
				result .put("success", true);
				result.put("message", "更新成功");
			}else{
				result.put("success", false);
				result.put("message", "更新失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "更新失败");
		}
		
		return JSON.toJSONString(result);
	}
	@RequestMapping("/baseinfo/find")
	@ResponseBody
	public String find(HttpServletRequest request) {
		
		String id = (String)request.getSession().getAttribute(com.frame.core.constant.Constant.Login_USER_SHOP_ID);
		JSONObject result = new JSONObject();
		try {
			Map<String ,Object> map = shopBaseinfoService.findById(id);
			if(map != null){
				
				String shopLogo = map.get("shopLogo") == null ? "" : map.get("shopLogo").toString();
				if(!"".equals(shopLogo)){
					map.put("shopLogo", Constant.imgPrefix + shopLogo);
				}
				String showPic = map.get("showPic") == null ? "" : map.get("showPic").toString();
				if(!"".equals(showPic)){
					String[] urls = showPic.split(",");
					String fullPath = "";
					for(String url : urls){
						fullPath += Constant.imgPrefix + url +",";
					}
					if(fullPath.length() > 0){
						fullPath = fullPath.substring(0, fullPath.length()-1);
					}
					map.put("showPic",  fullPath);
				}
			}
			result .put("success", true);
			result.put("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "初始化失败");
		}
		
		return  result.toString();
	}
	
	@RequestMapping("/baseinfo/update")
	@ResponseBody
	public String update(String id,String registName, String registTel, String shopCategoryId, String gps, String bankCardName, String percentage,
						String bankId, String cardNo, String idCard, String tel, String serviceBeginTime, String serviceEndTime, String sort, String zoneSize,
						@RequestParam(value = "showPics") MultipartFile[] showPics, @RequestParam(value = "logo") MultipartFile logo,
						String description, String shopName, String brandId, Integer isRecommend, String address, String oldLogo, String oldShowPics ) {
		
		String icon = "";
		JSONObject modelMap = new JSONObject();  
		if(logo != null && !logo.isEmpty()){
			
			try {
				icon = fileUpload("shoplogo", (CommonsMultipartFile)logo);
			} catch (Exception e) {
				logger.info("新增商户时，上传LOGO文件出错："+e);
				modelMap.put("success", false);
				modelMap.put("message", "上传图片失败");
				return modelMap.toString();
			}
		}else if(oldLogo != null && !"".equals(oldLogo)){
			icon = oldLogo.replaceAll(Constant.clearImgPrefix, "");
		}
		String showPicsUrl = "";
		if(showPics != null && showPics.length > 0){
			
			try {
				for(MultipartFile pic : showPics){
					if(!pic.isEmpty()){
						String url = fileUpload("shoplogo", (CommonsMultipartFile)pic);
						showPicsUrl += url +",";
					}
				}
			} catch (Exception e) {
				logger.info("新增商户时，上传展示图片文件出错："+e);
				modelMap.put("success", false);
				modelMap.put("message", "上传图片失败");
				return modelMap.toString();
			}
		}
		if(oldShowPics != null && !"".equals(oldShowPics)){
			showPicsUrl += oldShowPics.replaceAll(Constant.clearImgPrefix, "");
		}
		
		TShopInfo info = new TShopInfo();
		info.setId(id);
		info.setBankId(bankId);
		info.setDescription(description);
		info.setGps(gps);
		info.setIdCard(idCard);
		info.setBankCardNo(cardNo);
		info.setLogo(icon);
		info.setPercentage( (percentage == null || "".equals(percentage)) ? 0 : Float.parseFloat(percentage));
		info.setServiceBeginTime(serviceBeginTime);
		info.setServiceEndTime(serviceEndTime);
		info.setShopCategoryId(shopCategoryId);
		info.setShopName(shopName);
		info.setShowPic(showPicsUrl);
		info.setSort( (sort == null || "".equals(sort)) ? 99 : Integer.parseInt(sort));
		info.setTel(tel);
		info.setZoneSize((zoneSize == null || "".equals(zoneSize)) ? 10 : Integer.parseInt(zoneSize));
		info.setIsRecommend(isRecommend);
		info.setBrandId(brandId);
		info.setAddress(address);
		info.setRegistName(registName);
		info.setRegistTel(registTel);
		info.setBankCardName(bankCardName);
		JSONObject result = new JSONObject();
		
		try {
			int i = shopBaseinfoService.update(info);
			if(i > 0){
				result.put("success", true);
				result.put("message", "更新成功");
			}else{
				result.put("success", false);
				result.put("message", "更新失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "更新失败");
		}
		
		return JSON.toJSONString(result);
	}
	@RequestMapping("/baseinfo/export")
	@ResponseBody
	public String export(String shopName, HttpServletRequest request,HttpServletResponse response) {
		
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName =  format.format(date) + ".xls";

	
		/** 获得输出流 **/
		ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
		JSONObject result = new JSONObject();
		try {
			String headerColumn = "[{\"shopName\":\"店铺名称\"},"
								+ "{\"shopAccountName\":\"商户帐号\"},"
								+ "{\"registName\":\"注册人姓名\"},"
								+ "{\"registTel\":\"联系电话\"},"
								+ "{\"idCard\":\"注册人身份证\"},"
								+ "{\"bankName\":\"商户银行\"},"
								+ "{\"bankCardName\":\"结算账号名称\"},"
								+ "{\"bankCardNo\":\"分成账号\"},"
								+ "{\"percentage\":\"分成比例\"},"
								+ "{\"zoneSize\":\"存储总量（G）\"},"
								+ "{\"usedSize\":\"已用存储(G)\"},"
								+ "{\"photoCount\":\"照片张数\"},"
								+ "{\"createTime\":\"注册时间\"},"
								+ "{\"serviceBeginTime\":\"服务开始时间\"},"
								+ "{\"serviceEndTime\":\"服务结束时间\"},"
								+ "{\"status\":\"商户状态\"}]";
			
			
			List<Map<String, Object>> dataList = shopBaseinfoService.getAllDataForExport(shopName);
			
			FtpUtil ftp = new FtpUtil(Constant.ftpAddress, Constant.port, Constant.username, Constant.password);
			try {
				ftp.login();
				for(Map<String, Object> data : dataList){
					String filePath = Constant.path + "/" +data.get("id") ;
					
					long size = ftp.getFileSize(filePath);
					data.put("usedSize", size / (1024 * 1024 * 1024));
					int count = ftp.getFileCount(filePath);
					data.put("photoCount", count);
				}
			} catch (Exception e1) {
				logger.info("登录FTP服务器失败！");
				e1.printStackTrace();
			}finally{
				ftp.closeServer();
			}
			
			HSSFWorkbook workbook = new ExportExcelUtils().exportExcel2(dataList, headerColumn, fileName);
			workbook.write(byteOutPut);
			
			String url = getRealGePath();
			OutputStream out=new FileOutputStream(url + fileName);//文件本地存储地址
			workbook.write(out);
			out.close();
			File tempFile = new File(url + fileName);
			byteOutPut.close();
			GZIPOutputStream gizout = new GZIPOutputStream(new FileOutputStream(url + fileName+".gz"));
			byte[] buff = new byte[1024]; //设定读入缓冲区尺寸   
			FileInputStream in = new FileInputStream(tempFile); //把生成的csv文件
			
			int len;
			while ((len = in.read(buff)) != -1) {
				gizout.write(buff,0,len);
			}
			in.close();
			gizout.finish();
			gizout.close();
			tempFile.delete();//删除临时文件
			
			result.put("success", true);
			result.put("url", "/temp/"+ fileName+".gz");
		} catch (IOException e) {
			result.put("success", false);
			e.printStackTrace();
		} catch (Exception e) {
			result.put("success", false);
			e.printStackTrace();
		}
		return result.toString();
	}
	public String getRealGePath() {
		///D:/tomcat/apache-tomcat-6.0.35/webapps/ams/WEB-INF/classes/
		String url = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		url = url.replace("WEB-INF/classes", "temp");
		return url;
	}
}
