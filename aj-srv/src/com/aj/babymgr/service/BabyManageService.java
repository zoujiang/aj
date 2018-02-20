package com.aj.babymgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.babymgr.vo.TBabyInfo;
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
 * 胎儿管理
 * */

@Service("babyMgr")
public class BabyManageService implements PublishService{

	private Logger log = Logger.getLogger(BabyManageService.class);
	
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
		String babyId = params.optString("babyId");
		String userId = params.optString("userId");
		String nickName = params.optString("nickName");
		String conceptionDate = params.optString("conceptionDate");
		String bornDate = params.optString("bornDate");
		String photo = params.optString("photo");
		String bbNumber = params.optString("bbNumber");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(nickName == null || "".equals(nickName)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "nickName为空！");
			return returnJSON.toString();
		}
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}else{
			if("2".equals(oper) || "3".equals(oper)){
				if(babyId == null || "".equals(babyId)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "babyId为空！");
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
			TBabyInfo baby = new TBabyInfo();
			if("1".equals(oper)){
				baby.setId(UUIDUtil.uuid());
				baby.setNickname(nickName);
				baby.setConceptionDate(conceptionDate);
				baby.setExpectedDate(bornDate);
				String imgUrl= SystemConfig.getValue("img.http.url");
				baby.setPhotoUrl(photo.replace(imgUrl, ""));
				baby.setFamilyId(user.getFamilyId());
				baby.setAjno(new AJNoUtil(baseDAO).queryPAJNoSeq("TBNO"));
				baby.setCreateDate(DateUtils.currentDate());
				baby.setCreateUserId(userId);
				baby.setIsPrivate("0");
				if(bbNumber == null || "".equals(bbNumber)){
					baby.setBbNumber(1);
				}else{
					try {
						baby.setBbNumber(Integer.parseInt(bbNumber));
					} catch (NumberFormatException e) {
						baby.setBbNumber(1);
					}
				}
				Object pk = baseDAO.save(baby);

				//TODO 创建爱的传承相册 胎儿没有传承相册
				/*TAlbum album = new TAlbum();
				album.setId(UUIDUtil.uuid());
				album.setAlbumName("爱的传承");
				album.setAlbumType(3);
				album.setCreateDate(DateUtils.currentDate());
				album.setCreateUserId(Integer.parseInt(userId));
				album.setHadInherit(0);
				album.setIsDir(0);
				album.setIsSysinit(1);
				album.setOwnerUserId(Integer.parseInt(userId));
				album.setBabyUserId(pk.toString());
				album.setAlbumUrl( SystemConfig.getValue("chuancheng.url"));
				baseDAO.save(album);*/
				
				result.put("succMsg", "添加胎儿成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}else if("2".equals(oper)){
				String hsql1 = "from TBabyInfo where id = ? ";
				List<TBabyInfo> babyList = baseDAO.getGenericByHql(hsql1, babyId);
				if(babyList != null && babyList.size() >0){
					baby = babyList.get(0);
					if(nickName != null && !"".equals(nickName)){
						baby.setNickname(nickName);
					}
					if(conceptionDate != null && !"".equals(conceptionDate)){
						baby.setConceptionDate(conceptionDate);
					}
					if(bornDate != null && !"".equals(bornDate)){
						baby.setExpectedDate(bornDate);
					}
					if(bbNumber != null && !"".equals(bbNumber)){
						try {
							baby.setBbNumber(Integer.parseInt(bbNumber));
						} catch (NumberFormatException e) {
							baby.setBbNumber(1);
						}
					}
					if(photo != null && !"".equals(photo)){
						String imgUrl= SystemConfig.getValue("img.http.url");
						baby.setPhotoUrl(photo.replace(imgUrl, ""));
					}
					baseDAO.saveOrUpdate(baby);
					result.put("succMsg", "修改胎儿成功！");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
				}else{
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "没有对应胎儿");
				}
				return returnJSON.toString();
			}else if("3".equals(oper)){
				
				
				baseDAO.delete(TBabyInfo.class, babyId);
				
				result.put("succMsg", "删除胎儿成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				
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
