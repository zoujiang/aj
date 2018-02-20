package com.aj.sys.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TFeedback entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_feedback")
public class TFeedback implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String createdate;
	private String content;

	// Constructors

	/** default constructor */
	public TFeedback() {
	}

	/** minimal constructor */
	public TFeedback(Integer userId) {
		this.userId = userId;
	}

	/** full constructor */
	public TFeedback(Integer userId, String createdate, String content) {
		this.userId = userId;
		this.createdate = createdate;
		this.content = content;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "USER_ID", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "CREATEDATE", length = 30)
	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Column(name = "CONTENT", length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}