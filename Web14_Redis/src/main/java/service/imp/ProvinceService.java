package service.imp;

import java.util.List;

import bean.Province;
import dao.ProvinceDao;
import dao.imp.ProvinceDaoImp;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;
import service.IProvinceService;
import util.JedisUtil;

/**
 * 	使用redis 储存需要经常查询、不经常发生变化的数据	

	步骤：
		先从redis 缓存中查询数据
		有数据，直接返回；
		无数据，查询MySQL，将得到的数据存入redis，返回数据。 

	注意：
		数据库的表执行 增删改的相关操作，需要将redis缓存数据情况，再次存入	(确保redis 中缓存的数据是正确的、最新的)
		在service对应的增删改方法中，将redis数据删除。
		
 */

public class ProvinceService implements IProvinceService {

	private ProvinceDao dao = new ProvinceDaoImp();	

	@Override
	public String findAll() {
		// TODO Auto-generated method stub
		List<Province> list = dao.findAll(); // 查找 MySQL获取所有省份
		JSONArray jsonArray = JSONArray.fromObject(list);
		String json = jsonArray.toString();
		return json;
	}

	@Override
	public String findRedis() {
		// TODO Auto-generated method stub
		Jedis jedis = JedisUtil.getJedis();	//获得Jedis对象
		
		// 先查找 redis数据库。若没有数据，则去查找MySQL数据库
		String province = jedis.get("provinces");
		
		//判断redis中数据是否为空
		if (province == null || province.isEmpty()) {
			province = findAll(); // 查找 MySQL
			jedis.set("province", province); // 添加到redis
			
			jedis.close();	//记得！释放资源归还连接
		}

		return province;
	}

}
