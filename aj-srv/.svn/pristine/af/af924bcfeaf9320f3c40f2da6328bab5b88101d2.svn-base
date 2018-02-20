package com.aj.ad.ifpr.bean;

import java.util.Map;

public class ChannelBean {
	private String id;  //频道ID
	private String name;//应用名称
	
	public void read(Map row) {
	    if (row == null) {
	    	return;
	    }
		this.id = (String)row.get("id");
		this.name = (String)row.get("name");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
