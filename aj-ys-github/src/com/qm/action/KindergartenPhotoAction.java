package com.qm.action;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.DateUtil;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenGrade;
import com.qm.entities.KindergartenInfo;
import com.qm.entities.KindergartenPhoto;
import com.qm.entities.KindergartenStudent;
import com.qm.service.KindergartenGradeService;
import com.qm.service.KindergartenPhotoService;
import com.qm.service.KindergartenService;
import com.qm.service.KindergartenStudentService;
import com.qm.shop.Constant;
import com.qm.shop.customer.action.ShopCustomerAction;
import com.qm.shop.customer.service.ShopCustomerService;

@RestController
@RequestMapping("/kindergarten/photo")
public class KindergartenPhotoAction extends FtpImgDownUploadAction{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenStudentService kindergartenStudentService;
	@Autowired
	private KindergartenGradeService kindergartenGradeService;
	@Autowired
	private ShopCustomerService shopCustomerService;
	@Autowired
	private KindergartenService kindergartenService;
	@Autowired
	private KindergartenPhotoService kindergartenPhotoService;
	
	
	@RequestMapping("/list")
	public String list(Integer kindergartenId, Integer gradeId, String name, Integer offset, Integer limit){
		
		KindergartenStudent student = new KindergartenStudent();
		student.setKindergartenId(kindergartenId);
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
	public String queryPhotoByOwerId(String owerId){
		
		List<KindergartenPhoto> photoList = kindergartenPhotoService.queryPhotoByOwerId(owerId);
		for(KindergartenPhoto photo : photoList){
			if(photo.getPhotoUrl() != null && !"".equals(photo.getPhotoUrl())){
				photo.setPhotoUrl(Constant.imgPrefix +photo.getPhotoUrl());
			}
			if(photo.getVideoUrl() != null && !"".equals(photo.getVideoUrl())){
				
				photo.setVideoUrl(Constant.resPath + photo.getVideoUrl());
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
	
	
	
}
