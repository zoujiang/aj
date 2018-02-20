package com.aj.ad.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.ad.bean.AdBean;
import com.aj.ad.bean.AdContentBean;
import com.aj.ad.bean.AdRelationBean;
import com.aj.ad.bean.MsgVo;
import com.aj.ad.bean.PositionBean;
import com.aj.ad.bean.PositionLimitKey;
import com.aj.ad.constant.AdConstants;
import com.aj.ad.service.CmsAdService;
import com.aj.ad.service.CmsPositionService;
import com.frame.core.jdbcdao.BaseDao;
import com.frame.core.service.AuthorService;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;


@Service("cmsPositionService")
public class CmsPositionServiceImpl implements CmsPositionService {

	private Logger logger = Logger.getLogger(CmsPositionServiceImpl.class);
	
	private static final String add_new = "INSERT INTO CMS_AD_POSITION (ID, CODE, NAME, PAGE, TYPE, POSITION, IMAGE, CHANNEL_ID, STATUS,CREATE_USER,CREATE_TIME) VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
	private static final String update = "UPDATE CMS_AD_POSITION SET NAME = ?,PAGE = ?,TYPE = ?,POSITION = ?,IMAGE = ?,CHANNEL_ID = ?,UPDATE_USER = ?,UPDATE_TIME=NOW() WHERE ID = ?";
	private static final String update_online = "UPDATE CMS_AD_POSITION SET ONLINE_USER = ?, ONLINE_TIME = NOW(),STATUS = " + AdConstants.STATUS_ON + " WHERE ID = ?";
	private static final String update_offline = "UPDATE CMS_AD_POSITION SET OFFLINE_USER = ?, OFFLINE_TIME = NOW(),STATUS = " + AdConstants.STATUS_OFF + " WHERE ID = ?";
	private static final String select_max_code = "SELECT MAX(CODE) CODE FROM CMS_AD_POSITION WHERE CHANNEL_ID = ?";
	
	private static final String SQL_query = "SELECT MT.*, CH.NAME CHANNEL_NAME, (SELECT COUNT(1) FROM CMS_AD_ADRELATION WHERE POSITION_ID = MT.ID) AD_COUNT FROM CMS_AD_POSITION MT LEFT JOIN CMS_AD_CHANNEL CH ON MT.CHANNEL_ID = CH.ID WHERE 1=1 ";
	private static final String checkExists = "SELECT 1 FROM CMS_AD_POSITION WHERE CHANNEL_ID = ? AND PAGE = ? AND TYPE = ? AND POSITION = ? ";
	
	//上下线时间外的记录（无效）
	private static final String select_ad_by_posid_offline = "SELECT MT.*, "
										//这里加上5，方便在前台处理待上线状态，前台ad_status-5=1去判断广告内容上下线状态， 再加5判断‘待上线’
										+ " CASE WHEN AD.ONLINE_TIME > NOW() THEN AD.STATUS + 5"  //等待上线,上线时间大于当前时间
										+ "      WHEN AD.OFFLINE_TIME < NOW() THEN 3 "        //已过期，下线时间大于当前时间
										+ "  END AD_STATUS," 
										+ " AD.NAME AD_NAME, C.URL URL, "
										+ " C.TYPE, C.POSITION_TYPE, C.URL,C.IMG_SMALL, C.IMG_BIG,C.TEXT, C.ID C_ID, "
										+ " C.VIDEO, C.RELATION_ID, C.RELATION_TITLE, "
										+ " AD.ONLINE_TIME, AD.OFFLINE_TIME "
										+ " FROM (SELECT * FROM CMS_AD_ADRELATION R WHERE R.POSITION_ID = ?) MT "
									+ " LEFT JOIN CMS_AD_ADCONTENT C ON MT.CONTENT_ID = C.ID "
									+ " LEFT JOIN CMS_AD_AD AD ON C.ADID = AD.ID "
									+ " WHERE AD.ONLINE_TIME > NOW() OR AD.OFFLINE_TIME < NOW() "
									+ " ORDER BY AD_STATUS DESC, AD.OFFLINE_TIME DESC";
	//上下线时间内（有效）
	private static final String select_ad_by_posid_without_offline = "SELECT MT.*, AD.STATUS AD_STATUS, AD.NAME AD_NAME, "
									 + " AD.ONLINE_TIME, AD.OFFLINE_TIME, "
									 + " C.URL URL, C.TYPE, C.POSITION_TYPE, C.URL,C.IMG_SMALL, C.IMG_BIG,C.TEXT, "
									 + " C.VIDEO, C.RELATION_ID, C.RELATION_TITLE, "
									 
									 + " C.ID C_ID FROM (SELECT * FROM CMS_AD_ADRELATION R WHERE R.POSITION_ID = ?) MT "
							         + " LEFT JOIN CMS_AD_ADCONTENT C ON MT.CONTENT_ID = C.ID "
							         + " LEFT JOIN CMS_AD_AD AD ON C.ADID = AD.ID "
							         + " WHERE AD.ONLINE_TIME < NOW() AND AD.OFFLINE_TIME > NOW() "
							         //+ "AND AD.STATUS = 0 AND MT.STATUS = 0 "
							         + " ORDER BY AD.STATUS DESC, MT.STATUS DESC, MT.SEQ ASC";
	
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private CmsAdService cmsAdService;
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public DataModel<PositionBean> getList(PositionLimitKey limitKey, int currentPage, int pageSize) {
		
		String sql = SQL_query;
		
		List<String> paramList = new ArrayList<String>();
		if(StringUtil.isNotEmpty(limitKey.getName())){
			sql += " AND MT.NAME LIKE ? ";
			paramList.add("%" + limitKey.getName() + "%");
		}
		if(StringUtil.isNotEmpty(limitKey.getCode())){
			sql += " AND MT.CODE = ? ";
			paramList.add(limitKey.getCode());
		}

		
		if(StringUtil.isNotEmpty(limitKey.getStatus())){
			sql += " AND MT.STATUS = ? ";
			paramList.add(limitKey.getStatus());
		}
		if(StringUtil.isNotEmpty(limitKey.getType())){  //这个条件现在是广告内容  投放的时候在用
			sql += " AND MT.TYPE = ? ";
			paramList.add(limitKey.getStatus());
		}
		if(StringUtil.isNotEmpty(limitKey.getCreateUser())){
			sql += " AND MT.CREATE_USER = ? ";
			paramList.add(limitKey.getCreateUser());
		}
		
		
		
		if (StringUtil.isNotEmpty(limitKey.getSortColumnName())){
			//sql += " order by "+SortUtil.getChangeSortName(limitKey.getSortColumnName()) +" "+limitKey.getSortType();
		} else {
			sql += " ORDER BY CODE ASC";
		}
		
		Map<String, Object> map = baseDao.page(sql, currentPage, pageSize, paramList.toArray());
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		List<PositionBean> resultList = new ArrayList<PositionBean>();
		
		for(Map<String, Object> row : list){
			PositionBean vo = new PositionBean();
			vo.read(row);
			if (authorService.isCreateUser(vo.getCreateUser()) || authorService.isAdminOptAccount()) {
				vo.setEdit(true);//修改权限
				vo.setDelete(true);//删除
			}
			
			resultList.add(vo);
		}
		return new DataModel<PositionBean>(resultList,(Integer)map.get("totalCount"));
		
	}
	
	//根据广告位类型查询,这里现在是广告内容  投放的时候在用
	public List<PositionBean> getList(String type) {
		String sql = SQL_query;
		
		List<String> paramList = new ArrayList<String>();
		sql += " AND MT.TYPE = ? ";
		paramList.add(type);
		
		List<Map<String, Object>> list = baseDao.query(sql, paramList.toArray());
		List<PositionBean> resultList = new ArrayList<PositionBean>();
		
		for(Map<String, Object> row : list){
			PositionBean vo = new PositionBean();
			vo.read(row);
			resultList.add(vo);
		}
		
		return resultList;
		
	}
	
	public MsgVo online(String id) {
		
		MsgVo msg = checkPosAuthor(id);
		if (msg.isError()) {
			return msg;
		}
		PositionBean bean = getDetail(id);
		
		int i = baseDao.update(update_online, new Object[]{authorService.getSessionUser().getAccount(), id});
		
		if (i > 0) {
			logBizOprService.saveLog("广告位管理","6" , "上线广告位(名称:" + bean.getName() + ")" , null, null);
			return success();
		} else {
			return error();
		}
		
	}
	
	public MsgVo offline(String id) {
		
		MsgVo msg = checkPosAuthor(id);
		if (msg.isError()) {
			return msg;
		}
		
		PositionBean bean = getDetail(id);
		
		int i = baseDao.update(update_offline, new Object[]{authorService.getSessionUser().getAccount(), id});
		
		if (i > 0) {
			logBizOprService.saveLog("广告位管理","6" , "下线广告位(名称:" + bean.getName() + ")" , null, null);
			return success();
		} else {
			return error();
		}
	}
	
	
	public PositionBean getDetail(String id) {
		String sql = SQL_query  + " AND MT.ID = ? ";
		
		List<String> ps = new ArrayList<String>();
		
		ps.add(id);
		
		PositionBean vo = new PositionBean();
		List<Map<String, Object>> list = baseDao.query(sql, ps.toArray());
		if (list != null && list.size() > 0) {
			vo.read(list.get(0));
		}
		
		return vo;
		
	}
	
	public MsgVo save(PositionBean bean) {
		
		boolean flag = false;
		if (isExists(bean)) {
			return error("该广告位已存在，请勿重复增加.");
		}
		if (bean.isNew()) {
			flag = addNew(bean);
			if (flag) {
				logBizOprService.saveLog("广告位管理","1" , "新增广告位(名称:" + bean.getName() + ")" , null, null);
			}
		} else {
			
			MsgVo msg = checkPosAuthor(bean.getId());
			if (msg.isError()) {
				return msg;
			}
			
			flag = update(bean);
			if (flag) {
				logBizOprService.saveLog("广告位管理","2" , "修改广告位(名称:" + bean.getName() + ")" , null, null);
			}
		}
		
		
		return flag ? success() : error();
	}
	
	
	//查询广告位下面的广告-上下线时间以外的（无效的）
	public List<AdRelationBean> queryRelationsInOffline(String posid) {
		List list = baseDao.query(select_ad_by_posid_offline, new Object[]{posid});
		
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<AdRelationBean> beans = new ArrayList<AdRelationBean>();
		for (int i = 0; i < list.size(); i++) {
			Map row = (Map)list.get(i);
			AdRelationBean b = new AdRelationBean();
			b.read(row);
			
			AdContentBean content = new AdContentBean();
			content.read(row);
			content.setAd_name((String)row.get("ad_name"));
			content.setAd_status(((Long)row.get("ad_status")).intValue());
			content.setId((String)row.get("c_id"));
			content.setOnline_time((Date)row.get("online_time"));
			content.setOffline_time((Date)row.get("offline_time"));
			b.setContent(content);
			beans.add(b);
		}
		return beans;
		
	}
	//查询广告位下面的广告-上下线时间内的(有效的)
	public List<AdRelationBean> queryRelations(String posid) {
		List list = baseDao.query(select_ad_by_posid_without_offline, new Object[]{posid});
		
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<AdRelationBean> beans = new ArrayList<AdRelationBean>();
		for (int i = 0; i < list.size(); i++) {
			Map row = (Map)list.get(i);
			AdRelationBean b = new AdRelationBean();
			b.read(row);
			
			AdContentBean content = new AdContentBean();
			content.read(row);
			
			content.setAd_name((String)row.get("ad_name"));
			content.setAd_status((Integer)row.get("ad_status"));
			content.setId((String)row.get("c_id"));
			content.setOnline_time((Date)row.get("online_time"));
			content.setOffline_time((Date)row.get("offline_time"));
			b.setContent(content);
			beans.add(b);
		}
		return beans;
		
	}
	
	//下线投放的广告
	public MsgVo relationOffline(String relationId) {
		
		MsgVo msg = checkRelationPosAuthor(relationId);
		if (msg.isError()) {
			return msg;
		}
		
		String sql = "UPDATE CMS_AD_ADRELATION SET STATUS=?, OFF_USER=?,OFF_TIME=NOW() WHERE ID = ?";
		List<Object> ps = new ArrayList<Object>();
		
		//position_id=?, interval=?, seq=?, status=?, time=?, update_user=?,update_time=NOW() where id = ?
		ps.add(AdConstants.STATUS_OFF);
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(relationId);
		
		int i = baseDao.update(sql, ps.toArray());
		
		if (i > 0) {
			
			//为了日志记录  
			sql = "SELECT * FROM CMS_AD_ADRELATION WHERE ID = ?";
			List list = baseDao.query(sql, new Object[]{relationId});
			if (list == null || list.isEmpty()) {
				return error("投放不存在");
			}
			
			AdRelationBean bean = new AdRelationBean();
			bean.read((Map)list.get(0));
			
			AdContentBean content = cmsAdService.getContent(bean.getContent_id());
			if (content == null) {
				return error("广告内容不存在:" + bean.getContent_id());
			}
			AdBean adBean = cmsAdService.getDetail(content.getAdid());
			PositionBean posBean = getDetail(bean.getPosid());
			
			//为了日志记录--end
			logBizOprService.saveLog("广告位管理", "6", "无效广告投放(广告:" + adBean.getName() + "-" + content.getPosition_type() + ";广告位:" + posBean.getName()+  ")" , null, null);
			
			
			return success();
		} else {
			return error();
		}
		
	}
	//上线投放的广告
	public MsgVo relationOnline(String relationId) {
		
		MsgVo msg = checkRelationPosAuthor(relationId);
		if (msg.isError()) {
			return msg;
		}
		
		String sql = "UPDATE CMS_AD_ADRELATION SET STATUS=?, ON_USER=?,ON_TIME=NOW() WHERE ID = ?";
		List<Object> ps = new ArrayList<Object>();
		
		ps.add(AdConstants.STATUS_ON);
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(relationId);
		
		int i = baseDao.update(sql, ps.toArray());
		
		if (i > 0) {
			
			//为了日志记录  
			sql = "SELECT * FROM CMS_AD_ADRELATION WHERE ID = ?";
			List list = baseDao.query(sql, new Object[]{relationId});
			if (list == null || list.isEmpty()) {
				return error("投放不存在");
			}
			
			AdRelationBean bean = new AdRelationBean();
			bean.read((Map)list.get(0));
			
			AdContentBean content = cmsAdService.getContent(bean.getContent_id());
			if (content == null) {
				return error("广告内容不存在:" + bean.getContent_id());
			}
			AdBean adBean = cmsAdService.getDetail(content.getAdid());
			PositionBean posBean = getDetail(bean.getPosid());
			
			//为了日志记录--end
			logBizOprService.saveLog("广告位管理", "6", "生效广告投放(广告:" + adBean.getName() + "-" + content.getPosition_type() + ";广告位:" + posBean.getName()+  ")" , null, null);
			
			
			return success();
		} else {
			return error();
		}
	}
	//更新，主要是设置排序 播放时间，所以也不用去下线
	public MsgVo relationUpdate(AdRelationBean bean) {
		MsgVo msg = checkRelationPosAuthor(bean.getId());
		if (msg.isError()) {
			return msg;
		}
		
		String sql = "UPDATE CMS_AD_ADRELATION SET SHOW_INTERVAL=?, SEQ=?, UPDATE_USER=?,UPDATE_TIME=NOW() WHERE ID = ?";
		List<Object> ps = new ArrayList<Object>();
		
		ps.add(bean.getInterval());
		ps.add(bean.getSeq());
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(bean.getId());
		
		int i = baseDao.update(sql, ps.toArray());
		
		if (i > 0) {
			
			//为了日志记录  
			sql = "SELECT * FROM CMS_AD_ADRELATION WHERE ID = ?";
			List list = baseDao.query(sql, new Object[]{bean.getId()});
			if (list == null || list.isEmpty()) {
				return error("投放不存在");
			}
			
			bean = new AdRelationBean();
			bean.read((Map)list.get(0));
			
			AdContentBean content = cmsAdService.getContent(bean.getContent_id());
			if (content == null) {
				return error("广告内容不存在:" + bean.getContent_id());
			}
			AdBean adBean = cmsAdService.getDetail(content.getAdid());
			PositionBean posBean = getDetail(bean.getPosid());
			
			//为了日志记录--end
			logBizOprService.saveLog("广告位管理", "2", "调整广告投放(广告:" + adBean.getName() + "-" + content.getPosition_type() + ";广告位:" + posBean.getName()+  ")" , null, null);
			
			return success();
		} else {
			return error();
		}
		
	}
	
	
	private boolean update(PositionBean bean) {
		List<Object> ps = new ArrayList<Object>();
		ps.add(bean.getName());
		ps.add(bean.getPage());
		ps.add(bean.getType());
		ps.add(bean.getPosition());
		ps.add(bean.getImage());
		ps.add(bean.getChannelId());
		ps.add(authorService.getSessionUser().getAccount());
		
		ps.add(bean.getId());
		//保存
		int i = baseDao.update(update, ps.toArray());
		return i > 0;
	}
	private boolean addNew(PositionBean bean) {
		int currentCode = currentCode(bean.getChannelId());
		if (currentCode == -1) {
			logger.error("query currentCode error. appid:" + bean.getChannelId());
			return false;
		}
		currentCode++;
		List<Object> ps = new ArrayList<Object>();
		bean.initId();
		
		bean.setCode(currentCode);
		
		ps.add(bean.getId());
		ps.add(currentCode);
		ps.add(bean.getName());
		ps.add(bean.getPage());
		ps.add(bean.getType());
		ps.add(bean.getPosition());
		ps.add(bean.getImage());
		ps.add(bean.getChannelId());
		ps.add(AdConstants.STATUS_OFF);
		ps.add(authorService.getSessionUser().getAccount());
		//保存
		int i = 0;
		try {
			i = baseDao.update(add_new, ps.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("---update-error" + e.toString());
		}
		return i > 0;
	}
	private int currentCode(String channelId) {
		//取出当前应用最大编码
		List list = baseDao.query(select_max_code, new Object[]{channelId});
		if (list == null || list.isEmpty()) {
			return -1;
		}
		Map row = (Map)list.get(0);
		Object code = row.get("code");
		if (code == null) {
			return 0;
		}
		return (Integer)code;
	}
	
	private boolean isExists(PositionBean bean) {
		List<Object> ps = new ArrayList<Object>();
		ps.add(bean.getChannelId());
		ps.add(bean.getPage());
		ps.add(bean.getType());
		ps.add(bean.getPosition());
		String sql = "";
		if (!StringUtil.isEmpty(bean.getId())) {
			ps.add(bean.getId());
			sql += " and id <> ?";
		}
		
		
		List list = baseDao.query(checkExists + sql, ps.toArray());
		if (list == null || list.isEmpty()) {
			return false;
		}
		return true;
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
	
	private MsgVo checkPosAuthor(String id) {
		PositionBean old = this.getDetail(id);
		if (old == null) {
			return error("没找到相关记录");
		}
		if (!authorService.isCreateUser(old.getCreateUser()) && ! authorService.isAdminOptAccount()) {
			return error("对不起，这个广告位不是你创建的，没有操作权限");
		}
		
		return success();
	}
	
	//检查关联上的广告位权限，要对广告位有权限，才能操作下面的广告
	private MsgVo checkRelationPosAuthor(String relationId) {
		String sql = "SELECT * FROM CMS_AD_ADRELATION WHERE ID = ? ";
		
		List list = baseDao.query(sql, new Object[]{relationId});
		if (list == null || list.isEmpty()) {
			return error("没找到相关投放");
		}
		AdRelationBean bean = new AdRelationBean();
		bean.read((Map)list.get(0));
		
		PositionBean old = this.getDetail(bean.getPosid());
		if (old == null) {
			return error("没找到相关广告位rid:"  + relationId + "--pos:" + bean.getPosid());
		}
		if (!authorService.isCreateUser(old.getCreateUser()) && !authorService.isAdminOptAccount()) {
			return error("对不起，这个广告位不是你创建的，没有操作权限");
		}
		
		return success();
	}
	

}
