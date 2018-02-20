package com.aam.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TCustom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CUSTOM")
public class TCustom implements java.io.Serializable {

	// Fields

	private String userId;
	private String userPwd;
	private String userTel;
	private String nickName;
	private String sex;
	private Date birthday;
	private String sign;
	private String headPic;
	private String appVer;
	private String ucode;
	private String ip;
	private String address;
	private String phoneVer;
	private String phoneSysVer;
	private String ua;
	private String channelId;
	private String createUser;
	private Date createDt;
	private String isValid;
	private String modifyUser;
	private Date modifyDt;

	// Constructors

	/** default constructor */
	public TCustom() {
	}

	/** minimal constructor */
	public TCustom(String userId) {
		this.userId = userId;
	}

	/** full constructor */
	public TCustom(String userId, String userPwd, String userTel,
			String nickName, String sex, Date birthday, String sign,
			String headPic, String appVer, String ucode, String ip,
			String address, String phoneVer, String phoneSysVer, String ua,
			String channelId, String createUser, Date createDt, String isValid,
			String modifyUser, Date modifyDt) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.userTel = userTel;
		this.nickName = nickName;
		this.sex = sex;
		this.birthday = birthday;
		this.sign = sign;
		this.headPic = headPic;
		this.appVer = appVer;
		this.ucode = ucode;
		this.ip = ip;
		this.address = address;
		this.phoneVer = phoneVer;
		this.phoneSysVer = phoneSysVer;
		this.ua = ua;
		this.channelId = channelId;
		this.createUser = createUser;
		this.createDt = createDt;
		this.isValid = isValid;
		this.modifyUser = modifyUser;
		this.modifyDt = modifyDt;
	}

	// Property accessors
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_PWD", length = 512)
	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Column(name = "USER_TEL", length = 64)
	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	@Column(name = "NICK_NAME", length = 128)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "SEX", length = 64)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "SIGN", length = 512)
	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Column(name = "HEAD_PIC", length = 1024)
	public String getHeadPic() {
		return this.headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	@Column(name = "APP_VER", length = 64)
	public String getAppVer() {
		return this.appVer;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	@Column(name = "UCODE", length = 128)
	public String getUcode() {
		return this.ucode;
	}

	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	@Column(name = "IP", length = 128)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "ADDRESS", length = 32)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PHONE_VER", length = 128)
	public String getPhoneVer() {
		return this.phoneVer;
	}

	public void setPhoneVer(String phoneVer) {
		this.phoneVer = phoneVer;
	}

	@Column(name = "PHONE_SYS_VER", length = 128)
	public String getPhoneSysVer() {
		return this.phoneSysVer;
	}

	public void setPhoneSysVer(String phoneSysVer) {
		this.phoneSysVer = phoneSysVer;
	}

	@Column(name = "UA", length = 128)
	public String getUa() {
		return this.ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	@Column(name = "CHANNEL_ID", length = 128)
	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "CREATE_USER", length = 32)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DT")
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "MODIFY_USER", length = 32)
	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DT")
	public Date getModifyDt() {
		return this.modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

}