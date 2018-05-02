/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


@Service("kindergartenHonorQuery")
public class KindergartenHonorQueryService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
        int albumId = params.optInt("albumId");
        int pageSize = params.optInt("pageSize");
        int currentPage = params.optInt("currentPage");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId", "albumId", "pageSize", "currentPage"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		String imgPrefix =  SystemConfig.getValue("img.http.url");
	//	TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));
        String sql = "select h.id honorId, case when (h.photo_url is not null and h.photo_url != '') then concat('"+ imgPrefix+"',h.photo_url) else '' end honorUrl, h.create_time createTime,h.name honorName, case when u.id is null then h.create_user else u.nick_name end createUser  from t_kindergarten_honor h left join t_user u on h.create_user = u.id  where h.album_id = ? order by h.create_time desc limit ?,?";
        List<Map<String, Object>> data = baseDAO.getGenericBySQL(sql, new Object[]{albumId, currentPage * pageSize, pageSize });

		result.put("succMsg", "查询成功");
		result.put("data", data);
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
