package 测试;

import java.util.ArrayList;
import java.util.List;

public class TTTT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add(null);
		for(String s:list) {
			if(s.toString() == "") {
				System.out.println("成功");
			}
		}
	}

}
