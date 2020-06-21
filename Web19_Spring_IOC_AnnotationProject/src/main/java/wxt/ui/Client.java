package wxt.ui;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wxt.bean.Account;
import wxt.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class Client {

	/**
	 * 通过xml配置、注解，IOC容器中存储了:service模块对象、dao模块对象、QueryRunner对象(多例模式)、c3po连接池
	 * 
	 */

	@Autowired
	private AccountService service;

//	@Value("${name}")
//	private String n;
//	@Value("${age}")
//	private String a;
//	@Value("${addr}")
//	private String ad;
	
	/**
	 * 测试方法： 查询所有账户
	 */
	@Test
	public void test1() {
		// 1.创建IOC容器
		// ClassPathXmlApplicationContext context = new
		// ClassPathXmlApplicationContext("bean.xml");
		// 2.获取业务层对象
		// AccountService service = (AccountService) context.getBean("service");

		// 3.执行service中的方法
		List<Account> accounts = service.findAll();
		for (Account account : accounts) {
			System.out.println(account);
		}

		// 4.释放IOC容器资源
		// context.close();
	}

	/**
	 * 测试方法： 查询一个账户
	 */
	@Test
	public void test2() {
		// 1.创建IOC容器
		// ClassPathXmlApplicationContext context = new
		// ClassPathXmlApplicationContext("bean.xml");
		// 2.获取业务层对象
		// AccountService service = (AccountService) context.getBean("service");

		// 3.执行service中的方法
		Account account = service.findOne(3);

		System.out.println(account);

		// 4.释放IOC容器资源
		// context.close();
	}

	/**
	 * 测试方法： 添加一个账户
	 */
	@Test
	public void test3() {
		// 1.创建IOC容器
		// ClassPathXmlApplicationContext context = new
		// ClassPathXmlApplicationContext("bean.xml");
		// 2.获取业务层对象
		// AccountService service = (AccountService) context.getBean("service");

		// 3.执行service中的方法
		Account account = new Account();
		account.setName("王九");
		account.setMoney(2000f);

		service.saveOne(account);
		// 4.释放IOC容器资源
		// context.close();
	}

	/**
	 * 测试方法： 更新一个账户
	 */
	@Test
	public void test4() {
		// 1.创建IOC容器
		// ClassPathXmlApplicationContext context = new
		// ClassPathXmlApplicationContext("bean.xml");
		// 2.获取业务层对象
		// AccountService service = (AccountService) context.getBean("service");

		// 3.执行service中的方法
		Account account = new Account();
		account.setId(1);
		account.setName("小李");
		account.setMoney(1500f);

		service.updateOne(account);
		// 4.释放IOC容器资源
		// context.close();
	}

	/**
	 * 测试方法： 删除一个账户
	 */
	@Test
	public void test5() {
		// 1.创建IOC容器
		// ClassPathXmlApplicationContext context = new
		// ClassPathXmlApplicationContext("bean.xml");
		// 2.获取业务层对象
		// AccountService service = (AccountService) context.getBean("service");

		// 3.执行service中的方法
		service.deleteOne(2);
		
		// 4.释放IOC容器资源
		// context.close();
	}

	/**
	 * 测试：@Value 通过spEL表达式 获得.properties中数据
	 */
//	@Test
//	public void test6() {
////		Proxy.newProxyInstance(loader, interfaces, h)
//		System.out.println("姓名："+n);
//		System.out.println("年龄："+a);
//		System.out.println("住址："+ad);
//	}
}
