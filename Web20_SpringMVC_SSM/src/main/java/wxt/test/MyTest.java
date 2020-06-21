package wxt.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wxt.service.imp.ServiceImp;

public class MyTest {

	@Test	//测试IOC是否可运行，pom.xml完整，applicationContext.xml是否正确
	public void test1() {	
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		ServiceImp service = ac.getBean(ServiceImp.class);
		service.findAll();
	}
	
	public void test2() {
		
	}
}
