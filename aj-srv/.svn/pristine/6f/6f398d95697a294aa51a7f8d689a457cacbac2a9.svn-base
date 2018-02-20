package com.aj.photomgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.aj.msg.api.jpush.JPush;
import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;
import com.aj.photomgr.vo.TComment;
import com.aj.photomgr.vo.TPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.StringUtil;


/**
 * 照片评论管理
 * */

@Service("cmtMgr")
public class PhotoCommentManageService implements PublishService{

	private Logger log = Logger.getLogger(PhotoCommentManageService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String oper = params.optString("oper");
		String cmtType = params.optString("cmtType");
		String cmtId = params.optString("cmtId");
		String cmtContent = params.optString("cmtContent");
		String cmtTarget = params.optString("cmtTarget");
		String replyCmtId = params.optString("replyCmtId");
		
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
		if(cmtType == null || "".equals(cmtType)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "cmtType为空！");
			return returnJSON.toString();
		}
		if(cmtContent == null || "".equals(cmtContent)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "cmtContent为空！");
			return returnJSON.toString();
		}
		if("1".equals(oper)){
			TComment cmt = new TComment();
			cmt.setContent(cmtContent);
			cmt.setCreateDate(DateUtils.currentDate());
			cmt.setCreateUserId(Integer.parseInt(userId));
			if(replyCmtId == null || "".equals(replyCmtId)){
				//是否回复  1：否  0：是
				cmt.setIsReply(1);
			}else{
				cmt.setIsReply(0);
				//根据评论ID查询评论USERID
				String hsql = "from TComment where id = ?";
				List<TComment> quCmtList = baseDAO.getGenericByHql(hsql, Integer.parseInt(replyCmtId));
				if(quCmtList != null && quCmtList.size()>0){
					cmt.setReplyCommentId(replyCmtId);
					cmt.setReplyUserId(quCmtList.get(0).getCreateUserId());
				}
			}
			cmt.setPhotoId(cmtTarget);
			baseDAO.save(cmt);
			
			TPhoto photo = baseDAO.get(TPhoto.class, cmtTarget);
			String sql = "SELECT ID FROM t_user WHERE familyid = (SELECT FAMILY_ID FROM t_album WHERE id = ?)";
			List<Map<String, Object>> ids = baseDAO.getGenericBySQL(sql, new Object[]{photo.getAlbumId()});
			TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId));
			TAlbum album = baseDAO.get(TAlbum.class, photo.getAlbumId());
			String content = "";
			if(album.getAlbumType() == 3){
				//传承相册
				String babyName = "";
				if(album.getHadInherit() == null || album.getHadInherit()== 0){
					//未传承
					TChildrenInfo child = baseDAO.get(TChildrenInfo.class, album.getBabyUserId());
					babyName = child.getNickname();
				}else{
					String sql2 ="SELECT NICK_NAME nickName,USERTEL userTel  FROM t_user WHERE id = (SELECT inherit_target FROM  t_inherit_history WHERE child_user_id = ?)";
					List<Map<String, Object>> userList = baseDAO.getGenericBySQL(sql2, new Object[]{album.getBabyUserId()});
					if(userList != null && userList.size()>0){
						babyName = (userList.get(0).get("nickName") == null || "".equals(userList.get(0).get("nickName") == null) )? StringUtil.telTool(userList.get(0).get("userTel").toString()) : userList.get(0).get("nickName").toString();
					}
				}
				//推送x1对X2传承相册照片留下祈愿信:x3
				try {
					String sql1 = "SELECT CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE u.NICK_NAME END nickName FROM t_user u LEFT JOIN t_remark_info r ON u.ID = r.relation_id  AND r.create_user_id = ? AND r.type = '1' WHERE u.ID = ?";
					if(ids != null && ids.size() > 0){
						for(Map<String,Object> map : ids){
							List<Map<String, Object>> nickNameList = baseDAO.getGenericBySQL(sql1, new Object[]{map.get("ID")+"", userId});
							if(nickNameList != null && nickNameList.size()>0){
								String nick = nickNameList.get(0).get("nickName") == null?"":nickNameList.get(0).get("nickName").toString();
								if("".equals(nick)){
									nick = StringUtil.telTool(user.getUserTel());
								}
								content = nick+"对"+babyName+"传承相册照片留下祈愿信:"+cmtContent;
							}
							List<String> aliases = new ArrayList<String>();
							aliases.add(map.get("ID")+"");  
							boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(content);
							log.info("照片评论时，消息推送结果状态："+b);
						}
					}
				} catch (Exception e) {
					log.info("照片评论时，消息推送异常："+e);
				}
				
			}else{
				
				//推送
				try {
					String sql1 = "SELECT CASE WHEN r.remark IS NOT NULL THEN r.remark ELSE u.NICK_NAME END nickName FROM t_user u LEFT JOIN t_remark_info r ON u.ID = r.relation_id  AND r.create_user_id = ? AND r.type = '1' WHERE u.ID = ?";
					if(ids != null && ids.size() > 0){
						for(Map<String,Object> map : ids){
							List<Map<String, Object>> nickNameList = baseDAO.getGenericBySQL(sql1, new Object[]{map.get("ID")+"", userId});
							if(nickNameList != null && nickNameList.size()>0){
								String nick = nickNameList.get(0).get("nickName") == null?"":nickNameList.get(0).get("nickName").toString();
								if("".equals(nick)){
									nick = StringUtil.telTool(user.getUserTel());
								}
								content = nick+"对"+album.getAlbumName()+"照片进行了评论:"+cmtContent;
							}
							List<String> aliases = new ArrayList<String>();
							aliases.add(map.get("ID")+"");  
							boolean b = new JPush(JPushUserType.USER, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(content);
							log.info("照片评论时，消息推送结果状态："+b);
						}
					}
				} catch (Exception e) {
					log.info("照片评论时，消息推送异常："+e);
				}
			}
			
			
			
			result.put("succMsg", "评论成功！");	
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}else if("2".equals(oper)){
			if(cmtId == null || "".equals(cmtId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "cmtId为空！");
				return returnJSON.toString();
			}
			String hsql = "from TComment where id = ?";
			List<TComment> cmtList = baseDAO.getGenericByHql(hsql, Integer.parseInt(cmtId));
			if(cmtList == null || cmtList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "没有对应评论！");
				return returnJSON.toString();
			}else{
				TComment cmt = cmtList.get(0);
				cmt.setContent(cmtContent);
				baseDAO.update(cmt);
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "修改成功！");
				return returnJSON.toString();
			}
		}else if("3".equals(oper)){
			if(cmtId == null || "".equals(cmtId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "cmtId为空！");
				return returnJSON.toString();
			}
			String hsql = "from TComment where id = ?";
			List<TComment> cmtList = baseDAO.getGenericByHql(hsql, Integer.parseInt(cmtId));
			if(cmtList == null || cmtList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "没有对应评论！");
				return returnJSON.toString();
			}else{
				String sql = "SELECT c.id  FROM t_comment c,t_photo p  WHERE c.PHOTO_ID = p.ID AND c.id = ? AND ( c.CREATE_USER_ID = ? OR p.CREATE_USER_ID = ? ) ";
				List<Map<String, Object>> list = baseDAO.query(sql, cmtId, userId,userId);
				if(list == null || list.size() ==0){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "你无权删除该评论！");
					return returnJSON.toString();
				}else{
					sql = "delete from TComment where id = ?";
					baseDAO.deleteObject(cmtList.get(0));
					result.put("succMsg", "删除评论成功！");
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
