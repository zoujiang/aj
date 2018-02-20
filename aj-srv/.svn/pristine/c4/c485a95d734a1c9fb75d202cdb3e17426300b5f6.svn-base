package com.aj.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.sys.vo.TSysMsgAlert;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;

/**
 * 查询未读系统消息
 * */

@Service("querySysMsgAlert")
public class QuerySysMsgAlertService implements PublishService{

	private Logger log = Logger.getLogger(QuerySysMsgAlertService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String hadRead = params.optString("hadRead");
		String msgType = params.optString("msgType");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(pageSize == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize为空！");
			return returnJSON.toString();
		}
		if(currentPage == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage为空！");
			return returnJSON.toString();
		}
		
		try {
				
			String sql = " SELECT DISTINCT s.id id, s.MSG_TYPE msgType, s.MSG_TITLE msgTitle, s.MSG_CONTENT msgContent, s.RELATION_ID relationId, s.HAD_READ hadRead, s.CREATE_DATE createDate, s.UPDATE_DATE updateDate, s.RECIVE_USER_ID reciveUserId, "+
						  "s.DEAL_RESULT dealResult, s.SEND_USER_ID sendUserId,  u.PHOTO sendUserPhoto FROM t_sys_msg_alert s LEFT JOIN t_user u ON s.SEND_USER_ID = u.ID WHERE  s.RECIVE_USER_ID = ?";
			if(hadRead != null && "0".equals(hadRead)){
				sql += " and s.HAD_READ = 0 ";
			}
			if(msgType != null && !"".equals(msgType)){
				sql += " and s.MSG_TYPE =  "+ msgType ;
			}
			sql += " order by s.CREATE_DATE desc";
			
			List<Map<String, Object>> msgList = baseDAO.getGenericByPositionToSQL(sql, Integer.parseInt(currentPage) *Integer.parseInt(pageSize) , Integer.parseInt(pageSize),  new Object[]{Integer.parseInt(userId)});
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			List<TSysMsgAlert> resultList = new ArrayList<TSysMsgAlert>();
			if(msgList != null && msgList.size() > 0){
				for(Map<String, Object> map : msgList){
					 TSysMsgAlert tma = new TSysMsgAlert();
					 BeanUtils.populate(tma, map);  
					if(tma.getSendUserPhoto() != null && !"".equals(tma.getSendUserPhoto())){
						tma.setSendUserPhoto(SystemConfig.getValue("img.http.url")+tma.getSendUserPhoto());
					}
					
					if(tma.getHadRead()  == 0){
						//判断是否过期
						String inviteExpiredDay = SystemConfig.getValue("invite_expired_day");
						int expiredDay = 0;
						if(inviteExpiredDay == null || "".equals(inviteExpiredDay)){
							expiredDay = 7;
						}else{
							try {
								expiredDay = Integer.parseInt(inviteExpiredDay);
							} catch (NumberFormatException e) {
								expiredDay = 7;
							}
						}
						long currentMillis = System.currentTimeMillis();
						long createMillis = DateUtils.getTimeMillis(tma.getCreateDate());
						if((currentMillis - createMillis) > (expiredDay * 24 * 60 * 60 * 1000) ){
							tma.setDealResult(3);
							baseDAO.update(tma);
						}
					}
					resultList.add(tma);
				}
			}
			
			result.put("succMsg", "查询成功");
			result.put("msgList", resultList);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			
		} catch (Exception e) {
			
			log.info("查询系统消息失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}
