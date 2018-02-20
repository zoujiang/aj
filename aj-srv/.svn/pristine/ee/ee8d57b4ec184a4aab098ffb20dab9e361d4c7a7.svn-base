package com.frame.ifpr.vo;

import java.io.Serializable;

import com.frame.core.vo.ResultVo;
/**
 * 输出响应数据
 * @author lishun
 *
 */
public class ResponseVo implements Serializable{

	private static final long serialVersionUID = -8763378084324796817L;
	public static final String SUCCESS="000000";
	public static final String ERROR="100001";
	public static final String SYS_ERROR="500";
	public static final String TOKENERROR="100009";
	
	
	/** 响应服务名 */
	private String serviceName;
	/** 000000成功  100001 失败*/
	private String returnCode;
	/** 响应参数封装VO*/
	private ResultVo result;
	private String errorMsg;
	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public ResultVo getResult() {
		return result;
	}

	public void setResult(ResultVo result) {
		this.result = result;
	}
	
	
	
}
