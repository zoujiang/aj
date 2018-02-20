package com.aj.albummgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.familymgr.vo.TFamilyInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 相册列表
 * */

@Service("albumList")
public class AlbumQueryService implements PublishService{

	private Logger log = Logger.getLogger(AlbumQueryService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String albumCategory = params.optString("albumCategory");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
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
		String hsql = "from TUser where id = ?";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "该用户不存在！");
			return returnJSON.toString();
		}
		TUser user = userList.get(0);
		//查询家庭信息，是否已婚
		TFamilyInfo family = baseDAO.get(TFamilyInfo.class, user.getFamilyId());
		String sql = "";
		List<Object> paramsList = new ArrayList<Object>();
		
		if(family.getIsMarried() == 0){
			
			sql = "SELECT a.id albumId, a.ALBUM_NAME albumName, a.ALBUM_URL albumUrl, a.DESCRIPTION  albumDesc, a.ALBUM_TYPE albumType, a.ALBUM_CATEGORY albumCategory, a.IS_SYSINIT isSysInit, a.BABY_USER_ID babyId,"+
					" (SELECT  COUNT(*) FROM  t_photo p  WHERE p.ALBUM_ID = a.ID) photoNumber , ( SELECT COUNT(*) FROM t_photo p WHERE p.ALBUM_ID = a.ID AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(CREATE_DATE)) laterNumber, a.CREATE_DATE createDate, '' shopLogo, '' shopName, a.VISIBLE_PROPERTY visibleProperty "+
					" FROM "+
					"  t_album a "+
					" WHERE (a.FAMILY_ID = ? OR a.CREATE_USER_ID = ?)  AND CASE WHEN a.ALBUM_TYPE = 3 THEN  a.HADINHERIT = 1 ELSE 1=1 END" ;
				paramsList.add(user.getFamilyId());
				paramsList.add(Integer.parseInt(userId));
		}else{
			
			sql = "SELECT a.id albumId, a.ALBUM_NAME albumName, a.ALBUM_URL albumUrl, a.DESCRIPTION  albumDesc, a.ALBUM_TYPE albumType, a.ALBUM_CATEGORY albumCategory, a.IS_SYSINIT isSysInit,a.BABY_USER_ID babyId,"+
					" (SELECT  COUNT(*) FROM  t_photo p  WHERE p.ALBUM_ID = a.ID) photoNumber , ( SELECT COUNT(*) FROM t_photo p WHERE p.ALBUM_ID = a.ID AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(CREATE_DATE)) laterNumber , a.CREATE_DATE createDate, '' shopLogo, '' shopName, a.VISIBLE_PROPERTY visibleProperty  "+
					" FROM "+
					"  t_album a "+
					" WHERE ((a.FAMILY_ID = ? OR a.CREATE_USER_ID = ?) and a.ALBUM_TYPE !=3) or (a.ALBUM_TYPE = 3 and a.INHERIT_TARGET = ? )  OR (a.ALBUM_TYPE = 3 AND a.HADINHERIT = 1 AND a.family_id = ? )" ;
				paramsList.add(user.getFamilyId());
				paramsList.add(Integer.parseInt(userId));
				paramsList.add(Integer.parseInt(userId));
				paramsList.add(user.getFamilyId());
		}
		if(albumCategory != null && !"".equals(albumCategory)){
			sql += " and a.ALBUM_CATEGORY = ? ";
			paramsList.add(albumCategory);

		}

		/**
		String userStr = "'"+ userId +"'";
		if(family.getIsMarried() == 0){
			//已婚，商户相册，夫妻都可以看见
			List<TUser> users = baseDAO.getGenericByHql("from TUser where familyId = ?", user.getFamilyId());
			for(TUser u : users){
				userStr += ",'"+ u.getId() + "'";
			}
		}
		
		//查询影楼相册（免费+已付费的相册）
		sql += " union ";
		sql += "SELECT sa.id albumId, sa.album_name albumName, sa.album_logo albumUrl, sa.description albumDesc, '7' albumType, '1' albumCategory ,'0' isSysInit, '' babyId,"+
		" (SELECT  COUNT(1) FROM  t_shop_photo sp  WHERE sp.ALBUM_ID = sa.ID) photoNumber ,"+
		" ( SELECT COUNT(1) FROM t_shop_photo sp WHERE sp.ALBUM_ID = sa.ID AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(sp.create_time)) laterNumber, sa.create_time createDate, s.logo shopLogo, s.shop_name shopName , sa.VISIBLE_PROPERTY visibleProperty "+
		" FROM 	t_shop_album sa, t_shop_customer_user cu,t_shop_info s WHERE sa.shop_id = s.id and sa.user_id = cu.id AND  (sa.is_pay = 0 OR (sa.is_pay = 1 AND sa.had_paid = 1)) AND  ((sa.VISIBLE_PROPERTY != 0 and cu.user_id in ("+userStr+")) or ( sa.VISIBLE_PROPERTY = 0 and cu.user_id = '"+ userId +"' ))";
		//paramsList.add(userStr);
		
		//查询动感影集
		sql += " union ";
		sql += "SELECT sda.id albumId, sda.album_name albumName, sda.album_logo albumUrl, sda.description albumDesc, '8' albumType, '1' albumCategory ,'0' isSysInit, '' babyId,"+
			"(SELECT  COUNT(1) FROM  t_shop_photo sp  WHERE sp.ALBUM_ID = sda.ID) photoNumber ,"+
			"( SELECT COUNT(1) FROM t_shop_photo sp WHERE sp.ALBUM_ID = sda.ID AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(sp.create_time)) laterNumber, sda.create_time createDate, s.logo shopLogo, s.shop_name shopName , sda.VISIBLE_PROPERTY visibleProperty"+
			" FROM 	t_shop_dynamic_album sda, t_shop_customer_user cu,t_shop_info s WHERE sda.shop_id = s.id and sda.user_id = cu.id AND  ((sda.VISIBLE_PROPERTY != 0 and cu.user_id in ("+userStr+")) or ( sda.VISIBLE_PROPERTY = 0 and cu.user_id = '"+ userId +"' )) ";
		//paramsList.add(userStr);
		*/
		sql += " order by createDate";
		List<Map<String, Object>> albumList = baseDAO.getGenericByPositionToSQL(sql, (Integer.parseInt(currentPage) *Integer.parseInt(pageSize)) , Integer.parseInt(pageSize), paramsList.toArray());
		
		if(albumList == null || albumList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "暂无相册");
			return returnJSON.toString();
		}else{
			String imgUrl= SystemConfig.getValue("img.http.url");
			//String queryTemplateInfo = "select template_url templateUrl, template_logo templateLogo, music_url musicUrl from t_album_template t, t_shop_dynamic_album a  where t.id = a.template_id and  a.id = ? ";
		//	String ajServerUrl = SystemConfig.getValue("aj.service.address");
			for(int i=0;i<albumList.size();i++){
				Map<String, Object> map = albumList.get(i);
				map.put("albumUrl", (map.get("albumUrl") == null || "".equals(map.get("albumUrl").toString()) )? "" : imgUrl+map.get("albumUrl"));
			//	map.put("shopLogo", (map.get("shopLogo") == null || "".equals(map.get("shopLogo").toString()) )? "" : imgUrl+map.get("shopLogo"));
				//TODO 返回动感影集预览地址
			//	if(map.get("albumType") != null && "8".equals(map.get("albumType").toString())){
			//		List<Map<String, Object>> list = baseDAO.getGenericBySQL(queryTemplateInfo, new Object[]{ map.get("albumId")});
			//		if(list != null && list.size() > 0){
			//			String mUrl = list.get(0).get("musicUrl") == null ? "" : list.get(0).get("musicUrl").toString();
			//			String[] mUrls = mUrl.split(",");
			//			map.put("viewUrl", ajServerUrl + "/dynamic/demo.jsp?albumId="+map.get("albumId") +"&tpUrl="+list.get(0).get("templateUrl")+"&musicUrl="+ mUrls[new Random().nextInt(mUrls.length)]);
			//		}
			//	}
			//  JSONObject jo = new JSONObject();
			//	jo.put("albumId", albumList.get(i).get("albumId"));
			//	jo.put("albumName", albumList.get(i).get("albumName"));
			//	jo.put("albumUrl", albumList.get(i).get("albumUrl") == null ? "" : imgUrl+albumList.get(i).get("albumUrl"));
			//	jo.put("photoNumber", albumList.get(i).get("photoNumber"));
			//	ja.add(jo);
			}

			result.put("succMsg", "查询成功");
			result.put("list", albumList);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}
