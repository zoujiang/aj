package com.qm.entities;

public class RewardInfo {
    private Integer id;

    private Integer userType;

    private Integer kindergartenCategory;

    private Integer rewardInfo;

    private String rewardIcon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getKindergartenCategory() {
        return kindergartenCategory;
    }

    public void setKindergartenCategory(Integer kindergartenCategory) {
        this.kindergartenCategory = kindergartenCategory;
    }

    public Integer getRewardInfo() {
        return rewardInfo;
    }

    public void setRewardInfo(Integer rewardInfo) {
        this.rewardInfo = rewardInfo;
    }

    public String getRewardIcon() {
        return rewardIcon;
    }

    public void setRewardIcon(String rewardIcon) {
        this.rewardIcon = rewardIcon == null ? null : rewardIcon.trim();
    }
}