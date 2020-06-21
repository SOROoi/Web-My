package 排序;

import java.util.Arrays;
/*
 * 选择排序
 * 		从第一个数开始，依次和后面的数比较，小的往前放。第一次完毕，最小值出现在最小索引处
 * 时间复杂度：O(n2)
 */

public class SelectSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 21, 584, 1, 54, 3, 7, 27, 17, 57, 974, 6345, 243, 6, 5 };
		arr = select(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static int[] select(int[] arr) {
		// TODO Auto-generated method stub
		if (arr == null || arr.length == 0)	//若arr为空，不排序
			return arr;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {	//将第一个索引数 与之后所有数比较
				if (arr[i] > arr[j]) {	//若小，放到前
					int z = arr[i];
					arr[i] = arr[j];
					arr[j] = z;
				}
			}
		}
		return arr;
	}

}
