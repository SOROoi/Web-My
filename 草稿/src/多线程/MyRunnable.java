package 多线程;

public class MyRunnable implements Runnable {

	private static int ticket = 100;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			synchronized (this) {	//同步代码块
				if (ticket > 0) {
					System.out.println(Thread.currentThread().getName() + ": 售出第" + ticket-- + "张票");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					return;
				}

			}
		}
	}

}
