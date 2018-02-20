package com.aj.collectionmgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.collectionmgr.vo.TCollection;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 查询我的收藏
 * */

@Service("collectionsQuery")
public class QueryCollectionService implements PublishService{

	private Logger log = Logger.getLogger(QueryCollectionService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String type = params.optString("type");
		String target = params.optString("target");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(type == null || "".equals(type)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "type为空！");
			return returnJSON.toString();
		}
		if(target == null || "".equals(target)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "target为空！");
			return returnJSON.toString();
		}
		try {
			
			String nativeSQL = "SELECT DATE_FORMAT(createdate ,'%Y-%c-%d') cdt FROM t_collection WHERE TYPE = ? and TARGET = ? GROUP BY  DATE_FORMAT(createdate ,'%Y-%c-%d') ORDER BY DATE_FORMAT(createdate ,'%Y-%c-%d') DESC";
			List<String> dateList = baseDAO.query(nativeSQL, type, target);
			JSONArray ja = new JSONArray();
			if(dateList != null && dateList.size() >0){
				for(int i=0;i<dateList.size();i++){
					JSONObject dataJson = new JSONObject();		
					String key = dateList.get(i).toString();
					String hsql = "from TCollection  WHERE DATE_FORMAT(createDate ,'%Y-%c-%d') = ? AND type = ? and target = ? ORDER BY CREATEDATE";
				//	nativeSQL = "SELECT  c.ID collectionId, c.PHOTO_URL photoUrl,c.TEXT_URL textUrl  FROM  t_collection c WHERE DATE_FORMAT(c.createDate ,'%Y-%c-%d') = ? AND c.type = ? and target = ? ORDER BY c.CREATEDATE";
				//	List<Map<String, Object>> tempList = baseDAO.query(nativeSQL, key, type, target);
					List<TCollection> tempList  = baseDAO.getGenericByHql(hsql, key, type, target);
					dataJson.put("createDate", key);
					dataJson.put("data", tempList);
					ja.add(dataJson);
				}
			}
			
			result.put("succMsg", "查询成功！");
			result.put("colletionsList",ja);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		} catch (Exception e) {
			log.info("查询收藏异常："+e.getMessage());
			e.printStackTrace();
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "查询失败，服务器内部错误！");
			return returnJSON.toString();
		}
		
	}

}
