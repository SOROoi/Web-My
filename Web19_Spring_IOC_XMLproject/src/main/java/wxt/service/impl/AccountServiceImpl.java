package wxt.service.impl;

import java.util.List;

import wxt.bean.Account;
import wxt.dao.AccountDao;
import wxt.service.AccountService;

public class AccountServiceImpl implements AccountService {

	AccountDao dao;
	
	public void setDao(AccountDao dao) {
		this.dao = dao;
	}


	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return dao.selectAll();
	}

	
	@Override
	public Account findOne(Integer id) {
		// TODO Auto-generated method stub
		return dao.select(id);
	}

	@Override
	public void saveOne(Account account) {
		// TODO Auto-generated method stub
		dao.insert(account);
	}

	@Override
	public void updateOne(Account account) {
		// TODO Auto-generated method stub
		dao.update(account);
	}

	@Override
	public void deleteOne(Integer id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}
	
}
