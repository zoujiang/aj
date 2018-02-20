/**
 * 
 */
package com.frame.core.constant;

/**
 * @author Jideas
 * 
 */
public enum SheetNumberType {

	/**
	 * 网上订单
	 */
	OnlineOrder("WSDD", 6),

	/**
	 * 武汉7号面值卡批次号
	 */
	CardNumber("0", 4),

	/**
	 * 面值卡顺序号
	 */
	CardOrdinal("MZK", 4),
	
	/**
	 * 会员编号
	 */
	MemberCode("HY", 4),
	
	UnionPayOrdId("0",8),
	
	FolderCode("0",12),

	;
	private String defaultPrefix;
	private int length;

	private SheetNumberType(String defaultPrefix, int length) {
		this.defaultPrefix = defaultPrefix;
		this.length = length;
	}

	public String getDefaultPrefix() {
		return this.defaultPrefix;
	}

	public int getLength() {
		return length;
	}
}
