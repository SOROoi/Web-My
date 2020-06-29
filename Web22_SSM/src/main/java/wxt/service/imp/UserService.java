package wxt.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wxt.bean.Role;
import wxt.bean.UserInfo;
import wxt.dao.IUserDao;
import wxt.service.IUserService;

@Service("userService")		//此处 id 写入 spring-security.xml的 user-service-ref属性中
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class UserService implements IUserService {
	
	@Autowired
	private IUserDao dao;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//spring-security 的校验service，实现UserDetailsService 接口
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 根据用户名查找用户信息
		UserInfo userInfo = null;
		try {
			userInfo = dao.findByName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//获取用户的所有角色
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		List<Role> roles =  userInfo.getRoles();
		for(Role role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		}
		//将UserInfo信息封装入User(加密前)	用户名  密码  可用状态  。。。 角色List
//		User user= new User(username, "{noop}"+userInfo.getPassword(), userInfo.getStatus()==0 ? false : true,
//				true, true, true, authorities);
		
		//将UserInfo信息封装入User(加密后)
		//new User(String 用户名,String 密码, int 可用状态,true, true, true, List<SimpleGrantedAuthority> 角色List)
		User user= new User(username, userInfo.getPassword(), userInfo.getStatus()==0 ? false : true,
				true, true, true, authorities);
		
		return user;
	}

	//查找所有用户
	@Override
	public List<UserInfo> findAll() throws Exception{
		List<UserInfo> list = dao.findAll();
		return list;
	}

	//添加用户	须在保存到数据库前 对密码进行加密
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void save(UserInfo user) throws Exception {
		//利用BCryptPasswordEncoder对密码进行加盐加密，存入数据库
		String password = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(password);
		dao.save(user);
	}

	//添加一个初始用户"www 123456"
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void addUser() throws Exception {
		// TODO Auto-generated method stub
		UserInfo user = new UserInfo();
		user.setUsername("www");	//设置用户名
		user.setEmail("wxt@wxt.com");
		user.setPhoneNum("183********");
		user.setPassword(new BCryptPasswordEncoder().encode("123456"));	//设置密码，对密码进行BCrypt加密
		user.setStatus(1);		//设置状态为1，可用
		
		Role role = new Role();	//添加"ROLE_USER"角色
		role.setId("0");
		role.setRoleName("ROLE_USER");
		ArrayList<Role> roles = new ArrayList<Role>();
		roles.add(role);
		
		user.setRoles(roles);	//设置角色
		dao.save(user);
	}

	//查找一个用户
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public UserInfo findById(String id) throws Exception {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	//在users_role 表中添加 userId, roleId
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void addRoleToUser(String userId, String[] roleId) throws Exception {
		// TODO Auto-generated method stub
		for(String id:roleId) {
			dao.addRoleToUser(userId, id);		//遍历roleId 数组，将每一个roleId，与userId 一起插入数据库中
		}
	}

	//通过username查找用户id
	@Override
	public String findId(String username) throws Exception {
		// TODO Auto-generated method stub
		UserInfo user = dao.findByName(username);
		return user.getId();
	}
		



}
