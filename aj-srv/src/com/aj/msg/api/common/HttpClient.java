/**   
* @Package com.otos.msg.api.common
* @Description: 
* @author Wang Xue Feng   
* @date 2015年5月4日 上午8:52:16
* @version V1.0   
*/


package com.aj.msg.api.common;

import java.io.IOException;
import java.net.URLDecoder;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: HttpClient
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年5月4日 上午8:52:16
 * 
 */

public class HttpClient {

    /**
     * 请求HTTP地址
     * 
     * @Title: requestHttpClient
     * @Description:
     * @param url
     * @throws
     */
    public static JSONObject requestHttpClient(String url) {
        JSONObject jsonResult = null;
        // post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new HttpGet(url);
        try {
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity());
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
    
    
}
