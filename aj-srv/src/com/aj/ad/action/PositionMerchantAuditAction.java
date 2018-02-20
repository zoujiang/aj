package com.aj.ad.action;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.ad.bean.MsgVo;
import com.aj.ad.bean.PositionMerchantBean;
import com.aj.ad.bean.PositionMerchantLimitKey;
import com.aj.ad.service.PositionMerchantAuditService;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.service.AuthorService;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;


@Controller
public class PositionMerchantAuditAction extends FtpImgDownUploadAction {
	private Logger logger = Logger.getLogger(PositionMerchantAuditAction.class);
	
	@Autowired
	private PositionMerchantAuditService positionMerchantAuditService;
	@Autowired
	private AuthorService authorService;
	
	//列表
	@RequestMapping("/position/audit/list")
	@ResponseBody
	public DataModel<PositionMerchantBean> getList(PositionMerchantLimitKey limitKey, String page, String rows, String sort, String order){
		
		System.out.println("request：" + JSONObject.fromObject(limitKey));
		
		DataModel<PositionMerchantBean> dataModel = positionMerchantAuditService.getList(limitKey, Integer.valueOf(page), Integer.valueOf(rows));
		System.out.println("out：" + JSONObject.fromObject(dataModel));
		
		return dataModel;
	}
	
	
	@RequestMapping("/position/audit/detail")
	@ResponseBody
	public MsgVo getDetail(String id) {
		
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (StringUtil.isEmpty(id)) {
			return new MsgVo("请求有误");
		}
		
		PositionMerchantBean bean = positionMerchantAuditService.getDetail(id);
		
		if(bean == null) {
			return new MsgVo("没有找到相关信息:" + id);
		}
		
		msg = new MsgVo(true);
		msg.setObj(bean);
		
		return msg;
	}
	
	
	//通过
	@RequestMapping("/position/audit/pass")
	@ResponseBody
	public MsgVo pass(PositionMerchantBean vo) {
		logger.info("request-pass:" + JSONObject.fromObject(vo));
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (vo == null || StringUtil.isEmpty(vo.getId())) {
			return new MsgVo("请求有误");
		}
		
		msg = positionMerchantAuditService.pass(vo);
		return msg;
	}
	
	//驳回
	@RequestMapping("/position/audit/back")
	@ResponseBody
	public MsgVo back(PositionMerchantBean vo) {
		logger.info("request-back:" + JSONObject.fromObject(vo));
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		if (vo == null || StringUtil.isEmpty(vo.getId())) {
			return new MsgVo("请求有误");
		}
		if (StringUtil.isEmpty(vo.getReason())) {
			return new MsgVo("请输入退回说明");
		}
		
		msg = positionMerchantAuditService.back(vo);
		
		return msg;
	}
	
	
	
	
	private MsgVo checkLogin() {
		if (authorService.getSessionUser() == null) {
			return new MsgVo("登录已失效，请重新登录");
		}
		return new MsgVo(true);
	}
	
	
	
	
}
