package com.qm.shop.bank.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.log.service.LogBizOprService;
import com.qm.shop.bank.service.BankInfoService;

@Service("bankInfoService")
@Scope("prototype")
public class BankInfoServiceImpl  implements BankInfoService{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private GenericDAO baseDAO;
	
	@Autowired
	private LogBizOprService logBizOprService;
	
	
	@Override
	public List<Map<String, Object>> getAll() {
		String sql = "SELECT id, name  FROM t_bank WHERE STATUS = 0";
		return baseDAO.getGenericBySQL(sql, null);
	}
	
}
