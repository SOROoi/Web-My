package File类;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test2 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String path = Test2.class.getClassLoader().getResource("File类").getPath();
		System.out.println(path);
		System.out.println(URLDecoder.decode(path,"UTF-8"));
	}
	
	
}
