package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		// 获取MySQL连接
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/19spring?serverTimezone=UTC";
		String user = "root";
		String password = "root";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println(con);
		
		PreparedStatement pstate = con.prepareStatement("select * from account");
		ResultSet result = pstate.executeQuery();
		while(result.next()) {
			System.out.println("id:"+result.getInt(1));
			System.out.println("name:"+result.getObject(2));;
			System.out.println("money:"+result.getObject(3));
		}
		result.close();
		pstate.close();
		con.close();
	}

}
