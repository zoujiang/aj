package com.aj.pay.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * TShopAlbumOrderId entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name="t_shop_album_order")
public class TShopAlbumOrder  implements java.io.Serializable {


    // Fields    

     private String id;
     private String albumId;
     private Integer status;
     private String createTime;
     private String payType;
     private String prePayId;
     private String orderNo;
     private String goodsName;
     private Integer totalFee;
     private String errCode;
     private String errCodeDes;
     private String bankType;
     private String transactionId;
     private String payComplateTime;


    // Constructors

    /** default constructor */
    public TShopAlbumOrder() {
    }

	/** minimal constructor */
    public TShopAlbumOrder(String id, String albumId, Integer status, String createTime, String payType, String orderNo, Integer totalFee) {
        this.id = id;
        this.albumId = albumId;
        this.status = status;
        this.createTime = createTime;
        this.payType = payType;
        this.orderNo = orderNo;
        this.totalFee = totalFee;
    }
    
    /** full constructor */
    public TShopAlbumOrder(String id, String albumId, Integer status, String createTime, String payType, String prePayId, String orderNo, String goodsName, Integer totalFee, String errCode, String errCodeDes, String bankType, String transactionId, String payComplateTime) {
        this.id = id;
        this.albumId = albumId;
        this.status = status;
        this.createTime = createTime;
        this.payType = payType;
        this.prePayId = prePayId;
        this.orderNo = orderNo;
        this.goodsName = goodsName;
        this.totalFee = totalFee;
        this.errCode = errCode;
        this.errCodeDes = errCodeDes;
        this.bankType = bankType;
        this.transactionId = transactionId;
        this.payComplateTime = payComplateTime;
    }

   
    // Property accessors
	@Id
    @Column(name="id", nullable=false, length=64)
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    @Column(name="album_id", nullable=false, length=64)

    public String getAlbumId() {
        return this.albumId;
    }
    
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    @Column(name="status", nullable=false)

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name="create_time", nullable=false, length=32)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name="pay_type", nullable=false, length=1)

    public String getPayType() {
        return this.payType;
    }
    
    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Column(name="pre_pay_id", length=64)

    public String getPrePayId() {
        return this.prePayId;
    }
    
    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }

    @Column(name="order_no", nullable=false, length=64)

    public String getOrderNo() {
        return this.orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name="goods_name", length=200)

    public String getGoodsName() {
        return this.goodsName;
    }
    
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Column(name="total_fee", nullable=false)

    public Integer getTotalFee() {
        return this.totalFee;
    }
    
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    @Column(name="err_code", length=32)

    public String getErrCode() {
        return this.errCode;
    }
    
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    @Column(name="err_code_des", length=128)

    public String getErrCodeDes() {
        return this.errCodeDes;
    }
    
    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    @Column(name="bank_type", length=32)

    public String getBankType() {
        return this.bankType;
    }
    
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    @Column(name="transaction_id", length=64)

    public String getTransactionId() {
        return this.transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Column(name="pay_complate_time", length=32)

    public String getPayComplateTime() {
        return this.payComplateTime;
    }
    
    public void setPayComplateTime(String payComplateTime) {
        this.payComplateTime = payComplateTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TShopAlbumOrder) ) return false;
		 TShopAlbumOrder castOther = ( TShopAlbumOrder ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getAlbumId()==castOther.getAlbumId()) || ( this.getAlbumId()!=null && castOther.getAlbumId()!=null && this.getAlbumId().equals(castOther.getAlbumId()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getCreateTime()==castOther.getCreateTime()) || ( this.getCreateTime()!=null && castOther.getCreateTime()!=null && this.getCreateTime().equals(castOther.getCreateTime()) ) )
 && ( (this.getPayType()==castOther.getPayType()) || ( this.getPayType()!=null && castOther.getPayType()!=null && this.getPayType().equals(castOther.getPayType()) ) )
 && ( (this.getPrePayId()==castOther.getPrePayId()) || ( this.getPrePayId()!=null && castOther.getPrePayId()!=null && this.getPrePayId().equals(castOther.getPrePayId()) ) )
 && ( (this.getOrderNo()==castOther.getOrderNo()) || ( this.getOrderNo()!=null && castOther.getOrderNo()!=null && this.getOrderNo().equals(castOther.getOrderNo()) ) )
 && ( (this.getGoodsName()==castOther.getGoodsName()) || ( this.getGoodsName()!=null && castOther.getGoodsName()!=null && this.getGoodsName().equals(castOther.getGoodsName()) ) )
 && ( (this.getTotalFee()==castOther.getTotalFee()) || ( this.getTotalFee()!=null && castOther.getTotalFee()!=null && this.getTotalFee().equals(castOther.getTotalFee()) ) )
 && ( (this.getErrCode()==castOther.getErrCode()) || ( this.getErrCode()!=null && castOther.getErrCode()!=null && this.getErrCode().equals(castOther.getErrCode()) ) )
 && ( (this.getErrCodeDes()==castOther.getErrCodeDes()) || ( this.getErrCodeDes()!=null && castOther.getErrCodeDes()!=null && this.getErrCodeDes().equals(castOther.getErrCodeDes()) ) )
 && ( (this.getBankType()==castOther.getBankType()) || ( this.getBankType()!=null && castOther.getBankType()!=null && this.getBankType().equals(castOther.getBankType()) ) )
 && ( (this.getTransactionId()==castOther.getTransactionId()) || ( this.getTransactionId()!=null && castOther.getTransactionId()!=null && this.getTransactionId().equals(castOther.getTransactionId()) ) )
 && ( (this.getPayComplateTime()==castOther.getPayComplateTime()) || ( this.getPayComplateTime()!=null && castOther.getPayComplateTime()!=null && this.getPayComplateTime().equals(castOther.getPayComplateTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + ( getAlbumId() == null ? 0 : this.getAlbumId().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getCreateTime() == null ? 0 : this.getCreateTime().hashCode() );
         result = 37 * result + ( getPayType() == null ? 0 : this.getPayType().hashCode() );
         result = 37 * result + ( getPrePayId() == null ? 0 : this.getPrePayId().hashCode() );
         result = 37 * result + ( getOrderNo() == null ? 0 : this.getOrderNo().hashCode() );
         result = 37 * result + ( getGoodsName() == null ? 0 : this.getGoodsName().hashCode() );
         result = 37 * result + ( getTotalFee() == null ? 0 : this.getTotalFee().hashCode() );
         result = 37 * result + ( getErrCode() == null ? 0 : this.getErrCode().hashCode() );
         result = 37 * result + ( getErrCodeDes() == null ? 0 : this.getErrCodeDes().hashCode() );
         result = 37 * result + ( getBankType() == null ? 0 : this.getBankType().hashCode() );
         result = 37 * result + ( getTransactionId() == null ? 0 : this.getTransactionId().hashCode() );
         result = 37 * result + ( getPayComplateTime() == null ? 0 : this.getPayComplateTime().hashCode() );
         return result;
   }   





}