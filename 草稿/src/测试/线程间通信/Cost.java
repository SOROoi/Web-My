package 测试.线程间通信;

public class Cost implements Runnable {

	private Money money;

	public Cost(Money m) {
		money = m;
	}

	// 每隔2s花100块钱
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(2000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			synchronized (money) {
				money.setTotal(money.getTotal() - 100);
				money.setUser("女朋友");
				
				
//				try {		//加入延迟，测试不加同步的安全性
//					Thread.sleep(100l);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				System.out.println(money.timeCost(System.currentTimeMillis()) + "秒：	" + money.getUser() + "	-100	"
						+ "目前余额为：	" + money.getTotal());
			}
		}
	}

}
