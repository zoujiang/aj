package com.frame.ifpr.action;
import java.io.BufferedReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.frame.core.util.SystemConfig;
import com.frame.core.vo.ResultVo;
import com.frame.ifpr.service.FileUploadService;
import com.frame.ifpr.util.JsonUtil;
import com.frame.ifpr.vo.ErrorVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Controller
public class FileUploadAction implements ApplicationContextAware{
   private ApplicationContext ac;
   protected Log logger=LogFactory.getLog(FileUploadAction.class);
   
   private FileUploadService fileUploadServiceImpl;
   
   @RequestMapping("/fileUpload") 
   @ResponseBody
   public String fileUpload(String jsonParam,  MultipartFile file,HttpServletRequest request,HttpServletResponse response){
	   logger.debug("fileUpload---Action:"+jsonParam+"--------------------start");
	   String sysId = SystemConfig.getValue("system.id");
		Object jsonResq=null;
		String jsonReq=null;
		StringWriter sw=new StringWriter();
		try{
			if(!StringUtils.isEmpty(jsonParam)){
				jsonReq=jsonParam;
			}
			JSONObject jsonObject= null;
			String serviceName = null;
			//如果参数为空返回错误信息
			if(jsonReq==null||"".equals(jsonReq)){
				/* final String mesg ="jsonParam参数不能为空！";
				 ErrorVo errorVo=buildErrorVo(mesg,"100098");
				 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
				 logger.debug("fileUpload---Action:"+mesg);
				 return jsonResq.toString(); */
				//fileUploadServiceImpl= (FileUploadService) ac.getBean(serviceName);
				serviceName = sysId + "_file_upload_req"; 
			}else{
				jsonObject=JSONObject.fromObject(jsonReq);
				 serviceName=(String) jsonObject.get("serviceName");
			}
			 
			 if(serviceName.startsWith(sysId) && serviceName.endsWith("_req")){
				 serviceName=serviceName.substring(serviceName.indexOf(sysId)+sysId.length() + 1, serviceName.indexOf("_req"));
				 serviceName=toUtil(serviceName);
				 fileUploadServiceImpl= (FileUploadService) ac.getBean(serviceName);
				 jsonResq=JsonUtil.getJsonString4JavaPOJO((fileUploadServiceImpl.fileUpload(jsonObject, (CommonsMultipartFile) file)));
			 }else{
				 final String mesg ="不存在服务名为"+serviceName+"的服务！";
				 ErrorVo errorVo=buildErrorVo(mesg,ErrorVo.ERROR);
				 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
				 logger.debug("fileUpload---Action:"+mesg);
				 return (String) jsonResq;
			 }
		
		}catch(Exception e){
			 logger.error("fileUpload---Action:"+e.getMessage());
			 ErrorVo errorVo=buildErrorVo(e.getMessage(),ErrorVo.ERROR);
			 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
			 return (String) jsonResq;
		}
	logger.info("fileUpload---Action:"+jsonParam+"--------------------end");
	return jsonResq.toString();   
   }
   
   /**
    * use  for kindeditor 
    * @param jsonParam
    * @param file
    * @param request
    * @param response
    * @return
    */
   @RequestMapping("/fileUploadEditor") 
   @ResponseBody
   public String fileUploadEditor(String jsonParam,  HttpServletRequest request,HttpServletResponse response){
	   logger.debug("fileUpload---Action:"+jsonParam+"--------------------start");
		Object jsonResq=null;
		String jsonReq=null;
		StringWriter sw=new StringWriter();
		try{
			if(!StringUtils.isEmpty(jsonParam)){
				jsonReq=jsonParam;
			}else{
				BufferedReader br=request.getReader();
				String str=null;
				while((str=br.readLine())!=null){
					sw.write(str);
			  }	
			}
			//如果参数为空返回错误信息
			if(jsonReq==null||"".equals(jsonReq)){
				 final String mesg ="jsonParam参数不能为空！";
				 ErrorVo errorVo=buildErrorVo(mesg,"100098");
				 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
				 logger.debug("fileUpload---Action:"+mesg);
				 return jsonResq.toString(); 
			}
			 JSONObject jsonObject=JSONObject.fromObject(jsonReq);
			 String serviceName=(String) jsonObject.get("serviceName");
			 if(serviceName.startsWith("ams_") && serviceName.endsWith("_req")){
				 serviceName=serviceName.substring(serviceName.indexOf("ams_")+4, serviceName.indexOf("_req"));
				 serviceName=toUtil(serviceName);
				 fileUploadServiceImpl= (FileUploadService) ac.getBean(serviceName);
				 DefaultMultipartHttpServletRequest mrequest= (DefaultMultipartHttpServletRequest)request;  
				 Map map=mrequest.getFileMap();  
			     Collection<MultipartFile> collection = map.values();  
			     Iterator it = collection.iterator();  
			     while (it.hasNext()) {  
			         CommonsMultipartFile file=(CommonsMultipartFile) it.next(); 
			    	 jsonResq=JsonUtil.getJsonString4JavaPOJO((fileUploadServiceImpl.fileUpload(jsonObject, (CommonsMultipartFile) file)));
			     }
			 }else{
				 final String mesg ="不存在服务名为"+serviceName+"的服务！";
				 ErrorVo errorVo=buildErrorVo(mesg,ErrorVo.ERROR);
				 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
				 logger.debug("fileUpload---Action:"+mesg);
				 return (String) jsonResq;
			 }
		
		}catch(Exception e){
			 logger.error("fileUpload---Action:"+e.getMessage());
			 ErrorVo errorVo=buildErrorVo(e.getMessage(),ErrorVo.ERROR);
			 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
			 return (String) jsonResq;
		}
	logger.debug("fileUpload---Action:"+jsonParam+"--------------------end");
	
    JSONObject obj0=JSONObject.fromObject(jsonResq);
    JSONObject obj1=obj0.optJSONObject("result");
    JSONObject obj = new JSONObject();  
    if(obj1!=null){
    	obj.put("error", 0);  
        obj.put("url", obj1.optString("picPath")+"&imageName=img");  
    }else {
    	obj.put("error", 1); 
	}
	return obj.toString();
   }
 
	private String toUtil(String key){
		logger.debug("toUtil"+key+"--------------------start");
		StringBuffer tmpSb=new StringBuffer();
		for(int i = 0; i < key.length(); i ++){
			char curChar = key.charAt(i);
			if(curChar == '_'){
				i ++;
				curChar = key.charAt(i);
				tmpSb.append(Character.toUpperCase(curChar));
			}else{
				tmpSb.append(curChar);
			}
		}
		logger.debug("toUtil"+key+"--------------------end");
		return tmpSb.toString()+"Service";
	}
	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.ac=ac;
		
	}

	/*
	 * 封装的错误信息
	 */
	private ErrorVo buildErrorVo(String errorMesg,String errorCode){
		logger.debug("buildErrorVo("+errorMesg+") - start");
		 ErrorVo errorVo = new ErrorVo();
		 errorVo.setReturnCode(errorCode);
		 errorVo.setServiceName("ams_ file_upload_resp");
		 errorVo.setErrorMsg(errorMesg);
		 errorVo.setResult(new ResultVo());
		 logger.debug("buildErrorVo("+errorMesg+") - end");
		 return errorVo;
	 }
	
}