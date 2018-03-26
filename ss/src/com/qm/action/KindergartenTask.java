package com.qm.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.DateUtil;
import com.qm.entities.KindergartenPhoto;
import com.qm.entities.KindergartenTaskInfo;
import com.qm.entities.KindergartenTeacher;
import com.qm.entities.PrizeGrantInfo;
import com.qm.service.KindergartenPhotoService;
import com.qm.service.KindergartenTaskInfoService;
import com.qm.service.KindergartenTeacherService;
import com.qm.service.PrizeGrantService;
import com.qm.shop.Constant;

@RestController
@RequestMapping("/kindergarten/task")
public class KindergartenTask extends FtpImgDownUploadAction {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenTaskInfoService kindergartenTaskInfoService;
	@Autowired
	private PrizeGrantService prizeGrantService;
	@Autowired
	private KindergartenPhotoService kindergartenPhotoService;
	@Autowired
	private KindergartenTeacherService kindergartenTeacherService;
	
	@RequestMapping("/teacherList")
    public String teacherList(Integer kindergartenId,String name, int limit, int offset, HttpServletRequest request) {

		KindergartenTaskInfo info = new KindergartenTaskInfo();
        info.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
        info.setTeacherName(name);
        List<Integer> userTypes = new ArrayList<Integer>();
        userTypes.add(4);
        userTypes.add(5);
        userTypes.add(6);
        info.setUserTypes(userTypes);
        info.setOffset(offset);
        info.setPageSize(limit);
        List<KindergartenTaskInfo> kindergartenInfoList = kindergartenTaskInfoService.queryList(info);
        int total = kindergartenTaskInfoService.getTotal(info);
        JSONObject json = new JSONObject();
        json.put("rows",kindergartenInfoList );
        json.put("total", total);
        return json.toJSONString();
    }
	@RequestMapping("/managerList")
	public String managerList(Integer kindergartenId,String name, int limit, int offset, HttpServletRequest request) {
		
		KindergartenTaskInfo info = new KindergartenTaskInfo();
		info.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
		info.setTeacherName(name);
		info.setUserType(3);
		info.setOffset(offset);
		info.setPageSize(limit);
		List<KindergartenTaskInfo> kindergartenInfoList = kindergartenTaskInfoService.queryList(info);
		int total = kindergartenTaskInfoService.getTotal(info);
		JSONObject json = new JSONObject();
		json.put("rows",kindergartenInfoList );
		json.put("total", total);
		return json.toJSONString();
	}
	@RequestMapping("/leaderList")
	public String leaderList(Integer kindergartenId,String name, int limit, int offset, HttpServletRequest request) {
		
		KindergartenTaskInfo info = new KindergartenTaskInfo();
		info.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
		info.setTeacherName(name);
		List<Integer> userTypes = new ArrayList<Integer>();
        userTypes.add(1);
        userTypes.add(2);
        info.setUserTypes(userTypes);
		info.setOffset(offset);
		info.setPageSize(limit);
		List<KindergartenTaskInfo> kindergartenInfoList = kindergartenTaskInfoService.queryList(info);
		int total = kindergartenTaskInfoService.getTotal(info);
		JSONObject json = new JSONObject();
		json.put("rows",kindergartenInfoList );
		json.put("total", total);
		return json.toJSONString();
	}
	
	/**
	 * 发放奖励
	 * */
	@RequestMapping("/send")
	public String send(int id) {
		
		
		KindergartenTaskInfo info = kindergartenTaskInfoService.selectByPrimary(id);
		info.setIsSend(0);
		String time = DateUtil.getNowDate();
		info.setSendTime(time);
		info.setUpdateTime(time);
		int i = kindergartenTaskInfoService.update(info);
		JSONObject json = new JSONObject();
		if(i > 0){
			
			PrizeGrantInfo prize = new PrizeGrantInfo();
			prize.setCreateTime(time);
			prize.setKindergartenId(info.getKindergartenId());
			prize.setPrizeDate(time);
			prize.setTeacherId(info.getTeacherId());
			prize.setUserTel(info.getRenewPhone());
			prizeGrantService.insertSelected(prize);
			
			json.put("success",true );
			json.put("message", "更新成功");
		}else{
			json.put("success",false );
			json.put("message", "更新失败");
		}
		return json.toJSONString();
	}
	/**
	 * 查看明细
	 * */
	@RequestMapping("/viewDetails")
	public String viewDetails(int teacherId, String taskDate, int limit, int offset) {
		KindergartenTeacher teacher = kindergartenTeacherService.selectByPrimaryKey(teacherId);
		KindergartenPhoto photo = new KindergartenPhoto();
		photo.setCreateUser(teacher.getUserId()+"");
		photo.setCreateTime(taskDate);
		photo.setLimit(limit);
		photo.setOffset(offset);
		List<KindergartenPhoto> photoList = kindergartenPhotoService.queryListByTeacherIdAndDate(photo);
		String imgUrlPre = Constant.imgPrefix;
		String resUrlPre = Constant.resPrefix;
		for(KindergartenPhoto p : photoList){
			if(p.getCategory() == 1){
				//照片
				p.setPhotoUrl(imgUrlPre + p.getPhotoUrl());
			}else if(p.getVideoUrl() != null && !"".equals(p.getVideoUrl())){
				p.setVideoUrl(resUrlPre + p.getVideoUrl());
			}
		}
		int total = kindergartenPhotoService.getTotal(photo);
		JSONObject json = new JSONObject();
		json.put("rows",photoList );
		json.put("total", total);
		return json.toJSONString();
	}

}
