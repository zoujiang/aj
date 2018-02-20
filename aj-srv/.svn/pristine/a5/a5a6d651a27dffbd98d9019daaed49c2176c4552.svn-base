package com.aj.discussiongroup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.babymgr.vo.TBabyInfo;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.aj.discussiongroup.vo.TDiscussionGroup;
import com.aj.discussiongroup.vo.TDiscussionGroupUser;
import com.aj.easemob.service.EasemobGroupService;
import com.aj.easemob.utils.PropertiesUtils;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


@Service("discussionGroupMgr")
public class DiscussionGroupManageService implements PublishService{
	
	private Logger logger = Logger.getLogger(DiscussionGroupManageService.class);
	
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
		String oper = params.optString("oper");  
		String discussionId = params.optString("discussionId");  
		String discussionType = params.optString("discussionType");  
		String discussionName = params.optString("discussionName");  
		String discussionDesc = params.optString("discussionDesc");  
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}
		List<TUser> userList = baseDAO.getGenericByHql("from TUser where id = ? ", Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "用户不存在！");
			return returnJSON.toString();
		}
		TUser user = userList.get(0);
		if("1".equals(oper)){
			if(discussionType == null || "".equals(discussionType) ){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "discussionType为空！");
				return returnJSON.toString();
			}
			if(discussionName == null || "".equals(discussionName) ){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "discussionName为空！");
				return returnJSON.toString();
			}
			
			
			TDiscussionGroup group = new TDiscussionGroup();
			group.setDiscussionId(UUIDUtil.uuid());
			group.setDiscussionName(discussionName);
			group.setDiscussionType(discussionType);
			group.setDiscussionDesc(discussionDesc);
			group.setCreateUserId(userId);
			group.setCreateDate(DateUtils.currentDate());
			String pk = baseDAO.save(group);
			
			//创建环信讨论组
			JSONObject paramJSON = new JSONObject();
			paramJSON.put("groupname", discussionName);
			paramJSON.put("desc",discussionDesc);
			paramJSON.put("public",true); //是否公开
			paramJSON.put("approval",false); //是否需要验证
			paramJSON.put("owner",userId);
			List<String> members = new ArrayList<String>();
			
			
			//把自己家添加到讨论组里面
			String hsql = "from TUser where familyId = ?";
			List<TUser> users = baseDAO.getGenericByHql(hsql, userList.get(0).getFamilyId());
			for(int i=0; i< users.size(); i++){
				if(!userId.equals(users.get(i).getId().toString()) ){
					//环信聊天组添加成员， 但是不包含自己
					members.add(users.get(i).getId().toString());
				}
				
				TDiscussionGroupUser dgu = new TDiscussionGroupUser();
				dgu.setId(UUIDUtil.uuid());
				dgu.setUserId(users.get(i).getId().toString());
				dgu.setDiscussionGroupId(pk);
				baseDAO.save(dgu);
			}
			//把胎儿添加到讨论组
			/*List<TBabyInfo> babys = baseDAO.getGenericByHql("from TBabyInfo where familyId = ?", userList.get(0).getFamilyId());
			if(babys != null && babys.size()>0){
				for(int i=0;i<babys.size();i++){
					
				//	members.add(babys.get(i).getId().toString());
					
					TDiscussionGroupUser dgu = new TDiscussionGroupUser();
					dgu.setId(UUIDUtil.uuid());
					dgu.setUserId(babys.get(i).getId().toString());
					dgu.setDiscussionGroupId(pk);
					baseDAO.save(dgu);
				}
			}*/
			//把孩子添加到讨论组
			/*List<TChildrenInfo> children = baseDAO.getGenericByHql("from TChildrenInfo where familyId = ? and isPrivate = '0'", userList.get(0).getFamilyId());
			if(children != null && children.size()>0){
				for(int i=0;i<children.size();i++){
				//	members.add(children.get(i).getId().toString());
					
					TDiscussionGroupUser dgu = new TDiscussionGroupUser();
					dgu.setId(UUIDUtil.uuid());
					dgu.setUserId(children.get(i).getId().toString());
					dgu.setDiscussionGroupId(pk);
					baseDAO.save(dgu);
				}
			}*/
			if(members.size() > 0){
				
				paramJSON.put("members",members);
			}
			try {
				JSONObject object = easemobGroupService.createGroup(paramJSON);
				logger.info("添加环信讨论组返回："+ object);
				if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
					JSONObject data = object.getJSONObject("data");
					String groupId = data.optString("groupid");
					//更新到讨论组信息中
					String updateHsql = "update TDiscussionGroup set easemobGroupId = ? where discussionId = ?";
					baseDAO.update(updateHsql, groupId, pk);
					
					
				} else {
					if(object.optString("error") != null && "duplicate_unique_property_exists".equals(object.optString("error"))){
						logger.info(userId+"环信讨论组已经存在。。。。。");
					} else{
						logger.info(userId+"环信讨论组失败。。。。。"+object.optString("error"));
					}
					//删除讨论组用户
					String delHsql = "delete from TDiscussionGroupUser where  discussionGroupId = '"+pk+"'";
					baseDAO.deleteByHsql(delHsql);
					//删除讨论组
					delHsql = "delete from TDiscussionGroup where  discussionId = '"+pk+"'";
					baseDAO.deleteByHsql(delHsql);
					
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					result.put("succMsg", "");
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "创建环信讨论组失败");
					return returnJSON.toString();
				}
				
			} catch (Exception e) {
				logger.info("创建环信讨论组失败："+e);
				//删除讨论组用户
				String delHsql = "delete from TDiscussionGroupUser where  discussionGroupId = '"+pk+"'";
				baseDAO.deleteByHsql(delHsql);
				//删除讨论组
				delHsql = "delete from TDiscussionGroup where  discussionId = '"+pk+"'";
				baseDAO.deleteByHsql(delHsql);
				
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				result.put("succMsg", "");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "创建环信讨论组失败");
				return returnJSON.toString();
			}
			
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			result.put("succMsg", "创建成功");
			result.put("discussionId", pk);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}else if("2".equals(oper)){
			if(discussionId == null || "".equals(discussionId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "discussionId为空！");
				return returnJSON.toString();
			}
			List<TDiscussionGroup>  groupList = baseDAO.getGenericByHql("from TDiscussionGroup where discussionId = ? ", discussionId);
			if(groupList != null && groupList.size() > 0){
				TDiscussionGroup group = groupList.get(0);
				if(discussionType != null && !"".equals(discussionType)){
					group.setDiscussionType(discussionType);
				}
				if(discussionName != null && !"".equals(discussionName)){
					group.setDiscussionName(discussionName);
				}
				if(discussionDesc != null && !"".equals(discussionDesc)){
					group.setDiscussionDesc(discussionDesc);
				}
				baseDAO.update(group);
				
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				result.put("succMsg", "更新成功");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
				
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "讨论组不存在");
				return returnJSON.toString();
			}
			
		}else if("3".equals(oper)){
			if(discussionId == null || "".equals(discussionId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "discussionId为空！");
				return returnJSON.toString();
			}
			List<TDiscussionGroup>  groupList = baseDAO.getGenericByHql("from TDiscussionGroup where discussionId = ?", discussionId);
			if(groupList != null && groupList.size() > 0){
				TDiscussionGroup group = groupList.get(0);
				
				//删除环信讨论组
				JSONObject jo = new JSONObject();
				jo.put("group_id", group.getEasemobGroupId());
				try {
					JSONObject object = easemobGroupService.delGroup(jo);
					if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
						//删除群内所有成员
						baseDAO.execteNativeBulk("DELETE FROM t_discussion_group_user WHERE discussion_group_id = ?", group.getDiscussionId());
						//删除讨论组
						baseDAO.deleteObject(group);
						
						returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
						result.put("succMsg", "删除成功");
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "");
						return returnJSON.toString();
					} else {
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						result.put("succMsg", "删除失败");
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "");
						return returnJSON.toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					result.put("succMsg", "删除失败");
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					return returnJSON.toString();
				}
				
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "讨论组不存在");
				return returnJSON.toString();
			}
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "无效的操作类型");
			return returnJSON.toString();
		}
		
	}

}
