package com.qm.shop.category.service;

import java.util.List;
import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.category.vo.ShopCategoryVO;

public interface ShopCategoryService {

	DataModel<Map<String, Object>> getList(ShopCategoryVO limitKey);

	int addCategory(String id, String name, String description, Integer sort,
			String icon, Integer status);

	int updateCategoryStatus(ShopCategoryVO limitKey);

	Map<String, Object> findCategoryById(String id);

	int updateCategory(String id, String name, String description,
			Integer sort, String icon, Integer status);

	List<Map<String, Object>> selectAll();

}
