package dao;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import bean.User;

public interface CRUDdao {
	
	@Select("select * from user")
	@Results(id="userMap",value= {
			@Result(id=true,column="id",property="id"),
			@Result(column="username",property="name"),
			@Result(column="sex",property="sex"),
			@Result(column="birthday",property="birth"),
			@Result(column="address",property="addr")
	})
	public List<User> findAll();
	
	@Insert("insert into user(username,birthday,sex,address) values(#{name},#{birth},#{sex},#{addr})")
	public void add(User user);
}
