package com.frame.ifpr.common.parser.impl;


import java.lang.reflect.Method;

import net.sf.json.JSONObject;

import com.frame.core.util.StringUtil;
import com.frame.ifpr.common.parser.ParserAndWraper;
import com.frame.ifpr.exception.common.ParseException;
import com.frame.ifpr.util.JsonUtil;
import com.google.gson.Gson;
/**
 * 参数解析JSON解析实现类
 * 如果params参数中还嵌有json对象时，使用此方法
 * 如下面params还有JSONArray：
 * 
{
"serviceName":"tis_order_cmt",
"callType":"001",
"params": {
"userId":"15800299434",
"orderId":"15800299434",
"prodList":[{"id":"id","score":1, "desc":"质量很好","picUrl":"http://a.jpg;http://b.jpg", "anonymous":"1"}]
}
}
 * @author wangch
 *
 * @param outClass	封装解析数据对应的Java实体类，如：RequestVo
 * @param innerClass	封装解析数据对应的Java实体类，如：OrderCreateParamVo
 */
public class GsonParserAndWraper implements ParserAndWraper {
	private String outClass;
	private String innerClass;
	
	
	public Object doParse(Object obj) throws ParseException {
		Object outClassvo = null;
		Object innerClassVo = null;
		Gson gson = new Gson();
		if(obj instanceof String){
			String jsonStr=(String)obj;
			try {
				
				//outClassvo=Class.forName(outClass).newInstance();
				
				outClassvo = gson.fromJson(jsonStr, Class.forName(outClass));
				
				JSONObject json = JSONObject.fromObject(jsonStr);
				
				if (innerClass == null) {
					return outClassvo;
				}
				
				if (StringUtil.isNotEmpty(json.optString("params"))) {
					innerClassVo = gson.fromJson(json.optString("params"), Class.forName(innerClass));
					
					Method[] methods = outClassvo.getClass().getMethods();  
			        for(Method method:methods){  
			            if("setParams".equals(method.getName())){  
			                method.invoke(outClassvo, innerClassVo);  
			            }  
			        } 
				}
				
				return outClassvo;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ParseException("解析异常：JSON字符串解析出错",e);
			} 
			
		}else{
			throw new ParseException("解析异常：不支持"+obj.getClass()+"类型对象的解析");
		}
	}

	public Object doWrap(Object obj){
			String jsonString=null;
			jsonString=JsonUtil.getJsonString4JavaPOJO(obj);
			return jsonString;
	}

	public String getOutClass() {
		return outClass;
	}

	public void setOutClass(String outClass) {
		this.outClass = outClass;
	}

	public String getInnerClass() {
		return innerClass;
	}

	public void setInnerClass(String innerClass) {
		this.innerClass = innerClass;
	}
	
}
