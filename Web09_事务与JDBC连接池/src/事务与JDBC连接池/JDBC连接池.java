package 事务与JDBC连接池;

import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import javax.sql.DataSource;



/*						数据库连接池
 	
 	1.应用程序直接从数据库--获取连接（效率低）
 	
		1.直接获取：从数据库获取连接(Connection)，连接释放时连接返回数据库.
 		2.缺点：用户每次请求都向数据库获得连接，而数据库创建连接需要消耗较大的资源，创建时间长、获得连接慢。
 			--假设网站一天10万访问量，数据库服务器就需要创建10万次连接，极大的浪费数据库的资源，并且极易造成数据库服务器内存溢出、拓机。

 	2.使用数据库连接池--获取连接（效率高）
 	
		1.连接池获取：连接池存放n个连接(Connection)，用连接池中获得的连接与数据库交互，连接释放时返回连接池(连接池内连接数量保持恒定).
 		2.优点： 每次获取向连接池请求，获得连接快、降低了数据库的资源消耗
 			      

	3.编写数据库连接池(DataSource接口)
		
		1.如何编写连接池：
			1.编写连接池需实现javax.sql.DataSource接口。DataSource接口中定义了两个重载的getConnection方法：
						Connection getConnection() 
						Connection getConnection(String username, String password)

		2.实现DataSource接口，并实现连接池功能的步骤：
			1.在DataSource构造函数中批量创建与数据库的连接，并把创建的连接加入LinkedList对象中。
			2.实现getConnection方法，让getConnection方法每次调用时，从LinkedList中取一个Connection返回给用户。
			3.当用户使用完Connection，调用Connection.close()方法时，Collection对象应保证将自己返回到LinkedList中,而不要把conn还给数据库。
				（Collection保证将其返回到LinkedList中是此处编程的难点：--使用动态代理）
	
	4.如何使调用close()后,con对象返回连接池中：------增强close()方法
		
			 在实际开发，发现对象的方法满足不了开发需求时，有三种方式对其进行增强
			 1.写一个connecton子类，覆盖close方法，增强close方法	//一般不用
			 2.用包装设计模式，设计一个新类代替该类，增强close方法	//常用，但新类要添加"被增强类"的所有方法
			 3.用动态代理、aop、面向切面编程						//常用，使用该方案
	
 */

/*						开源数据库连接池(DataSoruce)
	1.简介：
		      现在很多WEB服务器(Weblogic, WebSphere, Tomcat)都提供了DataSoruce的实现，即连接池的实现。通常我们把DataSource的实现，按其英
		文含义称之为数据源，数据源中都包含了数据库连接池的实现。
		
	2.开源组织提供的数据源的独立实现：
			1.DBCP 数据库连接池 
			2.C3P0  数据库连接池
			
 	3.连接池实际应用：
 		1.不需要编写连接数据库代码，直接从数据源(连接池)获得数据库的连接。
 			原因1：开源连接池中已经写好了：加载驱动、获取连接、	创建连接池、返回连接池的代码
 			原因2：配置文件中需要配置：驱动包目录、url、user、password、隔离等级
 		
 		2.需要：	开启数据库、
 				导对应数据库的驱动包、
 				配置文件配--驱动包目录--url--user--password--隔离等级、
 				导数据源(连接池)的jar包
 			
 				
 	----------------------------------------------------------------------------------------------			
 	4.DBCP数据源(连接池)
 		1.简介：
 				DBCP是 Apache组织下的开源连接池实现
 				
 		2.使用：
 				使用DBCP数据源，应用程序应增加两个 jar 文件，一个配置properties文件：
				Commons-dbcp.jar：		连接池的实现
				Commons-pool.jar：		连接池实现的依赖库
				dbcpconfig.properties	配置信息
		
		3.获得DBCP连接池：
				BasicDataSourceFactory factory = new BasicDataSourceFactory();		//创建连接池工厂
				DataSource pool = factory.createDataSource(Properties prop);		//获得连接池对象

	5.C3P0 数据源(连接池)		(--配置文件c3p0-config.xml	见src/c3p0连接池)
		1.简介：
 				C3P0是 Spring内置的连接池
 				
 		2.使用：
 				使用C3P0数据源，应用程序应增加两个 jar 文件，一个配置xml文件：
				c3p0-xx-pre1.jar
				mchange-commons-xx.jar
				c3p0-config.xml		配置信息		//将该文件放在classpath下、或/WEB-INF/classes下
		
		3.获得C3P0连接池：
				DataSource pool = new ComboPooledDataSource();
 			
 	6.Tomcat数据源(连接池)	(--配置文件context.xml	见src/Tomcat连接池)
 		1.简介：
 				Tomcat内置了一个连接池
 				
 		2.使用：
 				1.让Tomcat在启动时创建连接池---在项目/META-INF目录下创建context.xml,配置Context信息：
 				2.数据库驱动jar文件需放置在tomcat的lib下
 				3.web项目，不需导 连接池jar包

		3.获得Tomcat连接池：(通过服务器提供的jndi容器获得对象)
				Context initCtx = new InitialContext();			//初始化jndi容器
				Context envCtx = (Context) initCtx.lookup("java:comp/env");		//获得jndi容器
				DataSource pool = (DataSource)envCtx.lookup("jdbc/EmployeeDB");		//通过jndi找到连接池对象
		
		4.拓展：
				删除tomcat中的应用资源时，应同时删除tomcat的 conf/Catalina/localhost目录下，应用资源的xml配置文件。否则服务器启动会报错

 	
 	
 */

/*						包装设计模式
	包装类设计模板：
			1.定义一个类，实现与被增强类相同的接口(添加"被增强类"的所有方法)
			2.在类中定义一个变量，记住被增强对象
			3.定义一个构造函数，接收被增强对象
			4.覆盖想增强的方法
			5.对于不想增强的方法，直接调用目标对象（被增强对象）的方法
 
 */

public class JDBC连接池 {

}

class  JdbcPool implements DataSource{		//数据库连接池
	
	private static LinkedList<Connection> list = new LinkedList<Connection>();
	private static Properties config = new Properties();
	static{
		try {
			config.load(JdbcPool.class.getClassLoader().getResourceAsStream("db.properties"));
			
			Class.forName(config.getProperty("driver"));
			for(int i=0;i<10;i++){
				Connection conn = DriverManager.getConnection(config.getProperty("url"), config.getProperty("username"), config.getProperty("password"));
				list.add(conn);
			}
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	
	//conn.close()
	/* 在实际开发，发现对象的方法满足不了开发需求时，有三种方式对其进行增强
	 * 1.写一个connecton子类，覆盖close方法，增强close方法
	 * 2.用包装设计模式
	 * 3.用动态代理    aop 面向切面编程
	 */
	public Connection getConnection() throws SQLException {
		
		if(list.size()<=0){
			throw new RuntimeException("数据库忙，请稍会再来！！");
		}
		Connection conn = list.removeFirst();   //mysqlconnection   C
		MyConnection my = new MyConnection(conn);
 		return my;      //my-------preparedStatment   commit   createStatement  close
	}
	
		//包装类设计模板
	//1.定义一个类，实现与被增强相同的接口
	//2.在类中定义一个变量，记住被增强对象
	//3.定义一个构造函数，接收被增强对象
	//4.覆盖想增强的方法
	//5.对于不想增强的方法，直接调用目标对象（被增强对象）的方法
	
	class MyConnection implements Connection{
		private Connection conn;
		public MyConnection(Connection conn){
			this.conn = conn;
		}
		public void close(){
			list.add(this.conn);
		}
		public void clearWarnings() throws SQLException {
			this.conn.clearWarnings();
			
		}
		public void commit() throws SQLException {
			this.conn.commit();
			
		}
		public Statement createStatement() throws SQLException {
			return this.conn.createStatement();
		}
		public Statement createStatement(int resultSetType,
				int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return this.conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		public Statement createStatement(int resultSetType,
				int resultSetConcurrency) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public boolean getAutoCommit() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}
		public String getCatalog() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public int getHoldability() throws SQLException {
			// TODO Auto-generated method stub
			return 0;
		}
		public DatabaseMetaData getMetaData() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public int getTransactionIsolation() throws SQLException {
			// TODO Auto-generated method stub
			return 0;
		}
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public SQLWarning getWarnings() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public boolean isClosed() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}
		public boolean isReadOnly() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}
		public String nativeSQL(String sql) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public CallableStatement prepareCall(String sql) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public PreparedStatement prepareStatement(String sql,
				int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public PreparedStatement prepareStatement(String sql,
				int resultSetType, int resultSetConcurrency)
				throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public PreparedStatement prepareStatement(String sql,
				int autoGeneratedKeys) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public PreparedStatement prepareStatement(String sql,
				int[] columnIndexes) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public PreparedStatement prepareStatement(String sql,
				String[] columnNames) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public PreparedStatement prepareStatement(String sql)
				throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		public void rollback() throws SQLException {
			// TODO Auto-generated method stub
			
		}
		public void rollback(Savepoint savepoint) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		public void setCatalog(String catalog) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		public void setHoldability(int holdability) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		public void setReadOnly(boolean readOnly) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		public Savepoint setSavepoint() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public Savepoint setSavepoint(String name) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		public void setTransactionIsolation(int level) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public Clob createClob() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Blob createBlob() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public NClob createNClob() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public SQLXML createSQLXML() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean isValid(int timeout) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public String getClientInfo(String name) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Properties getClientInfo() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setSchema(String schema) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public String getSchema() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void abort(Executor executor) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public int getNetworkTimeout() throws SQLException {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	
	

	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setLogWriter(PrintWriter arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setLoginTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}