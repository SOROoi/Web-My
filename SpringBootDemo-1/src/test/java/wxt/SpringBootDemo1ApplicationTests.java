package wxt;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wxt.bean.UserInfo;
import wxt.dao.IUserDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemo1ApplicationTests {

	@Autowired
	private IUserDao dao;
	
	@Test
	public void contextLoads() {
		List<UserInfo> list = dao.findAll();
		for(UserInfo u:list) {
			System.out.println(u);
			
		}
	}

}
