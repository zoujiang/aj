package com.frame.core.action;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.core.constant.FtpConstant;
import com.frame.core.service.ImageExeService;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.PreviewImageVo;


@Controller
public class ImageExeAction {
	@Resource
	private ImageExeService imageExeServiceImpl;
	protected Log logger=LogFactory.getLog(ImageExeAction.class);
	@RequestMapping("/imgnc") 
   public void getImage(HttpServletRequest request,HttpServletResponse response){
	  PreviewImageVo previewImageVo=new PreviewImageVo();
	  previewImageVo.setImg(request.getParameter("img"));
	  previewImageVo.setViewType(request.getParameter("viewType"));
	  previewImageVo.setWidth(request.getParameter("width"));
	  previewImageVo.setHeigth(request.getParameter("height"));
	  logger.debug("getImage("+previewImageVo+")"+"--------- begin");
		try{
			 String contenttype="image/jpg";
			 contenttype= imageType(previewImageVo.getImg());
			 OutputStream stream = null ;
			 if(null!=previewImageVo.getImg()&&!"".equals(contenttype)){
				 stream=imageExeServiceImpl.getImageAsByte(previewImageVo, response);
			 }
			 response.setContentType(contenttype);
		     stream.flush();
		     stream.close();	
		}catch (Exception e) {
			
		}
		  logger.debug("getImage("+previewImageVo+")"+"--------- end");
  }
  private String imageType(String imagPath){
	 if(null==imagPath||"".equals(imagPath)){
		 return "";
	 }
	 String imageSuffix=imagPath.substring(imagPath.lastIndexOf(".")+1).toLowerCase();
	 if("jpeg".equals(imageSuffix)){
		 return "image/jpeg";
	 }
	 if("jpg".equals(imageSuffix)){
		 return "image/jpg";
	 }
	 if("gif".equals(imageSuffix)){
		 return "image/gif";
	 }
	 if("png".equals(imageSuffix)){
		 return "image/png";
	 }
	return "";
  }
  
  @RequestMapping("/img") 
  public String getImageDisk(HttpServletRequest request,HttpServletResponse response){
			  String viewType = request.getParameter("viewType");
			  String newImagepath = request.getParameter("img");
			  if("2".equals(viewType))
			  {
				  newImagepath = removeSuffixImage(request.getParameter("img"));
			  }
			  String path_dir = SystemConfig.getValue("pic.default.cache.relate.dir");
			  FileInputStream imgIn = null;
			    //response.setHeader("Pragma", "no-cache");
		        //response.setHeader("Cache-Control", "no-cache");
		        //response.setDateHeader("Expires", 0);
			  OutputStream ous = null;
			  try {
				  imgIn = new FileInputStream(path_dir + newImagepath);
				  byte [] buffer = new byte[imgIn.available()];
				  imgIn.read(buffer);
				  imgIn.close();
				  response.addHeader("Content-Disposition", "attachment;filename=" + getFileName(newImagepath));
			      response.addHeader("Content-Length", "" + buffer.length);
			      response.setContentType("application/octet-stream");
			      ous = new BufferedOutputStream(response.getOutputStream());
			      ous.write(buffer);
			      ous.flush();
			      ous.close();
			  } catch (Exception e) {
				  e.printStackTrace();
			  } finally {
				  if (imgIn != null) {
					  try {
						imgIn.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
				  if (ous != null) {
					  try {
						  ous.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
				  
			  }
			  
			  return "forward:" + path_dir + newImagepath;	//  /ams
 }
  
  
  @RequestMapping("/imgCache") 
  public String getImageCache(HttpServletRequest request,HttpServletResponse response){
		try{
			  String viewType = request.getParameter("viewType");
			  String newImagepath = request.getParameter("img");
			  String oldImagepath = request.getParameter("img");
			  if("2".equals(viewType))
			  {
				  newImagepath = removeSuffixImage(request.getParameter("img"));
			  }
			
		    //response.setHeader("Pragma", "no-cache");
	        //response.setHeader("Cache-Control", "no-cache");
	        //response.setDateHeader("Expires", 0);
	        response.setContentType(imageType(newImagepath));
	        String rel_path_dir = request.getSession().getServletContext().getRealPath(SystemConfig.getValue("pic.default.cache.dir"));
	        String path_dir = SystemConfig.getValue("pic.default.cache.dir");
			  String dir1 = newImagepath.substring(0,newImagepath.lastIndexOf("/"));
			  File dir = new File(rel_path_dir + dir1); 
			  if (!dir.exists()) {
				  dir.mkdirs();
			  }
			  //File outputfile = new File("D:\\tmp\\text.jpg"); 
			 
			if (newImagepath == null || newImagepath.isEmpty() || !newImagepath.startsWith("/")) {
				return "forward:/ftp/default.png";
			}
			 File outputfile = new File(rel_path_dir + newImagepath); 
			if (outputfile.exists()) {
				 return "forward:" + path_dir + newImagepath;	//  /ams
				 
				 /* FileInputStream imgIn = null;
				  try {
					  imgIn = new FileInputStream(path_dir + newImagepath);
					  BufferedImage buffImg = ImageIO.read(imgIn);
				        // 将图像输出到Servlet输出流中。
				      ServletOutputStream sos = response.getOutputStream();
				      String type = this.imageType(newImagepath);
				      ImageIO.write(buffImg, type, sos);
				      sos.flush();  
				      sos.close();
				  } catch (Exception e) {
					  e.printStackTrace();
				  } finally {
					  if (imgIn != null) {
						  imgIn.close();
					  }
				  }*/
				  
			      
			  }
			
			/*
			  if (outputfile.exists()) {
				  return "redirect:" + path_dir + newImagepath;	//  /ams
				  
				  FileInputStream imgIn = null;
				  try {
					  imgIn = new FileInputStream(path_dir + newImagepath);
					  BufferedImage buffImg = ImageIO.read(imgIn);
				        // 将图像输出到Servlet输出流中。
				      ServletOutputStream sos = response.getOutputStream();
				      ImageIO.write(buffImg, "jpeg", sos);
				      sos.flush();  
				      sos.close();
				  } catch (Exception e) {
					  e.printStackTrace();
				  } finally {
					  if (imgIn != null) {
						  imgIn.close();
					  }
				  }
			      
			  }*/
			  String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
			  String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
			  String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
			  String port = (String) SystemConfig.getValue(FtpConstant.PORT);
			  String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
			  
			  FileOutputStream outputStream = new FileOutputStream(outputfile);
			  FtpUtil ftp = null;
			  try{	
				//开启ftp
				ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
				ftp.login();
				
				boolean isSuccess =  ftp.getFtpClient().retrieveFile(path+newImagepath, outputStream);
				 //ftp.getFtpClient().retrieveFile(path+newImagepath, response.getOutputStream());
				 
				if(!isSuccess){
					isSuccess =  ftp.getFtpClient().retrieveFile(path+oldImagepath, outputStream);
					if(isSuccess){
						 return "forward:" + path_dir + oldImagepath;
					}
				}
				 //直接FTP输出到用户  ,也可以直接使用下载下来的文件返回给用户
				 //ftp.getFtpClient().retrieveFile(path+newImagepath, response.getOutputStream());
				 return "forward:" + path_dir + newImagepath;
			  } catch (Exception e) {
				  logger.error(e.getMessage(), e);
			  }finally{
			    if (null != ftp) ftp.closeServer();
			    if (null != outputStream) outputStream.close();
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "forward:/ftp/default.png";
		
		
 }
  private  String removeSuffixImage(String imagePath){
		String imageSuffix=imagePath.substring(imagePath.lastIndexOf("/")+1);
		String imagePrefix=imagePath.substring(0,imagePath.lastIndexOf("/")+1);
		String newImagePrefix="";
		if(imageSuffix.startsWith("s")){
			newImagePrefix=imageSuffix.substring(1);
		}else{
			newImagePrefix=imageSuffix;
		}
		return imagePrefix+newImagePrefix;
	}	
  
  
  private  String getFileName(String imagePath){
	  String imageSuffix=imagePath.substring(imagePath.lastIndexOf("/")+1);
		return imageSuffix;
	}	
  
  private String suffixImage(String imagePath){
		String imageSuffix=imagePath.substring(imagePath.lastIndexOf(".")+1);
		return imageSuffix;
	}
  
}
