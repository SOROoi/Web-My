package 排序;

import java.util.Arrays;

public class ZiFuChuan {

	public static void main(String[] args) {
		String str = "khuisbwuynjldusgqjkwge";
		byte[] s = str.getBytes();
		Arrays.sort(s);
		String str1 = new String(s);
		System.out.println(str1);
	}

}
