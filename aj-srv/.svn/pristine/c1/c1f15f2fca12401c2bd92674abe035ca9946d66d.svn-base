package com.aj.ad.service;

import java.util.List;

import com.aj.ad.bean.AdBean;
import com.aj.ad.bean.AdContentBean;
import com.aj.ad.bean.AdLimitKey;
import com.aj.ad.bean.AdRelationBean;
import com.aj.ad.bean.MsgVo;
import com.frame.core.vo.DataModel;

public interface CmsAdService {
	
	public DataModel<AdBean> getList(AdLimitKey limitKey, int currentPage, int pageSize);

	//public MsgVo save(PositionBean vo);
	
	public AdBean getDetail(String id);
	
	public MsgVo online(String id);
	
	public MsgVo offline(String id);

	public MsgVo save(AdBean vo);
	
	//根据ID 查询广告内容
	public AdContentBean getContent(String contentId);

	public MsgVo saveContent(AdContentBean vo);

	
	public MsgVo saveRelation(AdRelationBean vo);
	
	//根据广告内容 查询广告的投放情况
	public List<AdRelationBean> queryRelations(String contentId);

	//删除投放
	public MsgVo delRelation(String relationId);
	


}
