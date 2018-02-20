package com.aj.praylettermgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TPrayletter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_prayletter")
public class TPrayletter implements java.io.Serializable {

	// Fields

	private String id;
	private String babyUserId;
	private Integer prayUserId;
	private String content;
	private String audioUrl;
	private String createDate;
	private String picUrl;

	// Constructors

	/** default constructor */
	public TPrayletter() {
	}

	/** minimal constructor */
	public TPrayletter(String babyUserId, Integer prayUserId) {
		this.babyUserId = babyUserId;
		this.prayUserId = prayUserId;
	}

	/** full constructor */
	public TPrayletter(String babyUserId, Integer prayUserId, String content,
			String audioUrl, String createDate) {
		this.babyUserId = babyUserId;
		this.prayUserId = prayUserId;
		this.content = content;
		this.audioUrl = audioUrl;
		this.createDate = createDate;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "BABY_USER_ID", nullable = false)
	public String getBabyUserId() {
		return this.babyUserId;
	}

	public void setBabyUserId(String babyUserId) {
		this.babyUserId = babyUserId;
	}

	@Column(name = "PRAY_USER_ID", nullable = false)
	public Integer getPrayUserId() {
		return this.prayUserId;
	}

	public void setPrayUserId(Integer prayUserId) {
		this.prayUserId = prayUserId;
	}

	@Column(name = "CONTENT", length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "AUDIO_URL", length = 100)
	public String getAudioUrl() {
		return this.audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	@Column(name = "CREATE_DATE", length = 30)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "PIC_URL")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}