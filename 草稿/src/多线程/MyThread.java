package 多线程;

public class MyThread extends Thread {
	
	public MyThread() {
	}
	
	public MyThread(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		int i = 0;
		while (i<60) {
			System.out.println(Thread.currentThread().getName()+":"+i);
			try {
				sleep(1);			//线程休眠，一般放在run()中。休眠0.1s
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}
}
