package wxt.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wxt.bean.User;
import wxt.service.IService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:applicationContext.xml")
public class MyTest {

	@Autowired
	IService service;

	@Test
	public void test() {
		List<User> list = service.findAll();
		for(User user:list) {
			System.out.println(user);
		}
	}

	@Test
	public void test2() {
		User user = new User(22, "阿高", new Date(), "女", "中国");
		service.add(user);
	}
}
