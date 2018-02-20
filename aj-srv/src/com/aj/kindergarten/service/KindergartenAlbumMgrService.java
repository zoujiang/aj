/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aam.model.TUser;
import com.aj.kindergarten.vo.TKindergartenAlbumVisible;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("kindergartenAlbumMgr")
public class KindergartenAlbumMgrService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int albumId = params.optInt("albumId");
        int visibleProperty = params.optInt("visibleProperty");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId", "albumId", "visibleProperty"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}

		TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));
        List<TKindergartenAlbumVisible> vbs = baseDAO.getGenericByHql("from TKindergartenAlbumVisible where albumId = ? and familyId = ?", albumId, user.getFamilyId());
		if(vbs.size() > 0){
            TKindergartenAlbumVisible visible = vbs.get(0);
            visible.setVisibleProperty(visibleProperty);
            baseDAO.update(visible);
        }else{
            TKindergartenAlbumVisible visible = new TKindergartenAlbumVisible();
            visible.setAlbumId(albumId);
            visible.setFamilyId(user.getFamilyId());
            visible.setVisibleProperty(visibleProperty);
            baseDAO.save(visible);
        }

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
