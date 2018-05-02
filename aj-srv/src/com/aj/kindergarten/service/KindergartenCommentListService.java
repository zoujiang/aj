package com.aj.kindergarten.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


/**
 *幼儿园评价列表查询
 * */

@Service("kindergartenCommentList")
public class KindergartenCommentListService implements PublishService{

	private Logger log = Logger.getLogger(KindergartenCommentListService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String kindergartenId = params.optString("kindergartenId");
		String pageSize = params.optString("pageSize");
		String currentPage = params.optString("currentPage");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		
		if(kindergartenId == null || "".equals(kindergartenId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "kindergartenId为空！");
			return returnJSON.toString();
		}
		if(pageSize == null || "".equals(pageSize)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "pageSize为空！");
			return returnJSON.toString();
		}
		if(currentPage == null || "".equals(currentPage)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "currentPage为空！");
			return returnJSON.toString();
		}
		
		TKindergartenInfo shop = baseDAO.get(TKindergartenInfo.class, Integer.parseInt(kindergartenId) );
		
		JSONObject shopInfo = new JSONObject();
		shopInfo.put("shopId", shop.getId());
		shopInfo.put("shopName", shop.getName());
		String showPics = "";
		String imgPrefix = SystemConfig.getValue("img.http.url");
		if(shop.getShowPics() != null && !"".equals(shop.getShowPics())){
			String[] pics = shop.getShowPics().split(",");
			for(int i= 0; i< pics.length ; i++){
				if(i == 0){
					showPics = imgPrefix + pics[i];
				}else{
					showPics += ","+imgPrefix + pics[i];
				}
			}
		}
		shopInfo.put("shopShowPic", showPics);
		shopInfo.put("shopLogo", (shop.getLogo() != null && !"".equals(shop.getLogo())) ? (imgPrefix+ shop.getLogo()) : "" );
		result.put("shopInfo", shopInfo);
		
		String sql = "SELECT u.id cmtUserId, u.PHOTO cmtUserPhoto, u.NICK_NAME cmtUserNickName, c.cmt_content cmtContent, c.cmt_img cmtImgUrl, c.score cmtScore, c.cmt_time cmtDate FROM t_shop_comment c, t_user u WHERE c.cmt_user_id = u.ID AND c.cmt_shop_id = ? AND c.status = 0  AND c.shop_type =1 ORDER BY c.cmt_time DESC";
		List<Map<String, Object>> cmtList = baseDAO.getGenericByPositionToSQL(sql,Integer.parseInt(currentPage) * Integer.parseInt(pageSize), Integer.parseInt(pageSize), new Object[]{kindergartenId});
		if(cmtList != null && cmtList.size() > 0){
			for(Map<String, Object> cmt : cmtList){
				if(cmt.get("cmtUserPhoto") != null && !"".equals(cmt.get("cmtUserPhoto").toString())){
					cmt.put("cmtUserPhoto", imgPrefix + cmt.get("cmtUserPhoto"));
				}
				String ciu = "";
				if(cmt.get("cmtImgUrl") != null && !"".equals(cmt.get("cmtImgUrl").toString())){
					String cmtImgUrl = cmt.get("cmtImgUrl").toString();
					String[] pics = cmtImgUrl.split(",");
					for(int i= 0; i< pics.length ; i++){
						if(i == 0){
							ciu = imgPrefix + pics[i];
						}else{
							ciu += ","+imgPrefix + pics[i];
						}
					}
				}
				cmt.put("cmtImgUrl",ciu);
			}
		}
		
		result.put("commentInfo", cmtList);
		
		result.put("successMsg", "查询成功");
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}
