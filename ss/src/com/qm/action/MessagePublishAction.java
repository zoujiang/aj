package com.qm.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.frame.core.util.DateUtil;
import com.qm.entities.KindergartenStudent;
import com.qm.entities.KindergartenTeacher;
import com.qm.entities.MessagePublishInfo;
import com.qm.entities.UserInfo;
import com.qm.service.KindergartenStudentService;
import com.qm.service.KindergartenTeacherService;
import com.qm.service.MessagePublishService;
import com.qm.service.UserInfoService;

@RestController
@RequestMapping("/msgpublish")
public class MessagePublishAction {

	@Autowired
	private MessagePublishService messagePublishService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private KindergartenTeacherService kindergartenTeacherService;
	@Autowired
	private KindergartenStudentService kindergartenStudentService;
	
	@RequestMapping("/list")
	public String list(Integer offset, Integer limit){
		
		JSONObject json = new JSONObject();
		
		MessagePublishInfo info = new MessagePublishInfo();
		info.setOffset(offset);
		info.setLimit(limit);
		List<MessagePublishInfo> list = messagePublishService.queryList(info);
		int total = messagePublishService.getTotal(info);
		
		json.put("rows", list);
		json.put("total", total);
		return json.toString();
		
	}
	
	@RequestMapping("/send")
	public String send(MessagePublishInfo info, String sendTimeStr){
		
		JSONObject json = new JSONObject();
		info.setCreateTime(DateUtil.dateFromat(new Date(), DateUtil.DATE_TIME_PATTERN2));
		info.setStatus(0);
		if(sendTimeStr != null){
			Date  sendTimeDate = DateUtil.convertStringToDate(sendTimeStr, DateUtil.DATE_TIME_PATTERN2);
			info.setSendTime((int)(sendTimeDate.getTime()/1000));
		}
		
		int i = messagePublishService.save(info);
		if(i > 0){
			json.put("success", true);
			json.put("message", "保存成功");
		}else{
			json.put("success", false);
			json.put("message", "保存失败");
		}
		return json.toString();
		
	}
	@RequestMapping("/del")
	public String del(Integer id){
		
		JSONObject json = new JSONObject();
		
		
		int i = messagePublishService.delByPrimary(id);
		if(i > 0){
			json.put("success", true);
			json.put("message", "删除成功");
		}else{
			json.put("success", false);
			json.put("message", "删除失败");
		}
		return json.toString();
		
	}
	
	@RequestMapping("/queryUser")
	public String queryUser(Integer  type, Integer offset, Integer limit){
		
		JSONObject json = new JSONObject();
		int total = 0 ;
		JSONArray array = new JSONArray();
		if(type == 1){
			//用户
			UserInfo user = new UserInfo();
			user.setOffset(offset);
			user.setLimit(limit);
			user.setIsValid(0);
			List<UserInfo> list = userInfoService.queryForPage(user);
			for(UserInfo info : list){
				JSONObject jo = new JSONObject();
				jo.put("nick", info.getNickName());
				jo.put("tel", info.getUsertel());
				array.add(jo);
			}
			 total = userInfoService.getTotal(user);
		}else if(type == 2){
			//教师
			KindergartenTeacher t = new KindergartenTeacher();
			t.setOffset(offset);
			t.setLimit(limit);
			List<KindergartenTeacher> list = kindergartenTeacherService.queryList(t);
			 total = kindergartenTeacherService.getTotal(t);
			 for(KindergartenTeacher info : list){
				JSONObject jo = new JSONObject();
				jo.put("nick", info.getName());
				jo.put("tel", info.getTel());
				array.add(jo);
			 }
		}else if(type ==3){
			//家长
			KindergartenStudent s = new KindergartenStudent();
			s.setOffset(offset);
			s.setLimit(limit);
			List<KindergartenStudent> list = kindergartenStudentService.queryList(s);
			 total = kindergartenStudentService.getTotal(s);
			 for(KindergartenStudent info : list){
				JSONObject jo = new JSONObject();
				jo.put("nick", info.getParentsName());
				jo.put("tel", info.getParentsTel());
				array.add(jo);
			 }
		}
		json.put("rows", array);
		json.put("total", total);
		return json.toString();
		
	}
}
