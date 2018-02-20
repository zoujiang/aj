package com.aj.albummgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAlbumAccessInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_album_access_info")
public class TAlbumAccessInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String albumId;
	private String accessTime;
	private String accessUserId;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "album_id")
	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	@Column(name = "access_time")
	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	@Column(name = "access_user_id")
	public String getAccessUserId() {
		return accessUserId;
	}

	public void setAccessUserId(String accessUserId) {
		this.accessUserId = accessUserId;
	}

}