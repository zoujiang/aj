package com.aj.photomgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPhoto entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_photo")
public class TPhoto implements java.io.Serializable {

	// Fields

	private String id;
	private String photoUrl;
	private String photoName;
	private String createDate;
	private Integer createUserId;
	private String albumId;
	private Integer isprivate;
	private String age;
	private String descript;
	private Integer digNum;
	private String digRelationUserId;
	private String babyId;
	private String sharedDate;
	private Integer sharedAlbumId;
	private String videoUrl;
	private String photoType;  //1:照片  2：视频

	// Constructors

	/** default constructor */
	public TPhoto() {
	}

	/** minimal constructor */
	public TPhoto(String photoUrl, Integer createUserId, String albumId) {
		this.photoUrl = photoUrl;
		this.createUserId = createUserId;
		this.albumId = albumId;
	}

	/** full constructor */
	public TPhoto(String photoUrl, String photoName, String createDate,
			Integer createUserId, String albumId, Integer isprivate,
			String age, String descript, Integer digNum,
			String digRelationUserId) {
		this.photoUrl = photoUrl;
		this.photoName = photoName;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.albumId = albumId;
		this.isprivate = isprivate;
		this.age = age;
		this.descript = descript;
		this.digNum = digNum;
		this.digRelationUserId = digRelationUserId;
	}

	// Property accessors
	@Id
	//@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PHOTO_URL", nullable = false, length = 100)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "PHOTO_NAME", length = 100)
	public String getPhotoName() {
		return this.photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	@Column(name = "CREATE_DATE", length = 30)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_USER_ID", nullable = false)
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "ALBUM_ID", nullable = false)
	public String getAlbumId() {
		return this.albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	@Column(name = "ISPRIVATE")
	public Integer getIsprivate() {
		return this.isprivate;
	}

	public void setIsprivate(Integer isprivate) {
		this.isprivate = isprivate;
	}

	@Column(name = "AGE", length = 20)
	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Column(name = "descript", length = 500)
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "dig_num")
	public Integer getDigNum() {
		return this.digNum;
	}

	public void setDigNum(Integer digNum) {
		this.digNum = digNum;
	}

	@Column(name = "dig_relation_user_id", length = 500)
	public String getDigRelationUserId() {
		return this.digRelationUserId;
	}

	public void setDigRelationUserId(String digRelationUserId) {
		this.digRelationUserId = digRelationUserId;
	}

	@Column(name = "BABY_ID")
	public String getBabyId() {
		return babyId;
	}
	public void setBabyId(String babyId) {
		this.babyId = babyId;
	}
	@Column(name = "SHAREDDATE", length = 30)
	public String getSharedDate() {
		return sharedDate;
	}

	public void setSharedDate(String sharedDate) {
		this.sharedDate = sharedDate;
	}

	@Column(name = "SHARED_ALBUM_ID")
	public Integer getSharedAlbumId() {
		return sharedAlbumId;
	}

	public void setSharedAlbumId(Integer sharedAlbumId) {
		this.sharedAlbumId = sharedAlbumId;
	}
	@Column(name = "VIDEO_URL" , length = 100)
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String vedioUrl) {
		this.videoUrl = vedioUrl;
	}
	@Column(name = "PHOTO_TYPE" , length = 1)
	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}
	
}