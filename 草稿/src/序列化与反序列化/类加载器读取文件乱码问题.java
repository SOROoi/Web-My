package 序列化与反序列化;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

/*					类加载器读取的文件为中文路径，乱码问题
 	
 	问题：
 		String path = JdbcUtil.class.getClassLoader().getResource("").getPath();  --(默认在/src/下)
 																			  	 --如果是中文路径,getResource("")获得的path是乱码 
 																			  	 其实是URL编码的字符
 	解决方法：
 		URLDecoder.decode(path,"UTF-8");	//将URL编码的字符解码为UTF-8的字符
 	
----------------------------------------------------------------------------------------------------------------------------
					关于URL编码 和 类加载器获取流对象的路径
	
	1.URL编码是一种将特殊字符转换为%xx表示形式的编码方式，以便在URL中传递参数或表示特殊含义的字符。
	例如，空格会被编码为"%20"，中文字符会被编码为"%E4%BD%A0"等。
	
	按照一般的web应用程序的结构，存在classes目录和src目录，此时getResourceAsStream("")获取的是哪个文件夹的路径


	2.getResourceAsStream("")获取的是哪个文件夹的路径
	ChatGPT:
	在一般的Web应用程序结构中，存在classes目录和src目录。这两个目录在Java Web应用程序中具有不同的作用和含义。
	
	src目录（源代码目录）：
		通常包含Java源代码文件（.java文件）和其他源文件，例如XML配置文件、属性文件等。
		src目录中的文件在构建过程中会被编译成字节码文件（.class文件）并输出到classes目录。
		
	classes目录（类文件目录）：
		通常包含编译后的字节码文件（.class文件）和其他资源文件，例如配置文件、模板文件等。
		classes目录是运行时类加载器（Runtime ClassLoader）加载类和资源文件的默认位置。
		对于getResourceAsStream("")方法，它获取的是类路径（Classpath）下的根路径。在上述Web应用程序结构中，类路径的根路径通常是classes目录。
	
	因此，当使用getResourceAsStream("")方法时，它将获取classes目录下的资源文件。你可以将资源文件放置在src目录中的任何子目录中，它们都可以在构建过程中被复制到classes目录中，并通过getResourceAsStream方法进行访问。
	
	总结起来，对于一般的Web应用程序结构，getResourceAsStream("")获取的是classes目录下的路径，其中包括在src目录中的资源文件。

-----------------------------------------------------------------------------------------------------	
	附：
		String username2=request.getParameter("username2");
		username2=new String(username2.getBytes("iso-8859-1"),"UTF-8");		--将得到的数据转换为字节码，再用UTF-8打开
	
	-----------------------------------------------------------------------------------
		
					URLEncoder.encode 与 URLDecoder.decode
					
	URLEncoder.encode(String s, String enc)：用指定的编码将 String 转换为 application/x-www-form-urlencoded 格式。
	URLDecoder.decode(String s, String enc)：用指定的编码将 application/x-www-form-urlencoded 转换为 String 格式。
	
	发送的时候使用 URLEncoder.encode编码，
	接收的时候使用 URLDecoder.decode解码


 */
public class 类加载器读取文件乱码问题 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Properties config = new Properties();
		
		String path = String.class.getClassLoader().getResource("jdbc/util/db.properties").getPath(); 
		path =java.net.URLDecoder.decode(path,"utf-8");	
		System.out.println(path);
		
		InputStream configIn = new FileInputStream(path);
		config.load(configIn);
		System.out.println("加载成功");
	}

}
