package com.qm.service;

import java.util.List;

import com.qm.entities.CouponExchange;

public interface CouponExchangeService {

	List<CouponExchange> queryList(CouponExchange info);

	int getTotal(CouponExchange info);

}
