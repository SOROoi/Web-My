package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wxt.bean.User;
import wxt.dao.UserDao;
import wxt.service.IService;

@Service("service")
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class ServiceImp implements IService {

	@Autowired
	UserDao dao;
	
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<User> findAll() {
		List<User> list = dao.findAll();
		System.out.println("执行service中方法");
		return list;
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void add(User user) {
		dao.add(user);
	}

}
