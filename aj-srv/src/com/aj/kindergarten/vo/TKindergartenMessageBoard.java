package com.aj.kindergarten.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TKindergartenMessageBoard entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_kindergarten_message_board")

public class TKindergartenMessageBoard  implements java.io.Serializable {


    // Fields    

     private Integer messageId;
     private String userId;
     private Integer userType;
     private Integer messageTye;
     private String messageContent;
     private String createTime;
     private Integer isReply;
     private Integer replyMessageId;
     private Integer albumId;


    // Constructors

    /** default constructor */
    public TKindergartenMessageBoard() {
    }

	/** minimal constructor */
    public TKindergartenMessageBoard(String userId, Integer isReply) {
        this.userId = userId;
        this.isReply = isReply;
    }
    
    /** full constructor */
    public TKindergartenMessageBoard(String userId, Integer userType, Integer messageTye, String messageContent, String createTime, Integer isReply, Integer replyMessageId) {
        this.userId = userId;
        this.userType = userType;
        this.messageTye = messageTye;
        this.messageContent = messageContent;
        this.createTime = createTime;
        this.isReply = isReply;
        this.replyMessageId = replyMessageId;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="message_id", unique=true, nullable=false)

    public Integer getMessageId() {
        return this.messageId;
    }
    
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    
    @Column(name="user_id", nullable=false, length=64)

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Column(name="user_type")

    public Integer getUserType() {
        return this.userType;
    }
    
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    
    @Column(name="message_tye")

    public Integer getMessageTye() {
        return this.messageTye;
    }
    
    public void setMessageTye(Integer messageTye) {
        this.messageTye = messageTye;
    }
    
    @Column(name="message_content", length=500)

    public String getMessageContent() {
        return this.messageContent;
    }
    
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    @Column(name="create_time", length=30)

    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="is_reply", nullable=false)

    public Integer getIsReply() {
        return this.isReply;
    }
    
    public void setIsReply(Integer isReply) {
        this.isReply = isReply;
    }
    
    @Column(name="reply_message_id")

    public Integer getReplyMessageId() {
        return this.replyMessageId;
    }
    
    public void setReplyMessageId(Integer replyMessageId) {
        this.replyMessageId = replyMessageId;
    }

    @Column(name="album_id")
    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }
}