package wxt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import wxt.bean.User;
import wxt.feignClient.UserFeignClient;

@RestController
@RequestMapping("/cus")
//@DefaultProperties(defaultFallback = "defaultFallback")	//默认降级方法
public class CustomerController {

//	@Autowired		//2.使用RestTemplate调用
//	private RestTemplate rest;
//	@Autowired		//1.使用Eureka调用
//	private DiscoveryClient client;
	@Autowired
	private UserFeignClient service;

	// 远程调用UserService服务
	@GetMapping("/{id}")
//	@HystrixCommand
	public User userService(@PathVariable("id") Long id) {
//		List<ServiceInstance> instances = client.getInstances("user-service"); // 1.通过服务名获得 服务实例 List(服务的集群)
//		ServiceInstance instance = instances.get(0); // 2.获得集群中的 服务实例
//		String baseUrl = "http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id;	//3.拼接为服务的url

//		String baseUrl = "http://user-service/user/" + id;	//1.负载均衡写法：中间加服务名
//		String user = rest.getForObject(baseUrl, String.class);	//2.使用RestTemplate
	
		User user = service.qureyUser(id);	//3.Feign写法
		return user;
	}
	
	public String defaultFallback() {
		return "服务器繁忙";
	}
}
