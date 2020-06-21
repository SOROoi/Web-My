package service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import dao.AccountDao;
import service.AccountService;

@Component(value="service")
public class AccountServiceImpl implements AccountService {
	
//	@Autowired
//	@Qualifier(value="accountDao1")
	@Resource(name="accountDao1")
	private AccountDao dao;
	
	{
		System.out.println("创建了service对象");
	}
	
	@Override
	public void save() {
		dao.update();
	}

}
