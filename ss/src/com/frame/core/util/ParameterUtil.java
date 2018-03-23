package com.frame.core.util;

import org.apache.log4j.Logger;

public class ParameterUtil {
	private static Logger logger = Logger.getLogger(ParameterUtil.class);

	/**
	 * 检查访问类型是否有误
	 * @param callType
	 * @return 类型正确返回true
	 */
	public static boolean checkCallType(String callType) {
		return "001".equals(callType) || "002".equals(callType);
	}
	
	
	/**
	 * 批量检查必填项,如果为空返回信息
	 * @param requiredParams 
	 *  如：
		String[] requiredParams = new String[]{"userId","buyerPhone"};
	 * @param body  paramVO
	 * @return errorMsg
	 */
	public static String checkRequiredParams(String[] requiredParams, Object obj) {
		Class<?> objClass = obj.getClass(); 
		if (obj == null) {
			return "参数为空";
		}
		
		for (String name : requiredParams) {
			 String getMethodName = "get" + toFirstLetterUpperCase(name); 
			 try{  
                 Object value = objClass.getMethod(getMethodName).invoke(obj);  
                 if (value == null || StringUtil.isEmpty(value.toString())) {
                	 return name + "不能为空";
                 }
                 
                 if ("pageSize".equals(name) || "currentPage".equals(name)) {
                	int intVal = 0;
             		try {
             			intVal = Integer.parseInt((String)value);
             		} catch (Exception e) {
             			return "分页信息有误" + name + "：" + value.toString();
             		}
             		
             		if (intVal < 1) {
             			return "分页值有误" + name + "：" + value.toString();
             		}
             		
                 }
                 
             }catch(Exception e){  
             }  
		}
		
		return null;
	}
	
	public static String toFirstLetterUpperCase(String str) {  
	    if(str == null || str.length() < 1){  
	        return str;  
	    }  
	    String firstLetter = str.substring(0, 1).toUpperCase();  
	    if (str.length() == 1) {
	    	return firstLetter;
	    }
	     
	    return firstLetter + str.substring(1, str.length());  
	 }  
	
	

}
