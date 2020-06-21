package wxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import wxt.bean.Permission;
import wxt.service.IPermissionService;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private IPermissionService service;

	// 查找所有permission
	@RequestMapping("/findAll")
	//@PreAuthorize("authentication.principal.username == '熊大'")	spel表达式注解控制权限
	public ModelAndView findAll() throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Permission> list = service.findAll();
		mv.addObject("permissionList", list);
		mv.setViewName("permission-list");
		return mv;
	}

	// 添加权限
	@RequestMapping("/save")
	public String save(Permission permission) throws Exception {
		service.save(permission);
		return "redirect:findAll";
	}

}
