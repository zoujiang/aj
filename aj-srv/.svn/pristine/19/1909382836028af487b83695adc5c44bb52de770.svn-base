package com.aj.ad.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.frame.core.util.StringUtil;

//广告内容  不同终端的内容不同
public class AdContentBean extends BaseBean{
	private static Logger logger = Logger.getLogger(AdContentBean.class);
	private String adid;	//广告ID 
	private String type;	//广告类型(url 外部链接;text 文字;video 视频;img 图片;product 商品广告;merchant 商家广告)
	private String position_type;	//类型 ios  android
	private String url;	//链接地址
	private String img_small;	//小图
	private String img_big;	//大图
	private String text;	  //文字正文
	private String video;	  //视频
	private String relation_id;	  //关联ID,商品ID，商户ID
	private String relation_title;	  //关联名称 商品名称、商户名称
	
	private String ad_name;  //广告名称  表没有此字段
	private int ad_status;  //广告状态  表没有此字段
	private Date online_time;
	private Date offline_time;
	
	private String rel_json; //广告内容与广告位关系JSON
	private List<AdRelationBean> rels;
	
	public void read(Map row) {
	    if (row == null) {
	    	return;
	    }
		this.id = (String)row.get("id");
		this.adid = (String)row.get("adid");
		this.type = (String)row.get("type");
		this.position_type = (String) row.get("position_type");
		this.url = (String) row.get("url");
		this.img_small = (String)row.get("img_small");
		this.img_big = (String)row.get("img_big");
		this.text = (String)row.get("text");
		this.video = (String)row.get("video");
		this.relation_id = (String)row.get("relation_id");
		this.relation_title = (String)row.get("relation_title");
	}
	
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPosition_type() {
		return position_type;
	}
	public void setPosition_type(String position_type) {
		this.position_type = position_type;
	}
	public String getUrl() {
		if (StringUtil.isEmpty(url)) {
			return null;
		}
		
		return url.trim();
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	public String getRel_json() {
		return rel_json;
	}
	public void setRel_json(String rel_json) {
		if (StringUtil.isEmpty(rel_json)) {
			return;
		}
		JSONArray array = null;
		try {
			array = JSONArray.fromObject(rel_json);
			if (array == null || array.isEmpty()) {
				return;
			}
			rels = new ArrayList<AdRelationBean>();
			for (int i = 0; i < array.size(); i++) {
				JSONObject json = array.getJSONObject(i);
				AdRelationBean bean = (AdRelationBean)JSONObject.toBean(json, AdRelationBean.class);
				if (bean != null) {
					rels.add(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.rel_json = rel_json;
	}
	public List<AdRelationBean> getRels() {
		return rels;
	}
	public void setRels(List<AdRelationBean> rels) {
		this.rels = rels;
	}
	public String getAd_name() {
		return ad_name;
	}
	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}

	public int getAd_status() {
		return ad_status;
	}

	public void setAd_status(int ad_status) {
		this.ad_status = ad_status;
	}

	public Date getOnline_time() {
		return online_time;
	}

	public void setOnline_time(Date online_time) {
		this.online_time = online_time;
	}

	public Date getOffline_time() {
		return offline_time;
	}

	public void setOffline_time(Date offline_time) {
		this.offline_time = offline_time;
	}
	
	public String getOnline_time_str() {
		if (this.online_time == null) {
			return null;
		}
		SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
		return format_yyyy_mm_dd.format(online_time);
	}
	public String getOffline_time_str() {
		if (this.offline_time == null) {
			return null;
		}
		SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
		return format_yyyy_mm_dd.format(offline_time);
	}

	public String getImg_small() {
		return img_small;
	}

	public void setImg_small(String img_small) {
		this.img_small = img_small;
	}

	public String getImg_big() {
		return img_big;
	}

	public void setImg_big(String img_big) {
		this.img_big = img_big;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getRelation_id() {
		return relation_id;
	}

	public void setRelation_id(String relation_id) {
		this.relation_id = relation_id;
	}

	public String getRelation_title() {
		return relation_title;
	}

	public void setRelation_title(String relation_title) {
		this.relation_title = relation_title;
	}
	
	
	
	
	
	
	
	

	
	

}
