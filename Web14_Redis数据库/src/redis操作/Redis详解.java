package redis操作;

/*						nosql 数据库
	
		
	1.NOSQL是什么
		NoSQL是not only sql，是一种非关系型数据库。优点有：高性能、易拓展、灵活、高可用。
		存储格式有key,value形式、文档形式、图片形式等，可以存储基础类型、对象、集合等格式。关系型只支持基础类型。
		适用于web2.0纯动态网站.
	
		
	2.NOSQL和关系型数据库
		nosql优点：
			1.查询速度快：nosql数据库将数据存储于缓存之中，关系型数据库将数据存储在硬盘中。
			2.性能高：nosql的存储格式是key,value形式、文档形式、图片形式等，可以存储基础类型、对象、集合等格式。关系型只支持基础类型。
			3.扩展性：nosql数据间没有耦合，扩展方便。
			
		关系型数据库优点：
			1.复杂查询：方便的在一个表以及多个表之间做非常复杂的数据查询.
			2.支持事务：事务支持使得数据访问的安全性很高.
			
			
	3.主流的NOSQL产品
		•	键值(Key-Value)存储数据库
				相关产品： Redis、Tokyo Cabinet/Tyrant、Voldemort、Berkeley DB
				典型应用： 内容缓存，主要用于处理大量数据的高访问负载。 
				数据模型： 一系列键值对
				优势： 快速查询
				劣势： 存储的数据缺少结构化
		•	列存储数据库
				相关产品：Cassandra, HBase, Riak
				典型应用：分布式的文件系统
				数据模型：以列簇式存储，将同一列数据存在一起
				优势：查找速度快，可扩展性强，更容易进行分布式扩展
				劣势：功能相对局限
		•	文档型数据库
				相关产品：CouchDB、MongoDB
				典型应用：Web应用（与Key-Value类似，Value是结构化的）
				数据模型： 一系列键值对
				优势：数据结构要求不严格
				劣势： 查询性能不高，而且缺乏统一的查询语法
		•	图形(Graph)数据库
				相关数据库：Neo4J、InfoGrid、Infinite Graph
				典型应用：社交网络
				数据模型：图结构
				优势：利用图结构相关算法。
				劣势：需要对整个图做计算才能得出结果，不容易做分布式的集群方案。
 */


/*						Redis 数据库	
	
	0.Redis是什么?
		1.由C语言开发的、非关系型(NoSQL)数据库，存储键值对数据。
		2.适用于web2.0纯动态网站.
		3.需经常查询、安全性无要求的数据，可以使用redis.
	
	0.redis的应用场景
		1.缓存（数据查询、短连接、新闻内容、商品内容等等）
		2.聊天室的在线好友列表
		3.任务队列。（秒杀、抢购、12306等等）
		4.应用排行榜
		5.网站访问统计
		6.数据过期处理（可以精确到毫秒
		7.分布式集群架构中的session分离
		
		
	1.Redis安装
		安装在Linux系统下，
		安装步骤详见：/Web11_Linux系统/src/黑马49期/Linux详解.java
		
		1.官网：https://redis.io
		2.中文网：http://www.redis.net.cn/
		3.解压直接可以使用：
			redis.windows.conf：配置文件
			redis-cli.exe：redis的客户端
			redis-server.exe：redis服务器端
			
			
	2.Redis命令：见/Web14_Redis数据库/src/redis操作/Redis.pdf
		
		
	3.Redis的持久化
		1.持久化:
			Redis的高性能是由于其将所有的数据都存储在了内存中，为了使Redis在重启后仍能保证数据不丢失，需将数据从内存同步到硬盘中，即持久化
		
		2.Redis持久化的方式：
			1.RDB持久化(默认支持,无需配置)：
				该机制在指定时间间隔内将内存中数据快照写入磁盘,保存在bin下dump.rdb中（redis.conf中可修改快照参数）
				
			2.AOF持久化(默认不开启)
				该机制以日志形式记录服务器所处理的每一个操作，在Redis服务器启动之初会读取该文件来重构数据库，以保证启动后数据库的完整。(默认
				不启用AOF，可修改redis.congf中：appendonly yes将其开启; 默认每秒记录一次操作：appendfsync everysec)
		
 */


/*						Redis 数据结构
 * 
	1.Redis 的数据		
		存储数据格式为key-value：	key都是字符串，value有5种不同的数据结构
		
	2.value 的数据结构
		1.字符串(String)
		2.哈希(hash)：map格式  
		3.列表(list)：linkedlist格式。支持重复元素
		4.集合(set)：不允许重复元素
		5.有序集合(sorted set)：不允许重复元素，且元素有顺序


 */


/*						Java程序操作Redis数据库

	1.准备：
			1.打开Linux系统的Redis数据库：redis.server redis.conf(后端模式打开)
			2.创建Java项目，导入操作redis依赖包
		
	2.直接获得Jedis连接对象(见/Web14_Redis/src/redis/Demo1.java)
			1、获得连接对象
				Jedis jedis = new Jedis("192.168.154.128",6379);
			2、存入数据
				jedis.set("username", "zhangsan");
			3、获得数据
				String name = jedis.get("username");
			
	3.通过连接池获得Jedis连接对象(见/Web14_Redis/src/redis/Demo1.java)
			0.创建连接池的配置对象
				JedisPoolConfig con = new JedisPoolConfig();
				con.setMaxIdle(30); // 最大闲置个数
				con.setMinIdle(10); // 最小闲置个数
				con.setMaxTotal(30); // 最大连接数
			1.创建jedis连接池
				JedisPool pool = new JedisPool(con, "192.168.154.128", 6379);
			2.从连接池中获得jedis连接对象
				Jedis jedis = pool.getResource();
			3.操作数据库
				jedis.set("xxx", "yyy"); // redis中只能存字符串,可以用json代替传入对象
				System.out.println(jedis.get("xxx"));
			4.关闭jedis资源
				jedis.close();
	
	4.Jedis连接池工具-JedisUtil(见/Web14_Redis/src/redis/util/JedisUtil.java)
*/


/*						Linux安装 redis

  	1.下载安装包
  		1.官网：https://redis.io
		2.中文网：http://www.redis.net.cn/
	
	
	2.安装步骤：
		可参考：	/Web11_Linux系统/src/安装/p2_Linux软件安装&Redis入门.pdf
				https://www.cnblogs.com/john-xiong/p/12098827.html
		
		
		0.将安装包 redis.tar.gz传输至Linux中
		
		1.解压：
			tar -xzvf redis.tar.gz
			
		2.安装：
			cd redis								--进入 解压文件夹
			make									--编译 redis		(gcc套件	将.c文件编译为.o文件)								
			make install PREFIX=/usr/local/redis	--安装到指定文件夹
			
		3.复制配置文件到安装目录下:
			cp redis.conf /usr/local/redis			--redis启动需要,包含端口信息
		
		4.将redis配置为后台启动：
			vi /usr/local/redis/redis.conf 			--将daemonize no 改成daemonize yes
		
		
  		5.指定配置文件,启动 redis:
  			/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf 
  			
  		 .查看服务是否启动,redis默认端口6379：
  			ps -ef | grep -i redis
  			
  		6.关闭 redis：
  			/usr/local/redis/bin/redis-cli shutdown
  			
  			
  		( 将redis加入到开机启动:
  			vi /etc/rc.local 
		    在里面添加内容：
		    /usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf (意思就是开机调用这段开启redis的命令)
		 )
		 
	3.常用命令：
		redis-server /usr/local/redis/redis.conf 		--启动redis
		redis-cli shutdown  							--停止redis
		ps -ef | grep -i redis							--查看服务是否启动，6379端口
		
		redis-cli										--连接本地redis服务器
		redis-cli -h 127.0.0.1 -p 6379					--连接远程redis服务器
		(连接后窗口)ping									--测试客户端与redis连接是否正常,正常则回复pong
		(连接后窗口)exit/quit								--断开连接 (或Ctrl+C)
		
		# 卸载redis：
		rm -rf /usr/local/redis 			--删除安装目录
		rm -rf /usr/bin/redis-* 			--删除所有redis相关命令脚本
		rm -rf /root/download/redis-4.0.4 	--删除redis解压文件夹
  			
  	4.重要的3个可执行文件：(安装目录bin包下)
		redis-server：	Redis服务器程序
		redis-cli：		Redis客户端程序，它是一个命令行操作工具。也可以使用telnet根据其纯文本协议操作。
		redis-benchmark：Redis性能测试工具，测试Redis在你的系统及配置下的读写性能
	
	5.开启远程访问
		1.修改redis 配置文件：
			vim redis.conf		
		
		2.将第70行的bind注释，第90行将protected-mode改为no，
			设置密码，
			取消第502行的注释，并修改密码，
			requirepass
		
		3.最后重启redis：

*/
public class Redis详解 {

}
