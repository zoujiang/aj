/** 
 * Description: TODO(用一句话描述该文件做什么)  
 * @className com.tist.apply.action.ServerAction
 * @author ChingWang    
 * @date 2016-9-5 下午9:23:55
 * @version V1.0   
 * Copyright (C) 2016 www.demo.com Inc. All rights reserved.        
 */

package com.tist.apply.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.util.ListRange;
import com.frame.core.util.LogUtil;
import com.frame.core.util.StringUtil;
import com.tist.apply.model.AppInfo;
import com.tist.apply.model.AppSrv;
import com.tist.apply.service.AppsrvService;

@Controller
@RequestMapping("/srv")
public class AppsrvAction {

	@Autowired private AppsrvService appsrvService;
	
	@RequestMapping("/list")
	@ResponseBody
	public String list(String appId, String srvType, int offset, int limit) {
		ListRange<AppInfo> range = new ListRange<AppInfo>();
		try{
			long total = appsrvService.getTotal(appId, srvType);
			range.setTotal(total);
			if(total > 0){
				List<AppInfo> list = appsrvService.getList(appId, srvType, offset, limit);
				range.setRows(list);
			}
		}catch (Exception e) {
			LogUtil.info("查询数据异常{}", e.getLocalizedMessage());
		}
		return JSONObject.toJSONString(range);
	}
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(AppSrv appSrv, String opt) {
		JSONObject obj = new JSONObject();
		if(appsrvService.editServer(appSrv)){
			obj.put("success", true);
			obj.put("message", "add".equals(opt)?"添加成功":"编辑成功");
		} else {
			obj.put("success", false);
			obj.put("message", "add".equals(opt)?"添加失败":"编辑失败");
		}
		return JSONObject.toJSONString(obj);
	}
	
	@RequestMapping("/isValid")
	@ResponseBody
	public String isValid(AppSrv appSrv, String opt){
		JSONObject obj = new JSONObject();
		if(StringUtil.isEmpty(appSrv.getSrvId())){
			obj.put("success", false);
			obj.put("message", "操作失败");
		} else {
			if(appsrvService.updateSrv(appSrv, opt)){
				obj.put("success", true);
				obj.put("message", "操作成功");
			} else {
				obj.put("success", false);
				obj.put("message", "操作失败");
			}
		}
		return JSONObject.toJSONString(obj);
	}
}
