package com.aj.collectionmgr.service;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 删除我的收藏
 * */

@Service("collectionDelete")
public class DelCollectionService implements PublishService{

	private Logger log = Logger.getLogger(DelCollectionService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String collectionIds = params.optString("collectionIds");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(collectionIds == null || "".equals(collectionIds)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "collectionIds为空！");
			return returnJSON.toString();
		}
		String ids = "";
		try {
			for(String id : collectionIds.split(",")){
				ids += id.trim()+",";
			}
			
			String hsql = "delete from TCollection where userId = "+userId+" and id in ("+ ids.substring(0, ids.length()-1)+")";
			baseDAO.deleteByHsql(hsql);
			result.put("succMsg", "删除成功！");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		} catch (Exception e) {
			log.info("删除收藏异常："+e.getMessage());
			e.printStackTrace();
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "删除失败，服务器内部错误！");
			return returnJSON.toString();
		}
		
	}

}
