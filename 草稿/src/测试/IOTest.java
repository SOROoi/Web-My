package 测试;

/*						字节流/字符流外的其他流

	基本数据操作流	DataOutputStream/DataInputStream
	内存操作流		ByteArrayOutputStream/ByteArrayInputStream(byte[] arr)
	打印流			PrintStream/PrintWriter	(每个2种构造)
	随机访问流		RandomAccessFile（Object子类）
	合并流			SequenceInputStream（2个构造）
	序列化流			ObjectInputStream/ObjectOutputStream
														(1.bean实现Serializable接口，
														2.不想序列化字段用transient，
														3.序列化文件后缀.ser)
	Properties集合	new Properties()	
									(1.Hashtable子类，
									2.键值对都是String，
									3.通过输入流-字符/字节都可-加载数据)
					主要方法：	String		getProperty(String k)		--获取值，通过键
						 		Set<String>	stringPropertyNames()		--获取键的Set集合
						 		Object		setProperty(String k , String v)	--添加数据至集合
						 		void		load(InputStream in)		--传入字节输入流，加载数据至集合
						 		void		load(Read re)				--传入字符输入流，加载数据至集合
						 		void		store(Writer w , String comments)		--传入字符输出流，将集合数据写入文件，comments为文件标准
						 		void		store(OutputStream o , String comments)	--传入字节输出流，将集合数据写入文件，comments为文件标准

	构造方法传入(InputStream/OutputStream)的流：
		DataOutputStream/DataInputStream、
		PrintStream、(两种构造)
		SequenceInputStream、(两种构造)
		ObjectInputStream/ObjectOutputStream
		
	构造方法传入(Writer)的流：
		PrintWriter、(两种构造)
	
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import 序列化与反序列化.Bean;

public class IOTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		File file = new File("C:\\Users\\asus pc\\Desktop\\新建文件夹\\东方不败.txt");
//		File newFile = new File("C:\\Users\\asus pc\\Desktop\\新建文件夹\\东方副本.txt");
//		method1(file, newFile);
//		System.out.println(Arrays.toString("\\r\\n".getBytes()));
//		OutputStream o = new BufferedOutputStream(System.out);
//		o.write(new byte[] { 48, 65, 97 }, 1, 1);
//		o.flush();

//		method2();
//		method3(new File("C:\\Users\\asus pc\\Desktop\\这是个文件夹\\论文"), new File("C:\\Users\\asus pc\\Desktop"));
//		method4();
//		method5();
//		method6();
//		method7();
//		method7_1();
//		method8();
//		method9();
//		method10();
//		method11();
		method12();

//		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("bos.txt"));
//		bos.write(new byte[] {48,49,65,66,97,98});
//		bos.flush();
//		bos.close();
	}

	private static void method1(File file, File newFile) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(newFile);

		byte[] b = new byte[1024];
		int n = -1;
		while ((n = fis.read(b)) != -1) {
			fos.write(b, 0, n);
		}

		fis.close();
		fos.close();
	}

	private static void method2() throws IOException {
		OutputStreamWriter ow = new OutputStreamWriter(
				new FileOutputStream("C:\\Users\\asus pc\\Desktop\\新建文本文档.txt", true), "gbk");
		ow.write("你好");
		ow.flush();
		ow.close();

	}

	// 复制多级文件夹
	private static void method3(File src, File dest) throws IOException {
		File newDirect = new File(dest, src.getName());
		newDirect.mkdir(); // 创建目标文件夹
		File[] files = src.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file)); // 源文件输入流
				File destFile = new File(newDirect, file.getName()); // 复制到目标文件夹的目标文件
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile)); // 输出流
				byte[] by = new byte[1024];
				int len = 0;
				while ((len = bis.read(by)) != -1) {
					bos.write(by, 0, len);
				}
				bis.close();
				bos.close();
			} else {
				method3(file, newDirect);
			}

		}
	}

	// 已知s.txt文件中有这样的一个字符串：“hcexfgijkamdnoqrzstuvwybpl”
	// 请编写程序读取数据内容，把数据排序后写入ss.txt中。
	private static void method4() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\asus pc\\Desktop\\新建文件夹\\s.txt"));
		String s = br.readLine(); // 读取字符串

		// 排序
		char[] arr = s.toCharArray();
		// Arrays.sort(arr); //Arrays工具类排序
		// 冒泡排序
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - 1 - i; j++) {
				if (arr[j] > arr[j + 1]) {
					char temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		String newStr = String.valueOf(arr);

		// 写入ss.txt
		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\asus pc\\Desktop\\新建文件夹\\ss.txt"));
		bw.write(newStr);
		bw.flush();
		br.close();
		bw.close();
	}

	// LineNumberReader --BufferedReader子类
	private static void method5() throws IOException {
		// TODO Auto-generated method stub
		LineNumberReader lr = new LineNumberReader(new FileReader("C:\\Users\\asus pc\\Desktop\\新建文件夹\\东方不败.txt"));
		String line = null;
		while ((line = lr.readLine()) != null) {
			System.out.println("第" + lr.getLineNumber() + "行：" + line);
		}
		lr.close();
	}

	// 基本数据类型流-输出流 DataOutputStream
	private static void method6() throws IOException {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("dos.txt"));

		// 写数据了
		dos.writeByte(10);
		dos.writeShort(100);
		dos.writeInt(1000);
		dos.writeLong(10000);
		dos.writeFloat(12.34F);
		dos.writeDouble(12.56);
		dos.writeChar('a');
		dos.writeBoolean(true);

		// 释放资源
		dos.close();
	}

	// 基本数据类型流-输入流 DataInputStream
	private static void method7() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream("dos.txt"));

		// 读数据
		byte b = dis.readByte();
		short s = dis.readShort();
		int i = dis.readInt();
		long l = dis.readLong();
		float f = dis.readFloat();
		double d = dis.readDouble();
		char c = dis.readChar();
		boolean bb = dis.readBoolean();

		// 释放资源
		dis.close();

		System.out.println(b);
		System.out.println(s);
		System.out.println(i);
		System.out.println(l);
		System.out.println(f);
		System.out.println(d);
		System.out.println(c);
		System.out.println(bb);
	}

	// 内存操作流 (ByteArrayOutputStream/ByteArrayInputStream(byte[] by) 先写后读)
	private static void method7_1() throws IOException {
		// 写数据
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < 10; i++) {
			baos.write(("hello" + i).getBytes());
		}
		byte[] barr = baos.toByteArray();
		System.out.println(Arrays.toString(barr));

		// 读数据
		ByteArrayInputStream bais = new ByteArrayInputStream(barr);
		int b = -1;
		while ((b = bais.read()) != -1) {
			System.out.println((char) b);
		}
	}

	// 打印流
	private static void method8() throws IOException {
		// TODO Auto-generated method stub
		PrintStream ps = new PrintStream("ps.txt");
		ps.println("hello");
		ps.close();

		// 开启自动刷新
		PrintWriter pw = new PrintWriter(new FileWriter("pw.txt"), true);
		pw.println("helloworld");
		pw.println(true);
		pw.close();
	}

	// 随机访问流(Object子类)
	private static void method9() throws IOException {
		// TODO Auto-generated method stub
		RandomAccessFile raf = new RandomAccessFile("dos.txt", "rw");

		// 写数据
		raf.writeInt(100);
		raf.writeChar(48);
		raf.writeUTF("夏天");

		// 读数据
		int i = raf.readInt();
		System.out.println("int：" + i + "  指针：" + raf.getFilePointer());
		char ch = raf.readChar();
		System.out.println("char：" + ch + "  指针：" + raf.getFilePointer());
		String s = raf.readUTF();
		System.out.println("String：" + s + "  指针：" + raf.getFilePointer());
	}

	// 合并流 SequenceInputStream -两种构造
	private static void method10() throws IOException {
		// 构造一：SequenceInputStream(InputStream s1,InputStream s2)
//		SequenceInputStream sis = new SequenceInputStream(new FileInputStream("bos.txt"),
//				new FileInputStream("ps.txt"));
//		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("sis.txt"));
//		byte[] by = new byte[1024];
//		int len = 0;
//		while ((len = sis.read(by)) != -1) {
//			bos.write(by, 0, len);
//			bos.flush();
//		}
//		sis.close();
//		bos.close();

		// 构造二：SequenceInputStream(Enumeration<InputStream>)
		Vector<InputStream> ve = new Vector<InputStream>();
		ve.add(new FileInputStream("ps.txt"));
		ve.add(new FileInputStream("bos.txt"));
		ve.add(new FileInputStream("pw.txt"));
		Enumeration<InputStream> en = ve.elements();
		SequenceInputStream sis2 = new SequenceInputStream(en);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("sis.txt"));
		byte[] by = new byte[1024];
		int len = 0;
		while ((len = sis2.read(by)) != -1) {
			bos.write(by, 0, len);
		}
		sis2.close();
		bos.close();
	}

	// 集合Properties- Hashtable子类
	private static void method11() throws IOException {
		// 创建Properties集合
		Properties pro = new Properties();
		// 1.传入输入流，加载数据到集合中
		pro.load(new FileInputStream("prop.properties"));

		// 2.获取值
		String value = pro.getProperty("A");
		System.out.println("A的值为：" + value);
		// 3.获取键的集合，并遍历
		Set<String> set = pro.stringPropertyNames();
		for (String s : set) {
			value = pro.getProperty(s);
			System.out.println("键值对：" + s + "=" + value);
		}
		// 4.添加键值对到集合
		pro.setProperty("0", "48");
		System.out.println("集合：" + pro);
	}

	// 序列化流 ObjectOutputStream/ObjectInputStream
	private static void method12() throws FileNotFoundException, IOException, ClassNotFoundException {
		//序列化
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("序列化.ser"));
		Bean bean = new Bean();
		bean.setName("李");
		bean.setAge(20);
		oos.writeObject(bean);
		
		//反序列化
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("序列化.ser"));
		Object o = ois.readObject();
		bean = (Bean)o;
		System.out.println(bean.getName()+":"+bean.getAge()+"岁");
	}
	
}
