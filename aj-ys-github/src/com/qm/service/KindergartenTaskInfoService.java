package com.qm.service;

import java.util.List;

import com.qm.entities.KindergartenTaskInfo;

public interface KindergartenTaskInfoService {

	List<KindergartenTaskInfo> queryList(KindergartenTaskInfo info);

	int getTotal(KindergartenTaskInfo info);

	KindergartenTaskInfo selectByPrimary(int id);

	int update(KindergartenTaskInfo info);

}
