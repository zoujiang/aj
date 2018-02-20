package com.aam.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TCusDeliAddr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CUS_DELI_ADDR")
public class TCusDeliAddr implements java.io.Serializable {

	// Fields

	private String deliId;
	private String deliName;
	private String deliTel;
	private String deliAddr;
	private String deliAreaAddr;
	private String deliDtlAddr;
	private String createUser;
	private Date createDt;
	private String isValid;
	private String modifyUser;
	private Date modifyDt;

	// Constructors

	/** default constructor */
	public TCusDeliAddr() {
	}

	/** minimal constructor */
	public TCusDeliAddr(String deliId) {
		this.deliId = deliId;
	}

	/** full constructor */
	public TCusDeliAddr(String deliId, String deliName, String deliTel,
			String deliAddr, String deliAreaAddr, String deliDtlAddr,
			String createUser, Date createDt, String isValid,
			String modifyUser, Date modifyDt) {
		this.deliId = deliId;
		this.deliName = deliName;
		this.deliTel = deliTel;
		this.deliAddr = deliAddr;
		this.deliAreaAddr = deliAreaAddr;
		this.deliDtlAddr = deliDtlAddr;
		this.createUser = createUser;
		this.createDt = createDt;
		this.isValid = isValid;
		this.modifyUser = modifyUser;
		this.modifyDt = modifyDt;
	}

	// Property accessors
	@Id
	@Column(name = "DELI_ID", unique = true, nullable = false, length = 32)
	public String getDeliId() {
		return this.deliId;
	}

	public void setDeliId(String deliId) {
		this.deliId = deliId;
	}

	@Column(name = "DELI_NAME", length = 32)
	public String getDeliName() {
		return this.deliName;
	}

	public void setDeliName(String deliName) {
		this.deliName = deliName;
	}

	@Column(name = "DELI_TEL", length = 32)
	public String getDeliTel() {
		return this.deliTel;
	}

	public void setDeliTel(String deliTel) {
		this.deliTel = deliTel;
	}

	@Column(name = "DELI_ADDR", length = 256)
	public String getDeliAddr() {
		return this.deliAddr;
	}

	public void setDeliAddr(String deliAddr) {
		this.deliAddr = deliAddr;
	}

	@Column(name = "DELI_AREA_ADDR", length = 256)
	public String getDeliAreaAddr() {
		return this.deliAreaAddr;
	}

	public void setDeliAreaAddr(String deliAreaAddr) {
		this.deliAreaAddr = deliAreaAddr;
	}

	@Column(name = "DELI_DTL_ADDR", length = 256)
	public String getDeliDtlAddr() {
		return this.deliDtlAddr;
	}

	public void setDeliDtlAddr(String deliDtlAddr) {
		this.deliDtlAddr = deliDtlAddr;
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