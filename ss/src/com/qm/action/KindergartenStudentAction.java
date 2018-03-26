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
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.DateUtil;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenGrade;
import com.qm.entities.KindergartenInfo;
import com.qm.entities.KindergartenStudent;
import com.qm.service.KindergartenGradeService;
import com.qm.service.KindergartenService;
import com.qm.service.KindergartenStudentService;
import com.qm.shop.Constant;

@RestController
@RequestMapping("/kindergarten/student")
public class KindergartenStudentAction extends FtpImgDownUploadAction{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenStudentService kindergartenStudentService;
	@Autowired
	private KindergartenGradeService kindergartenGradeService;
	@Autowired
	private KindergartenService kindergartenService;
	@Autowired
	private GenericDAO baseDAO;

	@RequestMapping("/list")
	public String list(Integer gradeId, String name, Integer offset, Integer limit, HttpServletRequest request){
		
		KindergartenStudent student = new KindergartenStudent();
		student.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
		student.setGradeId(gradeId);
		student.setName(name);
		student.setOffset(offset);
		student.setPageSize(limit);
		List<KindergartenStudent> list = kindergartenStudentService.queryList(student);
		for(KindergartenStudent s : list){
			if(s.getPhoto() != null && !"".equals(s.getPhoto())){
				s.setPhoto(Constant.imgPrefix + s.getPhoto());
			}
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
	@RequestMapping("/add")
	public String add(KindergartenStudent student,@RequestParam(value = "logoImg") MultipartFile logo, HttpServletRequest request){
		
		UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);

		JSONObject json = new JSONObject();
		String icon = null;
        if(logo != null && !logo.isEmpty()){

            try {
                icon = fileUpload("shoplogo", (CommonsMultipartFile)logo);
            } catch (Exception e) {
                logger.info("新增学生时，上传头像文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        student.setPhoto(icon);
        student.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
        student.setCreateUser(userExtForm.getAccount());
        student.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
		try {
			int i = kindergartenStudentService.save(student);
			 if(i > 0){
		            json.put("success",true );
		            json.put("message", "新增成功");
		            
		            //p判断该用户的手机号是否注册过 亲脉 帐号，如果没有吗则创建一个
		            Map<String, Object> userInfo = null;
		            String sql = "SELECT id FROM t_user  WHERE  usertel = ?";
		    		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new Object[]{student.getParentsTel()});
		    		if(list != null && list.size() > 0){
		    			userInfo = list.get(0);
		    		}
		            
	        		String password = 100000 +new Random().nextInt(899999) +"";
	        		
	        		if(userInfo == null || userInfo.isEmpty()){
	        			try {
	        				//该手机号没注册， 后台给注册一个
	            			boolean b = registAppUser(student.getParentsTel(), "", password);
	            			if(b){
	            				
	            				KindergartenInfo kInfo = kindergartenService.selectByPrimaryKey(student.getKindergartenId());
	            				String content = kInfo.getName()+"幼儿园已经在“亲脉”系统中添加您的小孩成为该幼儿园的学生。后台自动为您生成登录帐号：账号"+student.getParentsTel()+"，密码"+password+"，您可以下载亲脉APP进入生活我的生活中查看赠送服务亲脉下载地址：http://qm.dbfish.net/d";
	            				boolean sendState = KindergartenTeacherAction.sendMsg(student.getParentsTel(), content);
	            				logger.info("亲脉后台系统添加教师成功后，由于该教师还未注册过亲脉，所以自动生成帐号：" +student.getParentsTel() +", 注册结果：" +sendState );
	            			}
						} catch (Exception e) {
							logger.info("亲脉后台系统添加教师成功后，由于该教师还未注册过亲脉，所以自动生成帐号：但是发生异常：" + e);
						}
	        		}
		        }else{
		        	json.put("success",true );
		            json.put("message", "新增失败");
		        }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("新增学生信息异常：" + e);
			json.put("success",true );
            json.put("message", "新增异常："+e.getMessage());
		}
       
		return json.toString();
	}
	@RequestMapping("/del")
	public String del(Integer id){
		
		JSONObject json = new JSONObject();
		try {
			int i = kindergartenStudentService.deleteByPrimaryKey(id);
			if(i > 0){
				json.put("success", true);
				json.put("message", "删除成功");
			}else{
				json.put("success", false);
				json.put("message", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除学生信息时异常："+ e);
			json.put("success", false);
			json.put("message", "删除异常："+e.getMessage());
		}
		
		return json.toString();
	}
	@RequestMapping("/find")
	public String find(Integer id){
		JSONObject json = new JSONObject();
		try {
			KindergartenStudent  student = kindergartenStudentService.selectByPrimaryKey(id);
			if(student.getPhoto() != null && !"".equals(student.getPhoto())){
				student.setPhoto(Constant.imgPrefix + student.getPhoto());
			}
			json.put("success", true);
			json.put("message", student);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("初始化学生信息异常："+e);
			json.put("success", false);
			json.put("message", "初始化异常");
		}
		
		return json.toString();
	}
	
	@RequestMapping("/update")
	public String update(KindergartenStudent student,@RequestParam(value = "logoImg") MultipartFile logo, HttpServletRequest request){
		
		JSONObject json = new JSONObject();
		String icon = null;
        if(logo != null && !logo.isEmpty()){

            try {
                icon = fileUpload("shoplogo", (CommonsMultipartFile)logo);
                student.setPhoto(icon);
            } catch (Exception e) {
                logger.info("新增学生时，上传头像文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        KindergartenStudent oldInfo = kindergartenStudentService.selectByPrimaryKey(student.getId());
        
		try {
			student.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
			int i = kindergartenStudentService.update(student);
			 if(i > 0){
		            json.put("success",true );
		            json.put("message", "编辑成功");
		            
		            if(!oldInfo.getParentsTel().equals(student.getParentsTel())){
		            	 //p判断该用户的手机号是否注册过 亲脉 帐号，如果没有吗则创建一个
		            	Map<String, Object> userInfo = null;
			            String sql = "SELECT id FROM t_user  WHERE  usertel = ?";
			    		List<Map<String, Object>> list = baseDAO.getGenericBySQL(sql, new Object[]{student.getParentsTel()});
			    		if(list != null && list.size() > 0){
			    			userInfo = list.get(0);
			    		}
		        		String password = 100000 +new Random().nextInt(899999) +"";
		        		
		        		if(userInfo == null || userInfo.isEmpty()){
		        			try {
		        				//该手机号没注册， 后台给注册一个
		            			boolean b = registAppUser(student.getParentsTel(), password, "");
		            			if(b){
		            				
		            				KindergartenInfo kInfo = kindergartenService.selectByPrimaryKey(student.getKindergartenId());
		            				String content = kInfo.getName()+"幼儿园已经在“亲脉”系统中添加您的小孩成为该幼儿园的学生。后台自动为您生成登录帐号：账号"+student.getParentsTel()+"，密码"+password+"，您可以下载亲脉APP进入生活我的生活中查看赠送服务亲脉下载地址：http://qm.dbfish.net/d";
		            				boolean sendState = KindergartenTeacherAction.sendMsg(student.getParentsTel(), content);
		            				logger.info("亲脉后台系统添加教师成功后，由于该教师还未注册过亲脉，所以自动生成帐号：" +student.getParentsTel() +", 注册结果：" +sendState );
		            			}
							} catch (Exception e) {
								logger.info("亲脉后台系统添加教师成功后，由于该教师还未注册过亲脉，所以自动生成帐号：但是发生异常：" + e);
							}
		        		}
		            }
		            
		        }else{
		        	json.put("success",true );
		            json.put("message", "编辑失败");
		        }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("编辑学生信息异常：" + e);
			json.put("success",true );
            json.put("message", "编辑异常："+e.getMessage());
		}
       
		return json.toString();
	}
}
