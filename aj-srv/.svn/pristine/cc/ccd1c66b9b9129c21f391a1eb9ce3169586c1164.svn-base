package com.frame.task.vo;

import net.sf.json.JSONObject;

import com.frame.core.vo.MessageVo;

/**
 * 消息推送实体
 * 
 * @author yanzelai 2013-5-30
 */
public class CLTMsgVo extends MessageVo {
	private static final long serialVersionUID = -7662210750902068097L;
	private String action;// 请求方法
	private String message;// 在短消息中发送的文本
	private String title;// 消息标题
	private String uri;// 消息URI
	private String userIdArr;// 电话
	private String reuquestIndentifider;// 请求ID
	//增加推送时间和过期时间参数
	private String pushTime;//推送时间
	private String expiredTime;//过期时间

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUserIdArr() {
		return userIdArr;
	}

	public void setUserIdArr(String userIdArr) {
		this.userIdArr = userIdArr;
	}

	public String getReuquestIndentifider() {
		return reuquestIndentifider;
	}

	public void setReuquestIndentifider(String reuquestIndentifider) {
		this.reuquestIndentifider = reuquestIndentifider;
	}

	/**
	 * 组装客户端信息JSON
	 * @return
	 */
	@Override
	public String getContent() {
		JSONObject messageObj = new JSONObject();
		messageObj.put("action", action);
		JSONObject body = new JSONObject();
		body.put("pushType", "0");
		body.put("userIdArr", userIdArr);
		body.put("title", title);
		body.put("message", message);		
		body.put("uri", uri);	
		body.put("pushTime", pushTime);
		body.put("expiredTime", expiredTime);
		messageObj.put("body", body.toString());
		return messageObj.toString();
	}

	public String getPushTime() {
		return pushTime;
	}

	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}
}
