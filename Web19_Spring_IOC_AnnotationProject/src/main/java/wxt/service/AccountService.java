package wxt.service;

import java.util.List;

import wxt.bean.Account;

/**
 * 业务层接口
 * @author asus pc
 *
 */
public interface AccountService {
	/**
	 * 查找所有Account
	 * @return
	 */
	List<Account> findAll();
	
	/**
	 * 通过id查找Account
	 * @param id
	 * @return
	 */
	Account findOne(Integer id);
	
	/**
	 * 保存账户
	 * @param account
	 */
	void saveOne(Account account);
	
	/**
	 * 修改账户信息
	 * @param account
	 */
	void updateOne(Account account);
	
	/**
	 * 删除账户
	 * @param account
	 */
	void deleteOne(Integer id);
}
