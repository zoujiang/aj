package com.frame.core.vo;

/**
 * 请求参数封装VO
 * @author lishun
 *
 */
public class AHeadVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1284967812084875889L;
	/** 服务名 */
	protected String serviceName;
	/** 调用平台 */
	protected String callType;
	/** 参数*/
	
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
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
