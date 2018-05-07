package com.qm.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.DateUtil;
import com.frame.core.util.ExportExcelUtils;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenDailyStatistics;
import com.qm.entities.KindergartenGrade;
import com.qm.entities.KindergartenInfo;
import com.qm.entities.KindergartenStudent;
import com.qm.entities.KindergartenTeacher;
import com.qm.mapper.KindergartenDailyStatisticsMapper;
import com.qm.service.KindergartenGradeService;
import com.qm.service.KindergartenService;
import com.qm.service.KindergartenStudentService;
import com.qm.service.KindergartenTeacherService;
import com.qm.shop.Constant;

@Controller
@RequestMapping("/kindergarten")
@Scope("prototype")
public class KindergartenInfoAction extends FtpImgDownUploadAction {

  private Logger logger = LoggerFactory.getLogger(KindergartenInfoAction.class);
    @Autowired
    private KindergartenService kindergartenService;
    @Autowired
    private KindergartenGradeService kindergartenGradeService;
    @Autowired
    private KindergartenTeacherService kindergartenTeacherService;
    @Autowired
    private KindergartenStudentService kindergartenStudentService;
    @Autowired
    private KindergartenDailyStatisticsMapper kindergartenDailyStatisticsMapper;

    @RequestMapping("/kindergarten/list")
    @ResponseBody
    public String list(String name, int limit, int offset) {

        KindergartenInfo info = new KindergartenInfo();
        info.setName(name);
        info.setOffset(offset);
        info.setPageSize(limit);
        List<KindergartenInfo> kindergartenInfoList = kindergartenService.queryList(info);
        int total = kindergartenService.getTotal(info);
        JSONObject json = new JSONObject();
        json.put("rows",kindergartenInfoList );
        json.put("total", total);
        return json.toJSONString();
    }
    @RequestMapping("/kindergarten/add")
    @ResponseBody
    public String add(KindergartenInfo info, @RequestParam(value = "logoImg") MultipartFile logo, @RequestParam(value = "showPicsImg") MultipartFile[] showPics, HttpServletRequest request) {

        UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);


        JSONObject json = new JSONObject();
        
        //判断幼儿园名称是否存在
        KindergartenInfo query = new KindergartenInfo();
        query.setName(info.getName());
        query.setBrandId(info.getBrandId());
        query.setCategory(info.getCategory());
        List<KindergartenInfo> infos = kindergartenService.queryList(query);
        if(infos != null && !infos.isEmpty()){
        	 json.put("success", false);
             json.put("message", "相同品牌相同分类下存在相同名称的幼儿园");
             return json.toString();
        }
        
        String icon = "";
        if(logo != null && !logo.isEmpty()){

            try {
               icon = fileUpload("shoplogo", (CommonsMultipartFile)logo);
            } catch (Exception e) {
                logger.info("新增幼儿园时，上传LOGO文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        info.setLogo(icon);

        String showPicsUrl = "";
        if(showPics != null && showPics.length > 0){

            try {
                for(MultipartFile pic : showPics){
                    if(!pic.isEmpty()){

                        String url = fileUpload("shoplogo", (CommonsMultipartFile)pic);
                        showPicsUrl += url +",";
                    }
                }
            } catch (Exception e) {
                logger.info("新增幼儿园时，上传展示图片文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        info.setShowPics(showPicsUrl);
        info.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
        info.setCreateUser(userExtForm.getId());
        info.setStatus(0);
        try {
            int i = kindergartenService.save(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "新增成功");
            }else{
                json.put("success",false );
                json.put("message", "新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("新增幼儿园状态异常："+ e);
            json.put("success",false );
            json.put("message", "新增异常："+e.getMessage());
        }

        return json.toJSONString();
    }
    @RequestMapping("/kindergarten/update")
    @ResponseBody
    public String update(KindergartenInfo info, @RequestParam(value = "logoImg") MultipartFile logo, @RequestParam(value = "showPicsImg") MultipartFile[] showPics, String oldShowPics) {

        JSONObject json = new JSONObject();
        
        //判断幼儿园名称是否存在
        KindergartenInfo query = new KindergartenInfo();
        query.setName(info.getName());
        query.setBrandId(info.getBrandId());
        query.setCategory(info.getCategory());
        List<KindergartenInfo> infos = kindergartenService.queryList(query);
        if(infos != null && !infos.isEmpty() && infos.get(0).getId() != info.getId()){
        	 json.put("success", false);
             json.put("message", "相同品牌相同分类下存在相同名称的幼儿园");
             return json.toString();
        }
        
        
        if(logo != null && !logo.isEmpty()){

            try {
                String icon = fileUpload("shoplogo", (CommonsMultipartFile)logo);
                info.setLogo(icon);
            } catch (Exception e) {
                logger.info("编辑幼儿园时，上传LOGO文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }

        String showPicsUrl = "";
        if(showPics != null && showPics.length > 0){

            try {
                for(MultipartFile pic : showPics){
                    if(!pic.isEmpty()){

                        String url = fileUpload("shoplogo", (CommonsMultipartFile)pic);
                        showPicsUrl += url +",";
                    }
                }
            } catch (Exception e) {
                logger.info("编辑幼儿园时，上传展示图片文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        if(oldShowPics != null && !"".equals(oldShowPics)){
            showPicsUrl +=  oldShowPics.replaceAll(Constant.clearImgPrefix, "")  ;
        }
        info.setShowPics(showPicsUrl.endsWith(",")? showPicsUrl.substring(0,showPicsUrl.length()-1) : showPicsUrl);
        try {
            int i = kindergartenService.updateByPrimaryKeySelective(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "编辑成功");
            }else{
                json.put("success",false );
                json.put("message", "编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("编辑幼儿园状态异常："+ e);
            json.put("success",false );
            json.put("message", "编辑异常："+e.getMessage());
        }

        return json.toJSONString();
    }

    @RequestMapping("kindergarten/modifyState")
    @ResponseBody
    public String modifyStatus(Integer id, Integer status){
        KindergartenInfo info = new KindergartenInfo();
        info.setId(id);
        info.setStatus(status);
        JSONObject json = new JSONObject();
        try {
            int i = kindergartenService.updateByPrimaryKeySelective(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "编辑成功");
            }else{
                json.put("success",false );
                json.put("message", "编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("修改幼儿园状态异常："+ e);
            json.put("success",false );
            json.put("message", "编辑异常："+e.getMessage());
        }

        return json.toJSONString();
    }

    @RequestMapping("/kindergarten/find")
    @ResponseBody
    public String find(Integer id){

        JSONObject json = new JSONObject();
        try {
            KindergartenInfo info = kindergartenService.selectByPrimaryKey(id);
            if(info != null ){
                json.put("success", true);
                if(info.getLogo() != null && !"".equals(info.getLogo())){
                    info.setLogo(Constant.imgPrefix + info.getLogo());
                }
                if(info.getShowPics() != null && !"".equals(info.getShowPics())){
                    String showPicsFullPath = "";
                    String[] pics = info.getShowPics().split(",");
                    for(String pic : pics){
                        if("".equals(showPicsFullPath)){
                            showPicsFullPath += Constant.imgPrefix + pic;
                        }else{
                            showPicsFullPath += ","+ Constant.imgPrefix + pic;
                        }
                    }
                    info.setShowPics(showPicsFullPath);
                }
                json.put("data", info);
            }else{
                json.put("success", false);
                json.put("message", "初始化失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化幼儿园信息异常："+ e);
            json.put("success",false );
            json.put("message", "初始化幼儿园信息异常："+e.getMessage());
        }

        return json.toJSONString();
    }
    @RequestMapping("kindergarten/all")
    @ResponseBody
    public String all(){

        JSONObject json = new JSONObject();
        try {
        	KindergartenInfo info = new KindergartenInfo();
        	info.setStatus(0);
        	List<KindergartenInfo> list = kindergartenService.queryList(info);
            json.put("success", true);
            json.put("data", list);

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化幼儿园信息异常："+ e);
            json.put("success",false );
            json.put("message", "初始化幼儿园信息异常："+e.getMessage());
        }

        return json.toJSONString();
    }
    @RequestMapping("kindergarten/statistics")
    @ResponseBody
    public String statistics(Integer id){
    	
    	JSONObject json = new JSONObject();
    	try {
    		KindergartenGrade grade = new KindergartenGrade();
    		grade.setKindergartenId(id);
    		int seriesNum = kindergartenGradeService.getSeriesNum(grade);
    		int gradeNum = kindergartenGradeService.getTotal(grade);
    		KindergartenTeacher teacher = new KindergartenTeacher();
    		teacher.setKindergartenId(id);
    		int teacherNum = kindergartenTeacherService.getTotal(teacher);
    		KindergartenStudent student = new KindergartenStudent();
    		student.setKindergartenId(id);
    		int studentNum = kindergartenStudentService.getTotal(student);
    		//取一个月内的照片数统计数量、视频数量、荣誉数量
    		
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    		Calendar cal = Calendar.getInstance();
    		cal.add(Calendar.MONTH, -1);
    		String preMonth = format.format(cal.getTimeInMillis());
    		String currentDay = format.format(new Date());
    		Map<String, Object> param = new HashMap<String, Object>();
    		param.put("kindergartenId", id);
    		param.put("startDate", preMonth);
    		param.put("endDate", currentDay);
    		List<KindergartenDailyStatistics> statisticsList = kindergartenDailyStatisticsMapper.selectByCondition(param);
    		JSONObject data = new JSONObject();
    		data.put("seriesNum", seriesNum);
    		data.put("gradeNum", gradeNum);
    		data.put("teacherNum", teacherNum);
    		data.put("studentNum", studentNum);
    		data.put("statisticsList", statisticsList);
    		List<String> dayList = new ArrayList<String>();
    		List<Integer> photoDailyList = new ArrayList<Integer>();
    		List<Integer> videoDailyList = new ArrayList<Integer>();
    		List<Integer> honorDailyList = new ArrayList<Integer>();
    		for(KindergartenDailyStatistics kds : statisticsList){
    			dayList.add(kds.getDaily());
    			photoDailyList.add(kds.getPhotoNum());
    			videoDailyList.add(kds.getVideoNum());
    			honorDailyList.add(kds.getHonorNum());
    		}
    		data.put("dayList", dayList);
    		data.put("photoDailyList", photoDailyList);
    		data.put("videoDailyList", videoDailyList);
    		data.put("honorDailyList", honorDailyList);
    		
    		json.put("data", data);
    		json.put("success", true);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.info("查询幼儿园日统计信息异常："+ e);
    		json.put("success",false );
    		json.put("message", "查询幼儿园日统计信息异常："+e.getMessage());
    	}
    	
    	return json.toJSONString();
    }
    
    @RequestMapping("/kindergarten/export")
	@ResponseBody
	public String export(String name, HttpServletRequest request,HttpServletResponse response) {
		
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "kindergarten_"+ format.format(date) + ".xls";

	
		/** 获得输出流 **/
		ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
		JSONObject result = new JSONObject();
		try {
			String headerColumn = "[{\"name\":\"幼儿园名称\"},"
								+ "{\"address\":\"幼儿园地址\"},"
								+ "{\"registName\":\"注册人姓名\"},"
								+ "{\"serviceTel\":\"联系电话\"},"
								+ "{\"serviceStartTime\":\"服务开始时间\"},"
								+ "{\"serviceEndTime\":\"服务结束时间\"},"
								+ "{\"status\":\"幼儿园状态\"}]";
			
			KindergartenInfo info = new KindergartenInfo();
			info.setName(name);
			List<Map<String,Object>> dataList = kindergartenService.queryList2(info);
			
			HSSFWorkbook workbook = new ExportExcelUtils().exportExcel2(dataList, headerColumn, fileName);
			workbook.write(byteOutPut);
			
			String url = getRealGePath();
			OutputStream out=new FileOutputStream(url + fileName);//文件本地存储地址
			workbook.write(out);
			out.close();
			File tempFile = new File(url + fileName);
			byteOutPut.close();
			GZIPOutputStream gizout = new GZIPOutputStream(new FileOutputStream(url + fileName+".gz"));
			byte[] buff = new byte[1024]; //设定读入缓冲区尺寸   
			FileInputStream in = new FileInputStream(tempFile); //把生成的csv文件
			
			int len;
			while ((len = in.read(buff)) != -1) {
				gizout.write(buff,0,len);
			}
			in.close();
			gizout.finish();
			gizout.close();
			tempFile.delete();//删除临时文件
			
			result.put("success", true);
			result.put("url", "/temp/"+ fileName+".gz");
		} catch (IOException e) {
			result.put("success", false);
			e.printStackTrace();
		} catch (Exception e) {
			result.put("success", false);
			e.printStackTrace();
		}
		return result.toString();
	}
}
