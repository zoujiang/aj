/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aj.kindergarten.vo.TKindergartenNotice;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("kindergartenNoticeMgr")
public class KindergartenNoticeMgrService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String receiveUserFamilyId = params.optString("receiveUserFamilyId");
		String noticeContent = params.optString("noticeContent");
		int noticeId = params.optInt("noticeId");
		int gradeId = params.optInt("gradeId");
        int oper = params.optInt("oper"); // 1:新增 2:删除 3:修改状态为已处理

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

		String[] add = {"userId", "receiveUserFamilyId", "oper", "noticeContent", "gradeId"};
		String[] delOrUpdate = {"userId", "noticeId", "oper"};
		String re =  checkParam(params, new String[]{"oper"});
        if( re  != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		if(oper == 1){
			re =  checkParam(params, add);
			if(re != null){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", re +"不能为空！");
				return returnJSON.toString();
			}
			String[] familyIds = receiveUserFamilyId.split(",");
			for(String familyId : familyIds){

				TKindergartenNotice notice = new TKindergartenNotice();
				notice.setNoticeContent(noticeContent);
				notice.setReceiveUserFamilyId(familyId);
				notice.setReportTime(DateUtils.currentDate());
				notice.setReportUserId(userId);
				notice.setStatus(0);
				notice.setGradeId(gradeId);
				baseDAO.save(notice);
			}
			result.put("succMsg", "发布成功");
		}else  if(oper == 2 ){
			re =  checkParam(params, delOrUpdate);
			if(re != null){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", re +"不能为空！");
				return returnJSON.toString();
			}
			baseDAO.delete(TKindergartenNotice.class, noticeId);
			result.put("succMsg", "删除成功");
		}else if(oper == 3){
			TKindergartenNotice notic = baseDAO.get(TKindergartenNotice.class,noticeId );
			notic.setStatus(1);
			baseDAO.update(notic);
			result.put("succMsg", "更新成功");
		}


		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

	private String checkParam(JSONObject param, String[] keys){

	    for(String key : keys){
	        if(param.get(key) == null || "".equals(param.get(key).toString())){
	            return key;
            }
        }
        return null;
    }

}
