package com.frame.core.action;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.core.vo.MessageModel;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  2.0     
 */
public class BaseAction {
	public static final String SUCCESS="success";
	public static final String FALSE="false";
	public static final String ERROR="error";
	protected Logger log = LoggerFactory.getLogger(BaseAction.class);
	protected MessageModel Success = new MessageModel(true, "");
	protected MessageModel Failure = new MessageModel(false, "");
	protected MessageModel Yes = new MessageModel(true, "1");
	protected MessageModel No = new MessageModel(false, "2");
	protected MessageModel Again = new MessageModel(false, "3");
	
}
