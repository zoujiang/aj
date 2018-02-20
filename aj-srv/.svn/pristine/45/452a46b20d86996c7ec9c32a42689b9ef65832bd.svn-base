package com.frame.log.service;

import java.util.List;

import com.frame.core.exception.ServiceException;
import com.frame.core.util.pagination.key.LimitKey;
import com.frame.log.vo.LogBizOprLimitKey;
import com.frame.log.vo.LogBizOprMgrVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public interface LogBizOprService {
	/**保存日志
	 *@param oprSysModule String  系统模块：如广告管理，角色管理，菜单管理等
	 *@param oprType String 操作类型(0-查看，1-增加，2-修改，3-删除，4-登陆，5-登出，6-审批，7-启停，9-其他)
	 *@param oprContent String 操作内容如：新增商品(商品名称:北京烤鸭)
	 *@param oprTable String 操作表（如t_aam_config）
	 *@param oprTableData String 操作表数据
     *<p>若是新增：记录新增数据,格式参考toString方法,如："Role [id=123, code=234, title=蔡文]"</p>
     *<p>若是修改：记录修改前数据,格式参考toString方法</p>
     *<p>若是删除：记录删除行记录，如果是物理删除，则需要记录删除数据行，如果逻辑删除，根据需要可只记录删除数据ID</p>
     *@return void
	 **/
	public void saveLog(String oprSysModule,String oprType,String oprContent,String oprTable,String oprTableData) ;
	public void saveLog(String oprSysModule,String oprType,String oprContent,String oprTable,String oprTableData,String userId) ;
	public List<LogBizOprMgrVo> queryList(LogBizOprLimitKey key) throws ServiceException;
	public int getTotalCount(LogBizOprLimitKey key) throws ServiceException;

}

