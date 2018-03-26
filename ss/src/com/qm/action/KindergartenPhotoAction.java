package com.qm.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.EncryptUtils;
import com.qm.entities.KindergartenAlbum;
import com.qm.entities.KindergartenGrade;
import com.qm.entities.KindergartenPhoto;
import com.qm.entities.KindergartenStudent;
import com.qm.mapper.KindergartenAlbumMapper;
import com.qm.service.KindergartenGradeService;
import com.qm.service.KindergartenPhotoService;
import com.qm.service.KindergartenService;
import com.qm.service.KindergartenStudentService;
import com.qm.shop.Constant;

@RestController
@RequestMapping("/kindergarten/photo")
public class KindergartenPhotoAction extends FtpImgDownUploadAction{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenStudentService kindergartenStudentService;
	@Autowired
	private KindergartenGradeService kindergartenGradeService;
	@Autowired
	private KindergartenAlbumMapper kindergartenAlbumMapper;
	@Autowired
	private KindergartenService kindergartenService;
	@Autowired
	private KindergartenPhotoService kindergartenPhotoService;
	
	
	@RequestMapping("/list")
	public String list(Integer kindergartenId, Integer gradeId, String name, Integer offset, Integer limit, HttpServletRequest request){
		
		KindergartenStudent student = new KindergartenStudent();
		student.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
		student.setGradeId(gradeId);
		student.setName(name);
		student.setOffset(offset);
		student.setPageSize(limit);
		List<KindergartenStudent> list = kindergartenStudentService.queryListWithPotoNum(student);
		for(KindergartenStudent s : list){
			KindergartenGrade grade = kindergartenGradeService.selectGradeAndTeacherByPrimaryKey(s.getGradeId());
			if(grade != null){
				s.setFirstTeacherName(grade.getFirstTeacherName());
				s.setGradeName(grade.getName());
			}
		}
		int total = kindergartenStudentService.getTotal(student);
		JSONObject json = new JSONObject();
		json.put("rows", list);
		json.put("total", total);
		
		return json.toString();
	}
	
	
	@RequestMapping("/init")
	public String queryPhotoByOwerId(String owerId, Integer type){
		
		KindergartenPhoto p = new KindergartenPhoto();
		p.setType(type);
		p.setOwnerId(owerId);
		List<KindergartenPhoto> photoList = kindergartenPhotoService.selectByCondition(p);
		for(KindergartenPhoto photo : photoList){
			if(photo.getPhotoUrl() != null && !"".equals(photo.getPhotoUrl())){
				photo.setPhotoUrl(Constant.imgPrefix +photo.getPhotoUrl());
			}
			if(photo.getVideoUrl() != null && !"".equals(photo.getVideoUrl())){
				
				photo.setVideoUrl(Constant.resPrefix + photo.getVideoUrl());
			}
		}
		JSONObject json = new JSONObject();
		json.put("data", photoList);
		json.put("success", true);
		return json.toJSONString();
	}
	@RequestMapping("/alterPhotoName")
	public String alterPhotoName(KindergartenPhoto photo){
		
		JSONObject json = new JSONObject();
		json.put("success", false);
		if(photo != null && photo.getId() != null && photo.getName() != null &&  !"".equals(photo.getName())){
			
			int i = kindergartenPhotoService.update(photo);
			if(i > 0){
				json.put("success", true);
			}
		}
		
		return json.toJSONString();
	}
	@RequestMapping("/deleteFile")
	public String deleteFile(Integer id){
		
		JSONObject json = new JSONObject();
		json.put("success", false);
		if(id != null && id > 0){
			
			int i = kindergartenPhotoService.deleteByPrimary(id);
			if(i > 0){
				json.put("success", true);
			}
		}
		
		return json.toJSONString();
	}
	@RequestMapping("/albumList")
	public String albumList(Integer ownerId, Integer type){
		
		JSONObject json = new JSONObject();
		json.put("success", true);
		KindergartenAlbum ab = new KindergartenAlbum();
		ab.setType(type);
		if(type == 1){
			ab.setShcoolId(ownerId);
		}else if(type == 2){
			ab.setStudent(ownerId);
		}
		List<KindergartenAlbum> list = kindergartenAlbumMapper.selectByCondition(ab);
		for(KindergartenAlbum a : list){
			String address = a.getDownloadUrl();
			if(!"".equals(address)){
				a.setDownloadUrl(Constant.resPrefix + address);
				//生成一个
				String id_encode = EncryptUtils.getInstance().base64_encode(a.getId() + "");
				a.setShortAddress(id_encode);
			}
		}
		json.put("rows", list);
		return json.toJSONString();
	}
	@RequestMapping("/dInit")
	public String dInit(String id){
		
		JSONObject json = new JSONObject();
		json.put("success", true);
		String albumId =  EncryptUtils.getInstance().base64_decode(id);
		KindergartenAlbum ab = kindergartenAlbumMapper.selectByPrimaryKey(Integer.parseInt(albumId));
		if(ab.getAlbumUrl() != null && !"".equals(ab.getAlbumUrl())){
			ab.setAlbumUrl( Constant.imgPrefix + ab.getAlbumUrl() );
		}
		json.put("data", ab);
		return json.toJSONString();
	}
	/**
	 * 下载片源文件
	 * */
	@RequestMapping("/d")
	@ResponseBody
	public String download(String id, String accessCode) {
		
		JSONObject modelMap = new JSONObject();
		String albumId =  EncryptUtils.getInstance().base64_decode(id);
		if(albumId == null){
			modelMap.put("success", false);
			modelMap.put("message", "资源不存在");
		}else{
			
			KindergartenAlbum ab = kindergartenAlbumMapper.selectByPrimaryKey(Integer.parseInt(albumId));
			if(ab == null){
				modelMap.put("success", false);
				modelMap.put("message", "资源不存在");
			}else{
				if(ab.getDownloadSecret() != null && accessCode != null && accessCode.equals(ab.getDownloadSecret())){
					modelMap.put("success", true);
					modelMap.put("url", Constant.resPrefix + ab.getDownloadUrl());
				}else{
					modelMap.put("success", false);
					modelMap.put("message", "提取码错误");
				}
			}
		}
		return modelMap.toString();
	}
}
