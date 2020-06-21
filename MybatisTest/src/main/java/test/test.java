package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import bean.User;
import dao.CRUDdao;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
		SqlSessionFactory factory = builder.build(in);
		SqlSession session = factory.openSession();
		CRUDdao dao = session.getMapper(CRUDdao.class);
		
		List<User> list = dao.findAll();
		for (User user : list) {
			System.out.println(user);
		}
		System.out.println("-------------------------");
		
		dao.add(new User(99, "东东", new Date(), "男", "中国"));
		session.commit();
		System.out.println("添加User对象");		
		
		System.out.println("-------------------------");
		
		list = dao.findAll();
		for(User user:list) {
			System.out.println(user);
			
		}
		
		session.close();		//释放资源
		in.close();
	}

}
