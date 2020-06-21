package wxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import wxt.bean.User;
import wxt.service.imp.ServiceImp;

@Controller
@RequestMapping("/con")
public class MyController {
	// 注入ServiceImp
	@Autowired
	ServiceImp service;

	@RequestMapping("/testMvc") // 用于测试mvc环境是否搭建 IOC是否随服务器启动创建
	public String testMvc() {
		System.out.println("mvc环境正常");
		service.findAll();
		return "success";
	}

	@RequestMapping("/zen")		//添加用户
	public String zen(User user) {
		System.out.println(user.getName());
		service.save(user);
		return "success";
	}
	
	@RequestMapping("/shan")	//删除用户
	public String shan(int id) {
		service.remove(id);
		return "success";
	}
	
	@RequestMapping("/gai")		//修改用户信息
	public String gai(User user) {
		service.modify(user);
		return "success";
	}
	
	@RequestMapping("/chaAll")	//查询所有用户
	public ModelAndView chaAll() {
		ModelAndView mv = new ModelAndView();
		List<User> list = service.findAll();

		mv.addObject("data", list);
		mv.setViewName("list");

		return mv;
	}
	
	@RequestMapping("/chaUser")	//查询一个用户
	public ModelAndView chaUser(int id) {
		ModelAndView mv = new ModelAndView();
		User user = service.findUser(id);

		mv.addObject("data", user);
		mv.setViewName("list");

		return mv;
	}
}
