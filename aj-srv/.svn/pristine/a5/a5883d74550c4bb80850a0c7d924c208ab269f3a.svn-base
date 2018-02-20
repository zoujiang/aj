package com.aj.ad.action;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.ad.bean.AdBean;
import com.aj.ad.bean.AdContentBean;
import com.aj.ad.bean.AdLimitKey;
import com.aj.ad.bean.AdRelationBean;
import com.aj.ad.bean.MsgVo;
import com.aj.ad.service.CmsAdService;
import com.aj.ad.service.CmsPositionService;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.service.AuthorService;
import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.SortType;
import com.frame.core.vo.DataModel;



@Controller
public class AdAction extends FtpImgDownUploadAction {
	private Logger logger = Logger.getLogger(AdAction.class);
	
	@Autowired
	private CmsPositionService cmsPositionService;
	
	@Autowired
	private CmsAdService cmsAdService;
	@Autowired
	private AuthorService authorService;
	
	//列表
	@RequestMapping("/ad/list")
	@ResponseBody
	public DataModel<AdBean> getList(AdLimitKey limitKey, String page, String rows, String sort, String order){
		if (StringUtil.isNotEmpty(sort)) {
			limitKey.setSortColumnName(sort);
		}
		if (SortType.Asc.name().toLowerCase().equals(order)) {
			limitKey.setSortType(SortType.Asc);
		} else {
			limitKey.setSortType(SortType.Desc);
		}
		
		//System.out.println("request：" + JSONObject.fromObject(limitKey));
		
		DataModel<AdBean> dataModel = cmsAdService.getList(limitKey, Integer.valueOf(page), Integer.valueOf(rows));
		//System.out.println("out:" + JSONObject.fromObject(dataModel));
		return dataModel;
	}
	
	
	//保存
	@RequestMapping("/ad/save")
	@ResponseBody
	public MsgVo save(AdBean vo) {
		logger.info("request-save:" + JSONObject.fromObject(vo));
		System.out.println("request-save:" + JSONObject.fromObject(vo));
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (vo == null) {
			return new MsgVo("请求有误");
		}
		
		
		msg = cmsAdService.save(vo);
		return msg;
	}
	
	//详情
	@RequestMapping("/ad/detail")
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
		
		AdBean bean = cmsAdService.getDetail(id);
		
		if(bean == null) {
			return new MsgVo("没有找到相关广告:" + id);
		}
		
		msg = new MsgVo(true);
		msg.setObj(bean);
		
		return msg;
	}
	
	//上线
	@RequestMapping("/ad/online")
	@ResponseBody
	public MsgVo online(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		logger.info("request-ad online-id:" + id);
		
		msg = cmsAdService.online(id);
		
		return msg;
	}
	
	//下线
	@RequestMapping("/ad/offline")
	@ResponseBody
	public MsgVo offline(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		logger.info("request-ad offline-id:" + id);
		
		msg = cmsAdService.offline(id);
		
		return msg;
	}
	
	//内容详情
	@RequestMapping("/ad/content")
	@ResponseBody
	public MsgVo content(String id) {
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		logger.info("request-content-id:" + id);
		
		AdContentBean bean = cmsAdService.getContent(id);
		
		if(bean == null) {
			return new MsgVo("没有找到相关广告内容:" + id);
		}
		
		msg = new MsgVo(true);
		msg.setObj(bean);
		
		return msg;
	}
	
	//保存
	@RequestMapping("/ad/saveContent")
	@ResponseBody
	public MsgVo saveContent(AdContentBean vo) {
		logger.info("request-saveContent:" + JSONObject.fromObject(vo));
		System.out.println("request-saveContent:" + JSONObject.fromObject(vo));
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (vo == null &&  StringUtil.isEmpty(vo.getId()) && StringUtil.isEmpty(vo.getAdid()) && StringUtil.isEmpty(vo.getPosition_type())) {
			return new MsgVo("请求有误");
		}
		
		
		msg = cmsAdService.saveContent(vo);
		return msg;
	}
	
	//保存投放关系
	@RequestMapping("/ad/saveRelation")
	@ResponseBody
	public MsgVo saveRelation(AdRelationBean vo) {
		logger.info("request-saveRelation:" + JSONObject.fromObject(vo));
		System.out.println("request-saveRelation:" + JSONObject.fromObject(vo));
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (vo == null ||  StringUtil.isEmpty(vo.getPosid()) || StringUtil.isEmpty(vo.getContent_id())) {
			return new MsgVo("请求有误");
		}
		
		
		msg = cmsAdService.saveRelation(vo);
		return msg;
	}
	
	//查询投放关系
	@RequestMapping("/ad/relationListGrid")
	@ResponseBody
	public DataModel<AdRelationBean> relationList11(String contentId) {
		logger.info("request-listRelation:" + contentId);
		System.out.println("request-listRelation:" + contentId);
		
		List<AdRelationBean> list = cmsAdService.queryRelations(contentId);
		
		return new DataModel<AdRelationBean>(list, list.size());
	}
	
	
	//查询投放关系
	@RequestMapping("/ad/relationList")
	@ResponseBody
	public MsgVo relationList(String contentId) {
		logger.info("request-listRelation:" + contentId);
		System.out.println("request-listRelation:" + contentId);
		
		List<AdRelationBean> list = cmsAdService.queryRelations(contentId);
		
		MsgVo msg = new MsgVo(true);
		msg.setObj(list);
		return msg;
	}
	
	//删除投放关系
	@RequestMapping("/ad/delRelation")
	@ResponseBody
	public MsgVo delRelation(String relationId) {
		logger.info("request-delRelation:" + relationId);
		System.out.println("request-delRelation:" + relationId);
		
		if (StringUtil.isEmpty(relationId)) {
			return new MsgVo("请求有误");
		}
		
		MsgVo msg = cmsAdService.delRelation(relationId);
		
		return msg;
	}
	
	
	private MsgVo checkLogin() {
		if (authorService.getSessionUser() == null) {
			return new MsgVo("登录已失效，请重新登录");
		}
		return new MsgVo(true);
	}
	
	
	
	
}
