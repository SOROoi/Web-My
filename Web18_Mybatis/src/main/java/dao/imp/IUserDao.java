package dao.imp;

/*
 * 	用户的持久层接口
 * 
 */

import java.util.List;

import org.apache.ibatis.annotations.Select;

import bean.User;

public interface IUserDao {
	/**
	 * 查询所有操作
	 */
	//@Select("select * from user where sex = '男'")
	List<User> findAll();

	List<User> findOne(User user);
}
