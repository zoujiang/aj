/**   
* @Package com.otos.msg.action
* @Description: 
* @author Wang Xue Feng   
* @date 2015年5月6日 上午11:19:40
* @version V1.0   
*/


package com.aj.msg.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aj.msg.api.ida.IdaConfig;
import com.aj.msg.api.ida.IdaMessage;
import com.frame.core.action.BaseAction;
import com.frame.core.util.ResponseEntityUtil;
import com.frame.core.vo.MessageModel;

/**
 * @ClassName: ShortMessageAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年5月6日 上午11:19:40
 * 
 */

@Controller
public class ShortMessageAction extends BaseAction {

    /**
     * 说明：批量发送短信，以txt文件格式上传，一行一用户手机号
     */

    @RequestMapping("/push/short-message/send")
    @ResponseBody
    public ResponseEntity<MessageModel> sendShortMessage(HttpServletRequest request, MultipartFile file, String content) {
        try {
            if (!file.isEmpty()) {
                if (!file.getOriginalFilename().endsWith(".txt")) {
                    // System.out.println("不是有效的txt文件类型！");
                    return ResponseEntityUtil.getResponseEntity(Failure);
                }
                // 创建临时文件以供读取
                File localFile = new File(request.getSession().getServletContext().getRealPath("/") + "/temp.txt");
                file.transferTo(localFile);
                List<String> userPhone = readTxtFile(localFile);
                if (userPhone.size() > 100) {
                    // 用户数操作100个
                    return ResponseEntityUtil.getResponseEntity(Success);
                }
                if (userPhone.size() > 0) {
                    // 发送短信

                    IdaConfig.getRequestUrl();
                    JSONObject reult = IdaMessage.doIdaMessage(userPhone, content);
                }
                return ResponseEntityUtil.getResponseEntity(Success);
            }
            return ResponseEntityUtil.getResponseEntity(Failure);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.getResponseEntity(Failure);
        }
    }

    public static List<String> readTxtFile(File file) {
        List<String> list = new ArrayList<String>();
        try {
            String encoding = "GBK";
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                // System.out.println(lineTxt);
                list.add(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            // System.out.println("读取文件内容出错");
            e.printStackTrace();
        } finally {
            file.delete();
        }
        return list;
    }
}
