package com.aj.sys.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.cus.vo.CusSmsValidateParamVo;
import com.aam.cus.vo.CusSmsValidateResultVo;
import com.aam.model.TCustomReg;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.vo.RequestVo;
import com.frame.ifpr.vo.ResponseVo;
import com.util.Constant;

/**
 * 修改用户手机号码
 * */

@Service("changeUserTel")
public class ChangeUserTelService implements PublishService{

	private Logger log = Logger.getLogger(ChangeUserTelService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String userTel = params.optString("userTel");
		String ucode = params.optString("ucode");
		String smsCode = params.optString("smsCode");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}else if(userTel == null || "".equals(userTel)){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "userTel为空！");
				return returnJSON.toString();
		}else if(smsCode == null || "".equals(smsCode)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "smsCode为空！");
			return returnJSON.toString();
		}
		
		//验证短信验证码
		float validMinutes = Float.parseFloat(SystemConfig.getValue("sms.valid.minutes"))/24*60;
		String hql = "from TCustomReg po where po.userTel = ?  and po.createDt > CURRENT_TIME()  - ? order by createDt desc " ;
		List<TCustomReg> tcd =  baseDAO.getGenericByPosition(hql, 0, 1, userTel,validMinutes);
		if(tcd.size() > 0){
			TCustomReg tCustomReg = tcd.get(0);
			if(!tCustomReg.getSmsCode().equals(smsCode)){
				//
				returnJSON.put("errorMsg",SystemConfig.getValue("msg.sms.validate.failure"));
				returnJSON.put("returnCode",ResponseVo.ERROR);
				returnJSON.put("result",null); 
				return returnJSON.toString();
			}
		}else{
			returnJSON.put("errorMsg",SystemConfig.getValue("msg.sms.inValid"));
			returnJSON.put("returnCode",ResponseVo.ERROR);
			returnJSON.put("result",null); 
			return returnJSON.toString();
		}
		
		try {
			
			String hsql = " update  TUser set userTel = ?  where id = ? ";
			baseDAO.update(hsql,userTel ,Integer.parseInt(userId) );
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			result.put("succMsg", "修改成功");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
		} catch (Exception e) {
			
			log.info("修改用户手机号码，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}
