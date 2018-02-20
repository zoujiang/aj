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
@Table(name = "t_family_relation_key")
public class TFamilyRelationKey implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String key;
	private String createDate;

	// Constructors

	/** default constructor */
	public TFamilyRelationKey() {
	}

	/** minimal constructor */
	public TFamilyRelationKey(Integer userId, String key) {
		this.userId = userId;
		this.key = key;
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

	@Column(name = "USER_ID", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "FAMILY_KEY", nullable = false)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

		@Column(name = "CREATE_DATE", length = 30)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


}