package com.aj.petmgr.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.petmgr.vo.TPetInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.AJNoUtil;
import com.util.Constant;
import com.util.DateUtils;


/**
 * 宠物管理
 * */

@Service("petMgr")
public class PetManageService implements PublishService{

	private Logger log = Logger.getLogger(PetManageService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String oper = params.optString("oper");  /*1：添加2：修改3：删除*/
		String petId = params.optString("petId");
		String userId = params.optString("userId");
		String photoUrl = params.optString("photoUrl");
		String nickName = params.optString("nickName");
		String sex = params.optString("sex");
		String birthday = params.optString("birthday");
		String petType = params.optString("petType");
		
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
				if(petId == null || "".equals(petId)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "petId为空！");
					return returnJSON.toString();
				}
			}else{
				if(nickName == null || "".equals(nickName)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "nickName为空！");
					return returnJSON.toString();
				}
				if(photoUrl == null || "".equals(photoUrl)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "photoUrl为空！");
					return returnJSON.toString();
				}
				if(sex == null || "".equals(sex)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "sex为空！");
					return returnJSON.toString();
				}
				if(birthday == null || "".equals(birthday)){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "birthday为空！");
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
			TPetInfo pet = new TPetInfo();
			if("1".equals(oper)){
				pet.setNickName(nickName);
				String imgUrl= SystemConfig.getValue("img.http.url");
				pet.setPhotoUrl(photoUrl.replace(imgUrl, ""));
				pet.setSex(sex);
				pet.setBirthday(birthday);
				pet.setAjno(new AJNoUtil(baseDAO).queryPAJNoSeq("BNO"));
				pet.setCreateDate(DateUtils.currentDate());
				pet.setCreateUserId(user.getId()+"");
				pet.setPetType(petType);
				pet.setIsPrivate("1");
				pet.setId(UUID.randomUUID().toString());
				Object pk = baseDAO.save(pet);
				
				result.put("succMsg", "添加宠物成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}else if("2".equals(oper)){
				String hsql1 = "from TPetInfo where id = ? ";
				List<TPetInfo> petList = baseDAO.getGenericByHql(hsql1, petId);
				
				if(petList != null && petList.size() >0){
					pet = petList.get(0);
					if(nickName != null && !"".equals(nickName)){
						pet.setNickName(nickName);
					}
					if(photoUrl != null && !"".equals(photoUrl)){
						String imgUrl= SystemConfig.getValue("img.http.url");
						pet.setPhotoUrl(photoUrl.replace(imgUrl, ""));
					}
					if(sex != null && !"".equals(sex)){
						pet.setSex(sex);
					}
					if(birthday != null && !"".equals(birthday)){
						pet.setBirthday(birthday);
					}
					if(petType != null && !"".equals(petType)){
						pet.setPetType(petType);
					}
					baseDAO.saveOrUpdate(pet);
					result.put("succMsg", "修改宠物成功！");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
				}else{
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "没有对应宠物");
				}
				return returnJSON.toString();
			}else if("3".equals(oper)){
				
				baseDAO.delete(TPetInfo.class, petId);
						
				result.put("succMsg", "删除宠物成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				
				return returnJSON.toString();
			}{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "无效的操作类型！");
				return returnJSON.toString();
			}
		}
		
	}

}
