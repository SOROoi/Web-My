package IO流;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/*
 * 字节传输流读取数据
 * 		文本中的 '\r\n' 是4个ASCII码	(92,114,92,110)
 * 		换行中的 '\r' '\n' 是2个ASCII码  (回车13,换行10)
 * 
 */

public class ZiJieLiu {

	public static void main(String[] args) {
		File file1 = new File("C:\\Users\\asus pc\\Desktop\\1.txt");
		//java中可直接使用/符号分隔目录，Java运行时环境会自动根据操作系统的不同进行路径分隔符的转换。在Windows系统中，它会将正斜杠（/）转换为反斜杠（\）。
		File file2 = new File("C:/Users/asus pc/Desktop/2.txt");	
		test1(file1, file2);
	}

	// 传入一个源文件和接收文件，复制源文件至接收文件
	public static void test1(File src, File file) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(file);
		
			int len = 0;
			byte[] by = new byte[512];
			while ((len = fis.read(by)) != -1) {
//				System.out.println(Arrays.toString(by));
//				System.out.println(len);
				fos.write(by, 0, len);
			}
			System.out.println(Arrays.toString("\r\n".getBytes()));
			System.out.println("传输完成");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
