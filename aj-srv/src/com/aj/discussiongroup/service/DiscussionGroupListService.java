package com.aj.discussiongroup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.aj.discussiongroup.vo.TDiscussionGroup;
import com.aj.familymgr.vo.TInviteInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


@Service("discussionGroupList")
public class DiscussionGroupListService implements PublishService{
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String discussionNmae = params.optString("discussionNmae");  
		String pageSize = params.optString("pageSize");  
		String currentPage = params.optString("currentPage");  
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(pageSize == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize为空！");
			return returnJSON.toString();
		}
		int pageSizeInt = 10;
		try {
			pageSizeInt = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize格式不正确");
			return returnJSON.toString();
		}
		int currentPageInt = 0;
		try {
			currentPageInt = Integer.parseInt(currentPage);
		} catch (NumberFormatException e) {
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage格式不正确");
			return returnJSON.toString();
		}
		int begin = currentPageInt * pageSizeInt;
		List<Object> p = new ArrayList<Object>();
	    String sql = "SELECT "+
						  "g.discussionId, g.discussionName, g.discussionType, g.discussionDesc, g.easemob_group_id easemobGroupId, DATE_FORMAT(STR_TO_DATE(g.create_date, '%Y-%m-%d'),'%y/%c/%e') createDate, g.create_user_id createUserId, f.family_name familyName "+
					" FROM  "+
						 " t_discussion_group_user gu, "+
						 " t_discussion_group g," +
						 " t_user u, "+
						 " t_family_info f "+
					" WHERE gu.discussion_group_id = g.discussionId  "+
						" AND g.create_user_id = u.ID "+
						" AND u.FAMILYID = f.id "+
						" AND gu.user_id = ?  ";
	    			p.add(userId);
	    			if(discussionNmae != null && !"".equals(discussionNmae)){
	    				sql += " and g.discussionName like ?";
	    				p.add("%"+discussionNmae+"%");
	    			}
					sql +=" ORDER BY g.create_date DESC LIMIT "+begin+", "+ pageSizeInt;
		List<Map<String, Object>>  groupList = baseDAO.getGenericBySQL(sql, p.toArray() );
		if(groupList != null && groupList.size() > 0){
			String imgUrl= SystemConfig.getValue("img.http.url");
			for(int i=0; i< groupList.size(); i++){
				Map<String, Object> map = groupList.get(i);
				sql = "SELECT u.id userId, CONCAT('"+imgUrl+"',u.PHOTO) userPhoto, u.nick_name nickName FROM t_discussion_group_user g , t_user u WHERE g.user_id = u.ID AND g.discussion_group_id = ?";
				List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new Object[]{map.get("discussionId")});
				map.put("userList", list);
			}
			
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			result.put("succMsg", "查询成功");
			result.put("list", groupList);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
			
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "暂无讨论组");
			return returnJSON.toString();
		}
		
	}

}
