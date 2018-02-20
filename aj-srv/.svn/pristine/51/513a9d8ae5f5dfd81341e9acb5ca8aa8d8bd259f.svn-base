/**   
* @Package com.otos.msg.interfaces
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月28日 下午4:10:01
* @version V1.0   
*/


package com.aj.msg.service;

import java.util.List;

import com.aj.msg.vo.MsgLimitKey;
import com.aj.msg.vo.PushMessageParamVo;
import com.aj.msg.vo.SentMessageVo;
import com.frame.core.exception.ServiceException;

/**
 * @ClassName: MessageService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月28日 下午4:10:01
 * 
 */

public interface MessageService {

    /**
     * 保存消息信息
     * 
     * @param messageVo
     * @return
     * @throws ServiceException
     * @throws
     */
    public SentMessageVo saveMessage(PushMessageParamVo messageVo) throws ServiceException;

    /**
     * 根据ID编号获取消息
     * 
     * @Title: getMessageById
     * @Description:
     * @param id
     * @return
     * @throws ServiceException
     * @throws
     */
    public PushMessageParamVo getMessageById(String id) throws ServiceException;

    /**
     * 获取消息列表
     * 
     * @Title: queryList
     * @Description:
     * @param key
     * @return
     * @throws ServiceException
     * @throws
     */
    public List<PushMessageParamVo> queryList(MsgLimitKey key) throws ServiceException;

    public int getTotalCount(MsgLimitKey key) throws ServiceException;

    /**
     * 更新消息
     * 
     * @Title: updateMessage
     * @Description:
     * @param message
     * @throws
     */
    public boolean updateMessage(PushMessageParamVo message) throws ServiceException;
    public boolean updateAppMessage(PushMessageParamVo message) throws ServiceException;
    
    public boolean saveSendJpush(String eventId,String eventInfo,List<String> aliases,List<String> redPackIdList);
    public boolean saveSendJpush(String actStatus,String actName,List<String> aliases);
    public void changeStatus(String id, String status) ;
    public boolean saveSendJpushShop(String title,String msgContent,List<String> aliases);
}
