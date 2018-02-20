package com.frame.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.core.dao.GenericDAO;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.ListUtils;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.StringUtil;
import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;
import com.frame.system.po.Role;
import com.frame.system.po.RoleResource;
import com.frame.system.po.User;
import com.frame.system.service.RoleService;
import com.frame.system.vo.ResourceExtForm;
import com.frame.system.vo.RoleExtForm;
import com.frame.system.vo.TreeModel;
import com.frame.system.vo.TreeModel.AttributesModel;
import com.frame.system.vo.UserExtForm;


@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private GenericDAO genericDAO;
	 
	@Autowired
	private LogBizOprService logBizOprService;
	
	@Override
	public void addRole(RoleExtForm roleExtForm) {
		Role role = BeanUtils.copyProperties(Role.class, roleExtForm);
		role.setId(RandomGUID.newGuid());
		genericDAO.save(role);
		logBizOprService.saveLog("角色管理","1" , "新增角色(角色名称:" + role.getTitle() + ")" , "t_sys_role", role.toString());
	}

	@Override
	public void exeAssignResouces(String roleId,String[] resourceIds) {
		 String sql = "delete from RoleResource rr where rr.roleId='"+roleId+"' ";
		 genericDAO.execteBulk(sql);
		 RoleResource cmsRoleResource;
		 for(String rrid:resourceIds){
			 if(StringUtil.isNotEmpty(rrid)){
				 cmsRoleResource = new RoleResource();
				 cmsRoleResource.setId(RandomGUID.newGuid());
				 cmsRoleResource.setRoleId(roleId);
				 cmsRoleResource.setResourceId(rrid);
				 genericDAO.save(cmsRoleResource);
			 }
		 }
		logBizOprService.saveLog("角色管理","2" , "修改角色资源(角色ID：" + roleId +")", "t_sys_roleresource", roleId);

	}

	@Override
	public void deleteRole(String...roleIds) {
		 String sql = null;
		for(String id:roleIds){
			 sql = "delete from RoleResource rr where rr.roleId='"+id+"'  ";
			 genericDAO.execteBulk(sql);
			 genericDAO.delete(Role.class,id);
			logBizOprService.saveLog("角色管理","3" , "删除角色(角色ID:" + id + ")", "t_sys_roleresource", roleIds.toString());

		}

	}

	@Override
	public RoleExtForm getRoleById(String roleId) {
		return BeanUtils.copyProperties(RoleExtForm.class,genericDAO.get(Role.class,roleId));
	}

	@Override
	public List<RoleExtForm> getRoles(String... args) {
		String sql = " from Role r  order by r.code";
		return ListUtils.transform(genericDAO.getGenericByHql(sql), RoleExtForm.class);
	}

	@Override
	public List<Role> getRolesByCode(String code) {
		String hql = " from Role r where code='"+code+"'";
		List<Role>  list = this.genericDAO.getGenericByHql(hql);
		return list;
	}


	@Override
	public void updateRole(RoleExtForm roleExtForm) {
		Role role = genericDAO.get(Role.class,roleExtForm.getId());
		role.setCode(roleExtForm.getCode());
		role.setTitle(roleExtForm.getTitle());
		genericDAO.update(role);
		logBizOprService.saveLog("角色管理","2" , "更新角色(角色名称:" + role.getTitle() + ")", "t_sys_roleresource", role.toString());

	}

	@Override
	public List<RoleResource> getResoucerOfRole(String roleId) {
		String sql = " from RoleResource rr where  rr.roleId='"+roleId+"'";
		List<RoleResource> list = genericDAO.getGenericByHql(sql);
		return list;
	}
	
	@Override
	public TreeModel  generateResourceTree(List<ResourceExtForm> resourceExtForms){
		TreeModel root = new TreeModel();
		List<TreeModel> treeModeList = new ArrayList<TreeModel>();
		List<TreeModel> parentList = new ArrayList<TreeModel>();
		HashMap<String,ResourceExtForm> treemMap = new LinkedHashMap<String,ResourceExtForm>();
		HashMap<String, List<ResourceExtForm>> treeMapList =new LinkedHashMap<String, List<ResourceExtForm>>();
		for(ResourceExtForm form:resourceExtForms){
			String parentId = form.getParentId();
			if(!StringUtil.isEmpty(parentId)){
					if(treeMapList.containsKey(parentId)){
						treeMapList.get(parentId).add(form);
					}else{
						List<ResourceExtForm> list = new ArrayList<ResourceExtForm>();
						list.add(form);
						treeMapList.put(parentId,list);
					}
					treemMap.put(form.getId(), form);
			}else{
				TreeModel teemoModel = convertResouce2TreeNode(form);
				treeModeList.add(teemoModel);
				parentList.add(teemoModel);
			}
		}
		
		if(treeModeList.size()>0){
			root.setChildren(treeModeList.toArray(new TreeModel[treeModeList.size()]));
			root.setState("open");
			root.setId("root");
			root.setText("资源管理");
		}
		
		boolean hasChidren = true;
		List<TreeModel> nextTreeModelList = new ArrayList<TreeModel>();
		Set<String> parentIdKeySet = treeMapList.keySet();
		while(hasChidren){
			hasChidren = false;
			nextTreeModelList.clear();
			for(TreeModel parentTreeModel : parentList){
				Iterator<String> it = parentIdKeySet.iterator();
				while(it.hasNext()){
					String key = it.next();
					if(key.equals(parentTreeModel.getId())){
						List<ResourceExtForm> innerResouceList = treeMapList.get(key);
						List<TreeModel> childrenModelList = new ArrayList<TreeModel>();
						for(ResourceExtForm ref : innerResouceList){
							TreeModel tm = convertResouce2TreeNode(ref);
							tm.setState("open");
							nextTreeModelList.add(tm);
							childrenModelList.add(tm);
							hasChidren = true;
						}
						parentTreeModel.setChildren(childrenModelList.toArray(new TreeModel[childrenModelList.size()]));
						parentTreeModel.setState("open");
					}
				}
				if(!hasChidren){
					parentTreeModel.setState("closed");
				}else{
					/**
					 * 解决树显示问题
					 */
					parentTreeModel.setChecked(false);
				}
			}
			parentList = nextTreeModelList;
		}
		return root;
	}
	
	private TreeModel convertResouce2TreeNode(ResourceExtForm ref){
		TreeModel treeModel = new TreeModel();
		treeModel.setId(ref.getId());
		treeModel.setText(ref.getTitle());
		treeModel.setChecked(ref.isChecked());
		treeModel.setIconCls(ref.getIconCls());
		AttributesModel attributeModel = treeModel.new AttributesModel();
		attributeModel.setCode(ref.getCode());
		attributeModel.setParentId(ref.getParentId());
		attributeModel.setHref(ref.getHref());
		attributeModel.setIconCls(ref.getIconCls());
		treeModel.setAttributes(attributeModel);
		return treeModel;
	}

	@Override
	public DataModel<UserExtForm> getUsersByRoleIds(String roleIds) {
		if (StringUtil.isEmpty(roleIds)) return new DataModel<UserExtForm>();
		DataModel<UserExtForm> model = new DataModel<UserExtForm>();
		String[] roleIdArr = roleIds.split(",");
		List<User> userlist = new ArrayList<User>();
		for (int i = 0; i < roleIdArr.length; i++) {
			String roleId = roleIdArr[i];
			Role po = genericDAO.get(Role.class, roleId);
			if (null != po) {
				Set<User> userSet = po.getUsers();
				Iterator<User> it = userSet.iterator();
				while (it.hasNext()) {
					User  user = (User) it.next();
					if ("1".equals(user.getIsEnabled()) || StringUtil.isEmpty(user.getIsEnabled())) {
						userlist.add(user);
					}
				}
			}
		}
		if (userlist.size()>0) {
			model.setRows(ListUtils.transform(userlist, UserExtForm.class));
		}
		return model;
	}
	

	/* (非 Javadoc)
	 * <p>Title: getRole</p>
	 * <p>Description: </p>
	 * @param rid 
	 * @see com.frame.system.service.RoleService#getRole(java.lang.String)
	 */
	@Override
	public Role getRole(String rid) {
		return genericDAO.get(Role.class, rid);
	}

	/* (非 Javadoc)
	 * <p>Title: modifyState</p>
	 * <p>Description: </p>
	 * @param rid
	 * @param state 
	 * @see com.frame.system.service.RoleService#modifyState(java.lang.String, java.lang.String)
	 */
	@Override
	public int modifyState(String rid, String state) {
		String hql = "update Role r set r.state = ? where r.id = ?";
		return genericDAO.execteBulk(hql, new Object[]{state, rid});
	}

	/* (非 Javadoc)
	 * <p>Title: getTotal</p>
	 * <p>Description: </p>
	 * @param roleName
	 * @return 
	 * @see com.frame.system.service.RoleService#getTotal(java.lang.String)
	 */
	@Override
	public long getTotal(String roleName) {
		List<Object> params = new ArrayList<Object>();
		String hql = "select count(r) from Role r where 1=1";
		if(StringUtil.isNotEmpty(roleName)){
			hql += " and r.title like ?";
			params.add("%"+roleName+"%");
		}
		return genericDAO.getGenericCountByHql(hql, params.size() > 0 ? params.toArray() : null);
	}

	/* (非 Javadoc)
	 * <p>Title: getList</p>
	 * <p>Description: </p>
	 * @param roleName
	 * @param offset
	 * @param limit
	 * @return 
	 * @see com.frame.system.service.RoleService#getList(java.lang.String, int, int)
	 */
	@Override
	public List<Role> getList(String roleName, int start, int pageSize) {
		List<Object> params = new ArrayList<Object>();
		String hql = "from Role r where 1=1";
		if(StringUtil.isNotEmpty(roleName)){
			hql += " and r.title like ?";
			params.add("%"+roleName+"%");
		}
		hql += " order by r.id";
		return genericDAO.getGenericByPosition(hql, start, pageSize, params.size() > 0 ? params.toArray() : null);
	}

	/* (非 Javadoc)
	 * <p>Title: saveOrUpdateRole</p>
	 * <p>Description: </p>
	 * @param role 
	 * @see com.frame.system.service.RoleService#saveOrUpdateRole(com.frame.system.po.Role)
	 */
	@Override
	public void saveOrUpdateRole(Role role) {
		Role r = new Role();
		if(StringUtil.isNotEmpty(role.getId())){
			r=genericDAO.get(Role.class, role.getId());
		}else {
			r.setId(RandomGUID.newGuid());
		}
		r.setTitle(role.getTitle());
		r.setCode(role.getCode());
		r.setState(role.getState());
		genericDAO.merge(r);
	}

	/* (非 Javadoc)
	 * <p>Title: getListAll</p>
	 * <p>Description: </p>
	 * @return 
	 * @see com.frame.system.service.RoleService#getListAll()
	 */
	@Override
	public List<Role> getListAll() {
		String hql = "from Role r where 1=1";
		hql += " order by r.id";
		return genericDAO.getGenericByHql(hql, null);
	}
}
