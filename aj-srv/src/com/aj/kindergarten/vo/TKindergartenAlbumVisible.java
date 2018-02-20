package com.aj.kindergarten.vo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenAlbumVisible entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_album_visible")

public class TKindergartenAlbumVisible  implements java.io.Serializable {


	// Fields    
	private Integer id;
    private Integer albumId;
    private String familyId;
    private Integer visibleProperty;

    
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="album_id", nullable=false)

    public Integer getAlbumId() {
        return this.albumId;
    }
    
    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    @Column(name="family_id", nullable=false, length=64)

    public String getFamilyId() {
        return this.familyId;
    }
    
    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    @Column(name="visible_property", nullable=false)

    public Integer getVisibleProperty() {
        return this.visibleProperty;
    }
    
    public void setVisibleProperty(Integer visibleProperty) {
        this.visibleProperty = visibleProperty;
    }
   
    





}