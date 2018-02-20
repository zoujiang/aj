package com.aj.clt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.clt.service.EditService;
import com.aj.clt.vo.EditLimitKey;
import com.aj.clt.vo.EditMgrVo;
import com.aj.clt.vo.EditParamVo;
import com.aj.clt.vo.EditResultVo;
import com.frame.core.exception.ServiceException;
import com.frame.core.jdbcdao.JDBCBaseDao;
import com.frame.core.service.AuthorService;
import com.frame.core.util.GUID;
import com.frame.core.vo.ParamsVo;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.vo.RequestVo;
import com.frame.ifpr.vo.ResponseVo;
import com.frame.log.service.LogBizOprService;

@Service("editServiceImpl")
public class EditServiceImpl implements PublishService, EditService {

	
	private Log log=LogFactory.getLog(EditServiceImpl.class);
	
	private SimpleDateFormat sifk = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private JDBCBaseDao sqlBaseDAO;
	
	@Autowired
	private AuthorService authorService;
	@Autowired
	private LogBizOprService logBizOprService;	
	@Override
	public Object publishService(Object obj) throws PublicException{
		
		log.debug("publishService("+obj+") - start");
		
		ParamsVo pvo = ((RequestVo)obj).getParams();
		
		try{
			if(pvo instanceof EditParamVo){
				return this.queryEdit(obj);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		log.debug("publishService("+obj+") - end");
		return null;
	}
	
	@Override
	public Object queryEdit(Object obj) throws Exception {
		
		ResponseVo rsp = new ResponseVo();
		RequestVo request = (RequestVo) obj;
		
		EditParamVo paramVo = (EditParamVo) request.getParams();
		EditResultVo rsv = new EditResultVo();
		
		
		String model = paramVo.getModel(); //  模式 1  安卓 ， 2 ios ,  3 win phone 
		String channelId = paramVo.getChannelId();  // 渠道号（暂时没有渠道概念固定写100）
		String curVer = paramVo.getCurVer();  // 客户端版本
		
		String sql ="select * from CMS_EDITION_TWO where model='"+model+"' and channel_id='"+channelId+"' "+
		" and status='1' order by CLIENTVER desc";
		
		List list = sqlBaseDAO.queryList(sql);
		
		if(list.size()>0){
			Map m = (Map)list.get(0);
			rsv.setChannelId((String)m.get("CHANNEL_ID"));
			//if(((String)m.get("CLIENTURL")).startsWith(SystemConfig.getValue("res.http.url"))){
			rsv.setClientUrl( (String)m.get("CLIENTURL"));
			//}else{
			//	rsv.setClientUrl(SystemConfig.getValue("res.http.url") + (String)m.get("CLIENTURL"));
			//}
			rsv.setClientVer((String) m.get("CLIENTVER"));
			rsv.setForceUpdate((String)m.get("FORCEUPDATE"));
			rsv.setProperty((String)m.get("PROPERTY"));
			rsv.setUpdateContent((String)m.get("UPDATECONTENT"));
			rsv.setModel((String)m.get("model"));
		}else
		{
			rsv.setChannelId("");
			rsv.setClientUrl("");
			rsv.setClientVer("");
			rsv.setForceUpdate("");
			rsv.setProperty("");
			rsv.setUpdateContent("");
			rsv.setModel("");
		}
		
		rsp.setResult(rsv);
		rsp.setReturnCode(ResponseVo.SUCCESS);
		rsp.setServiceName("otos_queryEdit");
		return rsp;
	}
	
	
	// 查询版本, 管理系统
	public Map queryEditManage(EditLimitKey key) throws Exception
	{
		Map dateMap = new HashMap();
		List<EditMgrVo> voList = null;
		Integer totalResults = 0;
		try{
			Map<String, String> mapX = new HashMap();
			mapX.put("pageSize", String.valueOf(key.getPageSize()));
			mapX.put("currentPage", String.valueOf(key.getOffset()));
			mapX.put("order", key.getSortType().name());
			mapX.put("sort", key.getSortColumnName());
			mapX.put("status", key.getStatus());
			mapX.put("model", key.getModel());
			
			Map retMap = this.queryEdit(mapX);
			List<Map> retList = (List<Map>)retMap.get("list");
			totalResults = Integer.valueOf((String)retMap.get("totalResults"));
			
			voList = new ArrayList<EditMgrVo>();
			for(Map po : retList){
				EditMgrVo vo = new EditMgrVo();
				vo.setId((String)po.get("ID"));
				vo.setModel((String)po.get("MODEL"));
				vo.setClienturl((String)po.get("CLIENTURL"));
				vo.setClientver((po.get("CLIENTVER")).toString());
				vo.setForceupdate((String)po.get("FORCEUPDATE"));
				vo.setChannel_id((String)po.get("CHANNEL_ID"));
				vo.setUpdatecontent((String)po.get("UPDATECONTENT"));
				vo.setProperty((String)po.get("PROPERTY"));
				vo.setStatus((String)po.get("STATUS"));
				vo.setCreate_date(sifk.format(po.get("CREATE_DATE")));
				vo.setUpdate_date(sifk.format(po.get("UPDATE_DATE")));
				//if(StringUtil.isNotEmpty((String)po.get("CREATE_USERID"))){
				//	if (authorService.isCreateUser((String)po.get("CREATE_USERID"))||authorService.existInRole(SystemConfig.getValue("spss.admin.role"))) {
						vo.setEdit(true);//修改权限
						vo.setDelete(true);//删除
				//	}
				//}
				vo.setUpdate_userid((String)po.get("UPDATE_USERID"));
				voList.add(vo);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(e);
		}
		dateMap.put("dataList", voList);
		dateMap.put("totalResults", totalResults);
		return dateMap;
	}
	
	
	// 基本查询应用
	private Map queryEdit(Map<String, String> mapX) throws Exception {
		
		Map retMap = new HashMap();
		String order = mapX.get("order");   // 排序方向 
		String sort = mapX.get("sort");     // 排序字段
		String id = mapX.get("id");    		// 主键
		String model = mapX.get("model");   // 客户端版本
		String status = mapX.get("status"); // 状态
		
		String pageNum = (String) mapX.get("currentPage") == null ? "1": (String) mapX.get("currentPage"); // 当前页码 
		String pageCount = (String) mapX.get("pageSize") == null ? "10": (String) mapX.get("pageSize"); // 每页显示记录数
		
		
		// 条件
		String contions = "where 1=1";
		if(id!=null && !"".equals(id))
			contions = contions+" and id='"+id+"'";
		
		if(model!=null && !"".equals(model))
			contions = contions+" and model = '"+model+"'";
		
		if(status!=null && !"".equals(status))
			contions = contions+" and status = '"+status+"'";
		
		// 顺序
		String orderby = " order by create_date desc";
		if(!"".equals(sort) && sort != null)
		{
			orderby = " order by "+sort+ " " +order;
		}	
			
		String maxsql = "select count(1) as MAXCOUNT from CMS_EDITION_TWO "+contions;
		List<Map> MaxList = sqlBaseDAO.queryList(maxsql);
		  
		// 总数
		Integer maxcount = Integer.valueOf(MaxList.get(0).get("MAXCOUNT").toString());
		// 每页数 
		Integer ocount = Integer.valueOf(pageCount);
		// 第几页
		Integer opage = Integer.valueOf(pageNum);
		
		// 总页数
		Integer maxpage = 0;
		if(maxcount%ocount==0){
			 maxpage = maxcount/ocount;
		}else {
			 maxpage = maxcount/ocount+1;
		}
		int benum = (opage-1)*ocount;
		int ennum = (opage)*ocount;
		
		String qsel = "select * from CMS_EDITION_TWO "+contions+orderby+" LIMIT "+benum+","+ennum;
		
		List<Map> list = sqlBaseDAO.queryList(qsel);
		
		retMap.put("totalResults", maxcount.toString()); // 总数
		retMap.put("list", list); // 内容（list）
		
		return retMap;
	}
	
	// 根据id查询版本,管理系统
	public EditMgrVo queryEditById(String id) throws Exception
	{
		EditMgrVo editMgrVo = new EditMgrVo();
		Map<String, String> mapX = new HashMap();
		mapX.put("id", id);
		Map retMap = this.queryEdit(mapX);
		List poList = (List)retMap.get("list");
		if(poList.size()>0){
			Map po = (Map)poList.get(0);
			editMgrVo.setId((String)po.get("ID"));
			editMgrVo.setModel((String)po.get("MODEL"));
			editMgrVo.setClienturl((String)po.get("CLIENTURL"));
			editMgrVo.setClientver((po.get("CLIENTVER")).toString());
			editMgrVo.setForceupdate((String)po.get("FORCEUPDATE"));
			editMgrVo.setChannel_id((String)po.get("CHANNEL_ID"));
			editMgrVo.setUpdatecontent((String)po.get("UPDATECONTENT"));
			editMgrVo.setProperty((String)po.get("PROPERTY"));
			editMgrVo.setStatus((String)po.get("STATUS"));
			return editMgrVo;
		}else{
			return null;
		}
		
	}
	
	
	// 编辑版本
	@SuppressWarnings("unchecked")
	public void editEdit(EditMgrVo editMgrVo) throws Exception
	{
		String id = editMgrVo.getId();
		String model = editMgrVo.getModel();
		String clienturl  = editMgrVo.getClienturl();
		String clientver = editMgrVo.getClientver();
		String channel_id = editMgrVo.getChannel_id();
		String status = editMgrVo.getStatus();
		String forceupdate = editMgrVo.getForceupdate();
		String updatecontent = editMgrVo.getUpdatecontent();
		if(status.equals("1")){
			String sql1 = "update CMS_EDITION_TWO set status='"+0+"' where model='"+model+"'";
			sqlBaseDAO.updateTableArgs(sql1, null);
			logBizOprService.saveLog("客户端升级管理  ","2" , "冻结版本(版本模式：" + model+ ")", "","");

		}
		String sql2 = "";
		List<String> paras = new ArrayList<String>();
		if(clienturl!=null && !"".equals(clienturl)){
			sql2 = "update CMS_EDITION_TWO set model=?, status=?,channel_id=?, clienturl=?, forceupdate=?, clientver=?, updatecontent=? , update_date=now(), update_userid=? where id='"+id+"'";
			paras.add(model);
			paras.add(status);
			paras.add(channel_id);
			paras.add(clienturl);
			paras.add(forceupdate);
			paras.add(clientver);
			paras.add(updatecontent);
			paras.add(authorService.getSessionUser().getAccount());
			logBizOprService.saveLog("客户端升级管理  ","2" , "更新版本(版本模式：" + model+ ",版本号:" + clientver + ")", "","");

		}else{
			sql2 = "update CMS_EDITION_TWO set  model=?, status=?, forceupdate=?, clientver=?, updatecontent=? , update_date=now(), update_userid=? where id='"+id+"'";
			paras.add(model);
			paras.add(status);
			paras.add(channel_id);
			paras.add(forceupdate);
			paras.add(clientver);
			paras.add(updatecontent);
			paras.add(authorService.getSessionUser().getAccount());
			logBizOprService.saveLog("客户端升级管理  ","2" , "更新版本(版本模式：" + model+ ",版本号:" + clientver + ")", "","");

		}
		int ret = sqlBaseDAO.updateTableArgs(sql2, paras.toArray());
		if(ret==0)
		{
			throw new RuntimeException("save date faile");
		}
		
	}
	
	// 添加版本
	@SuppressWarnings("unchecked")
	@Override
	public void addEdit(EditMgrVo editMgrVo) throws Exception
	{
		String id = this.getUUID();
		String model = editMgrVo.getModel();
		String clienturl  = editMgrVo.getClienturl();
		String clientver = editMgrVo.getClientver();
		String channel_id = editMgrVo.getChannel_id();
		String status = editMgrVo.getStatus();
		String forceupdate = editMgrVo.getForceupdate();
		String updatecontent = editMgrVo.getUpdatecontent();
		String update_userid = authorService.getSessionUser().getAccount();
		String create_userid = authorService.getSessionUser().getAccount();
		if(status.equals("1")){
			String sql1 = "update CMS_EDITION_TWO set status='"+0+"' where model='"+model+"'";
			sqlBaseDAO.updateTableArgs(sql1, null);
		}
		String sql2 = "insert into CMS_EDITION_TWO(id,status,model,clienturl,forceupdate,clientver,channel_id,updatecontent,update_userid,create_userid,update_date,create_date)"+
		"values(?,?,?,?,?,?,?,?,?,?,now(),now())";
		List<String> paras = new ArrayList<String>();
		paras.add(id);
		paras.add(status);
		paras.add(model);
		paras.add(clienturl);
		paras.add(forceupdate);
		paras.add(clientver);
		paras.add(channel_id);
		paras.add(updatecontent);
		paras.add(update_userid);
		paras.add(create_userid);
		
		int ret = sqlBaseDAO.updateTableArgs(sql2, paras.toArray());
		logBizOprService.saveLog("客户端升级管理  ","1" , "新增版本(版本模式：" + model+ ",版本号:" + clientver + ")", "","");

		if(ret==0)
		{
			throw new RuntimeException("save date faile");
		}
	}
	
	// 上下线
	@Transactional
	@Override
	public boolean onOffEdit(String id, String status, String model)
	{
		try{
			if(status.equals("1")){
				String sql1 = "update CMS_EDITION_TWO set status='"+0+"' where model='"+model+"'";
				sqlBaseDAO.updateTableArgs(sql1, null);
			}
			String sql2 = "";
			sql2 = "update CMS_EDITION_TWO set status='"+status+"' where id='"+id+"'";
			int ret = sqlBaseDAO.updateTableArgs(sql2, null);
			if(status.equals("1")){
			logBizOprService.saveLog("客户端升级管理  ","2" , "版本上线(版本模式：" + model+ ",ID" + id + ")", "","");
			}else{
				logBizOprService.saveLog("客户端升级管理  ","2" , "版本下线(版本模式：" + model+ ",ID" + id + ")", "","");
			}
			
			if(ret>0)
				return true;
			else
				return false;
		}catch(Exception e){
			return false;
		}
	}
	
	// 删除应用
	public boolean deleteBusin(String id)
	{
		try{
			
			String sql = "delete from CMS_EDITION_TWO where id='"+id+"'";
			int ret = sqlBaseDAO.updateTableArgs(sql, null);
			logBizOprService.saveLog("客户端升级管理  ","3" , "版本删除(ID" + id + ")", "","");
			
			if(ret>0)
				return true;
			else
				return false;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 获取UUID
	 */
	private String getUUID() {
		String uuid = "";
		try {
			uuid = GUID.generateID("CLT");
		} catch (Exception e) {
			log.error("getUUID:" + e.getLocalizedMessage());
		}
		return uuid;
	}

}
