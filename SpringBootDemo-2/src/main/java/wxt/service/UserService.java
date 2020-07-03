package wxt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wxt.bean.User;
import wxt.dao.UserDao;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserDao dao;
	
	@Override
	public User service(Long id) {
		// TODO Auto-generated method stub
		return dao.selectByPrimaryKey(id);
	}

	
}
