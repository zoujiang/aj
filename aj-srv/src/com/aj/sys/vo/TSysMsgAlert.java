package com.aj.sys.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSysMsgAlert entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sys_msg_alert")
public class TSysMsgAlert implements java.io.Serializable {

	// Fields

	private Integer id;
	private int msgType;
	private String msgTitle;
	private String msgContent;
	private String relationId;
	private int hadRead;
	private String createDate;
	private String updateDate;
	private int reciveUserId;
	private Integer dealResult;
	private Integer sendUserId;
	private String sendUserPhoto;

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "MSG_TYPE")
	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	@Column(name = "MSG_TITLE", length = 100)
	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	@Column(name = "MSG_CONTENT", length = 1000)
	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	@Column(name = "RELATION_ID")
	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	@Column(name = "HAD_READ")
	public int getHadRead() {
		return hadRead;
	}

	public void setHadRead(int hadRead) {
		this.hadRead = hadRead;
	}
	@Column(name = "CREATE_DATE" , length = 32)
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "UPDATE_DATE" , length = 32)
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "RECIVE_USER_ID" )
	public int getReciveUserId() {
		return reciveUserId;
	}

	public void setReciveUserId(int reciveUserId) {
		this.reciveUserId = reciveUserId;
	}
	@Column(name = "DEAL_RESULT" )
	public Integer getDealResult() {
		return dealResult;
	}

	public void setDealResult(Integer dealResult) {
		this.dealResult = dealResult;
	}
	@Column(name = "SEND_USER_ID" )
	public Integer getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Integer sendUserId) {
		this.sendUserId = sendUserId;
	}
	@Transient
	public String getSendUserPhoto() {
		return sendUserPhoto;
	}

	public void setSendUserPhoto(String sendUserPhoto) {
		this.sendUserPhoto = sendUserPhoto;
	}

	
}