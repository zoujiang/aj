package com.frame.core.util.pagination.key;

/**
 * @author Jideas
 */
public class LimitKey {
	/**
	 * 当前页数的下一页
	 */
	protected int offset;
	
	/**
	 * 当前页可以显示的页面数
	 */
	protected int pageSize = Integer.MAX_VALUE;
	/**
	 * 搜索内容
	 */
	protected String searchText;
	/**
	 * 是否查询总数
	 */
	protected boolean queryTotalCount;
	/**
	 * 排序方式
	 */
	protected SortType sortType = SortType.Asc;
	/**
	 * 排序字段
	 */
	protected String sortColumnName;
	
	public LimitKey(int offset, int pageSize, boolean queryTotalCount) {
		this.offset = offset;
		this.pageSize = pageSize;
		this.queryTotalCount = queryTotalCount;
	}

	public LimitKey() {

	}
	
	
	 //bootstrapTable  当前页可以显示的页面数
	public void setLimit(int limit) {
		this.pageSize = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchText() {
		if (null == searchText) return null;
		return searchText.trim();
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public boolean isQueryTotalCount() {
		return queryTotalCount;
	}

	public void setQueryTotalCount(boolean queryTotalCount) {
		this.queryTotalCount = queryTotalCount;
	}

	public SortType getSortType() {
		return sortType;
	}

	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	public String getSortColumnName() {
		return sortColumnName;
	}

	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}
	
}
