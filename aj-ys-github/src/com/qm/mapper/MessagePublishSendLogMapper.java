package com.qm.mapper;

import java.util.List;

import com.qm.entities.MessagePublishSendLog;

public interface MessagePublishSendLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessagePublishSendLog record);

    int insertSelective(MessagePublishSendLog record);

    MessagePublishSendLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessagePublishSendLog record);

    int updateByPrimaryKey(MessagePublishSendLog record);

	List<MessagePublishSendLog> selectByCondtion(MessagePublishSendLog log);

	int getTotal(MessagePublishSendLog log);
}