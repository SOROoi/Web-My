package 测试;

import java.util.Arrays;

public class Paixu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 14, 74, 34, 5, 312, 55, 32, 78, 15 };
//		bubble(arr);
		select(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void select(int[] arr) {
		// TODO Auto-generated method stub
		if (arr == null || arr.length == 0)
			return;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					int z = arr[i];
					arr[i] = arr[j];
					arr[j] = z;
				}
			}
		}
	}

	// 冒泡
	private static void bubble(int[] arr) {
		// TODO Auto-generated method stub
		if (arr == null || arr.length == 0)
			return;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - 1 - i; j++) {
				if (arr[j] > arr[j + 1]) {
					int z = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = z;
				}
			}
		}
	}

}
