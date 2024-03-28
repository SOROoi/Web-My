package 测试;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class TTTT {
	private static int[] arrr;

	public static void main(String[] args) {
//		t1();
//		t2();
//		t3();
//		t4();
//		t5();
//		t6();
//		t7();
//		t8();
//		t9();
//		t10();
//		t11();

//		t11();
//		a: for (int i = 0; i < 5; i++) {			//标号a
//			b: for (int j = 0; j < 5; j++) {
//				if (j == 2) {
//					break a;
//				}
//				System.out.println("j=" + j);
//			}
//			System.out.println("现在循环a中");
//		}
//		System.out.println("跳出了循环a");
//		StringBuffer sb = new StringBuffer("abcde");
//		sb.insert(5, "fghij");
//		sb.delete(5, 10);
//		System.out.println(sb);

//		int[] arr = { 15, 16, 5, 89, 15, 3, 44, 8, 6, 15 };
//		arr = bubble(arr);		//冒泡
//		select(arr);			//选择
//		quick(arr, 0, arr.length - 1); // 快排 输入范围必须是[0, arr.length-1]
//		System.out.println(Arrays.toString(arr));
//		System.out.println("44所在索引为" + binary(arr, 44)); // 二分查找44所在索引
//
//		String s = "asd@163.com";
//		System.out.println(s.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));		//正则表达式
//		
//		String[] strArray = {"1", "2", "3", "4", "5"};
//		int[] intArray = Arrays.stream(strArray).mapToInt(Integer::parseInt).toArray();

		ArrayList<String> al = new ArrayList<>();
		al.add("1");
		al.add("2");
		System.out.println(al);
		System.out.println(Arrays.toString(al.toArray(new String[] {})));
		Iterator<String> it = al.iterator();
		while (it.hasNext()) { // 迭代器
			String s = it.next();
			System.out.println(s);

		}
		for (String s : al) {
			System.out.println(s);
		}

		String s = null;
		for (Iterator<String> i = al.iterator(); i.hasNext(); System.out.println(s)) { // 增强for原理
			s = (String) i.next();
		}

		switch (al.get(0)) {
		case "1":
			System.out.println("1,能成");
			break;
		case "2":
			System.out.println("2,成不了");
			break;
		default:
			System.out.println("啥也不是");
			break;
		}

		HashSet<Integer> hset = new HashSet<>();
		TreeSet<Integer> tset = new TreeSet<>();
		hset.add(11);
		hset.add(22);
		hset.add(33);
		tset.add(22);
		tset.add(11);
		tset.add(33);
		for (Integer i : hset) {
			System.out.println(i);
		}
		for (Integer i : tset) {
			System.out.println(i);
		}

		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(1, "添加功能");
		m.put(2, "删除功能");
		m.put(3, "获取功能");
		Set<Map.Entry<Integer, String>> set = m.entrySet();
		for (Map.Entry<Integer, String> entry : set) {
			Integer key = entry.getKey();
			String value = entry.getValue();
			System.out.println("键：" + key + "  值：" + value);
		}
		Set<Integer> keys = m.keySet();
		for (Integer key : keys) {
			String value = m.get(key); 
			System.out.println("键：" + key + "  值：" + value);
		}
		m.values();
	}

	/**
	 * 快速排序： (概念见知乎收藏夹)
	 * 以数组中最左侧数作为基准元素index，一次排序确定基准元素的位置，并使得元素左侧数都小于等于index,右侧数都大于等于index;
	 * 再对左右侧分别进行递归，最后达到全部排序的效果
	 */
	private static void quick(int[] arr, int left, int right) {
		// TODO Auto-generated method stub
		if (left >= right) { // 递归出口
			return;
		}
		int i, j, index; // 基准元素index = arr[left], 指针ij
		index = arr[left];
		i = left;
		j = right;

		while (i != j) { // 一次排序的结束条件 i=j
			while (i < j && arr[j] >= index) { // arr[j]从右往左与 index 对比,若比index小 则赋值给arr[i],指针跳到i 继续比较
				j--;
			}
			arr[i] = arr[j];
			while (i < j && arr[i] <= index) { // arr[i]从左往右与 index 对比,若比index大 则赋值给arr[j]
				i++;
			}
			arr[j] = arr[i];
		}
		arr[i] = index; // 重复上述过程至一次比较结束,i=j时，index处于正确位置（即左侧数小于等于index,右侧数大于等于index）
		quick(arr, left, i - 1); // 递归排列左右两侧基准元素
		quick(arr, i + 1, right);
	}

	private static int[] select(int[] arr) { // 选择排序，单次选择 将第一个数 与剩下所有数比较，小则与第一个数替换，使最小值置于索引最小处
		// TODO Auto-generated method stub
		for (int i = 0; i < arr.length - 1; i++) { // 选择次数
			for (int j = i + 1; j < arr.length; j++) { // 单次选择需要比较的个数

				if (arr[j] < arr[i]) {
					int tem = arr[i];
					arr[i] = arr[j];
					arr[j] = tem;
				}
			}
		}
		return arr;
	}

	private static int[] bubble(int[] arr) { // 冒泡排序，单次冒泡 使最大值 置于索引最大处（即末尾）
		// TODO Auto-generated method stub
		for (int i = 0; i < arr.length - 1; i++) { // 冒泡几次
			for (int j = 0; j < arr.length - 1 - i; j++) { // 单次冒泡的长度
				if (arr[j] > arr[j + 1]) {
					arr[j] = arr[j] ^ arr[j + 1];
					arr[j + 1] = arr[j] ^ arr[j + 1];
					arr[j] = arr[j] ^ arr[j + 1];
				}
			}
		}
		return arr;
	}

	private static int binary(int[] arr, int i) { // 二分查找，对已排序的数组，用二分法找出目标值所在的索引
		// TODO Auto-generated method stub
		int low = 0;
		int high = arr.length - 1;
		while (low <= high) { // 只要 low <= high,继续执行
			int mid = low + (high - low) / 2; // 中位索引 mid = low + (high - low) /2
			if (arr[mid] == i) { // 每次比较三种结果，不相同时改变low 或 high 的索引值，二分缩小区间
				return mid;
			}
			if (i > arr[mid]) {
				low = mid + 1;
			}
			if (i < arr[mid]) {
				high = mid - 1;
			}
		}
		return -1; // 未找到，跳出循环，输出-1
	}

	// 打印n行杨辉三角形
	// 思路：创建n行n列的二维数组，(二维数组中未定义的元素默认为0)
	// 第二行之后,每个一维数组的第一个元素为1，之后的每个元素由上一行的两个元素相加得出
	private static void t11() {
		// TODO Auto-generated method stub
		int n = 11;
		int[][] arr = new int[n][n];
		arr[0][0] = 1;

		// 定义杨辉三角的二维数组
		for (int i = 1; i < arr.length; i++) { // 定义第二行之后的元素
			for (int j = 0; j < arr[i].length; j++) {
				if (j == 0) {
					arr[i][j] = 1; // 每个一维数组的第一个元素等于1
				} else {
					arr[i][j] = arr[i - 1][j - 1] + arr[i - 1][j];
				}
			}
		}

		// 遍历杨辉三角的二维数组
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				if (arr[i][j] != 0) {
					System.out.print(arr[i][j] + "\t"); // 不等于0则输出
				}
			}
			System.out.println();
		}
	}

	// 二维数组
	private static void t10() {
		// TODO Auto-generated method stub
		int[][] arr = new int[3][2];
		arr[0] = new int[] { 1, 2, 3, 4 };

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				for (int j = 0; j < arr[i].length; j++) {
					System.out.println(arr[i][j]);
				}
			} else {
				System.out.println(arr[i]);
			}

		}

	}

	private static void t9() {
		// TODO Auto-generated method stub
		int[] ar = new int[2];
		int[] arr = new int[] { 1, 2, 3 };

		System.out.println(arrr);
		for (int i = 0; i < arr.length; i++) {
			System.out.println("arr[" + i + "]: " + arr[i]);
		}
	}

	// 输出nn乘法表
	private static void t8() {
		// TODO Auto-generated method stub
		int n = 6;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(i + "*" + j + "=" + i * j + "\t");
			}
			System.out.println();
		}
	}

	// 输出m行n列的星形
	private static void t7() {
		// TODO Auto-generated method stub
		int m = 3;
		int n = 4;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}

	private static void t6() {
		// TODO Auto-generated method stub
		ser: for (;;) {
			sa: // sa标号
			for (;;) {
				break sa; // 跳出sa标号的循环
			}
		}
	}

	private static void t5() {
		// TODO Auto-generated method stub
		int a = 0;
		switch (a) {
		case 1:
			System.out.println("输入了true");
			break;
		case 2:
			System.out.println("输入了false");
			break;
		default:
			System.out.println("无效输入");
			break;
		}
	}

	private static void t4() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入1或0：");
		String str = sc.nextLine();
		switch (str) {
		case "1":
			System.out.println("输入了true");
			break;
		case "0":
			System.out.println("输入了false");
			break;
		default:
			System.out.println("无效输入");
			break;
		}
	}

	private static void t3() {
		byte[] byteArr = new byte[] { 48, 65, 97 };
		String str = new String(byteArr);
		System.out.println(new byte[] { 48, 65, 97 }.toString());
		System.out.println(str);
		str = new String(byteArr, 2, 1);
		System.out.println(str);
	}

	private static void t2() {
		Scanner sc = new Scanner(System.in);
		int i = 0;
		while (true) {
			System.out.println("请输入数字:");
			i = sc.nextInt();
			if (i != 0) {
				System.out.println(i);
			} else {
				break;
			}
		}

	}

	public static void t1() {
		List<String> list = new ArrayList<String>();
		list.add(null);
		for (String s : list) {
			if (s.toString() == "") {
				System.out.println("成功");
			}
		}
	}

}
