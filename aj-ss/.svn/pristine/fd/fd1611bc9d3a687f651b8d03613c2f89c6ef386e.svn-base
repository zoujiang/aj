package com.frame.core.jdbcdao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

/**
 * @author lvyb
 * @date 2014-5-5
 */

@SuppressWarnings("rawtypes")
public class SplitPageResultSetExtractor implements ResultSetExtractor {
	private int currentPage = 1;// 当前页

	private int pageSize = 10;// 每页记录数

	private final RowMapper rowMapper;// 行包装器

	public SplitPageResultSetExtractor(RowMapper rowMapper, int currentPage,int pageSize) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	/**
	 * 没有使用该方法 如果数据量较大 就无法查询出数据
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	 @SuppressWarnings("unchecked")
	public Object extractData(ResultSet rs) throws SQLException,DataAccessException {
		List result = new ArrayList();
		int rowNum = 0;
		int end = currentPage*pageSize + 1;
		point: while (rs.next()) {
			++rowNum;
			if (rowNum < (currentPage-1)*pageSize+1) {
				continue point;
			} else if (rowNum >= end) {
				break point;
			} else {
				result.add(this.rowMapper.mapRow(rs, rowNum));
			}
		}
		return result;
	 }
}