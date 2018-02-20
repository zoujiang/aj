/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.zone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONObject;


import com.aam.model.TUser;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familymgr.vo.THomeInfo;
import com.aj.kindergarten.vo.TKindergartenAlbumVisible;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import org.springframework.stereotype.Service;

/**
 * 5.4.17查看空间
 * */
@Service("queryZoneInfo")
public class QueryZoneInfoService implements PublishService{

	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String homeId = params.optString("homeId");
		String familyId = params.optString("familyId");
		Integer type = params.optInt("type");
		Integer properties = params.optInt("properties") == 0 ? 1: params.optInt("properties");  //1 查看个人空间  2查看他人空间
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(type == null || type <= 0){
			type = 1;
		}
		
		
		//根据家庭ID 查询家庭成员list
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> albumList = new ArrayList<Map<String,Object>>();
		String imgUrl= SystemConfig.getValue("img.http.url");
		TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));		
		if(properties == 1){
			//个人空间
			//根据家庭ID 查询家庭名称
			TFamilyInfo familyInfo = baseDAO.get(TFamilyInfo.class, user.getFamilyId());
			result.put("familyName", familyInfo.getFamilyName());
			String hsql ;
			try {
				 hsql = "SELECT  id, " +
							  "nick_Name nickName," +
							  "aj_no ajNo,CONCAT('"+imgUrl+"',photo) photo, " +
							  " '1' sign, " +
							  "IF( (YEAR(NOW())-YEAR(birthday)-1) + ( DATE_FORMAT(birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') ) < 12 ,'Y','N' ) AS nonage " +
							  "FROM t_user u " +
							  " WHERE familyid = ? AND U.IS_VALID = 0  AND u.ID = ? ";
				//查询家庭自己的信息
				List<Map<String, Object>> myselfList = baseDAO.getGenericBySQL(hsql, new Object[]{user.getFamilyId(), userId});
				userList.addAll(myselfList);
				
				hsql = "SELECT  u.id, " +
						"CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE  u.NICK_NAME END nickName," +
						"aj_no ajNo,CONCAT('"+imgUrl+"',photo) photo, " +
						" '1' sign, " +
						"IF( (YEAR(NOW())-YEAR(birthday)-1) + ( DATE_FORMAT(birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') ) < 12 ,'Y','N' ) AS nonage " +
						"FROM t_user u left join t_remark_info r on u.id=r.relation_id and r.type=1  and r.create_user_id = ?" +
						" WHERE familyid = ? AND U.IS_VALID = 0  AND u.ID != ?";
				//查询家庭中其他大人的信息
				List<Map<String, Object>> otherParentList  = baseDAO.getGenericBySQL(hsql, new Object[]{userId, user.getFamilyId(), userId});
				userList.addAll(otherParentList);
				
				hsql = "SELECT "+
						 " u.id,"+
						 " CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE  u.nickname END nickName,"+
						 " ajno ajNo,"+
						 " CONCAT('"+imgUrl+"', photo_url) photo,"+
						 " '2' sign, " +
						 " IF( "+
						 "   (YEAR(NOW()) - YEAR(birthday) - 1) + ( "+
						 "     DATE_FORMAT(birthday, '%m%d') <= DATE_FORMAT(NOW(), '%m%d') "+
						 "   ) < 12, "+
						 "   'Y', "+
						 "   'N' "+
						 " ) AS nonage "+
					" FROM "+
						" t_children_info u left join t_remark_info r on u.id=r.relation_id and r.type=1 and r.create_user_id = ? "+
						//" WHERE u.family_id = ?  "+
						" WHERE u.family_id like  CONCAT('%',?,'%')  "+
						" AND ( u.is_private = '0' or (u.is_private = '1' and u.create_user_id = ?)) "+
					" ORDER BY u.birthday ";
				//查询小孩信息，单独放在一个List中
				List<Map<String, Object>> childrenList = baseDAO.getGenericBySQL(hsql, new Object[]{userId, user.getFamilyId(), user.getId()+""});
				userList.addAll(childrenList);
				
				hsql = "SELECT "+
						 " u.id,"+
						 "  CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE  u.nickname END nickName,"+
						 " ajno ajNo,"+
						 " CONCAT('"+imgUrl+"', photo_url) photo,"+
						 " '3' sign ," +
						 " 'N' AS nonage "+
					" FROM "+
						"  t_baby_info u left join t_remark_info r on u.id=r.relation_id and r.type=1 and r.create_user_id = ?"+
						//" WHERE u.family_id = ?  "+
						" WHERE u.family_id like  CONCAT('%',?,'%') "+
						" AND ( u.is_private = '0' or (u.is_private = '1' and u.create_user_id = ?)) ";
				//查询小孩信息，
				List<Map<String, Object>> babyList = baseDAO.getGenericBySQL(hsql, new Object[]{userId,user.getFamilyId(), user.getId()+""});
				userList.addAll(babyList);
				//查询宠物
				hsql = "SELECT "+
						 " u.id,"+
						 " CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE  u.NICK_NAME END nickName,"+
						 " ajno ajNo,"+
						 " CONCAT('"+imgUrl+"', photo_url) photo,"+
						 " '4' sign, " +
						 " 'N' AS nonage "+
					" FROM "+
						"  t_pet_info u left join t_remark_info r on u.id=r.relation_id and r.type=1 and r.create_user_id = ? "+
						" WHERE u.CREATE_USER_ID = ? ";
				//查询宠物信息，
				List<Map<String, Object>> petList = baseDAO.getGenericBySQL(hsql, new Object[]{userId,userId});
				userList.addAll(petList);
				
				if(type == 1){
					//查询普通相册空间
				
					String sql = "SELECT "+
							 " a.ID zoneId,"+
							 " a.ALBUM_NAME zoneName,"+
							 " a.ALBUM_TYPE zoneType,"+
							 " a.BABY_USER_ID childId,"+
							 " CONCAT('"+imgUrl+"', a.ALBUM_URL) zoneUrl,"+
							 " COUNT(p.id) photoNumber,"+
							 " (SELECT "+
							 "   COUNT(l.id) "+
							 " FROM "+
							 "	t_photo l "+
							 " WHERE l.ALBUM_ID = a.id "+
							 " AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(l.CREATE_DATE)) laterNumber,a.CREATE_DATE createDate, '' shopLogo, '' shopName  "+ 
						" FROM "+
							 " T_Album a "+
							 " LEFT JOIN t_photo p "+
							 "   ON p.ALBUM_ID = a.ID " + 
						" WHERE ( a.ALBUM_TYPE = 4 or ( a.ALBUM_TYPE = 3 and a.HADINHERIT =1))"+
							"  AND a.FAMILY_ID = ? "+
							" GROUP BY a.ID  order by createDate";
					albumList = baseDAO.getGenericBySQL(sql, new Object[]{user.getFamilyId()});
				}else if(type == 2){
					String ajServerUrl = SystemConfig.getValue("aj.service.address");
					//影楼相册
					String sql = "SELECT sa.id zoneId, sa.album_name zoneName, '7' zoneType, '' childId, case when sa.album_logo is not null and sa.album_logo != ''  then CONCAT('"+imgUrl+"',sa.album_logo) else '' end zoneUrl,  "+
							" (SELECT  COUNT(1) FROM  t_shop_photo sp  WHERE sp.ALBUM_ID = sa.ID) photoNumber ,"+
							" 0 laterNumber, sa.create_time createDate, s.logo shopLogo, s.shop_name shopName "+
							" FROM 	t_shop_album sa, t_shop_customer_user cu,t_shop_info s WHERE sa.VISIBLE_PROPERTY = 2 and sa.shop_id = s.id and sa.user_id = cu.id AND  (sa.is_pay = 0 OR (sa.is_pay = 1 AND sa.had_paid = 1)) AND cu.user_id in (select id from t_user where FAMILYID = ?)	";
							
							//查询动感影集
							sql += " union ";
							sql += "SELECT sda.id zoneId, sda.album_name zoneName, '8' zoneType, '' childId, case when sda.album_logo is not null and sda.album_logo != ''  then  CONCAT('"+imgUrl+"',sda.album_logo) else '' end zoneUrl, "+
								"(SELECT  COUNT(1) FROM  t_shop_photo sp  WHERE sp.ALBUM_ID = sda.ID) photoNumber ,"+
								"0 laterNumber,sda.create_time createDate, s.logo shopLogo, s.shop_name shopName "+
								" FROM 	t_shop_dynamic_album sda, t_shop_customer_user cu,t_shop_info s WHERE sda.VISIBLE_PROPERTY = 2 and sda.shop_id = s.id and sda.user_id = cu.id AND cu.user_id in (select id from t_user where FAMILYID = ?)";
							
							sql += " order by createDate";
							
							albumList = baseDAO.getGenericBySQL(sql, new Object[]{user.getFamilyId(), user.getFamilyId()});
							
							if(albumList != null && albumList.size() > 0){	
								String queryTemplateInfo = "select template_url templateUrl, template_logo templateLogo, music_url musicUrl from t_album_template t, t_shop_dynamic_album a  where t.id = a.template_id and  a.id = ? ";
								
								for(int i=0;i<albumList.size();i++){
									Map<String, Object> map = albumList.get(i);
									map.put("shopLogo", (map.get("shopLogo") == null || "".equals(map.get("shopLogo").toString()) )? "" : imgUrl+map.get("shopLogo"));
									if(map.get("zoneType") != null && "8".equals(map.get("zoneType").toString())){
										List<Map<String, Object>> list = baseDAO.getGenericBySQL(queryTemplateInfo, new Object[]{ map.get("zoneId")});
										if(list != null && list.size() > 0){
											String mUrl = list.get(0).get("musicUrl") == null ? "" : list.get(0).get("musicUrl").toString();
											String[] mUrls = mUrl.split(",");
											map.put("viewUrl", ajServerUrl + "/dynamic/demo.jsp?albumId="+map.get("zoneId") +"&tpUrl="+list.get(0).get("templateUrl")+"&musicUrl="+ mUrls[new Random().nextInt(mUrls.length)]);
										}
									}
								}
							}
					
				}else if(type == 3){
					//幼儿园,因为有可能填的爸爸的手机号，有可能是妈妈的手机号，所以这里要查询出2个家长的登陆手机号
					String family =	user.getFamilyId();
					List<TUser> users = baseDAO.getGenericByHql("from TUser where familyId = ?", family);
					String phones = "";
					for(TUser u : users){
						phones += "'"+ u.getUserTel() +"',";
					}
					phones = phones.substring(0, phones.length() -1);
					
					//List<Map<String, Object>> albumResult = new ArrayList<Map<String, Object>>();
					
					//List<TKindergartenStudent> students =  baseDAO.getGenericByHql("from TKindergartenStudent where parentsTel in ("+ phones +")", null);
					String sql = "select s.kindergarten_id kindergartenId, i.`name` kindergartenName, i.logo logo, s.grade_id gradeId, s.name studentName, s.id studentId, g.series from `t_kindergarten_student`  s, `t_kindergarten_info` i, `t_kindergarten_grade` g where s.`kindergarten_id` = i.id and s.grade_id = g.id and  s.`parents_tel` in ("+ phones +") ";
					List<Map<String, Object>> kinderpratenList = baseDAO.getGenericBySQL(sql, null);
					for(Map<String, Object> kd : kinderpratenList){
						
						String kindergartenId = kd.get("kindergartenId").toString();
						String kindergartenName = kd.get("kindergartenName").toString();
						String gradeId = kd.get("gradeId").toString();
						String studentName = kd.get("studentName").toString();
						String studentId = kd.get("studentId").toString();
						String series = kd.get("series").toString();   //入学年
						String logo = kd.get("logo") == null ? "" : imgUrl + kd.get("logo").toString() ;
						String albumName = kindergartenName +"("+ series +studentName+")";
						Map<String, Object> albumInfo = new HashMap<String, Object>();
						albumInfo.put("shopName", albumName);
						albumInfo.put("shopLogo", logo);
						
						//查询相册
						sql = "select a.*, (select count(1) from t_kindergarten_photo p where p.album_id = a.id ) photoNumber, (select count(1) from t_kindergarten_photo p where p.album_id = a.id AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(p.create_time)) laterNumber   from `t_kindergarten_albun` a where  a.shcool_id = ? and a.grade_id = ?  and case when a.type = 2 then  a.student = ? else 1=1 end";
						List<Map<String, Object>> kinderpratenAlbumList = baseDAO.getGenericBySQL(sql, new Object[]{ kindergartenId, gradeId, studentId} );
						
						List<Map<String, Object>> albumListResult = new ArrayList<Map<String, Object>>();
						
						for(Map<String, Object> album : kinderpratenAlbumList){
							//查看此相册是否被设置为 非所有人可见
							String hql2 = "from TKindergartenAlbumVisible where familyId = ? and albumId = ?";
							List<TKindergartenAlbumVisible> av = baseDAO.getGenericByHql(hql2, user.getFamilyId(), album.get("id"));
							if(av.size() == 0 || av.get(0).getVisibleProperty() ==2 ){
								Map<String, Object> temp = new HashMap<String, Object>();
								temp.put("zoneId", album.get("id"));
								temp.put("zoneName", album.get("album_name"));
								temp.put("zoneUrl", album.get("zoneUrl") == null ? "" : imgUrl+ album.get("zoneUrl"));
								temp.put("photoNumber", album.get("photoNumber"));
								temp.put("laterNumber", album.get("laterNumber"));
								
								albumListResult.add(temp);
							}
						}
						albumInfo.put("albumList", albumListResult);
						//albumResult.add(albumInfo);
						albumList.add(albumInfo);
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				result.put("succMsg", "");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "服务器内部错误！");
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			}
		}else if(properties == 2){
			//查看他人空间
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
			
			try {
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
			if(type == 1){
				String sql = "SELECT "+
						 " a.ID zoneId,"+
						 " a.ALBUM_NAME zoneName,"+
						 " a.ALBUM_TYPE zoneType,"+
						 " a.BABY_USER_ID childId,"+
						 " CONCAT('"+imgUrl+"', a.ALBUM_URL) zoneUrl,"+
						 " COUNT(p.id) photoNumber,"+
						 " (SELECT "+
						 "   COUNT(l.id) "+
						 " FROM "+
						 "	t_photo l "+
						 " WHERE l.ALBUM_ID = a.id "+
						 " AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(l.CREATE_DATE)) laterNumber,a.CREATE_DATE createDate, '' shopLogo, '' shopName  "+ 
					" FROM "+
						 " T_Album a "+
						 " LEFT JOIN t_photo p "+
						 "   ON p.ALBUM_ID = a.ID " + 
					" WHERE ( a.ALBUM_TYPE = 4 or ( a.ALBUM_TYPE = 3 and a.HADINHERIT =1))"+
						"  AND a.FAMILY_ID = ? "+
						" GROUP BY a.ID   order by createDate";
				albumList = baseDAO.getGenericBySQL(sql, new Object[]{user.getFamilyId()});
				
			}else if(type == 2){
					String sql = "SELECT sa.id zoneId, sa.album_name zoneName, '7' zoneType, '' childId, case when sa.album_logo is not null and sa.album_logo != ''  then CONCAT('"+imgUrl+"',sa.album_logo) else '' end zoneUrl,  "+
						" (SELECT  COUNT(1) FROM  t_shop_photo sp  WHERE sp.ALBUM_ID = sa.ID) photoNumber ,"+
						" 0 laterNumber, sa.create_time createDate, s.logo shopLogo, s.shop_name shopName "+
						" FROM 	t_shop_album sa, t_shop_customer_user cu,t_shop_info s WHERE sa.VISIBLE_PROPERTY = 2 and sa.shop_id = s.id and sa.user_id = cu.id AND  (sa.is_pay = 0 OR (sa.is_pay = 1 AND sa.had_paid = 1)) AND cu.user_id in (select id from t_user where FAMILYID = ?)	";
						
						//查询动感影集
						sql += " union ";
						sql += "SELECT sda.id zoneId, sda.album_name zoneName, '8' zoneType, '' childId, case when sda.album_logo is not null and sda.album_logo != ''  then  CONCAT('"+imgUrl+"',sda.album_logo) else '' end zoneUrl, "+
							"(SELECT  COUNT(1) FROM  t_shop_photo sp  WHERE sp.ALBUM_ID = sda.ID) photoNumber ,"+
							"0 laterNumber,sda.create_time createDate, s.logo shopLogo, s.shop_name shopName "+
							" FROM 	t_shop_dynamic_album sda, t_shop_customer_user cu,t_shop_info s WHERE sda.VISIBLE_PROPERTY = 2 and sda.shop_id = s.id and sda.user_id = cu.id AND cu.user_id in (select id from t_user where FAMILYID = ?)";
						
						sql += " order by createDate";
						
						albumList = baseDAO.getGenericBySQL(sql, new Object[]{user.getFamilyId(), user.getFamilyId(), user.getFamilyId()});
						
						if(albumList != null && albumList.size() > 0){	
							String queryTemplateInfo = "select template_url templateUrl, template_logo templateLogo, music_url musicUrl from t_album_template t, t_shop_dynamic_album a  where t.id = a.template_id and  a.id = ? ";
							String ajServerUrl = SystemConfig.getValue("aj.service.address");
							for(int i=0;i<albumList.size();i++){
								Map<String, Object> map = albumList.get(i);
								map.put("shopLogo", (map.get("shopLogo") == null || "".equals(map.get("shopLogo").toString()) )? "" : imgUrl+map.get("shopLogo"));
								if(map.get("zoneType") != null && "8".equals(map.get("zoneType").toString())){
									List<Map<String, Object>> list = baseDAO.getGenericBySQL(queryTemplateInfo, new Object[]{ map.get("zoneId")});
									if(list != null && list.size() > 0){
										String mUrl = list.get(0).get("musicUrl") == null ? "" : list.get(0).get("musicUrl").toString();
										String[] mUrls = mUrl.split(",");
										map.put("viewUrl", ajServerUrl + "/dynamic/demo.jsp?albumId="+map.get("zoneId") +"&tpUrl="+list.get(0).get("templateUrl")+"&musicUrl="+ mUrls[new Random().nextInt(mUrls.length)]);
									}
								}
							}
						}
				}else if(type == 3){
					//幼儿园,因为有可能填的爸爸的手机号，有可能是妈妈的手机号，所以这里要查询出2个家长的登陆手机号
					List<TUser> users = baseDAO.getGenericByHql("from TUser where familyId = ?", familyId);
					String phones = "";
					for(TUser u : users){
						phones += "'"+ u.getUserTel() +"',";
					}
					phones = phones.substring(0, phones.length() -1);

					//List<Map<String, Object>> albumResult = new ArrayList<Map<String, Object>>();

					//List<TKindergartenStudent> students =  baseDAO.getGenericByHql("from TKindergartenStudent where parentsTel in ("+ phones +")", null);
					String sql = "select s.kindergarten_id kindergartenId, i.`name` kindergartenName, i.logo logo, s.grade_id gradeId, s.name studentName, s.id studentId, g.series from `t_kindergarten_student`  s, `t_kindergarten_info` i, `t_kindergarten_grade` g where s.`kindergarten_id` = i.id and s.grade_id = g.id and  s.`parents_tel` in ("+ phones +") ";
					List<Map<String, Object>> kinderpratenList = baseDAO.getGenericBySQL(sql, null);
					for(Map<String, Object> kd : kinderpratenList){

						String kindergartenId = kd.get("kindergartenId").toString();
						String kindergartenName = kd.get("kindergartenName").toString();
						String gradeId = kd.get("gradeId").toString();
						String studentName = kd.get("studentName").toString();
						String studentId = kd.get("studentId").toString();
						String series = kd.get("series").toString();   //入学年
						String logo = kd.get("logo") == null ? "" : imgUrl + kd.get("logo").toString() ;
						String albumName = kindergartenName +"("+ series +studentName+")";
						Map<String, Object> albumInfo = new HashMap<String, Object>();
						albumInfo.put("shopName", albumName);
						albumInfo.put("shopLogo", logo);

						//查询相册
						sql = "select a.*, (select count(1) from t_kindergarten_photo p where p.album_id = a.id ) photoNumber, (select count(1) from t_kindergarten_photo p where p.album_id = a.id AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(p.create_time)) laterNumber   from `t_kindergarten_albun` a where  a.shcool_id = ? and a.grade_id = ?  and case when a.type = 2 then  a.student = ? else 1=1 end";
						List<Map<String, Object>> kinderpratenAlbumList = baseDAO.getGenericBySQL(sql, new Object[]{ kindergartenId, gradeId, studentId} );

						List<Map<String, Object>> albumListResult = new ArrayList<Map<String, Object>>();

						for(Map<String, Object> album : kinderpratenAlbumList){
							//查看此相册是否被设置为 非所有人可见
							String hql2 = "from TKindergartenAlbumVisible where familyId = ? and albumId = ?";
							List<TKindergartenAlbumVisible> av = baseDAO.getGenericByHql(hql2, familyId, album.get("id"));
							if(av.size() == 0 || av.get(0).getVisibleProperty() ==2 ){
								Map<String, Object> temp = new HashMap<String, Object>();
								temp.put("zoneId", album.get("id"));
								temp.put("zoneName", album.get("album_name"));
								temp.put("zoneUrl", album.get("zoneUrl") == null ? "" : imgUrl+ album.get("zoneUrl"));
								temp.put("photoNumber", album.get("photoNumber"));
								temp.put("laterNumber", album.get("laterNumber"));

								albumListResult.add(temp);
							}
						}
						albumInfo.put("albumList", albumListResult);
						//albumResult.add(albumInfo);
						albumList.add(albumInfo);
					}
				}
				result.put("succMsg", "查询成功");
				result.put("userInfo", userList);
				result.put("albumList", albumList);
				returnJSON.put("result", result);
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("errorMsg", "");
				
			}catch (Exception e) {
				e.printStackTrace();
				result.put("succMsg", "");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "服务器内部错误！");
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			}
			
		}
		result.put("succMsg", "查询成功");
		result.put("userInfo", userList);
		result.put("albumList", albumList);
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}
