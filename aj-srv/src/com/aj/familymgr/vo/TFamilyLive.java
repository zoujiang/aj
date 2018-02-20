package com.aj.familymgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TFamilyLive entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_family_live")
public class TFamilyLive implements java.io.Serializable {

	// Fields

	private String id;
	private String content;
	private String createDate;
	private String familyId;

	// Constructors

	/** default constructor */
	public TFamilyLive() {
	}

	/** minimal constructor */
	public TFamilyLive(String id) {
		this.id = id;
	}

	/** full constructor */
	public TFamilyLive(String id, String content, String createDate,
			String familyId) {
		this.id = id;
		this.content = content;
		this.createDate = createDate;
		this.familyId = familyId;
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

	@Column(name = "content", length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "create_date", length = 20)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "family_id", length = 64)
	public String getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

}