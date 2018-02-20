/**   
 * @Package com.otos.msg.api.ida
 * @Description: 
 * @author Wang Xue Feng   
 * @date 2015年5月4日 上午9:14:28
 * @version V1.0   
 */

package com.aj.msg.api.ida;

import net.sf.json.JSONObject;

import com.aj.msg.api.common.HttpClient;

/**
 * @ClassName: IdaToken
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年5月4日 上午9:14:28
 */

public class IdaToken {

    public IdaToken() {

    }

    /**
     * 获取Ida的token安全验证
     * 
     * @Title: getToken
     * @Description:
     * @return
     * @throws
     */
    public static String doGetToken() {
        if (IdaConfig.IDA_URL != null) {
            String url = IdaConfig.IDA_URL + "&a=security";
            JSONObject jsonResult = HttpClient.requestHttpClient(url);
            if (jsonResult != null) {
                return jsonResult.get("token").toString();
            }
        }
        return null;
    }
}
