package Map集合;

import java.util.Set;
import java.util.TreeSet;

/*
 * 问题：关于TreeMap集合的 存储顺序 与取出顺序
 * 
 * 		取出顺序为：	compareTo()返回值小于0，则作为左孩子	--Comparable
 * 					compareTo()返回值大于0，则作为右孩子
 * 		从左到右取出。
 */
public class TreeMapQuestion {

	public static void main(String[] args) {
		
		Person p1 = new Person("张三",12);
		Person p2 = new Person("里斯",13);
		Person p3 = new Person("王五",5);
		Person p4 = new Person("刘国",22);
		
		Set<Person> set = new TreeSet<Person>();
		set.add(p1);
		set.add(p2);
		set.add(p3);
		set.add(p4);
		
		for(Person p:set) {
			System.out.println(p.getName()+"="+p.getAge());
		}
	}
 
}
