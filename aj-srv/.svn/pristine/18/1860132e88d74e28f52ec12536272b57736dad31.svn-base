package com.frame.ifpr.common.parser.impl;


import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.frame.ifpr.common.parser.ParserAndWraper;
import com.frame.ifpr.exception.common.ParseException;
import com.frame.ifpr.util.JsonUtil;
/**
 * 客户端参数解析JSON解析实现类
 * @author caiwen
 *
 * @param outClass	封装解析数据对应的Java实体类，如：OrderVo
 * @param innerClass	封装解析数据对应的Java实体类，如：OrderItemVo
 */
public class OSCJsonParserAndWraper implements ParserAndWraper {
	private String outClass;
	private String innerClass;
	public Object doParse(Object obj) throws ParseException {
		Object vo=null;
		if(obj instanceof String){
			String jsonStr=(String)obj;
			try {
				vo=Class.forName(outClass).newInstance();
				if(innerClass==null){	
					BeanUtils.copyProperties(vo,JsonUtil.getMap4Json(jsonStr));
				}else{
					Map<String, Object> map=JsonUtil.getMap4JsonByList(jsonStr,Class.forName(innerClass));
					PropertyUtils.copyProperties(vo, map);
				}
				
				return vo;
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

