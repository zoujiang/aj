package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TRewardInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_reward_info")

public class TRewardInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userType;
	private Integer kindergartenCategory;
	private Integer rewardInfo;
	private String rewardIcon;

	// Constructors

	/** default constructor */
	public TRewardInfo() {
	}

	/** minimal constructor */
	public TRewardInfo(Integer userType, Integer kindergartenCategory) {
		this.userType = userType;
		this.kindergartenCategory = kindergartenCategory;
	}

	/** full constructor */
	public TRewardInfo(Integer userType, Integer kindergartenCategory, Integer rewardInfo, String rewardIcon) {
		this.userType = userType;
		this.kindergartenCategory = kindergartenCategory;
		this.rewardInfo = rewardInfo;
		this.rewardIcon = rewardIcon;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_type", nullable = false)

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Column(name = "kindergarten_category", nullable = false)

	public Integer getKindergartenCategory() {
		return this.kindergartenCategory;
	}

	public void setKindergartenCategory(Integer kindergartenCategory) {
		this.kindergartenCategory = kindergartenCategory;
	}

	@Column(name = "reward_info")

	public Integer getRewardInfo() {
		return this.rewardInfo;
	}

	public void setRewardInfo(Integer rewardInfo) {
		this.rewardInfo = rewardInfo;
	}

	@Column(name = "reward_icon", length = 128)

	public String getRewardIcon() {
		return this.rewardIcon;
	}

	public void setRewardIcon(String rewardIcon) {
		this.rewardIcon = rewardIcon;
	}

}