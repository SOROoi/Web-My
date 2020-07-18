package redis操作;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

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
		安装在Linux系统下，见本文档末尾。
		安装步骤详见：/Web11_Linux系统/src/黑马49期/Linux详解.java
		
		1.官网：https://redis.io
		2.中文网：http://www.redis.net.cn/
		3.解压直接可以使用：
			redis.windows.conf：配置文件
			redis-cli.exe：redis的客户端
			redis-server.exe：redis服务器端
			
			
	2.Redis命令：见/Web14_Redis数据库/src/redis操作/Redis.pdf
		
		
	3.
		
 */


/*						Linux安装 redis、启动redis

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
		
		/usr/local/redis/bin/redis-cli					--连接本地redis服务器
		./redis-cli -h 127.0.0.1 -p 6379					--连接远程redis服务器
		(连接后窗口)ping									--测试客户端与redis连接是否正常,正常则回复pong
		(连接后窗口)exit/quit								--断开连接 (或Ctrl+C)
		
		
		（# 卸载redis：
			rm -rf /usr/local/redis 			--删除安装目录
			rm -rf /usr/bin/redis-* 			--删除所有redis相关命令脚本
			rm -rf /root/download/redis-4.0.4 	--删除redis解压文件夹）
  			
  	4.重要的3个可执行文件：(安装目录bin包下)
		redis-server：	Redis服务器程序
		redis-cli：		Redis客户端程序，它是一个命令行操作工具。也可以使用telnet根据其纯文本协议操作。
		redis-benchmark：Redis性能测试工具，测试Redis在你的系统及配置下的读写性能
		redis-check-aof： 检查并修复AOF文件
		redis-check-dump： 检查并修复RDB文件
	
	5.开启远程访问
		1.修改redis 配置文件：
			vim redis.conf		
		
		2.将第70行的bind注释，第90行将protected-mode改为no，
			设置密码，
			取消第502行的注释，并修改密码，
			requirepass
		
		3.最后重启redis：

*/


/*						Redis 数据结构、命令、持久化

	1.Redis 的数据		
		存储数据格式为key-value：	key都是字符串，value有5种不同的数据结构
		
	2.value 的数据结构
		1.字符串(String)
		2.哈希(hash)：map格式  
		3.列表(list)：linkedlist格式。支持重复元素
		4.集合(set)：不允许重复元素
		5.有序集合(sorted set)：不允许重复元素，且元素有顺序



	1. 字符串类型 string
		1. 存储： set key value
			127.0.0.1:6379> set username zhangsan
			OK
			
		2. 获取： get key
			127.0.0.1:6379> get username
			"zhangsan"
			
		3. 删除： del key
			127.0.0.1:6379> del age
			(integer) 1
			
	2. 哈希类型 hash
		1. 存储： hset key field value
			127.0.0.1:6379> hset myhash username lisi
			(integer) 1
			127.0.0.1:6379> hset myhash password 123
			(integer) 1
			
		2. 获取： 
			* hget key field: 获取指定的field对应的值
				127.0.0.1:6379> hget myhash username
				"lisi"
			* hgetall key：获取所有的field和value
				127.0.0.1:6379> hgetall myhash
				1) "username"
				2) "lisi"
				3) "password"
				4) "123"
				
		3. 删除： hdel key field
			127.0.0.1:6379> hdel myhash username
			(integer) 1
	
	
	3. 列表类型 list:可以添加一个元素到列表的头部（左边）或者尾部（右边）
		1. 添加：
			1. lpush key value: 将元素加入列表左表
				
			2. rpush key value：将元素加入列表右边
				127.0.0.1:6379> lpush myList a
				(integer) 1
				127.0.0.1:6379> lpush myList b
				(integer) 2
				127.0.0.1:6379> rpush myList c
				(integer) 3
				
		2. 获取：
			* lrange key start end ：范围获取
				127.0.0.1:6379> lrange myList 0 -1
				1) "b"
				2) "a"
				3) "c"
				
		3. 删除：
			* lpop key： 删除列表最左边的元素，并将元素返回
			* rpop key： 删除列表最右边的元素，并将元素返回


	4. 集合类型 set ： 不允许重复元素
		1. 存储：sadd key value
			127.0.0.1:6379> sadd myset a
			(integer) 1
			127.0.0.1:6379> sadd myset a
			(integer) 0
			
		2. 获取：smembers key:	获取set集合中所有元素
			127.0.0.1:6379> smembers myset
			1) "a"
			
			
		3. 删除：srem key value:	删除set集合中的某个元素	
			127.0.0.1:6379> srem myset a
			(integer) 1
			
			
	5. 有序集合类型 zset：不允许重复元素，且元素有序(元素排序).
							每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
		1. 存储：zadd key score value
			127.0.0.1:6379> zadd mysort 60 zhangsan
			(integer) 1
			127.0.0.1:6379> zadd mysort 50 lisi
			(integer) 1
			127.0.0.1:6379> zadd mysort 80 wangwu
			(integer) 1
			
		2. 获取：zrange key start end [withscores]
			127.0.0.1:6379> zrange mysort 0 -1
			1) "lisi"
			2) "zhangsan"
			3) "wangwu"

			127.0.0.1:6379> zrange mysort 0 -1 withscores
			1) "zhangsan"
			2) "60"
			3) "wangwu"
			4) "80"
			5) "lisi"
			6) "500"
			
		3. 删除：zrem key value
			127.0.0.1:6379> zrem mysort lisi
			(integer) 1
			
			
	6. 通用命令
		1. keys * : 查询所有的键。                                               
		2. type key ： 获取键对应的value的类型。
		3. del key：删除指定的 key中 所有数据。
		
	-----------------------------------------------------------------------------------------------------------------
		
	7.Redis的持久化
		1.持久化:
			Redis的高性能是由于其将所有的数据都存储在了内存中。
			为了保证Redis在重启后，原有数据不丢失，需将数据从内存 同步到硬盘中，即持久化。
		
		2.Redis持久化机制：
			1.RDB：(默认方式,无需配置)：
				* 在一定的间隔时间中，检测key的变化情况，然后持久化数据到 dump.rdb文件中
				1. 编辑redis.conf文件:
					#   after 900 sec (15 min) if at least 1 key changed
					save 900 1
					#   after 300 sec (5 min) if at least 10 keys changed
					save 300 10
					#   after 60 sec if at least 10000 keys changed
					save 60 10000
				
				2. 重新启动redis服务器，将.rdb文件加载到内存中
					redis-server redis-conf
				
			2.AOF：
				* 日志记录的方式，可以记录每一条命令的操作到 appendonly.aof文件中。在Redis服务器启动之初会读取该文件来重构数据库.
				1. 编辑redis.conf文件:
					appendonly no（默认关闭aof） --> appendonly yes （开启aof）
					
					# appendfsync always ： 每一次操作都进行持久化
					appendfsync everysec ： 每隔一秒进行一次持久化
					# appendfsync no	 ： 不进行持久化
					
 */

/*						Jedis操作Redis数据库、Jedis连接池

	1.准备：
			1.打开Linux系统的Redis数据库：redis.server redis.conf(后端模式打开)
			2.创建Java项目，导入Jedis依赖包:
											commons-pool2-2.3.jar
											jedis-2.7.0.jar
		
	2.Java客户端 Jedis
		* Jedis: 一款java操作redis数据库的工具.
		* 使用步骤：
			1.下载jedis的jar包.
			2.使用:
	    		Jedis jedis = new Jedis("localhost",6379);		//1. 获取host port 的redis连接
	   			jedis.set("username","zhangsan");			//2. 操作
	    		jedis.close();						//3. 关闭连接
	
	3.Jedis操作各种redis中的数据结构
		1.字符串类型 string
			jedis.set()				--jedis.set("username","zhangsan");	//向 K中存一个V
			jedis.get()				--String username = jedis.get("username");	//从 K中取出V
	
		2.哈希类型 hash：	 	
			jedis.hset()			--jedis.hset("user","name","lisi");		//向 K中存一个K-V
			jedis.hget()			--String name = jedis.hget("user", "name");	//从 K中取出 K对应V
			jedis.hgetAll()			--Map<String, String> user = jedis.hgetAll("user");	//从 K中取出所有K-V
			
		3.列表类型 list：	 
			jedis.lpush()			--jedis.lpush("mylist","a","b","c");	//从左边存
			jedis.rpush()			--jedis.rpush("mylist","a","b","c");	//从右边存
			jedis.lpop()			--String element1 = jedis.lpop("mylist");	//左边弹出
			jedis.rpop()			--String element2 = jedis.rpop("mylist");	//右边弹出
			jedis.lrange()			--List<String> mylist2 = jedis.lrange("mylist", 0, -1);
	
		4.集合类型 set：		
			jedis.sadd()			--jedis.sadd("myset","java","php","c++");	//向 K中存入set数据
			jedis.smembers()		--Set<String> myset = jedis.smembers("myset");	//获得 K中所有数据
	
		5.有序集合类型 zset：	
			jedis.zadd()			--jedis.zadd("mysortedset",3,"亚瑟");
			jedis.zrange()			--Set<String> mysortedset = jedis.zrange("mysortedset", 0, -1);
	
	
		6.其他方法：
			jedis.setex("hehe",20,"hehe");		//hehe：hehe键值对存入redis，并且20秒后自动删除该键值对
			jedis.close();
	
	
	

	3.jedis连接池：JedisPool
	
		0.创建连接池的配置对象
			JedisPoolConfig conf = new JedisPoolConfig();
			conf.setMaxIdle(30); // 最大闲置个数
			conf.setMinIdle(10); // 最小闲置个数
			conf.setMaxTotal(30); // 最大连接数
			
		1.创建jedis连接池
			JedisPool pool = new JedisPool(conf, "192.168.154.128", 6379);
			
		2.从连接池中获得jedis连接对象
			Jedis jedis = pool.getResource();
			
		3.操作数据库
			jedis.set("xxx", "yyy"); // redis中只能存字符串,可以用json代替传入对象
			System.out.println(jedis.get("xxx"));
			
		4.关闭jedis资源
			jedis.close();
	
	
	4.Jedis连接池工具-JedisUtil	(见	/src/jedisUtil/JedisUtil.java)
			自己编写一个工具类，使用JedisPool 获得Jedis 对象。
	
	
	
	5.注意：使用redis缓存一些不经常发生变化的数据。
	
		* 数据库的数据一旦发生改变，则需要更新缓存。
			* 数据库的表执行 增删改的相关操作，需要将redis缓存数据情况，再次存入
			* 在service对应的增删改方法中，将redis数据删除。
	
*/


public class Redis详解 {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.154.128", 6379);
		jedis.set("hehe", "hehe");
		String value = jedis.get("hehe");
		System.out.println(value);
		jedis.close();
	}

	// 特殊方法setex
	@Test
	public void test1() {
		Jedis jedis = new Jedis("192.168.154.128", 6379);
		jedis.setex("hello", 5, "hehe");
		jedis.close();
	}

	// 2.存储hash类型
	@Test
	public void test2() {
		Jedis jedis = new Jedis("192.168.154.128", 6379);
		jedis.hset("myhash", "zhangsan", "11");

		String value = jedis.hget("myhash", "zhangsan");
		System.out.println("value为" + value);

		Map<String, String> map = jedis.hgetAll("myhash"); // 获得所有myhash的K-V
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key + ":" + map.get(key));
			;
		}
		jedis.close();
	}

	// 3.存储list类型
	@Test
	public void test3() {
		Jedis jedis = new Jedis("192.168.154.128", 6379);
		jedis.lpush("mylist", "1", "2", "3"); // 从list左边依次加入 1,2,3
		jedis.rpush("mylist", "1", "2", "3"); // 从list右边依次加入 1,2,3

		String value = jedis.lpop("mylist");
		System.out.println("左边弹栈：" + value);
		value = jedis.rpop("mylist");
		System.out.println("右边弹栈：" + value);

		List<String> list = jedis.lrange("mylist", 0, -1);
		for (String va : list) {
			System.out.println("List中元素有：" + va);
		}
		jedis.close();
	}

	// 4.存储set类型
	@Test
	public void test4() {
		Jedis jedis = new Jedis("192.168.154.128", 6379);
		jedis.sadd("myset", "1", "1", "2", "2", "3", "3", "4"); // 向set集合中添加 1,1,2,2,3,3,4

		Set<String> set = jedis.smembers("myset");
		for (String value : set) {
			System.out.println("set中元素有:" + value);
		}
		jedis.close();
	}

	// 5.存储zset类型
	@Test
	public void test5() {
		Jedis jedis = new Jedis("192.168.154.128", 6379);
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("zz", 60d);
		map.put("zz", 50d);
		map.put("xx", 60d);
		jedis.zadd("mysort", map);
		jedis.zadd("mysort", 70d, "yy");
		
		Set<String> set = jedis.zrange("mysort", 0, -1);
		for(String s :set) {
			System.out.println("zset中元素有："+s);
		}
		jedis.close();

	}

}
