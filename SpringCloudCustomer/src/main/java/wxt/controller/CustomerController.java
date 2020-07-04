package wxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import wxt.bean.User;

@RestController
@RequestMapping("/cus")
public class CustomerController {

	@Autowired
	private RestTemplate rest;
//	@Autowired
//	private DiscoveryClient client;

	// 远程调用UserService服务
	@GetMapping("/{id}")
	public User userService(@PathVariable("id") Long id) {
//		List<ServiceInstance> instances = client.getInstances("user-service"); // 1.通过服务名获得 服务实例 List(服务的集群)
//		ServiceInstance instance = instances.get(0); // 2.获得集群中的 服务实例
//		String baseUrl = "http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id;	//3.拼接为服务的url

		String baseUrl = "http://user-service/user/" + id;	//负载均衡写法：中间加服务名
		User user = rest.getForObject(baseUrl, User.class);
		user.setName("customer服务成功调用了user-service服务");
		return user;
	}
}
