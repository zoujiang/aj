package com.aj.discussiongroup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.aj.discussiongroup.vo.TDiscussionGroup;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


@Service("discussionGroupDetail")
public class DiscussionGroupDetailService implements PublishService{
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String discussionId = params.optString("discussionId");  
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(discussionId == null || "".equals(discussionId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "discussionId为空！");
			return returnJSON.toString();
		}
	
		List<TDiscussionGroup>  groupList = baseDAO.getGenericByHql("from TDiscussionGroup where discussionId = ?  ", discussionId );
		if(groupList != null && groupList.size() > 0){
			TDiscussionGroup group = groupList.get(0);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			result.put("succMsg", "查询成功");
			result.put("discussionId", group.getDiscussionId());
			result.put("discussionName", group.getDiscussionName());
			result.put("discussionDesc", group.getDiscussionDesc());
			result.put("discussionType", group.getDiscussionType());
			result.put("createUserId", group.getCreateUserId());
			result.put("createDate", group.getCreateDate());
			String imgPrefix = SystemConfig.getValue("img.http.url");
			
			String sql = "SELECT home_id FROM t_discussion_group_user gu WHERE discussion_group_id = ? GROUP BY home_id";
			List<Map<String, Object>> homeIdlist = baseDAO.getGenericBySQL(sql, new Object[]{discussionId});
			
			sql = "SELECT u.id userId, u.PHOTO userPhoto, case when r.remark is not null then r.remark else u.NICK_NAME end nickName FROM t_discussion_group_user g , t_user u left join t_remark_info r on u.id = r.relation_id and r.type = 1 and r.create_user_id = ? WHERE g.user_id = u.ID AND g.discussion_group_id = ? and g.home_id = ? ";
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			int totalGroupMember = 0;
			for(Map<String, Object> homeIdMap : homeIdlist){
				Map<String, Object> groupUserInfo = new HashMap<String, Object>();
				String homeId = homeIdMap.get("home_id") ==null ? "" : homeIdMap.get("home_id").toString();
				
				groupUserInfo.put("homeId", homeId);
				List<Map<String, Object>> userList = new ArrayList<Map<String,Object>>();
				if("".equals(homeId)){
					//自己家
					String myHomeSql = "SELECT u.id userId, u.PHOTO userPhoto, case when r.remark is not null then r.remark else u.NICK_NAME end nickName FROM t_discussion_group_user g , t_user u left join t_remark_info r on u.id = r.relation_id and r.type = 1 and r.create_user_id = ? WHERE g.user_id = u.ID AND g.discussion_group_id = ? and (g.home_id is null or g.home_id = '' )";
					userList = baseDAO.getGenericBySQL(myHomeSql, new Object[]{userId, group.getDiscussionId()});
				
					groupUserInfo.put("familyName", "我家");
				}else{
					//查询家庭信息
					String familySql = "SELECT family_name FROM  t_family_info WHERE id = (SELECT familyid FROM t_user WHERE id = (SELECT relation_user_id   FROM t_home_info WHERE id = ?)) OR id = (SELECT relation_user_id   FROM t_home_info WHERE id = ?)";
					List<Map<String, Object>> familyList = baseDAO.getGenericBySQL(familySql, new Object[]{homeId, homeId});
					if(familyList != null && familyList.size()>0){
						groupUserInfo.put("familyName", familyList.get(0).get("family_name"));
					}
					//亲友家
					userList = baseDAO.getGenericBySQL(sql, new Object[]{userId, group.getDiscussionId(),homeId});
				}
				if(userList != null && userList.size() >0){
					for(Map<String, Object> map : userList){
						totalGroupMember += 1;
						if(map.get("userPhoto") != null && !"".equals(map.get("userPhoto"))){
							map.put("userPhoto", imgPrefix + map.get("userPhoto").toString());
						}
					}
				}
				groupUserInfo.put("userList", userList);
				resultList.add(groupUserInfo);
			}
			
			result.put("groupMembers", resultList);
			result.put("userNumber", totalGroupMember);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
			
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "讨论组不存在");
			return returnJSON.toString();
		}
		
	}

}
