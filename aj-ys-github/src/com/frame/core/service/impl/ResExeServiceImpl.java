package com.frame.core.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.frame.core.constant.FtpConstant;
import com.frame.core.service.ResExeService;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.PreviewResVo;
import com.qm.shop.Constant;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Service
public class ResExeServiceImpl implements ResExeService {
	protected Log logger=LogFactory.getLog(ResExeServiceImpl.class);
	@Override
	public  OutputStream getResAsByte( PreviewResVo previewResVo,HttpServletResponse response)throws Exception {
		  logger.debug("getImageAsByte("+previewResVo+")"+"--------- begin");
		  String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
		  String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
		  String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
		  String port = (String) SystemConfig.getValue(FtpConstant.PORT);
		  String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
		  String newImagepath=previewResVo.getRes();
		  OutputStream outputStream=response.getOutputStream();
		//  FtpUtil ftp = null;
		  try{	
			//开启ftp
		//	ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
		//	ftp.login();
		//	ftp.getFtpClient().retrieveFile(path+newImagepath, outputStream);
			
			File afile = new File(path+newImagepath);  
	        InputStream it = new FileInputStream(afile);  

			byte flush[]  = new byte[1024];  
	        int len = 0;  
	        while(0<=(len=it.read(flush))){  
	        	outputStream.write(flush, 0, len);  
	        }  
	        //关闭流的注意 先打开的后关  
	        outputStream.close();  
	        it.close();  
		  } catch (Exception e) {
			 logger.error(e.getMessage(), e);
		  }finally{
		//    if(null!=ftp)
		//	ftp.closeServer();
		  }
			logger.debug("getImageAsByte("+previewResVo+")"+"--------- end");
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

		@Override
		public InputStream getInputResAsByte(PreviewResVo previewResVo) throws Exception {
			 logger.debug("getImageAsByte("+previewResVo+")"+"--------- begin");
			  String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
			  String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
			  String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
			  String port = (String) SystemConfig.getValue(FtpConstant.PORT);
			  String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
			  String newImagepath=previewResVo.getRes();
			  InputStream inputStream=null;
		//	  FtpUtil ftp = null;
			  try{	
				//开启ftp
		//		ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
		//		ftp.login();
		//		inputStream = ftp.getFtpClient().retrieveFileStream(path+newImagepath);
				inputStream = new FileInputStream(new File(path+newImagepath));
			  } catch (Exception e) {
				 logger.error(e.getMessage(), e);
			  }finally{
			  //  if(null!=ftp)
				//ftp.closeServer();
			  }
				logger.debug("getImageAsByte("+previewResVo+")"+"--------- end");
			    return inputStream;
		}
		
		@Override
		public boolean downFile2Local(PreviewResVo previewResVo,String localAddr) throws Exception {
			 logger.debug("downFile2Local("+localAddr+")"+"--------- begin");
			  String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
			  String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
			  String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
			  String port = (String) SystemConfig.getValue(FtpConstant.PORT);
			  String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
			  String newImagepath=previewResVo.getRes();
			  InputStream inputStream=null;
		//	  FtpUtil ftp = null;
			  try{	
				//开启ftp
		//		ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
		//		ftp.login();
		//		ftp.download(localAddr, path+newImagepath);
				return true;
			  } catch (Exception e) {
				 logger.error(e.getMessage(), e);
			  }finally{
		//	    if(null!=ftp)
		//		ftp.closeServer();
			  }
				logger.debug("getImageAsByte("+previewResVo+")"+"--------- end");
				return false;
		}

		@Override
		public Boolean downZipFile2Local(PreviewResVo previewResVo,String localAddr) {
			 logger.debug("downFile2Local("+localAddr+")"+"--------- begin");
			  String path = Constant.resPath;
			  String newImagepath = previewResVo.getRes();
			//  FtpUtil ftp = null;
			  try{	
				//开启ftp
			//	ftp=new FtpUtil(Constant.ftpAddress,  Constant.port, Constant.username, Constant.password);
			//	ftp.login();
			//	ftp.download(localAddr, path+newImagepath);
				return true;
			  } catch (Exception e) {
				 logger.error(e.getMessage(), e);
			  }finally{
			 //   if(null!=ftp)
			//	ftp.closeServer();
			  }
				logger.debug("getImageAsByte("+previewResVo+")"+"--------- end");
				return false;
		}
	}


