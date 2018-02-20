package com.aj.praylettermgr.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.babymgr.vo.TBabyInfo;
import com.aj.childrenmgr.vo.TChildrenInfo;
import com.aj.praylettermgr.vo.TPrayletter;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.UUIDUtil;


/**
 * 爱的传承新增祈愿信
 * */

@Service("prayLetterAdd")
public class AddPrayLetterService implements PublishService{

	private Logger log = Logger.getLogger(AddPrayLetterService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String babyId = params.optString("babyId");  
		String content = params.optString("content");
		String audio = params.optString("audio");
		String picUrl = params.optString("picUrl");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(babyId == null || "".equals(babyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "babyId为空！");
			return returnJSON.toString();
		}
		if((content == null || "".equals(content)) 
				&& (audio == null || "".equals(audio))
				&& (picUrl == null || "".equals(picUrl))
				){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "content,audio,picUrl不能都为空！");
			return returnJSON.toString();
		}
		String hsql = " from TBabyInfo where id = ? ";
		List<TBabyInfo> babyList = baseDAO.getGenericByHql(hsql, babyId);
		if(babyList == null || babyList.size() == 0){
			hsql = " from TChildrenInfo where id = ? ";
			List<TChildrenInfo> childList = baseDAO.getGenericByHql(hsql, babyId);
			if(childList == null || childList.size() == 0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "孩子信息不存在");
				return returnJSON.toString();
			}
		}
		TPrayletter letter = new TPrayletter();
		letter.setId(UUIDUtil.uuid());
		letter.setBabyUserId(babyId);
		letter.setContent(content);
		letter.setCreateDate(DateUtils.currentDate());
		letter.setPrayUserId(Integer.parseInt(userId));
		letter.setAudioUrl(audio);
		letter.setPicUrl(picUrl);
		baseDAO.save(letter);
		
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		result.put("succMsg", "操作成功！");	
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}
