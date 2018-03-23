package com.qm.service;

import java.util.List;

import com.qm.entities.MessagePublishInfo;

public interface MessagePublishService {

	int save(MessagePublishInfo info);

	List<MessagePublishInfo> selectNoSendTask(int currentInt);

	void update(MessagePublishInfo mp);

	List<MessagePublishInfo> queryList(MessagePublishInfo info);

	int getTotal(MessagePublishInfo info);

	int delByPrimary(Integer id);

}
