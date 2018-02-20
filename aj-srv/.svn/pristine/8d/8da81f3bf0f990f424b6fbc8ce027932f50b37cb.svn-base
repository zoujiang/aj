package com.aj.collectionmgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCollection entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_collection")
public class TCollection implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String type;
	private String photoUrl;
	private String textUrl;
	private String target;
	private String createDate;

	// Constructors

	/** default constructor */
	public TCollection() {
	}

	/** minimal constructor */
	public TCollection(Integer userId) {
		this.userId = userId;
	}

	/** full constructor */
	public TCollection(Integer userId, String type, String photoUrl,
			String textUrl, String target) {
		this.userId = userId;
		this.type = type;
		this.photoUrl = photoUrl;
		this.textUrl = textUrl;
		this.target = target;
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

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "PHOTO_URL", length = 100)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "TEXT_URL", length = 100)
	public String getTextUrl() {
		return this.textUrl;
	}

	public void setTextUrl(String textUrl) {
		this.textUrl = textUrl;
	}

	@Column(name = "TARGET", length = 1)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	@Column(name = "CREATEDATE", length = 32)
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}