package wxt.test;

import org.apache.ibatis.annotations.Insert;

import wxt.bean.User;

/*							BUG排除

	1.报错信息：java.lang.ClassNotFoundException: org.mybatis.spring.SqlSessionFactoryBean
		
		原applicationContext.xml配置：
			<!-- 注入SqlSessionFactory -->
			<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactory"> 
				<property name="dataSource" ref="dataSource"></property>
			</bean>
		
		原因：	1.class中的类名应该为 SqlSessionFactoryBean
				2.未导包 mybatis-spring.jar

		解决：	1.将 SqlSessionFactory 改为 SqlSessionFactoryBean
				2.导入mybatis-spring.jar
				
	2.报错信息：Bean named 'service' is expected to be of type 'wxt.service.imp.ServiceImp' but was actually of type 'com.sun.proxy.$Proxy31'
		
		原代码：		@Autowired
					ServiceImp service;
				
		正确代码：	@Autowired
					ServiceDao service;
		
		原因：	JDK的动态代理是不支持类注入的，只支持接口注入
		
	3.报错信息：java.lang.ClassNotFoundException: org.aspectj.lang.JoinPoint
		
		原aspectjweaver.jar版本：	1.9.2
		
		现aspectjweaver.jar版本：	1.9.5
		
		原因：	aspectjweaver版本问题。版本改为1.9.5 后正常运行
				
	4.异常：执行service.add(User user)方法，无法将数据插入到数据库
		
		原dao：		@Insert("insert into user('username','birthday','sex','address') values(#{name},#{birth},#{sex},#{addr})")
					public void add(User user);
		
		正确dao:		@Insert("insert into user(username,birthday,sex,address) values(#{name},#{birth},#{sex},#{addr})")
					public void add(User user);
		
		原因:		sql语句写错
			
 */
public class BUG排除 {

}
