package com.frame.ifpr.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 异常通用处理模块
 * 
 * @author lishun
 * 
 */
public class BaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(BaseException.class);

	public BaseException() {
		super();
	}

	public BaseException(String errMessage, Throwable e) {
		super(errMessage, e);
		logger.error(errMessage);
	}

	public BaseException(String errMessage) {
		super(errMessage);
		logger.error(errMessage);
	}

	public BaseException(Throwable errMessage) {
		super(errMessage);
	}
}
