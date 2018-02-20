/**   
* @Package com.otos.msg.api.sms
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月28日 上午11:27:45
* @version V1.0   
*/


package com.aj.msg.api.ida;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.aj.msg.api.common.HttpClient;

/**
 * @ClassName: IdaMessageApi
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月28日 上午11:27:45
 * 
 */

public class IdaMessage {
    
    public IdaMessage() {

    }

    /**
     * 爱达短信发送请求
     * 
     * @Title: doIdaMessage
     * @Description:
     * @param tele - 用户手机号码集合
     * @param content - 短信内容
     * @return
     * @throws
     */
    public static JSONObject doIdaMessage(List<String> tele, String content) {
    	try{
    	System.out.println("tele" + tele + ",content:" + content);
        Map<String, String> mapResult = new HashMap<String, String>();
        if (IdaConfig.IDA_URL != null) {
            // 验证手机号码个数，Ida接口声明单次手机号码不能超过100个
            if (tele.size() > 100) {
                mapResult.put("resultcode", "-10003");
                mapResult.put("info", "手机号码每次最大支持100个");
                return JSONObject.fromObject(mapResult);
            }
            // 验证短信内容长度，Ida接口声明长度不能超过100
            if (StringUtils.length(content) > 100) {
                mapResult.put("resultcode", "-10004");
                mapResult.put("info", "短信内容最多允许100个中文汉字长度");
                return JSONObject.fromObject(mapResult);
            }
            // 请求Ida服务器获取安全身份Token
            String token = IdaToken.doGetToken();
            // Token请求失败，直接返回错误信息
            if (StringUtils.isEmpty(token)) {
                mapResult.put("resultcode", "-10005");
                mapResult.put("info", "获取Token失败。");
                
                return JSONObject.fromObject(mapResult);
            }
            // Token获取成功,将token值放入到参数中
            String url = IdaConfig.IDA_URL + "&a=sendsms&TokenKey=" + token;
            // 遍历手机号码，存入参数中
            if (tele != null && tele.size() > 0) {
                String teleParam = "";
                for (int i = 0; i < tele.size(); i++) {
                    teleParam += tele.get(i);// .replace(",", "")
                    if (i + 1 < tele.size()) {
                        teleParam += ",";
                    }
                }
                url += "&tele=" + teleParam;
                // 将短信内容放入参数中
                url += "&content=" + content;
                return HttpClient.requestHttpClient(url);
            } else {
                mapResult.put("resultcode", "-10006");
                mapResult.put("info", "手机号码为空。");
                return JSONObject.fromObject(mapResult);
            }
        } else {
            mapResult.put("resultcode", "-99999");
            mapResult.put("info", "请求Ida官方数据接口失败！");
            return JSONObject.fromObject(mapResult);
        }}
        catch (Exception e) {
			// TODO: handle exception
        	return null;
		}
    }
}
