package com.aj.msg.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TMsgInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vm_msg_info")
public class VmMsgInfo implements java.io.Serializable {

	// Fields
	private String logId;
	private String userTel;
	private String sendResult;
	private String sendDt;
	private String jobDt;	
	private String msgId;
	private String msgFrom;
	private String msgType;
	private String msgTitle;
	private String msgContent;
	private String sentUserGrop;
	private String sentDelay;
	private Timestamp sentDelayTime;
	private Timestamp overDt;
	private Timestamp createDt;
	private String isValid;
	private Timestamp modifyDt;
	private String createUser;
	private String modifyUser;
	private Integer orderNum;

	@Column(name = "LOG_ID")
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
	@Column(name = "USER_TEL")
	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	@Column(name = "SEND_RESULT")
	public String getSendResult() {
		return sendResult;
	}

	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	@Column(name = "SEND_DT")
	public String getSendDt() {
		return sendDt;
	}

	public void setSendDt(String sendDt) {
		this.sendDt = sendDt;
	}
	@Column(name = "JOB_DT")
	public String getJobDt() {
		return jobDt;
	}

	public void setJobDt(String jobDt) {
		this.jobDt = jobDt;
	}

	@Id
	@Column(name = "MSG_ID", unique = true, nullable = false, length = 32)
	public String getMsgId() {
		return this.msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name = "MSG_FROM", length = 32)
	public String getMsgFrom() {
		return this.msgFrom;
	}

	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	@Column(name = "MSG_TYPE", length = 32)
	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Column(name = "MSG_TITLE", length = 128)
	public String getMsgTitle() {
		return this.msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	@Column(name = "MSG_CONTENT")
	public String getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Column(name = "SENT_USER_GROP", length = 32)
	public String getSentUserGrop() {
		return this.sentUserGrop;
	}

	public void setSentUserGrop(String sentUserGrop) {
		this.sentUserGrop = sentUserGrop;
	}

	@Column(name = "SENT_DELAY", length = 1)
	public String getSentDelay() {
		return this.sentDelay;
	}

	public void setSentDelay(String sentDelay) {
		this.sentDelay = sentDelay;
	}

	@Column(name = "SENT_DELAY_TIME", length = 19)
	public Timestamp getSentDelayTime() {
		return this.sentDelayTime;
	}

	public void setSentDelayTime(Timestamp sentDelayTime) {
		this.sentDelayTime = sentDelayTime;
	}

	@Column(name = "OVER_DT", length = 19)
	public Timestamp getOverDt() {
		return this.overDt;
	}

	public void setOverDt(Timestamp overDt) {
		this.overDt = overDt;
	}

	@Column(name = "CREATE_DT", length = 19)
	public Timestamp getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "MODIFY_DT", length = 19)
	public Timestamp getModifyDt() {
		return this.modifyDt;
	}

	public void setModifyDt(Timestamp modifyDt) {
		this.modifyDt = modifyDt;
	}

	@Column(name = "CREATE_USER", length = 32)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "MODIFY_USER", length = 32)
	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	@Column(name = "ORDER_NUM")
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}