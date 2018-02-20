package com.frame.ifpr.exception.common;

import com.frame.ifpr.exception.CommonException;

/**
 * 参数解析异常
 * 
 * @author lishun
 * 
 */
public class ParseException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParseException() {
		super();
	}

	public ParseException(String errMessage, Throwable e) {
		super(errMessage, e);
	}

	public ParseException(String errMessage) {
		super(errMessage);
	}

	public ParseException(Throwable errMessage) {
		super(errMessage);
	}

}
