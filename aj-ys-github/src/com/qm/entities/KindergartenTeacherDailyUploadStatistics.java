package com.qm.entities;

import com.frame.core.util.pagination.key.LimitKey;

public class KindergartenTeacherDailyUploadStatistics extends LimitKey{
	private Integer id;

    private Integer teacherId;

    private String teacherName;

    private Integer photoNum;

    private Integer videoNum;

    private Integer honorNum;

    private String staticticsDate;
    
    private Integer kindergartenId;
    
    private Integer gradeId;
    
    private String kindergartenName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getHonorNum() {
        return honorNum;
    }

    public void setHonorNum(Integer honorNum) {
        this.honorNum = honorNum;
    }

    public String getStaticticsDate() {
        return staticticsDate;
    }

    public void setStaticticsDate(String staticticsDate) {
        this.staticticsDate = staticticsDate == null ? null : staticticsDate.trim();
    }

	public Integer getKindergartenId() {
		return kindergartenId;
	}

	public void setKindergartenId(Integer kindergartenId) {
		this.kindergartenId = kindergartenId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getKindergartenName() {
		return kindergartenName;
	}

	public void setKindergartenName(String kindergartenName) {
		this.kindergartenName = kindergartenName;
	}
    
    
}