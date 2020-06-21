package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.UserInfo;

@Repository
public interface IUserDao {
	
	@Select("select * from users where username = #{userName}")
	@Results({
		@Result(id = true,property="id",column="id"),
		@Result(property="username",column="username"),
		@Result(property="email",column="email"),
		@Result(property="password",column="password"),
		@Result(property="phoneNum",column="phoneNum"),
		@Result(property="status",column="status"),
		@Result(property="roles",column="id",javaType=List.class,many = @Many(select="wxt.dao.IRoleDao.findByUserId")),
	})
	public UserInfo findByName(String userName) throws Exception;
	
	@Select("select * from users")
	public List<UserInfo> findAll() throws Exception;
	
	@Insert("insert into users(username,email,password,phoneNum,status) values(#{username},#{email},#{password},#{phoneNum},#{status})")
	public void save(UserInfo user) throws Exception;
	
	@Select("select * from users where id = #{id}")
	@Results({
		@Result(id = true,property="id",column="id"),
		@Result(property="username",column="username"),
		@Result(property="email",column="email"),
		@Result(property="password",column="password"),
		@Result(property="phoneNum",column="phoneNum"),
		@Result(property="status",column="status"),
		@Result(property="roles",column="id",javaType=List.class,many = @Many(select="wxt.dao.IRoleDao.findByUserId")),	
	})
	public UserInfo findById(String id) throws Exception;

	@Insert("insert into users_role values(#{userId},#{roleId})")
	public void addRoleToUser(@Param("userId")String user, @Param("roleId")String role) throws Exception;
}
