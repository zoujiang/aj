package com.aj.msg.vo;

import java.util.List;

/**
 * @ClassName: PushMessageVo
 * @Description: 推送消息通知接口请求 VO
 * @author Wang Xue Feng
 * @date 2015年4月28日 下午4:11:11
 */

public class PushMessageParamVo{
    /**
     * 主键编号
     */
    private String msgId;

    /**
     * 消息来源（0-用户系统创建，1-系统内置消息）
     */
    private String msgFrom;

    /**
     * 业务来源ID(只有当消息来源为系统消息时，此项不为空)
     */
    private String msgFromBuss;//

    /**
     * 消息类别(1-短信，2-消息推送)
     */
    private String msgType;

    /**
     * 消息标题
     */
    private String msgTitle;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 送达用户群体(0-点到点， 1-注册会员 ，2-配送人员， 3-商家， 4-配送站 )
     */
    private String msgSentUserGroup;
    
    /**
     * 是否延迟发送
     */
    private String msgSentDelay;
    
    /**
     * 延迟发送的发送时间
     */
    private String msgSentDelayTime;

    /**
     * 过期时间
     */
    private String msgExpireTime;

    /**
     * 是否生效(0-否，1-正常，2-冻结)
     */
    private String msgIsValid;

    /**
     * 消息创建者
     */
    private String msgCreator;

    /**
     * 修改者
     */
    private String msgModifyer;

    /**
     * 是否审核成功(0无状态，1成功，-1失败)
     */
    private Integer doAudit = 0;

    /**
     * 是否发送成功(0无状态，1成功，-1失败)
     */
    private Integer doSend = 0;
    
    /**
     * 消息推送平台
     */
    private String msgPlatform;

    /**
     * 用户手机号码
     */
    private String userPhone;
    
    private String sendResult;
    
    private String sendDt;

    
    private String partId;
    private String sentUserGropInfo;
    
    public String getSentUserGropInfo() {
		return sentUserGropInfo;
	}

	public void setSentUserGropInfo(String sentUserGropInfo) {
		this.sentUserGropInfo = sentUserGropInfo;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getSendResult() {
		return sendResult;
	}

	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}

	public String getSendDt() {
		return sendDt;
	}

	public void setSendDt(String sendDt) {
		this.sendDt = sendDt;
	}

	/**
     * @return msgId
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return msgFrom
     */
    public String getMsgFrom() {
        return msgFrom;
    }

    /**
     * @param msgFrom
     */
    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    /**
     * @return msgFromBuss
     */
    public String getMsgFromBuss() {
        return msgFromBuss;
    }

    /**
     * @param msgFromBuss
     */
    public void setMsgFromBuss(String msgFromBuss) {
        this.msgFromBuss = msgFromBuss;
    }

    /**
     * @return msgType
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * @param msgType
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * @return msgTitle
     */
    public String getMsgTitle() {
        return msgTitle;
    }

    /**
     * @param msgTitle
     */
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    /**
     * @return msgContent
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * @param msgContent
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * @return msgSentUserGroup
     */
    public String getMsgSentUserGroup() {
        return msgSentUserGroup;
    }

    /**
     * @param msgSentUserGroup
     */
    public void setMsgSentUserGroup(String msgSentUserGroup) {
        this.msgSentUserGroup = msgSentUserGroup;
    }

    /**
     * @return msgSentDelay
     */
    public String getMsgSentDelay() {
        return msgSentDelay;
    }

    /**
     * @param msgSentDelay
     */
    public void setMsgSentDelay(String msgSentDelay) {
        this.msgSentDelay = msgSentDelay;
    }

    /**
     * @return msgSentDelayTime
     */
    public String getMsgSentDelayTime() {
        return msgSentDelayTime;
    }

    /**
     * @param msgSentDelayTime
     */
    public void setMsgSentDelayTime(String msgSentDelayTime) {
        this.msgSentDelayTime = msgSentDelayTime;
    }

    /**
     * @return msgExpireTime
     */
    public String getMsgExpireTime() {
        return msgExpireTime;
    }

    /**
     * @param msgExpireTime
     */
    public void setMsgExpireTime(String msgExpireTime) {
        this.msgExpireTime = msgExpireTime;
    }

    /**
     * @return msgIsValid
     */
    public String getMsgIsValid() {
        return msgIsValid;
    }

    /**
     * @param msgIsValid
     */
    public void setMsgIsValid(String msgIsValid) {
        this.msgIsValid = msgIsValid;
    }

    /**
     * @return msgCreator
     */
    public String getMsgCreator() {
        return msgCreator;
    }

    /**
     * @param msgCreator
     */
    public void setMsgCreator(String msgCreator) {
        this.msgCreator = msgCreator;
    }

    /**
     * @return msgModifyer
     */
    public String getMsgModifyer() {
        return msgModifyer;
    }

    /**
     * @param msgModifyer
     */
    public void setMsgModifyer(String msgModifyer) {
        this.msgModifyer = msgModifyer;
    }

    /**
     * @return doAudit
     */
    public Integer getDoAudit() {
        return doAudit;
    }

    /**
     * @param doAudit
     */
    public void setDoAudit(Integer doAudit) {
        this.doAudit = doAudit;
    }

    /**
     * @return doSend
     */
    public Integer getDoSend() {
        return doSend;
    }

    /**
     * @param doSend
     */
    public void setDoSend(Integer doSend) {
        this.doSend = doSend;
    }

    /**
     * @return msgPlatform
     */
    public String getMsgPlatform() {
        return msgPlatform;
    }

    /**
     * @param msgPlatform
     */
    public void setMsgPlatform(String msgPlatform) {
        this.msgPlatform = msgPlatform;
    }

    /**
     * @return userPhone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * @param userPhone
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

}
