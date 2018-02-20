package com.frame.core.exception;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class ValueConvertException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2383783117905504434L;

	public ValueConvertException() {
		// do nothing
	}

	public ValueConvertException(String message) {
		super(message);
	}

	public ValueConvertException(Throwable cause) {
		super(cause);
	}
}
