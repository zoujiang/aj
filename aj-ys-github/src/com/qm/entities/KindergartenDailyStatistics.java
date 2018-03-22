package com.qm.entities;

public class KindergartenDailyStatistics {
    private Integer id;

    private Integer kindergartenId;

    private Integer photoNum;

    private Integer videoNum;

    private Integer honorNum;

    private String daily;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(Integer kindergartenId) {
        this.kindergartenId = kindergartenId;
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

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily == null ? null : daily.trim();
    }
}