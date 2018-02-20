package com.aam.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TCustomLogin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CUSTOM_LOGIN")
public class TCustomLogin implements java.io.Serializable {

	// Fields

	private String tokenId;
	private String userId;
	private Date loginDt;
	private Date logoutDt;

	// Constructors

	/** default constructor */
	public TCustomLogin() {
	}

	/** minimal constructor */
	public TCustomLogin(String tokenId, String userId) {
		this.tokenId = tokenId;
		this.userId = userId;
	}

	/** full constructor */
	public TCustomLogin(String tokenId, String userId, Date loginDt,
			Date logoutDt) {
		this.tokenId = tokenId;
		this.userId = userId;
		this.loginDt = loginDt;
		this.logoutDt = logoutDt;
	}

	// Property accessors
	@Id
	@Column(name = "TOKEN_ID", unique = true, nullable = false, length = 32)
	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Column(name = "USER_ID", nullable = false, length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_DT")
	public Date getLoginDt() {
		return this.loginDt;
	}

	public void setLoginDt(Date loginDt) {
		this.loginDt = loginDt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGOUT_DT")
	public Date getLogoutDt() {
		return this.logoutDt;
	}

	public void setLogoutDt(Date logoutDt) {
		this.logoutDt = logoutDt;
	}

}