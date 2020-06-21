package 多线程;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 实现多线程：
 * 			方式一：继承Thread类，重写run()方法
 * 
 * 			方式二：实现Runnable接口，新建Thread(Runnable r)传入Runnable对象
 * 
 * 			方式三：实现Callable接口，通过Executors类创建ExecutorService线程池对象，调用线程池对象.submit(Callable c)，执行线程
 * 
 * interrupt(): 中断线程，不能中断在运行中的线程，它只能改变中断状态(使isInterrupted()的中断状态变为false)
 * （--见https://www.cnblogs.com/jenkov/p/juc_interrupt.html）
 * 
 */
public class ShiXian {

	public static void main(String[] args) {
		// method1(); //方式一实现多线程
		// method2(); //方式二实现多线程
		// method3(); //中断线程
		 method4(); //方式三实现多线程
	}

	// 线程控制 方式一
	public static void method1() {
		Thread t1 = new MyThread("大老板");
		Thread t2 = new MyThread("小老板");
		Thread t3 = new MyThread("员工");

		t1.setPriority(1); // 设置线程优先级，10最高，1最低，默认5
		t2.setPriority(10);

		t3.setDaemon(true); // 守护线程，线程启动前调用该方法。当运行的线程都是守护线程时，虚拟机退出。

		t1.start();
		try {
			t1.join(); // 线程加入，启动线程后调用该方法。此线程执行完，才可执行join()后的线程
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
		t3.start();
	}

	// 线程安全的售票案例 方式二
	public static void method2() {
		MyRunnable run = new MyRunnable();
		Thread t1 = new Thread(run, "窗口1"); // 方式二创造线程对象
		Thread t2 = new Thread(run, "窗口2");

		t1.start();
		t2.start();

	}

	// 中断线程
	public static void method3() {
		Runnable run = new MyRunnable();
		Thread t1 = new Thread(run, "窗口1");
		t1.start();

		t1.interrupt(); // 中断线程，interrupt()不能中断在运行中的线程，它只能改变中断状态(使isInterrupted()的中断状态变为false)
	}

	// 方式三	ExecutorService 线程池对象的submit(Callable c)方法
	public static void method4() {
		ExecutorService e = Executors.newFixedThreadPool(2);
		e.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				for (int i = 0; i <= 100; i++) {
					System.out.println(Thread.currentThread().getName() + ":" + i);
				}
				return null;
			}
		});
	}
}
