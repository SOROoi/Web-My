package wxt.service;

import java.util.List;

import wxt.bean.UserInfo;

public interface IUserService {

	List<UserInfo> findAll();
}
