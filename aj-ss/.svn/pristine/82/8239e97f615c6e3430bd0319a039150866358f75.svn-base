/** 
 * Description: TODO(用一句话描述该文件做什么)  
 * @className com.tist.apply.service.impl.ApplyService
 * @author ChingWang    
 * @date 2016-9-4 下午3:53:37
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
import com.tist.apply.service.ApplyService;

@Service("applyService")
public class ApplyServiceImpl implements ApplyService {

	@Autowired private GenericDAO baseDAO;
	@Autowired private AuthorService authorService;
	
	/* (非 Javadoc)
	 * <p>Title: getDemoList</p>
	 * <p>Description: </p>
	 * @param name
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 * @see com.tist.apply.service.ApplyService#getDemoList(java.lang.String, int, int)
	 */
	@Override
	public List<AppInfo> getList(String name, int start, int pageSize)
			throws Exception {
		List<Object> params = new ArrayList<Object>();
		String hql = "from AppInfo ai where 1=1 ";
		if(!StringUtil.isEmpty(name)){
			hql += " and ai.appName like ?";
			params.add("%" + name +"%");
		}
		hql += " order by ai.createDt";
		
		List<AppInfo> list= baseDAO.getGenericByPosition(hql, start, pageSize, params.size() > 0 ? params.toArray() : null);
		return list;
	}

	/* (非 Javadoc)
	 * <p>Title: getTotal</p>
	 * <p>Description: </p>
	 * @param name
	 * @return 
	 * @see com.tist.apply.service.ApplyService#getTotal(java.lang.String)
	 */
	@Override
	public long getTotal(String name) {
		List<Object> params = new ArrayList<Object>();
		String chql = "select count(ai) from AppInfo ai where 1=1 ";
		if(!StringUtil.isEmpty(name)){
			chql += " and ai.appName like ?";
			params.add("%" + name +"%");
		}
		return baseDAO.getGenericCountByHql(chql, params.size() > 0 ? params.toArray() : null);
	}

	/* (非 Javadoc)
	 * <p>Title: editApply</p>
	 * <p>Description: </p>
	 * @param appInfo 
	 * @see com.tist.apply.service.ApplyService#editApply(com.tist.apply.model.AppInfo)
	 */
	@Override
	public boolean editApply(AppInfo appInfo) {
		try{
			if(StringUtil.isEmpty(appInfo.getAppId())){
				appInfo.setAppId(GUID.generateID(""));
				appInfo.setCreateUser(authorService.getSessionUserPo().getAccount());
				appInfo.setCreateDt(DateUtil.dateToTimestamp(new Date()));
				baseDAO.save(appInfo);
			}else{
				appInfo.setModifyUser(authorService.getSessionUserPo().getAccount());
				appInfo.setModifyDt(DateUtil.dateToTimestamp(new Date()));
				baseDAO.merge(appInfo);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/* (非 Javadoc)
	 * <p>Title: approveApply</p>
	 * <p>Description: </p>
	 * @param appInfo
	 * @return 
	 * @see com.tist.apply.service.ApplyService#approveApply(com.tist.apply.model.AppInfo)
	 */
	@Override
	public boolean updateApply(AppInfo appInfo, String opt) {
		try {
			AppInfo info = baseDAO.get(AppInfo.class, appInfo.getAppId());
			if("approve".equals(opt)){
				info.setApvSts(appInfo.getApvSts());
				info.setApvReason(appInfo.getApvReason());
				info.setApvUserId(authorService.getSessionUserPo().getId());
				info.setApvDt(DateUtil.dateToTimestamp(new Date()));
				//info.setIsValid("1");
			} else {
				info.setIsValid(appInfo.getIsValid());
				info.setModifyDt(DateUtil.dateToTimestamp(new Date()));
				info.setModifyUser(authorService.getSessionUserPo().getAccount());
			}
			baseDAO.merge(info);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
