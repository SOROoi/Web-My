package 测试;

import java.util.Arrays;

public class Test_6_24 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 87, 4, 27, 95, 36, 859, 45, 35, 98, 32, 56, 6, 8, 7, 654, 22, 44, 66 };

//		bubble(arr);
		select(arr);
		System.out.println(Arrays.toString(arr));
		System.out.println(binary(arr, 8));
	}

	private static int binary(int[] arr, int i) {
		// TODO Auto-generated method stub
		int low = 0;
		int high = arr.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (arr[mid] == i) {
				return mid;
			}
			if (arr[mid] < i) {
				low = mid + 1;
			}
			if (arr[mid] > i) {
				high = mid - 1;
			}
		}
		return -1;
	}

	private static void select(int[] arr) {
		// TODO Auto-generated method stub
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					int tem = arr[i];
					arr[i] = arr[j];
					arr[j] = tem;
				}
			}
		}
	}

	private static void bubble(int[] arr) {
		// TODO Auto-generated method stub
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - 1 - i; j++) {
				if (arr[j] > arr[j + 1]) {
					int tem = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = tem;
				}
			}
		}
	}

}
