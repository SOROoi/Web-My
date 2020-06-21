package wxt.service;

import java.util.List;

import wxt.bean.User;

/**
 * 业务层接口
 * @author asus pc
 *
 */
public interface IService {
	
	/**
	 * 增
	 * @param user
	 */
	void save(User user);
	
	/**
	 * 删
	 * @param id
	 */
	void remove(int id);
	
	/**
	 * 改
	 * @param user
	 */
	void modify(User user);
	
	/**
	 * 查一个
	 * @param id
	 * @return
	 */
	User findUser(int id);
	
	/**
	 * 查所有
	 * @return
	 */
	List<User> findAll();
}
