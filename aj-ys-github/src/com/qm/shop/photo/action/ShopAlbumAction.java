package com.qm.shop.photo.action;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.constant.FtpConstant;
import com.frame.core.util.DateUtil;
import com.frame.core.util.FileUtil;
import com.frame.core.util.GUID;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.DataModel;
import com.qm.shop.album.service.ShopAlbumService;
import com.qm.shop.album.vo.ShopAlbumVO;

import net.sf.json.JSONObject;


//@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class ShopAlbumAction extends FtpImgDownUploadAction {
	@Autowired
	private ShopAlbumService shopAlbumService;
	
	private String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
	private String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
	private String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
	private String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
	private String tempPath = (String) SystemConfig.getValue(FtpConstant.FTP_FILE_TEM_PATH);
	private int port =  SystemConfig.getValue(FtpConstant.PORT) == null ? 21 : Integer.parseInt(SystemConfig.getValue(FtpConstant.PORT).toString());
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/photo/list")
	@ResponseBody
	public DataModel<Map<String, Object>> list(ShopAlbumVO limitKey) {
		/*
		if(limitKey != null && limitKey.getShopName() != null && !"".equals(limitKey.getShopName())){
			try {
				limitKey.setShopName(new String( limitKey.getShopName().getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} */
		DataModel<Map<String, Object>> dataModel = shopAlbumService.getList(limitKey);
		return dataModel;
	}
	@RequestMapping("/photo/add")
	@ResponseBody
	public String add(@RequestParam(value = "albumLogo") MultipartFile file, 
							String shopId, String userId, String albumName, String albumType, String origPrice, 
							String payType_3, String payType_6, String payType_9, String payType_12, String payType_24, String payType_25,
							String photoUrls) {
		String icon = "";
		JSONObject modelMap = new JSONObject();  
		if(file != null && !file.isEmpty()){
			if(file != null && !file.isEmpty()){
				
				try {
					icon = fileUpload("shopalbum"+shopId, (CommonsMultipartFile)file);
				} catch (Exception e) {
					logger.info("新增商户相册时，上传LOGO文件出错："+e);
				}
			}
		}
		ShopAlbumVO album = new ShopAlbumVO();
		String id = GUID.generateID("SC");
		album.setId(id);
		album.setAlbumLogo(icon);
		album.setAlbumName(albumName);
		album.setAlbumType(albumType);
		album.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
		album.setOrigPrice(origPrice);
		album.setPayType_3(payType_3);
		album.setPayType_6(payType_6);
		album.setPayType_9(payType_9);
		album.setPayType_12(payType_12);
		album.setPayType_24(payType_24);
		album.setPayType_25(payType_25);
		album.setUserId(userId);
		album.setShopId(shopId);
		if(photoUrls != null && !"".equals(photoUrls)){
	//		FtpUtil ftp = new FtpUtil(ftpAddress, port, username, password);
			String[] urls = photoUrls.split(",");
			StringBuffer newUrls = new StringBuffer();
			for(String url : urls){
				String resourceUrl = path + url;
				String targetUrl = path +"/"+ shopId +"/"+ userId + url.replace(tempPath+"/", "");
				newUrls.append("/"+ shopId +"/"+ userId + url.replace(tempPath+"/", ""));
				newUrls.append(",");
				try {
	//				ftp.login();
		//			ftp.move(targetUrl , resourceUrl);
					FileUtil.move(targetUrl, resourceUrl);
				} catch (Exception e) {
					logger.info("移动文件失败。。。源地址："+resourceUrl +"---目标地址："+targetUrl);
				}
				newUrls.append("/"+ shopId +"/"+ userId + url.replace(tempPath, ""));
				newUrls.append(",");
			}
			//TODO 保存照片
		}
		int i = shopAlbumService.save(album);
		if(i > 0){
			modelMap.put("success", true);
			modelMap.put("message", "保存成功");
		}else{
			modelMap.put("success", false);
			modelMap.put("message", "保存失败");
		}
		return modelMap.toString();
	}
	
}
