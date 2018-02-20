package com.aj.clt.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aam.common.vo.MgrVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class EditMgrVo extends MgrVo {
	private String id;
	private String model; 
	private String clienturl;
	private String forceupdate;
	private String clientver;
	private String channel_id;
	private String updatecontent;
	private String property;
	private String status;
	private String update_userid;
	private String create_userid;
	private String update_date;
	private String create_date;
	//历史代码，暂时保留。以免需要上传文件
	private CommonsMultipartFile businPicFile;
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getUpdate_userid() {
		return update_userid;
	}
	public void setUpdate_userid(String update_userid) {
		this.update_userid = update_userid;
	}
	public String getCreate_userid() {
		return create_userid;
	}
	public void setCreate_userid(String create_userid) {
		this.create_userid = create_userid;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getClienturl() {
		return clienturl;
	}
	public void setClienturl(String clienturl) {
		this.clienturl = clienturl;
	}
	public String getForceupdate() {
		return forceupdate;
	}
	public void setForceupdate(String forceupdate) {
		this.forceupdate = forceupdate;
	}
	public String getClientver() {
		return clientver;
	}
	public void setClientver(String clientver) {
		this.clientver = clientver;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getUpdatecontent() {
		return updatecontent;
	}
	public void setUpdatecontent(String updatecontent) {
		this.updatecontent = updatecontent;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public CommonsMultipartFile getBusinPicFile() {
		return businPicFile;
	}
	public void setBusinPicFile(CommonsMultipartFile businPicFile) {
		this.businPicFile = businPicFile;
	}
	
}
