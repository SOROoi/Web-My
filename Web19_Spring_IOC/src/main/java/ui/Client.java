package ui;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bean.User;
import service.AccountService;

public class Client {
	
	/**
	 * 通过xml配置，IOC管理bean
	 * 
	 */
	@Test
	public void test1() {
		// 1.获取核心容器对象：指定配置文件位置
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		// 2.根据id 获取bean 对象
		// AccountService service = (AccountService) context.getBean("accountService");
		// AccountDao dao = context.getBean(AccountDao.class);
		// System.out.println(service);
		// System.out.println(dao);

		User user = context.getBean(User.class);
		System.out.println(user);

		// 销毁容器：将接口转为具体类，调用close方法
		ClassPathXmlApplicationContext con = (ClassPathXmlApplicationContext) context;
		con.close();

		// --------BeanFactory----------
		// Resource resource = new ClassPathResource("bean.xml");
		// BeanFactory factory = new XmlBeanFactory(resource);
		// IAccountService as = (IAccountService)factory.getBean("accountService");
		// System.out.println(as);
	}
	
	/**
	 * 通过xml、注解配置，IOC管理bean
	 * 
	 */
	@Test
	public void test2() {
		// 1.获取核心容器对象：指定配置文件位置
		ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
		// 2.根据id 获取bean 对象
		AccountService service = (AccountService) context.getBean("service");
		System.out.println(service);
		service.save();
		
		User user = context.getBean(User.class);
		System.out.println(user);

		// 销毁容器：将接口转为具体类，调用close方法
		ClassPathXmlApplicationContext con = (ClassPathXmlApplicationContext) context;
		con.close();
	}
}
