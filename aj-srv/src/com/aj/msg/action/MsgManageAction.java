package com.aj.msg.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.msg.service.MsgService;
import com.aj.msg.vo.MsgLimitKey;
import com.aj.msg.vo.VmMsgInfoVo;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.exception.ServiceException;
import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.SortType;
import com.frame.core.vo.DataModel;

@Controller
public class MsgManageAction extends FtpImgDownUploadAction {
	private Log log = LogFactory.getLog(MsgManageAction.class);
	@Autowired
	private MsgService msgServiceImpl;

	/**
	 * 区域资源数据列表
	 * 
	 * @param searchText
	 *            查询条件
	 * @param page
	 *            当前查询页
	 * @param pageSize
	 *            当前页数据记录数
	 * @param sort
	 *            需要排序的列名
	 * @param order
	 *            排序方式
	 * @return 返回查询的结果
	 */
	@RequestMapping("/btcs/queryMsgList")
	@ResponseBody
	public DataModel<VmMsgInfoVo> queryMsgList(String req, String page, String rows, String sort, String order) {
		log.debug("queryMsgList(req:" + req + ",rows:" + rows + ",sort:" + sort + ",order:" + order + ") - start");
		JSONObject jsonData = JSONObject.fromObject(req);
		MsgLimitKey key = new MsgLimitKey();
		String searchMsgFrom = null;
		String searchUserTel = null;
		String searchEndDt = null;
		String searchStartDt = null;
		
		if (!jsonData.isEmpty()) {
			searchMsgFrom = jsonData.getString("searchMsgFrom");
			searchUserTel = jsonData.getString("searchUserTel");			
			searchStartDt = jsonData.getString("searchStartDt");
			searchEndDt = jsonData.getString("searchEndDt");
		}
		if (StringUtil.isNotEmpty(sort)) {
			key.setSortColumnName(sort);
		}
		if (StringUtil.isNotEmpty(searchMsgFrom)) {
			key.setSearchMsgFrom(searchMsgFrom);
		}
		if (StringUtil.isNotEmpty(searchUserTel)) {
			key.setSearchUserTel(searchUserTel);
		}
		if (StringUtil.isNotEmpty(searchStartDt)) {
			key.setStartDt(searchStartDt);
		}
		if (StringUtil.isNotEmpty(searchEndDt)) {
			key.setEndDt(searchEndDt);
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
		DataModel<VmMsgInfoVo> areaList = null;
		try {
			List<VmMsgInfoVo> incStatList = msgServiceImpl.queryMsgList(key);
			int totalCount = msgServiceImpl.getMsgTotalCount(key);
			areaList = new DataModel<VmMsgInfoVo>(incStatList, totalCount);

		} catch (ServiceException e) {
			return new DataModel<VmMsgInfoVo>(new ArrayList<VmMsgInfoVo>(), 0);
		}
		log.debug("queryMsgList(" + req + "," + page + "," + rows + "," + sort + "," + order + ") - end");
		return areaList;
	}

	

}
