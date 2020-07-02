package wxt.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController2 {

	@Value("${person.name}") // spEL表达式获得 yml文件中信息
	private String name;
	@Value("${person.age}")
	private Integer age;
	@Value("${person.addr}")
	private String addr;

	@Autowired
	private DataSource dataSource;

	@RequestMapping("/hii")
	public String method() {
		return name + age + addr + dataSource;
	}

}
