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
@Table(name = "vm_shop_msg", catalog = "tis")
public class VmShopMsgLogInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String logId;
	private String msgId;
	private String isValid;

	private String msgTitle;
	private String msgContent;
	private String msgType;
	private String msgFrom;
	private Timestamp sendDt;
	private String msgPlatform;
	private String msgSts;
	private String userId;

	@Column(name = "MSG_PLATFORM", length = 32)
	public String getMsgPlatform() {
		return msgPlatform;
	}

	public void setMsgPlatform(String msgPlatform) {
		this.msgPlatform = msgPlatform;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "MSG_STS")
	public String getMsgSts() {
		return msgSts;
	}

	public void setMsgSts(String msgSts) {
		this.msgSts = msgSts;
	}

	@Column(name = "SEND_DT")
	public Timestamp getSendDt() {
		return sendDt;
	}

	public void setSendDt(Timestamp sendDt) {
		this.sendDt = sendDt;
	}

	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 32)
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Id
	@Column(name = "MSG_ID", length = 32)
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

}