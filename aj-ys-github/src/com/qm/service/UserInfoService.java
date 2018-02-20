package com.qm.service;

import java.util.List;

import com.qm.entities.UserInfo;

public interface UserInfoService {

	List<UserInfo> queryForPage(UserInfo user);

	int getTotal(UserInfo user);

}
