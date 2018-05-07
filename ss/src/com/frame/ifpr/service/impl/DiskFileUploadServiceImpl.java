package com.frame.ifpr.service.impl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.frame.core.util.FileUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.service.DiskFileUploadService;
import com.frame.ifpr.vo.FileUploadResultVo;
import com.frame.ifpr.vo.ResponseVo;
import com.frame.ifpr.vo.ThumbnailsVo;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;

/**
 * 上传文件的业务实现类
 * @author yt
 */
@Service("diskFileuploadService")
public class DiskFileUploadServiceImpl implements DiskFileUploadService {
	public static final String FTP_ADDRESS = "ftp.host";
	public static final String USERNAME = "ftp.username";
	public static final String PASSWORD = "ftp.password";
	public static final String PORT = "ftp.port";
	public static final String FTP_PATH = "ftp.path.root";
	private Log log=LogFactory.getLog(DiskFileUploadServiceImpl.class);
	@Override
	public Object fileUpload( JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("fileUpload----service:"+jsonObject+"---------------start");
		System.out.println("request" + request);
		DiskFileItemFactory  factory = new DiskFileItemFactory();  
		  String tempPath =  "/temp";
	        factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中。     
	        factory.setRepository(new File(tempPath)); //设定存储临时文件的目录。     
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	        upload.setHeaderEncoding("UTF-8");  
	        String fileName = null;
	        InputStream is = null;
	        System.out.println("upload" + upload);
		 try {  
	            List items = upload.parseRequest(request);
	            System.out.println("upload" + upload);
	            FileItem fileItem = (FileItem)items.get(0);  
	            
	            is = fileItem.getInputStream(); 
	            fileName = fileItem.getName();
	            log.info("fileUpload----fileName:"+fileName);
	        } catch (FileUploadException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
		 System.out.println("upload1111111111111111111");
		ResponseVo responseVo =new ResponseVo();
		boolean isNeedSmall = false;
		JSONObject jsonParams = null;
		try{
			if(jsonObject == null)isNeedSmall = false;
			jsonParams=JSONObject.fromObject(jsonObject.getString("params"));
		}catch(Exception e){
			isNeedSmall = false;
		}
		//if(jsonObject == null || "".equals(jsonObject.getString("params")) 
		//JSONObject jsonParams=JSONObject.fromObject(jsonObject.getString("params"));
	//	FtpUtil ftp = null;
		long size = is.available();
		 System.out.println("upload1111111111111111111>>>" + size);

		 log.info("fileUpload----uploadsize:"+size);
		String bigSize = (String) SystemConfig.getValue("clt.upload.size");
		String imgPath=SystemConfig.getValue("clt.upload.path");
		if (StringUtils.isNotEmpty(fileName) && size == 0) {
		    log.warn("fileUpload----service:"+fileName);
			throw new Exception("上传图片为空图片");
		}
		if(Long.parseLong(bigSize)<size){
			 log.warn("fileUpload----service:"+size);
			throw new Exception(SystemConfig.getValue("msg.upload.size.excess"));
		}
		

		String imageType=isImageType(fileName);
		 System.out.println("upload1111111111111111111imageType>>>" + imageType);
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
		 log.debug(thumbnailsVo);
		 }
		fileName=removeSpecial(fileName);
		 System.out.println("upload1111111111111111111fileName>>>" + fileName);
		fileName=fileName.substring(0,fileName.lastIndexOf("."));
		String ftpAddress = (String) SystemConfig.getValue(FTP_ADDRESS);
		String username = (String) SystemConfig.getValue(USERNAME);
		String password = (String) SystemConfig.getValue(PASSWORD);
		String port = (String) SystemConfig.getValue(PORT);
		String path = (String) SystemConfig.getValue(FTP_PATH);
		String imgUrl= null;
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
		try{
	//	ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
	//	ftp.login();
	//	 System.out.println("upload1111111111111111111ftp.login>>>" + ftp);

		/*
		if(!ftp.isDirExist(ftpPath)){
			 log.info("创建FTP目录" + ftpPath);
			ftp.createDir(ftpPath);
		}*/
	//	boolean flag = ftp.upload(is, ftpPath+fileName+"."+imageType);
		boolean flag = FileUtil.writeToLocal(ftpPath+fileName+"."+imageType, is);
		 System.out.println("upload1111111111111111111flag>>>" + flag);
		boolean smallFlag = false;
		if(isNeedSmall){
			BufferedImage bufferedImage=Thumbnails.of(is).size(thumbnailsVo.getSmallWidth(),thumbnailsVo.getSmallHeigth()).outputFormat(imageType).keepAspectRatio(false).outputQuality(1.0f).asBufferedImage();
			InputStream inputsamall=getImageStream(bufferedImage,imageType);
		//	smallFlag = ftp.upload(inputsamall,ftpPath+smallFileName+"."+imageType);
			smallFlag = FileUtil.writeToLocal(ftpPath+smallFileName+"."+imageType, inputsamall);
		}
	    log.info(ftpPath+fileName+"."+imageType + "上传成功?" + flag);
	    log.info(ftpPath+smallFileName+"."+imageType + "上传成功?" + smallFlag);
	    FileUploadResultVo fileUploadResultVo = new FileUploadResultVo();
	    System.out.println(ftpPath+fileName+"."+imageType + "上传成功?" + flag);
	    if(smallFlag){
	    	fileUploadResultVo.setPicPath(imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType);
	    }else{
	    	fileUploadResultVo.setPicPath(imgUrl+"/"+imgPath+"/"+fileName+"."+imageType);
	    }
	    
	    log.debug("fileUpload上传路径为"+imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType);
	    responseVo.setReturnCode(ResponseVo.SUCCESS);
	    System.out.println(ResponseVo.SUCCESS);
	    responseVo.setServiceName(SystemConfig.getValue("system.id")  + "_file_upload_resp");
	    responseVo.setResult(fileUploadResultVo);
		}catch (Exception e) {
			log.error(e.getMessage());
			final String mesg="FTP上传文件错误！";
			throw new Exception(mesg);
		}finally{
		//	ftp.closeServer();
			is.close();
		}
		 System.out.println("XXXXXXXXXXXXXXXXXXXXXX");
		log.debug("fileUpload----service:"+jsonObject+"---------------end");
		return responseVo;
	}
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str){
		return str.matches("^[1-9]\\d*$");	

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
	
	private  String removeSpecial(String imageName){
		String newImageName1=imageName.substring(imageName.lastIndexOf("\\")+1);
		String newImageName2=newImageName1.substring(newImageName1.lastIndexOf("/")+1);
		return newImageName2;
	}
	

}
