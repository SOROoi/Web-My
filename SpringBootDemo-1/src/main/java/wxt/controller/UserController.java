package wxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wxt.bean.UserInfo;
import wxt.service.IUserService;

@RestController
public class UserController {

	@Autowired
	private IUserService service;
	
	@RequestMapping("/find")
	public String find() {
		List<UserInfo> list = service.findAll();
		for(UserInfo user: list) {
			System.out.println(user);
		}
		return "哈哈哈";
	}
}
