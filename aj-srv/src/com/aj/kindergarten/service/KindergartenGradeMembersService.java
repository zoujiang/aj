/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aam.model.TUser;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("kindergartenGradeMembers")
public class KindergartenGradeMembersService implements PublishService{

	String imgUrl= SystemConfig.getValue("img.http.url");
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
        int gradeId = params.optInt("gradeId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId","gradeId"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		//查询班级信息
		TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, gradeId);
		if(grade == null){
			result.put("succMsg", "幼儿园不存在!");
			returnJSON.put("result", result);
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		List<Map<String, Object>> classMember = new ArrayList<Map<String, Object>>();
		//查询学校园长信息
		String sql = "SELECT id memberId,CASE TYPE  WHEN  1 THEN '园长' WHEN 2 THEN '副园长' WHEN 3 THEN '管理人员'  END memberNickName, CASE WHEN photo is not null THEN  CONCAT('"+imgUrl+"', photo) ELSE '' END memberPhoto , 0 isVip FROM t_kindergarten_teacher t WHERE kindergarten_id = ? AND TYPE IN (1, 2, 3) ORDER BY TYPE ";
		List<Map<String, Object>>  mgrMember =	baseDAO.getGenericBySQL(sql , new Object[]{grade.getKindergartenId()});
		classMember.addAll(mgrMember);
		//查询班级老师信息
		int firstTeacher = grade.getFirstTeacher();
		String teachers = ""+firstTeacher;
		if(grade.getSecondTeacher() != null && grade.getSecondTeacher() > 0){
			teachers += ","+grade.getSecondTeacher();
		}
		if(grade.getNurse() != null && grade.getNurse()  >0){
			teachers += ","+grade.getNurse();
		}
		if(teachers != null && !"".equals(teachers)){
			sql = "SELECT id memberId,CASE TYPE  WHEN  1 THEN '园长' WHEN 2 THEN '副园长' WHEN 3 THEN '管理人员' WHEN 4 THEN '主教' WHEN 5 THEN '副教' WHEN 6 THEN '保育员' WHEN 7 THEN '其他'  END memberNickName, CASE WHEN photo is not null THEN  CONCAT('"+imgUrl+"', photo) ELSE '' END memberPhoto , 0 isVip FROM t_kindergarten_teacher WHERE id IN ("+ teachers +")";
			List<Map<String, Object>>  teacherMember =	baseDAO.getGenericBySQL(sql , null);
			classMember.addAll(teacherMember);
		}
		//查询家长信息
		sql = "select s.id, s.`name` memberNickName, s.photo, u.familyid, u.`IS_VIP` isVip from t_kindergarten_student s left join t_user u on s.parents_tel = u.USERTEL where s.grade_id = ?";
		List<Map<String, Object>> studentAndParentsFamilyList = baseDAO.getGenericBySQL(sql , new Object[]{gradeId});
		for(Map<String, Object> spf : studentAndParentsFamilyList){
			String familyPhoto = spf.get("familyid") == null ? "" : imgUrl + spf.get("photo") +"," ;
			String familyId = spf.get("familyid") ==null? null :spf.get("familyid").toString() ;
			if(familyId != null && !"".equals(familyId)){
				
				TFamilyInfo family = baseDAO.get(TFamilyInfo.class, familyId);
				spf.put("memberNickName", family.getFamilyName());
				List<TUser> parentList = baseDAO.getGenericByHql("from TUser where familyId = ?", familyId);
				for(TUser u : parentList){
					if(u.getPhoto() != null && !"".equals(u.getPhoto())){
						familyPhoto += imgUrl + u.getPhoto() +",";
					}
				}
			}
			spf.put("memberPhoto", familyPhoto);
		}
		
		classMember.addAll(studentAndParentsFamilyList);

		result.put("succMsg", "查询成功");
		result.put("classMember", classMember);
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

	private String checkParam(JSONObject param, String[] keys){

	    for(String key : keys){
	        if(param.get(key) == null || "".equals(param.get(key).toString())){
	            return key;
            }
        }
        return null;
    }

}
