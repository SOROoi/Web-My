package wxt.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wxt.SpringBootDemo1Application;
import wxt.bean.UserInfo;
import wxt.dao.IUserDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemo1Application.class)
public class MapperTest {

	@Autowired
	private IUserDao dao;
	
	@Test
	public void test() {
		List<UserInfo> list = dao.findAll();
		System.out.println(list);
	}
	
}
