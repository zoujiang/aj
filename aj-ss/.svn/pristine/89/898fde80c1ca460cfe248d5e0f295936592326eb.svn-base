package com.qm.shop.brand.service;

import java.util.List;
import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.brand.vo.ShopBrandVO;

public interface ShopBrandService {

	DataModel<Map<String, Object>> getList(ShopBrandVO limitKey);

	int addBrand(String id, String brandName,Integer sortIndex,
			String brandIcon, Integer status);

	int updateBrandStatus(ShopBrandVO limitKey);

	Map<String, Object> findBrandById(String id);

	int updateBrand(String id, String brandName,Integer sortIndex,
			String brandIcon, Integer status);

	List<Map<String, Object>> selectAll();

	List<Map<String, Object>> findByBrandName(String brandName);

}
