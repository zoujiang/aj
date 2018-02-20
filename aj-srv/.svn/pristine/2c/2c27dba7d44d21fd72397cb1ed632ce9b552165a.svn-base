package com.aj.ad.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.ad.bean.AdBean;
import com.aj.ad.bean.AdContentBean;
import com.aj.ad.bean.AdLimitKey;
import com.aj.ad.bean.AdRelationBean;
import com.aj.ad.bean.MsgVo;
import com.aj.ad.bean.PositionBean;
import com.aj.ad.constant.AdConstants;
import com.aj.ad.service.CmsAdService;
import com.aj.ad.service.CmsPositionService;
import com.frame.core.jdbcdao.BaseDao;
import com.frame.core.service.AuthorService;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;


@Service("cmsAdService")
public class CmsAdServiceImpl implements CmsAdService {
	private static final Logger logger = Logger.getLogger(CmsAdServiceImpl.class);
	
	private static final String SQL_add = "INSERT INTO CMS_AD_AD (ID, NAME, ONLINE_TIME, OFFLINE_TIME, TYPE, STATUS, CREATE_USER,CREATE_TIME) VALUES (?, ?, ?, ?, ?, ?,?, NOW())";
	private static final String SQL_update = "UPDATE CMS_AD_AD SET NAME = ?, ONLINE_TIME = ?, OFFLINE_TIME = ?, TYPE = ?,STATUS=?, UPDATE_USER = ?, UPDATE_TIME=NOW() WHERE ID = ?";
	
	private static final String select = "SELECT ID, NAME, ONLINE_TIME, OFFLINE_TIME, TYPE, STATUS,CREATE_USER FROM CMS_AD_AD MT WHERE STATUS <> " + AdConstants.STATUS_DEL;
	
	private static final String checkExists = "SELECT 1 FROM CMS_AD_AD WHERE NAME = ? ";
	private static final String del_ad = "DELETE FROM CMS_AD_AD WHERE ID = ? ";
	private static final String del_content = "DELETE FROM CMS_AD_ADCONTENT WHERE ADID = ? ";
	//private static final String del_relations = "delete cms_ad_adrelation where content_id in (select content_id from cms_ad_adcontent where adid = ? and position_type = ?) ";
	private static final String del_relations = "DELETE FROM CMS_AD_ADRELATION WHERE CONTENT_ID IN (SELECT ID FROM CMS_AD_ADCONTENT WHERE ADID = ?) ";
	private static final String del_relation = "DELETE FROM CMS_AD_ADRELATION WHERE ID = ? ";
	private static final String insert_content = "INSERT INTO CMS_AD_ADCONTENT (ID, ADID, TYPE, POSITION_TYPE, URL, IMG_SMALL, IMG_BIG, TEXT, VIDEO, RELATION_ID, RELATION_TITLE, CREATE_USER,CREATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
	private static final String update_content = "UPDATE CMS_AD_ADCONTENT SET TYPE=?, URL=?, IMG_SMALL=?, IMG_BIG=?, TEXT=?, VIDEO=?, RELATION_ID=?, RELATION_TITLE=?, UPDATE_USER=?,UPDATE_TIME=NOW() WHERE ID = ?"; 
	private static final String insert_relation = "INSERT INTO CMS_AD_ADRELATION (ID, POSITION_ID, CONTENT_ID, SHOW_INTERVAL, SEQ, STATUS, TIME, CREATE_USER,CREATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
	private static final String update_relation = "UPDATE CMS_AD_ADRELATION SET POSITION_ID=?, SHOW_INTERVAL=?, SEQ=?, STATUS=?, TIME=?,OFF_USER=?,OFF_TIME=NOW(), UPDATE_USER=?,UPDATE_TIME=NOW() WHERE ID = ?";
	
	private static final String select_ad_content = "SELECT * FROM CMS_AD_ADCONTENT WHERE ID = ?";
	private static final String select_ad_content_by_ad_postype = "SELECT * FROM CMS_AD_ADCONTENT WHERE ADID = ? AND POSITION_TYPE = ?";
	private static final String select_ad_content_by_ad = "SELECT * FROM CMS_AD_ADCONTENT WHERE ADID = ?";
	//private static final String select_relations_by_content = "select * from cms_ad_adrelation where content_id = ?";
	private static final String select_relations_by_content = "SELECT MT.*, P.NAME POS_NAME FROM CMS_AD_ADRELATION MT LEFT JOIN CMS_AD_POSITION P ON P.ID = MT.POSITION_ID WHERE CONTENT_ID = ?";
	
	private static final String update_online = "UPDATE CMS_AD_AD SET ON_USER = ?, ON_TIME = NOW(),STATUS = " + AdConstants.STATUS_ON + " WHERE ID = ?";
	private static final String update_offline = "UPDATE CMS_AD_AD SET OFF_USER = ?, OFF_TIME = NOW(),STATUS = " + AdConstants.STATUS_OFF + " WHERE ID = ?";
	
	
	@Autowired
	private BaseDao baseDao = null;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private CmsPositionService cmsPositionService;
	
	@Autowired
	private LogBizOprService logBizOprService;
	
	public MsgVo save(AdBean bean) {
		boolean flag = false;
		if (isExists(bean)) {
			return new MsgVo(MsgVo.CODE_ERROR, "广告名已存在，请勿重复增加.");
		}
		if (bean.isNew()) {
			flag = addNew(bean);
			if (flag) {
				logBizOprService.saveLog("广告管理","1" , "新增广告(广告名称:" + bean.getName() + ")" , null, null);
			}
		} else {
			
			MsgVo msg = checkAdAuthor(bean.getId());
			if (msg.isError()) {
				return msg;
			}
			
			flag = update(bean);
			if (flag) {
				logBizOprService.saveLog("广告管理", "2", "修改广告(广告名称:" + bean.getName() + ")" , null, null);
			}
		}
		
		if (flag) {
			return success();
		} else {
			return error();
		}
		
	}
	
	public DataModel<AdBean> getList(AdLimitKey limitKey, int currentPage, int pageSize) {
		String sql = select;
		
		List<String> paramList = new ArrayList<String>();
		if(StringUtil.isNotEmpty(limitKey.getName())){
			sql += " and MT.NAME LIKE ? ";
			paramList.add("%" + limitKey.getName() + "%");
		}
		
		if(StringUtil.isNotEmpty(limitKey.getStatus())){
			sql += " AND MT.STATUS = ? ";
			paramList.add(limitKey.getStatus());
		}
		
		/*if (!authorService.existInRole(SystemConfig.getValue("otos.admin.role"))) {
			sql += "AND MT.CREATE_USER = ? ";
			paramList.add(authorService.getSessionUser().getAccount());
		}*/
		if (StringUtil.isNotEmpty(limitKey.getSortColumnName())){
			//sql += " order by "+SortUtil.getChangeSortName(limitKey.getSortColumnName()) +" "+limitKey.getSortType();
		} else {
			sql += " ORDER BY CREATE_TIME DESC";
		}
		
		Map<String, Object> map = baseDao.page(sql, currentPage, pageSize, paramList.toArray());
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		List<AdBean> resultList = new ArrayList<AdBean>();
		
		for(Map<String, Object> row : list){
			AdBean vo = new AdBean();
			vo.read(row);
			if (authorService.isCreateUser(vo.getCreateUser()) || authorService.isAdminOptAccount()) {
				vo.setEdit(true);//修改权限
				vo.setDelete(true);//删除
			}
			initContent(vo); //加载广告内容 与投放情况
			
			resultList.add(vo);
		}
		return new DataModel<AdBean>(resultList,(Integer)map.get("totalCount"));
	}
	
	//获取详情
	public AdBean getDetail(String id) {
		
		String sql = select + " AND MT.ID = ?";
		List list = baseDao.query(sql, new Object[]{id});
		if (list == null || list.isEmpty()) {
			return null;
		}
		AdBean bean = new AdBean();
		bean.read((Map)list.get(0));
		
		initContent(bean); //加载广告内容 与投放情况
		
		return bean;
	}	
	
	
	public MsgVo online(String id) {
		
		AdBean bean = getDetail(id);
		if (bean == null) {
			return error("没找到相关记录");
		}
		if (!authorService.isCreateUser(bean.getCreateUser()) && !authorService.isAdminOptAccount()) {
			return error("对不起，这个广告不是你创建的，没有操作权限");
		}
		
		int i = baseDao.update(update_online, new Object[]{authorService.getSessionUser().getAccount(), id});
		
		
		if (i > 0) {
			logBizOprService.saveLog("广告管理", "6", "上线广告(广告名称:" + bean.getName() + ")" , null, null);
			return success();
		} else {
			return error();
		}
		
	}
	
	public MsgVo offline(String id) {
		AdBean bean = getDetail(id);
		if (bean == null) {
			return error("没找到相关记录");
		}
		if (!authorService.isCreateUser(bean.getCreateUser()) && !authorService.isAdminOptAccount()) {
			return error("对不起，这个广告不是你创建的，没有操作权限");
		}
		
		int i = baseDao.update(update_offline, new Object[]{authorService.getSessionUser().getAccount(), id});
		
		if (i > 0) {
			logBizOprService.saveLog("广告管理", "6", "下线广告(广告名称:" + bean.getName() + ")" , null, null);
			//设置每个广告位上的广告为无效
			String sql = "UPDATE CMS_AD_ADRELATION SET STATUS = "+ AdConstants.STATUS_OFF + " WHERE CONTENT_ID IN (SELECT ID FROM CMS_AD_ADCONTENT C WHERE C.ADID = ?)";
			i = baseDao.update(sql, new Object[]{id});
			//logBizOprService.saveLog("广告管理", "9", "下线广告-自动设置广告位上的广告为无效(广告名称:" + bean.getName() + ")" , null, null);
			return success();
		} else {
			return error();
		}
	}
	
	//删除广告  现在没有用这个方法
	public boolean delete(String adid) {
		AdBean bean = getDetail(adid);
		if (bean == null) {
			return false;
		}
		delChild(adid);
		int row = baseDao.update(del_ad, new Object[]{adid});
		
		logBizOprService.saveLog("广告管理", "3", "删除广告(广告名称:" + bean.getName() + ")" , null, null);
		return row > 0;
	}
	
	
	//根据ID 查询广告内容
	public AdContentBean getContent(String contentId) {
		List list = baseDao.query(select_ad_content, new Object[]{contentId});
		if (list == null || list.isEmpty()) {
			return null;
		}
		AdContentBean bean = new AdContentBean();
		Map row = (Map)list.get(0);
		bean.read(row);
		//bean.setRels(queryRelations(bean.getId())); //加载内容的投放情况
		return bean;
	}
	
	public MsgVo saveContent(AdContentBean bean) {
		MsgVo msg = checkAdAuthor(bean.getAdid());
		if (msg.isError()) {
			return msg;
		}
		
		AdBean adBean = getDetail(bean.getAdid());
		
		if (bean.isNew()) {
			msg = addContent(bean);
			if (msg.isSuccess()) {
				logBizOprService.saveLog("广告管理", "1", "新增广告内容(广告:" + adBean.getName() + "-" + bean.getPosition_type() + ")" , null, null);
			}
		} else {
			msg = updateContent(bean);
			if (msg.isError()) {
				return msg;
			}
			logBizOprService.saveLog("广告管理", "2", "修改广告内容(广告:" + adBean.getName() + "-" + bean.getPosition_type() +  ")" , null, null);
			//广告位上的广告下线
			String sql = "UPDATE CMS_AD_ADRELATION SET STATUS = 0  WHERE CONTENT_ID = ? ";
			int i = baseDao.update(sql, new Object[]{bean.getId()});
			if (i > 0) {
				return success();
			} else {
				return success();
				//有可能存在没有投放的情况
				//return error("广告在广告位中下线失败");
			}
			
			
		}
		
		return msg;
		
	}
	
	//保存广告内容 与广告位关系
	public MsgVo saveRelation(AdRelationBean bean) {
		
		AdContentBean content = getContent(bean.getContent_id());
		if (content == null) {
			return error("广告内容不存在:" + bean.getContent_id());
		}
		MsgVo msg = checkAdAuthor(content.getAdid());
		if (msg.isError()) {
			return msg;
		}
		
		if (isRelationExists(bean)) {
			return error("广告已经投放在这个广告位上了");
		}
		
		AdBean adBean = getDetail(content.getAdid());
		PositionBean posBean = cmsPositionService.getDetail(bean.getPosid());
		
		if (bean.isNew()) {
			msg = addRelation(bean);
			if (msg.isSuccess()) {
				logBizOprService.saveLog("广告管理", "1", "新增广告投放(广告:" + adBean.getName() + "-" + content.getPosition_type() + ";广告位:" + posBean.getName()+ ")" , null, null);
			}
		} else {
			msg = updateRelation(bean);
			if (msg.isSuccess()) {
				logBizOprService.saveLog("广告管理", "2", "修改广告投放(广告:" + adBean.getName() + "-" + content.getPosition_type() + ";广告位:" + posBean.getName()+  ")" , null, null);
			}
		}
		return msg;
		
	}
	
	//删除投放
	public MsgVo delRelation(String relationId) {
		
		//为了日志记录  删除前出取来
		String sql = "SELECT * FROM CMS_AD_ADRELATION WHERE ID = ?";
		List list = baseDao.query(sql, new Object[]{relationId});
		if (list == null || list.isEmpty()) {
			return error("投放不存在");
		}
		
		AdRelationBean bean = new AdRelationBean();
		bean.read((Map)list.get(0));
		
		AdContentBean content = getContent(bean.getContent_id());
		if (content == null) {
			return error("广告内容不存在:" + bean.getContent_id());
		}
		AdBean adBean = getDetail(content.getAdid());
		//为了日志记录--end
		
		int i = baseDao.update(del_relation, new Object[]{relationId});
		
		if (i > 0) {
			
			PositionBean posBean = cmsPositionService.getDetail(bean.getPosid());
			
			logBizOprService.saveLog("广告管理", "3", "删除广告投放(广告:" + adBean.getName() + "-" + content.getPosition_type() + ";广告位:" + posBean.getName()+  ")" , null, JSONObject.fromObject(bean).toString());
			return success();
		} else {
			return error();
		}
		
	}
	
	private MsgVo addRelation(AdRelationBean bean) {
		List<Object> ps = new ArrayList<Object>();
		
		bean.initId();
		ps.add(bean.getId());
		ps.add(bean.getPosid());
		ps.add(bean.getContent_id());
		ps.add(bean.getInterval());
		ps.add(bean.getSeq());
		ps.add(AdConstants.STATUS_OFF);
		ps.add(bean.getTime());
		ps.add(authorService.getSessionUser().getAccount());
		
		int i = baseDao.update(insert_relation, ps.toArray());
		
		if (i > 0) {
			return success();
		} else {
			return error();
		}
	}
	
	private MsgVo updateRelation(AdRelationBean bean) {
		List<Object> ps = new ArrayList<Object>();
		
		//position_id=?, interval=?, seq=?, status=?, time=?, update_user=?,update_time=NOW() where id = ?
		ps.add(bean.getPosid());
		ps.add(bean.getInterval());
		ps.add(bean.getSeq());
		ps.add(AdConstants.STATUS_OFF);
		ps.add(bean.getTime());
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(bean.getId());
		
		int i = baseDao.update(update_relation, ps.toArray());
		
		if (i > 0) {
			return success();
		} else {
			return error();
		}
	}
	
	
	private MsgVo addContent(AdContentBean c) {
		
		MsgVo msg = checkAdAuthor(c.getAdid());
		if (msg.isError()) {
			return msg;
		}
		
		AdContentBean temp = getContent(c.getAdid(), c.getType());
		
		if (temp != null) {
			return error("广告已经有" + c.getType() + "的内容了");
		}
		
		List<Object> ps = new ArrayList<Object>();
		
		c.initId();
		ps.add(c.getId());
		ps.add(c.getAdid());
		ps.add(c.getType());
		ps.add(c.getPosition_type());
		ps.add(c.getUrl());
		ps.add(c.getImg_small());
		ps.add(c.getImg_big());
		ps.add(c.getText());
		ps.add(c.getVideo());
		ps.add(c.getRelation_id());
		ps.add(c.getRelation_title());
		ps.add(authorService.getSessionUser().getAccount());
	
		int i = baseDao.update(insert_content, ps.toArray());
		
		if (i > 0) {
			return success();
		} else {
			return error();
		}
	
	}
	
	//更新内容
	private MsgVo updateContent(AdContentBean c) {
		
		AdContentBean temp = getContent(c.getAdid(), c.getPosition_type());
		
		if (temp == null) {
			return error("广告内容不存在" + c.getPosition_type());
		} else if (!temp.getId().equals(c.getId())) {
			return error("广告内容不匹配old:" + temp.getId() + "--new:" + c.getId());
		}
		
		//type=?, url=?, img_small=?, img_big=?, text=?, video=?, relation_id=?, relation_title=?, update_user=?
		List<Object> ps = new ArrayList<Object>();
		
		//ps.add(c.getAdid());
		ps.add(c.getType());
		//ps.add(c.getPosition_type());
		ps.add(c.getUrl());
		ps.add(c.getImg_small());
		ps.add(c.getImg_big());
		ps.add(c.getText());
		ps.add(c.getVideo());
		ps.add(c.getRelation_id());
		ps.add(c.getRelation_title());
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(c.getId());
	
		int i = baseDao.update(update_content, ps.toArray());
		
		if (i > 0) {
			return success();
		} else {
			return error();
		}
	
	}
	
	//根据广告ID， 内容类型 查询广告内容
	private AdContentBean getContent(String adId, String postionTtype) {
		List list = baseDao.query(select_ad_content_by_ad_postype, new Object[]{adId, postionTtype});
		if (list == null || list.isEmpty()) {
			return null;
		}
		AdContentBean bean = new AdContentBean();
		Map row = (Map)list.get(0);
		bean.read(row);
		//bean.setRels(queryRelations(bean.getId())); //加载内容的投放情况
		return bean;
	}
	
	//根据广告ID 查询广告内容,同时加载内容的投放情况
	public List<AdContentBean> queryContents(String adid) {
		List list = baseDao.query(select_ad_content_by_ad, new Object[]{adid});
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<AdContentBean> beans = new ArrayList<AdContentBean>();
		for (int i = 0; i < list.size(); i++) {
			Map row = (Map)list.get(i);
			AdContentBean bean = new AdContentBean();
			bean.read(row);
			bean.setRels(queryRelations(bean.getId())); //加载内容的投放情况
			beans.add(bean);
		}
		
		return beans;
	}
	//根据广告内容 查询广告的投放情况
	public List<AdRelationBean> queryRelations(String contentId) {
		List list = baseDao.query(select_relations_by_content, new Object[]{contentId});
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<AdRelationBean> rels = new ArrayList<AdRelationBean>();
		for (int i = 0; i < list.size(); i++) {
			AdRelationBean bean = new AdRelationBean();
			Map row = (Map)list.get(i);
			bean.read(row);
			bean.setPos_name((String)row.get("pos_name"));
			
			if (authorService.isCreateUser(bean.getCreate_user()) || authorService.isAdminOptAccount()) {
				bean.setEdit(true);//修改权限
			}
			
			rels.add(bean);
		}
		
		return rels;
	}
	
	private void initContent(AdBean bean) {
		if (bean == null) {
			return;
		}
		List<AdContentBean> cs = queryContents(bean.getId());
		if (cs == null) {
			return;
		}
		for (AdContentBean c : cs) {
			if (AdConstants.ios.equals(c.getPosition_type())) {
				bean.setIos(c);
			} else if (AdConstants.android.equals(c.getPosition_type())) {
				bean.setAndroid(c);
			} else if (AdConstants.wxh5.equals(c.getPosition_type())) {
				bean.setWxh5(c);
			} 
		}
	}
	
	private boolean update(AdBean bean) {
		/*name = ?, online_time = ?, offline_time = ?, type = ? where id = ?*/
		
		List ps = new ArrayList();
		ps.add(bean.getName());
		ps.add(bean.getOnline_time());
		ps.add(bean.getOffline_time());
		ps.add(bean.getType());
		ps.add(AdConstants.STATUS_OFF);
		ps.add(authorService.getSessionUser().getAccount());
		ps.add(bean.getId());
		
		//更新
		int i = baseDao.update(SQL_update, ps.toArray());
		return i > 0;
	}
	
	private boolean addNew(AdBean bean) {
		//v_id, v_name, v_online_time, v_offline_time, v_type, v_status
		List ps = new ArrayList();
		bean.initId();
		ps.add(bean.getId());
		ps.add(bean.getName());
		ps.add(bean.getOnline_time());
		ps.add(bean.getOffline_time());
		ps.add(bean.getType());
		ps.add(AdConstants.STATUS_OFF);
		ps.add(authorService.getSessionUser().getAccount());
		//保存
		int i = baseDao.update(SQL_add, ps.toArray());
		return i > 0;
	}
	
	//删除与广告有关的记录
	private void delChild(String adid) {
		int row = baseDao.update(del_relations, new Object[]{adid});
		logger.info("del_relations:" + row + "---adid:" + adid);
		row = baseDao.update(del_content, new Object[]{adid});
		logger.info("del_content:" + row + "---adid:" + adid);
	}
	
	private void saveAdRelations(List<AdRelationBean> list) {
		
		
		if (list == null || list.isEmpty()) {
			return;
		}
		
		List<Map<String, Object>> ps = new ArrayList<Map<String, Object>>();
		
		for (AdRelationBean bean : list) {
			Map<String, Object> row = new LinkedHashMap<String, Object>();
			
			bean.initId();
			row.put("1", bean.getId());
			row.put("2", bean.getPosid());
			row.put("3", bean.getContent_id());
			row.put("4", bean.getInterval());
			row.put("5", bean.getSeq());
			row.put("6", AdConstants.STATUS_OFF);
			row.put("7", bean.getTime());
			
			ps.add(row);
		}
		
		baseDao.batchUpate(insert_content, ps);
		
		
		//广告位关系表
        //v_id, v_position_id, v_content_id, v_interval, v_seq, v_status, v_time
		/*final List<AdRelationBean> tempList = list;
        int[] _rows = baseDao.batchUpdate(insert_relations, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return tempList.size();
            }
            public void setValues(PreparedStatement ps, int i)throws SQLException {
            	//v_id, v_adid, v_type, v_position_type, v_url, v_img, v_img_2, v_content
            	AdRelationBean bean = (AdRelationBean) tempList.get(i);
            	bean.initId();
                ps.setString(1, bean.getId());
                ps.setString(2, bean.getPosid());
                ps.setString(3, bean.getContent_id());
                ps.setInt(4, bean.getInterval());
                ps.setInt(5, bean.getSeq());
                ps.setInt(6, AdConstants.STATUS_OFF);
                ps.setString(7, bean.getTime());
            }
        });*/
	}
	
	private boolean isExists(AdBean bean) {
		List ps = new ArrayList();
		ps.add(bean.getName());
		String sql = "";
		if (!StringUtil.isEmpty(bean.getId())) {
			ps.add(bean.getId());
			sql += " AND ID <> ?";
		}
		
		List list = baseDao.query(checkExists + sql, ps.toArray());
		if (list == null || list.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private boolean isRelationExists(AdRelationBean bean) {
		
		String sql = "SELECT * FROM CMS_AD_ADRELATION WHERE POSITION_ID = ? AND CONTENT_ID = ? ";
		
		List ps = new ArrayList();
		ps.add(bean.getPosid());
		ps.add(bean.getContent_id());
		
		if (!StringUtil.isEmpty(bean.getId())) {
			ps.add(bean.getId());
			sql += " AND ID <> ?";
		}
		
		List list = baseDao.query(sql, ps.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
		
	}

	public BaseDao getbaseDao() {
		return baseDao;
	}

	public void setbaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
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
	
	private MsgVo checkAdAuthor(String id) {
		AdBean old = getDetail(id);
		if (old == null) {
			return error("没找到相关记录");
		}
		if (!authorService.isCreateUser(old.getCreateUser()) && !authorService.isAdminOptAccount()) {
			return error("对不起，这个广告不是你创建的，没有操作权限");
		}
		
		return success();
	}
	
}
