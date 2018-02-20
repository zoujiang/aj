package com.qm.entities;

import com.frame.core.util.pagination.key.LimitKey;

public class KindergartenTeacher extends LimitKey{
    private Integer id;

    private Integer kindergartenId;

    private String name;

    private Integer sex;

    private String tel;

    private Integer type;

    private Integer age;

    private String rechargeTelNo;

    private String photo;

    private String createUser;

    private String createTime;
    
    private String kindergartenName;
    
    private String createUserName;
    //任教班级ID集合
    private String gradeNum;
    //任教班级名称集合
    private String gradeNumNames;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRechargeTelNo() {
        return rechargeTelNo;
    }

    public void setRechargeTelNo(String rechargeTelNo) {
        this.rechargeTelNo = rechargeTelNo == null ? null : rechargeTelNo.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

	public String getKindergartenName() {
		return kindergartenName;
	}

	public void setKindergartenName(String kindergartenName) {
		this.kindergartenName = kindergartenName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(String gradeNum) {
		this.gradeNum = gradeNum;
	}

	public String getGradeNumNames() {
		return gradeNumNames;
	}

	public void setGradeNumNames(String gradeNumNames) {
		this.gradeNumNames = gradeNumNames;
	}
    
}