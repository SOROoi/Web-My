package wxt.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import wxt.bean.UserInfo;

public interface IUserService extends UserDetailsService{

	public List<UserInfo> findAll() throws Exception;
	
	public void save(UserInfo user) throws Exception;
	
	public void addUser() throws Exception;
	
	public UserInfo findById(String id) throws Exception;

	public void addRoleToUser(String userId, String[] roleId) throws Exception;
	
	String findId(String username) throws Exception;

}
