package com.qm.service;

import com.qm.entities.KindergartenInfo;

import java.util.List;
import java.util.Map;

public interface KindergartenService {

    List<KindergartenInfo> queryList(KindergartenInfo info);
    List<Map<String,Object>> queryList2(KindergartenInfo info);

    int getTotal(KindergartenInfo limitKey);

    Integer save(KindergartenInfo info);

    int updateByPrimaryKeySelective(KindergartenInfo info);

    KindergartenInfo selectByPrimaryKey(Integer id);
}
