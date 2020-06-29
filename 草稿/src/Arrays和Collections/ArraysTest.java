package Arrays和Collections;

import java.util.Arrays;

public class ArraysTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {1,54,57,6,87,32,84,68,97,647,324,42,154,7};
		
		Arrays.sort(arr);	//排序
		System.out.println(Arrays.toString(arr));	//输出数组
		System.out.println(Arrays.binarySearch(arr, 42));	//二分查找
	}

}
