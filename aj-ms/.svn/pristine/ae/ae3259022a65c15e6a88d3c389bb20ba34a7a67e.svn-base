package com.tist.apply.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TAppInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_app_info")
public class AppInfo implements java.io.Serializable {

	// Fields

	private String appId;
	private String appName;
	private String appDesc;
	private String appKey;
	private String appMsUrl;
	private String appBiUrl;
	private String appCltUrl;
	private Date srvStartDt;
	private Date srvEndDt;
	private String modifyUser;
	private String createUser;
	private Date modifyDt;
	private String isValid = "0";
	private Date createDt;
	private String apvSts = "0";
	private String apvUserId;
	private Date apvDt;
	private String apvReason;
	// Constructors

	/** default constructor */
	public AppInfo() {
	}

	/** minimal constructor */
	public AppInfo(String appId) {
		this.appId = appId;
	}

	/** full constructor */
	public AppInfo(String appId, String appName, String appDesc,
			String appKey, String appMsUrl, String appBiUrl, String appCltUrl,
			Timestamp srvStartDt, Timestamp srvEndDt, String modifyUser,
			String createUser, Timestamp modifyDt, String isValid,
			Timestamp createDt, String apvSts, String apvUserId,
			Timestamp apvDt, String apvReason) {
		this.appId = appId;
		this.appName = appName;
		this.appDesc = appDesc;
		this.appKey = appKey;
		this.appMsUrl = appMsUrl;
		this.appBiUrl = appBiUrl;
		this.appCltUrl = appCltUrl;
		this.srvStartDt = srvStartDt;
		this.srvEndDt = srvEndDt;
		this.modifyUser = modifyUser;
		this.createUser = createUser;
		this.modifyDt = modifyDt;
		this.isValid = isValid;
		this.createDt = createDt;
		this.apvSts = apvSts;
		this.apvUserId = apvUserId;
		this.apvDt = apvDt;
		this.apvReason = apvReason;
	}

	// Property accessors
	@Id
	@Column(name = "APP_ID", unique = true, nullable = false, length = 32)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "APP_NAME", length = 64)
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name = "APP_DESC", length = 512)
	public String getAppDesc() {
		return this.appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	@Column(name = "APP_KEY", length = 64)
	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Column(name = "APP_MS_URL", length = 256)
	public String getAppMsUrl() {
		return this.appMsUrl;
	}

	public void setAppMsUrl(String appMsUrl) {
		this.appMsUrl = appMsUrl;
	}

	@Column(name = "APP_BI_URL", length = 256)
	public String getAppBiUrl() {
		return this.appBiUrl;
	}

	public void setAppBiUrl(String appBiUrl) {
		this.appBiUrl = appBiUrl;
	}

	@Column(name = "APP_CLT_URL", length = 256)
	public String getAppCltUrl() {
		return this.appCltUrl;
	}

	public void setAppCltUrl(String appCltUrl) {
		this.appCltUrl = appCltUrl;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SRV_START_DT", length = 19)
	public Date getSrvStartDt() {
		return this.srvStartDt;
	}

	public void setSrvStartDt(Date srvStartDt) {
		this.srvStartDt = srvStartDt;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SRV_END_DT", length = 19)
	public Date getSrvEndDt() {
		return this.srvEndDt;
	}

	public void setSrvEndDt(Date srvEndDt) {
		this.srvEndDt = srvEndDt;
	}

	@Column(name = "MODIFY_USER", length = 32)
	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	@Column(name = "CREATE_USER", length = 32)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DT", length = 19)
	public Date getModifyDt() {
		return this.modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DT", length = 19)
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	@Column(name = "APV_STS", length = 1)
	public String getApvSts() {
		return this.apvSts;
	}

	public void setApvSts(String apvSts) {
		this.apvSts = apvSts;
	}

	@Column(name = "APV_USER_ID", length = 32)
	public String getApvUserId() {
		return this.apvUserId;
	}

	public void setApvUserId(String apvUserId) {
		this.apvUserId = apvUserId;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APV_DT", length = 0)
	public Date getApvDt() {
		return this.apvDt;
	}

	public void setApvDt(Date apvDt) {
		this.apvDt = apvDt;
	}

	@Column(name = "APV_REASON", length = 32)
	public String getApvReason() {
		return this.apvReason;
	}

	public void setApvReason(String apvReason) {
		this.apvReason = apvReason;
	}

}