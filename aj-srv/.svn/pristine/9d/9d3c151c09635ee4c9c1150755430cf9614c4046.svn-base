package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TShow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_show")

public class TShow  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String name;
     private Integer type;
     private Integer category;
     private String imageUrl;
     private String videoUrl;
     private String createUser;
     private String createTime;
     private String shopId;


    // Constructors

    /** default constructor */
    public TShow() {
    }

	/** minimal constructor */
    public TShow(String shopId) {
        this.shopId = shopId;
    }
    
    /** full constructor */
    public TShow(String name, Integer type, Integer category, String imageUrl, String videlUrl, String createUser, String createTime, String shopId) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.imageUrl = imageUrl;
        this.videoUrl = videlUrl;
        this.createUser = createUser;
        this.createTime = createTime;
        this.shopId = shopId;
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
    
    @Column(name="name", length=128)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="type")

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    @Column(name="category")

    public Integer getCategory() {
        return this.category;
    }
    
    public void setCategory(Integer category) {
        this.category = category;
    }
    
    @Column(name="imageUrl", length=128)

    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    @Column(name="videoUrl", length=128)

    public String getVideoUrl() {
        return this.videoUrl;
    }
    
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
    
    @Column(name="shop_id", nullable=false, length=64)

    public String getShopId() {
        return this.shopId;
    }
    
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
   








}