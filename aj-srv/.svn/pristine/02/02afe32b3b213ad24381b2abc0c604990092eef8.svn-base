package com.aj.shop.service;

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
 * 查询商家品牌
 * */

@Service("shopBrandList")
public class QueryShopBrand implements PublishService{

	private Logger log = Logger.getLogger(QueryShopBrand.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		
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
		
		String sql = "select  id, brand_name brandName, brand_icon brandIcon, sort_index sortIndex from t_shop_brand where status = 0 order by sort_index";
		List<Map<String, Object>> brandList = baseDAO.getGenericByPositionToSQL(sql, (Integer.parseInt(currentPage) *Integer.parseInt(pageSize)) , Integer.parseInt(pageSize), null);
		String imgUrl= SystemConfig.getValue("img.http.url");
		if(brandList != null && brandList.size()>0){
			for(Map<String, Object> brand : brandList){
				if(brand.get("brandIcon") != null && !"".equals(brand.get("brandIcon"))){
					brand.put("brandIcon", imgUrl + brand.get("brandIcon"));
				}
			}
		}
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", brandList);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}
