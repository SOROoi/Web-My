package wxt.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import wxt.bean.User;

@FeignClient(value = "user-service",fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

	@GetMapping("/user/{id}")
	User qureyUser(@PathVariable("id") Long id);
}
