package 测试.线程间通信;

import java.io.PrintStream;

public class Work implements Runnable {

	private Money money;

	public Work(Money m) {
		money = m;
	}

	// 每3s赚钱200块
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(3000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			synchronized (money) {
				money.setTotal(money.getTotal() + 200);
				money.setUser("打工人");
				
				
//				try {	//加入延迟，测试不加同步的安全性
//					Thread.sleep(100l);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				
				System.out.println(money.timeCost(System.currentTimeMillis()) + "秒：	" + money.getUser() + "	+200	"
						+ "目前余额为：	" + money.getTotal());
				
			}
		}

		
	}

}
