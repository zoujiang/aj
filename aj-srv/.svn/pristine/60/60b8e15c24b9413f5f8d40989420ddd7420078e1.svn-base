package com.aj.familymgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familymgr.vo.TFamilyLive;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 查询家庭列表
 * */

@Service("queryHomeInfo")
public class QueryHomeInfoService implements PublishService{

	private Logger log = Logger.getLogger(QueryHomeInfoService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String callName = params.optString("callName");  
		
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
			//查询自己家庭
			String sql = "SELECT ''  homeId, '1' inviteArea, '' inviteId, '自己'  callName, ''  easemobGroupId, f.family_name  familyName, f.id familyId  FROM t_family_info f WHERE f.id = ?";
			List<Map<String, Object>> myHomeList = baseDAO.getGenericBySQL(sql, new Object[]{user.getFamilyId()});
			//查询所有家庭
			sql = "SELECT DISTINCT h.id homeId,h.invite_area inviteArea, h.invite_id inviteId, h.call_name callName, h.easemobGroupId easemobGroupId, "+
				"( "+
				" SELECT f.id FROM t_family_info f "+
					" WHERE  f.id = CASE WHEN  h.invite_area = '1' THEN  h.relation_user_id ELSE (SELECT u.FAMILYID FROM t_user u WHERE u.id = h.relation_user_id) END "+
				 ") familyId, ( "+
				" SELECT case when r.remark is not null then r.remark else f.family_name end FROM t_family_info f left join t_remark_info r on f.id = r.relation_id and r.type =2 and r.create_user_id = ?  "+
					" WHERE  f.id = CASE WHEN  h.invite_area = '1' THEN  h.relation_user_id ELSE (SELECT u.FAMILYID FROM t_user u WHERE u.id = h.relation_user_id) END "+
				 ") familyName FROM t_home_info h ,t_invite_info i WHERE h.is_valid = 0 and h.invite_id = i.id  AND ( h.create_user_id = ? OR h.create_user_id = ?) and h.relation_user_id != ? ";
			if(callName != null && !"".equals(callName)){
				
				sql += "and h.call_name like '%"+callName+"%'";
			}
			sql += "order by h.id";
			List<Map<String, Object>> homeList = baseDAO.getGenericBySQL(sql, new Object[]{userId, userId, user.getFamilyId(), user.getFamilyId()});
			myHomeList.addAll(homeList);
			if(myHomeList !=  null && myHomeList.size() > 0){
				String imgPrefix = SystemConfig.getValue("img.http.url");
				for(int i =0; i< myHomeList.size(); i++){
					
					Map<String, Object> map = myHomeList.get(i);
					String familyId = map.get("familyId").toString();
					//查询家庭动态
					List<TFamilyLive> fiList = baseDAO.getGenericByHql("from TFamilyLive where familyId = ? order by createDate desc", familyId);
					if(fiList != null && fiList.size() > 0){
						
						map.put("familySign", fiList.get(0).getContent());
					}else{
						
						map.put("familySign", "");
					}
					if("1".equals(map.get("inviteArea").toString())){
						//邀请全家的时候才查询所有
						sql = "SELECT CASE WHEN u.PHOTO IS NULL OR u.PHOTO = '' THEN '' ELSE  CONCAT('"+imgPrefix+"',u.PHOTO) END photoUrl, u.NICK_NAME nickName, u.id userId FROM t_user u WHERE u.FAMILYID = ? "+
								"	UNION  ALL "+
							//	"	SELECT CASE WHEN c.photo_url IS NULL OR c.photo_url = '' THEN '' ELSE  CONCAT('"+imgPrefix+"',c.photo_url) END photoUrl, c.nickname nickName, c.id userId FROM t_children_info c WHERE c.family_id = ? AND  (c.is_private = '0' OR ( c.is_private = '1' AND  c.create_user_id = ? )) "+
								"	SELECT CASE WHEN c.photo_url IS NULL OR c.photo_url = '' THEN '' ELSE  CONCAT('"+imgPrefix+"',c.photo_url) END photoUrl, c.nickname nickName, c.id userId FROM t_children_info c WHERE c.family_id like  CONCAT('%',?,'%') AND  (c.is_private = '0' OR ( c.is_private = '1' AND  c.create_user_id = ? )) "+
								"	UNION  ALL "+
							//	"	SELECT CASE WHEN b.photo_url IS NULL OR b.photo_url = '' THEN '' ELSE  CONCAT('"+imgPrefix+"',b.photo_url) END photoUrl, b.nickname nickName, b.id userId FROM t_baby_info b WHERE b.family_id = ? ";
								"	SELECT CASE WHEN b.photo_url IS NULL OR b.photo_url = '' THEN '' ELSE  CONCAT('"+imgPrefix+"',b.photo_url) END photoUrl, b.nickname nickName, b.id userId FROM t_baby_info b WHERE b.family_id like  CONCAT('%',?,'%') ";
						map.put("photoList",  baseDAO.getGenericBySQL(sql, new Object[]{familyId, familyId,userId, familyId}));
					}else{
						
						sql = "SELECT CASE WHEN u.PHOTO IS NULL OR u.PHOTO = '' THEN '' ELSE  CONCAT('"+imgPrefix+"',u.PHOTO) END photoUrl, u.NICK_NAME nickName, u.id userId FROM t_user u WHERE u.id = ( SELECT relation_user_id FROM t_home_info WHERE id = ?) ";
						map.put("photoList",  baseDAO.getGenericBySQL(sql, new Object[]{map.get("homeId")}));
					}
				}
			}
			
			result.put("succMsg", "查询成功");	
			result.put("homeList", myHomeList);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
				
		}
		
	}
	
/*	
	private void sendSysMsgToRelationUser(Integer targetUserId, String relationId, int msgType,
			String msgTitle, String msgContent) {
		
		TSysMsgAlert alert = new TSysMsgAlert();
		alert.setCreateDate(DateUtils.currentDate());
		alert.setHadRead(0);
		alert.setMsgContent(msgContent);
		alert.setMsgTitle(msgTitle);
		alert.setMsgType(msgType);
		alert.setRelationId(relationId);
		alert.setReciveUserId(targetUserId);
		baseDAO.save(alert);
		
	}*/

}
