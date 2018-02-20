/**   
* @Package com.otos.msg.api.jpush
* @Description: 
* @author Wang Xue Feng   
* @date 2015年5月5日 下午2:07:08
* @version V1.0   
*/


package com.aj.msg.api.jpush;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.audience.Audience;

import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;

/**
 * @ClassName: JPushClient
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年5月5日 下午2:07:08
 * 
 */

public class JPush {

    private Log log = LogFactory.getLog(JPush.class);


    // 推送平台
    private Platform platform = null;

    // 推送客户端
    private JPushClient jpushClient = null;

    // 推送别名
    private Audience audience = null;

    // 推送别名
    private String sound = "default";

    // 推送附加信息
    private Map<String, String> jpushExtra = null;

    // 推送到的用户类型
    private String userTypeName = null;

    // 推送给用户APP图标（IOS）显示的数量
    private Integer badge = 1;

    /**
     * 设置别名
     * 
     * @Title: setAudience
     * @Description:
     * @param tags
     * @throws
     */
    public JPush setAudienceAlias(List<String> aliases) {
        if (aliases != null && aliases.size() > 0) {
            this.audience = Audience.alias(aliases);
        }
        return this;
    }

    /**
     * 设置别名
     * 
     * @Title: setAudience
     * @Description:
     * @param tags
     * @throws
     */
    public JPush setSoundForIos(String sound) {
        if (sound != null) {
            this.sound = sound;
        }
        return this;
    }

    /**
     * 设置附加信息（订单类型）
     * 
     * @param orderArea [1-新订单，2-已接受，3-已配送，4-待处理，5-历史订单]
     * @param orderId [订单编号]
     * @param orderStatus [订单状态]
     * @param bussStatus [业务状态]
     * @throws
     */
    public JPush setExtra(String orderArea, String orderId, String orderStatus, String bussStatus) {
        this.jpushExtra = new HashMap<String, String>();
        this.jpushExtra.put("orderArea", orderArea);
        this.jpushExtra.put("orderId", orderId);
        this.jpushExtra.put("orderStatus", orderStatus);
        this.jpushExtra.put("bussStatus", bussStatus);
        return this;
    }
    
    public JPush setExtra(String jpType,String targetId) {
        this.jpushExtra = new HashMap<String, String>();
        this.jpushExtra.put("jpType", jpType);
        this.jpushExtra.put("targetId", targetId);
        return this;
    }
    public JPush setExtra(String msgType) {
        this.jpushExtra = new HashMap<String, String>();
        this.jpushExtra.put("msgType", msgType);
        return this;
    }
    /**
     * 推送广播，指定平台
     * 
     * @Title: sentNotification
     * @Description:
     * @param content
     * @return
     * @throws
     */
    public boolean sendNotification(String message) {
        boolean doSent = false;
        if ("USER".equals(this.userTypeName)) {
            this.jpushClient = new JPushConfig().jpushToUser();
            doSent = dosent(message);
           
        } else {
            doSent = dosent(message);
        }
        return doSent;
    }

    /**
     * 发送推送
     * 
     * @param message
     * @return
     */
    private boolean dosent(String message) {
        log.info("发送通知（广播）……");
        try {
            if (this.audience != null) {
                if (this.jpushExtra != null) {
                    if (this.platform.equals(Platform.android())) {
                        this.jpushClient.sendPush(JPushSendService.pushNotificationAndroidWithAliasAndExtra(this.audience, message, this.jpushExtra));
                    } else if (this.platform.equals(Platform.ios())) {
                        this.jpushClient.sendPush(JPushSendService.pushNotificationIOSWithAliasAndExtra(this.audience, message, this.jpushExtra, this.sound));
                    } else {
                        this.jpushClient.sendPush(JPushSendService.pushNotificationAllWithAliasAndExtra(this.audience, message, this.jpushExtra, this.sound));
                    }
                } else {
                    this.jpushClient.sendPush(JPushSendService.pushNotificationAllWithAlias(this.audience, message, this.sound));
                }
            } else {
                this.jpushClient.sendPush(JPushSendService.pushNotification(this.platform, message));
            }
            log.info("发送通知（广播）成功，广播内容:" + message);
            return true;
        } catch (APIConnectionException e) {
            log.error("发送通知（广播）失败[APIConnectionException]！"+e.getLocalizedMessage(), e);
            return false;
        } catch (APIRequestException e) {
            log.error("发送通知（广播）失败，异常[APIRequestException]！"+e.getLocalizedMessage(), e);
            return false;
        }
    }

    /**
     * 初始化
     * 
     * @param userType
     * @param platformType
     * @param sound
     */
    public JPush(JPushUserType userType, JPushPlatformType platformType) {

        /**
         * 选取设备平台
         */
        if ("ANDROID".equals(platformType.toString())) {
            this.platform = Platform.android();
        } else if ("IOS".equals(platformType.toString())) {
            this.platform = Platform.ios();
        } else {
            this.platform = Platform.all();
        }

        /**
         * 获取推送用户群体
         */
        if ("USER".equals(userType.toString())) {
            this.userTypeName = "USER";
            this.jpushClient = new JPushConfig().jpushToUser();
        }else  if ("MERCHANT".equals(userType.toString())) {
        	 this.userTypeName = "MERCHANT";
             this.jpushClient = new JPushConfig().jpushToMerchant();
        }
    }

    /** 
    * @return badge 
    */ 
    public Integer getBadge() {
        return badge;
    }

    /** 
    * @param badge 
    */ 
    public void setBadge(Integer badge) {
        this.badge = badge;
    }

}
