package com.qm.action;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.action.FtpImgDownUploadAction;
import com.qm.entities.KindergartenGrade;
import com.qm.entities.KindergartenHonor;
import com.qm.entities.KindergartenStudent;
import com.qm.mapper.KindergartenHonorMapper;
import com.qm.service.KindergartenGradeService;
import com.qm.service.KindergartenStudentService;
import com.qm.shop.Constant;

@RestController
@RequestMapping("/kindergarten/honor")
public class KindergartenHonorAction extends FtpImgDownUploadAction{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenStudentService kindergartenStudentService;
	@Autowired
	private KindergartenGradeService kindergartenGradeService;
	@Autowired
	private KindergartenHonorMapper kindergartenHonorMapper;
	
	
	@RequestMapping("/list")
	public String list(Integer kindergartenId, Integer gradeId, String name, Integer offset, Integer limit){
		
		KindergartenStudent student = new KindergartenStudent();
		student.setKindergartenId(kindergartenId);
		student.setGradeId(gradeId);
		student.setName(name);
		student.setOffset(offset);
		student.setPageSize(limit);
		List<KindergartenStudent> list = kindergartenStudentService.queryListWithHonorNum(student);
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
		
		KindergartenHonor p = new KindergartenHonor();
		p.setType(type);
		p.setOwnerId(owerId);
		List<KindergartenHonor> photoList = kindergartenHonorMapper.selectByCondition(p);
		for(KindergartenHonor photo : photoList){
			if(photo.getPhotoUrl() != null && !"".equals(photo.getPhotoUrl())){
				
				String subUrl =  photo.getPhotoUrl().substring(photo.getPhotoUrl().lastIndexOf("/") + 1);
				if(subUrl.startsWith("s")){
					photo.setPhotoUrl(Constant.imgPrefix +photo.getPhotoUrl().substring(0, photo.getPhotoUrl().lastIndexOf("/") + 1) + subUrl.substring(1));
				}else{
					photo.setPhotoUrl(Constant.imgPrefix +photo.getPhotoUrl());
				}
				
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
	public String alterPhotoName(KindergartenHonor photo){
		
		JSONObject json = new JSONObject();
		json.put("success", false);
		if(photo != null && photo.getId() != null && photo.getName() != null &&  !"".equals(photo.getName())){
			
			int i = kindergartenHonorMapper.updateByPrimaryKeySelective(photo);
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
			
			int i = kindergartenHonorMapper.deleteByPrimaryKey(id);
			if(i > 0){
				json.put("success", true);
			}
		}
		
		return json.toJSONString();
	}
}
