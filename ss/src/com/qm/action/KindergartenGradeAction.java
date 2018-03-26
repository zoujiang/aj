package com.qm.action;

import java.util.Date;
import java.util.List;

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
import com.qm.service.KindergartenGradeService;
import com.qm.shop.Constant;

@RestController
@RequestMapping("/kindergarten/grade")
public class KindergartenGradeAction extends FtpImgDownUploadAction{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private KindergartenGradeService kindergartenGradeService;

	@RequestMapping("/add")
	public String add(String series, String classNo, Integer firstTeacher, Integer secondTeacher, Integer nurse, 
			@RequestParam(value = "logoImg") MultipartFile logo, String declaration, String rule, HttpServletRequest request ){
		
        UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);

        KindergartenGrade grade = new KindergartenGrade();
        JSONObject json = new JSONObject();
        
		try {
			String icon = null;
			if(logo != null && !logo.isEmpty()){

			    try {
			        icon = fileUpload("gradelogo", (CommonsMultipartFile)logo);
			        grade.setLogo(icon);
			    } catch (Exception e) {
			        logger.info("新增幼儿园班级时，上传LOGO文件出错："+e);
			        json.put("success", false);
			        json.put("message", "上传图片失败");
			        return json.toString();
			    }
			}
			grade.setClassNo(classNo);
			grade.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
			grade.setCreateUser(userExtForm.getId());
			grade.setDeclaration(declaration);
			grade.setFirstTeacher(firstTeacher);
			grade.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
			grade.setNurse(nurse);
			grade.setSeries(series);
			grade.setSecondTeacher(secondTeacher);
			char[] ruleChar = "00000".toCharArray();
			if(rule != null && !"".equals(rule)){
				String[] splitRuleString = rule.split(",");
				for(String rString : splitRuleString){
					if("小小班".equals(rString.trim())){
						ruleChar[0] ='1';
					}else if("小班".equals(rString.trim())){
						ruleChar[1] ='1';
					}else if("中班".equals(rString.trim())){
						ruleChar[2] ='1';
					}else if("大班".equals(rString.trim())){
						ruleChar[3] ='1';
					}else if("大大班".equals(rString.trim())){
						ruleChar[4] ='1';
					}
				}
			}
			grade.setRule(new String(ruleChar));
			
			int i = kindergartenGradeService.save(grade);
			if(i > 0){
				 json.put("success",true );
			     json.put("message", "新增成功");
			}else{
			    json.put("success",false );
			    json.put("message", "新增失败");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.info("新增班级时出现异常："+e);
			  json.put("success",false );
			  json.put("message", "新增失败:"+e.getMessage());
		}
		return json.toString();
	}
	@RequestMapping("/list")
	public String list(String series, Integer limit, Integer offset, HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			KindergartenGrade grade = new KindergartenGrade();
			grade.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
			grade.setSeries(series);
			grade.setPageSize(limit);
			grade.setOffset(offset);
			List<KindergartenGrade> gradeList = kindergartenGradeService.queryList(grade);
			int total = kindergartenGradeService.getTotal(grade);
			json.put("rows", gradeList);
			json.put("total", total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json.toString();
		
	}
	
	@RequestMapping("/del")
    public String del(Integer id){
        JSONObject json = new JSONObject();
        try {
            int i = kindergartenGradeService.deleteByPrimaryKey(id);
            if(i > 0){
                json.put("success",true );
                json.put("message", "删除成功");
            }else{
                json.put("success",false );
                json.put("message", "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("删除幼儿园班级异常："+ e);
            json.put("success",false );
            json.put("message", "删除异常："+e.getMessage());
        }

        return json.toJSONString();
    }
	
	@RequestMapping("/find")
	public String find(Integer id){
		JSONObject json = new JSONObject();
		try {
			KindergartenGrade grade = kindergartenGradeService.selectByPrimaryKey(id);
			if(grade.getLogo() != null && !"".equals(grade.getLogo())){
				grade.setLogo(Constant.imgPrefix + grade.getLogo());
			}
			String ruleString = "";
			String[] ruleNames = {"小小班","小班","中班","大班","大大班"};
			String rule = grade.getRule();
			char[] ruleChar = rule.toCharArray();
			for(int i= 0 ; i< ruleChar.length ; i++){
				if(ruleChar[i] == '1'){
					ruleString += ruleNames[i] +";";
				}
			}
			grade.setRule(ruleString);
			
			json.put("message", grade);
			json.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("初始化班级信息异常："+e);
			json.put("message", "初始化班级信息失败");
			json.put("success", false);
		}
		
		return json.toString();
		
	}
	@RequestMapping("/update")
	public String update(Integer id, String series, String classNo, Integer firstTeacher, Integer secondTeacher, Integer nurse, 
			@RequestParam(value = "logoImg") MultipartFile logo, String declaration, String rule, HttpServletRequest request ){
		

        KindergartenGrade grade = new KindergartenGrade();
        JSONObject json = new JSONObject();
        grade.setId(id);
		try {
			String icon = null;
			if(logo != null && !logo.isEmpty()){

			    try {
			        icon = fileUpload("gradelogo", (CommonsMultipartFile)logo);
			        grade.setLogo(icon);
			    } catch (Exception e) {
			        logger.info("编辑幼儿园班级时，上传LOGO文件出错："+e);
			        json.put("success", false);
			        json.put("message", "上传图片失败");
			        return json.toString();
			    }
			}
			grade.setClassNo(classNo);
			grade.setDeclaration(declaration);
			grade.setFirstTeacher(firstTeacher);
			grade.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(com.frame.core.constant.Constant.LOGIN_SHOP_ID).toString()));
			grade.setNurse(nurse);
			grade.setSeries(series);
			grade.setSecondTeacher(secondTeacher);
			char[] ruleChar = "00000".toCharArray();
			if(rule != null && !"".equals(rule)){
				String[] splitRuleString = rule.split(",");
				for(String rString : splitRuleString){
					if("小小班".equals(rString.trim())){
						ruleChar[0] ='1';
					}else if("小班".equals(rString.trim())){
						ruleChar[1] ='1';
					}else if("中班".equals(rString.trim())){
						ruleChar[2] ='1';
					}else if("大班".equals(rString.trim())){
						ruleChar[3] ='1';
					}else if("大大班".equals(rString.trim())){
						ruleChar[4] ='1';
					}
				}
			}
			grade.setRule(new String(ruleChar));
			
			int i = kindergartenGradeService.update(grade);
			if(i > 0){
				 json.put("success",true );
			     json.put("message", "编辑成功");
			}else{
			    json.put("success",false );
			    json.put("message", "编辑失败");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.info("新增班级时出现异常："+e);
			  json.put("success",false );
			  json.put("message", "编辑失败:"+e.getMessage());
		}
		return json.toString();
	}
}
