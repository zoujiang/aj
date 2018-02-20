package com.frame.log.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.frame.core.dao.GenericDAO;
import com.frame.core.exception.ServiceException;
import com.frame.core.service.AuthorService;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.DateUtil;
import com.frame.core.util.GUID;
import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;
import com.frame.log.model.TLogBizOpr;
import com.frame.log.service.LogBizOprService;
import com.frame.log.vo.LogBizOprLimitKey;
import com.frame.log.vo.LogBizOprMgrVo;
import com.frame.system.po.User;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Service("logBizOprService")
public class LogBizOprServiceImpl implements LogBizOprService{
	private Log log=LogFactory.getLog(LogBizOprServiceImpl.class);

	@Autowired
	private GenericDAO baseDAO;
	@Autowired
	private AuthorService authorService;
	@Override
	public List<LogBizOprMgrVo> queryList(LogBizOprLimitKey key) throws ServiceException {
		List<LogBizOprMgrVo> logBizOprMgrResultList = null;

		try{
			log.debug("queryList("+ key + ") - start");
			String hql = "from TLogBizOpr po where po.oprUserAccount is not null ";
			hql += " and oprSysId = '" + SystemConfig.getValue("system.id") + "'"; 
			List<Object> paramList=new ArrayList<Object>();
			if (StringUtil.isNotEmpty(key.getSearchText())) {
				paramList.add("%"+key.getSearchText()+"%");
				hql += " and po.oprContent like ? ";
			}
			if (StringUtil.isNotEmpty(key.getOprType())) {
				hql += " and po.oprType = '" + key.getOprType() +"' ";
			}
			
			if (StringUtil.isNotEmpty(key.getOprUserAccount())) {
				hql += " and po.oprUserAccount like  '%" + key.getOprUserAccount() + "%'";
			}
			if (StringUtil.isNotEmpty(key.getStartDt())) {
				hql += " and po.oprDt >= ? ";
				paramList.add(DateUtil.convertStringToDate(key.getStartDt(), DateUtil.DATE_TIME_PATTERN2));

			}
			if (StringUtil.isNotEmpty(key.getEndDt())) {
				hql += " and po.oprDt <= ? ";
				paramList.add(DateUtil.convertStringToDate(key.getEndDt(), DateUtil.DATE_TIME_PATTERN2));

			}
			
			hql += " order by  po.oprDt desc";
			
			
			List<TLogBizOpr> poList = baseDAO.getGenericByPosition(hql, key.getOffset(), key.getPageSize(),paramList.toArray());
			logBizOprMgrResultList = new ArrayList<LogBizOprMgrVo>();
			Date now = new  Date();
			for(TLogBizOpr po : poList){
				LogBizOprMgrVo logBizOprMgrVo = new LogBizOprMgrVo();
				BeanUtils.copyProperties(po, logBizOprMgrVo);
				logBizOprMgrResultList.add(logBizOprMgrVo);
			}
		}catch(Exception e){
			log.error("queryList("+ key + ") - error");
			throw new ServiceException(e);
		}
		return logBizOprMgrResultList;
	}

	@Override
	public int getTotalCount(LogBizOprLimitKey key) throws ServiceException {
		log.debug("getAdvTotalCount("+ key +") - start");
		try{
			String hql = "select count(logId) from TLogBizOpr po where  po.oprUserAccount is not null ";
			
			hql += " and oprSysId = '" + SystemConfig.getValue("system.id") + "'"; 
			List<Object> paramList=new ArrayList<Object>();
			if (StringUtil.isNotEmpty(key.getSearchText())) {
				paramList.add("%"+key.getSearchText()+"%");
				hql += " and po.oprContent like ? ";
			}
			if (StringUtil.isNotEmpty(key.getOprType())) {
				hql += " and po.oprType = '" + key.getOprType() +"' ";
			}
			
			if (StringUtil.isNotEmpty(key.getOprUserAccount())) {
				hql += " and po.oprUserAccount like  '%" + key.getOprUserAccount() + "%'";
			}
			
			if (StringUtil.isNotEmpty(key.getStartDt())) {
				hql += " and po.oprDt >= ? ";
				paramList.add(DateUtil.convertStringToDate(key.getStartDt(), DateUtil.DATE_TIME_PATTERN2));

			}
			if (StringUtil.isNotEmpty(key.getEndDt())) {
				hql += " and po.oprDt <= ? ";
				paramList.add(DateUtil.convertStringToDate(key.getEndDt(), DateUtil.DATE_TIME_PATTERN2));

			}
			
			List<Object> list = baseDAO.getGenericByHql(hql,paramList.toArray());
			log.debug("getTotalCount("+ key +") - end");
			return Integer.parseInt(list.get(0).toString());
		}catch(Exception e){
			log.error("getTotalCount("+ key + ") - error");
			throw new ServiceException(e);
		}
	}
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
	@Override
	public void saveLog(String oprSysModule, String oprType, String oprContent,
			String oprTable, String oprTableData) {
		// TODO Auto-generated method stub
		TLogBizOpr lbo = new TLogBizOpr();
		try{
		lbo.setLogId(GUID.generateID("LOG_"));
		lbo.setOprContent(oprContent);
		lbo.setOprDt(new Date());
		lbo.setOprSysId(SystemConfig.getValue("system.id"));
		lbo.setOprSysModule(oprSysModule);
		lbo.setOprTable(oprTable);
		lbo.setOprTableData(oprTableData);
		lbo.setOprType(oprType);
		ServletRequestAttributes requestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=requestAttributes.getRequest();
		lbo.setOprUa(request.getHeader("User-Agent"));
		User  userPo = authorService.getSessionUserPo();
		if(userPo != null){
			lbo.setOprUserName(userPo.getName());
			lbo.setOprUserAccount(userPo.getAccount());
		}
		
		lbo.setOprUserIp(request.getLocalAddr());
		}catch(Exception e ){
			e.printStackTrace();
		}
		this.baseDAO.save(lbo);
	}

	@Override
	public void saveLog(String oprSysModule, String oprType, String oprContent,
			String oprTable, String oprTableData, String userId) {
		TLogBizOpr lbo = new TLogBizOpr();
		try{
		lbo.setLogId(GUID.generateID("LOG_"));
		lbo.setOprContent(oprContent);
		lbo.setOprDt(new Date());
		lbo.setOprSysId(SystemConfig.getValue("system.id"));
		lbo.setOprSysModule(oprSysModule);
		lbo.setOprTable(oprTable);
		lbo.setOprTableData(oprTableData);
		lbo.setOprType(oprType);
		ServletRequestAttributes requestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=requestAttributes.getRequest();
		lbo.setOprUa(request.getHeader("User-Agent"));
		lbo.setOprUserAccount(userId);
		lbo.setOprUserIp(request.getLocalAddr());
		}catch(Exception e ){
			e.printStackTrace();
		}
		this.baseDAO.save(lbo);
	}

}



