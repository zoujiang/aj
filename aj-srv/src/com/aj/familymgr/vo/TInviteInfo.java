package com.aj.familymgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TInviteInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_invite_info")
public class TInviteInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String fixSortName;
	private String fixCallName;
	private String customCallName;
	private String creatrUserId;
	private String relationUserId;
	private String inviteArea;
	private String isPrivate;
	private String regInfo;
	private String comfirmState;
	private String createDate;
	private String reason;
	private String updateDate;

	// Constructors

	/** default constructor */
	public TInviteInfo() {
	}

	/** minimal constructor */
	public TInviteInfo(String id, String creatrUserId) {
		this.id = id;
		this.creatrUserId = creatrUserId;
	}

	/** full constructor */
	public TInviteInfo(String id, String fixSortName, String fixCallName,
			String customCallName, String creatrUserId, String relationUserId,
			String inviteArea, String isPrivate, String regInfo,
			String comfirmState, String createDate, String reason,
			String updateDate) {
		this.id = id;
		this.fixSortName = fixSortName;
		this.fixCallName = fixCallName;
		this.customCallName = customCallName;
		this.creatrUserId = creatrUserId;
		this.relationUserId = relationUserId;
		this.inviteArea = inviteArea;
		this.isPrivate = isPrivate;
		this.regInfo = regInfo;
		this.comfirmState = comfirmState;
		this.createDate = createDate;
		this.reason = reason;
		this.updateDate = updateDate;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "fix_sort_name", length = 20)
	public String getFixSortName() {
		return this.fixSortName;
	}

	public void setFixSortName(String fixSortName) {
		this.fixSortName = fixSortName;
	}

	@Column(name = "fix_call_name", length = 20)
	public String getFixCallName() {
		return this.fixCallName;
	}

	public void setFixCallName(String fixCallName) {
		this.fixCallName = fixCallName;
	}

	@Column(name = "custom_call_name", length = 20)
	public String getCustomCallName() {
		return this.customCallName;
	}

	public void setCustomCallName(String customCallName) {
		this.customCallName = customCallName;
	}

	@Column(name = "creatr_user_id", nullable = false, length = 64)
	public String getCreatrUserId() {
		return this.creatrUserId;
	}

	public void setCreatrUserId(String creatrUserId) {
		this.creatrUserId = creatrUserId;
	}

	@Column(name = "relation_user_id", length = 64)
	public String getRelationUserId() {
		return this.relationUserId;
	}

	public void setRelationUserId(String relationUserId) {
		this.relationUserId = relationUserId;
	}

	@Column(name = "invite_area", length = 1)
	public String getInviteArea() {
		return this.inviteArea;
	}

	public void setInviteArea(String inviteArea) {
		this.inviteArea = inviteArea;
	}

	@Column(name = "is_private", length = 1)
	public String getIsPrivate() {
		return this.isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}

	@Column(name = "reg_info", length = 200)
	public String getRegInfo() {
		return this.regInfo;
	}

	public void setRegInfo(String regInfo) {
		this.regInfo = regInfo;
	}

	@Column(name = "comfirm_state", length = 1)
	public String getComfirmState() {
		return this.comfirmState;
	}

	public void setComfirmState(String comfirmState) {
		this.comfirmState = comfirmState;
	}

	@Column(name = "create_date", length = 20)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "reason", length = 200)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "update_date", length = 20)
	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}