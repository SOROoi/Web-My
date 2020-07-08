package wxt.feignClient;

import org.springframework.stereotype.Component;

import wxt.bean.User;

@Component
public class UserFeignClientFallback implements UserFeignClient {

	@Override
	public User qureyUser(Long id) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setName("服务器忙");
		return user;
	}

}
