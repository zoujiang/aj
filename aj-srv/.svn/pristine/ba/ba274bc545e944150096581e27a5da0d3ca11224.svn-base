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
@Table(name = "t_msg_info")
public class TMsgInfo implements java.io.Serializable {

	// Fields

	private String msgId;
	private String msgFrom;
	private String msgType;
	private String msgTitle;
	private String msgContent;
	private String platform;
	private String doAudit;
	private String msgUrl;
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
	private String doSend;
	private String msgUrlType;
	private String sentUserGropInfo;
	
	// Constructors

	/** default constructor */
	public TMsgInfo() {
	}

	/** minimal constructor */
	public TMsgInfo(String msgId) {
		this.msgId = msgId;
	}

	/** full constructor */
	public TMsgInfo(String msgId, String msgFrom, String msgType,
			String msgTitle, String msgContent, String msgImg, String msgUrl, 
			String sentUserGrop, String sentDelay, Timestamp sentDelayTime, 
			Timestamp overDt, Timestamp createDt, String isValid, Timestamp modifyDt,
			String createUser, String modifyUser, Integer orderNum,String platform,String doAudit,String doSend) {
		this.msgId = msgId;
		this.msgFrom = msgFrom;
		this.msgType = msgType;
		this.msgTitle = msgTitle;
		this.msgContent = msgContent;
		this.msgUrl = msgUrl;
		this.sentUserGrop = sentUserGrop;
		this.sentDelay = sentDelay;
		this.sentDelayTime = sentDelayTime;
		this.overDt = overDt;
		this.createDt = createDt;
		this.isValid = isValid;
		this.modifyDt = modifyDt;
		this.createUser = createUser;
		this.modifyUser = modifyUser;
		this.orderNum = orderNum;
		this.platform=platform;
		this.doAudit=doAudit;
		this.doSend=doSend;
		this.msgUrlType=msgUrlType;
		this.sentUserGropInfo=sentUserGropInfo;
	}

	// Property accessors
	
	@Id
	@Column(name = "MSG_ID", unique = true, nullable = false, length = 32)
	public String getMsgId() {
		return this.msgId;
	}



	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@Column(name = "SENT_USER_GROP_INFO")
	public String getSentUserGropInfo() {
		return sentUserGropInfo;
	}

	public void setSentUserGropInfo(String sentUserGropInfo) {
		this.sentUserGropInfo = sentUserGropInfo;
	}

	@Column(name = "MSG_URL_TYPE", length = 32)
	public String getMsgUrlType() {
		return msgUrlType;
	}

	public void setMsgUrlType(String msgUrlType) {
		this.msgUrlType = msgUrlType;
	}
	@Column(name = "MSG_PLATFORM", length = 32)
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
     * 是否审核成功(-1失败，0无状态，1成功)
     */
    @Column(name = "DO_SEND")
	  public String getDoSend() {
		return doSend;
	}

	public void setDoSend(String doSend) {
		this.doSend = doSend;
	}

	/**
     * 是否审核成功(-1失败，0无状态，1成功)
     */
    @Column(name = "DO_AUDIT")
	public String getDoAudit() {
		return doAudit;
	}

	public void setDoAudit(String doAudit) {
		this.doAudit = doAudit;
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

	

	@Column(name = "MSG_URL")
	public String getMsgUrl() {
		return this.msgUrl;
	}

	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
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