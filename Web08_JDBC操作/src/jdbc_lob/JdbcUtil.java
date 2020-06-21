package jdbc_lob;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*					JDBC工具类
 	用于：
 		1.加载数据库驱动
 		2.获取数据库Connection连接器
 		3.释放Connection、Statement、ResultSet资源
 		
 	注意：
 		在JdbcUtil.class.getClassLoader().getResource()方法中：
 			path不以'/'开头时，默认是从该项目所在的		/bin/包(即src)下取资源；
 			path以'/'开头时，依旧默认是从该项目所在的	/bin/包(即src)下取资源；
 */
public class JdbcUtil {
	private static String url = null;
	private static String user = null;
	private static String password = null;

	//加载数据库驱动，获取配置文件的驱动地址(如mysql)、url、user、password
	public static void setDriver() {	//使用static代码块不好,配置只能加载一次，如果想修改配置文件也不行。改成方法方便改变配置信息
		try {
			Properties config = new Properties();
			
			String path = JdbcUtil.class.getClassLoader().getResource("jdbc_lob/db.properties").getPath();
			path = java.net.URLDecoder.decode(path, "UTF-8");	//小心这里的中文路径乱码问题，用此代码解决
			InputStream configIn = new FileInputStream(path);	//获取配置文件的输入流,读取配置文件,该方式修改配置文件将刷新配置
			
			config.load(configIn);
			String driver = config.getProperty("Driver");
			url = config.getProperty("URL");
			user = config.getProperty("User");
			password = config.getProperty("Password");

			Class.forName(driver); // 加载数据库驱动

		} catch (Exception e) {
			new ExceptionInInitializerError(e); // 加载失败抛出初始化异常
		}
	}
	

	//获取连接器
	public static Connection getConnection() throws SQLException {	//获取连接器失败抛出异常
		Connection con = DriverManager.getConnection(url, user, password);
		return con;
	}
	
	//释放资源
	public static void close(ResultSet re,Statement state,Connection con) {
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
