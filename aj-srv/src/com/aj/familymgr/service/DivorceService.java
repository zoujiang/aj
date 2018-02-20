package com.aj.familymgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.albummgr.vo.TAlbum;
import com.aj.easemob.service.EasemobGroupService;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familymgr.vo.THomeInfo;
import com.aj.familymgr.vo.TInviteInfo;
import com.aj.photomgr.vo.TPhoto;
import com.aj.sys.vo.TSysMsgAlert;
import com.aj.task.service.TaskService;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.StringUtil;
import com.util.UUIDUtil;


/**
 * 离婚
 * */
@Service("divorceService")
public class DivorceService implements DivorceServiceInterface {

	private Logger log = Logger.getLogger(DivorceService.class);
	
	@Resource
	private GenericDAO baseDAO;
	@Resource
	private EasemobGroupService easemobGroupService;

	@Override
	public void divorce(TUser user, TUser otherUser, TInviteInfo invite){
		
		TFamilyInfo familyInfo = baseDAO.get(TFamilyInfo.class, user.getFamilyId());
		
		//给其中一个人重新设置家庭ID
		String familyId = UUID.randomUUID().toString();
		otherUser.setFamilyId(familyId);
		baseDAO.saveOrUpdate(otherUser);
		//创建family_info
		TFamilyInfo info = new TFamilyInfo();
		info.setId(familyId);
		info.setCreateUserId(otherUser.getId()+"");
		String familyName = otherUser.getNickName() == null || "".equals(otherUser.getNickName()) ? StringUtil.telTool(otherUser.getUserTel()) : otherUser.getNickName(); 
		info.setFamilyName(familyName+"之家");
		info.setIsMarried(3);
		info.setMarriedDate(null);
		baseDAO.save(info);
		
		//处理孩子,更新私有的孩子的家庭ID
		String sql = "UPDATE t_children_info SET family_id = ? WHERE create_user_id = ? AND is_private = '1'";
		baseDAO.execteNativeBulk(sql, familyId,otherUser.getId());
		//处理孩子,更新非私有的孩子的家庭ID
		sql = "UPDATE t_children_info SET family_id = concat(family_id ,?) WHERE (create_user_id = ? or create_user_id = ?) AND is_private = '0'";
		baseDAO.execteNativeBulk(sql, ","+familyId,otherUser.getId(), user.getId());
		//处理胎儿,更新私有的胎儿的家庭ID
		sql = "UPDATE t_baby_info SET family_id = ? WHERE create_user_id = ? AND is_private = '1'";
		baseDAO.execteNativeBulk(sql, familyId,otherUser.getId());
		//处理胎儿,更新非私有的胎儿的家庭ID
		sql = "UPDATE t_baby_info SET family_id = concat(family_id ,?) WHERE (create_user_id = ? or create_user_id = ?) AND is_private = '0'";
		baseDAO.execteNativeBulk(sql, ","+familyId,otherUser.getId(), user.getId());
		
		
		//处理夫妻相册
		String hsql = "from TAlbum where familyId = ? and albumType = 6 ";
		List<TAlbum> fuqiAlbumList = baseDAO.getGenericByHql(hsql, user.getFamilyId());
		if(fuqiAlbumList != null && fuqiAlbumList.size() > 0){
			//新建往事相册
			TAlbum wangshiAlbum = new TAlbum();
			String ws1Id = UUIDUtil.uuid();
			wangshiAlbum.setId(ws1Id);
			wangshiAlbum.setAlbumName("往事相册("+familyInfo.getFamilyName()+")");
			wangshiAlbum.setAlbumType(5);
			wangshiAlbum.setAlbumUrl(SystemConfig.getValue("wangshi.url"));
			wangshiAlbum.setCreateDate(DateUtils.currentDate());
			wangshiAlbum.setCreateUserId(user.getId());
			wangshiAlbum.setDescription("系统生成");
			wangshiAlbum.setIsSysinit(0);
			wangshiAlbum.setOwnerUserId(user.getId());
			wangshiAlbum.setIsDir(0);
			wangshiAlbum.setFamilyName(otherUser.getNickName());
			baseDAO.save(wangshiAlbum);
			//把相片转移过来
			hsql = "from TPhoto where albumId = ?";
			List<TPhoto> photoList = baseDAO.getGenericByHql(hsql, fuqiAlbumList.get(0).getId());
			if(photoList != null && photoList.size() >0){
				for(TPhoto photo : photoList){
					
					TPhoto newPhoto = new TPhoto();
					BeanUtils.copyProperties(photo, newPhoto);
					newPhoto.setAlbumId(ws1Id);
					newPhoto.setId(UUIDUtil.uuid());
					
					baseDAO.save(newPhoto);
				}
			}
			
			
			TAlbum wangshiAlbum2 = new TAlbum();
			String ws2Id = UUIDUtil.uuid();
			wangshiAlbum2.setId(ws2Id);
			wangshiAlbum2.setAlbumName("往事相册("+familyInfo.getFamilyName()+")");
			wangshiAlbum2.setAlbumType(5);
			wangshiAlbum2.setAlbumUrl(SystemConfig.getValue("wangshi.url"));
			wangshiAlbum2.setCreateDate(DateUtils.currentDate());
			wangshiAlbum2.setCreateUserId(otherUser.getId());
			wangshiAlbum2.setDescription("系统生成");
			wangshiAlbum2.setIsSysinit(0);
			wangshiAlbum2.setOwnerUserId(otherUser.getId());
			wangshiAlbum2.setIsDir(0);
			wangshiAlbum2.setFamilyName(user.getNickName());
			baseDAO.save(wangshiAlbum2);
			//把照片的相册ID更新为这个ID  ws2Id
			hsql = "update TPhoto set albumId= ? where albumId = ?";
			baseDAO.update(hsql, ws2Id, fuqiAlbumList.get(0).getId());
			
			//删除老的夫妻相册
			baseDAO.delete(TAlbum.class, fuqiAlbumList.get(0).getId());
		}
		
		//查询家庭相册
		hsql = "from TAlbum where familyId = ? and albumType = 4 ";
		List<TAlbum> familyAlbumList = baseDAO.getGenericByHql(hsql, user.getFamilyId());
		if(familyAlbumList != null && familyAlbumList.size() >0){
			
			//新建家庭相册
			TAlbum familyAlbum = new TAlbum();
			String faId1 = UUIDUtil.uuid();
			familyAlbum.setId(faId1);
			familyAlbum.setAlbumName("家庭相册("+familyInfo.getFamilyName()+")");
			familyAlbum.setAlbumType(4);
			familyAlbum.setAlbumUrl(SystemConfig.getValue("jiating.url"));
			familyAlbum.setCreateDate(DateUtils.currentDate());
			familyAlbum.setCreateUserId(user.getId());
			familyAlbum.setOwnerUserId(user.getId());
			familyAlbum.setDescription("系统创建");
			familyAlbum.setFamilyId(user.getFamilyId());
			familyAlbum.setIsSysinit(0);
			baseDAO.save(familyAlbum);
			//把照片复制到新相册
			for(TAlbum album : familyAlbumList){
				hsql = "from TPhoto where albumId = ?";
				List<TPhoto> photoList = baseDAO.getGenericByHql(hsql, album.getId());
				if(photoList != null && photoList.size() >0){
					for(TPhoto photo : photoList){
						
						TPhoto newPhoto = new TPhoto();
						BeanUtils.copyProperties(photo, newPhoto);
						newPhoto.setAlbumId(faId1);
						newPhoto.setId(UUIDUtil.uuid());
						newPhoto.setCreateUserId(user.getId());
						baseDAO.save(newPhoto);
					}
				}
			}
			
			
			TAlbum familyAlbum2 = new TAlbum();
			String faId2 = UUIDUtil.uuid();
			familyAlbum2.setId(faId2);
			familyAlbum2.setAlbumName("家庭相册("+familyInfo.getFamilyName()+")");
			familyAlbum2.setAlbumType(4);
			familyAlbum2.setAlbumUrl(SystemConfig.getValue("jiating.url"));
			familyAlbum2.setCreateDate(DateUtils.currentDate());
			familyAlbum2.setCreateUserId(otherUser.getId());
			familyAlbum.setOwnerUserId(otherUser.getId());
			familyAlbum2.setDescription("系统创建");
			familyAlbum2.setFamilyId(otherUser.getFamilyId());
			familyAlbum2.setIsSysinit(0);
			baseDAO.save(familyAlbum2);
			
			for(TAlbum album : familyAlbumList){
				//把照片的相册ID更新为这个ID  ws2Id
				hsql = "update t_photo set CREATE_USER_ID= ? , ALBUM_ID= ? where ALBUM_ID = ?";
				baseDAO.execteNativeBulk(hsql, otherUser.getId(), faId2, album.getId());
				//删除旧相册
				baseDAO.deleteObject(album);
			}
		}
		String fn = user.getNickName() == null || "".equals(user.getNickName()) ? StringUtil.telTool(user.getUserTel()) : user.getNickName(); 
		familyInfo.setFamilyName(fn+"之家");
		familyInfo.setIsMarried(3);
		familyInfo.setMarriedDate(null);
		baseDAO.update(familyInfo);
		//传承相册
		hsql = "from TAlbum where familyId ='"+user.getFamilyId()+"' and albumType =3";
		log.info("查询传承相册hsql:"+hsql);
		List<TAlbum> inheritAlbumList = baseDAO.getGenericByHql(hsql);
		if(inheritAlbumList != null && inheritAlbumList.size() > 0){
			for(TAlbum album : inheritAlbumList){
				if(album.getHadInherit() == 1){
					//已经传承
					TAlbum ccalbum = new TAlbum();
					String uuid = UUIDUtil.uuid();
					ccalbum.setId(uuid);
					ccalbum.setAlbumName("爱的传承");
					ccalbum.setAlbumType(3);
					ccalbum.setCreateDate(DateUtils.currentDate());
					ccalbum.setCreateUserId(user.getId());
					ccalbum.setFamilyId(user.getFamilyId());
					ccalbum.setHadInherit(album.getHadInherit());
					ccalbum.setIsDir(0);
					ccalbum.setIsSysinit(1);
					ccalbum.setOwnerUserId(user.getId());
					ccalbum.setBabyUserId(album.getBabyUserId());
					ccalbum.setAlbumUrl(SystemConfig.getValue("chuancheng.url"));
					ccalbum.setVisibleProperty(2);
					baseDAO.save(ccalbum);
					
					hsql = "from TPhoto where albumId = ?";
					List<TPhoto> photoList = baseDAO.getGenericByHql(hsql, album.getId());
					if(photoList != null && photoList.size()>0){
						for(TPhoto photo : photoList){
							TPhoto newPhoto = new TPhoto();
							BeanUtils.copyProperties(photo, newPhoto);
							newPhoto.setAlbumId(uuid);
							newPhoto.setId(UUIDUtil.uuid());
							
							baseDAO.save(newPhoto);
						}
					}
					
					
					album.setCreateUserId(otherUser.getId());
					album.setFamilyId(otherUser.getFamilyId());
					album.setOwnerUserId(otherUser.getId());
					baseDAO.update(album);
				}
				
			}
			
		}
		
		//处理家庭圈
		//1.处理夫妻自己创建的家庭圈
		hsql = "from THomeInfo where creatrUserId =? and relationUserId != ? and isPrivate = ? and isValid != 1";
		List<THomeInfo> homeList = baseDAO.getGenericByHql(hsql, user.getFamilyId(), user.getFamilyId(), "0");
		if(homeList != null && homeList.size() > 0){
			for(THomeInfo homeInfo : homeList){
				
				THomeInfo hi1 = new THomeInfo();
				hi1.setCallName(homeInfo.getCallName());
				hi1.setCreatrUserId(user.getFamilyId());
				String groupId1 = "";
				String groupId2 = "";
				//获取环信讨论组成员
				JSONObject param = new JSONObject();
				param.put("group_id", homeInfo.getEasemobGroupId());
				JSONObject jo;
				try {
					jo = easemobGroupService.getGroupMembs(param);
					//{"serviceName":"aj_easemob_group_membs_get","returnCode":"000000","result":[{"member":"13637973949"},{"member":"15800299434"},{"owner":"18682759607"}],"errorMsg":""}
					JSONArray newJa = new JSONArray();
					JSONArray ohterNewJa = new JSONArray();
					if(jo.get("statusCode") != null && "200".equals(jo.get("statusCode").toString())){
						JSONArray result = jo.getJSONArray("data");
						
						for (int i = 0; i < result.size(); i++) {
							String member = result.optJSONObject(i).optString("member");
							if(member == null || "".equals(member)){
								member = result.optJSONObject(i).optString("owner");
							}
							if(!(otherUser.getId()+"").equals(member)){
								newJa.add(member);
							}
							if(!(user.getId()+"").equals(member)){
								ohterNewJa.add(member);
							}
						}
					}
					////创建环信讨论组
					if(newJa.size() > 0){
						
						JSONObject paramJSON = new JSONObject();
						paramJSON.put("groupname", "讨论组");
						paramJSON.put("desc","");
						paramJSON.put("public",false); //是否公开
						paramJSON.put("approval",true); //是否需要验证
						paramJSON.put("owner",user.getId()+"");
						paramJSON.put("members",newJa);
						JSONObject object = easemobGroupService.createGroup(paramJSON);
						log.info("添加环信讨论组返回："+ object);
						if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
							JSONObject data = object.getJSONObject("data");
							String groupId = data.optString("groupid");
							//更新到讨论组信息中
							groupId1 = groupId;
						}
					}
					if(ohterNewJa.size() > 0){
						
						JSONObject paramJSON2 = new JSONObject();
						paramJSON2.put("groupname", "讨论组");
						paramJSON2.put("desc","");
						paramJSON2.put("public",false); //是否公开
						paramJSON2.put("approval",true); //是否需要验证
						paramJSON2.put("owner",otherUser.getId()+"");
						paramJSON2.put("members",ohterNewJa);
						JSONObject object = easemobGroupService.createGroup(paramJSON2);
						log.info("添加环信讨论组返回："+ object);
						if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
							JSONObject data = object.getJSONObject("data");
							String groupId = data.optString("groupid");
							//更新到讨论组信息中
							groupId2 = groupId;
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				hi1.setEasemobGroupId(groupId1);
				hi1.setId(UUIDUtil.uuid());
				hi1.setInviteArea(homeInfo.getInviteArea());
				hi1.setInviteId(homeInfo.getInviteId());
				hi1.setIsPrivate(homeInfo.getIsPrivate());
				hi1.setRelationUserId(homeInfo.getRelationUserId());
				hi1.setIsValid(0);
				baseDAO.save(hi1);
				
				THomeInfo hi2 = new THomeInfo();
				hi2.setCallName(homeInfo.getCallName());
				hi2.setCreatrUserId(otherUser.getFamilyId());
				hi2.setEasemobGroupId(groupId2);
				hi2.setId(UUIDUtil.uuid());
				hi2.setInviteArea(homeInfo.getInviteArea());
				hi2.setInviteId(homeInfo.getInviteId());
				hi2.setIsPrivate(homeInfo.getIsPrivate());
				hi2.setRelationUserId(homeInfo.getRelationUserId());
				hi2.setIsValid(0);
				baseDAO.save(hi2);
				
				//删除该homeinfo
				homeInfo.setIsValid(1);
				baseDAO.update(homeInfo);
				//baseDAO.deleteObject(homeInfo);
			}
		}
		
		//2.处理别人创建邀请范围为我家庭的的家庭圈
		hsql = "from THomeInfo where creatrUserId !=? and relationUserId = ? and inviteArea = ? and isValid != 1";
		List<THomeInfo> otherhomeList = baseDAO.getGenericByHql(hsql, user.getFamilyId(), user.getFamilyId() ,"1");
		if(otherhomeList != null && otherhomeList.size() > 0){
			for(THomeInfo homeInfo : otherhomeList){
				
				//获取环信讨论组成员
				JSONObject param = new JSONObject();
				param.put("group_id", homeInfo.getEasemobGroupId());
				JSONObject jo;
				String groupId = "";
				String owner = "";
				try {
					/*JSONObject groupDetails = easemobGroupService.getGroupDetail(param);
					//{"serviceName":"aj_easemob_group_membs_get","returnCode":"000000","result":[{"member":"13637973949"},{"member":"15800299434"},{"owner":"18682759607"}],"errorMsg":""}
					if(groupDetails.get("statusCode") != null && "200".equals(groupDetails.get("statusCode").toString())){
						JSONArray result = jo.getJSONArray("data");
						JSONArray array  = groupDetails.optJSONArray("result");
						for(int i=0;i<array.size();i++){
							 JSONObject ob = (JSONObject) array.get(i);
							 if(ob.optString("owner") != null && !"".equals(ob.optString("owner"))){
								 owner = ob.optString("owner");
							 }
						}
					}*/
					
					jo = easemobGroupService.getGroupMembs(param);
					//{"serviceName":"aj_easemob_group_membs_get","returnCode":"000000","result":[{"member":"13637973949"},{"member":"15800299434"},{"owner":"18682759607"}],"errorMsg":""}
					JSONArray ohterNewJa = new JSONArray();
					if(jo.get("statusCode") != null && "200".equals(jo.get("statusCode").toString())){
						JSONArray result = jo.getJSONArray("data");
						for (int i = 0; i < result.size(); i++) {
							String member = result.optJSONObject(i).optString("member");
							if(member == null || "".equals(member)){
								member = result.optJSONObject(i).optString("owner");
								owner = member;
							}
							if(!(user.getId()+"").equals(member)){
								ohterNewJa.add(member);
							}
						}
						if(ohterNewJa.size() > 0){
							
							JSONObject paramJSON = new JSONObject();
							paramJSON.put("groupname", "讨论组");
							paramJSON.put("desc","");
							paramJSON.put("public",false); //是否公开
							paramJSON.put("approval",true); //是否需要验证
							paramJSON.put("owner",owner);
							paramJSON.put("members",ohterNewJa);
							JSONObject object = easemobGroupService.createGroup(paramJSON);
							log.info("添加环信讨论组返回："+ object);
							if(object.get("statusCode") != null && "200".equals(object.get("statusCode").toString())){
								JSONObject data = object.getJSONObject("data");
								String id = data.optString("groupid");
								//更新到讨论组信息中
								groupId = id;
							}
						}
						
					}
				}catch(Exception e){
					log.info("创建环信讨论组失败，原因："+e);
				}
				
				//再创建一个家庭
				THomeInfo home1 = new THomeInfo();
				home1.setCallName(homeInfo.getCallName());
				home1.setCreatrUserId(homeInfo.getCreatrUserId());
				home1.setEasemobGroupId(groupId);
				home1.setId(UUIDUtil.uuid());
				home1.setInviteArea(homeInfo.getInviteArea());
				home1.setInviteId(homeInfo.getInviteId());
				home1.setIsPrivate(homeInfo.getIsPrivate());
				home1.setRelationUserId(otherUser.getFamilyId());
				home1.setIsValid(0);
				baseDAO.save(home1);
				//更新当前homeinfo信息
				//环信讨论组中去掉user用户]
				JSONObject delParam = new JSONObject();
				delParam.put("group_id", homeInfo.getEasemobGroupId());
				List<String> members = new ArrayList<String>();
				members.add(otherUser.getId()+"");
				delParam.put("usernames", members);
				try {
					easemobGroupService.delGroupMembs(delParam);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				homeInfo.setRelationUserId(user.getFamilyId());
				baseDAO.update(homeInfo);
				
			}
			
		}
		
		invite.setComfirmState("4");
		baseDAO.update(invite);
		
	}
	

}
