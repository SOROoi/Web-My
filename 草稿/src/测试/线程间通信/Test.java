package 测试.线程间通信;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Money m = new Money(10086);
		Thread th1 = new Thread(new Cost(m));
		Thread th2 = new Thread(new Work(m));
		
		th1.start();
		th2.start();
	}

}
