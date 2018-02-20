package com.aj.ad.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.ad.bean.MsgVo;
import com.aj.ad.bean.StatisticsItemBean;
import com.aj.ad.bean.StatisticsLimitKey;
import com.aj.ad.service.CmsAdService;
import com.aj.ad.service.CmsAdStatisticsService;
import com.aj.ad.service.CmsPositionService;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.service.AuthorService;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;


@Controller
public class AdStatisticsAction extends FtpImgDownUploadAction {
	private Logger logger = Logger.getLogger(AdStatisticsAction.class);
	
	@Autowired
	private CmsPositionService cmsPositionService;
	
	@Autowired
	private CmsAdService cmsAdService;
	
	@Autowired
	private CmsAdStatisticsService cmsAdStatisticsService;
	
	@Autowired
	private AuthorService authorService;
	
	//列表
	@RequestMapping("/stat/list")
	@ResponseBody
	public DataModel<StatisticsItemBean> getList(StatisticsLimitKey limitKey, String page, String rows, String sort, String order){
		
		
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return new DataModel<StatisticsItemBean>();
		}
		
		
		if(StringUtil.isEmpty(limitKey.getStartTimeParse())
				||  StringUtil.isEmpty(limitKey.getEndTimeParse())){
			return new DataModel<StatisticsItemBean>();
		}
		
		System.out.println("request：" + JSONObject.fromObject(limitKey));
		
		DataModel<StatisticsItemBean> dataModel = cmsAdStatisticsService.getList(limitKey);
		System.out.println("out:" + JSONObject.fromObject(dataModel));
		return dataModel;
	}
	
	
	//广告及广告位名称获取（查询条件）
	@RequestMapping("/stat/ads")
	@ResponseBody
	public List<Map<String, String>> ads() {
		System.out.println("request-ads");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return list;
		}
		
		Map<String, String> r = new HashMap<String,String>();
		r.put("id", "");
		r.put("text", "全部");
		list.add(r);
		if (msg.isError()) {
			return list;
		}
		
		List<String> ads = cmsAdStatisticsService.adNameList();
		
		if (ads == null) {
			return list;
		}
		
		for (String name : ads) {
			r = new HashMap<String,String>();
			r.put("id", name);
			r.put("text", name);
			list.add(r);
		}
		
		return list;
	}
	
	//广告位名称获取（查询条件）
	@RequestMapping("/stat/pos")
	@ResponseBody
	public List<Map<String, String>> pos() {
		System.out.println("request-pos");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return list;
		}
		
		Map<String, String> r = new HashMap<String,String>();
		r.put("id", "");
		r.put("text", "全部");
		list.add(r);
		if (msg.isError()) {
			return list;
		}
		
		List<String> pos = cmsAdStatisticsService.posNameList();
		
		if (pos == null) {
			return list;
		}
		
		for (String name : pos) {
			r = new HashMap<String,String>();
			r.put("id", name);
			r.put("text", name);
			list.add(r);
		}
		
		return list;
	}
	
	private MsgVo checkLogin() {
		if (authorService.getSessionUser() == null) {
			return new MsgVo("登录已失效，请重新登录");
		}
		return new MsgVo(true);
	}
	
	
	
	
}
