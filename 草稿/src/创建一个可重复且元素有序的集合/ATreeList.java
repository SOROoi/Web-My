package 创建一个可重复且元素有序的集合;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * 创建一个可重复且元素有序的集合 此排序是适用于Comparator接口 Comparable接口的排序
 * 
 * @author asus pc
 *
 * @param <T>
 */

public class ATreeList<T> {

	private ArrayList<T> list = new ArrayList<>(); // 本质为ArrayList集合
	private Comparator<T> co = null; // 传入Comparator对象
	private int len;
	private T[] ar;

	/*
	 * 构造方法，两种 1.传入Comparator对象 2.无参，自带Comparable接口
	 */
	public ATreeList(Comparator<T> com) {
		co = com;
	}

	public ATreeList() {

	}

	public static void main(String[] args) {
//		ATreeList<Integer> li = new ATreeList<>();
//		int[] arr = { 15, 45, 65, 25, 15, 3, 65, 57, 15 };
//		ATreeList<String> li = new ATreeList<>();
//		String[] arr = { "a","A","z","0","Z","0" };
//		for (String i : arr) { // 添加
//			li.addd(i);
//		}
		ATreeList<Student> li = new ATreeList<>(new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				// TODO Auto-generated method stub}
				return o1.getAge()-o2.getAge();
			}
		});
		Student a = new Student("A", 18);
		Student b = new Student("b", 16);
		Student c = new Student("C", 17);
		li.addd(a);
		li.addd(b);
		li.addd(c);

		for (int i = 0; i < li.size(); i++) { // 展示数据：元素是否排序，重复
			System.out.print(li.get(i) + "  ");
		}
		System.out.println();
		System.out.println(li);	//展示集合
	}

	/*
	 * 实际为ArrayList的get(int i)
	 */
	private T get(int i) {
		// TODO Auto-generated method stub
		return list.get(i);
	}

	/*
	 * 实际上为ArrayList的size()
	 */
	private int size() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/*
	 * addd(T t)方法，添加元素并排序
	 */
	public Object addd(T t) {
		// 首先判断是构造1还是构造2
//		if (co != null) {
//			list.add(t);	// 构造1，传入Comparator对象
//			
//			len = list.size();		// 将集合转为数组
//			ar = (T[]) new Object[len]; 
//			list.toArray(ar);
//			
//			quick(ar, 0, ar.length-1);	//对数组排序
//			return t;
//		}
//		// 否则构造2，需T实现Comparable接口
//		if (t instanceof Comparable) {
//			return -1;	//未实现Comparable接口，无法添加
//		}
//		
//		list.add(t);
//		len = list.size();
//		T[] ar = (T[]) new Object[len]; // 将集合转为数组
//		list.toArray(ar);
//		quick(ar, 0, ar.length - 1);
		int status = -1; // 加入状态码，1为构造1，2为构造2
		if (co != null) {
			status = 1;
		} else if (t instanceof Comparable) {
			status = 2;
		} else {
			return -1; // 两种构造都不是，无法添加
		}

		list.add(t);
		len = list.size();
		ar = (T[]) new Object[len]; // 将集合转为数组
		list.toArray(ar);
		quick(ar, 0, ar.length - 1, status); // 对数组排序

		list.clear(); // 清除所有元素
		for (T t1 : ar) { // 排序完成将数组重新装入list集合
			list.add(t1);
		}
		return t;
	}

	/*
	 * 引入compare() compareTo()方法的快速排序
	 */
	private void quick(T[] ar, int left, int right, int status) {
		// TODO Auto-generated method stub

		if (left >= right) {
			return;
		}

		int i, j;
		i = left;
		j = right;
		T index = ar[left];

		if (status == 1) {
			while (i != j) {
				while (i < j && co.compare(ar[j], index) >= 0) { // 比较ar[j]与基准元素的大小，若右边元素大于等于index则继续比较
					j--;
				}
				ar[i] = ar[j];
				while (i < j && co.compare(ar[i], index) <= 0) {
					i++;
				}
				ar[j] = ar[i];
			}
			ar[i] = index;
			quick(ar, left, i - 1, status);
			quick(ar, i + 1, right, status);
		} else if (status == 2) {

			while (i != j) {
				while (i < j && ((Comparable<T>) ar[j]).compareTo(index) >= 0) { // 比较ar[j]与基准元素的大小，若右边元素大于等于index则继续比较
					j--;
				}
				ar[i] = ar[j];
				while (i < j && ((Comparable<T>) ar[i]).compareTo(index) <= 0) { // 直接强转
					i++;
				}
				ar[j] = ar[i];
			}
			ar[i] = index;
			quick(ar, left, i - 1, status);
			quick(ar, i + 1, right, status);
		}
	}

}
