package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenTeacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_teacher")

public class TKindergartenTeacher  implements java.io.Serializable {


    // Fields    

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
     private Integer userId;


    // Constructors

    /** default constructor */
    public TKindergartenTeacher() {
    }

    
    /** full constructor */
    public TKindergartenTeacher(Integer kindergartenId, String name, Integer sex, String tel, Integer type, Integer age, String rechargeTelNo, String photo, String createUser, String createTime) {
        this.kindergartenId = kindergartenId;
        this.name = name;
        this.sex = sex;
        this.tel = tel;
        this.type = type;
        this.age = age;
        this.rechargeTelNo = rechargeTelNo;
        this.photo = photo;
        this.createUser = createUser;
        this.createTime = createTime;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="kindergarten_id")

    public Integer getKindergartenId() {
        return this.kindergartenId;
    }
    
    public void setKindergartenId(Integer kindergartenId) {
        this.kindergartenId = kindergartenId;
    }
    
    @Column(name="name", length=30)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="sex")

    public Integer getSex() {
        return this.sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
    @Column(name="tel", length=20)

    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="type")

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    @Column(name="age")

    public Integer getAge() {
        return this.age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    @Column(name="recharge_tel_no", length=20)

    public String getRechargeTelNo() {
        return this.rechargeTelNo;
    }
    
    public void setRechargeTelNo(String rechargeTelNo) {
        this.rechargeTelNo = rechargeTelNo;
    }
    
    @Column(name="photo", length=128)

    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    @Column(name="create_user", length=64)

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    @Column(name="create_time", length=30)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name="user_id")
	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
   

}