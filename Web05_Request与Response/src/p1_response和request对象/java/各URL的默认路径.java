package p1_response和request对象.java;

import java.net.URLDecoder;

/*			如下对象		方法的默认路径	

		主机资源目录：		response								"/"		加/
		当前应用目录：		request, Servlet, ServletContext		"/"		加/
		classes目录:			ClassLoader								""		不加/
		虚拟机启动目录：		IO流, new File(String s)					""		不加/
		
		例：
			请求转发：request.getRequestDispatcher("/form1.html").include(request, response);		--(默认为当前web应用下)
			
			重定向：response.sendRedirect("/Web05_Response__Request/1.html")		--(默认为主机名下)
			
			web应用对象获取资源：this.getServletContext().getRealPath("/form1.html");		--(默认为当前web应用下)
			
			web应用对象获取资源：this.getServletContext().getResourceAsStream("/fomr1.html");		--(默认为当前web应用下)
			
			响应头refresh： response.setHeader("refresh", "3;url='/day06/index.jsp'");	--(默认为主机名下)
	 	
	 	例：
			web.xml配置servlet映射		<servlet>
									        <servlet-name>FirstSeverlet</servlet-name>
									        <servlet-class>包名.类名</servlet-class>		(默认为classes目录下，即src目录下)
									    </servlet>
 			
 			Servlet中的基本IO流：FileInputStream in = new FileInputStream("");		(此处""默认为虚拟机启动的目录)
 
 			类加载器-输入流： InputStream in = UserDao.class.getClassLoader().getResourceAsStream("db.properties"); (此处""默认为classes目录下)
					      	  														
 			类加载器-绝对路径：String path = String.class.getClassLoader().getResource("").getPath();
 							 path = URLDecoder.decode(path, "UTF-8");	--解决乱码问题
 				(注意：填入的路径名必须是存在的，否则报错)
 			
 			ServletContext对象-绝对路径：String path = request.getSession().getServletContext().getRealPath("/xx");
 				(得到全路径，路径名可以不真实存在，得到：G:\apache-tomcat-8.5.39\wtpwebapps\Web20_SpringMVC_Day1\)
 				
 			request对象-web项目名：String contextPath = request.getContextPath();
 				(得到项目名：/Web20_SpringMVC_Day1)
 */

public class 各URL的默认路径 {

}
