package com.qm.mapper;

import java.util.List;

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
}