package wxt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

	@Autowired
	RedisTemplate<String, String> template;
	
	@RequestMapping("/redis")
	public String method() {

		return template.toString();
	}
}
