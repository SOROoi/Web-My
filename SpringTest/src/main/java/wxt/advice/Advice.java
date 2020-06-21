package wxt.advice;

import org.springframework.stereotype.Component;

@Component("advice")
public class Advice {

	public void before() {
		System.out.println("方法执行啦");
	}
	
	public void after() {
		System.out.println("方法结束啦");
	}
	
	public void exception() {
		System.out.println("方法出错");
	}
	
	public void fi() {
		System.out.println("方法完成");
	}
}
