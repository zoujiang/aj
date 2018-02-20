package com.frame.core.vo;

public class MsgVo {
	public static final String CODE_SUCCESS = "0000";
	public static final String CODE_ERROR_PS = "0001"; //参数出错
	public static final String CODE_ERROR = "0003"; //出错
	private String code;
	private String msg;
	private boolean success;
	private Object obj;
	
	public MsgVo() {
		super();
		success = true;
		code = CODE_SUCCESS;
	}
	
	public MsgVo(boolean success) {
		super();
		this.success = success;
		if (success) {
			code = CODE_SUCCESS;
		} else {
			code = CODE_ERROR;
		}
	}
	public MsgVo(String msg) {
		super();
		this.msg = msg;
		this.success = false;
	}
	
	public MsgVo(boolean success, String msg) {
		super();
		this.msg = msg;
		this.success = success;
		if (success) {
			code = CODE_SUCCESS;
		} else {
			code = CODE_ERROR;
		}
	}

	public MsgVo(String code, String msg) {
		super();
		this.msg = msg;
		this.code = code;
		if (CODE_SUCCESS.equals(code)) {
			success = true;
		} else {
			success = false;
		}
	}

	public String getCode() {
		if (code == null && success) {
			code = CODE_SUCCESS;
		} else if (code == null && !success) {
			code = CODE_ERROR;
		}
		return code;
	}

	public void setCode(String code) {
		if (CODE_SUCCESS.equals(code)) {
			success = true;
		} else {
			success = false;
		}
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}
	public boolean isError() {
		return !success;
	}

	public void setSuccess(boolean success) {
		if (success) {
			code = CODE_SUCCESS;
		}
		this.success = success;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	
}
