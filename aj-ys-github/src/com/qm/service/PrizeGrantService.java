package com.qm.service;

import java.util.List;

import com.qm.entities.PrizeGrantInfo;

public interface PrizeGrantService {

	List<PrizeGrantInfo> queryList(PrizeGrantInfo info);

	int getTotal(PrizeGrantInfo info);

}
