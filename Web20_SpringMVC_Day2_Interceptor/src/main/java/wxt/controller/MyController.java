package wxt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/con")
public class MyController {

	@RequestMapping("/1")
	public String testInterceptor() {
		System.out.println("controller方法执行了..");
		return "success";
	}
}
