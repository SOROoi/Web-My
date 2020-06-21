package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wxt.bean.Role;
import wxt.dao.IRoleDao;
import wxt.service.IRoleService;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class RoleService implements IRoleService {

	@Autowired
	private IRoleDao dao;
	
	//查找所有角色
	@Override
	public List<Role> findAll() throws Exception {
		// TODO Auto-generated method stub
		List<Role> roles = dao.findAll();
		return roles;
	}

	//添加角色
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void save(Role role) throws Exception {
		// TODO Auto-generated method stub
		dao.save(role);
	}


	//查找用户没有的role
	@Override
	public List<Role> findOtherRole(String id) throws Exception {
		// TODO Auto-generated method stub
		List<Role> roles = dao.findOtherRole(id);
		return roles;
	}

	//查找一个角色信息
	@Override
	public Role findById(String roleId) throws Exception {
		// TODO Auto-generated method stub
		Role role = dao.findById(roleId);
		return role;
	}

	//关联角色-权限，向role_permission表中插入permissionId,roleId
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void addPermissionToRole(String[] permissionId, String roleId) throws Exception {
		// TODO Auto-generated method stub
		for(String id:permissionId) {
			dao.addPermissionToRole(id,roleId);
		}
	}
	
	

}
