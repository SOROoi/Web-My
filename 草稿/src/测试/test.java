package 测试;
import java.util.Arrays;

/*
		测试冒泡排序、选择排序、二分查找
	
	
 */
public class test {

	public static void main(String[] args) {
		int[] arr = { 21, 1, 45, 87, 524, 2, 48, 72, 487, 14, 75, 93, 25 };
		bubble(arr); // 冒泡
//		select(arr); // 选择
		System.out.println(Arrays.toString(arr));
		int target = 524; // 查找target
		target = binary(arr, target); // 二分查找
		System.out.println(target);
	}

	//二分
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
			}
			if (target < arr[mid]) {
				high = mid - 1;
			}
		}
		return -1;
	}

	//选择
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

	//冒泡
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
