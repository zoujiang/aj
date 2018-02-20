package com.frame.core.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.frame.core.exception.ServiceException;
import com.frame.core.service.PageService;
import com.frame.core.util.StringUtil;
import com.frame.core.util.pagination.key.LimitKey;
import com.frame.core.util.pagination.key.SortType;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.PageParamsVo;
import com.frame.core.vo.PageResultVo;
import com.frame.core.vo.ParamsVo;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.frame.ifpr.vo.RequestVo;
import com.frame.ifpr.vo.ResponseVo;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
public abstract class PageServiceImpl<T> implements PageService<T>,PublishService {
	protected static final Log log = LogFactory.getLog(PageServiceImpl.class);
	/**
	 * 分页查询
	 * @param searchText 	查询条件
	 * @param currentPage	当前查询页
	 * @param pageSize		当前页数据记录数
	 * @param sort			需要排序的列名
	 * @param order			排序方式
	 * @return 返回查询的结果
	 */
	@Override
	public DataModel<T> getPageList(String searchText, String currentPage,
			String pageSize, String sort, String order,PageParamsVo pageParamsVo) throws ServiceException {
		log.debug("getPageList("+searchText+","+currentPage+","+pageSize+","+sort+","+order+") - start");
		LimitKey key = new LimitKey();
		if (StringUtil.isNotEmpty(searchText)) {
			key.setSearchText(searchText);
		}
		if (StringUtil.isNotEmpty(sort)) {
			key.setSortColumnName(sort);
		}
		if (SortType.Asc.name().toLowerCase().equals(order)) {
			key.setSortType(SortType.Asc);
		} else {
			key.setSortType(SortType.Desc);
		}
		if (StringUtil.isNotEmpty(currentPage) && StringUtil.isNotEmpty(pageSize)) {
			key.setOffset((Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize));
			key.setPageSize(Integer.parseInt(pageSize));
		}
		
		log.debug("getPageList("+searchText+","+currentPage+","+pageSize+","+sort+","+order+") - end");
		return this.getPageList(key,pageParamsVo);
	}

	@Override
	public Object publishService(Object obj) throws PublicException {
		log.debug("publishService("+obj+") - start");
		DataModel<T> dataModel=null;
		ResponseVo vo=null;
		
		try{
			if(obj instanceof RequestVo){
				RequestVo requestVo=(RequestVo)obj;
				ParamsVo paramsVo=requestVo.getParams();
				if(paramsVo instanceof PageParamsVo){
					PageParamsVo pageParamsVo=(PageParamsVo)paramsVo;
					pageParamsVo.setCallType(requestVo.getCallType());
					pageParamsVo.setServiceName(requestVo.getServiceName());
					dataModel=this.getPageList(pageParamsVo.getSearchText(), pageParamsVo.getCurrentPage(), 
							pageParamsVo.getPageSize(), pageParamsVo.getSortColumnName(), pageParamsVo.getOrder(),pageParamsVo);
				}
			}
			vo=new ResponseVo();
			
				PageResultVo<T> pageResultVo=new PageResultVo<T>();
				pageResultVo.setList(dataModel.getRows());
				pageResultVo.setTotalResults(dataModel.getTotal());
				vo.setResult(pageResultVo);
			vo.setServiceName(dataModel.getServerName());
			vo.setReturnCode(ResponseVo.SUCCESS);
			
		}catch(ServiceException e){
			log.error("publishService("+obj+") - error");
			throw new PublicException(e.getMessage());
		}
		log.debug("publishService("+obj+") - end");
		return vo;
	}

}


