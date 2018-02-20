package com.aj.task.ifpr;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.task.service.TaskService;
import com.frame.core.util.StringUtil;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

@Service("taskHide")
public class HideTaskService  implements PublishService{

	@Autowired private TaskService taskService;
	
	private Logger log = Logger.getLogger(HideTaskService.class);
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		JSONObject returnJSON = new JSONObject();
		
		JSONObject json = JSONObject.fromObject(obj);
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(params.optString("loginUser") == null || "".equals(params.optString("loginUser"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "登陆用户ID不能为空！");
			return returnJSON.toString();
		}
		if(params.optString("fromUser") == null || "".equals(params.optString("fromUser"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "发布任务用户ID不能为空！");
			return returnJSON.toString();
		}
		/*if(params.optString("toUser") == null || "".equals(params.optString("toUser"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "任务接收用户ID不能为空！");
			return returnJSON.toString();
		}*/
		
		if(params.optString("taskId") == null || "".equals(params.optString("taskId"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "任务ID不能为空！");
			return returnJSON.toString();
		}
		
		if(params.optString("isTip") == null || "".equals(params.optString("isTip"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "是否为提醒不能为空！");
			return returnJSON.toString();
		}
		
		try {
			JSONObject pm = new JSONObject();
			if(StringUtil.isEmpty(params.optString("toUser"))){//如果任务接收人为空，则隐藏自己发布的任务
				pm.put("taskIds", params.opt("taskId"));
				pm.put("fromUserId", params.opt("fromUser"));
				pm.put("reqType", "0");
				pm.put("state", params.opt("state"));
				taskService.delTask(pm);
			} else {//如果isTip = 1 则隐藏任务提醒；否则隐藏分配的任务
				if("1".equals(params.optString("isTip"))){
					pm.put("taskId", params.opt("taskId"));
					pm.put("toUserId", params.opt("toUser"));
					pm.put("tip", "0");
					taskService.modifyTaskTip(pm);
				} else {
					pm.put("taskIds", params.opt("taskId"));
					pm.put("fromUserId", params.opt("toUser"));
					pm.put("reqType", "0");
					pm.put("state", params.opt("state"));
					taskService.delAccept(pm);
				}
			}
			//taskService.modifyTaskTip(params);
			result.put("succMsg", "操作成功！");	
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "操作失败！");
			return returnJSON.toString();
		}
	}

}
