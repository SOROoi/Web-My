package p1_Servlet开发介绍;
				
/*				Servlet开发
 	知识点：
  		1.	servlet 意义，生命周期，执行流程，HttpServlet等
  		2.	web.xml中的配置：基本配置，启动服务器就加载，servlet映射写法
  		3.	servlet 相关的对象：ServletConfig, ServletContext(即context域)
 
 */

/*					
 	1.Servlet简介
 			1.1 Servlet是sun公司提供的一门用于开发动态web资源的技术。
 			
 			1.2 Sun公司在其API中提供了一个servlet接口，用户若想用发一个动态web资源(即开发一个Java程序向浏览器
 			输出数据)，需要完成以下2个步骤：
				1.编写一个Java类，实现servlet接口。
				2.把开发好的Java类部署到web服务器中。
 			
 			1.3 阅读Servlet API，解决两个问题：	(Servlet3.1 API 在浏览器书签内)	
				1.输出hello servlet的java代码应该写在servlet的哪个方法内？
				2.如何向IE浏览器输出数据？
			
			1.4 宏观Servlet
				从宏观上看，我们也把实现了servlet接口的java程序，称之为Servlet。
				
			1.5 官方实现
				tomcat 是sun 和 apache 合作出来的 jsp server，tomcat 中的 Servlet-API可以视为官方API.
				
	2.手动写Servlet程序 向浏览器输出数据
			1.在webapps目录中创建web应用 Day04
				1.创建web应用目录结构
							在classes目录中编写FirstServlet.java程序
				2.追加servlet映射：
							打开项目web.xml，
							复制服务器web.xml的开头22行，
							复制<servlet>标签，修改内容			---服务器使用的servlet
							复制<servlet-mapping>标签，修改内容	---映射
						------------------------如下-------------------------    
							    <servlet>
							        <servlet-name>FirstSeverlet</servlet-name>
							        <servlet-class>包名.类名</servlet-class>
							    </servlet>
							
							    <servlet-mapping>
							        <servlet-name>FirstSeverlet</servlet-name>
							        <url-pattern>/×××</url-pattern>
							    </servlet-mapping>
						-----------------------------------------------------
			2.导入jar包
				Servlet程序使用JDK编译,JDK1.8中无Servlet的jar包，需导入Servlet的jar包，该jar包位置在tomcat下：lib\servlet-api.jar
				命令行输入：set classpath=%classpath%		+	Servlet的jar包所在路径
				
			3.编译
				命令行进入FirstServlet.java所在目录，
				命令行输入：javac -d . FirstServlet.java	(带包编译)
			
			4.启动服务器/访问
				启动服务器，访问 localhost：8080/Day04/×××	
				
			5.错误500原因
				web.xml中 <servlet-class>标签中写错成wxt.Firstsevlet
											   修改为wxt.Firstservlet 后运行成功
				
	3.客户端访问Servlet的流程(见图)
			1.Web服务器首先检查，是否已经装载并创建了该Servlet的实例对象。
				如果是，则直接执行第4步，否则，执行第2步。
			
			2.装载并创建该Servlet的一个实例对象。 
			
			3.调用Servlet实例对象的init()方法。
			
			4.创建一个用于封装HTTP请求消息的HttpServletRequest对象和一个代表HTTP响应消息的HttpServletResponse对象，然后调
			用Servlet的service()方法并将请求和响应对象作为参数传递进去。
			
			5.WEB应用程序被停止或重新启动之前，Servlet引擎将卸载Servlet，并在卸载之前调用Servlet的destroy()方法。
			
	4.使用 eclipse 开发 动态web项目(一个项目必须搭配一个服务器)
			1.将视图切换为javaEE视图(搜索栏右侧 四个小图标)
			
 			2.新建web项目：新建 —— web —— Dynamic Web Project(动态web项目)
 			
 			3.新建服务器:	 点击servers小图标 —— "create a new server..." —— Apche —— Tomcat8.5 —— 部署项目 —— finish
 					
 					设置： 右键创建完的服务器 —— open —— Server Locations中勾选第二个选项 —— 保存 (该选项用来选择 项目部署到哪个位置)
 					(若无法修改Server Locations选项，先remove该服务器所有项目，再点Publish，就可以选择了)
 					
 			4.src中编写 servlet程序
 			
 			5.项目web.xml追加 servlet映射
 						------------------------如下-------------------------    
							    <servlet>
							        <servlet-name>FirstSeverlet</servlet-name>
							        <servlet-class>包名.类名</servlet-class>		(默认为classes目录下，即src目录下)
							    </servlet>
							
							    <servlet-mapping>
							        <servlet-name>FirstSeverlet</servlet-name>
							        <url-pattern>/×××</url-pattern>
							    </servlet-mapping>
						-----------------------------------------------------
 						
 			6.发布项目： 1.右键项目名 —— Run As —— Run on Server(绑定运行服务器)
 					   2.右键servers视图 —— publish (将web项目发布到服务器中)
 					   3.开启服务器
 			
 	5.eclipse中restart服务器
 			restart服务器可以将项目中的web.xml更新至服务器对应web应用中去。
 			
 	6.web应用web.xml中，关于Servlet的一些细节
 			1.同一个Servlet可以被映射到多个URL上，即多个<servlet-mapping>元素的<servlet-name>子元素的值相同。
 			
 			2.在Servlet映射到的URL中也可以使用*通配符，但是只能有两种固定的格式：一种格式是“*.扩展名”，另一种格式
 			是以正斜杠（/）开头并以“/*”结尾。
 			
 			3.对于如下的一些映射关系：
				Servlet1 映射到 /abc/* 		---先考虑前三种，哪个最像匹配哪个		
				Servlet2 映射到 /* 											
				Servlet3 映射到 /abc 											
				Servlet4 映射到 *.do 			---该方式优先级最低
 
 */


/*	
 	1.Servlet接口
			两个默认实现类：GenericServlet、HttpServlet (都是抽象类)	
 	
 	2.HttpServlet
 			1.是能够处理HTTP请求的servlet，它在原有Servlet接口上添加了一些与HTTP协议处理方法，它比Servlet接口的功能更为强大。开发
 			人员在编写Servlet时，通常应继承这个类，而避免直接去实现Servlet接口。
 			
 			2. HttpServlet实现Servlet接口时，重写了service方法，该方法体内的代码会自动判断用户的请求方式，如为GET请求，则调
 			用HttpServlet的doGet方法，如为Post请求，则调用doPost方法。因此，开发人员在编写Servlet时，通常只需要重写doGet或doPost方
 			法，而不要去重写service方法。
			
	3.Servlet的细节
			1.1 依赖servlet引擎
				Servlet是一个供其他Java程序（Servlet引擎）调用的Java类，它不能独立运行，它的运行完全由Servlet引擎来控制和调度。
			
			1.2 一个Servlet实例对象
				针对客户端的多次Servlet请求，通常情况下，服务器只会创建一个Servlet实例对象，也就是说Servlet实例对象一旦创建，它就
			会驻留在内存中，为后续的其它请求服务，直至web容器退出，servlet实例对象才会销毁。
			
			1.3 HTTP请求和请求、响应对象、service方法
				在Servlet的整个生命周期内，Servlet的init方法只被调用一次。而对一个Servlet的每次访问请求都导致Servlet引擎调用一次
			servlet的service方法。对于每次访问请求，Servlet引擎都会创建一个新的HttpServletRequest请求对象和一个新的HttpServletResponse
			响应对象，然后将这两个对象作为参数传递给它调用的Servlet的service()方法，service方法再根据请求方式分别调用doXXX方法。
			
			1.4 启动服务器时创建Servlet实例对象
				如果在<servlet>元素中配置了一个<load-on-startup>元素，那么WEB应用程序在启动时，就会装载并创建Servlet的实例对象、以及
			调用 该Servlet实例对象的init()方法.
				
				举例：
					<servlet>
						<servlet-name>invoker</servlet-name>
						<servlet-class>
							org.apache.catalina.servlets.InvokerServlet
						</servlet-class>
						<load-on-startup>2</load-on-startup>  		---1优先级最高
					</servlet>
					
				用途：为web应用写一个InitServlet，这个servlet配置为启动时装载，为整个web应用创建必要的数据库表和数据。
	
			1.5 缺省Servlet
			   格式： 如果某个Servlet的映射路径仅仅为一个正斜杠（/），那么这个Servlet就成为当前Web应用程序的缺省Servlet。
			
			   作用： 凡是在web.xml文件中找不到匹配的<servlet-mapping>元素的URL，它们的访问请求都将交给缺省Servlet处理，也就是说，缺省
			Servlet用于处理所有其他Servlet都不处理的访问请求。
			
			   默认缺省servlet:  在<tomcat的安装目录>\conf\web.xml文件中，注册了一个名称为org.apache.catalina.servlets.DefaultServlet的
			Servlet，并将这个Servlet设置为了缺省Servlet。
			
			1.6 Servlet执行条件
			   执行条件：浏览器端 URL 以应用资源目录开始时,才可执行 Servlet和缺省 Servlet.
			   
			   优先级：当浏览器端 URL 以应用资源目录开始时，先寻找有无符合的 Servlet映射，再匹配其他资源（如配置的主页）
			  										Servlet > 配置的主页
			  										
			1.7 Servlet线程安全问题（实现SingleThreadModel接口）
			  出现原因：当多个客户端并发访问同一个Servlet时，web服务器会为每一个客户端的访问请求创建一个线程，并在这个线程上调用Servlet的
			service方法，这些此service方法内如果访问了同一个资源的话，就有可能引发线程安全问题。
			
			  解决办法：使某个Servlet实现SingleThreadModel接口，那么Servlet引擎将以单线程模式来调用其service方法。
			  
			  原理：实现SingleThreadModel接口的Servlet，每次浏览器请求时，在服务器创建一个新的的Servlet实例对象。
			  
			  过时：并不能真正解决Servlet的线程安全问题，因为Servlet引擎会创建多个Servlet实例对象，而真正意义上解决多线程安全问题
			是指一个Servlet实例对象被多个线程同时调用的问题。
 
 	3. ServletConfig对象（servlet配置信息对象）
			1.在<servlet>的配置文件中，可以使用一个或多个<init-param>标签为servlet配置一些初始化参数。
					例：			<init-param>
									<param-name>data1</param-name>
									<param-value>aaa</param-value>
								</init-param>
								<init-param>
									<param-name>data2</param-name>
									<param-value>bbb</param-value>
								</init-param>
 			
 			2.创建和执行流程
 				web容器在创建servlet实例对象时，会自动将这些web.xml中初始化参数封装到ServletConfig对象中，并通过servlet的init方法，
 			  将ServletConfig对象传递给servlet。进而，程序员在service()方法中通过ServletConfig对象就可以得到当前servlet的初始化参数信息。

			3.得到并使用ServletConfig对象
				在doGet()方法中，this.getServletConfig()即可得到ServletConfig对象。然后调用ServletConfig对象的方法。
				
			4. ServletConfig对象的方法
				getInitParameter(String name)	---String				---返回配置信息中name对应的value
				getInitParameterNames()			---Enumeration<String>	---返回包含所有配置信息的迭代器Enumeration对象
				
			5. Enumeration对象的迭代
					例：		Enumeration<String> e= this.getServletConfig().getInitParameterNames();
							while (e.hasMoreElements()) {
								String name = (String) e.nextElement();
								String value = this.getServletConfig().getInitParameter(name);
							}
 	
 	3. ServletContext对象(web应用对象)
 			1.生命周期
 				创建：服务器启动后，自动为其中每个web应用创造ServletContext对象，它代表当前web应用。
 				销毁：服务器关闭时，web应用删除时，销毁对应的ServletContext对象。
 			
 			
 			2.ServletConfig对象中维护了ServletContext对象的引用，开发人员在编写servlet时，可以通过ServletConfig.getServletContext方法
 			获得ServletContext对象
 			
 			3.由于一个WEB应用中的所有Servlet共享同一个ServletContext对象，因此Servlet对象之间可以通过ServletContext对象来实现通讯。
 			ServletContext对象通常也被称之为context域对象。
	
			4.得到ServletContext对象(需在Servlet中)
				方式1：this.getServletConfig().getServletContext();
				方式2：this.getServletContext();
				
			5.ServletContext域对象共享数据(有线程安全问题，一般使用requst域)
				Servlet1: this.getServletContext().setAttribute("data","aaa");
				Servlet2: (String)this.getServletContext().getAttribute("data");
				
				ServletContext域是一个容器，该容器作用范围为应用程序范围。
				
			6.获取Web应用的初始化参数
				设置：在web.xml中，<context-param>标签为整个web应用配置初始化参数：(服务器启动后，自动为其中每个web应用创造ServletContext
				对象，将初始化参数封装到ServletContext对象中)
					例：		<context-param>
								<param-name>data<param-name/>
								<param-value>xxx<param-value/>
							<context-param/>
				获取单个：String value = this.getServletContext().getInitParameter("data");
				获取所有：Enumeration<String> e = this.getServletContext().getInitParameterNames();
			
			7.实现Servlet的转发
				重定向：你找我借钱，我说我没有，你自己去找他（浏览器发送两次请求）
				转发：你找我借钱，我说我没有，我帮你去找他（浏览器发送一次请求）
				
				
			8.利用ServletContext对象读取资源文件
			
				      方式1：Servlet对象 得到输入流。
				      		InputStream in = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");
								

				      方式2: 直接输入绝对路径。
				      		FileInputStream in = new FileInputStream("");		
					  										
					  										
				      方式3：Servlet对象 得到绝对路径。
				      		String path = this.getServletContext().getRealPath("/WEB-INF/classes/db.properties");
                          	FileInputStream in = new FileInputStream(path);
					  
					  
				      方式4：request对象 得到绝对路径。
				      		String path = request.getSession().getServletContext().getRealPath("/");	
					  		得到 path 为全路径： "G:\apache-tomcat-8.5.39\wtpwebapps\Web20_SpringMVC_Day1\"
					  		
					  	得到web项目名：
					  		1.String contextPath = request.getContextPath();		/Web20_SpringMVC_Day1
					  		2.String contextPath = request.getSession().getServletContext().getContextPath();	/Web20_SpringMVC_Day1
					  
				      方式5：ClassLoader对象 得到输入流。
					      	 (不在Servlet中，通过类加载器读取)	
					         (注意：类加载器只能装载一次，代码执行一次后修改文件，再次执行将是原数据)
					         (	      类加载器读的文件不能太大)
				      	  	InputStream in = UserDao.class.getClassLoader().getResourceAsStream("db.properties");	
				      	  																	(此处""默认为classes目录下)
				      	  
				      方式6：ClassLoader对象 得到绝对路径。
					      	  (不在Servlet中，通过类加载器读取，修改文件后，再次执行可刷新)
					      	  String path = UserDao.class.getClassLoader().getResource("db.properties").getPath();
						  	  FileInputStream in = new FileInputStream(path);
 */
public class Servlet开发 {
	public static void main(String[] args) {
		
	}
}
