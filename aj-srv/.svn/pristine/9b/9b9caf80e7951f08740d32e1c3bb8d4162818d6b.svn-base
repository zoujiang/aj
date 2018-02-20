package com.aj.remark.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.remark.vo.RemarkVo;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

@Service("remark")
public class RemarkService implements PublishService{

	private Logger log = Logger.getLogger(RemarkService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String relationId = params.optString("relationId");
		String remark = params.optString("remark");
		String type = params.optString("type");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(relationId == null || "".equals(relationId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "relationId为空！");
			return returnJSON.toString();
		}
		if(remark == null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "remark为空！");
			return returnJSON.toString();
		}
		if(type == null || "".equals(type)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "type为空！");
			return returnJSON.toString();
		}
		List<RemarkVo> markList = baseDAO.getGenericByHql("FROM RemarkVo WHERE createUserId = ? AND relationId = ?", new Object[]{userId, relationId});
		if(markList != null && markList.size() > 0){
			RemarkVo mark = markList.get(0);
			if("2".equals(type)){
				if(!remark.endsWith("之家")){
					remark += "之家";
				}
			}
			mark.setRemark(remark);
			baseDAO.update(mark);
		}else{
			RemarkVo mark = new RemarkVo();
			mark.setCreateUserId(userId);
			mark.setRelationId(relationId);
			if("2".equals(type)){
				if(!remark.endsWith("之家")){
					remark += "之家";
				}
			}
			mark.setRemark(remark);
			mark.setType(type);
			baseDAO.save(mark);
		}
		
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "操作成功！");
		return returnJSON.toString();
	}

}
