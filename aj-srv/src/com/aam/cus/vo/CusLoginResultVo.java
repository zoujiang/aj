package com.aam.cus.vo;

import com.frame.core.vo.ResultVo;


/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public class CusLoginResultVo extends ResultVo {
	private String tokenId;
	private String succMsg;
	private String userId;


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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

	/**
	 * @return the tokenId
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	
}
