package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbcc {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动
		String url = "jdbc:mysql://localhost:3306/wxtwxt?serverTimezone=UTC";
		String user = "root";
		String password = "root";
		Connection con = DriverManager.getConnection(url, user, password); // 获取连接
		System.out.println(con);

		System.out.println("-----------Statement对象执行sql----------------");
		Statement state = con.createStatement();
		String sql = null;
		// sql = "insert into javabean(field,method) values('haha','laugh');";
		// state.execute(sql); //执行sql

		ResultSet result = state.executeQuery("select * from javabean;"); // 执行查询sql，返回结果集
		while (result.next()) {
			System.out.println(result.getObject(1) + "  " + result.getObject(2) + "  " + result.getObject(3));
		}

		System.out.println("-----------PreparedStatement对象执行sql----------------");
		sql = "select * from javabean where id=?;";
		PreparedStatement pstate = con.prepareStatement(sql); // 预编译
		pstate.setString(1, "4"); // 设定占位符
		result = pstate.executeQuery();		// 执行sql查询，返回结果集
		// int row = pstate.executeUpdate();	//执行sql增删，返回数据库中影响的行数
		// pstate.execute();		//执行任意sql
		while (result.next()) {
			System.out.println(result.getObject(1) + "  " + result.getObject(2) + "  " + result.getObject(3));
		}
		
		result.close();		//释放 ResultSet资源	Statement资源	Connection资源
		state.close();
		pstate.close();
		con.close();
	}

}
