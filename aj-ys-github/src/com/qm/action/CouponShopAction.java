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
import com.frame.core.util.GUID;
import com.qm.entities.CouponShopInfo;
import com.qm.service.CouponShopService;
import com.qm.shop.Constant;

@RestController
@RequestMapping("/coupon/shop")
public class CouponShopAction extends FtpImgDownUploadAction {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CouponShopService couponShopService;

	@RequestMapping("/list")
    public String list(String name, int limit, int offset) {

		CouponShopInfo info = new CouponShopInfo();
        info.setShopName(name);
        info.setOffset(offset);
        info.setPageSize(limit);
        List<CouponShopInfo> kindergartenInfoList = couponShopService.queryList(info);
        int total = couponShopService.getTotal(info);
        JSONObject json = new JSONObject();
        json.put("rows",kindergartenInfoList );
        json.put("total", total);
        return json.toJSONString();
    }
	
	@RequestMapping("/add")
    public String add(CouponShopInfo info, @RequestParam(value = "logoImg") MultipartFile logo, @RequestParam(value = "showPicsImg") MultipartFile[] showPics, HttpServletRequest request) {

        //UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);

        info.setId(GUID.generateID("CSP"));
        JSONObject json = new JSONObject();
        String icon = "";
        if(logo != null && !logo.isEmpty()){

            try {
                icon = fileUpload("shoplogo", (CommonsMultipartFile)logo);
            } catch (Exception e) {
                logger.info("新增优惠券商家时，上传LOGO文件出错："+e);
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
                logger.info("新增优惠券商家时，上传展示图片文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        info.setShowPic(showPicsUrl);
        info.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
        info.setStatus(0);
        try {
            int i = couponShopService.save(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "新增成功");
            }else{
                json.put("success",false );
                json.put("message", "新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("新增优惠券商家异常："+ e);
            json.put("success",false );
            json.put("message", "新增异常："+e.getMessage());
        }

        return json.toJSONString();
    }
	
	@RequestMapping("/update")
    public String update(CouponShopInfo info, @RequestParam(value = "logoImg") MultipartFile logo, @RequestParam(value = "showPicsImg") MultipartFile[] showPics, String oldShowPics) {

        JSONObject json = new JSONObject();
        if(logo != null && !logo.isEmpty()){

            try {
                String icon = fileUpload("shoplogo", (CommonsMultipartFile)logo);
                info.setLogo(icon);
            } catch (Exception e) {
                logger.info("编辑优惠券商家时，上传LOGO文件出错："+e);
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
                logger.info("编辑优惠券商家时，上传展示图片文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        if(oldShowPics != null && !"".equals(oldShowPics)){
            showPicsUrl +=  oldShowPics.replaceAll(Constant.clearImgPrefix, "")  ;
        }
        info.setShowPic(showPicsUrl.endsWith(",")? showPicsUrl.substring(0,showPicsUrl.length()-1) : showPicsUrl);
        try {
            int i = couponShopService.updateByPrimaryKeySelective(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "编辑成功");
            }else{
                json.put("success",false );
                json.put("message", "编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("编辑优惠券商家异常："+ e);
            json.put("success",false );
            json.put("message", "编辑异常："+e.getMessage());
        }

        return json.toJSONString();
    }
	
	@RequestMapping("/modifyState")
    @ResponseBody
    public String modifyStatus(String id, Integer status){
		CouponShopInfo info = new CouponShopInfo();
        info.setId(id);
        info.setStatus(status);
        JSONObject json = new JSONObject();
        try {
            int i = couponShopService.updateByPrimaryKeySelective(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "编辑成功");
            }else{
                json.put("success",false );
                json.put("message", "编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("修改优惠券商家异常："+ e);
            json.put("success",false );
            json.put("message", "编辑异常："+e.getMessage());
        }

        return json.toJSONString();
    }
	
	 @RequestMapping("/find")
    public String find(String id){

        JSONObject json = new JSONObject();
        try {
        	CouponShopInfo info = couponShopService.selectByPrimaryKey(id);
            if(info != null ){
                json.put("success", true);
                if(info.getLogo() != null && !"".equals(info.getLogo())){
                    info.setLogo(Constant.imgPrefix + info.getLogo());
                }
                if(info.getShowPic() != null && !"".equals(info.getShowPic())){
                    String showPicsFullPath = "";
                    String[] pics = info.getShowPic().split(",");
                    for(String pic : pics){
                        if("".equals(showPicsFullPath)){
                            showPicsFullPath += Constant.imgPrefix + pic;
                        }else{
                            showPicsFullPath += ","+ Constant.imgPrefix + pic;
                        }
                    }
                    info.setShowPic(showPicsFullPath);
                }
                json.put("data", info);
            }else{
                json.put("success", false);
                json.put("message", "初始化失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化优惠券商家异常："+ e);
            json.put("success",false );
            json.put("message", "初始化优惠券商家信息异常："+e.getMessage());
        }

        return json.toJSONString();
    }
    @RequestMapping("/all")
    public String all(){

        JSONObject json = new JSONObject();
        try {
        	CouponShopInfo info = new CouponShopInfo();
        	info.setStatus(0);
        	List<CouponShopInfo> list = couponShopService.queryList(info);
            json.put("success", true);
            json.put("data", list);

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化优惠券商家列表异常："+ e);
            json.put("success",false );
            json.put("message", "初始化优惠券商家列表异常："+e.getMessage());
        }

        return json.toJSONString();
    }
}
