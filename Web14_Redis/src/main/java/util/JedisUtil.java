package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private static JedisPool pool = null;

	static {

		// 加载配置文件
		InputStream in = JedisUtil.class.getClassLoader().getResourceAsStream("redis.properties");	//相对于classes下
		Properties pro = new Properties();
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 创建JedisPoolConfig配置，获得JedisPool对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty("redis.maxIdle")));// 最大闲置个数
		poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("redis.minIdle")));// 最小闲置个数
		poolConfig.setMaxTotal(Integer.parseInt(pro.getProperty("redis.maxTotal")));// 最大连接数
		pool = new JedisPool(poolConfig, pro.getProperty("redis.url"),
				Integer.parseInt(pro.get("redis.port").toString()));
	}

	// 获得jedis资源的方法
	public static Jedis getJedis() {
		return pool.getResource();
	}

}
