package com.aj.kindergarten.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.shop.vo.TShopComment;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.UUIDUtil;

import net.sf.json.JSONObject;


/**
 *5.7.6发布幼儿园评价
 * */

@Service("kindergartenCommentPush")
public class KindergartenCommentPushService implements PublishService{

	private Logger log = Logger.getLogger(KindergartenCommentPushService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String kindergartenId = params.optString("kindergartenId");
		String content = params.optString("content");
		String imgUrl = params.optString("imgUrl");
		String score = params.optString("score");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		
		if(kindergartenId == null || "".equals(kindergartenId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "kindergartenId为空！");
			return returnJSON.toString();
		}
		if( (content == null || "".equals(content)) && (imgUrl == null || "".equals(imgUrl))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "评论内容不能为空！");
			return returnJSON.toString();
		}
		
		TShopComment cmt = new TShopComment();
		cmt.setId(UUIDUtil.uuid());
		cmt.setCmtContent(content);
		String cmtImgs = "";
		if(imgUrl != null && !"".equals(imgUrl)){
			cmtImgs = imgUrl.replaceAll(SystemConfig.getValue("clear.img.http.url"), "");
		}
		cmt.setCmtImg(cmtImgs);
		cmt.setCmtShopId(kindergartenId);
		cmt.setCmtTime(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
		cmt.setCmtUserId(userId);
		cmt.setScore(score != null ? Integer.parseInt(score) : null);
		cmt.setStatus(0);
		cmt.setShopType(1);
		baseDAO.save(cmt);
		
		result.put("successMsg", "发布成功");
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}