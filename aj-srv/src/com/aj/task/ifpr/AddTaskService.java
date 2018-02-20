package com.aj.task.ifpr;

import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.task.service.TaskService;
import com.frame.core.util.DateUtil;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 爱的传承新增祈愿信
 * */

@Service("taskAdd")
public class AddTaskService implements PublishService{

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
			returnJSON.put("errorMsg", "任务发布人不能为空！");
			return returnJSON.toString();
		}
		if(params.optString("type") == null || "".equals(params.optString("type"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "任务类型不能为空！");
			return returnJSON.toString();
		}
		if((params.optString("content") == null || "".equals(params.optString("content"))) 
				&& (params.optString("audioUrl") == null || "".equals(params.optString("audioUrl")))
				&& (params.optString("imgUrl") == null || "".equals(params.optString("imgUrl")))
				){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "任务内容不能都为空！");
			return returnJSON.toString();
		}
		if(params.optString("sendTime") == null || "".equals(params.optString("sendTime"))){
			if("0".equals(params.optString("type"))){
				params.put("sendTime", DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
			} else{
				Date date = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.DAY_OF_YEAR, 7);
				params.put("sendTime", DateUtil.format(c, DateUtil.DATE_TIME_PATTERN2));
			}
		}
		if(params.optString("toUserId") == null || "".equals(params.optString("toUserId"))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "任务接受人不能为空！");
			return returnJSON.toString();
		}
		
		try {
			taskService.addTask(params);
			result.put("succMsg", "添加成功！");	
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "发布任务失败！");
			return returnJSON.toString();
		}
		
	}

}
