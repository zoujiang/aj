package com.qm.service;

import java.util.List;

import com.qm.entities.MessagePublishSendLog;

public interface MessagePublishSendLogService {

	void save(MessagePublishSendLog log);

	List<MessagePublishSendLog> queryList(MessagePublishSendLog log);

	int getTotal(MessagePublishSendLog log);

}
