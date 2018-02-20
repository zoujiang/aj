package com.qm.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenInfo;
import com.qm.service.KindergartenService;
import com.qm.shop.Constant;

@Controller
@RequestMapping("/kindergarten")
@Scope("prototype")
public class KindergartenInfoAction extends FtpImgDownUploadAction {

  private Logger logger = LoggerFactory.getLogger(KindergartenInfoAction.class);
    @Autowired
    private KindergartenService kindergartenService;

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
}
