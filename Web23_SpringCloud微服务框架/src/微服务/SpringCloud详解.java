package 微服务;

/*						集群 / 分布式 / SOA(面向服务架构) / 微服务 / 远程调用方式 / SpringCloud

	1.集群技术：
		通过一组计算机完成同一个工作(业务)，达到更高的效率、稳定性。
		(通过负载均衡共同对外提供服务)

	2.分布式：
		将一个业务模块拆分为多个基础服务，在不同的服务器上部署运行，最终形成一个模块。
		(解决网站高并发问题)
		
	3. SOA：
		面向服务架构。(Service-Oriented Architecture)
		1.将业务系统分解为多个服务，通过服务的组合编排实现业务功能。
		
	4.微服务：
		将业务彻底 组件化、服务化。
		1.每个服务可独立开发、运行、部署。(可复用、可替换)
		2.每个服务对应唯一业务功能。(单一职责)
		3.只需向外界提供访问 rest接口,不关心服务细节。(面向服务)
	
	
	1.远程调用方式
	
		1.RPC：
			1.Remote Produce Call远程过程调用，类似的还有RMI。
			2.自定义数据传输 格式，基于原生TCP通信，限制了开发语言。速度快，效率高。
			3.早期的webservice，现在热门的dubbo，都是RPC的典型。
			
		2.Http：
			1.http是一种网络传输协议。
			2.规定了数据传输的 格式，基于TCP，不规定API和语言，跨语言、跨平台。更灵活。
			3.一般采用 rest风格的请求方式。Spring的 RestTemplate就基于HTTP进行了封装，支持3种客户端。
			4.流行框架 SpringCloud.
				(3种客户端工具：HttpClient、OKHttp、URLConnection)
				
			 .使用 RestTemplate：
				User user = new RestTemplate().getForObject("http://localhost/hello", User.class);
				(直接调用缺点：硬编码了调用服务的 Url，不清楚 服务是否宕机，就算集群也需手动实现负载均衡)
		
*/


/*						SpringCloud 框架、几个组件

	1.SpringCloud
	
		1.SpringCloud：是Spring 家族的微服务框架。		官网：https://spring.io/projects/spring-cloud#learn
		
		2.版本：从A为首字母 开始命名。如：Edgware.SR3	Finchley.RC1	Greenwich.SR6
		
		3.涉及组件：
			- Eureka：注册中心
			- Ribbon：负载均衡
			- Hystix：熔断器
			- Feign：服务调用
			- Zuul：服务网关
			
		4.SpringCloud 版本管理：
			<dependencyManagement>
				<dependencies>
		            <!-- SpringCloud依赖，一定要放到dependencyManagement中，起到管理版本的作用即可 -->
					<dependency>
						<groupId>org.springframework.cloud</groupId>
						<artifactId>spring-cloud-dependencies</artifactId>
						<version>${spring-cloud.version}</version>
						<type>pom</type>
						<scope>import</scope>
					</dependency>
				</dependencies>
			</dependencyManagement>
			
	
	
	------------------------------------------------ 组件一 ------------------------------------------------------------------
			
	1.Eureka 服务注册中心
		1.微服务框架的 服务注册中心，对服务提供者 进行注册，对服务使用者 提供url。(Eureka可以是一个集群，本身也是一个服务)
			1.降低服务之间耦合
			2.管理服务的可用状态
			3.便于对集群 的负载均衡(合理分配用户请求)
		
		2.Eureka治理机制：
			1.提供者：
				1.服务注册：1.启动时发送 rest请求，将自己注册到 EurekaServer。
						   2.EurekaServer将服务信息 保存到一个双层Map中。
						      第一层Map的Key就是服务名，第二层Map的key是服务的实例id，value为实例信息。
						      	1.实例id 默认格式：${hostname} + ${spring.application.name} + ${server.port}
						      	2.实例id 是区分同一服务的不同实例的唯一标准，因此不能重复.
						      	3.可以通过配置修改 实例id 格式：	instance:
    																instance-id: ${spring.application.name}:${server.port}
						      
				2.服务续约：注册后，维持一个心跳---定时发送 rest请求 续约 EurekaServer(默认30s)。	--配置服务续约(renew)的间隔：lease-renewal-interval-in-seconds： 	默认为30秒	
				3.服务下线：当服务实例进行正常关闭时，发送 rest请求 告知 EurekaServer。
				
				
			2.使用者：
				1.获取服务：启用 服务消费者时，发送请求给 EurekaServer，获取服务清单
				2.服务调用：通过服务名 获得服务实例、实例元数据
				
				
			3.EurekaServer:
				1.失效剔除：默认每隔60s 将当前清单中 超时90s 未续约的服务剔除。	--配置服务失效时间：lease-expiration-duration-in-seconds：	默认值90秒
				2.自我保护：一个服务未按时续约，Eureka会统计最近15分钟心跳丢失的 服务实例的比例是否超过了85%，尽可能保护当前实例注册信息。
		
		
		3.Eureka 使用：
			-------------------配置 Eureka服务治理-------------------
			1.导入依赖： (先添加 SpringCloud 版本管理)	
					<!-- Euceka -->
					<dependency>
					    <groupId>org.springframework.cloud</groupId>
					    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
					</dependency>
					
			2.启动类：添加 @EnableEurekaServer注解，声明该应用是一个EurekaServer。
			
			3.applicatiom.yml配置：

				spring:
				  application:
				    name: eureka-server 		# 服务名称，会在Eureka中显示
				eureka:
				  client:
				    register-with-eureka: false # 是否注册自己的信息到EurekaServer，默认是true
				    fetch-registry: false 		# 是否拉取其它服务的信息，默认是true
				    service-url: 				# EurekaServer的地址，现在是自己的地址，如果是集群，需要加上其它Server的地址。
				      defaultZone: http://127.0.0.1:${server.port}/eureka
				      
				      
		 	-------------------注册 服务 / 调用者-------------------
			1.导入依赖：	(先添加 SpringCloud 版本管理)	
					<dependency>
					    <groupId>org.springframework.cloud</groupId>
					    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
					</dependency>
					
			2.启动类： @EnableDiscoveryClient注解，声明 是Eureka的使用者。
			
			3.applicatiom.yml配置：

				spring:
				  application:
				    name: eureka-server 		# 服务名称，会在Eureka中显示
				eureka:
				  client:
				    service-url: 				# Eureka服务的 url地址
				      defaultZone: http://127.0.0.1:8888/eureka
				      
				---------------调用者-------------
				      
			4.远程调用类--通过服务名 获得IP/port--远程调用服务：
			
				1.启动类中 注入 RestTemplate对象：
					@Bean
					public RestTemplate createTemplate() {
						return new RestTemplate();
					}
				
				2.调用类中：(未加入负载均衡，代码繁琐)
					@Autowired
	    			private RestTemplate restTemplate;				// 远程调用
					@Autowired
	    			private DiscoveryClient discoveryClient;		// Eureka客户端，可以获取到服务实例信息
	    			
	    			public User queryById(Long id) {
		    			// 1.根据服务名称，获取服务实例
				        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
				        // 2.因为只有一个UserService,因此我们直接get(0)获取
				        ServiceInstance instance = instances.get(0);
				        // 3.获取ip和端口信息
				        String baseUrl = "http://"+instance.getHost() + ":" + instance.getPort()+"/user/";
						
						User user = restTemplate.getForObject(baseUrl + id , User.class);
						return user;
					}
						
				
	------------------------------------------------ 组件二 ------------------------------------------------------------------
	
	2.Ribbon 负载均衡
		1.负载均衡: 
			发送请求后从 Eureka服务清单中获取 服务集群(通过服务名)，通过算法 从集群中选择出适合的 服务(IP+port)，帮服务消费者 发送请求到该服务中。
			1.负载均衡算法：随机、轮询、hash，	默认轮询。
			2.默认连接超时时长1秒。
			
		
		2.Ribbon 使用：
			-------------------调用者-------------------
			1.导入依赖：(先添加 SpringCloud 版本管理)	
					<dependency>
					    <groupId>org.springframework.cloud</groupId>
					    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
					</dependency>
					
			2.启动类中: @LoadBalanced 注解于RestTemplate上，拦截RestTemplate请求进行 集群服务的选择
					@Bean
					@LoadBalanced	
					public RestTemplate createTemplate() {
						return new RestTemplate();
					}
			
			3.远程调用类中：
					@Autowired
	    			private RestTemplate restTemplate;				// 远程调用
	    			
	    			public User queryById(Long id) {
		    			
				        // 1.服务名的 url
				        String baseUrl = "http://服务名/user/" + id;
						
						User user = restTemplate.getForObject(baseUrl , User.class);
						return user;
					}
					
			4.修改负载均衡算法：(可选配置，默认轮询)
					1.application.yml中添加：	
					user-service:
						ribbon:
							NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
		
			5.ribbon配置：
					1.ribbon配置信息在 AbstractRibbonCommand 类中
					2.ribbon超时时长 = (connect + read)*2		--(Read + Connect)* (maxAutoRetries + 1) * (maxAutoRetriesNextServer + 1);
					
					ribbon:
					  MaxAutoRetries: 0 #最大重试次数，当Eureka中可以找到服务，但是服务连不上时将会重试
					  MaxAutoRetriesNextServer: 1 #切换实例的重试次数
					  OkToRetryOnAllOperations: false # 对所有的操作请求都进行重试，如果是get则可以，如果是post,put等操作没有实现幂等的情况下是很危险的，所以设置为false
					  ConnectTimeout: 1000 #请求连接的超时时间
					  ReadTimeout: 1800 #请求处理的超时时间
			
			
			
	------------------------------------------------ 组件三 ------------------------------------------------------------------
	
	3.Hystix 熔断器
		1.Hystix是Netflix开源的一个延迟和容错库。
			应对服务间调用延迟、调用失败阻塞 而引起服务器资源耗尽的 雪崩、阻塞等问题，提供了解决方案。	
		
		2.解决方案：
			1.线程隔离(默认实现)：为每个 依赖服务(一个服务有多个依赖服务)，分配一个小的线程池。调用请求通过线程池中空闲线程访问 依赖服务。
			2.线程降级(手动添加)：依赖服务调用超时(失败)、或线程池满时，不会阻塞--直接返回一个执行结果。
			3.线程熔断(默认实现)：熔断机制，不判断超时时长，直接降级。
			
		3.好处：
			1.访问线程池，用户的请求失败，不会导致阻塞而引起雪崩，(线程隔离)
			2.调用失败 直接返回执行结果。(线程降级、熔断)
			
			
		4.Hystix 的使用：
			---------------调用者---------------
			1.引入依赖：(先添加 SpringCloud 版本管理)	
					<dependency>
					    <groupId>org.springframework.cloud</groupId>
					    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
					</dependency>
					
			2.启动类： 添加 @EnableCircuitBreaker 表示开启线程熔断
					
					SpringCloud 提供了一个注解，可以代替 Eureka、Hystrix、SpringBoot的注解
					//@EnableDiscoveryClient
					//@EnableCircuitBreaker
					//@SpringBootApplication
					@SpringCloudApplication
					
					
			3.远程调用类：
					0.类上 添加默认失败方法	@DefaultProperties(defaultFallback = "defaultFallback")
					1.调用方法 开启线程降级	@HystrixCommand 
						1.可配方法的 超时时长，默认1秒
							@HystrixCommand(commandProperties = {
								@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
							})
					2.调用失败(超时)，执行默认 fallbackMethod方法。	
					3.fallbackMethod方法 返回值类型--以调用方法为准。
					
					@DefaultProperties(defaultFallback = "defaultFallback")
					public class service() {
					
						@HystrixCommand	
					    public User queryUserById(Long id){
					        String url = "http://user-service/user/" + id;
					        User user = this.restTemplate.getForObject(url, User.class);
					        return user;
					    }
					
						//fallbackMethod 方法返回值一般为String、参数列表为空。
					    public String defaultFallback(){
					        return "服务器忙";
					    }
					    
					}
					
			4.配置Hystrix 全局超时时长：(可选)
					1.配置信息在：	hystrix-core.jar，com.netflix.hystrix中，HystrixCommandProperties类。
					2.Ribbon的超时时间一定要小于Hystix的超时时间。
					3.Greenwich.SR6版本中，超时多了一个s，为：timeoutInMilliseconds
					
					hystrix:
					  command:
					  	default:
					        execution:
					          isolation:
					            thread:
					              timeoutInMillisecond: 6000 	# 设置hystrix的超时时间为6000ms，默认1秒
					              (timeoutInMilliseconds: 6000		Greenwich.SR6版本)
					  	user-service:			
					        execution:
					          isolation:
					            thread:
					              timeoutInMillisecond: 5000 	# 设置user-service服务的超时时长为6000ms
					              (timeoutInMilliseconds: 5000		Greenwich.SR6版本)
					              
		5.熔断机制：(默认启用)
			熔断器三个状态：
				Closed：所有请求正常访问。
				Open：所有请求直接降级。进入休眠状态(5秒)，5秒后进入Half Open状态。
				Half Open：此时释放部分请求，若这些请求都未超时，则进入 Closed；否则进入 Open。
				
			1.当Hystrix Command请求后端服务数量(默认20次)，失败超过一定比例(默认50%), 断路器会切换到开路状态(Open). 
			2.Open：所有请求会直接失败而不会发送到后端服务. 断路器保持在开路状态一段时间后(默认5秒), 自动切换到半开路状态(HALF-OPEN).
			3.Half-Open：这时会释放一些请求的返回情况, 如果请求成功, 断路器切回闭路状态(CLOSED), 否则重新切换到开路状态(OPEN). 
		
		
	------------------------------------------------ 组件四 ------------------------------------------------------------------

	4. Feign 声明式调用
		1. Feign 是Netflix开发的声明式、模板式的HTTP客户端。它可以使调用者像 调用本地方法一样，简洁、优雅地远程调用服务。
			1.Spring Cloud中Feign 支持 springMVC，整合了Ribbon、Hystrix. 


		2. Feign的使用：
			1.导入依赖：(先添加 SpringCloud 版本管理)	
					<dependency>
					    <groupId>org.springframework.cloud</groupId>
					    <artifactId>spring-cloud-starter-openfeign</artifactId>
					</dependency>
					
			2.启动类上：添加注解 @EnableFeignClients	--开启Feign
					
			3.创建Feign接口：		(该接口可调用远程方法)
				1.远程调用4个参数：请求方式、请求URL、传入参数、返回值.
				2.Feign底层对其动态代理
				
					@FeignClient("user-service")	--1.服务名，该注解 将该接口实现类对象注入IOC容器
					public interface UserService {
					
					    @GetMapping("/user/{id}")	--2.请求方式、URI
					    User queryUserById(@PathVariable("id") Long id);	--3.传入参数、返回值
					}
			
			
			4.调用接口--即调用远程方法:
				1.Fegin中使用了负载均衡，因此可以省去 @LoadBalanced 和 注入RestTemplate.
			
					@Autowired
					private UserFeignClient client;
					
					@GetMapping("{id}")
					public void test(@PathVariable("id") Long id){
						
						User user = client.queryUserById(id);		--远程调用
					}
			
			5.yml配置：
				1.ribbon超时时长：
					feign:
					  client: 
					    config: 
					      default: 
					        connect-timeout: 2000 	 #设置Feign ribbon连接超时时间 (连接到服务时间，默认一秒)
					        read-timeout: 3000 	 #设置Feign ribbon读取超时时间 (建立连接后，读取数据超时，默认一秒)
					        
				2.开启hystrix熔断:
					feign:
					  hystrix:
					    enabled: true 	#开启Feign的熔断功能
				
					    
					    
		3.Feign中启用 Hystrix熔断：
			0.启动类：注解 @EnableCircuitBreaker
		
			1.配置文件：
					feign:
					  hystrix:
					    enabled: true # 开启Feign的熔断功能
			
			
			2.熔断方法类--实现Feign接口：(作为fallback的处理类)
			
					@Component		--注入IOC容器
					public class UserFeignClientFallback implements UserService {
						@Override
						public User qureyUser(Long id) {
							User user = new User();
							user.setName("服务器忙");
							return user;
						}
					}
			
			3.Feign接口中，指定实现类：
					@FeignClient(value = "user-service", fallback = UserFeignClientFallback.class)
					public interface UserService {
						@GetMapping("/user/{id}")
					    User queryUserById(@PathVariable("id") Long id);
					}
		
		
	------------------------------------------------ 组件五 ------------------------------------------------------------------
	
	5. Zuul 网关
		1.对外提供网关的入口，不暴露服务地址；
		2.对客户请求的负载均衡，提供路由映射。
		3.处理权限校验问题；
		
		1.zuul的路由和负载均衡
			1.导入依赖：(先添加 SpringCloud 版本管理)
				自带web依赖、ribbon依赖、hystrix依赖。
					<dependency>
					    <groupId>org.springframework.cloud</groupId>
					    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
					</dependency>
				
			2.启动类：添加 @EnableZuulProxy注解，开启Zuul网关
			
			3.配置：
					0.基本配置：	server.port	
								spring.application.name	
								eureka.client.service-url.defaultZone
			
			
					0-1.Zuul对所有服务，添加了 默认的路由规则：	user-service: /user-service/**
					
					zuul:
					  routes:
					    user-service: /user-service/** 		# 服务名：映射路径 (简化写法)
					
					
					
					1.指定全局路由前缀后，发起请求时，路径就要以/api开头。
					
					zuul:
					  prefix: /api		 # 全局路由前缀
					  routes:
					      user-service: 	# 路由id，随意写
					        path: /user-service/** 		# 映射路径
					        service-id: user-service 	# 指定服务名
					        strip-prefix:false			# 去除前缀(映射到服务中时，去除path),默认true
					  ignored-services:		# 指定服务名，不添加 默认路由规则
					  	  - customer		
					
					        
		2.Zuul的过滤器 ZuulFilter
			1. ZuulFilter 是过滤器的顶层抽象类。常用于权限校验。
			
			2. ZuulFilter 中最重要的4个方法：
				1.返回过滤器类型：	FilterConstants.PRE_TYPE	--请求在被路由之前执行
									FilterConstants.ROUTE_TYPE	--在路由请求时调用，此过滤器将请求路由到微服务
									FilterConstants.POST_TYPE	--此过滤器在路由到微服务以后执行。
									FilterConstants.ERROR_TYPE	--在其他阶段发生错误时执行该过滤器，之后执行POST过滤器
					abstract public String filterType();
					
					
				2.返回过滤器执行顺序：数字小，优先级高
									FilterConstants中常量	--校验放在处理请求头前：FilterConstants.PRE_DECORATION_FILTER_ORDER - 1	
			    	abstract public int filterOrder();
			    	
			    	
			    3.返回过滤器是否执行：	true-执行
			    					false-不执行
			    	boolean shouldFilter();		// 来自IZuulFilter
			
				
				4.过滤器的执行逻辑：
			    	Object run() throws ZuulException;		// IZuulFilter
			
			
			3.过滤器执行生命周期：(见图片)
				pre---route---post
				 |------|------|-error
				 
				 
			4. Zuul 过滤器的使用:
				1.自定义过滤器：继承 ZuulFilter 抽象类。
						自定义过滤器实现4个方法：
						abstract public String filterType();
					    abstract public int filterOrder();
					    boolean shouldFilter();
					    Object run() throws ZuulException;
				    
				2.注入到IOC容器：添加 @Component 注解。
				
				
						(StringUtils工具：apache下的commons-lang3.jar，可以方便的操作字符串的工具类)
						@Override
						public Object run() throws ZuulException {
							// 1.获得request对象
							RequestContext requestContext = RequestContext.getCurrentContext();
							HttpServletRequest request = requestContext.getRequest();
							// 2.获得参数access-token的value
							String token = request.getParameter("access-token");
							if (StringUtils.isBlank(token)) {
								// 若access-token不存在、为空，则不执行
								requestContext.setSendZuulResponse(false);
								// 返回状态码
								requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
							}
							return null;
						}
				    
 */					


	

/*						BUG 排查
	
	1.Eureka客户端服务 无法注册到 Eureka.
		报错：	Cannot execute request on any known server
		
		原因：	application.yml配置错误
				(还有可能：配置中有tab缩进)
				
		错误配置：	#eureka配置
					eureka:
					  client:
					    service-url:                  
					      defaultZone: http://127.0.0.1:${server.port}/eureka
					      
		正确配置：	#eureka配置
					eureka:
					  client:
					    service-url:                 					 # EurekaServer的地址
					      defaultZone: http://127.0.0.1:8888/eureka

		总结：	eureka客户端配置中，defaultZone 应配 EurekaServer的地址
		
		
	2.Feign客户端连接超时
		报错：	feign.RetryableException: Read timed out executing GET http://user-service/user/1
		
		原因：	Feign的 HTTPClient连接远程服务器超时，修改配置即可
		
		添加配置：	feign:
					  client: 
					    config: 
					      default: 
					        connect-timeout: 20000  #设置Feign ribbon连接超时时间(连接到服务时间，默认一秒)
					        read-timeout: 20000  #设置Feign ribbon读取超时时间(连接服务后，读取数据超时，默认一秒)
					        
	3. Hystrix超时时长无效
		原因：	Greenwich.SR6版本配置变化
		
		原配置：	hystrix:
				  command:
				  	default:
				        execution:
				          isolation:
				            thread:
				              timeoutInMillisecond: 6000 	# 设置hystrix的超时时间为6000ms，默认1秒
				              (Finchley版本)
			
		现配置：	hystrix:
				  command:
				  	default:
				        execution:
				          isolation:
				            thread:
				              timeoutInMilliseconds: 6000 	# 设置hystrix的超时时间为6000ms，默认1秒
				              (Greenwich.SR6版本)
				              
		
 */

public class SpringCloud详解 {

	
}
