package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenBrand entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_brand")

public class TKindergartenBrand  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String brandName;
     private String brandIcon;
     private Integer brandSort;
     private Integer brandState;


    // Constructors

    /** default constructor */
    public TKindergartenBrand() {
    }

	/** minimal constructor */
    public TKindergartenBrand(String brandName, Integer brandState) {
        this.brandName = brandName;
        this.brandState = brandState;
    }
    
    /** full constructor */
    public TKindergartenBrand(String brandName, String brandIcon, Integer brandSort, Integer brandState) {
        this.brandName = brandName;
        this.brandIcon = brandIcon;
        this.brandSort = brandSort;
        this.brandState = brandState;
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
    
    @Column(name="brand_name", nullable=false, length=128)

    public String getBrandName() {
        return this.brandName;
    }
    
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    @Column(name="brand_icon", length=128)

    public String getBrandIcon() {
        return this.brandIcon;
    }
    
    public void setBrandIcon(String brandIcon) {
        this.brandIcon = brandIcon;
    }
    
    @Column(name="brand_sort")

    public Integer getBrandSort() {
        return this.brandSort;
    }
    
    public void setBrandSort(Integer brandSort) {
        this.brandSort = brandSort;
    }
    
    @Column(name="brand_state", nullable=false)

    public Integer getBrandState() {
        return this.brandState;
    }
    
    public void setBrandState(Integer brandState) {
        this.brandState = brandState;
    }
   








}