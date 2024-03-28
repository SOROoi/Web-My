package 多线程;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	public static void main(String[] args) {
		Timer time = new Timer();
		time.schedule(new TimerTask() {
			int i = 1;

			@Override
			public void run() { // 2秒后闹钟提醒，每1秒提醒一次
				// TODO Auto-generated method stub
				System.out.println("起床了" + i++);
			}
		}, 2000, 1000);
		try {
			Thread.sleep(10000); // 10秒后结束任务
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		time.cancel();
	}
}

//单例模式-懒汉式
class T {

	private T() {

	}

	private static T t = null;

	public synchronized static T create() { // 同步静态方法
		if (t == null) { // 懒加载(延迟加载)
			t = new T();
		}
		return t;
	}
}

//单例模式-饿汉式
class A {

	private A() {

	}

	private static A a = new A();

	public static A create() {
		return a;
	}

}
