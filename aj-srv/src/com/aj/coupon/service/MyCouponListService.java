package com.aj.coupon.service;

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
 * 我得优惠券
 * */

@Service("myCouponList")
public class MyCouponListService implements PublishService{

	private Logger log = Logger.getLogger(MyCouponListService.class);

	String imgUrl= SystemConfig.getValue("img.http.url");
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {



		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);

		String rt = checkParam(params, new String[]{"userId","pageSize","currentPage"});
		if(rt != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("errorMsg", rt + "不能为空！");
			return returnJSON.toString();
		}
		
		String sql = "SELECT i.`id` couponId, i.`name` couponName, i.`first_page_pic` couponLogo, i.show_img couponShowPic, i.`is_link` isLink, i.`link_address` linkAddress, s.`id` shopId, s.`shop_name` shopName," +
				"s.`address` shopAddress, s.`tel` shopTel, s.logo shopLogo, s.show_pic shopShowPic FROM t_coupon_exchange e, t_coupon_info i, t_coupon_shop_info s WHERE e.coupon_id = i.id and i.`shop_id` = s.`id` and e.user_id = ? order by e.exchange_time desc limit ?,?";

		List<Map<String, Object>> couponList = baseDAO.getGenericBySQL(sql , new Object[]{userId, Integer.parseInt(currentPage) * Integer.parseInt(pageSize), Integer.parseInt(pageSize) } );
		
		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";
		for(Map<String, Object> coupon : couponList){
			
			if(coupon.get("couponLogo") != null && !"".equals(coupon.get("couponLogo"))){
				coupon.put("couponLogo", imgUrl + coupon.get("couponLogo") );
			}
			if(coupon.get("shopLogo") != null && !"".equals(coupon.get("shopLogo"))){
				coupon.put("shopLogo", imgUrl + coupon.get("shopLogo") );
			}
			if(coupon.get("couponShowPic") != null && !"".equals(coupon.get("couponShowPic"))){
				
				String[] showPics = coupon.get("couponShowPic").toString().split(",");
				String pics = "";
				for(String pic : showPics){
					pics += imgUrl + pic +",";
				}
				coupon.put("couponShowPic" , pics.substring(0, pics.length() -1));
			}
			if(coupon.get("shopShowPic") != null && !"".equals(coupon.get("shopShowPic"))){
				
				String[] showPics = coupon.get("shopShowPic").toString().split(",");
				String pics = "";
				for(String pic : showPics){
					pics += imgUrl + pic +",";
				}
				coupon.put("shopShowPic" , pics.substring(0, pics.length() -1));
			}
			
			List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{coupon.get("shopId")});
			if(cmtList != null && cmtList.size() > 0){
				coupon.put("couponScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
				coupon.put("couponCmtCount", cmtList.get(0).get("num"));
			}else{
				coupon.put("couponScore", 4);
				coupon.put("couponCmtCount", 0);
			}
		}
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		JSONObject result = new JSONObject();
		result.put("couponList", couponList);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

	private String checkParam(JSONObject param, String[] keys){

		for(String key : keys){
			if(param.get(key) == null || "".equals(param.get(key).toString())){
				return key;
			}
		}
		return null;
	}

}
