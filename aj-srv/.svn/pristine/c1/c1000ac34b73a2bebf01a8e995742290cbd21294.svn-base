/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.live.service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.GpsDistanceUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("liveList")
public class LiveListService implements PublishService{

	String imgUrl= SystemConfig.getValue("img.http.url");
	String recommendCount= SystemConfig.getValue("aj.live.recommend.count");
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String gps = params.optString("gps");


		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

		String sql = "SELECT type , id brandId,brand_name brandName, CASE WHEN brand_icon IS NOT NULL AND brand_icon != '' THEN CONCAT('"+imgUrl+"',brand_icon) ELSE '' END brandIcon FROM t_shop_brand WHERE STATUS = 0 AND is_recommend = 1 ORDER  BY TYPE, sort_index limit 0,4";
		List<Map<String, Object>> brandList = baseDAO.getGenericBySQL(sql, null);
		result.put("brandList",brandList);

		if(recommendCount != null && !"".equals(recommendCount) && recommendCount.split("\\|").length == 3){
			int shopRecommendCount = Integer.parseInt(recommendCount.split("\\|")[0]) ;
			int kindergartenRecommendCount = Integer.parseInt(recommendCount.split("\\|")[1]) ;
			int couponRecommendCount = Integer.parseInt(recommendCount.split("\\|")[2]) ;
			sql = "";
			if(shopRecommendCount > 0){
				sql += "(SELECT i.id shopId, i.shop_name shopName, c.`name` shopCategory, i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps FROM t_shop_info i, t_shop_category c WHERE i.`shop_category_id` = c.`id` AND i.status = 0 ANd i.is_recommend = 1 order by i.recommend_ix limit "+shopRecommendCount +")";
			}
			if(kindergartenRecommendCount > 0){
				if(sql.length() > 0){
					sql += " union ";
				}
				sql += "(SELECT i.id shopId, i.name shopName, c.`name` shopCategory,  i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps FROM t_kindergarten_info i, t_shop_category c WHERE i.`shop_category_id` = c.`id` AND i.status = 0 AND i.`is_recommend` = 0 order by i.sort_index limit "+kindergartenRecommendCount +")";
			}
			if(couponRecommendCount > 0){
				if(sql.length() > 0){
					sql += " union ";
				}
				sql += "(SELECT i.id shopId, i.shop_name shopName, c.`name` shopCategory,  i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps FROM t_coupon_shop_info i, t_shop_category c WHERE i.`shop_category` = c.`id` AND i.status = 0 AND i.`is_recommend` = 1 order by i.recommend_ix limit "+couponRecommendCount +")";
			}
			List<Map<String, Object>> recommendList = new ArrayList<Map<String, Object>>();
			if(sql.length() > 0){
				recommendList = baseDAO.getGenericBySQL(sql, null);

				String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";

				for(Map<String, Object> shop : recommendList){
					String logo = shop.get("shopLogo") == null ? "" :shop.get("shopLogo").toString();
					if(!"".equals(logo)){
						logo = imgUrl + logo;
						shop.put("shopLogo", logo);
					}

					List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{shop.get("shopId")});
					String shopScore = "4";
					int shopCmtCount = 0;
					if(cmtList != null && cmtList.size() > 0){
						shopScore = cmtList.get(0).get("score") == null ? shopScore :  cmtList.get(0).get("score")+"";
						shopCmtCount = Integer.parseInt(cmtList.get(0).get("num")+"");
					}
					shop.put("shopScore", shopScore);
					shop.put("shopCmtCount", shopCmtCount);
					if(gps != null && !"".equals(gps) && shop.get("gps") != null && !"".equals(shop.get("gps"))){
						double distance = GpsDistanceUtil.getDistance(gps, shop.get("gps").toString());
						shop.put("distance", distance);
					}else{
						shop.put("distance", -1);
					}
				}
			}
			result.put("recommendList" ,recommendList);
		}
		
		result.put("succMsg", "查询成功");
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}
