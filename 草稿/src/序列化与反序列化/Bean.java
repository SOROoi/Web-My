package 序列化与反序列化;

import java.io.Serializable;

public class Bean implements Serializable {
	private String name;
	private int age;

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

	@Override
	public String toString() {
		return "Bean [name=" + name + ", age=" + age + "]";
	}

}
