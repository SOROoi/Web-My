package 装饰模式包装类;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SS extends InputStream {

	private InputStream in = null;

	public SS(InputStream i) {
		in = i;
	}

	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return in.read();
	}

	//SS类特有方法
	public void show() {
		System.out.println("这是SS类");
	}
	
	public static void main(String[] args) throws IOException {
		SS in = null;
		try {
			in = new SS(new FileInputStream("C:\\Users\\asus pc\\Desktop\\1.txt"));
			byte[] by = new byte[1024];
			int len = 0;
			in.show();
			while((len = in.read(by))!=-1) {
				System.out.println(new String(by,"gbk"));
			}
			in.show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			in.close();
		}
	}
}
