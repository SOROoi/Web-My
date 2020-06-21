package wxt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 该类为有异常的控制器 方法中的方法会出现异常。
 * 
 * @author asus pc
 *
 */
@Controller
@RequestMapping("/con")
public class ExceptionController {

	@RequestMapping("/1")
	public String exception1() {
		int a = 1 / 0;

		return "success";
	}
}
