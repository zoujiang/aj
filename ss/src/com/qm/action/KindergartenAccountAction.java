package com.qm.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.constant.Constant;
import com.frame.core.util.GUID;
import com.frame.core.util.SparkLib;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenAccount;
import com.qm.entities.SysUser;
import com.qm.entities.SysUserRole;
import com.qm.mapper.SysUserMapper;
import com.qm.mapper.SysUserRoleMapper;

@RestController
@RequestMapping("/kindergarten/account")
public class KindergartenAccountAction {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	
	@RequestMapping("/list")
    public String list(String name, int limit, int offset, HttpServletRequest request) {

        KindergartenAccount info = new KindergartenAccount();
        info.setUsername(name);
        info.setOffset(offset);
        info.setPageSize(limit);
        info.setKindergartenId(Integer.parseInt(request.getSession().getAttribute(Constant.LOGIN_SHOP_ID).toString()));
        List<Map<String, Object>> kindergartenAccountList = sysUserMapper.queryList(info);
        int total = sysUserMapper.getTotal(info);
        JSONObject json = new JSONObject();
        json.put("rows",kindergartenAccountList );
        json.put("total", total);
        return json.toJSONString();
    }
    @RequestMapping("/add")
    public String add(KindergartenAccount account, HttpServletRequest request) {

        UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);
        JSONObject json = new JSONObject();
        SysUser uv = new SysUser();
		String userId = GUID.generateID("SA");
		uv.setAccount(account.getUsername());
		uv.setEmail(account.getEmail());
		uv.setId(userId);
		if(account.getStatus() == 0){
			uv.setIsenabled("1");
		}else if(account.getStatus() == 1){
			uv.setIsenabled("2");
		}
		uv.setMobile(account.getTel());
		uv.setName(account.getNickname());
		uv.setPwd(SparkLib.encodePassword(account.getPassword(), Constant.MAX_PASSWORD));
		uv.setTele(account.getTel());
		uv.setCreateDt(new Date());
		uv.setAccountType("7");
		uv.setShopId(request.getSession().getAttribute(Constant.LOGIN_SHOP_ID)+"");
		
		int i = sysUserMapper.insertSelective(uv);
		
        
        try {
            if(i > 0){
            	//设置权限
        		SysUserRole role = new SysUserRole();
        		role.setId(GUID.generateID("UR"));
        		role.setUserid(uv.getId());
        		role.setRoleid("KINDERGARTENMANAGER");
        		try {
        			sysUserRoleMapper.insertSelective(role);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("新增幼儿园管理员账号，权限设置失败");
				}
        		
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
   // public String update(KindergartenAccount account) {
    public String update(String id, String kindergartenId, Integer status, String username, String nickname, String tel, String email, String password) {

        JSONObject json = new JSONObject();
         try {
        	SysUser uv = new SysUser();
     		uv.setAccount(username);
     		uv.setEmail(email);
     		uv.setId(id);
     		if(status == 0){
    			uv.setIsenabled("1");
    		}else if(status == 1){
    			uv.setIsenabled("2");
    		}
     		uv.setMobile(tel);
     		uv.setName(nickname);
     		uv.setPwd(SparkLib.encodePassword(password, Constant.MAX_PASSWORD));
     		uv.setTele(tel);
     		uv.setAccountType("7");
     		uv.setShopId(kindergartenId);
        	 
            int i = sysUserMapper.updateByPrimaryKeySelective(uv);
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
    public String modifyStatus(String id, Integer status){
    	SysUser info = new SysUser();
        info.setId(id);
        if(status == 0){
        	info.setIsenabled("1");
		}else if(status == 1){
			info.setIsenabled("2");
		}
        JSONObject json = new JSONObject();
        try {
            int i = sysUserMapper.updateByPrimaryKeySelective(info);
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
    public String find(String id){

        JSONObject json = new JSONObject();
        try {
        	Map<String, Object> info = sysUserMapper.selectMapByPrimaryKey(id);
        	info.put("password", SparkLib.decodePassword(info.get("password")+""));
            if(info != null&& !info.isEmpty() ){
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
        	SysUser account = new SysUser();
        	account.setAccount(username);
        	List<SysUser> info = sysUserMapper.findByCondition(account);
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
