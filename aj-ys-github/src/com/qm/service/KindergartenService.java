package com.qm.service;

import com.qm.entities.KindergartenInfo;

import java.util.List;

public interface KindergartenService {

    List<KindergartenInfo> queryList(KindergartenInfo info);

    int getTotal(KindergartenInfo limitKey);

    Integer save(KindergartenInfo info);

    int updateByPrimaryKeySelective(KindergartenInfo info);

    KindergartenInfo selectByPrimaryKey(Integer id);
}
