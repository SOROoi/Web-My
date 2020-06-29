package Arrays和Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("eeee");
		list.add("dddd");
		list.add("cccc");
		list.add("aaaa");
		list.add("bbbb");
		
		Collections.sort(list);		//排序
		for(String str:list) {
			System.out.println(str);
		}
		
		System.out.println(Collections.binarySearch(list, "aaaa"));		//二分查找

		System.out.println(Collections.max(list));		//最大值
		System.out.println("---------------------");
		
		Collections.reverse(list);	//反转
		for(String str:list) {
			System.out.println(str);
		}
		System.out.println("---------------------");
		
		Collections.shuffle(list);	//随机置换
		for(String str:list) {
			System.out.println(str);
		}
		
		Collections.synchronizedList(list);		//转为线程安全的List
		
		

		
	}

}
