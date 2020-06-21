package wxt.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConfigurationProperties(prefix = "person")		//此注解自动将 yml文件信息 注入字段， 需要提供get/set方法
@RestController
public class MyController {

	private String name;
	private Integer age;

	@RequestMapping("/hi")
	public String method() {
		return name + age;
	}
	
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
