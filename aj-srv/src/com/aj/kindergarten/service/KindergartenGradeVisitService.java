/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenGradeVisitInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;

import net.sf.json.JSONObject;


@Service("kindergartenGradeVisit")
public class KindergartenGradeVisitService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		Integer gradeId = params.optInt("gradeId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(gradeId == null || gradeId == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "gradeId为空！");
			return returnJSON.toString();
		}
		
		TKindergartenGradeVisitInfo visit = new TKindergartenGradeVisitInfo();
		visit.setCreateTime(DateUtils.currentDate());
		visit.setGradeId(gradeId);
		visit.setUserId(Integer.parseInt(userId));
		baseDAO.save(visit);
		
	    result.put("succMsg", "查询成功");
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}
}
