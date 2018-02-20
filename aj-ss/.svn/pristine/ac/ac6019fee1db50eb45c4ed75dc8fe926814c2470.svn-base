package com.qm.shop.comment.service.impl;

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
import com.qm.shop.comment.service.ShopCommentService;
import com.qm.shop.comment.vo.ShopCommentVO;
import com.qm.shop.customer.service.ShopCustomerService;
import com.qm.shop.customer.vo.ShopCustomerVO;

@Service("shopCommentService")
@Scope("prototype")
public class ShopCommentServiceImpl  implements ShopCommentService{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GenericDAO baseDAO;
	
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public DataModel<Map<String, Object>> getList(ShopCommentVO limitKey) {
		
		String sql = "SELECT c.id, u.NICK_NAME nickName, i.shop_name shopName, u.USERTEL userTel, c.score, c.cmt_content cmtContent, c.cmt_img cmtImg, c.type , c.cmt_time cmtTime, c.status FROM t_shop_comment c , t_user u, t_shop_info i WHERE c.cmt_user_id = u.id  and c.cmt_shop_id = i.id ";
		String countSql = "SELECT COUNT(1) FROM t_shop_comment c, t_user u, t_shop_info i WHERE c.cmt_user_id = u.id  and c.cmt_shop_id = i.id ";
		List<String> param = new ArrayList<String>();
		if(limitKey != null){
			if(limitKey.getShopName() != null && !"".equals(limitKey.getShopName().toString())){
				sql += " and i.shop_name like concat ('%',?,'%')";
				countSql += " and i.shop_name like concat ('%',?,'%')";
				param.add(limitKey.getShopName());
			}
			if(limitKey.getCmtUserName() != null && !"".equals(limitKey.getCmtUserName().toString())){
				sql += " and u.NICK_NAME like concat ('%',?,'%')";
				countSql += " and u.NICK_NAME like concat ('%',?,'%')";
				param.add(limitKey.getCmtUserName());
			}
			if(limitKey.getBeginTime() != null && !"".equals(limitKey.getBeginTime().toString())){
				sql += " and c.cmt_time > ?";
				countSql += " and c.cmt_time > ?";
				param.add(limitKey.getBeginTime()+" 00:00:00");
			}
			if(limitKey.getEndTime() != null && !"".equals(limitKey.getEndTime().toString())){
				sql += " and c.cmt_time < ?";
				countSql += "and c.cmt_time < ?";
				param.add(limitKey.getEndTime()+" 23:59:59");
			}
		}
		 sql += "ORDER BY cmt_time DESC ";
		List<Map<String, Object>> list = baseDAO.getGenericByPositionToSQL(sql, limitKey.getOffset(), limitKey.getPageSize(), param.toArray());
		int total = baseDAO.getGenericIntToSQL(countSql, param.toArray());
		return new DataModel<Map<String,Object>>(list, total);
	}

	@Override
	public int updateCommentStatus(String id, String status) {
		
		
		String sql = "update t_shop_comment set status = ? where id = ?";
		return baseDAO.execteNativeBulk(sql, status, id);
	}


}
