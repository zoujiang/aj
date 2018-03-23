package com.frame.core.vo;

import java.util.Map;

/**
 * 消息推送实体公共类
 * 
 * @author yanzelai 2013-06-18
 * @version 1.0
 */
public class MessageVo {
	private String url;// 数据地址
	private String charset;// 字符集
	private int timeout;// 超时时间
	private String content;// 内容


	private Map<String, ? extends Object> data;// 内容

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, ? extends Object> getData() {
		return data;
	}

	public void setData(Map<String, ? extends Object> data) {
		this.data = data;
	}





}
