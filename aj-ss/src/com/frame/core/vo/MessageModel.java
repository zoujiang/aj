package com.frame.core.vo;

public class MessageModel{
	private boolean result;
	private String message;
	private Object returnObj;

	public MessageModel() {
	}

	public MessageModel(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public MessageModel(boolean result, String message, Object returnObj) {
		this.result = result;
		this.message = message;
		this.returnObj = returnObj;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getReturnObj() {
		return returnObj;
	}

	public void setReturnObj(Object returnObj) {
		this.returnObj = returnObj;
	}
	
}
