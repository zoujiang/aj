package com.demo.area.vo;

import com.frame.core.util.SystemConfig;


/**     
 * 测试使用
 * 
 * @author：caiwen    
 * @date 2013-4-26 上午10:02:43    
 * @version  1.0    
 */
public class AreaResultVo {
	private String areaId;
	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @return the areaPic
	 */
	public String getAreaPic() {
		return areaPic;
	}
	/**
	 * @return the gdNum
	 */
	public int getGdNum() {
		return gdNum;
	}
	private String areaName;

	private String areaPic;
	private int gdNum;
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * @param areaPic the areaPic to set
	 */
	public void setAreaPic(String areaPic) {
		this.areaPic = SystemConfig.getValue("img.http.url") + areaPic;
	}
	/**
	 * @param gdNum the gdNum to set
	 */
	public void setGdNum(int gdNum) {
		this.gdNum = gdNum;
	}
	
	
	
}
