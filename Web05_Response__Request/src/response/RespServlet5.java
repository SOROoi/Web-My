package response;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 					请求重定向(两次请求，浏览器地址栏发送变化)
 					
 		方式一：
	 		response.setStatus(302);											//发送状态码302
			response.setHeader("location", "/Web05_Response__Request/1.html");	//发送响应头location
	 		
	 	方式二：
	 		response.sendRedirect("/Web05_Response__Request/1.html")		//一句解决
 */

public class RespServlet5 extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		test1(response);	//方式一
		test2(response);	//方式二
	}

	private void test2(HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("/Web05_Response__Request/1.html");
	}

	private void test1(HttpServletResponse response) {
		response.setStatus(302);											//发送状态码302
		response.setHeader("location", "/Web05_Response__Request/1.html");	//发送响应头location
	}

}