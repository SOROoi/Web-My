package wxt.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import wxt.bean.Account;
import wxt.dao.AccountDao;

public class AccountDaoImpl implements AccountDao {

	private QueryRunner runner;

	public void setRunner(QueryRunner runner) {
		this.runner = runner;
	}
	
	@Override
	public List<Account> selectAll() {
		
		try {
			return runner.query("select * from account", new BeanListHandler<Account>(Account.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("持久层查找所有失败");
		}
		
	}

	@Override
	public Account select(Integer id) {
		
		try {
			return runner.query("select * from account where id = ?", new BeanHandler<Account>(Account.class),id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("持久层查找失败");
		}
		
	}

	@Override
	public void insert(Account account) {
		// TODO Auto-generated method stub
		try {
			runner.update("insert into account(name,money) values(?,?)",account.getName(),account.getMoney());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("持久层插入数据失败");
		}
	}

	@Override
	public void update(Account account) {
		// TODO Auto-generated method stub
		try {
			runner.update("update account set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("持久层插入数据失败");
		}
	}
	
	@Override
	public void delete(Integer id) {
		
		try {
			runner.update("delete from account where id=?",id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("持久层插入数据失败");
		}
	}


}
