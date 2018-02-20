package com.aj.sys.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;

/**
 * 系统消息管理
 * */

@Service("sysMsgAlertMng")
public class SysMsgAlertManageService implements PublishService{

	private Logger log = Logger.getLogger(SysMsgAlertManageService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String msgId = params.optString("msgId");
		String oper = params.optString("oper");  /*1:更新为已读  2:删除*/
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(msgId == null || "".equals(msgId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "msgId为空！");
			return returnJSON.toString();
		}
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}
		
		try {
			if("1".equals(oper)){
				
				String hsql = " update TSysMsgAlert set hadRead = 1, updateDate = ? where reciveUserId = ? and id = ?";
				baseDAO.execteBulk(hsql, DateUtils.currentDate(), Integer.parseInt(userId),  Integer.parseInt(msgId));
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				result.put("succMsg", "操作成功");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
			}else if ("2".equals(oper)){
				String hsql = " update TSysMsgAlert set hadRead = 2, updateDate = ? where reciveUserId = ? and id = ?";
				baseDAO.execteBulk(hsql, DateUtils.currentDate(), Integer.parseInt(userId),  Integer.parseInt(msgId));
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				result.put("succMsg", "操作成功");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				result.put("succMsg", "");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "无效的操作类型");
			}
		} catch (Exception e) {
			
			
			log.info("更新消息状态失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}
