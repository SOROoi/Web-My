package wxt.service;

import java.util.List;

import wxt.bean.Role;

public interface IRoleService {

	public List<Role> findAll() throws Exception;

	public void save(Role role) throws Exception;

	public List<Role> findOtherRole(String id) throws Exception;

	public Role findById(String roleId) throws Exception;

	public void addPermissionToRole(String[] permissionId, String roleId) throws Exception;
}
