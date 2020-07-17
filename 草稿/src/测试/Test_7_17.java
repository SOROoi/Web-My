package 测试;

import java.util.Arrays;

public class Test_7_17 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 21, 48, 7, 254, 87, 14, 11, 35, 88, 77, 65 };
//		bubble(arr);
//		select(arr);
		quick(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
		System.out.println(binary(arr, 254));
	}

	private static int binary(int[] arr, int i) {
		// TODO Auto-generated method stub
		int low = 0;
		int high = arr.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (i == arr[mid]) {
				return mid;
			}
			if (i > arr[mid]) {
				low = mid + 1;
			}
			if (i < arr[mid]) {
				high = mid - 1;
			}
		}
		return -1;
	}

	private static void quick(int[] arr, int left, int right) {
		// TODO Auto-generated method stub
		if (left >= right) {
			return;
		}

		int i, j, index;
		index = arr[left];
		i = left;
		j = right;

		while (i != j) {
			while (i < j && arr[j] > index) {
				j--;
			}
			int tem = arr[i];
			arr[i] = arr[j];
			arr[j] = tem;

			while (i < j && arr[i] < index) {
				i++;
			}
			tem = arr[i];
			arr[i] = arr[j];
			arr[j] = tem;
		}
		quick(arr, i + 1, right);
		quick(arr, left, i - 1);
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
