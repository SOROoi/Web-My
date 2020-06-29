package 项目详解;

/*									项目笔记
 	0.使用Oracle数据库，用 PLSQL连接Oracle，用system账户登陆Oracle (账户：system，密码：root)
 	
	1.system账户，创建用户
		用户名：SSM	密码：ssm	权限：connect/resource
	
	2.登陆SSM用户，创建产品表
		表名：product	（见 --SSM环境搭建与产品操作.pdf）
			
			CREATE TABLE product(
				id varchar2(32) default SYS_GUID() PRIMARY KEY,
				productNum VARCHAR2(50) NOT NULL,
				productName VARCHAR2(50),
				cityName VARCHAR2(50),
				DepartureTime timestamp,
				productPrice Number,
				productDesc VARCHAR2(500),
				productStatus INT,
				CONSTRAINT product UNIQUE (id, productNum)
			);

		
		问题：	Oracle中唯一性约束：	CONSTRAINT product UNIQUE (id, productNum) 的含义？
		含义：	此唯一约束表示，id 和 productNum ，不能都相同。
				(唯一性约束指表中一个字段或者多个字段联合起来能够唯一标识一条记录的约束。联合字段中，可以包含空值。)

	2.0.该权限管理系统用户：
		用户名：wxt	密码：123456		角色：ADMIN
		用户名：www	密码：123456		角色：USER
		用户名：熊大	密码：123456		角色：无
	
	3.创建 maven工程，分模块开发
		父工程：	Web22_SSM_project	package: pom
		子模块：	SSM_dao				package: jar


	4.AdminLTE中，添加日期控件没效果，需添加JS
		在使用 AdminLTE时，如果页面上某个组件不是原生的效果，则说明有一些 JS对它进行控制。从模板中查找复制 JS即可。
		
		日期控件：	<input type="text" class="form-control pull-right" id="datepicker-a3" name="departureTime">

		JS代码：		$(document).ready(function() {
						$('#datepicker-a3').datetimepicker({
							format : "yyyy-mm-dd hh:ii",
							autoclose : true,
							todayBtn : true,
							language : "zh-CN"
						});
					});

	5. Bean 中数据 >= 表中数据：
		1.可以在 Bean中存入 多表数据；
		2.在向 数据库插入 数据时，表中含有的数据，Bean中必须都有。
		
	6. classpath*:applicationContext*.xml
		classpath 加 *：表示同时 读取jar包中的配置文件。
		applicationContext 加 *：表示读取 所有applicationContext开头的.xml文件
		
	7.分模块开发
		1.模块主要分为：父工程、dao、service、web
					1.父工程 pom包，主要提供依赖，及时 install到仓库。
					2.dao、service为 jar包，及时 install到仓库。
					3.web为 war包
		2.各模块导入需要的依赖，如 service导入 dao的依赖包。
		3.各模块相应的配置文件：
					1.如 dao层：	applicationContext-dao.xml，配置：IOC扫包、SqlSessionFactory、dataSource、dao扫描包
								db.properties，				配置：driver、url、username、password
					2.如 service层：applicationContext-service.xml，配置：IOC扫包、spring事务
					3.如 web层：	springmvc.xml，					配置：过滤静态资源、视图解析器、IOC扫包、注解MVC
								web.xml						配置：DispatcherServlet、乱码过滤器、spring监听器、spring配置文件路径
	
	(用户添加角色)
	8.将多个网页参数 绑定到String[]中。
		JSP页中：	<c:forEach items="${itemList }" var="item">
		            	<tr>
		           			<td><input type="checkbox" name="ids" value="${item.id}"/></td>
						</tr>
            		</c:forEach>
            		
		Controller中：
					public String deleteItem(Integer[] ids){	//方法中用String[]接收，网页参数名=方法参数名
						...
					}
	
	(用户添加角色)
	9.dao方法中传入多个参数，需要加 @Param 注解。
		dao中：		@Insert("insert into users_role values(#{userId},#{roleId})")
					public void addRoleToUser(@Param("userId")String user, @Param("roleId")String role) throws Exception;
		
		解释:		用 @Param注解，将方法参数 与sql中参数，关联起来。
		

							
*/


/*									项目笔记2

	1.两表之间创建中间表的时机。
		若两表为多对多关系，且 表1实体类，表2实体类 都有一对多数据，有必要创建中间表；
		若两表为一对多关系，可以在 后创的表添加外键，也可以创中间表；(外键必须在后创表中)
		若两表为一对一关系，则 后创的表 添加外键；


	2.前端页面 index.jsp中，<a>标签的写法
			如：<a href="my">
		
			1.为 href="xx" 时，	默认访问 web项目   /xx 映射：		http://localhost:8080/项目名/xx
			2.为 href="/xx" 时，	默认访问 主机名下  /xx 项目：		http://localhost:8080/xx
			
			
	3.前端页面 <button>按钮跳转
			<button type="button" onclick="location.href='../pages/product-add.jsp'">新建</button>
		
			
			
	4.SpringMVC框架提供的转发和重定向

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


/*									PageHelper--分页 		
								
		1. PageHelper介绍
			PageHelper是国内优秀的一款开源的mybatis分页插件，它支持主流与常用的数据库，例如mysql、oracle、mariaDB、DB2、SQLite、Hsqldb等。

		2. PageHelper使用
			1.导入依赖：
			  (jar包两个)
			  pagehelper.jar:
				  			https://oss.sonatype.org/content/repositories/releases/com/github/pagehelper/pagehelper/
							http://repo1.maven.org/maven2/com/github/pagehelper/pagehelper/
			  jsqlparser.jar：(sql 解析工具)
			  				http://repo1.maven.org/maven2/com/github/jsqlparser/jsqlparser/0.9.5/
	
			  (依赖一个)
			  maven坐标：	<dependency>
								<groupId>com.github.pagehelper</groupId>
								<artifactId>pagehelper</artifactId>
								<version>最新版本</version>
							</dependency>

			2.配置
				1. 在 MyBatis 配置 xml 中配置拦截器插件
					<!--
						plugins在配置文件中的位置必须符合要求，否则会报错，顺序如下:
						properties?, settings?,
						typeAliases?, typeHandlers?,
						objectFactory?,objectWrapperFactory?,
						plugins?,
						environments?, databaseIdProvider?, mappers?
					-->
					<plugins>
						<!-- com.github.pagehelper为PageHelper类所在包名 -->
						<plugin interceptor="com.github.pagehelper.PageInterceptor">
							<!-- 使用下面的方式配置参数，后面会有所有的参数介绍 -->
							<property name="param1" value="value1"/>
						</plugin>
					</plugins>
					
				2.在 Spring 配置文件中配置拦截器插件
					使用 spring 的属性配置方式，在applicationContext.xml中这样配置：
					
					<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
						<property name="dataSource" ref="dataSource">
						
						<!-- 传入 PageHelper 插件 -->
						<property name="plugins">
							<array>
								<!-- 传入插件的对象 -->
								<bean class="com.github.pagehelper.PageInterceptor">
									<property name="properties">
										<props>
											<prop key="helperDialect">oracle</prop>	//数据库类型
													//5.×版本的 PageHelper中，如果在配置文件中声明了此属性，会导致分页无效
													
											<prop key="reasonable">true</prop>	//分页合理化参数(上一页下一页无法越界)
										</props>
									</property>
								</bean>
							</array>
						</property>
					</bean>
	
			3.在service中：		执行 dao查询 的前一条，执行方法：
											PageHelper.startPage(page, size);
											
			    在controller中：	将查询结果 传入 PageInfo中,存入 ModelAndView ：
			    							PageInfo<Order> pageInfo = new PageInfo<Order>(list);
			    							mv.addObject("pageInfo",pageInfo);
				数据库在查询时会帮助我们分页。
			
				如：(在 Service实现类中)
					@Override
				    public List<Orders> findAll(int page, int size) throws Exception {
				
				        //参数pageNum 是查询的页码值   参数pageSize 代表是每页显示条数
				        PageHelper.startPage(page, size);
				        return ordersDao.findAll();
				    }
			
			4.在 Controller中使用 PageInfo pageInfo = new PageInfo(list); 获取 PageInfo(分页bean)，PageInfo中封装了分页信息。
				pageInfo.total	总记录数
				pageInfo.pages  总页数/尾页
				pageInfo.pageNum  当前页数
				pageInfo.pageSize  每页几条记录
				pageInfo.size   当前页的数量
	
		
		
	*多表查询：查询1.2表返回结果时，将所有数据封装到 1表bean中，1表bean的属性中包含 2表的数据 bean2。
	*														1.如果两表为一对一关系，2表 属性类型为 bean2；	   //一条1表数据对应 一条2表数据
	*														2.如果两表为一对多关系，2表 属性类型是 List<bean2>。  //一条1表数据对应 多条2表数据
*/


/*									Spring Security--权限操作	
 				(如果访问的网址是 不被权限控制的 则直接显示，否则跳转到 登陆页面login-page(见配置文件))
 				1.无需拦截的资源
 				2.访问需要的权限/角色
 				3.登陆页面、登录请求、登陆成功页、登陆失败页
 				4.UserDetailsService接口
		
		0.使用 Security框架后，访问流程：
		
			1.访问页面前，Security运作，判断页面 是否 需要权限：	(spring-security.xml 以下简称 '配置')
						1.无需权限，则放行	(配置，无需权限资源 之一)
						2.需要权限，跳转到 login-page页面(配置)
						
			2.login-page页面中，发送请求：/项目/login-processing-url(配置)：
						1.获得表单 username、password，对password 加密(若 配置 有加密方式)
						2.执行 user-service-ref(配置) 中 loadUserByUsername()方法，通过username,得到数据库中 User信息。
						3.比较表单密码 与 User密码；通过后，判断 User角色权限 是否为 access(配置)，
						4.都通过后，跳转到 default-target-url页面(配置)


	
		1.security框架中获得 User对象：
				SecurityContext context = SecurityContextHolder.getContext();
				User user = (User)(context.getAuthentication().getPrincipal());
		
		
		
		2.使用 Spring Security 流程：
			1.导入依赖：	1.spring-security-web，
						2.spring-security-config.
						
							<spring.security.version>5.0.1.RELEASE</spring.security.version>
							
							<dependency>
								<groupId>org.springframework.security</groupId>
								<artifactId>spring-security-web</artifactId>
								<version>${spring.security.version}</version>
							</dependency>
							<dependency>
								<groupId>org.springframework.security</groupId>
								<artifactId>spring-security-config</artifactId>
								<version>${spring.security.version}</version>
							</dependency>
			2.配置web.xml:
							//<filter-name>为固定名称，不可修改
							<context-param>
								<param-name>contextConfigLocation</param-name>
								<param-value>classpath:applicationContext.xml,classpath:spring-security.xml</param-value>
							</context-param>
							<filter>
								<filter-name>springSecurityFilterChain</filter-name>
								<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
							</filter>
							<filter-mapping>
								<filter-name>springSecurityFilterChain</filter-name>
								<url-pattern>/*</url-pattern>
							</filter-mapping>
						
			3.添加spring-security.xml：	1.配置不拦截的资源，
										2.配置拦截规则，角色，登入页，登出页，
										3.配置已实现的 UserDetailsService 类。
										 .配置表单密码 加密方式。(加密后：加密表单密码 与 数据库密码 比较)
									
			4.编写 UserService 实现 UserDetailsService接口,
					
					1.实现 UserDetailsService 接口，
					 		接口中方法：public UserDetails loadUserByUsername(String username)
					 		
					2.查询 UserInfo 信息：
							如： dao.findByName(username);
							
					3.查询 Role 信息：
					    装入 SimpleGrantedAuthority 类：
							如： List<Role> roles =  userInfo.getRoles();
							装入 new SimpleGrantedAuthority("ROLE_"+role.getRoleName());
							
					4.装入 User：	(UserDetails 实现类)			
							//已配置加密方式
							new User(username,userInfo.getPassword(),userInfo.getStatus()==0 ? false : true,
										...List<SimpleGrantedAuthority>);
							return user; 
						
						
						(	//未配置加密方式
						   将：用户名、"{noop}"+用户密码、用户状态、用户角色 封装入 User，返回 User。)
						   
						
					例:	
						public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
							UserInfo userInfo = dao.findByName(username);	
							
							List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
							List<Role> roles =  userInfo.getRoles();
							for(Role role : roles) {
								authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
							}
							//将：用户名、用户密码、用户状态...用户角色 封装入 User，返回 User
							User user= new User(username, userInfo.getPassword(), userInfo.getStatus()==0 ? false : true,
									true, true, true, authorities);
							return user;
						}
						
					

		2.为何spring security框架的过滤器名必须为"springSecurityFilterChain"
			见：https://blog.csdn.net/Trialknight/article/details/102750531
			
		3.利用 BCryptPasswordEncoder将密码加密(加盐)存入数据库。
			在 service层中操作：String newPassword = new BCryptPasswordEncoder().encode(password);
			
			使用 BCryptPasswordEncoder加密后，使用账户：wxt 密码：123456 将无法登陆。为此添加一个初始账户：www 密码：123456
		
	
			
*/


/*									AOP日志查看
		1.需要
			1.日志Bean:	SysLog
						字段：
							private String 	id;				--主键
							private String 	username;		--访问用户名
							private String 	ip;				--用户IP
							private Date 	visitTime;		--访问时间
							private String 	visitTimeStr;
							private Long 	executionTime;	--执行时长
							private String	url;			--访问的资源url
							private String	method;			--访问方法

			2.日志表：	syslog
			
			3.切面类：	LogAop		--用于切入controller方法，封装日志信息，存入数据库
						字段：
							private Date 	startTime; 		--访问时间
							private Class 	executionClass;	--访问的类
							private Method 	executionMethod; --访问的方法
		
		2.切面类LogAop 可获取对象：
			1.JoinPoint对象：
						1.在方法参数上传入 JoinPoint对象;
						2.通过 JoinPoint 获得当前访问的类;
						3.通过 JoinPoint 获得当前访问的方法名;
							@Before("execution(* wxt.controller.*.*(..))")
    						public  void beforePrintLog(JoinPoint jp){
    							Class clazz = jp.getTarget().getClass();
    							String methodName = jp.getSignature().getName();
    						}
			
			2.request对象:
						1.在web.xml中配置RequestContextListener监听器，即可从IOC中获取 request对象。
							<listener>
						       <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
						   	</listener>
						2.从 IOC中获取 requset对象：
							@Autowired
							private HttpServletRequest request;
			
			3.User用户对象：
						1.获取 srcurity框架的 SecurityContext对象：
							SecurityContext context = SecurityContextHolder.getContext();
					   	    也可通过request对象获取 SecurityContext对象：
					   	    request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
					   	
						2.从 SecurityContext对象中 获得 User对象：
							String username = ((User)(context.getAuthentication().getPrincipal())).getUsername();

		3.切面类LogAop ：
			1.前置通知：	获取访问时间、访问的Class、访问的Mthod。
			2.后置通知：	将 日志信息 封装到 日志Bean，存入数据库。

 */


/*									服务器端方法级权限控制、
 (--见：Web22_企业权限管理系统\src\项目详解\4.SSM权限操作.pdf)
 
	1.服务器端方法级权限控制
		在服务器端，可以通过Spring security提供的注解对方法来进行权限控制。
		Spring Security在方法的权限控制上 支持三种类型的注解：JSR-250注解、@Secured注解和支持SPEL表达式的注解。
	    这三种注解默认都是没有启用的，需要在spring-security.xml中，单独通过 global-method-security元素的对应属性进行启用。
	
	
	2.JSR-250注解
		需要导包。常用其 @RolesAllowed()注解。可省略"ROLE_"。
	
		1.启用注解：
			第一步：在pom.xml中导入依赖：
				   <dependency>
		              <groupId>javax.annotation</groupId>
		              <artifactId>jsr250-api</artifactId>
		              <version>1.0</version>
		           </dependency>
		           
			第二步：在spring-security.xml中开启：
				   <security:global-method-security jsr250-annotations="enabled"></security:global-method-security>
		
		2.使用注解：
			第三步：在指定的方法上使用：
				   @RequestMapping("/findAll.do")
				   @RolesAllowed("ADMIN")
				   public ModelAndView findAll() throws Exception {
				   
			注解说明：
				 @RolesAllowed()：表示具有其中角色的用户才可访问该方法，否则报403异常。
					  如：@RolesAllowed({"USER","ADMIN"}) 该方法只要具有"USER","ADMIN"任意一种权限就可以访问。这里可以省略前缀ROLE_，
						实际的权限可能是ROLE_ADMIN。
						
				 @PermitAll：表示允许所有的角色进行访问，也就是说不进行权限控制。
				 @DenyAll：和PermitAll相反的，表示无论什么角色都不能访问


	3.@Secured注解
		无需导额外包，spring-security包自带注解。不可省略"ROLE_"。
		
		1.启用注解：
			第一步：在spring-security.xml中开启：
				   <security:global-method-security secured-annotations="enabled"></security:global-method-security>
				   
		2.使用注解：
			第二步：在指定的方法上使用：
				   @RequestMapping("/findAll.do")
				   @Secured("ROLE_ADMIN")
				   public ModelAndView findAll(@RequestParam(name = "page",
			
			注解说明：
				@Secured()：表示具有其中角色的用户才可访问该方法，否则报403异常。
				
				
	4.支持 SPEL表达式的注解
		常用其 @PreAuthorize()注解。使用时需要掌握SPEL表达式。

		1.启用注解：
			第一步：在spring-security.xml中开启：
				   <security:global-method-security pre-post-annotations="enabled"></security:global-method-security>

		2.使用注解：
			第二步：在指定的方法上使用，可以使用 SPEL表达式：
				   @RequestMapping("/save.do")
				   @PreAuthorize("authentication.principal.username == 'tom'")	//表示只有用户名为‘tom’的才能访问该方法
				   public String save(UserInfo userInfo)
				   
				   @RequestMapping("/findAll.do")
				   @PreAuthorize("hasRole('ROLE_ADMIN')")	//表示只有'ROLE_ADMIN'角色的用户才可以访问
				   public ModelAndView findAll()
				   
			注解说明：
				@PreAuthorize()：在方法调用之前,基于表达式的计算结果来限制对方法的访问
				@PostAuthorize()：允许方法调用,但是如果表达式计算结果为false,将抛出一个安全性异常
				@PostFilter()：允许方法调用,但必须按照表达式来过滤方法的结果
				@PreFilter()：允许方法调用,但必须在进入方法之前过滤输入值


*/


/*									JSP页面 标签控制权限
 
 	0.介绍
 		在jsp中我们可以使用以下三种标签：
 			authentication、authorize、accesscontrollist
 			 	authentication：可以获取当前正在操作的对象信息。
 				authorize：用来控制页面上某些标签内容是否可以显示。
 				accesscontrollist：用于鉴定ACL权限的。
 			后两个标签我们可以用于权限控制。
 	
 	1.使用步骤：
 		1.导包：	<dependency>
					<groupId>org.springframework.security</groupId>
					<artifactId>spring-security-taglibs</artifactId>
					<version>version</version>
				</dependency>
				
		2.JSP页面导入：
				<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

 		3.常用标签
 			1.authentication
 				代表的是当前认证对象，可以获取当前正在操作的对象信息，例如用户名. 
 				<security:authentication property=""  htmlEscape=""  scope=""  var="" />
 				
				property：只允许指定Authentication所拥有的属性，可以进行属性的级联获取，如“principal.username”，不允许直接通过方法
						  进行调用。
				htmlEscape：表示是否需要将html进行转义。默认为true。
				scope：与var属性一起使用，用于指定存放获取的结果的属性名的作用范围,默认为pageContext。Jsp中拥有的作用范围都进行进行指定
				var：用于指定一个属性名，这样当获取到了authentication的相关信息后会将其以var指定的属性名进行存放，默认是存放
					在pageConext中

			2.authorize	
				用来判断普通权限，通过判断用户是否具有对应的权限，控制页面上某些标签内容是否可以显示
				<security:authorize access="hasRole('ADMIN')"  method=""  url=""  var="" >
					...显示内容
				</security:authorize>
				
				access： 使用表达式判断权限，当表达式的返回结果为true时表示拥有对应的权限，如：access="hasRole('ADMIN')"
				method：method属性是配合url属性一起使用的，表示用户应当具有指定url指定method访问的权限，
						method的默认值为GET，可选值为http请求的7种方法
				url：url表示如果用户拥有访问指定url的权限即表示可以显示authorize标签包含的内容
				var：用于指定将权限鉴定的结果存放在pageContext的哪个属性中
				
				(注意：使用此标签需要在 spring-security.xml 中开启SPEL表达式的解析：
					<security:http auto-config="true" use-expressions="true">
						<security:intercept-url pattern="/**" access="hasAnyRole('ROLE_USER','ROLE_ADMIN')"/>
					或添加：
					 <bean id="webexpressionHandler" class="org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler" />
				)

			3.accesscontrollist
				用于鉴定ACL权限的。其一共定义了三个属性：hasPermission、domainObject和var，其中前两个是必须指定的
				<security:accesscontrollist hasPermission="" domainObject="" var="" />
				
				hasPermission：hasPermission属性用于指定以逗号分隔的权限列表
				domainObject：domainObject用于指定对应的域对象
				var：var则是用以将鉴定的结果以指定的属性名存入pageContext中，以供同一页面的其它地方使用


 */


/*									异常汇总
 * 
	1.问题：启动服务器，浏览器输入项目显示404.
	
		提示信息： org.apache.catalina.core.StandardContext.listenerStart Exception sending context initialized event to 
		  listener instance of class [org.springframework.web.context.ContextLoaderListener]
		  org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sqlSessionFactory' defined 
		  in class path resource [applicationContext.xml]
	  
	  	异常处：applicationContext.xml中配置错误，如下
	  		<!-- 扫描dao的包-->
			<bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
				<property name="basePackage" value="wxt.dao"></property>
			</bean>
		正确配置：
			<!-- 扫描dao的包-->
			<bean id="mapScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
				<property name="basePackage" value="wxt.dao"></property>
			</bean>

		总结：扫描dao的包的配置中，id必须为"mapScanner"。
		
		
		
		
	2.问题：用PL/SQL插入数据，一直在执行，无法插入成功。
		
		原因：表被锁了，需要解锁
		
		解决方法：
				1.用dba权限的用户登陆数据库。
				2.select * from v$locked_object;	
												--查出被锁对象的session_id,
												
				3.select sid, serial#, machine, program from v$session where sid = 被锁对象的session_id;	
												--用上一步查出的session_id，查出被锁对象的 sid 和 serial#，
												
				4.alter system kill session '被锁对象sid,被锁对象serial#';
												--用上一步查出的 sid 和 serial#，解锁



	3.问题：访问index.jsp，页面缺少 /plugins下的 css样式
	
		原因：spring-mvc.xml中过滤静态资源未添加 /plugins路径
		
		错误配置：(spring-mvc.xml中)
			<!-- 过滤静态资源 -->
			<mvc:resources location="/css/" mapping="/css/**"></mvc:resources>
			<mvc:resources location="/img/" mapping="/img/**"></mvc:resources>
			<mvc:resources location="/js/" mapping="/js/**"></mvc:resources>
			
		正确配置：
			<!-- 过滤静态资源 -->
			<mvc:resources location="/css/" mapping="/css/**"></mvc:resources>
			<mvc:resources location="/img/" mapping="/img/**"></mvc:resources>
			<mvc:resources location="/js/" mapping="/js/**"></mvc:resources>
			<mvc:resources location="/plugins/" mapping="/plugins/**"></mvc:resources>
	
	4.问题：点击button按钮跳转到指定页面。
	
		写法：	<button type="button" class="btn btn-default" title="新建" onclick="location.href='../pages/product-add.jsp'">
					<i class="fa fa-file-o"></i> 新建
				</button>
				
		其他写法：☆如果是本页显示可以直接用location,方法如下：
				　　1.οnclick="javascript:window.location.href='URL'"
				　　2.οnclick="location='URL'"
				　　3.οnclick="window.location.href='URL'"
				
				  ☆如果页面中有frame可以将在location前面添加top.mainframe.frames['right_frame'].location
					在新页面打开的话：
					οnclick="window.open('http://linlizhu.cn')"
				
				   在模态窗口打开的话：
					οnclick="window.showModalDialog('http://linlizhu.cn')"
					
	5.问题：表单提交数据是字符串，绑定参数到bean 时出现类型转换问题
		
		方法一：	自定义类型转换器(springMVC框架)
				1. 自定义类型转换器，实现接口 Converter<S,T>
				2. springmvc.xml文件中注册：
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
					
		方式二：Bean中，添加日期格式化注解：(springMVC框架)
				@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
				private Date creatingTime;
				
	6.问题：表单提交 出发时间：2019-12-10 08:00,提交数据时出现400标志(bad request)
		
		原因：	表单数据——>bean 封装失败。
				因为表单数据 '出发时间'是String类型，bean中 '出发时间'是Date 类型。
				
				自定义类型转换器 StringToDate类 中convert()方法写错。
				
		总结：	converter()中 日期格式 需要和表单的保持一致，才能正确封装。
		
		错误代码：	@Override
					public Date convert(String str) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = null;
						try {
							date = sdf.parse(str);
						} catch (ParseException e) {
							throw new RuntimeException("日期解析错误");
						}
						return date;
					}	
					
		正确代码：	@Override
					public Date convert(String str) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Date date = null;
						try {
							date = sdf.parse(str);
						} catch (ParseException e) {
							throw new RuntimeException("日期解析错误");
						}
						return date;
					}
					
	7.更改 Product 类的get方法后，显示页面显示不了出发日期信息，服务器报错。
		(参考于 https://blog.csdn.net/qq_27471405/article/details/102599896)
		
		原因：product类实现了Serializable接口，读取时改动了这个bean对象，即加了属性，就会导致serialVersionUID发生变化，导致报错。
		
		解决方法：在Product类中给定一个固定的serialVersionUID。
		
		
	8.log4j日志无法显示。
	
	
	9.在设计查询所有订单时，页面查询结果无法显示
		原因：Order类中，忘记添加 orderStatusStr 属性，导致JSP页面中无法提取其属性
		
		解决方法：Order类中添加 orderStatusStr 属性。
		
		
	10.在使用分页插件 PageHelper后，服务器无法启动，启动报错。
		原因：PageHelper maven导入版本错误，需要导入最新版。
		
		错误代码：	<dependency>
						<groupId>com.github.pagehelper</groupId>
						<artifactId>pagehelper</artifactId>
						<version>5.1.2</version>
					</dependency>
					
		正确代码：	<dependency>
						<groupId>com.github.pagehelper</groupId>
						<artifactId>pagehelper</artifactId>
						<version>5.1.11</version>
					</dependency>
					
	11.使用分页插件 PageHelper，无法分页.
		参考网址：https://blog.csdn.net/ClearLoveQ/article/details/86133462
		
		原因：applicationContext.xml 中的配置信息错误:
				<property name="dialect" value="mysql"/>  //表示数据库类型
				
			  注：PageHelper 为5.×的版本中，如果在配置文件中声明了此属性，会导致分页无效
			  
		错误代码：	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
						<property name="dataSource" ref="dataSource"></property>
						
						<!-- 传入 PageHelper 插件 -->
						<property name="plugins">
							<array>
								<!-- 传入插件的对象  --> 
								<bean class="com.github.pagehelper.PageInterceptor">
									<property name="properties">
										<props>
											<prop key="dialect">oracle</prop>	
											<prop key="reasonable">true</prop>	
										</props>
									</property>
								</bean>
							</array>
						</property>		
					</bean>
					
		正确代码：	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
						<property name="dataSource" ref="dataSource"></property>
						
						<!-- 传入 PageHelper 插件 -->
						<property name="plugins">
							<array>
								<!-- 传入插件的对象  --> 
								<bean class="com.github.pagehelper.PageInterceptor">
									<property name="properties">
										<props>
											<prop key="reasonable">true</prop>	
										</props>
									</property>
								</bean>
							</array>
						</property>
					</bean>
					
	12.项目改名后，新项目名无法访问
		原因： 在 eclipse改名，只改了workspace下面的java工程的名字，在tomcat里面的项目名称其实还没有改
		
		解决方法：
				打开 eclipse：右键项目 ——> Properties ——> Web Project Setting ——> 修改Context Root.
				
				
	13.使用spring security 自定义登陆页面后，登陆成功无法跳转
		登入请求格式： 
			项目/login-processing-url(配置信息中)
	
		原因：
			<from action="login">在跳转时，并不是发送登入请求 "项目/login"，而是默认访问springMVC中的处理器
			而无权限 访问该地址，所以一直处于登陆界面 。
		
		解决方法：
			将<form>表单改为：<from action="/项目名/login">
			
		总结：
			<form>提交时，若action 以"/"开头，默认访问 主机名下.
						  
			而想要确保 action 跳转到项目下的映射：	  
											1.使用路径 action="/项目名/login"。
			
			
	14.使用spring security框架， 数据库存储账号密码，账号密码正确仍然无法登陆。
		参考地址：https://blog.csdn.net/weixin_44580977/article/details/98491875
		原因：
			springsecurity 的安全认证方式是，将前端用户输入的密码经行加密，然后与数据库中的密码匹配。
			spring-security.xml中配置了加密方式的话(<security:password-encoder ref="passwordEncoder"/>)，就需要数据库中
		   储存的也是加密过的密码。	
			而我配置了加密方式，但数据库中密码却是未加密的，导致两者不匹配。spring security认证失败。
			
		解决方法：
			1.不配置加密方式： 将spring-security.xml中的 <security:password-encoder ref="passwordEncoder"/> 注释掉，
			    数据库中密码未加密(注:123456)。
			    并将 UserService 中 user的密码设为 "{noop}"+userInfo.getPassWord()。
			2.配置加密方式。
			    并将数据库中的密码存为加密过的密码。
			    
			    
	15.异常java.sql.SQLException: ORA-00947: 没有足够的值
		原因：一般insert语句省略了values前面的字段名，然后再新增字段后不修改代码就会导致此类问题
		
		解决方案：修改sql语句
		
		
	16.在登陆页面，向数据库添加用户失败
		原因：因为登陆页面login.jsp没有权限，spring security拦截了控制器中访问数据库的 pattern 请求。
		
		
	17.在添加用户时，报错
		错误信息：Request processing failed; nested exception is org.mybatis.spring.MyBatisSystemException: nested exception
		 is org.apache.ibatis.type.TypeException: Could not set parameters for mapping: ParameterMapping{property='email',
		  mode=IN, javaType=class java.lang.String, jdbcType=null, numericScale=null, resultMapId='null', jdbcTypeName='
		  null', expression='null'}. Cause: org.apache.ibatis.type.TypeException: Error setting null for parameter #2 with
		  JdbcType OTHER . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration 
		  property. Cause: java.sql.SQLException: 无效的列类型
		  
		原因：在编写sql时，向数据库中插入了email数据。但是 userInfo 中无 email 数据，导致无法添加。
		
		解决方案：在添加用户时，sql 中插入的数据，UserInfo 中必须有该数据。
		
		
		
		
	18.用户详情页面无法显示
	
		原因：Permission中信息无法显示，因为 Permission中 ‘permissionName’写错为‘permissionNameString’,导致数据库无法将数据封装入Permission.
		
		解决方案：将 Permission中 ‘permissionNameString’ 改为 ‘permissionName’。
		
		
		
		
	19.SpringSecurity 注解对方法的权限控制 @PreAuthorize、@RolesAllowed、@Secured无效
	
		原因：如果 权限限制的方法 在某个bean(Controller)里，且这个bean由SpringMVC管理，那么SpringSecurity的权限注解配置，
			就需写在SpringMVC的配置文件中。
		
		解决方案：将写在spring-security.xml中的配置移动到spring-mvc.xml中。
			(若网页端报错：元素 "security:global-method-security" 的前缀 "security" 未绑定，
						则是spring-mv.xml 中未添加 spring-security 的约束)
						
	
	20.启动项目报错,因为在spring-security.xml的 <!-- -->注解 中添加了//。
		
		原因: <!-- -->注解 中添加了//，删除//即可。
		
		
	21.注解AOP没有生效
		
		分析：
			spring注解不生效可从以下几个方面找原因：

			1 springmvc的配置文件中只配置扫描@Controller的注解，spring的配置文件扫描除了@Controller的注解的其他的注解
			
			2 检查@Pointcut 的表达式是否正确
			
			3 如果在service层aop 可以生效，controller层不生效，
				把  <aop:aspectj-autoproxy proxy-target-class="true"/>添加到到springmvc的配置文件中
		
		原因：	因为在Controller层，所以需要在spring-mvc.xml中添加：
					<aop:aspectj-autoproxy proxy-target-class="true"/>
				添加后AOP生效。


	22.启动 tomcat失败
	
		log日志信息：	Line 66 in XML document from class path resource [applicationContext.xml] is invalid;
					元素 'beans' 必须不含字符 [子级], 因为该类型的内容类型为“仅元素”。
		
		applicationContext.xml错误配置：	<!-- 扫描注解的包 IOC  -->>
										<context:component-scan base-package="wxt">
										
		原因：	此错误提示，表示文件中存在非法字符。
				我的配置文件中多打了一个 > 符号，导致报错。
 */


public class 项目笔记 {

}
