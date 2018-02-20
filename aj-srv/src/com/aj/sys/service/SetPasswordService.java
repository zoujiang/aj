package com.aj.sys.service;

import java.util.List;

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
import com.util.StringUtil;
import com.util.UUIDUtil;

/**
 * 忘记密码， 设置密码
 * */

@Service("forgetPwd")
public class SetPasswordService implements PublishService{

	private Logger log = Logger.getLogger(SetPasswordService.class);
	
	@Resource
	private GenericDAO baseDAO;
	@Resource
	private EasemobUserService easemobUserService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String newPssword = params.optString("newPssword");
		String smsValidateToken = params.optString("smsValidateToken");
		String ucode = params.optString("ucode");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(smsValidateToken == null || "".equals(smsValidateToken)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "smsValidateToken为空！");
			return returnJSON.toString();
		}else if(newPssword == null || "".equals(newPssword)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "newPssword为空！");
			return returnJSON.toString();
		}else if(ucode == null || "".equals(ucode)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "ucode为空！");
			return returnJSON.toString();
		}
		
		
		try {
			//根据smsValidateToken查找userId
			String hsql = " from TCustomReg where smsTokenId = ? ";
			List<TCustomReg> customRegList = baseDAO.getGenericByHql(hsql, smsValidateToken);
			if(customRegList != null && customRegList.size() > 0){
				TCustomReg  customReg = customRegList.get(0);
				String userTel = customReg.getUserTel();
				
				//查询用户是否新注册， 如果是就生成家庭ID
				String qHsql = " from TUser where userTel = ?";
				List<TUser> userList = baseDAO.getGenericByHql(qHsql, userTel);
				TUser user = null;
				boolean isRegist = false;
				String familyId = UUIDUtil.uuid();
				if(userList == null || userList.size() ==0){
					//用户不存在，说明是注册
					user = new TUser();
					user.setUserTel(userTel);
					user.setNickName(StringUtil.telTool(userTel));
					user.setFamilyId(familyId);
					//生成亲脉号规则
					AJNoUtil au = new AJNoUtil(baseDAO);
					String ajNo = au.queryPAJNoSeq("PNO");
					user.setAjNo(ajNo);
					user.setRegistdate(DateUtils.currentDate());
					//默认一个性别
					user.setSex(0);
				//	user.setIsMarried(3);
					//系统默认的头像
					user.setPhoto("/clt/default/defaultPhoto.png");
					isRegist = true;
				
				}else{
					
					user = userList.get(0);
					String pwd = user.getPassword();
					if(pwd == null || "".equals(pwd)){
						//新注册， 但是该手机号已经存在 
						returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
						result.put("succMsg", "");
						returnJSON.put("result", result);
						returnJSON.put("errorMsg", "该用户已经存在！");
						return returnJSON.toString();
					}
				}
				user.setPassword(DesBase64Tool.encryptDES(newPssword));
				user.setIsValid(0);
				baseDAO.saveOrUpdate(user);
				
				if(isRegist){
					
					//注册环信
					hsql = "from TUser where userTel = ?";
					user = (TUser) baseDAO.getGenericByHql(hsql, user.getUserTel()).get(0);
					JSONObject paramJSON = new JSONObject();
					paramJSON.put("username", user.getId());
					paramJSON.put("password", DesBase64Tool.encryptDES("aj2015"));
					paramJSON.put("nickname", user.getUserTel());
					easemobUserService.createUser(paramJSON);
					
					//初始化个人相册
					sysCreateAlbum(user.getId(), user.getFamilyId());
					//生成一条自己和自己对应的关系
				/*	TFamilyRelationship familyRelationship = new TFamilyRelationship();
					familyRelationship.setCallId(34);
					familyRelationship.setCreateDate(DateUtils.currentDate());
					familyRelationship.setCreateUserId(user.getId());
					familyRelationship.setDescription("自己");
					familyRelationship.setIsConfirm(0);
					familyRelationship.setIsDel(1);
					familyRelationship.setIsPrivate(1);
					familyRelationship.setRelationUserId(user.getId());
					baseDAO.save(familyRelationship);*/
					
					//生成家庭信息
					TFamilyInfo family = new TFamilyInfo();
					family.setId(familyId);
					family.setFamilyName(StringUtil.telTool(userTel) +"之家");
					family.setCreateUserId(user.getId().toString());
					family.setCreateDate(DateUtils.currentDate());
					family.setIsMarried(3);
					baseDAO.save(family);
					
				}
				
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				if(isRegist){
					result.put("succMsg", SystemConfig.getValue("msg.setpassword.success"));
				}else{
					result.put("succMsg", "设置密码成功，请重新登录");
				}
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				result.put("succMsg", "");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "设置密码失败，请重新获取验证码！");
			}
		} catch (Exception e) {
			
			
			log.info("设置密码失败，原因："+e.getMessage());
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
		//夫妻相册 和家庭相册 结婚时候生成
		/*TAlbum album1 = new TAlbum();
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
		baseDAO.save(album1);*/
		TAlbum album2 = new TAlbum();
		album2.setId(UUIDUtil.uuid());
		album2.setAlbumName("家人相册");
		album2.setAlbumType(4);
		album2.setCategory("1");
		album2.setCreateDate(createDate);
		album2.setCreateUserId(createUserId);
		album2.setDescription("系统生成");
		album2.setIsSysinit(1);
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
