package 测试;

import java.util.Arrays;

public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 21, 45, 7, 6, 324, 8, 54, 87, 5, 35, 28, 36 };
//		bublle(arr);
		select(arr);
		System.out.println(Arrays.toString(arr));
		int target = 87;
		target = binary(arr, target);
		System.out.println(target);
	}

	private static int binary(int[] arr, int target) {
		// TODO Auto-generated method stub
		int low = 0;
		int high = arr.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (arr[mid] == target) {
				return mid;
			}
			if (arr[mid] < target) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return -1;
	}

	private static void bublle(int[] arr) {
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

}
