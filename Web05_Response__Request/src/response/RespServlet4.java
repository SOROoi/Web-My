package response;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 		控制浏览器定时刷新	
 			
 		response.setHeader("refresh", "3");							//每隔三秒刷新一次
 		response.setHeader("refresh", "3;url='/day06/index.jsp'");	//每隔三秒刷新一次，跳转到url地址（默认主机名下）
 		
 		response.sendRedirect(url)									//重定向
 		resquest.getRequestDispatcher(url).forward(request,response)//请求转发到指定url(服务器内部跳转url然后将结果发给浏览器)
 																					(只能定位到服务器资源s)
 		
 */

public class RespServlet4 extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		test1(response);
		test2(response);
	}

	private void test1(HttpServletResponse response) throws IOException {
		response.setHeader("refresh", "3");
		String data = new Random().nextInt(100000)+"";
		response.getWriter().write(data);
	}
	
	private void test2(HttpServletResponse response) throws IOException {	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		response.setHeader("refresh", "3;url='/Web05_Response__Request/1.html'");
		response.getWriter().write("登录成功，将在3秒后跳转，如果没有，请点<a href=''>超链接</a>");
	}

	private void test3(HttpServletRequest request,HttpServletResponse response) throws IOException, Exception {
		String message="<meta http-equiv='refresh' content='3;url=/day06/index.jsp'>登录成功，将在3秒后跳转，如果没有，请点<a href=''>超链接</a>";
		request.setAttribute("message",message);
		this.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	}
}