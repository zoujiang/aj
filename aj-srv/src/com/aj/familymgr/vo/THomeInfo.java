package com.aj.familymgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * THomeInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_home_info")
public class THomeInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String creatrUserId;
	private String relationUserId;
	private String inviteArea;
	private String isPrivate;
	private String inviteId;
	private String callName;
	private String easemobGroupId;
	private int    isValid;

	// Constructors

	/** default constructor */
	public THomeInfo() {
	}

	/** minimal constructor */
	public THomeInfo(String id, String creatrUserId, String relationUserId) {
		this.id = id;
		this.creatrUserId = creatrUserId;
		this.relationUserId = relationUserId;
	}

	/** full constructor */
	public THomeInfo(String id, String creatrUserId, String relationUserId,
			String inviteArea, String isPrivate) {
		this.id = id;
		this.creatrUserId = creatrUserId;
		this.relationUserId = relationUserId;
		this.inviteArea = inviteArea;
		this.isPrivate = isPrivate;
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

	@Column(name = "create_user_id", nullable = false, length = 64)
	public String getCreatrUserId() {
		return this.creatrUserId;
	}

	public void setCreatrUserId(String creatrUserId) {
		this.creatrUserId = creatrUserId;
	}

	@Column(name = "relation_user_id", nullable = false, length = 64)
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
	@Column(name = "invite_id", nullable = false, length = 64)
	public String getInviteId() {
		return inviteId;
	}

	public void setInviteId(String inviteId) {
		this.inviteId = inviteId;
	}
	@Column(name = "call_name", nullable = false, length = 20)
	public String getCallName() {
		return callName;
	}

	public void setCallName(String callName) {
		this.callName = callName;
	}
	@Column(name = "easemobGroupId", length = 100)
	public String getEasemobGroupId() {
		return easemobGroupId;
	}

	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
	}
	@Column(name = "is_valid")
	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

}