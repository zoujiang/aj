package com.aj.kindergarten.service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.GpsDistanceUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 查询幼儿园列表
 * */

@Service("kindergartenList")
public class KindergartenListService implements PublishService{

	private Logger log = Logger.getLogger(KindergartenListService.class);
	
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
		String kindergartenName = params.optString("kindergartenName");
		String brandId = params.optString("brandId");
		
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
		
		String sql = "SELECT sp.id kindergartenId, sp.name kindergartenName, sc.name kindergartenCategory, logo kindergartenLogo, address kindergartenAddress, gps FROM t_kindergarten_info sp, t_shop_category sc WHERE sp.shop_category_id = sc.id AND sp.status = 0 ";
		List<String> param = new ArrayList<String>();
		if(kindergartenName != null && !"".equals(kindergartenName.trim())){
			sql += " and sp.name like concat('%',?,'%') ";
			param.add(kindergartenName.trim());
		}
		if(brandId != null && !"".equals(brandId)){
			sql += " and sp.brand_id = ?"; 
			param.add(brandId);
		}
		//if(gps == null || "".equals(gps)){
			sql += " order by sort_index";
		//}
		List<Map<String, Object>> shopList = baseDAO.getGenericByPositionToSQL(sql, (Integer.parseInt(currentPage) *Integer.parseInt(pageSize)) , Integer.parseInt(pageSize), param.toArray());
		if(shopList != null && shopList.size()>0){
			String imgUrl= SystemConfig.getValue("img.http.url");
			String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";
			for(Map<String, Object> shop : shopList){
				if(shop.get("kindergartenLogo") != null && !"".equals(shop.get("kindergartenLogo"))){
					shop.put("kindergartenLogo", imgUrl + shop.get("kindergartenLogo"));
				}
				List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{shop.get("kindergartenId")});
				if(cmtList != null && cmtList.size() > 0){
					shop.put("kindergartenScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
					shop.put("kindergartenCmtCount", cmtList.get(0).get("num"));
				}else{
					shop.put("kindergartenScore", 4);
					shop.put("kindergartenCmtCount", 0);
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
