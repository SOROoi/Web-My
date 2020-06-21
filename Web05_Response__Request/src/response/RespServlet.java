package response;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*	1.将response对象的输出流对象封装到OutputStreamWrite中，指定GBK编码，写入"大家好"
	
	2.通过response中发送响应头Content-type来控制浏览器接收的编码，response中写入UTF-8的 "中国"。
			
			response.setContentType("text/html;charset=UTF-8");//设置响应头Content-type
			response.setCharacterEncoding("UTF-8");			//设置response输入的编码
			response.getWriter().write("中国");
			
*/
public class RespServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		test1(response,request);
		test2(response,request);
	}

	//通过response中发送响应头Content-type来控制浏览器接收的编码，response中写入UTF-8的 "中国"。
	private void test2(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException, IOException {
//		response.setHeader("Content-type", "text/html;charset=UTF-8");
//		response.getOutputStream().write("中国".getBytes("UTF-8"));

		response.setContentType("text/html;charset=UTF-8");//设置响应头Content-type
		response.setCharacterEncoding("UTF-8");			//设置response输入的编码
		response.getWriter().write("中国");

	}

	//将response对象的输出流对象封装到OutputStreamWrite中，指定GBK编码，写入"大家好"
	private void test1(HttpServletResponse response, HttpServletRequest request) {
		// TODO Auto-generated method stub
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(response.getOutputStream(),"GBK");
			osw.write("大家好");
			osw.flush();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ExceptionInInitializerError();
		}finally {
			try {
				osw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}