package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.Permission;

@Repository
public interface IPermissionDao {

	@Select("select * from permission where id in (select permissionId from role_permission where roleId = #{id})")
	public List<Permission> findByRoleId(String id) throws Exception;

	@Select("select * from permission")
	public List<Permission> findAll() throws Exception;

	@Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
	public void save(Permission permission) throws Exception;

	@Select("select * from permission where id not in (select permissionId from role_permission where roleId = #{roleId})")
	public List<Permission> findOtherPermission(String roleId) throws Exception;
	
}
