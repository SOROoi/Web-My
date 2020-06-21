package 小题目;

/*	
	打印如下菱形
	如：
		   *
		 * * *
	   * * * * *
		 * * * 
		   * 

	思路：由上图，做坐标系，得公式：	|x|+|y| <= 2;		-2 <= x <=2			-2 <= y <=2	
			从上向下打印符合公式的坐标即可
		
*/
public class LingXing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 10;
		lingXing();
		sanJiao(n);
	}

	// 打印菱形
	private static void lingXing() {
		// TODO Auto-generated method stub
		for (int y = 2; y >= -2; y--) {
			for (int x = -2; x <= 2; x++) {
				if (Math.abs(x) + Math.abs(y) <= 2) {
					System.out.print("* ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}

	// 打印三角
	private static void sanJiao(int n) {
		// TODO Auto-generated method stub
		for (int y = n; y >= 0; y--) {
			for (int x = 0; x <= n; x++) {
				if (x + y < n) {
					System.out.print("*");
				}else {
					System.out.print("");
				}
			}
			System.out.println();
		}
	}
}
