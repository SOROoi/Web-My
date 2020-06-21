package code;

import java.util.Arrays;

/*			二维数组中的查找

	1.在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
	请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

	
	我的：
		就硬编码，全部遍历一遍，一个个查找
		时间复杂度：O(n^2)
		空间复杂度：O(1)
	


	优解：
		1. 分析
			利用该二维数组的性质：
		
			每一行都按照从左到右递增的顺序排序，
			每一列都按照从上到下递增的顺序排序
			改变个说法，即对于左下角的值 m，m 是该行最小的数，是该列最大的数
			
		2.每次将 m 和目标值 target 比较：
		
			1.当 m < target，由于 m 已经是该列最大的元素，想要更大只有从行考虑，取值右移一位
			2.当 m > target，由于 m 已经是该行最小的元素，想要更小只有从列考虑，取值上移一位
			3.当 m = target，找到该值，返回 true
			用某行最小或某列最大与 target 比较，每次可剔除一整行或一整列
		
		时间复杂度：O(行高+列宽)
		空间复杂度：O(1)
		
		链接：https://www.nowcoder.com/questionTerminal/abc3fe2ce8e146608e868a70efebf62e?answerType=1&f=discussion
		来源：牛客网
 */

public class Test01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] arr = { { 1, 2, 3 }, { 2, 3, 4 }, { 5, 6, 7 } };
		int i = 243;
//		boolean flag = method(i, arr);
		boolean flag = best(i, arr);
		System.out.println(flag);
	}

	// 我的解法：就硬编码，全部遍历一遍，一个个查找
	private static boolean method(int i, int[][] arr) {
		// TODO Auto-generated method stub
		for (int j = 0; j < arr.length; j++) {
			for (int z = 0; z < arr[j].length; z++) {
				if (i == arr[j][z]) {
					return true;
				}
			}
		}
		return false;
	}

	// 优解
	private static boolean best(int i, int[][] arr) {
		// 判断arr是否为空
		if (arr.length == 0 || arr[0].length == 0) {
			return false;
		}
		int colLen = arr[0].length - 1; // 列的最大索引
		int row = arr.length - 1; // 行的最大索引
		int col = 0;
		while ((row >= 0) && (col <= colLen)) {
			if (arr[row][col] == i) {
				return true;
			}
			if (arr[row][col] < i) {
				col++;
				continue;
			}
			if (arr[row][col] > i) {
				row--;
				continue;
			}
		}
		return false;
	}

}
