package com.aj.sys.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.util.DesBase64Tool;
import com.util.Constant;

/**
 * 修改密码
 * */

@Service("updatePwd")
public class UpdatePasswordService implements PublishService{

	private Logger log = Logger.getLogger(UpdatePasswordService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String newPssword = params.optString("newPssword");
		String userId = params.optString("userId");
		String oldPassword = params.optString("oldPassword");
		String ucode = params.optString("ucode");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}else if(oldPassword == null || "".equals(oldPassword)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "oldPassword为空！");
				return returnJSON.toString();
		}else if(newPssword == null || "".equals(newPssword)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "newPssword为空！");
			return returnJSON.toString();
		}else if(ucode == null || "".equals(ucode)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "ucode为空！");
			return returnJSON.toString();
		}
		
		
		try {
			
			String hsql = " from TUser where id = ? and password = ? ";
			List<TUser> customRegList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId), DesBase64Tool.encryptDES(oldPassword) );
			if(customRegList != null && customRegList.size() > 0){
				TUser  customReg = customRegList.get(0);
				customReg.setPassword(DesBase64Tool.encryptDES(newPssword));
				
				baseDAO.update(customReg);
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				result.put("succMsg", SystemConfig.getValue("msg.updatepassword.success"));
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				result.put("succMsg", "");
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "原始密码错误！");
			}
		} catch (Exception e) {
			
			
			log.info("修改密码失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}
