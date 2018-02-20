/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aj.kindergarten.vo.TKindergartenMessageBoard;
import com.aj.kindergarten.vo.TKindergartenPhoto;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("kindergartenMessageBoardMgr")
public class KindergartenMessageBoardMgrService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int userType = params.optInt("userType");
		int messageType = params.optInt("messageType");
		String messageContent = params.optString("messageContent");
		int albumId = params.optInt("albumId");
		int messageId = params.optInt("messageId");  //1:班级 2:个人
        int oper = params.optInt("oper");  //1:新增 2:删除

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        String re = checkParam(params, new String[]{"userId", "userType", "messageType","messageContent" , "albumId", "oper"});
        if(re != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", re +"不能为空！");
			return returnJSON.toString();
		}
		if(oper == 2 && null != checkParam(params, new String[]{"messageId"})){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "messageId不能为空！");
			return returnJSON.toString();
		}

		//TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));
        if(oper == 1){

			TKindergartenMessageBoard board = new TKindergartenMessageBoard();
			board.setCreateTime(DateUtils.currentDate());
			board.setIsReply(0);
			board.setMessageContent(messageContent);
			board.setMessageTye(messageType);
			board.setUserId(userId);
			board.setUserType(userType);

            baseDAO.save(board);

			result.put("succMsg", "新增成功");
        }else if(oper == 2){

        	baseDAO.delete(TKindergartenMessageBoard.class, messageId);
			result.put("succMsg", "删除成功");
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
