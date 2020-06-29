package 排序;

import java.util.Arrays;

public class ZiFuChuan {

	public static void main(String[] args) {
		String str = "khuisbwuynjldusgqjkwgeADCBGFE";
		byte[] s = str.getBytes();
		Arrays.sort(s);
		str = new String(s);
		System.out.println(str);
	}

}
