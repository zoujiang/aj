package com.aj.babymgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TBabyInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_baby_info")
public class TBabyInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String nickname;
	private String conceptionDate;
	private String expectedDate;
	private Integer bbNumber;
	private String photoUrl;
	private String createDate;
	private String createUserId;
	private String familyId;
	private String isPrivate;
	private String ajno;

	// Constructors

	/** default constructor */
	public TBabyInfo() {
	}

	/** minimal constructor */
	public TBabyInfo(String id, String nickname, String createDate,
			String createUserId, String ajno) {
		this.id = id;
		this.nickname = nickname;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.ajno = ajno;
	}

	/** full constructor */
	public TBabyInfo(String id, String nickname, String conceptionDate,
			String expectedDate, Integer bbNumber, String photoUrl,
			String createDate, String createUserId, String familyId,
			String isPrivate, String ajno) {
		this.id = id;
		this.nickname = nickname;
		this.conceptionDate = conceptionDate;
		this.expectedDate = expectedDate;
		this.bbNumber = bbNumber;
		this.photoUrl = photoUrl;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.familyId = familyId;
		this.isPrivate = isPrivate;
		this.ajno = ajno;
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

	@Column(name = "nickname", nullable = false, length = 64)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "conception_date", length = 20)
	public String getConceptionDate() {
		return this.conceptionDate;
	}

	public void setConceptionDate(String conceptionDate) {
		this.conceptionDate = conceptionDate;
	}

	@Column(name = "expected_date", length = 20)
	public String getExpectedDate() {
		return this.expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	@Column(name = "bbNumber")
	public Integer getBbNumber() {
		return this.bbNumber;
	}

	public void setBbNumber(Integer bbNumber) {
		this.bbNumber = bbNumber;
	}

	@Column(name = "PHOTO_URL", length = 100)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "CREATE_DATE", nullable = false, length = 20)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_USER_ID", nullable = false, length = 64)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "FAMILY_ID", length = 64)
	public String getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	@Column(name = "IS_PRIVATE", length = 1)
	public String getIsPrivate() {
		return this.isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}

	@Column(name = "AJNO", nullable = false, length = 20)
	public String getAjno() {
		return this.ajno;
	}

	public void setAjno(String ajno) {
		this.ajno = ajno;
	}

}