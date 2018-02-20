/**   
* @Package com.otos.msg
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月27日 上午11:31:44
* @version V1.0   
*/


package com.aj.msg.api.jpush;

import com.frame.core.util.SystemConfig;

import cn.jpush.api.JPushClient;

/**
 * @ClassName: JPushCilent
 * @Description: 基于极光推送信息的Util工具包，需要依赖的jar包包括(jpush-client,gson,guava,mockwebserver)
 * @author Wang Xue Feng
 * @date 2015年4月27日 上午11:31:44
 */

public class JPushConfig {

    // 默认的模式，上线后改为true为生产模式
    public static boolean APNS_PRODUCTION = true;
    public static String masterSecret=SystemConfig.getValue("jpush.user.masterSecret");
    public static String appkey=SystemConfig.getValue("jpush.user.appkey");
    /**
     * 推送用户类型
     * 
     * @ClassName: JPushType
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @author Wang Xue Feng
     * @date 2015年5月5日 下午2:35:03
     */
    public static enum JPushUserType {
        MERCHANT("商家用户"), USER("注册会员用户"), DELIVERY("配送人员用户");
        private String name;

        JPushUserType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * 设备平台
     * 
     * @ClassName: JPushPlatformType
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @author Wang Xue Feng
     * @date 2015年5月5日 下午2:36:49
     */
    public static enum JPushPlatformType {
        ALL("所有设备"), IOS("苹果设备"), ANDROID("安卓设备"), WINPHONE("微软设备");
        private String name;

        JPushPlatformType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * 注册用户端JPush推送平台 配置 <br/>
     * 更新时间 2015-05-12
     * 
     * @Title: getJPushClient
     * @Description:
     * @return
     * @throws
     */
    protected JPushClient jpushToUser() {
    	//return new JPushClient(masterSecret, appKey)
        return new JPushClient(masterSecret,appkey, 3);
    }

    /**
     * 配送人员端JPush推送平台 配置<br/>
     * 更新日期 2015-05-11
     * 
     * @Title: getJPushClient
     * @Description:
     * @return
     * @throwsmasterSecret, String appKey,
     */
    protected JPushClient jpushToDelivery() {
        return new JPushClient(masterSecret,appkey, 3);
    }

    /**
     * 商家端JPush推送平台 配置
     * 
     * @Title: getJPushClient
     * @Description:
     * @return
     * @throws
     */
    protected JPushClient jpushToMerchant() {
        return new JPushClient(masterSecret,appkey, 3);
    }

    /**
     * 企业版APP推送配置
     * 
     * @Title: getJPushClient
     * @Description:
     * @return
     * @throws
     */
    protected JPushClient jpushCompanyApp() {
        return new JPushClient(masterSecret,appkey, 3);
    }

}
