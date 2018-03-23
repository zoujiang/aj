package com.qm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qm.entities.KindergartenPhoto;

public interface KindergartenPhotoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenPhoto record);

    int insertSelective(KindergartenPhoto record);

    KindergartenPhoto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenPhoto record);

    int updateByPrimaryKey(KindergartenPhoto record);

	List<KindergartenPhoto> queryPhotoByOwerId(@Param("ownerId") String ownerId);

	List<KindergartenPhoto> selectByCondition(KindergartenPhoto photo);

	int getTotal(KindergartenPhoto photo);

	/**
	 * @param  queryDay : 2018-02-02
	 * @param  kindergartenId 
	 * */
	int queryPhotoNumber(Map<String, Object> param);
	/**
	 * @param  queryDay : 2018-02-02
	 * @param  kindergartenId 
	 * */
	int queryVideoNumber(Map<String, Object> param);
}