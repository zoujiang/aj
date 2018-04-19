package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TKingergartenGradeVisitInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_kindergarten_grade_visit_info")

public class TKindergartenGradeVisitInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer gradeId;
	private String createTime;
	private Integer userId;

	// Constructors

	/** default constructor */
	public TKindergartenGradeVisitInfo() {
	}

	/** full constructor */
	public TKindergartenGradeVisitInfo(Integer gradeId, String createTime, Integer userId) {
		this.gradeId = gradeId;
		this.createTime = createTime;
		this.userId = userId;
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

	@Column(name = "grade_id", nullable = false)

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	@Column(name = "create_time", nullable = false, length = 32)

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "user_id", nullable = false)

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}