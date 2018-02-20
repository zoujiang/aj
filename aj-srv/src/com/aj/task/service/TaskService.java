package com.aj.task.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public interface TaskService {

	/**
	 * 添加任务
	 * @param params{
	 				text 文本内容
	    			audioUrl 语音
	   				imgUrl 图片
	    			createUser 创建人
	    			type 类型 0：普通任务；1：系统任务；2：关系验证任务
	    			sendTime 发送时间
	   				acceptUsers 接收人：多个用“,”隔开，系统任务接收人可为空
	   			}
	 * @throws Exception
	 */
	public void addTask(JSONObject params) throws Exception;
	
	/**
	 * 取消任务
	 * @param params{
	 		  		taskId:任务ID
	 		  		fromUserId:请求用户ID
	 		  }
	 * @return
	 * @throws Exception
	 */
	public void modifyTask(JSONObject params) throws Exception;
	
	/**
	 * 接受/拒绝任务
	 * @param params{
	 		  		taskId:任务ID
	 		  		fromUserId:请求用户ID
	 		  		reqType:0拒绝；2:接受
	 		  }
	 * @return
	 * @throws Exception
	 */
	public void modifyAccept(JSONObject params) throws Exception;
	
	/**
	 * 获取任务列表
	 * @param params{
	 		  		reqType:0：我的任务；1:今天任务；2：明天任务；3：遥远任务；4：历史任务
	 		  		fromUserId:请求用户ID
	 		  		pageNum:第几页
	 		  		pageSize:每页多少条
	 		  }
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTaskList(JSONObject params) throws Exception;

	/**
	 * 获取任务数量
	 * @param params{
	 		  		reqType:0：我的任务；1:今天任务；2：明天任务；3：遥远任务；4：历史任务
	 		  		fromUserId:请求用户ID
	 		  }
	 * @return
	 * @throws Exception
	 */
	public int getTaskCount(JSONObject params) throws Exception;
	
	/**
	 * 删除分配给我的任务
	 * @param params
	 * @throws Exception
	 */
	public void delAccept(JSONObject params) throws Exception;
	
	/**
	 * 删除我发布的任务
	 * @param params
	 * @throws Exception
	 */
	public void delTask(JSONObject params) throws Exception;

	/**
	 * 删除我发布的任务的处理提醒
	 * @param params
	 * @throws Exception
	 */
	public void modifyTaskTip(JSONObject params);
}
 