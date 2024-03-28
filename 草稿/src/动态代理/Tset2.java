package 动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 增强Cla
 * 
 * 注意：1.主方法中需使用接口的多态接收对象
 * 		2.Proxy.newProxyInstance()方法后需强转
 * @author asus pc
 *
 */

public class Tset2 {

	public static void main(String[] args) throws IllegalArgumentException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Inter object = new Cla();
		InvocationHandler in = new F(object);
		Inter proxy = (Inter) Proxy.newProxyInstance(Class.forName("动态代理.Cla").getClassLoader(), Cla.class.getInterfaces(),in);
		proxy.A();
	}

}

class F implements InvocationHandler {
	private Object obj;

	public F(Object object) {
		super();
		this.obj = object;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Object o = method.invoke(obj, args);
		System.out.println("你被增强了，快上");
		return o;
	}

}