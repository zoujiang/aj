package com.aj.familymgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 按手机号邀请家庭成员
 * */

@Service("addFamily")
public class AddFamilyService implements PublishService{

	private Logger log = Logger.getLogger(AddFamilyService.class);
	
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
		String fixSortName = params.optString("fixSortName");  
		String fixCallName = params.optString("fixCallName");
		String customCallName = params.optString("customCallName");
		String userTel = params.optString("userTel");
		String inviteArea = params.optString("inviteArea");
		String isPrivate = params.optString("isPrivate");
		String regInfo = params.optString("regInfo");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(fixSortName == null || "".equals(fixSortName)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "fixSortName为空！");
			return returnJSON.toString();
		}
		if(fixCallName == null || "".equals(fixCallName)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "fixCallName为空！");
			return returnJSON.toString();
		}
		if(customCallName == null || "".equals(customCallName)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "customCallName为空！");
			return returnJSON.toString();
		}
		if(userTel == null || "".equals(userTel)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userTel为空！");
			return returnJSON.toString();
		}
		if( (!"老公".equals(fixCallName) && !"老婆".equals(fixCallName)) && ( inviteArea == null || "".equals(inviteArea))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "inviteArea为空！");
			return returnJSON.toString();
		}
		if((!"老公".equals(fixCallName) && !"老婆".equals(fixCallName)) && (isPrivate == null || "".equals(isPrivate))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "isPrivate为空！");
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
				returnJSON.put("errorMsg", "您邀请的用户不存在，请确认手机号是否正确且注册！");
				return returnJSON.toString();
			}
			//检查是否已经邀请此人 并同意
			String sql = "SELECT 1 FROM t_home_info h  WHERE h.is_valid = 0 and h.create_user_id =  CASE WHEN h.is_private = '0' THEN  ? ELSE ?  END AND CASE WHEN h.invite_area = '1' THEN h.relation_user_id = ? ELSE h.relation_user_id = ? end";
			List<Map<String, Object>> homeList = baseDAO.getGenericBySQL(sql, new Object[]{user.getFamilyId(), user.getId(), relationUserList.get(0).getFamilyId(), relationUserList.get(0).getId()});
			if(homeList != null && homeList.size() > 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "家庭关系已经存在，不能重复添加");
				return returnJSON.toString();
			}
			
			if("老公".equals(fixCallName) || "老婆".equals(fixCallName)){
				
				//判断是否已经添加过老公或老婆
				String userSql = "select id from t_user where familyid = ?";
				List<Map<String, Object>> urList = baseDAO.getGenericBySQL(userSql, new Object[]{user.getFamilyId()});
				if(urList != null && urList.size() == 2){
					if("老公".equals(fixCallName)){
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "您已经添加过老公");
						return returnJSON.toString();
					}
					if("老婆".equals(fixCallName)){
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "您已经添加过老婆");
						return returnJSON.toString();
					}
					
				}
				//判断性别
				if(user.getSex() == 0 && "老公".equals(fixCallName)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您的性别为男，不能添加老公");
					return returnJSON.toString();
				}
				if(user.getSex() == 1 && "老婆".equals(fixCallName)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您的性别为女，不能添加老婆");
					return returnJSON.toString();
				}
				if(relationUserList.get(0).getSex() == 0 && "老婆".equals(fixCallName)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您添加的“老婆”对象性别为男，添加失败");
					return returnJSON.toString();
				}
				if(relationUserList.get(0).getSex() == 1 && "老公".equals(fixCallName)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您添加的“老公”对象性别为女，添加失败");
					return returnJSON.toString();
				}
				
				//结婚， 验证年龄是否满足
				if(user.getSex() == 0 && Integer.parseInt(DateUtils.formatBirthdat2Age(user.getBirthday())) < 22){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您还未到法定结婚年龄，不能添加老婆");
					return returnJSON.toString();
				}else if(user.getSex() == 1 && Integer.parseInt(DateUtils.formatBirthdat2Age(user.getBirthday())) < 20){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您还未到法定结婚年龄，不能添加老公");
					return returnJSON.toString();
				}
				if(relationUserList.get(0).getSex() == 0 && Integer.parseInt(DateUtils.formatBirthdat2Age(relationUserList.get(0).getBirthday())) < 22){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您添加的老公还未到法定结婚年龄");
					return returnJSON.toString();
				}else if(relationUserList.get(0).getSex() == 1 && Integer.parseInt(DateUtils.formatBirthdat2Age(relationUserList.get(0).getBirthday())) < 20){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您添加的老婆还未到法定结婚年龄");
					return returnJSON.toString();
				}
				//验证对方是否是已婚状态
				TFamilyInfo family = baseDAO.get(TFamilyInfo.class, relationUserList.get(0).getFamilyId());
				String call = "老婆";
				if(relationUserList.get(0).getSex() == 0){
					call = "老公";
				}
				if(family.getIsMarried() ==3){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "对方未婚状态，不能添加为"+call);
					return returnJSON.toString();
				}
			}
			
			
			TInviteInfo invite = new TInviteInfo();
			String inviteId = UUIDUtil.uuid();
			String currentDate = DateUtils.currentDate();
			invite.setComfirmState("0");
			invite.setCreateDate(currentDate);
			invite.setCreatrUserId(userId);
			invite.setCustomCallName(customCallName);
			invite.setFixCallName(fixCallName);
			invite.setFixSortName(fixSortName);
			invite.setId(inviteId);
			invite.setInviteArea(inviteArea);
			invite.setIsPrivate(isPrivate);
			invite.setRegInfo(regInfo);
			invite.setRelationUserId(relationUserList.get(0).getId()+"");
			baseDAO.save(invite);
			
			String f = "你个人";
			if("1".equals(inviteArea)){
				f = "你的家庭";
			}
			
			String targetNick = relationUserList.get(0).getNickName() == null ? relationUserList.get(0).getUserTel() :relationUserList.get(0).getNickName();
			String creatNick = user.getNickName() == null ? user.getUserTel() : user.getNickName();
			//发送系统消息到被邀请人
			String content = creatNick +"邀请"+f+"，称你为"+customCallName+"："+regInfo;
			sendSysMsgToRelationUser( Integer.parseInt(userId), relationUserList.get(0).getId(), inviteId, 1, "系统消息", content);
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
				task.put("content", "用户【"+creatNick+"】添加您为家人，他称呼您为【"+ customCallName+"】，是否接受？");
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
		alert.setDealResult(0);
		baseDAO.save(alert);
		
	}

}
