package springMVC;

/*								1.SpringMVC 的基本概念
 * 
					(	1.springMVC	关联 web项目，调度 展示层 和 controller.
						2.和 web项目相关的--web.xml中配置，和程序相关的--spring.xml中配置
					
					 1.MVC：表现层模型。表现层分为展示层(view) 控制层(controller),
					 2.springMVC 核心控制器 DispatcherServlet, 核心 Servlet,
					 3.与 DispatcherServlet 交互：HandlerMapping	HandlerAdapter ViewResolver,
					 
					 4.使用mvc框架：		1.web.xml:				配置DispatcherServlet，配置spring-mvc.xml
					 					2.spring-mvc.xml:		开启IOC注解
					 											注入ViewResolver
					 											过滤静态资源
																开启注解，注入HandlerMapping HandlerAdapter	
																
					 5.依赖包：		1.spring-web
									2.spring-webmvc
									3.servlet-api	
					 			
					 				4.jackson-databind		--JSON 数据绑定为 Bean
									  jackson-core			--返回 @Responsebody Bean 转为 JSON
								 	  jackson-annotations
								 	  
								 	5.Commons-fileupload	--文件上传：	MultipartFile 文件解析器
								 	  
								 	6.ersey-core			--跨服务器保存文件
								 	  jersey-client
					)		

	1.三层架构
		1.于B/S 架构(浏览器/服务器)的开发中，系统标准的三层架构为：表现层、业务层、持久层。
		
		表现层：	1.即 web层。它负责接收客户端请求，向客户端响应结果。
				2.表现层包括控制层和展示层：控制层负责接收请求，展示层负责结果的展示。
				3.表现层依赖业务层，接收到客户端请求一般会调用业务层进行业务处理。
				4.表现层的设计一般都使用 MVC 模型。（MVC 是表现层的设计模型，和其他层没有关系）
		业务层：	1.即 service 层。它负责业务逻辑处理，和开发项目的需求相关。
				2.业务层在业务处理时可能会依赖持久层，如果要对数据持久化需要保证事务一致性。（事务应该放到业务层来控制）
		持久层：	1.即 dao 层。负责数据持久化，包括数据层(即数据库)和数据访问层，
				2.数据库是对数据进行持久化的载体，数据访问层是业务层和持久层交互的接口.
				
			表现层(web层)			业务层(service层)			持久层(dao层)
			   JSP		--V				Service						Dao
			  Servlet	--C				JavaBean	--M


	2. MVC模型
		1. MVC 全名是 Model View Controller，是模型(model)－视图(view)－控制器(controller)的缩写，是一种用于设计创建 Web应用程
		序 表现层的模式。
		
		Model (模型)：数据模型。一般情况下用于封装数据。即 JavaBean
		View (视图)：一般用于展示数据。即 JSP/HTML
		Controller (控制器)：一般用于处理程序逻辑。即 servlet


	3. SpringMVC概述
	
		1.SpringMVC 是什么?
			1. SpringMVC 是一种基于 Java的实现 MVC设计模型的请求驱动类型的轻量级 Web框架，属于 Spring FrameWork的后续产品。
			2. 它通过一套注解，让一个简单的 Java类成为处理请求的控制器，而无须实现任何接口。同时它还支持 RESTful编程风格的请求。
	
	
		2.SpringMVC 和 Struts2 的优略分析
			共同点：
				1.它们都是表现层框架，都是基于 MVC 模型编写的。
				2.它们的底层都离不开原始 ServletAPI。
				3.它们处理请求的机制都是一个核心控制器。
				
			区别：
				1.Spring MVC 的入口是 Servlet, 而 Struts2 是 Filter。
				2.Spring MVC 是基于方法设计的，而 Struts2是基于类，Struts2每次执行都会创建一个动作类。所以 Spring MVC会比 Struts2快些。
				3.Spring MVC 使用更加简洁,同时还支持 JSR303, 处理 ajax 的请求更方便。
				 (JSR303 是一套 JavaBean参数校验的标准,它定义了很多常用的校验注解,直接将这些注解加在 JavaBean的属性上,就可在需要校验时
				    进行校验了。)
				4.Struts2 的 OGNL 表达式使页面的开发效率相比 Spring MVC 更高些，但执行效率并没有比 JSTL提升，尤其是 struts2 的表单标
				签，远没有 html 执行效率高。


 */


/*								 SpringMVC 入门案例		(--见: Web20_SpringMVC_Day1)
 * 
 	效果：
 		主页：localhost:8080/Web20_SpringMVC	(实际加载index.jsp)
 		点击超链接，控制台输出"Hello World,Hello SpringMVC"，并跳转到/WEB-INF/pages/下的success.jsp
 
 
 
		(三个要点：	核心控制器	--	web.xml <servlet>配置，		并读取springmvc.xml)
					视图解析器	--	springmvc.xml <bean>配置，	加到 IOC容器
				   controller类	--	注解配置类，配置方法			加到 IOC容器

	1. SpringMVC 入门案例
			0.创建 maven工程 web项目。
			
			1.在/webapp 目录下，创建 index.jsp:
				1.创建超链接,指向: hello 	(指向 @RequestMapping(path="/hello") 映射的方法)
					<a href="hello">入门程序</a>
			
			2.导包：		1.spring-context:		spring IOC容器 
						2.aspectjweaver:		spring AOP
						3.spring-web:								--springMVC	jar
						4.spring-webmvc:							--springMVC	jar
						5.servlet-api:			servlet API
						6.jsp-api:				JSP API
						
			3.配置前端(核心)控制器：		--一个 Servlet（springMVC 必备）
				1.web.xml中配置.
				2.配置<servlet>标签、<servlet-mapping>标签:			
					<servlet-class>:  org.springframework.web.servlet.DispatcherServlet (spring-webmvc包中)		//核心控制器 固定类		
							
				3.读取 springmvc.xml文件：
					1.配置<init-param>标签：全局初始化参数。
						<param-name>:	contextConfigLocation			//给 核心控制器类的 contextConfigLocation属性 配置值
						<param-value>：	classpath:springmvc.xml			//配置值(classpath:配置文件路径)
						
					2.配置<load-on-startup>标签：启动服务器就创建 核心控制器类对象。
						<load-on-startup>1</load-on-startup>
					
					例：	<!-- MVC前端控制器 -->
						<servlet>
  							<servlet-name>dispatcherServlet</servlet-name>
							<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
							<!-- 配置读取springmvc.xml文件 -->
							<init-param>
								<param-name>contextConfigLocation</param-name>
								<param-value>classpath:springmvc.xml</param-value>
							</init-param>
							<!-- 启动服务器，加载该servlet -->
							<load-on-startup>1</load-on-startup>
						</servlet>
						<!-- MVC前端控制器映射 -->
						<servlet-mapping>
							<servlet-name>dispatcherServlet</servlet-name>
							<url-pattern>/</url-pattern>
						</servlet-mapping>
					
			4.添加 spring-mvc.xml配置:
				1.创建 springmvc.xml,	(即bean.xml)(在 resources目录下)
				    约束同 spring, 多一个 mvc名称空间.
					
					例：	<?xml version="1.0" encoding="UTF-8"?>
						<beans xmlns="http://www.springframework.org/schema/beans"
						       xmlns:mvc="http://www.springframework.org/schema/mvc"		
						       xmlns:context="http://www.springframework.org/schema/context"
						       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						       xsi:schemaLocation="
						        http://www.springframework.org/schema/beans
						        http://www.springframework.org/schema/beans/spring-beans.xsd
						        http://www.springframework.org/schema/mvc
						        http://www.springframework.org/schema/mvc/spring-mvc.xsd
						        http://www.springframework.org/schema/context
						        http://www.springframework.org/schema/context/spring-context.xsd">
						        
						    <!-- 开启注解IOC  扫描controller -->
							<context:component-scan base-package="wxt">
								<!-- 只扫描controller注解 -->
								<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
							</context:component-scan>
						</beans>
				
				2.配置视图解析器：	(跳转页面时，视图解析器会帮你跳转到指定的页面)
					1.在springmvc.xml中配置.
					2.视图解析器 固定类：			
						class="org.springframework.web.servlet.view.InternalResourceViewResolver"	
					3.配置 跳转文件所在的目录：
						name="prefix"				--视图文件所在的目录				
						value="/WEB-INF/pages/"		--目录				--固定写法：/XX/XX/格式
					4.配置 跳转文件的后缀名：
						name="suffix"				--视图文件的后缀名				
						value=".jsp"				--后缀名
						
						例：	<bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
								<property name="prefix" value="/WEB-INF/pages/"></property>
								<property name="suffix" value=".jsp"></property>
							</bean>
					
				3.配置 <mvc:annotation-driven/>标签:		(开启 springMVC框架 注解的支持)
					1.在springmvc.xml中配置.
					2.开启 springMVC框架 注解支持：		<mvc:annotation-driven/>	
					3.此配置自动加载 RequestMappingHandlerMapping (处理映射器)和 RequestMappingHandlerAdapter (处理适配器) 
					
					
					
				例：	<!-- 开启注解扫描controller 到IOC -->
					<context:component-scan base-package="wxt">
						<!-- 只扫描controller注解 -->
						<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
					</context:component-scan>
					
					<!-- 配置视图解析器 -->
					<bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
						<property name="prefix" value="/WEB-INF/pages/"></property>		--固定写法：/XX/XX/格式
						<property name="suffix" value=".jsp"></property>
					</bean>
					
					<!-- 过滤静态资源 -->
					<mvc:resources location="/css/" mapping="/css/**"></mvc:resources>
					<mvc:resources location="/js/" mapping="/js/**"></mvc:resources>
					<mvc:resources location="/image/" mapping="/image/**"></mvc:resources>
					
					<!-- 开启注解mvc支持 -->
					<mvc:annotation-driven></mvc:annotation-driven>
					
					
					
			5.将项目部署到服务器：
				1.点击 Servers图标 ——> 空白处右键 ——> Add and Remove...
				2.添加项目即可
				
			
			(springMVC框架有一个默认的规则：Controller中 映射方法 有返回值表示发生跳转；
											返回的字符串，表示跳转到 视图解析器目录下 的JSP文件的名称)
												
			6.编写控制器 Controller类、注解加入IOC：
				1.创建 Controller类，
				2.创建 sayHello()方法，返回值String,返回"success"。
				3.在 /WEB-INF目录下创建 pages目录，pages目录中 创建success.jsp文件。
				4.在类上添加注解 @Controller，加入IOC,
				5.在方法上添加注解 @RequestMapping(path="/hello")，即可将请求映射到该方法。
				

			
					
 */


/*								 SpringMVC 入门案例  报错---异常分析
 	
 	1. web.xml中配置出错。
 			异常信息：	NumberFormatException: For input string: ""
 						InvocationTargetException：
 			
 			错误配置：	在 核心控制类 <servlet>标签中，
 						配置为：<load-on-startup></load-on-startup>
 					
			正确配置：	配置为：<load-on-startup>1</load-on-startup>
			
			分析：		少写1。
			
	2. springmvc.xml中配置出错。
			异常信息：	CannotLoadBeanClassException：
						ClassNotFoundException：
	
			错误配置：	在配置视图解析器bean时，
						配置为：<bean id="internalResourceViewResolver" class="InternalResourceViewResolver">
	
			正确配置：	配置为：<bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
			
			分析：		少写类的包名。应写全限定类名。
 */


/*								 springMVC 的组件、RequestMapping注解
 * 
 	0. SpringMVC 的三大组件：处理器映射器、处理器适配器、视图解析器。
 
	1. DispatcherServlet：前端控制器
			用户请求到达前端控制器，它就相当于 mvc 模式中的 c，dispatcherServlet 是整个流程控制的中心，由
		它调用其它组件处理用户的请求，dispatcherServlet 的存在降低了组件之间的耦合性。



	2. HandlerMapping：处理器映射器			--DispatcherServlet --> 通过映射 寻找与之匹配的 Handler(Controller)
			
			HandlerMapping 负责根据用户请求找到 Handler 即处理器，SpringMVC 提供了不同的映射器实现不同的
		映射方式，例如：配置文件方式，实现接口方式，注解方式等。



	3. HandlerAdapter：处理器适配器				--DispatcherServlet --> 执行 Handler的方法
			通过 HandlerAdapter 对处理器进行执行，这是适配器模式的应用，通过扩展适配器可以对更多类型的处理
		器进行执行。



	4. Handler：处理器
	
			它就是我们开发中要编写的具体业务控制器。由 DispatcherServlet 把用户请求转发到 Handler。由
		Handler 对具体的用户请求进行处理。



	5. ViewResolver：视图解析器				--DispatcherServlet --> 通过视图解析返回 View
			View Resolver 负责将处理结果生成 View 视图，ViewResolver 首先根据逻辑视图名解析成物理视图名
		即具体的页面地址，再生成 View 视图对象，最后对 View 进行渲染将处理结果通过页面展示给用户。
	
		
		
	6. View：视图
			SpringMVC 框架提供了很多的 View 视图类型的支持，包括：jstlView、freemarkerView、pdfView
		等。我们最常用的视图就是 jsp。
			一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，需要由程序员根据业务需求开
		发具体的页面。



	7. <mvc:annotation-driven>说明
			使用 <mvc:annotation-driven> 自动加载 RequestMappingHandlerMapping （处理映射器） 和
		RequestMappingHandlerAdapter （ 处 理 适 配 器 ） ， 可 用 在 SpringMVC.xml 配 置 文 件 中 使 用
		<mvc:annotation-driven>替代注解处理器和适配器的配置。
			
			它就相当于在 xml 中配置了：
			<!-- 上面的标签相当于 如下配置-->
			
			<!-- HandlerMapping -->
			<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"></bean>
			<bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"></bean>
			
			<!-- HandlerAdapter -->
			<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"></bean>
			<bean class="org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter"></bean>
			<bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"></bean>
			
			<!-- HadnlerExceptionResolvers -->
			<bean class="org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver"></bean>
			<bean class="org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver"></bean>
			<bean class="org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver"></bean>


	8. 注解：@RequestMapping 
		
		1.使用说明
			源码：	@Target({ElementType.METHOD, ElementType.TYPE})		//该注解可于方法上、类上
					@Retention(RetentionPolicy.RUNTIME)
					@Documented
					@Mapping
					public @interface RequestMapping {
					}
					
			作用：	用于建立请求 URL 和处理请求方法之间的对应关系。
			
			出现位置：
					1.类上：
						请求 URL 的第一级访问目录。此处不写的话，就相当于应用的根目录。写的话需要以/开头。
						它出现的目的是为了使我们的 URL 可以按照模块化管理.
						
						例如：
							@Controller
							@RequestMapping(path="/account")
							public class HelloController {
							
							账户模块 URI：
								/account/add			( @RequestMapping 中写 path="/account")
								/account/update
								/account/delete
								...
							订单模块 URI：
								/order/add				( @RequestMapping 中写 path="/order")
								/order/update
								/order/delete

					2.方法上：
						请求 URL 的第二级访问目录。
			
						属性：
							value：用于指定请求的 URL。它和 path 属性的作用是一样的。
							method：用于指定请求的方式。RequestMethod.POST(GET、HEAD、PUT、PATCH、DELETE、OPTIONS、TRACE)
							params：用于指定限制请求参数的条件。它支持简单的表达式。要求请求参数的 key 和 value 必须和配置的一模一样。
							headers：用于指定限制请求消息头的条件。
						
						例如：
							@RequestMapping(value="/testRequestMapping",method={RequestMethod.POST})  //该方法请求方式需为POST
							public String testRequestMapping(){										  //超链接请求方式为GET
								...
								return "success";
							}
							
							params = {"accountName"}:	表示请求中 必须有请求参数，属性名为 accountName。
							params = {"accountName=heihei"}:	表示请求中 请求参数 accountName 的值必须为 heihei
							params = {"moeny!100"}:		表示请求参数中 money 不能是 100。
							headers = {"Accept"}:		表示请求头 中必须要包含 Accept请求头(值不用管)。
							
						注意：
						以上四个属性只要出现 2 个或以上时，他们的关系是与的关系。

 */
 

/*								 谷歌中(google)查看请求头、响应头(小技巧)
 
  	1. 浏览器中 按F12 打开调试工具，
  	2. 点击 Network 即可进入，
  	3. 开始发送请求，即可拦截请求头、响应头，
  	4. 下方 Headers 中就是请求头、响应头的参数。
  	
 */


/*								 请求参数的绑定、post请求中文时乱码、自定义类型转换器 Converter、获得原生的ServletAPI对象
 	1.何为请求参数的绑定
 		程序中得到 请求参数 即为 springMVC中请求参数的绑定。
 	
 	2.绑定案例：
 		1.转为 基本类型、String
	 		请求参数:			username=hehe&password=123
	 		MVC框架：			sayHello(String username,String password){}
	 		规则：				定义方法的 参数中： 方法参数名 = 网页参数名 即可。
 		
 		2.转为 Javabean类型
	 		请求参数:			username=hehe&password=123
	 		MVC框架：			sayHello(User user){}					//如果需要获取 请求参数，则在方法中 定义 传入参数
	 		规则：				1.网页参数名 = Javabean中 成员变量名。
	 							2.方法参数是 Javabean类型。
 		
 		
 		3.转为 Javabean关联对象(Javabean1中包含Javabean2)
	 		请求参数:			<form action="param/saveAccount" method="post">
							        姓名：<input type="text" name="username" /><br/>
							        密码：<input type="text" name="password" /><br/>
							        金额：<input type="text" name="money" /><br/>
							        用户姓名：<input type="text" name="account.uname" /><br/>
							        用户年龄：<input type="text" name="account.age" /><br/>
							    <input type="submit" value="提交" /></form>
							    
	 		MVC框架：			@RequestMapping(path="/param/saveAccount")
	 							sayHello(User user){}
	 							
	 		规则：				1.网页参数名 = bean1中的 成员变量名。
 								2.网页参数名 写作： bean2.属性名	(account.uname)
 								
 										
 		4.转为 Javabean类型(包含 List Map类型 属性)
 			请求参数:			<form action="param/saveAccount" method="post">
							        姓名：<input type="text" name="username" /><br/>
							        密码：<input type="text" name="password" /><br/>
							        金额：<input type="text" name="money" /><br/>
							
							        用户姓名：<input type="text" name="list[0].uname" /><br/>
							        用户年龄：<input type="text" name="list[0].age" /><br/>
							
							        用户姓名：<input type="text" name="map['one'].uname" /><br/>
							        用户年龄：<input type="text" name="map['one'].age" /><br/>
								<input type="submit" value="提交" /></form>
								
	 		MVC框架：			@RequestMapping(path="/param/saveAccount")
	 							sayHello(User user){}
	 		
	 		规则：				1. List中对象：网页参数名 写作： list[0].属性名	(list[0].uname)
	 							2. Map中对象：  网页参数名 写作： map['one'].属性名	(map['one'].uname)
	 							
		5.将多个网页参数 绑定到String[]中。
			JSP页中：			<c:forEach items="${itemList }" var="item">
					            	<tr>
					           			<td><input type="checkbox" name="ids" value="${item.id}"/></td>
									</tr>
			            		</c:forEach>
	            		
			Controller中：		public String deleteItem(Integer[] ids){	//方法中用String[]接收，网页参数名=传入参数名
									...
								}
								
			规则：				1.方法中用String[]接收，网页参数名=方法参数名
 	
 	3.支持的数据类型：(方法中)  
		1. 基本数据类型和 String类型
		2. 实体类型（JavaBean）
		3. 集合数据类型（List、map集合等）

			
	4.使用要求：
		1.基本数据类型和字符串类型:
			1. 提交表单的name 和 参数的名 相同
			2. 区分大小写
		2. 实体类型（JavaBean）:
			1. 提交表单的name和JavaBean中的属性名称需要一致
			2. 如果一个JavaBean类中包含其他的引用类型，那么表单的name属性需要编写成：对象.属性 例如：address.name
		3. 给集合属性数据封装:
			1. JSP页面编写方式：list[0].属性


   -----------------------------------------------------------------------------------------------------------	
	5.post请求下--参数为中文时 的乱码问题
		1.解决方案：配置一个 CharacterEncodingFilter 过滤器即可。（由 spring 提供）
		2.在 web.xml中配置。
		3.配置<filter>标签：
							<filter>
								<filter-name>characterEncodingFilter</filter-name>
								<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
								<init-param>
									<param-name>encoding</param-name>
									<param-value>UTF-8</param-value>
								</init-param>
							</filter>
							<filter-mapping>
								<filter-name>characterEncodingFilter</filter-name>
								<url-pattern>/*</url-pattern>
							</filter-mapping>
   -------------------------------------------------------------------------------------------------------------	
		
	6.自定义类型转换器
		1. 表单提交的任何数据类型全部都是字符串类型，但是后台定义Integer类型，数据也可以封装，说明Spring框架内部会默认进行数据类型转换。
		2. 如果想自定义数据类型转换，可以实现Converter的接口：
			1. 自定义类型转换器，实现接口：
					public class StringToDateConverter implements Converter<String, Date>{

						public Date convert(String source) {
							if(source == null) {
								throw new RuntimeException("参数不能为空");
							}
							try {
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								// 解析字符串
								Date date = df.parse(source);
								return date;
							} catch (Exception e) {
								throw new RuntimeException("类型转换错误");
							}
						}
					}
			2. 注册自定义类型转换器，在springmvc.xml配置文件中编写配置：
					<!-- 注册自定义类型转换器，原本转换器中，添加自定义类型转换器 -->
					<bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
						<property name="converters">
							<set>
								<bean class="cn.itcast.utils.StringToDateConverter"/>		//添加自定义转换器
							</set>
						</property>
					</bean>
					<!-- 开启Spring对MVC注解的支持 加载自定义类型转换器 -->
					<mvc:annotation-driven conversion-service="conversionService"/>
	---------------------------------------------------------------------------------------------------------------
	
	7.在控制器中 使用原生的ServletAPI对象(request、response)
		1. 只需要在 控制器的方法参数 定义 HttpServletRequest和 HttpServletResponse对象.


 */


/*								 SpringMVC 常用注解、测试注解报错
 * 
	1. @RequestParam
		1. 作用：绑定 指定名称 参数 。请求参数 方法参数 名称可以不一致。
		2. 属性：
			1. value：请求参数中的名称
			2. required：请求参数中是否必须提供此参数，默认值是true，必须提供
		3. 代码如下：
					@RequestMapping(path="/hello")
					public String sayHello(@RequestParam(value="username",required=false)String name) {
						System.out.println(name);
						return "success";
					}
	
	2. @RequestBody
		1. 作用：获取post 请求体（注意：get请求方式不可以）
		2. 属性：
			1. required：是否必须有请求体，默认值是true
		3. 代码如下：
					@RequestMapping(path="/hello")
					public String sayHello(@RequestBody String body) {
						System.out.println(body);
						return "success";
					}

	3. @PathVariable
		1. 作用：绑定url中 占位符中数据。例如：url为	/delete/{id}，{id}就是占位符
		2. 属性：
			1. value：指定url中的占位符名称
		3. Restful风格的URL：
			1. 请求路径一样，可以根据不同的请求方式去执行后台的不同方法
			2. restful风格的URL优点：
									1. 结构清晰
									2. 符合标准
									3. 易于理解
									4. 扩展方便
		4. 代码如下：
					@RequestMapping(path="/hello/{sid}")
					public String sayHello(@PathVariable(value="sid") String id) {
						System.out.println(id);
						return "success";
					}

	4. @RequestHeader		（用处不大）
		1. 作用：获取指定请求头的值
		2. 属性:
			1. value：请求头的名称
		3. 代码如下:
					@RequestMapping(path="/hello")
					public String sayHello(@RequestHeader(value="Accept") String header) {
						System.out.println(header);
						return "success";
					}

	5. @CookieValue
		1. 作用：获取请求中 指定cookie名称的值
		2. 属性：
			1. value：cookie的名称
		3. 代码：
					@RequestMapping(path="/hello")
					public String sayHello(@CookieValue(value="JSESSIONID") String cookieValue) {
						System.out.println(cookieValue);
						return "success";
					}
	
	6. @ModelAttribute
		1. 作用
			1. 出现在方法上：先执行该方法，再执行 控制器方法。
			2. 出现在参数上：获取指定的数据给参数赋值。
		2. 应用场景：
			1. 方法上：当提交表单数据不是完整的实体数据时，保证没有提交的字段使用 @ModelAttribute方法 返回的数据。
		3. 具体的代码：
			1. 修饰方法，有返回值：(出现在方法1上(有返回值))
					@ModelAttribute	
					public User showUser(String name) {
						System.out.println("showUser执行了...");
						// 模拟从数据库中查询对象
						User user = new User();
						user.setName("哈哈");
						user.setPassword("123");
						user.setMoney(100d);
						return user;			//return后，将User对象传递给 下一个方法的形参
					}
					
					@RequestMapping(path="/updateUser")
					public String updateUser(User user) {	//User对象传递到此处,没有提交的字段使用User原来的数据
						System.out.println(user);
						return "success";
					}
			2. 修饰方法，没有返回值：(出现在方法1上(无返回值)、出现在方法2参数上)
					@ModelAttribute	
					public void showUser(String name,Map<String, User> map) {
						System.out.println("showUser执行了...");
						// 模拟从数据库中查询对象
						User user = new User();
						user.setName("哈哈");
						user.setPassword("123");
						user.setMoney(100d);
						map.put("abc", user);	//将User对象存入Map集合
					}
					
					@RequestMapping(path="/updateUser")
					public String updateUser(@ModelAttribute(value="abc") User user) {	  //从Map集合中取出User对象，给形参
						System.out.println(user);										  //没有提交的字段使用User原数据
						return "success";
					}

	7. @SessionAttributes()
		1. 作用：
			1.出现在类上：添加 key，将Model集合中 key数据 存入到session域对象中。
			2.Model接口(框架提供)：
				1.实现类 ModelMap、ExtendedModelMap。
				2.是 Map集合,将数据存入request域中。
				3.想使用时，在方法参数上定义即可。
		2. 属性：
			1. value：指定存入属性的名称
		3. 代码如下：
					@Controller
					@RequestMapping(path="/anno")
					@SessionAttributes(value={"msg","age"})   //把 Model中 msg、age 存入到session域对中
					public class HelloController {					
					
						@RequestMapping(value="/testSessionAttributes")		// model中 存入key、value
					    public String testSessionAttributes(Model model){
					        // 底层会存储到request域对象中
					        model.addAttribute("msg","美美");
					        model.addAttribute("age",18);
					        return "success";
					    }
					
					    @RequestMapping(value="/getSessionAttributes")		// model中 取出key、value
					    public String getSessionAttributes(ModelMap modelMap){
					        String msg = (String) modelMap.get("msg");
					        String age = (String) modelMap.get("age");
					        System.out.println(msg + age);
					        return "success";
					    }
					
					    @RequestMapping(value="/delSessionAttributes")	//清除 session域中数据(model中还有数据)
					    public String delSessionAttributes(SessionStatus status,Model model){
					        status.setComplete();
					        System.out.println(model);
					        return "success";
					    }
					}
			
-------------------------------- 测试注解时报错，无法启动 tomcat服务器------------------------------------------
	解决方案：重新 remove、add项目，即可

 */


/*								2.响应数据和结果视图		(--见: Web20_SpringMVC_Day2)
 * 
	1.Controller返回值 种类：	(对应不同响应数据和结果视图)(默认跳转方式：请求转发)
	
		1.返回值：字符串。
			方法返回值 String，return指定视图的名称，跳转到 视图解析器下 指定名称的页面。
				例：		@RequestMapping(value="/hello")
						public String sayHello() {
							// 跳转到XX页面
							return "success";
						}
			
		2.返回值：void。无 return。
 			方法返回值 void，无 return，默认跳转到 视图解析器 "/con1"目录下 名为"2"的页面。
 												（根据 类映射    @RequestMapping(value="/con1")）
 												（根据 方法映射 @RequestMapping(value="/2")）
 			无"xx/con1/2.yy"的页面，则报404异常。
 			
 			
 		3.返回值：void。有 return。
 			方法返回值 void，有 return，默认不发生跳转。
 			
	 			1.需跳转：在方法中，使用请求转发或者重定向跳转到指定的页面。再加 return;
		 			例：		重定向：response.sendRedirect(request.getContextPath()+"/index.jsp");
		 					转发：request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);
	 			2.不跳转：浏览器直接响应。
	 				例:		response.setCharacterEncoding("UTF-8");
	 						response.setContentType("text/html;charset=UTF-8");
	 						response.getWriter().print("你好");
	 						return;
 		
 		4.返回值：ModelAndView对象(可存储数据\跳转页面)
 			方法返回值 ModelAndView对象，可以向对象中存储数据(存到 request域中)：	mv.addObject("users", users);
 									     也可以跳转页面(视图解析器下 同名页面)：	mv.setViewName("success");
			ModelAndView对象可以在方法中创建：
				例：		ModelAndView mv = new ModelAndView();
						mv.addObject("users", users);
						mv.setViewName("success");
						return mv;
			
			
			
	2. SpringMVC框架提供的转发和重定向

		1.手动跳转：关键字 forward 请求转发:	return "forward:/xx/yy.jsp"(默认web应用目录下)
		
				例：		@RequestMapping(path="/5")	
						public String test5() {
							return "forward:/WEB-INF/pages/success.jsp";			--跳转到页面success.jsp
						}
						
						1.forward:xx			--跳转到 同控制器下 方法
						2.forward:/xx			--跳转到 web应用下: /xx	(可访问/WEB-INF 中资源)

		2.手动跳转：关键字 redirect 重定向:		return "redirect:/xx/yy.jsp"(默认web应用目录下/con1/下,框架内部封装了项目名/类映射名/)
			
				例：		@RequestMapping(path="/6")	
						public String test6() {
							return "redirect:5";			--跳转到 本控制器中 @RequestMapping("/5") 方法
						}
						
						1.redirect:xx			--跳转到 同控制器下 方法
						2.redirect:/xx			--跳转到 web应用下: /xx	(不可访问/WEB-INF 中资源)
						3.redirect:http//xx		--支持跳转绝对路径
						
	
						
						
 */	

/*								 @ResponseBody 向客户端响应 json数据(页面发送异步请求时，需要服务器返回一个json数据)
	
		1.webapp添加依赖包：	webapp/js 目录下，放入 jquery-2.2.4 依赖包。
		
		2.配置-不拦截静态资源： 
				原因：即使对服务器端JS、CSS的请求，核心控制器也会拦截 并查找处理器适配器，从而无法访问JS、CSS文件。
				1. springmvc.xml中配置。
				2. 添加<mvc:resources location="" mapping=""></mvc:resources>标签。
						location：该文件地址不拦截。	如：	location="/js/"
						mapping：该映射请求不拦截。	如：	mapping="/js/**"
				3.例：
						<mvc:resources location="/css/" mapping="/css/**"/> <!-- 样式 -->
						<mvc:resources location="/images/" mapping="/images/**"/> <!-- 图片 -->
						<mvc:resources location="/js/" mapping="/js/**"/> <!-- javascript -->
						
		3.页面: 编写JQ代码，发送ajax请求。
		
		4.处理器Controller：
				--- 获取json数据，转为bean对象。
				--- springmvc框架 支持将json转为bean。		(条件：json的key名  与 bean的属性名  相同)
				
				准备：
					0. 导包：							(mvc框架 json 转 bean 依赖包)
							1. jackson-databind
							2. jackson-core
							3. jackson-annotations
							
				1.得到json数据 对应的bean: 
							传入参数为：封入数据的 Bean		(后台自动将json数据 封装到 bean中)
							
				2.响应客户端 (向浏览器返回 json数据)：
							返回值为: Bean类型			(后台自动将bean对象 转为json 响应给客户端)
							返回值前加入注解：@ResponseBody
							
				3.例：	
						@RequestMapping(path="/testAjax")
						public @ResponseBody User testAjax(User user) {
							System.out.println(user);
							user.setName(user.getName()+"1");
							user.setAge(user.getAge()+1);
							return user;
						}

 */

/*								 WEB-INF目录访问权限
 *
	1.WEB-INF目录下的文件，浏览器无法直接访。
		1.WEB-INF不作为Web应用的公共文档树的一部分。因此，WEB-INF 目录下的资源不是为客户直接服务的。
		2.WEB-INF下文件，请求转发(forward)可以访问，重定向(redirect)不可访问。
		3.WEB-INF下面的内容，服务器级别才能访问，客户端并不能访问。
			转发：	服务器级别。	(默认web应用下)	request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);
			重定向：	客户端级别。	(默认主机名下)	response.sendRedirect();
		3.如果需要直接访问，可以放在和WEB-INF同级目录下。 
 */

/*								 SpringMVC 实现文件上传	(--见: Web20_SpringMVC_Day2_FileUpload)								 
 * 
 	1.表单数据/文件上传 的必要前提：
 		A form 表单的 enctype = "multipart/form-data"			---请求体为 分界符划分/几个部分的描述
 			
 			(默认值是：	application/x-www-form-urlencoded		---请求体为 键值对形式
																---enctype：表单请求正文的类型。
			
		B method 属性取值必须是 Post。
		
			(get方式：	1.请求数据会在 URL栏提交，
					 	2.IE的 URL长度限制是2083个字符，(2K+35) 
					 	3.提交数据类型：只允许ASCII码。)
		
		C 提供一个文件选择域<input type="file" name="upload"/>
		
	2.文件上传的原理
		1.当 form 表单的 enctype 取值不是默认值后，request.getParameter()将失效。

		2.当 form 表单的 enctype 取值为 Multipart/form-data 时，请求正文内容就变成：
		   每一部分都是 MIME 类型描述的正文：
			-----------------------------7de1a433602ac 分界符
			Content-Disposition: form-data; name="userName" 协议头
			aaa 协议的正文
			-----------------------------7de1a433602ac
			Content-Disposition: form-data; name="file"; 
			filename="C:\Users\zhy\Desktop\fileupload_demofile\b.txt"
			Content-Type: text/plain 协议的类型（MIME 类型）
			bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
			-----------------------------7de1a433602ac--
	
	3.第三方组件实现文件上传
		1.使用 Commons-fileupload 组件实现文件上传。
		2. Commons-fileupload 组件可以解析 multipart类型的请求体。


	-------------------------------三种方式 处理文件上传--------------------------------------------------------
	注：
		三种方式都需导包：		1.Commons-fileupload		--解析requst 得到上传文件 
		
		三种方式都需编写 JSP页面：
								<form action="con/upload" method="post" enctype="multipart/form-data">
									名称：<input type="text" name="picname"/><br/>
									图片：<input type="file" name="upload"/><br/>	//文件上传标签
									<input type="submit" value="上传"/>
								</form>

	
	1.传统方式 处理上传文件
		1.原理：	利用 Commons-fileupload 包中的 ServletFileUpload 对象解析 request，获得文件项。
		2.需要：	导Commons-fileupload包
		
		例：		public String fileuoload1(HttpServletRequest request) {
					// 使用fileupload组件完成文件上传
			        // 上传的位置
			        String path = request.getSession().getServletContext().getRealPath("/uploads/");
			        // 判断，该路径是否存在
			        File file = new File(path);
			        if(!file.exists()){
			            // 创建该文件夹
			            file.mkdirs();
			        }
			
			        // 解析request对象，获取上传文件项
			        DiskFileItemFactory factory = new DiskFileItemFactory();		//文件上传 核心代码
			        ServletFileUpload upload = new ServletFileUpload(factory);		//文件上传 核心代码
			        
			        // 解析request
			        List<FileItem> items = upload.parseRequest(request);			//文件上传 核心代码
			        // 遍历
			        for(FileItem item:items){
			            // 进行判断，当前item对象是否是上传文件项
			            if(item.isFormField()){
			                // 说明普通表单项
			            }else{
			                // 说明上传文件项
			                // 获取上传文件的名称
			                String filename = item.getName();						//获得文件名
			                // 把文件的名称设置唯一值，uuid
			                String uuid = UUID.randomUUID().toString().replace("-", "");
			                filename = uuid+"_"+filename;
			                // 完成文件上传
			                item.write(new File(path,filename));
			                // 删除临时文件
			                item.delete();
			            }
			        }
					return "success";
				}
				
	
	2. springMVC方式 处理上传文件
		1.原理：	利用 MVC框架的 文件解析器 MultipartFile，获得文件项 MultipartFile upload。
				request ————> 前端控制器 ————> 文件解析器 ————> 得到upload对象 ————> 处理器Controller
		
		2.需要：	1.导Commons-fileupload包.
				2.配置文件解析器：
				
								<!-- 配置文件上传解析器 -->
								<bean id="multipartResolver" <!-- id、class 的值是固定的-->
									class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
									<!-- 设置上传文件的最大尺寸为 5MB -->
									<property name="maxUploadSize">
										<value>5242880</value>
									</property>
								</bean>

				3.在Controller类，对应方法中，定义传入参数：MultipartFile upload(此处名称 与文件上传标签 name属性值名称 必须相同)
		
		例：		public String fileuoload2(HttpServletRequest request, MultipartFile upload) {
					// 使用fileupload组件完成文件上传
			        // 上传的位置
			        String path = request.getSession().getServletContext().getRealPath("/uploads/");
			        // 判断，该路径是否存在
			        File file = new File(path);
			        if(!file.exists()){
			            // 创建该文件夹
			            file.mkdirs();
			        }
			
			        // 说明上传文件项
			        // 获取上传文件的名称
			        String filename = upload.getOriginalFilename();			//获得文件名
			        // 把文件的名称设置唯一值，uuid
			        String uuid = UUID.randomUUID().toString().replace("-", "");
			        filename = uuid+"_"+filename;
			        
			        // 完成文件上传
			        upload.transferTo(new File(path,filename));				//文件上传 核心代码
			
			        return "success";
				}
				
	3. springMVC方式 跨服务器上传
		1.原理：	利用 MVC框架的 文件解析器 MultipartFile，获得文件项 MultipartFile upload。
				request ————> 前端控制器 ————> 文件解析器 ————> 得到upload对象.
		
		2.需要：	0.新开一个服务器，修改8080、8005、8009端口号，部署一个新web项目用于保存文件。
					新开服务器步骤：	1.见：https://blog.csdn.net/SUNBOYmxbsH/article/details/78824963
									2.设置： 右键创建完的服务器 —— open —— Server Locations中勾选第二个选项 —— 保存 (选择 项目部署到哪个位置)
 										(若无法修改Server Locations选项，先remove该服务器所有项目，再点Publish，就可以选择了)
					
				1.导Commons-fileupload包，该包用于 解析request 获得上传文件.
				2.导 jersey-core、jersey-client包，该包用于 跨服务器保存文件。
				3.在springmvc.xml中，配置文件解析器(见方式2)
				4.在Controller类，对应方法中，定义传入参数：MultipartFile upload
				
		3.异常原因：	1.异常： ...returned a responses tatus of 409 Conflict.
					    文件服务器无对应文件夹 uoloadFile，需要手动创建 uoloadFile文件夹！！
					
					2.异常：...returned a response status of 403 Forbidden.
					    取消tomcat服务器只读权限.	(默认外界只可读、不可写，修改只读权限为false即可)
						
					(相关配置：在tomcat/conf/web.xml中,加入:	
														<servlet>				
															<servlet-name>default</servlet-name>
															...
															<init-param>							//新增的参数
																<param-name>readonly</param-name>	//新增的参数
																<param-value>false</param-value>	//新增的参数
															</init-param>							//新增的参数
														</servlet> 	
					
					3.异常： java.net.MalformedURLException: unknown protocol: localhost] with root cause
					    URL协议格式错误，在保存文件 的服务器地址，加上：http://
					
		例：		public String upload3(MultipartFile upload) throws Exception {
					//定义保存文件 的服务器地址
					String path = "http://localhost:8090/uploadFile/";
					
					//跨服务器保存文件
					//1.创建客户端对象
					Client client = Client.create();	//jersey-client包下，获取客户端对象，用于跨服务器连接
					
					//2.客户端和文件服务器进行连接
					WebResource webResource = client.resource(path + upload.getOriginalFilename());	//跨服务器连接，连接到文件服务器 的保存文件路径
					
					//3.保存文件
					webResource.put(upload.getBytes());		//上传到文件服务器
					return "success";
				}
 */

/*								 SpringMVC 异常处理器 HandlerExceptionResolver	(--见: Web20_SpringMVC_Day2_FileUpload)	

	1.异常处理的思路
		   系统中异常包括两类：预期异常和运行时异常 RuntimeException，前者通过捕获异常从而获取异常信息，后者主要通过规范代码开发、测试
		通过手段减少运行时异常的发生。系统的 dao、service、controller 出现都通过 throws Exception 向上抛出，最后由 springmvc 前端
		控制器 交由 异常处理器 进行异常处理.
	
	
	-------------------------------------------------------------------------------------------------------------------
	2.SpringMVC 异常处理 步骤
	
		1.导包：	无需导额外包
		
		2.编写自定义异常类：(做提示信息的)
				异常代码位置使用 try..catch，catch中 throw 抛出自定义异常，方法声明上直接抛出异常即可
		
   (必须)3.编写自定义异常处理器：(实现 HandlerExceptionResolver 接口)
				实现接口方法 resolveException()：作用---在异常发生后跳转到错误页面。
				
			例：	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
			        // 获取到异常对象
			        SysException e = null;
			        if(ex instanceof SysException){
			            e = (SysException)ex;
			        }else{
			            e = new SysException("系统正在维护....");
			        }
			        // 创建ModelAndView对象
			        ModelAndView mv = new ModelAndView();
			        mv.addObject("errorMsg",e.getMessage());
			        mv.setViewName("error");
			        return mv;
			    }		
				
   (必须)4.配置异常处理器:(springmc.xml中)
			
			例：	<bean id="exceptionResolver" class="cn.itcast.exception.自定义异常处理器">
	
 */

/*								 SpringMVC 拦截器 HandlerInterceptor		(--见: Web20_SpringMVC_Day2_Interceptor)				
	
	1.拦截器的作用
		Spring MVC 的处理器拦截器类似于 Servlet 开发中的过滤器 Filter，用于 对处理器进行预处理和后处理。

	2.拦截器和过滤器(Filter)的区别
	
		过滤器：	1. servlet 规范中的一部分，任何 java web 工程都可以使用。
				2.在 url-pattern 中配置了/*之后，可以对所有要访问的资源拦截。

		拦截器：	1. SpringMVC 框架独有的，只有使用了 SpringMVC 框架的工程才能用。
				2.只会拦截访问的控制器方法，如果访问的是 jsp，html,css,image 或者 js，不进行拦截。
				
	-------------------------------------------------------------------------------------------------			
	1.配置拦截器 步骤：		(--见: Web20_SpringMVC_Day2_Interceptor)	
	
		1.导包：	无需导额外包
		
		2.编写拦截器，实现 HandlerInterceptor接口：(可重写三个方法)
		
				1.预处理方法 boolean preHandle()：	1.在Controller方法前，执行
													2.返回true：放行，执行下一个 拦截器/controller 方法
													3.返回false：不放行，可使用 request/response对象 跳转到其他页面
													
				2.后处理方法 void postHandle():		1.在Controller方法后，执行，跳转到success.jsp前
													2.可使用 request/response对象 跳转到其他页面(controller方法跳转的页面将不显示)
				
				3.收尾方法 void afterCompletion():	1.在跳转success.jsp后，执行
													2.不能进行跳转页面
		
		3.IOC中配置拦截器：springmvc.xml中配置。
					
				例：	<mvc:interceptors>
						<mvc:interceptor>				--配置一个拦截器 
							 <mvc:mapping path="/**"/>	--要拦截的具体方法：	path="/**" --全部方法都拦截	path="/user/*" --/user访问目录下的方法都拦截
							 <bean class="wxt.interceptor.MyInterceptor"></bean> 	--配置拦截器对象
						</mvc:interceptor>
					</mvc:interceptors>
					
		4.如果配置了多个拦截器，按照springmvc.xml中的顺序进行拦截。
 */


/*								3. SSM 整合
 * 
	1.搭建整合环境
		1.整合说明：	SSM整合可以使用多种方式，选择 XML+注解方式。
		
		2.整合思路：	1.先搭建整合的环境
					2.搭建 Spring 的环境
					3. Spring 整合 Springmvc框架
					4. Spring 整合 Mybatis框架
					
	-----------------------------------------------------------------------------------------------------------------		
		applicationContext.xml:
							1.开启注解IOC (一项)			spring
							2.开启注解AOP (一项)			spring
							3.数据源	(一项)				MyBatis
							4.sqlSessionFactory	(一项)	MyBatis
							5.扫描的dao包	(一项)		MyBatis
							6.声明式事务	(三项)			spring/MyBatis
							
		web.xml:
							1.DispatcherServlet	(两项)	springMVC
							2.中文乱码过滤器	(两项)		springMVC
							3.启动加载IOC	(两项)		spring/springMVC
		
		springmvc.xml:
							1.开启注解	(一项)			springMVC
							2.视图解析器	(一项)			springMVC
							3.过滤静态资源	(一项)		springMVC
							4.开启MVC注解的支持	(一项)	springMVC					
		
	-----------------------------------------------------------------------------------------------------------------
	0. 创建数据库和表结构：
					create database ssm;
					use ssm;
					create table user(
						id int primary key auto_increment,
						name varchar(20),
						money double
					);
	
	1. SSM 整合步骤：
	
		1.创建maven的工程
					1. 创建ssm_parent父工程（打包方式选择pom，必须的）
					2. 创建ssm_web子模块（打包方式是war包）
					3. 创建ssm_service子模块（打包方式是jar包）
					4. 创建ssm_dao子模块（打包方式是jar包）
					5. 创建ssm_domain子模块（打包方式是jar包）
					6. web依赖于service，service依赖于dao，dao依赖于domain
		
		2.导包：		1.spring-context:			Spring IOC容器 
					2.aspectjweaver:			Spring AOP
					3.spring-web:				springMVC框架
					4.spring-webmvc:			springMVC框架
					5.mybatis：					Mybatis框架
					6.spring-tx:				Spring事务
					7.spring-jdbc：				Spring 事务管理器
					
					8.servlet-api:				servlet API
					9.jsp-api:					JSP API
					10.jstl:					JSP中 el表达式
					
					11.mybatis-spring：			Spring整合 mybatis			(新增)
					12.spring-test:				Spring整合 junit
					13.junit(4.12)：				junit测试
					14.mysql-connector-java:	MySQL驱动					8.0.16
					15.log4j：					日志
					16.slf4j-api：				日志
					17.slf4j-log4j12：			日志
					18.c3p0：					c3p0连接池
					
		3.编写dao、service类：
					1.编写实体类，bean包下
					2.编写dao接口，dao包下
					3.编写service接口、实现类，service包下
					4.编写controller类，controller包下
		
		
		4.搭建spring环境：
		
			1.配置 spring 环境：		1.创建 applicationContext.xml，编写 spring 配置。
									2.开启注解扫描，扫描service和dao，controller注解不扫描。
										
			2.测试 spring 框架：		1.建test类，创建IOC容器，执行service中的方法：
			
										ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
										AccountService as = (AccountService) ac.getBean("accountService");
										as.findAll();
			
			 例：-------------------		applicationContext.xml		-----------------------------
			 		<?xml version="1.0" encoding="UTF-8"?>
					<beans xmlns="http://www.springframework.org/schema/beans"
					       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					       xmlns:context="http://www.springframework.org/schema/context"
					       xmlns:aop="http://www.springframework.org/schema/aop"
					       xmlns:tx="http://www.springframework.org/schema/tx"
					       xsi:schemaLocation="http://www.springframework.org/schema/beans
						http://www.springframework.org/schema/beans/spring-beans.xsd
						http://www.springframework.org/schema/context
						http://www.springframework.org/schema/context/spring-context.xsd
						http://www.springframework.org/schema/aop
						http://www.springframework.org/schema/aop/spring-aop.xsd
						http://www.springframework.org/schema/tx
						http://www.springframework.org/schema/tx/spring-tx.xsd">
					
					    <!--开启注解的扫描，希望处理service和dao，controller不需要Spring框架去处理-->
					    <context:component-scan base-package="cn.itcast" >
					        <!--配置哪些注解不扫描-->
					        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
					    </context:component-scan>
					</beans>
		
		
		5. Spring 整合 SpringMVC:
			
			1.配置 SpringMVC环境：	1.在web.xml中，配置DispatcherServlet前端控制器
									2.在web.xml中，配置DispatcherServlet过滤器解决中文乱码
									3.创建 springmvc.xml，编写 MVC 配置.
									
			2.测试 SpringMVC框架：	1.编写index.jsp、list.jsp，编写超链接： <a href= "account/findAll">查询所有</a>
									2.创建AccountController类，编写方法，测试：
									
										@RequestMapping("/findAll")
										public String findAll() {
											System.out.println("表现层：查询所有账户...");
											return "list";
										}
			
			3.Spring 整合 SpringMVC的框架：
									1.目的：在controller中能调用service对象中的方法。
											
									2.在web.xml中配置ContextLoaderListener监听器(该监听器只能加载WEB-INF/classes下applicationContext.xml配置文件)
										项目启动时，服务器创建IOC对象，controller直接注入service对象就可使用(加载applicationContext.xml)
										(不配置则 启动无IOC对象，需要时手动创建)
									
									3.在controller中注入service对象，调用service对象的方法进行测试:
									
											@Autowired
											private AccountService accoutService;
											
											@RequestMapping("/findAll")
											public String findAll() {
												System.out.println("表现层：查询所有账户...");
												accoutService.findAll();
												return "list";
											}
									
			 例：----------------------------	web.xml		-----------------------------------
				 	<!-- 前端控制器 -->
					<servlet>
						<servlet-name>dispatcherServlet</servlet-name>
						<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
						<!-- 加载springmvc.xml配置文件 -->
						<init-param>
							<param-name>contextConfigLocation</param-name>
							<param-value>classpath:springmvc.xml</param-value>
						</init-param>
						<!-- 启动服务器，加载该servlet -->
						<load-on-startup>1</load-on-startup>
					</servlet>
					<!-- 前端控制器映射 -->
					<servlet-mapping>
						<servlet-name>dispatcherServlet</servlet-name>
						<url-pattern>/</url-pattern>
					</servlet-mapping>
				
					<!-- 中文乱码过滤器 -->
					<filter>
						<filter-name>characterEncodingFilter</filter-name>
						<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
						<init-param>
							<param-name>encoding</param-name>
							<param-value>UTF-8</param-value>
						</init-param>
					</filter>
					<!-- 中文乱码过滤器映射 -->
					<filter-mapping>
						<filter-name>characterEncodingFilter</filter-name>
						<url-pattern>/*</url-pattern>
					</filter-mapping>
					
			 例：------------------------	springmvc.xml	------------------------------------
			 		<?xml version="1.0" encoding="UTF-8"?>
					<beans xmlns="http://www.springframework.org/schema/beans"
					       xmlns:mvc="http://www.springframework.org/schema/mvc" 
					       xmlns:context="http://www.springframework.org/schema/context"
					       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					       xsi:schemaLocation="
					        http://www.springframework.org/schema/beans
					        http://www.springframework.org/schema/beans/spring-beans.xsd
					        http://www.springframework.org/schema/mvc
					        http://www.springframework.org/schema/mvc/spring-mvc.xsd
					        http://www.springframework.org/schema/context
					        http://www.springframework.org/schema/context/spring-context.xsd">
					
					    <!--开启注解扫描，只扫描Controller注解-->
					    <context:component-scan base-package="cn.itcast">
					        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller" />
					    </context:component-scan>
					
					    <!--配置的视图解析器对象-->
					    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
					        <property name="prefix" value="/WEB-INF/pages/"/>	--固定写法：/XX/XX/格式
					        <property name="suffix" value=".jsp"/>
					    </bean>
					
					    <!--过滤静态资源-->
					    <mvc:resources location="/css/" mapping="/css/**" />
					    <mvc:resources location="/images/" mapping="/images/**" />
					    <mvc:resources location="/js/" mapping="/js/**" />
					
					    <!--开启SpringMVC注解的支持-->
					    <mvc:annotation-driven/>
					</beans>

			 例：-------------------    web.xml	---------------------------------------
					<!-- 配置Spring的监听器 -->
					<listener>
						<listener-class>org.springframework.web.context.ContextLoaderListener</listener￾class>
					</listener>
					<!-- 监听器读取 IOC配置文件路径 -->
					<context-param>
						<param-name>contextConfigLocation</param-name>
						<param-value>classpath:applicationContext.xml</param-value>
					</context-param>
						
						
		6. Spring 整合 MyBatis框架
			
			1.配置 MyBatis环境：	1.创建 SqlMapConfig.xml，编写 MyBatis配置。

			2.编写SQL语句：		1.在AccountDao接口的方法上添加注解：
								
									@Repository
									public interface AccountDao {
									
										@Insert(value = "insert into account (name,money) values (#{name},#{money})")
										public void saveAccount(Account account);
										
										@Select("select * from account")
										public List<Account> findAll();
									}
								
			3.测试 MyBatis框架：	1.编写测试类，测试 MyBatis环境：
			
									@Test
									public void run2() throws Exception {
										Account account = new Account();
										account.setName("熊大");
										account.setMoney(400d);
										// 加载配置文件
										InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
										// 创建工厂
										SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
										// 创建sqlSession对象
										SqlSession session = factory.openSession();
										// 获取代理对象
										AccountDao dao = session.getMapper(AccountDao.class);
										dao.saveAccount(account);
										// 提交事务
										session.commit();
										// 释放资源
										session.close();
										inputStream.close();
									}
			
			4.Spring整合MyBatis框架：		
								1.目的：将MyBatis产生dao实现类 的sqlSessionFactory、datasource，直接加入到IOC中，避免手动创建。
								2.如何实现：将 SqlMapConfig.xml内容，配置到applicationContext.xml中
								3.在AccountDao接口中添加： @Repository注解
								4.在service中注入dao对象，进行测试	(直接注入dao？IOC只配置了sqlSessionFactory、datasource对象，
																 dao实现类就会自动进入IOC容器么？)
								
			 例：------------------------	SqlMapConfig.xml  (不需要)	----------------------------
					<?xml version="1.0" encoding="UTF-8"?>
					<!DOCTYPE configuration
						PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
						"http://mybatis.org/dtd/mybatis-3-config.dtd">
					<configuration>
						<environments default="mysql">
							<environment id="mysql">
							<transactionManager type="JDBC"/>
							<dataSource type="POOLED">
								<property name="driver" value="com.mysql.jdbc.Driver"/>
								<property name="url" value="jdbc:mysql:///ssm"/>
								<property name="username" value="root"/>
								<property name="password" value="root"/>
							</dataSource>
							</environment>
						</environments>
						<!-- 使用的是注解 -->
						<mappers>
							<!-- <mapper class="cn.itcast.dao.AccountDao"/> -->
							<!-- 该包下所有的dao接口都可以使用 -->
							<package name="cn.itcast.dao"/>
						</mappers>
					</configuration>

			 例：-------------------		applicationContext.xml		-----------------------------
			 		<!--Spring整合MyBatis框架-->
			 		<!--配置 spring内置连接池-->
				    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
				        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
				        <property name="url" value="jdbc:mysql://localhost:3306/ssm?serverTimezone=UTC"/>
				        <property name="username" value="root"/>
				        <property name="password" value="root"/>
				    </bean>
				
				    <!--配置SqlSessionFactory工厂-->
				    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">	--注意此处为SqlSessionFactoryBean
				        <property name="dataSource" ref="dataSource" />
				    </bean>
				
				    <!--配置扫描dao的包-->
				    <bean id="mapScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">	--id是固定写法
				        <property name="basePackage" value="cn.itcast.dao"/>
				    </bean>
			 		
			 		
		7.配置 Spring 声明式事务管理
		
			1.配置：事务管理器、事务通知、AOP
			2.在applicationContext.xml中配置
			
			 例：-------------------		applicationContext.xml		-----------------------------
			 		<!-- 配置事务管理器 -->
			 		<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
			 			<property name="dataSource" ref="dataSource"/>
			 		</bean>
			 		
			 		<!-- 配置事务的通知 -->
			 		<tx:advice id="txAdvice" transaction-manager="transactionManager">
			 			<!-- 配置事务的属性 -->
			 			<tx:attributes>
			 				<tx:method name="*" propagation="REQUIRED" read-only="false"/>
			 				<tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
			 			</tx:attributes>
			 		</tx:advice>
			 		
			 		<!-- 配置AOP -->
			 		<aop:config>
			 			<!-- 1. 配置 通用切入点表达式 -->
			 			<aop:pointcut id="pt1" expression="execution(* wxt.service.*.*(..))"/>
			 			<!-- 2. 建立切入点表达式和事务通知的对应关系 -->
			 			<aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"/>
			 		</aop:config>
 */

/*								  SSM整合 服务器启动报错
 * 
 	1.异常信息：		//无法找到类：DataSourceTransactionManagers
 	  org.springframework.beans.factory.CannotLoadBeanClassException: Cannot find class [org.springframework.jdbc.dataso
 	  urce.DataSourceTransactionManagers] for bean with name 'transactionManager' defined in class path resource [applic
 	  ationContext.xml]; nested exception is java.lang.ClassNotFoundException: org.springframework.jdbc.datasource.
 	  DataSourceTransactionManagers	
 	
 		1.原因：applicationContext.xml中，事务管理器，类名写错，多加了一个s。
 		
 		2.错误代码：
			<!-- 配置事务管理器 -->
			<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManagers">
			
		3.正确代码：
			<!-- 配置事务管理器 -->
			<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">	
 	
 	
 	
 	2.异常信息：		未启动MySQL服务
 		1.原因：没有打开MySQL服务。
 		
 		
 		
 	3.异常信息：		//配置的数据源 DriverManagerDataSource中，无 driverClass属性
 	  Caused by: org.springframework.beans.NotWritablePropertyException: Invalid property 'driverClass' of bean class 
 	  [org.springframework.jdbc.datasource.DriverManagerDataSource]: Bean property 'driverClass' is not writable or has 
 	  an invalid setter method. Does the parameter type of the setter match the return type of the getter?
 		
 		1.原因： <property>标签写错为 name="driverClass"，无该属性
 				实际属性为：driverClassName
 		
 		1.错误代码：
 			<!-- 数据源：spring-jdbc内置数据源 -->
			<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
				<property name="driverClass" value="com.mysql.cj.jdbc.Driver"></property>
				
		2.正确代码：
			<!-- 数据源：spring-jdbc内置数据源 -->
			<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
				<property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
 */

public class SpringMVC详解 {

}
