package File类;

import java.io.File;
import java.io.FilenameFilter;

/*
 * 	File类:
 			String[] list()		--获取目录下所有文件或文件夹的名称数组（受保护/隐藏文件 在此方法中会传为null值）
 			File[] listFiles()	--获取目录下所有文件或文件夹的File数组
 */
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		checkAll(new File("G:\\JD\\JavaWeb工程文件\\草稿"));
	}

	// String[] list() 获取目录下所有文件或文件夹的名称数组
	public static void test1() {
		File file = new File("G:\\学习资料\\JavaSE学习资料\\day19_集合框架（IO流）");
		String[] list = file.list();
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
		System.out.println(file.getName());
		for (String s : list) {
			System.out.println(s);
		}

		System.out.println("----------------------");

		list = file.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if (new File(dir, name).isFile()) {
					return true;
				}
				return false;
			}
		});
		for (String s : list) {
			System.out.println(s);
		}
	}

	// 递归获取目录下 所有文件
	public static void checkAll(File file) {
		if (!file.isDirectory()) {
			return;
		}
		File[] list = file.listFiles();
		for (File f : list) {
			if (f == null) {
				continue;
			}
			if (f.isDirectory()) {
				System.out.println("--------");
				checkAll(f);
			}else {
				System.out.println(f.getName());
			}
		}
	}
		
}
