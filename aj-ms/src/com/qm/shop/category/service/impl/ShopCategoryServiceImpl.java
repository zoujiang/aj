package com.qm.shop.category.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;
import com.qm.shop.category.service.ShopCategoryService;
import com.qm.shop.category.vo.ShopCategoryVO;

@Service("shopCategoryService")
@Scope("prototype")
public class ShopCategoryServiceImpl  implements ShopCategoryService{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GenericDAO baseDAO;
	
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public DataModel<Map<String, Object>> getList(ShopCategoryVO limitKey) {
		
		String sql = "select id, name, description, sort, status, create_time, icon from t_shop_category where 1=1 ";
		String countSql = "select count(1) from t_shop_category where 1=1 ";
		List<String> param = new ArrayList<String>();
		if(limitKey != null){
			if(limitKey.getName() != null && !"".equals(limitKey.getName().toString())){
				sql += " and name like concat ('%',?,'%')";
				countSql += " and name like concat ('%',?,'%')";
				param.add(limitKey.getName());
			}
		}
		List<Map<String, Object>> list = baseDAO.getGenericByPositionToSQL(sql, limitKey.getOffset(), limitKey.getPageSize(), param.toArray());
		int total = baseDAO.getGenericIntToSQL(countSql, param.toArray());
		return new DataModel<Map<String,Object>>(list, total);
	}

	@Override
	public int addCategory(String id, String name, String description,
			Integer sort, String icon, Integer status) {
		
		String sql = "insert into t_shop_category (id, name, description, sort, status, create_time, icon) values (?,?,?,?,?,?,?)";
		int i =	baseDAO.execteNativeBulk(sql, id, name, description, sort, status, DateUtil.dateFromatYYYYMMddHHmmss(new Date()), icon);
		return i;
	}

	@Override
	public int updateCategoryStatus(ShopCategoryVO limitKey) {
		
		String sql = "update t_shop_category set status = ? where id = ?";
		int i = baseDAO.execteNativeBulk(sql, limitKey.getStatus(), limitKey.getId());
		return i;
	}

	@Override
	public Map<String, Object> findCategoryById(String id) {
		
		String sql = "select id, name, description, sort, status, create_time, icon from t_shop_category where id = ?";
		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new String[]{id});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int updateCategory(String id, String name, String description,
			Integer sort, String icon, Integer status) {
		
		String sql = "update t_shop_category set name = ?, description = ?, sort = ?, status = ? ";
		List<Object> param = new ArrayList<Object>();
		param.add(name);
		param.add(description);
		param.add(sort);
		param.add(status);
		if(icon != null && !"".equals(icon)){
			sql += ", icon = ?";
			param.add(icon);
		}
		sql += " where id = ?";
		param.add(id);
		
		return baseDAO.execteNativeBulk(sql, param.toArray());
	}

	@Override
	public List<Map<String, Object>> selectAll() {

		String sql = "select id, name, description, sort, status, create_time, icon from t_shop_category where status=0 order by sort ";
		return baseDAO.getGenericBySQL(sql, null);
	}
	
	
	
}
