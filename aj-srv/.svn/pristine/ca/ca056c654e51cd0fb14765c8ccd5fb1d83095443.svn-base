package com.aj.babymgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.babymgr.vo.TBabyInfo;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.AJNoUtil;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 胎儿出生
 * */

@Service("babyBorn")
public class BabyBornService implements PublishService{

	private Logger log = Logger.getLogger(BabyBornService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String babyId = params.optString("babyId");
		String userId = params.optString("userId");
		String nickName = params.optString("nickName");
		String truename = params.optString("truename");
		String bornDate = params.optString("bornDate");
		String photo = params.optString("photo");
		String sex = params.optString("sex");
		
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
		if(babyId == null || "".equals(babyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "babyId为空！");
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
			TUser user = userList.get(0);
			String babyHsql = "from TBabyInfo where id = ?  and familyId = ?";
			List<TBabyInfo> babyList = baseDAO.getGenericByHql(babyHsql, babyId, user.getFamilyId());
			if(babyList == null || babyList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您没有控制该孩子的权限！");
				return returnJSON.toString();
			}
			
			//删除baby信息
			baseDAO.deleteObject(babyList.get(0));
			//新建child信息    由于存在多胞胎的情况，由客户端直接调用创建child
			/*TChildrenInfo child = new TChildrenInfo();
			child.setAjno(new AJNoUtil(baseDAO).queryPAJNoSeq("PNO"));
			String imgUrl= SystemConfig.getValue("img.http.url");
			child.setPhotoUrl(photo.replace(imgUrl, ""));
			child.setNickname(nickName);
			child.setTruename(truename);
			child.setSex(sex);
			child.setBirthday(bornDate);
			child.setCreateDate(DateUtils.currentDate());
			child.setCreateUserId(userId);
			child.setFamilyId(user.getFamilyId());
			child.setId(UUIDUtil.uuid());
			child.setIsPrivate("0");
			Object pk = baseDAO.save(child);
			
			//创建爱的传承相册
			TAlbum album = new TAlbum();
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
			
			
			result.put("succMsg", "出生成功！");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
	}

}
