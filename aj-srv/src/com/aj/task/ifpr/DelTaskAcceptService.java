package com.aj.task.ifpr;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.task.service.TaskService;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

@Service("taskAcceptDel")
public class DelTaskAcceptService implements PublishService {

	@Autowired private TaskService taskService;
	
	private Logger log = Logger.getLogger(AddTaskService.class);
	
	
	@Override
	public Object publishService(Object obj) throws PublicException {
	
		JSONObject returnJSON = new JSONObject();
		
		JSONObject json = JSONObject.fromObject(obj);
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(params.optString("fromUserId") == null || "".equals(params.optString("fromUserId"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "用户ID不能为空！");
			return returnJSON.toString();
		}
		
		if(params.optString("reqType") == null || "".equals(params.optString("reqType"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "请求类型不能为空！");
			return returnJSON.toString();
		}
		
		if((params.optString("taskIds") == null || "".equals(params.optString("taskIds")) && "0".equals(params.optString("reqType")))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "任务ID不能为空！");
			return returnJSON.toString();
		}
		
		try {
			params.put("state", 0);
			taskService.delAccept(params);
			result.put("succMsg", "删除任务成功！");	
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "删除任务失败！");
			return returnJSON.toString();
		}
	}

}
