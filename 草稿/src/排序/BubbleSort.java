package 排序;

import java.util.Arrays;
/*
 * 冒泡排序
 * 		两两比较，大的数放到最后。第一次完毕，最大值出现在最大索引处
 * 时间复杂度：O(n2)
 */
public class BubbleSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 4, 87, 21, 57, 21, 547, 54, 4, 85, 3, 74 };
		arr = bubble(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static int[] bubble(int[] arr) {
		// TODO Auto-generated method stub
		if (arr == null || arr.length == 0)	//若arr为空，不排序
			return arr;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - 1 - i; j++) {
				if (arr[j] > arr[j + 1]) { // 如果大，放到后
					int z = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = z;
				}
			}
		}
		return arr;
	}

}
