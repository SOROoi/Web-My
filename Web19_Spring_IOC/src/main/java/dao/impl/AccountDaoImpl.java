package dao.impl;

import org.springframework.stereotype.Repository;

import dao.AccountDao;

@Repository(value="accountDao1")
public class AccountDaoImpl implements AccountDao {

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("更新数据");
	}

}
