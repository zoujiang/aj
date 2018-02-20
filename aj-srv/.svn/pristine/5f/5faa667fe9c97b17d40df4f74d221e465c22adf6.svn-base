package com.aam.cus.service.impl;

import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aam.cus.service.DeliAddrService;
import com.aam.cus.vo.CusModifyParamVo;
import com.aam.cus.vo.CusOperResultVo;
import com.aam.cus.vo.DeliAddrDtlParamVo;
import com.aam.cus.vo.DeliAddrDtlResultVo;
import com.aam.cus.vo.DeliAddrListParamVo;
import com.aam.cus.vo.DeliAddrListResultVo;
import com.aam.cus.vo.DeliAddrMgrParamVo;
import com.aam.cus.vo.DeliOperResultVo;
import com.aam.model.TCusDeliAddr;
import com.aam.util.constant.Constant;
import com.aam.util.constant.ServerNameConstant;
import com.frame.core.dao.GenericDAO;
import com.frame.core.exception.ServiceException;
import com.frame.core.service.AuthorService;
import com.frame.core.service.impl.PageServiceImpl;
import com.frame.core.util.BeanUtilEx;
import com.frame.core.util.GUID;
import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;
import com.frame.core.util.pagination.key.LimitKey;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.PageParamsVo;
import com.frame.core.vo.ParamsVo;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.vo.RequestVo;
import com.frame.ifpr.vo.ResponseVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Service("deliAddrServiceImpl")
public class DeliAddrServiceImpl extends PageServiceImpl<DeliAddrListResultVo> implements PublishService,DeliAddrService{
	private Log log=LogFactory.getLog(DeliAddrServiceImpl.class);

	@Autowired
	private GenericDAO baseDAO;
	@Autowired
	private AuthorService authorService;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		log.debug("publishService("+obj+") - start");
		//ResponseVo resp=gdServiceImpl.queryGdDetail(obj);
		ParamsVo pvo = ((RequestVo)obj).getParams();
		if(pvo instanceof DeliAddrDtlParamVo){
			return this.getDeliAddrDtl(obj);
		}
		if(pvo instanceof DeliAddrMgrParamVo){
			return this.doModify(obj);
		}else{
			return super.publishService(obj);
		}
		//log.debug("publishService("+obj+") - end");
		//return null;
	}

	@Override
	public Object doModify(Object obj) throws PublicException {
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		DeliAddrMgrParamVo paramVo = (DeliAddrMgrParamVo) request.getParams();
		DeliOperResultVo uor = new DeliOperResultVo();
		rsp.setServiceName(ServerNameConstant.AAM_DELI_ADDR_MGR_RESP);
		rsp.setReturnCode(ResponseVo.SUCCESS);
		if(paramVo.getOper().equals(Constant.OPER_ADD)){
			TCusDeliAddr tCusDeliAddr = new TCusDeliAddr();
			tCusDeliAddr.setCreateDt(new Date());
			//tCusDeliAddr.setLoginDt(new Date());
			tCusDeliAddr.setDeliId(GUID.generateID(Constant.AAM_PREFIX_DELI_ID));
			tCusDeliAddr.setDeliName(paramVo.getDeliName());
			tCusDeliAddr.setDeliAddr(paramVo.getDeliAreaAddr() + paramVo.getDeliDtlAddr());
			tCusDeliAddr.setDeliAreaAddr(paramVo.getDeliAreaAddr());
			tCusDeliAddr.setDeliDtlAddr(paramVo.getDeliDtlAddr());
			tCusDeliAddr.setDeliTel(paramVo.getDeliTel());
			tCusDeliAddr.setCreateUser(paramVo.getUserId());
			tCusDeliAddr.setIsValid(Constant.OPER_VALID);
			this.baseDAO.save(tCusDeliAddr);
			uor.setSuccMsg(SystemConfig.getValue("msg.deli.add.success"));
			rsp.setResult(uor);
			return rsp;
		}else if(paramVo.getOper().equals(Constant.OPER_MODIFY)){
			TCusDeliAddr tCusDeliAddr = this.baseDAO.get(TCusDeliAddr.class, paramVo.getDeliId());
			tCusDeliAddr.setDeliName(paramVo.getDeliName());
			tCusDeliAddr.setDeliAddr(paramVo.getDeliAreaAddr() + paramVo.getDeliDtlAddr());
			tCusDeliAddr.setDeliAreaAddr(paramVo.getDeliAreaAddr());
			tCusDeliAddr.setDeliAddr(paramVo.getDeliDtlAddr());
			tCusDeliAddr.setDeliTel(paramVo.getDeliTel());
			tCusDeliAddr.setModifyUser(paramVo.getUserId());
			tCusDeliAddr.setModifyDt(new Date());		
			this.baseDAO.update(tCusDeliAddr);
			uor.setSuccMsg(SystemConfig.getValue("msg.deli.modify.success"));
			rsp.setResult(uor);
			return rsp;
		}else if(paramVo.getOper().equals(Constant.OPER_DELETE)){
			TCusDeliAddr tCusDeliAddr = this.baseDAO.get(TCusDeliAddr.class, paramVo.getDeliId());
			tCusDeliAddr.setIsValid(Constant.OPER_INVALID);
			tCusDeliAddr.setModifyUser(paramVo.getUserId());
			tCusDeliAddr.setModifyDt(new Date());		
			this.baseDAO.update(tCusDeliAddr);
			uor.setSuccMsg(SystemConfig.getValue("msg.deli.delete.success"));
			rsp.setResult(uor);
			return rsp;
		}
		uor.setSuccMsg(SystemConfig.getValue("msg.deli.oper.failure"));
		rsp.setResult(uor);
		return rsp;
	}

	@Override
	public Object getDeliAddrDtl(Object obj) throws PublicException {
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		DeliAddrDtlParamVo paramVo = (DeliAddrDtlParamVo) request.getParams();
		DeliAddrDtlResultVo uor = new DeliAddrDtlResultVo();
		String hql = " from TCusDeliAddr po where po.isValid = '1' and  po.createUser=? and po.deliId = ? order by createDt desc";
		List<TCusDeliAddr> poList = baseDAO.getGenericByPosition(hql, 0, 1,paramVo.getUserId(),paramVo.getDeliId());
		
		if(poList == null || poList.size() == 0){
			rsp.setResult(null);
			rsp.setReturnCode(ResponseVo.ERROR);
			rsp.setErrorMsg(SystemConfig.getValue("msg.deli.notExits"));
			return rsp;
		}
		TCusDeliAddr cusDeliAddr = poList.get(0);
		uor.setDeliAddr(cusDeliAddr.getDeliAddr());
		uor.setDeliAreaAddr(cusDeliAddr.getDeliAreaAddr());
		uor.setDeliDtlAddr(cusDeliAddr.getDeliDtlAddr());
		uor.setDeliName(cusDeliAddr.getDeliName());
		uor.setDeliTel(cusDeliAddr.getDeliTel());
		rsp.setResult(uor);
		rsp.setServiceName(ServerNameConstant.AAM_DELI_ADDR_DTL_RESP);
		rsp.setReturnCode(ResponseVo.SUCCESS);
		return rsp;
	}

	@Override
	public DataModel<DeliAddrListResultVo> getPageList(LimitKey key,
			PageParamsVo pageParamsVo) throws ServiceException {
		try{
			log.debug("getPageList("+ key+","+ pageParamsVo + ") - start");
			DeliAddrListParamVo deliAddrListParamVo = (DeliAddrListParamVo)pageParamsVo;
			String hql = "from TCusDeliAddr po where po.isValid = '1' and  po.createUser=? order by createDt desc";
			
			List<TCusDeliAddr> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize(),deliAddrListParamVo.getUserId());
			List<DeliAddrListResultVo> voList = new ArrayList<DeliAddrListResultVo>();
			for (TCusDeliAddr tcus : poList){
				DeliAddrListResultVo curDalVo = new DeliAddrListResultVo();
				curDalVo.setDeliAddr(tcus.getDeliAddr());
				curDalVo.setDeliId(tcus.getDeliId());
				curDalVo.setDeliName(tcus.getDeliName());
				curDalVo.setDeliTel(tcus.getDeliTel());
				voList.add(curDalVo);
			}
			//List<DeliAddrListResultVo> voList = BeanUtilEx.copys(DeliAddrListResultVo.class, poList);
			DataModel<DeliAddrListResultVo> dataModel=new DataModel<DeliAddrListResultVo>();
			dataModel.setTotal(getCountByKey(key,pageParamsVo));
			dataModel.setRows(voList);
			dataModel.setServerName(ServerNameConstant.AAM_DELI_ADDR_LIST_RESP);
			log.debug("getPageList("+ key +","+ pageParamsVo + ") - end");
			return dataModel;
		}catch(ServiceException e){
			log.error("getPageList("+ key +","+ pageParamsVo + ") - error");
			
		}
		DataModel<DeliAddrListResultVo> dataModel=new DataModel<DeliAddrListResultVo>();
		dataModel.setTotal(getCountByKey(key,pageParamsVo));
		dataModel.setRows(new ArrayList<DeliAddrListResultVo>());
		dataModel.setServerName(ServerNameConstant.AAM_DELI_ADDR_LIST_RESP);
		return dataModel;
	}

	@Override
	public int getCountByKey(LimitKey key, PageParamsVo pageParamsVo)
			throws ServiceException {
		log.debug("getCountByKey("+ key +","+ pageParamsVo +") - start");
		DeliAddrListParamVo deliAddrListParamVo = (DeliAddrListParamVo)pageParamsVo;
		try{
			String hql = "select count(deliId) from TCusDeliAddr po where po.isValid = '1' and  po.createUser=? order by createDt desc";
			List<Object> list = baseDAO.getGenericByHql(hql,deliAddrListParamVo.getUserId());
			log.debug("getCountByKey("+ key +","+ pageParamsVo +") - end");
			return Integer.parseInt(list.get(0).toString());
		}catch(Exception e){
			log.error("getCountByKey("+ key +","+ pageParamsVo + ") - error");
			return 0;
		}

	}

}



