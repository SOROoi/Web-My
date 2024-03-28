package 位运算;
/*
		<<左移：	左边最高位丢弃，右边补0
		>>右移：	最高位是0，左边补0；最高位是1，左边补1
		>>>无符号右移：	左边总补0
		
	
 */

public class Tesst {

	public static void main(String[] args) {
		t1();
		
		
	}

	//	<<左移
	private static void t1() {

		int a = 3 <<31;		//对于int类型的整数移位a<<b，先用b对32求余，得到的结果才是真正移位的位数
							//对于1<<32，实际移动位数	32%32=0
			
		System.out.println(a);
		System.out.println(0b10000000000000000000000000000000);
		

		int b = 3 <<32;
		long c = 3l <<32;	//对于long类型的整数移位，b对64求余
							//对于1L<<32，实际移动位数	32%64=32
		System.out.println(b);
		System.out.println(c);
	}
}
