package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenPhoto entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_photo")

public class TKindergartenPhoto  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer type;
     private Integer category;
     private String photoUrl;
     private String videoUrl;
     private Integer albumId;
     private String createTime;
     private String createUser;
     private Integer digNum;
     private Integer commentNum;
     private String name;
     private String ownerId;
     private String digRelationUerId;


    // Constructors

    /** default constructor */
    public TKindergartenPhoto() {
    }

	/** minimal constructor */
    public TKindergartenPhoto(Integer type, Integer category, Integer digNum, Integer commentNum, String ownerId) {
        this.type = type;
        this.category = category;
        this.digNum = digNum;
        this.commentNum = commentNum;
        this.ownerId = ownerId;
    }
    
    /** full constructor */
    public TKindergartenPhoto(Integer type, Integer category, String photoUrl, String videoUrl, Integer albumId, String createTime, String createUser, Integer digNum, Integer commentNum, String name, String ownerId) {
        this.type = type;
        this.category = category;
        this.photoUrl = photoUrl;
        this.videoUrl = videoUrl;
        this.albumId = albumId;
        this.createTime = createTime;
        this.createUser = createUser;
        this.digNum = digNum;
        this.commentNum = commentNum;
        this.name = name;
        this.ownerId = ownerId;
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
    
    @Column(name="type", nullable=false)

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    @Column(name="category", nullable=false)

    public Integer getCategory() {
        return this.category;
    }
    
    public void setCategory(Integer category) {
        this.category = category;
    }
    
    @Column(name="photo_url", length=128)

    public String getPhotoUrl() {
        return this.photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    @Column(name="video_url", length=128)

    public String getVideoUrl() {
        return this.videoUrl;
    }
    
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    @Column(name="album_id")

    public Integer getAlbumId() {
        return this.albumId;
    }
    
    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }
    
    @Column(name="create_time", length=30)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="create_user", length=64)

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    @Column(name="dig_num", nullable=false)

    public Integer getDigNum() {
        return this.digNum;
    }
    
    public void setDigNum(Integer digNum) {
        this.digNum = digNum;
    }
    
    @Column(name="comment_num", nullable=false)

    public Integer getCommentNum() {
        return this.commentNum;
    }
    
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
    
    @Column(name="name", length=128)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="owner_id", nullable=false, length=64)

    public String getOwnerId() {
        return this.ownerId;
    }
    
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name="dig_relation_user_id",  length=1000)
    public String getDigRelationUerId() {
        return digRelationUerId;
    }

    public void setDigRelationUerId(String digRelationUerId) {
        this.digRelationUerId = digRelationUerId;
    }
}