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
import com.frame.core.util.SystemConfig;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenInfo;
import com.qm.entities.KindergartenTeacher;
import com.qm.entities.UserInfo;
import com.qm.mapper.KindergartenTeacherMapper;
import com.qm.mapper.UserInfoMapper;
import com.qm.service.KindergartenService;
import com.qm.service.KindergartenTeacherService;
import com.qm.shop.Constant;
import com.qm.shop.customer.action.ShopCustomerAction;
import com.qm.shop.customer.service.ShopCustomerService;

@RestController
@RequestMapping("/kindergarten/teacher")
public class KindergartenTeacherAction extends FtpImgDownUploadAction {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenTeacherService kindergartenTeacherService;
	@Autowired
	private ShopCustomerService shopCustomerService;
	@Autowired
	private KindergartenService kindergartenService;
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@RequestMapping("/list")
    public String list(String name, Integer kindergartenId, Integer type,  int limit, int offset) {

        KindergartenTeacher info = new KindergartenTeacher();
        info.setName(name);
        info.setType(type);
        info.setOffset(offset);
        info.setPageSize(limit);
        info.setKindergartenId(kindergartenId);
        List<KindergartenTeacher> KindergartenTeacherList = kindergartenTeacherService.queryList(info);
        int total = kindergartenTeacherService.getTotal(info);
        JSONObject json = new JSONObject();
        json.put("rows",KindergartenTeacherList );
        json.put("total", total);
        return json.toJSONString();
    }
    @RequestMapping("/add")
    public String add(KindergartenTeacher account, @RequestParam(value = "logoImg") MultipartFile logo, HttpServletRequest request) {

        UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);

        String userType = "";
		String role = "";
		if(account.getType() == 1){
			role = "园长";
			userType = "3";
		}else if(account.getType() == 2){
			role = "副园长";
			userType = "3";
		}else if(account.getType() == 3){
			role = "管理人员";
			userType = "4";
		}else if(account.getType() == 4){
			role = "主教";
			userType = "2";
		}else if(account.getType() == 5){
			role = "副教";
			userType = "2";
		}else if(account.getType() == 6){
			role = "保育员";
			userType = "2";
		}else if(account.getType() == 7){
			role = "其他";
		}
        
        
        JSONObject json = new JSONObject();
        //验证学校园长，管理人员，教师个数，不能超过个数
        String teacherNumber = SystemConfig.getValue("aj.kindergarten.teacher.number");
        String[] nubs = teacherNumber.split("\\|");
        if("3".equals(userType)){
        	//园长
        	int teacherNum = kindergartenTeacherService.getNumberByType(3);
        	if(Integer.parseInt(nubs[0]) <= teacherNum){
        		json.put("success", false);
                json.put("message", "每个幼儿园最多只能有"+nubs[0]+"个园长");
                return json.toString();
        	}
        }else if("4".equals(userType)){
        	int teacherNum = kindergartenTeacherService.getNumberByType(4);
        	if(Integer.parseInt(nubs[1]) <= teacherNum){
        		json.put("success", false);
                json.put("message", "每个幼儿园最多只能有"+nubs[1]+"个管理人员");
                return json.toString();
        	}
        }else if("2".equals(userType)){
        	int teacherNum = kindergartenTeacherService.getNumberByType(2);
        	if(Integer.parseInt(nubs[2]) <= teacherNum){
        		json.put("success", false);
                json.put("message", "每个幼儿园最多只能有"+nubs[2]+"个教师");
                return json.toString();
        	}
        }
        String icon = null;
        if(logo != null && !logo.isEmpty()){

            try {
                icon = fileUpload("teacherlogo", (CommonsMultipartFile)logo);
            } catch (Exception e) {
                logger.info("新增幼儿园教师时，上传头像文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        account.setPhoto(icon);
        
        account.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
        account.setCreateUser(userExtForm.getId());
        //验证手机号是否已经注册为教师
        KindergartenTeacher validate = new KindergartenTeacher();
        validate.setTel(account.getTel());
        validate = kindergartenTeacherService.queryTeacherByTel(validate);
        if(validate != null){
        	 json.put("success", false);
             json.put("message", "该联系电话已经注册为幼儿园教师，添加失败");
             return json.toString();
        }
        
        //p判断该用户的手机号是否注册过 亲脉 帐号，如果没有吗则创建一个
        
        Map<String, Object> userInfo = shopCustomerService.findAppUserByUserTel(account.getTel());
		String password = 100000 +new Random().nextInt(899999) +"";
		
		if(userInfo == null || userInfo.isEmpty()){
			try {
				
				//该手机号没注册， 后台给注册一个
    			boolean b = registAppUser(account.getTel(), password, userType, this.shopCustomerService);
    			if(b){
    				
    				KindergartenInfo kInfo = kindergartenService.selectByPrimaryKey(account.getKindergartenId());
    				String content = kInfo.getName()+"幼儿园已经在“亲脉”系统中添加您为［"+role+"］,后台自动为您生成登录帐号：账号"+account.getTel()+"，密码"+password+"，您可以下载亲脉APP进入生活我的生活中查看赠送服务亲脉下载地址：http://qm.dbfish.net/d";
    				boolean sendState = ShopCustomerAction.sendMsg(account.getTel(), content);
    				logger.info("亲脉后台系统添加教师成功后，由于该教师还未注册过亲脉，所以自动生成帐号：" +account.getTel() +", 注册结果：" +sendState );
    				
    				userInfo = shopCustomerService.findAppUserByUserTel(account.getTel());
    				account.setUserId(Integer.parseInt(userInfo.get("id")+""));
    			}else{
    				logger.info("新增幼儿园教师时，注册亲脉用户失败");
                    json.put("success", false);
                    json.put("message", "新增幼儿园教师时，注册亲脉用户失败");
                    return json.toString();
    			}
			} catch (Exception e) {
				logger.info("亲脉后台系统添加教师成功后，由于该教师还未注册过亲脉，所以自动生成帐号：但是发生异常：" + e);
			}
			
		}else{
			//已经注册过了， 把用户的类型更改为教师、园长或者管理人员
			UserInfo user = new UserInfo();
			user.setType(Integer.parseInt(userType));
			user.setId(Integer.parseInt(userInfo.get("id")+""));
			userInfoMapper.updateByPrimaryKeySelective(user);
			account.setUserId(Integer.parseInt(userInfo.get("id")+""));
		}
        
        
        try {
            int i = kindergartenTeacherService.save(account);
            if(i > 0){
                json.put("success",true );
                json.put("message", "新增成功");
                
               
            }else{
                json.put("success",false );
                json.put("message", "新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("新增幼儿园帐号异常："+ e);
            json.put("success",false );
            json.put("message", "新增异常："+e.getMessage());
        }

        return json.toJSONString();
    }
    
    
    @RequestMapping("/update")
    public String update(KindergartenTeacher account,  @RequestParam(value = "logoImg") MultipartFile logo) {

        JSONObject json = new JSONObject();
        
        String userType = "";
		String role = "";
		if(account.getType() == 1){
			role = "园长";
			userType = "3";
		}else if(account.getType() == 2){
			role = "副园长";
			userType = "3";
		}else if(account.getType() == 3){
			role = "管理人员";
			userType = "4";
		}else if(account.getType() == 4){
			role = "主教";
			userType = "2";
		}else if(account.getType() == 5){
			role = "副教";
			userType = "2";
		}else if(account.getType() == 6){
			role = "保育员";
			userType = "2";
		}else if(account.getType() == 7){
			role = "其他";
		}
        
      //验证学校园长，管理人员，教师个数，不能超过个数
        String teacherNumber = SystemConfig.getValue("aj.kindergarten.teacher.number");
        String[] nubs = teacherNumber.split("\\|");
        if("3".equals(userType)){
        	//园长
        	int teacherNum = kindergartenTeacherService.getNumberByType(3);
        	if(Integer.parseInt(nubs[0]) <= teacherNum){
        		json.put("success", false);
                json.put("message", "每个幼儿园最多只能有"+nubs[0]+"个园长");
                return json.toString();
        	}
        }else if("4".equals(userType)){
        	int teacherNum = kindergartenTeacherService.getNumberByType(4);
        	if(Integer.parseInt(nubs[1]) <= teacherNum){
        		json.put("success", false);
                json.put("message", "每个幼儿园最多只能有"+nubs[1]+"个管理人员");
                return json.toString();
        	}
        }else if("2".equals(userType)){
        	int teacherNum = kindergartenTeacherService.getNumberByType(2);
        	if(Integer.parseInt(nubs[2]) <= teacherNum){
        		json.put("success", false);
                json.put("message", "每个幼儿园最多只能有"+nubs[2]+"个教师");
                return json.toString();
        	}
        }
        
        if(logo != null && !logo.isEmpty()){

            try {
            	 String icon = fileUpload("teacherlogo", (CommonsMultipartFile)logo);
                account.setPhoto(icon);
            } catch (Exception e) {
                logger.info("更新幼儿园教师时，上传头像文件出错："+e);
                json.put("success", false);
                json.put("message", "上传图片失败");
                return json.toString();
            }
        }
        
        
      //如果教师手机号已经改变
        KindergartenTeacher info = kindergartenTeacherService.selectByPrimaryKey(account.getId());
        KindergartenTeacher validate = new KindergartenTeacher();
        validate.setTel(account.getTel());
        validate = kindergartenTeacherService.queryTeacherByTel(validate);
        if( !info.getTel().equals(account.getTel())) {
        	 //验证手机号是否已经注册为教师
            if(validate != null && validate.getId() == account.getId()){
            	 json.put("success", false);
                 json.put("message", "该联系电话已经注册为幼儿园教师，编辑失败");
                 return json.toString();
            }
        }
       
        //判断教师的类型是否改变，如果改变，则更新user表的类型
        if(validate.getType() != account.getType()){
        	UserInfo user = new UserInfo();
			user.setType(Integer.parseInt(userType));
			user.setId(validate.getUserId());
			userInfoMapper.updateByPrimaryKeySelective(user);
        }
        
         try {
            int i = kindergartenTeacherService.updateByPrimaryKeySelective(account);
            if(i > 0){
                json.put("success",true );
                json.put("message", "编辑成功");
            }else{
                json.put("success",false );
                json.put("message", "编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("编辑幼儿园帐号异常："+ e);
            json.put("success",false );
            json.put("message", "编辑异常："+e.getMessage());
        }

        return json.toJSONString();
    }

    @RequestMapping("/del")
    public String del(Integer id){
        JSONObject json = new JSONObject();
        try {
            int i = kindergartenTeacherService.deleteByPrimaryKey(id);
            if(i > 0){
                json.put("success",true );
                json.put("message", "删除成功");
            }else{
                json.put("success",false );
                json.put("message", "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("删除幼儿园教师异常："+ e);
            json.put("success",false );
            json.put("message", "删除异常："+e.getMessage());
        }

        return json.toJSONString();
    }

    @RequestMapping("/find")
    public String find(Integer id){

        JSONObject json = new JSONObject();
        try {
        	KindergartenTeacher info = kindergartenTeacherService.selectByPrimaryKey(id);
            if(info != null ){
            	String photo = info.getPhoto();
            	if(photo != null && !"".equals(photo)){
            		info.setPhoto(Constant.imgPrefix + photo);
            	}
            	
                json.put("success", true);
                json.put("message", info);
            }else{
                json.put("success", false);
                json.put("message", "初始化失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化幼儿园教师信息异常："+ e);
            json.put("success",false );
            json.put("message", "初始化幼儿园教师信息异常："+e.getMessage());
        }

        return json.toJSONString();
    }
   
}
