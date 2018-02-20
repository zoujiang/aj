package com.aj.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 * 查询商家相册列表
 * */

@Service("shopAlbumList")
public class QueryShopAlbumListService implements PublishService{

	private Logger log = Logger.getLogger(QueryShopAlbumListService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		//String shopId = params.optString("shopId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		/*
		if(shopId == null || "".equals(shopId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "shopId为空！");
			return returnJSON.toString();
		}*/
		//根据userId查询有哪些商户
		String sql = "select DISTINCT t.* from ( SELECT si.id shopId, si.shop_name shopName, si.logo shopLogo, si.show_pic shopShowPic, si.address shopAddress,si.tel FROM t_shop_album sa , t_shop_info si , t_shop_customer_user scu  WHERE sa.shop_id = si.id AND sa.user_id = scu.id AND si.status = 0 AND scu.user_id = ? "+
				"	UNION "+
				"	SELECT  si.id shopId, si.shop_name shopName, si.logo shopLogo, si.show_pic shopShowPic, si.address shopAddress,si.tel   FROM t_shop_dynamic_album sa , t_shop_info si , t_shop_customer_user scu  WHERE sa.shop_id = si.id AND sa.user_id = scu.id AND  si.status = 0 AND scu.user_id = ?"
				+ " ) t ";

		String imgUrl= SystemConfig.getValue("img.http.url");
		List<Map<String, Object>> shopList = baseDAO.getGenericBySQL(sql, new Object[]{userId, userId});
		String commentSql = "SELECT COUNT(1) num, FORMAT(AVG(score),1) score FROM t_shop_comment WHERE cmt_shop_id = ?";
		if(shopList != null && shopList.size()>0){
			for(Map<String, Object> shop : shopList){
				String shopId = shop.get("shopId").toString();
				List<Map<String, Object>> cmtList = baseDAO.getGenericBySQL(commentSql, new Object[]{shop.get("shopId")});
				if(cmtList != null && cmtList.size() > 0){
					shop.put("shopScore", cmtList.get(0).get("score") == null ? 4 : cmtList.get(0).get("score"));
					shop.put("shopCmtCount", cmtList.get(0).get("num"));
				}else{
					shop.put("shopScore", 4);
					shop.put("shopCmtCount", 0);
				}

				if(shop.get("shopLogo") != null && !"".equals(shop.get("shopLogo"))){
					shop.put("shopLogo", imgUrl + shop.get("shopLogo"));
				}
				if(shop.get("shopShowPic") != null && !"".equals(shop.get("shopShowPic"))){
					String[] pics = shop.get("shopShowPic").toString().split(",");
					StringBuffer fullUrl = new StringBuffer();
					for(String pic : pics){
						fullUrl.append(imgUrl);
						fullUrl.append(pic);
						fullUrl.append(",");
					}
					shop.put("shopShowPic", fullUrl.toString().substring(0, fullUrl.toString().length()-1));
				}

				List<Map<String, Object>> albumList =new ArrayList<Map<String,Object>>();
				sql = "SELECT DISTINCT t.* FROM ("+
						"	SELECT  sa.id albumId, sa.album_name albumName , sa.album_logo albumLogo, (SELECT COUNT(1) FROM t_shop_photo WHERE album_id = sa.id) photoCount  ,sa.create_time createTime,"+
						"	CASE WHEN sa.is_pay = 0 OR sa.had_paid = 1 THEN 1 ELSE 0 END isPaid , 0 albumType , '' albumUrl, '' musicUrl"+
						"	FROM t_shop_album sa , t_shop_customer_user scu  WHERE  scu.id  = sa.user_id  AND scu.user_id = ?  AND sa.shop_id = ?"+
						"	UNION "+
						"	SELECT  sa.id albumId, sa.album_name albumName , sa.album_logo albumLogo, (SELECT COUNT(1) FROM t_shop_photo WHERE album_id = sa.id) photoCount  ,sa.create_time createTime,1 isPaid , 1 albumType, a.template_url albumUrl, a.music_url musicUrl "+
						"	FROM t_shop_dynamic_album sa ,t_shop_customer_user scu, t_album_template a  WHERE scu.id  = sa.user_id  AND sa.template_id = a.id AND scu.user_id = ? AND sa.shop_id = ?"+
						"	) t "+
						"	ORDER BY t.createTime desc";

				albumList = baseDAO.getGenericBySQL(sql, new Object[]{userId, shopId,userId, shopId});
				if(albumList != null && albumList.size() > 0){
					String ajServerUrl = SystemConfig.getValue("aj.service.address");
					for(Map<String, Object> album : albumList){
						if(album.get("albumLogo") != null && !"".equals(album.get("albumLogo"))){
							album.put("albumLogo", imgUrl + album.get("albumLogo"));
						}
						if(album.get("albumType") != null && "1".equals(album.get("albumType").toString())){
							//动感影集
							String music = album.get("musicUrl") == null ? "" : album.get("musicUrl").toString();
							String m = "";
							if(!"".equals(music)){
								m = music.split(",")[new Random().nextInt(music.split(",").length) ];
							}
							String url = ajServerUrl + "/dynamic/demo.jsp?albumId="+album.get("albumId") +"&tpUrl="+album.get("albumUrl")+"&musicUrl="+ m;
							album.put("albumUrl", url);
						}
					}
				}
				shop.put("shopAlbum", albumList);

			}
		}


		result.put("list", shopList);
		
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

}
