package com.qm.shop.brand.service;

import java.util.List;
import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.brand.vo.ShopBrandVO;

public interface ShopBrandService {

	DataModel<Map<String, Object>> getList(ShopBrandVO limitKey);

	int addBrand(String id, String brandName,Integer sortIndex,
			String brandIcon, Integer status, Integer type , Integer isRecommond);

	int updateBrandStatus(ShopBrandVO limitKey);

	Map<String, Object> findBrandById(String id);

	int updateBrand(String id, String brandName,Integer sortIndex,
			String brandIcon, Integer status, Integer type, Integer isRecommend);

	List<Map<String, Object>> selectAll(Integer type);

	List<Map<String, Object>> findByBrandName(String brandName, Integer type);

}
