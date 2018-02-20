package com.aj.ad.ifpr.vo;

import java.util.Date;

import org.apache.log4j.Logger;

import com.frame.core.vo.PageParamsVo;


//访问日志
public class AdLogParamVo extends PageParamsVo {
	private static Logger logger = Logger.getLogger(AdLogParamVo.class);
	private String userId;	//用户ID(和客户端唯一标志至少需要一个)
	
	private Date time;		//操作时间YYYY-MM-DD HH24:MI:SS
	
	private String callType;	//访问平台类型 001-Android,002-IOS,003-Web,004-Wap
	private String ucode;	//客户端唯一标志COOKIES/IMEI
	private String phoneVer;	//手机型号,如：iPhone Simulator，若获取不到，则为空
	private String phoneSysVer;	//手机操作系统,如：ios5.0,注意，如果安卓系统，必须是android开头，如果ios必须是ios开头，若获取不到，则为空
	private String ip;	//终端IP
	private String ip_net;	//终端IP 服务器取到的IP
	private String areaCode;	//区域编码（切换城市时的编号），默认500000
	private String phoneNum;	//手机号码
	//private LogBackParamBean backParam;
	private String backParam;
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getUcode() {
		return ucode;
	}
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}
	public String getPhoneVer() {
		return phoneVer;
	}
	public void setPhoneVer(String phoneVer) {
		this.phoneVer = phoneVer;
	}
	public String getPhoneSysVer() {
		return phoneSysVer;
	}
	public void setPhoneSysVer(String phoneSysVer) {
		this.phoneSysVer = phoneSysVer;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIp_net() {
		return ip_net;
	}
	public void setIp_net(String ip_net) {
		this.ip_net = ip_net;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getBackParam() {
		return backParam;
	}
	public void setBackParam(String backParam) {
		this.backParam = backParam;
	}
	
	
	
	
	
	

	
	
}
