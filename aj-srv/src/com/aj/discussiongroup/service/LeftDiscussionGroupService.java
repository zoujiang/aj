package com.aj.discussiongroup.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.aj.discussiongroup.vo.TDiscussionGroup;
import com.aj.easemob.service.EasemobGroupService;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


@Service("leftDiscussionGroup")
public class LeftDiscussionGroupService implements PublishService{
	
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
		//查询讨论组信息
		List<TDiscussionGroup> dgList = baseDAO.getGenericByHql("from TDiscussionGroup where discussionId = ? ", discussionId);
		if(dgList == null || dgList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "讨论组不存在！");
			return returnJSON.toString();
		}
		JSONObject paramJSON = new JSONObject();
		paramJSON.put("group_id", dgList.get(0).getEasemobGroupId());
		List<String> members = new ArrayList<String>();
		members.add(userId);
		paramJSON.put("usernames", members);
		try {
			easemobGroupService.delGroupMembs(paramJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "DELETE FROM t_discussion_group_user WHERE user_id=? AND discussion_group_id = ?";
		baseDAO.execteNativeBulk(sql, userId,discussionId);
		
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		result.put("succMsg", "退出成功");
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}
