package com.frame.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.service.ComboUtilService;
import com.frame.core.vo.ComboTreeVo;
import com.frame.core.vo.ComboVo;
import com.frame.core.vo.ProdTreeVo;
import com.frame.po.WeResBussPo;




/**
 * 获取数据字典基础数据工具类
 * 
 * @author yanzelai 2013-05-07
 * @version 1.0
 */
@Service
public class ComboUtilServiceImpl implements ComboUtilService {
	@Autowired
	private GenericDAO baseDAO;
	
}