package com.aj.coupon.vo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_coupon_info")
public class TCouponInfoEntity {
    private int id;
    private String name;
    private int getCondition;
    private String startTime;
    private String endTime;
    private Integer totalNum;
    private Integer leftNum;
    private int isRecommend;
    private int recommendIdx;
    private String firstPagePic;
    private String tag;
    private String showImg;
    private int isLink;
    private String linkAddress;
    private String description;
    private String shopId;
    private int isValidate;
    private String createUser;
    private String createTime;
    private Integer limitNumber;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "get_condition")
    public int getGetCondition() {
        return getCondition;
    }

    public void setGetCondition(int getCondition) {
        this.getCondition = getCondition;
    }

    @Basic
    @Column(name = "start_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "total_num")
    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    @Basic
    @Column(name = "left_num")
    public Integer getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }

    @Basic
    @Column(name = "is_recommend")
    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    @Basic
    @Column(name = "recommend_idx")
    public int getRecommendIdx() {
        return recommendIdx;
    }

    public void setRecommendIdx(int recommendIdx) {
        this.recommendIdx = recommendIdx;
    }

    @Basic
    @Column(name = "first_page_pic")
    public String getFirstPagePic() {
        return firstPagePic;
    }

    public void setFirstPagePic(String firstPagePic) {
        this.firstPagePic = firstPagePic;
    }

    @Basic
    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Basic
    @Column(name = "show_img")
    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    @Basic
    @Column(name = "is_link")
    public int getIsLink() {
        return isLink;
    }

    public void setIsLink(int isLink) {
        this.isLink = isLink;
    }

    @Basic
    @Column(name = "link_address")
    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "shop_id")
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "is_validate")
    public int getIsValidate() {
        return isValidate;
    }

    public void setIsValidate(int isValidate) {
        this.isValidate = isValidate;
    }

    @Basic
    @Column(name = "create_user")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TCouponInfoEntity that = (TCouponInfoEntity) o;
        return id == that.id &&
                getCondition == that.getCondition &&
                isRecommend == that.isRecommend &&
                recommendIdx == that.recommendIdx &&
                isLink == that.isLink &&
                isValidate == that.isValidate &&
                Objects.equals(name, that.name) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(totalNum, that.totalNum) &&
                Objects.equals(leftNum, that.leftNum) &&
                Objects.equals(firstPagePic, that.firstPagePic) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(showImg, that.showImg) &&
                Objects.equals(linkAddress, that.linkAddress) &&
                Objects.equals(description, that.description) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, getCondition, startTime, endTime, totalNum, leftNum, isRecommend, recommendIdx, firstPagePic, tag, showImg, isLink, linkAddress, description, shopId, isValidate, createUser, createTime);
    }
    @Basic
    @Column(name = "limit_num")
    public Integer getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(Integer limitNumber) {
        this.limitNumber = limitNumber;
    }
}
