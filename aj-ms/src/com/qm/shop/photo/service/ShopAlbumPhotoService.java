package com.qm.shop.photo.service;

import java.util.List;
import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.album.vo.ShopAlbumVO;
import com.qm.shop.photo.vo.ShopAlbumPhotoDO;

public interface ShopAlbumPhotoService {

	DataModel<Map<String, Object>> getList(ShopAlbumVO limitKey);

	int save(ShopAlbumPhotoDO photo);

	List<Map<String, Object>> queryPhotoByAlbumId(String id);

	int deleteAllInAlbumId(String albumId);

}
