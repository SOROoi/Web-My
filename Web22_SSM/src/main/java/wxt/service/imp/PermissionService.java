package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wxt.bean.Permission;
import wxt.dao.IPermissionDao;
import wxt.service.IPermissionService;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class PermissionService implements IPermissionService {

	@Autowired
	private IPermissionDao dao;
	
	//查找所有permission
	@Override
	public List<Permission> findAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	//添加权限
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void save(Permission permission) throws Exception {
		// TODO Auto-generated method stub
		dao.save(permission);
	}

	//查找一个角色未添加的权限
	@Override
	public List<Permission> findOtherPermission(String roleId) throws Exception {
		// TODO Auto-generated method stub
		List<Permission> list = dao.findOtherPermission(roleId);
		return list;
	}

}
