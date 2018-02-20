/** 
 * Description: TODO(用一句话描述该文件做什么)  
 * @className com.tist.apply.service.ServerService
 * @author ChingWang    
 * @date 2016-9-5 下午9:24:12
 * @version V1.0   
 * Copyright (C) 2016 www.demo.com Inc. All rights reserved.        
 */

package com.tist.apply.service;

import java.util.List;

import com.tist.apply.model.AppInfo;
import com.tist.apply.model.AppSrv;

public interface AppsrvService {

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param appSrv
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-5      Ching Wang     v1.0.0      create
	 *
	 */
	public boolean editServer(AppSrv appSrv);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param appId
	 * @param srvType
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-6      Ching Wang     v1.0.0      create
	 *
	 */
	public long getTotal(String appId, String srvType);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param appId
	 * @param srvType
	 * @param offset
	 * @param limit
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-6      Ching Wang     v1.0.0      create
	 *
	 */
	public List<AppInfo> getList(String appId, String srvType, int start, int pageSize);

	/** 
	 * TODO（用一句话描述该方法做什么）
	 * @param appSrv
	 * @param opt
	 * @return
	 * @version
	 * Date           Author         Version     Description
	 * -----------------------------------------------------------------
	 * 2016-9-6      Ching Wang     v1.0.0      create
	 *
	 */
	public boolean updateSrv(AppSrv appSrv, String opt);

}
