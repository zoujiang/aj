/**   
* @Package com.otos.msg
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月27日 下午12:42:40
* @version V1.0   
*/


package com.aj.msg.api.jpush;

import java.util.Map;

import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * @ClassName: JPushSendService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月27日 下午12:42:40
 * 
 */

public class JPushSendService {

    /**
     * 名词解释 <br/>
     * Notification “通知”对象，是一条推送的实体内容对象之一（另一个是“消息”），是会作为“通知”推送到客户端的 <br/>
     * Message 自定义消息，透传消息.此部分内容不会展示到通知栏上，JPush SDK 收到消息内容后透传给 App。App 需要自行处理
     */

    /**
     * (Notification)推送到指定设备平台上，指定对象的通知信息<br/>
     * 不能指定模式，默认为开发模式
     * 
     * @Title: buildPushObject
     * @Description:
     * @param platform 指定平台
     * @param notification - 通知内容
     * @param alias - 用户别名
     * @return
     * @throws
     */
    public static PushPayload pushNotification(Platform platform, String notification) {
        return PushPayload.newBuilder().setPlatform(platform).setAudience(Audience.all()).setNotification(Notification.alert(notification)).setOptions(Options.newBuilder().setApnsProduction(JPushConfig.APNS_PRODUCTION).build()).build();
    }

    /**
     * 全平台发送广播，不带附加信息，推送到指定人员
     * 
     * @Title: pushNotificationAllWithExtra
     * @Description:
     * @param notification
     * @param extra
     * @return
     * @throws
     */
    public static PushPayload pushNotificationAllWithAlias(Audience audience, String notification, String sound) {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(audience)
                .setNotification(Notification.newBuilder().setAlert(notification).addPlatformNotification(AndroidNotification.newBuilder().build()).addPlatformNotification(IosNotification.newBuilder().setBadge(1).setSound(sound).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(JPushConfig.APNS_PRODUCTION).build()).build();
    }

    /**
     * 全平台发送广播，带附加信息
     * 
     * @Title: pushNotificationAllWithExtra
     * @Description:
     * @param notification
     * @param extra
     * @return
     * @throws
     */
    public static PushPayload pushNotificationAllWithAliasAndExtra(Audience audience, String notification, Map<String, String> extra, String sound) {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(audience)
                .setNotification(Notification.newBuilder().setAlert(notification).addPlatformNotification(AndroidNotification.newBuilder().addExtras(extra).build()).addPlatformNotification(IosNotification.newBuilder().setBadge(1).setSound(sound).addExtras(extra).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(JPushConfig.APNS_PRODUCTION).build()).build();
    }

    /**
     * Android平台发送广播，带附加信息
     * 
     * @Title: pushNotificationAndroidWithExtra
     * @Description:
     * @param notification
     * @param extra
     * @return
     * @throws
     */
    public static PushPayload pushNotificationAndroidWithAliasAndExtra(Audience audience, String notification, Map<String, String> extra) {
        return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(audience).setNotification(Notification.newBuilder().setAlert(notification).addPlatformNotification(AndroidNotification.newBuilder().addExtras(extra).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(JPushConfig.APNS_PRODUCTION).build()).build();
    }

    /**
     * Ios平台发送广播，带附加信息
     * 
     * @Title: pushNotificationIOSWithExtra
     * @Description:
     * @param notification
     * @param extra
     * @return
     * @throws
     */
    public static PushPayload pushNotificationIOSWithAliasAndExtra(Audience audience, String notification, Map<String, String> extra, String sound) {
        return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(audience).setNotification(Notification.newBuilder().setAlert(notification).addPlatformNotification(IosNotification.newBuilder().setBadge(1).setSound(sound).addExtras(extra).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(JPushConfig.APNS_PRODUCTION).build()).build();
    }

}
