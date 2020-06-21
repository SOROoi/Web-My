package 测试;
/*		
		测试递归阶乘

 */
public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 11;
		i = jieCheng(i);
		System.out.println(i);
	}

	private static int jieCheng(int i) {
		// TODO Auto-generated method stub
		if (i == 1) {
			return 1;
		}else {
			return i*jieCheng(i-1);
		}
	}

}
