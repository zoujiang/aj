package com.frame.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.service.ComboUtilService;
import com.frame.core.vo.ComboTreeVo;
import com.frame.core.vo.ComboVo;
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
	@Override
	public List<ComboVo> getCoNameComboList(String selectId) {
		/*List<TCo> poList = baseDAO.listAll(TCo.class);
		List<ComboVo> voList = new ArrayList<ComboVo>();

		for(TCo po : poList){
			ComboVo cvo = new ComboVo();
			cvo.setId(po.getCoId());
			cvo.setName(po.getCoName());
			voList.add(cvo);
		}*/
		return null;
	
		//return voList;
	}
	
	@Override
	public List<ComboTreeVo> getBusinessTypeTreeList() {
		List<WeResBussPo> poList = baseDAO.listAll(WeResBussPo.class);
		List<ComboTreeVo> voList = new ArrayList<ComboTreeVo>();
		WeResBussPo po = null;
		for (int i = 0; i < poList.size(); i++) {
			po = poList.get(i);
			ComboTreeVo vo = new ComboTreeVo();
			vo.setId(po.getId());
			vo.setText(po.getName());
			voList.add(vo);
		}
		return voList;
	}
	
	
}