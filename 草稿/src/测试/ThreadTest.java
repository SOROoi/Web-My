package 测试;

/*
	tips:	Thread中关于中断的三个方法：
		
		1.public void interrupt()：	中断线程。终止阻塞并抛出一个InterruptedException异常。将中断状态变为true。
		
		2.public static boolean interrupted()：	查询中断状态，然后清除中断（将中断状态变为false）
		
		3.public boolean isInterrupted()：	查询中断状态。
 */

public class ThreadTest {

	public static void main(String[] args) throws InterruptedException {
		Thread th1 = method1();
		Thread th2 = method2();

		// Ⅰ.启动线程
//		th1.setDaemon(true); // 设置th1为守护线程
//		th2.setDaemon(true); // 设置th2为守护线程
		th1.start(); // 启动线程，再由JVM调用线程的run()方法。一个线程只能启动一次
		th2.start();

		// Ⅱ.设置线程优先级(默认5，1最低，10最高)
//		th1.setPriority(6);
//		th2.setPriority(5);

		// Ⅲ.线程控制：休眠、加入、礼让、后台、中断
		Thread.sleep(1000l); // 1.休眠：让线程休眠x毫秒

//		th1.join(); 			//2.加入：该线程执行完，才可执行之后的代码。
		System.out.println("主线程执行完毕了");

//		Thread.yield(); 		// 3.礼让：(放在run()方法中)暂停当前执行的线程，执行其他线程，使多个线程的执行更和谐

		// th1.setDaemon(true); //4.后台：将线程标记为守护线程或用户进程，当正在运行的线程都是守护线程时，JVM退出。必须在线程启动前执行！

		th1.interrupt(); // 5.中断线程：关闭线程的阻塞状态，并向该线程对象抛出一个InterruptedException对象 
	}

	// 方式一：继承Thread类，
	private static Thread method1() {
		// TODO Auto-generated method stub
		MyThread th1 = new MyThread("1");
		return th1;
	}

	// 方式二：Thread构造时，传入Runnable实现类对象，该对象重写了run()方法
	private static Thread method2() {
		Thread th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 1;
				while (true) {
					System.out.println(Thread.currentThread().getName() + "线程启动了" + i + "次");
					i++;

					try {
						Thread.sleep(2000l);
					} catch (InterruptedException e) { // 如果抛出InterruptedException,则打印提示信息
						// TODO Auto-generated catch block
						System.out.println(Thread.currentThread().getName() + "线程被中断了");
					}

					Thread.yield(); // 线程礼让
				}
			}
		}, "2");
		return th2;
	}
}

//方式一：继承Thread类：1.定义需要的构造方法(构造方法不可继承，默认只有无参构造)，2.重写run()方法。
class MyThread extends Thread {

	public MyThread() {
		// TODO Auto-generated constructor stub
		super();
	}

	public MyThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		int i = 1;
		while (true) {
			try {
				System.out.println(this.getName() + "线程启动了" + i + "次");
				i++;
				Thread.sleep(2000l);
				Thread.yield(); // 线程礼让
			}catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName()+"线程被终止了");
			}
		}
	}

}