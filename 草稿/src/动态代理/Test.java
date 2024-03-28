package 动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * 
 	JDK 动态代理
 	
 	使用前提：首先存在一个被代理类(Cla)对象，我们需要增强其接口(Inter)中的功能
 	
 	使用：	1.创建 被代理类对象；
 			2.Proxy.newProxyInstance(...)创建 代理对象
 			3.代理对象执行方法即可。
 	
 	效果：代理对象的接口方法都得到增强。
 */
public class Test {

	public static void main(String[] args) {
		Cla c = new Cla();
		//此方法的第二个参数不能直接传入Inter.class,因为需要的参数为Class数组
		Inter i = (Inter)Proxy.newProxyInstance(c.getClass().getClassLoader(), c.getClass().getInterfaces(), new InvocationHandler() {

			@Override
			//method 指代被代理类中的任一方法
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("开始啦！");
				Object o = method.invoke(c, args);
				System.out.println("结束啦");
				return o;
			}
			
		});
		i.A();
		i.B();
	}
}
