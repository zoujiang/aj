package com.aj.ad.ifpr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.frame.core.jdbcdao.BaseDao;
import com.frame.core.vo.ParamsVo;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.vo.RequestVo;
import com.frame.ifpr.vo.ResponseBean;
import com.aj.ad.ifpr.bean.LogSynBean;
import com.aj.ad.ifpr.config.AdConstants;
import com.aj.ad.ifpr.service.LogSynService;
import com.aj.ad.ifpr.vo.AdLogParamVo;
import com.aj.ad.ifpr.vo.AdLogResultVo;

@Service("logSynService")
public class LogSynServiceImpl implements LogSynService,PublishService {
	private static final String insert = "INSERT INTO CMS_AD_ACCESSLOG (ID, USERID, APPID, TIME, AD_POSITION_NAME, AD_NAME, AD_SEQ, AD_STATUS, AREA_CODE, CLIENT_TYPE, CLIENT_MARK, CLIENT_MODEL, CLIENT_SYSTEM, CLIENT_UA, CLIENT_IP, CLIENT_PHONE, AD_POSITION_ID, AD_CONTENT_ID, AD_RELATIONAL_ID, AD_URL, UPDATE_TIME, CLIENT_IP_NET)"
											+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";	

	private Logger log = Logger.getLogger(LogSynServiceImpl.class);
	@Autowired
	private BaseDao baseDao;
	private String respServiceName = "btcs_ad_syn";
	private RequestVo requestVo;
	private AdLogParamVo param;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		log.debug("publishService("+obj+") - start");
		
		requestVo = (RequestVo)obj;
		if (requestVo == null) {
			return ResponseBean.error("请求参数有误", respServiceName);
		}
		
		if (!AdConstants.callType_andriond.equals(requestVo.getCallType()) 
				&& !AdConstants.callType_ios.equals(requestVo.getCallType()) ) {
			//logger.info("终端类型有误：" + accessType);
			return ResponseBean.error("终端类型有误" + requestVo.getCallType(), respServiceName);
		}
		
		ParamsVo pvo = requestVo.getParams();
		if (pvo == null) {
			return ResponseBean.error("请求参数为空", respServiceName);
		}
		
		if(pvo instanceof AdLogParamVo){
			param = (AdLogParamVo)pvo;
			
			LogSynBean logBean = null;
			try {
				logBean = (LogSynBean)JSONObject.toBean(JSONObject.fromObject(param.getBackParam()), LogSynBean.class);
			} catch (Exception e) {
				log.info("回传参数转换有误:" + e.toString() + "--backParam:" + param.getBackParam());
				return ResponseBean.error("回传参数有误" + e.toString() + "--backParam:" + param.getBackParam(), respServiceName);
			}
			
			if (logBean == null) {
				return ResponseBean.error("回传参数为空", respServiceName);
			}
			logBean.parseParam(param);
			logBean.setNetip(getClientIp());
			
			boolean flag = this.log(logBean);
			
			if (flag) {
				AdLogResultVo vo = new AdLogResultVo();
				vo.setSuccMsg("保存成功");
				return ResponseBean.success(vo, respServiceName);
			}
			
			return ResponseBean.error("error", respServiceName);
		}
		
		log.debug("publishService("+obj+") - end");
		
		return null;
	}
	
	
	

	
	@Override
	public boolean log(LogSynBean bean) {
		//v_id, v_userid, v_appid, v_time, v_ad_position_name, v_ad_name, v_ad_seq, v_ad_status, v_area_code, 
		//v_client_type, v_client_mark, v_client_model, v_client_system, v_client_ua, v_client_ip, 
		//v_client_phone, v_ad_position_id, v_ad_content_id, v_ad_relational_id, v_ad_url
		
		String sql = "INSERT INTO CMS_AD_ACCESSLOG "
				 + " (ID, USERID, CHANNEL_ID, TIME, AD_POSITION_NAME, AD_NAME, AD_SEQ, AD_STATUS, AREA_CODE, "
				 + "	CALL_TYPE, UCODE, PHONE_VER, PHONE_SYS_VER, IP, IP_NET, PHONE_NUM, AD_POSITION_ID, "
				 + " AD_CONTENT_ID, AD_RELATION_ID, RELATION_ID, RELATION_NAME, AD_URL, CREATE_TIME)"
				 +  " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
		
		List ps = new ArrayList();
		ps.add(initId());
		ps.add(bean.getUserid());
		ps.add(bean.getChannel_id());
		ps.add(new Date());
		ps.add(bean.getAd_position_name());
		ps.add(bean.getAd_name());
		ps.add(bean.getAd_seq());
		ps.add(bean.getAd_status());
		ps.add(bean.getAreaCode());
		ps.add(bean.getCall_type());
		ps.add(bean.getUcode());
		ps.add(bean.getPhoneVer());
		ps.add(bean.getPhoneSysVer());
		ps.add(bean.getIp());
		ps.add(bean.getNetip());
		ps.add(bean.getPhoneNum());
		
		ps.add(bean.getAd_position_id());
		ps.add(bean.getAd_content_id());
		ps.add(bean.getAd_relation_id());
		ps.add(bean.getRelation_id());
		ps.add(bean.getRelation_title());
		ps.add(bean.getAd_url());
		
		//保存
		int i = baseDao.update(sql, ps.toArray());
		return i > 0;
		
	}
	
	
	private String getClientIp() {
		ServletRequestAttributes requestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=requestAttributes.getRequest();
		
		
		String ip = null;
	    ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("Proxy-Client-IP");
	    } else if (ip.length() > 15) { // "***.***.***.***".length()
	    	//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP以','分割
	    	if (ip.indexOf(",") > 0) {
	    		ip = ip.substring(0, ip.indexOf(","));
	    	}
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getRemoteAddr();
	    }
	    log.info("x-forwarded-for:" + request.getHeader("x-forwarded-for")
				    + "===Proxy-Client-IP:" + request.getHeader("Proxy-Client-IP")
				    + "===WL-Proxy-Client-IP:" + request.getHeader("WL-Proxy-Client-IP")
				    + "===getRemoteAddr:" + request.getRemoteAddr()
				    + "===final IP:" + ip);
	    return ip;
	}
	
	public String initId() {
		String uuid = UUID.randomUUID().toString();
		//去掉“-”符号 
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}

	

}
