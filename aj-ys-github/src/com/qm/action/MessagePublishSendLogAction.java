package com.qm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.qm.entities.MessagePublishSendLog;
import com.qm.service.MessagePublishSendLogService;

@RestController
@RequestMapping("/msgpublish/log")
public class MessagePublishSendLogAction {

	@Autowired
	private MessagePublishSendLogService messagePublishSendLogService;
	
	@RequestMapping("/list")
	public String list(String  userTel, Integer offset, Integer limit){
		
		JSONObject json = new JSONObject();
		MessagePublishSendLog log = new MessagePublishSendLog();
		log.setReciveUser(userTel);
		log.setOffset(offset);
		log.setLimit(limit);
		List<MessagePublishSendLog> list = messagePublishSendLogService.queryList(log);
		int total = messagePublishSendLogService.getTotal(log);
		json.put("rows", list);
		json.put("total", total);
		return json.toString();
		
	}
}
