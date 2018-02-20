package com.aam.cus.vo;

import com.frame.core.vo.ParamsVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-09 上午10:02:43    
 * @version  1.0     
 */
public class CusSmsValidateParamVo  extends ParamsVo {
	private String userTel;
	private String smsCode;

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	
}
