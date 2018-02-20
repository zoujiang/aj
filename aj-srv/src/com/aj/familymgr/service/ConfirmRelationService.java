package com.aj.familymgr.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.easemob.service.EasemobGroupService;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familymgr.vo.THomeInfo;
import com.aj.familymgr.vo.TInviteInfo;
import com.aj.msg.api.jpush.JPush;
import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;
import com.aj.sys.vo.TSysMsgAlert;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;

@Service("confirmFamily")
public class ConfirmRelationService implements PublishService{

	private Logger log = Logger.getLogger(ConfirmRelationService.class);
	
	@Resource
	private GenericDAO baseDAO;
	@Resource
	private EasemobGroupService easemobGroupService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String comefrom = params.optString("comefrom");
		String sysAlertMsgId = params.optString("sysAlertMsgId");
		String taskFromUserId = params.optString("taskFromUserId");
		String taskSendTime = params.optString("taskSendTime");
		String customCallName = params.optString("customCallName");
		String oper = params.optString("oper"); //1：同意  2：拒绝
		String reason = params.optString("reason");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(comefrom == null || "".equals(comefrom)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "comefrom为空！");
			return returnJSON.toString();
		}
		if("1".equals(comefrom)){
			
			if(taskFromUserId == null || "".equals(taskFromUserId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "taskFromUserId为空！");
				return returnJSON.toString();
			}
			if(taskSendTime == null || "".equals(taskSendTime)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "taskSendTime为空！");
				return returnJSON.toString();
			}
		}else if("2".equals(comefrom)){
			
			if(sysAlertMsgId == null || "".equals(sysAlertMsgId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "sysAlertMsgId为空！");
				return returnJSON.toString();
			}
		}
		
		
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}
		
		List<TInviteInfo> inviteList  = new ArrayList<TInviteInfo>();
		if("1".equals(comefrom)){
			
			String hsql = "from TInviteInfo where creatrUserId = ? and relationUserId = ? and createDate = ? and comfirmState = '0'";
			inviteList = baseDAO.getGenericByHql(hsql, taskFromUserId, userId, taskSendTime);
		}else if("2".equals(comefrom)){
			String sql = "SELECT RELATION_ID inviteId FROM t_sys_msg_alert WHERE id = ?";
			List<Map<String, Object>> inviteMapList = baseDAO.getGenericBySQL(sql, new Object[]{sysAlertMsgId});
			if(inviteMapList != null && inviteMapList.size() > 0){
				String inviteId = inviteMapList.get(0).get("inviteId")+"";
				inviteList = baseDAO.getGenericByHql("from TInviteInfo where id = ? and comfirmState = '0'", inviteId);
			}
		}
		if(inviteList != null && inviteList.size() > 0){
			TInviteInfo invite = inviteList.get(0);
			//判断邀请是否过期
			String inviteExpiredDay = SystemConfig.getValue("invite_expired_day");
			int expiredDay = 0;
			if(inviteExpiredDay == null || "".equals(inviteExpiredDay)){
				expiredDay = 7;
			}else{
				try {
					expiredDay = Integer.parseInt(inviteExpiredDay);
				} catch (NumberFormatException e) {
					expiredDay = 7;
				}
			}
			long currentMillis = System.currentTimeMillis();
			long createMillis = DateUtils.getTimeMillis(invite.getCreateDate());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if((currentMillis - createMillis) > (expiredDay * 24 * 60 * 60 * 1000) ){
				
				//更新邀请信息状态为：已过期 ：
				invite.setComfirmState("3");
				baseDAO.saveOrUpdate(invite);
				
				if("2".equals(comefrom)){
					String natvieSQL = "update t_sys_msg_alert set DEAL_RESULT = ?, UPDATE_DATE = ? where id = ?";
					baseDAO.execteNativeBulk(natvieSQL, 3, sdf.format(new Date()), sysAlertMsgId);
				}
				
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "邀请信息已经过期！");
				return returnJSON.toString();
			}
			
			String userHsql = "from TUser where id = ?";
			TUser createUser = (TUser) baseDAO.getGenericByHql(userHsql, Integer.parseInt(invite.getCreatrUserId())).get(0);
			TUser relationUser = (TUser) baseDAO.getGenericByHql(userHsql, Integer.parseInt(invite.getRelationUserId())).get(0);
			
			if("1".equals(oper)){
				//同意
				//验证关系是否已经建立， 出现在互相邀请，或者同时邀请父亲又邀请母亲等类似情况
				String regSql = "SELECT 1 FROM t_home_info h  WHERE h.is_valid = 0 and h.create_user_id =  CASE WHEN h.is_private = '0' THEN  ? ELSE ?  END AND CASE WHEN h.invite_area = '1' THEN h.relation_user_id = ? ELSE h.relation_user_id = ? end";
				List<Map<String, Object>> homeList = baseDAO.getGenericBySQL(regSql, new Object[]{createUser.getFamilyId(), createUser.getId(), relationUser.getFamilyId(), relationUser.getId()});
				if(homeList != null && homeList.size() > 0){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "家庭关系已经存在，不能重复添加");
					return returnJSON.toString();
				}
				
				
				if("老公".equals(invite.getFixCallName()) || "老婆".equals(invite.getFixCallName())){
					
					String fsql = "select id from t_user where familyid = ?";
					List<Map<String, Object>> list = baseDAO.getGenericBySQL(fsql, new Object[]{createUser.getFamilyId()});
					if(list != null && list.size()>1){
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "邀请您的人已经结婚，关系确认失败");
						return returnJSON.toString();
					}
					list = baseDAO.getGenericBySQL(fsql, new Object[]{relationUser.getFamilyId()});
					if(list != null && list.size()>1){
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "您已经结婚，关系确认失败");
						return returnJSON.toString();
					}
					
					//1.设置家庭名称
					TFamilyInfo fi = new TFamilyInfo();
					fi.setId(createUser.getFamilyId());
					String nickName = (createUser.getNickName() == null || "".equals(createUser.getNickName())) ? createUser.getUserTel() :createUser.getNickName();
					String nickName2 = (relationUser.getNickName() == null || "".equals(relationUser.getNickName())) ? relationUser.getUserTel() :relationUser.getNickName();
					fi.setFamilyName(nickName+"&"+nickName2+"之家");
					//设置结婚日期
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
					fi.setIsMarried(0);
					fi.setMarriedDate(sdf2.format(new Date()));
					baseDAO.update(fi);
					
					//2.生成共用的夫妻相册。
					String now = sdf.format(new Date());
					TAlbum album1 = new TAlbum();
					album1.setId(UUIDUtil.uuid());
					album1.setAlbumName("夫妻相册");
					album1.setAlbumType(6);
					album1.setCategory("1");
					album1.setCreateDate(now);
					album1.setCreateUserId(createUser.getId());
					album1.setDescription("系统生成");
					album1.setIsSysinit(0);
					album1.setOwnerUserId(createUser.getId());
					album1.setIsDir(0);
					album1.setFamilyId(createUser.getFamilyId());
					album1.setAlbumUrl( SystemConfig.getValue("fuqi.url"));
					album1.setVisibleProperty(1);
					baseDAO.save(album1);
					//zouedit
					//3.更新relationUser的家庭相册的familyid
					String sql = "UPDATE t_album SET FAMILY_ID = ? WHERE ALBUM_TYPE = 4 AND CREATE_USER_ID = ?";
					baseDAO.execteNativeBulk(sql, createUser.getFamilyId(), relationUser.getId());
					//更新传承相册的familyid
					sql = "UPDATE t_album SET FAMILY_ID = ? WHERE ALBUM_TYPE = 3 AND FAMILY_ID = ?";
					baseDAO.execteNativeBulk(sql, createUser.getFamilyId(), relationUser.getFamilyId());
					//4.是否有孩子存在
					/* sql = "update t_children_info set family_id = ? where family_id = ?";
					 baseDAO.execteNativeBulk(sql, createUser.getFamilyId(), relationUser.getFamilyId());
					 */
					 sql = "SELECT id , family_id FROM t_children_info WHERE family_id LIKE '%"+relationUser.getFamilyId()+"%'";
					 List<Map<String, Object>> children = baseDAO.getGenericBySQL(sql, new Object[]{});
					 if(children != null && children.size() > 0){
						 String updateChildrenSql = "update t_children_info set family_id = ? where id = ?";
						 for(Map<String, Object> child : children){
								 
								 String id = child.get("id").toString();
								 String familyId = child.get("family_id").toString();
								 baseDAO.execteNativeBulk(updateChildrenSql, familyId.replace(relationUser.getFamilyId(), createUser.getFamilyId()), id);
						 }
					 }
					 
					 
					 //胎儿
					/* sql = "update t_baby_info set family_id = ? where family_id = ?";
					 baseDAO.execteNativeBulk(sql, createUser.getFamilyId(), relationUser.getFamilyId());
					 */
					 sql = "SELECT id , family_id, is_private FROM t_baby_info WHERE family_id LIKE '%"+relationUser.getFamilyId()+"%'";
					 List<Map<String, Object>> babys = baseDAO.getGenericBySQL(sql, new Object[]{});
					 if(babys != null && babys.size() > 0){
						 String updateBabySql = "update t_baby_info set family_id = ? where id = ?";
						 for(Map<String, Object> baby : babys){
							 String id = baby.get("id").toString();
							 String familyId = baby.get("family_id").toString();
							 baseDAO.execteNativeBulk(updateBabySql, familyId.replace(relationUser.getFamilyId(), createUser.getFamilyId()), id);
						 }
					 }
					 
					 
					
					//5.判断是否有邀请家庭的讨论组， 把配偶加到讨论组
					sql = "SELECT easemobGroupId FROM t_home_info WHERE is_valid = 0 and create_user_id = ?";
					List<Map<String, Object>> easemobGroupList = baseDAO.getGenericBySQL(sql, new Object[]{createUser.getFamilyId()});
					if(easemobGroupList != null && easemobGroupList.size() > 0){
						for(Map<String, Object> easemobGroup : easemobGroupList){
							JSONObject paramJSON = new JSONObject();
							paramJSON.put("group_id", easemobGroup.get("easemobGroupId"));
							List<String> members = new ArrayList<String>();
							members.add(relationUser.getId()+"");
							paramJSON.put("usernames", members);
							try {
								easemobGroupService.addGroupMembs(paramJSON);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					//6.查询relationUser的家庭讨论组，把createUser加入到讨论组
					easemobGroupList = baseDAO.getGenericBySQL(sql, new Object[]{relationUser.getFamilyId()});
					if(easemobGroupList != null && easemobGroupList.size() > 0){
						for(Map<String, Object> easemobGroup : easemobGroupList){
							JSONObject paramJSON = new JSONObject();
							paramJSON.put("group_id", easemobGroup.get("easemobGroupId"));
							List<String> members = new ArrayList<String>();
							members.add(createUser.getId()+"");
							paramJSON.put("usernames", members);
							try {
								easemobGroupService.addGroupMembs(paramJSON);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					//7.更新homeinfo中创建者或者是被关联者为relationUser的familyid为当前createUser的familyid
				/*	sql = "update t_home_info set create_user_id = ? where create_user_id = ?";
					baseDAO.execteNativeBulk(sql, createUser.getFamilyId(), relationUser.getFamilyId());
					sql = "update t_home_info set relation_user_id = ? where relation_user_id = ?";
					baseDAO.execteNativeBulk(sql, createUser.getFamilyId(), relationUser.getFamilyId());*/
					
					String hsql = "from THomeInfo where creatrUserId= ? ";
					List<THomeInfo> homes = baseDAO.getGenericByHql(hsql, relationUser.getFamilyId());
					String hsql1 = "from THomeInfo where creatrUserId= ? and relationUserId = ?";
					if(homes != null && homes.size()>0){
						for(THomeInfo home : homes){
							List<THomeInfo> l = baseDAO.getGenericByHql(hsql1, createUser.getFamilyId(), home.getRelationUserId());
							if(l ==null || l.size() == 0){
								home.setCreatrUserId(createUser.getFamilyId());
								baseDAO.update(home);
							}else{
								home.setIsValid(1);
								baseDAO.update(home);
							}
						}
					}
					
					hsql = "from THomeInfo where relationUserId= ? ";
					homes = baseDAO.getGenericByHql(hsql, relationUser.getFamilyId());
					hsql1 = "from THomeInfo where creatrUserId= ? and relationUserId = ?";
					if(homes != null && homes.size()>0){
						for(THomeInfo home : homes){
							List<THomeInfo> l = baseDAO.getGenericByHql(hsql1, home.getCreatrUserId(), createUser.getFamilyId());
							if(l ==null || l.size() == 0){
								home.setRelationUserId(createUser.getFamilyId());
								baseDAO.update(home);
							}else{
								home.setIsValid(1);
								baseDAO.update(home);
							}
						}
					}
					
					
					//8.设置家庭ID一致
					relationUser.setFamilyId(createUser.getFamilyId());
					baseDAO.update(relationUser);
					
					//发送一条系统消息给邀请人
					String nick = "";
					if(relationUser.getNickName() == null || "".equals(relationUser.getNickName())){
						String userTel = relationUser.getUserTel();
						
						nick = userTel.substring(0, 3)+"****"+userTel.substring(7,11);
					}else{
						nick = relationUser.getNickName().toString();
					}
					String content = nick +"已同意你的结婚："+reason;
					sendSysMsgToRelationUser( Integer.parseInt(userId), createUser.getId(), invite.getId(), 0, "系统消息", content);
					sendPush(createUser.getId()+"", content);
				}else{
					
					//发送一条系统消息给邀请人
					String nick = "";
					if(relationUser.getNickName() == null || "".equals(relationUser.getNickName())){
						String userTel = relationUser.getUserTel();
						
						nick = userTel.substring(0, 3)+"****"+userTel.substring(7,11);
					}else{
						nick = relationUser.getNickName().toString();
					}
					String content = nick +"已同意你的邀请，称你为"+customCallName+"："+reason;
					sendSysMsgToRelationUser( Integer.parseInt(userId), createUser.getId(), invite.getId(), 0, "系统消息", content);
					sendPush(createUser.getId()+"", content);
				}
				
					
				//建立一个环信讨论组，方便聊天
				//创建环信讨论组
				JSONObject paramJSON = new JSONObject();
				paramJSON.put("groupname", "讨论组");
				paramJSON.put("desc","");
				paramJSON.put("public",false); //是否公开
				paramJSON.put("approval",true); //是否需要验证
				paramJSON.put("owner",userId);
				Set<String> members = new HashSet<String>();
				
				
				//1.根据邀请信息，生成正向关系
				THomeInfo home = new THomeInfo();
				home.setId(UUIDUtil.uuid());
				home.setCallName(invite.getCustomCallName());
				home.setIsValid(0);
				//邀请范围  1：全家  2：个人
				String inviteArea = invite.getInviteArea();
				//0：夫妻均可见 1：仅个人可见
				String isPrivate = invite.getIsPrivate();
				home.setInviteArea(inviteArea);
				if("1".equals(inviteArea)){
					
					home.setRelationUserId(relationUser.getFamilyId());
					
					//邀请全家
					List<TUser> users = baseDAO.getGenericByHql("from TUser where familyId = ? ", relationUser.getFamilyId());
					for(TUser user : users){
						members.add(user.getId()+"");
					}
				}else{
					home.setRelationUserId(invite.getRelationUserId());
					
					members.add(relationUser.getId()+"");
				}
				if("0".equals(isPrivate)){
					home.setCreatrUserId(createUser.getFamilyId());
					
					//夫妻可见
					List<TUser> users = baseDAO.getGenericByHql("from TUser where familyId = ? ", createUser.getFamilyId());
					for(TUser user : users){
						members.add(user.getId()+"");
					}
				}else{
					home.setCreatrUserId(invite.getCreatrUserId());
					
					members.add(createUser.getId()+"");
				}
				home.setIsPrivate(isPrivate);
				home.setInviteId(invite.getId());
				
				//2.根据邀请信息，生成反向关系
				
				THomeInfo home2 = new THomeInfo();
				home2.setId(UUIDUtil.uuid());
				home2.setCallName(customCallName == null ? "亲人" : customCallName);
				home2.setIsValid(0);
				if("1".equals(inviteArea)){
					home2.setIsPrivate("0");
					home2.setCreatrUserId(relationUser.getFamilyId());
				}else{
					home2.setIsPrivate("1");
					home2.setCreatrUserId(invite.getRelationUserId());
				}
				
				if("0".equals(isPrivate)){
					home2.setInviteArea("1");
					home2.setRelationUserId(createUser.getFamilyId());
				}else{
					home2.setInviteArea("2");
					home2.setRelationUserId(invite.getCreatrUserId());
				}
				home2.setInviteId(invite.getId());
				paramJSON.put("members",members);
				try {
					JSONObject object = easemobGroupService.createGroup(paramJSON);
					log.info("添加环信讨论组返回："+ object);
					if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
						JSONObject data = object.getJSONObject("data");
						String groupId = data.optString("groupid");
						//更新到讨论组信息中
						
						home.setEasemobGroupId(groupId);
						home2.setEasemobGroupId(groupId);
						
					} else {
						if(object.optString("error") != null && "duplicate_unique_property_exists".equals(object.optString("error"))){
							log.info(userId+"环信讨论组已经存在。。。。。");
						} else{
							log.info(userId+"环信讨论组失败。。。。。"+object.optString("error"));
						}
						
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						result.put("succMsg", "");
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "关系确认失败");
						return returnJSON.toString();
					}
					
				} catch (Exception e) {
					log.info("创建环信讨论组失败："+e);
					
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					result.put("succMsg", "");
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "关系确认失败");
					return returnJSON.toString();
				}
				
				baseDAO.save(home);
				baseDAO.save(home2);
				
				//3.更新邀请信息状态为已接受
				invite.setComfirmState("1");
				baseDAO.saveOrUpdate(invite);
				//根据邀请ID查询系统消息， 更新系统消息处理结果
				if("2".equals(comefrom)){
					baseDAO.update("update TSysMsgAlert set dealResult = 1 where id = ? ", Integer.parseInt(sysAlertMsgId));
				}else{
					baseDAO.update("update TSysMsgAlert set dealResult = 1 where relationId = ? ", invite.getId());
				}
				
			}else if("2".equals(oper)){
				//拒绝
				
				invite.setComfirmState("2");
				invite.setReason(reason);
				baseDAO.saveOrUpdate(invite);
				
				//根据邀请ID查询系统消息， 更新系统消息处理结果
				if("2".equals(comefrom)){
					baseDAO.update("update TSysMsgAlert set dealResult = 2 where id = ? ", Integer.parseInt(sysAlertMsgId));
				}else{
					baseDAO.update("update TSysMsgAlert set dealResult = 2 where relationId = ? ", invite.getId());
				}
				
				//发送一条系统消息给邀请人
				String nick = "";
				if(relationUser.getNickName() == null || "".equals(relationUser.getNickName())){
					String userTel = relationUser.getUserTel();
					
					nick = userTel.substring(0, 3)+"****"+userTel.substring(7,11);
				}else{
					nick = relationUser.getNickName().toString();
				}
				String content = nick +"已拒绝你的邀请："+reason;
				sendSysMsgToRelationUser( Integer.parseInt(userId), createUser.getId(), invite.getId(), 0, "系统消息", content);
				
				
			}
			
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "操作成功！");
			return returnJSON.toString();
			
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "邀请信息不存在或者已经过期！");
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
	private void sendPush(String targetUserId, String msgContent) {
		
		//推送
		try {
			List<String> aliases = new ArrayList<String>();
			aliases.add(targetUserId);  
			boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(msgContent);
			log.info("确认家人关系时，消息推送结果状态："+b);
		} catch (Exception e) {
			log.info("确认家人关系时，消息推送异常："+e);
		}
		
	}
}
