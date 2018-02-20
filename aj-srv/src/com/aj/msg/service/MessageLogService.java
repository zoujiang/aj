/**   
* @Package com.otos.msg.service
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月30日 上午9:44:46
* @version V1.0   
*/


package com.aj.msg.service;

import com.frame.core.exception.ServiceException;

/**
 * @ClassName: MessageLogService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月30日 上午9:44:46
 * 
 */

public interface MessageLogService {

    /**
     * 更新日志信息
     * 
     * @param messageId
     * @return
     * @throws ServiceException
     * @throws
     */
    public int updateMessageLogByMessage(String messageId, String resultStatus) throws ServiceException;
}
