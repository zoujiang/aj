package com.aj.live.service;

import com.aam.model.TUser;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 查询我的生活列表
 * */

@Service("myliveList")
public class QueryMyLiveService implements PublishService{

	private Logger log = Logger.getLogger(QueryMyLiveService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {

		String imgUrl= SystemConfig.getValue("img.http.url");

		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String gps = params.optString("gps");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
		if(user == null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "用户不存在！");
			return returnJSON.toString();
		}
		String sql = "SELECT 1 type, i.id shopId, i.shop_name shopName, c.`name` shopCategory, i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps " +
				"FROM t_shop_info i, t_shop_category c WHERE i.`shop_category_id` = c.`id` AND i.status = 0 AND i.`id` " +
				"IN (SELECT shop_id FROM t_shop_album WHERE user_id = ? UNION SELECT shop_id FROM t_shop_dynamic_album WHERE user_id = ?) ";

		List<Map<String, Object>> shopList = baseDAO.getGenericBySQL(sql, new Object[]{userId, userId});

		sql = "SELECT  2 type, i.id shopId, i.name shopName, c.`name` shopCategory,  i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps " +
				"FROM t_kindergarten_info i, t_shop_category c WHERE i.`shop_category_id` = c.`id` AND i.status = 0 AND i.`id` " +
				"IN (SELECT shcool_id FROM t_kindergarten_albun WHERE `student` IN (SELECT id FROM t_kindergarten_student WHERE  parents_tel IN (SELECT USERTEL FROM t_user WHERE familyid = ? ))) ";
		shopList.addAll((Collection<? extends Map<String, Object>>) baseDAO.getGenericBySQL(sql , new Object[]{user.getFamilyId()}));

		sql = "SELECT 3 type,i.id shopId, i.shop_name shopName, c.`name` shopCategory,  i.`logo` shopLogo, i.`address` shopAddress, i.`gps` gps "+
				" FROM t_coupon_shop_info i, t_shop_category c WHERE i.`shop_category` = c.`id` AND i.status = 0 AND i.`id` "+
				" IN (SELECT c.shop_id FROM t_coupon_info c, t_my_coupon m WHERE c.`id` = m.`coupon_id` AND m.`user_id` = ?)";
		shopList.addAll((Collection<? extends Map<String, Object>>) baseDAO.getGenericBySQL(sql, new Object[]{userId}));


		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";
		for(Map<String, Object> shop : shopList){

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

		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", shopList);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}
