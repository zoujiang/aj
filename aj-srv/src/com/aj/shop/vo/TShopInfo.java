package com.aj.shop.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TShopInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_shop_info")

public class TShopInfo  implements java.io.Serializable {


    // Fields    

     private String id;
     private String shopName;
     private String idCard;
     private String address;
     private String shopCategoryId;
     private String gps;
     private String bankId;
     private String bankCardNo;
     private String tel;
     private String bankCardName;
     private Float percentage;
     private String serviceBeginTime;
     private String serviceEndTime;
     private String logo;
     private String showPic;
     private String description;
     private String createTime;
     private Integer status;
     private Integer zoneSize;
     private Integer showInFirst;
     private Integer isRecommend;


    // Constructors

    /** default constructor */
    public TShopInfo() {
    }

	/** minimal constructor */
    public TShopInfo(String id, String shopName, Integer showInFirst) {
        this.id = id;
        this.shopName = shopName;
        this.showInFirst = showInFirst;
    }
    
    /** full constructor */
    public TShopInfo(String id, String shopName, String idCard, String address, String shopCategoryId, String gps, String bankId, String bankCardNo, String tel, String bankCardName, Float percentage, String serviceBeginTime, String serviceEndTime, String logo, String showPic, String description, String createTime, Integer status, Integer zoneSize, Integer showInFirst, Integer isRecommend) {
        this.id = id;
        this.shopName = shopName;
        this.idCard = idCard;
        this.address = address;
        this.shopCategoryId = shopCategoryId;
        this.gps = gps;
        this.bankId = bankId;
        this.bankCardNo = bankCardNo;
        this.tel = tel;
        this.bankCardName = bankCardName;
        this.percentage = percentage;
        this.serviceBeginTime = serviceBeginTime;
        this.serviceEndTime = serviceEndTime;
        this.logo = logo;
        this.showPic = showPic;
        this.description = description;
        this.createTime = createTime;
        this.status = status;
        this.zoneSize = zoneSize;
        this.showInFirst = showInFirst;
        this.isRecommend = isRecommend;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="id", unique=true, nullable=false, length=64)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="shop_name", nullable=false, length=200)

    public String getShopName() {
        return this.shopName;
    }
    
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
    @Column(name="id_card", length=20)

    public String getIdCard() {
        return this.idCard;
    }
    
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    @Column(name="address", length=500)

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="shop_category_id", length=64)

    public String getShopCategoryId() {
        return this.shopCategoryId;
    }
    
    public void setShopCategoryId(String shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }
    
    @Column(name="gps", length=30)

    public String getGps() {
        return this.gps;
    }
    
    public void setGps(String gps) {
        this.gps = gps;
    }
    
    @Column(name="bank_id", length=64)

    public String getBankId() {
        return this.bankId;
    }
    
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
    
    @Column(name="bank_card_no", length=30)

    public String getBankCardNo() {
        return this.bankCardNo;
    }
    
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
    
    @Column(name="tel", length=30)

    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="bank_card_name", length=100)

    public String getBankCardName() {
        return this.bankCardName;
    }
    
    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }
    
    @Column(name="percentage", precision=12, scale=0)

    public Float getPercentage() {
        return this.percentage;
    }
    
    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }
    
    @Column(name="service_begin_time", length=32)

    public String getServiceBeginTime() {
        return this.serviceBeginTime;
    }
    
    public void setServiceBeginTime(String serviceBeginTime) {
        this.serviceBeginTime = serviceBeginTime;
    }
    
    @Column(name="service_end_time", length=32)

    public String getServiceEndTime() {
        return this.serviceEndTime;
    }
    
    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }
    
    @Column(name="logo", length=100)

    public String getLogo() {
        return this.logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    @Column(name="show_pic", length=500)

    public String getShowPic() {
        return this.showPic;
    }
    
    public void setShowPic(String showPic) {
        this.showPic = showPic;
    }
    
    @Column(name="description", length=1000)

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="create_time", length=32)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="status")

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="zone_size")

    public Integer getZoneSize() {
        return this.zoneSize;
    }
    
    public void setZoneSize(Integer zoneSize) {
        this.zoneSize = zoneSize;
    }
    
    @Column(name="show_in_first", nullable=false)

    public Integer getShowInFirst() {
        return this.showInFirst;
    }
    
    public void setShowInFirst(Integer showInFirst) {
        this.showInFirst = showInFirst;
    }
    
    @Column(name="is_recommend")

    public Integer getIsRecommend() {
        return this.isRecommend;
    }
    
    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }
   








}