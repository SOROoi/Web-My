package 测试;

import java.io.File;
import java.io.FilenameFilter;

public class RecursionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(method1(6));
//		method2(new File("C:\\Users\\asus pc\\Desktop\\这是个文件夹"));
		System.out.println(method3(7));;
	}

	// 递归求阶乘
	private static int method1(int i) {
		if (i != 1) {
			return i * method1(i - 1);
		} else {
			return 1;
		}

	}

	// 递归输出指定目录下所有指定后缀名的文件绝对路径
	private static void method2(File file) {
//		if(file.isDirectory()) {
//			File[] files = file.listFiles();
//			for(File f:files) {
//				method2(f);
//			}
//		}
//		if(file.isFile()) {
//			if(file.getName().endsWith(".txt")) {
//				System.out.println(file.getAbsolutePath());
//			}
//		}
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".txt")) {
				System.out.println(f.getAbsolutePath());
			}
			if (f.isDirectory()) {
				method2(f);
			}
		}
	}

	// 兔子问题
	// 有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问第二十个月的兔子对数为多少？
	private static int method3(int i) {
		if (i == 1 || i == 2) {
			return 1;
		}
		if (i > 2) {
			int num = method3(i - 1) + method3(i - 2);
			return num;
		}
		return 0;
	}

}
