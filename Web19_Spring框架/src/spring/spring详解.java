package spring;

/*								1.spring详解
							
					(1.IOC 依赖注入，降低耦合
					 2.AOP 面向切面，方法增强
					 3.声明式事务--Service层
					 4.IOC 核心容器接口--ApplicationContext(单例)/BeanFactory(多例)
					 
					 5.注入bean (xml):	1.默认构造					<bean	id  class ,
					 					2.Factory工厂的创建方/法		<bean	id  class , id  factory-bean  factory-method ,	
					 					3.Factory工厂的静态创建方法	<bean	id  class  factory-method  ,
					 					4.开启注解(类上添加注解)：		<context:component-scan base-package="service"></context:component-scan>
					 												@Component("user") @Service("service") 
																	@Controller("controller") @Repository("dao")
					 												
					 6.注入数据	(xml)：	1.带参构造					<constructor-arg>
					 					2.set方法注入				<property>
					 					3.注解(字段添加注解)：			引用类型：@Autowired @Qualifier("id") @Resource("id") 
					 												基本类型：@Value
					 												集合类型：只能使用xml
					 					
					 7.AOP 编程：	Joinpoint, Pointcut, Advice, Target, Weaving, Aspect
					 8.AOP 流程：	1.核心业务代码
					 				2.抽取共同代码--通知类
					 				3.配置通知与切入点的关系：			aspect切面--	切入点：	切入点表达式
					 															通知：	通知类型，方法
									4.配置开启AOP注解：(常)			<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
									 (若Controller层，mvc配置中添加：<aop:aspectj-autoproxy proxy-target-class="true"/>)
					 					
					 9.事务控制：		控制类：DataSourceTransactionManager
					 10.事务流程：	1.配置 事务管理器					<bean id class="..DataSourceTransactionManager">
					 				2.配置 事务通知					<tx:advice>	<tx:attributes> <tx:method>
					 				3.配置 AOP						<aop:config> <aop:advisor advice-ref pointcut-ref>
					 				
					 				4.开启注解 控制事务：(常)			<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>	
						 				5.使用 @Transactional 注解：	类：		@Service("accountService")
																			@Transactional(propagation= Propagation.SUPPORTS,readOnly=true)
					 												
					 												方法：	@Transactional(propagation= Propagation.REQUIRED,readOnly=false)
					 					)

 	1.什么是spring
 		JavaSE/EE应用的一站式、轻量级、开源框架。以IOC 和AOP 为核心。还能整合许多优秀的开源框架和类库。

 		IOC(Inverse Of Control)：	反转控制:把创建对象的权力交给框架/工厂，它包括依赖注入和依赖查找。	//用来降低耦合
 		AOP(Aspect Oriented Programming)：	面向切面编程
			
	2.spring官网
		https://spring.io
		
	3.spring的优势
		1.IOC 依赖注入。方便解耦，简化开发
		2.AOP 编程的支持
		3.声明式事务的支持(事务控制应该放在业务层Service)
		4.方便程序的测试
		5.方便集成各种优秀框架
		6.降低 JavaEE API 的使用难度
		7.Java 源码是经典学习范例

	4.maven坐标
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-context</artifactId>
		    <version>5.1.5.RELEASE</version>
		</dependency>

	5.程序的耦合
		程序的耦合
			耦合：程序间的依赖关系
			包括：
				类之间的依赖
	           	方法间的依赖
		解耦：
			降低程序间的依赖关系
		实际开发中：
	   		应该做到：编译期不依赖，运行时才依赖。
	   		
		解耦的思路：
			第一步：使用反射来创建对象，而避免使用new关键字。
			第二步：通过读取配置文件来获取要创建的对象全限定类名
			
	6.解耦：工厂模式
		Bean:		计算机英语中，意为可重用组件(可被重复调用的组件)。
		Javabean:	用Java 语言编写的可重用组件(如dao、service)。
					Javabean范围 > 实体类
				
		Bean工厂：	用来创建service和dao。	
			
			第一步：需要一个配置文件来配置需要的service和dao。
				       配置的内容：唯一标志=全限定类名(key=value)
			第二步：通过读取配置文件中的内容，反射创建对象。
					Class.forName(全限定类名).newInstance();//调用默认构造函数
			
			配置文件可以是xml 或properties
			
	7.单例模式：
		第一种：类为单例类
				public class A{
					private A(){}
					private static A a = new A();
					public static A getClass(){
						return a;	
					}	
				}
				
		第二种：工厂类中，静态变量Map/List 储存对象，每次取出都为同一个对象
		        public class A{
					private static Map<String,Object> beans = new HashMap<String,Object>();
					static{
						Object o = new Object();
						beans.put("o",o);
					}
				}      
 */

/*								IOC (基于xml)					(--见: Web19_Spring_IOC_XMLproject)
	1.准备spring 开发包
		官网：	 http://spring.io/
		下载地址：http://repo.springsource.org/libs-release-local/org/springframework/spring
		解压:(Spring 目录结构)
	 			docs:	API和开发规范.
				libs:	jar包和源码.
				schema:	约束.
		
		特别说明：
				spring5 版本是用 jdk8 编写的，所以要求我们的 jdk 版本是 8 及以上。
				同时 tomcat 的版本要求 8.5 及以上。


	2.使用spring
		1.创建maven 工程。
		2.添加依赖包spring。
		3.创建配置文件bean.xml。约束在：浏览器 ——> Spring Framework Documentation ——> core ——> 查找‘xmlns’
								(该网页实质位于spring-framework-5.0.2.RELEASE-dist\spring-framework-5.0.2.RELEASE\docs\
																				spring-framework-reference\index.html)
								(xml配置文件开发约束：查找xmlns,	注解开发约束：查找xmlns:context)
		4.配置文件bean.xml添加约束(结尾手动添加</beans>标签)。
		
		
		
	3.让spring 管理(bean)资源
	
		1.创建出service、dao的bean模块:
			1.service包:	接口、实现类
			2.dao包:		接口、实现类
		
		2.配置bean.xml,添加需要的bean：
			1.配置 service 
				<bean id="accountService" class="service.impl.AccountServiceImpl"></bean>
			2.配置 dao
				<bean id="accountDao" class="dao.impl.AccountDaoImpl"></bean>
			
			id属性：	    该bean 的唯一标识
			class属性：该bean 的全限定类名。(bean 应先创建出，再填写) (bean 的具体实现类)
		
		3.测试：获取spring的IOC核心容器,根据id获取对象classpathxmlapplicationcontext
			//1.获取核心容器对象：指定配置文件位置
			ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
			
			//2.获取bean 对象:根据bean.xml中的 id 
			AccountService service = (AccountService) context.getBean("accountService");
		 	//3.获取bean 对象:根据bean接口的class
			AccountDao dao = context.getBean(AccountDao.class);
				
	4.IOC核心容器：ApplicationContext接口、的三个常用实现类：
			ClassPathXmlApplicationContext：它可以加载类路径下的配置文件，要求配置文件必须在类路径下。不在的话，加载不了。(常用)
		 	FileSystemXmlApplicationContext：它可以加载磁盘任意路径下的配置文件(必须有访问权限）：根据全路径
			AnnotationConfigApplicationContext：它是用于读取注解创建容器的，是第二天的内容。
			
	5.核心容器的两个接口引发出的问题：
			ApplicationContext: (单例对象适用)(只创建对象一次)
				它在构建核心容器时，创建对象采取的策略是采用立即加载的方式。也就是说，只要一读取完配置文件马上就创建配置文件中配置的对象。

			BeanFactory: (多例对象使用)
				它在构建核心容器时，创建对象采取的策略是采用延迟加载的方式。也就是说，什么时候根据id获取对象了，什么时候才真正的创建对象。
				
 */

/*								(IOC管理bean) bean.xml详解、创建bean的方式、bean生命周期
	
	以下为bean.xml的配置：
	
	    0.spring对bean的管理细节
		    1.三种不同bean 的注入方式：用哪个对象创建，用什么id来取
		    2.bean对象的作用范围
		    3.bean对象的生命周期
	    
	
		1.三种不同bean 的注入方式
		   	1.第一种：spring 使用class对应类 的默认构造函数创建对象。
		            在spring的配置文件中使用bean标签，配以id和class属性之后，且没有其他属性和标签时。
		            采用的就是默认构造函数创建bean对象，此时如果类中没有默认构造函数，则对象无法创建。
		
		    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>
		    
		    
			2.第二种：spring 使用factory-bean对应普通工厂类 的factory-method方法 创建对象（使用某个类中的方法创建对象，并存入spring容器）
		    <bean id="instanceFactory" class="com.itheima.factory.InstanceFactory"></bean>
		    <bean id="accountService" factory-bean="instanceFactory" factory-method="getAccountService"></bean>
		   
			3.第三种：spring 使用工厂中的静态方法创建对象（使用某个类中的静态方法创建对象，并存入spring容器)
		    <bean id="accountService" class="com.itheima.factory.StaticFactory" factory-method="getAccountService"></bean>
	   
	
	    2.bean的作用范围	(调整bean是单例还是多例)
	        
	        bean标签的scope属性：
		            作用：用于指定bean的作用范围
		            取值： 常用的就是单例的和多例的
		                singleton：单例的（默认值）
		                prototype：多例的
		                request： web项目中，每个http request新建一个bean
		                session： web项目中，每个http session新建一个bean
		                global-session：每个global http session新建一个bean,作用于集群环境的会话范围（全局会话范围），当不是集群环境时，它就是session
	
		    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl" scope="prototype"></bean>
		    -->
	
	    3.bean对象的生命周期
	    
		            单例对象	(单例对象的生命周期和容器相同)
		                出生：当容器创建时对象出生
		                活着：只要容器还在，对象一直活着
		                死亡：容器销毁，对象消亡
		                
		            多例对象
		                出生：当我们使用对象时spring框架为我们创建
		                活着：对象只要是在使用过程中就一直活着。
		                死亡：当对象长时间不用，且没有别的对象引用时，由Java的垃圾回收器回收
	                
	     <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"
	          scope="prototype" init-method="init" destroy-method="destroy"></bean>
	     
 */

/*								Spring中的依赖注入				--JDK的动态代理是不支持类注入的，只支持接口注入
		
		
		1.依赖注入：
			Dependency Injection
			
		2.IOC的作用：
			降低程序间的耦合（依赖关系）
			
		3.依赖关系的管理：
			以后都交给spring来维护
			--在当前类需要用到其他类的对象，由spring为我们提供，我们只需要在配置文件中说明
			
		4.依赖关系的维护：
			就称之为依赖注入。							--JDK的动态代理是不支持类注入的，只支持接口注入
			
			
		5.依赖注入--对象数据：
			 注入数据：					有三类
					1.基本类型和String
					2.其他bean类型（在配置文件中或者注解配置过的bean）
					3.复杂类型/集合类型
			 注入数据的方式：				有三种
					1.使用构造函数提供
 					2.使用set方法提供
					3.使用注解提供（明天的内容）
 
	 		1.构造函数注入：							(无法灵活按需传入数据，一般不用)
	 			前提：		使用该bean 构造方法，配置信息需与构造方法对应
			        使用的标签:	<constructor-arg>标签
			        标签出现的位置：bean标签的内部
			            
					例：
						<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
					        <constructor-arg name="name" value="泰斯特"></constructor-arg>
					        <constructor-arg name="age" value="18"></constructor-arg>
					        <constructor-arg name="birthday" ref="now"></constructor-arg>
					    </bean>
					
					    <!-- 配置一个日期对象 -->
					    <bean id="now" class="java.util.Date"></bean>
				    	
					    type：用于指定要注入的数据的数据类型，该数据类型也是构造函数中某个或某些参数的类型
			            index：用于指定要注入的数据给构造函数中指定索引位置的参数赋值。索引的位置是从0开始
			            name：用于指定给构造函数中指定名称的参数赋值                                        常用的
			            =============以上三个用于指定给构造函数中哪个参数赋值===============================
			            value：用于提供基本类型和String类型的具体数据
			            ref：引用其他id的<bean>标签对象。它指的是 在spring的Ioc核心容器中出现过的bean对象
				            
				        优势：
				            在获取bean对象时，注入数据是必须的操作，否则对象无法创建成功。
				        弊端：
				            改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。
			            
	  		2.set方法注入(更常用的方式)
	  			前提：			该bean所有成员变量 有对应set方法，
			        涉及的标签：		<property>
			        出现的位置：		bean标签的内部
			            
					例：
						<bean id="user" class="bean.User">
							<property name="name" value="张三"></property>
							<property name="age" value="14"></property>
					        <property name="birth" ref="now"></property>	//可以不注入该数据
					    </bean>
		    			
		    			name：用于指定注入时所调用的  set方法名称(方法名去除‘set’,变量名,区分大小写)
			            value：用于提供基本类型和String类型的 具体数据
			            ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象
				            
				        优势：
				            创建对象时没有明确的限制，可以直接使用默认构造函数。可以自由注入需要数据
				        弊端：
				            如果有某个成员必须有值，配置中不注入该数据，则获取对象时set方法没有执行，该成员没有值。
			
			2.set方法注入复杂类型的成员/集合类型的成员
			        注入List 结构集合的标签：		<list> <array> <set>
			        注入Map 结构集合的标签:		<map>  <props>
			        出现的位置：					<property>标签的内部
			        结构相同，标签可以互换
			        
					例：
						<bean id="user" class="bean.User">
							<property name="friends">
								<array>
									<value>李四</value>
									<value>王五</value>
									<value>赵六</value>
								</array>
							</property>
							<property name="grade">
								<map>
									<entry key="语文" value="95"></entry>
									<entry key="数学" value="100"></entry>
									<entry key="英语" value="88"></entry>
								</map>
							</property>
						</bean>
						
						value：具体数据。
 */





/*								2.IOC (基于注解/XML)				(--见: Web19_Spring_IOC_AnnotationProject)
	0.曾经XML的配置：
		<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"
	 		scope=""  init-method="" destroy-method="">
			<property name=""  value="" | ref=""></property>
		</bean>


	1.创建工程：
		1.创建配置文件bean2.xml,
				其中约束在：
				浏览器——> spring document——> 搜索:'xmlns:context'
			
				例：	<?xml version="1.0" encoding="UTF-8"?>
					<beans xmlns="http://www.springframework.org/schema/beans"
					    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					    xmlns:context="http://www.springframework.org/schema/context"	//开启注解IOC的名称空间
					    xsi:schemaLocation="http://www.springframework.org/schema/beans
					        http://www.springframework.org/schema/beans/spring-beans.xsd
					        http://www.springframework.org/schema/context
					        http://www.springframework.org/schema/context/spring-context.xsd">
					</beans>
				
		2.在bean2.xml中告知创建容器时扫描的包，<beans>中添加：
			<context:component-scan base-package="service"></context:component-scan>
			
		3.在需要spring管理的类上添加注解,	如：	@Component("user") @Service("service") 
											@Controller("controller") @Repository("dao")
		
		
	-----------------------------------注解释义----------------------------------------------------------------
	
	1.注入对象的 注解:
		他们的作用就和在XML配置文件中编写一个<bean>标签实现的功能是一样的
		1.@Component:
			作用：用于把当前类对象存入spring容器中
			属性：
				value：用于指定bean的id。当我们不写时，它的默认值是 首字母小写的当前类名。
			例：	
				@Component(value="service")
				public class AccountServiceImpl implements AccountService {
				
		2.@Controller：一般用在表现层
		3.@Service：一般用在业务层
		4.@Repository：一般用在持久层
		
		以上三个注解他们的作用和属性与Component是一模一样。
		他们三个是spring框架为我们提供明确的三层使用的注解，使我们的三层对象更加清晰	

	2.注入数据的 注解：
		与 xml配置文件 <bean>中<property> 作用一样
		
		1.@Autowired:
			作用：自动按类型注入。只要容器中有唯一的一个bean类型对象  和要注入的变量类型匹配，就可以注入成功;
				如果ioc容器中没有任何bean的类型和要注入的变量类型匹配，则报错;
				如果Ioc容器中有多个类型匹配时：再用要注入的变量名 与 容器中已匹配的key(id)匹配，有相同的注入成功，无相同则报错
			出现位置：
				可以是变量上，也可以是方法上
			细节：
				在使用注解注入时，set方法就不是必须的了。
			例：
				@Autowired
				private AccountDao dao;
			
		2.@Qualifier("id"):
			作用：在按照类型注入的基础之上 按照名称注入。在给类成员注入时不能单独使用。但是在给方法参数注入时可以（稍后我们讲）
			
			value：用于指定注入bean的id。
			
			例：
				@Autowired
				@Qualifier(value="accountDao1")
				private AccountDao dao;
				
			例：(纯注解)
				//用于创建一个QueryRunner对象				     
			    @Bean(name="runner")
			    @Scope("prototype")
			    public QueryRunner createQueryRunner(@Qualifier("ds2") DataSource dataSource){
			        return new QueryRunner(dataSource);
			    }
			
			    
				//创建数据源对象
			    @Bean(name="ds2")
			    public DataSource createDataSource(){
			        try {
			            ComboPooledDataSource ds = new ComboPooledDataSource();
			            ds.setDriverClass(driver);
			            ds.setJdbcUrl(url);
			            ds.setUser(username);
			            ds.setPassword(password);
			            return ds;
			        }catch (Exception e){
			            throw new RuntimeException(e);
			        }
			    }
			
		3.@Resource("id")
			作用：直接按照bean的id注入。它可以独立使用
			
			name：用于指定bean的id。
	 		
	 		例：
	 			@Resource(name="accountDao1")
				private AccountDao dao; 			
	 	
	 	以上三个注入都只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现。
		另外，集合类型的注入只能通过XML来实现。

		4.@Value("")
			作用：用于注入基本类型和String类型的数据
	    	
	    	value：	1.用于指定数据的值。
	    			2.它可以使用spring中 SpEL 表达式。
	    			  SpEL的写法：@Value("${表达式}")	
			
			例：
				@Value(value="张三")
				private String name;
				
				
		4-1. @Value 通过spEL表达式 获得.properties中数据：(基于xml)
			
			1.需配置：
				<context:property-placeholder location="classpath:my.properties" file-encoding="UTF-8"/>
			
			2.使用 @Value:
				@Value("${name}")
				private String name;
		
		
		
	3.作用范围 的注解：
		他们的作用就和在bean标签中使用scope属性实现的功能是一样的
		1.@Scope("prototype")
	 		作用：用于指定bean的作用范围
	  		属性：
				value：指定范围的取值。常用取值：singleton(单例) prototype(多例)
			例：
				@Component(value="service")
				@Scope(value="prototype")
				public class AccountServiceImpl implements AccountService {
			
	4.生命周期 相关 了解
		他们的作用就和在bean标签中使用init-method和destroy-methode的作用是一样的
		1.@PreDestroy
			作用：用于指定销毁方法
		2.@PostConstruct
			作用：用于指定初始化方法
		
 */


/*								引入 properties文件，使用 SpEL表达式
 * 
 	文章参考:	http://www.hellojava.com/a/793.html
 	说明：	这两种用法都是用来通过注解来获取properties参数值的。
 	
 	推荐使用 <context:property-placeholder>，可以解决读取 properties的乱码问题。
 	
 	
 	
	1. <context:property-placeholder> 的方式：
		
		介绍：将配置文件加载至spring上下文中，然后通过SpEL表达式 ${} 取得值，常用于bean的属性上
		
		1.xml加入标签：
					<context:property-placeholder location="classpath:my.properties" file-encoding="UTF-8"/>
					
		2.使用：
					@Value("${name}")
					private String name;
					
				
					
										
	2. <util:properties> 的方式：
		
		介绍：以声明bean方式来使用，创建了一个bean，使用的时候通过SpEL表达式 #{} 获取bean的属性
		
		1.在xml顶部加入:
					xmlns:util="http://www.springframework.org/schema/util"
					http://www.springframework.org/schema/util 
        			http://www.springframework.org/schema/util/spring-util.xsd
		
		2.xml加入标签：
					<util:properties id="pro" location="classpath:my.properties" />
					
		3.使用:
					@Value("#{pro['name']}")
					private String name;
					
 */


/*								IOC (基于纯注解)
 	
	 	1.创建一个类，作为配置类：它的作用和bean.xml是一样的。(新注解)
	 		
	 		配置类只需要满足3个条件中的一个：  1.创建容器时传入：配置类.class
	 									  2.主配置类扫描了该包、且该类上标 @Configuration (最优解)
	 									  3.主配置类标注了 @Import(配置类.class)		
	 	       例:	主配置类
		 		@Configuration			//1.
				@ComponentScan({"com.itheima","config"})		//2.
				@Import(JdbcConfig.class)			//4.
				@PropertySource("classpath:jdbcConfig.properties")		//5.
				public class SpringConfiguration {
					@Bean(name="runner")			//3.
				    @Scope("prototype")
				    public QueryRunner createQueryRunner(@Qualifier("ds2") DataSource dataSource){
				        return new QueryRunner(dataSource);
				    }
			    }
			    
			1.Configuration
				作用：   指定当前类是一个配置类
				细节：   当配置类.class作为AnnotationConfigApplicationContext对象的传入参数时，该注解可以不写。
						
			2.PropertySource
				作用：		指定properties文件的位置。配置类注解 可使用spEL表达式获取数据，如：@Value("${jdbc.driver}")
	 			value 属性：	指定文件的名称和路径。
							  关键字：classpath，表示类路径下
				
			3.Value
				作用：	为基本类型、String类型的字段注入数据。可以配合 @PropertySource 注入properties文件中的数据。
					
			4.Bean
				作用：		把当前方法的返回值作为bean对象存入spring的ioc容器中
 				name 属性：   指定bean的id。当不写时，默认值是当前方法的名称
				细节：		使用注解配置方法时，若方法需传入参数，spring框架会去容器中查找有没有可用的bean对象。
					     查找的方式和Autowired注解方式是一样的
				
			5.ComponentScan
				作用： 	  该注解指定spring在创建容器时要扫描的包
	 			value 属性：   它和basePackages的作用是一样的，都是用于指定创建容器时要扫描的包。
				———— 我们使用此注解就等同于在xml中配置了:
					<context:component-scan base-package="com.itheima"></context:component-scan>
			
			6.Import
				作用： 	  导入其他配置类.class
				value 属性：   用于指定其他配置类的字节码。
						当我们使用Import的注解之后，有Import注解的类就父配置类，而导入的都是子配置类
				
							  
							  
			例：配置类(以下四个注解 代替了 注入外部Bean的XML配置：@Configuration @PropertySource @Value @Bean)
			
				@Configuration
				@PropertySource(classpath="jdbc.properties")	//结合@Value 注入properties 文件数据
				public class JdbcConfig {

				    @Value("${jdbc.driver}")
				    private String driver;
				
				    @Value("${jdbc.url}")
				    private String url;
				
				    @Value("${jdbc.username}")
				    private String username;
				
				    @Value("${jdbc.password}")
				    private String password;
				
				    
					//用于创建一个QueryRunner对象				     
				    @Bean(name="runner")
				    @Scope("prototype")
				    public QueryRunner createQueryRunner(@Qualifier("ds2") DataSource dataSource){
				        return new QueryRunner(dataSource);
				    }
				
				    
					//创建数据源对象
				    @Bean(name="ds2")
				    public DataSource createDataSource(){
				        try {
				            ComboPooledDataSource ds = new ComboPooledDataSource();
				            ds.setDriverClass(driver);
				            ds.setJdbcUrl(url);
				            ds.setUser(username);
				            ds.setPassword(password);
				            return ds;
				        }catch (Exception e){
				            throw new RuntimeException(e);
				        }
				    }
				
				    @Bean(name="ds1")
				    public DataSource createDataSource1(){
				        try {
				            ComboPooledDataSource ds = new ComboPooledDataSource();
				            ds.setDriverClass(driver);
				            ds.setJdbcUrl("jdbc:mysql://localhost:3306/eesy02");
				            ds.setUser(username);
				            ds.setPassword(password);
				            return ds;
				        }catch (Exception e){
				            throw new RuntimeException(e);
				        }
				    }
				}
				
 		2.测试类
 			1.创建IOC容器对象,传入 配置类.class
 				ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfiguration.class,JdbcConfig.class);
			2.通过容器获取bean模块
			
 */

/*								Spring整合junit
		
 	使用Spring整合的Junit单元测试：@Test方法执行时 自动创建IOC容器
 	
	Spring整合junit的配置：
		1、导入spring整合junit的jar(坐标)：spring-test包
		
		2、使用Junit提供的一个注解把原有的main方法替换了，替换成spring提供的
				@Runwith(替换为Runner类.class)
				@RunWith(SpringJUnit4ClassRunner.class)
				
 		3、告知spring的运行器，spring和ioc创建是基于xml还是注解的，并且说明位置
 				@ContextConfiguration
					locations 属性：指定xml文件的位置，加上classpath关键字，表示在类路径下
					classes 属性：指定注解类所在地位置

		当我们使用spring 5.x版本的时候，要求junit的jar必须是4.12及以上
		
		例：	@RunWith(SpringJUnit4ClassRunner.class)
			@ContextConfiguration(classes = SpringConfiguration.class)
			//@ContextConfiguration(locations="classpath:applicationContext.xml")
			public class AccountServiceTest {
			
			    @Autowired
			    private IAccountService as = null;
			
			
			    @Test
			    public void testFindAll() {
			        //3.执行方法
			        List<Account> accounts = as.findAllAccount();
			        for(Account account : accounts){
			            System.out.println(account);
			        }
			    }
			    
			}
 */



/*								3.动态代理
	1.动态代理
		对java 类进行增强的方式。(其他两种增强方式：继承、包装类)

	2.动态代理特点
		字节码随用随创建，随用随加载。
		它与静态代理的区别也在于此。因为静态代理是字节码一上来就创建好，并完成加载。
		装饰者模式就是静态代理的一种体现。

	3.两种方式：
		1.接口的动态代理
			提供者：JDK 官方的Proxy 类。
			要求：    被代理类最少实现一个接口。
			-----------------------------------
			JDK 动态代理
 	
		 	使用前提：首先存在一个被代理类(Cla)对象，该类实现了至少一个接口(Inter)
		 	
		 	使用：	1.创建 被代理类对象 cla;
		 			2.Proxy.newProxyInstance(...)创建 代理对象, Handler方法中，必须通过cla对象调用 method.invoke(cla,args);
		 			3.代理对象执行方法即可。
		 	
		 	效果：代理对象的所有方法都得到增强。
		 	-----------------------------------
	
		2.子类的动态代理
			提供者：第三方CGLib，如果报asmxxxx 异常，需要导入asm.jar。
			要求：   被代理类不能用final 修饰的类(最终类)。
			
		
		例：
         *  1.接口的动态代理
	         *  特点： 字节码随用随创建，随用随加载
	         *  作用： 不修改源码的基础上对方法增强
	         *  分类：
	         *        基于接口的动态代理
	         *        基于子类的动态代理
	         *        
	         *  基于接口的动态代理：
	         *        涉及的类：Proxy
	         *        提供者：JDK官方
	         *        
	         *  如何创建代理对象：
	         *        使用Proxy类中的newProxyInstance方法
	         *        
	         *  创建代理对象的要求：
	         *        被代理类最少实现一个接口，如果没有则不能使用
	         *        
	         *  newProxyInstance方法的参数： newProxyInstance(Classloader loader,Class[] c,InvocationHandler handler)
	         *        ClassLoader：类加载器
	         *            它是用于加载代理对象字节码的。和被代理对象使用相同的类加载器。固定写法。
	         *        Class[]：字节码数组
	         *            它是用于让代理对象和被代理对象有相同方法。固定写法。
	         *        InvocationHandler：用于提供增强的代码
	         *            它是让我们写如何代理。我们一般都是写一个该接口的实现类，通常情况下都是匿名内部类，但不是必须的。
	         *            此接口的实现类都是谁用谁写。
	         *            
          
         IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                     // 作用：执行被代理对象的任何接口方法都会经过该方法
                     // 方法参数的含义
                     // @param proxy   代理对象的引用
                     // @param method  代理对象调用时，执行的方法
                     // @param args    代理对象调用时，执行方法所需的参数
                     // @return        和被代理对象方法有相同的返回值
                     // @throws Throwable
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if("test".equals(method.getName())){
                            return method.invoke(accountService,args);
                        }

                        Object rtValue = null;
                        try {
                            //1.开启事务
                            txManager.beginTransaction();
                            //2.执行操作
                            rtValue = method.invoke(accountService, args);
                            //3.提交事务
                            txManager.commit();
                            //4.返回结果
                            return rtValue;
                        } catch (Exception e) {
                            //5.回滚操作
                            txManager.rollback();
                            throw new RuntimeException(e);
                        } finally {
                            //6.释放连接
                            txManager.release();
                        }
                    }
                });
                
		例：
         *  1.子类的动态代理
         	 *  
			 *  基于子类的动态代理：
			 *        涉及的类：Enhancer
			 *        提供者：第三方cglib库
			 *      
			 *  如何创建代理对象：
			 *        使用Enhancer类中的create方法
			 *      
			 *  创建代理对象的要求：
			 *        被代理类不能是最终类
			 *      
			 *  create方法的参数：
			 *        Class：字节码
			 *            它是用于指定被代理对象的字节码。
			 *		  Callback：用于提供增强的代码
			 *            它是让我们写如何代理。我们一般都是些一个该接口的实现类，通常情况下都是匿名内部类，但不是必须的。
			 *            此接口的实现类都是谁用谁写。
			 *            我们一般写的都是该接口的子接口实现类：MethodInterceptor
			           
         Producer cglibProducer = (Producer)Enhancer.create(producer.getClass(), new MethodInterceptor() {
		             // 执行被代理对象的任何方法都会经过该方法
		             // @param proxy
		             // @param method
		             // @param args
		             //    以上三个参数和基于接口的动态代理中invoke方法的参数是一样的
		             // @param methodProxy ：当前执行方法的代理对象
		             // @return
		             // @throws Throwable
	            @Override
	            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
	                //提供增强的代码
	                Object returnValue = null;
	
	                //1.获取方法执行的参数
	                Float money = (Float)args[0];
	                //2.判断当前方法是不是销售
	                if("saleProduct".equals(method.getName())) {
	                    returnValue = method.invoke(producer, money*0.8f);
	                }
	                return returnValue;
	            }
        });
*/

/*								AOP（基于XML）					(--见: Web19_Spring_AOP)

 	1.什么是AOP
 		AOP：全称 Aspect Oriented Programming(面向切面编程)。
		含义：它就是把我们程序重复的代码抽取出来，在需要执行的时候，使用动态代理的技术，在不修改源码的基础上，对我们的已有方法进行增强。

	2.AOP 作用优势
		作用：在程序运行期间，不修改源码对已有方法进行增强。
		优势：减少重复代码
			   提高开发效率
			   维护方便
			   
	3.AOP 的实现方式
		使用动态代理技术.

	
	4.Spring 中AOP 的细节
		spring 的aop，就是通过配置的方式，实现上一章节的功能。

	5.AOP 相关术语
		Joinpoint(连接点):
			所谓连接点是指那些被拦截到的点。在spring 中,连接点指方法(因为spring 只支持方法类型的连接点)。
		Pointcut(切入点):
			所谓切入点是定义我们要对哪些 Joinpoint 进行拦截。spring 中,切入点指被增强的方法.
		
			--所有的切入点都是连接点，但不是所有的连接点都是切入点。
		
		Advice(通知/增强):
			所谓通知是指拦截到 Joinpoint 之后所要做的事情就是通知。
			通知的类型(根据通知处于method.invoke()的前后位置分为五种类型)：前置通知,后置通知,异常通知,最终通知,环绕通知。
			
			增强后的方法：
						try{
							前置通知;
							原方法();
							后置通知;
						} catch (Exception e) {
							异常通知;
						} finally {
							最终通知;
						}
						  --后置通知和异常通知只能执行一个
							
		Introduction(引介):
			引介是一种特殊的通知，在不修改类代码的前提下, Introduction 可以在运行期为类动态地添加一些方法或 Field。
		Target(目标对象):
			被代理对象。
		Weaving(织入):
			是指把增强应用到目标对象来创建新的代理对象的过程。spring采用动态代理织入，而 AspectJ 采用编译期织入和类装载期织入。
		Proxy（代理）:
			代理对象。
		Aspect(切面):
			是切入点和通知（引介）的结合。

		
	6.学习 spring 中的 AOP 要明确的事：
		a、开发阶段（我们做的）
			1.编写核心业务代码（开发主线）：大部分程序员来做，要求熟悉业务需求。
			2.把公用代码抽取出来，制作成通知。（开发阶段最后再做）：AOP 编程人员来做。
			3.在配置文件中，声明切入点与通知间的关系，即切面。：AOP 编程人员来做。
			
		b、运行阶段（Spring 框架完成的）
			1.Spring 框架监控切入点方法的执行。一旦监控到切入点方法被运行，使用代理机制，动态创建目标对象的代理对象，根据通知
			  类别，在代理对象的对应位置，将通知对应的功能织入，完成完整的代码逻辑运行。

	--------------------------------------		重点			-----------------------------------------------
	7.使用AOP的步骤：
	
		1.添加依赖：	1.spring-context：			spring IOC
					2.aspectjweaver：			spring AOP 
				              
				              
		2.创建spring配置文件、导入约束:
				导入aop的约束：（浏览器 —— Spring Framework —— core —— 查找"xmlns:aop"）
				
				例：<?xml version="1.0" encoding="UTF-8"?>
					<beans xmlns="http://www.springframework.org/schema/beans"
						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						xmlns:aop="http://www.springframework.org/schema/aop"
						xsi:schemaLocation="http://www.springframework.org/schema/beans
							http://www.springframework.org/schema/beans/spring-beans.xsd
							http://www.springframework.org/schema/aop
							http://www.springframework.org/schema/aop/spring-aop.xsd">
					</beans>
		
		3.配置XML,添加bean(IOC):
				例：     //配置 service 
					<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
						<property name="accountDao" ref="accountDao"></property>
					</bean>
					  //配置 dao
					<bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">
						<property name="dbAssit" ref="dbAssit"></property>
					</bean>

		4.通知类：抽取公共代码制作成通知类。通知类中 保存着增强的方法，将通知类 加入到IOC。
				(--通知类，见：/IOC配置文件/Logger.java)
				
		5.配置XML、 配置AOP切面(AOP):		
				(用该配置方式，AOP依赖包版本 必须为1.8.7，否则报错)
			
				例： <aop:config>	//1.开始配置AOP
				        <aop:aspect id="logAdvice" ref="logger">	//1配置切面、2该切面的id、3使用哪个bean 作为通知类
				            //1通知类型：增强在什么位置、2使用该通知bean 的什么方法、3给谁增强 
				            <aop:before method="printLog" pointcut="execution(* com.itheima.service.impl.*.*(..))"></aop:before>
				        </aop:aspect>
				    </aop:config>
				    
				(基于XML的AOP配置步骤)
		        0、把通知Bean用IOC来管理(添加配置)
		        1、配置AOP:	使用<aop:config> 表明
		        2、配置切面:
				               <aop:aspect>:			   表示切面
							                id 属性：给切面提供一个唯一标识
							                ref属性：指定 通知类bean 的Id。
		        3、配置通知的类型:在<aop:aspect> 内部,使用对应标签来配置:
				               <aop:before>：	  		   表示配置前置通知:在切入点方法执行前执行。
				               <aop:after-returning>：	   表示配置后置通知:在切入点方法执行后执行。
				               <aop:after-throwing>：	   表示配置异常通知:在切入点方法产生异常后执行。
				               <aop:after>：	   			   表示配置最终通知:无论切入点方法是否正常执行 它都会最后执行。
				               <aop:around>:			   表示配置环绕通知:该method 方法，将会代替切入点方法。
						                    method属性： 	   指定 通知类bean 中哪个方法是前置通知
						                    pointcut属性： 指定切入点表达式，该表达式表示 对业务层中哪些方法增强
				                    
		
								1.切入点表达式的写法：execution(表达式)
							                表达式：
											(被增强方法)访问修饰符  (被增强方法)返回值  (被增强方法)包名.包名.包名...类名.方法名(参数列表)
							                标准的表达式写法：
											public void com.itheima.service.impl.AccountServiceImpl.saveAccount()
									1.访问修饰符：可以省略
											void com.itheima.service.impl.AccountServiceImpl.saveAccount()
									2.返回值：可以使用通配符，表示任意返回值
											* com.itheima.service.impl.AccountServiceImpl.saveAccount()
									3.包名：可以使用通配符，表示任意包。但是有几级包，就需要写几个*.
											* *.*.*.*.AccountServiceImpl.saveAccount())
							                    包名可以使用..表示当前包及其子包
													*..AccountServiceImpl.saveAccount()
									4.类名和方法名：都可以使用*.*()来实现通配
													* com.itheima.service.impl.*.*()
								                参数列表：
	 										1.可以直接写数据类型：
												基本类型直接写名称          	 int
												引用类型写包名.类名的方式   java.lang.String
											2.可以使用：通配符*	表示任意类型，但是必须有参数
											3.可以使用：..		表示有无参数均可，有参数可以是任意类型
								                全通配写法：
								                    * *..*.*(..)
							
							                实际开发中切入点表达式的通常写法：
							                    切到业务层实现类下的所有方法
							          execution(* com.itheima.service.impl.*.*(..))
							                       
				例： <aop:config>
				       	 // 配置切入点表达式 
				        <aop:pointcut id="pt1" expression="execution(* com.itheima.service.impl.*.*(..))"></aop:pointcut>
				        <aop:aspect id="logAdvice" ref="logger">
				            <aop:before method="beforePrintLog" pointcut-ref="pt1" ></aop:before>
				            <aop:after-returning method="afterReturningPrintLog" pointcut-ref="pt1"></aop:after-returning>
				            <aop:after-throwing method="afterThrowingPrintLog" pointcut-ref="pt1"></aop:after-throwing>
				            <aop:after method="afterPrintLog" pointcut-ref="pt1"></aop:after>
				            
							// 配置环绕通知 详细的注释请看Logger类中
				            // <aop:around method="aroundPringLog" pointcut-ref="pt1"></aop:around>	    
				        </aop:aspect>
				    </aop:config>
				    
				4.配置切入点表达式：1.此标签写在aop:aspect内部，只能当前切面使用,
								  2.写在aop:aspect 外部，此时所有切面可用
					使用 <aop:pointcut>
						id属性：用于指定表达式的唯一标识。
						expression属性：用于指定表达式内容。
						
				5.配置环绕通知(<aop:aspect>中)：1.环绕通知，是spring框架为我们提供的一种，可以在代码中手动控制增强方法何时执行的方式。
							  				  2.环绕通知配置后，原方法执行，实际上执行的是通知类中，关联环绕通知的方法。
					使用 <aop:around method="aroundPringLog" pointcut-ref="pt1"></aop:around>
					
					通知类的环绕通知方法：Spring框架为我们提供了一个接口：ProceedingJoinPoint。该接口有一个方法proceed()，
									      此方法就相当于明确调用切入点方法。
									     该接口可以作为环绕通知的方法参数，在程序执行时，spring框架会为我们提供该接口的实现类供我们使用。
				              

		6.测试类中进行测试:
			1.获取IOC容器
				ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
			2.获取bean对象
				ServiceIn service = (ServiceIn) context.getBean("service");
			3.调用被增强的方法
			 	service.all();
 */

/*								做homework 时报错				(--见: Web19_Spring_AOP_Homework)
 	1.报错：
 			Pointcut is not well-formed: expecting '(' at character position 0
 			切入点表达式不正确
 			
	 	2.原代码：
	 			<!-- 配置AOP -->
				<aop:config>
					<aop:pointcut id="pointcut1" expression="* wxt.service.impl.*.*(..)"></aop:pointcut>
					<aop:aspect id="shiwu" ref="shiwuAdvice">
						<aop:before method="startShiwu" pointcut="pointcut1"></aop:before>
						<aop:after-returning method="commitShiwu" pointcut="pointcut1"></aop:after-returning>
						<aop:after-throwing method="exceptionShiwu" pointcut="pointcut1"></aop:after-throwing>
						<aop:after method="finallyShiwu" pointcut="pointcut1"></aop:after>
					</aop:aspect>
				</aop:config>
				
		3.正确代码：
				<!-- 配置AOP -->
				<aop:config>
					<aop:pointcut id="pointcut1" expression="execution(* wxt.service.impl.*.*(..))"></aop:pointcut>
					<aop:aspect id="shiwu" ref="shiwuAdvice">
						<aop:before method="startShiwu" pointcut-ref="pointcut1"></aop:before>
						<aop:after-returning method="commitShiwu" pointcut-ref="pointcut1"></aop:after-returning>
						<aop:after-throwing method="exceptionShiwu" pointcut-ref="pointcut1"></aop:after-throwing>
						<aop:after method="finallyShiwu" pointcut-ref="pointcut1"></aop:after>
					</aop:aspect>
				</aop:config>
	
		4.总结：
				1.注意切入点表达式的格式：expression="execution()"，不要忘记写execution()
				2.通知类型<aop:before>标签在引用切入点表达式时，应该使用： pointcut-ref，不要忘记写ref
				
	2.报错：
			Can't call commit when autocommit=true
			当自动提交是开启的，无法提交
			
		1.总结：使用了,commit()或者rollback(),这两个都需要一个先决条件，就是mysql的自动提交功能要关闭：
																		connection.setAutoCommit(false);
		
 */

/*								AOP（基于注解/XML）
 	1.AOP 基于注解的步骤：
 	
 		1.添加依赖：	1.spring-context：			spring IOC
					2.aspectjweaver：			spring AOP
	
				        
 		2.spring配置文件:
				1.导入aop的约束：（浏览器 —— Spring Framework —— core —— 查找"xmlns:aop"）
 
 				2.配置application.xml，开启注解AOP的支持：
 				
	 				例：	<!-- 配置spring开启注解AOP的支持 -->
					    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
				 
				3.若对 Controller层进行 AOP增强，在springmvc.xml中添加：
					
	 				例：	<!-- 通知spring使用cglib而不是jdk的来生成代理方法 AOP	可以拦截到Controller -->
						<aop:aspectj-autoproxy proxy-target-class="true"/>
					
				    
		3.在通知类中添加注解：
				1. @Aspect ：切面类
				      表示该类是一个 切面类，同<aop:aspect>
					
					例：	@Component("logger")
						@Aspect//表示当前类是一个切面类
						public class Logger {
						
				2. @Pointcut ：切入点表达式
				      方法上定义切入点表达式，同<aop:pointcut>
				      
				      格式:		@Pointcut("execution(* com.itheima.service.impl.*.*(..))")
				      		private void pt1(){}
				      		
				      调用格式：	@Before("pt1()")
				      		public  void beforePrintLog(JoinPoint jp){}
				
					例：	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
						@Pointcut("execution(* com.itheima.service.impl.*.*(..))")
						private void pt1(){}
						
						
						
				1. @Before("pt1()") ：前置通知
				      表示方法是 前置通知，同<aop:before>
				
					例：	@Before("execution(* wxt.controller.*.*(..))")
    					public  void beforePrintLog(JoinPoint jp){
    					
    					
    			2. @AfterReturning("pt1()") ：后置通知
				      表示方法是 后置通知，同<aop:after-returning>
    				
    				例： @AfterReturning("execution(* wxt.controller.*.*(..))")
    					public  void afterReturningPrintLog(JoinPoint jp){
    					
    					
    			3. @AfterThrowing("pt1()") ：异常通知
				      表示方法是 异常通知，同<aop:after-throwing>
    			      
    			        例： @AfterThrowing("execution(* wxt.controller.*.*(..))")
    					public  void afterThrowingPrintLog(JoinPoint jp){
    					
    					
    			4. @After("pt1()") ：最终通知
    			      表示方法是 最终通知，同<aop:after>
    				
    				例： @After("execution(* wxt.controller.*.*(..))")
    					public  void afterPrintLog(JoinPoint jp){
    					
    					
    			5. @Around("pt1()") ：环绕通知
    			      表示方法是 环绕通知，同<aop:around>
    			      
    				例： @Around("execution(* wxt.controller.*.*(..))")
    					public Object aroundPringLog(JoinPoint jp){
    			
    			
    	4.获得 JoinPoint对象：	(org.aspectj.lang.JoinPoint包)
    			1.在方法参数上传入 JoinPoint切入点对象;
    			2.获得当前访问的类：			Class clazz = jp.getTarget().getClass();
    			3.获得当前访问的方法名:		String methodName = jp.getSignature().getName();
    			
    			
    	5.测试类中进行测试:
			1.获取IOC容器
				ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
			2.获取bean对象
				ServiceIn service = (ServiceIn) context.getBean("service");
			3.调用被增强的方法
			 	service.all();
			 	
		(使用注解AOP，在执行增强方法时，通知的执行顺序会有问题(执行顺序为：前置、最终、后置/异常)，因此注解AOP 时尽量使用环绕通知)
 */

/*								AOP（纯注解）
	1.不使用spring配置文件
	
	2.在配置类上添加注解：@EnableAspectJAutoProxy 即可

		@Configuration
		@ComponentScan(basePackages="com.itheima")
		@EnableAspectJAutoProxy
		public class SpringConfiguration {
		}
 */



/*								4.Spring中的 JdbcTemplate		(--见: Web19_Spring_JdbcTemplate)
	1.JdbcTemplate 概述
		它是 spring 框架中提供的一个对象，是对原始 Jdbc API 对象的简单封装。
		
		导包：	spring-jdbc-5.0.2.RELEASE.jar	(JdbcTemplate)
		 		spring-tx-5.0.2.RELEASE.jar	（DriverManagerDataSource连接池）
		
	2.spring 框架的其他操作模板类：
		操作关系型数据的：	JdbcTemplate
						HibernateTemplate
		操作 nosql 数据库的：RedisTemplate
		操作消息队列的：	JmsTemplate


	2.5.JdbcTemplate 方法
		1.添加方法：
			jt.update("insert into account(name,money)values(?,?)","eee",3333f);
					
		2.更新方法：
			jt.update("update account set name=?,money=? where id=?","test",4567,7);
			
		3.删除方法：
			jt.update("delete from account where id=?",8);
			
		4.查询：
			(query(String sql, RowMapper<T> rowMapper, Object... args) --List<T>) jdk1.5后适用
			(query(String sql, Object[] args, RowMapper<T> rowMapper) --List<T>) 所有版本适用
			
			1.使用 RowMapper 的实现类（常用）
			 String sql = "select * from account where money > ?";
			 List<Account> accounts = jt.query(sql, new BeanPropertyRowMapper<Account>(Account.class), 1000f);	
			 
			 
			2.自己实现 RowMapper 接口
			 List<Account> accounts = jt.query(sql,new AccountRowMapper(),1000f);	
			
			
		5.查询返回一行一列（使用聚合函数，但不加group by子句）
			String sql = "select count(*) from account where money > ?";
			Long count = jt.queryForObject(sql, Long.class, 1000f);
	   				
	   					
	
	3.JdbcTemplate 的使用：
		1.创建工程，添加依赖包：	spring-context(IOC)
								spring-jdbc-5.0.2.RELEASE.jar(JdbcTemplate)
								spring-tx-5.0.2.RELEASE.jar(spring事务)
						
		2.配置JdbcTemplate：
				<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
					<property name="dataSource" ref="dataSource"></property>
				</bean>
						
		3.配置DataSource：		(spring 内置DriverManagerDataSource)
				<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
					<property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
					<property name="url" value="jdbc:mysql://localhost:3306/19spring?serverTimezone=UTC"></property>
					<property name="username" value="root"></property>
					<property name="password" value="root"></property>
				</bean>
				
		4.使用：	
	        	JdbcTemplate jt = new JdbcTemplate(dataSource);		//传入DataSource
	        	String sql = "select * from user";
				List<User> list = jt.query(sql, new BeanPropertyRowMapper<User>(User.class));	//执行sql

				
 */

/*								spring中 控制事务(基于XML)			(--见：G:\JD\JavaWeb工程文件\day04_eesy_05tx_xml）
 * 
 	1.Spring 事务控制
 		1.JavaEE 体系进行分层开发，事务处理位于业务层。
 		2.spring 框架为我们提供了一组事务控制的接口。
 		3.该接口在 spring-tx-5.0.2.RELEASE.jar 中。


	2.Spring 中事务控制的 API
	
		1-1.事务控制	接口：
			接口：		PlatformTransactionManager
			方法：	1.获取事务状态信息：		TransactionStatus getTransaction(TransactionDefinition definition)
					2.提交事务：				void commit(TransactionStatus status)
					3.回滚事务：				void rollback(TransactionStatus status)
			
		1-2.接口 实现类：	(真正管理事务的对象)(spring-jdbc 包中)
			实现类：org.springframework.jdbc.datasource.DataSourceTransactionManager 
											(使用Spring JDBC 或iBatis 持久化数据时使用)
	
	
	
		2.事务的定义信息	接口
			接口：		TransactionDefinition
			方法：	1.获取事务对象名称：		String getName()
					2.获取事务隔离级别：		int getIsolationLevel()
					3.获取事务传播行为：		int getPropagationBehavior()  (传播行为：什么情况下必须有事务--增删改，
																					什么情况下可有可没有--查询)
					4.获取事务超过时间：		int getTimeout()			(超过时间：提交和回滚多长时间过期)
					5.获取事务是否只读：		boolean isReadOnly()		读写型事务：增加、删除、修改开启事务
																		只读型事务：执行查询时，也会开启事务
			取值：	1.事务的隔离级别：
								ISOLATION_DEFAULT : 默认级别，归属下面一种
								ISOLATION_READ_UNCOMMITTED : 可以读取未提交数据
								ISOLATION_READ_COMMITTED : 只能读取已提交数据。解决脏读(Oracle默认级别)
								ISOLATION_REPEATABLE_READ : 只能读取开启事务那一刻的数据，其他事务修改数据，结果不变。解决不可重复读。(MySQL默认级别)
								ISOLATION_SERIALIZABLE : 只能读取开启事务那一刻的数据，不能读取其他事务提交新增的数据。解决幻读问题。
					2.事务的传播行为：
								REQUIRED: 如果当前没有事务，就新建一个事务；如果已经存在一个事务中，加入到这个事务中.  //常用,默认
								SUPPORTS: 支持当前事务，如果当前没有事务，就以非事务方式执行（没有事务）
								MANDATORY：使用当前的事务，如果当前没有事务，就抛出异常
								REQUERS_NEW: 新建事务，如果当前在事务中，把当前事务挂起。
								NOT_SUPPORTED: 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
								NEVER: 以非事务方式运行，如果当前存在事务，抛出异常
								NESTED: 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行 REQUIRED 类似的操作。
					3.超时时间：
								默认值-1，表示 没有超时限制。若有限制，以秒为单位进行设置。
					4.是否是只读事务：
								建议查询时设置为只读。
								
		3.事务具体的运行状态	接口：
			接口：		TransactionStatus
			方法：	1.刷新事务：				void flush()
					2.获取是否存在存储点：		boolean hasSavepoint()
					3.获取事务是否完成：		boolean isCompleted()
					4.获取事务是否为新的事务：	boolean isNewTransaction()
					5.获取事务是否回滚：		boolean isRollbackOnly()
					6.设置事务回滚：			void setRollbackOnly()
		
		
		
		
		
	3. spring中 事务控制 步骤：(基于XML 声明式 事务控制)		（见：G:\JD\JavaWeb工程文件\day04_eesy_05tx_xml）
	
		0.添加依赖：	1.spring-context：			spring IOC
					2.aspectjweaver：			spring AOP
					3.spring-tx：				spring 事务控制			(新增)
					4.spring-jdbc：				spring JdbcTemplate		(需要)
					5.spring-test：				spring 整合Junit
					6.mysql-connector-java:		MySQL驱动
					7.junit：					Test测试
	
		1.配置bean.xml：
		
			1.配置 事务管理器
			2.配置 事务的通知
				1.此时我们需要导入事务的约束 tx名称空间和约束，同时也需要aop的 约束（主页 —— Data Access —— 搜索：xmlns:tx）
		        2.使用tx:advice标签配置事务通知
		        3.配置事务的属性：在事务的通知tx:advice标签的内部
	        3.配置 AOP
	        	1.配置 通用切入点表达式
	        	2.建立 事务通知和切入点表达式的对应关系
			
				例：    
		         //1. 配置事务管理器 
			    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
			        <property name="dataSource" ref="dataSource"></property>
			    </bean>
			
			     //2. 配置事务的通知: 1.约束	 2.<tx:advice>配置事务通知
			    <tx:advice id="txAdvice" transaction-manager="transactionManager">
			        //3. 配置事务的属性     	
			        <tx:attributes>
			            <tx:method name="*" propagation="REQUIRED" read-only="false"/>
			            <tx:method name="find*" propagation="SUPPORTS" read-only="true"></tx:method>
			        </tx:attributes>
			    </tx:advice>
		    
				   属性： 	id：给事务通知起一个唯一标识
				        transaction-manager：给事务通知 提供一个 事务管理器bean 的id
				        
				        <tx:method>标签：指定 不同切入点方法名 所对应的 事务属性
				        	name: 切入的方法名，* 表示全部方法，find* 表示 find开头 的方法。
					        isolation：用于指定事务的隔离级别。默认值是DEFAULT，表示使用数据库的默认隔离级别。
			                propagation：用于指定事务的传播行为。默认值是REQUIRED，表示一定会有事务，增删改的选择。查询方法可以选择SUPPORTS。
			                read-only：用于指定事务是否只读。只有查询方法才能设置为true。默认值是false，表示读写。
			                	
			                	(只读事务：设置只读为true，是提示数据库驱动程序和系统，该事务不包含更改数据的操作.那么JDBC驱动程序和数据库
			                	就有可能对该事务进行一些特定的优化)
			                	
			                timeout：用于指定事务的超时时间，默认值是-1，表示永不超时。如果指定了数值，以秒为单位。
			                rollback-for：用于指定一个异常，当产生该异常时，事务回滚，产生其他异常时，事务不回滚。没有默认值。不写表示任何异常都回滚。
			                no-rollback-for：用于指定一个异常，当产生该异常时，事务不回滚，产生其他异常时事务回滚。没有默认值。不写表示任何异常都回滚。
		
			     // 3.配置aop
			    <aop:config>
			        //1. 配置 通用切入点表达式
			        <aop:pointcut id="pt1" expression="execution(* com.itheima.service.impl.*.*(..))"></aop:pointcut>
			        //2. 建立切入点表达式和事务通知的对应关系 
			        <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"></aop:advisor>
			    </aop:config>
		
		2.测试即可
					
				
 */

/*								spring中 控制事务(基于注解/XML)	(--见：G:\JD\JavaWeb工程文件\day04_eesy_06tx_anno）
	1.spring中 事务控制 步骤：			
		
		（		spring中 基于注解 的 事务控制配置 要点：				   ）
		（		        1、配置事务管理器							   ）
		（		        2、开启spring对注解事务的支持				   ）
		（		        3、在需要事务支持的地方使用@Transactional注解 ）
		
		0.添加依赖：	1.spring-context：			spring IOC
					2.aspectjweaver：			spring AOP
					3.spring-tx：				spring 事务控制
					4.spring-jdbc：				spring 事务管理器
					5.spring-test：				spring 整合Junit
					6.mysql-connector-java:		MySQL驱动
					7.junit：					Test测试
		
		1.配置bean.xml：
			1.导入约束：在导入xmlns:tx 约束后，增加context 的约束。
			   
			    例：	<?xml version="1.0" encoding="UTF-8"?>
					<beans xmlns="http://www.springframework.org/schema/beans"
					       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
					       xmlns:aop="http://www.springframework.org/schema/aop"
					       xmlns:tx="http://www.springframework.org/schema/tx"
					       xmlns:context="http://www.springframework.org/schema/context"
					       xsi:schemaLocation="
					        http://www.springframework.org/schema/beans
					        http://www.springframework.org/schema/beans/spring-beans.xsd
					        http://www.springframework.org/schema/tx
					        http://www.springframework.org/schema/tx/spring-tx.xsd
					        http://www.springframework.org/schema/aop
					        http://www.springframework.org/schema/aop/spring-aop.xsd
					        http://www.springframework.org/schema/context
					        http://www.springframework.org/schema/context/spring-context.xsd">
					        
			2.将dao、service 等bean 加入注解，添加至IOC。
				注意：使用注解时，dao不可继承JdbcDaoSupport 类。
			3.配置 创建IOC容器时要扫描的包。
			4.配置外部库bean: 将JdbcTemplate 等 在bean.xml中添加声明，添加至IOC。
			5.配置事务管理器															------要点1.
			6.开启spring 对注解事务的支持。											------要点2.
			   
			    例：	 //3. 配置spring创建容器时要扫描的包
				    <context:component-scan base-package="com.itheima"></context:component-scan>
				
				  	 //4. 配置JdbcTemplate
				    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
				        <property name="dataSource" ref="dataSource"></property>
				    </bean>
				
					 //4. 配置数据源
				    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
				        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
				        <property name="url" value="jdbc:mysql://localhost:3306/eesy"></property>
				        <property name="username" value="root"></property>
				        <property name="password" value="1234"></property>
				    </bean>

					 //5. 配置事务管理器 
				    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
				        <property name="dataSource" ref="dataSource"></property>
				    </bean>
				
					 //6. 开启spring对注解事务的支持
				    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>	
			
		2.添加注解
			1.在需要事务支持的地方使用 @Transactional 注解。
		
			    例：	@Service("accountService")
					@Transactional(propagation= Propagation.SUPPORTS,readOnly=true) //只读型事务的配置
					public class AccountServiceImpl implements IAccountService{
					
					    @Autowired
					    private IAccountDao accountDao;
					
					    @Override
					    public Account findAccountById(Integer accountId) {
					        return accountDao.findAccountById(accountId);
					
					    }

					    //需要的是读写型事务配置
					    @Transactional(propagation= Propagation.REQUIRED,readOnly=false)
					    @Override
					    public void transfer(String sourceName, String targetName, Float money) {
					        ...
					    }
					}
		
		3.测试即可
		
 */

/*								spring中 控制事务(基于纯注解)		(--见：G:\JD\JavaWeb工程文件\day04_eesy_07anno_tx_withoutxml）

	1.spring中 事务控制 步骤：(基于纯注解)				
		
		0.添加依赖：	1.spring-context：			spring IOC
					2.aspectjweaver：			spring AOP
					3.spring-tx：				spring 事务控制
					4.spring-jdbc：				spring 事务管理器
					5.spring-test：				spring 整合Junit
					6.mysql-connector-java:		MySQL驱动
					7.junit：					Test测试
					
		1.创建 config包。
		2.创建 主配置类 SpringConfiguration。							：相当于bean.xml
			1.添加注解 @Configuration。								：可写可不写。
			2.添加注解 @ComponentScan("com.wxt")						：表示要扫描的包。
			3.添加注解 @Import(JdbcConfig.class,TransactionConfig)	：导入子配置类.class
			4.添加注解 @PropertySource("jdbcConfig.properties") 		：spEL表达式 的文件
			5.添加注解 @EnableTransactionManagement 					：开启注解事务 的支持
			
		3.配置外部bean.
			1.创建 子配置类 JdbcConfig。							//和数据库连接相关的 外部库bean 配置类
				2.添加方法，将JdbcTemplate 作为返回值,
						     DataSource 作为传入参数,
						     return new JdbcTemplate(ds);
				3.方法添加注解 @Bean								//1.将JdbcTemplate 加到IOC中
				
				4.添加方法，将DataSource 作为返回值,
							创建DriverManagerDataSource对象(spring内置DataSource)
							并设定对象的driver、url、
								username、password,
				5.方法添加注解 @Bean								//2.将DataSource 加到IOC中
			
			
			1.创建 子配置类 TransactionConfig						//和事务相关的 外部库bean 配置类	
				2.添加方法，将PlatformTransactionManager 作为返回值
							DataSource 作为传入参数,
							return new DataSourceTransactionManager(ds);
				3.方法添加注解 @Bean								//3.将PlatformTransactionManager 加到IOC中
			
			1.创建 配置文件 jdbcConfig.properties					//使子配置类 使用spEL表达式 调用本文件数据
			2.写入： jdbc.driver=com.mysql.cj.jdbc.Driver
					jdbc.url=jdbc:mysql://localhost:3306/19spring?serverTimezone=UTC
					jdbc.username=root
					jdbc.password=root
					
		4.将dao、service 等bean 加入注解，添加至IOC。
		5.在需要事务支持的地方使用 @Transactional 注解。
		6.测试.	
			1.测试类中，@ContextConfiguration(locations="classpath:bean.xml")
				      改为 @ContextConfiguration(classes= SpringConfiguration.class)

 */

public class spring详解 {
	
	public static void main(String[] args) {
		Object e;
		if((e=new Object())!=null) {
			
		}
		if(e!=null) {
			System.out.println("不为空");
		}
	}
}
