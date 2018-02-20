package com.aj.ad.ifpr.bean;

import java.util.UUID;

import com.frame.core.util.StringUtil;

public class BaseBean {
	protected String id;
	
	public String initId() {
		String uuid = UUID.randomUUID().toString();
		//去掉“-”符号 
		id = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
		return id;
	}
	
	public boolean isNew() {
		return StringUtil.isEmpty(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
