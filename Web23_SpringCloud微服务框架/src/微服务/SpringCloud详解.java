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
				      
			4.调用者--通过服务名 获得IP/port--远程调用服务：
			
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
			
			3.调用类中：
					@Autowired
	    			private RestTemplate restTemplate;				// 远程调用
	    			
	    			public User queryById(Long id) {
		    			
				        // 1.服务名的 url
				        String baseUrl = "http://服务名/user/" + id;
						
						User user = restTemplate.getForObject(baseUrl , User.class);
						return user;
					}
		
			
 */
	

/*						BUG 排查
	
	1.Eureka客户端服务 无法注册到 Eureka.
		报错：	Cannot execute request on any known server
		
		原因：	application.yml配置错误
				
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
		
 */

public class SpringCloud详解 {

	
}
