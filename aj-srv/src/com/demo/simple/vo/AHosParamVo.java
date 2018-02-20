package com.demo.simple.vo;

import com.frame.core.vo.ABodyParamVo;

/**
 * 请求参数封装VO
 * @author lishun
 *
 */
public class AHosParamVo extends ABodyParamVo {
	/**
	 * 
	 */
	/** 服务名 */
	protected String methodId;
	/** 调用参数 */
	protected String hosName;
	protected String hosCode;
	protected String isWithIntro;
	protected String isWithForcePay;
	protected String startIndex;
	protected String orderby;
	protected String isWithDepartment;
	protected String isWithDepIntro;
	protected String source;
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	public String getHosName() {
		return hosName;
	}
	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public String getHosCode() {
		return hosCode;
	}
	public void setHosCode(String hosCode) {
		this.hosCode = hosCode;
	}
	public String getIsWithIntro() {
		return isWithIntro;
	}
	public void setIsWithIntro(String isWithIntro) {
		this.isWithIntro = isWithIntro;
	}
	public String getIsWithForcePay() {
		return isWithForcePay;
	}
	public void setIsWithForcePay(String isWithForcePay) {
		this.isWithForcePay = isWithForcePay;
	}
	public String getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getIsWithDepartment() {
		return isWithDepartment;
	}
	public void setIsWithDepartment(String isWithDepartment) {
		this.isWithDepartment = isWithDepartment;
	}
	public String getIsWithDepIntro() {
		return isWithDepIntro;
	}
	public void setIsWithDepIntro(String isWithDepIntro) {
		this.isWithDepIntro = isWithDepIntro;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
