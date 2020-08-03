package MySQL数据库;

/*						MySQL数据库
 	
 	SQL: 结构化查询语言(Structured Query Language)
 	DDL: 数据定义语言(Data Definition Language)：定义数据库、数据表的结构: ceate(创建) drop(删除) alter(修改) truncate(删除)
  	DML: 数据操纵语言(Data Manipulation Language):主要用来操作数据：       insert(插入) update(更新) delete(删除)
  	DQL: 数据查询语言(Data Query Language):                                select(查询) from子句 where子句
  	DCL: 数据控制语言(Data Control Language):定义访问权限，取消访问权限，安全设置 grant
  	
  
 	0.数据库软件简介
			1.商用收费：	SQL Server：	--微软的数据库产品
						Oracle：		--Oracle公司
			
			2.免费：		MySQL：		--Oracle公司收购
						DB2：		--IBM公司，银行证券用 
						SyBase：		--少见

	1. MySQL介绍
			1.起源：
				MySQL由瑞典MySQL AB公司开发,目前属于 Oracle公司

			2.简介：
				1.MySQL是一个开源的关系型数据库管理系统
				2.MySQL分为社区版(Community Server)和企业版(Enterprise Editions)
				
	2. 安装MySQL的本质
			1.在电脑上安装一个MySQL数据库的服务器，其端口为3306，用户名为root，密码为root
			2.MySQL服务器通过服务开启和关闭
			3.通过shell命令行连接该MySQL服务器，存放数据
	
	3. MySQL下载、安装和配置
			
			1.下载
				从官网下载即可，地址：https://dev.mysql.com/downloads/mysql/
				
			2.MySQL安装方式
				1.MSI安装(Windows Installer)(--见B站收藏夹)
				2.ZIP安装	--初学者较难掌握

			3.配置环境变量
				将MySQL\bin的目录添加到环境变量path中，即可在命令行中任意目录下运行MySQL\bin下的指令
			
			4.确认安装成功
				在命令行中输入：			mysql -uroot -p		--输入账号密码操作数据库
				输入密码后，再输入:		\s					--显示mysql在本机的配置信息

				例：mysql> \s
					--------------
					mysql  Ver 14.14 Distrib 5.5.62, for Win64 (AMD64)
					
					Connection id:          5											--连接的ID号
					Current database:													--当前连接的数据库
					Current user:           ODBC@localhost								--当前连接的用户
					SSL:                    Not in use									--当前是否启用了安全链接
					Using delimiter:        ;											--当前使用的分隔符
					Server version:         5.5.62-log MySQL Community Server (GPL)		--当前服务器版本
					Protocol version:       10											--当前采用协议
					Connection:             localhost via TCP/IP						--当前链接的方式
					Server characterset:    latin1 (即ISO-8859-1)						--当前服务器字符集
					Db     characterset:    latin1 (即ISO-8859-1)						--当前数据库字符集
					Client characterset:    gbk											--当前客户端字符集
					Conn.  characterset:    gbk											--当前链接的字符集
					TCP port:               3306										--链接的端口
					Uptime:                 39 min 21 sec								--服务器已启动运行的时间
					
			5.设置默认字符集
				修改my.ini配置文件(--见浏览器书签栏)
					
			6.如何卸载
				1.打开控制面板，删除MySQL
				2.手动删除MySQL安装目录的所有文件C:\Program Files\MySQL
				3.手动删除MySQL数据存放文件C:\ProgramData\MySQL
				
	4. 关系型数据库
			1.用来描述实体和实体之间的关系，如：男生和女生、学生和班级、员工和部门
			2.E-R关系图(Entity Relationship实体联系)
					方框：表示实体
					椭圆：表示属性
					菱形：表示关系
					
	5.修改MySQL字符集
			1.查看字符集：mysql> show variables like 'char%';
		
			2.修改字符集：mysql> SET character_set_client = utf8 ;
					mysql> SET character_set_connection = utf8 ;
		 			mysql> SET character_set_database = utf8 ;
		 			mysql> SET character_set_results = utf8 ;
		 			mysql> SET character_set_server = utf8 ;
		 			mysql> SET collation_connection = utf8 ;
		 			mysql> SET collation_database = utf8 ;
		 			mysql> SET collation_server = utf8 ;
		 			
		 	3.解决编码问题： set names 'utf8';
		 	    它相当于下面的三句指令：
					SET character_set_client = utf8;
					SET character_set_results = utf8;
					SET character_set_connection = utf8;
*/

/*									MySQL的使用
 	1.数据库服务器、数据库和表的关系
 		Client ----- MySQL ----- DB -----表
 		(客户端)			   			-----表
 						   ----- DB
 
 	2.如何存放数据
 			1.装好mysql，只是装好了一个数据库管理程序，要想通过这个程序保存数据，需要在这个程序下创建多个库来保存。(一般开发人员会针对每一个
 		应用创建一个数据库)
 			2.而库又是使用表保存数据的，所以库创建好后，我们又会在数据库下面创建多个表，以保存程序中不同实体的数据。
 	
 	3. 操作数据库---SQL语句
 		注： 1.[]中的数据可写可不写
 		注： 2.sql语句末尾要加;分隔(Windows不需要)
 		
 			0.登陆数据库：
 				mysql -uroot -p		--输入账号密码登陆数据库
 			0.退出数据库
 				exit;
 			
 			1.创建数据库：		(字符集、校对规则，见MySQL5.1参考手册10.10)
				create database 数据库1;	 					---创建"数据库1"
 							 	character set 字符集1;	---指定数据库采用的"字符集1" 如：utf8
		   						collate 校对规则1;		---指定数据库字符集的"校对规则1" 如：utf8_general_ci

 			2.查看数据库：
				show databases;					---查看当前数据库服务器中的所有数据库
 				
 				select database();				--查看正在使用的数据库
 				
 				(show create database 数据库1;	---查看"数据库1"的定义信息)
 											
  			3.切换数据库：
 				use 数据库1;			--切换到"数据库1"
 				
 			4.删除数据库：
 				drop database 数据库1;		---删除"数据库1"
 				
 			5.修改数据库：
 				alter database 数据库1 character set 字符集1;  --修改"数据库1"的字符集为"字符集1"
  									   collate 校对规则1;	  --修改"数据库1"的校对规则为“校对规则1”
  							
 			6.备份数据库
 				1.备份库/表中的数据：
	  				mysqldump -u用户名 -p 数据库1 > g:文件名.sql (window命令)
                    
	  				mysqldump -u用户名 -p 数据库1 表1 > g:文件名.sql (window命令)
				    
			   	2.恢复tt库：（恢复数据只能恢复表的数据，不能恢复库）
			   		方式一：
					  2.1  为恢复库，要先创建库：  create database tt;
					  2.2  再恢复tt库 ：
						use tt; 
						source c:/tt.sql;	    （source:可以执行一个 sql脚本    sql命令）
					
					方式二：
					  2.1  为恢复库，要先创建库：  create database tt;
					  2.2  恢复库： mysql -uroot -proot tt<c:\1.sql   (window命令)
 					
 					
 	4. 操作表（--见黑马49期--）
 	 		1.创建表：
 	 			create table 表1(			--创建"表1"
 	 				列1  列的类型(长度) 约束,
 	 				列2  列的类型(长度) 约束
 	 			);
 				--------列的约束--------	
 				1.主键约束：primary key									--不允许为空，不允许重复
 					1.1.删除主键：alter table 表1 drop primary key;
 					1.2.定义主键自动增长：auto_increment					--定义auto_increment后，
																			主键可以插入null值
																			如：insert into test0 values(null,'a');
 					
 				2.唯一约束：unique 										--该属性唯一，可以为空
 				3.非空约束：not null										--该属性不可为空
 				4.外键约束：constraint 约束名称1 foreign key(本表列1) references 他表(他表列1);	--定义本表列1的值必须来自其他表列1的值
 				
 				-----------------------
 				例：create table student(		--创建表student ,列为id、name、age、sex
 					id int primary key,
 					name varchar(31) not null auto_increment,
 					identity varchar(50) unique,
 					age int,
 					sex int，
 					constraint student_FK foreign key(name) references parents(child_id)	--外键约束
 					);
 					
 				--------数据类型-------
 				Java中			MySQL中
 				char/String		char()		char(3):	 '一  '	'一空格空格'		--固定长度
 								varchar()	varchar(3):  '一  '	'一'				--可变长度			
			 								长度代表的是字符的个数		
			 								 					
 				int				int				 								
 				double			double(总长度,小数点后长度)：		double(10,2)
 				float			float(总长度,小数点后长度)：		float(10,2)
 				BigDecimal		decimal(总长度,小数点后长度)：		decimal(10,2)	--默认是整数
 				
 				boolean			bit
 				
 				Date			date:		yyyy-MM-dd
 				Time			time:		hh:mm:ss
 				Timestamp		datetime:	yyyy-MM-dd hh:mm:ss	--默认值是null
 				Timestamp		timestamp:	yyyy-MM-dd hh:mm:ss	--默认使用当前时间
 								
 				String			text:	--主要用来存放文本	    -大数据
 				byte[]			blob：	--存放的是二进制			-大数据
 								
			2.查看表:
				show tables;		--查看数据库中所有表
				
				desc 表1;		--查看"表1"的结构 
				
				(show create table 表1;	--查看"表1"的定义)
 
 			3.删除表：
 				drop table student;
 				
 			4.修改表：
 				1.添加列(add)：
 					alter table 表1 add 列1 列的类型 列的约束;		--向"表1"中添加 "列1"(列的类型、列的约束)
 					(--alter table student add chengji int not null;)
 				
 				2.修改列的类型(modify):
 					alter table 表1 modify 列1 列的类型;			--修改"表1"中"列1"的类型
 					(--alter table student modify sex varchar(2);)
 				
 				3.修改列名(change):
 					alter table 表1 change 列1 列x 列的类型;		--将"表1"中"列1"的列名改为"列x"
 					(alter table student change sex gender varchar(2);)
 				
 				4.删除列(drop)
 					alter table 表1 drop 列1;					--删除"表1"中的"列1"
 					(--alter table student drop chengji;)
 				
 				---一般不要使用---
 				5.修改表名(rename)
 					rename table 表1 to 表x;						--将"表1"名称改为"表x"
 					(--alter table student to person;)
 				
 				6.修改表的字符集(character set)
 					alter table 表1 character set 字符集1;		--修改"表1"的字符集为"字符集1"
 					(--alter table student character set gbk;)
 					
 	5. Sql完成对表中数据的CRUD操作
 	
			1.表中插入记录：
				---插入全列数据---
				insert into 表1(列1，列2，列3) values(值1，值2，值3);		--向"表1"中插入一列数据(全列都有数据)
				(--insert into student(id,name,age,gender) values(1,'zhangsan',24,1);)
				
				
				---插入部分数据---
				insert into 表1(列3，列4) values(值3，值4);
				(--insert into student(age,gender) values(24,1);)		--向"表1"中插入一列数据(部分列有数据)
				
				---插入全列数据的简单写法---
				insert into 表1 values(值1，值2，值3);
				(--insert into student values(1,'zhangsan',24,1);)		
				
				---批量插入,多条全列数据---
				insert into 表1 values(值1，值2，值3),
									  (值1，值2，值3),
									  (值1，值2，值3);		--向"表1"中插入三条数据
				(--insert into student values(2,'zhangsan',24,1),
											 (3,'zhangsan',24,1),
											 (4,'zhangsan',24,1);)
			
			2.查看表中记录
				select * from 表1;			--查看"表1"中的数据
				(--select * from student;)
 			
 			3.删除表中记录(删除表)：
 				1.	delete from 表1 [where 条件];		--一条一条删除"表1"中符合条件的记录
	 				(--delete from student where id=1;)	
	 				(--delete from student;)			--全部删除
 				
 				2.	truncate table 表1;					--先删除"表1"，再创建"表1",记录全部消失
 				
 				3.	drop table student;					--删除表
 				
 				---面试：delete删除数据和 truncate删除数据的差别---
 				delete：  DML 一条一条删除表中数据
 				truncate：DDL 先删除表再重建表
 				
 				---效率：哪个更高---
 				数据少：delete效率高
 				数据多：truncate效率高
 				
 			4.更新表记录:
 				update 表1 set 列1=列值,列2=列值2 [where 条件];		--修改"表1"中符合条件的记录的列值
 				(--update student set name='石破天' where id=1; )	--把id为1的记录,name改为石破天(如果参数为字符串、日期要加单引号)
 				(--update student set name='石破天'; )				--全部修改
 				
 			5.查询记录、并显示(select)：
 				1.通用格式：
 					select [distinct][ * ][列1,列2] from 表1 [where 条件]		--选择显示符合条件的属性(列)
 				
					1.1.查询所有商品：
						select * from product;					--查询表product中所有记录
					
					1.2.查询商品名称和商品价格:
						select pname,price from product;		--查询表product中,名称pname和商品价格price
						
					1.3.去掉重复的值:(查询商品所有价格)
						select distinct price from product;		--查询表product中所有价格price,去掉price重复的记录
						
				2.别名查询： as关键字(as可省略)
					2.1.表的别名：(主要用于多表查询)
						select p.pname,p.price from product as p;	--设表product的别名为p,查询显示表p的pname、price
						
					2.2.列的别名：
						select pname as 商品名称,price as 商品价格 from product;	--查询表product中pname、price，并显示为"商品名称"、"商品价格"
						
				3.运算查询：仅在查询结果上做了运算 + - * /
					select *,price*1.5 from product;			--显示表product所有数据，并对price*1.5后显示
					select *,price*1.5 as 折后价 from product;	--显示表product所有数据，并对price*1.5后，显示为"折后价"
					
				4.条件查询:(where + 属性 + 关系运算符/like + 逻辑运算符)
					4.0. where后的关系运算符：
						 >		--大于
						 >=		--大于等于
						 <		--小于
						 <=		--小于等于
						 =		--等于
						 <>		--不等于(标准sql语句)
						 !=		--不等于(非标准sql语句)
					
	 					4.1.查询商品价格大于60的所有商品信息：
	 						select * from product where price > 60;		--查询表product中所有price>60的记录
	 						
	 					4.2.查询商品价格不等于88的所有商品：
	 						select * from product where price <> 88;	--查询表product中所有price不等于88的记录
	 				
	 					4.3.查询商品价格在10到100之间:
	 						select * from product where price > 10 and price < 100;		--查询表product所有price在10到100之间的记录
	 						
	 						select * from product where price between 10 and 100;		--查询表product所有price在10到100之间的记录
 					
 					4.4.where后的逻辑运算符：
 						and		--与
 						or		--或
 						not		--非
 						
 						4.5.查询商品价格小于35或者大于900的商品信息:
 						select * from product where price < 35 or price > 900;		--查询表product中所有price小于35 or 大于900的记录
 					
 					4.6.where后的模糊查询 like关键字：
 						 _		--代表一个字符
 						 %		--代表多个字符
 						 
 						4.7.查询名字中带有饼的所有商品信息：
 							select * from product where pname like '%饼%';		--查询表product中所有pname中带有"饼"的记录
 					
 						4.8.查询名字中第二个字为熊的所有商品信息：
 							select * from product where pname like '_熊%';		--查询表product中所有pname中第二个字为"熊"的记录
 							
 					4.9.where后 在某个范围中获得值 in关键字：
 						
 						4.10.查询商品分类ID在1、4、5中的所有商品信息:
 							select * from product where pid in (1,4,5);			--查询表product中所有pid包含于集合(1,4,5)的记录
 							
 				5.排序查询：(from后 where后	order by + 属性 + asc/desc)
 					asc:		--升序(默认的排序方式)		--ascend
 					desc: 		--降序					--descend
 					
 						5.1.查询所有商品，按照价格进行排序:
 							select * from product order by price;		--查询表product中所有记录，按price排序，用默认排序方式显示
 							
 						5.2.查询所有商品，按照价格进行降序排序:
 							select * from product order by price desc;		--查询表product中所有记录，按price排序，用降序显示
 							
 						5.3.查询名称有"小"的商品，按价格降序排序：
 							select * from product where pname like '%小%' order by price desc;	----查询表product中所有pname有"小"的记录，按price排序，降序显示
 							
 				6.聚合函数：(where后不可接聚合函数)
 					sum(..):		--求和
 					avg(..):		--求平均值
 					count(1):		--统计数量
 					max(..):		--最大值
 					min(..):		--最小值
 						
 						6.1.获得所有商品价格的总和:
 							select sum(price) from product;			--查询表product中所有记录，显示price总和--sum(price)的值  
 							
						6.2.获得所有商品的平均价格:
							select avg(price) from product;			--查询表product中所有记录，显示price均值--avg(price)的值
							
						6.3.获得所有商品的个数:
							select count(1) from product;			--查询表product中所有记录，显示记录的数量--count(1)的值
 					
 						---------注：where后不可接聚合函数---------
 						6.4.查询商品价格大于平均价格的所有商品信息(子查询)
 							select * from product where price > (select avg(price) from product);	----查询表product中所有price大于平均price的记录
 						
 				7.分组(where后	group by关键字 + 属性)：
 						7.1.根据pid属性分组，分组后统计商品个数：
 							select pid,count(*) from product group by pid;		--根据pid属性分组，显示pid列、count(*)列的数据记录
 							
 						7.2.根据pid分组，分组统计每组商品的平均价格，并显示商品平均价格大于60的信息:
 							select pid,avg(price) 					--显示pid列、avg(price)列
 							from product group by pid				--根据pid属性分组
 							having avg(price) >60;					--显示avg(price)大于60的组
 
						------注---------
						having关键字：可以接聚合函数，出现在分组之后		--用于条件过滤
						where关键字：不可接聚合函数，出现在分组之前		--用于条件过滤
						
				8.select语句的编写顺序、执行顺序：
						
						8.1.编写顺序: 
							S..F..W..G..H..O		select.. from.. where.. group by.. having.. order by
						
						8.2.执行顺序:
							F..W..G..H..S..O		from.. where.. group by.. having.. select.. order by
						
 */	

public class MySQL详解 {

}
