/** 
 * Description: TODO(用一句话描述该文件做什么)  
 * @className com.tist.apply.service.impl.ServerServiceImpl
 * @author ChingWang    
 * @date 2016-9-5 下午9:24:36
 * @version V1.0   
 * Copyright (C) 2016 www.demo.com Inc. All rights reserved.        
 */

package com.tist.apply.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.service.AuthorService;
import com.frame.core.util.DateUtil;
import com.frame.core.util.GUID;
import com.frame.core.util.StringUtil;
import com.tist.apply.model.AppInfo;
import com.tist.apply.model.AppSrv;
import com.tist.apply.service.AppsrvService;

@Service("appsrvService")
public class AappsrvServiceImpl implements AppsrvService {
	@Autowired private GenericDAO baseDAO;
	@Autowired private AuthorService authorService;
	/* (非 Javadoc)
	 * <p>Title: editServer</p>
	 * <p>Description: </p>
	 * @param appSrv
	 * @return 
	 * @see com.tist.apply.service.ServerService#editServer(com.tist.apply.model.AppSrv)
	 */
	@Override
	public boolean editServer(AppSrv appSrv) {
		try{
			if(StringUtil.isEmpty(appSrv.getSrvId())){
				appSrv.setSrvId(GUID.generateID(""));
				appSrv.setIsValid("0");
				appSrv.setCreateUser(authorService.getSessionUserPo().getAccount());
				appSrv.setCreateDt(DateUtil.dateToTimestamp(new Date()));
				baseDAO.save(appSrv);
			}else{
				AppSrv srv = baseDAO.get(AppSrv.class, appSrv.getSrvId());
				srv.setEndDt(appSrv.getEndDt());
				srv.setStartDt(appSrv.getStartDt());
				srv.setSrvCfg(appSrv.getSrvCfg());
				srv.setSrvPlace(appSrv.getSrvPlace());
				srv.setSrvIp(appSrv.getSrvPlace());
				srv.setSrvType(appSrv.getSrvType());
				srv.setSrvName(appSrv.getSrvName());
				srv.setModifyUser(authorService.getSessionUserPo().getAccount());
				srv.setModifyDt(DateUtil.dateToTimestamp(new Date()));
				baseDAO.merge(appSrv);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/* (非 Javadoc)
	 * <p>Title: getTotal</p>
	 * <p>Description: </p>
	 * @param appId
	 * @param srvType
	 * @return 
	 * @see com.tist.apply.service.AppsrvService#getTotal(java.lang.String, java.lang.String)
	 */
	@Override
	public long getTotal(String appId, String srvType) {
		List<Object> params = new ArrayList<Object>();
		String hql = "select count(ai) from AppSrv ai where 1=1 ";
		if(!StringUtil.isEmpty(appId)){
			hql += " and ai.appId = ?";
			params.add(appId);
		}
		if(!StringUtil.isEmpty(srvType)){
			hql += " and ai.srvType = ?";
			params.add(srvType);
		}
		hql += " order by ai.createDt";
		return baseDAO.getGenericCountByHql(hql, params.size() > 0 ? params.toArray() : null);
	}
	/* (非 Javadoc)
	 * <p>Title: getList</p>
	 * <p>Description: </p>
	 * @param appId
	 * @param srvType
	 * @param offset
	 * @param limit
	 * @return 
	 * @see com.tist.apply.service.AppsrvService#getList(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<AppInfo> getList(String appId, String srvType,int start, int pageSize) {
		List<Object> params = new ArrayList<Object>();
		String hql = "from AppSrv ai where 1=1 ";
		if(!StringUtil.isEmpty(appId)){
			hql += " and ai.appId = ?";
			params.add(appId);
		}
		if(!StringUtil.isEmpty(srvType)){
			hql += " and ai.srvType = ?";
			params.add(srvType);
		}
		hql += " order by ai.createDt";
		
		List<AppInfo> list= baseDAO.getGenericByPosition(hql, start, pageSize, params.size() > 0 ? params.toArray() : null);
		return list;
	}
	/* (非 Javadoc)
	 * <p>Title: updateSrv</p>
	 * <p>Description: </p>
	 * @param appSrv
	 * @param opt
	 * @return 
	 * @see com.tist.apply.service.AppsrvService#updateSrv(com.tist.apply.model.AppSrv, java.lang.String)
	 */
	@Override
	public boolean updateSrv(AppSrv appSrv, String opt) {
		try {
			AppSrv srv = baseDAO.get(AppSrv.class, appSrv.getSrvId());
			srv.setIsValid(appSrv.getIsValid());
			srv.setModifyDt(DateUtil.dateToTimestamp(new Date()));
			srv.setModifyUser(authorService.getSessionUserPo().getAccount());
			baseDAO.merge(srv);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
