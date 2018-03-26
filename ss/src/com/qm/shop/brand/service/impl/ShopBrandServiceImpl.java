package com.qm.shop.brand.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.vo.DataModel;
import com.qm.shop.brand.service.ShopBrandService;
import com.qm.shop.brand.vo.ShopBrandVO;

@Service("shopBrandService")
@Scope("prototype")
public class ShopBrandServiceImpl  implements ShopBrandService{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GenericDAO baseDAO;
	
	@Override
	public DataModel<Map<String, Object>> getList(ShopBrandVO limitKey) {
		
		String sql = "select id, brand_name, sort_index, status, brand_icon, type, is_recommend from t_shop_brand where 1=1 ";
		String countSql = "select count(1) from t_shop_brand where 1=1 ";
		List<String> param = new ArrayList<String>();
		if(limitKey != null){
			if(limitKey.getBrandName() != null && !"".equals(limitKey.getBrandName().toString())){
				sql += " and brand_name like concat ('%',?,'%')";
				countSql += " and brand_name like concat ('%',?,'%')";
				param.add(limitKey.getBrandName());
			}
		}
		sql += " order by sort_index ";
		List<Map<String, Object>> list = baseDAO.getGenericByPositionToSQL(sql, limitKey.getOffset(), limitKey.getPageSize(), param.toArray());
		int total = baseDAO.getGenericIntToSQL(countSql, param.toArray());
		return new DataModel<Map<String,Object>>(list, total);
	}

	@Override
	public int addBrand(String id, String brandName,
			Integer sortIndex, String brandIcon, Integer status, Integer type, Integer isRecommend) {
		
		String sql = "insert into t_shop_brand (id, brand_name, sort_index, status, brand_icon, type, is_recommend) values (?,?,?,?,?,?,?)";
		int i =	baseDAO.execteNativeBulk(sql, id, brandName, sortIndex, status, brandIcon, type, isRecommend );
		return i;
	}

	@Override
	public int updateBrandStatus(ShopBrandVO limitKey) {
		
		String sql = "update t_shop_brand set status = ? where id = ?";
		int i = baseDAO.execteNativeBulk(sql, limitKey.getStatus(), limitKey.getId());
		return i;
	}

	@Override
	public Map<String, Object> findBrandById(String id) {
		
		String sql = "select id, brand_name, sort_index, status, brand_icon, type, is_recommend from t_shop_brand where id = ?";
		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new String[]{id});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int updateBrand(String id, String brandName,
			Integer sortIndex, String brandIcon, Integer status, Integer type, Integer isRecommend) {
		
		String sql = "update t_shop_brand set brand_name = ?, sort_index = ?, status = ? , type = ?, is_recommend = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(brandName);
		param.add(sortIndex);
		param.add(status);
		param.add(type);
		param.add(isRecommend);
		if(brandIcon != null && !"".equals(brandIcon)){
			sql += ", brand_icon = ?";
			param.add(brandIcon);
		}
		sql += " where id = ?";
		param.add(id);
		
		return baseDAO.execteNativeBulk(sql, param.toArray());
	}

	@Override
	public List<Map<String, Object>> selectAll(Integer type) {

		String sql = "select id, brand_name, sort_index, status, brand_icon ,type, is_recommend from t_shop_brand where status=0 and type = ? order by sort_index ";
		return baseDAO.getGenericBySQL(sql, new Object[]{type});
	}

	@Override
	public List<Map<String, Object>> findByBrandName(String brandName, Integer type) {
		
		String sql = "select id, brand_name, sort_index, status, brand_icon from t_shop_brand where brand_name = ? and type = ?";
		return baseDAO.getGenericBySQL(sql, new Object[]{brandName, type});
	}
	
	
	
}
