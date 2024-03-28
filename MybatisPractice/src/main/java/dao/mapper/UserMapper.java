package dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import bean.Person;
import bean.Role;
import bean.Timee;

public interface UserMapper {

	@Select("select * from person where id = #{id}")
	Role findById(int id);

	@Select("select * from person")
	List<Role> findAll();

	@Select("select * from person where gender = #{gender}")
	List<Role> findByGender(String gender);

	// 一对一查询Timee类
	@Select("select * from time where id = #{id}")
	@Results(id = "timeMap", value = { @Result(id = true, column = "id", property = "id"),
			@Result(column = "name", property = "name"), @Result(column = "size", property = "size"),
			@Result(property = "role", column = "id", javaType = Role.class, one = @One(select = "dao.mapper.UserMapper.findById", fetchType = FetchType.EAGER)) })
	Timee findTimeById(int id);

	// 一对多查询Person类,
	// select * from person p1, person p2 where p1.id = 1 and p1.gender = p2.gender;
	@Select("select * from person where id = #{id}")
	@Results(id = "personMap", value = { @Result(id = true, column = "id", property = "id"),
			@Result(column = "name", property = "name"), @Result(column = "age", property = "age"),
			@Result(column = "gender", property = "gender"),
			@Result(property = "roles", javaType = List.class, column = "gender", many = @Many(select = "dao.mapper.UserMapper.findByGender", fetchType = FetchType.EAGER)) })
	//此处@Result中的 column 表示， 将首条sql查询结果中的column列值，作为参数传递给另一个select语句
	Person findPersonById(int id);
}
