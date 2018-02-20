package com.aj.familymgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TFamilyInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_family_info")
public class TFamilyInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String familyName;
	private String createUserId;
	private String createDate;
	private int isMarried;
	private String marriedDate;

	// Constructors

	/** default constructor */
	public TFamilyInfo() {
	}

	/** minimal constructor */
	public TFamilyInfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public TFamilyInfo(String id, String familyName, String createUserId,
			String createDate) {
		this.id = id;
		this.familyName = familyName;
		this.createUserId = createUserId;
		this.createDate = createDate;
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

	@Column(name = "family_name", length = 64)
	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	@Column(name = "create_user_id", length = 64)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "create_date", length = 20)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "is_married")
	public int getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(int isMarried) {
		this.isMarried = isMarried;
	}
	@Column(name = "married_date", length = 30)
	public String getMarriedDate() {
		return marriedDate;
	}

	public void setMarriedDate(String marriedDate) {
		this.marriedDate = marriedDate;
	}

}