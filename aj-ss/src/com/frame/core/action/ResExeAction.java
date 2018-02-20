package com.frame.core.action;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.frame.core.service.ResExeService;
import com.frame.core.vo.PreviewResVo;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Controller
public class ResExeAction {
	@Resource
	private ResExeService resExeServiceImpl;
	protected Log logger=LogFactory.getLog(ResExeAction.class);
	
	@RequestMapping("/resdown2Mp3") 
	public String getResdownAmr2Mp3(HttpServletRequest request,HttpServletResponse response){
		  PreviewResVo previewExeVo=new PreviewResVo();
		  String url = request.getParameter("url");
		  previewExeVo.setRes(url);
		  logger.debug("getRes("+previewExeVo+")"+"--------- begin");
		  String imageSuffix=url.substring(url.lastIndexOf(".")+1).toLowerCase();
			 String name = url.substring(url.lastIndexOf("/")+1).toLowerCase();
			try{
				 //String contenttype=imageSuffix + "/jpg";
				 String contenttype= imageType(previewExeVo.getRes());
				 
				 String tmpUrl = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
				 tmpUrl = tmpUrl.replace("/WEB-INF/classes/", "/temp/");
				 String amrPath = tmpUrl + name;
				 String mp3Path = tmpUrl + name + ".mp3";
				 boolean isSucc = false;
				 File mp3PathFile = new File(mp3Path);
				 //if(FileUtils.)
				 if(!mp3PathFile.exists()){
				 if(null!=previewExeVo.getRes()&&!"".equals(contenttype)){
					 try {
						isSucc=resExeServiceImpl.downFile2Local(previewExeVo, amrPath);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				 changeToMp3Inlinux(amrPath, mp3Path);
				 }
			}catch (Exception e) {
				e.printStackTrace();
			}
			  logger.debug("getImage("+previewExeVo+")"+"--------- end");
			  return "forward:../temp/" + name + ".mp3";
	  }
	
	@RequestMapping("/resdownMp4") 
	public void getResdownMp4(HttpServletRequest request,HttpServletResponse response){
		  PreviewResVo previewExeVo=new PreviewResVo();
		  String url = request.getParameter("url");
		  if(url.indexOf(".m4v") > 0){
			  url = url.substring(0,url.indexOf(".m4v"));
		 }
		  previewExeVo.setRes(url);
		  logger.debug("getRes("+previewExeVo+")"+"--------- begin");
			try{
				 String imageSuffix=url.substring(url.lastIndexOf(".")+1).toLowerCase();
				 String name = url.substring(url.lastIndexOf("/")+1).toLowerCase();
				 //String contenttype=imageSuffix + "/jpg";
				 String contenttype= imageType(previewExeVo.getRes());
				 OutputStream stream = null ;
				 response.setContentType("video/mp4");
				 if(null!=previewExeVo.getRes()&&!"".equals(contenttype)){
					 stream=resExeServiceImpl.getResAsByte(previewExeVo, response);
				 }
				// response.setContentType(contenttype);
				 //boolean  isInline  =   false; 
				// String inlineType  =  isInline?"inline":"attachment"; //  是否内联附件
				// response.setHeader("Content-Disposition" ,inlineType + ";filename=\"" +name + "\""); 
				 response.setContentType("video/mp4");
			     stream.flush();
			     stream.close();	
			}catch (Exception e) {
				e.printStackTrace();
			}
			  logger.debug("getImage("+previewExeVo+")"+"--------- end");
	  }
	@RequestMapping("/resdownOld") 
	public String getResdown(HttpServletRequest request,HttpServletResponse response){
		return this.getRes(request, response);
		 /* PreviewResVo previewExeVo=new PreviewResVo();
		  String url = request.getParameter("url");
		  previewExeVo.setRes(url);
		  logger.debug("getRes("+previewExeVo+")"+"--------- begin");
			try{
				 String imageSuffix=url.substring(url.lastIndexOf(".")+1).toLowerCase();
				 String name = url.substring(url.lastIndexOf("/")+1).toLowerCase();
				 //String contenttype=imageSuffix + "/jpg";
				 String contenttype= imageType(previewExeVo.getRes());
				 OutputStream stream = null ;
				 if(null!=previewExeVo.getRes()&&!"".equals(contenttype)){
					 stream=resExeServiceImpl.getResAsByte(previewExeVo, response);
				 }
				 //response.setContentType(contenttype);
				 boolean  isInline  =   true; 
				 String inlineType  =  isInline?"inline":"attachment"; //  是否内联附件
				 response.setHeader("Content-Disposition" ,inlineType + ";filename=\"" +name + "\""); 
				 response.setContentType("application/x-msdownload");
			     stream.flush();
			     stream.close();	
			}catch (Exception e) {
				e.printStackTrace();
			}*/
			 // logger.debug("getImage("+previewExeVo+")"+"--------- end");
	  }
	
	 private String cvtToMp3(InputStream is,String name) {  
		 //先将流写文件
		
		 String url = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		 url = url.replace("/WEB-INF/classes/", "/temp/");
		 String amrPath = url + name;
		 String mp3Path = url + name + ".mp3";
		 try{
		 OutputStream fos = new FileOutputStream(amrPath);
		// 将输入流is写入文件输出流fos中   
		    int ch = 0;  
		    try {
		        while((ch=is.read()) != -1){
		            fos.write(ch);  
		        }  
		    } catch (IOException e1) {
		        e1.printStackTrace();  
		    } finally{  
		     //关闭输入流等（略）   
		        fos.close();  
		        is.close();  
		    }  
		  //amr转换成mp3
		    changeToMp3Inlinux(amrPath, mp3Path);
		    //
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }

		    return mp3Path;
	 } 
	 
	 public static void changeToMp3Inlinux(String sourcePath, String targetPath) {  
		 try    {        
		 String commands = "ffmpeg -y -i " + sourcePath + " "+ targetPath + " ";
		 System.out.print(commands);
		 Process process = Runtime.getRuntime().exec (commands);
		 }//end try
		 catch (java.io.IOException e){
			 System.err.println ("IOException " + e.getMessage());
	    } 
	 }
	 
	 public static void changeToMp3(String sourcePath, String targetPath) {  
	        File source = new File(sourcePath);  
	        File target = new File(targetPath);  
	        AudioAttributes audio = new AudioAttributes();  
	        Encoder encoder = new Encoder();  
	        audio.setCodec("libmp3lame");  
	        EncodingAttributes attrs = new EncodingAttributes();  
	        attrs.setFormat("mp3");  
	        attrs.setAudioAttributes(audio);  
	  
	        try {  
	            encoder.encode(source, target, attrs);  
	        } catch (IllegalArgumentException e) {  
	            e.printStackTrace();  
	        } catch (InputFormatException e) {  
	            e.printStackTrace();  
	        } catch (EncoderException e) {  
	            e.printStackTrace();  
	        }  
	    } 
	 
	@RequestMapping("/resOld") 
	   public void getResOld(HttpServletRequest request,HttpServletResponse response){
		  PreviewResVo previewExeVo=new PreviewResVo();
		  String url = request.getParameter("url");
		  previewExeVo.setRes(url);
		  logger.debug("getRes("+previewExeVo+")"+"--------- begin");
			try{
				 String imageSuffix=url.substring(url.lastIndexOf(".")+1).toLowerCase();
				 //String contenttype=imageSuffix + "/jpg";
				 String contenttype= imageType(previewExeVo.getRes());
				 OutputStream stream = null ;
				 if(null!=previewExeVo.getRes()&&!"".equals(contenttype)){
					 stream=resExeServiceImpl.getResAsByte(previewExeVo, response);
				 }
				 response.setContentType(contenttype);
			     stream.flush();
			     stream.close();	
			}catch (Exception e) {
				e.printStackTrace();
			}
			  logger.debug("getImage("+previewExeVo+")"+"--------- end");
	  }
	
	@RequestMapping("/res") 
	   public String getRes(HttpServletRequest request,HttpServletResponse response){
		PreviewResVo previewExeVo=new PreviewResVo();
		  String url = request.getParameter("url");
		  previewExeVo.setRes(url);
		  logger.debug("getRes("+previewExeVo+")"+"--------- begin");
		  String imageSuffix=url.substring(url.lastIndexOf(".")+1).toLowerCase();
		 String name = url.substring(url.lastIndexOf("/")+1).toLowerCase();
		 String mp4Path = null;
				 
				 //String contenttype=imageSuffix + "/jpg";
				 String contenttype= imageType(previewExeVo.getRes());
				 Boolean isSucc = null;
				 
				 String tmpUrl = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
				 tmpUrl = tmpUrl.replace("/WEB-INF/classes/", "/temp/");
				 mp4Path = tmpUrl + name;
				 File mp4PathFile = new File(mp4Path);
				 //if(FileUtils.)
				 if(!mp4PathFile.exists()){
					// mp4PathFile.
					 if(null!=previewExeVo.getRes()&&!"".equals(contenttype)){
						 try {
							 if(name.endsWith(".zip")){
								 //压缩文件下载
								 isSucc=resExeServiceImpl.downZipFile2Local(previewExeVo, mp4Path);
							 }else{
								 isSucc=resExeServiceImpl.downFile2Local(previewExeVo, mp4Path);
							 }
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
				 }
			 logger.info("../temp/" + name);
			 System.out.println("../temp/" + name);
			  logger.debug("getImage("+previewExeVo+")"+"--------- end");
		 return "forward:../temp/" + name;
	  }
	
  private String imageType(String imagPath){
	 if(null==imagPath||"".equals(imagPath)){
		 return "";
	 }
	 String imageSuffix=imagPath.substring(imagPath.lastIndexOf(".")+1).toLowerCase();
	 if("jpeg".equalsIgnoreCase(imageSuffix)){
		 return "image/jpeg";
	 }
	 if("jpg".equalsIgnoreCase(imageSuffix)){
		 return "image/jpg";
	 }
	 if("gif".equalsIgnoreCase(imageSuffix)){
		 return "image/gif";
	 }
	 if("png".equalsIgnoreCase(imageSuffix)){
		 return "image/png";
	 }
	 if("avi".equalsIgnoreCase(imageSuffix)){
		 return "video/x-msvideo";
	 }
	 
	/* if("acc".equalsIgnoreCase(imageSuffix)){
		 return "video/x-msvideo";
	 }*/
	 if("mp3".equalsIgnoreCase(imageSuffix)){
		 return "audio/x-mpeg";
	 }
	 if("mp4".equalsIgnoreCase(imageSuffix)){
		 return "video/mp4";
	 }
	 if("wav".equalsIgnoreCase(imageSuffix)){
		 return "audio/x-wav";
	 }
	 if("swf".equalsIgnoreCase(imageSuffix)){
		 return "application/x-shockwave-flash";
	 }
	 if("zip".equalsIgnoreCase(imageSuffix)){
		 return "application/zip";
	 }
	return "application/x-msdownload";
	
  }
@RequestMapping("/resdown") 
  public void loadMobileClient(HttpServletRequest request, HttpServletResponse response){
	  PreviewResVo previewExeVo=new PreviewResVo();
	  String url = request.getParameter("url");
	  previewExeVo.setRes(url);
	  logger.debug("getRes("+previewExeVo+")"+"--------- begin");
		try{
			 String imageSuffix=url.substring(url.lastIndexOf(".")+1).toLowerCase();
			 String name = url.substring(url.lastIndexOf("/")+1).toLowerCase();
			 //String contenttype=imageSuffix + "/jpg";
			 String contenttype= imageType(previewExeVo.getRes());
			 InputStream inputStream = null;
			 if(null!=previewExeVo.getRes()&&!"".equals(contenttype)){
				 inputStream=resExeServiceImpl.getInputResAsByte(previewExeVo);
			 }
			request.setCharacterEncoding("utf-8");
	        response.setCharacterEncoding("UTF-8");
	        //写明要下载的文件的大小 
	        response.setContentLength((int)inputStream.available());         
	        response.reset();
	        response.setContentType("application/octet-stream");
	        String agent = (String)request.getHeader("USER-AGENT");     
	        if(agent != null && agent.indexOf("MSIE") == -1) {
	         // FF           
				  String enableFileName = "";
				   try {
				    enableFileName = new String(name.getBytes("UTF-8"),"ISO-8859-1");
				   } catch (UnsupportedEncodingException e) {
				    e.printStackTrace();
				   }        
	         response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);    
	        } else {
	            try {
			        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(name, "UTF-8"));
			       } catch (UnsupportedEncodingException e1) {
			        e1.printStackTrace();
			       }
	        }
	        response.setHeader("Connection", "close");
	        try {
				    
	            BufferedInputStream buff=new BufferedInputStream(inputStream); 
	            byte [] b=new byte[1024];//
	            long k=0;            
	 
	            OutputStream myout=response.getOutputStream(); 
	            while(k<inputStream.available()){ 
	                int j=buff.read(b,0,1024); 
	                k+=j; 
	                myout.write(b,0,j); 
	            } 
			   myout.flush();
			  } catch (IOException e) {
			   e.printStackTrace();
			  }   
		}catch(Exception e ){
	  e.printStackTrace();
	  }
		
  }


  
}
