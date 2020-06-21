package wxt.test;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		JdbcTemplate jt = context.getBean("jdbcTemplate", JdbcTemplate.class);
		jt.execute("insert into account(name,money) values('李云飞',900)");
//		System.out.println(new ArrayList<>().isEmpty()?"无内容":new ArrayList<>().add("aaa"));  //三目运算符
	}

}
