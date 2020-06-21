package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import wxt.bean.User;

@Repository("userDao")
public interface UserDao {

	@Select("select * from user")
	@Results(id = "resultMap", value = { 
			@Result(id = true, property = "id", column = "id"), 
			@Result(property = "name", column = "username"), 
			@Result(property = "birth", column = "birthday"), 
			@Result(property = "sex", column = "sex"), 
			@Result(property = "addr", column = "address")
			})
	public List<User> findAll();
	
	@Insert("insert into user(username,birthday,sex,address) values(#{name},#{birth},#{sex},#{addr})")
	public void add(User user);
}
