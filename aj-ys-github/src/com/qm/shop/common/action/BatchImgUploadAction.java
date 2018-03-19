package com.qm.shop.common.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.constant.FtpConstant;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.SystemConfig;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/fileUploadCommon")
@Scope("prototype")
public class BatchImgUploadAction extends FtpImgDownUploadAction{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping("/f/fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam(value = "file") MultipartFile file, String from) {
		log.info("上传图片来源："+from);
		String DBPath;
		try {
			System.out.println( file.getContentType());
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
				throw new FileUploadException("上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
			}
			long size = file.getSize();
			if (size == 0) {
				throw new FileUploadException("上传文件为空文件");
			}	
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			DBPath = "";
			String sDBPath = "";// 数据库路径
			FtpUtil ftp = null;
			if (StringUtils.isNotEmpty(module)) {
				if (StringUtils.isNotEmpty(fileName)) {
					String unique = String.valueOf(System.currentTimeMillis()) + new Random().nextInt(10);
					fileName = unique+"." + imageSuffix;
					DBPath = "/" + module+"/"+fileName; //    不能这个路径/upload/wod
					sDBPath = "/" + module+"/s"+fileName;
					ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
					boolean flag = false;
					boolean sflag = false;
					try {
						ftp.login();
						log.info("fileUpload("+module+","+file+","+SystemConfig.getValue("pic.needSmall.module.filetype")+") -  start");
						flag = ftp.upload(file.getInputStream(), path + DBPath);
						
						if(from != null && "dynamic".equals(from)){
							String nDBPath = "/" + module+"/n"+fileName;
							//动感影集，生成一个中等大小的图片
							log.info("fileUpload("+module+","+file+") - nomal start");
							
							//动感影集显示大概宽500 高750，按照这个比例压缩
							//BufferedImage bufferedImage=zoomImage(im, 0.5f);
				          
							BufferedImage bufferedImage=zoomImage(file.getInputStream());
							InputStream inputsamall=getImageStream(bufferedImage,imageSuffix);
							sflag = ftp.upload(inputsamall, path + nDBPath);
							log.info("fileUpload("+module+","+file+") - nomal end");
						}else if(SystemConfig.getValue("pic.needSmall.module").contains(module) && SystemConfig.getValue("pic.needSmall.module.filetype").indexOf(imageSuffix.toLowerCase()) != -1){
							log.info("fileUpload("+module+","+file+") - small start");
							BufferedImage bufferedImage=zoomImage(file.getInputStream(), 0.3f);
							InputStream inputsamall=getImageStream(bufferedImage,imageSuffix);
							sflag = ftp.upload(inputsamall, path + sDBPath);
							log.info("fileUpload("+module+","+file+") - small end");
						}
						
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
			JSONObject result = new JSONObject();
			result.put("success", true);
			result.put("url", DBPath);
			result.put("surl", sDBPath);
			return result.toString();
		} catch (Exception e) {
		
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("url", "");
			return result.toString();
		}
	}
	
	private BufferedImage zoomImage(InputStream srcfile) {
		  
          
          BufferedImage result = null;  
    	  
	        try {  
	            BufferedImage im = ImageIO.read(srcfile);  
	          //  BufferedImage im = readMemoryImage(srcfile);  
	            
	            /* 原始图像的宽度和高度 */  
	            int width = im.getWidth();  
	            int height = im.getHeight();  
	              
	            float resizeTimes = 1.0f;
	            if(width > 500 || height > 750){
	            	if(width*1.0/height >= 500.0/750){
	            		resizeTimes =  490.0f / width   ;
	            	}else{
	            		resizeTimes =  740.0f / height  ;
	            	}
	            }
	            
	            /* 调整后的图片的宽度和高度 */  
	            int toWidth = (int) (width * resizeTimes);  
	            int toHeight = (int) (height * resizeTimes);  
	  
	            /* 新生成结果图片 */  
	            result = new BufferedImage(toWidth, toHeight,  
	                    BufferedImage.TYPE_INT_RGB);  
	  
	            result.getGraphics().drawImage(  
	                    im.getScaledInstance(toWidth, toHeight,  
	                            java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
	              
	  
	        } catch (Exception e) {  
	            System.out.println("创建缩略图发生异常" + e.getMessage());  
	        }  
	          
	        return result;  
          
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
			   e.printStackTrace();
			   flag = false;
			   }
		   
		   
		   
		   
		   return flag;
	    }
	 
	 /** 
	     * @param im 
	     *            原始图像 
	     * @param resizeTimes 
	     *            倍数,比如0.5就是缩小一半,0.98等等double类型 
	     * @return 返回处理后的图像 
	     */  
	    public static BufferedImage zoomImage(InputStream srcfile, float resizeTimes) {  
	          
	        BufferedImage result = null;  
	  
	        try {  
	            BufferedImage im = ImageIO.read(srcfile);  
	  
	            /* 原始图像的宽度和高度 */  
	            int width = im.getWidth();  
	            int height = im.getHeight();  
	              
	            /* 调整后的图片的宽度和高度 */  
	            int toWidth = (int) (width * resizeTimes);  
	            int toHeight = (int) (height * resizeTimes);  
	  
	            /* 新生成结果图片 */  
	            result = new BufferedImage(toWidth, toHeight,  
	                    BufferedImage.TYPE_INT_RGB);  
	  
	            result.getGraphics().drawImage(  
	                    im.getScaledInstance(toWidth, toHeight,  
	                            java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
	              
	  
	        } catch (Exception e) {  
	            System.out.println("创建缩略图发生异常" + e.getMessage());  
	        }  
	          
	        return result;  
	  
	    }  
	      
}
