package 测试;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		method1();
//		method2();
		method3();
	}

	//把C:\Users\asus pc\Desktop\新建文件夹\三国演义下面的视频名称修改为		00?_介绍.avi
	private static void method3() {
		File file = new File("C:\\Users\\asus pc\\Desktop\\新建文件夹\\三国演义");
		File[] flist;
		if(!file.exists()) {
			System.out.println("目录不存在");
			return;
		}
		flist = file.listFiles();
		for(File f : flist) {
			String newName = f.getAbsolutePath().replace(f.getName(), "00"+f.getName().replace(".txt", "")+"_介绍.avi");
			f.renameTo(new File(newName));
		}

		
	}

	// 方法2：File类中方法
	private static void method2() {
		File file = new File("新建文本.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean flag = file.isDirectory();
		System.out.println("是否为目录：" + flag);
		flag = file.isFile();
		System.out.println("是否为文件：" + flag);
		flag = file.exists();
		System.out.println("是否存在：" + flag);
		flag = file.canRead();
		System.out.println("是否可读：" + flag);
		flag = file.canWrite();
		System.out.println("是否可写：" + flag);
		flag = file.isHidden();
		System.out.println("是否隐藏：" + flag);
		System.out.println("----------------------");

		String s = file.getAbsolutePath();
		System.out.println("绝对路径：" + s);
		s = file.getPath();
		System.out.println("相对路径：" + s);
		s = file.getName();
		System.out.println("文件名称：" + s);
		s = file.length() + "";
		System.out.println("文件长度：" + s);
		Date d = new Date(file.lastModified());
		System.out.println("上次修改时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d));
		System.out.println("----------------------");

		File newFile = new File(file.getAbsolutePath().replace(file.getName(), ""));
		String[] list = newFile.list(new FilenameFilter() {	//过滤出以.txt结尾的文件名

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(name.endsWith(".txt")) {
					return true;
				}
				return false;
			}
			
		});
		for (String str : list) {
			System.out.println("文件名：" + str);
		}
		System.out.println("----------------------");
		File[] flist = newFile.listFiles(new FilenameFilter() {	//过滤出为目录的File

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(new File(dir,name).isDirectory()) {
					return true;
				}
				return false;
			}
			
		});
		for(File f:flist) {
			System.out.println("文件名："+f.getName());
		}

	}

	// 方法1：更改文件名
	private static void method1() {
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\asus pc\\Desktop\\新建文件夹\\新建文本文档.txt");
		System.out.println(file.getAbsolutePath().replace(file.getName(), "东方不败.txt"));
		if (file.exists()) {
			System.out.println("文件存在");
			// 更改文件名
			file.renameTo(new File(file.getAbsolutePath().replace(file.getName(), "东方不败.txt")));
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("文件创建失败");
			}
		}
	}

}
