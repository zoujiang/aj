package com.aj.albummgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.photomgr.vo.TPhoto;
import com.aj.shop.vo.TShopAlbum;
import com.aj.shop.vo.TShopCustomerUser;
import com.aj.shop.vo.TShopDynamicAlbum;
import com.aj.shop.vo.TShopPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 相册管理
 * */

@Service("albumMgr")
public class AlbumManageService implements PublishService{

	private Logger log = Logger.getLogger(AlbumManageService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String oper = params.optString("oper");  /*1：添加2：修改3：删除 4:复制 5:移动所有照片*/
		String albumType = params.optString("albumType");  /*1：个人相册 2：空间相册 3:爱的传承相册 4.家庭相册  */
		String userId = params.optString("userId");
		String albumId = params.optString("albumId");
		String albumName = params.optString("albumName");
		String albumDesc = params.optString("albumDesc");
		String albumUrl = params.optString("albumUrl");
		String albumCategory = params.optString("albumCategory");
		String targetAlbumId = params.optString("targetAlbumId");
		String targetAlbumType = params.optString("targetAlbumType");
		String visibleProperty = params.optString("visibleProperty");
		
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
		}else{
			if("1".equals(oper)){
				if(albumName == null || "".equals(albumName)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "albumName为空！");
					return returnJSON.toString();
				}
				if(albumUrl == null || "".equals(albumUrl)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "albumUrl为空！");
					return returnJSON.toString();
				}
				if(albumCategory == null || "".equals(albumCategory)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "albumCategory为空！");
					return returnJSON.toString();
				}
			}else if("2".equals(oper)){
				if(albumId == null || "".equals(albumId)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "albumId为空！");
					return returnJSON.toString();
				}
				if(visibleProperty == null || "".equals(visibleProperty)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "visibleProperty为空！");
					return returnJSON.toString();
				}
			}else if("3".equals(oper)){
				if(albumId == null || "".equals(albumId)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "albumId为空！");
					return returnJSON.toString();
				}
				
			}else if("5".equals(oper)){
				if(targetAlbumType == null || "".equals(targetAlbumType)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "targetAlbumType为空！");
					return returnJSON.toString();
				}
			}
		}
		String hsql = " from TUser where id = ? ";
		List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if(userList == null || userList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有该用户！");
			return returnJSON.toString();
		}else{
			TUser user = userList.get(0);
			//查询家庭信息，是否已婚
			TFamilyInfo family = baseDAO.get(TFamilyInfo.class, user.getFamilyId());
			TAlbum album = new TAlbum();
			if("1".equals(oper)){
				if(albumType == null || "".equals(albumType)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "albumType为空！");
					return returnJSON.toString();
				}
				//查询相册名称是否存在
				String queryHsql = "from TAlbum where albumName = ? and createUserId = ? and albumType = ?";
				List<TAlbum> albumList = baseDAO.getGenericByHql(queryHsql, albumName, Integer.parseInt(userId), Integer.parseInt(albumType));
				if(albumList != null && albumList.size() > 0){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "相册已经存在！");
					return returnJSON.toString();
				}
				album.setId(UUIDUtil.uuid());
				album.setAlbumName(albumName);
				album.setDescription(albumDesc);
				album.setCreateUserId(Integer.parseInt(userId));
				album.setOwnerUserId(Integer.parseInt(userId));
				album.setCreateDate(DateUtils.currentDate());
				String imgUrl= SystemConfig.getValue("img.http.url");
				album.setAlbumUrl(albumUrl.replace(imgUrl, ""));
				album.setIsSysinit(1); //非系统相册
				album.setAlbumType(Integer.parseInt(albumType));
				album.setVisibleProperty(Integer.parseInt(visibleProperty));
				album.setIsDir(0);
				album.setCategory(albumCategory);
				if("6".equals(albumType)){
					//如果是家庭相册，那么给相册设置一个家庭ID
					if(family.getIsMarried() == 3){
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "您还未婚，不能创建“夫妻相册”");
						return returnJSON.toString();
					}
				}
				if("1".equals(albumType)||"5".equals(albumType)){
					//个人可见的相册， 去掉相册的familyid值
					album.setFamilyId("");
				}else{
					//夫妻相册或者家庭相册，设置familyid
					album.setFamilyId(user.getFamilyId());
				}
				
				baseDAO.save(album);
				
				result.put("succMsg", "添加相册成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}else if("2".equals(oper)){
				if(!"7".equals(albumType) && !"8".equals(albumType)){
					
					String hsql1 = "from TAlbum where id = ? ";
					List<TAlbum> albumList = baseDAO.getGenericByHql(hsql1, albumId);
					if(albumList != null && albumList.size() >0){
						album = albumList.get(0);
						if(albumName != null && !"".equals(albumName)){
							album.setAlbumName(albumName);
						}
						if(albumDesc != null && !"".equals(albumDesc)){
							album.setDescription(albumDesc);
						}
						if(albumUrl != null && !"".equals(albumUrl)){
							String imgUrl= SystemConfig.getValue("img.http.url");
							album.setAlbumUrl(albumUrl.replace(imgUrl, ""));
						}
						if(albumCategory != null && !"".equals(albumCategory)){
							album.setCategory(albumCategory);
						}
						if(albumType != null && !"".equals(albumType)){
							album.setAlbumType(Integer.parseInt(albumType));
							if("1".equals(albumType)||"5".equals(albumType)){
								//个人可见的相册， 去掉相册的familyid值
								album.setFamilyId("");
							}else{
								//夫妻相册或者家庭相册，设置familyid
								album.setFamilyId(user.getFamilyId());
							}
						}
						album.setVisibleProperty(Integer.parseInt(visibleProperty));
						baseDAO.saveOrUpdate(album);
						result.put("succMsg", "修改相册成功！");
						returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "");
					}else{
						returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "没有对应相册");
					}
				}else{
					//商户相册，包含普通相册和动感影集
					if("7".equals(albumType)){
						//普通相册
						TShopAlbum sa = baseDAO.get(TShopAlbum.class, albumId);
						
						TShopCustomerUser tcu =  baseDAO.get(TShopCustomerUser.class, sa.getUserId());
						if(tcu != null && !tcu.getUserId().equals(userId)){
							//相册拥有者不是当前登陆用户，不能编辑相册类型
							if(visibleProperty != null && !visibleProperty.equals(sa.getVisibleProperty())){
								result.put("succMsg", "");
								returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
								returnJSON.put("result", result);
								returnJSON.put("errorMsg", "您不能编辑相册类型");
								return returnJSON.toString();
							}
						}
						
						if(albumName != null && !"".equals(albumName)){
							sa.setAlbumName(albumName);
						}
						if(albumDesc != null && !"".equals(albumDesc)){
							sa.setDescription(albumDesc);
						}
						if(albumUrl != null && !"".equals(albumUrl)){
							String imgUrl= SystemConfig.getValue("img.http.url");
							sa.setAlbumLogo(albumUrl.replace(imgUrl, ""));
						}
						sa.setVisibleProperty(Integer.parseInt(visibleProperty));
						baseDAO.saveOrUpdate(sa);

					}else if("8".equals(albumType)){
						//动感影集
						TShopDynamicAlbum sa = baseDAO.get(TShopDynamicAlbum.class, albumId);
						
						TShopCustomerUser tcu =  baseDAO.get(TShopCustomerUser.class, sa.getUserId());
						if(tcu != null && !tcu.getUserId().equals(userId)){
							//相册拥有者不是当前登陆用户，不能编辑相册类型
							if(visibleProperty != null && !visibleProperty.equals(sa.getVisibleProperty())){
								result.put("succMsg", "");
								returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
								returnJSON.put("result", result);
								returnJSON.put("errorMsg", "您不能编辑相册类型");
								return returnJSON.toString();
							}
						}
						
						if(albumName != null && !"".equals(albumName)){
							sa.setAlbumName(albumName);
						}
						if(albumDesc != null && !"".equals(albumDesc)){
							sa.setDescription(albumDesc);
						}
						if(albumUrl != null && !"".equals(albumUrl)){
							String imgUrl= SystemConfig.getValue("img.http.url");
							sa.setAlbumLogo(albumUrl.replace(imgUrl, ""));
						}
						sa.setVisibleProperty(Integer.parseInt(visibleProperty));
						baseDAO.saveOrUpdate(sa);
					}
					result.put("succMsg", "修改相册成功！");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
				}
				return returnJSON.toString();
			}else if("3".equals(oper)){
				String msg = "";
				if(!"7".equals(albumType) && !"8".equals(albumType)){
					int i = 0;
					int j = 0;
					for(String amId : albumId.split(",")){
						
						String hsql1 = "from TAlbum where id = ? and (createUserId = ? or familyId =? )";
						List<TAlbum> albumList = baseDAO.getGenericByHql(hsql1, amId, Integer.parseInt(userId), user.getFamilyId());
						if(albumList != null && albumList.size() >0){
							i = albumList.size();
							album = albumList.get(0);
						//	if(album.getIsSysinit() != 0){
								j ++;
								//删除改相册下所有照片
								try {
									hsql1 = "delete from TPhoto where albumId = '"+album.getId()+"'";
									baseDAO.deleteByHsql(hsql1);
									//删除相册
									baseDAO.deleteObject(album);
								} catch (Exception e) {
									log.info("删除相册时异常："+e.getMessage());
									e.printStackTrace();
								}
						//	}
						}
					}
					
					if(i - j == 0){
						msg = "删除成功";
					}else{
						msg = "共删除"+i+"个相册，成功删除"+j+"个相册，其中系统初始化的相册不可删除";
					}
				}else{
					//商户相册，包含普通相册和动感影集
					if("7".equals(albumType)){
						//普通相册
						baseDAO.delete(TShopAlbum.class, albumId);
						baseDAO.execteNativeBulk("delete from t_shop_photo where album_id = ? ", albumId);

					}else if("8".equals(albumType)){
						//动感影集
						baseDAO.delete(TShopDynamicAlbum.class, albumId);
						baseDAO.execteNativeBulk("delete from t_shop_photo where album_id = ? ", albumId);
					}
					msg = "删除成功";
				}
				result.put("succMsg", msg);
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}else if("4".equals(oper)){
				if(targetAlbumId == null || "".equals(targetAlbumId)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "targetAlbumId为空");
					return returnJSON.toString();
				}else{
					baseDAO.execteNativeBulk("update t_photo set ALBUM_ID = ? where ALBUM_ID = ?", targetAlbumId, albumId);
					result.put("succMsg", "移动成功");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					return returnJSON.toString();
				}
			}else if("5".equals(oper)){
				if(!"7".equals(albumType)){
					//普通相册
					if(!"7".equals(targetAlbumType)){
						//移动到的目标相册也是普通相册
						String sql = "update t_photo set album_id = ? where album_id = ?";
						baseDAO.execteNativeBulk(sql, targetAlbumType, albumType);
					}
					//普通相册照片不能移动到商家相册
				}else{
					if(!"7".equals(targetAlbumType)){
						//商家相册照片移动到普通相册
						hsql = "from TShopPhoto where albumId = ?";
						List<TShopPhoto> shopPhotoList = baseDAO.getGenericByHql(hsql, albumId);
						for(TShopPhoto sp : shopPhotoList){
							TPhoto photo = new TPhoto();
							photo.setAlbumId(targetAlbumId);
							photo.setCreateDate(DateUtils.currentDate());
							photo.setCreateUserId(Integer.parseInt(userId));
							photo.setDigNum(sp.getDigNum());
							photo.setDigRelationUserId(sp.getDigRelationUserId());
							photo.setId(UUIDUtil.uuid());
							photo.setPhotoUrl(sp.getPhotoUrl());
							photo.setPhotoType("1");
							baseDAO.save(photo);
							baseDAO.deleteObject(sp);
						}
						
						
					}else if("7".equals(targetAlbumType)){
						//商家相册移动到商家相册
						
						String sql = "update t_shop_photo set album_id = ? where album_id = ?";
						baseDAO.execteNativeBulk(sql, targetAlbumType, albumType);
					}
				}
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "移动成功");
				return returnJSON.toString();
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "无效的操作类型！");
				return returnJSON.toString();
			}
		}
		
	}

}
