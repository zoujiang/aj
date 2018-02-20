package com.aj.discussiongroup.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TDiscussionGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_discussion_group")
public class TDiscussionGroup implements java.io.Serializable {

	// Fields

	private String discussionId;
	private String discussionType;
	private String discussionName;
	private String discussionDesc;
	private String createUserId;
	private String createDate;
	private String easemobGroupId;

	// Constructors

	/** default constructor */
	public TDiscussionGroup() {
	}

	/** minimal constructor */
	public TDiscussionGroup(String discussionId, String discussionType,
			String discussionName) {
		this.discussionId = discussionId;
		this.discussionType = discussionType;
		this.discussionName = discussionName;
	}

	/** full constructor */
	public TDiscussionGroup(String discussionId, String discussionType,
			String discussionName, String discussionDesc) {
		this.discussionId = discussionId;
		this.discussionType = discussionType;
		this.discussionName = discussionName;
		this.discussionDesc = discussionDesc;
	}

	// Property accessors
	@Id
	@Column(name = "discussionId", unique = true, nullable = false, length = 64)
	public String getDiscussionId() {
		return this.discussionId;
	}

	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

	@Column(name = "discussionType", nullable = false, length = 20)
	public String getDiscussionType() {
		return this.discussionType;
	}

	public void setDiscussionType(String discussionType) {
		this.discussionType = discussionType;
	}

	@Column(name = "discussionName", nullable = false, length = 50)
	public String getDiscussionName() {
		return this.discussionName;
	}

	public void setDiscussionName(String discussionName) {
		this.discussionName = discussionName;
	}

	@Column(name = "discussionDesc", length = 200)
	public String getDiscussionDesc() {
		return this.discussionDesc;
	}

	public void setDiscussionDesc(String discussionDesc) {
		this.discussionDesc = discussionDesc;
	}
	@Column(name = "create_user_id", length = 64)
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name = "create_date", length = 30)
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name = "easemob_group_id", length = 100)
	public String getEasemobGroupId() {
		return easemobGroupId;
	}

	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
	}

}