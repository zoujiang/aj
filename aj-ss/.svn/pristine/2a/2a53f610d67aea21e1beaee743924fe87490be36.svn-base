package com.qm.shop.album.service;

import java.util.List;
import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.album.vo.ShopAlbumVO;

public interface ShopAlbumService {

	DataModel<Map<String, Object>> getList(ShopAlbumVO limitKey);

	int save(ShopAlbumVO limitKey);

	List<Map<String, Object>> queryListByShopIdAndUserId(String shopId,
			String userId);

	List<Map<String, Object>> queryNopaidAlbumListByShopIdAndUserId(
			String shopId, String userId);

	Map<String, Object> find(String id);

	int update(ShopAlbumVO album);

	int del(String id);

	Map<String, Object> findDownloadPageInit(String albumId);

}
