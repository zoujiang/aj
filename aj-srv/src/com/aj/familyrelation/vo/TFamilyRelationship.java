package com.aj.familyrelation.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TFamilyRelationship entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_family_relationship")
public class TFamilyRelationship implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer createUserId;
	private Integer relationUserId;
	private Integer callId;
	private String createDate;
	private Integer isPrivate;
	private Integer isDel;
	private Integer isConfirm;
	private String description;

	// Constructors

	/** default constructor */
	public TFamilyRelationship() {
	}

	/** minimal constructor */
	public TFamilyRelationship(Integer createUserId, Integer relationUserId,
			Integer callId) {
		this.createUserId = createUserId;
		this.relationUserId = relationUserId;
		this.callId = callId;
	}

	/** full constructor */
	public TFamilyRelationship(Integer createUserId, Integer relationUserId,
			Integer callId, String createDate, Integer isPrivate) {
		this.createUserId = createUserId;
		this.relationUserId = relationUserId;
		this.callId = callId;
		this.createDate = createDate;
		this.isPrivate = isPrivate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CREATE_USER_ID", nullable = false)
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "RELATION_USER_ID", nullable = false)
	public Integer getRelationUserId() {
		return this.relationUserId;
	}

	public void setRelationUserId(Integer relationUserId) {
		this.relationUserId = relationUserId;
	}

	@Column(name = "CALL_ID", nullable = false)
	public Integer getCallId() {
		return this.callId;
	}

	public void setCallId(Integer callId) {
		this.callId = callId;
	}

	@Column(name = "CREATE_DATE", length = 30)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "IS_PRIVATE")
	public Integer getIsPrivate() {
		return this.isPrivate;
	}

	public void setIsPrivate(Integer isPrivate) {
		this.isPrivate = isPrivate;
	}
	@Column(name = "IS_DEL")
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	@Column(name = "IS_CONFIRM")
	public Integer getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}
	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}