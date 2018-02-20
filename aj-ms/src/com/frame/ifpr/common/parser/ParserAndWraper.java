package com.frame.ifpr.common.parser;

import com.frame.ifpr.exception.common.ParseException;

/**
 * 客户端参数解析接口定义
 * 
 * @author caiwen
 * 
 */
public interface ParserAndWraper {
	public Object doParse(Object obj) throws ParseException;

	public Object doWrap(Object obj);
}
