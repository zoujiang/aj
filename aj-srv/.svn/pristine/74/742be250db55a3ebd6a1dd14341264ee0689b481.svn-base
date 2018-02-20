package com.aj.task.ifpr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.task.service.TaskService;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

@Service("taskList")
public class GetTaskService implements PublishService {

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
		
		try {
			int count = taskService.getTaskCount(params);
			result.put("totalResults", count);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if(count > 0){
				list = taskService.getTaskList(params);
			}
			result.put("list", list);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "请求任务列表失败！");
			return returnJSON.toString();
		}
		
	}

}
