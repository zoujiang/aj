package com.aj.albummgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAlbum entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_album")
public class TAlbum implements java.io.Serializable {

	// Fields

	private String id;
	private String albumName;
	private String description;
	private Integer createUserId;
	private String createDate;
	private Integer isSysinit;
	private String albumUrl;
	private Integer albumType;
	private String familyId;
	private Integer ownerUserId;
	private String parentId;
	private Integer isDir;
	private String  familyName;  /*离婚专用，记录离婚对象的昵称*/
	private Integer hadInherit; /*是否已经传承 0 ： 未传承  1： 已传承*/
	private String category;
	private String babyUserId;  //爱的传承相册中孩子ID
	private Integer inheritTarget;  //传承对象ID, 
	private Integer visibleProperty; //可见属性 0: 个人可见  1:夫妻可见  2:所有可见

	// Constructors

	/** default constructor */
	public TAlbum() {
	}

	/** minimal constructor */
	public TAlbum(String albumName, Integer createUserId, Integer isSysinit) {
		this.albumName = albumName;
		this.createUserId = createUserId;
		this.isSysinit = isSysinit;
	}

	/** full constructor */
	public TAlbum(String albumName, String description, Integer createUserId,
			String createDate, Integer isSysinit) {
		this.albumName = albumName;
		this.description = description;
		this.createUserId = createUserId;
		this.createDate = createDate;
		this.isSysinit = isSysinit;
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

	@Column(name = "ALBUM_NAME", nullable = false, length = 100)
	public String getAlbumName() {
		return this.albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "CREATE_USER_ID", nullable = false)
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "CREATE_DATE", length = 30)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "IS_SYSINIT", nullable = false)
	public Integer getIsSysinit() {
		return this.isSysinit;
	}

	public void setIsSysinit(Integer isSysinit) {
		this.isSysinit = isSysinit;
	}

	@Column(name = "ALBUM_URL", length = 100)
	public String getAlbumUrl() {
		return albumUrl;
	}
	public void setAlbumUrl(String albumUrl) {
		this.albumUrl = albumUrl;
	}
	@Column(name = "ALBUM_TYPE")
	public Integer getAlbumType() {
		return albumType;
	}

	public void setAlbumType(Integer albumType) {
		this.albumType = albumType;
	}
	@Column(name = "FAMILY_ID", length = 100)
	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	@Column(name = "OWNER_USER_ID")
	public Integer getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Integer ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	@Column(name = "PARENT_ID")
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name = "IS_DIR")
	public Integer getIsDir() {
		return isDir;
	}

	public void setIsDir(Integer isDir) {
		this.isDir = isDir;
	}
	@Column(name = "FAMILY_NAME", length = 30)
	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	@Column(name = "HADINHERIT")
	public Integer getHadInherit() {
		return hadInherit;
	}

	public void setHadInherit(Integer hadInherit) {
		this.hadInherit = hadInherit;
	}
	
	@Column(name = "ALBUM_CATEGORY")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	@Column(name = "BABY_USER_ID")
	public String getBabyUserId() {
		return babyUserId;
	}
	
	public void setBabyUserId(String babyUserId) {
		this.babyUserId = babyUserId;
	}
	@Column(name = "INHERIT_TARGET")
	public Integer getInheritTarget() {
		return inheritTarget;
	}

	public void setInheritTarget(Integer inheritTarget) {
		this.inheritTarget = inheritTarget;
	}
	@Column(name = "VISIBLE_PROPERTY")
	public Integer getVisibleProperty() {
		return visibleProperty;
	}

	public void setVisibleProperty(Integer visibleProperty) {
		this.visibleProperty = visibleProperty;
	}
	

}