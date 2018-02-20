package com.demo.area.service;

import java.util.List;

import com.demo.area.vo.AreaLimitKey;
import com.demo.area.vo.AreaMgrVo;
import com.frame.core.exception.ServiceException;

/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public interface AreaService {
	public void saveArea(AreaMgrVo areaMgrVo) throws ServiceException;

	public List<AreaMgrVo> queryAreaList(AreaLimitKey key) throws ServiceException;

	public int getAreaTotalCount(AreaLimitKey key) throws ServiceException;

	public boolean deleteArea(String id);

	public AreaMgrVo findArea(String id);

	
	
}
