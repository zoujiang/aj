package com.frame.ifpr.exception.common;

import com.frame.ifpr.exception.CommonException;

/**
 * 客户端认证异常
 * 
 * @author lishun
 * 
 */
public class AuthorException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthorException() {
		super();
	}

	public AuthorException(String errMessage, Throwable e) {
		super(errMessage, e);
	}

	public AuthorException(String errMessage) {
		super(errMessage);
	}

	public AuthorException(Throwable errMessage) {
		super(errMessage);
		// TODO Auto-generated constructor stub
	}

}
