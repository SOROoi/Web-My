package wxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import wxt.bean.Permission;
import wxt.bean.Role;
import wxt.service.IPermissionService;
import wxt.service.IRoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private IRoleService service;
	@Autowired
	private IPermissionService permissionService;
	
	//查询所有角色
	@RequestMapping("/findAll")
	@Secured("ROLE_USER")	//spring-security 自带注解	需写全权限名
	public ModelAndView findAll() throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Role> roles = service.findAll();
		mv.addObject("roleList", roles);
		mv.setViewName("role-list");
		return mv;
	}

	//添加角色
	@RequestMapping("/save")
	public String save(Role role) throws Exception{
		service.save(role);
		return "redirect:findAll";
	}
	
	//查找角色信息
	@RequestMapping("/findById")
	public ModelAndView findById(@RequestParam("id")String roleId) throws Exception{
		ModelAndView mv = new ModelAndView();
		Role role = service.findById(roleId);
		mv.addObject("role", role);
		mv.setViewName("role-show");
		return mv;
	}
	
	//查找角色信息 和 角色未添加的权限List<Permission>
	@RequestMapping("/findRoleByIdAndAllPermission")
	public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name="id")String roleId) throws Exception {
		ModelAndView mv = new ModelAndView();
		Role role = service.findById(roleId);	//查找角色信息
		List<Permission> list = permissionService.findOtherPermission(roleId);	//查找未添加的权限
		mv.addObject("role", role);
		mv.addObject("permissionList", list);
		mv.setViewName("role-permission-add");
		return mv;
	}
	
	//关联角色-权限信息，即向role_permission表中插入permissionId,roleId
	@RequestMapping("/addPermissionToRole")
	public String addPermissionToRole(@RequestParam(name="ids")String[] permissionId,@RequestParam(name="roleId")String roleId) throws Exception{
		service.addPermissionToRole(permissionId,roleId);
		return "redirect:findAll";
	}
	
	
}
