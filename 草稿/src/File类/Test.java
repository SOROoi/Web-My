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
//		checkAll(new File("G:\\JD\\JavaWeb工程文件\\草稿"));
//		test1();
		test2(new File("G:\\学习资料\\JavaSE学习资料\\day19_IO流（异常_File）\\avi"));

	}

	/*
	 * 输出指定目录下指定后缀名的文件名称
	 */
	private static void test2(File file) {
		// TODO Auto-generated method stub
		File[] files = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(name != null && name.endsWith(".avi")) {
					return true;
				}
				return false;
			}
		});
		for (File f : files) {
			int lastIndex= f.getName().lastIndexOf(".");	//获取最后一个"."的索引
			if(lastIndex > 0) {			//如果索引存在，则截取名字并输出
				String n =  f.getName().substring(0, lastIndex);
				System.out.println(n);
			}
			
		}
	}

	/*
	 * 输出指定目录下所有文件名/文件夹名，输出目录下所有文件名
	 */
	// String[] list() 获取目录下所有文件或文件夹的名称数组
	public static void test1() {
		File file = new File("G:\\学习资料\\JavaSE学习资料\\day19_IO流（异常_File）");
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
			} else {
				System.out.println(f.getName());
			}
		}
	}

}
