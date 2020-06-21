package spring;

public class A {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = new int[] { 5, 45, 67, 8, 145, 7, 3, 89 };
		paixu(arr);
		for (int i : arr) {
			System.out.println(i);
		}
	}

	// 冒泡排序
	public static int[] paixu(int[] arr) {
		int len = arr.length;

		for (int x = 0; x < len - 1; x++) {
			for (int i = 0; i < len - 1 - x; i++) {
				if (arr[i] > arr[i + 1]) {
					int j = arr[i + 1];
					arr[i + 1] = arr[i];
					arr[i] = j;
				}
			}
		}

		return arr;
	}
}
