package redis操作;

/*						Redis数据库
	
	1.Redis是什么
		一种C语言开发的、高性能、非关系型(NoSQL)、键值对数据库
		
	2.NoSQL是什么
		NoSQL是not only sql，是一种非关系型数据库。优点有：高性能、易拓展、灵活、高可用
	
	3.Redis安装
		安装在Linux系统下，安装步骤详见/Web11_Linux系统/黑马49期/p2_Linux软件安装&Redis入门.pdf
		
	4.Redis的数据结构		(redis命令见/Web14_Redis数据库/src/redis操作/Redis.pdf)
		1.字符串(String)
		2.哈希(hash)
		3.字符串列表(list)
		4.字符串集合(set)
		5.有序字符串集合(sorted set)
		
	5.Redis的持久化
		1.持久化:
			Redis的高性能是由于其将所有的数据都存储在了内存中，为了使Redis在重启后仍能保证数据不丢失，需将数据从内存同步到硬盘中，即持久化
		
		2.Redis持久化的方式：
			1.RDB持久化(默认支持,无需配置)：
				该机制在指定时间间隔内将内存中数据快照写入磁盘,保存在bin下dump.rdb中（redis.conf中可修改快照参数）
				
			2.AOF持久化(默认不开启)
				该机制以日志形式记录服务器所处理的每一个操作，在Redis服务器启动之初会读取该文件来重构数据库，以保证启动后数据库的完整。(默认
				不启用AOF，可修改redis.congf中：appendonly yes将其开启; 默认每秒记录一次操作：appendfsync everysec)
			
	
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
public class Redis {

}
