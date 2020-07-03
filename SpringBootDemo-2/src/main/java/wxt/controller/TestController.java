package wxt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wxt.bean.User;
import wxt.service.IUserService;

@RestController
public class TestController {

	@Autowired
	private IUserService service;
	
	@RequestMapping("/test/{id}")
	public User test1(@PathVariable Long id) {
		User user =service.service(id);
		return user;
	}
}
