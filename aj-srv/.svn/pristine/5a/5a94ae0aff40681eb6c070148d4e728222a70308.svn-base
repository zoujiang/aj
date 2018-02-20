package com.aj.praylettermgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.aj.photomgr.vo.TPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 爱的传承相册管理
 * */

@Service("babyPhotosMgr")
public class BabyPhotosMgrService implements PublishService{

	private Logger log = Logger.getLogger(BabyPhotosMgrService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String childId = params.optString("childId");  
		String oper = params.optString("oper");
		String age = params.optString("age");
		String photoId = params.optString("photoId");
		String photoUrl = params.optString("photoUrl");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(childId == null || "".equals(childId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "childId为空！");
			return returnJSON.toString();
		}
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}
		if(age == null || "".equals(age)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "age为空！");
			return returnJSON.toString();
		}
		String imgUrl= SystemConfig.getValue("img.http.url");
		if("1".equals(oper)){
			//新增
			if(photoUrl == null || "".equals(photoUrl)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoUrl为空！");
				return returnJSON.toString();
			}
			//查询孩子信息
			TChildrenInfo child = baseDAO.get(TChildrenInfo.class, childId);
			int  year = Integer.parseInt(DateUtils.formatBirthdat2Age(child.getBirthday()));
			if(year < Integer.parseInt(age.substring(0, age.indexOf("-")))){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您只能添加"+(year+1)+"岁以下的照片！");
				return returnJSON.toString();
			}
			
			String hsql = "from TAlbum where  albumType = ? and babyUserId = ? ";
			List<TAlbum> albumList = baseDAO.getGenericByHql(hsql, 3, childId);
			if(albumList == null || albumList.size() == 0){
				TAlbum album = new TAlbum();
				String id = UUIDUtil.uuid();
				album.setId(id);
				album.setCreateUserId(Integer.parseInt(userId));
				album.setAlbumName("爱的传承");
				album.setAlbumType(3);
				album.setOwnerUserId(Integer.parseInt(userId));
				album.setCreateDate(DateUtils.currentDate());
				album.setIsSysinit(1);
				album.setVisibleProperty(2);
				baseDAO.save(album);
				
				TPhoto photo = new TPhoto();
				photo.setId(UUIDUtil.uuid());
				photo.setBabyId(childId);
				photo.setAge(age);
				photo.setAlbumId(id);
				photo.setCreateDate(DateUtils.currentDate());
				photo.setCreateUserId(Integer.parseInt(userId));
				photo.setPhotoUrl(photoUrl.replace(imgUrl, ""));
				baseDAO.save(photo);
			}else{
				String albumId = albumList.get(0).getId();
				TPhoto photo = new TPhoto();
				photo.setId(UUIDUtil.uuid());
				photo.setBabyId(childId);
				photo.setAge(age);
				photo.setAlbumId(albumId);
				photo.setCreateDate(DateUtils.currentDate());
				photo.setCreateUserId(Integer.parseInt(userId));
				photo.setPhotoUrl(photoUrl.replace(imgUrl, ""));
				baseDAO.save(photo);
			}
			result.put("succMsg", "上传成功！");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}else if("2".equals(oper)){
			//修改
			if(photoUrl == null || "".equals(photoUrl)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoUrl为空！");
				return returnJSON.toString();
			}
			if(photoId == null || "".equals(photoId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoId为空！");
				return returnJSON.toString();
			}
			String hsql = "from TPhoto where  id = ?  and babyId = ?";
			List<TPhoto> photoList = baseDAO.getGenericByHql(hsql, photoId, childId);
			if(photoList == null || photoList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "无权修改！");
				return returnJSON.toString();
			}else{
				TPhoto photo = photoList.get(0);
				photo.setPhotoUrl(photoUrl.replace(imgUrl, ""));
				baseDAO.update(photo);
				result.put("succMsg", "修改成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}
		
		}else if("3".equals(oper)){
			//删除
			if(photoId == null || "".equals(photoId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoId为空！");
				return returnJSON.toString();
			}
			TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
			String sql = "SELECT p.ID FROM t_photo p, t_album a WHERE p.ALBUM_ID = a.ID AND p.ID = ? AND a.FAMILY_ID =? AND p.BABY_ID = ?";
			List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{photoId, user.getFamilyId(), childId});
			if(photoList == null || photoList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "无权删除！");
				return returnJSON.toString();
			}else{
				baseDAO.execteNativeBulk("delete from t_photo where id =  ?", new Object[]{photoId});
				result.put("succMsg", "删除成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "无效的操作类型");
			return returnJSON.toString();
		}
	}

}
