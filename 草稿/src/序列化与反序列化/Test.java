package 序列化与反序列化;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/*					序列化与反序列化
 	
 	序列化：将内存中对象存储到文件中。
 	反序列化：读取序列化文件，将其转为内存中对象。
 	
 	条件：	1.被序列化的对象，该类必须实现了Serializable 接口。
			2.若类中有不想被序列化的字段，使用transient 关键字。
			
	注意： 当序列化一个对象到文件时， 按照 Java 的标准约定是给文件一个 .ser 扩展名。
 */

public class Test {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

//		serialize(); // 序列化
		fanSerializable(); //反序列化
	}

	// 序列化方法 将对象序列化至文件中
	private static void serialize() throws IOException {
		String path = Test.class.getClassLoader().getResource("").getPath();
		System.out.println("path:" + path);
		File file = new File(URLDecoder.decode(path, "UTF-8"), "序列化与反序列化/序列化.ser");
		OutputStream out = new FileOutputStream(file);
		ObjectOutputStream objOut = new ObjectOutputStream(out); // 建立 ObjectOutputStream 序列化对象

		Bean b = new Bean(); // 对象，实现了Serializable接口
		b.setName("王二小");
		b.setAge(8);

		objOut.writeObject(b);
		objOut.flush();
		System.out.println("序列化完成");
		objOut.close();
	}

	// 反序列化方法 将序列化文件转为内存中对象
	private static void fanSerializable() throws IOException, ClassNotFoundException {
		InputStream in = Test.class.getClassLoader().getResourceAsStream("序列化与反序列化/序列化.ser");
		ObjectInputStream objIn = new ObjectInputStream(in); // 创建反序列化对象

		Object o = objIn.readObject(); // 反序列化，将文件转为内存中对象
		System.out.println("反序列化完成");
		System.out.println("获得的对象为：" + o);
		objIn.close();
//		InputStream i = Test.class.getClassLoader().getResourceAsStream("prop.properties");
//		Properties pro = new Properties();
//		pro.load(in);
//
//		for (Map.Entry<Object, Object> entry : pro.entrySet()) {
//			System.out.println(entry.getKey().toString() + entry.getValue().toString());
//		}
//		System.out.println("完成");
//		i.close();
	}
}
