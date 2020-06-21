package p2_HTTP协议;
/*
	1.什么是HTTP协议
			1.客户端连上web服务器后，若想获得web服务器中的某个web资源，需遵守一定的通讯格式，
			HTTP协议用于,定义客户端与web服务器通讯的格式。（具体格式为HTTP请求/HTTP响应）
			
			2.HTTP是hypertext transfer protocol（超文本传输协议）的简写，
			它是TCP/IP协议的一个应用层协议，用于定义WEB浏览器与WEB服务器之间交换数据的过程。
			
			3.HTTP协议的版本： HTTP/1.0	一次连接只有一次HTTP请求
							 HTTP/1.1	一次连接可以多次HTTP请求
			(一个web页面中，使用img标签引用了3幅图片，客户端访问该页面时，总共会访问3次服务器，即向服务器发送了3次HTTP请求)
			
	2.客户端向服务器发送的请求需包含：想要访问的
			协议、IP、端口号、主机名(为IP时默认为localhost)、虚拟目录(无该项时，访问对应主机名下(Connector标签中)path="" 对应的，
																					已配置好web.xml的web应用的主页)
			
	3.使用telnet程序连上web服务器，并使用HTTP协议获取某个页面，快速了解 HTTP协议的作用
			telnet程序是Windows自带的网络客户端软件，可以访问互联网上任何一台服务器。
			操作：1.打开命令行
				 2.输入： telnet localhost 8080	(代表连接localhost服务器的8080端口程序)
				 3.以http协议发送请求：  GET /aa/1.html HTTP/1.1	(获取应用资源)(以http协议形式)
				 					  Host:						(指定指定主机下的资源，不写默认localhost )
		
-------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------				 					  
			
				 					  
	4.HTTP请求
			1.客户端连上服务器后，向服务器请求某个web资源，称之为客户端向服务器发送了一个HTTP请求。
			一个完整的HTTP请求包括：一个请求行、若干请求头、以及实体内容。
				
				举例：
					1.GET /books/java.html HTTP/1.1			----请求行：用于描述客户端的.请求方式、请求的资源名称、使用的HTTP协议版本号。
					3.Accept-Language: en-us
					4.Connection: Keep-Alive
					5.Host: localhost						----多个请求头(2-8行)：用于描述客户端请求哪台主机，以及
					6.Referer: http://localhost/links.asp							         客户端的一些环境信息等.
					7.User-Agent: Mozilla/4.0
					8.Accept-Encoding: gzip, deflate
					9.	 									----一个空行
					10.×××××××××							----实体内容:如表单提交时的请求数据   <form method="post"> 
					
	4-1 HTTP请求——请求行
			1.请求行中的GET称之为请求方式，请求方式有：POST、GET、HEAD、OPTIONS、DELETE、TRACE、PUT
				  					        常用的有： GET、 POST
			
			2.不管POST或GET，都用于向服务器请求某个WEB资源，这两种方式的区别主要表现在数据传递上：
					请求方式为GET方式：
									 位置：   在请求行的URL地址后
									 格式：    以?的形式带上交给服务器的数据，多个数据之间以&进行分隔.
									  		例如：GET /mail/1.html?name=abc&password=xyz HTTP/1.1
									 数据量：在URL地址后附带的参数是有限制的，其数据容量通常不能超过1K。
									 明文提交：提交数据明文可见
									 
					请求方式为POST方式：
									 位置：可以在请求的实体内容中向服务器发送数据，
									 数据量：传送的数据量无限制。
									 
			3. get 与 post 的区别？
				1. Get是不安全的，数据被放在请求的URL中；
				   Post的所有操作对用户不可见。
				   
				2. Get传送的数据量较小，这主要是因为受URL长度限制；
				   Post传送的数据量不受限制。
				   
				3. Get限制Form表单的数据集的值必须为ASCII字符；
				   Post支持整个ISO10646字符集。
				   
				4. Get执行效率却比Post方法高。Get是form提交的默认方法。
				   
									 
	4-2 HTTP请求——请求头
		　　	Accept:浏览器通过这个头告诉服务器，它所支持的数据类型				Accept: text/html,image/* 	
		　　	Accept-Charset: 浏览器通过这个头告诉服务器，它支持哪种字符集		Accept-Charset: ISO-8859-1
		　　	Accept-Encoding：浏览器通过这个头告诉服务器，支持的压缩格式		Accept-Encoding: gzip,compress
		　　	Accept-Language：浏览器通过这个头告诉服务器，它的语言环境			Accept-Language: zh-cn (中文-中国)
		　　	Host：浏览器通过这个头告诉服务器，想访问哪台主机					Host: www.it315.org:80
		　　	If-Modified-Since: 告诉服务器，缓存数据的时间。是否需要更新页面	If-Modified-Since: Tue, 11 Jul 2000 18:23:51 GMT
		　　	Referer：浏览器通过这个头告诉服务器，客户机是哪个页面来的  防盗链	Referer: http://www.it315.org/index.jsp
		    User-Agent:通过这个头告诉服务器，客户机软件环境(操作系统)			User-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)
		　　	Cookie：浏览器可以通过这个头向服务器带数据						Cookie
			Connection：通过这个头告诉服务器，请求完后是断开链接还是保持链接	Connection: close/Keep-Alive 
			Date:当前时间值												Date: Tue, 11 Jul 2000 18:23:51 GMT
			Range:指示服务器只传输一部分Web资源，可实现断点续传				Range:bytes=1000-2000		(传输范围从1000到2000字节)
																		Range:bytes=1000-		(传输资源中第1000个字节以后的内容)
																		Range:bytes=1000		(传输最后1000个字节的内容)
				

--------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------


	5.HTTP响应
			1.一个HTTP响应 代表服务器向客户端回送的数据，
						   它包括：一个状态行、若干消息头、以及实体内容
				
				举例：
					1.HTTP/1.1	  200 	OK					--状态行: HTTP版本号  状态码  原因叙述   ：用于描述服务器对请求的处理结果。
					2.Server: Microsoft-IIS/5.0
					3.Date: Thu, 13 Jul 2000 05:46:53 GMT
					4.Content-Length: 2291					--多个响应头(2-6行)：用于描述服务器的基本信息，以及数据的
					5.Content-Type: text/html									描述，服务器通过这些数据的描述信息，可
					6.Cache-control: private									以通知客户端如何处理等一会儿它回送的数据
					7.										--一个空行
					8.<HTML>								--实体内容：代表服务器向客户端回送的数据
					9.<BODY>
					……
					
	5-1 HTTP响应—状态行
			格式： HTTP版本号		状态码　		原因叙述<CRLF>
			举例： HTTP/1.1  		200 		OK
			
			状态码：表示服务器对请求的处理结果，它是一个三位的十进制数。响应状态码分为5类，如下所示：
			
					   状态码--------------------------含义
					100～199	(不常用)---------表示成功接收请求，要求客户端继续提交下一次请求才能完成整个处理过程
					200～299	----------------表示成功接收请求并已完成整个处理过程，常用200
					300～399	----------------为完成请求，客户需进一步细化请求。例如，请求的资源已经移动一个新地址，常用302(去找别人)、
																										307和304(去找缓存)
					400～499	----------------客户端的请求有错误，常用404(web服务器中无此资源) 403(无权限,服务器拒绝访问此资源)
					500～599	----------------服务器端出现错误，常用 500
					
	5-2 HTTP响应—响应头
		　　Location: 服务器通过这个头，来告诉浏览器跳到哪里						  --Location: http://www.it315.org/index.jsp 
		　　Server：服务器通过这个头，告诉浏览器服务器的型号						  --Server:apache tomcat
		　　Content-Encoding：服务器通过这个头，告诉浏览器，数据的压缩格式			  --Content-Encoding: gzip 
		　　Content-Length: 服务器通过这个头，告诉浏览器回送数据的长度			  --Content-Length: 80
		　　Content-Language: 服务器通过这个头，告诉浏览器语言环境				  --Content-Language: zh-cn 
		　　Content-Type：服务器通过这个头，告诉浏览器回送数据的类型				  --Content-Type: text/html; charset=GB2312
		　　Refresh：服务器通过这个头，告诉浏览器多长时间刷新一次					  --Refresh: 1;url=http://www.it315.org			
		　　Last-Modified:服务器通过这个头，告诉浏览器当前资源缓存时间			  --Last-Modified: Tue, 11 Jul 2000 18:23:51 GMT
		　　Content-Disposition: 服务器通过这个头，告诉浏览器以下载方式打开数据	  --Content-Disposition:attachment; filename=aaa.zip
		　　Transfer-Encoding：服务器通过这个头，告诉浏览器数据是以分块方式回送的	  --Transfer-Encoding: chunked
		　　Connection: close/Keep-Alive										  --Connection: close/Keep-Alive   
		　　Accept-Ranges:该头说明Web服务器是否支持Range,支持则返回bytes			  --Accept-Ranges: bytes/none
		　　Content-Range:指定了返回的 web资源字节范围							  --Content-Range:1000-3000/5000 (返回1000-3000的字节)
																		    (资源共5000字节)
		　　ETag:缓存相关的头			
		　　Expires:服务器通过这个头，告诉浏览器把回送的资源缓存多少时间，-1或0不缓存  --Expires: -1
		　　
		　　Cache-Control: no-cache	
		　　Pragma: no-cache
			服务器通过以上两个头，控制浏览器不要缓存





 	
 
 */
public class Http协议 {

}
