package bean;

import java.io.Serializable;

public class Role implements Serializable {

	private int id;
	private String name;
	private int age;
	private String gender;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		if (Integer.valueOf(gender) == 1) {
			gender = "男";
		} else {
			gender = "女";
		}
		return "Role [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + "]";
	}
}
