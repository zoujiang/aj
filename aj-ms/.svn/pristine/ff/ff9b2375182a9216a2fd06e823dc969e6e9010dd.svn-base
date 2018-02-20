package com.qm.shop.account.service;

import java.util.Map;

import com.frame.core.vo.DataModel;
import com.frame.system.po.User;
import com.qm.shop.account.vo.ShopAccountVO;

public interface ShopAccountService {

	DataModel<Map<String, Object>> getList(ShopAccountVO limitKey);

	int save(ShopAccountVO limitKey);

	int updateAccountStatus(String id, String status);

	Map<String, Object> findAccountById(String id);

	int update(ShopAccountVO limitKey);

	Map<String, Object> findAccountByUsername(String username);

	int saveSysUser(User uv);


}
