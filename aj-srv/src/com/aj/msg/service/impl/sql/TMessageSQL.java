/**   
* @Package com.otos.cmt.service.impl.sql
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月29日 上午10:53:44
* @version V1.0   
*/


package com.aj.msg.service.impl.sql;

/**
 * @ClassName: TMessageSQL
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月29日 上午10:53:44
 * 
 */

public class TMessageSQL {

    /**
     * 查询数量部分
     * 
     * @Title: queryCountFrom
     * @Description:
     * @return
     * @throws
     */
    public static StringBuffer queryCountFrom() {
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append(" SELECT ");
        sqlQuery.append(" count(MSG_ID) ");
        sqlQuery.append(" FROM T_MSG_INFO where 1=1 ");
        return sqlQuery;
    }

    /**
     * SQL查询数据部分
     * 
     * @Title: queryFrom
     * @Description:
     * @return
     * @throws
     */
    public static StringBuffer queryDataFrom() {
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append(" SELECT ");
        sqlQuery.append(" MSG_ID as id,");
        sqlQuery.append(" MSG_TITLE as title,");
        sqlQuery.append(" MSG_CONTENT as msgContent,");
        sqlQuery.append(" MSG_FROM as msgFrom,");
        sqlQuery.append(" MSG_TYPE as msgType,");
        sqlQuery.append(" SENT_DELAY as msgSentDelay,");
        sqlQuery.append(" date_format(SENT_DELAY_TIME,'%Y-%m-%d %h:%i:%s') as msgSentDelayTime,"); 
        sqlQuery.append(" SENT_USER_GROP as msgSentUserGroup, ");
        sqlQuery.append(" '' as userTel, ");
        sqlQuery.append(" DO_SEND as sendResult, ");
        sqlQuery.append(" SENT_DELAY_TIME as sendDt, ");
        sqlQuery.append(" MSG_PLATFORM as msgPlatform, ");
        sqlQuery.append(" DO_SEND as doSend, ");
        
        sqlQuery.append(" DO_AUDIT as doAudit, ");
        sqlQuery.append(" SENT_USER_GROP_INFO as sentUserGropInfo ");
        sqlQuery.append(" FROM T_MSG_INFO  where 1=1 ");
        return sqlQuery;
    }


}
