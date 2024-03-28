package 测试;

import java.util.Arrays;

import 排序.QuickSort;

public class Test_7_05 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 21, 48, 7, 254,21, 87, 14, 11, 35, 88, 77, 65,21};
//		bubb le(arr);
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

	/**
	 * 快速排序：	(概念见知乎收藏夹)
	 * 			以数组中最左侧数作为基准元素index，一次排序确定基准元素的位置，并使得元素左侧数都小于等于index,右侧数都大于等于index;
				再对左右侧分别进行递归，最后达到全部排序的效果
	 */
	private static void quick(int[] arr, int left, int right) {
		// TODO Auto-generated method stub
		if (left >= right) {					//递归出口
			return;
		}

		int i, j, index;
		index = arr[left];
		i = left;
		j = right;

		while (i != j) {
			while (i < j && arr[j] >= index) {		//arr[j]从右往左比较，若大于等于index则不变，否则与arr[i]交换，然后去执行下一段
				j--;
			}
//			int tem = arr[i];
			arr[i] = arr[j];
//			arr[j] = tem;

			while (i < j && arr[i] <= index) {		//arr[i]从左往右比较，若小于等于index则不变，否则与arr[j]交换
				i++;
			}
			arr[j] = arr[i];
//			tem = arr[i];
//			arr[i] = arr[j];
//			arr[j] = tem;
		}											
		arr[i] = index;				//重复上方过程，一次循环结束后index 位于自身位置（即左侧数都小于index,右侧数都大于index）
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
