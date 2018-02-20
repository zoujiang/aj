package com.qm.shop.common.action;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.constant.FtpConstant;
import com.frame.core.util.DateUtil;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.SystemConfig;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenPhoto;
import com.qm.mapper.KindergartenPhotoMapper;

import net.sf.json.JSONObject;
/**
 * 幼儿园照片视频上传
 * */
@Controller
@RequestMapping("/fileUploadCommon")
@Scope("prototype")
public class BatchFileUploadAction extends FtpImgDownUploadAction{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private KindergartenPhotoMapper kindergartenPhotoMapper;
	
	@RequestMapping("/files/fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam(value = "file") MultipartFile file, Integer type, String ownerId, HttpServletRequest request) {
		
		UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);

		String DBPath;
		try {
			String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
			String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
			String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
			String port = (String) SystemConfig.getValue(FtpConstant.PORT);
			String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
			String module = (String) SystemConfig.getValue(FtpConstant.FTP_FILE_TEM_PATH);
			String fileName = file.getOriginalFilename();// 获取文件名
			String imageSuffix=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			if(StringUtils.isNotEmpty(fileName) && "".equals(imageSuffix)){
				 log.warn("fileUpload----service:"+"上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
				throw new FileUploadException("上传文件格式不正确文件名："+fileName);
			}
			long size = file.getSize();
			if (size == 0) {
				throw new FileUploadException("上传文件为空文件");
			}	
			
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			DBPath = "";
			
			FtpUtil ftp = null;
			module = "kindergarten";
			if (StringUtils.isNotEmpty(module)) {
				if (StringUtils.isNotEmpty(fileName)) {
					String unique = String.valueOf(System.currentTimeMillis()) + new Random().nextInt(10);
					fileName = unique+"." + imageSuffix;
					DBPath = "/" + module+"/"+fileName; //    不能这个路径/upload/wod
					ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
					boolean flag = false;
					try {
						ftp.login();
						log.info("fileUpload("+module+","+file+","+SystemConfig.getValue("pic.needSmall.module.filetype")+") -  start");
						flag = ftp.upload(file.getInputStream(), path + DBPath);
						int category = 1;
						//判断文件是否为视频
						if(isVedioFile(fileName)){
							category = 2;
						}
						KindergartenPhoto photo = new KindergartenPhoto();
						photo.setCategory(category);
						photo.setCommentNum(0);
						photo.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
						photo.setCreateUser(userExtForm.getAccount());
						photo.setDigNum(0);
						photo.setOwnerId(ownerId);
						photo.setType(type);
						if(category == 1){
							photo.setPhotoUrl(DBPath);
						}else{
							photo.setVideoUrl(DBPath);
						}
						kindergartenPhotoMapper.insertSelective(photo);
						
						
						
					} catch (Exception e) {
						log.error(e.getMessage());
						e.printStackTrace();
						throw new FileUploadException("FTP上传文件出错");
					} finally{
						ftp.closeServer();
					}
					if (!flag) {
						log.error("FTP文件上传失败");
						throw new IOException("FTP文件上传失败");
					}
				} else {
					// 当文件上传的文本框为空时，对应返回的路径为空。
					DBPath = "";
				}
			}
			log.debug("fileUpload("+module+","+file+") - end");
			JSONObject result = new JSONObject();
			result.put("success", true);
			result.put("url", DBPath);
			result.put("type", type);
			return result.toString();
		} catch (Exception e) {
		
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("url", "");
			return result.toString();
		}
	}
	
	private final static String PREFIX_VIDEO="video/";

    /**
     * Get the Mime Type from a File
     * @param fileName 文件名
     * @return 返回MIME类型
     * thx https://www.oschina.net/question/571282_223549
     * add by fengwenhua 2017年5月3日09:55:01
     */
    private static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileName);
        return type;
    }

    /**
     * 根据文件后缀名判断 文件是否是视频文件
     * @param fileName 文件名
     * @return 是否是视频文件
     */
    public static boolean isVedioFile(String fileName){
        String mimeType = getMimeType(fileName);
        if (!TextUtils.isEmpty(fileName) && mimeType != null &&mimeType.contains(PREFIX_VIDEO)){
            return true;
        }
        return false;
    }

	
}
