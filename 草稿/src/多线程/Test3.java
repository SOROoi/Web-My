package 多线程;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test3 {

	public static void main(String[] args) {
		// 创建固定大小的线程池
		ExecutorService executor = Executors.newFixedThreadPool(3);

		// 提交任务给线程池
//        method1(executor);
		method2(executor);

	}

	//用三个线程的池执行8个任务，任务有返回值，用submit()方法
	private static void method2(ExecutorService executor) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 8; i++) {
			final int index = i;
			Future<String> s = executor.submit(() -> {		//传入lambda表达式 实现函数式接口的抽象方法
				System.out.println("Task"+index+"线程名:"+Thread.currentThread().getName());
				return String.valueOf(index);
			});
			try {
				String str = s.get();
				System.out.println(" 消息："+str);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		executor.shutdown();
	}

	//用三个线程的池执行10个任务，任务无返回值，用executor()方法
	private static void method1(ExecutorService executor) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			final int taskIndex = i;
			executor.execute(() -> {
				System.out.println("Task " + taskIndex + " executed by " + Thread.currentThread().getName());
			});
		}

		// 关闭线程池
		executor.shutdown();
	}

}
