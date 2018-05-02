package com.aam.cus.service.impl;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.aam.cus.service.CusService;
import com.aam.cus.vo.CusInfoParamVo;
import com.aam.cus.vo.CusInfoResultVo;
import com.aam.cus.vo.CusLimitKey;
import com.aam.cus.vo.CusLoginParamVo;
import com.aam.cus.vo.CusLoginResultVo;
import com.aam.cus.vo.CusLogoutParamVo;
import com.aam.cus.vo.CusMgrResultVo;
import com.aam.cus.vo.CusModifyParamVo;
import com.aam.cus.vo.CusOperResultVo;
import com.aam.cus.vo.CusRegParamVo;
import com.aam.cus.vo.CusSmsApplyParamVo;
import com.aam.cus.vo.CusSmsValidateParamVo;
import com.aam.cus.vo.CusSmsValidateResultVo;
import com.aam.model.TCustom;
import com.aam.model.TCustomLogin;
import com.aam.model.TCustomReg;
import com.aam.model.TUser;
import com.aam.util.constant.Constant;
import com.aam.util.constant.ServerNameConstant;
import com.aj.easemob.service.EasemobUserService;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familyrelation.vo.TFamilyRelationship;
import com.aj.msg.api.jpush.JPushConfig;
import com.aj.sms.SingletonClient;
import com.frame.core.dao.GenericDAO;
import com.frame.core.exception.ServiceException;
import com.frame.core.service.AuthorService;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.DateUtil;
import com.frame.core.util.FileUtil;
import com.frame.core.util.GUID;
import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.ParamsVo;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.util.DesBase64Tool;
import com.frame.ifpr.vo.RequestVo;
import com.frame.ifpr.vo.ResponseVo;
import com.frame.log.service.LogBizOprService;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Service("cusServiceImpl")
public class CusServiceImpl implements PublishService,CusService{
	private Log log=LogFactory.getLog(CusServiceImpl.class);
	@Autowired
	private GenericDAO baseDAO;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private LogBizOprService logBizOprService;
	@Resource
	private EasemobUserService easemobUserService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		log.debug("publishService("+obj+") - start");
		//ResponseVo resp=gdServiceImpl.queryGdDetail(obj);
		ParamsVo pvo = ((RequestVo)obj).getParams();
		if(pvo instanceof CusLoginParamVo){
			return this.doLogin(obj);
		}
		if(pvo instanceof CusLogoutParamVo){
			return this.doLogOut(obj);
		}
		if(pvo instanceof CusModifyParamVo){
			return this.doModify(obj);
		}
		if(pvo instanceof CusRegParamVo){
			return this.doReg(obj);
		}
		if(pvo instanceof CusInfoParamVo){
			return this.getCusInfo(obj);
		}if(pvo instanceof CusSmsApplyParamVo){
			return this.doUserRegSmsApplyToken(obj);
		}if(pvo instanceof CusSmsValidateParamVo){
			return this.doUserRegSmsValidate(obj);
		}
		log.debug("publishService("+obj+") - end");
		return null;
	}
	
	//
	public Object doLogin(Object obj) throws PublicException {
		log.debug("login("+ obj +") - start");
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		CusLoginParamVo paramVo = (CusLoginParamVo) request.getParams();
		CusLoginResultVo rsv = new CusLoginResultVo();
		
		String hql = "from TUser po where 1=1 ";
		if (StringUtil.isEmpty(paramVo.getUserTel())&&
				StringUtil.isEmpty(paramVo.getUserPwd())){
			rsp.setResult(null);
			rsp.setResult(rsv);
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setErrorMsg(SystemConfig.getValue("msg.login.pwd.notmatched"));
			return rsp;
		}else{
			hql += " and po.userTel = '" + paramVo.getUserTel() + "'";
			hql += " and po.password = '" + DesBase64Tool.encryptDES(paramVo.getUserPwd()) + "'";
		}
		
	//	List<TGdTypePo> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize());
		List<TUser> tcs =  baseDAO.getGenericByHql(hql);
		if(tcs == null || tcs.size() == 0){
			rsp.setResult(null);
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setErrorMsg(SystemConfig.getValue("msg.login.pwd.notmatched"));
			return rsp;
		}	
		//登陆成功，需要插入日志
		//tcs.get(0);
		
		TCustomLogin tcLogin = new TCustomLogin();
		tcLogin.setUserId(tcs.get(0).getId()+"");
		logBizOprService.saveLog("用户登陆","4" , "用户登陆(登陆用户ID:" + tcs.get(0).getId()  + "用户电话号码：" + tcs.get(0).getUserTel() +")", "", "");
		tcLogin.setLoginDt(new Date());
		tcLogin.setTokenId(GUID.generateID(Constant.TB_PREFIX_TOKEN));
		baseDAO.saveOrUpdate(tcLogin);
		CusLoginResultVo ulrv = new CusLoginResultVo();
		ulrv.setTokenId(tcLogin.getTokenId());
		ulrv.setSuccMsg(SystemConfig.getValue("msg.login.success"));
		ulrv.setUserId(tcLogin.getUserId());
		rsp.setResult(ulrv);
		rsp.setServiceName(ServerNameConstant.AAM_USER_LOGIN_RESP);
		rsp.setReturnCode(ResponseVo.SUCCESS);
		 String origin = JPushConfig.appkey + ":" + JPushConfig.masterSecret;
		 log.debug("login("+ obj +") - end");
			try{
				 String base64_auth_string=this.getBase64(origin);
				  com.frame.core.util.HttpClient.doDelete("https://device.jpush.cn/v3/aliases/"+tcs.get(0).getId(), base64_auth_string) ;
			}catch(Exception e){
				
			}
		return rsp;
	}
	public static String getBase64(String str) {  
	       byte[] b = null;  
	       String s = null;  
	       try {  
	           b = str.getBytes("utf-8");  
	       } catch (UnsupportedEncodingException e) {  
	           e.printStackTrace();  
	       }  
	       if (b != null) {  
	           s = new BASE64Encoder().encode(b);  
	       }  
	       return s;  
	   }  
	@Override
	public Object doLogOut(Object obj) throws PublicException {
		log.debug("doLogOut("+ obj +") - start");
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		CusLogoutParamVo paramVo = (CusLogoutParamVo) request.getParams();
		String hql = "from TCustomLogin po where po.userId=? order by loginDt desc";
	//	List<TGdTypePo> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize());
		// baseDAO.getGenericByPosition(tCustomHql, 0, 1, paramVo.getUserTel());
		List<TCustomLogin> tcs =  baseDAO.getGenericByPosition(hql, 0, 1,paramVo.getUserId());
		if(tcs == null || tcs.size() == 0){
			rsp.setResult(null);
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setErrorMsg(SystemConfig.getValue("msg.logout.failure"));
			return rsp;
		}
		//登陆成功，需要插入日志
		TCustomLogin tcLogin = tcs.get(0);
		logBizOprService.saveLog("用户登出","5" , "用户登出(登陆用户ID:" + tcLogin.getUserId()  +")", "", "");

		tcLogin.setLogoutDt(new Date());
		baseDAO.save(tcLogin);
		CusOperResultVo uor = new CusOperResultVo();
		uor.setSuccMsg(SystemConfig.getValue("msg.logout.success"));
		rsp.setResult(uor);
		rsp.setReturnCode(ResponseVo.SUCCESS);
			log.debug("login("+ obj +") - end");
		return rsp;

	}

	@Override
	public Object doModify(Object obj) throws PublicException {
		String imgUrl= SystemConfig.getValue("img.http.url");
		log.debug("doReg("+ obj +") - start");
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		CusModifyParamVo paramVo = (CusModifyParamVo) request.getParams();
		CusOperResultVo uor = new CusOperResultVo();
		String userId = paramVo.getUserId();
		//修改个人信息
		if(userId == null || "".equals(userId)){
			
			uor.setSuccMsg("");
			rsp.setResult(uor);
			rsp.setErrorMsg("userId为空");
			rsp.setReturnCode(ResponseVo.SYS_ERROR);
			return rsp;
		}
		
		TUser tCustom = null;
		TFamilyInfo family = null;
		try {
			tCustom = baseDAO.get(TUser.class, Integer.parseInt(userId));
			family = baseDAO.get(TFamilyInfo.class, tCustom.getFamilyId());
			if(paramVo.getAddress() != null){
				tCustom.setAddress(paramVo.getAddress());
			}
			if(paramVo.getSign() != null){
				tCustom.setAutograph(paramVo.getSign());
			}
			if(paramVo.getBirthday() != null){
				tCustom.setBirthday(paramVo.getBirthday());
			}
			if(paramVo.getNickName()!= null){
				tCustom.setNickName(paramVo.getNickName());
			}
			if(paramVo.getPhoto() != null){
				tCustom.setPhoto(paramVo.getPhoto().replace(imgUrl, ""));
			}
			if(paramVo.getSex() != null && !"".equals(paramVo.getSex())){
				tCustom.setSex(Integer.parseInt(paramVo.getSex()));
			}
			if(paramVo.getTrueName() != null){
				tCustom.setTrueName(paramVo.getTrueName());
			}
			if(paramVo.getIsMarried() != null && !"".equals(paramVo.getIsMarried())){
				if("3".equals(paramVo.getIsMarried())){
					String hsql = "from TFamilyRelationship where createUserId = ? and (callId = 5  or callId = 6) and isConfirm = 0 and isDel = 1";
					List<TFamilyRelationship> familyRelationship =  baseDAO.getGenericByHql(hsql, Integer.parseInt(userId));
					if(familyRelationship != null && familyRelationship.size() > 0){
						uor.setSuccMsg("");
						rsp.setResult(uor);
						rsp.setErrorMsg("您已经有婚姻关系，不能设置为未婚");
						rsp.setReturnCode(ResponseVo.SYS_ERROR);
						return rsp;
					}else{
						family.setIsMarried(Integer.parseInt(paramVo.getIsMarried()));
					//	tCustom.setIsMarried(Integer.parseInt(paramVo.getIsMarried()));
					}
				}else{
					family.setIsMarried(Integer.parseInt(paramVo.getIsMarried()));
					//tCustom.setIsMarried(Integer.parseInt(paramVo.getIsMarried()));
				}
			}
			baseDAO.saveOrUpdate(tCustom);
			logBizOprService.saveLog("用户修改资料","2" , "用户修改资料(用户ID:" + tCustom.getId()  +",用户电话号码:"+ tCustom.getUserTel()  +")", "", "");
			
			//修改成功之后，如果昵称有变化，更新环信中的昵称信息
			if(paramVo.getNickName()!= null){
				JSONObject paramJSON = new JSONObject();
				paramJSON.put("ownerUserName", tCustom.getId());
				paramJSON.put("nickname",paramVo.getNickName());
				easemobUserService.modNick(paramJSON);
			}
			
			uor.setSuccMsg(SystemConfig.getValue("msg.user.info.modify.success"));
			rsp.setResult(uor);
			rsp.setReturnCode(ResponseVo.SUCCESS);
			return rsp;
		} catch (Exception e) {
			e.printStackTrace();
			uor.setSuccMsg(SystemConfig.getValue("更新资料失败！"));
			rsp.setResult(uor);
			rsp.setReturnCode(ResponseVo.SYS_ERROR);
			return rsp;
		}
	}

	@Override
	public Object getCusInfo(Object obj) throws PublicException {
		log.debug("getCusInfo("+ obj +") - start");
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		CusInfoParamVo paramVo = (CusInfoParamVo) request.getParams();
		//先判断是否存在此注册用户，按照userId计算
		String hql = "from TUser po where po.id = ? and po.isValid = '0' ";
		List<TUser> tcs =  baseDAO.getGenericByHql(hql,Integer.parseInt(paramVo.getUserId()));
		if(tcs == null || tcs.size() == 0){
			rsp.setResult(null);
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setErrorMsg(SystemConfig.getValue("msg.user.modify.notexits"));
			return rsp;
		}
		//查询家庭信息，是否已婚
		TFamilyInfo family = baseDAO.get(TFamilyInfo.class, tcs.get(0).getFamilyId());
		//查询user表中同一个familyid下面的用户个数
		List<TUser> users = baseDAO.getGenericByHql("from TUser where isValid = '0' and familyId = ?", new Object[]{tcs.get(0).getFamilyId()});
		CusInfoResultVo uor = new CusInfoResultVo();
		uor.setAddress(tcs.get(0).getAddress());
		uor.setBirthday(tcs.get(0).getBirthday());
		if(tcs.get(0).getPhoto() == null || "null".equals(tcs.get(0).getPhoto())){
			uor.setPhoto("");
		}else{
			uor.setPhoto(SystemConfig.getValue("img.http.url") + FileUtil.getResponseAddr(tcs.get(0).getPhoto()));
		}
		uor.setNickName(tcs.get(0).getNickName());
		uor.setTrueName(tcs.get(0).getTrueName());
		uor.setSex(tcs.get(0).getSex()+"");
		uor.setSign(tcs.get(0).getAutograph());
		uor.setUserId(tcs.get(0).getId()+"");
		uor.setUserTel(tcs.get(0).getUserTel());
		uor.setIsMarried(family.getIsMarried()+"");
		uor.setAjNo(tcs.get(0).getAjNo());
		uor.setIsVip(tcs.get(0).getIsVip());
		uor.setVipExpiredDate(tcs.get(0).getVipExpiredDate());
		if(tcs.get(0).getVipExpiredDate() == null || "".equals(tcs.get(0).getVipExpiredDate())){
			uor.setIsVip(1);
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if(format.parse(tcs.get(0).getVipExpiredDate()).before(new Date())){
					uor.setIsVip(1);
				}else{
					uor.setIsVip(0);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		int userType = 1;
		if(tcs.get(0).getType() == 2){
			userType =2;
		}else if(tcs.get(0).getType() == 4 || tcs.get(0).getType() == 5){
			userType =3;
		}
		uor.setType(userType);
		
		if(users.size() > 1){
			uor.setFlag("1");
		}else{
			uor.setFlag("0");
		}
		/*String sql = "from TFamilyRelationKey where userId = ? ";
		List<TFamilyRelationKey> tfrkList = baseDAO.getGenericByHql(sql, Integer.parseInt(paramVo.getUserId()));
		if(tfrkList != null && tfrkList.size() > 0){
			
			uor.setKey(tfrkList.get(0).getKey());
		}else{
			uor.setKey("");
		}*/
		rsp.setResult(uor);
		rsp.setReturnCode(ResponseVo.SUCCESS);
		
		log.debug("getCusInfo("+ obj +") - end");
		return rsp;
	}
	
	
	@Override
	public Object doReg(Object obj) throws PublicException {
		log.debug("doReg("+ obj +") - start");
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		CusRegParamVo paramVo = (CusRegParamVo) request.getParams();
		//先判断是否存在此注册用户，按照userId计算
		String hql = "from TUser po where po.userTel = ?  ";
		List<TUser> tcs =  baseDAO.getGenericByHql(hql,paramVo.getUserTel());
		if(tcs != null && tcs.size() > 0){
			rsp.setResult(null);
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setErrorMsg(SystemConfig.getValue("msg.reg.user.exits"));
			return rsp;
		}
		//手机号码验证
		float validMinutes = Float.parseFloat(SystemConfig.getValue("sms.valid.minutes"))/24*60;
		String validHql = "from TCustomReg po where po.userTel = ?  and po.createDt > CURRENT_TIME()  - ? order by createDt desc " ;
		List<TCustomReg> tcd =  baseDAO.getGenericByPosition(validHql, 0, 1, paramVo.getUserTel(),validMinutes);
		if(tcd.size() > 0){
			TCustomReg tCustomReg = tcd.get(0);
			if(!tCustomReg.getSmsCode().equals(paramVo.getSmsValidateToken())){
				//
				rsp.setErrorMsg(SystemConfig.getValue("msg.sms.inValid"));
				rsp.setReturnCode(ResponseVo.ERROR);
				rsp.setResult(null);
				return rsp;
			}
			//验证通过，直接注册成功
			String tCustomHql = "from TUser po where po.userTel = ?" ;
			List<TUser> tct =  baseDAO.getGenericByPosition(tCustomHql, 0, 1, paramVo.getUserTel());
			if(tct.size() > 0){
				rsp.setErrorMsg(SystemConfig.getValue("msg.user.pwd.modify.userExit"));
				rsp.setReturnCode(ResponseVo.ERROR);
				rsp.setResult(null);
				return rsp;
			}
			
			TUser tCustom = new TUser();
			tCustom.setUserTel(paramVo.getUserTel());
			tCustom.setPassword(DesBase64Tool.encryptDES(paramVo.getUserPwd()));
			tCustom.setIsVip(0);
		/*	tCustom.setSex("2");
			tCustom.setIsValid("1");
			tCustom.setCreateDt(new Date());
			tCustom.setCreateUser(tCustom.getUserId());
			tCustom.setAppVer(paramVo.getAppVer());
			tCustom.setPhoneVer(tCustom.getPhoneVer());
			tCustom.setPhoneSysVer(tCustom.getPhoneSysVer());
			tCustom.setChannelId(paramVo.getChannelId());
			tCustom.setIp(paramVo.getIp());
			tCustom.setUa(paramVo.getUa());
			tCustom.setUcode(paramVo.getUcode());*/
		
		Object pk = baseDAO.save(tCustom);
		
		
		CusOperResultVo uor = new CusOperResultVo();
		uor.setSuccMsg(SystemConfig.getValue("msg.reg.success"));
		rsp.setResult(uor);
		rsp.setReturnCode(ResponseVo.SUCCESS);
		logBizOprService.saveLog("用户注册","9" , "用户注册(用户ID:" + tCustom.getId()  +",用户电话号码:"+ tCustom.getUserTel()  +")", "", "");

		log.debug("doLogOut("+ obj +") - end");
		return rsp;
		}else{
			rsp.setErrorMsg(SystemConfig.getValue("msg.sms.inExpired"));
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setResult(null);
			return rsp;
		}
	}

	@Override
	public boolean checkToken(String token, String userId)
			 {
		log.debug("checkToken("+ token + "," + userId  +") - start");
		float validHours = Float.parseFloat(SystemConfig.getValue("ifpr.tokenControl.hours"))/24;
		String hql = null;
		if(token.equals(SystemConfig.getValue("ifpr.tokenControl.tv.testt")))return true;
		hql = "from TCustomLogin po where po.tokenId = ?  and userId = ?   and po.loginDt > CURRENT_TIME()  - ?";
		List<TCustomLogin> tcs =  baseDAO.getGenericByHql(hql,token,userId,validHours);
		if(tcs != null && tcs.size() > 0){
			return true;
		}
		return false;
	}
		
	@Override
	public List<CusMgrResultVo> queryCusPageList(CusLimitKey key)
			throws ServiceException {
		log.debug("queryCusPageList("+ key+","+ key + ") - start");
		String hql = "from TCustom po where isValid = '1' ";
		if (StringUtil.isNotEmpty(key.getSearchText())) {
			hql += " and po.userTel like '%" + key.getSearchText() + "%'";
			
		}
		hql += " order by  po.createDt desc";
		
		List<TCustom> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize());
//		List<CusMgrResultVo> voList = BeanUtilEx.copys(CusMgrResultVo.class, poList);
		List<CusMgrResultVo> voList = new ArrayList<CusMgrResultVo>();
		
		for(TCustom po : poList){
			CusMgrResultVo vo = new CusMgrResultVo();
			BeanUtils.copyProperties(po, vo);
				if (authorService.existInRole(SystemConfig.getValue("srv.admin.role"))) {
					vo.setEdit(true);//修改权限
					vo.setDelete(true);//删除
				}
			voList.add(vo);
		}
	
		return voList;
	}

	@Override
	public int getGdTotalCount(CusLimitKey key) throws ServiceException {
		log.debug("queryCusPageList("+ key+","+ key + ") - start");
		String hql = "select count(userId) from TCustom po where isValid = '1' ";
		if (StringUtil.isNotEmpty(key.getSearchText())) {
			hql += " and po.userTel like '%" + key.getSearchText() + "%'";
		}
		
		if (StringUtil.isNotEmpty(key.getStartDt())) {
			Timestamp st = DateUtil.stringToTimestamp(key.getStartDt());
			hql += " and po.createDt >= " + st + "";
		}
		if (StringUtil.isNotEmpty(key.getEndDt())) {
			Timestamp st = DateUtil.stringToTimestamp(key.getEndDt());
			hql += " and po.createDt <= " + st + "";
		}
		
		List<Object> list = baseDAO.getGenericByHql(hql);
		log.debug("getCountByKey("+ key +") - end");
		return Integer.parseInt(list.get(0).toString());
	}

	@Override
	public Object doUserRegSmsApplyToken(Object obj) {
		// TODO Auto-generated method stub
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		CusSmsApplyParamVo paramVo = (CusSmsApplyParamVo) request.getParams();
		String userTel = paramVo.getUserTel();
		String type = paramVo.getType();
		if(userTel == null || "".equals(userTel)|| !isPhoneNumber(userTel)){
			rsp.setErrorMsg("手机号码不正确");
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setResult(null);
			return rsp;
		}
		if(type == null || "".equals(type)){
			rsp.setErrorMsg("type不能为空");
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setResult(null);
			return rsp;
		}
		if("1".equals(type)){
			//注册，验证手机号是否已经注册
			List<TUser> users = baseDAO.getGenericByHql("from TUser where userTel = ? and isValid = 0 ", userTel);
			if(users != null && users.size() > 0){
				rsp.setErrorMsg("该号码已经注册了哦");
				rsp.setReturnCode(ResponseVo.ERROR);
				rsp.setResult(null);
				return rsp;
			}
		}
		
		Calendar cal = Calendar.getInstance();  
		Date date = new Date();  
		cal.setTime(date);  
		cal.add(Calendar.MINUTE, -10);  
		Date startDt = cal.getTime(); 
		
		//10分钟三条
		
				String hql = "select count(id) from TCustomReg po where userTel = ? and createDt > ? ";
				List<Object> list = baseDAO.getGenericByHql(hql,userTel,startDt);
				int sendCnt = Integer.parseInt(list.get(0).toString());
				
				
				if(sendCnt>3){
					rsp.setErrorMsg(SystemConfig.getValue("msg.sms.apply.10minlimitcnt"));
					rsp.setReturnCode(ResponseVo.ERROR);
					rsp.setResult(null);
					return rsp;
				}
				
				cal.setTime(date);  
				cal.set(Calendar.HOUR_OF_DAY, 0);  
				cal.set(Calendar.MINUTE, 0);  
				cal.set(Calendar.SECOND, 0);  
				cal.set(Calendar.MILLISECOND, 0);  
				startDt = cal.getTime(); 
		//一天10条
		 hql = "select count(id) from TCustomReg po where userTel = ? and createDt > ? ";
		list = baseDAO.getGenericByHql(hql,userTel,startDt);
		sendCnt = Integer.parseInt(list.get(0).toString());
		
		
		if(sendCnt>10){
			rsp.setErrorMsg(SystemConfig.getValue("msg.sms.apply.1daylimitcnt"));
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setResult(null);
			return rsp;
		}
		
		
		
		
		
		//下发短信
		String smsCode="123456";
		if(!StringUtil.equals(SystemConfig.getValue("msg.sms.apply.testflag"), "1")){
			smsCode=getRandNum(6);
			try {
				 SingletonClient.getClient().sendSMS(new String[] {userTel}, SystemConfig.getValue("msg.sms.apply.content")+smsCode, "",5);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rsp.setErrorMsg(SystemConfig.getValue("msg.sms.apply.failure"));
				rsp.setReturnCode(ResponseVo.ERROR);
				rsp.setResult(null);
				return rsp;
			}
			
		}
		
		//下发短信
		/*if(false){
			//todo
			rsp.setErrorMsg(SystemConfig.getValue("msg.sms.apply.failure"));
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setResult(null);
			return rsp;
		}*/
		//保存数据库
		TCustomReg tCustomReg = new TCustomReg();
		tCustomReg.setSmsCode(smsCode);
		tCustomReg.setSmsTokenId(GUID.generateID(Constant.AAM_PREFIX_SMS_TKN));
		tCustomReg.setCreateDt(new Date());
		tCustomReg.setUserTel(userTel);
		tCustomReg.setId(GUID.generateID(Constant.AAM_PREFIX_SMS_ID));
		this.baseDAO.save(tCustomReg);
		CusOperResultVo uor = new CusOperResultVo();
		uor.setSuccMsg(SystemConfig.getValue("msg.sms.apply.success"));
		logBizOprService.saveLog("用户获取验证码","9" , "用户获取手机验证码(用户ID:" + tCustomReg.getId()  +",用户电话号码:"+ tCustomReg.getUserTel()  +")", "", "");

		rsp.setResult(uor);
		rsp.setReturnCode(ResponseVo.SUCCESS);
			log.debug("login("+ obj +") - end");
		return rsp;
	}
	public  int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
	
	private  String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;

   }

	@Override
	public Object doUserRegSmsValidate(Object obj) {
		// TODO Auto-generated method stub
				ResponseVo rsp = new ResponseVo();
				RequestVo request = (RequestVo) obj;
				CusSmsValidateParamVo paramVo = (CusSmsValidateParamVo) request.getParams();
				//String userTel = paramVo.getUserTel();
				float validMinutes = Float.parseFloat(SystemConfig.getValue("sms.valid.minutes"))/24*60;
				String hql = "from TCustomReg po where po.userTel = ?  and po.createDt > CURRENT_TIME()  - ? order by createDt desc " ;
				List<TCustomReg> tcd =  baseDAO.getGenericByPosition(hql, 0, 1, paramVo.getUserTel(),validMinutes);
				if(tcd.size() > 0){
					TCustomReg tCustomReg = tcd.get(0);
					String key = SystemConfig.getValue("sms.valid.key.test");
					if(!tCustomReg.getSmsCode().equals(paramVo.getSmsCode())){
						//
						rsp.setErrorMsg(SystemConfig.getValue("msg.sms.validate.failure"));
						rsp.setReturnCode(ResponseVo.ERROR);
						rsp.setResult(null); 
						return rsp;
					}else{
						//验证通过，返回短信码Token
						CusSmsValidateResultVo csvResultVo = new CusSmsValidateResultVo();
						csvResultVo.setSmsValidateToken(tCustomReg.getSmsTokenId());
						csvResultVo.setSuccMsg(SystemConfig.getValue("msg.sms.validate.success"));
						rsp.setResult(csvResultVo);
						rsp.setReturnCode(ResponseVo.SUCCESS);
						return rsp;
					}
				}else{
					rsp.setErrorMsg(SystemConfig.getValue("msg.sms.inValid"));
					rsp.setReturnCode(ResponseVo.ERROR);
					rsp.setResult(null);
					return rsp;
				}
				
	}

	@Override
	public boolean deleteCus(String id) {
		try{
			TCustom cus = this.baseDAO.load(TCustom.class, id);
			cus.setIsValid("0");
			cus.setModifyUser(authorService.getSessionUserPo().getAccount());
			cus.setModifyDt(DateUtil.dateToTimestamp(new Date()));
			this.baseDAO.update(cus);
			logBizOprService.saveLog("用户资料管理","3" , "删除用户(用户电话号码：" + cus.getUserTel() +")", "", "");

			return true;
		}catch(Exception e){
			return false;
		}
	}
	  private static boolean isPhoneNumber(String phone){
	        String reg = "^\\d{11}$";
	        return Pattern.compile(reg).matcher(phone).find();
	    }
}



