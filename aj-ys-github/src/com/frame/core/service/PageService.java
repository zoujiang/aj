package com.frame.core.service;

import com.frame.core.exception.ServiceException;
import com.frame.core.util.pagination.key.LimitKey;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.PageParamsVo;


public interface PageService<T> {
	/**
	 * 分页查询
	 * @param searchText 	查询条件
	 * @param currentPage	当前查询页
	 * @param pageSize		当前页数据记录数
	 * @param sort			需要排序的列名
	 * @param order			排序方式
	 * @return 返回查询的结果
	 */
	public DataModel<T> getPageList(String searchText, String currentPage, String pageSize, String sort, String order,PageParamsVo pageParamsVo) throws ServiceException;
	
	/**
	 * 分页查询
	 * @param LimitKey 分页条件
	 * @return 返回查询的结果
	 */
	public DataModel<T> getPageList(LimitKey key,PageParamsVo pageParamsVo) throws ServiceException;
	/**
	 * 查询总数
	 * @param LimitKey 分页条件
	 * @return 返回查询的总数
	 */
	public int getCountByKey(LimitKey key,PageParamsVo pageParamsVo) throws ServiceException;
}
