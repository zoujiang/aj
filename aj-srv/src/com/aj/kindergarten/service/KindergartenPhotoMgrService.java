/**  
* 2015-11-17  
* author:zoujiang
*/  

package com.aj.kindergarten.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aj.kindergarten.vo.TKindergartenAlbum;
import com.aj.kindergarten.vo.TKindergartenGrade;
import com.aj.kindergarten.vo.TKindergartenInfo;
import com.aj.kindergarten.vo.TKindergartenPhoto;
import com.aj.kindergarten.vo.TKindergartenStudent;
import com.frame.core.constant.FtpConstant;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.FileZipUtil;
import com.util.GradeNameUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("kindergartenPhotoMgr")
public class KindergartenPhotoMgrService implements PublishService{

	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {
		
		JSONObject json = JSONObject.fromObject(obj);
		
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		String photoName = params.optString("photoName");
		String photoUrl = params.optString("photoUrl"); //[{"photoUrl":"", "videoUrl": "",category:1},{"photoUrl":"", "videoUrl": "",category:1}]
		String ownerId = params.optString("ownerId");
	//	String videoUrl = params.optString("videoUrl");
		int type = params.optInt("type");  //1:班级 2:个人
    //    int category = params.optInt("category");  //1:照片 2:视频
        int oper = params.optInt("oper");
        Integer photoId = params.optInt("photoId"); 
    //    int albumId = params.optInt("albumId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);
		JSONObject result = new JSONObject();

        

		//TUser user = baseDAO.get(TUser.class, Integer.parseInt( userId));
        if(oper == 1){
        	String re = checkParam(params, new String[]{"userId", "photoUrl", "ownerId","type", "oper"});
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
				
				gradeId = Integer.parseInt(ownerId);
				//班级
				TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, Integer.parseInt(ownerId));
				kindergartenId = grade.getKindergartenId();
				album.setShcoolId(grade.getKindergartenId());
				album.setGradeId(Integer.parseInt(ownerId));
				currentClass = GradeNameUtil.getGradeName(grade);
				album.setCurrentGradeName(currentClass);
				albumList = baseDAO.getGenericByHql("from TKindergartenAlbum where shcoolId=? and gradeId = ? and currentGradeName = ? and student is null", album.getShcoolId(), album.getGradeId(), currentClass);
				
			}else if(type == 2){
				//学生
				TKindergartenStudent student = baseDAO.get(TKindergartenStudent.class, Integer.parseInt(ownerId));
				album.setShcoolId( student.getKindergartenId());
				album.setGradeId(student.getGradeId());
				album.setStudent(Integer.parseInt(ownerId));
				TKindergartenGrade grade = baseDAO.get(TKindergartenGrade.class, student.getGradeId());
				kindergartenId = grade.getKindergartenId();
				currentClass = GradeNameUtil.getGradeName(grade);
				album.setCurrentGradeName(currentClass);
				albumList = baseDAO.getGenericByHql("from TKindergartenAlbum where shcoolId=? and gradeId = ? and student =? and currentGradeName = ?", album.getShcoolId(), album.getGradeId(), album.getStudent(), currentClass);
				gradeId = student.getGradeId();
			}
			TKindergartenInfo kinder = baseDAO.get(TKindergartenInfo.class, kindergartenId);
			//查询是否已经有相册生成
			JSONArray ja = null;
			String imgUrl= SystemConfig.getValue("img.http.url");
			photoUrl = photoUrl.replaceAll(imgUrl, "");
			try {
				ja = JSONArray.fromObject(photoUrl);
				if(ja.size() ==0){
					returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
					returnJSON.put("result", result);
					returnJSON.put("errorMsg", "photoUrl不能为空");
					return returnJSON.toString();
				}
			} catch (Exception e) {
				returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
				returnJSON.put("result", result);
				returnJSON.put("errorMsg", "photoUrl格式不对！");
				return returnJSON.toString();
			}
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
			
			for(int i=0;i < ja.size(); i ++){
				JSONObject jo = JSONObject.fromObject(ja.get(i));
				TKindergartenPhoto photo = new TKindergartenPhoto();
	            photo.setAlbumId(album.getId());
	            photo.setCategory(jo.optInt("category"));
	            photo.setCommentNum(0);
	            photo.setCreateTime(DateUtils.currentDate());
	            photo.setCreateUser(userId);
	            photo.setDigNum(0);
	            photo.setName(photoName);
	            photo.setOwnerId(ownerId);
	            photo.setPhotoUrl(jo.optString("photoUrl"));
	            photo.setType(type);
	            photo.setVideoUrl(jo.optString("videoUrl"));
	            photo.setGradeId(gradeId);
	            photo.setKindergartenId(kindergartenId);
	            baseDAO.save(photo);
			}
            
          //生成压缩包
			createZipPackage(album.getId(), type, ownerId);
			result.put("succMsg", "新增成功");
			returnJSON.put("result", result);
			returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
			returnJSON.put("errorMsg", "");
			return returnJSON.toString();
        }else if(oper == 2){
        	
        	String re = checkParam(params, new String[]{"userId", "photoId", "oper"});
    		if(re != null){
    			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
    			returnJSON.put("result", result);
    			returnJSON.put("errorMsg", re +"不能为空！");
    			return returnJSON.toString();
    		}
        	
        	if(photoId == null || photoId == 0){
        		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
    			returnJSON.put("result", result);
    			returnJSON.put("errorMsg", "photoId不能为空");
    			return returnJSON.toString();
        	}
        	TKindergartenPhoto photo = baseDAO.get(TKindergartenPhoto.class, photoId);
        	//生成压缩包
			createZipPackage(photo.getAlbumId(), type, ownerId);
			baseDAO.delete(TKindergartenPhoto.class, photoId);
        	result.put("succMsg", "删除成功");
    		returnJSON.put("result", result);
    		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
    		returnJSON.put("errorMsg", "");
    		return returnJSON.toString();
        }else if(oper == 3){
        	if(photoId == null || photoId == 0){
        		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
    			returnJSON.put("result", result);
    			returnJSON.put("errorMsg", "photoId不能为空");
    			return returnJSON.toString();
        	}
        	if(photoName == null || "".equals(photoName)){
        		returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
        		returnJSON.put("result", result);
        		returnJSON.put("errorMsg", "名称不能为空");
        		return returnJSON.toString();
        	}
        	TKindergartenPhoto photo = baseDAO.get(TKindergartenPhoto.class, photoId);
        	photo.setName(photoName);
        	baseDAO.update(photo);
        	result.put("succMsg", "编辑成功");
    		returnJSON.put("result", result);
    		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
    		returnJSON.put("errorMsg", "");
    		return returnJSON.toString();
        }else{
        	
        	returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("result", result);
			returnJSON.put("errorMsg", "无效的操作类型");
			return returnJSON.toString();
        }

		
	}

	private void createZipPackage(final Integer albumId, final Integer type, final String ownerId) {
		new Thread(){

			@Override
			public void run() {
				TKindergartenPhoto photo = new TKindergartenPhoto();
			//	photo.setType(type);
			//	photo.setOwnerId(ownerId);
				photo.setAlbumId(albumId);
				List<TKindergartenPhoto> photoList =  baseDAO.getGenericByHql("from TKindergartenPhoto where albumId = ?", albumId);
				List<String> urlList = new ArrayList<String>();
				for(TKindergartenPhoto p : photoList){
					if(p.getCategory() == 1){
						urlList.add(p.getPhotoUrl());
					}else if(p.getCategory() == 2){
						urlList.add(p.getVideoUrl());
					}
				}
				String to ="/kindergarten/Album/"+ albumId +".zip" ;
				String targetUrl = SystemConfig.getValue("res.http.url") + to ;
				String[] arr = {};
				delOldZipFile(to);
				FileZipUtil.zip(urlList.toArray(arr), targetUrl);
				
				TKindergartenAlbum ab = baseDAO.get(TKindergartenAlbum.class, albumId);
				ab.setDownloadUrl(to);
				ab.setDownloadSecret(RandomGUID.getRandomString(4));
				baseDAO.update(ab);
			}

		}.start();
		
	}
	private void delOldZipFile(String remoteFilePath) {
		String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
		String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
		String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
		String port = (String) SystemConfig.getValue(FtpConstant.PORT);
		String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
		FtpUtil ftp = new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
		try {
			ftp.login();
			ftp.delFile(remoteFilePath);
		} catch (Exception e) {
			e.printStackTrace();
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
