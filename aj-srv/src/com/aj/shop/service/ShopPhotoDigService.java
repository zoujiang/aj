package com.aj.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.msg.api.jpush.JPush;
import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;
import com.aj.photomgr.vo.TPhoto;
import com.aj.shop.vo.TShopAlbum;
import com.aj.shop.vo.TShopPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.StringUtil;


/**
 * 照片赞或取消赞
 * */

@Service("shopPhotoDig")
public class ShopPhotoDigService implements PublishService{

	private Logger log = Logger.getLogger(ShopPhotoDigService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String oper = params.optString("oper");
		String photoId = params.optString("photoId");
		
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
		if(photoId == null || "".equals(photoId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "photoId为空！");
			return returnJSON.toString();
		}
		TShopPhoto photo = baseDAO.get(TShopPhoto.class, photoId);
		if(photo == null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有对应照片！");
			return returnJSON.toString();
		}
		if("0".equals(oper)){
			//赞
			//判断是否已经点赞
			if(photo.getDigRelationUserId() != null && !"".equals(photo.getDigRelationUserId())){
				for(String digedUserId : photo.getDigRelationUserId().split(",")){
					if(userId.equals(digedUserId)){
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "您已经赞过！");
						return returnJSON.toString();
					}
				}
				photo.setDigRelationUserId(userId +","+photo.getDigRelationUserId());
			}else{
				photo.setDigRelationUserId(userId);
				photo.setDigNum(null);
			}
			if(photo.getDigNum() == null){
				photo.setDigNum( 1);
			}else{
				photo.setDigNum(photo.getDigNum() + 1);
			}
	//		String sql = "SELECT ID FROM t_user WHERE familyid = (SELECT FAMILY_ID FROM t_album WHERE id = ?)";
	//		List<Map<String, Object>> ids = baseDAO.getGenericBySQL(sql, new Object[]{photo.getAlbumId()});
			String sql = "SELECT ID FROM t_user WHERE familyid = ( select familyid from t_user where id =  (select user_id from  t_shop_customer_user where id = (select user_id from t_shop_album where id = ?)))";
			List<Map<String, Object>> ids = baseDAO.getGenericBySQL(sql, new Object[]{photo.getAlbumId()});
			
			TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
			TShopAlbum album = baseDAO.get(TShopAlbum.class, photo.getAlbumId());
			//推送
			try {
				String content = "";
				String sql1 = "SELECT CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE u.NICK_NAME END nickName FROM t_user u LEFT JOIN t_remark_info r ON u.ID = r.relation_id  AND r.create_user_id = ? AND r.type = '1' WHERE u.ID = ?";
				if(ids != null && ids.size() > 0){
					for(Map<String,Object> map : ids){
						List<Map<String, Object>> nickNameList = baseDAO.getGenericBySQL(sql1, new Object[]{map.get("ID")+"", userId});
						if(nickNameList != null && nickNameList.size()>0){
							String nick = nickNameList.get(0).get("nickName") == null?"":nickNameList.get(0).get("nickName").toString();
							if("".equals(nick)){
								nick = StringUtil.telTool(user.getUserTel());
							}
							content = nick+"对"+album.getAlbumName()+"照片进行了点赞";
						}
						List<String> aliases = new ArrayList<String>();
						aliases.add(map.get("ID")+"");  
						boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(content);
						log.info("照片点赞时，消息推送结果状态："+b);
					}
				}
			} catch (Exception e) {
				log.info("照片点赞时，消息推送异常："+e);
			}
			
			baseDAO.update(photo);
			result.put("succMsg", "点赞成功！");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
			
		}else if("1".equals(oper)){
			//取消赞
			String digUserIds = photo.getDigRelationUserId();
			if(digUserIds == null || "".equals(digUserIds)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "操作失败，您还没有点赞！");
				return returnJSON.toString();
			}else{
				boolean b = false;
				String newUserIds = "";
				for(String uId : digUserIds.split(",")){
					if(userId.equals(uId.trim())){
						b = true;
					}else{
						newUserIds += ","+uId.trim();
					}
				}
				if(!b){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "操作失败，您还没有点赞！");
					return returnJSON.toString();
				}else{
					photo.setDigNum(photo.getDigNum() - 1);
					if("".equals(newUserIds)){
						photo.setDigRelationUserId("");
					}else{
						photo.setDigRelationUserId(newUserIds.substring(1));
					}
					baseDAO.update(photo);
					result.put("succMsg", "取消点赞成功！");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					return returnJSON.toString();
				}
				
			}
			
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "没有对应操作类型！");
			return returnJSON.toString();
		}
	}

}
