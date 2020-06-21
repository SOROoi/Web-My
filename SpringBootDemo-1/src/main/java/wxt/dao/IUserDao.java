package wxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import wxt.bean.UserInfo;

@Mapper
public interface IUserDao {

	@Select("select * from user")
	@Results(id="map",value = {
		@Result(id = true,property = "id",column = "id"),
		@Result(property = "username",column = "username"),
		@Result(property = "password",column = "password"),
		@Result(property = "name",column = "name")		
	})
	List<UserInfo> findAll();
}
