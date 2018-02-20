package com.aj.photomgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TComment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_comment")
public class TComment implements java.io.Serializable {

	// Fields

	private Integer id;
	private String content;
	private Integer createUserId;
	private String createDate;
	private String photoId;
	private Integer isReply;
	private Integer replyUserId;
	private String replyCommentId;

	// Constructors

	/** default constructor */
	public TComment() {
	}

	/** minimal constructor */
	public TComment(Integer createUserId, String createDate) {
		this.createUserId = createUserId;
		this.createDate = createDate;
	}

	/** full constructor */
	public TComment(String content, Integer createUserId, String createDate,
			String photoId, Integer isReply, Integer replyUserId, String replyCommentId) {
		this.content = content;
		this.createUserId = createUserId;
		this.createDate = createDate;
		this.photoId = photoId;
		this.isReply = isReply;
		this.replyUserId = replyUserId;
		this.replyCommentId = replyCommentId;
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

	@Column(name = "CONTENT", length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CREATE_USER_ID", nullable = false)
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "CREATE_DATE", nullable = false, length = 30)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "PHOTO_ID")
	public String getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	@Column(name = "IS_REPLY")
	public Integer getIsReply() {
		return this.isReply;
	}

	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}

	@Column(name = "REPLY_USER_ID")
	public Integer getReplyUserId() {
		return this.replyUserId;
	}

	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}
	@Column(name = "REPLY_COMMENT_ID")
	public String getReplyCommentId() {
		return replyCommentId;
	}

	public void setReplyCommentId(String replyCommentId) {
		this.replyCommentId = replyCommentId;
	}

	
}