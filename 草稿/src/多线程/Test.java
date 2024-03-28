package 多线程;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test extends Thread{

	 private BlockingQueue<String> queue;

	    public Test(BlockingQueue<String> queue) {
	        this.queue = queue;
	    }

	    public void run() {
	        try {
	        	System.out.println("Thread is running...");
	            while (!isInterrupted()) {
	                String message = queue.take(); // 阻塞获取消息
	                System.out.println("Received message: " + message);
	            }
	        } catch (InterruptedException e) {
	            System.out.println("Thread is interrupted. Terminating...");
	        }
	    }

	    public static void main(String[] args) {
	        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
	        Test thread = new Test(queue);
	        thread.start();

	        try {
	            Thread.sleep(2000); // 让线程运行一段时间
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        thread.interrupt(); // 请求中断线程的执行
	    }
    
}
