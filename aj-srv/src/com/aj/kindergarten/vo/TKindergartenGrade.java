package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TKindergartenGrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_kindergarten_grade")

public class TKindergartenGrade implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer kindergartenId;
	private String series;
	private String classNo;
	private Integer firstTeacher;
	private Integer secondTeacher;
	private Integer nurse;
	private String logo;
	private String declaration;
	private String name;
	private String rule;
	private String createTime;
	private String createUser;
	private String className;

	// Constructors

	/** default constructor */
	public TKindergartenGrade() {
	}

	/** minimal constructor */
	public TKindergartenGrade(Integer kindergartenId) {
		this.kindergartenId = kindergartenId;
	}

	/** full constructor */
	public TKindergartenGrade(Integer kindergartenId, String series, String classNo, Integer firstTeacher,
			Integer secondTeacher, Integer nurse, String logo, String declaration, String name, String rule,
			String createTime, String createUser, String className) {
		this.kindergartenId = kindergartenId;
		this.series = series;
		this.classNo = classNo;
		this.firstTeacher = firstTeacher;
		this.secondTeacher = secondTeacher;
		this.nurse = nurse;
		this.logo = logo;
		this.declaration = declaration;
		this.name = name;
		this.rule = rule;
		this.createTime = createTime;
		this.createUser = createUser;
		this.className = className;
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

	@Column(name = "kindergarten_id", nullable = false)

	public Integer getKindergartenId() {
		return this.kindergartenId;
	}

	public void setKindergartenId(Integer kindergartenId) {
		this.kindergartenId = kindergartenId;
	}

	@Column(name = "series", length = 10)

	public String getSeries() {
		return this.series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	@Column(name = "class_no", length = 10)

	public String getClassNo() {
		return this.classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	@Column(name = "first_teacher")

	public Integer getFirstTeacher() {
		return this.firstTeacher;
	}

	public void setFirstTeacher(Integer firstTeacher) {
		this.firstTeacher = firstTeacher;
	}

	@Column(name = "second_teacher")

	public Integer getSecondTeacher() {
		return this.secondTeacher;
	}

	public void setSecondTeacher(Integer secondTeacher) {
		this.secondTeacher = secondTeacher;
	}

	@Column(name = "nurse")

	public Integer getNurse() {
		return this.nurse;
	}

	public void setNurse(Integer nurse) {
		this.nurse = nurse;
	}

	@Column(name = "logo", length = 128)

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "declaration", length = 500)

	public String getDeclaration() {
		return this.declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	@Column(name = "name", length = 100)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "rule", length = 10)

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Column(name = "create_time", length = 30)

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "create_user", length = 64)

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "class_name", length = 20)

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}