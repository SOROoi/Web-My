package wxt.service;

import java.util.List;

import wxt.bean.Permission;

public interface IPermissionService {

	public List<Permission> findAll() throws Exception;

	public void save(Permission permission) throws Exception;

	public List<Permission> findOtherPermission(String roleId) throws Exception;

	
}
