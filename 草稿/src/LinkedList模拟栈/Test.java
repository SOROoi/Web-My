package LinkedList模拟栈;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StackList<String> sl = new StackList<String>();
		sl.add("1");
		sl.add("2");
		sl.add("3");
		sl.add("4");
		sl.add("5");
		System.out.println(sl.toString());		
		
		sl.remove();
		System.out.println(sl.toString());	
	}

}
