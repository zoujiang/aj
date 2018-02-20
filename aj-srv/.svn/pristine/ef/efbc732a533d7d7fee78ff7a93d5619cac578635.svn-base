/**   
* @Package com.otos.msg.action
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月28日 下午1:47:32
* @version V1.0   
*/


package com.aj.msg.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.msg.service.MessageService;
import com.aj.msg.vo.MsgLimitKey;
import com.aj.msg.vo.PushMessageParamVo;
import com.aj.msg.vo.SentMessageVo;
import com.frame.core.action.BaseAction;
import com.frame.core.constant.Constant;
import com.frame.core.exception.ServiceException;
import com.frame.core.util.ResponseEntityUtil;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.MessageModel;
import com.frame.log.service.LogBizOprService;
import com.frame.system.vo.UserExtForm;

/**
 * @ClassName: MessageAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月28日 下午1:47:32
 * 
 */

@Controller
public class MessageAction extends BaseAction {

    @Autowired
    private MessageService messageService;
	@Autowired
	private LogBizOprService logBizOprService;    

    /**
     * 获取消息列表
     * 
     * @Title: getMessage
     * @Description:
     * @param page
     * @param rows
     * @return
     * @throws
     */
    @RequestMapping("/push/message/getMessage")
    @ResponseBody
    public DataModel<PushMessageParamVo> getMessage(String type,String req,String page, String rows) {
        // 处理相关页面参数
    	JSONObject jsonData = JSONObject.fromObject(req);
		MsgLimitKey key = new MsgLimitKey();
		String searchMsgFrom = null;
		String searchUserTel = null;
		String searchEndDt = null;
		String searchStartDt = null;
		
		if (!jsonData.isEmpty()) {
			searchMsgFrom = jsonData.getString("searchMsgFrom");
			searchUserTel = jsonData.getString("searchUserTel");			
			searchStartDt = jsonData.getString("searchStartDt");
			searchEndDt = jsonData.getString("searchEndDt");
			
		}
		if(StringUtil.isNotEmpty(type)){
			key.setMsgType(type);
		}
		if (StringUtil.isNotEmpty(searchMsgFrom)) {
			key.setSearchMsgFrom(searchMsgFrom);
		}
		if (StringUtil.isNotEmpty(searchUserTel)) {
			key.setSearchUserTel(searchUserTel);
		}
		if (StringUtil.isNotEmpty(searchStartDt)) {
			key.setStartDt(searchStartDt);
		}
		if (StringUtil.isNotEmpty(searchEndDt)) {
			key.setEndDt(searchEndDt);
		}
      
        if (StringUtil.isNotEmpty(page) && StringUtil.isNotEmpty(rows)) {
            key.setOffset((Integer.parseInt(page) - 1) * Integer.parseInt(rows));
            key.setPageSize(Integer.parseInt(rows));
        }
        DataModel<PushMessageParamVo> model = null;
        try {
            List<PushMessageParamVo> queryList = messageService.queryList(key);
            int totalCount = messageService.getTotalCount(key);
            model = new DataModel<PushMessageParamVo>(queryList, totalCount);
        } catch (ServiceException e) {
            e.printStackTrace();
            return new DataModel<PushMessageParamVo>(new ArrayList<PushMessageParamVo>(), 0);
        }
        return model;
    }

    /**
     * 获取消息信息
     */
    @RequestMapping("/push/message/single")
    @ResponseBody
    public PushMessageParamVo getSingleMessage(String id, Integer auditValue) {
        try {
            return messageService.getMessageById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据ID获取用户发生异常====" + e.getStackTrace());
            return null;
        }
    }

    /**
     * 更新消息
     */
    @RequestMapping("/push/message/updateMessage")
    @ResponseBody
    public ResponseEntity<MessageModel> updateMessage(HttpServletRequest request, PushMessageParamVo message) {
        try {
            UserExtForm user = (UserExtForm) request.getSession().getAttribute(Constant.LoginAdminUser);
            message.setMsgIsValid("1");
            message.setMsgModifyer(user.getAccount());
            messageService.updateMessage(message);
            return ResponseEntityUtil.getResponseEntity(Success);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.getResponseEntity(Failure);
        }
    }
  //改变状态
	@RequestMapping("/push/message/change")
	@ResponseBody
	public Object change(String id,String status){
		try {
			messageService.changeStatus(id,status);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageModel(false, "操作失败");
		}
		return new MessageModel(true, "操作成功");
	}

    /**
     * 新增消息
     */
    @RequestMapping("/push/message/addMessage")
    @ResponseBody
    public ResponseEntity<MessageModel> addMessage(HttpServletRequest request, PushMessageParamVo message) {
        try {
            UserExtForm user = (UserExtForm) request.getSession().getAttribute(Constant.LoginAdminUser);
            message.setMsgIsValid("1");
            message.setMsgCreator(user.getAccount());
            SentMessageVo sentMessageVo= messageService.saveMessage(message);
            return ResponseEntityUtil.getResponseEntity(Success);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.getResponseEntity(Failure);
        }
    }
    
    /**
     * 更新应用消息
     */
    @RequestMapping("/push/message/updateAppMessage")
    @ResponseBody
    public ResponseEntity<MessageModel> updateAppMessage(HttpServletRequest request, PushMessageParamVo message) {
        try {
            UserExtForm user = (UserExtForm) request.getSession().getAttribute(Constant.LoginAdminUser);
            message.setMsgIsValid("1");
            message.setMsgModifyer(user.getAccount());
            messageService.updateAppMessage(message);
            return ResponseEntityUtil.getResponseEntity(Success);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.getResponseEntity(Failure);
        }
    }
    
    
   /* *//**
     * 更新应用消息
     */
    @RequestMapping("/push/message/testActPush")
    @ResponseBody
    public ResponseEntity<MessageModel> testActPush(HttpServletRequest request) {
        try {
           
           
            List<String> aliases = new ArrayList<String>();
            aliases.add("U16040719480900111");
            messageService.saveSendJpush("2", "大家一起合租", aliases);
            return ResponseEntityUtil.getResponseEntity(Success);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.getResponseEntity(Failure);
        }
    }
    @RequestMapping("/push/message/testActPushShop")
    @ResponseBody
    public ResponseEntity<MessageModel> testActPushShop() {
        try {
           
           
            List<String> aliases = new ArrayList<String>();
            aliases.add("SHOP_16080920564700029");
            messageService.saveSendJpushShop("测试","测试一下推送", aliases);
            return ResponseEntityUtil.getResponseEntity(Success);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntityUtil.getResponseEntity(Failure);
        }
    }
}
