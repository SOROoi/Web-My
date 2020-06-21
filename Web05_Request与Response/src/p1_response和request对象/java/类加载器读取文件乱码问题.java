package p1_response和request对象.java;

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
 	解决方法：
 		Path=java.net.URLDecoder.decode(path,"UTF-8");	//需要进行decode才能正常显示中文路径

	
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
