package p1_response和request对象.java;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestURL();	//		http://localhost:8080/Web20_SpringMVC_Day1/controller/test2
		request.getRequestURI();	//		/Web20_SpringMVC_Day1/controller/test2
		request.getContextPath();	//		/Web20_SpringMVC_Day1
		request.getQueryString();	//返回 get请求中的 参数部分。如：a=1&b=2&c=3
		request.getRemoteAddr();	//返回客户端的IP地址
		request.getRemoteHost();	//返回客户机的主机名
		request.getRemotePort();	//返回客户机的网络端口号
		
		request.getLocalAddr();		//返回服务器的ip地址
		request.getLocalName();		//返回服务器的主机名
		request.getMethod();		//返回客户机的请求方式
		
		request.getHeader("Host");	//获得请求头"Host"的值
		request.getHeaders("Accept-Encoding");		//获得请求头""所有值的迭代器 Enumeration
		request.getHeaderNames();	//获得所有请求头名称的迭代器 Enumeration
		
		request.getParameter("name");	//获得客户端请求中"name" 对应的数据值
		request.getParameterNames();	//获得客户端请求中所有数据名的迭代器 Enumeration
		request.getParameterValues("name");	//获得客户端请求中数据名对应的所有数据值的迭代器 Enumeration
		request.getParameterMap();		//获得客户端请求中所有数据名.数据值，存入Map集合中，返回Map
		
		//请求转发，意味着--可使用request域
		request.getRequestDispatcher("url").forward(request, response);	//请求转发：发送一次请求	url默认当前应用目录下
																		//地址栏不变化
																		//只能定位到服务器资源(第二个servlet)
		request.setAttribute("password", 12345);	//将数据存入request域，通过请求转发 带给下一个servlet处理此数据
		
		
		System.out.println("------------------------------------------------------------------");
		
		response.setCharacterEncoding("UTF-8");		//设置写数据的编码
		response.setHeader("Content-type", "text/html;charset=UTF-8");	//设置响应头"Content-type"
		response.setContentType("text/html;charset=UTF-8");	//设置返回数据的类型，编码
		response.setContentType("image/jpeg");	//设置返回数据的类型
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("中文.txt", "UTF-8"));	
												//设置响应头，下载方式打开文件
		response.setDateHeader("Expires",System.currentTimeMillis()+1000*3600);	
												//缓存response内容一个小时，期间访问该Servlet都调用缓存文件，而不发送请求。
		
		response.setDateHeader("Expires", -1);	//设置页面不缓存，每次访问新数据
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		
		response.setHeader("refresh", "3");		//设置浏览器每隔3秒刷新一次
		response.setHeader("refresh", "3;url='/day06/index.jsp'");	//设置浏览器每隔3秒刷新一次,跳转到url地址（默认主机下资源）
		
		response.getOutputStream();		//获得输出流，可以向流中输出二进制数据
		response.getWriter();			//获得输出流，可以向流中输出文本数据
		
		//重定向1
		response.sendRedirect("/Web05_Response__Request/1.html");	//重定向：浏览器发送两次请求	url默认主机下资源
																	//地址栏发生变化
																	//可定位任意资源(第二个servlet)
		//重定向2
		response.setStatus(302);											//发送状态码302
		response.setHeader("location", "/Web05_Response__Request/1.html");	//发送响应头location及重定向地址(默认主机名下)
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
