/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenAlbum;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.aj.kindergarten.vo.TKindergartenHonor;
import com.aj.kindergarten.vo.TKindergartenInfo;
import com.aj.kindergarten.vo.TKindergartenStudent;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.GradeNameUtil;

import net.sf.json.JSONObject;


@Service("kindergartenHonorMgr")
public class KindergartenHonorMgrService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String honorName = params.optString("honorName");
		String honorUrl = params.optString("honorUrl");  //多个以英文逗号隔开
		int type = params.optInt("type");
		int ownerId = params.optInt("ownerId");
        int oper = params.optInt("oper");
        Integer honerId = params.optInt("honerId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        

	//	TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));
        if(oper == 1){
        	String re = checkParam(params, new String[]{"userId", "honorUrl","type", "ownerId", "oper"});
    		if(re != null){
    			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
    			returnJSON.put("result", result);
    			returnJSON.put("errorMsg", re +"不能为空！");
    			return returnJSON.toString();
    		}
        	
        	TKindergartenAlbum album = new TKindergartenAlbum();
        	album.setType(type);
			
			String currentClass = null;
			List<TKindergartenAlbum> albumList = null ;
			Integer gradeId = null ;
			Integer kindergartenId = null;
			if(type == 1){
				
				gradeId = ownerId;
				//班级
				TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, ownerId);
				album.setShcoolId(grade.getKindergartenId());
				album.setGradeId(ownerId);
				currentClass =  GradeNameUtil.getGradeName(grade);
				album.setCurrentGradeName(currentClass);
				albumList = baseDAO.getGenericByHql("from TKindergartenAlbum where shcoolId=? and gradeId = ? and currentGradeName = ? and student is null", album.getShcoolId(), album.getGradeId(), currentClass);
				kindergartenId = grade.getKindergartenId();
			}else if(type == 2){
				//学生
				TKindergartenStudent student = baseDAO.get(TKindergartenStudent.class, ownerId);
				album.setShcoolId( student.getKindergartenId());
				album.setGradeId(student.getGradeId());
				album.setStudent(ownerId);
				TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, student.getGradeId());
				currentClass = GradeNameUtil.getGradeName(grade);
				album.setCurrentGradeName(currentClass);
				albumList = baseDAO.getGenericByHql("from TKindergartenAlbum where shcoolId=? and gradeId = ? and student =? and currentGradeName = ?", album.getShcoolId(), album.getGradeId(), album.getStudent(), currentClass);
				gradeId = student.getGradeId();
				kindergartenId = grade.getKindergartenId();
			}
			TKindergartenInfo kinder = baseDAO.get(TKindergartenInfo.class, kindergartenId);
			//查询是否已经有相册生成
			String imgUrl= SystemConfig.getValue("img.http.url");
			honorUrl = honorUrl.replaceAll(imgUrl, "");
			if(albumList == null || albumList.size() == 0){
				album.setAlbumDesc("");
				album.setAlbumName(currentClass);
				album.setAlbumUrl(kinder.getLogo());
				album.setCreateTime(DateUtil.now());
				album.setCreateUser(userId);
				album.setGradeId(gradeId);
				baseDAO.save(album);
			}else{
				album = albumList.get(0);
			}
			
			String[] honorUrls = honorUrl.split(",");
			for(String url : honorUrls){
				TKindergartenHonor honor = new TKindergartenHonor();
	            honor.setCategory(1);
	            honor.setCreateTime(DateUtils.currentDate());
	            honor.setCreateUser(userId);
	            honor.setName(honorName);
	            honor.setType(type);
	            honor.setPhotoUrl(url);
	            honor.setOwnerId(ownerId+"");
	            honor.setCommentNum(0);
	            honor.setDigNum(0);
	            honor.setKindergartenId(kindergartenId);
	            honor.setGradeId(gradeId);
	            honor.setAlbumId(album.getId());
	            baseDAO.save(honor);
			}
            
            result.put("succMsg", "新增成功");
    		returnJSON.put("result", result);
    		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
    		returnJSON.put("errorMsg", "");
    		return returnJSON.toString();
            
        }else if(oper == 2){
        	//删除
        	if(honerId == null || honerId == 0){
        		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
    			returnJSON.put("result", result);
    			returnJSON.put("errorMsg", "honerId不能为空！");
    			return returnJSON.toString();
        	} 
        	baseDAO.delete(TKindergartenHonor.class, honerId);
        	result.put("succMsg", "删除成功");
     		returnJSON.put("result", result);
     		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
     		returnJSON.put("errorMsg", "");
     		return returnJSON.toString();
        }else if(oper == 3){
        	if(honerId == null || honerId == 0){
        		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
    			returnJSON.put("result", result);
    			returnJSON.put("errorMsg", "honerId不能为空！");
    			return returnJSON.toString();
        	} 
        	if(honorName == null|| "".equals(honorName)){
        		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
    			returnJSON.put("result", result);
    			returnJSON.put("errorMsg", "名称不能为空！");
    			return returnJSON.toString();
        	}
        	
        	TKindergartenHonor honer = baseDAO.get(TKindergartenHonor.class, honerId);
        	honer.setName(honorName);
        	baseDAO.update(honer);
        	result.put("succMsg", "编辑成功");
     		returnJSON.put("result", result);
     		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
     		returnJSON.put("errorMsg", "");
     		return returnJSON.toString();
        }else{
        	returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "无效的操作类型！");
			return returnJSON.toString();
        }

		
	}

	private String checkParam(JSONObject param, String[] keys){

	    for(String key : keys){
	        if(param.get(key) == null || "".equals(param.get(key).toString())){
	            return key;
            }
        }
        return null;
    }
}
