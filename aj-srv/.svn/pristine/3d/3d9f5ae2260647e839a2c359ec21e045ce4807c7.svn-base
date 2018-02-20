package com.aam.cus.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aam.cus.service.CusService;
import com.aam.cus.vo.CusLimitKey;
import com.aam.cus.vo.CusMgrResultVo;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.exception.ServiceException;
import com.frame.core.util.ExcelWriteHelperPoi;
import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.SortType;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.MessageModel;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Controller
public class CusAction extends FtpImgDownUploadAction  {
	private Log log=LogFactory.getLog(CusAction.class);
	@Autowired
	private CusService cusServiceImpl;
//	@Autowired
//	private AuthorService authorService;
	
	@RequestMapping("/checkToken")
	@ResponseBody
	public boolean checkToken(String token, String userId){
		return	cusServiceImpl.checkToken(token, userId);
	}

	/**
	 * 商品资源数据列表
	 * @param searchText 	查询条件
	 * @param page		   	当前查询页
	 * @param rows		   	当前页数据记录数
	 * @param sort			需要排序的列名
	 * @param order			排序方式
	 * @return 返回查询的结果
	 */
	@RequestMapping("/cus/queryCusList")
	@ResponseBody
	public DataModel<CusMgrResultVo> queryCusList(String req, String page, String rows, String sort, String order) {
		log.debug("queryGdList(req:" + req +",rows:"+rows+",sort:"+sort+ ",order:"+order+") - start"); //$NON-NLS-1$
		JSONObject jsonData = JSONObject.fromObject(req);
		CusLimitKey key = new CusLimitKey();
		String searchText=null;
		if(!jsonData.isEmpty()){
			searchText=jsonData.getString("searchText");
		}
		if (StringUtil.isNotEmpty(searchText)) {
			key.setSearchText(searchText);
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
		DataModel<CusMgrResultVo> result = null;
		try {
			List<CusMgrResultVo> incStatList = cusServiceImpl.queryCusPageList(key);
			int totalCount = cusServiceImpl.getGdTotalCount(key);
			result = new DataModel<CusMgrResultVo>(incStatList, totalCount);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			return new DataModel<CusMgrResultVo>(new ArrayList<CusMgrResultVo>(), 0);
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
	@RequestMapping("/cus/queryCusListExport")
	public String GdExport(String req, String page, String rows, String sort, String order, String columns, HttpServletRequest request) {
		log.debug("GdExport(req:" + req +",rows:"+rows+",sort:"+sort+ ",order:"+order+") - start"); //$NON-NLS-1$
		JSONObject jsonData = JSONObject.fromObject(req);
		CusLimitKey key = new CusLimitKey();
		String startDt = null;
		String endDt = null;
		String searchText=null;
		if(!jsonData.isEmpty()){
			startDt=jsonData.getString("startDt");
			endDt=jsonData.getString("endDt");
			searchText=jsonData.getString("searchText");
		}
		if (StringUtil.isNotEmpty(startDt)) {
			key.setStartDt(startDt);
		}
		if (StringUtil.isNotEmpty(endDt)) {
			key.setEndDt(endDt);
		}
		if (StringUtil.isNotEmpty(searchText)) {
			key.setSearchText(searchText);
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
		List<CusMgrResultVo> incStatList;
		try {
			incStatList = cusServiceImpl.queryCusPageList(key);
		} catch (ServiceException e) {
			incStatList = new ArrayList<CusMgrResultVo>();
		}
		//List<GdVo> resAppList = GdService.queryGdPageList(key);
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_DATA, incStatList);
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_HEADER, columns);
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_FILENAME, "ResGd");
		log.debug("GdExport("+searchText+","+page+","+rows+","+sort+","+order+","+columns+") - end");
		return "forward:../doExport";
	}
	/**
	 * 区域管理删除
	 * @param
	 * @return 返回客户端信息
	 */
	@RequestMapping("/cus/delete")
	@ResponseBody
	public MessageModel deleteArea(String id) {
		log.debug("deleteArea("+id+") - start");
		if (cusServiceImpl.deleteCus(id)) {
			return new MessageModel(true, "删除成功"); 
		} else {
			return new MessageModel(false, "删除失败");
		}
	}
	
}
