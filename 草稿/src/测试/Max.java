package 测试;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Max {

	public static void main(String[] args) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(123);
//		sb.insert(2, "后就是");
//		System.out.println(sb.toString());
//		sb.deleteCharAt(0);
//		System.out.println(sb.toString());
//		sb.append("呀kkk");
//		sb.delete(6,9);
//		System.out.println(sb.toString());
//		sb.replace(5, 6, "吗");
//		System.out.println(sb.toString());
//		sb.reverse();
//		System.out.println(sb.toString());
//		
//		Integer in = new Integer(33);
//		Integer.valueOf(44);
//		int i = new Integer("555").intValue();
//		String j = Integer.toString(8, 2);
//		System.out.println(j);
//		
//		boolean flag = "abab".matches("[abc]*");
//		System.out.println(flag);
//		
//		double d= 2.3;
//		double p = d + 3;
//		
//		int ii = 1;
//		switch(ii) {
//			case 1:
//				break;
//				
//			case 2:
//				break;
//			default:
//				break;
//		}
//		double ceil = Math.ceil(9.8);
//		double floor = Math.floor(9.8);
//		System.out.println("ceil="+ceil+", floor="+floor);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd 下午hh:mm");
//		Date d = new Date(System.currentTimeMillis()+1000*60*60);
//		String s = sdf.format(d);
//		System.out.println(s);

//		Calendar c = Calendar.getInstance();
//		int year = 2021;
//		c.set(year,2,1);
//		c.add(Calendar.DATE,-1);
//		int days = c.get(Calendar.DATE);
//		System.out.println(days);

//		List<String> l = new ArrayList<String>();
//		l.add("0");
//		l.add("1");
//		l.add("2");
//		l.add(3,"3");
//		for(String s:l) {
//			System.out.print(s);
//		}
//		System.out.println("");
//		
//		Iterator<String> it = l.iterator();
//		while(it.hasNext()) {
//			System.out.println(it.next());
//		}

//		List<String> li = new ArrayList<String>();
//		li.add("hello");
//		li.add("world");
//		method(li);
//		System.out.println(li.toString());
//		
//		
//		
//		TreeSet<Integer> ts = new TreeSet<Integer>(new Comparator<Integer>() {
//
//			@Override
//			public int compare(Integer o1, Integer o2) {
//				// TODO Auto-generated method stub
//				int num = o1.intValue()-o2.intValue();
//				return num;
//			}
//			
//		});
//		ts.add(31);
//		ts.add(12);
//		ts.add(41);
//		ts.add(15);
//		
//		ts.add(31);
//		ts.add(12);
//		ts.add(41);
//		ts.add(15);
//		System.out.println(ts);

//		Map<String, String> map = new HashMap<String, String>();
//		map.put("a", "1");
//		map.put("b", "2");
//		map.put("c", "3");
//		map.put("a", "4");
//		Set<String> set = map.keySet();
//		for(String k:set) {
//			String v = map.get(k);
//			System.out.println(k+"-"+v);
//		}
//		
//		Set<Map.Entry<String, String>> entrys = map.entrySet();
//		for(Map.Entry<String, String> entry : entrys) {
//			String k = entry.getKey();
//			String v = entry.getValue();
//		}

//		Scanner sc = new Scanner(System.in);
//		System.out.println("请输入字符串");
//		String s = sc.nextLine();
//		char[] array = s.toCharArray();
//		TreeMap<Character, Integer> tm = new TreeMap<Character, Integer>();
//		for (char c : array) {
//			Integer num = tm.get(c);
//			if (num == null) {
//				tm.put(c, 1);
//			} else {
//				tm.put(c, num + 1);
//			}
//		}
//		StringBuilder sb = new StringBuilder();
//		Set<Character> set = tm.keySet();
//		for(Character c:set) {
//			sb.append(c).append("(").append(tm.get(c)).append(")");
//		}
//		System.out.println(sb.toString());
		
		
//		List<Integer> li = new ArrayList<Integer>();
//		li.add(13);
//		li.add(4);
//		li.add(3);
//		li.add(5);
//		li.add(2);
//		li.add(8);
//		Collections.sort(li);
//		System.out.println("排序后："+li);
//		int index = Collections.binarySearch(li, 4);
//		System.out.println("4的索引是："+index);
//		int max = Collections.max(li);
//		System.out.println("最大值是："+max);
//		Collections.reverse(li);
//		System.out.println("翻转后："+li);
//		Collections.shuffle(li);
//		System.out.println("随机置换后："+li);
		
//		int[] arr = new int[] {1,3,2,4};
//		System.out.println(arr);
		
//		int i = 35;
//		String hi = Integer.toString(i,2);	//最高只有36进制
//		System.out.println("二进制为："+hi);
//		String two = "1010";
//		int num = Integer.parseInt(two,2);
//		System.out.println("十进制为："+num);
//		num = 0x10;
//		System.out.println(num);
		
		try {
			throw new Exception("自定义异常");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		
		
	}

	private static void method(List<String> li) {
		// TODO Auto-generated method stub
		// 并发修改异常
//		for(String s :li) {
//			if("world".equals(s)) {
//				li.add("javaee");
//				break;		//加上break;则不会产生"并发修改异常"
//			}
//		}

//		Iterator<String> it =  li.iterator();
//		while(it.hasNext()) {
//			String s = (String)it.next();
//			if("world".equals(s)) {
//				li.add("javaee");
//				break;
//			}
//		}

		// 增强for底层原理
//		for(String s;li.iterator().hasNext();) {
//			s = (String)li.iterator().next();
//		}

		for (int i = 0; i < li.size(); i++) {
			String s = (String) li.get(i);
			if ("world".equals(s)) {
				li.add("javaee");
			}
		}

	}
}
