package com.aj.discussiongroup.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TDiscussionGroupUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_discussion_group_user")
public class TDiscussionGroupUser implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String discussionGroupId;
	private String homeId;

	// Constructors

	/** default constructor */
	public TDiscussionGroupUser() {
	}

	/** minimal constructor */
	public TDiscussionGroupUser(String id) {
		this.id = id;
	}

	/** full constructor */
	public TDiscussionGroupUser(String id, String userId,
			String discussionGroupId) {
		this.id = id;
		this.userId = userId;
		this.discussionGroupId = discussionGroupId;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "user_id", length = 64)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "discussion_group_id", length = 64)
	public String getDiscussionGroupId() {
		return this.discussionGroupId;
	}

	public void setDiscussionGroupId(String discussionGroupId) {
		this.discussionGroupId = discussionGroupId;
	}
	@Column(name = "home_id", length = 64)
	public String getHomeId() {
		return homeId;
	}

	public void setHomeId(String homeId) {
		this.homeId = homeId;
	}

}