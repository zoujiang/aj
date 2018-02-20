package com.aam.cus.vo;

import com.frame.core.vo.ParamsVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class CusSmsApplyParamVo  extends ParamsVo {
	private String userTel;
	
	private String type;

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
