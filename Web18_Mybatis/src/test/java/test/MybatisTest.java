package test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import bean.User;
import dao.imp.IUserDao;

/**
 * mybatis测试类
 * 
 * @author asus pc
 *
 */
public class MybatisTest {

	/**
	 * 入门案例 
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//1.读取配置文件
		InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
		
		//2.创建SqlSessionFactory工厂
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();	//创建工厂mybatis使用了构建者模式：
		SqlSessionFactory factory = builder.build(in); 	//构建者模式：把对象的创建细节隐藏，使用者直接传入需求即可创建对象	
		
		//3.使用工厂生产SqlSession对象			
		SqlSession session = factory.openSession();	//生产SqlSession使用了工厂模式	：解耦(降低类之间的依赖关系，代码避免频繁修改)
		
		//4.使用SqlSession创建Dao接口的代理对象---只定义接口和映射配置sql，得到执行这个sql语句的接口的具体实现类对象
		IUserDao dao =session.getMapper(IUserDao.class);//使用了代理模式：不修改源码基础上对已有方法增强
		
		//5.使用代理对象执行方法
		List<User> users;
		
//		users = test1(dao);
		users = test2(dao);
		
		for(User user: users) {
			System.out.println(user);	
		}
		
		//6.释放资源
		session.close();
		in.close();
	}

	private static List<User> test2(IUserDao dao) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername("老王");
		user.setId(41);
		List<User> users = dao.findOne(user);
		return users;
	}

	private static List<User> test1(IUserDao dao) {
		List<User> users = dao.findAll();
		return users;
	}
	
	@Test
	public void t1() {
		short s1 = 1;
		s1 += 1;
	}
}
