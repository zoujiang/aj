package com.frame.core.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.frame.ifpr.exception.BaseException;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class ServiceException extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7015020592773793894L;

	private Log logger = LogFactory.getLog(ServiceException.class);

	public ServiceException() {
		super();
	}

	public ServiceException(String errMessage, Throwable e) {
		super(errMessage, e);
		logger.error(errMessage);
	}

	public ServiceException(String errMessage) {
		super(errMessage);
		logger.error(errMessage);
	}

	public ServiceException(Throwable errMessage) {
		super(errMessage);
	}
	
}
