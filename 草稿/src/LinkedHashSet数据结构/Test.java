package LinkedHashSet数据结构;


/*		
		LinkedHashSet 有序(存储顺序)，唯一，继承自HashSet
		
	1.其中，以哈希表保证其唯一，以链表保证其有序。
	2.底层数据结构：元素之间带有before、after指针的哈希表结构。

 */

/*
	1.参考网址：https://bbs.csdn.net/topics/380156470?page=2
	
	2.参考网友发言：
		HashSet是用HashMap来实现的，只是使用了HashMap的key。
		LinkedHashSet是由LinkedHashMap来实现的。
		
		HashMap使用数组来存放元素，数组的每一个元素都是一个链表，链表里面所有的元素的hashcode都是一样的，然后用equals来区分具体某一个元素。
		
		LinkedHashMap和HashMap不一样的地方就在于，LinkedHashMap的Entry和HashMap的Entry不一样。
		HashMap的Entry：
		static class Entry<K,V> implements Map.Entry<K,V> {
		        final K key;
		        V value;
		        Entry<K,V> next;//指向相同hashcode的下一个元素的指针
		        final int hash;
		}
		LinkedHashMap的Entry：
		private static class Entry<K,V> extends HashMap.Entry<K,V> {
		        Entry<K,V> before, after;//指向前一个和后一个Entry的指针
		}
		
		可以看出来，LinkedHashMap比HashMap还多了before, after，也就是用他们俩来维护插入的顺序。
		
		LinkedHashMap和HashMap的插入删除性能基本上差不多，以删除来说，基本步骤是：先计算hashcode，找到在数组里面的位置，然后修改指针。差别在修改指针这一步，基本可以忽略。
 
 */

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
