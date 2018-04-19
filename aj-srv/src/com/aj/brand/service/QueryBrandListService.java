package com.aj.brand.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


/**
 * 品牌列表
 * */

@Service("queryBrandList")
public class QueryBrandListService implements PublishService{

	private Logger log = Logger.getLogger(QueryBrandListService.class);
	String imgUrl= SystemConfig.getValue("img.http.url");
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String type = params.optString("type"); //1影楼 2幼儿园 3券商
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(type == null || "".equals(type)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "type为空！");
			return returnJSON.toString();
		}
		if(pageSize == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize为空！");
			return returnJSON.toString();
		}
		if(currentPage == null || "".equals(currentPage)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage为空！");
			return returnJSON.toString();
		}
		
		String sql = "select id ,brand_name brandName, case when (brand_icon is not null and brand_icon != '') then concat('"+imgUrl+"', brand_icon) else ''  end brandIcon   from t_shop_brand  where status = 0 and type = ? order by sort_index limit ?,?";
		List<Map<String, Object>> brandList = baseDAO.getGenericBySQL(sql, new Object[]{type, Integer.parseInt(currentPage) * Integer.parseInt(pageSize) , Integer.parseInt(pageSize) });
			result.put("succMsg", "查询成功");
			result.put("list", brandList);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		
	}

}
