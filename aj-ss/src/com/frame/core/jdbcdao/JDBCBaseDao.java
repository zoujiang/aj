package com.frame.core.jdbcdao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author xiongfeng
 *
 */
public interface JDBCBaseDao {
	
	public int updateTableArgs(String sql);
	
	public int updateTableArgs(String sql, Object[] args);
	
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter setter);
	
	public List queryList(String sql);
	
	public List queryList(String sql,Object[] objects);
	
	public Connection  getConnection();
	
	public void executeSql(String sql);
}
