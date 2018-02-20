package com.frame.core.action;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.core.service.ComboUtilService;
import com.frame.core.vo.ComboTreeVo;
import com.frame.core.vo.ComboVo;


/**
 * 获取数据字典基础数据工具类
 * 
 * @author yanzelai 2013-05-07
 * @version 1.0
 */
@Controller
public class ComboUtilAction {
	@Autowired
	ComboUtilService comboUtilService;
	private List<ComboVo> list;
	private List<ComboTreeVo> treeList;

	/**
	 * 获取需求类型
	 */
	@RequestMapping("/combo/getCoNameComboList")
	@ResponseBody
	public String getCoNameComboList(String selectId) {
		list = comboUtilService.getCoNameComboList(selectId);
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 获取业务类型树形列表
	 */
	@RequestMapping("/combo/getbusinessTypeTree")
	@ResponseBody
	public String getPlatTree(String selectId) {
		treeList = comboUtilService.getBusinessTypeTreeList();
		StringBuffer jsonTree = new StringBuffer("[{\"id\": \"businessType\",\"text\": \"业务类型\" ");
		if (null!=treeList && treeList.size()>0) {
			jsonTree.append(" ,\"children\":  ");
			jsonTree.append(JSONArray.fromObject(treeList).toString());
		}
		jsonTree.append("}]");
		return jsonTree.toString();
	}

}
