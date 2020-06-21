package wxt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义拦截器
 * 无需实现方法，说明接口中的方法都已经写好了
 * 也可以自行重写方法
 * @author asus pc
 *
 */
public class MyInterceptor implements HandlerInterceptor {

	/**
	 * 预处理方法
	 * Controller方法执行前，执行此方法
	 * 
	 * return true 放行		执行下一个 拦截器/controller 方法
	 * return false 不放行	可使用 request/response对象 跳转到其他页面
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		System.out.println("拦截器111 preHandle()执行了...");
		return true;
	}

	
	/**
	 * 后处理方法
	 * Controller方法执行后，执行此方法，再执行 success.jsp
	 * 
	 * 此方法中也可以 跳转到其他页面
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		System.out.println("拦截器111 postHandle()执行了...");
	}

	
	/**
	 * 收尾方法
	 * 跳转success.jsp后，执行
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
		System.out.println("拦截器111 afterCompletion()执行了...");
	}
	
	
}
