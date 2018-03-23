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
import com.frame.core.vo.ProdTreeVo;


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
}
