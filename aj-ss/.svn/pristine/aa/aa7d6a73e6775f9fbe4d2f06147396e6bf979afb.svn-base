package com.frame.ifpr.action;
//package com.mymost.ifpr.action;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.StringWriter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.mymost.ifpr.util.JsonUtil;
//import com.mymost.ifpr.vo.ErrorVo;
//import com.mymost.ifpr.vo.ResponseVo;
//
///**
// * 对外发布服务的接口
// * 
// * @author lishun
// * 
// */
//@Controller
//public class ProxyInterface {
//	/**
//	 * 支持POST表单，数据key=jsonRequest的请求 也支持流形式的处理请求
//	 * 
//	 * @param jsonRequest
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/{servicename}")
//	@ResponseBody
//	public String doExecute(@PathVariable String servicename,
//			String jsonRequest, HttpServletRequest request) {
//		// PublishService publishService=(PublishService)
//		// ac.getBean(servicename);
//		Object jsonResq = null;
//		// StringWriter sw=new StringWriter();
//		// try{
//		// if(!StringUtils.isEmpty(jsonRequest)){
//		// jsonResq=publishService.publishService(jsonRequest);
//		// }else{
//		// BufferedReader br=request.getReader();
//		// String str=null;
//		// while((str=br.readLine())!=null){
//		// sw.write(str);
//		// }
//		// jsonResq=publishService.publishService(sw.getBuffer().toString());
//		// }
//		// }catch(Exception e){
//		// e.printStackTrace();
//		// }
//		return (String) jsonResq;
//	}
//
//	/**
//	 * 共享服务代理
//	 * 
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/proxy")
//	@ResponseBody
//	public String doExecute(HttpServletRequest request,
//			HttpServletResponse response) {
//		StringWriter reqParameter = new StringWriter();
//		// 读取请求数据
//		BufferedReader br = null;
//		try {
//			br = request.getReader();
//			String temp = null;
//			while ((temp = br.readLine()) != null) {
//				reqParameter.append(temp);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			ErrorVo errorVo = new ErrorVo();
//			errorVo.setReturnCode(ResponseVo.ERROR);
//			errorVo.setErrorMsg("数据请求异常");
//			errorVo.setServiceName("");
//			return JsonUtil.getJsonString4JavaPOJO(errorVo);
//		}
//		// 将读取数据转化成JSON数据
//		JSONObject reqJSON = JSONObject.fromObject(reqParameter.toString());
//		String serviceName = (String) reqJSON.get("serviceName");
//		// 判断请求服务名是否存在
//		if (StringUtils.isNotEmpty(serviceName)) {
//			ErrorVo errorVo = new ErrorVo();
//			errorVo.setReturnCode(ResponseVo.ERROR);
//			errorVo.setErrorMsg("请求服务名不存在");
//			errorVo.setServiceName(serviceName);
//			return JsonUtil.getJsonString4JavaPOJO(errorVo);
//		}
//		// 判断请求服务名是否合法ams_act_list_req
//		if (serviceName.startsWith("ams_") && serviceName.endsWith("_req")) {
//			serviceName = serviceName.substring(
//					serviceName.indexOf("ams_") + 4, serviceName
//							.indexOf("_req"));
//			serviceName = toUtil(serviceName) + "Service";
//			return "forward:../" + serviceName + "?parameterData=" + reqJSON;
//		} else {
//			ErrorVo errorVo = new ErrorVo();
//			errorVo.setReturnCode(ResponseVo.ERROR);
//			errorVo.setErrorMsg("请求服务名格式错误");
//			errorVo.setServiceName(serviceName);
//			return JsonUtil.getJsonString4JavaPOJO(errorVo);
//		}
//	}
//
//	/**
//	 * 重组服务名
//	 * 
//	 * @param key
//	 * @return
//	 */
//	private String toUtil(String key) {
//		String[] arr = key.split("_");
//		StringBuffer str = new StringBuffer();
//		if (arr != null && arr.length > 0) {
//			str.append(arr[0]);
//			for (int i = 1; i < arr.length; i++) {
//				str.append(arr[i].substring(0, 1).toUpperCase()
//						+ arr[i].substring(1));
//			}
//		}
//		return str.toString();
//	}
//
//}
