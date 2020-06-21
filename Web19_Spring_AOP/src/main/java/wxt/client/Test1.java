package wxt.client;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wxt.service.ServiceIn;

public class Test1 {
	
	@Test
	public void test1() {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		ServiceIn service = (ServiceIn) context.getBean("service");
		service.all();
	}
}
