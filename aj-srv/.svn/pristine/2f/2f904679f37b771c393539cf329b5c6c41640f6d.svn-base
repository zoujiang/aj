package com.aj.praylettermgr.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.photomgr.vo.TPhoto;
import com.aj.sys.vo.TSysMsgAlert;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.BeanUtils;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 爱的传承
 * */

@Service("prayDo")
public class InheritService implements PublishService{

	private Logger log = Logger.getLogger(InheritService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String babyId = params.optString("babyId");  
		String phoneNo = params.optString("phoneNo");  
		String description = params.optString("description");  
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(babyId == null || "".equals(babyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "babyId为空！");
			return returnJSON.toString();
		}
		if(phoneNo == null || "".equals(phoneNo)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "ajNo为空！");
			return returnJSON.toString();
		}
		//只能是child表中
		String babyHsql = "FROM TChildrenInfo WHERE id = ?";
		List<TChildrenInfo> babyList = baseDAO.getGenericByHql(babyHsql, babyId);
		
		if(babyList == null || babyList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "孩子信息不存在");
			return returnJSON.toString();
		}
		//判断孩子是否满12岁
		/*	String birthday = babyList.get(0).getBirthday();
		if(birthday == null|| "".equals(birthday)){
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "请先设置孩子的生日");
			return returnJSON.toString();
		}
		//放开限制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int birdayYear = sdf.parse(birthday).getYear();
			int currentYear = new Date().getYear();
			if(currentYear - birdayYear < 12){
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "孩子未满12岁，还不能传承");
				return returnJSON.toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		
		List<TUser> inheritUserList = baseDAO.getGenericByHql("from TUser where userTel = ?", phoneNo);
		
		if(inheritUserList == null || inheritUserList.size() == 0){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "传承对象不存在，请重新输入手机号码");
			return returnJSON.toString();
		}else{
			String hsql = " from TUser where id = ? ";
			List<TUser> userList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
			
			if(!userList.get(0).getFamilyId().equals(babyList.get(0).getFamilyId())){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您没有传承该孩子的权限");
				return returnJSON.toString();
			}
			//验证是否已经添加为亲人
			String sql = "SELECT * FROM t_invite_info i WHERE  "+
						 " (((i.creatr_user_id = ? OR i.creatr_user_id = ? ) AND (i.relation_user_id = ? OR i.relation_user_id = ?) AND (i.fix_call_name = '儿子' OR i.fix_call_name = '女儿')) "+ 
						 "	OR ((i.creatr_user_id = ? OR i.creatr_user_id = ?) AND ( i.relation_user_id = ? OR i.relation_user_id = ?) AND (i.fix_call_name = '爸爸' OR i.fix_call_name = '妈妈'))) AND i.comfirm_state = '1'";
			List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new Object[]{userId, userList.get(0).getFamilyId(), inheritUserList.get(0).getId()+"", inheritUserList.get(0).getFamilyId(), inheritUserList.get(0).getId()+"", inheritUserList.get(0).getFamilyId(),userId, userList.get(0).getFamilyId()});
			if(list == null || list.size() ==0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "请先邀请传承对象为您的孩子，再传承");
				return returnJSON.toString();
			}
			
			
			String inheritAbumHql = " FROM TAlbum  WHERE albumType = 3 AND hadInherit = 0 AND babyUserId = ? ";
			List<TAlbum> albumList = baseDAO.getGenericByHql(inheritAbumHql, babyId);
			if(albumList != null && albumList.size()>0){
				TAlbum album = albumList.get(0);
					
					TUser inheritUser = inheritUserList.get(0);
					//新建爱的传承相册
					TAlbum newAlbum = new TAlbum();
					newAlbum.setAlbumName(album.getAlbumName()+"("+babyList.get(0).getNickname() +")");
					newAlbum.setAlbumType(album.getAlbumType());
					newAlbum.setAlbumUrl(album.getAlbumUrl());
					newAlbum.setCreateDate(DateUtils.currentDate());
					newAlbum.setCreateUserId(inheritUser.getId());
					newAlbum.setDescription(album.getDescription());
					newAlbum.setIsDir(album.getIsDir());
					newAlbum.setIsSysinit(album.getIsSysinit());
					newAlbum.setOwnerUserId(inheritUser.getId());
					String pk = UUIDUtil.uuid();
					newAlbum.setId(pk);
					newAlbum.setHadInherit(1);
					newAlbum.setBabyUserId(album.getBabyUserId());
					newAlbum.setInheritTarget(inheritUser.getId());
					newAlbum.setVisibleProperty(3);
					baseDAO.save(newAlbum);
					//复制一份爱的传承相册下面的照片到刚新建的相册下面
					String queryPhotoHsql = "from TPhoto where albumId = ?";
					List<TPhoto> photoList = baseDAO.getGenericByHql(queryPhotoHsql, album.getId());
					if(photoList != null && photoList.size() > 0){
						
						for(int i= 0; i< photoList.size(); i++){
							
							TPhoto temp = photoList.get(i);
							TPhoto newTemp = BeanUtils.copyProperties(TPhoto.class, temp);
							newTemp.setId(UUIDUtil.uuid());
							newTemp.setAlbumId(pk);
							newTemp.setSharedAlbumId(null);
							newTemp.setSharedDate(null);
							baseDAO.save(newTemp);
							
						}
					}
					//设置相册已经传承
					album.setAlbumName(album.getAlbumName()+"("+babyList.get(0).getNickname() +")");
					album.setHadInherit(1);
					baseDAO.update(album);
					// 传承祈愿信   因为祈愿信暂时没有删除功能，就直接修改祈愿信的babyid  TODO  祈愿信先不动
				/*	String prayLettterHsql = "update TPrayletter set inheritTarget = ?, hadInherit = 1 where babyUserId = ?";
					baseDAO.update(prayLettterHsql, inheritUser.getId().toString(), babyId );*/
					//记录传承信息
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sql = "insert into t_inherit_history (child_user_id, inherit_target,child_create_user_id, inherit_time) values (?,?,?,?)";
					baseDAO.execteNativeBulk(sql, babyId, inheritUser.getId().toString(), babyList.get(0).getCreateUserId(), sdf.format(new Date()));
					
					//删掉已经传承的小孩信息
					baseDAO.deleteObject(babyList.get(0));
					
					result.put("succMsg", "传承成功！");	
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					return returnJSON.toString();
			}else{
				log.info("传承失败：爱的传承相册不存在或者已经传承");
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "爱的传承相册不存在或者已经传承");
				return returnJSON.toString();
			}
		}
	}
	private void sendSysMsgToRelationUser(Integer targetUserId, String relationId, int msgType,
			String msgTitle, String msgContent) {
		
		TSysMsgAlert alert = new TSysMsgAlert();
		alert.setCreateDate(DateUtils.currentDate());
		alert.setHadRead(0);
		alert.setMsgContent(msgContent);
		alert.setMsgTitle(msgTitle);
		alert.setMsgType(msgType);
		alert.setRelationId(relationId);
		alert.setReciveUserId(targetUserId);
		baseDAO.save(alert);
		
	}
}
