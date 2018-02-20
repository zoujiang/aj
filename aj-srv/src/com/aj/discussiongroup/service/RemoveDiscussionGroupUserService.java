package com.aj.discussiongroup.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.discussiongroup.vo.TDiscussionGroup;
import com.aj.discussiongroup.vo.TDiscussionGroupUser;
import com.aj.easemob.service.EasemobGroupService;
import com.aj.easemob.utils.PropertiesUtils;
import com.aj.familymgr.vo.THomeInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


@Service("removeDiscussionUser")
public class RemoveDiscussionGroupUserService implements PublishService{
	
	private static Logger logger = Logger.getLogger(RemoveDiscussionGroupUserService.class);
	
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
		String homeIds = params.optString("homeIds");  
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
		
		if(homeIds == null || "".equals(homeIds)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "homeIds为空！");
			return returnJSON.toString();
		}
		
		if(discussionId == null || "".equals(discussionId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "discussionId为空！");
			return returnJSON.toString();
		}
		
		//查询讨论组信息
		List<TDiscussionGroup> dgList = baseDAO.getGenericByHql("from TDiscussionGroup where discussionId = ? ", discussionId);
		if(dgList == null || dgList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "讨论组不存在或您没有管理权限！");
			return returnJSON.toString();
		}
		
		JSONObject paramJSON = new JSONObject();
		paramJSON.put("group_id", dgList.get(0).getEasemobGroupId());
		List<String> members = new ArrayList<String>();
		String[] homeIdSplit = homeIds.split(",");
		String homeIdsForDel = "";
		for(String homeId : homeIdSplit){
			homeIdsForDel += "'"+homeId+"',";
			List<THomeInfo> homeList = baseDAO.getGenericByHql("from THomeInfo where id = ?", homeId);
			if(homeList != null && homeList.size()>0){
				THomeInfo home = homeList.get(0);
				if("1".equals(home.getInviteArea())){
					//邀请全家 那么relationUserId是familyId
					String familyId = home.getRelationUserId();
					List<TUser> userList = baseDAO.getGenericByHql("from TUser where familyId = ?", familyId);
					if(userList != null && userList.size() > 0){
						for(int i=0;i<userList.size();i++){
							members.add(userList.get(i).getId().toString());
						}
					}
				}else{
						members.add(home.getRelationUserId());
				}
			}
		}
		paramJSON.put("usernames", members);
		try {
			JSONObject object = easemobGroupService.delGroupMembs(paramJSON);
			logger.info("移除环信讨论组成员返回结果："+object.toString());
			if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
				//删除讨论组用户
				String sql = "DELETE FROM t_discussion_group_user WHERE discussion_group_id = ? AND home_id IN ("+homeIdsForDel.substring(0,homeIdsForDel.lastIndexOf(","))+")";
				baseDAO.execteNativeBulk(sql, discussionId);
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				result.put("succMsg", "移除成功");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			} else {
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				result.put("succMsg", "移除失败");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
		result.put("succMsg", "移除失败");
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
		
	}

}
