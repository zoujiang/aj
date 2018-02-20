package com.aj.shop.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.shop.vo.TShopAlbum;
import com.aj.shop.vo.TShopInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;


/**
 *5.7.4影楼相册浏览
 * */

@Service("shopAlbumView")
public class ShopAlbumViewService implements PublishService{

	private Logger log = Logger.getLogger(ShopAlbumViewService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String albumId = params.optString("albumId");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		
		if(albumId == null || "".equals(albumId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "albumId为空！");
			return returnJSON.toString();
		}
		
		TShopAlbum  album = baseDAO.get(TShopAlbum.class, albumId);
		if(album == null ){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("errorMsg", "相册不存在");
			return returnJSON.toString();
		}else{
			String imgPrefix = SystemConfig.getValue("img.http.url");
			result.put("albumName", album.getAlbumName());
			
			//相册浏览数+1
			album.setViewCount(album.getViewCount() + 1);
			baseDAO.update(album);
			
			result.put("albumLogo", imgPrefix+album.getAlbumLogo());
			
			TShopInfo shop = baseDAO.get(TShopInfo.class, album.getShopId());
			
			JSONObject shopInfo = new JSONObject();
			shopInfo.put("shopId", shop.getId());
			shopInfo.put("shopName", shop.getShopName());
			String showPics = "";
			
			if(shop.getShowPic() != null && !"".equals(shop.getShowPic())){
				String[] pics = shop.getShowPic().split(",");
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
		/*	
			String sql = "SELECT DATE_FORMAT(p.create_time, '%Y-%m-%d') create_date FROM (SELECT * FROM t_shop_photo WHERE  ALBUM_ID = ? ORDER BY create_time DESC ) p " +
					" GROUP BY DATE_FORMAT(p.create_time, '%Y-%m-%d') ORDER BY create_time DESC";
			//	String sql = "SELECT DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') create_date FROM (SELECT * FROM t_photo WHERE CREATE_USER_ID =? AND ALBUM_ID = ? ORDER BY create_date DESC LIMIT ?,? ) p " +
			//			" GROUP BY DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') ORDER BY create_date DESC";
			List<Map<String, Object>> dayList = baseDAO.getGenericBySQL(sql, new Object[]{albumId});
			//查询每天下面的照片信息
			String imgUrl= SystemConfig.getValue("img.http.url");
			JSONArray photoInfo = new JSONArray();
			if(dayList != null && dayList.size()>0){
				for(int i=0;i<dayList.size();i++){
					
					String date = dayList.get(i).get("create_date").toString();
					//sql = "SELECT p.id photoId,CONCAT('"+imgUrl+"',p.PHOTO_URL)  photo, p.ISPRIVATE isPrivate  FROM t_photo p WHERE p.CREATE_USER_ID =? AND p.ALBUM_ID = ?  AND  DATE_FORMAT(p.CREATE_DATE, '%Y-%m-%d') = ? ORDER BY p.CREATE_DATE";
					sql = "SELECT p.id photoId,p.PHOTO_URL  photoUrl FROM t_shop_photo p WHERE  p.ALBUM_ID = ?  AND  DATE_FORMAT(p.create_time, '%Y-%m-%d') = ? ORDER BY p.create_time DESC";
					List<Map<String, Object>> photoList = baseDAO.getGenericBySQL(sql, new Object[]{albumId, date});
					for(Map<String, Object> photo : photoList){
						//返回小图
						String pu = photo.get("photoUrl").toString();
						String preU = pu.substring(0, pu.lastIndexOf("/")+1);
						String endU = pu.substring(pu.lastIndexOf("/")+1);
						photo.put("photoUrl", imgUrl+preU + "s"+endU);
					}
					
					JSONObject jo = new JSONObject();
					jo.put("photoDate", date.replaceFirst("-", "年").replace("年0", "年").replace("-", "月").replace("月0", "月")+"日");
					jo.put("photoList", photoList);
					photoInfo.add(jo);
				}
			}
			
			
		*/
			JSONArray photoInfo = new JSONArray();
			String date = album.getPhotoTime();
			
			JSONObject jo = new JSONObject();
			jo.put("photoDate", date.replaceFirst("-", "年").replace("年0", "年").replace("-", "月").replace("月0", "月")+"日");
			String imgUrl= SystemConfig.getValue("img.http.url");
			List<Map<String, Object>> photoList = baseDAO.getGenericBySQL("SELECT p.id photoId,p.PHOTO_URL  photoUrl FROM t_shop_photo p WHERE  p.ALBUM_ID = ?  ORDER BY p.create_time DESC", new Object[]{albumId});
			for(Map<String, Object> photo : photoList){
				//返回小图
				String pu = photo.get("photoUrl") == null ? "" : photo.get("photoUrl").toString();
				if(!"".equals(pu) ){
					
					String preU = pu.substring(0, pu.lastIndexOf("/")+1);
					String endU = pu.substring(pu.lastIndexOf("/")+1);
					photo.put("photoUrl", imgUrl+preU + "s"+endU);
				}
			}
			
			jo.put("photoList", photoList);
			photoInfo.add(jo);
			
			result.put("photoInfo", photoInfo);
			if(album.getIsPay() == 0){
				result.put("isPaid", 1);
			}else{
				result.put("isPaid", album.getHadPaid());

				try {
					int month = DateUtil.monthSpace(DateUtil.stringToTimestamp(album.getCreateTime(), DateUtil.DATE_TIME_PATTERN2), new Date());
					int  total_fee = 0; //总金额，单位：分
					double od = Double.parseDouble(album.getOriginalPrice());
					String[] payTypes = album.getPayType().split("\\|");
					// 原价od * 折扣百分比 * 0.01  * 100（分）
					if(month <= 3){
						total_fee = (int)(od * Integer.parseInt(payTypes[0]));
					}else if(month <= 6){
						total_fee = (int)(od * Integer.parseInt(payTypes[1]));
					}else if(month <= 9){
						total_fee = (int)(od * Integer.parseInt(payTypes[2]));
					}else if(month <= 12){
						total_fee = (int)(od * Integer.parseInt(payTypes[3]));
					}else if(month <= 24){
						total_fee = (int)(od * Integer.parseInt(payTypes[4]));
					}else{
						total_fee = (int)(od * Integer.parseInt(payTypes[5]));
					}
					
					result.put("originalPrice", album.getOriginalPrice());
					result.put("newPrice", total_fee * 0.01);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
		}
		
		
	}

}
