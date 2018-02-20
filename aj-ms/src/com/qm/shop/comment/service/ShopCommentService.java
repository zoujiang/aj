package com.qm.shop.comment.service;

import java.util.Map;

import com.frame.core.vo.DataModel;
import com.qm.shop.comment.vo.ShopCommentVO;

public interface ShopCommentService {

	DataModel<Map<String, Object>> getList(ShopCommentVO limitKey);

	int updateCommentStatus(String id, String status);

}
