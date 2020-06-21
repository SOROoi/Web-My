package 反射;

/*
 * 	Person 类
 * 		用于 被反射解析
 */

public class Person {
	public String name;
	private int age;
	
	public Person(){
	}
		
	private Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public void eat() {
		System.out.println("public eat方法");
	}
	
	private void eat(String food) {
		System.out.println("private eat方法  eat"+food);
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
	
}
