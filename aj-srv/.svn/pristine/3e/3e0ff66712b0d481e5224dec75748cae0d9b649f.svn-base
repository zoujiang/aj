package com.aj.ad.service;

import java.util.List;

import com.aj.ad.bean.AdRelationBean;
import com.aj.ad.bean.MsgVo;
import com.aj.ad.bean.PositionBean;
import com.aj.ad.bean.PositionLimitKey;
import com.frame.core.vo.DataModel;

public interface CmsPositionService {
	
	public DataModel<PositionBean> getList(PositionLimitKey limitKey, int currentPage, int pageSize);

	public MsgVo save(PositionBean vo);
	
	public PositionBean getDetail(String id);
	
	public MsgVo online(String id);
	
	public MsgVo offline(String id);
	
	//根据广告位类型查询,这里现在是广告内容  投放的时候在用
	public List<PositionBean> getList(String type) ;
	
	//查询广告位下面的广告-上下线时间以外的（无效的）
	public List<AdRelationBean> queryRelationsInOffline(String posid);
	
	//查询广告位下面的广告-上下线时间内的(有效的)
	public List<AdRelationBean> queryRelations(String posid);
	
	
	//下线投放的广告
	public MsgVo relationOffline(String relationId);
	//上线投放的广告
	public MsgVo relationOnline(String relationId) ;
	
	//更新，主要是设置排序 播放时间，所以也不用去下线
	public MsgVo relationUpdate(AdRelationBean bean) ;


}
