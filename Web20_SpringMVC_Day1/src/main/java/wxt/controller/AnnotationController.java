package wxt.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import wxt.bean.User;

@Controller
@RequestMapping("/anno")
@SessionAttributes(value={"msg","age"})   // 把msg=美美存入到session域对中
public class AnnotationController {

	/**
     * 获取请求参数的内容
     * @return
     */
	@RequestMapping(value="/testRequestParam")
	public String testRequestParam(@RequestParam(value="name")String n) {
		System.out.println(n);
		return "success";
	}
	
	
	/**
     * 获取到请求体的内容
     * @return
     */
	@RequestMapping(value="/testRequestBody")
	public String testRequestBody(@RequestBody String body) {
		System.out.println(body);
		return "success";
	}

	
	/**
     * PathVariable注解
     * @return
     */
	@RequestMapping(value="/testPathVariable/{sid}")
	public String testPathVariable(@PathVariable(value="sid") String id) {
		System.out.println(id);
		return "success";
	}
	
	
	/**
     * 获取请求头的值
     * @param header
     * @return
     */
	@RequestMapping(value="/testRequestHeader")
	public String testRequestHeader(@RequestHeader(value="Accept") String header) {
		System.out.println(header);
		return "success";
	}
	
	
	 /**
     * 获取Cookie的值
     * @return
     */
	@RequestMapping(value="/testCookieValue")
	public String testCookieValue(@CookieValue(value="JSESSIONID") String cookieValue) {
		System.out.println(cookieValue);
		return "success";
	}

	
	/**
     * ModelAttribute注解
     * @return
     */
/*
	@ModelAttribute
	public User testModelAttribute() {
		User user = new User();
		user.setUserword(123456);
		System.out.println("执行了@ModelAttribute 方法1");
		return user;
	}
	@RequestMapping(value="/testModelAttribute")
	public String testModelAttribute(User user) {
		System.out.println(user);
		return "success";
	}
	
*/
/*
	@ModelAttribute
	public void testModelAttribute(Map<String,User> map) {
		User user = new User();
		user.setUserword(654321);
		map.put("abc",user);
		System.out.println("执行了@ModelAttribute 方法2");
	}
	@RequestMapping(value="/testModelAttribute")
	public String testModelAttribute(@ModelAttribute(value="abc") User user) {
		System.out.println(user);
		return "success";
	}
*/
	
	
	/**
     * SessionAttributes的注解
     * @return
     */
	@RequestMapping(value="/testSessionAttributes")
	public String testSessionAttributes(Model model) {
		model.addAttribute("msg","美美");
		model.addAttribute("age",17);
		System.out.println("执行了testSessionAttributes");
		return "success";
	}
	/**
     * 获取值
     * @param modelMap
     * @return
     */
	@RequestMapping(value="/getSessionAttributes")
	public String getSessionAttributes(ModelMap model) {
		String msg = (String) model.get("msg");
		Integer age = (Integer) model.get("age");
		System.out.println(msg + age);
		return "success";
	}
	/**
     * 清除
     * @param status
     * @return
     */
	@RequestMapping(value="/delSessionAttributes")
	public String delSessionAttributes(SessionStatus status,Model model) {
		status.setComplete();
		System.out.println(model);
		return "success";
	}

	
}
