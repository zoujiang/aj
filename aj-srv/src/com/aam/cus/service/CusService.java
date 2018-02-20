package com.aam.cus.service;

import java.util.List;

import com.aam.cus.vo.CusLimitKey;
import com.aam.cus.vo.CusMgrResultVo;
import com.frame.core.exception.ServiceException;
import com.frame.ifpr.exception.PublicException;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public interface CusService {
	public Object doLogin(Object obj) throws PublicException;
	public Object doLogOut(Object obj) throws PublicException;
	public Object doModify(Object obj) throws PublicException;
	public Object doReg(Object obj) throws PublicException;
	public boolean checkToken(String token,String userId) ;
	public Object doUserRegSmsApplyToken(Object obj) ;
	public Object doUserRegSmsValidate(Object obj) ;
	
	/**
	 * 获取顾客列表
	 * @return
	 */
	public List<CusMgrResultVo>  queryCusPageList(CusLimitKey key) throws ServiceException;
	/**
	 * 获取顾客列表总数
	 * @return
	 */
	int getGdTotalCount(CusLimitKey key) throws ServiceException;
	Object getCusInfo(Object obj) throws PublicException;
	public boolean deleteCus(String id) ;

}
