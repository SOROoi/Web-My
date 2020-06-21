package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.Role;

@Repository
public interface IRoleDao {

	@Select("select * from role where id in (select roleId from users_role where userId = #{id})")
	@Results({
		@Result(id = true, property = "id",column = "id"),
		@Result(property = "roleName",column = "roleName"),
		@Result(property = "roleDesc",column = "roleDesc"),
		@Result(property = "permissions",column = "id",javaType = List.class, many = @Many(select="wxt.dao.IPermissionDao.findByRoleId")),
	})
	public List<Role> findByUserId(String id) throws Exception;
	
	@Select("select * from role")
	public List<Role> findAll() throws Exception;

	@Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
	public void save(Role role) throws Exception;

	@Select("select * from role where id not in(select roleId from users_role where userId = #{id})")
	public List<Role> findOtherRole(String id) throws Exception;

	@Select("select * from role where id = #{roleId}")
	@Results({
		@Result(id=true,property="id",column="id"),
		@Result(property="roleName",column="roleName"),
		@Result(property="roleDesc",column="roleDesc"),
		@Result(property="permissions",column="id",javaType=List.class,many=@Many(select="wxt.dao.IPermissionDao.findByRoleId")),
		
	})
	public Role findById(String roleId) throws Exception;

	@Insert("insert into role_permission values(#{id},#{roleId})")
	public void addPermissionToRole(@Param("id")String id, @Param("roleId")String roleId) throws Exception;
}
