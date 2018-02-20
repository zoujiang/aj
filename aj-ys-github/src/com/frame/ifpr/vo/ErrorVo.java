package com.frame.ifpr.vo;

import com.frame.core.vo.ResultVo;

public class ErrorVo extends ResponseVo {
	
	private static final long serialVersionUID = 2871133698876591916L;
	/**
	 * 错误原因，包含子系统出错信息
	 */
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public ResultVo getResult() {
		return new ResultVo();
	}

	

}
