package com.qm.service.impl;

import com.qm.entities.KindergartenInfo;
import com.qm.mapper.KindergartenInfoMapper;
import com.qm.service.KindergartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KindergartenServiceImpl implements KindergartenService {

    @Autowired
    private KindergartenInfoMapper kindergartenInfoMapper;

    public List<KindergartenInfo> queryList(KindergartenInfo info) {
        try {
            return kindergartenInfoMapper.selectByCondition(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotal(KindergartenInfo limitKey) {
        return kindergartenInfoMapper.getTotal(limitKey);
    }

    public Integer save(KindergartenInfo info) {
        return kindergartenInfoMapper.insert(info);
    }

    public int updateByPrimaryKeySelective(KindergartenInfo info) {
        return kindergartenInfoMapper.updateByPrimaryKeySelective(info);
    }

    public KindergartenInfo selectByPrimaryKey(Integer id) {
        return kindergartenInfoMapper.selectByPrimaryKey(id);
    }
}
