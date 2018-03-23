package com.qm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.util.DateUtil;
import com.qm.entities.MessagePublishInfo;
import com.qm.mapper.MessagePublishInfoMapper;
import com.qm.service.MessagePublishService;

@Service
public class MessagePublishServiceImpl implements MessagePublishService {

	@Autowired
	private MessagePublishInfoMapper messagePublishInfoMapper;

	@Override
	public int save(MessagePublishInfo info) {
		
		return messagePublishInfoMapper.insertSelective(info);
	}

	@Override
	public List<MessagePublishInfo> selectNoSendTask(int currentInt) {
		
		return messagePublishInfoMapper.selectNoSendTask(currentInt);
	}

	@Override
	public void update(MessagePublishInfo mp) {
		
		messagePublishInfoMapper.updateByPrimaryKeySelective(mp);
		
	}

	@Override
	public List<MessagePublishInfo> queryList(MessagePublishInfo info) {
		List<MessagePublishInfo> list = messagePublishInfoMapper.selectByCondition(info);
		for(MessagePublishInfo mi : list){
			int st = mi.getSendTime();
			mi.setSendTimeStr(DateUtil.dateFromat(st * 1000L, DateUtil.DATE_TIME_PATTERN2));
		}
		return list;
	}

	@Override
	public int getTotal(MessagePublishInfo info) {
		
		return messagePublishInfoMapper.getTotal(info);
	}

	@Override
	public int delByPrimary(Integer id) {
		
		return messagePublishInfoMapper.deleteByPrimaryKey(id);
	}
}
