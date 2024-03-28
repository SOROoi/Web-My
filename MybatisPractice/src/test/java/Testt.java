import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.Person;
import bean.Role;
import bean.Timee;
import dao.mapper.UserMapper;

public class Testt {

	private InputStream in;
	private SqlSession sqlSession;
	private UserMapper mapper;

	@Before // 用于在测试方法执行之前执行
	public void init() throws Exception {
		// 1.读取配置文件，生成字节输入流
		in = Resources.getResourceAsStream("SqlMapConfig.xml");
		// 2.获取SqlSessionFactory
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
		// 3.获取SqlSession对象
		sqlSession = factory.openSession();
		// 4.获取dao的代理对象
		mapper = sqlSession.getMapper(UserMapper.class);
	}

	@After // 用于在测试方法执行之后执行
	public void destroy() throws Exception {
		// 提交事务
		sqlSession.commit();
		// 6.释放资源
		sqlSession.close();
		in.close();
	}

	// 测试findAll
	@Test
	public void t1() {
		List<Role> list = mapper.findAll();
		for (Role r : list) {
			System.out.println(r);
		}
	}

	// 测试findById
	@Test
	public void t2() {
		Role r = mapper.findById(3);
		System.out.println(r);
	}

	// 测试一对一查询Timee类
	@Test
	public void t3() {
		Timee t = mapper.findTimeById(2);
		System.out.println(t);
	}

	// 测试一对多查询Person类
	@Test
	public void t4() {
		Person p= mapper.findPersonById(2);
		System.out.println(p);
	}
}
