package com.aj.photomgr.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.msg.api.jpush.JPush;
import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;
import com.aj.photomgr.vo.TPhoto;
import com.aj.shop.vo.TShopPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 相片管理
 * */

@Service("albumPhotoMgr")
public class PhotoManageService implements PublishService{

	private Logger log = Logger.getLogger(PhotoManageService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String albumId = params.optString("albumId");
		String photoUrl = params.optString("photoUrl");
		String oper = params.optString("oper");  /*1:新增  2：删除 3：移动 4：复制 */
		String photoId = params.optString("photoId");
		String videoUrl = params.optString("videoUrl");
		String description = params.optString("description");
		String targetAlbumId = params.optString("targetAlbumId");
		String albumType = params.optString("albumType");
		String targetAlbumType = params.optString("targetAlbumType");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}
		if(albumId == null || "".equals(albumId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "albumId为空！");
			return returnJSON.toString();
		}
		
		String userSql = "from TUser where id = ? and isValid = 0 ";
		List<TUser> userList = baseDAO.getGenericByHql(userSql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "用户不存在");
			return returnJSON.toString();
		}
	/*	//查询家庭信息，是否已婚
		TFamilyInfo family = baseDAO.get(TFamilyInfo.class, userList.get(0).getFamilyId());*/
		//取消验证，只要能看到的相册， 都是能操作的相册
	/*	String albumSql = "from TAlbum where id = ? ";
		List<TAlbum> targetAlbumList = baseDAO.getGenericByHql(albumSql, albumId);
		TAlbum album = null;
		if(targetAlbumList == null || targetAlbumList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "相册不存在");
			return returnJSON.toString();
		}else{
			//type: 1：个人相册 2：空间相册 3:爱的传承相册 4.家庭相册 5.往事相册（离婚之后系统自动生成，相册下面是子相册） 6:夫妻相册
			album = targetAlbumList.get(0);
			int type = album.getAlbumType();
			
	//		if(type != 1 && family.getIsMarried() == 3){
	//			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
	//			returnJSON.put("result", result);
	//			returnJSON.put("errorMsg", "您还未婚，只能上传照片到“个人相册”");
	//			return returnJSON.toString();
	//		}
			
			if(type == 1 || type == 5){
				if(album.getCreateUserId() != Integer.parseInt(userId)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您无权操作此相册");
					return returnJSON.toString();
				}
			}else{
				if(!userList.get(0).getFamilyId().equals(album.getFamilyId()) && !"4".equals(oper)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "您无权操作此相册");
					return returnJSON.toString();
				}
			}
		}
	*/	
		if("1".equals(oper)){
			if((photoUrl == null || "".equals(photoUrl)) && (videoUrl == null || "".equals(videoUrl))){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoUrl,videoUrl不能同时为空");
				return returnJSON.toString();
			}else if(albumType == null || "".equals(albumType)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "albumType不能为空");
				return returnJSON.toString();
			}
			String imgUrl= SystemConfig.getValue("img.http.url");
			String videoResUrl= SystemConfig.getValue("res.http.url");
			
			if(videoUrl != null && !"".equals(videoUrl)){
				//视频
				String[] videoUrls = videoUrl.replace(videoResUrl, "").split(",");
				String[] imgUrls = photoUrl.replace(imgUrl, "").split(",");
				int i=0;
				for(String url : videoUrls){
					
					TPhoto photo = new TPhoto();
					photo.setId(UUIDUtil.uuid());
					photo.setAlbumId(albumId);
					photo.setCreateDate(DateUtils.currentDate());
					photo.setCreateUserId(Integer.parseInt(userId));
					photo.setDescript(description);
					photo.setPhotoType("2");
					photo.setVideoUrl(url);
					photo.setPhotoUrl(imgUrls[i]);
					baseDAO.save(photo);
					
					i++ ;
				}
			}else{
				
				String[] imgUrls = photoUrl.replace(imgUrl, "").split(",");
				for(String url : imgUrls){
					
					TPhoto photo = new TPhoto();
					photo.setId(UUIDUtil.uuid());
					photo.setAlbumId(albumId);
					photo.setCreateDate(DateUtils.currentDate());
					photo.setCreateUserId(Integer.parseInt(userId));
					photo.setDescript(description);
					photo.setPhotoType("1");
					photo.setPhotoUrl(url);
					baseDAO.save(photo);
					
				}
			}
			
			if("4".equals(albumType)){
				//判断当前时间，是否在0-7点，如果在这个时间段，则不推送
				int hours = new Date().getHours();
				if(hours >= 7){
					
					//x1之家相册有更新，点击进入家庭相册查看
					TFamilyInfo info = baseDAO.get(TFamilyInfo.class, userList.get(0).getFamilyId());
					try {
						String content = "";
					//	String sql = "SELECT id FROM t_user u WHERE familyid = ? AND id != ?";
					//	List<Map<String, Object>> ids = baseDAO.getGenericBySQL(sql, new Object[]{userList.get(0).getFamilyId(), userId});
						
						
						
						String sql = "SELECT DISTINCT * FROM "+
								" (SELECT DISTINCT "+
								"  u.ID id"+
								" FROM "+
								"  t_user u "+
								" WHERE u.familyid = ? "+
								" ) temp "+
								"	UNION "+
								"	SELECT  * FROM "+
								"	  (SELECT DISTINCT "+
								"    u.ID id"+
								"  FROM "+
								"    t_home_info h,"+
								"    t_user u "+
								"  WHERE h.is_valid = 0 and u.familyid != ? "+
								"    AND  "+
								"    CASE "+
								"      WHEN h.is_private = '0' "+
								"      THEN h.create_user_id =u.FAMILYID "+
								"      ELSE h.create_user_id =u.ID "+
								"    END "+
								"    AND "+
								"    CASE "+
								"      WHEN h.invite_area = '1' "+
								"      THEN h.relation_user_id = u.FAMILYID "+
								"      ELSE h.relation_user_id = u.ID "+
								"    END ) t2";
						
						List<Map<String, Object>> ids =  baseDAO.getGenericBySQL(sql, new Object[]{userList.get(0).getFamilyId(),userList.get(0).getFamilyId()});
						
						if(ids != null && ids.size() > 0){
							String sql2 = "SELECT create_time FROM t_push_history WHERE send_user_id = ? AND recive_user_id = ? AND push_type = '1' order by create_time desc";
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String currentDate = sdf.format(new Date());
							content = info.getFamilyName()+"相册有更新，点击进入家庭相册查看";
							List<String> aliases = new ArrayList<String>();
							for(Map<String,Object> map : ids){
								if(userId.equals(map.get("id"))){
									//如果是自己，则跳过
									continue;
								}
								List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql2, new Object[]{userId, map.get("id")});
								
								if(list == null || list.size()==0){
									aliases.add(map.get("id")+"");  
								}else{
									String prePushTime = list.get(0).get("create_time").toString();
									long l = System.currentTimeMillis() - sdf.parse(prePushTime).getTime();
									if(l <= 4* 60 * 60 *1000){
										//间隔4小时
										log.info("推送间隔时间小于4小时，本次不推送");
									}else{
										boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(content);
										log.info("照片更新时，消息推送结果状态："+b);
										if(b){
											String sql_push = "INSERT INTO t_push_history(send_user_id, recive_user_id, push_type, create_time ) VALUES (?,?,?,?)";
											baseDAO.execteNativeBulk(sql_push, new Object[]{userId, map.get("id")+"", "1",currentDate});
										}
									}
								}
							}
						}
					} catch (Exception e) {
						log.info("照片更新时，消息推送异常："+e);
					}
				}
				
				else{
					log.info("当前时间不在推送的时间（0-7）范围内，不推送");
				}
				
			}
			
			
		}else if("2".equals(oper)){
			if(photoId == null || "".equals(photoId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoId为空");
				return returnJSON.toString();
			}
			String[] ids = photoId.split(",");
			for(String id : ids ){
				
				List<TPhoto> photoList = baseDAO.getGenericByHql("from TPhoto where id = ?", id);
				if(photoList == null || photoList.size() == 0){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "被删除内容不存在");
					return returnJSON.toString();
				}
				String albumSql = "from TAlbum where id = ? ";
				List<TAlbum> targetAlbumList = baseDAO.getGenericByHql(albumSql, photoList.get(0).getAlbumId());
				if(targetAlbumList == null || targetAlbumList.size() == 0){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "相册不存在");
					return returnJSON.toString();
				}else{
					TAlbum album = targetAlbumList.get(0);
					int type = album.getAlbumType();
					
					if(type == 1 || type == 5){
						if(album.getCreateUserId() != Integer.parseInt(userId)){
							returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "您无权操作此相册内容");
							return returnJSON.toString();
						}
					}else{
						if(!album.getFamilyId().equals(userList.get(0).getFamilyId())){
							returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "您无权操作此相册内容");
							return returnJSON.toString();
						}
					}
				}
				
				baseDAO.deleteObject(photoList.get(0));
			}
			
		}else if("3".equals(oper)){
			//移动
			if(photoId == null || "".equals(photoId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoId为空");
				return returnJSON.toString();
			}else if(targetAlbumId == null || "".equals(targetAlbumId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "targetAlbumId为空");
				return returnJSON.toString();
			}else if(targetAlbumId == null || "".equals(targetAlbumId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "targetAlbumId为空");
				return returnJSON.toString();
			}else if(albumType == null || "".equals(albumType)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "albumType不能为空");
				return returnJSON.toString();
			}else if(targetAlbumType == null || "".equals(targetAlbumType)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "targetAlbumType不能为空");
				return returnJSON.toString();
			}
			String[] ids = photoId.split(",");
			if(!"7".equals(albumType) && !"7".equals(targetAlbumType)){
				for(String id : ids ){
					
					TPhoto photo = baseDAO.get(TPhoto.class, id);
					if(photo != null){
						photo.setAlbumId(targetAlbumId);
						baseDAO.update(photo);
					}
				}
			}else if("7".equals(albumType) && "7".equals(targetAlbumType)){
				for(String id : ids ){
					
					TShopPhoto photo = baseDAO.get(TShopPhoto.class, id);
					if(photo != null){
						photo.setAlbumId(targetAlbumId);
						baseDAO.update(photo);
					}
				}
			}else if(!"7".equals(albumType) && "7".equals(targetAlbumType)){
				//普通相册移动到商家相册
				for(String id : ids ){
					
					TPhoto photo = baseDAO.get(TPhoto.class, id);
					if(photo != null){
						
						TShopPhoto sp = new TShopPhoto();
						sp.setAlbumId(targetAlbumId);
						sp.setCreateTime(photo.getCreateDate());
						sp.setDigNum(photo.getDigNum());
						sp.setDigRelationUserId(photo.getDigRelationUserId());
						sp.setId(UUIDUtil.uuid());
						sp.setPhotoUrl(photo.getPhotoUrl());
						baseDAO.save(sp);
						
						baseDAO.delete(TPhoto.class, id);
					}
				}
			}else if("7".equals(albumType) && !"7".equals(targetAlbumType)){
				//商家相册移动到普通相册
				for(String id : ids ){
					
					TShopPhoto photo = baseDAO.get(TShopPhoto.class, id);
					if(photo != null){
						
						TPhoto sp = new TPhoto();
						sp.setAlbumId(targetAlbumId);
						sp.setCreateDate(photo.getCreateTime());
						sp.setDigNum(photo.getDigNum());
						sp.setDigRelationUserId(photo.getDigRelationUserId());
						sp.setId(UUIDUtil.uuid());
						sp.setPhotoUrl(photo.getPhotoUrl());
						sp.setCreateUserId(Integer.parseInt(userId));
						sp.setDescript("");
						sp.setIsprivate(0);
						sp.setPhotoType("1");
						baseDAO.save(sp);
						
						baseDAO.delete(TShopPhoto.class, id);
					}
				}
			}
			
		}else if("4".equals(oper)){
			//复制
			if(photoId == null || "".equals(photoId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoId为空");
				return returnJSON.toString();
			}else if(targetAlbumId == null || "".equals(targetAlbumId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "targetAlbumId为空");
				return returnJSON.toString();
			}else if(albumType == null || "".equals(albumType)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "albumType不能为空");
				return returnJSON.toString();
			}else if(targetAlbumType == null || "".equals(targetAlbumType)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "targetAlbumType不能为空");
				return returnJSON.toString();
			}
			
			String[] ids = photoId.split(",");
			if(!"7".equals(albumType) && !"7".equals(targetAlbumType)){
				
				for(String id : ids ){
					
					TPhoto photo = baseDAO.get(TPhoto.class, id);
					if(photo != null){
						TPhoto target = new TPhoto(); 
						target.setAlbumId(targetAlbumId);
						target.setCreateDate(DateUtils.currentDate());
						target.setCreateUserId(Integer.parseInt(userId));
						target.setId(UUIDUtil.uuid());
						target.setPhotoType(photo.getPhotoType());
						target.setPhotoUrl(photo.getPhotoUrl());
						target.setVideoUrl(photo.getVideoUrl());
						baseDAO.save(target);
					}
				}
			}else  if("7".equals(albumType) && "7".equals(targetAlbumType)){
				
				for(String id : ids ){
					
					TShopPhoto photo = baseDAO.get(TShopPhoto.class, id);
					if(photo != null){
						TShopPhoto target = new TShopPhoto(); 
						target.setAlbumId(targetAlbumId);
						target.setCreateTime(DateUtils.currentDate());
						target.setDigNum(photo.getDigNum());
						target.setDigRelationUserId(photo.getDigRelationUserId());
						target.setId(UUIDUtil.uuid());
						target.setPhotoUrl(photo.getPhotoUrl());
						baseDAO.save(target);
					}
				}
				
			}else  if(!"7".equals(albumType) && "7".equals(targetAlbumType)){
				//普通相册复制到商家相册
				for(String id : ids ){
					
					TPhoto photo = baseDAO.get(TPhoto.class, id);
					if(photo != null){
						TShopPhoto target = new TShopPhoto(); 
						target.setAlbumId(targetAlbumId);
						target.setCreateTime(DateUtils.currentDate());
						target.setDigNum(photo.getDigNum());
						target.setDigRelationUserId(photo.getDigRelationUserId());
						target.setId(UUIDUtil.uuid());
						target.setPhotoUrl(photo.getPhotoUrl());
						baseDAO.save(target);
					}
				}
			}else  if("7".equals(albumType) && !"7".equals(targetAlbumType)){
				//商家相册复制到普通相册
				for(String id : ids ){
					
					TShopPhoto photo = baseDAO.get(TShopPhoto.class, id);
					if(photo != null){
						TPhoto sp = new TPhoto();
						sp.setAlbumId(targetAlbumId);
						sp.setCreateDate(DateUtils.currentDate());
						sp.setDigNum(photo.getDigNum());
						sp.setDigRelationUserId(photo.getDigRelationUserId());
						sp.setId(UUIDUtil.uuid());
						sp.setPhotoUrl(photo.getPhotoUrl());
						sp.setCreateUserId(Integer.parseInt(userId));
						sp.setDescript("");
						sp.setIsprivate(0);
						sp.setPhotoType("1");
						baseDAO.save(sp);
				}
			}
		}
		}
		result.put("succMsg", "操作成功！");	
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	
	}
}
