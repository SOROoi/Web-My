package wxt.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import wxt.bean.Role;
import wxt.bean.UserInfo;
import wxt.service.IRoleService;
import wxt.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService service;
	@Autowired
	private IRoleService roleService;
	
	//查找所有用户
	@RequestMapping("/findAll")
	@RolesAllowed("ADMIN")	//JSR250
	public ModelAndView findAll() throws Exception {
		ModelAndView mv = new ModelAndView();
		List<UserInfo> userList = service.findAll();
		mv.addObject("userList", userList);
		mv.setViewName("user-list");
		return mv;
	}
	
	//添加用户
	@RequestMapping("/save")
	public String save(UserInfo user) throws Exception {
		service.save(user);
		return "redirect:findAll";
	}
	
	//添加一个初始用户"www 123456"
	@RequestMapping("/addUser")
	public String addUser() throws Exception {
		service.addUser();
		return "main";	//添加初始账户，返回主页面
	}
	
	//查询用户详情
	@RequestMapping("/findById")
	public ModelAndView findById(String id) throws Exception {
		ModelAndView mv = new ModelAndView();
		UserInfo user = service.findById(id);
		mv.addObject("user", user);
		mv.setViewName("user-show");
		return mv;
	}
	
	//查询用户可以添加的角色
	@RequestMapping("/findUserByIdAndAllRole")
	public ModelAndView findUserByIdAndAllRole(String id) throws Exception {
		ModelAndView mv = new ModelAndView();
		UserInfo  user = service.findById(id);	//通过userId 查找用户信息
		List<Role> roleList = roleService.findOtherRole(id);	//通过userId 查找未添加的List<Role>
		mv.addObject("user", user);
		mv.addObject("roleList", roleList);
		mv.setViewName("user-role-add");
		return mv;
	}
	
	//为用户添加角色(在users_role 表中添加记录)		参数绑定userId 和List<String> roleId
	@RequestMapping("/addRoleToUser")
	public String addRoleToUser(@RequestParam(value="userId")String userId,@RequestParam(value="ids")String[] roleId) throws Exception {
		service.addRoleToUser(userId,roleId);
		return "redirect:findAll";
	}
	
	
}
