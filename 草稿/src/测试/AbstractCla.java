package 测试;

//	1.定义一个抽象类----- is a
//	抽象类 定义的是该继承体系的共性功能

public abstract class AbstractCla {

	public static final int a = 1; // 接口的 成员变量默认 public static final,静态常量
	public int b = 2;	//抽象类的成员变量 可以是变量也可以常量
	
	public static void show() {		//抽象类的方法可以是非抽象方法
		System.out.println("这是一个抽象类的静态方法");
	}
	
	public abstract void method1();		//抽象类的方法可以是抽象方法
	
	public AbstractCla() {		//有构造方法
		
	}
}
