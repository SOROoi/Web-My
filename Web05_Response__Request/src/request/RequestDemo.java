package request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 		if中的代码即为防盗链
 * 		
 * 		利用请求头referer判断来源页。从本网站来源的，才可查看。
 *									非本网站来源，重定向，跳转到首页。
 * 
 */
public class RequestDemo extends HttpServlet {

	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//request获取请求头referer，得到来源路径
		String referer=request.getHeader("referer");
		
		//1. referer==null用户直接粘贴url访问，不看广告
		//2. !referer.startsWith("http://localhost")别的网站引用url访问
		if(referer==null && !referer.startsWith("http://localhost"))
		{
			response.sendRedirect("/day06/index.jsp");	//重定向到首页去，看广告！
			return;
		}
		response.getOutputStream().write("bbb".getBytes());	//来自本网站，给你看
	}
	
	



	 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
