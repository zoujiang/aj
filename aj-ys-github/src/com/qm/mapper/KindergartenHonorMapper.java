package com.qm.mapper;

import java.util.List;

import com.qm.entities.KindergartenHonor;
import com.qm.entities.KindergartenPhoto;

public interface KindergartenHonorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KindergartenHonor record);

    int insertSelective(KindergartenHonor record);

    KindergartenHonor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KindergartenHonor record);

    int updateByPrimaryKey(KindergartenHonor record);

	List<KindergartenHonor> selectByCondition(KindergartenHonor p);
}