package p1_response和request对象.java;

/*			request对象

	1. HttpServletRequest 简介
		   HttpServletRequest对象代表客户端的请求，当客户端通过HTTP协议访问服务器时，HTTP请求头中的所有信息都封装在这个对象中，开发人员通过
		 这个对象的方法，可以获得客户这些信息。

	2. URL与 URI 
		URI:  					 /news/1.html		--用来标识某一个资源
		URL: http://www.baidu.com/news/1.html		--用来标识互联网上某一个资源
		
	3. request常用方法
		1.获得客户机信息
			getRequestURL方法		返回客户端发出请求时的完整URL。		http://localhost:8080/Web20_SpringMVC_Day1/controller/test2
			getRequestURI方法		返回请求行中的资源名部分。			/Web20_SpringMVC_Day1/controller/test2
			getContextPath()		返回web项目名						/Web20_SpringMVC_Day1
			getQueryString方法		返回 get请求中的 参数部分。(post无效)
			getRemoteAddr方法		返回发出请求的客户机的IP地址
			getRemoteHost方法		返回发出请求的客户机的完整主机名	--没有在DNS中注册,返回 IP.127.0.0.1，
			getRemotePort方法		返回客户机所使用的网络端口号
			getLocalAddr方法			返回WEB服务器的IP地址。	 
			getLocalName方法			返回WEB服务器的主机名
			getMethod				得到客户机请求方式
			
		2.获得客户机请求头
			getHeader(string name)方法 		得到请求头name的值
			getHeaders(String name)方法 		得到请求头name所有值的迭代器 Enumeration
			getHeaderNames方法 				得到所有请求头的名称的迭代器 Enumeration
		
		3.获得客户机请求参数(客户端提交的数据)
			getParameter(name)方法					得到客户端数据名name对应的数据值 		返回String
			getParameterNames方法 					得到客户端所有数据名name的迭代器 		返回Enumeration
			getParameterValues（String name）方法	得到客户端数据名name对应的所有数据值 	返回String[]
			getParameterMap方法						得到客户端数据名name、数据值，存入Map集合中	返回	Map

	4.客户端收集用户数据(一个数据需有数据名name、数据值value)
		1.超链接(get方式)				url后加：	?数据名=数据值	如：?username=wxt
		2.表单提交数据
		
		url后若为中文数据，要编码后再提交
		
	5.request得到请求数据的中文乱码问题 
		1.post方式提交的数据(只对post有效)
			request.setCharacterEncoding("UTF-8");				---指定servlet打开数据的码表(只对post方式有效)
			String username=request.getParameter("username");	
			System.out.println(username);
			
		2.get提交的数据	(2019.5.12的Web12_AJAX项目中发现不需要写该代码，程序自动会用UTF-8打开)
			String username2=request.getParameter("username2");
			username2=new String(username2.getBytes("iso-8859-1"),"UTF-8");	--将得到的数据转换为字节码，再用UTF-8打开			
			System.out.println(username2);
		
	6.request对象--请求转发
		1.request对象有getRequestDispatcher方法，返回一个RequestDispatcher对象，调用其forward方法可以实现请求转发。
			
			例：	String data = "aaa";
				resquest.setAttribute("data",data);						//将数据带到转发的url中
				resquest.getRequestDispatcher(url).forward(request,response);	//请求转发到指定url，执行第二个servlet
																				(服务器内部跳转url然后将结果发给浏览器)
																				(url默认web应用下)
		
		2.应用场景：MVC设计模式(model view cotroller	数据封装	数据显示	数据处理)

		3.特点：	
				1.发送一次请求
				2.地址栏不发生变化
				3.只能定位到服务器资源
		
		4.request域----域对象转发并传输数据:
			
			request对象同时也是一个域对象，开发人员通过request对象在实现转发时，可以把数据通过request对象存入域，带给其它web资源处理。
				setAttribute方法 
				getAttribute方法  
				removeAttribute方法
				getAttributeNames方法

			例：	String data = "aaa";
				resquest.setAttribute("data",data);						//将数据存入request域,带到转发的url中
				resquest.getRequestDispatcher(url).forward(request,response);	
			
			
	7. request.getRequestDispatcher(url)====RequestDispatcher对象的包含功能
		 --include方法：
			  RequestDispatcher.include方法用于将RequestDispatcher对象封装的资源内容作为当前响应内容的一部分包含进来，从而实现可编程的服务
			器端包含功能。被包含的Servlet程序不能改变响应消息的状态码和响应头，如果它里面存在这样的语句，这些语句的执行结果将被忽略。

		例：	resquest.getRequestDispatcher(url).include(request,response);		//(url默认web应用下)
			response.getWriter().write("aaaaaaa<br/>");
			resquest.getRequestDispatcher(url2).include(request,response);
*/	

/*			request请求转发的注意细节
 	
 	1. forward()的封装
 		forward()将请求转发到RequestDispatcher对象封装的资源。
 
 	2. forward()前不可向客户端传数据-(IllegalStateException异常)
 		   若在调用forward方法前，已经在Servlet程序中真正向客户端传送内容，forward方法将抛出IllegalStateException异常。
 		   
 		   例1：	×	PrintWriter writer = response.getWriter();
 		   		×	writer.write("aaa");
 		   		×	writer.close();			//输出流PrintWriter close()后真正向客户端输出了数据
 		   		×	resquest.getRequestDispatcher(url).forward(request,response);	--(url默认web应用下)
 		   			
 		   例2：	×	resquest.getRequestDispatcher(url).forward(request,response);
 		   		×	resquest.getRequestDispatcher(ur2).forward(request,response);
 
 	3. requst请求转发后加return
 		   为避免上述例2中的问题，每次forward()跳转后加入一句return;代码(return后代码不执行)
 		   
 		   例：	√	resquest.getRequestDispatcher(url).forward(request,response);	--(url默认web应用下)
 		   			return;
 		   			
 	4. forward()时，会清空response中的数据
 		   如果在调用forward方法之前向Servlet引擎的缓冲区(response)中写入了内容，只要写入到缓冲区中的内容还没有被真正输出到客户端，forward方法
 		就可以被正常执行，原来写入到输出缓冲区中的内容将被清空。
 		   但是，已写入到HttpServletResponse对象中的响应头字段信息保持有效。
 	
 */

public class Request对象 {

}
