package com.aam.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TCustomReg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CUSTOM_REG")
public class TCustomReg implements java.io.Serializable {

	// Fields

	private String id;
	private String smsTokenId;
	private String userTel;
	private String smsCode;
	private String createUser;
	private Date createDt;
	private String isValid;
	private String modifyUser;
	private Date modifyDt;

	// Constructors

	/** default constructor */
	public TCustomReg() {
	}

	/** minimal constructor */
	public TCustomReg(String id) {
		this.id = id;
	}

	/** full constructor */
	public TCustomReg(String id, String smsTokenId, String userTel,
			String smsCode, String createUser, Date createDt, String isValid,
			String modifyUser, Date modifyDt) {
		this.id = id;
		this.smsTokenId = smsTokenId;
		this.userTel = userTel;
		this.smsCode = smsCode;
		this.createUser = createUser;
		this.createDt = createDt;
		this.isValid = isValid;
		this.modifyUser = modifyUser;
		this.modifyDt = modifyDt;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "SMS_TOKEN_ID", length = 8)
	public String getSmsTokenId() {
		return this.smsTokenId;
	}

	public void setSmsTokenId(String smsTokenId) {
		this.smsTokenId = smsTokenId;
	}

	@Column(name = "USER_TEL", length = 32)
	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	@Column(name = "SMS_CODE", length = 32)
	public String getSmsCode() {
		return this.smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Column(name = "CREATE_USER", length = 32)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DT")
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "MODIFY_USER", length = 32)
	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DT")
	public Date getModifyDt() {
		return this.modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

}