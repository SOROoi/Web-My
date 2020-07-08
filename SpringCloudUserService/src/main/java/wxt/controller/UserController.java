package wxt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wxt.bean.User;
import wxt.mapper.UserMapper;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserMapper mapper;
	
	@GetMapping("/{id}")
	public User find(@PathVariable("id") Long id) {
		try {
			Thread.sleep(3500l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapper.selectByPrimaryKey(id);
	}
}
