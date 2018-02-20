package com.frame.core.jdbcdao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.frame.core.jdbcdao.BaseDao;

public class BaseDaoImpl extends JdbcTemplate implements BaseDao {
	
	private static Logger logger = Logger.getLogger(BaseDaoImpl.class);
	private DataSource dataSource;
	@Override
    public Map<String, Object> callProcedure(String procedureName, Object... params) {
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withProcedureName(procedureName);
        return simpleJdbcCall.execute(params);
    }
	@Override
    public <T> T callFunction(String functionName, Class<T> retureType, Object... params) {
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        simpleJdbcCall.withFunctionName(functionName);
        return simpleJdbcCall.executeFunction(retureType, params);
    }
	@Override
	public int update(String sql) {
		logger.info(sql);
        int result = super.update(sql);
		return result;
	}
	@Override
	public void emptyTable(String sql){
		logger.info(sql);
        super.execute(sql);
	}
    @Override
    public int update(String sql, Object... params) {
    	logger.info(sql);
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.update(sql, params);
    }

    @Override
    public int update(String sql, Object[] params, int[] paramsTypes) {
    	logger.info(sql);
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.update(sql, params, paramsTypes);
    }
    
    @Override
	public List<Map<String, Object>> query(String sql) {
		logger.info(sql);
		return super.queryForList(sql);
	}

    @Override
    public List<Map<String, Object>> query(String sql, Object... params) {
    	logger.info(sql);
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.queryForList(sql,params);
    }

    @Override
    public List<Map<String, Object>> query(String sql, Object[] params,int[] paramsTypes) {
    	logger.info(sql);
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.queryForList(sql,params,paramsTypes);
    }

    @Override
    public int insert(String sql) {
        logger.info(sql);
        int result = super.update(sql);
        return result;
    }

    @Override
    public int insert(String sql, Object... params) {
    	logger.info(sql);
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.update(sql, params);
    }

    @Override
    public int insert(String sql, Object[] params, int[] paramsTypes) {
    	logger.info(sql);
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.update(sql, params, paramsTypes);
    }
    
    @Override
    public int delete(String sql) {
        logger.info(sql);
        int result = super.update(sql);
        return result;
    }

    @Override
    public int delete(String sql, Object... params) {
    	logger.info(sql);
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.update(sql, params);
    }

    @Override
    public int delete(String sql, Object[] params, int[] paramsTypes) {
    	logger.info(sql);
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.update(sql, params, paramsTypes);
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType) {
        return super.queryForObject(sql, requiredType);
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> t, Object... params) {
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return super.queryForObject(sql, t, params);
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> t, Object[] params,int[] paramsTypes) {
    	if(params != null){
    		logger.info(Arrays.toString(params));
    	}
        return this.queryForObject(sql, t, params, paramsTypes);
    }

	@Override
	public int count(String sql) {
		logger.info(sql);
		int a = super.queryForInt(sql);
		return a;
	}

	@Override
	public int count(String sql, Object... params) {
		logger.info(sql);
		if(params != null){
    		logger.info(Arrays.toString(params));
    	}
		int a = super.queryForInt(sql,params);
		return a;
	}

	@Override
	public int[] batchUpate(String sql ,final List<Map<String, Object>> list) {
		logger.info(sql);
		if(list != null){
    		logger.info(Arrays.toString(list.toArray()));
    	}
		int[] a = super.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Map<String,Object> map = list.get(i);
				int a= 1;
				for(String key :map.keySet()){					
					Object value = map.get(key);
					if(value instanceof String){
						ps.setString(a, (String)value);
					} else if(value instanceof Integer){
						ps.setInt(a, (Integer)value);
					}
					a++;
				}
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		return a;
	}
	@Override
	public List<Map<String, Object>> querySP(String sql, int currentPage, int pageSize)throws DataAccessException {
		logger.info(sql+" currentPage:"+currentPage+" pageSize:"+pageSize);
		return querySP(sql, currentPage, pageSize, getColumnMapRowMapper());
	}

	/**
	 * 自定义行包装器查询<br>
	 * <b>如果结果结合比较大应该调用setFetchsize() 和setMaxRow两个方法来控制一下，否则会内存溢出</b>
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> querySP(String sql, int currentPage, int pageSize,RowMapper rowMapper) throws DataAccessException {
		return (List) query(sql, new SplitPageResultSetExtractor(rowMapper,currentPage, pageSize));
	}

	@Override
	public List<Map<String, Object>> queryForListPagination(String sql, Object[] arg1,int currentPage, int pageSize) throws DataAccessException {
		logger.info(sql+" currentPage:"+currentPage+" pageSize:"+pageSize);
		if(arg1 != null){
    		logger.info(Arrays.toString(arg1));
    	}
		return queryPagination(sql, arg1, currentPage, pageSize,getColumnMapRowMapper());
	}

	@Override
	public List<Map<String, Object>> queryForListPagination(String sql, Object[] arg1,int[] types, int currentPage, int pageSize)throws DataAccessException {
		logger.info(sql+" currentPage:"+currentPage+" pageSize:"+pageSize);
		if(arg1 != null){
    		logger.info(Arrays.toString(arg1));
    	}
		return queryPagination(sql, arg1, types, currentPage, pageSize,getColumnMapRowMapper());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map<String, Object>> queryPagination(String sql, final Object[] arg1,int[] types, int currentPage, int pageSize,RowMapper columnMapRowMapper) {
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, types);
		factory.setResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
		PreparedStatementCreator psc = factory.newPreparedStatementCreator(arg1);
		return (List) query(psc, new SplitPageResultSetExtractor(columnMapRowMapper, currentPage, pageSize));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map<String, Object>> queryPagination(String sql, final Object[] arg1,int currentPage, int pageSize, RowMapper columnMapRowMapper) {
		int[] types = new int[arg1.length];
		for (int i = 0; i < arg1.length; i++) {
			types[i] = Types.VARCHAR;
		}
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, types);
		factory.setResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
		PreparedStatementCreator psc = factory.newPreparedStatementCreator(arg1);
		SplitPageResultSetExtractor extractor = new SplitPageResultSetExtractor(columnMapRowMapper, currentPage, pageSize);
		return (List) query(psc, extractor);
	}
	@Override
	public Map<String, Object> page(String sql, int currentPage,int pageSize,Object... params) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.queryForListPagination(sql, params, currentPage, pageSize);
		map.put("list", list);
		String countSql = "select count(1) from ("+sql+") INNER_TAB";
		int totalCount = this.count(countSql,params);
		
		// 总页数
		int totalPages = (int) Math.ceil((totalCount - 1) / pageSize) + 1;
		if(currentPage > totalPages){
			if(totalPages > 0){
				currentPage = totalPages;
			}		
		}
		if (totalPages == 0) {
			totalPages = 1;
		}
		map.put("totalCount", totalCount);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		map.put("pageCount", totalPages);
		return map;
	}
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		super.setDataSource(dataSource);
	}
}
