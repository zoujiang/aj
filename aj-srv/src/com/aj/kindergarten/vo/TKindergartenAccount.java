package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_account")

public class TKindergartenAccount  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String username;
     private String password;
     private Integer kindergartenId;
     private String tel;
     private String nickname;
     private String createTime;
     private Integer status;
     private String email;


    // Constructors

    /** default constructor */
    public TKindergartenAccount() {
    }

	/** minimal constructor */
    public TKindergartenAccount(String username, Integer kindergartenId, Integer status) {
        this.username = username;
        this.kindergartenId = kindergartenId;
        this.status = status;
    }
    
    /** full constructor */
    public TKindergartenAccount(String username, String password, Integer kindergartenId, String tel, String nickname, String createTime, Integer status, String email) {
        this.username = username;
        this.password = password;
        this.kindergartenId = kindergartenId;
        this.tel = tel;
        this.nickname = nickname;
        this.createTime = createTime;
        this.status = status;
        this.email = email;
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
    
    @Column(name="username", nullable=false, length=30)

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="password", length=100)

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="kindergarten_id", nullable=false)

    public Integer getKindergartenId() {
        return this.kindergartenId;
    }
    
    public void setKindergartenId(Integer kindergartenId) {
        this.kindergartenId = kindergartenId;
    }
    
    @Column(name="tel", length=30)

    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="nickname", length=30)

    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    @Column(name="create_time", length=32)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="status", nullable=false)

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="email", length=100)

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
   








}