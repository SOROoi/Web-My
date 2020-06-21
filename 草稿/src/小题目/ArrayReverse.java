package 小题目;

import java.util.Arrays;

/*
 * 
 * 将数组逆序
 */
public class ArrayReverse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		reverse(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void reverse(int[] arr) {
		// TODO Auto-generated method stub
		int l = arr.length / 2;
		for (int i = 0; i < l; i++) {
			int tem = arr[i];
			arr[i] = arr[arr.length - 1 - i];
			arr[arr.length - 1 - i] = tem;
		}
	}

}
