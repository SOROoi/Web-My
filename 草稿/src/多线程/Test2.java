package 多线程;

public class Test2 {

	public static void main(String[] args) {
		Thread thread = new Thread(() -> {
			try {
				System.out.println("Thread started");
				Thread.sleep(5000); // 休眠5秒钟
				System.out.println("Thread finished");
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted");
				// 可以进行其他的处理逻辑
			}
		});

		thread.start(); // 启动线程

		try {
			Thread.sleep(2000); // 主线程休眠2秒钟
			thread.interrupt(); // 中断目标线程
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
