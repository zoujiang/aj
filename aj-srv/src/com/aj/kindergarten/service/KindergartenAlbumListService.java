/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.kindergarten.vo.TKindergartenAlbumVisible;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;

import net.sf.json.JSONObject;


@Service("kindergartenAlbumList")
public class KindergartenAlbumListService implements PublishService{

	String imgUrl= SystemConfig.getValue("img.http.url");
	String resUrl= SystemConfig.getValue("res.http.url");

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();
		if(userId == null || "".equals(userId)){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "userId为空！");
			return returnJSON.toString();
		}

		TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));
		//幼儿园,因为有可能填的爸爸的手机号，有可能是妈妈的手机号，所以这里要查询出2个家长的登陆手机号
		String family =	user.getFamilyId();
		List<TUser> users = baseDAO.getGenericByHql("from TUser where familyId = ?", family);
		String phones = "";
		for(TUser u : users){
			phones += "'"+ u.getUserTel() +"',";
		}
		phones = phones.substring(0, phones.length() -1);

		String sql = "select s.kindergarten_id kindergartenId, i.`name` kindergartenName, i.logo logo, s.grade_id gradeId, s.name studentName, s.id studentId, g.series from `t_kindergarten_student`  s, `t_kindergarten_info` i, `t_kindergarten_grade` g where s.`kindergarten_id` = i.id and s.grade_id = g.id and  s.`parents_tel` in ("+ phones +") ";
		List<Map<String, Object>> kinderpratenList = baseDAO.getGenericBySQL(sql, null);
        List<Map<String, Object>> kdList = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> kd : kinderpratenList){

			String kindergartenId = kd.get("kindergartenId").toString();
			String kindergartenName = kd.get("kindergartenName").toString();
			String gradeId = kd.get("gradeId").toString();
			String studentName = kd.get("studentName").toString();
			String studentId = kd.get("studentId").toString();
			String series = kd.get("series").toString();   //入学年
			String logo = kd.get("logo") == null ? "" : imgUrl + kd.get("logo").toString() ;
			Map<String, Object> albumInfo = new HashMap<String, Object>();
            String kdName = kindergartenName +"("+ series +"级"+studentName+")";
			albumInfo.put("kindergartenId", kindergartenId);
			albumInfo.put("kindergartenName", kdName);
			albumInfo.put("studentId", studentId);

			//查询相册
			sql = "select a.*, (select count(1) from t_kindergarten_photo p where p.album_id = a.id ) photoNumber, (select count(1) from t_kindergarten_photo p where p.album_id = a.id AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(p.create_time)) laterNumber   from `t_kindergarten_album` a where  a.shcool_id = ? and a.grade_id = ?  and a.type = 1";
			List<Map<String, Object>> kinderpratenAlbumList = baseDAO.getGenericBySQL(sql, new Object[]{ kindergartenId, gradeId} );

			List<Map<String, Object>> albumList = new ArrayList<Map<String, Object>>();

			for(Map<String, Object> album : kinderpratenAlbumList){

				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("albumId", album.get("id"));
				temp.put("albumName", album.get("album_name"));
				temp.put("albumUrl", album.get("album_url") == null ? "" : imgUrl+ album.get("album_url"));
				int photoNumber = Integer.parseInt(album.get("photoNumber")+"");
				int laterNumber = Integer.parseInt(album.get("laterNumber")+"");
				
				temp.put("albumDesc", album.get(""));
				temp.put("albumType", album.get("type"));
				temp.put("downloadUrl", (album.get("download_url") == null || "".equals(album.get("download_url"))) ? "" : resUrl+ album.get("download_url"));
				temp.put("downloadSecret", album.get("download_secret"));
				//班级照片数量为 班级照片数 + 个人照片数
				String sql1 = "select id from t_kindergarten_album a where a.shcool_id = ? and a.grade_id = ?  and a.type = 2 and student = ? and current_grade_name =?";
				List<Map<String, Object>> generAlbumList = baseDAO.getGenericBySQL(sql1, new Object[]{ kindergartenId, gradeId, studentId, album.get("current_grade_name")} );
				if(generAlbumList != null && generAlbumList.size() > 0){
					String sql2 = "select count(1) from t_kindergarten_photo p where p.album_id = ? ";
					int studentPhotoNum = baseDAO.getGenericCountToSQL(sql2, new Object[]{generAlbumList.get(0).get("id")} );
					photoNumber += studentPhotoNum;
					String sql3 = "select count(1) from t_kindergarten_photo p where  DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(p.create_time) and p.album_id = ? ";
					int studentLastPhotoNum = baseDAO.getGenericCountToSQL(sql3, new Object[]{generAlbumList.get(0).get("id")} );
					laterNumber += studentLastPhotoNum;
				}
				temp.put("photoNumber", photoNumber);
				temp.put("laterNumber", laterNumber);
				temp.put("gradeId", album.get("grade_id"));
				
                //查看此相册是否被设置为 非所有人可见
                String hql2 = "from TKindergartenAlbumVisible where albumId = ?";
                List<TKindergartenAlbumVisible> av = baseDAO.getGenericByHql(hql2, album.get("id"));
                if(av.size() == 0 || av.get(0).getVisibleProperty() ==2 ){
                    temp.put("visibleProperty", 2);
                    albumList.add(temp);
                }else if(av.get(0).getVisibleProperty() ==0){
                	//个人可见
                	if(av.get(0).getFamilyId().equals(userId)){
                		//设置的人和当前登录的人ID一样时， 才会显示该相册
                		 temp.put("visibleProperty", 0);
                		 albumList.add(temp);
                	}
                }else if(av.get(0).getVisibleProperty() ==1){
                	//夫妻可见
                    
                    String setUserid =  av.get(0).getFamilyId();
                    TUser setUser = baseDAO.get(TUser.class, Integer.parseInt( setUserid));
                    if(setUser.getFamilyId().equals(family)){
                    	temp.put("visibleProperty", 1);
                    	albumList.add(temp);
                    }
                }
			}
			albumInfo.put("albumList", albumList);

            kdList.add(albumInfo);
		}
			
		result.put("succMsg", "查询成功");
		result.put("list", kdList);
		returnJSON.put("result", result);
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
	}

}
