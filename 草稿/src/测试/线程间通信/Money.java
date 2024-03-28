package 测试.线程间通信;

public class Money {

	private int total; // 余额
	private long startTime; // 初始时间
	private String user; // 使用者

	public Money(int i) {
		total = i;
		startTime = System.currentTimeMillis();
	}

	// 获取余额
	public int getTotal() {
		return total;
	}

	public void setTotal(int i) {
		total = i;
	}

	// 获取初始时间
	public long getTime() {
		return startTime;
	}

	// 经过了多少秒
	public int timeCost(long now) {
		long costTime = (now - getTime()) / 1000;
		return (int) costTime;
	}

	// 设置使用者
	public void setUser(String name) {
		user = name;
	}
	
	// 显示使用者
	public String getUser() {
		return user;
	}
}
