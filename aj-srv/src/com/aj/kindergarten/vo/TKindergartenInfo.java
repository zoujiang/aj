package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_info")

public class TKindergartenInfo  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String name;
     private String brandId;
     private String tele;
     private String registName;
     private String address;
     private String serviceTel;
     private String gps;
     private Integer category;
     private String serviceStartTime;
     private String serviceEndTime;
     private Integer sortIndex;
     private Integer isRecommend;
     private String logo;
     private String showPics;
     private String description;
    private String createTime;
    private String createUser;
    /**
     * //1公办、2普惠民办、3民办
     */
    private Integer properties;
    private  String shopCategoryId;



    // Constructors

    /** default constructor */
    public TKindergartenInfo() {
    }

	/** minimal constructor */
    public TKindergartenInfo(String name, String brandId) {
        this.name = name;
        this.brandId = brandId;
    }
    
    /** full constructor */
    public TKindergartenInfo(String name, String brandId, String tele, String registName, String address, String serviceTel, String gps, Integer category, String serviceStartTime, String serviceEndTime, Integer sortIndex, Integer isRecommend, String logo, String showPics, String description, String createTime, String createUser) {
        this.name = name;
        this.brandId = brandId;
        this.tele = tele;
        this.registName = registName;
        this.address = address;
        this.serviceTel = serviceTel;
        this.gps = gps;
        this.category = category;
        this.serviceStartTime = serviceStartTime;
        this.serviceEndTime = serviceEndTime;
        this.sortIndex = sortIndex;
        this.isRecommend = isRecommend;
        this.logo = logo;
        this.showPics = showPics;
        this.description = description;
        this.createTime = createTime;
        this.createUser = createUser;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="name", nullable=false, length=128)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="brand_id", nullable=false)

    public String getBrandId() {
        return this.brandId;
    }
    
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
    
    @Column(name="tele", length=20)

    public String getTele() {
        return this.tele;
    }
    
    public void setTele(String tele) {
        this.tele = tele;
    }
    
    @Column(name="regist_name", length=20)

    public String getRegistName() {
        return this.registName;
    }
    
    public void setRegistName(String registName) {
        this.registName = registName;
    }
    
    @Column(name="address", length=300)

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="service_tel", length=50)

    public String getServiceTel() {
        return this.serviceTel;
    }
    
    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }
    
    @Column(name="gps", length=30)

    public String getGps() {
        return this.gps;
    }
    
    public void setGps(String gps) {
        this.gps = gps;
    }
    
    @Column(name="category")

    public Integer getCategory() {
        return this.category;
    }
    
    public void setCategory(Integer category) {
        this.category = category;
    }
    
    @Column(name="service_start_time", length=30)

    public String getServiceStartTime() {
        return this.serviceStartTime;
    }
    
    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }
    
    @Column(name="service_end_time", length=30)

    public String getServiceEndTime() {
        return this.serviceEndTime;
    }
    
    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }
    
    @Column(name="sort_index")

    public Integer getSortIndex() {
        return this.sortIndex;
    }
    
    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }
    
    @Column(name="is_recommend")

    public Integer getIsRecommend() {
        return this.isRecommend;
    }
    
    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }
    
    @Column(name="logo", length=200)

    public String getLogo() {
        return this.logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    @Column(name="show_pics", length=1000)

    public String getShowPics() {
        return this.showPics;
    }
    
    public void setShowPics(String showPics) {
        this.showPics = showPics;
    }
    
    @Column(name="description", length=4000)

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="create_time", length=30)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="create_user", length=64)

    public String getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name="properties", length=1)
    public Integer getProperties() {
        return properties;
    }

    public void setProperties(Integer properties) {
        this.properties = properties;
    }

    @Column(name="shop_category_id")
    public String getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(String shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }
}