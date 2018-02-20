package com.aj.sys.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aj.albummgr.vo.TAlbum;
import com.frame.core.dao.GenericDAO;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.UUIDUtil;

/**
 * 删除我家首页照片更新消息提醒，  更新所有家庭相册的访问时间
 * */

@Service("delPhotoRemind")
public class DelPhotoRemindService implements PublishService{

	private Logger log = Logger.getLogger(DelPhotoRemindService.class);
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String familyId = params.optString("familyId");
		
		JSONObject returnJSON = new JSONObject();
		JSONObject result = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}
		if(familyId == null || "".equals(familyId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "familyId为空！");
			return returnJSON.toString();
		}
		
		try {
			//根据familyid查询家庭相册
			String hsql = "from TAlbum where albumType = 4 and familyId = ?";
			List<TAlbum> albumList = baseDAO.getGenericByHql(hsql, familyId);
			if(albumList != null && albumList.size()>0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(TAlbum album : albumList){
					
					//记录该相册访问记录
					String sql= "select id from t_album_access_info where album_id = ? and access_user_id = ?";
					List<Map<String, Object>> accessList = baseDAO.getGenericBySQL(sql, new Object[]{album.getId(), userId});
					if(accessList ==  null || accessList.size() == 0){
						//新增
						sql = "insert into t_album_access_info (id, album_id, access_time, access_user_id) values (?,?,?,?)";
						baseDAO.execteNativeBulk(sql, UUIDUtil.uuid(), album.getId(), sdf.format(new Date()), userId);
					}else{
						sql = "update t_album_access_info set access_time = ? where id = ? ";
						baseDAO.execteNativeBulk(sql, sdf.format(new Date()), accessList.get(0).get("id").toString());
					}
					
				}
			}
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			result.put("succMsg", "操作成功");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "");
			
		} catch (Exception e) {
			
			log.info("查询系统消息失败，原因："+e.getMessage());
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			result.put("succMsg", "");
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "服务器内部错误！");
			e.printStackTrace();
		}
		
		return returnJSON.toString();
	}

}
