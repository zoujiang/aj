package com.frame.core.jdbcdao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BaseDao {
	
	public Map<String, Object> callProcedure(String procedureName, Object... params);
	
    public <T> T callFunction(String functionName, Class<T> retureType, Object... params);
    
	public int update(String sql);
	
	public void emptyTable(String sql);

    public int update(String sql, Object... params);

    public int update(String sql, Object[] params, int[] paramsTypes);

	public List<Map<String,Object>> query(String sql);
 
    public List<Map<String, Object>> query(String sql, Object... params);

    public List<Map<String, Object>> query(String sql, Object[] params,int[] paramsTypes);

    public <T> T queryForObject(String sql, Class<T> t);

    public <T> T queryForObject(String sql, Class<T> t, Object... params);

    public <T> T queryForObject(String sql, Class<T> t, Object[] params, int[] paramsTypes);

	public int insert(String sql);

    public int insert(String sql, Object... params);

    public int insert(String sql, Object[] params, int[] paramsTypes);

	public int delete(String sql);

    public int delete(String sql, Object... params);

    public int delete(String sql, Object[] params, int[] paramsTypes);	

    public int count(String sql);

    public int count(String sql,Object... params);

    public int[] batchUpate(String sql ,final List<Map<String,Object>> list);
    
    public List<Map<String, Object>> querySP(String sql, int currentPage, int rowsCount);

	/**
	 * 普通分页查询<br>
	 * <b>如果结果结合比较大应该调用setFetchsize() 和setMaxRow两个方法来控制一下，否则会内存溢出</b>
	 * 
	 * @see #setFetchSize(int)
	 * @see #setMaxRows(int)
	 * @param sql
	 *            查询的sql语句
	 * @param currentPage
	 *            起始行
	 * @param rowsCount
	 *            获取的行数
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryForListPagination(String sql, Object[] arg1,int currentPage, int rowsCount);

	/**
	 * 普通分页查询<br>
	 * <b>如果结果结合比较大应该调用setFetchsize() 和setMaxRow两个方法来控制一下，否则会内存溢出</b>
	 * 
	 * @see #setFetchSize(int)
	 * @see #setMaxRows(int)
	 * @param types
	 *            [] 传递参数的类型值 java.sql.Types.VARCHAR
	 * @param sql
	 *            查询的sql语句
	 * @param currentPage
	 *            起始行
	 * @param rowsCount
	 *            获取的行数
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryForListPagination(String sql, Object[] arg1,int[] types, int currentPage, int rowsCount);
	/**
	 * 分页使用
	 * @param sql
	 * @param currentPage
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Map<String,Object> page(String sql,int currentPage,int pageSize,Object... params);
}
