package wxt.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import wxt.bean.User;

/*
 * 控制器类
 */
@Controller
@RequestMapping(path="/controller")
public class HelloController {
	
	/**
	 * 请求参数封装到 基本类型/String中
	 * @param username
	 * @return
	 */
	@RequestMapping(path="/hello")
	public String sayHello(String username) {			//如果需要获取 请求参数，则在方法中 定义 传入参数
		System.out.println("Hello World,Hello SpringMVC");
		System.out.println("用户名为："+username);
		return "success";
	}
	 
	/**
	 * 请求参数封装到 Javabean中（Javabean中包含Javabean、集合List、Map）
	 * @param user
	 * @return
	 */
	@RequestMapping(path="/form")
	public String userInf(User user) {				//如果需要获取 请求参数，则在方法中 定义 传入参数
		System.out.println("用户信息："+user);
		return "success";
	}
	
	/**
	 * 自定义类型转换器 String —— Date
	 * 格式：yyyy-MM-dd 
	 * @param user
	 * @return
	 */
	@RequestMapping(path="/form2")
	public String userInf2(User user) {				//如果需要获取 请求参数，则在方法中 定义 传入参数
		System.out.println("用户信息："+user);
		return "success";
	}
	
	/**
	 * 获得原生的servlet API
	 * @param tips
	 * @return
	 */
	@RequestMapping(path="/getServlet")					//如果需要获取 原生的servlet request对象，则在方法参数中 定义对象
	public String getServlet(HttpServletRequest request,HttpServletResponse response) {		
		System.out.println("暗号："+request.getParameter("tips"));
		System.out.println("request对象地址："+request);
		System.out.println("response对象地址："+response); 
		return "success";
	}
	
	@RequestMapping("/test")
	public String test() {
		
		return "redirect:/1.jsp";
	}
	
	@RequestMapping("/1")
	public String method() {
		return "success";
	}
	
	
	@RequestMapping("/test2")
	public void test2(HttpServletRequest request) {
		String url = request.getRequestURL().toString();	//	URL为：http://localhost:8080/Web20_SpringMVC_Day1/controller/test2
		String uri = request.getRequestURI();			//	URI为/Web20_SpringMVC_Day1/controller/test2
		String contextPath = request.getContextPath();	//	contextPath为/Web20_SpringMVC_Day1
		String path = request.getSession().getServletContext().getRealPath("/");	//	path为G:\apache-tomcat-8.5.39\wtpwebapps\Web20_SpringMVC_Day1\
		System.out.println("URL为："+url);
		System.out.println("URI为"+uri);
		System.out.println("contextPath为"+contextPath);
		System.out.println("path为"+path);
		
		
		
		
		return;
	}
	
}
