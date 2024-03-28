package IO流;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.mozilla.universalchardet.UniversalDetector;

public class Text {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		File file = new File("C:\\Users\\asus pc\\trace.log");
//		FileInputStream input = new FileInputStream(file);
//		InputStreamReader in = new InputStreamReader(input, "UTF-8");
//		OutputStreamWriter out = new OutputStreamWriter(System.out);
//		char[] by = new char[1024];
//		int len = 0;
//		while ((len = in.read(by)) != -1) {
//			out.write(by, 0, len);
//			out.flush();
//		}
//		input.close();
//		out.close();
		File file = new File("C:\\Users\\asus pc\\Desktop\\windows操作贴士.txt");
		// 使用字节流读取
		FileInputStream in = new FileInputStream(file);
		int len = 0;
		byte[] by = new byte[1024];
		// 使用 UniversalDetector 检测文件编码
		UniversalDetector uni = new UniversalDetector(null);
		// 确保还有字节可以读取 并且字符检测器还未完成检测编码
		while ((len = in.read(by)) != -1 && !uni.isDone()) {
			uni.handleData(by, 0, len);
		}
		// 通知检测器已读取整个文件
		uni.dataEnd();
		// 获取编码类型
		String encoding = uni.getDetectedCharset();
		System.out.println(encoding);
		// 重置检测器
		uni.reset();
		in.close();

		// 使用字符流读取,指定编码
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();

	}

}
