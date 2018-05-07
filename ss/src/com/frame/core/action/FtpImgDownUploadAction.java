package com.frame.core.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.constant.FtpConstant;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.FileUtil;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.GUID;
import com.frame.core.util.HttpClient;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.MessageModel;
import com.frame.ifpr.vo.ThumbnailsVo;

import net.coobird.thumbnailator.Thumbnails;

/**
 * FTP文件（单文件和多文件）上传,下载 的Action基类
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0  
 */
public class FtpImgDownUploadAction extends FileDownUploadAction {

	private Log log=LogFactory.getLog(FtpImgDownUploadAction.class);

	@Autowired
	private GenericDAO baseDAO; 
	
	
	@Override
	public String fileUpload(String module, CommonsMultipartFile file)
			throws ParseException, IOException, FileUploadException {
		log.debug("fileUpload("+module+","+file+") - start");
		String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
		String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
		String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
		String port = (String) SystemConfig.getValue(FtpConstant.PORT);
		String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
		String fileName = file.getFileItem().getName();// 获取文件名
	    String imageSuffix=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
	    if(StringUtils.isNotEmpty(fileName) && "".equals(imageSuffix)){
			 log.warn("fileUpload----service:"+"上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
			throw new FileUploadException("上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
		}
		
		
		long size = file.getSize();
		if (size == 0) {
			throw new FileUploadException("上传文件为空文件");
		}	
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		String DBPath = "";// 数据库路径
		String sDBPath = "";// 数据库路径
	//	FtpUtil ftp = null;
		if (StringUtils.isNotEmpty(module)) {
			if (StringUtils.isNotEmpty(fileName)) {
				String unique = String.valueOf(System.currentTimeMillis());
				//modify by xuec 上传文件名修改 begin
				fileName = unique+"." + imageSuffix;
				//modify by xuec 上传文件名修改 end
				DBPath = "/" + module+"/"+fileName; //    不能这个路径/upload/wod
				sDBPath = "/" + module+"/s"+fileName;
	//			ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
				boolean flag = false;
				boolean sflag = false;
				try {
		//			ftp.login();
					log.info("fileUpload("+module+","+file+") -  start");
		//			flag = ftp.upload(file.getInputStream(), path + DBPath);
					flag = FileUtil.writeToLocal( path + DBPath, file.getInputStream());
					if(SystemConfig.getValue("pic.needSmall.module").contains(module) && 
							SystemConfig.getValue("pic.needSmall.module.filetype").indexOf(imageSuffix.toLowerCase()) > 0){
						log.info("fileUpload("+module+","+file+") - small start");
						ThumbnailsVo thumbnailsVo = new ThumbnailsVo();
						String[] sizeConfig = SystemConfig.getValue("pic." + module + ".size").split("\\*");
						thumbnailsVo.setSmallWidth(Integer.parseInt(sizeConfig[0]));
						thumbnailsVo.setSmallHeigth(Integer.parseInt(sizeConfig[1]));
						thumbnailsVo.setFileType(imageSuffix);
						BufferedImage bufferedImage=Thumbnails.of(file.getInputStream()).size(thumbnailsVo.getSmallWidth(),thumbnailsVo.getSmallHeigth()).outputFormat(imageSuffix).keepAspectRatio(false).outputQuality(1.0f).asBufferedImage();
						InputStream inputsamall=getImageStream(bufferedImage,imageSuffix);
				//		sflag = ftp.upload(inputsamall, path + sDBPath);
						sflag = FileUtil.writeToLocal( path + sDBPath, inputsamall);
						log.info("fileUpload("+module+","+file+") - small end");
						return sDBPath;
					}
					
					//flag = ftp.upload(file.getInputStream(),DBPath);
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
					throw new FileUploadException("FTP上传文件出错");
				} finally{
				//	ftp.closeServer();
				}
				if (!flag) {
					log.error("FTP文件上传失败");
					throw new IOException("FTP文件上传失败");
				}
				if(SystemConfig.getValue("pic.needSmall.module").contains(module) && 
						SystemConfig.getValue("pic.needSmall.module.filetype").indexOf(imageSuffix.toLowerCase()) > 0){
					if (!sflag) {
						log.error("FTP生成缩略图文件上传失败");
						throw new IOException("FTP生成缩略图文件上传失败");
					}
				}
				
			} else {
				// 当文件上传的文本框为空时，对应返回的路径为空。
				DBPath = "";
			}
		}
		log.debug("fileUpload("+module+","+file+") - end");
		return DBPath;
	}

	private InputStream getImageStream(BufferedImage bufferedImage,String type){
		log.debug("getImageStream"+type+"---------------start");
		InputStream is = null;  
		 ByteArrayOutputStream bs = new ByteArrayOutputStream(); 
		 ImageOutputStream imOut; 
		 try {  
			 imOut = ImageIO.createImageOutputStream(bs);  
			 ImageIO.write(bufferedImage, type,imOut);
			 is= new ByteArrayInputStream(bs.toByteArray()); 
			 } catch (IOException e) {  
				 e.printStackTrace();  
				 } 
			 log.debug("getImageStream"+type+"---------------end");
	   return is;  
	}
	@Override
	public MessageModel download(HttpServletResponse response, String fileName,
			String moduleName) {
		log.debug("download("+fileName+","+moduleName+") - start");
		FtpUtil ftp = null;
		try {
			/** 设置HTML头部信息 **/
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
			response.setContentType("application/octet-stream;charset=UTF-8");
			String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
			String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
			String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
			String port = (String) SystemConfig.getValue(FtpConstant.PORT);
			String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
			ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
			OutputStream os=response.getOutputStream();
			ftp.login();
			log.debug("链接FTP,地址:" + ftpAddress + "用户名：" + username + "密码：" + username + "端口：" + username + "地址：" + path);
			String remotePath=path + "/" + moduleName+"/"+fileName;
			FTPFile file = ftp.getFile(remotePath);
			log.debug("ftp.getFile-----:" + remotePath);
			response.addHeader("Content-Length", "" + file.getSize());
			ftp.download(os, remotePath);
			log.debug("ftp.download------:" + remotePath);
			os.close();
			log.debug("流关闭");
			return new MessageModel(true, "文件下载成功");
		}catch (Exception e) {
			log.error(e.getMessage());
			return new MessageModel(false, "文件下载异常。");
		}finally{
			ftp.closeServer();
		}
	}
	
	public  boolean registAppUser(String userTel, String password, String userType) {
		//生成验证信息
		String id = GUID.generateID("SID_");
		String tokenId = GUID.generateID("STKN");
		
		String sql = "INSERT INTO t_custom_reg (id, SMS_TOKEN_ID, USER_TEL, SMS_CODE, CREATE_DT) VALUES (?,?,?,?,?);";
		int i = baseDAO.execteNativeBulk(sql, id, tokenId, userTel, "123456", new Date());
		
		if(i > 0){
			
			JSONObject jo = new JSONObject();
			jo.put("serviceName", "aj_forget_pwd");
			jo.put("callType", "001");
			JSONObject param = new JSONObject();
			param.put("newPssword",  password);
			param.put("smsValidateToken", tokenId);
			param.put("ucode", "1111");
			param.put("userType", userType);
			jo.put("params", param);
			try {
				String returnStr = HttpClient.doJSONPostMethod(SystemConfig.getValue("qm.app.interface.url"), jo.toString());
				JSONObject returnJson = JSONObject.parseObject(returnStr);
				String returnCode =  returnJson.getString("returnCode");
				if("000000".equals(returnCode)){
					//注册成功
					return true;
				}
			} catch (Exception e) {
				log.info("调用接口注册账号异常："+e);
			}
		}
		return false;
	}

}
