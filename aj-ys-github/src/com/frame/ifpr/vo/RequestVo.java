package com.frame.ifpr.vo;

import java.io.Serializable;

import com.frame.core.vo.ParamsVo;

public class RequestVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1284967812084875888L;
	/** 服务名 */
	protected String serviceName;
	/** 调用平台 */
	protected String callType;
	/** 参数*/
	protected ParamsVo params;
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public ParamsVo getParams() {
		return params;
	}

	public void setParams(ParamsVo params) {
		this.params = params;
	}
	
	
}
