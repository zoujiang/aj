package com.demo.area.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.core.vo.MgrVo;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class AreaMgrVo extends MgrVo {
	private String areaId;
	private String areaName;
	private String createUser;
	private String createDt;
	private String isValid;
	private Integer areaOrder;
	private String modifyUser;
	private String modifyDt;
	private String areaPic;
	private CommonsMultipartFile areaPicFile;
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public Integer getAreaOrder() {
		return areaOrder;
	}
	public void setAreaOrder(Integer areaOrder) {
		this.areaOrder = areaOrder;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getModifyDt() {
		return modifyDt;
	}
	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}
	public String getAreaPic() {
		return areaPic;
	}
	public void setAreaPic(String areaPic) {
		this.areaPic = areaPic;
	}
	public CommonsMultipartFile getAreaPicFile() {
		return areaPicFile;
	}
	public void setAreaPicFile(CommonsMultipartFile areaPicFile) {
		this.areaPicFile = areaPicFile;
	}
	
	
}
