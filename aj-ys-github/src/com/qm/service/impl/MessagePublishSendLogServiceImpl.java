package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.entities.MessagePublishSendLog;
import com.qm.mapper.MessagePublishSendLogMapper;
import com.qm.service.MessagePublishSendLogService;

@Service
public class MessagePublishSendLogServiceImpl implements MessagePublishSendLogService{

	@Autowired
	private MessagePublishSendLogMapper messagePublishSendLogMapper;

	@Override
	public void save(MessagePublishSendLog log) {
		
		messagePublishSendLogMapper.insertSelective(log);
	}

	@Override
	public List<MessagePublishSendLog> queryList(MessagePublishSendLog log) {
		
		return messagePublishSendLogMapper.selectByCondtion(log);
	}

	@Override
	public int getTotal(MessagePublishSendLog log) {
		
		return messagePublishSendLogMapper.getTotal(log);
	}
}
