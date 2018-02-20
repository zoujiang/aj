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
 * TAppSrv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_app_srv")
public class AppSrv implements java.io.Serializable {

	// Fields

	private String srvId;
	private String appId;
	private String srvName;
	private String srvPlace;
	private String srvIp;
	private String srvCfg;
	private Date startDt;
	private Date endDt;
	private String modifyUser;
	private String createUser;
	private Date modifyDt;
	private String isValid;
	private Date createDt;
	private String srvType;

	// Constructors

	/** default constructor */
	public AppSrv() {
	}

	/** minimal constructor */
	public AppSrv(String srvId) {
		this.srvId = srvId;
	}

	/** full constructor */
	public AppSrv(String srvId, String appId, String srvName, String srvPlace,
			String srvIp, String srvCfg, Timestamp startDt, Timestamp endDt,
			String modifyUser, String createUser, Timestamp modifyDt,
			String isValid, Timestamp createDt, String srvType) {
		this.srvId = srvId;
		this.appId = appId;
		this.srvName = srvName;
		this.srvPlace = srvPlace;
		this.srvIp = srvIp;
		this.srvCfg = srvCfg;
		this.startDt = startDt;
		this.endDt = endDt;
		this.modifyUser = modifyUser;
		this.createUser = createUser;
		this.modifyDt = modifyDt;
		this.isValid = isValid;
		this.createDt = createDt;
		this.srvType = srvType;
	}

	// Property accessors
	@Id
	@Column(name = "SRV_ID", unique = true, nullable = false, length = 32)
	public String getSrvId() {
		return this.srvId;
	}

	public void setSrvId(String srvId) {
		this.srvId = srvId;
	}

	@Column(name = "APP_ID", length = 32)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "SRV_NAME", length = 128)
	public String getSrvName() {
		return this.srvName;
	}

	public void setSrvName(String srvName) {
		this.srvName = srvName;
	}

	@Column(name = "SRV_PLACE", length = 128)
	public String getSrvPlace() {
		return this.srvPlace;
	}

	public void setSrvPlace(String srvPlace) {
		this.srvPlace = srvPlace;
	}

	@Column(name = "SRV_IP", length = 128)
	public String getSrvIp() {
		return this.srvIp;
	}

	public void setSrvIp(String srvIp) {
		this.srvIp = srvIp;
	}

	@Column(name = "SRV_CFG", length = 256)
	public String getSrvCfg() {
		return this.srvCfg;
	}

	public void setSrvCfg(String srvCfg) {
		this.srvCfg = srvCfg;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DT", length = 19)
	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DT", length = 0)
	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
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
	@JSONField(format="yyyy-MM-dd")
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
	@JSONField(format="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DT", length = 19)
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	@Column(name = "SRV_TYPE", length = 1)
	public String getSrvType() {
		return this.srvType;
	}

	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}
}