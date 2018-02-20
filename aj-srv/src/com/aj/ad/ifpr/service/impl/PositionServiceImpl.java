package com.aj.ad.ifpr.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.jdbcdao.BaseDao;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.ParamsVo;
import com.frame.ifpr.constant.ServerNameConstant;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.vo.RequestVo;
import com.frame.ifpr.vo.ResponseBean;
import com.aj.ad.ifpr.bean.AdContentBean;
import com.aj.ad.ifpr.bean.AdPositionBean;
import com.aj.ad.ifpr.bean.AdRelationBean;
import com.aj.ad.ifpr.bean.LogBackParamBean;
import com.aj.ad.ifpr.config.AdConstants;
import com.aj.ad.ifpr.service.PositionService;
import com.aj.ad.ifpr.vo.AdListParamVo;
import com.aj.ad.ifpr.vo.AdListResultBean;
import com.aj.ad.ifpr.vo.AdListResultVo;

@Service("positionService")
public class PositionServiceImpl implements PositionService,PublishService {
	
	private Logger log = Logger.getLogger(PositionServiceImpl.class);
	private static SimpleDateFormat format_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//上下线时间内（有效）
	private static final String select_ad_by_posid_without_offline = "SELECT MT.*, AD.STATUS AD_STATUS, AD.NAME AD_NAME, "
									 + " AD.ONLINE_TIME, AD.OFFLINE_TIME, "
									 + " C.TYPE, C.POSITION_TYPE, C.URL,C.IMG_SMALL,C.IMG_BIG,C.TEXT,C.VIDEO,C.RELATION_ID,C.RELATION_TITLE, "
									 + " C.ID C_ID FROM (SELECT * FROM CMS_AD_ADRELATION R WHERE R.POSITION_ID = ?) MT "
							         + " LEFT JOIN CMS_AD_ADCONTENT C ON MT.CONTENT_ID = C.ID "
							         + " LEFT JOIN CMS_AD_AD AD ON C.ADID = AD.ID "
							         + " WHERE AD.ONLINE_TIME < NOW() AND AD.OFFLINE_TIME > NOW() "
							         //+ "AND AD.STATUS = 0 AND MT.STATUS = 0 "
							         + " ORDER BY AD.STATUS DESC, MT.STATUS DESC, MT.SEQ ASC";
		
	@Autowired
	private BaseDao baseDao;
	private String respServiceName = "ad_list";
	private RequestVo requestVo;
	private AdListParamVo adListParam;
	@Override
	public Object publishService(Object obj) throws PublicException {
		log.debug("publishService("+obj+") - start");
		
		requestVo = (RequestVo)obj;
		if (requestVo == null) {
			return ResponseBean.error("请求参数有误", respServiceName);
		}
		
	/*	if (!AdConstants.callType_andriond.equals(requestVo.getCallType()) 
				&& !AdConstants.callType_ios.equals(requestVo.getCallType()) ) {
			//logger.info("终端类型有误：" + accessType);
			return ResponseBean.error("终端类型有误" + requestVo.getCallType(), respServiceName);
		}*/
		
		ParamsVo pvo = requestVo.getParams();
		if (pvo == null) {
			return ResponseBean.error("请求参数为空", respServiceName);
		}
		
		if(pvo instanceof AdListParamVo){
			adListParam = (AdListParamVo)pvo;
			return this.queryRelations(adListParam);
		}
		
		log.debug("publishService("+obj+") - end");
		
		return null;
	}
	
	
	public Object queryRelations(AdListParamVo param) {
		
		log.info("queryRelations:" + JSONObject.fromObject(param));
		
		if (StringUtil.isEmpty(param.getChannel())) {
			return ResponseBean.error("频道编码为空", respServiceName);
		}
		if (StringUtil.isEmpty(param.getCode())) {
			return ResponseBean.error("广告位编码为空", respServiceName);
		}
		
		AdPositionBean postion = query(param.getChannel(), param.getCode());
		
		if (postion == null) {
			return ResponseBean.error("广告位不存在" + param.getChannel() + "-" + param.getCode(), respServiceName);
		}
		if (postion.getStatus() != AdConstants.status_on) {
			return ResponseBean.error("广告位已下线" + param.getChannel() + "-" + param.getCode(), respServiceName);
		}
		
		
		List<AdRelationBean> rels = queryRelations(postion.getId());
		if (rels != null) {
			for (AdRelationBean b : rels) {
				b.setPostion(postion);
			}
		}
		
		AdListResultVo vo = new AdListResultVo(parseRel(rels));
		
		log.debug("publishService("+param+") - end");
		return ResponseBean.success(vo, respServiceName);
	}
	
	
	public AdPositionBean query(String channel_id, String code) {
		String sql = "SELECT * FROM CMS_AD_POSITION P WHERE P.CHANNEL_ID = ? AND P.CODE = ?";
		List list = baseDao.query(sql, new Object[]{channel_id, code});
		if (list == null || list.isEmpty()) {
			return null;
		}
		AdPositionBean bean = new AdPositionBean();
		bean.read((Map)list.get(0));
		return bean;
	}
	
	
	//查询广告位下面的广告-上下线时间内的(有效的)
	public List<AdRelationBean> queryRelations(String posid) {
		List list = baseDao.query(select_ad_by_posid_without_offline, new Object[]{posid});
		
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<AdRelationBean> beans = new ArrayList<AdRelationBean>();
		for (int i = 0; i < list.size(); i++) {
			Map row = (Map)list.get(i);
			AdRelationBean b = new AdRelationBean();
			b.read(row);
			
			AdContentBean content = new AdContentBean();
			content.read(row);
			
			content.setAd_name((String)row.get("ad_name"));
			content.setAd_status((Integer)row.get("ad_status"));
			content.setId((String)row.get("c_id"));
			content.setOnline_time((Date)row.get("online_time"));
			content.setOffline_time((Date)row.get("offline_time"));
			b.setContent(content);
			beans.add(b);
		}
		return beans;
		
	}
	
	
	//分析广告，过滤掉那些无法显示的
	private List<AdListResultBean> parseRel(List<AdRelationBean> rels) {
		if (rels == null) {
			return null;
		}
		List<AdListResultBean> list = new ArrayList<AdListResultBean>();
		int seq = 1;
		for (AdRelationBean b : rels) {
			AdListResultBean bean = new AdListResultBean();
			AdContentBean content = b.getContent();
			if(!isValid(b)) { //无效
				continue;
			}
			
			bean.setName(content.getAd_name());
			bean.setType(content.getType());
			
			bean.setImgSmall(content.getImg_small());
			bean.setImgBig(content.getImg_big());
			bean.setUrl(content.getUrl());
			bean.setText(content.getText());
			bean.setVideo(content.getVideo());
			bean.setRelationId(content.getRelation_id());
			bean.setRelationTitle(content.getRelation_title());
			bean.setInterval(b.getInterval());
			bean.setOnlineTime(content.getOnline_time());
			bean.setOfflineTime(content.getOffline_time());
			bean.setTime(b.getTime());
			
			LogBackParamBean backParam = new LogBackParamBean();//回调跳转地址时使用
			backParam.setAd_content_id(content.getId());
			backParam.setAd_position_id(b.getPosid());
			backParam.setAd_relation_id(b.getId());
			backParam.setAd_position_name(b.getPostion().getName());//广告位名称
			backParam.setAd_name(content.getAd_name());	//广告名称
			backParam.setAd_content_type(content.getType());//广告类型  文字视频等等
			backParam.setAd_seq("AD" + seq);	//广告栏位置ID
			//backParam.setArea_code("500000");	//区域编码  客户端统计的时候上传
			backParam.setAd_url(content.getUrl());	//连接地址/客户端跳转规则
			backParam.setChannel_id(adListParam.getChannel());	//频道ID
			backParam.setAd_position_code(Integer.valueOf(adListParam.getCode()));  //广告位编码   用于跳转回传时取信息
			backParam.setCall_type(requestVo.getCallType());	//访问平台类型
			
			backParam.setRelation_id(content.getRelation_id());
			backParam.setRelation_title(content.getRelation_title());
			
			log.info("backParam:" + JSONObject.fromObject(backParam));
				
			bean.setBackParam(JSONObject.fromObject(backParam).toString());
			
			list.add(bean);
			
			seq++;
		}
		return list;
	}
	
	
	//是否有效，有效返回true,无效返回false
	private boolean isValid(AdRelationBean bean) {
		if (bean == null) {
			return false;
		}
		AdContentBean content = bean.getContent();
		if (content == null) {
			return false;
		}
		//广告本身已下线
		if (content.getAd_status() != AdConstants.status_on) {
			return false;
		}
		//广告在该广告位下无效
		if (bean.getStatus() != AdConstants.status_on) {
			return false;
		}
		
		return isTimeValid(bean.getTime());
		
	}
	//校验时间是否 在时间段中
	private static boolean isTimeValid(String timeSplit) {
		
		if (StringUtil.isEmpty(timeSplit)) { //没有时间段
			return true;
		}
		String[] ts = timeSplit.trim().split(",");
		if (ts == null || ts.length == 0) {
			return true;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String cur = sdf.format(new Date());
		for (String times : ts) {
			if (StringUtil.isEmpty(times)) { 
				continue;
			}
			String[] tt = times.split("-");
			if (tt.length < 2) {
				continue;
			}
			String start = tt[0];
			String end = tt[1];
			
			if (cur.compareTo(start) >= 0 && 
					cur.compareTo(end) < 0) {
				return true;
			}
		}
		
		return false;
	}
	
	
	

}
