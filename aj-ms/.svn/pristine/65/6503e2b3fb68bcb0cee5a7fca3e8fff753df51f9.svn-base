/** 
 * Description: TODO(用一句话描述该文件做什么)  
 * @className com.tist.apply.action.ApplyAction
 * @author ChingWang    
 * @date 2016-9-4 下午3:28:05
 * @version V1.0   
 * Copyright (C) 2016 www.demo.com Inc. All rights reserved.        
 */

package com.tist.apply.action;

import java.util.ArrayList;
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
import com.tist.apply.service.ApplyService;
import com.tist.apply.service.AppsrvService;

@Controller
@RequestMapping("/apply")
public class ApplyAction {
	
	@Autowired private ApplyService applyService;
	@Autowired private AppsrvService appsrvService;

	@RequestMapping("/list")
	@ResponseBody
	public String list(String appName, int offset, int limit) {
		ListRange<AppInfo> range = new ListRange<AppInfo>();
		try{
			long total = applyService.getTotal(appName);
			range.setTotal(total);
			if(total > 0){
				List<AppInfo> list = applyService.getList(appName, offset, limit);
				range.setRows(list);
			}
		}catch (Exception e) {
			LogUtil.info("查询数据异常{}", e.getLocalizedMessage());
		}
		return JSONObject.toJSONString(range);
	}
	
	@RequestMapping("/statlist")
	@ResponseBody
	public String statlist(String appName, int offset, int limit) {
		ListRange<JSONObject> range = new ListRange<JSONObject>();
		try{
			long total = applyService.getTotal(appName);
			range.setTotal(total);
			if(total > 0){
				List<JSONObject> objects = new ArrayList<JSONObject>();
				List<AppInfo> list = applyService.getList(appName, offset, limit);
				for(AppInfo appInfo : list){
					JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(appInfo));
					object.put("appCnt", appsrvService.getTotal(appInfo.getAppId(), "1"));
					object.put("dataCnt", appsrvService.getTotal(appInfo.getAppId(), "2"));
					objects.add(object);
				}
				range.setRows(objects);
			}
		}catch (Exception e) {
			LogUtil.info("查询数据异常{}", e.getLocalizedMessage());
		}
		return JSONObject.toJSONString(range);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(AppInfo appInfo, String opt) {
		JSONObject obj = new JSONObject();
		if(applyService.editApply(appInfo)){
			obj.put("success", true);
			obj.put("message", "add".equals(opt)?"添加成功":"编辑成功");
		} else {
			obj.put("success", false);
			obj.put("message", "add".equals(opt)?"添加失败":"编辑失败");
		}
		return JSONObject.toJSONString(obj);
	}
	
	@RequestMapping("/approve")
	@ResponseBody
	public String approve(AppInfo appInfo, String opt){
		JSONObject obj = new JSONObject();
		if(StringUtil.isEmpty(appInfo.getAppId())){
			obj.put("success", false);
			obj.put("message", "审批失败,申请接入ID不能为空");
		} else {
			if(applyService.updateApply(appInfo, opt)){
				obj.put("success", true);
				obj.put("message", "审批成功");
			} else {
				obj.put("success", false);
				obj.put("message", "审批失败");
			}
		}
		return JSONObject.toJSONString(obj);
	}
	
	@RequestMapping("/isValid")
	@ResponseBody
	public String isValid(AppInfo appInfo, String opt){
		JSONObject obj = new JSONObject();
		if(StringUtil.isEmpty(appInfo.getAppId())){
			obj.put("success", false);
			obj.put("message", "操作失败");
		} else {
			if(applyService.updateApply(appInfo, opt)){
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
