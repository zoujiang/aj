package com.frame.core.action;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.core.service.ImageExeService;
import com.frame.core.vo.PreviewImageVo;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Controller
public class ImageExeAction {
	@Resource
	private ImageExeService imageExeServiceImpl;
	protected Log logger=LogFactory.getLog(ImageExeAction.class);
	@RequestMapping("/img") 
   public void getImage(HttpServletRequest request,HttpServletResponse response){
	  PreviewImageVo previewImageVo=new PreviewImageVo();
	  previewImageVo.setImg(request.getParameter("img"));
	  if(request.getParameter("viewType") == null || "".equals(request.getParameter("viewType"))){
		  previewImageVo.setViewType("1");
	  }else{
		  previewImageVo.setViewType(request.getParameter("viewType"));
	  }
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
	 if("mp3".equalsIgnoreCase(imageSuffix)){
		 return "audio/x-mpeg";
	 }
	 if("mp4".equalsIgnoreCase(imageSuffix)){
		 return "video/mp4";
	 }
	 
	return "";
	
  }
}
