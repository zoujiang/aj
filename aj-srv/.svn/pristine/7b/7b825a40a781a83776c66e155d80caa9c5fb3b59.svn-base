package com.aj.bank.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TBank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_bank")

public class TBank  implements java.io.Serializable {


    // Fields    

     private String id;
     private String name;


    // Constructors

    /** default constructor */
    public TBank() {
    }

	/** minimal constructor */
    public TBank(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public TBank(String id, String name) {
        this.id = id;
        this.name = name;
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
    
    @Column(name="name", length=100)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   








}