package LinkedList模拟栈;

import java.util.LinkedList;

public class StackList<T> {
	
	private LinkedList<T> li = new LinkedList<T>();

	public StackList() {
	}
	
	//增
	public void add(T t) {
		li.addFirst(t);
	}
	
	//删
	public void remove() {
		li.removeFirst();
	}
	
	public String toString() {
		return li.toString();
	}
}
