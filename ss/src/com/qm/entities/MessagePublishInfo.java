package com.qm.entities;

import com.frame.core.util.pagination.key.LimitKey;

public class MessagePublishInfo extends LimitKey{
    private Integer id;

    private Integer sendTime;

    private String createTime;

    private String msgContent;

    private Integer status;

    private String reciveUserTel;
    
    private String sendTimeStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReciveUserTel() {
        return reciveUserTel;
    }

    public void setReciveUserTel(String reciveUserTel) {
        this.reciveUserTel = reciveUserTel == null ? null : reciveUserTel.trim();
    }

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}
    
}