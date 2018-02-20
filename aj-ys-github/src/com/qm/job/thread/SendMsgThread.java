package com.qm.job.thread;

import java.util.Date;

import com.frame.core.util.DateUtil;
import com.frame.core.util.HttpClient;
import com.frame.core.util.SystemConfig;
import com.qm.entities.MessagePublishInfo;
import com.qm.entities.MessagePublishSendLog;
import com.qm.service.MessagePublishSendLogService;

import net.sf.json.JSONObject;

public class SendMsgThread extends Thread{

	
	private MessagePublishInfo publishInfo;
	private String userTel ;
	private MessagePublishSendLogService messagePublishSendLogService;
	
	public SendMsgThread(MessagePublishInfo publishInfo, String tel, MessagePublishSendLogService messagePublishSendLogService){
		
		this.publishInfo = publishInfo;
		this.userTel = tel;
		this.messagePublishSendLogService = messagePublishSendLogService;
	}
	@Override
	public void run() {
		
		MessagePublishSendLog log = new MessagePublishSendLog();
		log.setMsgpublishId(this.publishInfo.getId());
		log.setReciveUser(userTel);
		log.setSendTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
		
		if(userTel.trim().length() == 11){
			
			JSONObject jo = new JSONObject();
			jo.put("serviceName", "aj_send_sms");
			jo.put("callType", "001");
			JSONObject param = new JSONObject();
			param.put("userTel",  this.userTel);
			param.put("content", this.publishInfo.getMsgContent());
			jo.put("params", param);
			String returnStr = HttpClient.doJSONPostMethod(SystemConfig.getValue("qm.app.interface.url"), jo.toString());
			if(returnStr != null && !"".equals(returnStr)){
				
				JSONObject returnJson = JSONObject.fromObject(returnStr);
				String returnCode =  returnJson.optString("returnCode");
				if("000000".equals(returnCode)){
					log.setStatus(0);
				}
			}
		}
		
		log.setStatus(1);
		
		messagePublishSendLogService.save(log);
	}
}