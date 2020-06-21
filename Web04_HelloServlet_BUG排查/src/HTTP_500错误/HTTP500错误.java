package HTTP_500错误;
/*
 * 工程：Web04_HelloServlet 
 * 
 * 问题：
 * 		服务器运行正常，主页运行正常，执行servlet失败 (在浏览器窗口输入：http://localhost:8080/HelloServlet/hello)
 * 
 * 错误信息：
  			HTTP Status 500 – Internal Server Error
			Type Exception Report
			
			Message Error instantiating servlet class [/HelloServlet/src/show/HelloServlet]
			
			Description The server encountered an unexpected condition that prevented it from fulfilling the request.
			
			Exception
			
			javax.servlet.ServletException: Error instantiating servlet class [/HelloServlet/src/show/HelloServlet]
				org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493)
				org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)
				org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:660)
				org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
				org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:798)
				org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
				org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:806)
				org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1498)
				org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
				java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
				java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
				org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
				java.lang.Thread.run(Unknown Source)
			Root Cause
			
			java.lang.ClassNotFoundException: /HelloServlet/src/show/HelloServlet
				org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1364)
				org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1185)
				org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493)
				org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)
				org.apache.catalina.valves.AbstractAccessLogValve.invoke(AbstractAccessLogValve.java:660)
				org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
				org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:798)
				org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
				org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:806)
				org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1498)
				org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
				java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
				java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
				org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
				java.lang.Thread.run(Unknown Source)
				
	web.xml内容：
				<?xml version="1.0" encoding="UTF-8"?>
				<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
				  <display-name>Web04_HelloServlet</display-name>
				  <servlet>
				    <servlet-name>hi</servlet-name>
				    <servlet-class>/HelloServlet/src/show/HelloServlet</servlet-class>
				  </servlet>
				  <servlet-mapping>
				    <servlet-name>hi</servlet-name>
				    <url-pattern>/hello</url-pattern>
				  </servlet-mapping>
				  <welcome-file-list>
				    <welcome-file>index.html</welcome-file>
				    <welcome-file>index.htm</welcome-file>
				    <welcome-file>index.jsp</welcome-file>
				    <welcome-file>default.html</welcome-file>
				    <welcome-file>default.htm</welcome-file>
				    <welcome-file>default.jsp</welcome-file>
				  </welcome-file-list>
				</web-app>
 *  
 *  排查：
 *  		1.分析错误信息 Error instantiating servlet class [/HelloServlet/src/show/HelloServlet]，
 *  			   说明在实例化servlet类时出错，检查后发现web应用目录中并不存在该文件路径/HelloServlet/src/show/HelloServlet，那么
 *  			错误信息中的这个路径是从哪里冒出来的呢？
 *  		
 *  		2.检查后发现 Web04_HelloServlet工程中的web.xml书写错误，错误：
 *  						<servlet-class>/HelloServlet/src/show/HelloServlet</servlet-class>
 *  		   正确的格式应该为：
 *  						<servlet-class>show.HelloServlet</servlet-class>
 *  
 *  总结： 
 *  		web.xml中<servlet-class>标签正确格式应该为：(默认为classes文件夹下)	
 *  											 <servlet-class>包名.类名</servlet-class>
 *
 *
 */
public class HTTP500错误 {

}
