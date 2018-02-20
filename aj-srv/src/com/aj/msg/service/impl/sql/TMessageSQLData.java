/**   
* @Package com.otos.msg.service.impl.sql
* @Description: 
* @author Wang Xue Feng   
* @date 2015年5月4日 下午3:30:34
* @version V1.0   
*/


package com.aj.msg.service.impl.sql;

import com.aj.msg.vo.PushMessageParamVo;

/**
 * @ClassName: TMessageSQLData
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年5月4日 下午3:30:34
 * 
 */

public class TMessageSQLData {

    /**
     * 绑定从数据库查询出来的数据（结合com.otos.msg.service.impl.sql.TMessageSQL.queryDataFrom()方法的SQL查询）
     * 
     * @Title: bindSQLData
     * @Description:
     * @param dataMap
     * @return
     * @throws
     */
    public static PushMessageParamVo bindSQLData(PushMessageParamVo message, Object[] dataMap) {
        for (int i = 0; i < dataMap.length; i++) {
            if (i == 0) {
                message.setMsgId(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 1) {
                message.setMsgTitle(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 2) {
                message.setMsgContent(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 3) {
                message.setMsgFrom(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 4) {
                message.setMsgType(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 5) {
                message.setMsgSentDelay(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 6) {
                message.setMsgSentDelayTime(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 7) {
                message.setMsgSentUserGroup(dataMap[i] == null ? "0" : dataMap[i].toString());
            }
            if (i == 8) {
                message.setUserPhone(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 9) {
                message.setSendResult(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 10) {
                message.setSendDt(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 11) {
                message.setMsgPlatform(dataMap[i] == null ? "" : dataMap[i].toString());
            }
            if (i == 13) {
                message.setDoAudit(dataMap[i] == null ? 0 : Integer.parseInt(dataMap[i].toString()));
            }
            if (i == 12) {
                message.setDoSend(dataMap[i] == null ? 0 : Integer.parseInt(dataMap[i].toString()));
            }
            if (i == 14) {
                message.setSentUserGropInfo(dataMap[i] == null ? "" : dataMap[i].toString());
            }
        }
        return message;
    }
}
