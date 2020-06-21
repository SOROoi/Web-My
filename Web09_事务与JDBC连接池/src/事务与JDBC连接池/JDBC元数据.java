package 事务与JDBC连接池;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/*						数据库元数据-/DatabaseMetaData /ParameterMetaData /ResultSetMetaData
	1.元数据简介：
			封装了Connection 连接的数据库、表、列的定义信息。
			类在 java.sql 包下,属于Java生态。

	2.元数据－ DataBaseMetaData(数据库元数据)
		
		DataBaseMetaData data = connection.getMetaData();
		
		DataBaseMetaData对象
			getURL()：返回一个String类对象，代表数据库的URL。
			getUserName()：返回连接当前数据库管理系统的用户名。
			getDatabaseProductName()：返回数据库的产品名称。
			getDatabaseProductVersion()：返回数据库的版本号。
			getDriverName()：返回驱动驱动程序的名称。
			getDriverVersion()：返回驱动程序的版本号。
			isReadOnly()：返回一个boolean值，指示数据库是否只允许读操作。
			

	3.元数据－ ParameterMetaData (sql信息)

		ParameterMetaData data = PreparedStatement.getParameterMetaData();
		
		ParameterMetaData对象
			getParameterCount():获得需要参数的个数
			getParameterType(int param):获得指定参数的sql类型 
			
			
	4.元数据－ ResultSetMetaData (结果集信息)
		
		ResultSetMetaData data = ResultSet.getMetaData();
		
		ResultSetMetaData对象
			getColumnCount()：返回resultset对象的列数
			getColumnName(int column)：获得指定列的名称
			getColumnTypeName(int column)：获得指定列的类型
			



 */


public class JDBC元数据 {
	
	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("");	//获取数据库连接
		DatabaseMetaData md = con.getMetaData();	//获取元数据对象
		md.getURL();
		
	}
}
