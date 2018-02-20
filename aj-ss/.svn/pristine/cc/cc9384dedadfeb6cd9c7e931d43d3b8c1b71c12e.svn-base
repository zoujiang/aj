package com.qm.shop.feedback.service;

import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.feedback.vo.ShopFeedbackVO;

public interface ShopFeedbackService {

	DataModel<Map<String, Object>> getList(ShopFeedbackVO limitKey);

	Map<String, Object> findById(String id);

	int update(String id, String replyContent, String userid,
			String dateFromatYYYYMMddHHmmss);

}
