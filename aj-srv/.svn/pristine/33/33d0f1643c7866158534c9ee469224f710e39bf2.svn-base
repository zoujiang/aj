/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aam.model.TUser;
import com.aj.kindergarten.vo.TKindergartenAlbumVisible;
import com.aj.kindergarten.vo.TKindergartenPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("kindergartenPhotoMgr")
public class KindergartenPhotoMgrService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String photoName = params.optString("photoName");
		String photoUrl = params.optString("photoUrl");
		String ownerId = params.optString("ownerId");
		String videoUrl = params.optString("videoUrl");
		int type = params.optInt("type");  //1:班级 2:个人
        int category = params.optInt("category");  //1:照片 2:视频
        int oper = params.optInt("oper");
        int albumId = params.optInt("albumId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId", "photoUrl", "ownerId","type" , "category", "oper", "albumId"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}

		//TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));
        if(oper == 1){

            TKindergartenPhoto photo = new TKindergartenPhoto();
            photo.setAlbumId(albumId);
            photo.setCategory(category);
            photo.setCommentNum(0);
            photo.setCreateTime(DateUtils.currentDate());
            photo.setCreateUser(userId);
            photo.setDigNum(0);
            photo.setName(photoName);
            photo.setOwnerId(ownerId);
            photo.setPhotoUrl(photoUrl);
            photo.setType(type);
            photo.setVideoUrl(videoUrl);
            baseDAO.save(photo);
        }

		result.put("succMsg", "新增成功");
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
