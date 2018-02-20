package com.aj.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TCustomReg;
import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.easemob.service.EasemobUserService;
import com.aj.familymgr.vo.TFamilyInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.util.DesBase64Tool;
import com.util.AJNoUtil;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;

/**
 * 根据用户ID，批量查询用户昵称，头像 ， 签名
 * */

@Service("batchQueryUserInfo")
public class BatchQueryUserInfoService implements PublishService{

	private Logger log = Logger.getLogger(BatchQueryUserInfoService.class);
	
	@Resource
	private GenericDAO baseDAO;
	@Resource
	private EasemobUserService easemobUserService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userIds = params.optString("userIds");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userIds == null || "".equals(userIds)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userIds为空！");
			return returnJSON.toString();
		}
		
		String imgPrefix = SystemConfig.getValue("img.http.url");
		try {
			//根据smsValidateToken查找userId
			String sql = " SELECT u.id userId,u.NICK_NAME nickName, CONCAT('"+imgPrefix+"' ,u.PHOTO) userPhoto, u.AUTOGRAPH userSign FROM t_user u WHERE u.id IN ("+userIds+")  ";
			List<Map<String, Object>> userInfoList = baseDAO.getGenericBySQL(sql, null);
			
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			result.put("succMsg", "查询成功");
			result.put("userInfo", userInfoList);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
		} catch (Exception e) {
			
			
			log.info("查询用户信息失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}
	private void sysCreateAlbum(Integer createUserId, String familyId) {
		String createDate = DateUtils.currentDate();
		TAlbum album = new TAlbum();
		album.setId(UUIDUtil.uuid());
		album.setAlbumName("个人相册");
		album.setAlbumType(1);
		album.setCategory("1");
		album.setCreateDate(createDate);
		album.setCreateUserId(createUserId);
		album.setDescription("系统生成");
		album.setIsSysinit(0);
		album.setOwnerUserId(createUserId);
		album.setIsDir(0);
		album.setAlbumUrl( SystemConfig.getValue("geren.url"));
		album.setVisibleProperty(0);
		baseDAO.save(album);
		TAlbum album1 = new TAlbum();
		album1.setId(UUIDUtil.uuid());
		album1.setAlbumName("夫妻相册");
		album1.setAlbumType(6);
		album1.setCategory("1");
		album1.setCreateDate(createDate);
		album1.setCreateUserId(createUserId);
		album1.setDescription("系统生成");
		album1.setIsSysinit(0);
		album1.setOwnerUserId(createUserId);
		album1.setIsDir(0);
		album1.setFamilyId(familyId);
		album1.setAlbumUrl( SystemConfig.getValue("fuqi.url"));
		album1.setVisibleProperty(1);
		baseDAO.save(album1);
		TAlbum album2 = new TAlbum();
		album2.setId(UUIDUtil.uuid());
		album2.setAlbumName("家人相册");
		album2.setAlbumType(4);
		album2.setCategory("1");
		album2.setCreateDate(createDate);
		album2.setCreateUserId(createUserId);
		album2.setDescription("系统生成");
		album2.setIsSysinit(0);
		album2.setOwnerUserId(createUserId);
		album2.setIsDir(0);
		album2.setFamilyId(familyId);
		album2.setAlbumUrl( SystemConfig.getValue("jiating.url"));
		album2.setVisibleProperty(2);
		baseDAO.save(album2);
		/*TAlbum album3= new TAlbum();
		album3.setId(UUIDUtil.uuid());
		album3.setAlbumName("个人视频相册");
		album3.setAlbumType(1);
		album3.setCategory("2");
		album3.setCreateDate(createDate);
		album3.setCreateUserId(createUserId);
		album3.setDescription("系统生成");
		album3.setIsSysinit(0);
		album3.setOwnerUserId(createUserId);
		album3.setIsDir(0);
		baseDAO.save(album3);
		
		TAlbum album4= new TAlbum();
		album4.setId(UUIDUtil.uuid());
		album4.setAlbumName("家庭视频相册");
		album4.setAlbumType(4);
		album4.setCategory("2");
		album4.setCreateDate(createDate);
		album4.setCreateUserId(createUserId);
		album4.setDescription("系统生成");
		album4.setIsSysinit(0);
		album4.setOwnerUserId(createUserId);
		album4.setIsDir(0);
		album4.setFamilyId(familyId);
		baseDAO.save(album4);*/
		
	}
}
