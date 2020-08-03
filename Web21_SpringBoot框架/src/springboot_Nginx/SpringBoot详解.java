package springboot_Nginx;

/*								 1.SpringBoot简介
	
	1. SpringBoot简介
		1.Spring是Java企业版（Java Enterprise Edition，JEE，也称J2EE）的轻量级代替品。
		2.SpringBoot对 Spring的缺点进行的改善和优化，基于约定优于配置的思想，可以让开发人员不必在配置与逻辑业务之间进行思维的切换，全身
		    心的投入到逻辑业务的代码编写中。
	    
	2. SpringBoot的特点
	    1.为基于Spring的开发提供更快的入门体验。
		2.开箱即用，没有代码生成，也无需XML配置。同时也可以修改默认值来满足特定的需求。
		3.提供了一些大型项目中常见的非功能性特性，如嵌入式服务器、安全、指标，健康检测、外部配置等。
		4.SpringBoot不是对Spring功能上的增强，而是提供了一种快速使用Spring的方式


	3. SpringBoot的核心功能
	
		1.起步依赖：	将此前混乱的依赖关系、版本关系整合，将相同功能的依赖包、版本关系整合到一起，作为 SpringBoot的一个启动器。

		2.自动配置：	1.Spring Boot自动配置，是基于 引入的启动器，自动使用其对应配置。该过程是Spring自动完成的。
					2.但通过自己设置参数（.properties），也可以自定义配置。
				   	3.自动配置由启动器决定。
				
		3.spring-boot-starter-parent:
					其版本即 SpringBoot版本，它提供了 SpringBoot启动器、依赖包、插件的版本管理。
		
		
		
	4.spring-boot-starter-web的功能
	
		1.其中有 springMVC框架、spring框架、log日志、JSON处理、tomcat等功能，及其依赖包。
		
		2.其中有 SpringBoot自动配置--功能核心包：	spring-boot-autoconfigure-x.x.x.jar
		
			1. @SpringBootApplication注解 就在此包中。
				--@SpringBootConfiguration
				--@ComponentScan
				--@EnableAutoConfiguration
		  
		  	2.自动配置：
		  		通过 @SpringBootApplication -- @EnableAutoConfiguration -- AutoConfigurationImportSelector类
		  			-- getCandidateConfigurations()方法 -- 调用 SpringFactoriesLoader类(spring-core.jar包中) --
		  			
		  			-- 读取"META-INF/spring.factories"中指定的配置类类名，并加载这些类中的配置
		  
		  
		  
		  
	*. Spring boot参考文档
		网址：https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/index.html


	*. Spring Tools4介绍：(原文链接：https://blog.csdn.net/zyplanke/article/details/104235750)
		最新的STS4：
			官方地址为：https://spring.io/tools    (现在官方名字中已经少了Suite这个单词，仅为Spring Tools)
			
		STS3：官方只更新至2019年，2020年已经不在更新维护了。
		STS4(all-in-one完整版本)：包括IDE，可以使用安装，下载解压即可使用STS。
		
 */


/*								(eclipse) 创建项目、核心文件、配置文件、yml写法
 
	1.安装Spring boot 插件 Spring Tool Suite
	
		1.查看Eclipse版本：	help--About Eclipse

		2.下载插件：			http://download.springsource.com/release/TOOLS/update/3.9.4.RELEASE/e4.7/springsource-tool-suite-3.9.4.RELEASE-e4.7.3a-updatesite.zip
							打开浏览器下载插件包
	
		3.安装：				help -- install new software.. -- add插件包 -- 选中带spring的四项 -- 除了网上提示的勾选，还勾选Concat all update sites...
							(安装过慢的话，打开 preferences -- install/update -- available software sites，除了插件包的地址，其余都不要勾选)


	2.创建 SpringBoot项目
		1. New -> Spring Starter Project
		
		2. 设置项目信息
		
		3. 勾选需要的组件 --> finish	
		
		
		
	3.核心文件
		0.static文件夹
			1.放置静态资源
		
		1.SpringBootDemoApplication.java
			1.SpringBoot引导类,项目的入口,
			2.要将Application类放在最外侧，即包含所有子包,否则报错
			
		2.pom.xml
			1.依赖文件，整合了需要的jar包	
			2.<parent>中就是 SpringBoot 的依赖，对依赖进行了版本控制。
			
		3.application.properties :只能配置基本类型、String
		  application.yml :可以配置对象、集合
			1.配置文件.
			2.功能：	
				server.port=8091	--服务器端口
				server.servlet.context-path=/demo	#当前web项目名称
				
		properties配置信息见：（87.4后）
			https://docs.spring.io/spring-boot/docs/2.0.1.RELEASE/reference/htmlsingle/#common-application-properties



	4. application.yml文件写法：		优先级：	yml < ymal < properties
		0.格式：
			数据前空格，缩进一致为同级。
	
		1.#服务器的配置：
			server:
			  port: 8081
			  servlet：
			  	context-path： /demo
				
		2.#普通数据的配置：
			name: zhangsan
		
		3.#对象的配置：
			person:
			  name: tom
			  age: 19
			  addr: shanghai
		
		4.#集合（普通字符串）：
			city:
			  - beijing
			  - tianjin
			  - chongqing
			  - shanghai
			或：
			city: [beijing,tianjin,chongqing,shanghai]
		
		5.#集合（对象集合）：
			student:
			  - name: tom
			    age: 18
			    addr: beijing
			  - name: lucy
			    age: 17
			    addr: tianjin
			或：
			student: [{name: tom,age: 18,addr: beijing},{name: lucy,age: 17,addr: tianjin}]
			    
		6.#Map配置：
			map:
			  key1: value1
			  key2: value2
		
	
 */


/*								 热部署、常用注解、注入外部Bean(spring纯注解)/注入属性
 * 
	1.热部署
		1.修改代码后不重启就能生效，称之为热部署。
		2.在 pom.xml 中添加如下配置：	(功能坐标)
						<dependency>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-devtools</artifactId>
						</dependency>
						
	2.配置 @ConfigurationProperties的执行器
		1.配置后在 yml文件中会有 对应字段提示，
		2.在 pom.xml 中添加：
						<dependency>
						    <groupId>org.springframework.boot</groupId>
						    <artifactId>spring-boot-configuration-processor</artifactId>
						    <optional>true</optional>
						</dependency>

	2. SpringBoot 常用注解
	
		1. @SpringBootApplication
		
			1.标注的类为引导类，
			2.注该类是Spring的一个配置类，(@SpringBootConfiguration，等同 @Configuration)
			3.同级包及其子包下的类 都被 扫描IOC，	(@ComponentScan)
			4.启动自动配置，	(@EnableAutoConfiguration	加载 application.properties 配置)
							(默认配置在：spring-boot-autoconfigure-2.1.15.RELEASE.jar/META-INF/spring-configuration-metadata.json)


		2. @Value("${name}")
			0.标注字段，
			1.向字段 注入数据 name，
			2.使用 spEL 表达式，获得 application.yml 中的 name。
			
			
		3. @ConfigurationProperties(prefix="person")
			0.标注类，需要提供 get/set方法，
			1.自动向字段 注入yml文件中 person.xx值，
				(prefix属性，对应 yml文件中 "person")	
				
				如：	private String name;	--注入yml文件中 person.name值
					
					public String getName() {	--提供 get/set方法
						return name;
					}
				
					public void setName(String name) {
						this.name = name;
					}
			
		4. @EnableConfigurationProperties(JdbcProperties.class)
			0.标注于配置类上，可以将带有 @ConfigurationProperties 的类 注入IOC容器
				
					
		5. @@RestController
			1.等同于 @Controller + @ResponseBody
			2.相当于 Controller中所有的方法都添加了 @ResponseBody注解，将方法的返回值以 JSON 或 String 发回客户端。


		6. @Mapper	(Mybatis注解)
			1.Dao接口的注解，将Dao注入到IOC容器中
			
		 . @MapperScan("wxt.mapper")
			1.配置类 的注解，标注后 mapper包下dao可省略 @Mapper注解。
			
		
		7. @GetMapping		(springMVC注解)
			0.标注于方法，用于处理 GET请求的映射。
			1.是 @RequestMapping(method = RequestMethod.GET)的缩写。
			
		 . @PostMapping
			0.标注于方法，用于处理 POST请求的映射。
			1.是 @RequestMapping(method = RequestMethod.POST)的缩写。
			
			
	3.SpringBoot中注入外部Bean、属性：
		1.原理：	SpringBoot 完全摒弃了XML注解，实际上是使用spring 的纯注解来注入外部Bean。
				并且SpringBoot 还添加了一些注解，对注入Bean和属性做了优化。
				
		2.注入外部Bean:
				@Configuration
				//将带有@ConfigurationProperties注解的类 注入IOC容器 (SpringBoot)
				@EnableConfigurationProperties(JdbcProperties.class)	
				public class JdbcConfig{
					
					@Bean
					public DataSource createDataSource(JdbcProperties prop){
						DruidDataSource dataSource = new DruidDataSource();
						dataSource.setDriverClassName(prop.getDriverClassName());
						dataSource.setUrl(prop.getUrl());
						dataSource.setUsername(prop.getUsername());
						dataSource.setPassword(prop.getPassword());
						return dataSource;
					}
				}
				
				//将application.properties中数据注入字段，需字段提供get/set方法   (SpringBoot)
				@ConfigurationProperties(prefix="jdbc")	
				@Data									//该注解会在class文件中生成get/set方法。需要导入lombok.jar (lombok)
				public class JdbcProperties{
					private String driverClassName;
					private String url;
					private String username;
					private String password;
				}
		
		
	3.2 SpringBoot中注入外部Bean、属性：(最简洁)
		0.方法上添加 	@Bean
					@ConfigurationProperties(prefix="a") 即可。
					
		1.原理：	spring 自动将 application.properties中 配置属性,      
				与     
				new DruidDataSource()中 同名set方法匹配，注入到 DruidDataSource对象中。
				
		2.使用：
				@Configuration
				public class JdbcConfig{
				
					@Bean
					@ConfigurationProperties(prefix="jdbc")
					public DataSource createDruid(){
						return new DruidDataSource();
					}
				
				}
 */


/*								注册 springMVC组件
					

	1.自定义组件、拓展组件：
		HandlerMapping、HandlerAdapter、ViewResolver
		HandlerExceptionResolver、HandlerInterceptor、Converter、MultipartResolver
		
		1.可拓展组件：需要考虑 是否要实现接口
		2.可拓展组件：需要手动注入IOC容器(xml、注册到WebMvcConfigurer(SpringBoot中))
		
		
	2.SpringBoot中使用组件：
		1.配置类：
			1.实现`WebMvcConfigurer`并添加`@Configuration`注解。

		2.注册组件：
			1.通过 WebMvcConfigurer方法注册组件。
			
		3.例：
				@Configuration
				public class MvcConfig implements WebMvcConfigurer {
				
					@Override
					public void addInterceptors(InterceptorRegistry registry) {
						HandlerInterceptor interceptor = new MyInterceptor();
						registry.addInterceptor(interceptor).addPathPatterns("/**");	//注册拦截器，设置拦截路径
					}
				}
			
*/


/*								整合 Mybatis
	
	1.导入起步依赖：	
		1.由 mybatis提供，需给定版本：
			<dependency>
			    <groupId>org.mybatis.spring.boot</groupId>
			    <artifactId>mybatis-spring-boot-starter</artifactId>
			    <version>1.3.2</version>
			</dependency>

	2.导入数据库驱动：
			<dependency>
				<groupId>mysql</groupId>
				<artifactId>mysql-connector-java</artifactId>
			</dependency>
			
	3.配置数据库连接信息：
		1.在 application.properties中配置：
			#DB Configuration:
			spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
			spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springboot?serverTimezone=UTC
			spring.datasource.username=root
			spring.datasource.password=root
			
	4.创建表
	5.创建实体类 UserInfo
	6.编写Dao接口，并添加 @Mapper注解，即可注解编写sql.
	
 */


/*								整合 spring事务、连接池
	1.启动器依赖：
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-jdbc</artifactId>
			</dependency>
			
	2.依赖包介绍：
		1.该起步依赖包含了事务、 HikariCP连接池(目前最快的连接池)。
		2.底层包含了：	spring-jdbc.jar
		  				spring-tx.jar
			 			HikariCP.jar
		
	3.我们只需要配置：
		# 连接四大参数
		spring.datasource.url=jdbc:mysql://localhost:3306/heima
		spring.datasource.username=root
		spring.datasource.password=123
		
		# 可省略，SpringBoot自动推断
		spring.datasource.driverClassName=com.mysql.jdbc.Driver
		
		spring.datasource.hikari.idle-timeout=60000
		spring.datasource.hikari.maximum-pool-size=30
		spring.datasource.hikari.minimum-idle=10
 */


/*								通用 Mapper
			
	1.介绍：
		1.github：	https://github.com/abel533/Mapper
		
		1.通用 Mapper，自动生成 单表操作的通用方法，无需再自己定义。
		    无需配置 Mybatis驼峰，只需配置 Mybatis别名包(如果使用)。
		    
		3.底层包含了:		mybatis.jar
						mybatis-spring.jar
						spring-boot-starter-jdbc.jar;

	2.通用Mapper启动器
		通用Mapper的作者也为自己的插件编写了启动器：
			<dependency>
				<groupId>tk.mybatis</groupId>
				<artifactId>mapper-spring-boot-starter</artifactId>
				<version>2.0.2</version>
			</dependency>
			
	3.通用Mapper使用：
		1.Dao 继承 Mapper<T> 接口.				
		
		2.Dao 标注 @Mapper 注入IOC.						--(Mybatis注解)
		
		3.Bean 标注 @Table(name="user_tab") 关联表名.		--(通用Mapper 注解)
		
		4.Bean字段 	标注 @Id 关联主键.
					标注 @KeySql(useGeneratedKeys = true) 表示自增.	--(通用Mapper 注解)
					标注 @Transient 不会匹配数据库字段.
 */


/*								整合 Junit

	1.导入起步依赖：
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
				<scope>test</scope>
			</dependency>

	2.编写测试类：
		1.SpringRunner继承自SpringJUnit4ClassRunner，使用哪一个Spring提供的测试测试引擎都可以,
		2.@SpringBootTest的属性指定的是引导类的字节码对象.
		
			@RunWith(SpringRunner.class)
			@SpringBootTest(classes = MySpringBootApplication.class)
			public class MapperTest {
				
				@Test
				public void test() {
				}
			}

 */


/*								整合 Redis

	--详细使用见：黑马57期/微服务电商Day15

	1.导入起步依赖：
			<!-- 配置使用redis启动器 -->
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-data-redis</artifactId>
			</dependency>


	2.配置redis的连接信息
			#Redis
			spring.redis.host=127.0.0.1
			spring.redis.port=6379


	3.注入RedisTemplate测试redis操作
		1.导入启动器后，spring自动将 RedisTemplate注入到IOC容器中。(已测)
	
			@RunWith(SpringRunner.class)
			@SpringBootTest(classes = SpringbootJpaApplication.class)
			public class RedisTest {
			
				@Autowired
				private UserRepository userRepository;
				
				@Autowired
				private RedisTemplate<String, String> redisTemplate;
				
				@Test
				public void test() throws JsonProcessingException {
					//从redis缓存中获得指定的数据
					String userListData = redisTemplate.boundValueOps("user.findAll").get();
					
					//如果redis中没有数据的话
					if(null==userListData){
					
						//查询数据库获得数据
						List<User> all = userRepository.findAll();
						
						//转换成json格式字符串
						ObjectMapper om = new ObjectMapper();
						userListData = om.writeValueAsString(all);
						
						//将数据存储到redis中，下次在查询直接从redis中获得数据，不用在查询数据库
						redisTemplate.boundValueOps("user.findAll").set(userListData);
						System.out.println("===============从数据库获得数据===============");
					}else{
						System.out.println("===============从redis缓存中获得数据===============");
					}
					System.out.println(userListData);
				}
			}

 */


/*								使用 Lombok

	1. Lombok 的作用
		1. lombok 是一个开发工具，内置许多注解如：@Data
		2. 使用 lombok注解可以减少开发代码。
		
		
	2.常用注解：
	
		1. @Data
			1.位置：注解在类上。
			2.作用：	1.为所有属性自动生成 getter/setter、equals、canEqual、hashCode、toString方法。
					2.若属性为 final，则不会为该属性生成 setter方法。
					3.等同于 @Getter + @Setter + @EqualsAndHashCode + @ToString + @RequiredArgsConstructor

		2. @Getter/@Setter/@Accessors
			1.位置：注解在属性上。
			2.作用：	1.为相应的属性自动生成 Getter/Setter方法。
					2.使用 @Accessors 注解可以让set方法返回类本身，从而实现链式风格编程。
			
		3. @NonNull
			1.位置：注解在属性 或 构造器参数上。
			2.作用：	1.生成一个非空的声明，可用于校验参数，避免空指针。
			3.例：
					public class Bean{
					  private String name;
					  
					  public Bean(@NonNull String name) {
					    this.name = name;
					  }
					}
			
		4. @Cleanup
			1.位置：注解在对象上。
			2.作用：	1.帮助我们自动调用该对象的 close()方法。
			3.例：
					@Cleanup 
					InputStream in = new FileInputStream(args[0]);

		5. @EqualsAndHashCode
			1.位置：注解在类上。
			2.作用：	1.使用所有非静态（static）和非瞬态（transient）属性来生成 equals和 hasCode，
					2.也能通过 exclude来排除一些属性。
			3.例：	
					@EqualsAndHashCode(exclude={"id", "shape"})
					public class Bean{}
					
		6. @ToString
			1.位置：注解在类上。
			2.作用：	1.自动生成一个toString()方法。
					2.默认情况下，会输出类名、所有属性（按照属性定义顺序），用逗号来分割。
					3.通过 includeFieldNames参数设为 true，就能明确的输出toString()属性。
					4.通过 exclude = {"column","column2"}，可以排除column,column2 属性。
			3.例：
					@ToString(exclude = {"column","column2"}, includeFieldNames=true, callSuper=true)
					public class Bean{}
					
		7. @NoArgsConstructor、@RequiredArgsConstructor、@AllArgsConstructor
			1.位置：注解在类上。
			2.作用：	1.生成 无参构造。
					2.生成 部分参数构造。
					3.生成 全参构造。
		
	3.使用：
		1.导入依赖：
			lombok.jar
			
		2.安装 Lombok插件：
			1.IDE为 Eclipse：
				1.从官网下载lombok.jar。
					1.下载地址：https://projectlombok.org/download
					2.eclipse需要在英文路径下
				2.双击下载好的lombak.jar，安装。
					1.点击 Specify location..
					2.选择 eclipse的安装目录
					3.点击 Install / Update
				3.确认是否安装：
					1.eclipse安装路径下是否多了一个lombok.jar包
					2.配置文件 eclipse.ini中是否有：
						-javaagent:D:\Program Files (x86)\eclipse-jee-neon-1a-win32-x86_64\eclipse\lombok.jar
			2.IDE为 Idea。
			
		3.更新项目，使用注解。
			
*/


/*								ResponseEntity(RESTful)、通用异常处理

					(测试代码在：SpringCloudCustomer/wxt/controller/TestErrorController中)
	
	1.ResponseEntity<Bean> 代替 Bean		----作为返回值	
		1.一个响应包括：响应行(状态码)、响应头、响应体(数据)
		
		2.@ResponseBody：数据序列化后，存入响应体。
		
		3.Bean：响应体数据。
		
		4.ResponseEntity<Bean>：代表：响应实体<响应体数据>。包含响应行、响应头、响应体。
		
		
		5.ResponseEntity<Item>：							----RESTful风格要求响应 状态码、响应体
			1.响应实体类，属于springMVC框架 spring-web.jar包。
			2.可以设置状态码、响应体。
			3.使用 作为返回值：
			
				@GetMapping("/addCommodit/{price}/{name}")
				public ResponseEntity<Item> add(@PathVariable(name = "price", required = true) Integer price,
						@PathVariable(name = "name", required = true) String name) {
					if (StringUtils.isBlank(name) || (price == null)) {			//commons-lang3下工具包
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
					}
					Item i = new Item(Math.random() * 1000 + "", name, price);
			
					return ResponseEntity.status(HttpStatus.CREATED).body(i);	//返回响应实体，包含状态码、响应体
					--简化写法:	return ResponseEntity.ok(body);		//返回 OK状态码、实体类
				}
				
				注：
					1.ResponseEntity.status(status)方法中：status 可以为 HttpStatus或 int。
				
		
		
	2.客户端工具--Insomnia
		该工具可发送 RESTful风格请求，获得响应。
	
	
	
	3.通用异常处理：						----该处理方式的异常 一般是发送给前端或用户，告知错误(通常设计为 运行时异常)
		1.目的：对所有 Controller中的异常进行处理。
		
		2.技术：使用 AOP技术，对所有 Controller进行增强。
		
		3.实现：	1.MVC框架提供了 @ControllerAdvice注解----类上----处理所有标注 @Controller的处理器异常。
				2.和 @ExceptionHandler(Class ex)注解----方法----处理 Class匹配的异常。
			
				
		1:流程：
			1.依赖：spring-webmvc.jar
			
			2.自定义 异常处理通知类：
				1.@ControllerAdvice注解。
				2.@ExceptionHandler(Class ex)注解。
				
			3.例：
				1.自定义 异常处理通知类：
					@ControllerAdvice	//处理所有标注 @Controller的处理器异常
					public class CommonExceptionHandler {
					
						 // @ExceptionHandler	声明异常.Class，处理 RuntimeException异常
						 // @param	产生的异常对象
						 // @return	异常处理结果
						@ExceptionHandler(RuntimeException.class)
						public ResponseEntity<String> handleException(RuntimeException ex){
							
							return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
						}
					}
					
				2：Controller:
					@RestController
					public class TestErrorController {
						
						@GetMapping
						public ResponseEntity<Item> get() {
							throw new RuntimeException("请求有误");		//抛出 RuntimeException异常
							...
							return ResponseEntity.status(HttpStatus.CREATED).body(i);;
						}
					
					}
		
		2.优化：
			1.原因：	1.Controller中抛出的 异常信息为硬编码。
					2.所有 Runtime异常 处理结果相同，应该按异常信息 分别处理。
					3.处理结果封装为 JSON。
			
			2.解决方案：
					1.自定义 异常(类)---内含异常信息。	(处理工程中所有 Runtime异常，都使用此异常)
					2.自定义 异常信息(枚举类)。
					3.自定义 异常结果Bean。
					
			3.例：
				1.自定义 异常信息(枚举类)：
				
				2.自定义 异常(类)：
				
				3.自定义 异常结果Bean.
*/


/*								2.Nginx服务器

	1.Nginx介绍
		0. Nginx由 C编写。
		1. Nginx是一个高性能 web服务器:
				1.作为 web服务器，只能存放静态资源。
				2.高性能，能够支持高达 50000个并发连接的响应。	--tomcat最大连接数200
		2. Nginx支持负载均衡。
		3. Nginx支持邮件代理、反向代理。
		
		
	2.Nginx常用作网关：
		1. Nginx不仅是 web服务器，还具备网关必备的功能：
			1.反向代理
			2.负载均衡
			3.动态路由
			4.请求过滤
				
				
	3.web服务器
		1.web应用服务器：只能存放静态资源
			1.tomcat			--最大连接数200
			2.resin
			3.jetty
	
		2.web服务器：可以存放动态资源(java、python)
			1.Apache 服务器
			2.Nginx				--最大连接数50000
			3.IIS
			
		3.区分：web服务器不能解析jsp等页面，只能处理js、css、html等静态资源。
		    并发：web服务器的并发能力远高于web应用服务器。
			
			
	4.Nginx与反向代理
		1.反向代理:
			1.代理：	通过客户机的配置，实现让一台服务器代理客户机，客户的所有请求都交给代理服务器发送。(被代理对象是 客户机)
			2.反向代理：用一台服务器，代理真实服务器，用户访问时，不再是访问真实服务器，而是代理服务器。(被代理对象是 真实服务器)
		
		2.nginx做反向代理：
			1.在nginx中配置好反向代理的规则，不同的请求，交给不同的真实服务器处理
			2.请求到达nginx，nginx会根据已经定义的规则进行请求的转发，从而实现路由功能
			
			
	5.Linux下安装Nginx
		1.官网：	http://nginx.org/en/	(作者为俄罗斯人)
		
		2.安装步骤:						(参考：https://www.cnblogs.com/shamo89/p/7645792.html)
			1.解压:		tar -zxvf nginx-1.5.9.tar.gz 
			2.进入安装包:		cd nginx-1.5.9
			3.检查编译环境-指定安装目录-脚本文件目录：	./configure --prefix=/usr/local/src/Nginx --sbin-path=/usr/bin/nginx
			4.编译并安装：	make && make install
			(解决 PCRE问题：yum -y install pcre-devel)
			(解决  OpenSSL问题：yum -y install openssl openssl-devel)
			
			
	6.Nginx的启动
		0.Nginx默认端口：80
		1.启动命令：	nginx
			(启动后有两个进程)
			(1.nginx：master		主进程)
			(2.nginx：worker		工作进程)
			
		2.停止命令：	nginx -s stop
		3.重新加载：	nginx -s reload
		4.开机关闭防火墙：	chkconfig iptables off
		5.手动关闭防火墙：	service iptables stop
		
		直接访问虚拟机IP，即可访问 nginx页面(需关闭 linux防火墙)
		
		
	7.Nginx目录结构				(参考于：https://www.cnblogs.com/liang-io/p/9340335.html#_label4)
		|--client_body_temp
		|--conf								--Nginx所有配置文件的目录
		|   |-- fastcgi.conf         　　　				--fastcgi相关参数的配置文件
		|   |-- fastcgi.conf.default     　　			--fastcgi.conf的原始备份
		|   |-- fastcgi_params          　　　　　　　	--fastcgi的参数文件
		|   |-- fastcgi_params.default
		|   |-- koi-utf
		|   |-- koi-win
		|   |-- mime.types          　　　　　　　　　　--媒体类型，
		|   |-- mime.types.default
		|   |-- nginx.conf         　　　　　			--Nginx默认的主配置文件
		|   |-- nginx.conf.default
		|   |-- scgi_params         　　　　　　		--scgi相关参数文件，一般用不到
		|   |-- scgi_params.default
		|   |-- uwsgi_params               　　　		--uwsgi相关参数文件，一般用不到
		|   |-- uwsgi_params.default
		|   |-- win-utf
		|-- fastcgi_temp               　　　　　		--fastcgi临时数据目录
		|-- html							--默认的静态资源目录,类似于tomcat的 webapp
		|   |--50x.html									--错误页面优雅替代显示文件，例如：出现502错误时会调用此页面 / error_page   500502503504  /50x.html；
		|   |-- index.html   							--默认的首页文件，首页文件名字是在nginx.conf中事先定义好的。
		|-- logs         					--默认的日志路径，包括错误日志及访问日志
		|   |-- access.log								--默认访问日志文件，使用tail -f access.log，可以实时观看网站用户访问情况信息
		|   |-- error.log								--错误日志文件，如果Nginx出现启动故障等问题，一定要看看这个错误日志
		|   |-- nginx.pid								--pid文件，Nginx进程启动后，会把所有进程的ID号写到此文件
		|-- proxy_temp      				--临时目录
		|-- sbin							--Nginx命令的目录，如Nginx的启动命令nginx
		|   |-- nginx									--Nginx的启动命令nginx
		|-- scgi_temp						--临时目录
		|-- uwsgi_temp						--临时目录
		
		
	8.Nginx配置文件 nginx.conf:
		worker_processes  1；                		← worker进程的数量
		events {                     		← 事件区块开始
			worker_connections  1024；				  ← 每个worker进程支持的最大连接数
	 	}                                   ← 事件区块结束
		http {                              ← HTTP区块开始
			include       mime.types；          			  ← Nginx支持的媒体类型库文件
			default_type  application/octet-stream；     ← 默认的媒体类型
			sendfile        on；     					  ← 开启高效传输模式
			keepalive_timeout  65；     				  ← 连接超时
			server {            					  ← 第一个Server区块开始，表示一个独立的虚拟主机站点
				listen       80；     							 ← 提供服务的端口，默认80
				server_name  localhost；      					 ← 提供服务的域名主机名
				location / {            					 ← 第一个location区块开始
					root   html；      							 	← 站点的根目录，相当于Nginx的安装目录
					index  index.html index.htm；      				← 默认的首页文件，多个用空格分开
				}         									 ← 第一个location区块结果
				error_page   500502503504  /50x.html；    		 ← 出现对应的http状态码时，使用50x.html回应客户
				location = /50x.html {        				 ← location区块开始，访问50x.html
					root   html；    								← 指定对应的站点目录为html
				}
			}
		}

	
	1.Nginx做反向代理(反向代理的配置)：
		server {
	        listen       80;
	        server_name  manage.leyou.com;
	
	        proxy_set_header X-Forwarded-Host $host;
	        proxy_set_header X-Forwarded-Server $host;
	        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	
	        location / {
				proxy_pass http://192.168.154.1:10000;
				proxy_connect_timeout 600;
				proxy_read_timeout 600;
	        }
	    }
		server {
	        listen       80;
	        server_name  api.leyou.com;
	
	        proxy_set_header X-Forwarded-Host $host;
	        proxy_set_header X-Forwarded-Server $host;
	        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	
	        location / {
				proxy_pass http://192.168.154.1:8800;
				proxy_connect_timeout 600;
				proxy_read_timeout 600;
	        }
	    }
		
	
*/


/*								AJAX请求跨域问题
	
	1.跨域:
		浏览器对于javascript的同源策略的限制。
	
	2.怎样属于跨域:			
		1.域名不同					www.jd.com 与 www.taobao.com
		2.域名相同，端口不同			www.jd.com:8080 与 www.jd.com:8081
		3.二级域名不同				item.jd.com 与 miaosha.jd.com
		
		
	3.为什么有跨域问题:
		1.跨域不一定会有跨域问题。
		2.跨域问题是浏览器对于 ajax请求的一种安全限制：
			一个页面发起的 ajax请求，只能是与当前页域名相同的路径，这能有效的阻止跨站攻击。
		3.跨域问题 是针对 ajax的一种限制。
		
		
	4.解决跨域问题的方案：
		1.Jsonp
			1.最早的解决方案，利用script标签可以跨域的原理实现。
			2.限制： 	需要服务器的支持。
					只能发起GET请求。
					
		2.nginx反向代理
			1.利用 nginx把跨域反向代理为不跨域，支持各种请求方式
			2.缺点：	需要 nginx额外配置，语义不清晰。
			
		3.CORS
			1.规范化的跨域请求解决方案，安全可靠。
			2.优势：	在服务端进行控制是否允许跨域，可自定义规则
					支持各种请求方式
			3.缺点：	会产生额外的请求
				
				
	5.MVC的 CORS跨域过滤器
		1.MVC框架已经写好了 CorsFilter,直接使用即可。
		2.使用 CorsFilter：
			1.编写配置类。
			2.创建 CorsConfiguration配置对象，UrlBasedCorsConfigurationSource映射路径对象。
			2.将 CorsFilter注入IOC容器。
			例：
				@Configuration
				public class GlobalCorsConfig {
				    @Bean
				    public CorsFilter corsFilter() {
				        //1.添加CORS配置信息
				        CorsConfiguration config = new CorsConfiguration();
				        //1) 允许的域,不要写*，否则cookie就无法使用了
				        config.addAllowedOrigin("http://manage.leyou.com");
				        //2) 是否发送Cookie信息
				        config.setAllowCredentials(true);
				        //3) 允许的请求方式
				        config.addAllowedMethod("OPTIONS");
				        config.addAllowedMethod("HEAD");
				        config.addAllowedMethod("GET");
				        config.addAllowedMethod("PUT");
				        config.addAllowedMethod("POST");
				        config.addAllowedMethod("DELETE");
				        config.addAllowedMethod("PATCH");
				        // 4）允许的头信息
				        config.addAllowedHeader("*");
				        // 5）有效时长
				        config.setMaxAge(3600l);
				
				        //2.添加映射路径，我们拦截一切请求
				        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
				        configSource.registerCorsConfiguration("/**", config);
				
				        //3.返回新的CorsFilter.
				        return new CorsFilter(configSource);
				    }
				}
	
*/


/*								异常
		
	1.启动 SpringBoot项目失败。
		提示信息：NoClassDefFoundError: ch/qos/logback/core/spi/LifeCycle
		
		原因：缺少依赖 logback-classic包
		
		简介：Logback 是一个 Java 领域的日志框架。它被认为是 Log4J 的继承人。
			
		pom.xml中加入：	<dependency>
							<groupId>ch.qos.logback</groupId>
							<artifactId>logback-classic</artifactId>
							<exclusions>
								<exclusion>
									<groupId>org.slf4j</groupId>
									<artifactId>slf4j-log4j12</artifactId>
								</exclusion>
							</exclusions>
						</dependency>
	
	
	2.无法注入IUserDao 到 UserService中。
		提示信息：Field dao in wxt.service.imp.UserService required a bean of type 'wxt.dao.IUserDao' that could not be found.
	
		原IUserDao:		@Repository
						public interface IUserDao {
						
		后IUserDao:		@Mapper
						public interface IUserDao {
		
		原因：SpringBoot，注解开发Dao层时，应使用 @Mapper
	

 */




/*								(idea) 创建一个SpringBoot项目

 	1.创建一个 maven工程。
 	
 	
 	2.添加 SpringBoot 的起步依赖：	
 		1.在pom.xml中，添加 SpringBoot 的起步依赖spring-boot-starter-parent。
 		2.所有springboot工程都需要继承 spring-boot-starter-parent.
 		
 		例：	
 			<parent>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-parent</artifactId>
				<version>2.0.1.RELEASE</version>
			</parent>


	3.导入web的启动依赖：
 		1.集成 SpringMVC 进行 Controller的开发，需要导入此坐标。
 		2. web 功能的起步依赖。
 		
 		例：		
 			<dependencies>
				<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-web</artifactId>
				</dependency>
			</dependencies>
 
 
 	4.编写 SpringBoot引导类：
 		1.要通过SpringBoot提供的引导类起步，SpringBoot才可以进行访问。
 		2.引导类需要添加 @SpringBootApplication，表示该类为引导类，该注解具备多种功能。
 		3.引导类中提供 main方法，main方法中执行：SpringApplication.run(引导类.class)

 		例：
 			@SpringBootApplication
			public class MySpringBootApplication {
			
				public static void main(String[] args) {
					SpringApplication.run(MySpringBootApplication.class);
				}
				
			}

	5.编写 controller类：
		1.在引导类 MySpringBootApplication 同级包 或者子包中，创建 Controller类。
		2. controller类上添加注解 @Controller。

 */

public class SpringBoot详解 {

	
}
