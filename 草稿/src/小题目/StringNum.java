package 小题目;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
 * 
 * 
 */
public class StringNum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String str = "sadiojhiohqwdaaaaf";
		Map<Character, Integer> map = new TreeMap<Character, Integer>();
		char[] strArr = str.toCharArray();

		for (int j = 0; j < strArr.length; j++) { // 将数据存入Map集合中
			Integer value = map.get(strArr[j]);
			if (value == null) {
				map.put(strArr[j], 1);
			}else {
				map.put(strArr[j], value + 1);
			}
		}

		Set<Character> set = map.keySet();
		StringBuilder builder = new StringBuilder();
		for (Character ch : set) {
			System.out.println(ch + " " + map.get(ch) + "个");
			builder.append(ch).append(map.get(ch));
		}
		System.out.println("StringBuilder: " + builder);
	}

}
