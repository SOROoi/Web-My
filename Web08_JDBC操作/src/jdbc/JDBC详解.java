package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*						JDBC驱动	(java data base connectivity----Java数据库连接)
 
 	1. JDBC简介
 		1.JDBC就是数据库驱动，Java操作数据库的规范
 		
 		2.JDBC结构示意：
		 					应用程序
		 					   |
		 					  JDBC(接口,在JavaSE的包java.sql/javax.sql中)
		 					   |
 			MySQL驱动(需导包)———————Oracle驱动(需导包)
 					  |					|
 					 MySQL			  Oracle
 				
 					
 		3.不同数据库软件有不同的驱动,SUN公司为了简化、统一对数据库的操作，定义了一套Java操作数据库的规范，称之为JDBC。
 		
 		
 	2.JDBC包中DriverManager类(驱动管理器)
 	
 		1.用于加载驱动、创建与数据库的链接，这个API的常用方法：
			DriverManager.registerDriver(new Driver())		//不用此方法加载驱动，用Class.forName("")
			DriverManager.getConnection(url, user, password)，
		
		2.实际开发中，不推荐采用registerDriver(new Driver())方法注册驱动
			原因一：查看Driver的源代码可以看到，采用此种方式,会导致驱动程序注册两次(加载class文件也会创建),在内存中会有两个Driver对象。
			原因二:程序依赖mysql的api(需要导包)，脱离mysql的jar包，程序将无法编译，将来程序切换底层数据库将会非常麻烦
 		
 		3.推荐方式：Class.forName(“com.mysql.cj.jdbc.Driver”)
			原因：采用此种方式不会导致驱动对象在内存中重复出现，并且采用此种方式，程序仅仅只需要一个字符串，不需要依赖具体的驱动，使程序的
			        灵活性更高。
 		
 	3.获取数据库连接的URL写法
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



 	4.java中编写JDBC程序的步骤：
 		1.加载驱动：		build path---导MySQL驱动包
 			Class.forName("com.mysql.cj.jdbc.Driver");	//驱动com.mysql.jdbc.Driver已被弃用
 			
 		2.获取MySQL的连接及其对象
 			String url = "jdbc:mysql://localhost:3306/08jdbc?serverTimezone=UTC&user=root&password=root";
 			Connection con = DriverManager.getConnection(url);		//Mysql最新版驱动8.0，需要时区信息
 			
 		3.获取向数据库发送sql语句的statement对象
 			Statement state = con.createStatement();
 			
 		4.向数据库发送sql,获取数据库返回的结果集
 			ResultSet re = state.executeQuery("select * from users");
 			
 		5.从结果集中获取数据
 			while (re.next()) { 			---re类似迭代器，指针读取每一行
				System.out.print("id:" + re.getObject("id") + "\t");
				System.out.print("name:" + re.getObject("name") + " \t");
			}
		
 		6.释放资源(释放链接)
 			re.close();
			state.close();
			con.close();

	5.JDBC包中Connection类
	
		1.用于代表数据库的链接，Collection是数据库编程中最重要的一个对象，客户端与数据库所有交互都通过connection对象完成
		
		2.Connection对象的常用方法：
				createStatement()：					创建向数据库发送sql的statement对象。
				prepareStatement(sql)：				创建向数据库发送预编译sql的PrepareSatement对象。
				prepareCall(sql)：					创建执行存储过程的callableStatement对象。 
				setAutoCommit(boolean autoCommit)：	设置事务是否自动提交。 
				commit()：							在链接上提交事务。(使用事务需先关闭自动提交-AutoCommit)
				rollback()：							在此链接上回滚事务。
				
				(以下3个一起使用)
				Savepoint sp = con.setSavepoint();	设置回滚点				--返回Savepoint回滚点对象
				con.rollback(sp);			  		回滚回滚点之后的sql		--后接commit()才有效
				con.commit();  						提交事务					--未收到commit()自动回滚事务中所有sql
		
		3.结束后释放资源
				con.close();

	6.JDBC包中Statement类
	
		1.用于向数据库发送SQL语句
		
		2.Statement对象常用方法：
				executeQuery(String sql)：		用于向数据发送查询语句。					---返回ResultSet，结果集对象
				executeUpdate(String sql)：		用于向数据库发送insert、update或delete语句	---返回int，表示sql语句影响数据库的行数
				execute(String sql)：			用于向数据库发送任意sql语句				---返回boolean，表示sql语句是否执行
				
		 (批处理)addBatch(String sql)：			把多条sql语句放到一个批处理中。
				executeBatch()：					向数据库发送一批sql语句执行。
				clearBatch();					清空st的批处理命令
				
		3.结束后释放资源
				state.close();

	7.JDBC包中PreparedStatement类
		
		1.简介：
			1.是Statement的子类
			2.通过Connection.preparedStatement()方法获得
		
		2.与Statement比较，优点：
		
			1.PreparedStatement可以避免SQL注入的问题。
				1.1.原因：
					  1.Statement.executeQuery(String sql)时，sql结合传入参数，可能使传入参数也成为sql语句，使原本sql语义发生变化；
					  2.而PreparedStatement在使用传入参数时，会对参数进行转义再结合sql，sql语义不会变化，避免了sql注入问题。
				
			2.数据库每收到一次Statement的sql语句，都需要先编译再执行；
			  PreparedStatement 可对SQL进行预编译，从而提高数据库的执行效率
			  
			3.PreparedStatement对于sql中的参数，允许使用占位符？进行替换，简化sql语句的编写。
			---------------例：------------
					PreparedStatement st = null;
					ResultSet re = null;
					
					String sql = "insert into users(id,username,password,email,birthday,nickname) values(?,?,?,?,?,?);";
					state = conn.prepareStatement(sql);  	//预编译这条sql
					state.setString(2, username);			//设定每一个?的值，2表示第二个?，username表示设定的值
					state.setString(3, password);
					state.setDate(5, new java.sql.Date(user.getBirthday().getTime()));
					re = state.executeQuery();		//执行sql语句
					
			-------------面试题：-----------------
			statment和preparedStatement的区别:
				1. preparedStatement是statement的子类
				2. preparedStatement可以防止sql注入的问题
				3. preparedStatement会对sql语句进行预编译，以减轻数据库服务器的压力
				
						java---class---jvm
						sql----编译---数据库
			-------------------------------------
			
		3.对应数据库中的类型,向sql中?传入参数的方法		(sql中有?时才需要使用)
		----------------------------------常用数据类型转换表----------------------------------------------------------
				SQL列的类型					设置?索引(1.2..)对应参数.方法							Java传参类型
				
				boolean						setBoolean(int Index, boolean x)					boolean
				
				tinyint						setByte(int Index, byte x) 							byte
				smallint 					setShort(int Index, short x)						short
				int							setInt(int Index, int x)							int
				bigint						setLong(int Index, long x)							long
				
				char,varchar,longvarchar	setString(int Index, String x)						String
				
				text(文本大数据)	 			setCharacterStream(int Index, Reader r, long len)	Reader			流文件的字节数
				blob(二进制大数据)			setBinaryStream(int Index, InputStream i,long len)	InputStream		流文件的字节数
				
				date						setDate(int Index, Date x) 							java.sql.Date
				time						setTime(int Index, Time x) 							java.sql.Time
				timestamp					setTimestamp(int Index, Timestamp x)				java.sql.Timestamp
				
		4.PreparedStatement对象常用方法：
				executeQuery()：				用于向数据发送查询语句。						---返回ResultSet，结果集对象
				executeUpdate()：			用于向数据库发送insert、update或delete语句		---返回int，表示sql语句影响数据库的行数
				execute()					用于向数据库发送任意sql语句					---返回boolean，表示sql语句是否执行
				addBatch();						//添加sql语句放到一个批处理中
				executeBatch();					//执行批处理
				clearBatch();					//清除st中的批处理命令	

	8.JDBC包中ResultSet类
	
		1.用于代表Sql语句的执行结果集
		
		2.Resultset封装执行结果，代表的类似于表格的一行数据。
		  ResultSet对象维护了一个指向表格数据行的游标，初始的时候，游标在第一行之前.
		    调 用ResultSet.next() 方法，可以使游标指向具体的数据行，进行调用方法获取该行的数据。
		    若游标走到底，则.next() 返回false。
		 
		3.ResultSet对象的get方法获得该列数据：
				获取任意类型的数据
					getObject(int index)			---返回Object		---传入列索引查找对应的列数据，此方法索引从1开始
					getObject(string columnName)	---返回Object		---传入列名查找数据
					
				获取指定类型的数据，例如：
					getString(int index)			---返回String		---传入列索引查找对应的列数据，此方法索引从1开始
					getString(String columnName)	---返回String		---传入列名查找数据
					
		4.结束后释放资源
					result.close();
		----------------------------------常用数据类型转换表----------------------------------------------------------
				SQL类型						Jdbc对应方法								返回Java程序的类型
				boolean						getBoolean(string colName)				boolean 
				
				tinyint						getByte(string colName)					byte
				smallint 					getShort(string colName)				short
				int							getInt(string colName)					int
				bigint						getLong(string colName)					long
				
				char,varchar,longvarchar	getString(string colName)				String
				
				text(文本大数据)				getCharacterStream(string colName)		Reader
											getClob(int i).getCharacterStream()		Reader
											
				blob(二进制大数据)			getBinaryStream(String colName)			InputStream
											getBlob(i).getBinaryStream()			InputStream
				
				date						getDate(string colName)					java.sql.Date
				time						getTime(string colName)					java.sql.Time
				timestamp					getTimestamp(string colName)			java.sql.Timestamp
	
		4.ResultSet还提供了对结果集进行滚动的方法：
				next()：					移动到下一行
				Previous()：				移动到前一行
				absolute(int row)：		移动到指定行
				beforeFirst()：			移动resultSet的最前面。
				afterLast() ：			移动到resultSet的最后面。
				
	9.释放资源
		1.Jdbc程序运行完后，要释放与数据库进行交互的对象，它们是ResultSet, Statement和Connection对象
		
		2.特别是Connection对象，它是非常稀有的资源，用完后必须马上释放，
		    如果Connection不能及时、正确的关闭，极易导致系统宕机。
		  Connection的使用原则是尽量晚创建，尽量早的释放。
		  
		3.为确保资源释放代码能运行，资源释放代码也一定要放在finally语句中。  

 */

/*----------------------JDBC向数据库中存、取大数据	(--见/Web08_JDBC操作/src/jdbc_lob/JDBC_lob.java)-----------------------------
	
	1.大数据
		1.基本概念：大数据也称之为LOB(Large Objects)，LOB又分为：
					clob：用于存储大文本。Text
					blob：用于存储二进制数据，例如图像、声音、二进制文等。
					
	2.MySQL中的大数据类型(列的类型)：
					Text：mysql存储大文本的类型
					blob：mysql用于存储二进制数据
			
	3.Text类型存、取：
			1.将Text类型存入MySQL中：
					Connection con = JdbcUtil.getConnection();
					
					String sql = "insert into textclob(id,textdata) values(?,?)";
					PreparedStatement pState = con.prepareStatement(sql);
					pState.setString(1,"1");			//1索引的?对应数据库中id,数据库中id类型为varchar，调用setString()，传入"1"
					
					File file = new File("src/1.txt");
					FileReader reader = new FileReader(file);
					pState.setCharacterStream(2, reader, file.length()); //传入字符输入流,注意length长度须设置,且JDK6后可传入long型
					
					int i = pState.executeUpdate();		//控制数据库执行sql语句，插入数据
					
			2.将Text类型从MySQL中取出：
					Connection con = JdbcUtil.getConnection();
					
					String sql = "select id,textdata from textclob where id='1'";
					PreparedStatement pState = con.prepareStatement(sql);
					
					ResultSet re = pState.executeQuery();		//控制数据库执行sql语句，获得结果集
					
					if(re.next()){
						Reader reader = re.getCharacterStream("textdata");	//从结果集中取得大数据，存入Reader流中
					}

	4.Blob类型存、取：
			1.将Blob类型存入MySQL中：
					Connection con = JdbcUtil.getConnection();
					
					String sql = "insert into bytelob(id,blobdata) values(?,?)";
					PreparedStatement pState = con.prepareStatement(sql);
					pState.setString(1,"1");			//1索引的?对应数据库中id,数据库中id类型为varchar，调用setString()，传入"1"
					
					File file = new File("src/1.txt");
					FileInputStream in = new FileInputStream(file);
					pState.setBinaryStream(2, in, file.lenth());	//传入字节输入流,注意length长度须设置,且JDK6后可传入long型
					
					int i = pState.executeUpdate();		//控制数据库执行sql语句，插入数据

			2.将Blob类型从MySQL中取出：
					Connection con = JdbcUtil.getConnection();
					
					String sql = "select id,blobdata from bytelob where id='1'";
					PreparedStatement pState = con.prepareStatement(sql);
					
					ResultSet re = pState.executeQuery();		//控制数据库执行sql语句，获得结果集
					
					if(re.next()){
						InputStream  in = re.getBinaryStream("blobdata");	//从结果集中取得大数据，存入InputStream流中
					}
			

 */


/*----------------------使用JDBC对数据库进行CRUD	(--见/Web08_JDBC操作/src/jdbc/JDBC_crud.java)--------------------------------

	1.类加载器读取的文件为中文路径，乱码问题		(--见/Web08_JDBC操作/src/jdbc/util/JdbcUtil.java)
	
		问题：
 		String path = JdbcUtil.class.getClassLoader().getResource("").getPath();  //(默认在/src/下)
 																			  	  //如果是中文路径,getResource("")获得的path是乱码 
 		解决方法：
 		Path=java.net.URLDecoder.decode(path,"utf-8");	//需要进行decode才能正常显示中文路径

	
		附：
			String username2=request.getParameter("username2");
			username2=new String(username2.getBytes("iso-8859-1"),"UTF-8");		--将得到的数据转换为字节码，再用UTF-8打开
			
	2.在JdbcUtil.class.getClassLoader().getResource(String path)方法中：
 			path不以'/'开头时，默认是从该项目所在的		/bin/包(即src)下取资源；
 			path以'/'开头时，报错。

	3.URLEncoder.encode 与 URLDecoder.decode
		URLEncoder.encode(String s, String enc)：用指定的编码将 String 转换为 application/x-www-form-urlencoded 格式。
		URLDecoder.decode(String s, String enc)：用指定的编码将 application/x-www-form-urlencoded 转换为 String 格式。
		
		发送的时候使用 URLEncoder.encode编码，接收的时候使用 URLDecoder.decode解码

 */


/*----------------------JDBC批处理--------------------------------------------------------------------------------------------
 	1.什么是批处理
 		JDBC向数据库发送多条SQL语句执行，执行一批SQL语句
 		
 	2.实现批处理
		
		方式一：  1.获得Statement对象st,
				2.多次调用addBatch(sql)方法添加多个sql,
				3.调用executeBatch()执行批处理，
				4.最后clearBatch()清除批处理命令
 				
 		      特点：可以对不同的sql语句进行批处理。	无预编译、效率低。
 				
 		      例：		conn = JdbcUtils.getConnection();
	 				String sql1 = "insert into testbatch(id,name) values('1','aaa')";	//sql
					String sql2 = "update testbatch set name='bbb' where id='1'";	
					
					Statement st = conn.createStatement();  	//内部有一个list集合装载sql语句
					st.addBatch(sql1);				//添加sql
					st.addBatch(sql2);
					
					int[] arr = st.executeBatch();	//执行批处理，返回int[],每个arr[index]保存着每一条sql影响的记录数量
					st.clearBatch();				//清除st中的批处理命令
					
		方式一：  1.创建一条sql
				2.预编译sql,获得PreparedStatement对象state,
				3.调用setXxx(i,xx)设定好一条sql的所有?,
				4.调用addBatch()方法添加第一条sql，
				5.再用setXxx(i,xx)设定好一条sql的所有?,
				6.调用addBatch()方法添加第二条sql，
				....
				4.调用executeBatch()执行批处理，
				5.最后clearBatch()清除批处理命令
 				
 		      特点：可以对同一条的sql语句进行批量处理。预编译、效率高 	--做批量插入   批量更新
 				
 		      例：		String sql = "insert into user(name,password,email,birthday) values(?,?,?,?)";	//sql
					PreparedStatement st = conn.prepareStatement(sql);		//预编译
					
					for(int i=0;i<50000;i++){
						st.setString(1, "aaa" + i);				//设定?的值
						st.setString(2, "123" + i);
						st.setString(3, "aaa" + i + "@sina.com");
						st.setDate(4,new Date(1980, 10, 10));
						
						st.addBatch();							//添加设定好的sql
						if(i%1000==0){
							st.executeBatch();					//执行批处理
							st.clearBatch();					//清除st中的批处理命令	
						}
					}
					st.executeBatch();
					st.clearBatch();
 
	3.批处理的效率
		比如同样插入1000W条数据，Oracle的效率远高于MySQL
 
 */

/*----------------------获得插入数据库自动生成的主键(仅对插入有效)-------------------------------------------------------------------
 	1.目的：插入一条数据后获得该数据的主键值
 	
 	2.方式：	1.创建PreparedStatement对象st,使用con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)方法
 		    2.调用st.executeUpdate()执行sql语句
 		    3.调用ResultSet rs = st.getGeneratedKeys()获得结果集，封装了主键
 		    4.取出主键，if(rs.next())
					   System.out.println(rs.getObject(1));
	
	3.例：	conn = JdbcUtils.getConnection();
			String sql = "insert into test(name) values('aaa')";
			st = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			st.executeUpdate();				----------传该参，获得插入数据的主键----------
			
			rs = st.getGeneratedKeys();
			if(rs.next()){
				System.out.println(rs.getInt(1));
			}
 
 */

/*----------------------JDBC调用存储过程---------------------------------------------------------------------------------------
 	1.存储过程简介：
 		1.存储过程是数据库中的函数，是一串sql语句
 		2.存储过程将数据处理的过程隐藏在数据库中，防止暴露(常用于银行金融类)
 		3.java通过JDBC获得存储过程，实现数据处理(逻辑由数据库提供)
 	
 	2.存储过程的使用：
 		1.数据库中编写存储过程：略，见MySQL文档
 		2.Java中通过con.prepareCall("{?,?}"),得到CallableStatement对象st，并调用存储过程：
 		3.设置传入值?--st.setString(1, "abcdefg")
 		4.设置返回值?--st.registerOutParameter(2, Types.VARCHAR)		//registerOutParameter(i,s)方法中，
 																	//s代表数据库中的类型，在java.sql.Types的字段中选择
 		5.执行存储过程--st.execute()
 		6.使用st得到输出--st.getString(2)		//得到返回值-第2个?
 
 	3.例：	Connection con = JdbcUtils.getConnection();
			CallableStatement st = conn.prepareCall("{call demoSp(?,?)}");	//2.
			st.setString(1, "aaaaa");	//3.
			st.registerOutParameter(2, Types.VARCHAR);		//4.
			st.execute();			//5.
			
			System.out.println(st.getString(2));	//6.
 		
 */

/*----------------------JDBC对结果集进行滚动--------------------------------------------------------------------------------------
	1.对结果集进行滚动简介：
 		1.得到ResultSet对象re后，对结果集进行滚动，即改变re光标的位置，可跳转到需要的行
 		
 	2.ResultSet对结果集进行滚动的方法：
		 		next()：				移动到下一行
				Previous()：			移动到前一行
				absolute(int row)：	移动到指定行
				beforeFirst()：		移动resultSet的第一行。
				afterLast()：		移动到resultSet的最后行。

 	
 	
 */

/*----------------------JDBC编程注意事项---------------------------------------------------------------------------------------
 (Java程序外)00.开启数据库(如MySQL)服务器
 		
 (Java程序外)01.必须在项目中导入数据库(如MySQL)的驱动jar包
 				--复制jar包放入lib文件夹	--build path
	 		
 (Java程序内)1.必须加载数据库(如MySQL)驱动			
	 				--Class.forName("驱动包下Driver类的地址");	--com.mysql.cj.jdbc.Driver	--默认class库目录下
	 				
	 		2.必须创建Connection连接对象，传入url,user,password
	 				--Connection con = DriverManager.getConnection(url, user, password);
	 		
	 		3.通过properties配置文件读取url时，注意配置文件里url中url协议、数据库名称、时区信息
	 		 
			4.通过properties配置文件读取Driver地址时，注意不要写错地址包名
			
			5.类加载器读取properties配置文件path时，配置文件path含中文会出现乱码，使用java.net.URLDecoder.decode(path, "UTF-8")解决
					--String path = JdbcUtil.class.getClassLoader().getResource("jdbc_lob/db.properties").getPath();
					--path = java.net.URLDecoder.decode(path, "UTF-8");
	
			6.必须使用try..finally 确保一定释放Connection.PreparedStatement.ResultSet.Reader.Writer资源
 */

public class JDBC详解 {
	/**
	 * MySQL数据库操作：
	 * 
	 * create database 08jdbc;
	 * 
	 * create table users( id int primary key, name varchar(40),
	 * password varchar(40), email varchar(60), birthday date);
	 * 
	 * insert into users values(1,'zs','123456','zs@sina.com','1980-12-04'),
	 * (2,'lisi','123456','lisi@sinq.com','1981-12-04'),
	 * (3,'wangwu','123456','wangwu@sinq.com','1979-12-04');
	 * 
	 * @throws ClassNotFoundException
	 * 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/08jdbc?serverTimezone=UTC&user=root&password=root"; // 协议--主机名--端口--库
		String user = "root"; // 用户名
		String password = "root"; // 密码

		Connection con = null; // 配合try..finally确保释放资源
		Statement state = null;
		ResultSet re = null;
		try {
			// 1.加载驱动：build path---导MySQL驱动包
			// DriverManager.registerDriver(new Driver());	使用这种方法需要代码中import mysql驱动的Driver类，不利于更换数据库
			//												此方法Driver会存在两个对象
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2.获取MySQL数据库的连接
			// Connection con = DriverManager.getConnection(url, user, password); //
			// url:连接哪个数据库 user:用户名 password:密码
			con = DriverManager.getConnection(url);

			// 3.获取向数据库发送sql语句的statement对象
			state = con.createStatement();

			// 4.向数据库发送sql,获取数据库返回的结果集
			re = state.executeQuery("select * from users"); // executeQuery()为向数据库发送查询语句的方法
															// ResultSet封装结果集

			// 5.从结果集中获取数据
			while (re.next()) { // re类似迭代器，指针读取每一行
				System.out.print("id:" + re.getObject("id") + "\t");
				System.out.print("name:" + re.getObject("name") + " \t");
				System.out.print("password:" + re.getObject("password") + "\t");
				System.out.print("email:" + re.getObject("email") + " \t");
				System.out.println("birthday:" + re.getObject("birthday"));
			}
		} finally {
			// 6.释放资源(释放链接)
			if (re != null) {
				try {						//确保re没有释放资源也可以执行state.close()
					re.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				re = null;
			}
			if (state != null) {
				try {						//确保state没有释放资源也可以执行con.close()
					state.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				state = null;
			}
			if (con != null) {
				try {						//确保con.close()一定会执行
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				con = null;
			}
		}
	}

}
