package com.frame.ifpr.action;

import java.io.BufferedReader;
import java.io.StringWriter;
import java.util.Date;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.service.base.Context;
import com.frame.ifpr.util.DesBase64Tool;
import com.frame.ifpr.util.HttpClientUtils;
import com.frame.ifpr.util.JsonUtil;
import com.frame.ifpr.vo.ErrorVo;
import com.frame.ifpr.vo.ResponseVo;

/**
 * 对外发布服务的接口
 * @author lishun
 *
 */
@Controller
public class IFPRAction implements ApplicationContextAware{
	private Log log=LogFactory.getLog(IFPRAction.class);
	private ApplicationContext ac;
	/**
	 * 支持POST表单，数据key=jsonRequest的请求
	 * 也支持流形式的处理请求
	 * @param jsonRequest
	 * @param request
	 * @return
	 */
	@RequestMapping("/{servicename}") 
	@ResponseBody
	public String doExecute(@PathVariable String servicename,String jsonRequest,HttpServletRequest request) {
		log.debug("doExecute("+servicename+","+jsonRequest+") - start");
		PublishService publishService=(PublishService) ac.getBean(servicename);
		Object jsonResq=null;
		StringWriter sw=new StringWriter();
		try{
			if(!StringUtils.isEmpty(jsonRequest)){
				jsonResq=publishService.publishService(jsonRequest);
			}else{
				BufferedReader br=request.getReader();
				String str=null;
				while((str=br.readLine())!=null){
					sw.write(str);
				}
				jsonResq=publishService.publishService(sw.getBuffer().toString());
			}
		}catch(Exception e){
			log.error("" , e);
		}
		log.debug("doExecute("+servicename+","+jsonRequest+") - end");
		return (String) jsonResq;
	}
	@RequestMapping("/proxy") 
	@ResponseBody
	public String doExecute(String jsonRequest,HttpServletRequest request,HttpServletResponse response) {
		log.info("doExecute("+jsonRequest+") - start"+new Date());
		String systemId = SystemConfig.getValue("system.id")  + "_";
		Object jsonResq=null;
		String jsonReq=null;
		StringWriter sw=new StringWriter();
		try{
			if(!StringUtils.isEmpty(jsonRequest)){
				jsonReq=jsonRequest;
			}else{
				BufferedReader br=request.getReader();
				String str=null;
				while((str=br.readLine())!=null){
					sw.write(str);
				}
				jsonReq=sw.getBuffer().toString();
			}
			 
			 JSONObject jsonObject=null; 
			 Boolean encryptDES=false;
			 try {
				jsonObject=JSONObject.fromObject(jsonReq);
				log.info("doExecute("+jsonObject.toString()+") - start"+new Date());
			} catch (Exception e) {
				log.info("信息已经加密,需先解密");
				encryptDES=true;
				jsonObject=JSONObject.fromObject(DesBase64Tool.decryptDES(jsonReq,SystemConfig.getValue("mkt.des.key")));
				jsonReq=JsonUtil.getJsonString4JavaPOJO(jsonObject);	
			} 
			//获取分页属性
			 String pageSize=request.getParameter("pageSize");
			 String currentPage=request.getParameter("currentPage");
			 String t=request.getParameter("t");
			 String v=request.getParameter("v");
			 if(!StringUtils.isEmpty(pageSize)){
				 jsonReq=jsonReq.replaceAll("\"pageSize\"\\s*:\\s*\"\\d+\"", "\"pageSize\":\""+pageSize+"\"");
			 }
			 if(!StringUtils.isEmpty(currentPage)){
				 jsonReq=jsonReq.replaceAll("\"currentPage\"\\s*:\\s*\"\\d+\"", "\"currentPage\":\""+currentPage+"\"");
			 }
			 
			 String serviceName=(String) jsonObject.get("serviceName");
			 if( "1".equals(SystemConfig.getValue("system.encryptdes.enable")) 
					 && (SystemConfig.getValue("system.encryptdes.escape").indexOf(serviceName) < 0 && encryptDES==false)){
				 ErrorVo errorVo=new ErrorVo();
				 errorVo.setReturnCode(ResponseVo.TOKENERROR);
				 errorVo.setErrorMsg(SystemConfig.getValue("msg.system.encryptdes.notmatch"));
				 errorVo.setServiceName(serviceName);
				 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
				 return (String) jsonResq;
			 }
			 Boolean b=true;
			 if("1".equals(SystemConfig.getValue("ifpr.tokenControl.tv.enableTest"))){
				 t = SystemConfig.getValue("ifpr.tokenControl.tv.testt");
				 v = SystemConfig.getValue("ifpr.tokenControl.tv.testv");
			 }
			 if(!"0".equals(SystemConfig.getValue("ifpr.tokenControl.tag"))){
				 b=tokenValidate(jsonObject, serviceName,t);//验证token
			}
			
			 if(b){
			 if(serviceName.startsWith(systemId)){
				 serviceName=serviceName.substring(serviceName.indexOf(systemId)+systemId.length(), serviceName.length());
				 serviceName=toUtil(serviceName);
				 PublishService publishService=(PublishService) ac.getBean(serviceName);
				/* ///PublishService.token = t;
				 //PublishService.version = v;
				 Context context = new Context();
				 context.setUserToken(t);
				 context.setClientVersion(v);
				 publishService.setContext(context);*/
				 jsonResq=publishService.publishService(jsonReq);
			 }else{
				 ErrorVo errorVo=new ErrorVo();
				 errorVo.setReturnCode(ResponseVo.SYS_ERROR);
				 errorVo.setErrorMsg("服务器开小差,请稍候再试");
				 errorVo.setServiceName(serviceName);
				 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
			 }
			 }else{
				 ErrorVo errorVo=new ErrorVo();
				 errorVo.setReturnCode(ResponseVo.TOKENERROR);
				 errorVo.setErrorMsg("用户未登录");
				 errorVo.setServiceName(serviceName);
				 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);	 
				 
			 }
		}catch(Exception e){
			 ErrorVo errorVo=new ErrorVo();
			 errorVo.setReturnCode(ResponseVo.SYS_ERROR);
			 errorVo.setErrorMsg(e.getMessage());
			 errorVo.setServiceName("服务器开小差,请稍候再试");
			 jsonResq=JsonUtil.getJsonString4JavaPOJO(errorVo);
			 log.error("" , e);
			 return (String) jsonResq;
		}
		
		log.info("doExecute("+jsonResq+") - end"+new Date());
		return (String) jsonResq;
	}
	public Boolean tokenValidate(JSONObject jsonObject, String serviceName,String t) {
		if(SystemConfig.getValue("ifpr.tokenControl.ifpr.escape").indexOf( serviceName) > -1){
			return true;
		}
		Object jsonResq;
		String params=jsonObject.getString("params");
		JSONObject p=JSONObject.fromObject(params);
		if(!p.containsKey("userId")){
			return true;
		}
		String userId=p.getString("userId");
		if(p.containsKey("smsValidateToken")){
			String smsValidateToken=p.getString("smsValidateToken");
			if(StringUtil.isNotEmpty(smsValidateToken))return true;
		}
		String aamUrl = SystemConfig.getValue("sag.aam.rpc.url") + "?token=" + t + "&userId=" + userId;
		String msg = HttpClientUtils.invokePostService(aamUrl,
				"", "UTF-8", 10000);
		try{
			if(Boolean.parseBoolean(msg.trim())){
				return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			return false;
		}
		    
	}
	
	/*
	public Boolean tokenValidate(JSONObject jsonObject, String serviceName,String t) {
		if(SystemConfig.getValue("ifpr.tokenControl.ifpr.escape").indexOf( serviceName) > -1){
			return true;
		}
		String params=jsonObject.getString("params");
		JSONObject p=JSONObject.fromObject(params);
		if(!p.containsKey("userCode")){
			return true;
		}
		String userCode=p.getString("userCode");
		
		
		return cusServiceImpl.checkToken(t, userCode);
	}
	
	*/
	
	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.ac=ac;
	}
	private String toUtil(String key){
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
		return tmpSb.toString();
	}
}
