package com.aj.childrenmgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.task.service.ThreadPoolTaskService;
import com.util.AJNoUtil;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 儿女管理
 * */

@Service("childrenMgr")
public class ChildrenManageService implements PublishService{

	private Logger log = Logger.getLogger(ChildrenManageService.class);
	
	@Resource
	private GenericDAO baseDAO;
	@Resource
	private ThreadPoolTaskService threadPoolTaskService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String oper = params.optString("oper");  /*1：添加2：修改3：删除*/
		String childrenId = params.optString("childrenId");
		String userId = params.optString("userId");
		String photoUrl = params.optString("photo");
		String nickName = params.optString("nickName");
		String trueName = params.optString("trueName");
		String sex = params.optString("sex");
		String birthday = params.optString("birthday");
		String phoneNo = params.optString("phoneNo");
		String ajno = params.optString("ajno");
		String callId = params.optString("callId");
		String description = params.optString("description");
		String isPrivate = params.optString("isPrivate");
		String babyId = params.optString("babyId");
		String fixSortName = params.optString("fixSortName");
		String fixCallName = params.optString("fixCallName");
		
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
			if("2".equals(oper) || "3".equals(oper)){
				if(childrenId == null || "".equals(childrenId)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "childrenId为空！");
					return returnJSON.toString();
				}
			}else{
				
				
				if(fixSortName == null || "".equals(fixSortName)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "fixSortName为空！");
					return returnJSON.toString();
				}
				if(fixCallName == null || "".equals(fixCallName)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "fixCallName为空！");
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
			if("1".equals(oper)){
				
				TChildrenInfo child = new TChildrenInfo();
				child.setAjno(new AJNoUtil(baseDAO).queryPAJNoSeq("PNO"));
				String imgUrl= SystemConfig.getValue("img.http.url");
				child.setPhotoUrl(photoUrl.replace(imgUrl, ""));
				child.setNickname(nickName);
				child.setTruename(trueName);
				child.setSex(sex);
				child.setBirthday(birthday);
				child.setCreateDate(DateUtils.currentDate());
				child.setCreateUserId(userId);
				child.setFamilyId(user.getFamilyId());
				child.setId(UUIDUtil.uuid());
				child.setIsPrivate(isPrivate);
				child.setBabyId(babyId);
				child.setFixSortName(fixSortName);
				child.setFixCallName(fixCallName);
				Object pk = baseDAO.save(child);
				
				//创建爱的传承相册
				TAlbum album = new TAlbum();
				album.setId(UUIDUtil.uuid());
				album.setAlbumName("爱的传承");
				album.setAlbumType(3);
				album.setCreateDate(DateUtils.currentDate());
				album.setCreateUserId(user.getId());
				album.setFamilyId(user.getFamilyId());
				album.setHadInherit(0);
				album.setIsDir(0);
				album.setIsSysinit(1);
				album.setOwnerUserId(Integer.parseInt(userId));
				album.setBabyUserId(pk.toString());
				album.setAlbumUrl( SystemConfig.getValue("chuancheng.url"));
				album.setVisibleProperty(2);
				baseDAO.save(album);
				
				
				result.put("succMsg", "添加孩子成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}else if("2".equals(oper)){
				//修改，暂时只对无亲脉号的儿女修改
				String hsql1 = "from TChildrenInfo where id = ? ";
				List<TChildrenInfo> childrenList = baseDAO.getGenericByHql(hsql1, childrenId);
				if(childrenList != null && childrenList.size() >0){
					TChildrenInfo child = childrenList.get(0);
					if(nickName != null && !"".equals(nickName)){
						child.setNickname(nickName);
					}
					if(photoUrl != null && !"".equals(photoUrl)){
						String imgUrl= SystemConfig.getValue("img.http.url");
						child.setPhotoUrl(photoUrl.replace(imgUrl, ""));
					}
					if(trueName != null && !"".equals(trueName)){
						child.setTruename(trueName);
					}
					if(birthday != null && !"".equals(birthday)){
						child.setBirthday(birthday);
					}
					if(sex != null && !sex.equals(child.getSex()) ){
						child.setSex(sex);
					}
					if(isPrivate != null && !"".equals(isPrivate)){
						child.setIsPrivate(isPrivate);
						child.setCreateUserId(userId);
						child.setFamilyId(user.getFamilyId());
					}
					baseDAO.update(child);
					result.put("succMsg", "修改儿女成功！");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
				}else{
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "没有对应儿女");
				}
				return returnJSON.toString();
			}else if("3".equals(oper)){
				
				String hsql1 = "from TChildrenInfo where id = ? and familyId = ? ";
				List<TChildrenInfo> childList = baseDAO.getGenericByHql(hsql1, childrenId,  userList.get(0).getFamilyId());
				if(childList != null && childList.size() >0){
					TChildrenInfo child = childList.get(0);
					//删除爱的传承相册
					baseDAO.execteNativeBulk("delete from t_album  where ALBUM_TYPE = 3 and BABY_USER_ID = '"+child.getId()+"'");
					baseDAO.deleteObject(child);
						
					result.put("succMsg", "删除儿女成功！");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					
				}else{
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "没有对应儿女");
				}
				
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
