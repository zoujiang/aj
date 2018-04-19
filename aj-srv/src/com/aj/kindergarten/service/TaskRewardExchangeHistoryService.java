/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenTaskInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


@Service("taskRewardExchangeHistory")
public class TaskRewardExchangeHistoryService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int pageSize = params.optInt("pageSize") <=0 ? 10 :params.optInt("pageSize");
		int currentPage = params.optInt("currentPage");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}

		String hsql = "from TKindergartenTaskInfo where userId =? order by taskDate desc";
		List<TKindergartenTaskInfo> taskInfoList =baseDAO.getGenericByPosition(hsql, Integer.parseInt( userId), pageSize * currentPage, pageSize);
		result.put("succMsg", "查询成功");
		result.put("list", taskInfoList);
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}
