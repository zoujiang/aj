/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


@Service("kindergartenStudentList")
public class KindergartenStudentListService implements PublishService{

	String imgUrl= SystemConfig.getValue("img.http.url");
	String resUrl= SystemConfig.getValue("res.http.url");

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String gradeId = params.optString("gradeId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(gradeId == null || "".equals(gradeId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "gradeId为空！");
			return returnJSON.toString();
		}

		String sql = "select s.id studentId, s.`name` memberNickName, s.photo, u.familyid, u.`IS_VIP` isVip from t_kindergarten_student s left join t_user u on s.parents_tel = u.USERTEL where s.grade_id = ?";
		List<Map<String, Object>> studentAndParentsFamilyList = baseDAO.getGenericBySQL(sql , new Object[]{gradeId});
		for(Map<String, Object> spf : studentAndParentsFamilyList){
			spf.put("memberNickName", spf.get("memberNickName")+"之家");
			String familyPhoto = spf.get("familyid") == null ? "" : imgUrl + spf.get("photo") +"," ;
			spf.put("photo", familyPhoto);
			String familyId = spf.get("familyid") ==null? null :spf.get("familyid").toString() ;
			if(familyId != null && !"".equals(familyId)){
				
				List<TUser> parentList = baseDAO.getGenericByHql("from TUser where familyId = ?", familyId);
				for(TUser u : parentList){
					if(u.getPhoto() != null && !"".equals(u.getPhoto())){
						familyPhoto += imgUrl + u.getPhoto() +",";
					}
				}
			}
			spf.put("memberPhoto", familyPhoto);
		}
			
		result.put("succMsg", "查询成功");
		result.put("list", studentAndParentsFamilyList);
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}
