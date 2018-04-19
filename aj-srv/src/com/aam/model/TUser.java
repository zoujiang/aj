package com.aam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user")
public class TUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String password;
	private String nickName;
	private String trueName;
	private String photo;
	private Integer sex;
	private String birthday;
	private String address;
	private String autograph;
	private String userTel;
	private String qrcode;
	private String registdate;
	private String age;
	private String ajNo;
	private String zodiac;
	private String constellation;
	private String conceptionDate;
	private String bornDate;
	//private String marriedDate;
	//private Integer isMarried;
	private String familyId;
	private Integer isValid;
	private String channelId;
	private Integer createUserId;
	private Integer type;  //1普通用户  2.教师  3校长／管理员
	private Integer isVip;  //是否VIP  0否 1是
	private String vipExpiredDate; //vip过期时间

	// Constructors

	/** default constructor */
	public TUser() {
	}

	/** minimal constructor */
	public TUser(String userTel, String password) {
		this.userTel = userTel;
		this.password = password;
	}

	/** full constructor */
	public TUser(String userTel, String password, String nickName,
			String trueName, String photo, Integer six, String birthday,
			String address, String autograph, String phoneno, String qrcode,
			String registdate, String age, String ajNo, String zodiac,
			String constellation, String conceptionDate, String bornDate,
			String marriedDate, Integer isMarried, String familyId) {
		this.userTel = userTel;
		this.password = password;
		this.nickName = nickName;
		this.trueName = trueName;
		this.photo = photo;
		this.sex = six;
		this.birthday = birthday;
		this.address = address;
		this.autograph = autograph;
		this.qrcode = qrcode;
		this.registdate = registdate;
		this.age = age;
		this.ajNo = ajNo;
		this.zodiac = zodiac;
		this.constellation = constellation;
		this.conceptionDate = conceptionDate;
		this.bornDate = bornDate;
	//	this.marriedDate = marriedDate;
	//	this.isMarried = isMarried;
		this.familyId = familyId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	@Column(name = "PASSWORD", length = 64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "NICK_NAME", length = 32)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "TRUE_NAME", length = 32)
	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	@Column(name = "PHOTO", length = 100)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "SEX")
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "BIRTHDAY", length = 30)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "AUTOGRAPH", length = 500)
	public String getAutograph() {
		return this.autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	@Column(name = "USERTEL", length = 20)
	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String phoneno) {
		this.userTel = phoneno;
	}

	@Column(name = "QRCODE", length = 100)
	public String getQrcode() {
		return this.qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	@Column(name = "REGISTDATE", length = 30)
	public String getRegistdate() {
		return this.registdate;
	}

	public void setRegistdate(String registdate) {
		this.registdate = registdate;
	}

	@Column(name = "AGE", length = 3)
	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Column(name = "AJ_NO", length = 20)
	public String getAjNo() {
		return this.ajNo;
	}

	public void setAjNo(String ajNo) {
		this.ajNo = ajNo;
	}

	@Column(name = "ZODIAC", length = 20)
	public String getZodiac() {
		return this.zodiac;
	}

	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}

	@Column(name = "CONSTELLATION", length = 20)
	public String getConstellation() {
		return this.constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	@Column(name = "CONCEPTION_DATE", length = 30)
	public String getConceptionDate() {
		return this.conceptionDate;
	}

	public void setConceptionDate(String conceptionDate) {
		this.conceptionDate = conceptionDate;
	}

	@Column(name = "BORN_DATE", length = 30)
	public String getBornDate() {
		return this.bornDate;
	}

	public void setBornDate(String bornDate) {
		this.bornDate = bornDate;
	}
	/*
	@Column(name = "MARRIED_DATE", length = 30)
	public String getMarriedDate() {
		return this.marriedDate;
	}

	public void setMarriedDate(String marriedDate) {
		this.marriedDate = marriedDate;
	}

	@Column(name = "IS_MARRIED")
	public Integer getIsMarried() {
		return this.isMarried;
	}

	public void setIsMarried(Integer isMarried) {
		this.isMarried = isMarried;
	}*/
	@Column(name = "FAMILYID", length = 64)
	public String getFamilyId() {
		return this.familyId;
	}
	
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	
	@Column(name = "IS_VALID")
	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	@Column(name = "JPUSH_CHANNELID", length = 128)
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	@Column(name = "CREATE_USER_ID")
	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name = "TYPE")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "IS_VIP")
	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	@Column(name = "VIP_EXPIRED_DATE")
	public String getVipExpiredDate() {
		return vipExpiredDate;
	}

	public void setVipExpiredDate(String vipExpiredDate) {
		this.vipExpiredDate = vipExpiredDate;
	}
	
}