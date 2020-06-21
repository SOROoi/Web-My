package Listener和Filter;



/*					Listener监听器
	0.依赖
		存在于Servlet-API中，监听关于Servlet中对象状态变化。
		一般不考虑监听器的生命周期。
		web.xml配置简单，与 Filer不同。

	1.监听
		监听：当A事件发生时，执行B事件，实际就是接口回调
		接口回调：当某一个监听机制 监听到某事件的发生，便调用 监听器接口 的方法，需要传入(注册)一个监听器实例对象给监听机制
		
	2.web监听器
		1.数量：	8个
		2.配置：	在web.xml中注册,
				与Filter不同,Listener只需配置class地址即可。
				
				web.xml：
						<listener>
			  				<listener-class>com.itheima.listener.MyRequestListener</listener-class>
						</listener>
			
		
	3.三个作用域 创建和销毁 的监听器
	
		1. ServletContextListener（作用域创建和销毁时，执行该监听器接口中的方法）
				1.servletcontext创建：启动服务器、装载该项目时
				2.servletContext销毁：关闭服务器. 从服务器移除项目
				3.需web.xml注册监听器	
				
				4.作用：利用该监听器，在servletcontext创建的时候， 
						1. 完成自己想要的初始化工作
						2. 执行自定义任务调度。 执行某一个任务。 Timer 
				
		
 		2. ServletRequestListener（作用域创建和销毁时，执行该监听器接口中的方法）
				1.request创建:访问服务器上的任意资源都会有请求出现。
								访问 html： 会
								访问 jsp:	会
								访问 servlet : 会 
				2.request销毁:一个请求处理完成便销毁
					
				3.例-监听器：public class MyRequestListener implements ServletRequestListener {
							@Override
							public void requestDestroyed(ServletRequestEvent sre) {
								System.out.println("servletrequest 销毁了");
							}
						
							@Override
							public void requestInitialized(ServletRequestEvent sre) {
								System.out.println("servletrequest 初始化了");
							}
						}
						
				4.web.xml注册监听器:	<listener>
						  				<listener-class>com.itheima.listener.MyRequestListener</listener-class>
									</listener>
	
		3. HttpSessionListener（作用域创建和销毁时，执行该监听器接口中的方法）

				1.session的创建
					1.只要调用getSession
						html:		不会
						jsp:		会	  getSession();
						servlet: 	会
			
				2.session的销毁
					1.超时  30分钟
					2.非正常关闭 销毁
					(序列化、钝化：正常关闭服务器时，会将内存中session对象变成文件，保存在服务器电脑的硬盘中，不会销毁session)
			
				3.例-监听器：public class MySessionListener implements HttpSessionListener {
			
								@Override
								public void sessionCreated(HttpSessionEvent se) {
									System.out.println("创建session了");
								}
							
								@Override
								public void sessionDestroyed(HttpSessionEvent se) {
									System.out.println("销毁session了");
								}
							}
							
				4.web.xml注册监听器:	<listener>
						  				<listener-class>com.itheima.listener.MySessionListener</listener-class>
									</listener>	
									
				作用：统计在线人数.
				
	4.三个作用域属性状态变更的监听器（可以监听在作用域中值 添加、替换、移除的动作）
		
		1.ServletContextAttributeListener	（在servletContext域中值 添加、替换、移除时,执行此监听器方法）			
				1.context的添加：	context.setsetAttribute("name",x);
				2.context的替换：	context.setsetAttribute("name",y);
				3.context的移除:		context.removeAttribute("name");
				4.需要在web.xml中注册监听器
			
		2.ServletRequestAttributeListener	（在request域中值 添加、替换、移除时执行此监听器方法）
				1.request的添加：	request.setsetAttribute("name",x);
				2.request的替换：	request.setsetAttribute("name",y);
				3.request的移除:		request.removeAttribute("name");
				4.需要在web.xml中注册监听器
			
		3.HttpSessionAttributeListener	  	（在session域中值 添加、替换、移除时执行此监听器方法）
				1.session的添加：	session.setsetAttribute("name",x);
				2.session的替换：	session.setsetAttribute("name",y);
				3.session的移除:		session.removeAttribute("name");
				4.需要在web.xml中注册监听器
			
	5.httpSession里面存值的状态变更的监听器
		
		1.HttpSessionBindingListener	（监听对象与session 绑定和解除绑定 的动作时执行该监听器方法）
				1.对象与session绑定:		session.setAttribute("bean",bean)
				2.对象与session解除绑定:	session.removeAttribute("bean");
				3.不需要注册监听器，需要让bean实现该接口
			
		2.HttpSessionActivationListener	（监听session的值发生  钝化或活化 的动作时执行该监听器方法）
				1.钝化 （序列化）：把内存中的数据 存储到硬盘上
				2.活化 （反序列化）：把硬盘中的数据读取到内存中。
				3.session的钝化：正常关闭服务器，即会发生钝化
				4.session的活化：正常开启服务器，即会发生活化
				5.不需要注册监听器，需要让bean实现该接口、Serializable接口
				6.如何让session的在一定时间内钝化:
						做配置即可:
						1. 在tomcat里面 conf/context.xml 里面配置:			对所有的运行在这个服务器的项目生效  
						
						2. 在conf/Catalina/localhost/context.xml 配置:	对 localhost生效。  localhost:8080
						
						3. 在自己的web工程项目中的 META-INF/context.xml:	只对当前的工程生效。
						
							//maxIdleSwap="1" ： 	1分钟不用就钝化
							//directory="itheima" ： 钝化后的session对象文件存放的目录位置。 
							//D:\tomcat\apache-tomcat-7.0.52\work\Catalina\localhost\ListenerDemo\itheima
						
						配置：<Context>
								<Manager className="org.apache.catalina.session.PersistentManager" maxIdleSwap="1">
									<Store className="org.apache.catalina.session.FileStore" directory="itheima"/>
								</Manager>
							 </Context>

 */


/*					Filter过滤器
 	0.依赖
 		1.依赖于Servlet-API.
 		2.Filter是个接口
 	
 	1.过滤器原理：
 		1.过滤器对客户端发出来的每一次请求进行过滤。 
 		2.发出每一次请求，过滤器处理， 然后servlet处理，最后再过过滤器(执行在过滤器chain放行后的代码)，返回浏览器。  
 		3.本质上类似于一个Servlet，chain.doFilter(request,response)放行，可以视为执行 Servlet的service方法。
 		
 	2.作用：
		1. 对一些敏感词汇进行过滤
		2. 统一设置编码
		3. 自动登录
 			
	3.Filter的生命周期
 		1.创建：在服务器启动的时候就创建。 
		2.销毁：服务器停止的时候。
		
 	4.如何使用Filter：
		1. 定义一个类， 实现Filter接口，实现 doFilter()方法：
							public class FilterDemo implements Filter {
								public void destroy() {
								}
							
								public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
									System.out.println("来到过滤器了");
									chain.doFilter(request, response);	//此代码表示对该请求放行
									System.out.println("离开过滤器了");	//此处代码一般不写。在servlet处理后，返回浏览器前执行此代码
								}
							
								public void init(FilterConfig fConfig) throws ServletException {
								}
							
							}		
		2. 在web.xml里注册过滤器：
								<filter>
								    <filter-name>FilterDemo</filter-name>
								    <filter-class>com.itheima.filter.FilterDemo</filter-class>
								</filter>
								<filter-mapping>
								    <filter-name>FilterDemo</filter-name>
								    <url-pattern>/*</url-pattern>
								    <dispatcher>REQUEST</dispatcher>
								</filter-mapping>
		3.注册完成后对mapping中所有符合条件的uri请求进行过滤
		
	5.Filter执行顺序
		1. 客户端发出请求，先经过过滤器， 如果过滤器放行，那么才能到servlet
		2. 如果有多个过滤器， 那么他们会按照注册的映射顺序 排队。 只要有一个过滤器不放行，那么后面排队的过滤器以及请求的servlet都不会收到请求。
		
	6.Filter细节：
		1. init方法的参数：
		 		FilterConfig , 可以用于获取filter在注册的名字 以及初始化参数。  其实这里的设计的初衷与ServletConfig是一样的。
		2. 放行：
				在doFilter() 方法中操作，使用参数 chain.doFilter(request, response); 放行， 让请求到达下一个目标。
				
		3. <url-pattern>写法格式：
				与servlet一样：
				1. 全路径匹配 以/开始 ：			/LoginServlet
				2. 以目录匹配 以/开始 以*结束：		/demo01/*  
				3. 以后缀名匹配  以*开始以后缀名结束：*.jsp  *.html *.do 
			
		4.mapping中针对 dispatcher 设置：
				REQUEST ： 只要是请求过来，都过滤，默认就是REQUEST 
			   	FORWARD : 只要是转发都过滤。 
			   	ERROR ： 页面出错发生跳转就过滤
			   	INCLUDE ： 包含页面的时候就过滤。
			   	例：<filter-mapping>
				  	<filter-name>FilterDemo</filter-name>
				  	<url-pattern>/demo01/*</url-pattern>
				  	<dispatcher>REQUEST</dispatcher>	------对哪种页面进行过滤
				  </filter-mapping>
	
 */


/*					ServletContext对象、session钝化活化、Filter生命周期
 
 	1.ServletContext对象、session钝化活化、Filter对象的生命周期是一样的
 	
 	2.它们的生命周期都是：
 					1.创建：在服务器启动的时候就创建(活化)。 
					2.销毁：服务器停止的时候就销毁(钝化)。
 
 */


public class 监听器和过滤器 {
	public static void main(String[] args) {
		
		
		
	}
}
