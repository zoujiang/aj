package com.aj.ad.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.ad.bean.StatisticsBean;
import com.aj.ad.bean.StatisticsItemBean;
import com.aj.ad.bean.StatisticsLimitKey;
import com.aj.ad.service.CmsAdStatisticsService;
import com.frame.core.jdbcdao.BaseDao;
import com.frame.core.service.AuthorService;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;


@Service("cmsAdStatisticsService")
public class CmsAdStatisticsServiceImpl implements CmsAdStatisticsService {
	private static final Logger logger = Logger.getLogger(CmsAdStatisticsServiceImpl.class);
	
	@Autowired
	private BaseDao baseDao = null;
	
	@Autowired
	private AuthorService authorService;

	
	public DataModel<StatisticsItemBean> getList(StatisticsLimitKey limitKey) {
		
		//String sql = "select * from cms_ad_accesslog where 1=1 ";
		String sql = " FROM CMS_AD_ACCESSLOG WHERE 1=1 ";
		
		List<String> paramList = new ArrayList<String>();
		if(StringUtil.isNotEmpty(limitKey.getStartTimeParse())){
			sql += " AND CREATE_TIME >= TO_DATE('" + limitKey.getStartTimeParse() + "','YYYY-MM-DD')";
		}
		if(StringUtil.isNotEmpty(limitKey.getEndTimeParse())){
			//sql += " and p.post_time <= to_date('2013-12-18 17:00:00','yyyy-mm-dd hh24:mi:ss')";
			sql += " AND CREATE_TIME <= TO_DATE('" + limitKey.getEndTimeParse() + "','YYYY-MM-DD')";
		}
		
		if(StringUtil.isNotEmpty(limitKey.getPos()) && !"-".equals(limitKey.getPos())){
			sql += " AND AD_POSITION_NAME = ? ";
			//paramList.add("%" + limitKey.getPos() + "%");
			paramList.add(limitKey.getPos());
		}
		
		if(StringUtil.isNotEmpty(limitKey.getAd()) && !"-".equals(limitKey.getAd())){
			sql += " AND AD_NAME = ? ";
			paramList.add(limitKey.getAd());
		}
		
		List<StatisticsBean> list = new ArrayList<StatisticsBean>();
		StatisticsBean bean = new StatisticsBean();
		bean.setAdName(limitKey.getAd());
		bean.setPosName(limitKey.getPos());
		
		list.add(bean);
		
		
		int count = 0;
		//登录用户数
		count = baseDao.count("SELECT COUNT(1) FROM (SELECT DISTINCT USERID " + sql + " AND USERID IS NOT NULL)", paramList.toArray());
		bean.setLoginUserNum(count);
		
		//未登录用户数 按唯一表示计算
		count = baseDao.count("SELECT COUNT(1) FROM (SELECT DISTINCT UCODE " + sql + " AND USERID IS NULL)", paramList.toArray());
		bean.setAnonyUserNum(count);
		
		//总用户数
		//bean.setTotalUserNum(bean.getLoginUserNum() + bean.getAnonyUserNum());
		
		//登录点击数
		count = baseDao.count("SELECT COUNT(1) " + sql + " AND USERID IS NOT NULL", paramList.toArray());
		bean.setLoginVisitNum(count);
		
		//未登录点击数
		count = baseDao.count("SELECT COUNT(1) " + sql + " AND USERID IS NULL", paramList.toArray());
		bean.setAnonyVisitNum(count);
		
		//总点击数
		//bean.setTotalVisitNum(bean.getLoginVisitNum() + bean.getAnonyVisitNum());
		
		List<StatisticsItemBean> itemList = new ArrayList<StatisticsItemBean>();
		StatisticsItemBean sib = new StatisticsItemBean();
		sib.setAdName(StringUtil.isEmpty(bean.getAdName()) ?  "全部" : bean.getAdName());
		sib.setPosName(StringUtil.isEmpty(bean.getPosName()) ?  "全部" : bean.getPosName());
		sib.setTitle("登录用户数(人)");
		sib.setNum(bean.getLoginUserNum());
		itemList.add(sib);
		
		sib = new StatisticsItemBean();
		sib.setTitle("未登录用户数(人)");
		sib.setNum(bean.getAnonyUserNum());
		itemList.add(sib);
		
		sib = new StatisticsItemBean();
		sib.setTitle("总用户数(人)");
		sib.setNum(bean.getTotalUserNum());
		itemList.add(sib);
		
		sib = new StatisticsItemBean();
		sib.setTitle("登录点击数(次)");
		sib.setNum(bean.getLoginVisitNum());
		itemList.add(sib);
		
		sib = new StatisticsItemBean();
		sib.setTitle("未登录点击数(次)");
		sib.setNum(bean.getAnonyVisitNum());
		itemList.add(sib);
		
		sib = new StatisticsItemBean();
		sib.setTitle("总点击数(次)");
		sib.setNum(bean.getTotalVisitNum());
		itemList.add(sib);
		
		
		return new DataModel<StatisticsItemBean>(itemList,1);
	}
	
	@Override
	public List<String> adNameList() {
		String sql = "SELECT AD_NAME FROM CMS_AD_ACCESSLOG GROUP BY AD_NAME ORDER BY AD_NAME";
		
		List<Map<String, Object>> list = baseDao.query(sql);
		
		if (list == null || list.isEmpty()) {
			return null;
		}
		
		List<String> nameList = new ArrayList<String>();
		
		for(Map<String, Object> row : list){
			String name = (String)row.get("ad_name");
			if (StringUtil.isEmpty(name)) {
				continue;
			}
			nameList.add(name);
		}
		
		return nameList;
	}

	@Override
	public List<String> posNameList() {
		String sql = "SELECT AD_POSITION_NAME POS_NAME FROM CMS_AD_ACCESSLOG GROUP BY AD_POSITION_NAME ORDER BY AD_POSITION_NAME";
		
		List<Map<String, Object>> list = baseDao.query(sql);
		
		if (list == null || list.isEmpty()) {
			return null;
		}
		
		List<String> nameList = new ArrayList<String>();
		
		for(Map<String, Object> row : list){
			String name = (String)row.get("pos_name");
			if (StringUtil.isEmpty(name)) {
				continue;
			}
			nameList.add(name);
		}
		
		return nameList;
	}
	
	
	
}
