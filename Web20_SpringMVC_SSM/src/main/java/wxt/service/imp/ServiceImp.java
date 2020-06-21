package wxt.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wxt.bean.User;
import wxt.dao.IUserDao;
import wxt.service.IService;

@Service
public class ServiceImp implements IService {

	@Autowired
	IUserDao dao;

	@Override // 增加用户
	public void save(User user) {
		dao.insert(user);
	}

	@Override // 删除用户
	public void remove(int id) {
		dao.delete(id);
	}

	@Override // 修改用户信息
	public void modify(User user) {
		dao.update(user);
	}

	@Override // 查询一个用户的信息
	public User findUser(int id) {
		User user = dao.selectUser(id);
		return user;
	}

	@Override // 查询所有用户的信息
	public List<User> findAll() {
		// TODO Auto-generated method stub
		System.out.println("数据库查找所有用户..");
		List<User> list = dao.selectAll();
		return list;
	}

}
