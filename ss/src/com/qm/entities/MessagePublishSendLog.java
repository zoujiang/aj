package com.qm.entities;

import com.frame.core.util.pagination.key.LimitKey;

public class MessagePublishSendLog extends LimitKey{
    private Integer id;

    private String reciveUser;

    private String sendTime;

    private Integer msgpublishId;

    private Integer status;
    
    private String msgContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReciveUser() {
        return reciveUser;
    }

    public void setReciveUser(String reciveUser) {
        this.reciveUser = reciveUser == null ? null : reciveUser.trim();
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime == null ? null : sendTime.trim();
    }

    public Integer getMsgpublishId() {
        return msgpublishId;
    }

    public void setMsgpublishId(Integer msgpublishId) {
        this.msgpublishId = msgpublishId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
    
}