package com.demo.area.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.area.service.AreaService;
import com.demo.area.vo.AreaLimitKey;
import com.demo.area.vo.AreaMgrVo;
import com.demo.area.vo.AreaResultVo;
import com.demo.model.TArea;
import com.frame.core.dao.GenericDAO;
import com.frame.core.exception.ServiceException;
import com.frame.core.service.AuthorService;
import com.frame.core.service.impl.PageServiceImpl;
import com.frame.core.util.BeanUtilEx;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.DateUtil;
import com.frame.core.util.GUID;
import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;
import com.frame.core.util.pagination.key.LimitKey;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.PageParamsVo;
import com.frame.ifpr.constant.ServerNameConstant;
import com.frame.log.service.LogBizOprService;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Service("areaServiceImpl")
public class AreaServiceImpl extends PageServiceImpl<AreaResultVo> implements AreaService{
	private Log log=LogFactory.getLog(AreaServiceImpl.class);
	@Autowired
	private GenericDAO baseDAO;
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public DataModel<AreaResultVo> getPageList(LimitKey key, PageParamsVo pageParamsVo)
			throws ServiceException {
		try{
			log.debug("getPageList("+ key+","+ pageParamsVo + ") - start");
			String hql = "from TArea po where po.isValid = '1' ";
			if (StringUtil.isNotEmpty(key.getSearchText())) {
				hql += " and po.areaName like '%" + key.getSearchText() + "%'";
			}
				hql += " order by po.areaOrder,po.createDt ";
			
			List<TArea> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize());
			List<AreaResultVo> voList = BeanUtilEx.copys(AreaResultVo.class, poList);
			
			DataModel<AreaResultVo> dataModel=new DataModel<AreaResultVo>();
			dataModel.setTotal(getCountByKey(key,pageParamsVo));
			dataModel.setRows(voList);
			log.debug("getPageList("+ key +","+ pageParamsVo + ") - end");

			return dataModel;
		}catch(ServiceException e){
			log.error("getPageList("+ key +","+ pageParamsVo + ") - error");
			throw new ServiceException(e);
		}
	}

	@Override
	public int getCountByKey(LimitKey key, PageParamsVo pageParamsVo)
			throws ServiceException {
		log.debug("getCountByKey("+ key +","+ pageParamsVo +") - start");
		try{
			String hql = "select count(areaId) from TArea where isValid = '1'";
			if (StringUtil.isNotEmpty(key.getSearchText())) {
				hql += "  and areaName like '%" + key.getSearchText() + "%'";
			}
			List<Object> list = baseDAO.getGenericByHql(hql);
			log.debug("getCountByKey("+ key +","+ pageParamsVo +") - end");
			return Integer.parseInt(list.get(0).toString());
		}catch(Exception e){
			log.error("getCountByKey("+ key +","+ pageParamsVo + ") - error");
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveArea(AreaMgrVo vo) throws ServiceException {
		try{
			if(StringUtil.isEmpty(vo.getAreaId())){
				TArea po=new TArea();
				BeanUtils.copyProperties(vo, po);
				po.setAreaId(GUID.generateID("AREA"));
				po.setCreateUser(authorService.getSessionUserPo().getAccount());
				po.setCreateDt(DateUtil.dateToTimestamp(new Date()));
				logBizOprService.saveLog("区域管理","1" , "新增区域(区域名称:" + vo.getAreaName() + ")" , "", "");

				baseDAO.save(po);
			}else{
				TArea po=baseDAO.get(TArea.class, vo.getAreaId());
				BeanUtils.copyProperties(vo, po);
				po.setModifyDt(DateUtil.dateToTimestamp(new Date()));
				po.setModifyUser(authorService.getSessionUserPo().getAccount());
				baseDAO.merge(po);
				logBizOprService.saveLog("区域管理","2" , "修改区域(区域ID:" + vo.getAreaId()+ ")" , "", "");
			}
		}catch(Exception e){
			if(vo.getAreaId()==null){
				logBizOprService.saveLog("区域管理","1" , "新增区域(区域ID:" + vo.getAreaName()+ ")失败,"+ e.getStackTrace() , "", "");
			}else{
				logBizOprService.saveLog("区域管理","2" , "修改区域(区域ID:" + vo.getAreaId()+ ")失败,"+ e.getStackTrace() , "", "");
			}
			throw new ServiceException(e);
		}
	}

	@Override
	public List<AreaMgrVo> queryAreaList(AreaLimitKey key) throws ServiceException {
		List<AreaMgrVo> voList = null;
		try{
			log.debug("queryAreaList("+ key + ") - start");
			String hql = "from TArea po where 1=1 ";
			List<Object> paramList=new ArrayList<Object>();
			if (StringUtil.isNotEmpty(key.getSearchText())) {
				paramList.add("%"+key.getSearchText()+"%");
				hql += " and po.areaName like ? ";
			}
			hql += " order by  po.areaOrder,po.createDt ";
			
			List<TArea> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize(),paramList.toArray());
			voList = new ArrayList<AreaMgrVo>();
			
			for(TArea po : poList){
				AreaMgrVo vo = new AreaMgrVo();
				BeanUtils.copyProperties(po, vo);
				if(po.getCreateUser()!=null){
					if (authorService.isCreateUser(po.getCreateUser())||authorService.existInRole(SystemConfig.getValue("srv.admin.role"))) {
						vo.setEdit(true);//修改权限
						vo.setDelete(true);//删除
					}
					vo.setCreateUser(po.getCreateUser());
				}
				if(po.getModifyUser()!=null){
					vo.setModifyUser(po.getModifyUser());
				}
				voList.add(vo);
			}
		}catch(Exception e){
			log.error("queryAdvList("+ key + ") - error");
			throw new ServiceException(e);
		}
		return voList;
	}
	
	@Override
	public List<AreaMgrVo> queryAreaListExport(AreaLimitKey key) throws ServiceException {
		List<AreaMgrVo> voList = null;
		try{
			log.debug("queryAreaList("+ key + ") - start");
			String hql = "from TArea po where 1=1 ";
			List<Object> paramList=new ArrayList<Object>();
			if (StringUtil.isNotEmpty(key.getSearchText())) {
				paramList.add("%"+key.getSearchText()+"%");
				hql += " and po.areaName like ? ";
			}
			hql += " order by  po.areaOrder,po.createDt ";
			
			List<TArea> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize(),paramList.toArray());
			voList = new ArrayList<AreaMgrVo>();
			
			for(TArea po : poList){
				AreaMgrVo vo = new AreaMgrVo();
				BeanUtils.copyProperties(po, vo);
				if("1".equals(vo.getIsValid())){
					vo.setIsValid("是");
				}else{
					vo.setIsValid("否");
				}
				if(po.getCreateUser()!=null){
					if (authorService.isCreateUser(po.getCreateUser())||authorService.existInRole(SystemConfig.getValue("srv.admin.role"))) {
						vo.setEdit(true);//修改权限
						vo.setDelete(true);//删除
					}
					vo.setCreateUser(po.getCreateUser());
				}
				if(po.getModifyUser()!=null){
					vo.setModifyUser(po.getModifyUser());
				}
				voList.add(vo);
			}
		}catch(Exception e){
			log.error("queryAdvList("+ key + ") - error");
			throw new ServiceException(e);
		}
		return voList;
	}

	

	@Override
	public int getAreaTotalCount(AreaLimitKey key) throws ServiceException {
		log.debug("getAreaTotalCount("+ key +") - start");
		try{
			String hql = "select count(areaId) from TArea po where isValid = '1' ";
			List<Object> paramList=new ArrayList<Object>();
			if (StringUtil.isNotEmpty(key.getSearchText())) {
				paramList.add("%"+key.getSearchText()+"%");
				hql += " and po.areaName like ? ";
			}
			hql += " ";
			
			List<Object> list = baseDAO.getGenericByHql(hql,paramList.toArray());
			log.debug("getAreaTotalCount("+ key +") - end");
			return Integer.parseInt(list.get(0).toString());
		}catch(Exception e){
			log.error("getAreaTotalCount("+ key + ") - error");
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean deleteArea(String id) {
		try{
			baseDAO.delete(TArea.class, id);
			logBizOprService.saveLog("区域管理","2" , "删除区域(区域ID:" + id+ ")" , "", "");
			return true;
		}catch(Exception e){
			logBizOprService.saveLog("区域管理","2" , "删除区域(区域ID:" + id+ ")失败,"+ e.getStackTrace() , "", "");
			return false;
		}
	}

	@Override
	public AreaMgrVo findArea(String id) {
		AreaMgrVo vo=new AreaMgrVo();
		TArea po=baseDAO.get(TArea.class, id);
		BeanUtils.copyProperties(po, vo);
		if(po.getCreateUser()!=null){
			vo.setCreateUser(po.getCreateUser());
		}
		if(po.getModifyUser()!=null){
			vo.setModifyUser(po.getModifyUser());
		}
		return vo;
	}
}
