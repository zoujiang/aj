package com.frame.core.service;

import java.util.List;

import com.frame.core.vo.ComboTreeVo;
import com.frame.core.vo.ComboVo;

/**
 * 获取数据字典基础数据工具类
 * 
 * @author 蔡文
 * @version 1.0
 */
public interface ComboUtilService {
	/**
	 * 获取公司名称列表
	 */
	public List<ComboVo> getCoNameComboList(String selectId);	
	
	/**
	 * 获取业务类型树形列表
	 */
	public List<ComboTreeVo> getBusinessTypeTreeList();
}
