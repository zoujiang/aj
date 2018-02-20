package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenStudent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_student")

public class TKindergartenStudent  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer kindergartenId;
     private String name;
     private Integer age;
     private Integer sex;
     private String parentsName;
     private String parentsTel;
     private Integer gradeId;
     private String photo;
     private String createUser;
     private String createTime;


    // Constructors

    /** default constructor */
    public TKindergartenStudent() {
    }

    
    /** full constructor */
    public TKindergartenStudent(Integer kindergartenId, String name, Integer age, Integer sex, String parentsName, String parentsTel, Integer gradeId, String photo, String createUser, String createTime) {
        this.kindergartenId = kindergartenId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.parentsName = parentsName;
        this.parentsTel = parentsTel;
        this.gradeId = gradeId;
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
    
    @Column(name="age")

    public Integer getAge() {
        return this.age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    @Column(name="sex")

    public Integer getSex() {
        return this.sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
    @Column(name="parents_name", length=20)

    public String getParentsName() {
        return this.parentsName;
    }
    
    public void setParentsName(String parentsName) {
        this.parentsName = parentsName;
    }
    
    @Column(name="parents_tel", length=20)

    public String getParentsTel() {
        return this.parentsTel;
    }
    
    public void setParentsTel(String parentsTel) {
        this.parentsTel = parentsTel;
    }
    
    @Column(name="grade_id")

    public Integer getGradeId() {
        return this.gradeId;
    }
    
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
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
   








}