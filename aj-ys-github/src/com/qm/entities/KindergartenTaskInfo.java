package com.qm.entities;

import java.util.List;

import com.frame.core.util.pagination.key.LimitKey;

public class KindergartenTaskInfo extends LimitKey{
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
    
    private Integer kindergartenId;
    //多个userType
    private List<Integer> userTypes;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate == null ? null : taskDate.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public Integer getVisitGradeNum() {
        return visitGradeNum;
    }

    public void setVisitGradeNum(Integer visitGradeNum) {
        this.visitGradeNum = visitGradeNum;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getPhotoNum() {
        return photoNum;
    }

    public void setPhotoNum(Integer photoNum) {
        this.photoNum = photoNum;
    }

    public Integer getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(Integer videoNum) {
        this.videoNum = videoNum;
    }

    public Integer getIsGetReward() {
        return isGetReward;
    }

    public void setIsGetReward(Integer isGetReward) {
        this.isGetReward = isGetReward;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRenewPhone() {
        return renewPhone;
    }

    public void setRenewPhone(String renewPhone) {
        this.renewPhone = renewPhone == null ? null : renewPhone.trim();
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime == null ? null : sendTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

	public String getTotalReward() {
		return totalReward;
	}

	public void setTotalReward(String totalReward) {
		this.totalReward = totalReward;
	}

	public Integer getKindergartenId() {
		return kindergartenId;
	}

	public void setKindergartenId(Integer kindergartenId) {
		this.kindergartenId = kindergartenId;
	}

	public List<Integer> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<Integer> userTypes) {
		this.userTypes = userTypes;
	}
    
}