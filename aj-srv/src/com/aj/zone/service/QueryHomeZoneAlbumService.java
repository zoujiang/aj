/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.zone.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.aj.familymgr.vo.THomeInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

/**
 * 8.4根据家人ID查看空间
 * */
@Service("zoneByFamilyList")
public class QueryHomeZoneAlbumService implements PublishService{

	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String familyId = params.optString("familyId");
		String homeId = params.optString("homeId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(familyId == null || "".equals(familyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "familyId为空！");
			return returnJSON.toString();
		}
		if(homeId == null || "".equals(homeId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "homeId为空！");
			return returnJSON.toString();
		}
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		try {
			String imgUrl= SystemConfig.getValue("img.http.url");
			//根据家庭ID 查询家庭名称
			String familySql = "SELECT CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE f.family_name END as familyName FROM t_family_info f LEFT JOIN t_remark_info r ON f.id = r.relation_id AND r.type = 2 and r.create_user_id = ? WHERE f.id = ?";
			List<Map<String, Object>> familyList = baseDAO.getGenericBySQL(familySql, new Object[]{userId,familyId});
			String familyName = "";
			if(familyList != null && familyList.size()>0){
				familyName = familyList.get(0).get("familyName") == null ? "" :familyList.get(0).get("familyName").toString();
			}
			result.put("familyName", familyName);
			//根据homeId查询是邀请的单人还是家庭，决定返回个人还是整个家庭
			THomeInfo homeInfo = baseDAO.get(THomeInfo.class, homeId);
			if( "1".equals(homeInfo.getInviteArea())){
				//邀请的家庭
				//根据家庭ID 查询家庭成员list
			
				String hsql = "SELECT  u.id, " +
							  "case when r.remark is not null then r.remark else nick_Name end nickName," +
							  "aj_no ajNo,CONCAT('"+imgUrl+"',photo) photo, "+
							  " '1' sign, " +
							  "IF( (YEAR(NOW())-YEAR(birthday)-1) + ( DATE_FORMAT(birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') ) < 12 ,'Y','N' ) AS nonage " +
							  "FROM t_user u left join t_remark_info r on u.id = r.relation_id and r.type = '1' and r.create_user_id = ? " +
							  " WHERE familyid = ? AND U.IS_VALID = 0  ORDER BY u.SEX";
				//查询家庭中大人的信息
				userList = baseDAO.getGenericBySQL(hsql, new Object[]{userId,familyId});
				hsql = "SELECT "+
						 " u.id,"+
						 " case when r.remark is not null then r.remark else nickName end nickName,"+
						 " ajno ajNo,"+
						 " CONCAT('"+imgUrl+"', photo_url) photo,"+
						 " '2' sign ," +
						 " IF( "+
						 "   (YEAR(NOW()) - YEAR(birthday) - 1) + ( "+
						 "     DATE_FORMAT(birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') "+
						 "   ) < 12, "+
						 "   'Y', "+
						 "   'N' "+
						 " ) AS nonage "+
					"	FROM "+
						"  t_children_info u left join t_remark_info r on u.id = r.relation_id and r.type = '1' and r.create_user_id = ? "+
						//" WHERE u.family_id = ?  "+
						" WHERE u.family_id like  CONCAT('%',?,'%')  "+
						 " AND u.is_private = '0' "+
					"	ORDER BY u.SEX ";
				//查询小孩信息，单独放在一个List中
				List<Map<String, Object>> childrenList = baseDAO.getGenericBySQL(hsql, new Object[]{userId, familyId});
				userList.addAll(childrenList);
				
				hsql = "SELECT "+
						 " u.id,"+
						 " case when r.remark is not null then r.remark else nickName end nickName,"+
						 " ajno ajNo,"+
						 " CONCAT('"+imgUrl+"', photo_url) photo,"+
						 " '3' sign ," +
						 " 'N' AS nonage "+
					"	FROM "+
						"  t_baby_info u  left join t_remark_info r on u.id = r.relation_id and r.type = '1' and r.create_user_id = ? "+
						//" WHERE u.family_id = ?  "+
						" WHERE u.family_id like  CONCAT('%',?,'%')  "+
						 " AND u.is_private = '0' ";
				//查询小孩信息，单独放在一个List中
				List<Map<String, Object>> babyList = baseDAO.getGenericBySQL(hsql, new Object[]{userId,familyId});
				userList.addAll(babyList);
			
		}else{
			//邀请个人
			String hsql = "SELECT  u.id, " +
					  "case when r.remark is not null then r.remark else nick_Name end nickName," +
					  "aj_no ajNo,CONCAT('"+imgUrl+"',photo) photo, "+
					  " '1' sign, " +
					  " 'Y' nonage " +
					  "FROM t_user u  left join t_remark_info r on u.id = r.relation_id and r.type = '1' and r.create_user_id = ? " +
					  " WHERE u.id = ? AND U.IS_VALID = 0 ";
			userList = baseDAO.getGenericBySQL(hsql, new Object[]{userId, homeInfo.getRelationUserId()});
		}
	
			
		String sql = "SELECT "+
					 " a.ID zoneId,"+
					 " a.ALBUM_NAME zoneName,"+
					 " a.ALBUM_TYPE zoneType,"+
					 " a.BABY_USER_ID childId,"+
					 " case when a.ALBUM_URL is not null and a.ALBUM_URL != '' then  CONCAT('"+imgUrl+"', a.ALBUM_URL)  else '' end zoneUrl,"+
					 " COUNT(p.id) photoNumber ," +
					 " a.CREATE_DATE createDate, '' shopLogo, '' shopName "+
				"	FROM "+
					 " T_Album a "+
					 " LEFT JOIN t_photo p "+
					 "   ON p.ALBUM_ID = a.ID "+
				"	WHERE ( a.ALBUM_TYPE = 4 or ( a.ALBUM_TYPE = 3 and a.HADINHERIT =1)) "+
					"  AND a.FAMILY_ID = ? "+
					" GROUP BY a.ID ";
		
		
		//查询影楼相册（免费+已付费的相册）
				sql += " union ";
				sql += "SELECT sa.id zoneId, sa.album_name zoneName, '7' zoneType, '' childId, case when sa.album_logo is not null and sa.album_logo != ''  then CONCAT('"+imgUrl+"',sa.album_logo) else '' end zoneUrl,  "+
				" (SELECT  COUNT(1) FROM  t_shop_photo sp  WHERE sp.ALBUM_ID = sa.ID) photoNumber ,"+
				"  sa.create_time createDate, s.logo shopLogo, s.shop_name shopName "+
				" FROM 	t_shop_album sa, t_shop_customer_user cu,t_shop_info s WHERE sa.VISIBLE_PROPERTY = 2 and sa.shop_id = s.id and sa.user_id = cu.id AND  (sa.is_pay = 0 OR (sa.is_pay = 1 AND sa.had_paid = 1)) AND cu.user_id in (select id from t_user where FAMILYID = ?)	";
				
				//查询动感影集
				sql += " union ";
				sql += "SELECT sda.id zoneId, sda.album_name zoneName, '8' zoneType, '' childId, case when sda.album_logo is not null and sda.album_logo != ''  then  CONCAT('"+imgUrl+"',sda.album_logo) else '' end zoneUrl, "+
					"(SELECT  COUNT(1) FROM  t_shop_photo sp  WHERE sp.ALBUM_ID = sda.ID) photoNumber ,"+
					"sda.create_time createDate, s.logo shopLogo, s.shop_name shopName "+
					" FROM 	t_shop_dynamic_album sda, t_shop_customer_user cu,t_shop_info s WHERE sda.VISIBLE_PROPERTY = 2 and sda.shop_id = s.id and sda.user_id = cu.id AND cu.user_id in (select id from t_user where FAMILYID = ?)";
				
				sql += " order by createDate";
		
		
		
		List<Map<String, Object>> albumList = baseDAO.getGenericBySQL(sql, new Object[]{familyId, familyId, familyId});
			
		if(albumList != null && albumList.size()>0){
			String accessSql= "select id, album_id, access_time, access_user_id from t_album_access_info where album_id = ? and access_user_id = ?";
			String lastUpdatePhotoSql = "SELECT "+
					 "   COUNT(l.id) laterNumber "+
					 " FROM "+
					 "   t_photo l "+
					 " WHERE l.ALBUM_ID = ? "+
					 "   AND l.CREATE_DATE >= ? "; 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			for(Map<String, Object> album : albumList){
				String time = "";
				//记录该相册访问记录
				List<Map<String, Object>> accessList = baseDAO.getGenericBySQL(accessSql, new Object[]{album.get("zoneId").toString() , userId});
				if(accessList ==  null || accessList.size() == 0){
					//默认查询7天内的
					c.setTime(new Date());
					c.add(Calendar.DATE, -7);
					Date dateFrom = c.getTime();
					time = sdf.format(dateFrom);
				}else{
					//上一次访问时间
					String perTime = accessList.get(0).get("access_time").toString();
					if( ((new Date().getTime() - sdf.parse(perTime).getTime()) / (1000 * 60 * 60 * 24)) > 7){
						//大于7天 按照7天查询
						c.setTime(new Date());
						c.add(Calendar.DATE, -7);
						Date dateFrom = c.getTime();
						time = sdf.format(dateFrom);
					}else{
						time = perTime;
					}
				}
				List<Map<String, Object>> lastUpdateList = baseDAO.getGenericBySQL(lastUpdatePhotoSql, new Object[]{album.get("zoneId").toString(), time} );
				if(lastUpdateList != null && lastUpdateList.size() > 0){
					album.put("laterNumber", lastUpdateList.get(0).get("laterNumber") == null ? "0" : lastUpdateList.get(0).get("laterNumber"));
				}else{
					album.put("laterNumber",  "0" );
				}
				
				String queryTemplateInfo = "select template_url templateUrl, template_logo templateLogo, music_url musicUrl from t_album_template t, t_shop_dynamic_album a  where t.id = a.template_id and  a.id = ? ";
				String ajServerUrl = SystemConfig.getValue("aj.service.address");
				album.put("shopLogo", (album.get("shopLogo") == null || "".equals(album.get("shopLogo").toString()) )? "" : imgUrl+album.get("shopLogo"));
				//TODO 返回动感影集预览地址
				if(album.get("zoneType") != null && "8".equals(album.get("zoneType").toString())){
					List<Map<String, Object>> list = baseDAO.getGenericBySQL(queryTemplateInfo, new Object[]{ album.get("zoneId")});
					if(list != null && list.size() > 0){
						String mUrl = list.get(0).get("musicUrl") == null ? "" : list.get(0).get("musicUrl").toString();
						String[] mUrls = mUrl.split(",");
						int i = new Random().nextInt(mUrls.length);
						album.put("viewUrl", ajServerUrl + "/dynamic/demo.jsp?albumId="+album.get("zoneId") +"&tpUrl="+list.get(0).get("templateUrl")+"&musicUrl="+ mUrls[i]);
					}
				}
			}
		}
			
		result.put("succMsg", "查询成功");
		result.put("userInfo", userList);
		result.put("AlbumList", albumList);
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
	} catch (Exception e) {
		e.printStackTrace();
		result.put("succMsg", "");
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "服务器内部错误！");
		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
	}
	return returnJSON.toString();
	}

}
