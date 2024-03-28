package 测试;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Teeest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Int i = new Int() {

			@Override
			public void play() {
				// TODO Auto-generated method stub
				System.out.println("play");
			}

			@Override
			public void eat() {
				// TODO Auto-generated method stub
				System.out.println("eat");
			}
		};
		Int proxy = (Int) Proxy.newProxyInstance(Teeest.class.getClassLoader(), i.getClass().getInterfaces(),
				new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// TODO Auto-generated method stub
						System.out.println("吃前");
						method.invoke(i, args);
						System.out.println("吃后");
						return null;
					}
				});
		
		proxy.eat();
		proxy.play();
	}

}

interface Int {

	void eat();

	void play();

}