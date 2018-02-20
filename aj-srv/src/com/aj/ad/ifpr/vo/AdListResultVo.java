package com.aj.ad.ifpr.vo;

import java.util.List;

import com.frame.core.vo.ResultVo;

public class AdListResultVo extends ResultVo {
	
	private List list;

	public AdListResultVo(List list) {
		super();
		this.list = list;
	}

	
	public List<AdListResultBean> getList() {
		return list;
	}

	
	
	
	

	

	
	
	
}
