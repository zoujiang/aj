package com.frame.core.service.impl;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.frame.core.constant.FtpConstant;
import com.frame.core.service.ImageExeService;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.PreviewImageVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Service
public class ImageExeServiceImpl implements ImageExeService {
	protected Log logger=LogFactory.getLog(ImageExeServiceImpl.class);
	@Override
	public  OutputStream getImageAsByte( PreviewImageVo previewImageVo,HttpServletResponse response)throws Exception {
		  logger.debug("getImageAsByte("+previewImageVo+")"+"--------- begin");
		  String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
		  String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
		  String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
		  String port = (String) SystemConfig.getValue(FtpConstant.PORT);
		  String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
		  String newImagepath=previewImageVo.getImg();
		  if(null!=previewImageVo.getViewType()&&!"".equals(previewImageVo.getViewType())&&"1".equals(previewImageVo.getViewType())){
			  newImagepath=addSuffixImage(newImagepath);
			  
		  }else if(null!=previewImageVo.getViewType()&&!"".equals(previewImageVo.getViewType())&&"2".equals(previewImageVo.getViewType())){
			  newImagepath=removeSuffixImage(newImagepath);
		  }else if(null!=previewImageVo.getViewType()&&!"".equals(previewImageVo.getViewType())&&"3".equals(previewImageVo.getViewType()))
		  {  newImagepath=removeSuffixImage(newImagepath);
			  if(!isNum(previewImageVo.getWidth())||!isNum(previewImageVo.getHeigth())){
				  logger.warn("固定宽高浏览width="+previewImageVo.getWidth()+"或heigth="+previewImageVo.getWidth()+"非数字！");
				  previewImageVo.setHeigth("245");
				  previewImageVo.setWidth("150");
			  }
			  
		  }
		  OutputStream outputStream=response.getOutputStream();
		  FtpUtil ftp = null;
		  try{	
			//开启ftp
			ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
			ftp.login();
			if("3".equals(previewImageVo.getViewType())){
			  InputStream in =ftp.getFtpClient().retrieveFileStream(path+newImagepath);
			 Thumbnails.of(in).size(Integer.parseInt(previewImageVo.getWidth()),Integer.parseInt(previewImageVo.getHeigth())).outputFormat(suffixImage(newImagepath)).keepAspectRatio(false).outputQuality(1.0f)
			 .toOutputStream(outputStream);
			}else{
			ftp.getFtpClient().retrieveFile(path+newImagepath, outputStream);
			}
		  } catch (Exception e) {
			 logger.error(e.getMessage(), e);
		  }finally{
		    if(null!=ftp)
			ftp.closeServer();
		  }
			logger.debug("getImageAsByte("+previewImageVo+")"+"--------- end");
		    return outputStream;
			}

		private  String removeSuffixImage(String imagePath){
			String imageSuffix=imagePath.substring(imagePath.lastIndexOf(".")+1);
			String imagePrefix=imagePath.substring(0,imagePath.lastIndexOf(".")+1);
			String newImagePrefix="";
			if(imagePrefix.startsWith("s")){
				newImagePrefix=imagePrefix.substring(1)+".";
			}else{
				newImagePrefix=imagePrefix;
			}
			return newImagePrefix+imageSuffix;
		}	
		private  String addSuffixImage(String imagePath){
			/*String imageSuffix=imagePath.substring(imagePath.lastIndexOf(".")+1);
			String imagePrefix=imagePath.substring(0,imagePath.lastIndexOf(".")+1);
			String newImagePrefix="";
			if(imagePrefix.indexOf("_small.")==-1){
				newImagePrefix=imagePath.substring(0,imagePath.lastIndexOf("."))+"_small.";
			}else{
				newImagePrefix=imagePrefix;
			}
			 return newImagePrefix+imageSuffix;*/
			 return imagePath;
		}
		
		/**
		 * 判断是否为数字
		 * @param str
		 * @return
		 */
		public  boolean isNum(String str){		
			if(null==str||"".equals(str.trim()))
				return false;
			return str.matches("^[1-9]\\d*$");	

		}
		private String suffixImage(String imagePath){
			String imageSuffix=imagePath.substring(imagePath.lastIndexOf(".")+1);
			return imageSuffix;
		}
	}


