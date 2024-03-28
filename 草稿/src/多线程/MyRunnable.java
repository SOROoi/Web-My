package 多线程;

import java.util.Hashtable;

public class MyRunnable implements Runnable {

	private static int ticket = 100;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			synchronized (this) { // 同步代码块
				if (ticket > 0) {
					System.out.println(Thread.currentThread().getName() + ": 售出第" + ticket-- + "张票");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("已中断，停止执行");
						return;
					}

//					if (Thread.currentThread().isInterrupted()) {
//						System.out.println("已经被中断，但继续执行中true");
//					} else {
//						System.out.println("未中断false");
//					}
					
					
				} else {
					return;
				}

			}
		}
	}

}
