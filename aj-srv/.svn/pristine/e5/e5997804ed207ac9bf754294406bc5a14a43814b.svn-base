package com.aj.kindergarten.service;

import com.aj.kindergarten.vo.TKindergartenInfo;
import com.aj.shop.vo.TShopInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 查询幼儿园详情
 * */

@Service("kindergartenDetail")
public class QueryKindergartenDetailService implements PublishService{

	private Logger log = Logger.getLogger(QueryKindergartenDetailService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String kindergartenId = params.optString("kindergartenId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

		String re = checkParam(params, new String[]{"userId","kindergartenId"});
		if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		//根据userId查询有哪些商户

		String imgUrl= SystemConfig.getValue("img.http.url");
        String resUrl= SystemConfig.getValue("res.http.url");
		TKindergartenInfo kindergartenInfo = baseDAO.get(TKindergartenInfo.class, Integer.parseInt(kindergartenId));
		Map<String, Object> shopInfo = new HashMap<String, Object>();
		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";
		if(kindergartenInfo != null){
			shopInfo.put("kindergartenId", kindergartenId);
			shopInfo.put("kindergartenName", kindergartenInfo.getName());
			shopInfo.put("kindergartenAddress", kindergartenInfo.getAddress());
			shopInfo.put("tel",  kindergartenInfo.getTele());
			shopInfo.put("description", kindergartenInfo.getDescription());

			List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{kindergartenId});
			if(cmtList != null && cmtList.size() > 0){
				shopInfo.put("kindergartenScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
				shopInfo.put("kindergartenCmtCount", cmtList.get(0).get("num"));
			}else{
				shopInfo.put("kindergartenScore", 4);
				shopInfo.put("kindergartenCmtCount", 0);
			}

			if( kindergartenInfo.getLogo() != null && !"".equals(kindergartenInfo.getLogo())){
				shopInfo.put("kindergartenLogo", imgUrl + kindergartenInfo.getLogo());
			}
			if( kindergartenInfo.getShowPics() != null && !"".equals(kindergartenInfo.getShowPics())){
				String[] pics = kindergartenInfo.getShowPics().split(",");
				StringBuffer fullUrl = new StringBuffer();
				for(String pic : pics){
					fullUrl.append(imgUrl);
					fullUrl.append(pic);
					fullUrl.append(",");
				}
				shopInfo.put("kindergartenShowPic", fullUrl.toString().substring(0, fullUrl.toString().length()-1));
			}
			//查询作品展示
           String sql = "SELECT  id showId, NAME showName, category, CASE WHEN (imageUrl IS NOT NULL AND imageUrl != '' ) THEN CONCAT('"+imgUrl+"',imageUrl) ELSE '' END imageUrl , CASE WHEN (videoUrl IS NOT NULL AND videoUrl != '' ) THEN CONCAT('"+resUrl+"',videoUrl) ELSE '' END  videoUrl  FROM t_show  WHERE shop_id = ?";
           List<Map<String, Object>> shopShow = baseDAO.getGenericBySQL(sql, new Object[]{kindergartenId});
            result.put("kindergartenShow", shopShow);
		}
        result.put("shopInfo", shopInfo);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
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
