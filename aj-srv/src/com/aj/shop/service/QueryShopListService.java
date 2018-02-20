package com.aj.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.GpsDistanceUtil;


/**
 * 查询商家列表
 * */

@Service("shopList")
public class QueryShopListService implements PublishService{

	private Logger log = Logger.getLogger(QueryShopListService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String gps = params.optString("gps");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		String showInFirstPage = params.optString("showInFirstPage");
		String isRecommend = params.optString("isRecommend");
		String shopName = params.optString("shopName");
		String brandId = params.optString("brandId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		/*if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		} */
		
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
		
		String sql = "select sp.id shopId, shop_name shopName, sc.name shopCategory, logo shopLogo, address shopAddress, gps from t_shop_info sp, t_shop_category sc where sp.shop_category_id = sc.id and sp.status = 0 ";
		List<String> param = new ArrayList<String>();
		if(showInFirstPage != null && !"".equals(showInFirstPage)){
			sql += " and sp.show_in_first = ?"; 
			param.add(showInFirstPage);
		}
		if(shopName != null && !"".equals(shopName.trim())){
			sql += " and sp.shop_name like concat('%',?,'%') "; 
			param.add(shopName.trim());
		}
		if(isRecommend != null && !"".equals(isRecommend)){
			sql += " and sp.is_recommend = ?"; 
			param.add(isRecommend);
		}
		if(brandId != null && !"".equals(brandId)){
			sql += " and sp.brand_id = ?"; 
			param.add(brandId);
		}
		//if(gps == null || "".equals(gps)){
			sql += " order by recommend_ix";
		//}
		List<Map<String, Object>> shopList = baseDAO.getGenericByPositionToSQL(sql, (Integer.parseInt(currentPage) *Integer.parseInt(pageSize)) , Integer.parseInt(pageSize), param.toArray());
		if(shopList != null && shopList.size()>0){
			String imgUrl= SystemConfig.getValue("img.http.url");
			String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";
			for(Map<String, Object> shop : shopList){
				if(shop.get("shopLogo") != null && !"".equals(shop.get("shopLogo"))){
					shop.put("shopLogo", imgUrl + shop.get("shopLogo"));
				}
				List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{shop.get("shopId")});
				if(cmtList != null && cmtList.size() > 0){
					shop.put("shopScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
					shop.put("shopCmtCount", cmtList.get(0).get("num"));
				}else{
					shop.put("shopScore", 4);
					shop.put("shopCmtCount", 0);
				}
				if(gps != null && !"".equals(gps) && shop.get("gps") != null && !"".equals(shop.get("gps"))){
					double distance = GpsDistanceUtil.getDistance(gps, shop.get("gps").toString());
					shop.put("distance", distance);
				}else{
					shop.put("distance", -1);
				}
			}
		}
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", shopList);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}
