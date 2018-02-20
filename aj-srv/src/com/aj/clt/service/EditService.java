package com.aj.clt.service;

import java.util.Map;

import com.aj.clt.vo.EditLimitKey;
import com.aj.clt.vo.EditMgrVo;


/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public interface EditService {
	
	public Object queryEdit(Object obj) throws Exception;
	
	// 查询版本, 管理系统
	public Map<String,Object> queryEditManage(EditLimitKey key) throws Exception;
	
	// 根据id查询版本,管理系统
	public EditMgrVo queryEditById(String id) throws Exception;
	
	// 编辑版本,管理系统
	public void editEdit(EditMgrVo editMgrVo) throws Exception;
	
	// 添加版本,管理系统
	public void addEdit(EditMgrVo editMgrVo) throws Exception;
	
	// 上下线
	public boolean onOffEdit(String id, String status, String model);
	
	// 删除应用
	public boolean deleteBusin(String id);
}
