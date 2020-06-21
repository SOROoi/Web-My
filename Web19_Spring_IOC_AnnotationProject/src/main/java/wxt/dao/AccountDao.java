package wxt.dao;

import java.util.List;

import wxt.bean.Account;

public interface AccountDao {
	
	/**
	 * 查找所有账户
	 * @return
	 */
	List<Account> selectAll();
	
	/**
	 * 查找一个账户
	 * @param id
	 * @return
	 */
	Account select(Integer id);
	
	/**
	 * 添加账户
	 */
	void insert(Account account);
	
	/**
	 * 删除账户
	 */
	void delete(Integer id);
	
	/**
	 * 更改账户
	 */
	void update(Account account);
}
