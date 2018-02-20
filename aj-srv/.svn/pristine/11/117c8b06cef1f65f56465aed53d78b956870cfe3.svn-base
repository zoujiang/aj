/**   
* @Package com.otos.msg.service.impl
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月30日 上午9:48:36
* @version V1.0   
*/


package com.aj.msg.service.impl;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.msg.service.MessageLogService;
import com.aj.msg.vo.SentMessageVo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.exception.ServiceException;
import com.frame.core.service.impl.PageServiceImpl;
import com.frame.core.util.pagination.key.LimitKey;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.PageParamsVo;

/**
 * @ClassName: MessageLogServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月30日 上午9:48:36
 * 
 */
@Service
public class MessageLogServiceImpl extends PageServiceImpl<SentMessageVo> implements MessageLogService {

    @Autowired
    private GenericDAO baseDAO;

    @Override
    public DataModel<SentMessageVo> getPageList(LimitKey key, PageParamsVo pageParamsVo) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getCountByKey(LimitKey key, PageParamsVo pageParamsVo) throws ServiceException {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public int updateMessageLogByMessage(String messageId, String resultStatus) throws ServiceException {
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append(" UPDATE T_MSG_SEND_LOG set SEND_RESULT = '" + resultStatus + "' WHERE MSG_ID = '" + messageId + "'");
        Query query = baseDAO.getSession().createQuery(sqlQuery.toString());
        return query.executeUpdate();
    }


}
