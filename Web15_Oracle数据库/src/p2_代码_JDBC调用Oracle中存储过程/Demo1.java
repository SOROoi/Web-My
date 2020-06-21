package p2_代码_JDBC调用Oracle中存储过程;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import oracle.jdbc.driver.OracleTypes;

/*
 			1.导入驱动包
            2.注册驱动
            3.获取连接
            4.获取执行SQL的statement
            5.封装参数
            6.执行SQL
            7.获取结果
            8.释放资源  
 * 
 * 
 	存储过程：
			create or replace procedure proc_updatesal(vempno in number,vsal out number)
			is
			   
			begin
			  select sal*12 + nvl(comm,0) into vsal from temp where empno = vempno;
			end;

 */
public class Demo1 {

	//调用存储过程
	@Test
	public void test1() throws Exception {
		//1.加载驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//2.获取连接
		String url = "jdbc:oracle:thin:@192.168.61.100:1521:orcl";
		String user = "wxt";
		String password = "wxt";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println(con);
		//3.获取执行存储过程的statement,执行调用存储过程的sql语句,设定?的值(第一个?，是存储过程中in类型参数，用setXxx()方法设定;
		//														            第二个?，是存储过程中out类型参数，用registerOutParameter()方法设定)
		String sql = "{call proc_updatesal(?,?)}";
		CallableStatement cstate = con.prepareCall(sql);
		//设置输入参数
		cstate.setInt(1, 7788);
		//注册输出参数
		cstate.registerOutParameter(2, OracleTypes.NUMBER);	//在Oracle驱动包中的 oracle.jdbc.driver.OracleTypes的字段中选择
		//4.执行sql
		cstate.execute();
		//5.获取值
		int i = cstate.getInt(2);
		System.out.println("7788年薪为"+i);
		
		//6.释放资源
		cstate.close();
		con.close();
	}
	
	//调用存储函数
	@Test
	public void test2() throws Exception {
		//1.加载驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//2.获取连接
		String url = "jdbc:oracle:thin:@192.168.61.100:1521:orcl";
		String user = "wxt";
		String password = "wxt";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println(con);
		//3.获取执行存储函数的statement,执行调用存储函数的sql语句,设定?的值
		String sql = "{?= call func_getsal(?)}";
		CallableStatement cstate = con.prepareCall(sql);
		//设置输入参数
		cstate.setInt(2, 7788);
		//注册输出参数
		cstate.registerOutParameter(1, OracleTypes.NUMBER);	//在Oracle驱动包中的 oracle.jdbc.driver.OracleTypes的字段中选择
		//4.执行sql
		cstate.execute();
		//5.获取值
		int i = cstate.getInt(1);
		System.out.println("7788年薪为"+i);
		
		//6.释放资源
		cstate.close();
		con.close();
	}
}
