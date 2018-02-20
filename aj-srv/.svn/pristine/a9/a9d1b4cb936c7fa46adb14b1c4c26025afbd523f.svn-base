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
@Table(name = "vm_msg_log_info")
public class VmMsgLogInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String logId;
	private String msgId;
	private String sendResult;
	private String phoneVer;
	private String isValid;
	private String ucode;
	private String nickName;
	private String userTel;
	private String msgTitle;
	private String msgContent;
	private String msgType;
	private String msgFrom;	
	private Timestamp sendDt;
	private String msgPlatform;
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
	@Column(name = "PHONE_VER")
	public String getPhoneVer() {
		return phoneVer;
	}

	public void setPhoneVer(String phoneVer) {
		this.phoneVer = phoneVer;
	}
	@Column(name = "UCODE")
	public String getUcode() {
		return ucode;
	}

	public void setUcode(String ucode) {
		this.ucode = ucode;
	}
	@Column(name = "NICK_NAME")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	

	

	

	
	

	

	

	
	

	

}