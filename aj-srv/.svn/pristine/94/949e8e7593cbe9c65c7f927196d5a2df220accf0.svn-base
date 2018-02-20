package com.aj.clt.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.clt.service.EditService;
import com.aj.clt.vo.EditLimitKey;
import com.aj.clt.vo.EditMgrVo;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.SortType;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.MessageModel;

@RequestMapping("/edit")
@Controller
public class EditAction extends FtpImgDownUploadAction {
	private Log log=LogFactory.getLog(EditAction.class);
	@Autowired
	private EditService editServiceImpl;
	
	
	/**
	 * 应用资源数据列表
	 * @param searchText 	查询条件
	 * @param page		   	当前查询页
	 * @param pageSize		当前页数据记录数
	 * @param sort			需要排序的列名
	 * @param order			排序方式
	 * @return 返回查询的结果
	 */
	@RequestMapping("/queryEditList")
	@ResponseBody
	public DataModel<EditMgrVo> queryEditList(String status, String model,String page, String rows, String sort, String order) {
		
		EditLimitKey key = new EditLimitKey();
		
		if (StringUtil.isNotEmpty(status)) {
			key.setStatus(status);
		}
		if (StringUtil.isNotEmpty(model)) {
			key.setModel(model);
		}
		if (StringUtil.isNotEmpty(sort)) {
			key.setSortColumnName(sort);
		}
		if (SortType.Asc.name().toLowerCase().equals(order)) {
			key.setSortType(SortType.Asc);
		} else {
			key.setSortType(SortType.Desc);
		}
		if (StringUtil.isNotEmpty(page) && StringUtil.isNotEmpty(rows)) {
			key.setOffset(Integer.parseInt(page));
			key.setPageSize(Integer.parseInt(rows));
		}
		DataModel<EditMgrVo> editList = null;
		try {
			Map retMap = editServiceImpl.queryEditManage(key);
			List<EditMgrVo> incStatList = (List<EditMgrVo>)retMap.get("dataList");
			int totalCount = (Integer)retMap.get("totalResults");
			editList = new DataModel<EditMgrVo>(incStatList, totalCount);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataModel<EditMgrVo>(new ArrayList<EditMgrVo>(), 0);
		}
		return editList;
	}
	
	/**
	 * 应用查询根据id
	 * @param
	 * @return 返回客户端信息
	 */
	@RequestMapping("/findEdit")
	@ResponseBody
	public Object findEdit(String id) {
		try {
			return new MessageModel(true, "操作成功",editServiceImpl.queryEditById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageModel(false, "操作失败");
		}
	}
	
	
	/**
	 * 编辑版本
	 */
	@RequestMapping("/editEdit")
	@ResponseBody
	public Object editBusin(EditMgrVo editMgrVo){
		try {
			editServiceImpl.editEdit(editMgrVo);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageModel(false, "操作失败");
		}
		return new MessageModel(true, "操作成功");
	}
	
	/**
	 * 版本添加
	 */
	@RequestMapping("/addEdit")
	@ResponseBody
	public Object addEdit(EditMgrVo editMgrVo){
		try {
			editServiceImpl.addEdit(editMgrVo);
		}  catch (Exception e) {
			e.printStackTrace();
			return new MessageModel(false, "操作失败");
		}
		return new MessageModel(true, "操作成功");
	}
	
	/**
	 * 上下线
	 * @param
	 * @return 返回客户端信息
	 */
	@RequestMapping("/onOffEdit")
	@ResponseBody
	public MessageModel onOffEdit(String id, String status, String model){
		if (editServiceImpl.onOffEdit(id, status, model)) {
			return new MessageModel(true, "操作成功"); 
		} else {
			return new MessageModel(false, "操作失败");
		}
	}
	
	/**
	 * 删除
	 * @param
	 * @return 返回客户端信息
	 */
	@RequestMapping("/deleteEdit")
	@ResponseBody
	public MessageModel deleteEdit(String id){
		log.debug("deleteEdit("+id+") - start");
		if (editServiceImpl.deleteBusin(id)) {
			return new MessageModel(true, "删除成功"); 
		} else {
			return new MessageModel(false, "删除失败");
		}
	}
	
}
