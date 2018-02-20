package com.aj.msg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.msg.model.VmMsgInfo;
import com.aj.msg.service.MsgService;
import com.aj.msg.vo.MsgLimitKey;
import com.aj.msg.vo.VmMsgInfoVo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.exception.ServiceException;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.DateUtil;
import com.frame.core.util.StringUtil;
/**     
 * 
 * @author：xuechun  
 * @version  1.0     
 */
@Service("msgServiceImpl")
public class MsgServiceImpl implements MsgService{
	private Log log=LogFactory.getLog(MsgServiceImpl.class);
	@Autowired
	private GenericDAO baseDAO;
	

	@Override
	public List<VmMsgInfoVo> queryMsgList(MsgLimitKey key) throws ServiceException {
		List<VmMsgInfoVo> voList = null;
		try{
			log.debug("queryAreaList("+ key + ") - start");
			String hql = "from VmMsgInfo po where po.isValid=1 ";
			List<Object> paramList=new ArrayList<Object>();
			if (StringUtil.isNotEmpty(key.getSearchMsgFrom())) {
				paramList.add(key.getSearchMsgFrom());
				hql += " and po.msgFrom = ? ";
			}
			if (StringUtil.isNotEmpty(key.getSearchUserTel())) {
				paramList.add(key.getSearchUserTel());
				hql += " and po.userTel = ? ";
			}
			if (StringUtil.isNotEmpty(key.getStartDt())) {
				paramList.add(DateUtil.stringToTimestamp(key.getStartDt()));
				hql += " and po.sendDt >? ";
			}
			if (StringUtil.isNotEmpty(key.getEndDt())) {
				paramList.add(DateUtil.stringToTimestamp(key.getEndDt()));
				hql += " and po.sendDt <? ";
			}
			hql += " order by po.sendDt";
			
			List<VmMsgInfo> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize(),paramList.toArray());
			voList = new ArrayList<VmMsgInfoVo>();
			
			for(VmMsgInfo po : poList){
				VmMsgInfoVo vo = new VmMsgInfoVo();
				BeanUtils.copyProperties(po, vo);
				
				voList.add(vo);
			}
		}catch(Exception e){
			log.error("queryAdvList("+ key + ") - error");
			throw new ServiceException(e);
		}
		return voList;
	}
	@Override
	public int getMsgTotalCount(MsgLimitKey key) throws ServiceException {
		log.debug("getAreaTotalCount("+ key +") - start");
		try{
			String hql = "select count(logId) from VmMsgInfo po where po.isValid=1 ";
			List<Object> paramList=new ArrayList<Object>();
			if (StringUtil.isNotEmpty(key.getSearchMsgFrom())) {
				paramList.add(key.getSearchMsgFrom());
				hql += " and po.msgFrom = ? ";
			}
			if (StringUtil.isNotEmpty(key.getSearchUserTel())) {
				paramList.add(key.getSearchUserTel());
				hql += " and po.userTel = ? ";
			}
			if (StringUtil.isNotEmpty(key.getStartDt())) {
				paramList.add(DateUtil.stringToTimestamp(key.getStartDt()));
				hql += " and po.sendDt >? ";
			}
			if (StringUtil.isNotEmpty(key.getEndDt())) {
				paramList.add(DateUtil.stringToTimestamp(key.getEndDt()));
				hql += " and po.sendDt <? ";
			}
			hql += " order by po.sendDt";
			List<Object> list = baseDAO.getGenericByHql(hql,paramList.toArray());
			log.debug("getAreaTotalCount("+ key +") - end");
			return Integer.parseInt(list.get(0).toString());
		}catch(Exception e){
			log.error("getAreaTotalCount("+ key + ") - error");
			throw new ServiceException(e);
		}
	}
	
}
