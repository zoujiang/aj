package com.frame.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.constant.Constant;
import com.frame.core.dao.GenericDAO;
import com.frame.core.service.AuthorService;
import com.frame.core.util.BeanCopy;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.ListUtils;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.SparkLib;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;
import com.frame.system.po.Resource;
import com.frame.system.po.User;
import com.frame.system.po.UserRole;
import com.frame.system.service.UserService;
import com.frame.system.vo.ResourceExtForm;
import com.frame.system.vo.UserExtForm;
import com.frame.system.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private GenericDAO genericDAO;
    @Autowired
    private LogBizOprService logBizOprService;
    @Autowired
    private AuthorService authorService;

    @Override
    public boolean addUser(UserExtForm userExtForm) {
        /*
        if(this.checkUseEmailExisted(userExtForm.getId(), userExtForm.getEmail())){
			return false;
		}else if(this.checkUserAccountExisted(userExtForm.getId(), userExtForm.getAccount())){
			return false;
		}else if(this.checkUserMobileExisted(userExtForm.getId(), userExtForm.getMobile())){
			return false;
		}*/
        User cmsUser = BeanUtils.copyProperties(User.class, userExtForm);
        if (StringUtil.isEmpty(cmsUser.getId()))
            cmsUser.setId(RandomGUID.newGuid());
        cmsUser.setPwd(SparkLib.encodePassword(userExtForm.getPwd(), Constant.MAX_PASSWORD));
        genericDAO.save(cmsUser);
        logBizOprService.saveLog("用户管理", "1", "添加用户(用户账号:" + cmsUser.getAccount() + ",用户名称:" + cmsUser.getName() + ")", "t_sys_user", cmsUser.toString());
        return true;
    }

    @Override
    public String saveUser(UserExtForm userExtForm) {
		/*
		if(this.checkUseEmailExisted(userExtForm.getId(), userExtForm.getEmail())){
			return false;
		}else if(this.checkUserAccountExisted(userExtForm.getId(), userExtForm.getAccount())){
			return false;
		}else if(this.checkUserMobileExisted(userExtForm.getId(), userExtForm.getMobile())){
			return false;
		}*/
        User cmsUser = BeanUtils.copyProperties(User.class, userExtForm);
        if (StringUtil.isEmpty(cmsUser.getId()))
            cmsUser.setId(RandomGUID.newGuid());
        cmsUser.setPwd(SparkLib.encodePassword(userExtForm.getPwd(), Constant.MAX_PASSWORD));
        String pk = genericDAO.save(cmsUser);
        logBizOprService.saveLog("用户管理", "1", "添加用户(用户账号:" + cmsUser.getAccount() + ",用户名称:" + cmsUser.getName() + ")", "t_sys_user", cmsUser.toString());
        return pk;
    }

    @Override
    public String saveUser(User user) {
		/*
		if(this.checkUseEmailExisted(userExtForm.getId(), userExtForm.getEmail())){
			return false;
		}else if(this.checkUserAccountExisted(userExtForm.getId(), userExtForm.getAccount())){
			return false;
		}else if(this.checkUserMobileExisted(userExtForm.getId(), userExtForm.getMobile())){
			return false;
		}*/
//        if (StringUtil.isEmpty(user.getId()))
//            user.setId(RandomGUID.newGuid());
        user.setPwd(SparkLib.encodePassword(user.getPwd(), Constant.MAX_PASSWORD));
        String pk = user.getId();
        genericDAO.getSession().saveOrUpdate(user);
        logBizOprService.saveLog("用户管理", "1", "添加用户(用户账号:" + user.getAccount() + ",用户名称:" + user.getName() + ")", "t_sys_user", user.toString());
        return pk;
    }

    @Override
    public boolean checkUserAccountExisted(String id, String account) {
        String sql = "";
        boolean isexists = false;
        if (StringUtil.isNotEmpty(id)) {
            sql = "select count(u.id) from User u where u.account ='" + account + "' and u.id <> '" + id + "' ";
        } else {
            sql = "select count(u.id)  from User u where u.account ='" + account + "'";
        }

        int count = genericDAO.getGenericCountByHql(sql);
        if (count > 0) isexists = true;
        return isexists;
    }

    @Override
    public boolean checkUserMobileExisted(String id, String mobile) {
        String sql = "";
        boolean isexists = false;
        if (StringUtil.isNotEmpty(id)) {
            sql = "select count(u.id)  from User u where u.mobile ='" + mobile + "' and u.id <> '" + id + "' ";
        } else {
            sql = "select count(u.id)  from User u where u.mobile ='" + mobile + "'";
        }

        int count = genericDAO.getGenericCountByHql(sql);
        if (count > 0) isexists = true;
        return isexists;
    }

    @Override
    public boolean checkUseEmailExisted(String id, String email) {
        String sql = "";
        boolean isexists = false;
        if (StringUtil.isNotEmpty(id)) {
            sql = "select count(u.id)  from User u where u.email ='" + email + "' and u.id <> '" + id + "' ";
        } else {
            sql = "select count(u.id)  from User u where u.email ='" + email + "'";
        }

        int count = genericDAO.getGenericCountByHql(sql);
        if (count > 0) isexists = true;
        return isexists;
    }

    @Override
    public void exeAssignRoles(String userId, String[] roleIds) {
        /**
         * 删除用户具有的角
         */
        String sql = "delete from UserRole r where r.userId ='" + userId + "'";
        genericDAO.execteBulk(sql);
        UserRole role = null;
        for (String roleId : roleIds) {
        	if(StringUtil.isNotEmpty(roleId)){
        		 role = new UserRole();
                 role.setId(RandomGUID.newGuid());
                 role.setUserId(userId);
                 role.setRoleId(roleId);
                 genericDAO.save(role);
                 logBizOprService.saveLog("用户管理", "2", "修改用户角色(用户ID：" + userId + ",角色Id:" + roleId + ")", "t_sys_userrole", roleIds.toString());
        	}
        }

    }

    @Override
    public void deleteUser(String userId) {
        //删除用户，要删除用户所具有的角色
        String sql = "delete from UserRole ur where ur.userId='" + userId + "'";
        genericDAO.execteBulk(sql);
        genericDAO.delete(User.class, userId);
        logBizOprService.saveLog("用户管理", "3", "删除用户(用户ID：" + userId + ")", "t_sys_userrole", userId);

    }

    @Override
    public List<UserRole> getRolesByUserId(String userId) {
        String sql = "from UserRole ur where  ur.userId='" + userId + "'";
        List<UserRole> list = genericDAO.getGenericByHql(sql);
        return list;
    }


    @Override
    public UserExtForm getUserById(String userId) {
        User cmsUser = genericDAO.get(User.class, userId);
        cmsUser.setPwd(SparkLib.decodePassword(cmsUser.getPwd()));
        if (cmsUser != null) {
            return BeanUtils.copyProperties(UserExtForm.class, cmsUser);
        }
        return null;
    }

    @Override
    public User getUser(String userId) {
        User cmsUser = genericDAO.get(User.class, userId);
        return cmsUser;
    }

    @Override
    public DataModel getUsers(String... args) {
        DataModel model = new DataModel();
        model.setRows(ListUtils.transform(genericDAO.listAll(User.class), UserExtForm.class));
        return model;
    }

    @Override
    public boolean updateUser(UserExtForm userExtForm) {
		/*
		if(this.checkUseEmailExisted(userExtForm.getId(), userExtForm.getEmail())){
			return false;
		}else if(this.checkUserAccountExisted(userExtForm.getId(), userExtForm.getAccount())){
			return false;
		}else if(this.checkUserMobileExisted(userExtForm.getId(), userExtForm.getMobile())){
			return false;
		}*/
        User cmsUser = genericDAO.get(User.class, userExtForm.getId());
        cmsUser.setEmail(userExtForm.getEmail());
        cmsUser.setName(userExtForm.getName());
        cmsUser.setIsEnabled(userExtForm.getIsEnabled());
        cmsUser.setTele(userExtForm.getTele());
        cmsUser.setMobile(userExtForm.getMobile());
        cmsUser.setPwd(SparkLib.encodePassword(userExtForm.getPwd(), Constant.MAX_PASSWORD));
        genericDAO.saveOrUpdate(cmsUser);
        logBizOprService.saveLog("用户管理", "2", "修改用户(用户ID：" + cmsUser.getAccount() + ")", "t_sys_user", cmsUser.toString());

        return true;
    }

    @Override
    public void updateUserState(String state, String... ids) {
        for (String id : ids) {
            User cmsUser = genericDAO.get(User.class, id);
            cmsUser.setIsEnabled(state);
            genericDAO.saveOrUpdate(cmsUser);
            logBizOprService.saveLog("用户管理", "2", "更新用户状态(用户ID：" + cmsUser.getAccount() + ",状态: " + state + " )", "t_sys_user", ids.toString());

        }

    }

    @Override
    public List<ResourceExtForm> getUserResource(String userId) {
        /**
         * 获取用户资源
         */
        StringBuffer sb = new StringBuffer("select distinct rs from UserRole ur,Role r,Resource rs,RoleResource rr ");
        sb.append(" where r.id=ur.roleId and rr.roleId=r.id and rr.resourceId=rs.id ");
        sb.append(" and ur.userId = '").append(userId).append("' order by rs.code ");
        //.append("' group by rs.id order by rs.code ");
        List<Resource> resouceList = this.genericDAO.getGenericByHql(sb.toString());
        List<ResourceExtForm> resultList = new ArrayList<ResourceExtForm>();
        for (Resource rs : resouceList) {
            ResourceExtForm rf = new ResourceExtForm();
            rf.setId(rs.getId());
            rf.setCode(rs.getCode());
            rf.setHref(rs.getHref());
            rf.setParentId(rs.getParentId());
            rf.setTitle(rs.getTitle());
            rf.setIconCls(rs.getIconCls());
            resultList.add(rf);
        }
//		return ListUtils.transform(resouceList,ResourceExtForm.class);
        return resultList;
    }

    @Override
    public String getLoginInfo(String loginName, String password) {
        String name = loginName.trim();
        String hsql = "from User u where  u.account ='" + name + "' and  isEnabled='1' ";
        List<User> list = this.genericDAO.getGenericByHql(hsql);
        if (list != null && list.size() == 1) {
            User cmsUser = list.get(0);
            if (SparkLib.decodePassword(cmsUser.getPwd()).equals(password)) {
                return cmsUser.getId();
            }
        }
        return null;
    }


    public String getLoginInfo(String loginName, String password, String type) {

        String name = loginName.trim();
        String hsql = "from User u where u.account ='" + name + "' and accountType in ( " + type + ") ";
        List<User> list = this.genericDAO.getGenericByHql(hsql);
        if (list != null && list.size() == 1) {
            User cmsUser = list.get(0);
            if (SparkLib.decodePassword(cmsUser.getPwd()).equals(password)) {
                return cmsUser.getId();
            }
        }
        return null;
    }


    public UserVo getUserInfoByLoginNameAndPassword(String loginName, String password) {
        String name = loginName.trim();
        String hsql = "from User u where u.account=? and u.isEnabled = 1";
        List<User> list = this.genericDAO.getGenericByHql(hsql, new Object[]{name});
        if (list != null && list.size() > 0) {
            User cmsUser = list.get(0);
            if (SparkLib.decodePassword(cmsUser.getPwd()).equals(password)) {
                return BeanCopy.copy(UserVo.class, cmsUser);
            }
        }
        return null;
    }

    public User getUserPoByLoginNameAndPassword(String loginName, String password) {
        String name = loginName.trim();
        String hsql = "from User u where u.account=?";
        List<User> list = this.genericDAO.getGenericByHql(name);
        if (list != null && list.size() == 1) {
            User cmsUser = list.get(0);
            return cmsUser;
        }
        return null;
    }

    @Override
    public boolean exeResetPassWord(String userid, String password, String newspassword) {
        boolean ret = true;
        User cmsUser = genericDAO.get(User.class, userid);
        if (SparkLib.decodePassword(cmsUser.getPwd()).equals(password)) {
            cmsUser.setPwd(SparkLib.encodePassword(newspassword, Constant.MAX_PASSWORD));
            this.genericDAO.update(cmsUser);
        } else {
            ret = false;
        }
        logBizOprService.saveLog("用户管理", "2", "修改密码(用户ID：" + cmsUser.getAccount() + ")", "t_sys_user", userid);

        return ret;
    }


    @Override
    public List<User> queryListByAccounts(String accounts) {
        final String hql = " FROM User AS U WHERE U.account IN (:alist)";
        String[] queryParams = StringUtil.split(accounts, ",");
        Query query = genericDAO.getSession().createQuery(hql);
        query.setParameterList("alist", queryParams);
        List<User> lsuser = query.list();
        return lsuser;
    }

    @Override
    public String queryStrMobiles(String accounts) {
        List<User> lsuser = this.queryListByAccounts(accounts);
        StringBuilder sb = new StringBuilder("");
        for (User user : lsuser) {
            sb.append(user.getMobile());
            sb.append(";");
        }
        return sb.substring(0, sb.length() - 1);
    }


    @Override
    public String queryStrEmails(String accounts) {
        List<User> lsuser = this.queryListByAccounts(accounts);
        StringBuilder sb = new StringBuilder("");
        for (User user : lsuser) {
            sb.append(user.getEmail());
            sb.append(";");
        }
        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public String[] queryStrEmailAndMobiles(String accounts) {
        String[] strEmailAndMobiles = new String[2];
        List<User> lsuser = this.queryListByAccounts(accounts);
        StringBuilder sbEmails = new StringBuilder("");
        StringBuilder sbMobiles = new StringBuilder("");
        for (User user : lsuser) {
            sbMobiles.append(user.getMobile());
            sbMobiles.append(";");
            sbEmails.append(user.getEmail());
            sbEmails.append(";");

        }
        strEmailAndMobiles[0] = sbEmails.substring(0, sbEmails.length() - 1);
        strEmailAndMobiles[1] = sbMobiles.substring(0, sbMobiles.length() - 1);
        return strEmailAndMobiles;
    }

    @Override
    public List<?> queryListByType(List<Integer> type) {
        String sql = "SELECT ID,MOBILE FROM T_SYS_USER";
        if (type.size() > 0) {
            for (int i = 0; i < type.size(); i++) {
                if (i + 1 == 1) {
                    sql = sql + " where ACCOUNT_TYPE = " + type.get(i);
                } else {
                    sql = sql + " ACCOUNT_TYPE = " + type.get(i);
                }
                if (i + 1 < type.size()) {
                    sql = sql + " or ";
                }
            }
        }
        Query query = genericDAO.getSession().createSQLQuery(sql);
        return query.list();
    }

    @Override
    public List<?> queryListByType(String type) {
        String hql = " from User where accountType = ?  ";
        Query query = genericDAO.getSession().createSQLQuery(hql);
        query.setString(0, type);
        return query.list();
    }

    public static void main(String[] args) {
        System.out.println(SparkLib.encodePassword("123abc", Constant.MAX_PASSWORD));
        System.out.println(SparkLib.decodePassword("QOQIQINJNYNXKEKJIIJZIPJWIKJXIFISJDIRJEIQJBITIYJFILIQIPIQILIQ"));
        System.out.println(SparkLib.decodePassword("RURQRQQQRPQRQVQSQVNPQIOJPTPLPTPYPZPXPUPAPRPGPEOXLAKQKULJLIKN"));
        System.out.println(SparkLib.decodePassword("NMOQQLQKPRPPPVKIIJJYIIKDIKJXIFISJDIRJEIQJBITIYJFILIQIPIQILIQ"));
    }
    
	/* (非 Javadoc)
	 * <p>Title: getTotal</p>
	 * <p>Description: </p>
	 * @param userName
	 * @return 
	 * @see com.frame.system.service.UserService#getTotal(java.lang.String)
	 */
	@Override
	public long getTotal(String userName) {
		List<Object> params = new ArrayList<Object>();
		String hql = "select count(u) from User u where 1=1 and u.isEnabled <> '0'";
		if(StringUtil.isNotEmpty(userName)){
			hql += " and u.name like ?";
			params.add("%"+userName+"%");
		}
		return genericDAO.getGenericCountByHql(hql, params.size() > 0 ? params.toArray() : null);
	}

	/* (非 Javadoc)
	 * <p>Title: getList</p>
	 * <p>Description: </p>
	 * @param userName
	 * @param offset
	 * @param limit
	 * @return 
	 * @see com.frame.system.service.UserService#getList(java.lang.String, int, int)
	 */
	@Override
	public List<User> getList(String userName, int start, int pageSize) {
		List<Object> params = new ArrayList<Object>();
		String hql = "from User u where 1=1 and u.isEnabled <> '0'";
		if(StringUtil.isNotEmpty(userName)){
			hql += " and u.name like ?";
			params.add("%"+userName+"%");
		}
		hql += " order by u.id";
		return genericDAO.getGenericByPosition(hql, start, pageSize, params.size() > 0 ? params.toArray() : null);
	}

	/* (非 Javadoc)
	 * <p>Title: modifyState</p>
	 * <p>Description: </p>
	 * @param uid
	 * @param state
	 * @return 
	 * @see com.frame.system.service.UserService#modifyState(java.lang.String, java.lang.String)
	 */
	@Override
	public int modifyState(String uid, String state) {
		String hql = "update User u set u.isEnabled = ? where u.id = ?";
		return genericDAO.execteBulk(hql, new Object[]{state, uid});
	}

	/* (非 Javadoc)
	 * <p>Title: saveOrUpdateRole</p>
	 * <p>Description: </p>
	 * @param user 
	 * @see com.frame.system.service.UserService#saveOrUpdateRole(com.frame.system.po.User)
	 */
	@Override
	public void saveOrUpdateRole(User user) {
		User u = new User();
		if(StringUtil.isNotEmpty(user.getId())){
			u=genericDAO.get(User.class, user.getId());
		}else {
			u.setId(RandomGUID.newGuid());
		}
		u.setAccount(user.getAccount());
		u.setName(user.getName());
		u.setMobile(user.getMobile());
		u.setTele(user.getTele());
		u.setIsEnabled(user.getIsEnabled());
		u.setEmail(user.getEmail());
		u.setPwd(SparkLib.encodePassword(user.getPwd(), Constant.MAX_PASSWORD));
		genericDAO.merge(u);
	}
}
