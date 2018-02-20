package com.frame.ifpr.exception;

/**
 * 接口接入异常
 * 
 * @author lishun
 * 
 */
public class ReceiveException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ReceiveException() {
		super();
	}

	public ReceiveException(String errMessage, Throwable e) {
		super(errMessage, e);
	}

	public ReceiveException(String errMessage) {
		super(errMessage);
	}

	public ReceiveException(Throwable errMessage) {
		super(errMessage);
	}

}
