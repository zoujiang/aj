package com.frame.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.core.vo.DataModel;
import com.frame.log.service.LogBizOprService;
import com.frame.system.po.RoleResource;
import com.frame.system.service.ResourceService;
import com.frame.system.service.RoleManager;
import com.frame.system.service.RoleService;
import com.frame.system.vo.ResourceExtForm;
import com.frame.system.vo.RoleExtForm;
import com.frame.system.vo.TreeModel;
@Component
public class RoleManagerImpl  implements RoleManager{
	
	@Autowired
	private RoleService roleServices;
	
	@Autowired
	private ResourceService resourceService;
	

	@Override
	public void addRole(RoleExtForm roleExtForm) {
		roleServices.addRole(roleExtForm);
	}

	@Override
	public void exeAssignResouces(String roleId,String[] resourceIds) {
		roleServices.exeAssignResouces(roleId,resourceIds);
		
	}

	@Override
	public void deleteRole(String... roleIds) {
		roleServices.deleteRole(roleIds);
	}

	@Override
	public RoleExtForm getRoleById(String roleId) {
		return roleServices.getRoleById(roleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataModel getRoles(String... args) {
		DataModel model = new DataModel();
		model.setRows(roleServices.getRoles(args));
		return model;
	}

	@Override
	public void updateRole(RoleExtForm roleExtForm) {
		 roleServices.updateRole(roleExtForm);
	}

	@Override
	public TreeModel getResourceOfRole(String roleId) {
		
		List<ResourceExtForm> r = resourceService.getResources();
		List<RoleResource> ur = roleServices.getResoucerOfRole(roleId);
		
		for(ResourceExtForm ref:r){
			if(ur!=null && ur.size()>0){
				boolean checked = false;
				for(RoleResource uref:ur){
					if(ref.getId().equals(uref.getResourceId())){
						ref.setChecked(true);
						checked = true;
						break;
					}
				}
				if(!checked)ref.setChecked(false);
			}else{
				ref.setChecked(false);
			}
		}
		return roleServices.generateResourceTree(r);
		 
	}

	@Override
	public DataModel getUsersByRoleIds(String roleIds) {
		// TODO Auto-generated method stub
		return roleServices.getUsersByRoleIds(roleIds);
	}

}
