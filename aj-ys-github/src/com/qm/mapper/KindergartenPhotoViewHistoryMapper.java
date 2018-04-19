package com.qm.mapper;

import java.util.Map;

import com.qm.entities.KindergartenPhotoViewHistory;

public interface KindergartenPhotoViewHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenPhotoViewHistory record);

    int insertSelective(KindergartenPhotoViewHistory record);

    KindergartenPhotoViewHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenPhotoViewHistory record);

    int updateByPrimaryKey(KindergartenPhotoViewHistory record);

	int selectTotalByCondition(Map<String, Object> queryParam);
}