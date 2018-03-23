package com.qm.mapper;

import java.util.List;
import java.util.Map;

import com.qm.entities.KindergartenAccount;
import com.qm.entities.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<Map<String, Object>> queryList(KindergartenAccount info);

	int getTotal(KindergartenAccount info);

	Map<String, Object> selectMapByPrimaryKey(String id);

	List<SysUser> findByCondition(SysUser account);
}