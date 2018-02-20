package com.aj.ad.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.ad.bean.AdRelationBean;
import com.aj.ad.bean.MsgVo;
import com.aj.ad.bean.PositionBean;
import com.aj.ad.bean.PositionLimitKey;
import com.aj.ad.service.CmsPositionService;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.service.AuthorService;
import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;
import com.frame.core.util.pagination.key.SortType;
import com.frame.core.vo.DataModel;


@Controller
public class PositionAction extends FtpImgDownUploadAction {
	private Logger logger = Logger.getLogger(PositionAction.class);
	
	@Autowired
	private CmsPositionService cmsPositionService;
	@Autowired
	private AuthorService authorService;
	
	//列表
	@RequestMapping("/position/list")
	@ResponseBody
	public DataModel<PositionBean> getList(PositionLimitKey limitKey, String page, String rows, String sort, String order){
		if (StringUtil.isNotEmpty(sort)) {
			limitKey.setSortColumnName(sort);
		}
		if (SortType.Asc.name().toLowerCase().equals(order)) {
			limitKey.setSortType(SortType.Asc);
		} else {
			limitKey.setSortType(SortType.Desc);
		}
		
		System.out.println("request：" + JSONObject.fromObject(limitKey));
		
		DataModel<PositionBean> dataModel = cmsPositionService.getList(limitKey, Integer.valueOf(page), Integer.valueOf(rows));
		return dataModel;
	}
	
	
	//根据类型 取该类型下所有广告位（广告投放的时候使用）
	@RequestMapping("/position/listByType")
	@ResponseBody
	public MsgVo getListByType(String type) {
		
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(type)) {
			return new MsgVo("请求有误");
		}
		
		List<PositionBean> list = cmsPositionService.getList(type);
		
		msg = new MsgVo(true);
		msg.setObj(list);
		
		return msg;
	}
	
	
	//保存
	@RequestMapping("/position/save")
	@ResponseBody
	public MsgVo save(PositionBean vo) {
		logger.info("request-save:" + JSONObject.fromObject(vo));
		System.out.println("request-save:" + JSONObject.fromObject(vo));
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (vo == null) {
			return new MsgVo("请求有误");
		}
		
		
		msg = cmsPositionService.save(vo);
		return msg;
	}
	
	//详情
	@RequestMapping("/position/detail")
	@ResponseBody
	public MsgVo detail(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		logger.info("request-detail-id:" + id);
		
		PositionBean bean = cmsPositionService.getDetail(id);
		
		if(bean == null) {
			return new MsgVo("没有找到相关广告位:" + id);
		}
		
		msg = new MsgVo(true);
		msg.setObj(bean);
		
		return msg;
	}
	
	//上线
	@RequestMapping("/position/online")
	@ResponseBody
	public MsgVo online(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		logger.info("request-online-id:" + id);
		
		msg = cmsPositionService.online(id);
		
		return msg;
	}
	
	//下线
	@RequestMapping("/position/offline")
	@ResponseBody
	public MsgVo offline(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		logger.info("request-offline-id:" + id);
		
		msg = cmsPositionService.offline(id);
		
		return msg;
	}
	
	
	//广告投放列表
	@RequestMapping("/position/relationList")
	@ResponseBody
	public MsgVo relationList(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		PositionBean bean = cmsPositionService.getDetail(id);
		if (bean == null) {
			return new MsgVo("该广告位不存在" + id);
		}
		
		if (!authorService.isCreateUser(bean.getCreateUser()) && !authorService.existInRole(SystemConfig.getValue("cms.admin.role"))) {
			return new MsgVo("对不起，这个广告位不是你创建的，没有操作权限");
		}
		
		List<AdRelationBean> rels = cmsPositionService.queryRelations(bean.getId());
		List<AdRelationBean> relsOff = cmsPositionService.queryRelationsInOffline(bean.getId());
		
		if (rels == null) {
			rels = new ArrayList<AdRelationBean>();
		}
		if (relsOff != null && !relsOff.isEmpty()) {
			rels.addAll(relsOff); //把有效期外的加进来
		}
		
		if (rels.isEmpty()) {
			return new MsgVo("该广告位下没有广告" + bean.getId() + "---" + bean.getCode());
		}
		
		bean.setRels(rels);
		
		msg = new MsgVo(true);
		msg.setObj(bean);
		return msg;
	}
	
	
	//广告位上的广告 上线
	@RequestMapping("/position/ralationOnline")
	@ResponseBody
	public MsgVo ralationOnline(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		
		msg = cmsPositionService.relationOnline(id);
		
		return msg;
	}
	
	//广告位上的广告 下线 
	@RequestMapping("/position/ralationOffline")
	@ResponseBody
	public MsgVo ralationOffline(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		msg = cmsPositionService.relationOffline(id);
		
		return msg;
	}
	
	//广告位上的广告  修改序号 轮播时间
	@RequestMapping("/position/relationUpdate")
	@ResponseBody
	public MsgVo relationUpdate(AdRelationBean bean) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (bean == null || StringUtil.isEmpty(bean.getId()) || bean.getInterval() < 1 || bean.getSeq() < 1) {
			return new MsgVo("请求有误");
		}
		
		msg = cmsPositionService.relationUpdate(bean);
		
		return msg;
	}
	
	
	
	private MsgVo checkLogin() {
		if (authorService.getSessionUser() == null) {
			return new MsgVo("登录已失效，请重新登录");
		}
		return new MsgVo(true);
	}
	
	
	
	
}
