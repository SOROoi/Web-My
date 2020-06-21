package wxt.test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class TestDemo {
	
	@Value("${name}")
	private String n;
	@Value("${age}")
	private String a;
	@Value("${addr}")
	private String ad;
	
	@Test 
	public void test1() throws UnsupportedEncodingException {
		System.out.println("姓名："+n);
		System.out.println("年龄："+a);
		System.out.println("住址："+ad);
	}

}
