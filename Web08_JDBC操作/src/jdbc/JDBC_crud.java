package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.util.JdbcUtil;

/*						利用JDBC操作数据库 crud
 	用于：
	 		增
	 		删
	 		改
	 		查
			获取所有数据
 */

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
 */
public class JDBC_crud {
	private Connection con = null;	//创建该JDBC2对象时即加载Connection对象，此对象中使用一个Connection，防止Connection创建过多
	{																							//因为数据库的连接数是有限制的
		try {
			JdbcUtil.setDriver();			//加载驱动
			con = JdbcUtil.getConnection();	//获取连接器
		} catch (SQLException e) {
			System.out.println("获取连接器失败");	//获取连接器失败抛出初始化异常
		}
	}
	
	// 增加一行数据
	public void insert(){
		Statement state = null;
		ResultSet re = null;
		try {
			state = con.createStatement();
			int i = state.executeUpdate("insert into users values(5,'wangwu','123456','wangwu@sinq.com','1979-12-04');");
			if (i > 0) {
				System.out.println("插入数据成功");
			}
			re = state.executeQuery("select * from users;");
			while(re.next()) {
				System.out.println(re.getObject("id"));
				System.out.println(re.getObject("name"));
				System.out.println(re.getObject("password"));
				System.out.println(re.getObject("email"));
				System.out.println(re.getObject("birthday"));
			}
		} catch(SQLException e){
			e.printStackTrace(); 		//插入失败
		}finally { // 确保con,state,re使用完被释放
			JdbcUtil.close(re, state, con);
		}

	}

	// 删除一行数据
	public void delete(){
		Statement state = null;
		ResultSet re = null;
		try {
			state = con.createStatement();
			int i = state.executeUpdate("delete from users where id=4;");
			if (i > 0) {	//i为sql语句影响数据库的行数，大于0表示操作成功
				System.out.println("删除数据成功");
			}
			re = state.executeQuery("select * from users;");
			while(re.next()) {
				System.out.println(re.getObject("id"));
				System.out.println(re.getObject("name"));
				System.out.println(re.getObject("password"));
				System.out.println(re.getObject("email"));
				System.out.println(re.getObject("birthday"));
			}
		} catch(SQLException e){
			e.printStackTrace(); 		//删除失败
		}finally { // 确保con,state,re使用完被释放
			JdbcUtil.close(re, state, con);
		}

	}
	
	// 修改一行数据
	public void update(){
		Statement state = null;
		ResultSet re = null;
		try {
			state = con.createStatement();
			int i = state.executeUpdate("update users set name='god',password='654321' where id=3;");
			if (i > 0) {	//i为sql语句影响数据库的行数，大于0表示操作成功
				System.out.println("修改数据成功");
			}
			re = state.executeQuery("select * from users;");
			while(re.next()) {
				System.out.println(re.getObject("id"));
				System.out.println(re.getObject("name"));
				System.out.println(re.getObject("password"));
				System.out.println(re.getObject("email"));
				System.out.println(re.getObject("birthday"));
			}
		} catch(SQLException e){
			e.printStackTrace(); 		//删除失败
		}finally { // 确保con,state,re使用完被释放
			JdbcUtil.close(re, state, con);
		}

	}
		
	// 查询一行数据
	public void find(){
		Statement state = null;
		ResultSet re = null;
		try {
			state = con.createStatement();
			
			re = state.executeQuery("select * from users where id=1;");
			while(re.next()) {
				System.out.println(re.getObject("id"));
				System.out.println(re.getObject("name"));
				System.out.println(re.getObject("password"));
				System.out.println(re.getObject("email"));
				System.out.println(re.getObject("birthday"));
			}
		} catch(SQLException e){
			e.printStackTrace(); 		//删除失败
		}finally { // 确保con,state,re使用完被释放
			JdbcUtil.close(re, state, con);
		}

	}
	
	// 获取所有数据
	public void getAll(){
		Statement state = null;
		ResultSet re = null;
		try {
			state = con.createStatement();
			
			re = state.executeQuery("select * from users;");
			while(re.next()) {
				System.out.println(re.getObject("id"));
				System.out.println(re.getObject("name"));
				System.out.println(re.getObject("password"));
				System.out.println(re.getObject("email"));
				System.out.println(re.getObject("birthday"));
			}
		} catch(SQLException e){
			e.printStackTrace(); 		//删除失败
		}finally { // 确保con,state,re使用完被释放
			JdbcUtil.close(re, state, con);
		}

	}
		
	public static void main(String[] args) {
//		new JDBC2().insert();
//		new JDBC2().delete();
//		new JDBC2().update();
//		new JDBC2().find();
		new JDBC_crud().getAll();
	}
}
