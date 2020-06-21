package response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 文件下载和中文文件的下载
 * 	1.获取到文件
 * 	2.写到response对象
 * 	3.告诉浏览器以下载方式读此资源
 * 	
 *  4.下载文件是中文文件，则文件名需要经过url编码
 */
public class RespServlet2 extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// test1(response);	// 下载非中文文件
		test2(response);	// 下载中文文件
	}

	// 下载中文文件，中文文件名需要经过url编码
	private void test2(HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 1.发送响应头:下载方式打开
		response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode("折木.jpg", "UTF-8"));
		
		// 2.获取文件
		InputStream in = this.getServletContext().getResourceAsStream("/Download/折木.jpg");
		// 3.写入response
		OutputStream out = response.getOutputStream();
		byte[] by = new byte[1024];
		int len = 0;
		while ((len = in.read(by)) != -1) {
			out.write(by, 0, len);
		}
		out.close();
		in.close();
	}

	// 下载非中文文件
	private void test1(HttpServletResponse response) throws IOException {
		// 发送响应头
		response.setHeader("Content-Disposition", "attachment; filename=1.jpg");
		// 获取文件
		InputStream in = this.getServletContext().getResourceAsStream("/Download/1.jpg");
		// 写入response
		OutputStream out = response.getOutputStream();
		byte[] by = new byte[1024];
		int len = 0;
		while ((len = in.read(by)) != -1) {
			out.write(by, 0, len);
		}
		out.close();
		in.close();
	}

}