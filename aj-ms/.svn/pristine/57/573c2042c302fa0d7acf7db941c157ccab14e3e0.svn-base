package com.qm.shop.customer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;
import com.qm.shop.customer.service.ShopCustomerService;
import com.qm.shop.customer.vo.ShopCustomerVO;

@Service("shopCustomerService")
@Scope("prototype")
public class ShopCustomerServiceImpl  implements ShopCustomerService{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GenericDAO baseDAO;
	
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public DataModel<Map<String, Object>> getList(ShopCustomerVO limitKey) {
		
		String sql = "SELECT  u.id,"+
					  "(SELECT  a.account FROM t_sys_user a WHERE a.shop_id = s.id AND ISENABLED = '1' and ACCOUNT_TYPE = '4' ORDER BY create_dt LIMIT 0,1)  shopAccountName,"+
					 " s.shop_name shopName,"+
					 " s.id shopId,"+
					 " u.user_tel userTel,"+
					 " u.user_name shopCustomerName "+
					 " FROM "+
					 "  t_shop_customer_user u,"+
					 "  t_shop_info s "+
					 " WHERE u.shop_id = s.id ";
		String countSql = "SELECT COUNT(1) FROM t_shop_customer_user u, t_shop_info s WHERE u.shop_id = s.id ";
		List<String> param = new ArrayList<String>();
		if(limitKey != null){
			if(limitKey.getShopName() != null && !"".equals(limitKey.getShopName().toString())){
				sql += " and s.shop_name like concat ('%',?,'%')";
				countSql += " and s.shop_name like concat ('%',?,'%')";
				param.add(limitKey.getShopName());
			}
			if(limitKey.getShopId() != null && !"".equals(limitKey.getShopId().toString())){
				sql += " and s.id = ?";
				countSql += " and s.id = ? ";
				param.add(limitKey.getShopId());
			}
			if(limitKey.getUserName() != null && !"".equals(limitKey.getUserName().toString())){
				sql += " and u.user_name like concat ('%',?,'%')";
				countSql += " and u.user_name like concat ('%',?,'%')";
				param.add(limitKey.getUserName());
			}
		}
		List<Map<String, Object>> list = baseDAO.getGenericByPositionToSQL(sql, limitKey.getOffset(), limitKey.getPageSize(), param.toArray());
		int total = baseDAO.getGenericIntToSQL(countSql, param.toArray());
		return new DataModel<Map<String,Object>>(list, total);
	}

	@Override
	public int save(ShopCustomerVO limitKey) {
		String sql = "INSERT INTO t_shop_customer_user (id, shop_id, user_id, user_name, user_tel, create_time) VALUES (?,?,?,?,?,?)";
		
		return baseDAO.execteNativeBulk(sql, limitKey.getId(), limitKey.getShopId(), limitKey.getUserId(), limitKey.getUserName(), limitKey.getUserTel(), limitKey.getCreateTime());
	}

	@Override
	public Map<String, Object> findAccountByUserTelAndShopId(String userTel,String shopId) {
		String sql = "SELECT id, shop_id shopId, user_id userId, user_name userName, user_tel userTel , create_time createTime FROM t_shop_customer_user WHERE user_tel = ? AND shop_id = ?";
		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new Object[]{userTel, shopId});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> findAppUserByUserTel(String userTel) {
		
		String sql = "SELECT id FROM t_user  WHERE  usertel = ?";
		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new Object[]{userTel});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int saveRegistRegInfo(String id, String tokenId, String userTel) {
		
		String sql = "INSERT INTO t_custom_reg (id, SMS_TOKEN_ID, USER_TEL, SMS_CODE, CREATE_DT) VALUES (?,?,?,?,?);";
		return baseDAO.execteNativeBulk(sql, id, tokenId, userTel, "123456", new Date());
	}

	@Override
	public List<Map<String, Object>> getAll(String shopId, String userTel) {
		
		List<String> param = new ArrayList<String>();
		String sql = "select id, user_tel userTel, user_name userName from  t_shop_customer_user where shop_id = ?";
		param.add(shopId);
		if(userTel != null && !"".equals(userTel)){
			sql += " and user_tel like concat('',? ,'%')";
			param.add(userTel);
		}
		sql += " order by user_tel";
		return baseDAO.getGenericBySQL(sql, param.toArray());
	}
	@Override
	public Map<String, Object> findCustomerById(String userId) {
		
		String sql = "select id, user_tel userTel, user_name userName from  t_shop_customer_user where id = ?";
		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new Object[]{userId});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
}
