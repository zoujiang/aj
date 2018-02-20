package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TRewardInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_reward_info")

public class TRewardInfo  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String rewardCondition;
     private String rewardContent;
     private Integer imageNum;
     private Integer videoNum;


    // Constructors

    /** default constructor */
    public TRewardInfo() {
    }

    
    /** full constructor */
    public TRewardInfo(String rewardCondition, String rewardContent, Integer imageNum, Integer videoNum) {
        this.rewardCondition = rewardCondition;
        this.rewardContent = rewardContent;
        this.imageNum = imageNum;
        this.videoNum = videoNum;
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
    
    @Column(name="reward_condition", length=2)

    public String getRewardCondition() {
        return this.rewardCondition;
    }
    
    public void setRewardCondition(String rewardCondition) {
        this.rewardCondition = rewardCondition;
    }
    
    @Column(name="reward_content", length=200)

    public String getRewardContent() {
        return this.rewardContent;
    }
    
    public void setRewardContent(String rewardContent) {
        this.rewardContent = rewardContent;
    }
    
    @Column(name="image_num")

    public Integer getImageNum() {
        return this.imageNum;
    }
    
    public void setImageNum(Integer imageNum) {
        this.imageNum = imageNum;
    }
    
    @Column(name="video_num")

    public Integer getVideoNum() {
        return this.videoNum;
    }
    
    public void setVideoNum(Integer videoNum) {
        this.videoNum = videoNum;
    }
   








}