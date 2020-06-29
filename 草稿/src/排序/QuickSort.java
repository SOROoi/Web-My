package 排序;

import java.util.Arrays;

/*					快速排序

	1.快速排序基本思想：
		通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另一部分的所有数据都要小。
		然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。

	2.核心思想：
		1.在待排序的元素中任取一个作为基准(通常取第一个元素，称为基准元素)
		
		2.将待排序的元素进行分块，比基准元素大的元素移动到基准元素的右侧，比基准元素小的移动到左侧，
		    从而一趟排序过程，就可以锁定基准元素的最终位置。
		    
		3.对左右两个分块重复以上步骤直到所有元素都是有序序列。

 */

public class QuickSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 54, 87, 35, 68, 98, 12, 45, 97, 32, 5, 46, 8, 77, 544, 11, 22, 33, 10 };

		sort(arr);
		System.out.println(Arrays.toString(arr));
	}

	private static void sort(int[] arr) {
		// TODO Auto-generated method stub
		int left = 0;
		int right = arr.length - 1;
		quickSort(arr, left, right);
	}

	private static void quickSort(int[] arr, int left, int right) {
		// TODO Auto-generated method stub
		if (left >= right) {
			return;
		} // 递归的出口：传入的左索引 等于或大于 右索引，表示需排序的分块中只有一个数字 或 分块为空，无需排序。
		
		int i, j, index;
		i = left;	//左指针
		j = right;	//右指针
		index = arr[left]; // 左边第一个作为 基准元素
		
		while (i != j) {	// i = j ，代表一次排序结束
			while (i < j && arr[j] >= index) {
				j--;
			}
			int tem = arr[i];	// 右侧数字 小于 基准元素时，交换
			arr[i] = arr[j];
			arr[j] = tem;
			
			while(i < j && arr[i] <= index) {
				i++;
			}
			tem = arr[i];	// 左侧数字 大于 基准元素时，交换
			arr[i] = arr[j];
			arr[j] = tem;
			
		}
		quickSort(arr, i+1, right);
		quickSort(arr, left, i-1);
	}

}
