package redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.util.JedisUtil;

/*
 * 	使用Java程序操作redis数据库
 * 
 */
public class Demo1 {

	@Test
	public void test() {

		// 1、获得连接对象
		Jedis jedis = new Jedis("192.168.154.128", 6379);

		// 2、操作数据库——存入数据
		jedis.set("username", "zhangsan");

		// 3、获得数据
		String name = jedis.get("username");
		System.out.println(name);

	}

	// 通过Jedis连接池获得jedis连接对象
	@Test
	public void test2() {
		// 0.创建连接池的配置对象
		JedisPoolConfig con = new JedisPoolConfig();
		con.setMaxIdle(30); // 最大闲置个数
		con.setMinIdle(10); // 最小闲置个数
		con.setMaxTotal(30); // 最大连接数

		// 1.创建jedis连接池
		JedisPool pool = new JedisPool(con, "192.168.154.128", 6379);

		// 2.从连接池中获得jedis连接对象
		Jedis jedis = pool.getResource();

		// 3.操作数据库
		jedis.set("xxx", "yyy"); // redis中只能存字符串,可以用json代替传入对象
		System.out.println(jedis.get("xxx"));

		// 4.关闭jedis资源
		jedis.close();
		pool.close();
	}
	
	//使用JedisUtil工具获得Jedis对象
	@Test
	public void test3() {
		Jedis jedis = JedisUtil.getJedis();
		System.out.println(jedis.get("xxx"));;
		jedis.close();
	}
}
