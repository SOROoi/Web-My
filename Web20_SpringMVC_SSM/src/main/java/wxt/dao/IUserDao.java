package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import wxt.bean.User;

/**
 * 操作数据库的接口
 * @author asus pc
 *
 */
@Repository
public interface IUserDao {
	
	/**
	 * 保存用户信息到数据库
	 */
	@Insert("insert into user(name,money) values(#{name},#{money})")	//mybatis中ognl表达式,#{}表示传入参数
	void insert(User user);
	
	/**
	 * 数据库删除用户
	 */
	@Delete("delete from user where id = #{id}")
	void delete(int id);
	
	/**
	 * 更改用户信息
	 * @param user
	 */
	@Update("update user set name=#{name},money=#{money} where id=#{id}")
	void update(User user);
	
	/**
	 * 查找一个用户
	 * @param id
	 * @return
	 */
	@Select("select * from user where id = #{id}")
	User selectUser(int id);
	
	/**
	 * 查询所有用户
	 * @return
	 */
	@Select("select * from user")
	List<User> selectAll();
	
	
}
