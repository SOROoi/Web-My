package 查找;

/*
 * 		二分查找
 * 
 * 	给定一个升序的int 数组(无重复值)，用二分查找找到值 num
 * 
 * 	要点：	
 * 		起始：		low = 0;	high = length - 1 ;
 * 		循环条件：	low <= high;
	  		中位索引： 	mid = low + (high - low) / 2;
	  		若 num > 中位数：	low = mid + 1;
	 		若 num < 中位数： high = mid - 1;
 * 
 */
public class BinarySearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 1, 2, 5, 6, 8, 12, 18, 23, 24, 25, 28, 33, 35, 37, 42 };
		int num = 42;
		num = binarySearch(arr, num);
		System.out.println(num);
	}

	private static int binarySearch(int[] arr, int num) {
		// TODO Auto-generated method stub
		int high = arr.length - 1;
		int low = 0;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (arr[mid] < num) {
				low = mid + 1;
			}
			if (arr[mid] > num) {
				high = mid - 1;
			}
			if (arr[mid] == num) {
				return mid;
			}
		}
		return -1;
	}

}
