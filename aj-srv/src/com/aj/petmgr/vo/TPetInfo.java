package com.aj.petmgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPetInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_pet_info")
public class TPetInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String nickName;
	private String petType;
	private String birthday;
	private String sex;
	private String photoUrl;
	private String ajno;
	private String createDate;
	private String createUserId;
	private String isPrivate;

	// Constructors

	/** default constructor */
	public TPetInfo() {
	}

	/** minimal constructor */
	public TPetInfo(String id, String nickName) {
		this.id = id;
		this.nickName = nickName;
	}

	/** full constructor */
	public TPetInfo(String id, String nickName, String petType,
			String birthday, String sex, String photoUrl, String ajno,
			String createDate, String createUserId, String isPrivate) {
		this.id = id;
		this.nickName = nickName;
		this.petType = petType;
		this.birthday = birthday;
		this.sex = sex;
		this.photoUrl = photoUrl;
		this.ajno = ajno;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.isPrivate = isPrivate;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NICK_NAME", nullable = false, length = 64)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "PET_TYPE", length = 64)
	public String getPetType() {
		return this.petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	@Column(name = "BIRTHDAY", length = 20)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "SEX", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "PHOTO_URL", length = 100)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "AJNO", length = 20)
	public String getAjno() {
		return this.ajno;
	}

	public void setAjno(String ajno) {
		this.ajno = ajno;
	}

	@Column(name = "CREATE_DATE", length = 20)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_USER_ID", length = 64)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "IS_PRIVATE", length = 1)
	public String getIsPrivate() {
		return this.isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}

}