/**   
* @Package com.otos.msg.vo
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月28日 下午4:16:12
* @version V1.0   
*/


package com.aj.msg.vo;

/**
 * @ClassName: SentMessageVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月28日 下午4:16:12
 * 
 */

public class SentMessageVo implements java.io.Serializable {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    public enum SentStatus {
        SUCCESS("成功"), FAIL("失败");
        private final String name;

        SentStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
    
    /**
     * 构造函数
     */
    public SentMessageVo(String msgId, SentStatus msgStatus, Integer success, Integer fail) {
        this.msgId = msgId;
        this.msgStatus = msgStatus;
        this.successCounts = success;
        this.failCounts = fail;
    }
    
    /**
     * 消息编号
     */
    private String msgId;

    /**
     * 消息发送状态
     */
    private SentStatus msgStatus;
    
    /**
     * 成功数量
     */
    private Integer successCounts;

    /**
     * 失败数量
     */
    private Integer failCounts;

    /**
     * @return msgId
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return msgStatus
     */
    public SentStatus getMsgStatus() {
        return msgStatus;
    }

    /**
     * @param msgStatus
     */
    public void setMsgStatus(SentStatus msgStatus) {
        this.msgStatus = msgStatus;
    }

    /**
     * @return successCounts
     */
    public Integer getSuccessCounts() {
        return successCounts;
    }

    /**
     * @param successCounts
     */
    public void setSuccessCounts(Integer successCounts) {
        this.successCounts = successCounts;
    }

    /**
     * @return failCounts
     */
    public Integer getFailCounts() {
        return failCounts;
    }

    /**
     * @param failCounts
     */
    public void setFailCounts(Integer failCounts) {
        this.failCounts = failCounts;
    }

}
