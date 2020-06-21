package 数据类型;

/**
 * 
 * @author 月色泠然
 *
 */
public class Test {

	public static void main(String[] args) {
		short s = 1;
		short s1 = 2;
		// s = s + s1 错误
		s = 1 + 2;
		s = 15555;

		int i;
		i = 5 / 2 ;
		System.out.println(i);
		test1();
		{
			System.out.println("代码块");
		}
	}
	
	/**
	 * 测试方法1
	 */
	 public static void test1(){
		int a = 2;
		int b = 3;
		a = a^b;
		b = a^b;
		a = a^b;
		System.out.println(a+" + "+b);
	}
}
