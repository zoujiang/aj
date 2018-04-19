package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TKindergartenTaskInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_kindergarten_task_info")

public class TKindergartenTaskInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String taskDate;
	private Integer userId;
	private Integer teacherId;
	private String teacherName;
	private Integer visitGradeNum;
	private Integer userType;
	private Integer photoNum;
	private Integer videoNum;
	private Integer isGetReward;
	private String remark;
	private String renewPhone;
	private Integer isSend;
	private String sendTime;
	private String updateTime;
	private String totalReward;

	// Constructors

	/** default constructor */
	public TKindergartenTaskInfo() {
	}

	/** minimal constructor */
	public TKindergartenTaskInfo(String taskDate) {
		this.taskDate = taskDate;
	}

	/** full constructor */
	public TKindergartenTaskInfo(String taskDate, Integer userId, Integer teacherId, String teacherName,
			Integer visitGradeNum, Integer userType, Integer photoNum, Integer videoNum, Integer isGetReward,
			String remark, String renewPhone, Integer isSend, String sendTime, String updateTime, String totalReward) {
		this.taskDate = taskDate;
		this.userId = userId;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.visitGradeNum = visitGradeNum;
		this.userType = userType;
		this.photoNum = photoNum;
		this.videoNum = videoNum;
		this.isGetReward = isGetReward;
		this.remark = remark;
		this.renewPhone = renewPhone;
		this.isSend = isSend;
		this.sendTime = sendTime;
		this.updateTime = updateTime;
		this.totalReward = totalReward;
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

	@Column(name = "task_date", nullable = false, length = 32)

	public String getTaskDate() {
		return this.taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	@Column(name = "user_id")

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "teacher_id")

	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "teacher_name", length = 30)

	public String getTeacherName() {
		return this.teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Column(name = "visit_grade_num")

	public Integer getVisitGradeNum() {
		return this.visitGradeNum;
	}

	public void setVisitGradeNum(Integer visitGradeNum) {
		this.visitGradeNum = visitGradeNum;
	}

	@Column(name = "user_type")

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Column(name = "photo_num")

	public Integer getPhotoNum() {
		return this.photoNum;
	}

	public void setPhotoNum(Integer photoNum) {
		this.photoNum = photoNum;
	}

	@Column(name = "video_num")

	public Integer getVideoNum() {
		return this.videoNum;
	}

	public void setVideoNum(Integer videoNum) {
		this.videoNum = videoNum;
	}

	@Column(name = "is_get_reward")

	public Integer getIsGetReward() {
		return this.isGetReward;
	}

	public void setIsGetReward(Integer isGetReward) {
		this.isGetReward = isGetReward;
	}

	@Column(name = "remark", length = 126)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "renew_phone", length = 20)

	public String getRenewPhone() {
		return this.renewPhone;
	}

	public void setRenewPhone(String renewPhone) {
		this.renewPhone = renewPhone;
	}

	@Column(name = "is_send")

	public Integer getIsSend() {
		return this.isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	@Column(name = "send_time", length = 32)

	public String getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "update_time", length = 32)

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "total_reward", length = 10)

	public String getTotalReward() {
		return this.totalReward;
	}

	public void setTotalReward(String totalReward) {
		this.totalReward = totalReward;
	}

}