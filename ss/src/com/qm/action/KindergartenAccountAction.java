package com.qm.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.util.DateUtil;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenAccount;
import com.qm.service.KindergartenAccountService;

@RestController
@RequestMapping("/kindergarten/account")
public class KindergartenAccountAction {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KindergartenAccountService kindergartenAccountService;
	
	@RequestMapping("/list")
    public String list(String name, String kindergartenName, int limit, int offset) {

        KindergartenAccount info = new KindergartenAccount();
        info.setUsername(name);
        info.setOffset(offset);
        info.setPageSize(limit);
        info.setKindergartenName(kindergartenName);
        List<KindergartenAccount> kindergartenAccountList = kindergartenAccountService.queryList(info);
        int total = kindergartenAccountService.getTotal(info);
        JSONObject json = new JSONObject();
        json.put("rows",kindergartenAccountList );
        json.put("total", total);
        return json.toJSONString();
    }
    @RequestMapping("/add")
    public String add(KindergartenAccount account, HttpServletRequest request) {

        UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);


        JSONObject json = new JSONObject();
        
        account.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
        account.setCreateUser(userExtForm.getId());
        account.setStatus(0);
        try {
            int i = kindergartenAccountService.save(account);
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
    public String update(KindergartenAccount account) {

        JSONObject json = new JSONObject();
         try {
            int i = kindergartenAccountService.updateByPrimaryKeySelective(account);
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

    @RequestMapping("/modifyState")
    public String modifyStatus(Integer id, Integer status){
    	KindergartenAccount info = new KindergartenAccount();
        info.setId(id);
        info.setStatus(status);
        JSONObject json = new JSONObject();
        try {
            int i = kindergartenAccountService.updateByPrimaryKeySelective(info);
            if(i > 0){
                json.put("success",true );
                json.put("message", "编辑成功");
            }else{
                json.put("success",false );
                json.put("message", "编辑失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("修改幼儿园帐号状态异常："+ e);
            json.put("success",false );
            json.put("message", "编辑异常："+e.getMessage());
        }

        return json.toJSONString();
    }

    @RequestMapping("/find")
    public String find(Integer id){

        JSONObject json = new JSONObject();
        try {
        	KindergartenAccount info = kindergartenAccountService.selectByPrimaryKey(id);
            if(info != null ){
                json.put("success", true);
                json.put("message", info);
            }else{
                json.put("success", false);
                json.put("message", "初始化失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化幼儿园帐号信息异常："+ e);
            json.put("success",false );
            json.put("message", "初始化幼儿园帐号信息异常："+e.getMessage());
        }

        return json.toJSONString();
    }
    
    @RequestMapping("/findByUsername")
    public String findByUsername(String username){

        JSONObject json = new JSONObject();
        try {
        	KindergartenAccount account = new KindergartenAccount();
        	account.setUsername(username);
        	List<KindergartenAccount> info = kindergartenAccountService.findByCondition(account);
            if(info != null && info.size() > 0 ){
                json.put("success", true);
                json.put("message", info.get(0));
            }else{
                json.put("success", false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("findByUsername异常："+ e);
            json.put("success",false );
        }

        return json.toJSONString();
    }
   
}
