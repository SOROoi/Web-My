package dao.imp;



import java.util.List;

import org.apache.ibatis.annotations.Select;

import bean.User;

public interface ZhuJieUserDao {
	
	/**
	 * 查找所有用户
	 * @return
	 */
	@Select("select * from user")
	List<User> findAll();
}
