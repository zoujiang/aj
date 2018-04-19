package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenAlbun entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_album")

public class TKindergartenAlbum  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String albumName;
     private String albumUrl;
     private String albumDesc;
     private String createTime;
     private String createUser;
     private Integer shcoolId;
     private Integer gradeId;
     private Integer student;
     private String currentGradeName;
     private Integer type;
     private String downloadUrl;
     private String downloadSecret;


    // Constructors

    /** default constructor */
    public TKindergartenAlbum() {
    }

	/** minimal constructor */
    public TKindergartenAlbum(String albumName, String createTime, String createUser, Integer type) {
        this.albumName = albumName;
        this.createTime = createTime;
        this.createUser = createUser;
        this.type = type;
    }
    
    /** full constructor */
    public TKindergartenAlbum(String albumName, String albumUrl, String albumDesc, String createTime, String createUser, Integer shcoolId, Integer gradeId, Integer student, String currentGradeName, Integer type) {
        this.albumName = albumName;
        this.albumUrl = albumUrl;
        this.albumDesc = albumDesc;
        this.createTime = createTime;
        this.createUser = createUser;
        this.shcoolId = shcoolId;
        this.gradeId = gradeId;
        this.student = student;
        this.currentGradeName = currentGradeName;
        this.type = type;
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
    
    @Column(name="album_name", nullable=false, length=128)

    public String getAlbumName() {
        return this.albumName;
    }
    
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    
    @Column(name="album_url", length=128)

    public String getAlbumUrl() {
        return this.albumUrl;
    }
    
    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }
    
    @Column(name="album_desc", length=500)

    public String getAlbumDesc() {
        return this.albumDesc;
    }
    
    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }
    
    @Column(name="create_time", nullable=false, length=32)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="create_user", nullable=false, length=64)

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    @Column(name="shcool_id")

    public Integer getShcoolId() {
        return this.shcoolId;
    }
    
    public void setShcoolId(Integer shcoolId) {
        this.shcoolId = shcoolId;
    }
    
    @Column(name="grade_id")

    public Integer getGradeId() {
        return this.gradeId;
    }
    
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
    
    @Column(name="student")

    public Integer getStudent() {
        return this.student;
    }
    
    public void setStudent(Integer student) {
        this.student = student;
    }
    
    @Column(name="current_grade_name", length=32)

    public String getCurrentGradeName() {
        return this.currentGradeName;
    }
    
    public void setCurrentGradeName(String currentGradeName) {
        this.currentGradeName = currentGradeName;
    }
    
    @Column(name="type", nullable=false)

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name="download_url", length=128)
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	 @Column(name="download_secret", length=10)
	public String getDownloadSecret() {
		return downloadSecret;
	}

	public void setDownloadSecret(String downloadSecret) {
		this.downloadSecret = downloadSecret;
	}


}