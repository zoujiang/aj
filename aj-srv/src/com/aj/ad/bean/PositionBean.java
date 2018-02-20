package com.aj.ad.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.aj.ad.constant.AdConstants;

//广告位
public class PositionBean extends BaseBean {
	private String channelId;
	private int code;		//广告位编码
	private String name;		//广告位名称
	private String page;		//页面名称
	private String type;		//广告位类型（android/ios/web/wap）
	private String position;	//广告位位置（上top,中middle,下bottom）
	private String image;	//预览图片
	private int status;		//广告位状态(上线1,下线0)
	private int adcount; //广告数量
	
	private String createUser; //创建用户
	
	private ChannelBean channel;
	
	private List<AdRelationBean> rels;
	
	public void read(Map row) {
	    if (row == null) {
	    	return;
	    }
		this.id = (String)row.get("id");
		this.channelId = (String)row.get("channel_id");
		this.code = (Integer)row.get("code");
		this.name = (String)row.get("name");
		this.page = (String)row.get("page");
		this.type = (String)row.get("type");
		this.position = (String)row.get("position");
		this.image = (String)row.get("image");
		this.status = (Integer)row.get("status");
		if (row.get("ad_count") != null) {
			this.adcount = Integer.parseInt((Long)row.get("ad_count") + "");
		}
		this.createUser = (String)row.get("create_user");
		ChannelBean channel = new ChannelBean();
		channel.setId(channelId);
		channel.setName((String)row.get("channel_name"));
		this.channel = channel;
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getStatus() {
		return status;
	}
	public String getStatusText() {
		if (status == AdConstants.STATUS_ON) {
			return "上线";
		} else {
			return "下线";
		}
	}
	public void setStatus(int status) {
		this.status = status;
	}


	public String getChannelId() {
		return AdConstants.MainChannel;
		//return channelId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public ChannelBean getChannel() {
		return channel;
	}


	public void setChannel(ChannelBean channel) {
		this.channel = channel;
	}


	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public List<AdRelationBean> getRels() {
		return rels;
	}


	public void setRels(List<AdRelationBean> rels) {
		this.rels = rels;
	}
	
	
	
	
	

	
	
	
	
	
	
	
}
