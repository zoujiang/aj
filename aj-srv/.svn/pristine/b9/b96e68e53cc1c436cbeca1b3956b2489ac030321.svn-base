/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import com.aam.model.TUser;
import com.aj.familymgr.vo.TFamilyInfo;
import com.aj.familymgr.vo.THomeInfo;
import com.aj.kindergarten.vo.TKindergartenAlbumVisible;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service("kindergartenAlbumList")
public class KindergartenAlbumListService implements PublishService{

	String imgUrl= SystemConfig.getValue("img.http.url");

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int pageSize = params.optInt("pageSize");
		int currentPage = params.optInt("currentPage");

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
            String kdName = kindergartenName +"("+ series +studentName+")";
			albumInfo.put("kindergartenName", kdName);

			//查询相册
			sql = "select a.*, (select count(1) from t_kindergarten_photo p where p.album_id = a.id ) photoNumber, (select count(1) from t_kindergarten_photo p where p.album_id = a.id AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= DATE(p.create_time)) laterNumber   from `t_kindergarten_albun` a where  a.shcool_id = ? and a.grade_id = ?  and case when a.type = 2 then  a.student = ? else 1=1 end";
			List<Map<String, Object>> kinderpratenAlbumList = baseDAO.getGenericBySQL(sql, new Object[]{ kindergartenId, gradeId, studentId} );

			List<Map<String, Object>> albumList = new ArrayList<Map<String, Object>>();

			for(Map<String, Object> album : kinderpratenAlbumList){

				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("albumId", album.get("id"));
				temp.put("albumName", album.get("album_name"));
				temp.put("albumUrl", album.get("zoneUrl") == null ? "" : imgUrl+ album.get("zoneUrl"));
				temp.put("photoNumber", album.get("photoNumber"));
				temp.put("laterNumber", album.get("laterNumber"));
				temp.put("albumDesc", album.get(""));
				temp.put("albumType", album.get("type"));

                //查看此相册是否被设置为 非所有人可见
                String hql2 = "from TKindergartenAlbumVisible where familyId = ? and albumId = ?";
                List<TKindergartenAlbumVisible> av = baseDAO.getGenericByHql(hql2, user.getFamilyId(), album.get("id"));
                if(av.size() == 0 || av.get(0).getVisibleProperty() ==2 ){
                    temp.put("visibleProperty", 2);
                }else{
                    temp.put("visibleProperty", av.get(0).getVisibleProperty());
                }
                temp.put("gradeId", album.get("grade_id"));


                albumList.add(temp);
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
