package com.aj.ad.service;

import com.aj.ad.bean.MsgVo;
import com.aj.ad.bean.PositionMerchantBean;
import com.aj.ad.bean.PositionMerchantLimitKey;
import com.frame.core.vo.DataModel;

public interface PositionMerchantAuditService {
	
	public DataModel<PositionMerchantBean> getList(PositionMerchantLimitKey limitKey, int currentPage, int pageSize);
	public PositionMerchantBean getDetail(String id);
	
	//通过
	public MsgVo pass(PositionMerchantBean bean);
	//驳回
	public MsgVo back(PositionMerchantBean bean);
	

}
