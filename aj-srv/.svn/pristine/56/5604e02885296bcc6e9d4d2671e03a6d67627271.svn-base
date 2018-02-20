package com.frame.log.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.core.action.BaseAction;
import com.frame.core.exception.ServiceException;
import com.frame.core.util.ExcelWriteHelperPoi;
import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.SortType;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;
import com.frame.log.vo.LogBizOprLimitKey;
import com.frame.log.vo.LogBizOprMgrVo;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Controller
public class LogBizOprAction extends BaseAction  {
	private Log log=LogFactory.getLog(LogBizOprAction.class);
	@Autowired
	private LogBizOprService logBizOprService;


	/**
	 * 商品资源数据列表
	 * @param searchText 	查询条件
	 * @param page		   	当前查询页
	 * @param rows		   	当前页数据记录数
	 * @param sort			需要排序的列名
	 * @param order			排序方式
	 * @return 返回查询的结果
	 */
	@RequestMapping("/log/queryList")
	@ResponseBody
	public DataModel<LogBizOprMgrVo> queryList(String req, String page, String rows, String sort, String order) {
		log.debug("queryList(req:" + req +",rows:"+rows+",sort:"+sort+ ",order:"+order+") - start"); //$NON-NLS-1$
		JSONObject jsonData = JSONObject.fromObject(req);
		LogBizOprLimitKey key = new LogBizOprLimitKey();
		String searchText=null;
		String sortColumnName=null;
		String oprUserAccount=null;
		String oprType=null;
		String startDt=null;
		String endDt=null;
		if(!jsonData.isEmpty()){
			searchText=jsonData.getString("searchText");
			//sortColumnName = jsonData.getString("sortColumnName");
			oprUserAccount = jsonData.getString("oprUserAccount");
			oprType = jsonData.getString("oprType");
			startDt = jsonData.getString("startDt");
			endDt = jsonData.getString("endDt");
		}
		if (StringUtil.isNotEmpty(searchText)) {
			key.setSearchText(searchText);
		}
		if (StringUtil.isNotEmpty(sortColumnName)) {
			key.setSortColumnName(sortColumnName);
		}
		if (StringUtil.isNotEmpty(oprUserAccount)) {
			key.setOprUserAccount(oprUserAccount);
		}
		if (StringUtil.isNotEmpty(oprType)) {
			key.setOprType(oprType);
		}
		if (StringUtil.isNotEmpty(startDt)) {
			key.setStartDt(startDt);
		}
		if (StringUtil.isNotEmpty(endDt)) {
			key.setEndDt(endDt);
		}
		if (SortType.Asc.name().toLowerCase().equals(order)) {
			key.setSortType(SortType.Asc);
		} else {
			key.setSortType(SortType.Desc);
		}
		if (StringUtil.isNotEmpty(page) && StringUtil.isNotEmpty(rows)) {
			key.setOffset((Integer.parseInt(page) - 1) * Integer.parseInt(rows));
			key.setPageSize(Integer.parseInt(rows));
		}
		DataModel<LogBizOprMgrVo> result = null;
		try {
			List<LogBizOprMgrVo> incStatList = logBizOprService.queryList(key);
			int totalCount = logBizOprService.getTotalCount(key);
			result = new DataModel<LogBizOprMgrVo>(incStatList, totalCount);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			return new DataModel<LogBizOprMgrVo>(new ArrayList<LogBizOprMgrVo>(), 0);
		}
		log.debug("queryGdList("+searchText+","+page+","+rows+","+sort+","+order+") - end");
		return result;
	}
	
	/**
	 * 商品资源数据导出
	 * @param searchText 	查询条件（封装JSON字符串）
	 * @param page		   	当前查询页
	 * @param rows		   	当前页数据记录数
	 * @param sort			需要排序的列名
	 * @param order			排序方式
	 * @param columns		
	 * @return 返回业务逻辑视图
	 */
	@RequestMapping("/cus/queryListExport")
	public String queryListExport(String req, String page, String rows, String sort, String order, String columns, HttpServletRequest request) {
		log.debug("GdExport(req:" + req +",rows:"+rows+",sort:"+sort+ ",order:"+order+") - start"); //$NON-NLS-1$
		JSONObject jsonData = JSONObject.fromObject(req);
		LogBizOprLimitKey key = new LogBizOprLimitKey();
		String searchText=null;
		String sortColumnName=null;
		String oprUserAccount=null;
		String oprType=null;
		String startDt=null;
		String endDt=null;
		if(!jsonData.isEmpty()){
			searchText=jsonData.getString("searchText");
			sortColumnName = jsonData.getString("sortColumnName");
			oprUserAccount = jsonData.getString("oprUserAccount");
			oprType = jsonData.getString("oprType");
			startDt = jsonData.getString("startDt");
			endDt = jsonData.getString("endDt");
		}
		if (StringUtil.isNotEmpty(searchText)) {
			key.setSearchText(searchText);
		}
		if (StringUtil.isNotEmpty(sortColumnName)) {
			key.setSortColumnName(sortColumnName);
		}
		if (StringUtil.isNotEmpty(oprUserAccount)) {
			key.setOprUserAccount(oprUserAccount);
		}
		if (StringUtil.isNotEmpty(oprType)) {
			key.setOprType(oprType);
		}
		if (StringUtil.isNotEmpty(startDt)) {
			key.setStartDt(startDt);
		}
		if (StringUtil.isNotEmpty(endDt)) {
			key.setEndDt(endDt);
		}
		if (SortType.Asc.name().toLowerCase().equals(order)) {
			key.setSortType(SortType.Asc);
		} else {
			key.setSortType(SortType.Desc);
		}
		if (StringUtil.isNotEmpty(page) && StringUtil.isNotEmpty(rows)) {
			key.setOffset((Integer.parseInt(page) - 1) * Integer.parseInt(rows));
			key.setPageSize(Integer.parseInt(rows));
		}
		List<LogBizOprMgrVo> incStatList;
		try {
			incStatList = logBizOprService.queryList(key);
		} catch (ServiceException e) {
			incStatList = new ArrayList<LogBizOprMgrVo>();
		}
		//List<GdVo> resAppList = GdService.queryGdPageList(key);
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_DATA, incStatList);
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_HEADER, columns);
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_FILENAME, "ResGd");
		log.debug("GdExport("+searchText+","+page+","+rows+","+sort+","+order+","+columns+") - end");
		return "forward:../doExport";
	}
}
