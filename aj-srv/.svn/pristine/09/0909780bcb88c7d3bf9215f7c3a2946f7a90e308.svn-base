package com.aj.familymgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.easemob.service.EasemobGroupService;
import com.aj.familymgr.vo.THomeInfo;
import com.aj.familymgr.vo.TInviteInfo;
import com.aj.task.service.TaskService;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 删除亲友家庭
 * */

@Service("deleteFamily")
public class DeleteFamilyService implements PublishService{

	private Logger log = Logger.getLogger(DeleteFamilyService.class);
	@Resource
	private EasemobGroupService easemobGroupService;
	@Resource
	private GenericDAO baseDAO;
	@Resource
	private TaskService taskService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String homeId = params.optString("homeId");  
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(homeId == null || "".equals(homeId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "homeId为空！");
			return returnJSON.toString();
		}
		
		
		TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
		THomeInfo home = baseDAO.get(THomeInfo.class, homeId);
		//查询反向家庭记录
		List<THomeInfo> home2List = baseDAO.getGenericByHql("from THomeInfo where creatrUserId = ? and relationUserId = ?", home.getRelationUserId(), home.getCreatrUserId());
		if(home2List != null && home2List.size() > 0){
			THomeInfo home2 = home2List.get(0);
			home2.setIsValid(1);
			baseDAO.update(home2);
		//	baseDAO.deleteObject(home2);
		}
		//删除邀请记录，暂时先不删了， 删了会导致朋友在删除两个离婚的夫妻其中一个时 导致另外一个在家庭圈列表查询不出来
	//	baseDAO.delete(TInviteInfo.class, home.getInviteId());
		//删除环信讨论组
		JSONObject object = new JSONObject();
		object.put("group_id", home.getEasemobGroupId());
		try {
			easemobGroupService.delGroup(object);
		} catch (Exception e) {
		}
		home.setIsValid(1);
		baseDAO.update(home);
		//baseDAO.deleteObject(home);
	
		result.put("succMsg", "删除成功");	
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();				
		
	}

}
