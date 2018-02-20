package com.aj.praylettermgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.babymgr.vo.TBabyInfo;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.aj.praylettermgr.vo.TPrayletter;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 爱的传承删除祈愿信
 * */

@Service("prayLetterDel")
public class DelPrayLetterService implements PublishService{

	private Logger log = Logger.getLogger(DelPrayLetterService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String prayLetterId = params.optString("prayLetterId");  
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(prayLetterId == null || "".equals(prayLetterId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "prayLetterId为空！");
			return returnJSON.toString();
		}
		String letterHsql = "from TPrayletter where id = ? ";
		List<TPrayletter> letterList = baseDAO.getGenericByHql(letterHsql, prayLetterId);
		if(letterList  == null || letterList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "删除对象不存在");
			return returnJSON.toString();
		}
		List<TUser> userList = baseDAO.getGenericByHql("from TUser where id = ?", Integer.parseInt(userId));
		
		String hsql = " from TBabyInfo where id = ? ";
		List<TBabyInfo> babyList = baseDAO.getGenericByHql(hsql, letterList.get(0).getBabyUserId());
		if(babyList == null || babyList.size() == 0){
			hsql = " from TChildrenInfo where id = ? ";
			List<TChildrenInfo> childList = baseDAO.getGenericByHql(hsql, letterList.get(0).getBabyUserId());
			if(childList == null || childList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "孩子信息不存在");
				return returnJSON.toString();
			}else{
				if(!childList.get(0).getFamilyId().equals(userList.get(0).getFamilyId())){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "你无权删除该条祈愿信");
					return returnJSON.toString();
				}
				
			}
		}else{
			if(!babyList.get(0).getFamilyId().equals(userList.get(0).getFamilyId())){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "你无权删除该条祈愿信");
				return returnJSON.toString();
			}
		}
		
		baseDAO.delete(TPrayletter.class, prayLetterId);
		
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		result.put("succMsg", "操作成功！");	
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}
