package com.qm.shop.feedback.service;

import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.feedback.vo.ShopFeedbackVO;

public interface ShopFeedbackService {

	DataModel<Map<String, Object>> getList(ShopFeedbackVO limitKey);

	Map<String, Object> findById(String id);

	int update(String id, String replyContent, String userid,
			String dateFromatYYYYMMddHHmmss);

	int add(String generateID, String fbCategory, String fbContent,
			String dateFromatYYYYMMddHHmmss, String shopId, String userid);

	int updateNotShow(String id);

	int update(String id, String fbCategory, String fbContent);

}
