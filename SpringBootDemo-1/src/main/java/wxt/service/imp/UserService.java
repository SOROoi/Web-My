package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wxt.bean.UserInfo;
import wxt.dao.IUserDao;
import wxt.service.IUserService;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class UserService implements IUserService{

	@Autowired
	private IUserDao dao;
	
	@Override
	public List<UserInfo> findAll() {
		List<UserInfo> list = dao.findAll();
		return list;
	}

}
