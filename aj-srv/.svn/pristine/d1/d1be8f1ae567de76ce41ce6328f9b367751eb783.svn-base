package com.util;

import java.util.List;
import java.util.Map;

import com.frame.core.dao.GenericDAO;

public class AJNoUtil {

	
	private GenericDAO baseDAO;
	
	public AJNoUtil(GenericDAO baseDAO){
		
		this.baseDAO = baseDAO;
	}
	
	public  synchronized String queryPAJNoSeq(String type){
		String no = "10000";
		String updateSql = "";
		String	sql = "select PNO,TBNO,BNO from t_ajno_seq ";
		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, null);
		if(list != null && list.size() > 0){
			if(list.get(0).get("PNO") == null || Integer.parseInt(list.get(0).get("PNO").toString()) < 10000){
				updateSql = "update t_ajno_seq set PNO = 10001 ";
			}else{
				no = list.get(0).get("PNO").toString();
				updateSql = "update t_ajno_seq set PNO = PNO + 1 ";
			}
		}else{
			updateSql = "insert into t_ajno_seq (PNO) values (10001)";
		}
		baseDAO.execteNativeBulk(updateSql, null);
		
		return no;
		
	}
}
