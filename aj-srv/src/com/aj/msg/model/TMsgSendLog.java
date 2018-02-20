package com.aj.msg.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TMsgSendLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_msg_send_log")
public class TMsgSendLog implements java.io.Serializable {

	// Fields

	private String logId;
	private String msgId;
	private String userId;
	private String userTel;
	private String sendResult;
	private Timestamp sendDt;
	private Timestamp jobDt;
	private String msgSts;

	// Constructors

	/** default constructor */
	public TMsgSendLog() {
	}

	/** minimal constructor */
	public TMsgSendLog(String logId) {
		this.logId = logId;
	}

	/** full constructor */
	public TMsgSendLog(String logId, String msgId, String userId, String userTel,
			String sendResult, Timestamp sendDt, Timestamp jobDt,String msgSts) {
		this.logId = logId;
		this.msgId = msgId;
		this.userId = userId;
		this.userTel = userTel;
		this.sendResult = sendResult;
		this.sendDt = sendDt;
		this.jobDt = jobDt;
		this.msgSts=msgSts;
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
	@Column(name = "MSG_STS")
	public String getMsgSts() {
		return msgSts;
	}

	public void setMsgSts(String msgSts) {
		this.msgSts = msgSts;
	}

	@Column(name = "MSG_ID", length = 32)
	public String getMsgId() {
		return this.msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name = "USER_ID", length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_TEL", length = 32)
	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	@Column(name = "SEND_RESULT", length = 2)
	public String getSendResult() {
		return this.sendResult;
	}

	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}

	@Column(name = "SEND_DT", length = 19)
	public Timestamp getSendDt() {
		return this.sendDt;
	}

	public void setSendDt(Timestamp sendDt) {
		this.sendDt = sendDt;
	}

	@Column(name = "JOB_DT", length = 19)
	public Timestamp getJobDt() {
		return this.jobDt;
	}

	public void setJobDt(Timestamp jobDt) {
		this.jobDt = jobDt;
	}

}