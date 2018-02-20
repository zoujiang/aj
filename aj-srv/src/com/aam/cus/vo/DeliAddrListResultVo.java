package com.aam.cus.vo;

import java.util.Date;

import com.frame.core.vo.ResultVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class DeliAddrListResultVo  extends ResultVo {
	private String deliId       ;
	private String deliName     ;
	private String deliTel      ;
	private String deliAddr     ;
	public String getDeliId() {
		return deliId;
	}
	public void setDeliId(String deliId) {
		this.deliId = deliId;
	}
	public String getDeliName() {
		return deliName;
	}
	public void setDeliName(String deliName) {
		this.deliName = deliName;
	}
	public String getDeliTel() {
		return deliTel;
	}
	public void setDeliTel(String deliTel) {
		this.deliTel = deliTel;
	}
	public String getDeliAddr() {
		return deliAddr;
	}
	public void setDeliAddr(String deliAddr) {
		this.deliAddr = deliAddr;
	}
}
