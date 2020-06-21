package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import bean.User;
import dao.imp.ZhuJieUserDao;

public class ZhuJieTest {
	/*
	 * 基于注解开发的测试
	 */
	public static void main(String[] args) throws IOException {
		InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(in);
		SqlSession sqlSession = factory.openSession();
		ZhuJieUserDao dao = sqlSession.getMapper(ZhuJieUserDao.class);
		
		List<User> list = dao.findAll();
		for(User user:list) {
			System.out.println(user);
		}
		
		sqlSession.close();
		in.close();
		
	}
}
