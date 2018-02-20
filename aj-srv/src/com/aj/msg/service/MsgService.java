package com.aj.msg.service;

import java.util.List;

import com.aj.msg.vo.MsgLimitKey;
import com.aj.msg.vo.VmMsgInfoVo;
import com.frame.core.exception.ServiceException;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public interface MsgService {

	public List<VmMsgInfoVo> queryMsgList(MsgLimitKey key) throws ServiceException;

	public int getMsgTotalCount(MsgLimitKey key) throws ServiceException;

	
}
