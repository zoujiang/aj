package com.aj.childrenmgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TChildrenInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_children_info")
public class TChildrenInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String nickname;
	private String truename;
	private String sex;
	private String birthday;
	private String photoUrl;
	private String ajno;
	private String createDate;
	private String createUserId;
	private String familyId;
	private String isPrivate;
	private String babyId;
	private String fixSortName;
	private String fixCallName;

	// Constructors

	/** default constructor */
	public TChildrenInfo() {
	}

	/** minimal constructor */
	public TChildrenInfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public TChildrenInfo(String id, String nickname, String truename,
			String sex, String birthday, String photoUrl, String ajno,
			String createDate, String createUserId, String familyId) {
		this.id = id;
		this.nickname = nickname;
		this.truename = truename;
		this.sex = sex;
		this.birthday = birthday;
		this.photoUrl = photoUrl;
		this.ajno = ajno;
		this.createDate = createDate;
		this.createUserId = createUserId;
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

	@Column(name = "nickname", length = 64)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "truename", length = 64)
	public String getTruename() {
		return this.truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	@Column(name = "sex", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "birthday", length = 20)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "photo_url", length = 100)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "ajno", length = 64)
	public String getAjno() {
		return this.ajno;
	}

	public void setAjno(String ajno) {
		this.ajno = ajno;
	}

	@Column(name = "create_date", length = 20)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_user_id", length = 64)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "family_id", length = 64)
	public String getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	@Column(name = "is_private", length = 1)
	public String getIsPrivate() {
		return isPrivate;
	}
	
	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}
	@Column(name = "baby_id", length = 64)
	public String getBabyId() {
		return babyId;
	}

	public void setBabyId(String babyId) {
		this.babyId = babyId;
	}
	@Column(name = "fixSortName", length = 10)
	public String getFixSortName() {
		return fixSortName;
	}

	public void setFixSortName(String fixSortName) {
		this.fixSortName = fixSortName;
	}
	@Column(name = "fixCallName", length = 20)
	public String getFixCallName() {
		return fixCallName;
	}

	public void setFixCallName(String fixCallName) {
		this.fixCallName = fixCallName;
	}
	
	

}