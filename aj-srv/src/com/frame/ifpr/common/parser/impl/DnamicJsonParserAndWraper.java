package com.frame.ifpr.common.parser.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.frame.ifpr.common.parser.ParserAndWraper;
import com.frame.ifpr.exception.common.ParseException;
import com.frame.ifpr.util.JsonUtil;

public class DnamicJsonParserAndWraper implements ParserAndWraper {
	private String outClass;
	private String innerClass;
	
	@Override
	public Object doParse(Object obj) throws ParseException {
		Object outClassvo=null;
		if(obj instanceof String){
			String jsonStr=(String)obj;
			try {
				outClassvo=Class.forName(outClass).newInstance();
				Map<String,Object> newrequestMap=new HashMap<String, Object>();
				Map<String,Object> requestMap =JsonUtil.getMap4Json(jsonStr);
				Iterator<String> iterRequestParamsKeys=requestMap.keySet().iterator();
				
				while(iterRequestParamsKeys.hasNext()){
					String key=iterRequestParamsKeys.next();
					if(!"params".equals(key)){
						newrequestMap.put(key, requestMap.get(key));
					}
				}
				String paramsJson=requestMap.get("params").toString();
				Map<String,Object> ManageParamVo=new HashMap<String, Object>();
				if(innerClass!=null){
					ManageParamVo.put("params", paramsJson);
					Object innerClassVo=Class.forName(innerClass).newInstance();
					BeanUtils.copyProperties(innerClassVo, ManageParamVo);
					newrequestMap.put("params", innerClassVo);
				}
				BeanUtils.copyProperties(outClassvo,newrequestMap);
				return outClassvo;
			} catch (Exception e) {
				throw new ParseException("解析异常：JSON字符串解析出错",e);
			} 
			
		}else{
			throw new ParseException("解析异常：不支持"+obj.getClass()+"类型对象的解析");
		}
		
	}

	@Override
	public Object doWrap(Object obj) {
		// TODO Auto-generated method stub
		return null;
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
