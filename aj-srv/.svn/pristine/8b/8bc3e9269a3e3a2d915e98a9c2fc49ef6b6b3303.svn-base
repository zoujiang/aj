package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_notice")

public class TKindergartenNotice  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String reportUserId;
     private String reportTime;
     private String receiveUserFamilyId;
     private String noticeContent;
     private Integer status;
     private Integer gradeId;


    // Constructors

    /** default constructor */
    public TKindergartenNotice() {
    }

	/** minimal constructor */
    public TKindergartenNotice(String reportUserId, String reportTime, String receiveUserFamilyId, Integer status) {
        this.reportUserId = reportUserId;
        this.reportTime = reportTime;
        this.receiveUserFamilyId = receiveUserFamilyId;
        this.status = status;
    }
    
    /** full constructor */
    public TKindergartenNotice(String reportUserId, String reportTime, String receiveUserFamilyId, String noticeContent, Integer status) {
        this.reportUserId = reportUserId;
        this.reportTime = reportTime;
        this.receiveUserFamilyId = receiveUserFamilyId;
        this.noticeContent = noticeContent;
        this.status = status;
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
    
    @Column(name="report_user_id", nullable=false, length=64)

    public String getReportUserId() {
        return this.reportUserId;
    }
    
    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }
    
    @Column(name="report_time", nullable=false, length=30)

    public String getReportTime() {
        return this.reportTime;
    }
    
    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
    
    @Column(name="receive_user_family_id", nullable=false, length=64)

    public String getReceiveUserFamilyId() {
        return this.receiveUserFamilyId;
    }
    
    public void setReceiveUserFamilyId(String receiveUserFamilyId) {
        this.receiveUserFamilyId = receiveUserFamilyId;
    }
    
    @Column(name="notice_content", length=1024)

    public String getNoticeContent() {
        return this.noticeContent;
    }
    
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
    
    @Column(name="status", nullable=false)

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name="grade_id", nullable=false)
    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
}