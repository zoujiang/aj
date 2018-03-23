package com.qm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qm.entities.MessagePublishInfo;

public interface MessagePublishInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessagePublishInfo record);

    int insertSelective(MessagePublishInfo record);

    MessagePublishInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessagePublishInfo record);

    int updateByPrimaryKeyWithBLOBs(MessagePublishInfo record);

    int updateByPrimaryKey(MessagePublishInfo record);

	List<MessagePublishInfo> selectNoSendTask(@Param("currentInt") int currentInt);

	List<MessagePublishInfo> selectByCondition(MessagePublishInfo info);

	int getTotal(MessagePublishInfo info);
}