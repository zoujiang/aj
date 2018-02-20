package com.aj.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.msg.api.jpush.JPush;
import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;
import com.aj.task.service.TaskService;
import com.aj.task.vo.Task;
import com.aj.task.vo.TaskAccept;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired protected GenericDAO baseDAO;
	private static final Log log = LogFactory.getLog(TaskServiceImpl.class); 
	
	@Override
	public void addTask(JSONObject params) throws Exception {
		//保存任务信息
		Task task = new Task();
		task.setText(params.get("content").toString());
		task.setAudioUrl(params.get("audioUrl").toString());
		task.setImgUrl(params.get("imgUrl").toString());
		task.setUserId(Integer.parseInt(params.get("fromUserId").toString()));
		String sendTime = params.get("sendTime").toString();
		if(sendTime == null || "".equals(sendTime)){
			sendTime = DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2);
		}
		task.setStatus(1);
		task.setSendTime(sendTime);
		task.setType(Integer.parseInt(params.get("type").toString()));
		task.setCreateDate(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
		baseDAO.saveOrUpdate(task);
		log.info(JSONObject.fromObject(params).toString() +"   任务保存成功;taskID="+task.getId());
		//保存任务接收人信息
		String userIdStr = params.get("toUserId").toString();
		if(userIdStr != null && !"".equals(userIdStr)){
			String[] userIds = userIdStr.split(",");
			for(String userId : userIds){
				TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
				TaskAccept accept = new TaskAccept();
				accept.setTask(task);
				accept.setUserId(Integer.parseInt(userId));
				accept.setStatus(1);
				accept.setCreateDate(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
				baseDAO.saveOrUpdate(accept);
				log.info(JSONObject.fromObject(params).toString() +"   任务接收人保存成功;acceptID="+accept.getId());
				
				String msg = "【"+(StringUtil.isEmpty(user.getNickName()) ? (StringUtil.isEmpty(user.getTrueName())? user.getUserTel() : user.getTrueName()) :user.getNickName())+"】"+task.getText()+"("+DateUtil.dateFromat(DateUtil.stringToDate(task.getSendTime()+" 00:00:00"), "yyyy年MM月dd日")+").";
				
				//调用消息推送
				List<String> aliases = new ArrayList<String>();
			    aliases.add(user.getId()+"");
			    
			    
			    boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(msg);
			    log.info("调用消息推送JPush:" + user.getAjNo()+".rel：" + b);
			}
		}
	}

	@Override
	public void modifyTask(JSONObject params) throws Exception {
	//	Task task = baseDAO.get(Task.class, Integer.parseInt(params.get("taskId").toString()));

		String reqType = params.optString("reqType");
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(0);
		paramlist.add(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
		paramlist.add(Integer.parseInt(params.getString("fromUserId")));
		String sql = "";
		if("1".equals(reqType)){
			sql += "update t_task t set t.status = ? , t.update_date = ? where t.user_id = ? and DATEDIFF(t.send_time,NOW()) > 0 and t.status = 1";
		}else{
			sql +=  "update t_task t set t.status = ? , t.update_date = ? where t.user_id = ?  and t.status = 1 and t.id in (";
			String taskIdStr = params.get("taskIds").toString();
			String[] taskIds = taskIdStr.split(",");
			for(String taskId : taskIds){
				sql += "?,";
				paramlist.add(Integer.parseInt(taskId));
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ")";
		}
		
		baseDAO.execteNativeBulk(sql, paramlist.toArray());
		
		log.info("任务删除成功;请求"+JSONObject.fromObject(params).toString());
	}

	@Override
	public void modifyAccept(JSONObject params) throws Exception {
		
		String reqType = params.optString("reqType");
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(Integer.parseInt(params.optString("status")));//0:拒绝 2 接受
		paramlist.add(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
		paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
		
		String taskIdStr = "";
		String sql = "";
		if("1".equals(reqType)){
			String ssql = "select t.id from t_task t, t_task_accept ta where t.id = ta.id and ta.user_id = ? and t.status = 1 and ta.status = 1";
			List<Map<String, Object>> maps = baseDAO.getGenericBySQL(ssql, new Object[]{Integer.parseInt(params.get("fromUserId").toString())});
			if(maps != null && maps.size() > 0){
				sql+="update t_task_accept  set status = ? , update_date = ? where user_id = ? and status = 1 and task_id in(";
				for(Map<String, Object> map : maps){
					sql += "?,";
					taskIdStr += map.get("id").toString()+",";
					paramlist.add(Integer.parseInt(map.get("id").toString()));
				}
				sql = sql.substring(0, sql.length() - 1);
				taskIdStr = taskIdStr.substring(0, taskIdStr.length() - 1);
				sql += ")";
			}
			//sql+="update t_task_accept  set status = ? , update_date = ? where user_id = ? and status = 1 and task_id in(select t.id from t_task t where t.status = 1)";
			//sql+="update TaskAccept set status = ?, updateDate = ?  where userId = ? and status = 1 and task.status = 1";
		} else {
			sql+="update t_task_accept  set status = ? , update_date = ? where user_id = ? and status = 1 and task_id in(";
			taskIdStr = params.get("taskIds").toString();
			String[] taskIds = taskIdStr.split(",");
			for(String taskId : taskIds){
				sql += "?,";
				paramlist.add(Integer.parseInt(taskId));
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ")";
		}
		
		if(sql != null && !"".equals(sql)){
			baseDAO.execteNativeBulk(sql, paramlist.toArray());
			paramlist = new ArrayList<Object>();
			String ssql = "SELECT t.CONTENT content, t.SEND_TIME sendTime, tau.NICK_NAME nickName, tau.TRUE_NAME trueName, tau.USERTEL userTel, t.USER_ID userId FROM t_task_accept ta LEFT JOIN t_user tau ON ta.user_id = tau.id LEFT JOIN t_task t ON ta.TASK_ID = t.id WHERE ta.user_id = ? and ta.TASK_ID in (";
			paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			String[] taskIds = taskIdStr.split(",");
			for(String taskId : taskIds){
				ssql += "?,";
				paramlist.add(Integer.parseInt(taskId));
			}
			ssql = ssql.substring(0, ssql.length() - 1);
			ssql += ")";
			List<Map<String, Object>> taskList = baseDAO.getGenericBySQL(ssql, paramlist.toArray());
			if(taskList != null && taskList.size() > 0){
				for(Map<String, Object> map : taskList){
					String userId = map.get("userId").toString();
					TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
					String msg = "【"+(StringUtil.isEmpty(user.getNickName()) ? (StringUtil.isEmpty(user.getTrueName())? user.getUserTel() : user.getTrueName()) :user.getNickName())+"】：您发布的任务 【"+map.get("content").toString()+"("+DateUtil.dateFromat(DateUtil.stringToDate(map.get("sendTime").toString()+" 00:00:00"), "yyyy年MM月dd日")+")】已被【";
					if(map.get("nickName") != null && !"".equals(map.get("nickName").toString())){
						msg += map.get("nickName").toString();
					} else if(map.get("trueName") != null && !"".equals(map.get("trueName").toString())){
						msg += map.get("trueName").toString();
					} else {
						msg += map.get("userTel").toString();
					}
					msg += "】"+ ("0".equals(params.optString("status"))? "拒绝" : "接受")+".";
					
					//调用消息推送
					List<String> aliases = new ArrayList<String>();
				    aliases.add(user.getId()+"");
				    
				    
				    boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(msg);
				    log.info("调用消息推送JPush:" + user.getAjNo()+".rel：" + b);
				}
			}
		}
		log.info("任务接受/拒绝成功;请求内容"+JSONObject.fromObject(params).toString());
	}

	@Override
	public List<Map<String, Object>> getTaskList(JSONObject params) throws Exception {
		int pageNum = Integer.parseInt(params.get("pageNum").toString());
		int pageSize = Integer.parseInt(params.get("pageSize").toString());
		String reqType = params.get("reqType").toString();
		List<Object> paramlist = new ArrayList<Object>();
		String sql = "";
		if("0".equals(reqType)){
			sql +="SELECT t.id, t.content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status FROM t_task t, t_user tu WHERE t.user_id = tu.id and t.status = 1 AND t.user_id = ?  and t.state <> 0 ORDER BY t.send_time DESC";
			paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
		}else {
			sql += "select * from (";
			if("1".equals(reqType)){
				sql +="SELECT t.id, t.content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, ta.USER_ID toUserId, tau.NICK_NAME toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, ta.status accept FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) = 0 and t.status = 1 and ta.status <> 0 AND ta.user_id = ?  and ta.state <> 0";
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			} else if("2".equals(reqType)){
				sql +="SELECT t.id, '0' isTip, t.content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, ta.USER_ID toUserId, tau.NICK_NAME toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, ta.status accept FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) = 1 and t.status = 1 and ta.status <> 0 AND ta.user_id = ?  and ta.state <> 0";
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			} else if("3".equals(reqType)){
				sql +="SELECT t.id, '0' isTip, t.content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, ta.USER_ID toUserId, tau.NICK_NAME toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, ta.status accept FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) > 1 and t.status = 1 and ta.status <> 0 AND ta.user_id = ?  and ta.state <> 0";
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			} else {
				sql +="SELECT t.id, '0' isTip, t.content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, ta.USER_ID toUserId, tau.NICK_NAME toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, ta.status accept FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) < 0 and t.status = 1 AND ta.user_id = ?  and ta.state <> 0";//今天之前的
				sql +=" union ";
				sql+="SELECT t.id, '0' isTip, t.content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, ta.USER_ID toUserId, tau.NICK_NAME toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, ta.status accept FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) >= 0 and t.status = 1  and ta.status = 0   and ta.state <> 0 AND ta.user_id = ? ";//今天已经接受或者拒绝的
				sql +=" union ";
				/*sql+="SELECT t.id, '0' isTip, t.content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, '' toUserId, '' toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, '' accept FROM t_task t, t_user tu WHERE t.user_id = tu.id and t.status = 1 AND t.user_id = ?  and t.state <> 0 ";//我发布的任务
				sql+="union ";*/
				sql +="SELECT t.id, '1' isTip, CONCAT('您提交给 ', IF(ISNULL(tau.NICK_NAME) || LENGTH(TRIM(tau.NICK_NAME)) < 1, IF(ISNULL(tau.TRUE_NAME) || LENGTH(TRIM(tau.TRUE_NAME)) < 1, tau.USERTEL, tau.TRUE_NAME), tau.NICK_NAME),' 的任务【',t.CONTENT,'(', SUBSTR(t.SEND_TIME, 1, 10), ')】已被【',tau.NICK_NAME, '】接受') content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, ta.USER_ID toUserId, tau.NICK_NAME toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, ta.STATUS accept FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.ID = ta.TASK_ID AND t.USER_ID = tu.ID AND ta.USER_ID = tau.ID AND t.status = 1 AND ta.tip = 1 AND ta.STATUS = 2 AND t.USER_ID = ? ";
				sql+="union ";
				sql +="SELECT t.id, '1' isTip, CONCAT('您提交给 ', IF(ISNULL(tau.NICK_NAME) || LENGTH(TRIM(tau.NICK_NAME)) < 1, IF(ISNULL(tau.TRUE_NAME) || LENGTH(TRIM(tau.TRUE_NAME)) < 1, tau.USERTEL, tau.TRUE_NAME), tau.NICK_NAME),' 的任务【',t.CONTENT, '(', SUBSTR(t.SEND_TIME, 1, 10), ')】已被【',tau.NICK_NAME, '】拒绝') content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, ta.USER_ID toUserId, tau.NICK_NAME toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, ta.STATUS accept FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.ID = ta.TASK_ID AND t.USER_ID = tu.ID AND ta.USER_ID = tau.ID AND t.status = 1 AND ta.tip = 1 AND ta.STATUS = 0 AND t.USER_ID = ? ";
				sql+="union ";
				sql +="SELECT t.id, '1' isTip, CONCAT('您提交给 ', IF(ISNULL(tau.NICK_NAME) || LENGTH(TRIM(tau.NICK_NAME)) < 1, IF(ISNULL(tau.TRUE_NAME) || LENGTH(TRIM(tau.TRUE_NAME)) < 1, tau.USERTEL, tau.TRUE_NAME), tau.NICK_NAME),' 的任务【',t.CONTENT, '(', SUBSTR(t.SEND_TIME, 1, 10), ')】尚未处理哦，请知晓') content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, ta.USER_ID toUserId, tau.NICK_NAME toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, ta.STATUS accept FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.ID = ta.TASK_ID AND t.USER_ID = tu.ID AND ta.USER_ID = tau.ID AND t.status = 1 AND ta.tip = 1 AND ta.STATUS = 1 AND t.USER_ID = ? ";
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				//paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			}
			sql+=") a order by sendTime desc, createDate desc";
		}
		List<Map<String, Object>> list = baseDAO.getGenericByPositionToSQL(sql, (pageNum - 1) * pageSize, pageSize, paramlist.toArray());
		if(list != null && list.size() > 0){
			for(Map<String, Object> map : list){
				map.put("photo", SystemConfig.getValue("img.http.url")+map.get("photo"));
				if(!"1".equals(map.get("isTip"))){
					map.put("isTip","0");
				}
			}
		}
		return list;
	}
	
	
	@Override
	public int getTaskCount(JSONObject params) throws Exception {
		String reqType = params.get("reqType").toString();
		List<Object> paramlist = new ArrayList<Object>();
		String sql = "";
		if("0".equals(reqType)){
			sql +="SELECT count(cnt) as totalCnt FROM t_task t, t_user tu WHERE t.user_id = tu.id AND t.user_id = ?  and t.status = 1  and t.state <> 0 ORDER BY t.send_time DESC";
			paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
		}else {
			sql += "select count(cnt) as totalCnt from (";
			if("1".equals(reqType)){
				sql +="SELECT 1  as cnt FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) = 0 and t.status = 1 and ta.status <> 0 AND ta.user_id = ?  and ta.state <> 0";
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			} else if("2".equals(reqType)){
				sql +="SELECT 1  as cnt  FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) = 1 and t.status = 1 and ta.status <> 0 AND ta.user_id = ?  and ta.state <> 0";
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			} else if("3".equals(reqType)){
				sql +="SELECT 1  as cnt FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) > 1 and t.status = 1 and ta.status <> 0 AND ta.user_id = ?  and ta.state <> 0";
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			} else {
				sql +="SELECT 1  as cnt FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) < 0 and t.status = 1 AND ta.user_id = ?  and ta.state <> 0";//今天之前的
				sql +=" union all ";
				sql+="SELECT 1 as cnt FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.id = ta.task_id AND t.user_id = tu.id AND ta.USER_ID = tau.ID AND DATEDIFF(t.send_time,NOW()) >= 0 and t.status = 1  and ta.status = 0   and ta.state <> 0 AND ta.user_id = ? ";//今天已经接受或者拒绝的
				sql +=" union all ";
				/*sql+="SELECT t.id, '0' isTip, t.content, t.audio_url audioUrl, t.img_url imgUrl,t.user_id userId,tu.nick_name nickName, '' toUserId, '' toNickName, tu.PHOTO photo, t.send_time sendTime, t.create_date createDate, t.type, t.status, '' accept FROM t_task t, t_user tu WHERE t.user_id = tu.id and t.status = 1 AND t.user_id = ?  and t.state <> 0 ";//我发布的任务
				sql+="union ";*/
				sql +="SELECT 1 as cnt FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.ID = ta.TASK_ID AND t.USER_ID = tu.ID AND ta.USER_ID = tau.ID AND t.status = 1 AND ta.tip = 1 AND ta.STATUS = 2 AND t.USER_ID = ? ";
				sql+="union all ";
				sql +="SELECT 1 as cnt FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.ID = ta.TASK_ID AND t.USER_ID = tu.ID AND ta.USER_ID = tau.ID AND t.status = 1 AND ta.tip = 1 AND ta.STATUS = 0 AND t.USER_ID = ? ";
				sql+="union all ";
				sql +="SELECT 1 as cnt FROM t_task t, t_user tu, t_task_accept ta, t_user tau WHERE t.ID = ta.TASK_ID AND t.USER_ID = tu.ID AND ta.USER_ID = tau.ID AND t.status = 1 AND ta.tip = 1 AND ta.STATUS = 1 AND t.USER_ID = ? ";
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				//paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
				paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
			}
			sql+=") a ";
		}
		
		return baseDAO.getGenericCountToSQL(sql, paramlist.toArray());
	}
	
	public static void main(String[] args){
		List<Object> objects = new ArrayList<Object>();
		String aaa = "1111111";
		objects.add(aaa);
		objects.add(aaa);
		System.out.println(objects.size());
		System.out.println(objects.get(0));
		System.out.println(objects.get(1));
	}

	@Override
	public void delAccept(JSONObject params) throws Exception {
		String reqType = params.optString("reqType");
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(Integer.parseInt(params.optString("state")));//0:删除1:显示
		paramlist.add(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
		paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
		
		String sql = "";
		if("1".equals(reqType)){
			String ssql = "select t.id from t_task t, t_task_accept ta where t.id = ta.task_id and ta.user_id = ? and t.status = 1 and ta.state = 1";
			List<Map<String, Object>> maps = baseDAO.getGenericBySQL(ssql, new Object[]{Integer.parseInt(params.get("fromUserId").toString())});
			if(maps != null && maps.size() > 0){
				sql+="update t_task_accept  set state = ? , update_date = ? where user_id = ? and state = 1 and task_id in(";
				for(Map<String, Object> map : maps){
					sql += "?,";
					paramlist.add(Integer.parseInt(map.get("id").toString()));
				}
				sql = sql.substring(0, sql.length() - 1);
				sql += ")";
			}
			//sql+="update t_task_accept  set status = ? , update_date = ? where user_id = ? and status = 1 and task_id in(select t.id from t_task t where t.status = 1)";
			//sql+="update TaskAccept set status = ?, updateDate = ?  where userId = ? and status = 1 and task.status = 1";
		} else {
			sql+="update t_task_accept  set state = ? , update_date = ? where user_id = ? and state = 1 and task_id in(";
			String taskIdStr = params.get("taskIds").toString();
			String[] taskIds = taskIdStr.split(",");
			for(String taskId : taskIds){
				sql += "?,";
				paramlist.add(Integer.parseInt(taskId));
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ")";
		}
		
		baseDAO.execteNativeBulk(sql, paramlist.toArray());
		
		log.info("任务接受/拒绝成功;请求内容"+JSONObject.fromObject(params).toString());
	}

	@Override
	public void delTask(JSONObject params) throws Exception {
		String reqType = params.optString("reqType");
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(Integer.parseInt(params.optString("state")));//0:删除1:显示
		paramlist.add(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
		paramlist.add(Integer.parseInt(params.get("fromUserId").toString()));
		
		String sql = "";
		if("1".equals(reqType)){
			String ssql = "select t.id from t_task t where t.user_id = ? and t.state = 1";
			List<Map<String, Object>> maps = baseDAO.getGenericBySQL(ssql, new Object[]{Integer.parseInt(params.get("fromUserId").toString())});
			if(maps != null && maps.size() > 0){
				sql+="update t_task  set state = ? , update_date = ? where user_id = ? and state = 1 and id in(";
				for(Map<String, Object> map : maps){
					sql += "?,";
					paramlist.add(Integer.parseInt(map.get("id").toString()));
				}
				sql = sql.substring(0, sql.length() - 1);
				sql += ")";
			}
			//sql+="update t_task_accept  set status = ? , update_date = ? where user_id = ? and status = 1 and task_id in(select t.id from t_task t where t.status = 1)";
			//sql+="update TaskAccept set status = ?, updateDate = ?  where userId = ? and status = 1 and task.status = 1";
		} else {
			sql+="update t_task  set state = ? , update_date = ? where user_id = ? and state = 1 and id in(";
			String taskIdStr = params.get("taskIds").toString();
			String[] taskIds = taskIdStr.split(",");
			for(String taskId : taskIds){
				sql += "?,";
				paramlist.add(Integer.parseInt(taskId));
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ")";
		}
		
		baseDAO.execteNativeBulk(sql, paramlist.toArray());
		
		log.info("任务接受/拒绝成功;请求内容"+JSONObject.fromObject(params).toString());
	}

	@Override
	public void modifyTaskTip(JSONObject params) {
		String sql = "update t_task_accept set tip = ? , update_date = ? where user_id = ? and task_id = ?";
		baseDAO.execteNativeBulk(sql, new Object[]{params.optInt("tip"), DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2), params.optInt("toUserId"), params.optInt("taskId")});
	}

}
