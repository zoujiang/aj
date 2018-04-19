/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenPhoto;
import com.aj.kindergarten.vo.TKindergartenPhotoViewHistory;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


@Service("kindergartenPhotoVisit")
public class KindergartenPhotoVisitService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		Integer photoId = params.optInt("photoId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(photoId == null || photoId == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "photoId为空！");
			return returnJSON.toString();
		}
		TKindergartenPhoto photo = baseDAO.get(TKindergartenPhoto.class, photoId);
		TKindergartenPhotoViewHistory history = new TKindergartenPhotoViewHistory();
		history.setCategory(photo.getCategory());
		history.setGradeId(photo.getGradeId());
		history.setKindergartenId(photo.getKindergartenId());
		history.setPhotoId(photoId);
		history.setCreateTime(System.currentTimeMillis() / 1000);
		history.setUserId(Integer.parseInt(userId));
		baseDAO.save(history);
		
	    result.put("succMsg", "查询成功");
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}
}
