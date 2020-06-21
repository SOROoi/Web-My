package Map集合;

public class Person implements Comparable<Person>{
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String name,Integer age) {
		this.age = age;
		this.name = name;
	}

	private String name;
	private Integer age;

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

	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		return this.age-o.getAge();
	}

	
}
