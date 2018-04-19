package com.aj.sys.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.sys.vo.TVipPackage;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;

/**
 * 查询VIP套餐
 * */

@Service("queryVipPackage")
public class QueryVipPackageService implements PublishService{

	private Logger log = Logger.getLogger(QueryVipPackageService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		
		
		try {
				
			List<TVipPackage> resultList = baseDAO.getGenericByHql("from TVipPackage order by sortIndex", null);
			
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			result.put("succMsg", "查询成功");
			result.put("resultList", resultList);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			
		} catch (Exception e) {
			
			log.info("查询失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}
