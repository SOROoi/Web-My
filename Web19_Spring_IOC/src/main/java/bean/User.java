package bean;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("user")
public class User {
	@Value(value="张三")
	private String name;
	@Value(value="15")
	private Integer age;
	private Date birth;
	private String[] friends;
	private Map<String, Integer> grade;

	public Map<String, Integer> getGrade() {
		return grade;
	}

	public void setGrade(Map<String, Integer> grade) {
		this.grade = grade;
	}

	public String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", birth=" + birth + ", friends=" + Arrays.toString(friends)
				+ ", grade=" + grade + "]";
	}

	
	
	
}
