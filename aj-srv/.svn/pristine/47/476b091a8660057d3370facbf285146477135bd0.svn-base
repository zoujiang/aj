package com.aj.familymgr.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.familymgr.vo.TDivorceInfo;
import com.aj.familymgr.vo.TInviteInfo;
import com.aj.sys.vo.TSysMsgAlert;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.StringUtil;

@Service("dealDivocreApply")
public class DealDivocreApplyService implements PublishService{

	private Logger log = Logger.getLogger(DealDivocreApplyService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Resource
	private DivorceServiceInterface divorceService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String comefrom = params.optString("comefrom");
		String sysAlertMsgId = params.optString("sysAlertMsgId");
		String taskFromUserId = params.optString("taskFromUserId");
		String taskSendTime = params.optString("taskSendTime");
		String oper = params.optString("oper"); //1：同意  2：拒绝
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if("1".equals(comefrom)){
			
			if(taskFromUserId == null || "".equals(taskFromUserId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "taskFromUserId为空！");
				return returnJSON.toString();
			}
			if(taskSendTime == null || "".equals(taskSendTime)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "taskSendTime为空！");
				return returnJSON.toString();
			}
		}else if("2".equals(comefrom)){
			
			if(sysAlertMsgId == null || "".equals(sysAlertMsgId)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "sysAlertMsgId为空！");
				return returnJSON.toString();
			}
		}
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}
		
		List<TDivorceInfo> divocreList  = new ArrayList<TDivorceInfo>();
		if("1".equals(comefrom)){
			
			String hsql = "from TDivorceInfo where createUserId = ? and relationUserId = ? and applyDate = ? ";
			divocreList = baseDAO.getGenericByHql(hsql, taskFromUserId, userId, taskSendTime);
		}else if("2".equals(comefrom)){
			String sql = "SELECT RELATION_ID divorceId FROM t_sys_msg_alert WHERE id = ?";
			List<Map<String, Object>> divorceMapList = baseDAO.getGenericBySQL(sql, new Object[]{sysAlertMsgId});
			if(divorceMapList != null && divorceMapList.size() > 0){
				String divorceId = divorceMapList.get(0).get("divorceId")+"";
				divocreList = baseDAO.getGenericByHql("from TDivorceInfo where id = ? and status = '1'", divorceId);
			}
		}


		
		
		if(divocreList != null && divocreList.size() > 0){
			TDivorceInfo divocee = divocreList.get(0);
			if(divocee.getStatus() == 2 || divocee.getStatus() == 3){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "信息已经处理！");
				return returnJSON.toString();
			}
			/*if(divocee.getStatus() == 3){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "信息已经过期！");
				return returnJSON.toString();
			}*/
			
			String userHsql = "from TUser where id = ?";
			TUser createUser = (TUser) baseDAO.getGenericByHql(userHsql, Integer.parseInt(divocee.getCreateUserId())).get(0);
			TUser relationUser = (TUser) baseDAO.getGenericByHql(userHsql, Integer.parseInt(divocee.getRelationUserId())).get(0);
			
			String hsql = "from TInviteInfo where (creatrUserId = ? or relationUserId = ?) and  (fixCallName = ? or fixCallName = ? ) and comfirmState = '1'";
			/*String fixCallName = "";
			if(createUser.getSex() == 0){
				fixCallName = "老婆";
			}else{
				fixCallName = "老公";
			}*/
			List<TInviteInfo> inviteList =  baseDAO.getGenericByHql(hsql, createUser.getId()+"", createUser.getId()+"", "老婆", "老公");
			if(inviteList == null || inviteList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "未能找到您的配偶信息");
				return returnJSON.toString();
			}
			
			String applyDate = divocee.getApplyDate();
			//系统设置离婚申请超时时间
			//String timeoutDay = SystemConfig.getValue("divorce.apply.timeout");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				int hour = (int)((System.currentTimeMillis() -  sdf.parse(applyDate).getTime()) / (1000 * 60 * 60 )); 
				if(hour >= 72){
					//已经超时， 更新该条离婚申请为超时状态
					divocee.setStatus(3);
					baseDAO.update(divocee);
					if("2".equals(comefrom)){
						String natvieSQL = "update t_sys_msg_alert set DEAL_RESULT = 3, UPDATE_DATE = ? where id = ?";
						baseDAO.execteNativeBulk(natvieSQL, sdf.format(new Date()), sysAlertMsgId);
					}
					
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "信息已经过期！");
					return returnJSON.toString();
				}
			}catch(Exception e){
				
			}
			
			String content = (relationUser.getNickName() ==null || "".equals(relationUser.getNickName()) ? StringUtil.telTool(relationUser.getUserTel()) : relationUser.getNickName());
			if("1".equals(oper)){
				//同意离婚
				divorceService.divorce(createUser, relationUser, inviteList.get(0));
				
				divocee.setStatus(2);
				baseDAO.update(divocee);
				if("2".equals(comefrom)){
					String natvieSQL = "update t_sys_msg_alert set DEAL_RESULT = 1, UPDATE_DATE = ? where id = ?";
					baseDAO.execteNativeBulk(natvieSQL, sdf.format(new Date()), sysAlertMsgId);
				}
				content += "已同意你的离婚。";
				result.put("succMsg", "操作成功！");	
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}else if("2".equals(oper)){
				//拒绝
				divocee.setStatus(3);
				baseDAO.update(divocee);
				if("2".equals(comefrom)){
					String natvieSQL = "update t_sys_msg_alert set DEAL_RESULT = 2, UPDATE_DATE = ? where id = ?";
					baseDAO.execteNativeBulk(natvieSQL, sdf.format(new Date()), sysAlertMsgId);
				}
				content += "已拒绝你的离婚。";
			}
			sendSysMsgToRelationUser(relationUser.getId(), createUser.getId(),divocreList.get(0).getId(), 0, "系统消息", content);
			
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "操作成功！");
			return returnJSON.toString();
			
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "离婚信息不存在或者已经过期！");
			return returnJSON.toString();
		}
	}
	
	private void sendSysMsgToRelationUser(Integer sendUserId, Integer targetUserId, String relationId, int msgType,
			String msgTitle, String msgContent) {
		
		TSysMsgAlert alert = new TSysMsgAlert();
		alert.setCreateDate(DateUtils.currentDate());
		alert.setHadRead(0);
		alert.setMsgContent(msgContent);
		alert.setMsgTitle(msgTitle);
		alert.setMsgType(msgType);
		alert.setRelationId(relationId);
		alert.setReciveUserId(targetUserId);
		alert.setSendUserId(sendUserId);
		alert.setDealResult(0);
		baseDAO.save(alert);
		
	}
}
