/**   
 * @Package com.otos.msg.api.ida
 * @Description: 
 * @author Wang Xue Feng   
 * @date 2015年5月4日 上午8:51:49
 * @version V1.0   
 */

package com.aj.msg.api.ida;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.aj.msg.api.common.HttpClient;

/**
 * @ClassName: IdaUser
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年5月4日 上午8:51:49
 */

public class IdaUser {

    public IdaUser() {

    }

    /**
     * 获取Ida用户
     * 
     * @Title: doGetUser
     * @Description:
     * @param uid 获取某个用户ID的手机号码（可为空）
     * @param tele 获取某个手机段的用户（如：134）（可为空）
     * @param page 当前页码数（可为空）
     * @param pageSize 每页数量（可为空）
     * @return
     * @throws
     */
    public static JSONObject doGetUser(String uid, String tele, String page, String pageSize) {
        Map<String, String> mapResult = new HashMap<String, String>();
        if (IdaConfig.IDA_URL != null) {
            // 请求Ida服务器获取安全身份Token
            String token = IdaToken.doGetToken();
            // Token请求失败，直接返回错误信息
            if (StringUtils.isEmpty(token)) {
                mapResult.put("resultCode", "-10005");
                mapResult.put("info", "获取Token失败。");
                return JSONObject.fromObject(mapResult);
            }
            // Token获取成功,将token值放入到参数中
            String url = IdaConfig.IDA_URL + "&a=findtele&TokenKey=" + token;
            if (!StringUtils.isEmpty(uid)) {
                url += "&uid=" + uid;
            } else {
                if (!StringUtils.isEmpty(tele)) {
                    url += "&tele=" + tele;
                }
                if (!StringUtils.isEmpty(page)) {
                    url += "&p=" + page;
                }
                if (!StringUtils.isEmpty(pageSize)) {
                    url += "&size=" + pageSize;
                }
            }
            // 将短信内容放入参数中
            return HttpClient.requestHttpClient(url);
        } else {
            mapResult.put("resultcode", "-99999");
            mapResult.put("info", "请求Ida官方数据接口失败！");
            return JSONObject.fromObject(mapResult);
        }
    }

    public static void main(String[] args) {
        JSONObject jsonObjec = doGetUser(null, null, "1", "20");
        if (jsonObjec != null) {
            System.out.println(jsonObjec.get("info"));
        }
    }

}
