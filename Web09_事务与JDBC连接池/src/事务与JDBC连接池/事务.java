package 事务与JDBC连接池;


/*						事务
	1.事务简介：
		1.事务指逻辑上的一组操作，这组操作的各个单元，要不全部成功，要不全部失败
		2.事务对应数据库中一组sql语句的执行，要不全部执行，否则全部不执行
		
	2.MySQL中执行一个事务：
		1.数据库如何使用事务：
				1.开启事务start transaction
				2.执行sql1
				3.执行sql2
				4.提交事务commit  //未提交commit或执行sql过程中出现异常导致未提交commit�?
									会自动回滚事务中所有sql

		2.数据库开启事务命令:
				1.start transaction;	//开启事务
				2.sql1;
				3.sql2;
				4.rollback;(手动回滚)  	//回滚事务：撤回开启事务后执行的sql语句
				5.commit;  				//提交事务

	3.事务的四大特性(ACID):		//支持事务即支持四大特性，支持四大特性即支持事务
		1.原子性（Atomicity）:原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生
		 
		2.一致性（Consistency）:事务前后数据的完整性必须保持一致
		
		3.隔离性（Isolation）:事务的隔离性是多个用户并发访问数据库时，数据库为每一个用户开启的事务，不能被其他事务的操作数据所干扰，多个并发
		  					事务之间要相互隔离
		  					--开启1事务后，这之后的其他事务的操作不可对1事务造成影响
		
		4.持久性（Durability):持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来即使数据库发生故障也不应该对其有任何影响
		
	4.事务的隔离性:
		1.不考虑隔离性的后果：
			1.脏读：在本事务内读取了另外一个事务未提交的数据
			2.不可重复读：在一个事务先后两次读取，发生数据不一致情况,第二次读取到另一个事务已经提交的数据(强调数据更新 update)
                (A 查询账户5000--->B向A账户转入5000--->A查询账户10000)
			3.虚读(幻读)：在一个事务中,第二次读取发生的记录数不同,读取到另一个事务已经提交的数据(强调数据记录变化 insert)
                (A 第一次读取,存在5条记录--->B向A插入一条新的记录--->A第二次读取 存在6条记录)



            不可重复读: 
		
		2.数据库事务的四种隔离级别：
			read uncommitted：最低级别，以上情况均无法保证 (可读未提交数据)
			read committed：可避免脏读情况发生（可读已提交数据，锁住其他线程的事务）
			repeatable read：可避免脏读、不可重复读情况的发生	（重复读取结果相同）
			serializable：可避免脏读、不可重复读、虚读情况的发生。（事务变为单线程）
			
		3.数据库中设置隔离的语句(数据库中，针对当前会话有效)
			set session transaction isolation level + 4种隔离级别		//设置当前会话事务隔离级别
			select @@tx_isolation;									//查看当前会话事务隔离级别
			
		4.不同数据库支持的隔离级别:
			MySQL:	默认repeatable read级别，支持四种隔离级别
			Oracle:	默认read committed级别，支持read committed和serializable
		
		5.JDBC中设置隔离级别：
			1.JDBC连接con对象的默认隔离级别与其连接的数据库有关，若MySQL则为repeatable read，若Oracle则为read committed
			
			2.JDBC中设定隔离级别：con = JdbcUtils.getConnection();
								con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	//设置隔离级别
			
		
 	5.JDBC中开启事务：
 		1.Jdbc默认提交方式：
 			1.Jdbc在获得一个Connection对象时，Connection对象默认会自动提交(commit)在它上面发送的SQL语句
 			2.若想开启事务，关闭这种默认提交方式即可---调用con.setAutoCommit(false);
 		
 		2.JDBC控制事务方法：
					con.setAutoCommit(false); 					//关闭自动提交(即开启事务)
					con.rollback();(手动回滚)  					//回滚事务				--不需要接commit()
					
					(3个一起使用)
					Savepoint sp = con.setSavepoint();			//设置回滚点				--返回Savepoint回滚点对象
					con.rollback(sp);			  				//回滚回滚点之后的sql		--后接commit()才有效
					con.commit();  								//提交事务				--未收到commit()自动回滚事务中所有sql
 		
 		3.例:		try{
	 					con = JdbcUtils_DBCP.getConnection();
						con.setAutoCommit(false);   	 //start transaction; 
						
						String sql1 = "update account set money=money-100 where name='aaa'";
						String sql2 = "update account set money=money+100 where name='bbb'";
						
						PreparedStatement st = conn.prepareStatement(sql1);
						st.executeUpdate();
						
						Savepoint sp = conn.setSavepoint();	 //设置回滚点
						
						st = conn.prepareStatement(sql2);
						st.executeUpdate();
						
						conn.commit();					// commit; 
						
 					}catch (Exception e) {				//发生异常
						e.printStackTrace();			//记录异常
						conn.rollback(sp);				//手动回滚至回滚点
						conn.commit();  				//手动回滚点后，一定要记得提交事务
 					}
 */

public class 事务 {

}
