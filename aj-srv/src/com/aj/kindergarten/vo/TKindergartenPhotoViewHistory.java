package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TKindergartenPhotoViewHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_kindergarten_photo_view_history")

public class TKindergartenPhotoViewHistory implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer photoId;
	private Integer category;
	private Integer gradeId;
	private Integer kindergartenId;
	private Long createTime;
	private Integer userId;

	// Constructors

	/** default constructor */
	public TKindergartenPhotoViewHistory() {
	}

	/** minimal constructor */
	public TKindergartenPhotoViewHistory(Integer photoId) {
		this.photoId = photoId;
	}

	/** full constructor */
	public TKindergartenPhotoViewHistory(Integer photoId, Integer category, Integer gradeId, Integer kindergartenId,
			Long createTime) {
		this.photoId = photoId;
		this.category = category;
		this.gradeId = gradeId;
		this.kindergartenId = kindergartenId;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "photo_id", nullable = false)

	public Integer getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	@Column(name = "category")

	public Integer getCategory() {
		return this.category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	@Column(name = "grade_id")

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	@Column(name = "kindergarten_id")

	public Integer getKindergartenId() {
		return this.kindergartenId;
	}

	public void setKindergartenId(Integer kindergartenId) {
		this.kindergartenId = kindergartenId;
	}

	@Column(name = "create_time")
	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}