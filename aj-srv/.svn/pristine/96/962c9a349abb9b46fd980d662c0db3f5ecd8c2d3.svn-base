package com.aj.familymgr.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familymgr.vo.TInviteInfo;
import com.aj.msg.api.jpush.JPush;
import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;
import com.aj.sys.vo.TSysMsgAlert;
import com.aj.task.service.TaskService;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.StringUtil;
import com.util.UUIDUtil;


/**
 * 结婚
 * */

@Service("marry")
public class MarryService implements PublishService{

	private Logger log = Logger.getLogger(MarryService.class);
	
	@Resource
	private GenericDAO baseDAO;
	@Resource
	private TaskService taskService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String userTel = params.optString("userTel");
		String marryDesc = params.optString("marryDesc");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		
		if(userTel == null || "".equals(userTel)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userTel为空！");
			return returnJSON.toString();
		}
		
		String hsql = " from TUser where id = ? ";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该用户！");
			return returnJSON.toString();
		}else{
			TUser user = userList.get(0);
			String birthday = user.getBirthday();
			if(birthday == null || "".equals(birthday)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "请先设置您的出生日期！");
				return returnJSON.toString();
			}else{
				int age = DateUtil.getAge(birthday);
				if( (user.getSex() == 0 && age < 22) || (user.getSex() == 1 && age < 20)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您尚未达到结婚年龄!");
					return returnJSON.toString();
				}
			}
			
			if(user.getUserTel().equals(userTel)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "不能添加自己！");
				return returnJSON.toString();
			}
			
			String hsql2 = " from TUser where userTel = ? ";
			List<TUser> relationUserList = baseDAO.getGenericByHql(hsql2, userTel);
			
			if(relationUserList == null || relationUserList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您的结婚对象还未注册或手机号输入错误！");
				return returnJSON.toString();
			}
			TUser relationUser = relationUserList.get(0);
			
			TFamilyInfo family = baseDAO.get(TFamilyInfo.class, relationUser.getFamilyId());
			if(family != null && family.getIsMarried() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您的结婚对象已经结婚了哦");
				return returnJSON.toString();
			}
			
			if(user.getSex() == relationUser.getSex()){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您和您的结婚对象性别不能相同哦");
				return returnJSON.toString();
			}
			
			if(relationUser.getSex() == 0 && Integer.parseInt(DateUtils.formatBirthdat2Age(relationUser.getBirthday())) < 22){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您的结婚对象还未达到结婚年龄");
				return returnJSON.toString();
			}
			if(relationUser.getSex() == 1 && Integer.parseInt(DateUtils.formatBirthdat2Age(relationUser.getBirthday())) < 20){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您的结婚对象还未达到结婚年龄");
				return returnJSON.toString();
			}
			
			TInviteInfo invite = new TInviteInfo();
			String inviteId = UUIDUtil.uuid();
			String currentDate = DateUtils.currentDate();
			invite.setComfirmState("0");
			invite.setCreateDate(currentDate);
			invite.setCreatrUserId(userId);
			if(user.getSex() == 0){
				invite.setCustomCallName("老婆");
				invite.setFixCallName("老婆");
			}else if(user.getSex() == 1){
				invite.setCustomCallName("老公");
				invite.setFixCallName("老公");
			}
			invite.setFixSortName("无");
			invite.setId(inviteId);
			invite.setInviteArea("1");
			invite.setIsPrivate("0");
			invite.setRegInfo(marryDesc);
			invite.setRelationUserId(relationUserList.get(0).getId()+"");
			baseDAO.save(invite);
			/*String customCallName = "";
			if(user.getSex() == 0){
				customCallName = "老婆";
			}else if(user.getSex() == 1){
				customCallName="老公";
			}*/
			//String reciveUserNick = relationUserList.get(0).getNickName() == null ? relationUserList.get(0).getUserTel() :relationUserList.get(0).getNickName();
			String sendUserNick = (user.getNickName() == null || "".equals(user.getNickName())) ? StringUtil.telTool(user.getUserTel())  : user.getNickName();
			//发送系统消息到被邀请人
			String content = sendUserNick +"恳请结婚："+marryDesc;
			sendSysMsgToRelationUser(user.getId(), relationUserList.get(0).getId(), inviteId, 1, "系统消息", content);
			
			//推送
			try {
				List<String> aliases = new ArrayList<String>();
				aliases.add(relationUserList.get(0).getId()+"");  
				boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(content);
				log.info("邀请亲友时，消息推送结果状态："+b);
			} catch (Exception e) {
				log.info("邀请亲友时，消息推送异常："+e);
			}
			
			
			//添加一个任务
			
		/*	try {
				
				JSONObject task = new JSONObject();
				task.put("content", "用户【"+reciveUserNick+"】添加您为家人，他称呼您为【"+ customCallName+"】，是否接受？");
				task.put("fromUserId", userId);
				task.put("sendTime", currentDate);
				task.put("type", "2");
				task.put("toUserId", relationUserList.get(0).getId()+"");
				taskService.addTask(task);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			result.put("succMsg", "邀请成功，等待对方确认！");	
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
				
		}
		
	}
	
	
	private void sendSysMsgToRelationUser(Integer sendUserId, Integer targetUserId, String relationId, int msgType,
			String msgTitle, String msgContent) {
		
		TSysMsgAlert alert = new TSysMsgAlert();
		alert.setCreateDate(DateUtils.currentDate());
		alert.setHadRead(0);
		alert.setMsgContent(msgContent);
		alert.setMsgTitle(msgTitle);
		alert.setMsgType(msgType);
		alert.setRelationId(relationId);
		alert.setReciveUserId(targetUserId);
		alert.setSendUserId(sendUserId);
		baseDAO.save(alert);
		
	}
	
}
