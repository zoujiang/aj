package com.frame.ifpr.service.impl;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.service.FileUploadService;
import com.frame.ifpr.vo.FileUploadResultVo;
import com.frame.ifpr.vo.ResponseVo;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


/**
 * 上传文件的业务实现类
 * @author yt
 */
@Service("fileuploadService")
public class FileUploadServiceImpl implements FileUploadService {
	public static final String FTP_ADDRESS = "ftp.host";
	public static final String USERNAME = "ftp.username";
	public static final String PASSWORD = "ftp.password";
	public static final String PORT = "ftp.port";
	public static final String FTP_PATH = "ftp.path.root";
	private Log log=LogFactory.getLog(FileUploadServiceImpl.class);
	
	public Object fileUploadFtp( JSONObject jsonObject, CommonsMultipartFile file) throws Exception {
		log.debug("fileUpload----service:"+jsonObject+"---------------start");
		ResponseVo responseVo =new ResponseVo();
		boolean isNeedSmall = false;
		String fileName = file.getFileItem().getName();// 获取文件名
		
		//if(jsonObject == null || "".equals(jsonObject.getString("params")) 
		//JSONObject jsonParams=JSONObject.fromObject(jsonObject.getString("params"));
		FtpUtil ftp = null;
		long size = file.getSize();
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
		isNeedSmall = true;
		if(SystemConfig.getValue("pic.suffix.filetype").indexOf(imageType.toLowerCase()) < 0){
			 //log.warn("fileUpload----service:"+"上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
			//throw new Exception("上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
			 //如果不是图片，那么则上传视频，或者音频地址
			//ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
			isNeedSmall = false;
		}
		fileName=removeSpecial(fileName);
		fileName=fileName.substring(0,fileName.lastIndexOf("."));
		String ftpAddress = (String) SystemConfig.getValue(FTP_ADDRESS);
		String username = (String) SystemConfig.getValue(USERNAME);
		String password = (String) SystemConfig.getValue(PASSWORD);
		String port = (String) SystemConfig.getValue(PORT);
		String path = (String) SystemConfig.getValue(FTP_PATH);
		String imgUrl= "";
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
		smallFileName="s" + fileName+"";
		ftpPath = path + "/"+imgPath+"/"; // 不能这个路径/upload/wod
		try{
		ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
		ftp.login();
		
		if(!ftp.isDirExist(ftpPath)){
			 log.info("创建FTP目录" + ftpPath);
			ftp.createDir(ftpPath);
		}
		boolean flag = ftp.upload(file.getInputStream(), ftpPath+fileName+"."+imageType);
		boolean smallFlag = false;
		
		if(isNeedSmall){
			long startTime = System.currentTimeMillis();
			int width = Integer.parseInt(SystemConfig.getValue("pic.default.width.size"));
			InputStream ins = file.getInputStream();
			BufferedImage image = ImageIO.read(ins);  
			  
			int imageWidth = image.getWidth();  
			int imageHeitht = image.getHeight(); 
			int height = Float.valueOf((float) (imageHeitht/(imageWidth*1.0) * width)).intValue();
			if(width > imageWidth){
				 width = imageWidth;
				 height= imageHeitht;
			}
			long startTime2 = System.currentTimeMillis();
			log.info("获取长宽耗时间" + (startTime2-startTime));
			
			//String imgPath = ftpPath+fileName+"."+imageType;
			
			
			
			
			/*if ((float)300 / 400 != (float)imageWidth / imageHeitht) {  
			   if (imageWidth > imageHeitht) {  
			       image = Thumbnails.of(fromPic).height(300).asBufferedImage();  
			   } else {  
			       image = Thumbnails.of(fromPic).width(400).asBufferedImage();  
			   }  
			   builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, 400, 300).size(400, 300);  
			} else {  
			   builder = Thumbnails.of(image).size(400, 300);  
			}  
			builder.outputFormat("jpg").toFile(toPic);  
*/
			//BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
            //bufferedImage.getGraphics().drawImage(image.getScaledInstance(width, height, image.SCALE_SMOOTH),  
            //        0,0,null);  
            //OutputStream os = null;  
            // "image/jpeg"  
           // ImageIO.write(bufferedImage, imageType, os);  
            
			//BufferedImage bufferedImage=Thumbnails.of(file.getInputStream()).size(width, height).outputFormat(imageType)。keepAspectRatio(false).outputQuality(0.5f).asBufferedImage();
			BufferedImage bufferedImage=Thumbnails.of(file.getInputStream()).size(width, height).keepAspectRatio(false).outputQuality(0.2f).asBufferedImage();
			InputStream inputsamall=getImageStream(bufferedImage,imageType);
			smallFlag = ftp.upload(inputsamall,ftpPath+smallFileName+"."+imageType);
			log.info("压缩耗时" + (System.currentTimeMillis()-startTime2));
		}
	    log.info(ftpPath+fileName+"."+imageType + "上传成功?" + flag);
	    log.info(ftpPath+smallFileName+"."+imageType + "上传成功?" + smallFlag);
	    FileUploadResultVo fileUploadResultVo = new FileUploadResultVo();
	    
	    if(smallFlag){
	    	fileUploadResultVo.setPicPath(imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType);
	    }else{
	    	fileUploadResultVo.setPicPath(imgUrl+"/"+imgPath+"/"+fileName+"."+imageType);
	    }
	   
	    log.debug("fileUpload上传路径为"+imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType);
	    responseVo.setReturnCode(ResponseVo.SUCCESS);
	    responseVo.setServiceName(SystemConfig.getValue("system.id")  + "_file_upload_resp");
	    responseVo.setResult(fileUploadResultVo);
		}catch (Exception e) {
			log.error(e.getMessage());
			final String mesg="FTP上传文件错误！";
			throw new Exception(mesg);
		}finally{
			ftp.closeServer();
		}
		log.debug("fileUpload----service:"+jsonObject+"---------------end");
		return responseVo;
	}
	
	@Override
	public Object fileUpload( JSONObject jsonObject, CommonsMultipartFile file) throws Exception {
		log.debug("fileUpload----service:"+jsonObject+"---------------start");
		ResponseVo responseVo =new ResponseVo();
		boolean isNeedSmall = false;
		String fileName = file.getFileItem().getName();// 获取文件名
		
		//if(jsonObject == null || "".equals(jsonObject.getString("params")) 
		//JSONObject jsonParams=JSONObject.fromObject(jsonObject.getString("params"));
		long size = file.getSize();
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
		isNeedSmall = true;
		if(SystemConfig.getValue("pic.suffix.filetype").indexOf(imageType.toLowerCase()) < 0){
			isNeedSmall = false;
		}
		fileName=removeSpecial(fileName);
		fileName=fileName.substring(0,fileName.lastIndexOf("."));
		
		String smallFileName="";
		String unique = String.valueOf(System.currentTimeMillis());
		fileName = unique;
		smallFileName="s" + fileName+"";
		try{
		boolean smallFlag = false;
		 String imgUrl = SystemConfig.getValue("pic.default.cache.relate.dir");
		File fileParent = new File(imgUrl+"/"+imgPath+"/");
		if(!fileParent.exists()){
			fileParent.createNewFile();
		}
		String bigFile = imgUrl+"/"+imgPath+"/"+fileName+"."+imageType;
		File outputfile = new File(bigFile); 
		 file.transferTo(outputfile);
		
		 //isNeedSmall = false;
		 if(isNeedSmall){
				 FileOutputStream os =null;
				try{
				long startTime = System.currentTimeMillis();
				String soutputfile = imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType; 
				/*
				log.info("获取长宽耗时间" + (startTime2-startTime));
				 String soutputfile = imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType; 
				 image =  Thumbnails.of(image).scale(1f).sourceRegion(0, 0, width, height).asBufferedImage();
				Thumbnails.of(image).scale(1f).toFile(soutputfile);*/
				/*long startTime2 = System.currentTimeMillis();
				BufferedImage bi = ImageIO.read(outputfile);
				if (Float.valueOf(bi.getWidth()) / Float.valueOf(bi.getHeight()) > 1) {
				    bi = Thumbnails.of(bi).height(300).asBufferedImage();
				} else {
				    bi = Thumbnails.of(bi).width(300).asBufferedImage();
				}
				if (bi.getWidth() - 300 > 0) {
				    int x = (bi.getWidth() - 300) / 2;
				    bi = Thumbnails.of(bi).scale(1f).sourceRegion(x, 0, 300, 300).asBufferedImage();
				} else {
				    bi = Thumbnails.of(bi).scale(1f).sourceRegion(0, 0, 300, 300).asBufferedImage();
				}
				 String soutputfile = imgUrl+"/"+imgPath+"/"+smallFileName+"."+imageType; 
				Thumbnails.of(bi).scale(1f).toFile(soutputfile);*/
				
					//	bi	.toFile(soutputfile);
				/*long startTime2 = System.currentTimeMillis();
				BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
				
	            bufferedImage.getGraphics().drawImage(image.getScaledInstance(width, height, image.SCALE_SMOOTH),  
	                    0,0,null);  
	            
	            Thumbnails.of(bufferedImage).scale(1f).toFile(soutputfile);*/
	            //outImage(soutputfile, bufferedImage,  quality);
				//smallFlag = ftp.upload(inputsamall,ftpPath+smallFileName+"."+imageType);
				smallFlag = resizeImage(bigFile,soutputfile);
				log.info(bigFile + "压缩耗时>>" + (System.currentTimeMillis()-startTime));
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally{
					if(os != null)os.close();
				}
			}
		 
		 
		 
	    FileUploadResultVo fileUploadResultVo = new FileUploadResultVo();
	    String imgHttpUrl= "";
		if(SystemConfig.getValue("pic.suffix.filetype").indexOf(imageType) > -1){
			imgHttpUrl= SystemConfig.getValue("img.http.url");
		}else{
			imgHttpUrl= SystemConfig.getValue("res.http.url");
		}
		
	    if(smallFlag){
	    	fileUploadResultVo.setPicPath(imgHttpUrl+"/"+imgPath+"/"+smallFileName+"."+imageType);
	    }else{
	    	fileUploadResultVo.setPicPath(imgHttpUrl+"/"+imgPath+"/"+fileName+"."+imageType);
	    }
	   
	    responseVo.setReturnCode(ResponseVo.SUCCESS);
	    responseVo.setServiceName(SystemConfig.getValue("system.id")  + "_file_upload_resp");
	    responseVo.setResult(fileUploadResultVo);
		}catch (Exception e) {
			log.error(e.getMessage());
			final String mesg="FTP上传文件错误！";
			throw new Exception(mesg);
		}finally{
			
		}
		log.debug("fileUpload----service:"+jsonObject+"---------------end");
		return responseVo;
	}
	
	   public  boolean resizeImage(String bigFile,String toFile) {
		   boolean flag = true;
		   try {
			   Metadata metadata = JpegMetadataReader.readMetadata(new File(bigFile));
			   Directory exifDirectory = metadata.getFirstDirectoryOfType(ExifDirectoryBase.class);
			   if (exifDirectory == null || !exifDirectory.containsTag(ExifDirectoryBase.TAG_ORIENTATION)) flag = false;
		        
		        int width = Integer.parseInt(SystemConfig.getValue("pic.default.width.size"));
				int height = width;
		        float quality = Float.parseFloat(SystemConfig.getValue("pic.default.quality"));
		        BufferedImage bi = ImageIO.read(new File(bigFile));
		        
				int imageWidth = bi.getWidth();  
				int imageHeight =  bi.getHeight();
		        if(width > imageWidth){
					 width = imageWidth;
					 height= imageHeight;
				}
		        BufferedImage image =  Thumbnails.of(bigFile).size( width, height).outputQuality(quality).asBufferedImage();
		        int orientation = 0;
		        if(flag)orientation = exifDirectory.getInt(ExifDirectoryBase.TAG_ORIENTATION);
		        switch (orientation) {
	            case 1: break;//"Top, left side (Horizontal / normal)";
	            case 2: break;//"Top, right side (Mirror horizontal)";
	            case 3:  image = Thumbnails.of(image).scale(1f).rotate(180).asBufferedImage();break;//"Bottom, right side (Rotate 180)";
	            case 4: break;//"Bottom, left side (Mirror vertical)";
	            case 5: image = Thumbnails.of(image).scale(1f).rotate(270).asBufferedImage();break; //"Left side, top (Mirror horizontal and rotate 270 CW)";
	            case 6: Thumbnails.of(image).scale(1f).rotate(270).asBufferedImage();break; //"Right side,   top (Rotate 90 CW)";
	            case 7: Thumbnails.of(image).scale(1f).rotate(90).asBufferedImage();break; //"Right side, bottom (Mirror horizontal and rotate 90 CW)";
	            case 8: Thumbnails.of(image).scale(1f).rotate(270).asBufferedImage(); break;//"Left side, bottom (Rotate 270 CW)";
	            default:break;
	         }
		        //log.info("角度值：" + orientation);
		        Thumbnails.of(image).scale(1f).toFile(toFile);
		        flag = true;
			   } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			   flag = false;
			   }
		   
		   
		   
		   
		   return flag;
	    }

	   public static BufferedImage resizeImage(final BufferedImage bufferedimage,
	            final int w, final int h) {
	        int type = bufferedimage.getColorModel().getTransparency();
	        BufferedImage img;
	        Graphics2D graphics2d;
	        (graphics2d = (img = new BufferedImage(w, h, type))
	                .createGraphics()).setRenderingHint(
	                RenderingHints.KEY_INTERPOLATION,
	                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0, bufferedimage
	                .getWidth(), bufferedimage.getHeight(), null);
	        graphics2d.dispose();
	        return img;
	    }	
	
	 /** 
     * * 将图片文件输出到指定的路径，并可设定压缩质量 
   *  
   * @param outImgPath 
   * @param newImg 
   * @param per 
   */  
  private  void outImage(String outImgPath, BufferedImage newImg,  
          float per) {  
      // 判断输出的文件夹路径是否存在，不存在则创建  
      File file = new File(outImgPath);  
      if (!file.getParentFile().exists()) {  
          file.getParentFile().mkdirs();  
      }// 输出到文件流  
      FileOutputStream newimage = null;
      try {  
          newimage = new FileOutputStream(outImgPath);  
       //   JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);  
       //   JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(newImg);  
          // 压缩质量  
        //  jep.setQuality(per, true);  
        //  encoder.encode(newImg, jep);  
          
        //获取原文件的格式
          String fileType = outImgPath.substring(outImgPath.lastIndexOf("/")+1, outImgPath.length());
          ImageIO.write(newImg, fileType, newimage);   
          
      } catch (Exception e) {  
          // TODO Auto-generated catch blocke.printStackTrace();  
      }  finally{
    	  if(newimage != null)
			try {
				newimage.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
      }
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
		 /*if("jpeg".equals(imageSuffix)){
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
		 }*/
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
