/** 
 * Description: TODO(用一句话描述该文件做什么)  
 * @className com.tist.apply.service.ApplyService
 * @author ChingWang    
 * @date 2016-9-4 下午3:52:01
 * @version V1.0   
 * Copyright (C) 2016 www.demo.com Inc. All rights reserved.        
 */

package com.tist.apply.service;

import java.util.List;

import com.tist.apply.model.AppInfo;

public interface ApplyService {
	
	public abstract List<AppInfo> getList(String name, int start, int pageSize) throws Exception;

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param name
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-4      Ching Wang     v1.0.0      create
	 *
	 */
	public abstract long getTotal(String name);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param appInfo
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-4      Ching Wang     v1.0.0      create
	 *
	 */
	public abstract boolean editApply(AppInfo appInfo);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param appInfo
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-4      Ching Wang     v1.0.0      create
	 *
	 */
	public abstract boolean updateApply(AppInfo appInfo, String opt);
}
