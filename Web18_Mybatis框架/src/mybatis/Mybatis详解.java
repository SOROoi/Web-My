package mybatis;


/*							Web18_Mybatis框架（D:\我的资料\黑马57期）

 					
						(配置：
						 1.主配置文件：	数据库信息 driver,url,username,password
										Mybatis框架扫描的dao包
						 2.编写dao接口：	注解sql	
						 3.依赖：		mybatis.jar	数据库驱动	)

						(对象：
						 1.SqlSessionFactoryBuilder:	new SSFB();		builder.build(InputsStream in)
						 2.SqlSessionFactory:			factory.openSession()			
						 3.SqlSession:					session.getMapper(Class c)
						 
							Resources.getResourceAsStream("SqlMapConfig.xml");		
						 )	
						 
						(注解开发：
						 1.sql中 #{x} 为 ognl表达式，传入参数为pojo时，x代表 pojo中字段名
						 2.返回值为pojo 时，						添加 @Results注解
						 3.返回值为pojo 且含字段为pojo，			添加 @Results注解		一对一(property为 pojo)
						 4.返回值为pojo 且含字段为List<pojo>时,	添加 @Results注解		一对多(property为 List<pojo>)
						 	)
						 
 	1.框架(framework)
 		就是软件开发中的一套解决方案，不同的框架解决的是不同的问题。
	   
	    使用框架的好处：
		框架封装了很多的细节，使开发者可以使用极简的方式实现功能。大大提高开发效率。
		
	2.三层架构
		表现层：是用于展示数据的
		业务层：是处理业务需求
		持久层：是和数据库交互的
	
	3.持久层技术解决方案：
		JDBC技术：				Connection、PreparedStatement、ResultSet
		Spring的JdbcTemplate：	Spring中对jdbc的简单封装
		Apache的DBUtils：		它和Spring的JdbcTemplate很像，也是对Jdbc的简单封装	
			
		以上这些都不是框架
			1.JDBC是规范
			2.Spring的JdbcTemplate和Apache的DBUtils都只是工具类
		
	4.mybatis概述
		1.mybatis是一个持久层框架，用java编写的。
		2.它封装了jdbc操作的很多细节，使开发者只需要关注sql语句本身，而无需关注注册驱动，创建连接等繁杂过程
		3.它使用了ORM思想实现了结果集的封装。
	
		ORM：
			Object Relational Mappging 对象关系映射
		简单的说：
			就是把数据库表和实体类及实体类的属性对应起来
			让我们可以操作实体类就实现操作数据库表。
			user			User
			id				userId
			user_name		userName
			

 */


/*							创建项目使用 Mybatis
						

	1.Maven 项目中使用 Mybatis(--见/Web18_Mybatis)
	
		一：创建 maven工程,不勾选跳过骨架(需手动创建resources目录，右键Build Path ——> Configure build path将其位置加入Source中 ——> Add Folder...)
		二：导入 mybatis依赖坐标、MySQL驱动坐标、log4j(并在resources中添加log4j.properties)、Junit坐标. (1.2)
		三：创建实体类和 dao的接口.
		四：创建 mybatis 主配置文件，配置数据库、连接池等. (3.4)
		五：创建 mybatis 映射配置文件，配置方法对应sql. (5.6)
			(若在接口中想使用注解定义sql,如下操作即可：)
			(1.修改SqlMapConfig.xml的配置，将<mappers>标签改为<mappers>
													  			<mapper class="dao.imp.UserDaoImp"/>
													  		</mappers>  )
			(2.修改接口代码,在抽象方法上添加注解: @Select("select * from user")
			(3.无需创建 映射配置文件)
		
		1.使用Maven 构建项目，添加Mybatis依赖包：	(或只需将 mybatis-x.x.x.jar 文件置于 classpath 中即可)
			则需将下面的 dependency 代码置于 pom.xml 文件中：
						<dependency>
						  <groupId>org.mybatis</groupId>
						  <artifactId>mybatis</artifactId>
						  <version>3.4.5</version>
						</dependency>
						
		2.(Maven项目)添加 MySQL 驱动：(驱动版本8.0.15一直报错：改为驱动版本8.0.16后正常运行)
			将驱动的 dependency 代码置于 pom.xml 文件中。
			
		3.创建 Mybatis 主配置文件：
			在 resources 目录下创建 SqlMapConfig.xml 文件，复制 Config的约束.xml(见/常用配置文件) 中内容即可。
			
		4.配置 Mybatis 主配置文件:
			1.SqlMapConfig.xml中添加数据库配置代码.
			2.SqlMapConfig.xml中添加 映射配置文件 的位置：<mappers>
												  		<mapper resource="dao/imp/UserDaoImp.xml"/>
												  	 </mappers>
			
		5.创建 Mybatis 映射配置文件(每个dao独立的配置文件)：
			在 resources 目录下创建 dao/imp/UserDaoImp.xml 文件，复制 Mapper的约束.xml(见/常用配置文件) 中内容即可。
		
		6.配置 dao中方法执行的sql语句(UserDaoImp.xml)
			1.配置 映射哪个文件
			2.配置 方法、返回实体类类型、执行的sql:	<select id="findAll" resultType="bean.User">
													select * from user
												</select>

	2.环境搭建的注意事项：
		         第一个：创建IUserDao.xml 和 IUserDao.java时名称是为了和我们之前的知识保持一致。
				在Mybatis中它把持久层的操作接口名称和映射文件也叫做：Mapper
				所以：IUserDao 和 IUserMapper是一样的.
				
			 第二个：在idea中创建目录，和创建包是不一样的
				包在创建时：com.itheima.dao它是三级结构
				目录在创建时：com.itheima.dao是一级目录
				
	   (必须)第三个：mybatis的映射配置文件位置 必须和dao接口的包结构相同
	   (必须)第四个：映射配置文件的mapper标签 namespace属性的取值 必须是dao接口的全限定类名(包.包.类名)
	   (必须)第五个：映射配置文件的操作配置（select），id属性的取值必须是dao接口的方法名

		当我们遵从了第三，四，五点之后，我们在开发中就无须再写dao的实现类。
		
	3.读取配置文件的方式：
		第一个：使用类加载器，它只能读取和类同一路径下的配置文件
		第二个：使用ServletContext对象的getRealPath()方法,得到一个当前应用部署的绝对路径
 */


/*						（代码）使用mybatis创建dao的代理对象
							
  	
  	1.代码如下：
	  	public static void main(String[] args) throws Exception{	  	
			//1.读取配置文件
			InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
			
			//2.创建SqlSessionFactory工厂
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();	//创建工厂mybatis使用了构建者模式：
			SqlSessionFactory factory = builder.build(in); 	//构建者模式：把对象的创建细节隐藏，使用者直接传入需求即可创建对象	
			
			//3.使用工厂生产SqlSession对象			
			SqlSession session = factory.openSession();	//生产SqlSession使用了工厂模式	：解耦(降低类之间的依赖关系，代码避免频繁修改)
			
			//4.使用SqlSession创建Dao接口的代理对象
			UserDaoImp dao =session.getMapper(UserDaoImp.class);	//使用了代理模式：不修改源码基础上对已有方法增强
			
			//5.使用代理对象执行方法
			List<User> users = dao.findAll();
			for(User user: users) {
				System.out.println(user);	
			}
			
			//6.释放资源
			session.close();
			in.close();
		}
 */


/*							映射配置文件的细节
 	-1.使用typeAliases配置别名
 		由于parameterType或resultType属性中需要写上完整的实体类路径，路径中需要包含完整的包名，导致出现许多冗余代码。
 		可以在主配置文件SqlMapConfig.xml 中使用<typeAliases>标签来简化。
 		
 		1.使用typeAlias单独配置别名：
 			<configuration>
			    <typeAliases>
			          <!--type属性：完整的实体类 包路径；
			              alias属性：实体类别名-->
			        <typeAlias alias="userinfo" type="sqlmapping.Userinfo"/>
			    </typeAliases>
			</configuration>
			
			注1.这个别名在SQL映射文件中进行使用。
			注2.在引用别名时是不区分大小写的，比如如下代码也能正确得到运行。
				parameterType="USERinfo"
				resultType="USERINFo"
				
		2.使用package批量配置别名：
			如果实体类的数量较多，则极易出现<typeAlias>配置爆炸，这种情况可以使用<package>标签来解决。
			它的原理就是扫描指定包下的类，这些类都被自动赋予了与类同名的别名，不区分大小写，别名中不包含包名。
				<configuration>
				    <typeAliases>
				        <package name="包名"/>
				    </typeAliases>
				</configuration>
			
			注3.如果在不同的包中出现相同实体类名的情况，在MyBatis解析XML配置文件时就会出现异常信息。
 
 	0.标签中属性的value：严格区分大小写
 	  #{}中的内容：传入实体类时,写JavaBean的属性名,严格区分大小写
 	  Colum 属性：写数据库表中的列名。若sql中定义了别名，则写别名
 	
 	1.根据id查询(映射配置文件)
		<select id="findById" resultType="com.itheima.domain.User" parameterType="int">
			select * from user where id = #{uid} 
		</select>
		
		id属性:			接口中的方法名
		resultType属性： 	    接口方法返回值的类型,全限定类名。
		parameterType属性：接口方法传入参数的类型,全限定类名。
 		sql中的#{}字符：	代表占位符，相当于原来jdbc部分所学的?，都是用于执行语句时替换实际的数据。 具体的数据是由#{}里面的内容决定的。
 		#{}中内容的写法： 由于数据类型是基本类型，所以此处可以随意写。
 		#{}中内容：		可以接收简单类型值或Javabean /pojo 成员变量值
 	
 	2.插入数据(映射配置文件)
 		<insert id="saveUser" parameterType="com.itheima.domain.User">
 		 insert into user(username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address}) 
 		</insert>
 		
 		ognl表达式：	0. mybatis 通过 ognl 表达式获取javabean 传入sql的数据
 					1.它是apache提供的一种表达式语言，全称是： Object Graphic Navigation Language 对象图导航语言
 		 			2.它通过对象的取值方法来获取数据。在写法上把get给省略了、直接写JavaBean属性名(区分大小写)。
						比如：我们获取用户的名称
							   类中的写法：user.getUsername();
							 OGNL表达式写法：user.username
						mybatis中为什么能直接写username,而不用user.呢：
							因为在parameterType中已经提供了属性所属的类，所以此时不需要写对象名；
							若没写parameterType对应类，则需要写user.username

	3.控制事务提交(java代码)
		在Java 代码中执行dao 方法来实现插入数据操作，需要进行session.commit();来实现事务提交
		
		如：		@After//在测试方法执行完成之后执行
			 	public void destroy() throws Exception{ 
			 		session.commit(); 
			 		//7.释放资源 
			 		session.close(); 
			 		in.close(); 
			 	}
		
	4.新增用户id的返回值(映射配置文件)
		<!-- 保存用户 -->
		<insert id="saveUser" parameterType="user">
		    <!-- 配置插入操作后，获取插入数据的id -->
		    <selectKey keyProperty="userId" keyColumn="id" resultType="int" order="AFTER">
		        select last_insert_id();
		    </selectKey>
		    insert into user(username,address,sex,birthday)values(#{userName},#{userAddress},#{userSex},#{userBirthday});
		</insert>
		
		新增用户后，同时返回当前新增用户的id值，因为id是由数据库自动增长的，所以我们就要在新增后将自动增长auto_increment的值返回。
		keyProperty属性：javabean中的属性名
		keyColumn属性：   数据库中的列名
		order属性：执行sql前(或执行sql后)执行其中语句
		
	5.更新用户(映射配置文件)
		<!-- 更新用户 -->
	    <update id="updateUser" parameterType="USER">
	        update user set username=#{userName},address=#{userAddress},sex=#{userAex},birthday=#{userBirthday} where id=#{userId}
	    </update>
	    
	6.删除用户(映射配置文件) 
		<!-- 删除用户-->
	    <delete id="deleteUser" parameterType="java.lang.Integer">
	        delete from user where id = #{uid}
	    </delete>
	    
	7.返回数据封装到JavaBean 时，JavaBean 中属性名与数据库中表的列名不相同，解决方案：
		一、在<mapper>中添加如下配置，在配置sql 时，返回类型使用resultMap 属性：
			<!-- 配置 查询结果的列名和实体类的属性名的对应关系 -->
		    <resultMap id="userMap" type="com.itheima.domain.User">	
		        <!-- 主键字段的对应 -->
		        <id property="userId" column="id"></id>
		        <!--非主键字段的对应-->
		        <result property="userName" column="username"></result>
		        <result property="userAddress" column="address"></result>
		        <result property="userSex" column="sex"></result>
		        <result property="userBirthday" column="birthday"></result>
		    </resultMap>
		    
		    <!-- 根据id查询用户 -->
		    <select id="findById" parameterType="INT" resultMap="userMap">
		        select * from user where id = #{uid}
		    </select>
		    
			其中id属性：为标识信息，可任意填写；
			type属性：查询的对应实体类(JavaBean),全限定类名
			property属性：JavaBean中属性名
			column属性：表的列名
		
		二、在sql语句中，对列名取别名，别名与JavaBean中属性名相同即可。
			
	    
	8.用户模糊查询(映射配置文件)  
		!-- 根据名称模糊查询 -->
	    <select id="findByName" parameterType="string" resultMap="userMap">
			select * from user where username like #{name}
		    <!-- select * from user where username like '%${value}%'-->
		</select>
		
		若使用#{}方式传入参数：		   则Java 测试代码中 字符串 要写为"%王%"
		若使用'%${value}%'方式传入参数：则Java 测试代码中 字符串 写为"王"
		
	9.传递Javabean /pojo 包装对象(映射配置文件、Java代码)
		    配置：	<!-- 根据queryVo的条件查询用户 -->
			    <select id="findUserByVo" parameterType="com.itheima.domain.QueryVo" resultMap="userMap">
			        select * from user where username like #{user.username}
			    </select>
		    
		    接口：	public interface IUserDao {   
		    		List<User> findByVo(QueryVo vo); 
		    	}
		    
		  包装类： public class QueryVo implements Serializable {
					private User user; 
					public User getUser() {
						return user;
					}
					public void setUser(User user) { 
					this.user = user; 
					} 
				}
		
		将Javabean 封装到QueryVo 类中，通过传入QueryVo 对象传入参数给sql。
		此时#{user.username}代表：QueryVo对象.getUser().username
	
 */


/*							mybatis数据库连接池、事务、动态sql
 
	1.mybatis中的连接池
		mybatis连接池提供了3种方式的配置：POOLED  UNPOOLED  JNDI	(MyBatis内置三个数据源factory)
				配置位置：	主配置文件SqlMapConfig.xml中的dataSource标签，type属性就是表示采用何种连接池方式。
				type属性的取值：
						(使用)POOLED	  采用传统的javax.sql.DataSource规范中的连接池，mybatis中有针对规范的实现.
						(原理)UNPOOLED 采用传统的获取连接的方式，虽然也实现Javax.sql.DataSource接口，但是并没有使用池的思想。
						(了解)JNDI	  采用服务器提供的JNDI技术实现，来获取DataSource对象，不同的服务器所能拿到DataSource是不一样。
								 	  注意：如果不是web或者maven的war工程，是不能使用的。
								 我们课程中使用的是tomcat服务器，采用连接池就是dbcp连接池。
				 
	2.mybatis中的事务
		它是通过sqlsession对象的commit方法和rollback方法实现事务的提交和回滚。
		
		2.1 自动提交事务
			mybatis中自动提交事务默认是关闭的，可以手动打开：
			SqlSession session = factory.openSession(true);

	3.动态sql
		动态sql:根据实体类传入参数的不同取值，使用不同的SQL 语句来进行查询。比如在id 如果不为空时可以根据id 查询，如果username 不为空时
			      还要加入用户名作为条件。这种情况在我们的多条件组合查询中经常会碰到。

		3.1 根据用户信息，查询用户列表
			dao接口：	List<User> findByUser(User user);

			dao映射配置:	<select id="findByUser" resultType="it.heima.User" parameterType="it.heima.User">
							select * from user where 1=1
							<if test="username!=null and username != '' ">
								and username like #{username}
							</if>
							<if test="address != null">
								and address like #{address}
							</if>
						</select>

				<if>标签:如果满足if 标签的条件，则增加标签内的sql 一起执行
				test属性：if 标签的条件，一般用来判断parameterType实体类的属性是否为空，实体类属性名区分大小写
				
		3.2 <where>标签
				<select id="findByUser" resultType="user" parameterType="user">
					<include refid="defaultSql"></include>
					<where>
						<if test="username!=null and username != '' ">
							and username like #{username}
						</if>
						<if test="address != null">
							and address like #{address}
						</if>
					</where>
				</select>

				<where>标签：包裹<if>标签,如果<if>标签内条件成立则增加 where + sql 语句，可以用来省略 where 1=1
				
		3.3 <foreach>标签
				传入多个id 查询用户信息，需要select * from users where username like '%张%' and id in (10,89,16)
				在进行范围查询时，需要将一个集合的值，作为参数动态添加进来
				
				1.在QueryVo 中加入一个List 集合用于封装参数
					public class QueryVo implements Serializable {
						private List<Integer> ids;
						public List<Integer> getIds() {
							return ids;
						}
						public void setIds(List<Integer> ids) {
							this.ids = ids;
						}
					}
					
				2.持久层 Dao 接口
					List<User> findInIds(QueryVo vo);

				3.Dao 映射配置
					<select id="findInIds" resultType="user" parameterType="queryvo">
						<!-- select * from user where id in (？); -->
						select * from user
						<where>
							<if test="ids != null and ids.size() > 0">
								<foreach collection="ids" open="id in ( " close=")" item="uid" separator=",">
									#{uid}
								</foreach>
							</if>
						</where>
					</select>

					test属性：其中变量，是parameterType实体类的成员变量名
					<foreach>标签：用于遍历集合
					collection 属性：代表要遍历的集合元素(parameterType的成员变量名)，注意编写时不要写#{}
					open 属性：代表sql 语句的开始部分
					close 属性：代表结束部分
					item：代表遍历集合的每个元素，生成的变量名
					sperator：代表分隔符
					
		3.4 Mybatis中简化编写SQL片段
				Sql中可将重复的sql 提取出来，使用时用<include> 引用即可，最终达到sql 重用的目的。
				
				1.定义代码片段(<mapper>标签中)
					<!-- 抽取重复的语句代码片段 -->
					<sql id="defaultSql">
						select * from user
					</sql>

				2.引用代码片段
					<select id="findById" resultType="UsEr" parameterType="int">
						<include refid="defaultSql"></include>
						where id = #{uid}
					</select>

 */


/*							mybatis多表查询

 	1.一对一
 		查询两张表，表1(User)一条记录 对应 表2(Account)一条记录。
 		sql：select u.*,a.id as aid,a.uid,a.money from account a,user u where a.uid =u.id;
 		
 		1.定义Account 类:在Account 类中加入User 类的对象作为Account 类的一个属性。
			public class Account implements Serializable {
				private Integer id;
				private Integer uid;
				private Double money;
				private User user;
				...
			}
		
		2.定义Dao 接口中的方法：
			List<Account> findAll();

		3.定义Dao 映射配置文件：
			<mapper namespace="com.itheima.dao.IAccountDao">
				<!-- 建立对应关系 -->
				<resultMap type="account" id="accountMap">
					<id column="aid" property="id"/>
					<result column="uid" property="uid"/>
					<result column="money" property="money"/>
					<!-- 它是用于指定从表方的引用实体属性的 -->
					<association property="user" javaType="user">
						<id column="id" property="id"/>
						<result column="username" property="username"/>
						<result column="sex" property="sex"/>
						<result column="birthday" property="birthday"/>
						<result column="address" property="address"/>
					</association>
				</resultMap>
				
				<select id="findAll" resultMap="accountMap">
					select u.*,a.id as aid,a.uid,a.money from account a,user u where a.uid =u.id;
				</select>
			</mapper>
			
			<association> 标签：指定(将数据库表中数据--sql查询结果)封装到哪个实体类(javaType)中，并存入type实体类的property数据。
			javaType 属性：数据库数据封装的实体类。
				column 属性：数据库查询结果的列名。
 				property 属性：javaType实体类的属性名。
 				
 		4.AccountTest 类中测试：
 			@Test
 			public void testFindAll() {
				List<Account> accounts = accountDao.findAll();
				for(Account au : accounts) {
					System.out.println(au);
					System.out.println(au.getUser());
				}
			}

 		
 	2.一对多
 		查询两张表，表1(User)一条记录 对应 表2(Account)多条记录。
 		sql：SELECT u.*, acc.id id, acc.uid, acc.money FROM user u LEFT JOIN account acc ON u.id = acc.uid

 		
 		1.定义User 类,加入 List<Account>属性：
 			public class User implements Serializable {
				private Integer id;
				private String username;
				private Date birthday;
				private String sex;
				private String address;
				private List<Account> accounts;
				...
			}
		
		2.定义Dao 接口中的方法：
			List<User> findAll();

		3.定义Dao 映射文件配置：
			<mapper namespace="com.itheima.dao.IUserDao">
				<resultMap type="user" id="userMap">
					<id column="id" property="id"></id>
					<result column="username" property="username"/>
					<result column="address" property="address"/>
					<result column="sex" property="sex"/>
					<result column="birthday" property="birthday"/>
					
					<!-- collection 是用于建立一对多中集合属性的对应关系，ofType 用于指定集合元素的数据类型-->
					<collection property="accounts" ofType="account">
						<id column="aid" property="id"/>
						<result column="uid" property="uid"/>
						<result column="money" property="money"/>
					</collection>
				</resultMap>
				
				<!-- 配置查询所有操作 -->
				<select id="findAll" resultMap="userMap">
					select u.*,a.id as aid ,a.uid,a.money from user u left outer join account 
					a on u.id =a.uid
				</select>
			</mapper>
			
			<collection>标签：定义了User 关联的部分Account 信息，并封装到集合，保存在'type'实体类 的'property'属性中。
			ofType 属性：数据库数据封装的实体类。
 	
 	3.Role 到 User 的多对多
 		查询三张表(Role、User、USER_ROLE)，中间表(USER_ROLE 表)，定义Role 类封装Role、User 表的信息。
 		sql：	SELECT  
 						r.*,
				 		u.id uid, 
				 		u.username username, 
				 		u.birthday birthday,
				 		u.sex sex,
				 		u.address address
				FROM	
						ROLE r
				INNER JOIN 					----显示内联接
						USER_ROLE ur
				ON ( r.id = ur.rid)
				INNER JOIN	
				 		USER u
				ON (ur.uid = u.id);

 		Mybatis中：多对多关系其实我们看成是双向的一对多关系。
 		
 		1.定义角色实体Role 类：
 			public class Role implements Serializable {
				private Integer roleId;
				private String roleName;
				private String roleDesc;
				//多对多的关系映射：一个角色可以赋予多个用户
				private List<User> users;
			}
		
		2.定义Dao 接口：
			public interface IRoleDao {
				List<Role> findAll();
			}

		3.定义映射配置文件：
			<mapper namespace="com.itheima.dao.IRoleDao">
				<!--定义 role 表的 ResultMap-->
				<resultMap id="roleMap" type="role">
					<id property="roleId" column="rid"></id>
					<result property="roleName" column="role_name"></result>
					<result property="roleDesc" column="role_desc"></result>
					
					<collection property="users" ofType="user">
						<id column="id" property="id"></id>
						<result column="username" property="username"></result>
						<result column="address" property="address"></result>
						<result column="sex" property="sex"></result>
						<result column="birthday" property="birthday"></result>
					</collection>
				</resultMap>
	
				<!--查询所有-->
				<select id="findAll" resultMap="roleMap">
					select u.*,r.id as rid,r.role_name,r.role_desc from role r
					left outer join user_role ur on r.id = ur.rid
				 	left outer join user u on u.id = ur.uid
				</select>
			</mapper>
 */


/*							延迟加载
	
	1.延迟加载：就是在程序需要用到数据时才进行加载，不需要用到数据时就不加载数据。延迟加载也称懒加载.
	
	2.一对一实现延迟加载(association)
		查询账户信息同时查询用户信息。
		
		1.定义Account 实体类：
			public class Account implements Serializable {
				private Integer id;
				private Integer uid;
				private Double money;
				
				private User user;
				...
			}
		
		2.定义DAO 接口：
			public interface IAccountDao {
				List<Account> findAll();
			}

		3.账户的映射配置文件：
			<mapper namespace="com.itheima.dao.IAccountDao">
				<!-- 建立对应关系 -->
				<resultMap type="account" id="accountMap">
					<id column="aid" property="id"/>
					<result column="uid" property="uid"/>
					<result column="money" property="money"/>
					
					<!-- 它是用于指定从表方的引用实体属性的 -->
					<association property="user" javaType="user"
						select="com.itheima.dao.IUserDao.findById"
						column="uid">
					</association>
				</resultMap>
				
				<select id="findAll" resultMap="accountMap">
					select * from account
				</select>
			</mapper>
			
			<association>标签：指定封装数据的实体类
			select 属性： 填写需要用到Account中的property="user"数据时，延迟加载的<select>标签的 全路径名 + id属性
			column 属性： 填写sql查询结果中要传递给<select>标签映射的参数。
		
		4.用户的持久层接口和映射配置文件
			DAO接口：
				public interface IUserDao {
					User findById(Integer userId);
				}
			
			映射配置文件：
				<mapper namespace="com.itheima.dao.IUserDao">
					<!-- 根据 id 查询 -->
					<select id="findById" resultType="user" parameterType="int" >
						select * from user where id = #{uid}
					</select>
				</mapper>

		5.开启 Mybatis 的延迟加载策略
			在Mybatis 的主配置文件 SqlMapConfig.xml 文件<configuration>中添加延迟加载的配置：
			<settings>
				<setting name="lazyLoadingEnabled" value="true"/>
				<setting name="aggressiveLazyLoading" value="false"/>
			</settings>

		6.测试
			查账户信息不查用户信息：	只加载第一个sql语句。
			查账户信息、用户信息：		先加载第一个sql语句，再执行第二个sql语句。
			
	3.一对多实现延迟加载(Collection)
		查询账户信息同时查询用户信息。
		
		1.定义User 实体类中加入 List<Account>属性：(封装查询结果，有实体类为一对一，有List集合为一对多)
			public class User implements Serializable {
				private Integer id;
				private String username;
				private Date birthday;
				private String sex;
				private String address;
			
				private List<Account> accounts;
				...
			}

		2.定义DAO 接口：
			List<User> findAll();

		3.定义DAO 接口映射配置文件：
			<resultMap type="user" id="userMap">
				<id column="id" property="id"></id>
				<result column="username" property="username"/>
				<result column="address" property="address"/>
				<result column="sex" property="sex"/>
				<result column="birthday" property="birthday"/>
				
				<!-- collection 是用于建立一对多中集合属性的对应关系
				ofType 用于指定集合元素的数据类型
				select 是用于指定查询账户的唯一标识（账户的 dao 全限定类名加上方法名称）
				column 是用于指定使用哪个字段的值作为条件查询
				-->
				<collection property="accounts" ofType="account"
					select="com.itheima.dao.IAccountDao.findByUid"
					column="id">
				</collection>
			</resultMap>
			
			<!-- 配置查询所有操作 -->
			<select id="findAll" resultMap="userMap">
				select * from user
			</select>
			
			<collection>标签：主要用于加载关联的集合对象
			select 属性：用于指定查询 account 列表的 sql 语句，所以填写的是该 sql 映射的 id
			column 属性：用于指定 select 属性的 sql 语句的参数来源，上面的参数来自于 user 的 id 列，所以就写成 id 这个字段名了
			
		4.定义账户DAO 接口、账户DAO 映射配置文件
			DAO接口：
				public interface IAccountDao {
					Account findByUid(Integer userId);
				}
			
			映射配置文件：
				<select id="findByUid" resultType="account" parameterType="int">
					select * from account where uid = #{uid}
				</select>

		5.开启 Mybatis 的延迟加载策略
		6.测试时调用User 类的accounts（延迟加载），或不调用（不加载）。
 */


/*							缓存

 	
 	1.什么是缓存
		 存在于内存中的临时数据。
 	
	2.为什么使用缓存
		减少和数据库的交互次数，提高执行效率。
	
	3.什么样的数据能使用缓存，什么样的数据不能使用
		适用于缓存：
			经常查询并且不经常改变的。
			数据的正确与否对最终结果影响不大的。
		不适用于缓存：
			经常改变的数据
			数据的正确与否对最终结果影响很大的。
			例如：商品的库存，银行的汇率，股市的牌价。
			
	4.Mybatis中的一级缓存和二级缓存
		(一级缓存通过接口方法得到的对象地址值相同，二级缓存通过方法得到的对象地址值不同--因为二级缓存是缓存的数据)
		一级缓存：(无需配置)
			1.它指的是Mybatis 中SqlSession 对象的缓存。
			2.当我们执行查询之后，查询的结果会同时存入到SqlSession为我们提供一块区域中。
			3.该区域的结构是一个Map。当我们再次查询同样的数据，mybatis会先去sqlsession中查询是否有，有的话直接拿出来用。
			4.一级缓存是 SqlSession 级别的缓存，只要 SqlSession 没有 flush 或 close，它就存在。
			 当SqlSession对象消失时，mybatis的一级缓存也就消失了。
			5.清空一级缓存：
					SqlSession 调用修改，添加，删除，commit()，close()，clearCache()方法
					sqlSession.close();
					sqlSession.clearCache();
		
		二级缓存:(需配置)
			1.它指的是Mybatis中SqlSessionFactory对象的缓存。由同一个SqlSessionFactory对象创建的SqlSession共享其缓存。
			2.二级缓存的使用：
				第一步：让Mybatis框架支持二级缓存（在SqlMapConfig.xml中配置）：
							<settings>
								<!-- 开启二级缓存的支持 -->
								<setting name="cacheEnabled" value="true"/>
							</settings>

				第二步：让当前的映射文件支持二级缓存（在IUserDao.xml中配置）
							<mapper namespace="com.itheima.dao.IUserDao">
								<!-- 开启二级缓存的支持 -->
								<cache></cache>
							</mapper>

				第三步：让当前的操作支持二级缓存（在select标签中配置）
							<select id="findById" resultType="user" parameterType="int" useCache="true">
								select * from user where id = #{uid}
							</select>
			
			3.二级缓存的禁用：
				第三步：让当前的操作不支持二级缓存（在select标签中配置）
							<select id="findById" resultType="user" parameterType="int" useCache="false">
								select * from user where id = #{uid}
							</select>
					注意：针对每次查询都需要最新的数据 sql，要设置成 useCache=false，禁用二级缓存。
 */


/*							注解开发
					

		1.在接口方法上添加注解
		
			在mybatis 中，针对CRUD 一共有四个注解：@Select @Insert @Update @Delete
			
			如： @Select(value="select * from user") value可省略
			    @Select("select * from user") 
			    
			    
		2.在mybatis主配置文件中，引入带有注解的dao接口
			    <mappers>
			        <package name="com.itheima.dao"></package>
			    </mappers>
		
		
		3.为什么只用注解就能执行sql语句？
			执行需要的四个信息：dao位置、方法名、sql语句、返回值
				通过注解对应的方法名得到：dao位置、方法名
				通过注解信息得到：sql语句
				通过方法声明的返回值得到：返回值
				
		3.报错-细节
			mybatis 中，只要使用了注解定义接口，就不可以存在接口的映射配置文件，否则会报错
		
		3.其他注解
			1.@Param
				位置：dao方法参数中
				作用：将 sql中参数 绑定 方法参数。
				
					例：	@Insert("insert into users_role values(#{user.id},#{roleId})")
						public void addRoleToUser(@Param("user")User u, @Param("roleId")String role) throws Exception;
				
		
		4.注解建立实体类与数据库列名的对应：
			@Select("select * from user")
			@Results(id="userMap",value= {
					@Result(id=true,column="id",property="userId"),
					@Result(column="username",property="userName"),
					@Result(column="sex",property="userSex"),
					@Result(column="address",property="userAddress"),
					@Result(column="birthday",property="userBirthday")
					})
			List<User> findAll();
			
			1.其他方法想使用此映射的，添加：@ResultMap("id名")
					例：	@Select("select * from user where id=#{id} ")
					    @ResultMap("userMap")
					    User findById(Integer userId);
	
		5.注解开发：一对一
			1.一对一：使用立即加载，不需要在主配置文件中开启
			2.注解开发一对一：需要指定是否延迟加载，xml映射不配置则是立即加载。
			
				@Select("select * from account")
			    @Results(id="accountMap",value = {
			            @Result(id=true,column = "id",property = "id"),
			            @Result(column = "uid",property = "uid"),
			            @Result(column = "money",property = "money"),
			            //配置一对一关系
			            @Result(property = "user",column = "uid",javaType = User.class,
					            one=@One(select="com.itheima.dao.IUserDao.findById",
					            fetchType= FetchType.EAGER))
			    })
			    List<Account> findAll();
			
						@Result 中属性：
						id ：是否是主键字段
						column ：数据库的列名	/当为多表查询时(一对一、一对多)表示传递给另一个select语句的参数
						property ：实体类属性名
						one：多表、一对一查询。使用 @One 注解
								select :第二个查询的sql。写法为全限定类名+方法名
								fetchType：是否延迟加载。FetchType.EAGER=立即加载	FetchType.LAZY=延迟加载
					
		6.注解开发：一对多
			1.一对多：使用延迟加载，不需要在主配置文件中开启
			
			@Select("select * from user")
		    @Results(id="userMap",value={
		            @Result(id=true,property = "userId",column = "id"),
		            @Result(property = "userName",column = "username"),
		            @Result(property = "userAddress",column = "address"),
		            @Result(property = "userSex",column = "sex"),
		            @Result(property = "userBirthday",column = "birthday",
		            @Result(property = "accounts",column = "id",javaType = Account.class,
		                    many = @Many(select = "com.itheima.dao.IAccountDao.findAccountByUid",
		                                fetchType = FetchType.LAZY))
		    })
		    List<User> findAll();
		    
		7.注解开发：开启二级缓存
			与xml映射配置对比，第一步相同，第二步不同。
			
			第一步：主配置文件开启二级缓存：
					<settings>
				        <setting name="cacheEnabled" value="true"/>
				    </settings>
	
			第二步：在Dao接口上添加注解：
					@CacheNamespace(blocking = true)	//blocking = true 表示开启二级缓存
					public interface IUserDao {
						...
					}
 */


/*							数据库连接的URL写法
 * 
 		1.URL用于标识数据库的位置	jdbc:mysql://localhost:3306/
 		
 		2.URL的写法为：
			 			jdbc:	mysql:[]	//localhost:3306/	test 	?参数名=参数值&参数名=参数值
						 |		    |				|			 |			  |
						协议		    子协议		      主机名：端口 		数据库名称	携带参数
						
		3.常用参数：  useUnicode=true&characterEncoding=UTF-8 	---设定链接的字符集
					user=root&password=root					---携带数据库用户名和密码
		
		4.Mysql的url地址的简写形式：	jdbc:mysql:///sid
		
		5.常用数据库URL地址的写法：
				Oracle写法：		jdbc:oracle:thin:@localhost:1521:orcl
				SqlServer：		jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=sid
				MySql：			jdbc:mysql://localhost:3306/08jdbc?serverTimezone=UTC	//serverTimeZone时区信息


 */

public class Mybatis详解 {

}
