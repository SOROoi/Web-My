package p1_response和request对象.java;

import java.net.URLEncoder;

/*		response对象

 	1.HttpServletResponse简介
 		  HttpServletResponse是服务器的响应对象。这个对象中封装了向客户端: 发送数据、发送响应头，发送响应状态码的方法。
 		
 		  request和response对象即然代表请求和响应，那我们要获取客户机提交过来的数据，只需要找request对象就行了。要向
 		客户机输出数据，只需要找response对象就行了。

	2.发送http 响应头
		例：response.setHeader("Content-type", "text/html;charset=UTF-8");
	 	
	3.控制在response中写数据的编码
		response.setCharacterEncoding("UTF-8");
	
	4.同时指定响应头Content-type编码、在response中写数据的编码	
	----(见/Web05_Response__Request/src/response/RespServlet.java)
	 	
	 	response.setContentType("text/html;charset=UTF-8");
	 	
	5.文件下载和中文文件的下载	
	----(见/Web05_Response__Request/src/response/RespServlet2.java)
	 	
	 	1.告诉浏览器以下载方式读此资源
	 	2.写到response对象
	  	3.获取到文件
	 	4.下载文件是中文文件，则文件名需要经过url编码
	 		response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode("折木.jpg", "UTF-8"));
	 		
	6.输出随机图片	
	----(见/Web05_Response__Request/src/response/RespServlet3.java)
	
		BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);	--1.获取BufferedImage图形对象
		Graphics g = image.getGraphics();							--2.获取Graphics图形编辑对象
		
		（g对象操作代码）												--2..用g画图像	
		
		response.setContentType("image/jpeg");						--4.发送响应头告诉浏览器返回数据的类型
		
		response.setDateHeader("Expires",-1);						--5.发送响应头控制浏览器不要缓存response中的数据，每次都访问新图片
 		response.setHeader("Cache-Control","no-cache");									
 		response.setHeader("Pragma","no-cache");
 	
 		ImageIO.write(image,"jpg",response.getOutputStream());		--6.将图片输出到浏览器
 
 	7.控制浏览器定时刷新	
	----(见/Web05_Response__Request/src/response/RespServlet4.java)
 		
 		response.setHeader("refresh","3");							//每隔三秒刷新一次
 		response.setHeader("refresh", "3;url='/day06/index.jsp'");	//每隔三秒刷新一次，跳转到url地址（默认主机下资源）
 
 	8.控制浏览器缓存当前Servlet内容
 		response.setDateHeader("Expires",System.currentTimeMillis()+1000*3600); //缓存response内容一个小时，
 																				期间访问该Servlet都调用缓存文件，而不发送请求。
 																		
 	9.重定向(你来找我，我让你去找别人)
	----(见/Web05_Response__Request/src/response/RespServlet5.java)
	
		特点：
			1.浏览器会向服务器发送两次请求，意味着2个response、request对象
			2.浏览器地址栏发送变化
			3.可定位任意资源
		
		使用：一般不使用，加重服务器负担
  		
 		何时使用：
 			登陆	(重定向：让用户能看到浏览器地址发生改变)，
 		
 			购物(重定向：浏览器地址发生改变。 购物执行第一个购物servlet，结束后地址为第二个购物完成servlet，刷新页面不会有问题)		
 				(转发： 浏览器地址不发生改变。 购物执行第一个和第二个servlet，结束后地址为第一个购物servlet，刷新页面再次购买)
 		
 		方式一：发送状态码302、响应头locaton
	 		response.setStatus(302);											//发送状态码302
			response.setHeader("location", "/Web05_Response__Request/1.html");	//发送响应头location及重定向地址(默认主机名下)
			
		方式二：
	 		response.sendRedirect("/Web05_Response__Request/1.html")			//等同方式一，url默认主机名下
 		

 		
 */

/*		response的细节
 
	1. getOutputStream和getWriter方法分别用于得到输出二进制数据、输出文本数据的ServletOuputStream、Printwriter对象。
	
	2.一个 response对象的 getOutputStream和 getWriter两个方法互相排斥，调用了其中的任何一个方法后，就不能再调用另一方法。
	
	3. Servlet程序向ServletOutputStream或PrintWriter对象中写入的数据将被Servlet引擎从response里面获取，Servlet引擎将这些数据当作响应
	消息的正文，然后再与响应状态行和各响应头组合后输出到客户端。
		
	4. Serlvet的service方法结束后，Servlet引擎将检查getWriter或getOutputStream方法返回的输出流对象是否已经调用过close方法，如果没有，
	Servlet引擎将调用close方法关闭该输出流对象。	



 */
public class Response对象 {

}
