package com.aj.ad.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.ad.bean.MsgVo;
import com.aj.ad.bean.PositionMerchantBean;
import com.aj.ad.bean.PositionMerchantLimitKey;
import com.aj.ad.service.CmsPositionService;
import com.aj.ad.service.PositionMerchantAuditService;
import com.frame.core.jdbcdao.BaseDao;
import com.frame.core.service.AuthorService;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;


@Service("positionMerchantAuditService")
public class PositionMerchantAuditServiceImpl implements PositionMerchantAuditService {

	private Logger logger = Logger.getLogger(PositionMerchantAuditServiceImpl.class);
	
	
	private static final String SQL_query = "SELECT MT.*, MCH.MERCHANT_NAME MERCHANT_NAME, AP.CODE POSITION_CODE, "
												+ " AP.NAME POSITION_NAME, AP.IMAGE POSITION_IMAGE FROM VCBGMALLV1.T_MALL_MERCHANT_AD_RELATION MT  "
												+ " LEFT JOIN VCBGMALLV1.T_MALL_MERCHANT MCH ON MT.MERCHANT_ID = MCH.MERCHANT_ID "
												+ " LEFT JOIN CMS_AD_POSITION AP ON MT.AD_ID = AP.ID WHERE MT.STATUS <> 3  ";
	
	
	
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private CmsPositionService cmsPositionService;
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public DataModel<PositionMerchantBean> getList(PositionMerchantLimitKey limitKey, int currentPage, int pageSize) {
		
		String sql = SQL_query;
		
		List<String> paramList = new ArrayList<String>();
		if(StringUtil.isNotEmpty(limitKey.getMerchantName())){
			sql += " AND MCH.MERCHANT_NAME LIKE ? ";
			paramList.add("%" + limitKey.getMerchantName() + "%");
		}
		if(StringUtil.isNotEmpty(limitKey.getPositionName())){
			sql += " AND AP.NAME LIKE ? ";
			paramList.add("%" + limitKey.getPositionName() + "%");
		}
		if(StringUtil.isNotEmpty(limitKey.getPositionCode())){
			sql += " AND AP.CODE = ? ";
			paramList.add(limitKey.getPositionCode());
		}

		if(StringUtil.isNotEmpty(limitKey.getStatus())){
			sql += " AND MT.STATUS = ? ";
			paramList.add(limitKey.getStatus());
		}
		
		sql += " ORDER BY MT.CREATE_DATE DESC";
		
		
		Map<String, Object> map = baseDao.page(sql, currentPage, pageSize, paramList.toArray());
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		List<PositionMerchantBean> resultList = new ArrayList<PositionMerchantBean>();
		
		for(Map<String, Object> row : list){
			PositionMerchantBean vo = new PositionMerchantBean();
			vo.read(row);
			
			resultList.add(vo);
		}
		return new DataModel<PositionMerchantBean>(resultList,(Integer)map.get("totalCount"));
		
	}
	
	public PositionMerchantBean getDetail(String id) {
		String sql = SQL_query  + " AND MT.ID = ? ";
		
		List<String> ps = new ArrayList<String>();
		
		ps.add(id);
		
		PositionMerchantBean vo = new PositionMerchantBean();
		List<Map<String, Object>> list = baseDao.query(sql, ps.toArray());
		if (list != null && list.size() > 0) {
			vo.read(list.get(0));
		}
		
		return vo;
	}
	
	//通过
	public MsgVo pass(PositionMerchantBean bean) {
		String sql = "UPDATE VCBGMALLV1.T_MALL_MERCHANT_AD_RELATION "
						+ " SET STATUS = 1,"
						+ " EFF_START_TIME = ?, "
						+ " EFF_END_TIME = ?, "
						+ " REASON = ?,"
						+ " PASS_USER = ?,"
						+ " PASS_DATE = NOW() "
				       // + " BACK_DATE = ?,"
				       // + " BACK_USER = ? "
				        + " WHERE ID = ? ";
		List<Object> ps = new ArrayList<Object>();
		ps.add(bean.getEffStartTime());
		ps.add(bean.getEffEndTime());
		ps.add(bean.getReason());
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(bean.getId());
		
		int i = baseDao.update(sql, ps.toArray());
		
		if (i > 0) {
			bean = getDetail(bean.getId());
			logBizOprService.saveLog("广告位审核","6" , "通过(商户:" + bean.getMerchantName() + ";广告位:" + bean.getPositionName() + ")" , null, null);
			return success();
		} else {
			return error();
		}
	} 
	//驳回
	public MsgVo back(PositionMerchantBean bean) {
		
		String sql = "UPDATE T_MALL_MERCHANT_AD_RELATION "
				+ " SET STATUS = 4,"
				+ " REASON = ?,"
		        + " BACK_USER = ?, "
		        + " BACK_DATE = NOW()"
		        + " WHERE ID = ? ";
		List<Object> ps = new ArrayList<Object>();
		ps.add(bean.getReason());
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(bean.getId());
		
		int i = baseDao.update(sql, ps.toArray());
		
		if (i > 0) {
			bean = getDetail(bean.getId());
			logBizOprService.saveLog("广告位审核","6" , "驳回(商户:" + bean.getMerchantName() + ";广告位:" + bean.getPositionName() + ")" , null, null);
			return success();
		} else {
			return error();
		}
	}
	
	
	
	
	private MsgVo error() {
		return new MsgVo("操作失败");
	}
	private MsgVo error(String msg) {
		return new MsgVo(msg);
	}
	
	private MsgVo success() {
		MsgVo vo = new MsgVo(true);
		vo.setMsg("操作成功");
		return vo;
	}
	
	private MsgVo success(String msg) {
		MsgVo vo = new MsgVo(true);
		vo.setMsg(msg);
		return vo;
	}
	
	/*private MsgVo checkPosAuthor(String id) {
		PositionBean old = this.getDetail(id);
		if (old == null) {
			return error("没找到相关记录");
		}
		if (!authorService.isCreateUser(old.getCreateUser()) && !authorService.existInRole(SystemConfig.getValue("cms.admin.role"))) {
			return error("对不起，这个广告位不是你创建的，没有操作权限");
		}
		
		return success();
	}*/
	
	
	

}
