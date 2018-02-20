package com.frame.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TLogBizOpr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_LOG_BIZ_OPR")
public class TLogBizOpr implements java.io.Serializable {

	// Fields

	private String logId;
	private String oprUserName;
	private Date oprDt;
	private String oprType;
	private String oprContent;
	private String oprTable;
	private String oprTableData;
	private String oprUserIp;
	private String oprSysModule;
	private String oprUserAccount;
	private String oprUa;
	private String oprSysId;

	// Constructors

	/** default constructor */
	public TLogBizOpr() {
	}

	/** minimal constructor */
	public TLogBizOpr(String logId) {
		this.logId = logId;
	}

	/** full constructor */
	public TLogBizOpr(String logId, String oprUserName, Date oprDt,
			String oprType, String oprContent, String oprTable,
			String oprTableData, String oprUserIp, String oprSysModule,
			String oprUserAccount, String oprUa, String oprSysId) {
		this.logId = logId;
		this.oprUserName = oprUserName;
		this.oprDt = oprDt;
		this.oprType = oprType;
		this.oprContent = oprContent;
		this.oprTable = oprTable;
		this.oprTableData = oprTableData;
		this.oprUserIp = oprUserIp;
		this.oprSysModule = oprSysModule;
		this.oprUserAccount = oprUserAccount;
		this.oprUa = oprUa;
		this.oprSysId = oprSysId;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 32)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "OPR_USER_NAME", length = 32)
	public String getOprUserName() {
		return this.oprUserName;
	}

	public void setOprUserName(String oprUserName) {
		this.oprUserName = oprUserName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPR_DT")
	public Date getOprDt() {
		return this.oprDt;
	}

	public void setOprDt(Date oprDt) {
		this.oprDt = oprDt;
	}

	@Column(name = "OPR_TYPE", length = 32)
	public String getOprType() {
		return this.oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}

	@Column(name = "OPR_CONTENT", length = 65535)
	public String getOprContent() {
		return this.oprContent;
	}

	public void setOprContent(String oprContent) {
		this.oprContent = oprContent;
	}

	@Column(name = "OPR_TABLE", length = 1024)
	public String getOprTable() {
		return this.oprTable;
	}

	public void setOprTable(String oprTable) {
		this.oprTable = oprTable;
	}

	@Column(name = "OPR_TABLE_DATA", length = 65535)
	public String getOprTableData() {
		return this.oprTableData;
	}

	public void setOprTableData(String oprTableData) {
		this.oprTableData = oprTableData;
	}

	@Column(name = "OPR_USER_IP", length = 32)
	public String getOprUserIp() {
		return this.oprUserIp;
	}

	public void setOprUserIp(String oprUserIp) {
		this.oprUserIp = oprUserIp;
	}

	@Column(name = "OPR_SYS_MODULE", length = 32)
	public String getOprSysModule() {
		return this.oprSysModule;
	}

	public void setOprSysModule(String oprSysModule) {
		this.oprSysModule = oprSysModule;
	}

	@Column(name = "OPR_USER_ACCOUNT", length = 32)
	public String getOprUserAccount() {
		return this.oprUserAccount;
	}

	public void setOprUserAccount(String oprUserAccount) {
		this.oprUserAccount = oprUserAccount;
	}

	@Column(name = "OPR_UA", length = 2048)
	public String getOprUa() {
		return this.oprUa;
	}

	public void setOprUa(String oprUa) {
		this.oprUa = oprUa;
	}

	@Column(name = "OPR_SYS_ID", length = 32)
	public String getOprSysId() {
		return this.oprSysId;
	}

	public void setOprSysId(String oprSysId) {
		this.oprSysId = oprSysId;
	}

}