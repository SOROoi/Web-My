package 测试;

import java.util.Arrays;

public class Test_6_27 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 54, 87, 59, 98, 34, 241, 8, 5, 423, 22, 33, 66, 44, 77, 88, 121, 24 };

//		bubble(arr);
//		select(arr);
		sort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
		System.out.println(binary(arr, 54));
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
			if (i > arr[mid]) {
				low = mid + 1;
			}
			if (i < arr[mid]) {
				high = mid - 1;
			}
		}
		return -1;
	}

	private static void sort(int[] arr, int left, int right) {
		// TODO Auto-generated method stub
		if (left >= right) {
			return;
		}

		int i, j, index;
		i = left;
		j = right;
		index = arr[left];

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
		sort(arr, left, i - 1);
		sort(arr, i + 1, right);
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
