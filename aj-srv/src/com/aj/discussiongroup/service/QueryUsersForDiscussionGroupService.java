package com.aj.discussiongroup.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 查看所有家庭成员
 * */

@Service("queryUsersForDiscussionGroup")
public class QueryUsersForDiscussionGroupService implements PublishService{

	private Logger log = Logger.getLogger(QueryUsersForDiscussionGroupService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String discussionGroupId = params.optString("discussionGroupId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(discussionGroupId == null || "".equals(discussionGroupId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "discussionGroupId为空！");
			return returnJSON.toString();
		}
		
		String hsql = " from TUser where id = ? ";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "用户不存在！");
			return returnJSON.toString();
		}else{
			TUser user = userList.get(0);
			String sql = "SELECT temp.userId, temp.nickName, temp.photo,"+
							" CASE WHEN du.id IS NULL THEN '1' ELSE '2' END isJoin FROM  "+
							" (SELECT "+
							"  u.ID userId,"+
							  "u.NICK_NAME nickName,"+
							  "u.PHOTO photo  "+
							" FROM "+
							 " t_home_info h,"+
							 " t_user u "+
							 " WHERE  "+
							"h.is_valid = 0 and h.create_user_id = "+
							 " CASE "+
							   " WHEN h.is_private = '0' "+
							   " THEN ? "+
							   " ELSE ? "+
							  " END "+
							  " AND "+
							  " CASE "+
							   " WHEN h.invite_area = '1' "+
							   " THEN h.relation_user_id = u.FAMILYID "+
							   " ELSE h.relation_user_id = u.ID "+
							 " END ) temp LEFT JOIN t_discussion_group_user du ON temp.userId = du.user_id "+
							 " and du.discussion_group_id = ? "+
							 " ORDER BY temp.nickName ";
			List<Map<String, Object>> familyUserList =  baseDAO.getGenericBySQL(sql, new Object[]{user.getFamilyId(), userId, discussionGroupId});
			if(familyUserList != null && familyUserList.size() > 0){
				String imgUrl= SystemConfig.getValue("img.http.url");
				for(Map<String, Object> map : familyUserList){
					if( userId.equals(map.get("userId").toString())){
						//把自己从列表剔除
						familyUserList.remove(map);
						continue;
					}
					map.put("photo", (map.get("photo") == null || "".equals(map.get("photo"))) ? "" : imgUrl+ map.get("photo") .toString());
				}
				
			}
			
			result.put("list", familyUserList);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}
