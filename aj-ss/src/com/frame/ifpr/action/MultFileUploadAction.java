package com.frame.ifpr.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.core.util.FtpUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.service.FileUploadService;
import com.frame.ifpr.util.JsonUtil;
import com.frame.ifpr.vo.ErrorVo;
import com.frame.ifpr.vo.FileUploadResultVo;
import com.frame.ifpr.vo.ResponseVo;
import com.frame.ifpr.vo.ThumbnailsVo;

public class MultFileUploadAction {
	   protected Log logger=LogFactory.getLog(MultFileUploadAction.class);
		public static final String FTP_ADDRESS = "ftp.host";
		public static final String USERNAME = "ftp.username";
		public static final String PASSWORD = "ftp.password";
		public static final String PORT = "ftp.port";
		public static final String FTP_PATH = "ftp.path.root";
	  @RequestMapping("/diskFileUpload") 
	   @ResponseBody
	   public String fileUpload(String jsonParam, HttpServletRequest request,HttpServletResponse response){
		   logger.debug("fileUpload---Action:"+jsonParam+"--------------------start");
		   String sysId = SystemConfig.getValue("system.id");
			String jsonReq=null;
			StringWriter sw=new StringWriter();
	System.out.println("welcome..");
	String flag = "1";
	Map<String,Object> resultMap = new HashMap<String,Object>();
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	
	DiskFileItemFactory factory = new DiskFileItemFactory();
	
	// 设置内存缓冲区，超过后写入临时文件
	factory.setSizeThreshold(10240000);
	// 设置临时文件存储位置
	String realPath = request.getSession().getServletContext().getRealPath("/upload");  ;
	File file = new File(realPath);
	if(!file.exists()){
		file.mkdirs();
	}
	factory.setRepository(file);
	ServletFileUpload upload = new ServletFileUpload(factory);
	// 设置单个文件的最大上传值
	upload.setFileSizeMax(10002400000l);
	// 设置整个request的最大值
	upload.setSizeMax(10002400000l);
	upload.setHeaderEncoding("UTF-8");
	JSONObject jsonObject= null;
	try{
	jsonObject=JSONObject.fromObject(jsonParam);
	}catch(Exception e){
		e.printStackTrace();
	}
	try {
		
		List<FileItem> items = upload.parseRequest(request);
		String filePath = saveFiles(jsonObject, items);
		ResponseVo responseVo =new ResponseVo();
		  responseVo.setReturnCode(ResponseVo.SUCCESS);
		    responseVo.setServiceName(SystemConfig.getValue("system.id")  + "_file_upload_resp");
		    FileUploadResultVo fileUploadResultVo = new FileUploadResultVo();
		    fileUploadResultVo.setPicPath(filePath);
		    responseVo.setResult(fileUploadResultVo);
		    
		    return JsonUtil.getJsonString4JavaPOJO(responseVo);
		  
	} catch (FileUploadException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	  }
	  
	  private String saveFiles(JSONObject jsonObject,List<FileItem> files) throws  Exception{
		  StringBuffer rtnpath = new StringBuffer();
		  FtpUtil ftp = null;
		  String ftpAddress = (String) SystemConfig.getValue(FTP_ADDRESS);
			String username = (String) SystemConfig.getValue(USERNAME);
			String password = (String) SystemConfig.getValue(PASSWORD);
			String port = (String) SystemConfig.getValue(PORT);
			String path = (String) SystemConfig.getValue(FTP_PATH);
			String imgUrl= null;
			
			try{
			ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
			ftp.login();
			
		  for(FileItem file:files){
			long size = file.getSize();
			 boolean isNeedSmall = false;
			logger.info("fileUpload----uploadsize:"+size);
			String bigSize = (String) SystemConfig.getValue("clt.upload.size");
			String imgPath=SystemConfig.getValue("clt.upload.path");
			String fileName = file.getName();// 获取文件名
			JSONObject jsonParams = null;
			try{
				if(jsonObject == null)isNeedSmall = false;
				jsonParams=JSONObject.fromObject(jsonObject.getString("params"));
			}catch(Exception e){
				isNeedSmall = false;
			}
			
			if (StringUtils.isNotEmpty(fileName) && size == 0) {
				logger.warn("fileUpload----service:"+fileName);
				throw new Exception("上传图片为空图片");
			}
			if(Long.parseLong(bigSize)<size){
				logger.warn("fileUpload----service:"+size);
				throw new Exception(SystemConfig.getValue("msg.upload.size.excess"));
			}
			String imageType=isImageType(fileName);
			 ThumbnailsVo thumbnailsVo = null;
			
			if(SystemConfig.getValue("pic.suffix.filetype").indexOf(imageType) > -1){
				 //log.warn("fileUpload----service:"+"上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
				//throw new Exception("上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
				 //如果不是图片，那么则上传视频，或者音频地址
				//ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
				isNeedSmall = false;
			}else{
			 thumbnailsVo = new ThumbnailsVo();
			 if(jsonParams != null){
				 if(null!=jsonParams.get("smallWidth")&&isNum(jsonParams.get("smallWidth").toString())){
					 thumbnailsVo.setSmallWidth(Integer.parseInt(jsonParams.get("smallWidth").toString()));
					 isNeedSmall = true;
				 }
				 if(null!=jsonParams.get("smallHeigth")&&isNum(jsonParams.get("smallHeigth").toString())){
					 thumbnailsVo.setSmallHeigth(Integer.parseInt(jsonParams.get("smallHeigth").toString()));
					 isNeedSmall = true;
				 }
				 if(null!=jsonParams.get("fileType")&&imageType(jsonParams.get("fileType").toString())){
					 thumbnailsVo.setFileType(jsonParams.get("fileType").toString());
				 }
				}
			 logger.debug(thumbnailsVo);
			 }
			fileName=removeSpecial(fileName);
			fileName=fileName.substring(0,fileName.lastIndexOf("."));
			if(SystemConfig.getValue("pic.suffix.filetype").indexOf(imageType) > -1){
				imgUrl= SystemConfig.getValue("img.http.url");
			}else{
				imgUrl= SystemConfig.getValue("res.http.url");
			}
			//String imgUrl= SystemConfig.getValue("img.http.url");
			String smallFileName="";
			String ftpPath = "";// 数据库路径
			
			String unique = String.valueOf(System.currentTimeMillis());
			fileName = unique;
			smallFileName=fileName+"_small";
			ftpPath = path + "/"+imgPath+"/"; // 不能这个路径/upload/wod
			/*
			if(!ftp.isDirExist(ftpPath)){
				 log.info("创建FTP目录" + ftpPath);
				ftp.createDir(ftpPath);
			}*/
			boolean flag = ftp.upload(file.getInputStream(), ftpPath+fileName+"."+imageType);
			boolean smallFlag = false;
			if(isNeedSmall){
				BufferedImage bufferedImage=Thumbnails.of(file.getInputStream()).size(thumbnailsVo.getSmallWidth(),thumbnailsVo.getSmallHeigth()).outputFormat(imageType).keepAspectRatio(false).outputQuality(1.0f).asBufferedImage();
				InputStream inputsamall=getImageStream(bufferedImage,imageType);
				smallFlag = ftp.upload(inputsamall,ftpPath+smallFileName+"."+imageType);
			}
			logger.info(ftpPath+fileName+"."+imageType + "上传成功?" + flag);
			logger.info(ftpPath+smallFileName+"."+imageType + "上传成功?" + smallFlag);
		    FileUploadResultVo fileUploadResultVo = new FileUploadResultVo();
		    
		    if(smallFlag){
		    	rtnpath.append(imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType+";");
		    }else{
		    	rtnpath.append(imgUrl+"/"+imgPath+"/"+fileName+"."+imageType+";");
		    }
		    
		    logger.debug("fileUpload上传路径为"+imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType);
			
		  }//end for
			}catch (Exception e) {
				logger.error(e.getMessage());
				final String mesg="FTP上传文件错误！";
				throw new Exception(mesg);
			}finally{
				ftp.closeServer();
			}
			logger.debug("fileUpload----service:"+jsonObject+"---------------end");
			if(rtnpath.length() > 0)rtnpath.deleteCharAt(rtnpath.length() - 1);
			return rtnpath.toString();
		  
	  }
	
	  private String isImageType(String imageName){
			 if(null==imageName||"".equals(imageName)){
				 return "";
			 }
			 String imageSuffix=imageName.substring(imageName.lastIndexOf(".")+1).toLowerCase();
			 if("jpeg".equals(imageSuffix)){
				 return "jpeg";
			 }
			 if("jpg".equals(imageSuffix)){
				 return "jpg";
			 }
			 if("gif".equals(imageSuffix)){
				 return "gif";
			 }
			 if("png".equals(imageSuffix)){
				 return "png";
			 }
			return imageSuffix;
			
		  }
		private  String removeSpecial(String imageName){
			String newImageName1=imageName.substring(imageName.lastIndexOf("\\")+1);
			String newImageName2=newImageName1.substring(newImageName1.lastIndexOf("/")+1);
			return newImageName2;
		}
		/**
		 * 判断是否为数字
		 * @param str
		 * @return
		 */
		public static boolean isNum(String str){
			return str.matches("^[1-9]\\d*$");	

		}
		private InputStream getImageStream(BufferedImage bufferedImage,String type){
			logger.debug("getImageStream"+type+"---------------start");
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
			 logger.debug("getImageStream"+type+"---------------end");
		   return is;  
		}
		
		/**
		 * 判断上传的图片是否符合规范
		 * @param imagPath
		 * @return
		 */
		 private boolean imageType(String imageType){
			 String type=imageType.toLowerCase();
			 if(null==type||"".equals(type)){
				 return true;
			 }
			 if("jpeg".equals(type)){
				 return true;
			 }
			 if("jpg".equals(type)){
				 return true;
			 }
			 if("gif".equals(type)){
				 return true;
			 }
			 if("png".equals(type)){
				 return true;
			 }
			return false;
			
		  }
}
