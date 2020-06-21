package wxt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import wxt.bean.User;

@Controller
@RequestMapping(path="/con1")
public class Controller1 {
	
	/**
	 * 返回值为String
	 * @return
	 */
	@RequestMapping(path="/1")
	public String test1() {
									//跳转到页面success.jsp
		return "success";
	}
	
	/**
	 * 返回值为void,无return
	 */
	@RequestMapping(path="/2")	
	public void test2() {
									//跳转到页面2.jsp
	}
	
	/**
	 * 返回值为void,有return
	 * @param request
	 * @param response
	 */
	@RequestMapping(path="/3")
	public void test3(HttpServletRequest request,HttpServletResponse response) {
//		try {
//			request.getRequestDispatcher("/WEB-INF/pages/3.jsp").forward(request, response);	//跳转到页面3.jsp
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
		try {
			response.sendRedirect(request.getContextPath()+"/4.jsp");	//跳转到页面4.jsp
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} 
		return;
	}
	
	/**
	 * 返回值ModelAndView对象
	 * @return
	 */
	@RequestMapping(path="/4")	
	public ModelAndView test4() {
		ModelAndView mv = new ModelAndView();			//跳转到页面5.jsp
		mv.addObject("a", "大家好");
		mv.setViewName("5");
		return mv;
	}
	
	/**
	 * 关键字进行请求转发
	 * @return
	 */
	@RequestMapping(path="/5")	
	public String test5() {
		return "forward:/WEB-INF/pages/success.jsp";			//跳转到页面success.jsp
	}
	
	/**
	 * 关键字进行请求重定向
	 * @return
	 */
	@RequestMapping(path="/6")	
	public String test6() {
		return "redirect:6.jsp";			//跳转到页面6.jsp
	}
	
	@RequestMapping(path="/testAjax")
	public @ResponseBody User testAjax(User user) {
		System.out.println(user);
		user.setName(user.getName()+"1");
		user.setAge(user.getAge()+1);
		return user;
	}
}

