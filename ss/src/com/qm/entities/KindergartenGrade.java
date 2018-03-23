package com.qm.entities;

import com.frame.core.util.pagination.key.LimitKey;

public class KindergartenGrade extends LimitKey{
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
    
    private String kindergartenName;
    
    private String firstTeacherName;
    
    private String secondTeacherName;
    
    private String nurseName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(Integer kindergartenId) {
        this.kindergartenId = kindergartenId;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series == null ? null : series.trim();
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo == null ? null : classNo.trim();
    }

    public Integer getFirstTeacher() {
        return firstTeacher;
    }

    public void setFirstTeacher(Integer firstTeacher) {
        this.firstTeacher = firstTeacher;
    }

    public Integer getSecondTeacher() {
        return secondTeacher;
    }

    public void setSecondTeacher(Integer secondTeacher) {
        this.secondTeacher = secondTeacher;
    }

    public Integer getNurse() {
        return nurse;
    }

    public void setNurse(Integer nurse) {
        this.nurse = nurse;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration == null ? null : declaration.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

	public String getKindergartenName() {
		return kindergartenName;
	}

	public void setKindergartenName(String kindergartenName) {
		this.kindergartenName = kindergartenName;
	}

	public String getFirstTeacherName() {
		return firstTeacherName;
	}

	public void setFirstTeacherName(String firstTeacherName) {
		this.firstTeacherName = firstTeacherName;
	}

	public String getSecondTeacherName() {
		return secondTeacherName;
	}

	public void setSecondTeacherName(String secondTeacherName) {
		this.secondTeacherName = secondTeacherName;
	}

	public String getNurseName() {
		return nurseName;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}
    
}