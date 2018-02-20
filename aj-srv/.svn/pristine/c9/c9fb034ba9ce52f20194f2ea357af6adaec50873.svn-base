package com.aj.familymgr.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 设置家庭资料
 * */

@Service("setFamilyInfo")
public class SetFamilyInfoService implements PublishService{

	private Logger log = Logger.getLogger(SetFamilyInfoService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String familyName = params.optString("familyName");
		String familyLive = params.optString("familyLive");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if((familyName == null || "".equals(familyName)) && (familyLive == null || "".equals(familyLive))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "familyName和familyLive不能同时为空");
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
			TUser user =  userList.get(0);
			if(familyName != null && !"".equals(familyName)){
				
				
				TFamilyInfo info = baseDAO.get(TFamilyInfo.class, user.getFamilyId());
				if(!familyName.endsWith("之家")){
					info.setFamilyName(familyName+"之家");
				}else{
					info.setFamilyName(familyName);
				}
				baseDAO.update(info);
			}
			if(familyLive != null && !"".equals(familyLive)){
				
				TFamilyLive live = new TFamilyLive();
				live.setId(UUIDUtil.uuid());
				live.setContent(familyLive);
				live.setCreateDate(DateUtils.currentDate());
				live.setFamilyId(user.getFamilyId());
				baseDAO.save(live);
				
			}
			
			result.put("succMsg", "设置成功");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
	}
			
}
