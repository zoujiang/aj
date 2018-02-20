package com.qm.shop.photo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;
import com.qm.shop.album.vo.ShopAlbumVO;
import com.qm.shop.photo.service.ShopAlbumPhotoService;
import com.qm.shop.photo.vo.ShopAlbumPhotoDO;

@Service("shopAlbumPhotoService")
@Scope("prototype")
public class ShopAlbumPhotoServiceImpl  implements ShopAlbumPhotoService{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GenericDAO baseDAO;
	
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public DataModel<Map<String, Object>> getList(ShopAlbumVO limitKey) {
		
		String sql = "SELECT  a.id, i.shop_name shopName, a.account username, a.tele tel, a.name nickname, DATE_FORMAT(a.create_dt,'%Y-%m-%d %H:%i:%s') createTime, a.ISENABLED status FROM t_sys_user a , t_shop_info i WHERE a.shop_id = i.id ";
		String countSql = "select count(1) from t_sys_user a, t_shop_info i WHERE a.shop_id = i.id";
		List<String> param = new ArrayList<String>();
		List<Map<String, Object>> list = baseDAO.getGenericByPositionToSQL(sql, limitKey.getOffset(), limitKey.getPageSize(), param.toArray());
		int total = baseDAO.getGenericIntToSQL(countSql, param.toArray());
		return new DataModel<Map<String,Object>>(list, total);
	}

	@Override
	public int save(ShopAlbumPhotoDO vo) {
		String sql = "INSERT INTO t_shop_photo (id, album_id, photo_url, create_time) VALUES (?,?,?,?)";
		
		return baseDAO.execteNativeBulk(sql, vo.getId(),vo.getAlbumId(), vo.getUrl(), vo.getCreateTime());
	}

	@Override
	public List<Map<String, Object>> queryPhotoByAlbumId(String id) {
		
		String sql = "select id, photo_url photoUrl  from t_shop_photo where album_id = ? order by create_time";
		return baseDAO.getGenericBySQL(sql, new Object[]{id});
	}

	@Override
	public int deleteAllInAlbumId(String albumId) {
		String sql = "delete from t_shop_photo where album_id = ?";
		return baseDAO.execteNativeBulk(sql, albumId);
	}

	
}
