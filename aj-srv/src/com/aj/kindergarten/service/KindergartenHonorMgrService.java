/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aj.kindergarten.vo.TKindergartenHonor;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("kindergartenHonorMgr")
public class KindergartenHonorMgrService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String honorName = params.optString("honorName");
		String honorUrl = params.optString("honorUrl");
		int type = params.optInt("type");
		int ownerId = params.optInt("ownerId");
        int oper = params.optInt("oper");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId", "honorUrl","type", "ownerId", "oper"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}

	//	TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));
        if(oper == 1){

            TKindergartenHonor honor = new TKindergartenHonor();
            honor.setCategory(1);
            honor.setCreateTime(DateUtils.currentDate());
            honor.setCreateUser(userId);
            honor.setName(honorName);
            honor.setType(type);
            honor.setPhotoUrl(honorUrl);
            honor.setOwnerId(ownerId+"");
            honor.setCommentNum(0);
            honor.setDigNum(0);
            baseDAO.save(honor);
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
