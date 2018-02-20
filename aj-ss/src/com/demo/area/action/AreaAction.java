package com.demo.area.action;

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

import com.demo.area.service.AreaService;
import com.demo.area.vo.AreaLimitKey;
import com.demo.area.vo.AreaMgrVo;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.exception.ServiceException;
import com.frame.core.util.ExcelWriteHelperPoi;
import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.SortType;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.MessageModel;


@Controller
public class AreaAction extends FtpImgDownUploadAction {
	private Log log=LogFactory.getLog(AreaAction.class);
	@Autowired
	private AreaService areaServiceImpl;
	/**
	 * 添加区域
	 */
	@RequestMapping("/area/addArea")
	@ResponseBody
	public String addArea(AreaMgrVo areaMgrVo){
		try {
			if(areaMgrVo.getAreaPicFile() != null &&StringUtils.isNotEmpty(areaMgrVo.getAreaPicFile().getOriginalFilename())){
				areaMgrVo.setAreaPic(this.fileUpload("demo", areaMgrVo.getAreaPicFile()));
			}
			areaServiceImpl.saveArea(areaMgrVo);
		} catch (IOException e) {
			log.error("附件上传错误：createMarketActivity("+ e.getMessage()+ "");
			return "fileUploadError";
		} catch (FileUploadException e) {
			log.error("附件为空文件：createMarketActivity(" + e.getMessage());
			return "fileMinSizeError";
		}  catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
			
	/**
	 * 区域资源数据列表
	 * @param searchText 	查询条件
	 * @param page		   	当前查询页
	 * @param pageSize		   	当前页数据记录数
	 * @param sort			需要排序的列名
	 * @param order			排序方式
	 * @return 返回查询的结果
	 */
	@RequestMapping("/area/queryAreaList")
	@ResponseBody
	public DataModel<AreaMgrVo> queryAreaList(AreaLimitKey key, String page, String rows, String sort, String order) {
		log.debug("queryAreaList(req:" + key.getSearchText() +",rows:"+rows+",sort:"+sort+ ",order:"+order+") - start");
		//JSONObject jsonData = JSONObject.fromObject(req);
		//AreaLimitKey key = new AreaLimitKey();
		
		if (StringUtil.isNotEmpty(sort)) {
			key.setSortColumnName(sort);
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
		DataModel<AreaMgrVo> areaList = null;
		try {
			List<AreaMgrVo> incStatList = areaServiceImpl.queryAreaList(key);
			int totalCount = areaServiceImpl.getAreaTotalCount(key);
			areaList = new DataModel<AreaMgrVo>(incStatList, totalCount);
			
		} catch (ServiceException e) {
			return new DataModel<AreaMgrVo>(new ArrayList<AreaMgrVo>(), 0);
		}
		log.debug("queryAreaList("+key.getSearchText()+","+page+","+rows+","+sort+","+order+") - end");
		return areaList;
	}
	/**
	 * 区域资源数据导出
	 * @param searchText 	查询条件（封装JSON字符串）
	 * @param page		   	当前查询页
	 * @param rows		   	当前页数据记录数
	 * @param sort			需要排序的列名
	 * @param order			排序方式
	 * @param columns		
	 * @return 返回业务逻辑视图
	 */
	@RequestMapping("/area/queryAreaListExport")
	public String areaExport(String req, String page, String rows, String sort, String order, String columns, HttpServletRequest request) {
		log.debug("areaExport(req:" + req +",rows:"+rows+",sort:"+sort+ ",order:"+order+") - start"); //$NON-NLS-1$
		JSONObject jsonData = JSONObject.fromObject(req);
		AreaLimitKey key = new AreaLimitKey();
		String searchText=null;
		if(!jsonData.isEmpty()){
			searchText=jsonData.getString("searchText");
		}
		if (StringUtil.isNotEmpty(sort)) {
			key.setSortColumnName(sort);
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
		List<AreaMgrVo> incStatList=null;
		try {
			incStatList = areaServiceImpl.queryAreaListExport(key);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_DATA, incStatList);
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_HEADER, columns);
		request.setAttribute(ExcelWriteHelperPoi.EXPORT_FILENAME, "area");
		log.debug("areaExport("+searchText+","+page+","+rows+","+sort+","+order+","+columns+") - end");
		return "forward:../doExport";
	}
	/**
	 * 区域管理删除
	 * @param
	 * @return 返回客户端信息
	 */
	@RequestMapping("/area/deleteArea")
	@ResponseBody
	public MessageModel deleteArea(String id) {
		log.debug("deleteArea("+id+") - start");
		if (areaServiceImpl.deleteArea(id)) {
			return new MessageModel(true, "删除成功"); 
		} else {
			return new MessageModel(false, "删除失败");
		}
	}
	/**
	 * 区域管理编辑
	 * @param
	 * @return 返回客户端信息
	 */
	@RequestMapping("/area/findArea")
	@ResponseBody
	public AreaMgrVo findArea(String id) {
		return areaServiceImpl.findArea(id);
	}
}
