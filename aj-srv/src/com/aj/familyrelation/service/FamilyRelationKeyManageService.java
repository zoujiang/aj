package com.aj.familyrelation.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.cus.vo.CusOperResultVo;
import com.aam.cus.vo.CusSmsValidateResultVo;
import com.aam.model.TCustomReg;
import com.aam.model.TUser;
import com.aj.familyrelation.vo.TFamilyRelationKey;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.GUID;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.vo.ResponseVo;
import com.frame.log.service.LogBizOprService;
import com.util.Constant;
import com.util.DateUtils;


/**
 * 9.5管理家庭关系密钥（aj_family_relation_key_manage）
 * */

@Service("familyRelationKeyManage")
public class FamilyRelationKeyManageService implements PublishService{

	private Logger log = Logger.getLogger(FamilyRelationKeyManageService.class);
	
	@Resource
	private GenericDAO baseDAO;
	@Resource 
	private LogBizOprService logBizOprService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String oper = params.optString("oper");
		String key = params.optString("key");
		String smsCode = params.optString("smsCode");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(oper == null || "".equals(oper)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "oper为空！");
			return returnJSON.toString();
		}
		if( !"4".equals(oper) && (key == null || "".equals(key))){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "key为空！");
			return returnJSON.toString();
		}
		TUser user = (TUser) baseDAO.getGenericByHql("from TUser where id = ?", Integer.parseInt(userId)).get(0);
		String hsql = "from TFamilyRelationKey where userId = ? ";
		List<TFamilyRelationKey> pkList = baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
		if("1".equals(oper)){
			//设置密钥
			if(pkList != null && pkList.size() >0){
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您已经设置过密钥");
				return returnJSON.toString();
			}else{
				TFamilyRelationKey familyRelationKey = new TFamilyRelationKey();
				familyRelationKey.setCreateDate(DateUtils.currentDate());
				familyRelationKey.setKey(key);
				familyRelationKey.setUserId(Integer.parseInt(userId));
				baseDAO.save(familyRelationKey);
				result.put("succMsg", "设置成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "");
				return returnJSON.toString();
			}
		}else if("2".equals(oper)){
			//删除密钥
			if(pkList != null && pkList.size() >0){
				if(key.equals(pkList.get(0).getKey())){
					
					baseDAO.delete(TFamilyRelationKey.class, pkList.get(0).getId());
					result.put("succMsg", "清除密钥成功！");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "");
					return returnJSON.toString();
				}else{
					
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "密钥验证不通过，清除密钥失败");
					return returnJSON.toString();
				}
				
			}else{
				
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您还没有设置密钥");
				return returnJSON.toString();
			}
		}else if("3".equals(oper)){
			//修改
			if(pkList != null && pkList.size() >0){
				
				TFamilyRelationKey tFamilyRelationKey = pkList.get(0);
				tFamilyRelationKey.setKey(key);
				baseDAO.update(tFamilyRelationKey);
				result.put("succMsg", "修改密钥成功！");
				returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "修改密钥成功");
				return returnJSON.toString();
				
			}else{
				
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "您还没有设置密钥");
				return returnJSON.toString();
			}
		}else if("4".equals(oper)){
			//找回密钥
			
			//TODO 下发短信
			
			//保存数据库
			TCustomReg tCustomReg = new TCustomReg();
			tCustomReg.setSmsCode("123456");
			tCustomReg.setCreateDt(new Date());
			tCustomReg.setUserTel(user.getUserTel());
			tCustomReg.setId(GUID.generateID(Constant.AAM_PREFIX_SMS_ID));
			this.baseDAO.save(tCustomReg);
			CusOperResultVo uor = new CusOperResultVo();
			uor.setSuccMsg(SystemConfig.getValue("msg.sms.apply.success"));
			logBizOprService.saveLog("用户获取验证码","9" , "用户获取手机验证码(用户ID:" + tCustomReg.getId()  +",用户电话号码:"+ tCustomReg.getUserTel()  +")", "", "");
			result.put("succMsg", "短信验证码下发成功！");
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "短信验证码下发成功");
			return returnJSON.toString();
		}else if("5".equals(oper)){
			//验证短信 smsCode
			float validMinutes = Float.parseFloat(SystemConfig.getValue("sms.valid.minutes"))/24*60;
			String hql = "from TCustomReg po where po.userTel = ?  and po.createDt > CURRENT_TIME()  - ? order by createDt desc " ;
			List<TCustomReg> tcd =  baseDAO.getGenericByPosition(hql, 0, 1, user.getUserTel(),validMinutes);
			if(tcd.size() > 0){
				TCustomReg tCustomReg = tcd.get(0);
				String testkey = SystemConfig.getValue("sms.valid.key.test");
				if(!tCustomReg.getSmsCode().equals(smsCode)){
					//
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "短信验证码错误");
					return returnJSON.toString();
				}else{
					//验证通过
					String delHsql = "delete from TFamilyRelationKey where userId = " + Integer.parseInt(userId);
					baseDAO.deleteByHsql(delHsql);
					result.put("succMsg", "成功清除密钥，请重新设置！");
					returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "成功清除密钥，请重新设置");
					return returnJSON.toString();
				}
			}else{
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "短信验证码无效");
				return returnJSON.toString();
			}
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "错误的操作类型");
			return returnJSON.toString();
		}
		
	}

}
