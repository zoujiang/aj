package com.aj.discussiongroup.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.discussiongroup.vo.TDiscussionGroup;
import com.aj.discussiongroup.vo.TDiscussionGroupUser;
import com.aj.easemob.service.EasemobGroupService;
import com.aj.familymgr.vo.THomeInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


@Service("inviteDiscussionUser")
public class InviteDiscussionGroupUserService implements PublishService{
	
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
		String userIds = params.optString("userIds");  
		String type = params.optString("type");  
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
		if(type == null || "".equals(type)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "type为空！");
			return returnJSON.toString();
		}
		if("1".equals(type) ){
			if(homeIds == null || "".equals(homeIds)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "homeIds为空！");
				return returnJSON.toString();
			}
		}
		if("2".equals(type) ){
			if(userIds == null || "".equals(userIds)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "userIds为空！");
				return returnJSON.toString();
			}
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
		List<TDiscussionGroupUser> tguList = baseDAO.getGenericByHql("from TDiscussionGroupUser where discussionGroupId = ?", discussionId);
		if("1".equals(type)){
			String[] homeIdSplit = homeIds.split(",");
			
			for(String homeId : homeIdSplit){
				
				List<THomeInfo> homeList = baseDAO.getGenericByHql("from THomeInfo where id = ?", homeId);
				if(homeList != null && homeList.size()>0){
					THomeInfo home = homeList.get(0);
					if("1".equals(home.getInviteArea())){
						//邀请全家 那么relationUserId是familyId
						String familyId = home.getRelationUserId();
						List<TUser> userList = baseDAO.getGenericByHql("from TUser where familyId = ?", familyId);
						if(userList != null && userList.size() > 0){
							for(int i=0;i<userList.size();i++){
								boolean b = true;
								for(TDiscussionGroupUser groupUser : tguList){
									if(userList.get(i).getId().toString().equals(groupUser.getUserId())){
										b = false;
									}
								}
								if(b){
									//添加环信成员
									members.add(userList.get(i).getId().toString());
									
									TDiscussionGroupUser dgu = new TDiscussionGroupUser();
									dgu.setId(UUIDUtil.uuid());
									dgu.setUserId(userList.get(i).getId().toString());
									dgu.setDiscussionGroupId(discussionId);
									dgu.setHomeId(homeId);
									baseDAO.save(dgu);
								}
							}
							
						}
					}else{
						
						boolean b = true;
						for(TDiscussionGroupUser groupUser : tguList){
							if(home.getRelationUserId().equals(groupUser.getUserId())){
								b = false;
							}
						}
						if(b){
							//添加环信成员
							members.add(home.getRelationUserId());
							
							TDiscussionGroupUser dgu = new TDiscussionGroupUser();
							dgu.setId(UUIDUtil.uuid());
							dgu.setUserId(home.getRelationUserId());
							dgu.setDiscussionGroupId(discussionId);
							dgu.setHomeId(homeId);
							baseDAO.save(dgu);
						}
					}
				}
			}
		}else if("2".equals(type)){
			String[] userIdSplit = userIds.split(",");
			
			for(String user : userIdSplit){
				
				//添加环信成员
				members.add(user);
				
				TDiscussionGroupUser dgu = new TDiscussionGroupUser();
				dgu.setId(UUIDUtil.uuid());
				dgu.setUserId(user);
				dgu.setDiscussionGroupId(discussionId);
				baseDAO.save(dgu);
				
			}
		}
		paramJSON.put("usernames", members);
		try {
			easemobGroupService.addGroupMembs(paramJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		result.put("succMsg", "邀请成功");
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
		
	}

}
