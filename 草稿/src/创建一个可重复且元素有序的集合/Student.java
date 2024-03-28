package 创建一个可重复且元素有序的集合;

public class Student {

	private String name;
	private int age;

	public String getName() {
		return name;
	}


//	public Student() {
//		this.age = 0;
//
//	}


	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
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

}
