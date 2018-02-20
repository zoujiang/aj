package com.aj.shop.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TShopAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_shop_customer_user")

public class TShopCustomerUser  implements java.io.Serializable {


    // Fields    

     private String id;
     private String shopId;
     private String userId;
     private String userName;
     private String userTel;
     private Integer createTime;


   
   
    // Property accessors
    @Id 
    
    @Column(name="id", unique=true, nullable=false, length=64)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    @Column(name="shop_id", length=64)
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	 @Column(name="user_id", length=64)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	 @Column(name="user_name", length=64)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	 @Column(name="user_tel", length=64)
	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	 @Column(name="create_time", length=64)
	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
    
   
}