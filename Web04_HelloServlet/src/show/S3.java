package show;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 		Eclipse中启动服务器，项目中Servlet更改将会自动部署到服务器
 * 
 */

public class S3 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//类装载器获得相对于classes目录下的资源文件路径
		String s = this.getClass().getClassLoader().getResource("db.properties").getPath();
		FileInputStream in = new FileInputStream(s); 
		Properties p = new Properties();
		p.load(in);
		String value = p.getProperty("url");
		System.out.println(value);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}