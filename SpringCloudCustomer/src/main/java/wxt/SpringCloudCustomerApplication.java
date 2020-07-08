package wxt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
//@EnableDiscoveryClient	//表示是 Eureka使用者
//@EnableCircuitBreaker		//启用 Hystrix熔断
@SpringCloudApplication
@EnableFeignClients		//启用Feign
public class SpringCloudCustomerApplication {

	//注入到IOC
//	@Bean
//	@LoadBalanced	//1.负载均衡		2.启用feign后，便可不使用此方式
//	public RestTemplate creaTemplate() {
//		return new RestTemplate();
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudCustomerApplication.class, args);
	}

}
