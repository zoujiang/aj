/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aj.kindergarten.vo.TKindergartenGrade;
import com.aj.kindergarten.vo.TKindergartenPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("kindergartenGradeEdit")
public class KindergartenGradeEditService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int gradeId = params.optInt("gradeId"); 
		String declaration = params.optString("declaration");
		String logo = params.optString("logo");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId", "gradeId"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		if(declaration == null || logo == null ){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "declaration和logo不能都为空");
			return returnJSON.toString();
		}

		TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, gradeId);
		if(grade == null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "班级不存在！");
			return returnJSON.toString();
		}
		if(declaration != null){
			grade.setDeclaration(declaration);
		}
		if(logo != null){
			String imgUrl= SystemConfig.getValue("img.http.url");
			logo = logo.replaceAll(imgUrl, "");
			grade.setLogo(logo);
		}
		baseDAO.update(grade);
		result.put("succMsg", "更新成功");
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
