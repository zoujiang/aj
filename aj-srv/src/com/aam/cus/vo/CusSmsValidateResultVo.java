package com.aam.cus.vo;

import com.frame.core.vo.ResultVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class CusSmsValidateResultVo  extends ResultVo {
	private String succMsg;
	private String smsValidateToken;
	public String getSmsValidateToken() {
		return smsValidateToken;
	}

	public void setSmsValidateToken(String smsValidateToken) {
		this.smsValidateToken = smsValidateToken;
	}

	/**
	 * @return the succMsg
	 */
	public String getSuccMsg() {
		return succMsg;
	}

	/**
	 * @param succMsg the succMsg to set
	 */
	public void setSuccMsg(String succMsg) {
		this.succMsg = succMsg;
	}

	
	
}
