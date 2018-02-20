package com.aj.coupon.vo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_coupon_shop_info")
public class TCouponShopInfoEntity {
    private String id;
    private String shopName;
    private String address;
    private String shopCategory;
    private String gps;
    private String tel;
    private String serviceBeginTime;
    private String serviceEndTime;
    private String logo;
    private String showPic;
    private String description;
    private String createTime;
    private Integer status;
    private int isRecommend;
    private String registName;
    private String registTel;
    private String recommendIx;
    private String brandId;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "shop_category")
    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    @Basic
    @Column(name = "gps")
    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    @Basic
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "service_begin_time")
    public String getServiceBeginTime() {
        return serviceBeginTime;
    }

    public void setServiceBeginTime(String serviceBeginTime) {
        this.serviceBeginTime = serviceBeginTime;
    }

    @Basic
    @Column(name = "service_end_time")
    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "show_pic")
    public String getShowPic() {
        return showPic;
    }

    public void setShowPic(String showPic) {
        this.showPic = showPic;
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
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "regist_name")
    public String getRegistName() {
        return registName;
    }

    public void setRegistName(String registName) {
        this.registName = registName;
    }

    @Basic
    @Column(name = "regist_tel")
    public String getRegistTel() {
        return registTel;
    }

    public void setRegistTel(String registTel) {
        this.registTel = registTel;
    }

    @Basic
    @Column(name = "recommend_ix")
    public String getRecommendIx() {
        return recommendIx;
    }

    public void setRecommendIx(String recommendIx) {
        this.recommendIx = recommendIx;
    }

    @Basic
    @Column(name = "brand_id")
    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TCouponShopInfoEntity that = (TCouponShopInfoEntity) o;
        return isRecommend == that.isRecommend &&
                Objects.equals(id, that.id) &&
                Objects.equals(shopName, that.shopName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(shopCategory, that.shopCategory) &&
                Objects.equals(gps, that.gps) &&
                Objects.equals(tel, that.tel) &&
                Objects.equals(serviceBeginTime, that.serviceBeginTime) &&
                Objects.equals(serviceEndTime, that.serviceEndTime) &&
                Objects.equals(logo, that.logo) &&
                Objects.equals(showPic, that.showPic) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(registName, that.registName) &&
                Objects.equals(registTel, that.registTel) &&
                Objects.equals(recommendIx, that.recommendIx) &&
                Objects.equals(brandId, that.brandId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopName, address, shopCategory, gps, tel, serviceBeginTime, serviceEndTime, logo, showPic, description, createTime, status, isRecommend, registName, registTel, recommendIx, brandId);
    }
}
