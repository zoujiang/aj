package com.aj.familymgr.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.familymgr.vo.TDivorceInfo;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familymgr.vo.TInviteInfo;
import com.aj.msg.api.jpush.JPush;
import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;
import com.aj.sys.vo.TSysMsgAlert;
import com.aj.task.service.TaskService;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.StringUtil;
import com.util.UUIDUtil;


/**
 * 离婚申请
 * */

@Service("divorceApply")
public class DivorceApplyService implements PublishService{

	private Logger log = Logger.getLogger(DivorceService.class);
	
	@Resource
	private GenericDAO baseDAO;
	@Resource
	private DivorceServiceInterface divorceService;
	@Resource
	private TaskService taskService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String divorceDesc = params.optString("divorceDesc");
		String type = params.optString("model");  //是否强制离婚标识 1：是  0或其他：否
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
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
			//查询家庭信息，是否已婚
			TFamilyInfo family = baseDAO.get(TFamilyInfo.class, user.getFamilyId());
			if(family.getIsMarried() != 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您还未婚，不能执行离婚操作");
				return returnJSON.toString();
			}
			
			hsql = "from TInviteInfo where (creatrUserId = ? or relationUserId = ? ) and  ( fix_call_name = '老公' or  fix_call_name = '老婆')   and comfirmState = '1'";
			List<TInviteInfo> inviteList =  baseDAO.getGenericByHql(hsql, userId, userId);
			if(inviteList == null || inviteList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "未能找到您的配偶信息");
				return returnJSON.toString();
			}
			String otherUserId = "";  //配偶的用户id
			if(inviteList.get(0).getCreatrUserId().equals(userId)){
				//当前用户是关系的邀请者
				otherUserId = inviteList.get(0).getRelationUserId();
			}else{
				otherUserId = inviteList.get(0).getCreatrUserId();
			}
			//查询是否有上一次离婚申请记录，如果有，且上一次申请未超时，那么本次离婚申请为强制离婚
			String queryHsql = "from TDivorceInfo where createUserId = ? and relationUserId = ?  order by applyDate desc";
			List<TDivorceInfo> divorceList = baseDAO.getGenericByHql(queryHsql, userId, otherUserId);
			if(divorceList != null && divorceList.size() > 0){
				TDivorceInfo divorceInfo = divorceList.get(0);
			//	if(divorceInfo.getStatus() != 3){
					try {
						if("1".equals(type)){
							List<TUser> otherUserList = baseDAO.getGenericByHql(" from TUser where id = ?", Integer.parseInt(otherUserId));
							divorceService.divorce(user, otherUserList.get(0), inviteList.get(0));
							
							divorceInfo.setStatus(2);
							baseDAO.update(divorceInfo);
							result.put("succMsg", "成功离婚！");	
							returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "");
							return returnJSON.toString();
						}else{
							
							String applyDate = divorceInfo.getApplyDate();
							//系统设置离婚申请超时时间
							String timeoutDay = SystemConfig.getValue("divorce.first.apply.timeout");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							
								int hour = (int)((System.currentTimeMillis() -  sdf.parse(applyDate).getTime()) / (1000 * 60 * 60 )); 
								if(hour > Integer.parseInt(timeoutDay)){
									//已经超时， 更新该条离婚申请为超时状态
									divorceInfo.setStatus(3);
									baseDAO.update(divorceInfo);
								}else{
									//判断是否间隔了24小时，如果没有则提示等待
									
									if((System.currentTimeMillis() - sdf.parse(divorceInfo.getApplyDate()).getTime()) < (1000 * 60 * 60 *24 )){
										long preApplyTime = sdf.parse(applyDate).getTime();
										
										returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
										returnJSON.put("result", result);
										returnJSON.put("errorMsg", "距离上次申请未过24小时，请于"+ sdf.format(preApplyTime + (1000 * 60 * 60 *24 )) +"再试！");
										return returnJSON.toString();
									}else if((System.currentTimeMillis() - sdf.parse(divorceInfo.getApplyDate()).getTime()) < (1000 * 60 * 60 * Integer.parseInt(timeoutDay) )){
										
										//未超时， 则是强制离婚，返回给客户端一个状态，是否确定强制离婚
										result.put("succMsg", "确认是否强制离婚？");	
										returnJSON.put("returnCode", "100002");
										returnJSON.put("result", result);
										returnJSON.put("errorMsg", "");
										return returnJSON.toString();
										
									}
								}
							}
					} catch (ParseException e) {
						e.printStackTrace();
					}
			//	}
			}
			if(divorceDesc == null){
				divorceDesc = "";
			}
			String currentDate = DateUtils.currentDate();
			//记录信息到离婚申请表
			TDivorceInfo model = new TDivorceInfo();
			String uuid = UUIDUtil.uuid();
			model.setId(uuid);
			model.setCreateUserId(userId);
			model.setRelationUserId(otherUserId);
			model.setApplyDate(currentDate);
			model.setStatus(1);
			model.setDivorceDesc(divorceDesc);
			baseDAO.save(model);
			String nick = (user.getNickName() == null || "".equals(user.getNickName())) ? StringUtil.telTool(user.getUserTel()) : user.getNickName();
			sendSysMsgToRelationUser(Integer.parseInt(otherUserId), userId, uuid, 2, "系统消息", nick+"恳请离婚："+divorceDesc);
			//推送
			try {
				List<String> aliases = new ArrayList<String>();
				aliases.add(userId);  
				boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(nick+"恳请离婚："+divorceDesc);
				log.info("离婚申请时，消息推送结果状态："+b);
			} catch (Exception e) {
				log.info("离婚申请时，消息推送异常："+e);
			}
			//添加一个任务
			
		/*	try {
				
				JSONObject task = new JSONObject();
				task.put("content", "您的配偶向您提出了离婚申请，是否接受？");
				task.put("fromUserId", userId);
				task.put("sendTime", currentDate);
				task.put("type", "2");
				task.put("toUserId", otherUserId);
				taskService.addTask(task);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
			result.put("succMsg", "申请成功！");	
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
				
		}
		
	}
	
	
	private void sendSysMsgToRelationUser(Integer targetUserId, String createUserId, String divorceId, int msgType,
			String msgTitle, String msgContent) {
		
		TSysMsgAlert alert = new TSysMsgAlert();
		alert.setCreateDate(DateUtils.currentDate());
		alert.setHadRead(0);
		alert.setMsgContent(msgContent);
		alert.setMsgTitle(msgTitle);
		alert.setMsgType(msgType);
		alert.setRelationId(divorceId);
		alert.setReciveUserId(targetUserId);
		alert.setSendUserId(Integer.parseInt(createUserId));
		baseDAO.save(alert);
		
	}
}
