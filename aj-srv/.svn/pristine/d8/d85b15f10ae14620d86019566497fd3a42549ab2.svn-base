package com.aj.praylettermgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.aj.familymgr.vo.TInviteInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 爱的传承查询祈愿信列表
 * */

@Service("prayLetterList")
public class QueryPrayLetterService implements PublishService{

	private Logger log = Logger.getLogger(QueryPrayLetterService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String babyId = params.optString("babyId");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		
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
		if(pageSize == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize为空！");
			return returnJSON.toString();
		}
		if(currentPage == null || "".equals(currentPage)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage为空！");
			return returnJSON.toString();
		}
		
		
		//查询祈愿信
		String sql = "select inherit_target, child_create_user_id from t_inherit_history where child_user_id = ?";
		List<Map<String, Object>> inheritHistory = baseDAO.getGenericBySQL(sql, new Object[]{babyId});
		if(inheritHistory != null && inheritHistory.size()>0){
			//传承过了， 那么小孩信息， 就应读取传承人的信息，
			TUser user = baseDAO.get(TUser.class, Integer.parseInt(inheritHistory.get(0).get("inherit_target").toString()));
			result.put("babyId", user.getId());
			result.put("birthday", user.getBirthday());
			result.put("babyName", user.getNickName());
			result.put("babyPhoto", user.getPhoto());
		}else{
			TChildrenInfo childList = baseDAO.get(TChildrenInfo.class, babyId);
			result.put("babyId", childList.getId());
			result.put("birthday", childList.getBirthday());
			result.put("babyName", childList.getNickname());
			result.put("babyPhoto", childList.getPhotoUrl());
			
		}
		
	//	String letterHSQL = "from TPrayletter where babyUserId = ? order by createDate desc";
	//	List<TPrayletter> letterList =	baseDAO.getGenericByPosition(letterHSQL, Integer.parseInt(currentPage), Integer.parseInt(pageSize), babyId);
		String letterSql = "SELECT "+
							  "l.id, "+
							  "l.AUDIO_URL audioUrl, "+
							  "l.BABY_USER_ID babyId, "+
							  "l.CONTENT content, "+
							  "l.CREATE_DATE createDate, "+
							  "l.PIC_URL picUrl, "+
							  "l.PRAY_USER_ID prayUserId, "+
							  "u.NICK_NAME nickName,"+ 
							  "u.PHOTO photo  "+
							"FROM "+
							 " t_prayletter l, "+
							 " t_user u  "+
							"WHERE l.PRAY_USER_ID = u.ID  "+
							"AND l.BABY_USER_ID = ?  "+
							"ORDER BY l.CREATE_DATE DESC ";
		List<Map<String, Object>> letterList =  baseDAO.getGenericByPositionToSQL(letterSql, (Integer.parseInt(currentPage) *Integer.parseInt(pageSize)), Integer.parseInt(pageSize), new Object[]{babyId});						
		
		//循环取每条祈愿信评论者 的称谓, 只取爷爷奶奶外公外婆爸爸妈妈
		if(letterList != null && letterList.size() > 0){
			Map<String, Object> familyUserMap = new HashMap<String, Object>();
			//先得到父母的信息
			String createUserId = "";
			if(inheritHistory != null && inheritHistory.size()>0){
				createUserId = inheritHistory.get(0).get("child_create_user_id").toString();
			}else{
				TChildrenInfo childList = baseDAO.get(TChildrenInfo.class, babyId);
				createUserId = childList.getCreateUserId();
			}
			
			//1.根据孩子创建者找到用户，如果性别为男，则是爸爸，否则是妈妈
			TUser createUser = (TUser) baseDAO.getGenericByHql("from TUser where id = ?", Integer.parseInt(createUserId)).get(0);
			if(createUser.getSex() == 0){
				//男
				familyUserMap.put(createUserId, "爸爸");
				//2.根据爸爸找到妈妈
				String inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
				List<TInviteInfo> inviteList = baseDAO.getGenericByHql(inviteHsql, createUserId);
			    if(inviteList != null && inviteList.size() >0){
			    	//妈妈USERID
			    	String wifeUserId = inviteList.get(0).getRelationUserId();
			    	familyUserMap.put(wifeUserId, "妈妈");
			    	//3.根据妈妈找到外公外婆
			    	//这里有2中情况， 有可能是外公外婆添加的妈妈，有可能是妈妈添加的外公外婆
			    	//3.1 外公外婆添加妈妈
			    	inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '女儿' and comfirmState = '1' ";
			    	inviteList = baseDAO.getGenericByHql(inviteHsql, wifeUserId);
			    	if(inviteList != null && inviteList.size() >0){
				    	//外公外婆userid
				    	String createMontherUserId = inviteList.get(0).getCreatrUserId();
				    	TUser createMontherUser = (TUser) baseDAO.getGenericByHql("from TUser where id = ? ", Integer.parseInt(createMontherUserId)).get(0);
				    	if(createMontherUser.getSex() == 0){
				    		
				    		familyUserMap.put(createMontherUserId, "外公");
				    		//3.1.1根据外公找到外婆
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, createMontherUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//外公添加的外婆
				    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "外婆");
				    		}else{
				    			//外婆添加的外公
				    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, createMontherUserId);
						    	if(inviteList != null && inviteList.size() > 0){
					    			//外公添加的外婆
					    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "外婆");
					    		}
				    		}
				    	}else{
				    		familyUserMap.put(createMontherUserId, "外婆");
				    		//3.1.2根据外婆找到外公
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, createMontherUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//外婆添加的外公
				    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "外公");
				    		}else{
				    			//外公添加的外婆
				    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, createMontherUserId);
						    	if(inviteList != null && inviteList.size() > 0){
					    			//外公添加的外婆
					    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "外公");
					    		}
				    		}
				    	}
				    }else{
				    	//3.2妈妈添加外公外婆
				    	inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '爸爸' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, wifeUserId);
				    	if(inviteList != null && inviteList.size()>0){
				    		//找到了外公
				    		String waigongUserId = inviteList.get(0).getRelationUserId();
				    		familyUserMap.put(waigongUserId, "外公");
				    		
				    		//3.2.1根据外公找到外婆
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, waigongUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//外公添加的外婆
				    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "外婆");
				    		}else{
				    			//外婆添加的外公
				    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, waigongUserId);
						    	if(inviteList != null && inviteList.size() > 0){
					    			//外公添加的外婆
					    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "外婆");
					    		}
				    		}
				    	}else{
				    		//妈妈没有直接找到外公，那么就去查找外婆
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '妈妈' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, wifeUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//找到了外婆
				    			String waipoUserId = inviteList.get(0).getRelationUserId();
					    		familyUserMap.put(waipoUserId, "外婆");
					    		
					    		//3.2.1根据外婆找到外公
					    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, waipoUserId);
					    		if(inviteList != null && inviteList.size() > 0){
					    			//外公添加的外婆
					    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "外公");
					    		}else{
					    			//外公添加的外婆
					    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
							    	inviteList = baseDAO.getGenericByHql(inviteHsql, waipoUserId);
							    	if(inviteList != null && inviteList.size() > 0){
						    			//外公添加的外婆
						    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "外公");
						    		}
					    		}
				    		}
				    	}
				    }
			    }
			    //4.根据爸爸找到爷爷奶奶 //和3类似
		    	//这里有2中情况， 有可能是爷爷奶奶添加的爸爸，有可能是爸爸添加的爷爷奶奶
		    	//4.1 爷爷奶奶添加的爸爸
		    	inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '儿子' and comfirmState = '1' ";
		    	inviteList = baseDAO.getGenericByHql(inviteHsql, createUserId);
		    	if(inviteList != null && inviteList.size() >0){
			    	//爷爷奶奶userid
			    	String createFatherUserId = inviteList.get(0).getCreatrUserId();
			    	TUser createFatherUser = (TUser) baseDAO.getGenericByHql("from TUser where id = ? ", Integer.parseInt(createFatherUserId)).get(0);
			    	if(createFatherUser.getSex() == 0){
			    		
			    		familyUserMap.put(createFatherUserId, "爷爷");
			    		//4.1.1根据爷爷找到奶奶
			    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, createFatherUser.getId()+"");
			    		if(inviteList != null && inviteList.size() > 0){
			    			//爷爷添加的奶奶
			    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "奶奶");
			    		}else{
			    			//奶奶添加的爷爷
			    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, createFatherUser.getId()+"");
					    	if(inviteList != null && inviteList.size() > 0){
				    			//爷爷添加的奶奶
				    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "奶奶");
				    		}
			    		}
			    	}else{
			    		familyUserMap.put(createFatherUserId, "奶奶");
			    		//4.1.2根据奶奶找到爷爷
			    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, createFatherUserId);
			    		if(inviteList != null && inviteList.size() > 0){
			    			//奶奶添加的爷爷
			    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "爷爷");
			    		}else{
			    			//爷爷添加的奶奶
			    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, createFatherUserId);
					    	if(inviteList != null && inviteList.size() > 0){
				    			//爷爷添加的奶奶
				    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "爷爷");
				    		}
			    		}
			    	}
			    }else{
			    	//4.2爸爸添加爷爷奶奶
			    	inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '爸爸' and comfirmState = '1' ";
			    	inviteList = baseDAO.getGenericByHql(inviteHsql, createUserId);
			    	if(inviteList != null && inviteList.size()>0){
			    		//找到了爷爷
			    		String yeyeUserId = inviteList.get(0).getRelationUserId();
			    		familyUserMap.put(yeyeUserId, "爷爷");
			    		
			    		//4.2.1根据爷爷找到奶奶
			    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, yeyeUserId);
			    		if(inviteList != null && inviteList.size() > 0){
			    			//爷爷添加的奶奶
			    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "奶奶");
			    		}else{
			    			//奶奶添加的爷爷
			    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, yeyeUserId);
					    	if(inviteList != null && inviteList.size() > 0){
				    			//外公添加的外婆
				    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "奶奶");
				    		}
			    		}
			    	}else{
			    		//爸爸没有直接找到爷爷，那么就去查找奶奶
			    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '妈妈' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, createUserId);
			    		if(inviteList != null && inviteList.size() > 0){
			    			//找到了奶奶
			    			String nainaiUserId = inviteList.get(0).getRelationUserId();
				    		familyUserMap.put(nainaiUserId, "奶奶");
				    		
				    		//4.2.2根据奶奶找到爷爷
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, nainaiUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//奶奶添加的爷爷
				    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "爷爷");
				    		}else{
				    			//爷爷添加的奶奶
				    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, nainaiUserId);
						    	if(inviteList != null && inviteList.size() > 0){
					    			//爷爷添加的奶奶
					    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "爷爷");
					    		}
				    		}
			    		}
			    	}
			    }
			
			}else{
				//女  下面就注释就不一一改了
				familyUserMap.put(createUserId, "妈妈");
				//2.根据爸爸找到妈妈
				String inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
				List<TInviteInfo> inviteList = baseDAO.getGenericByHql(inviteHsql, createUserId);
			    if(inviteList != null && inviteList.size() >0){
			    	//妈妈USERID
			    	String wifeUserId = inviteList.get(0).getRelationUserId();
			    	familyUserMap.put(wifeUserId, "爸爸");
			    	//3.根据妈妈找到外公外婆
			    	//这里有2中情况， 有可能是外公外婆添加的妈妈，有可能是妈妈添加的外公外婆
			    	//3.1 外公外婆添加妈妈
			    	inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '儿子' and comfirmState = '1' ";
			    	inviteList = baseDAO.getGenericByHql(inviteHsql, wifeUserId);
			    	if(inviteList != null && inviteList.size() >0){
				    	//外公外婆userid
				    	String createMontherUserId = inviteList.get(0).getCreatrUserId();
				    	TUser createMontherUser = (TUser) baseDAO.getGenericByHql("from TUser where id = ? ", Integer.parseInt(createMontherUserId)).get(0);
				    	if(createMontherUser.getSex() == 0){
				    		
				    		familyUserMap.put(createMontherUserId, "爷爷");
				    		//3.1.1根据外公找到外婆
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, createMontherUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//外公添加的外婆
				    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "奶奶");
				    		}else{
				    			//外婆添加的外公
				    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, createMontherUserId);
						    	if(inviteList != null && inviteList.size() > 0){
					    			//外公添加的外婆
					    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "奶奶");
					    		}
				    		}
				    	}else{
				    		familyUserMap.put(createMontherUserId, "奶奶");
				    		//3.1.2根据外婆找到外公
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, createMontherUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//外婆添加的外公
				    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "爷爷");
				    		}else{
				    			//外公添加的外婆
				    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, createMontherUserId);
						    	if(inviteList != null && inviteList.size() > 0){
					    			//外公添加的外婆
					    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "爷爷");
					    		}
				    		}
				    	}
				    }else{
				    	//3.2妈妈添加外公外婆
				    	inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '爸爸' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, wifeUserId);
				    	if(inviteList != null && inviteList.size()>0){
				    		//找到了外公
				    		String waigongUserId = inviteList.get(0).getRelationUserId();
				    		familyUserMap.put(waigongUserId, "爷爷");
				    		
				    		//3.2.1根据外公找到外婆
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, waigongUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//外公添加的外婆
				    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "奶奶");
				    		}else{
				    			//外婆添加的外公
				    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, waigongUserId);
						    	if(inviteList != null && inviteList.size() > 0){
					    			//外公添加的外婆
					    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "奶奶");
					    		}
				    		}
				    	}else{
				    		//妈妈没有直接找到外公，那么就去查找外婆
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '妈妈' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, wifeUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//找到了外婆
				    			String waipoUserId = inviteList.get(0).getRelationUserId();
					    		familyUserMap.put(waipoUserId, "奶奶");
					    		
					    		//3.2.1根据外婆找到外公
					    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, waipoUserId);
					    		if(inviteList != null && inviteList.size() > 0){
					    			//外公添加的外婆
					    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "爷爷");
					    		}else{
					    			//外公添加的外婆
					    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
							    	inviteList = baseDAO.getGenericByHql(inviteHsql, waipoUserId);
							    	if(inviteList != null && inviteList.size() > 0){
						    			//外公添加的外婆
						    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "爷爷");
						    		}
					    		}
				    		}
				    	}
				    }
			    }
			    //4.根据爸爸找到爷爷奶奶 //和3类似
		    	//这里有2中情况， 有可能是爷爷奶奶添加的爸爸，有可能是爸爸添加的爷爷奶奶
		    	//4.1 爷爷奶奶添加的爸爸
		    	inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '女儿' and comfirmState = '1' ";
		    	inviteList = baseDAO.getGenericByHql(inviteHsql, createUserId);
		    	if(inviteList != null && inviteList.size() >0){
			    	//爷爷奶奶userid
			    	String createFatherUserId = inviteList.get(0).getCreatrUserId();
			    	TUser createFatherUser = (TUser) baseDAO.getGenericByHql("from TUser where id = ? ", Integer.parseInt(createFatherUserId)).get(0);
			    	if(createFatherUser.getSex() == 0){
			    		
			    		familyUserMap.put(createFatherUserId, "外公");
			    		//4.1.1根据爷爷找到奶奶
			    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, createFatherUser);
			    		if(inviteList != null && inviteList.size() > 0){
			    			//爷爷添加的奶奶
			    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "外婆");
			    		}else{
			    			//奶奶添加的爷爷
			    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, createFatherUserId);
					    	if(inviteList != null && inviteList.size() > 0){
				    			//爷爷添加的奶奶
				    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "外婆");
				    		}
			    		}
			    	}else{
			    		familyUserMap.put(createFatherUserId, "外婆");
			    		//4.1.2根据奶奶找到爷爷
			    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, createFatherUserId);
			    		if(inviteList != null && inviteList.size() > 0){
			    			//奶奶添加的爷爷
			    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "外公");
			    		}else{
			    			//爷爷添加的奶奶
			    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, createFatherUserId);
					    	if(inviteList != null && inviteList.size() > 0){
				    			//爷爷添加的奶奶
				    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "外公");
				    		}
			    		}
			    	}
			    }else{
			    	//4.2爸爸添加爷爷奶奶
			    	inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '爸爸' and comfirmState = '1' ";
			    	inviteList = baseDAO.getGenericByHql(inviteHsql, createUserId);
			    	if(inviteList != null && inviteList.size()>0){
			    		//找到了爷爷
			    		String yeyeUserId = inviteList.get(0).getRelationUserId();
			    		familyUserMap.put(yeyeUserId, "外公");
			    		
			    		//4.2.1根据爷爷找到奶奶
			    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, yeyeUserId);
			    		if(inviteList != null && inviteList.size() > 0){
			    			//爷爷添加的奶奶
			    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "外婆");
			    		}else{
			    			//奶奶添加的爷爷
			    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, yeyeUserId);
					    	if(inviteList != null && inviteList.size() > 0){
				    			//外公添加的外婆
				    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "外婆");
				    		}
			    		}
			    	}else{
			    		//爸爸没有直接找到爷爷，那么就去查找奶奶
			    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '妈妈' and comfirmState = '1' ";
				    	inviteList = baseDAO.getGenericByHql(inviteHsql, createUserId);
			    		if(inviteList != null && inviteList.size() > 0){
			    			//找到了奶奶
			    			String nainaiUserId = inviteList.get(0).getRelationUserId();
				    		familyUserMap.put(nainaiUserId, "外婆");
				    		
				    		//4.2.2根据奶奶找到爷爷
				    		inviteHsql = "from TInviteInfo where creatrUserId = ? and fixCallName = '老公' and comfirmState = '1' ";
					    	inviteList = baseDAO.getGenericByHql(inviteHsql, nainaiUserId);
				    		if(inviteList != null && inviteList.size() > 0){
				    			//奶奶添加的爷爷
				    			familyUserMap.put(inviteList.get(0).getRelationUserId(), "外公");
				    		}else{
				    			//爷爷添加的奶奶
				    			inviteHsql = "from TInviteInfo where relationUserId = ? and fixCallName = '老婆' and comfirmState = '1' ";
						    	inviteList = baseDAO.getGenericByHql(inviteHsql, nainaiUserId);
						    	if(inviteList != null && inviteList.size() > 0){
					    			//爷爷添加的奶奶
					    			familyUserMap.put(inviteList.get(0).getCreatrUserId(), "外公");
					    		}
				    		}
			    		}
			    	}
			    }
			}
			
			
			
			if(familyUserMap != null && !familyUserMap.isEmpty()){
				for(int i =0 ; i< letterList.size(); i++){
					Map<String, Object> letter = letterList.get(i);
					letter.put("photo", (letter.get("photo")== null || "".equals(letter.get("photo")) ? "" : SystemConfig.getValue("img.http.url") + letter.get("photo")));
					String prayUserId =  letter.get("prayUserId").toString();
					if(familyUserMap.containsKey(prayUserId)){
						letter.put("callName", familyUserMap.get(prayUserId));
					}else{
						letter.put("callName", letter.get("nickName"));
					}
				}
			}
			result.put("paryletterList", letterList);
			result.put("succMsg", "查询成功！");	
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
			
		}
		
		result.put("paryletterList", letterList);
		result.put("succMsg", "查询成功！");	
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}
