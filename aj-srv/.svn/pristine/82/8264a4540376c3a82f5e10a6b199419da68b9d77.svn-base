package com.frame.core.jdbcdao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.frame.core.jdbcdao.JDBCBaseDao;




public class JDBCBaseDaoImpl implements JDBCBaseDao {

	private static Logger logger = Logger.getLogger(JDBCBaseDaoImpl.class.getName());
	
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @author xiongfeng
	 * @param sql
	 * @return
	 * 单挑更新
	 */
	public int updateTableArgs(String sql){
		try{
			logger.info("----the method is updateTableArgs and params only is sql--------");
			logger.info("----the sql is : "+sql);
			int result = jdbcTemplate.update(sql);
			return result;
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * @author xiongfeng
	 * @param sql
	 * @param args
	 * @return
	 * 单条更新
	 */
	public int updateTableArgs(String sql, Object[] args){
		try{
			logger.info("----the method is updateTableArgs--------");
			logger.info("----the sql is : "+sql);
			int result = jdbcTemplate.update(sql, args);
			return result;
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return 0;
		}
	}
	/**
	 *批量更新，需要设置setter
	 */
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter setter) {
		logger.info("batchUpdate =====size:" + setter.getBatchSize() + "----the sql is : "+sql);
        return jdbcTemplate.batchUpdate(sql, setter);
    }
	


	@SuppressWarnings("unchecked")
	public List queryList(String sql){
		logger.info("----the sql is : "+sql);
		List list = new ArrayList();
		list = jdbcTemplate.queryForList(sql);
		return list;
	}
	public List queryList(String sql,Object[] objects){
		logger.info("----the sql is : "+sql);
		List list = new ArrayList();
		list = jdbcTemplate.queryForList(sql, objects);
		return list;
	}

	
	
	public Connection  getConnection()
	{
		Connection cn = null;
		
		try {
			cn =  jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cn;
	}
	
	public void executeSql(String sql)
	{
		logger.info("----the sql is : "+sql);
		jdbcTemplate.execute(sql);
	}
	
}
