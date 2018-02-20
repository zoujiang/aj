package com.aj.albummgr.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.photomgr.vo.TPhoto;
import com.aj.shop.vo.TShopAlbum;
import com.aj.shop.vo.TShopDynamicAlbum;
import com.aj.shop.vo.TShopPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;

import net.sf.json.JSONObject;


/**
 * 相册传递， 传递给他人， 包括普通相册和商家相册
 * */

@Service("albumTransmit")
public class AlbumTransmitService implements PublishService{

	private Logger log = Logger.getLogger(AlbumTransmitService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String albumId = params.optString("albumId");
		String albumType = params.optString("albumType");
		String phoneNo = params.optString("phoneNo");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		
		if(albumId == null || "".equals(albumId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "albumId为空！");
			return returnJSON.toString();
		}
		if(albumType == null || "".equals(albumType)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "albumType为空！");
			return returnJSON.toString();
		}
		if(phoneNo == null || "".equals(phoneNo)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "phoneNo为空！");
			return returnJSON.toString();
		}
		String hsql = " from TUser where id = ? ";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该用户！");
			return returnJSON.toString();
		}else{
			hsql = " from TUser where userTel = ?";
			List<TUser> targetUserList = baseDAO.getGenericByHql(hsql, phoneNo);
			if(targetUserList == null || targetUserList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "该号码未注册亲脉，请注册亲脉后再试");
				return returnJSON.toString();
			}else{
				TUser targerUser = targetUserList.get(0);
				if(targerUser.getId() == Integer.parseInt(userId)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "不能传承给自己");
					return returnJSON.toString();
				}
				//为该用户创建一个个人可见属性的相册
				TAlbum album = new TAlbum();
				if(albumType.equals("7") || albumType.equals("8")){
					String newAlbumId = UUIDUtil.uuid();
					String date = DateUtils.currentDate();
					if(albumType.equals("7")){
						
						TShopAlbum shopAlbum = baseDAO.get(TShopAlbum.class, albumId);
						if(shopAlbum == null){
							returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "相册不存在");
							return returnJSON.toString();
						}else{
							album.setAlbumName(shopAlbum.getAlbumName());
							album.setAlbumType(1);
							album.setAlbumUrl(shopAlbum.getAlbumLogo());
							album.setCategory("1");
							album.setCreateDate(date);
							album.setCreateUserId(targerUser.getId());
							album.setDescription(shopAlbum.getDescription());
							album.setId(newAlbumId);
							album.setIsDir(0);
							album.setVisibleProperty(0);
							album.setOwnerUserId(targerUser.getId());
							album.setIsSysinit(1);
							baseDAO.save(album);
							
						}
					}else{
						TShopDynamicAlbum shopAlbum = baseDAO.get(TShopDynamicAlbum.class, albumId);
						if(shopAlbum == null){
							returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
							returnJSON.put("result", result);
							returnJSON.put("errorMsg", "相册不存在");
							return returnJSON.toString();
						}else{
							
							album.setAlbumName(shopAlbum.getAlbumName());
							album.setAlbumType(1);
							album.setAlbumUrl(shopAlbum.getAlbumLogo());
							album.setCategory("1");
							album.setCreateDate(date);
							album.setCreateUserId(targerUser.getId());
							album.setDescription(shopAlbum.getDescription());
							album.setId(newAlbumId);
							album.setIsDir(0);
							album.setVisibleProperty(0);
							album.setOwnerUserId(targerUser.getId());
							album.setIsSysinit(1);
							baseDAO.save(album);
						}
					}
					//设置照片信息
					List<TShopPhoto> photoList =  baseDAO.getGenericByHql(" from TShopPhoto where albumId = ?", albumId);
					if(photoList != null && photoList.size() > 0){
						for(TShopPhoto sPhoto : photoList){
							TPhoto photo = new TPhoto();
							photo.setAlbumId(newAlbumId);
							photo.setCreateDate(date);
							photo.setCreateUserId(targerUser.getId());
							photo.setId(UUIDUtil.uuid());
							photo.setPhotoType("1");
							photo.setPhotoUrl(sPhoto.getPhotoUrl());
							baseDAO.save(photo);
						}
					}
				}else{
					//普通相册
					TAlbum orgiAlbum = baseDAO.get(TAlbum.class, albumId);
					if(orgiAlbum == null){
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "相册不存在");
						return returnJSON.toString();
					}else{
						
						String newAlbumId = UUIDUtil.uuid();
						String date = DateUtils.currentDate();
						album.setAlbumName(orgiAlbum.getAlbumName());
						album.setAlbumType(1);
						album.setAlbumUrl(orgiAlbum.getAlbumUrl());
						album.setCategory(orgiAlbum.getCategory());
						album.setCreateDate(date);
						album.setCreateUserId(targerUser.getId());
						album.setDescription(orgiAlbum.getDescription());
						album.setId(newAlbumId);
						album.setIsDir(0);
						album.setVisibleProperty(0);
						album.setOwnerUserId(targerUser.getId());
						album.setIsSysinit(1);
						baseDAO.save(album);
						
						//设置照片信息
						List<TPhoto> photoList =  baseDAO.getGenericByHql(" from TPhoto where albumId = ?", albumId);
						if(photoList != null && photoList.size() > 0){
							for(TPhoto photo : photoList){
								TPhoto newPhoto = new TPhoto();
								newPhoto.setAlbumId(newAlbumId);
								newPhoto.setCreateDate(date);
								newPhoto.setCreateUserId(targerUser.getId());
								newPhoto.setId(UUIDUtil.uuid());
								newPhoto.setPhotoType(photo.getPhotoType());
								newPhoto.setPhotoUrl(photo.getPhotoUrl());
								newPhoto.setVideoUrl(photo.getVideoUrl());
								baseDAO.save(newPhoto);
							}
						}
					}
				}
			}
		}
		result.put("succMsg", "操作成功");
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}
