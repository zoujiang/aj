package com.qm.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.util.DateUtil;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.CouponInfo;
import com.qm.entities.CouponShopInfo;
import com.qm.service.CouponService;
import com.qm.shop.Constant;

@RestController
@RequestMapping("/coupon/baseinfo")
public class CouponAction extends FtpImgDownUploadAction {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CouponService couponService;
	
	@RequestMapping("/list")
    public String list(String shopId,String name, int limit, int offset) {

		CouponInfo info = new CouponInfo();
        info.setShopId(shopId);
        info.setName(name);
        info.setOffset(offset);
        info.setPageSize(limit);
        List<CouponInfo> kindergartenInfoList = couponService.queryList(info);
        int total = couponService.getTotal(info);
        JSONObject json = new JSONObject();
        json.put("rows",kindergartenInfoList );
        json.put("total", total);
        return json.toJSONString();
    }
	
	@RequestMapping("/add")
    public String add(CouponInfo info, @RequestParam(value = "logoImg") MultipartFile logo, @RequestParam(value = "showPicsImg") MultipartFile[] showPics, HttpServletRequest request) {

        UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);

        JSONObject json = new JSONObject();
        String icon = "";
        if(logo != null && !logo.isEmpty()){

            try {
                icon = fileUpload("couponlogo", (CommonsMultipartFile)logo);
            } catch (Exception e) {
                logger.info("新增优惠券时，上传LOGO文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        info.setFirstPagePic(icon);

        String showPicsUrl = "";
        if(showPics != null && showPics.length > 0){

            try {
                for(MultipartFile pic : showPics){
                    if(!pic.isEmpty()){

                        String url = fileUpload("couponlogo", (CommonsMultipartFile)pic);
                        showPicsUrl += url +",";
                    }
                }
            } catch (Exception e) {
                logger.info("新增优惠券时，上传展示图片文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        info.setShowImg(showPicsUrl);
        info.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
        info.setCreateUser(userExtForm.getAccount());
        info.setIsValidate(0);
        try {
            int i = couponService.save(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "新增成功");
            }else{
                json.put("success",false );
                json.put("message", "新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("新增优惠券异常："+ e);
            json.put("success",false );
            json.put("message", "新增异常："+e.getMessage());
        }

        return json.toJSONString();
    }
	
	@RequestMapping("/modifyState")
    @ResponseBody
    public String modifyStatus(Integer id, Integer status){
		CouponInfo info = new CouponInfo();
        info.setId(id);
        info.setIsValidate(status);
        JSONObject json = new JSONObject();
        try {
            int i = couponService.updateByPrimaryKeySelective(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "编辑成功");
            }else{
                json.put("success",false );
                json.put("message", "编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("修改优惠券异常："+ e);
            json.put("success",false );
            json.put("message", "编辑异常："+e.getMessage());
        }

        return json.toJSONString();
    }
	@RequestMapping("/find")
    @ResponseBody
    public String find(Integer id){
        JSONObject json = new JSONObject();
        try {
        	CouponInfo info  = couponService.selectByPrimaryKey(id);
            if(info != null){
                json.put("success",true );
                json.put("data", info);
            }else{
                json.put("success",false );
                json.put("message", "初始化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询优惠券异常："+ e);
            json.put("success",false );
            json.put("message", "初始化异常："+e.getMessage());
        }

        return json.toJSONString();
    }
	
	@RequestMapping("/update")
    public String update(CouponInfo info, @RequestParam(value = "logoImg") MultipartFile logo, @RequestParam(value = "showPicsImg") MultipartFile[] showPics, String oldShowPics) {


        JSONObject json = new JSONObject();
        String icon = "";
        if(logo != null && !logo.isEmpty()){

            try {
                icon = fileUpload("couponlogo", (CommonsMultipartFile)logo);
                info.setFirstPagePic(icon);
            } catch (Exception e) {
                logger.info("新增优惠券时，上传LOGO文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }

        String showPicsUrl = oldShowPics == null? "" : oldShowPics.replaceAll(Constant.clearImgPrefix, "");
        if(showPics != null && showPics.length > 0){

            try {
                for(MultipartFile pic : showPics){
                    if(!pic.isEmpty()){

                        String url = fileUpload("couponlogo", (CommonsMultipartFile)pic);
                        if("".equals(showPicsUrl)){
                        	showPicsUrl += url ;
                        }else{
                        	showPicsUrl += "," + url;
                        }
                    }
                }
            } catch (Exception e) {
                logger.info("新增优惠券时，上传展示图片文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        info.setShowImg(showPicsUrl);
        try {
            int i = couponService.updateByPrimaryKeySelective(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "编辑成功");
            }else{
                json.put("success",false );
                json.put("message", "编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("编辑优惠券异常："+ e);
            json.put("success",false );
            json.put("message", "编辑异常："+e.getMessage());
        }

        return json.toJSONString();
    }

}
