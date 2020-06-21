package 反射;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * 		反射：通过类的Class对象，可以获得该类的 构造方法/成员变量/成员方法
 * 
 * 		Constructor<T> getDeclaredConstructor(Class<T>...types)		--获得指定的构造方法
 * 		Field getDeclaredField(String name)							--获得指定的成员变量
 * 		Method getDeclaredMethod(String name,Class<T>...types)		--获得指定的成员方法
 * 
 * 		void setAccessible(boolean flag)	--flag为true时，可无视访问权限
 * 
 */

public class Test {

	public static void main(String[] args)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {

		Class c = Class.forName("反射.Person");
		
		Constructor cons = c.getDeclaredConstructor(String.class, int.class); // 获得私有构造方法
		cons.setAccessible(true); // 取消访问权限
		Object o = cons.newInstance("廿三", 23);
		System.out.println(o);

		Field f = c.getDeclaredField("age"); // 获得指定的成员变量
		f.setAccessible(true);
		f.set(o, 33);
		System.out.println(o);
		
		Method m = c.getDeclaredMethod("eat", String.class);
		m.setAccessible(true);
		m.invoke(o, "面包");
	}

}
