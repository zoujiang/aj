package com.aj.shop.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TShopAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_shop_account")

public class TShopAccount  implements java.io.Serializable {


    // Fields    

     private String id;
     private String username;
     private String password;
     private String shopId;
     private String tel;
     private String nickname;
     private String createTime;
     private Integer status;
     private String email;


    // Constructors

    /** default constructor */
    public TShopAccount() {
    }

	/** minimal constructor */
    public TShopAccount(String id, String username, Integer status) {
        this.id = id;
        this.username = username;
        this.status = status;
    }
    
    /** full constructor */
    public TShopAccount(String id, String username, String password, String shopId, String tel, String nickname, String createTime, Integer status, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.shopId = shopId;
        this.tel = tel;
        this.nickname = nickname;
        this.createTime = createTime;
        this.status = status;
        this.email = email;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="id", unique=true, nullable=false, length=64)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
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
    
    @Column(name="shop_id", length=64)

    public String getShopId() {
        return this.shopId;
    }
    
    public void setShopId(String shopId) {
        this.shopId = shopId;
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