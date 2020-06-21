package wxt.service;

import java.util.List;

import wxt.bean.User;

public interface IService {

	public List<User> findAll();
	
	public void add(User user);
}
