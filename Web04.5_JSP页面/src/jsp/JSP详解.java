package jsp;

/*								JSP详解
	1.什么是JSP？
		1.JSP全称是Java Server Pages，它和servle技术一样，都是SUN公司定义的一种用于开发动态web资源的技术。
		2.JSP技术的最大特点是，写jsp就像在写html，但它相比html而言，html只能为用户提供静态数据，而Jsp技术允许在页面中嵌套java代
		  码，为用户提供动态数据。

	2.JSP原理
		1.当用户访问一个JSP页面时，会向一个Servlet容器（Tomcat等）发出请求；
		2.JSP被请求时，JSP引擎先把该JSP文件转换成一个Java源文件(Servlet),
		3.转换时如果JSP文件有任何语法错误，转换过程将中断，并向服务端和客户端输出出错信息。
		4.转换成功，JSP引擎用javac把该Java源文件编译成相应的class文件。
		5.jspService()方法被调用来处理客户端的请求。
		
		(Tomcat中 JSP转换后的 servlet文件位于：G:\apache-tomcat-8.5.39\work\Catalina\localhost\Web12_AJAX\org\apache\jsp)
	
	3. JSP转换后的 servlet代码：
		jspService方法：
				public void _jspService(HttpServletRequest request,HttpServletResponse response){
					...
					PageContext pageContext;
					ServletContext application;		
					ServletConfig config;			
					Object page = this;				
					HttpSession session = null;
					JspWriter out = null;			
					JspWriter _jspx_out = null;
					PageContext _jspx_page_context = null;
					
					//application：	表示 ServletContext对象,等同于 context域对象
					//config：		表示 ServletConfig对象
					//page：			表示该 JSP转换后的 servlet对象
					//out:			表示 JspWriter对象，等同于：response.getgetWriter()
					//session：		表示 HttpSession对象，等同于 session域对象
					  
					...
			      	out.write("<body>\r\n");
			      	out.write("\t当前时间为：\r\n");
			      	out.write("\t");
			
					Date date = new Date();					//Service方法中写入了 JSP页面中的代码
					out.write(date.toLocaleString());
				
			      	out.write("\r\n");
			      	out.write("</body>\r\n");
					...
				}
 */
	

/*								JSP语法
 
 	1.JSP模版元素
 		概述：	JSP页面中的HTML内容称之为JSP模版元素。它定义了页面的结构和外观。


	2.JSP脚本表达式
		概述：	用于将 程序数据 输出到客户端
		语法：	<%= 变量或表达式 %>
		例：		当前时间:<%= new java.util.Date() %> 
		注意：	JSP脚本表达式中的变量或表达式后面不能有分号（;）
	
	
	3.JSP脚本片断
		概述：	1.用于在JSP页面中编写多行Java代码。
				2.一个JSP页面中可以有多个脚本片断，在两个或多个脚本片断之间可以嵌入文本、HTML标记和其他JSP元素。
				3.多个脚本片断中的代码可以相互访问.
				4.单个脚本片断中的Java语句可以是不完整的，但是，多个脚本片断组合后的结果必须是完整的Java语句.
		
		语法：	<% 
				   多行java代码 
				%> 
				
		例：		<%
				  int x = 10;
				  out.println(x);
				%>
		注意：	1.JSP脚本片断中只能出现java代码，不能出现其它模板元素。
				2.JSP引擎在翻译JSP页面中，会将JSP脚本片断中的 Java代码将被原封不动 地放到Servlet的_jspService方法中。
				3.JSP脚本片断中的Java代码必须严格遵循Java语法。


	5.JSP声明
		概述：	1.Jsp声明中的java代码，会被翻译到_jspService方法的外面。
				2.JSP声明可用于定义 静态代码块、成员变量和方法。
				3.多个静态代码块、变量和函数可以定义在一个JSP声明中，也可以分别单独定义在多个JSP声明中。

				
		语法：	<%！ 
				 java代码
				%>

		例:		<%!
				  static 
				  { 
				  	System.out.println("loading Servlet!"); 
				  }
				  private int globalVar = 0;
				  public void jspInit()
				  {
					System.out.println("initializing jsp!");
				  }
				%>
	
	
	6.JSP注释
		概述：	JSP中的注释
		语法：	<%-- 注释信息 --%>
		注意：	JSP引擎在将JSP页面翻译成Servlet程序时，忽略JSP页面中被注释的内容。
		
		
	7.JSP指令
		概述：	1.JSP指令（directive）是为JSP引擎而设计的，它们并不直接产生任何可见输出，而只是告诉引擎如何处理JSP页面中的其余部分。
				2.JSP 2.0规范中共定义了三个指令：
											page指令
											Include指令
											taglib指令
				3.如果一个指令有多个属性，这多个属性可以写在一个指令中，也可以分开写。

		语法：	<%@ 指令 属性名="值" %>

				
		page指令：	用于定义JSP页面的各种属性，无论page指令出现在JSP页面中的什么地方，它作用的都是整个JSP页面，为了保持程序的可读性和
				  	遵循良好的编程习惯，page指令最好是放在整个JSP页面的起始位置。
		include指令：用于引入其它JSP页面，如果使用include指令引入了其它JSP页面，那么JSP引擎将把这两个JSP翻译成一个servlet。所以include
					指令引入通常也称之为静态引入。
		Taglib指令:	用于在JSP页面中导入标签库，讲自定义标签技术时讲。

		
		例:		page:		<%@ page contentType="text/html;charset=gb2312"%>
				  			<%@ page import="java.util.Date"%>
				    		或
				  			<%@ page contentType="text/html;charset=gb2312" import="java.util.Date"%>
				  			
				include:	<%@ include file="relativeURL"%>		
						   	//其中的file属性用于指定被引入文件的路径。路径以“/”开头，表示代表当前web应用。
 */


/*								JSP九大隐式对象
	1.九大对象：	
			request
			response
			session
			application			--servletContext
			config				--servletConfig
			page				--this
			exception
			out					--jspWriter
			pageContext								
	
	2.pageContext对象：
		概述：	1.pageContext对象 代表JSP页面的运行环境，
				2.pageContext对象 封装了对其它8大隐式对象的引用，
				3.pageContext对象 自身是一个域对象，可以用来保存数据。
				4.pageContext对象 还封装了web开发中经常涉及到的一些常用操作。(例如引入和跳转其它资源、检索其它域对象中的属性等。)
				
		通过pageContext获得其他对象:
				getException方法返回	exception隐式对象 
				getPage方法返回	page隐式对象
				getRequest方法返回	request隐式对象 
				getResponse方法返回	response隐式对象 
				getServletConfig方法返回	config隐式对象
				getServletContext方法返回	application隐式对象
				getSession方法返回	session隐式对象 
				getOut方法返回	out隐式对象
				
		引入和跳转到其他资源:
				1.PageContext类中：定义了一个forward方法和两个include方法，分别简化和替代RequestDispatcher.forward方法
				   和include方法。
				2.方法接收的 资源 如果以“/”开头， “/”代表当前web应用。

	

 */


/*								JSP标签
	1.JSP标签
		JSP标签也称之为Jsp Action(JSP动作)元素，它用于在Jsp页面中提供业务逻辑功能，避免在JSP页面中直接编写java代码，造成jsp页面
		难以维护。
 
 
	2.JSP常用标签
		<jsp:include> 标签:
			概述：	用于把另外一个资源的输出内容插入进当前JSP页面的输出内容之中，这种在JSP页面执行时的引入方式称之为动态引入。
			
			语法：	<jsp:include page="relativeURL | <%=expression%>" flush="true|false" />
						//page属性用于指定被引入资源的相对路径，它也可以通过执行一个表达式来获得。
						//flush属性指定在插入其他资源的输出内容时，是否先将当前JSP页面的已输出的内容刷新到客户端。
			
			
		<jsp:forward> 标签:
			概述：	用于把请求转发给另外一个资源。
			
			语法：	<jsp:forward page="relativeURL | <%=expression%>" /> 
						//page属性用于指定请求转发到的资源的相对路径，它也可以通过执行一个表达式来获得。

			
		<jsp:param> 标签:
			概述：	当使用<jsp:include>和<jsp:forward>标签引入或将请求转发时，可以使用<jsp:param>标签向这个资源传递参数。
			
			语法1：	<jsp:include page="relativeURL | <%=expression%>">
						<jsp:param name="parameterName" value="parameterValue|<%= expression %>" />
					</jsp:include>
						//name属性用于指定参数名，
						//value属性用于指定参数值。
						//在<jsp:include>和<jsp:forward>标签中可以使用多个<jsp:param>标签来传递多个参数
					
					
			语法2：	<jsp:forward page="relativeURL | <%=expression%>">
						<jsp:param name="parameterName" value="parameterValue|<%= expression %>" />
					</jsp:include>
 */


/*								映射JSP、四大域对象
	1.映射JSP：
		<servlet>
			<servlet-name>SimpleJspServlet</servlet-name>
			<jsp-file>/jsp/simple.jsp</jsp-file>
			<load-on-startup>1</load-on-startup >
		</servlet>
			……
		<servlet-mapping>
			<servlet-name>SimpleJspServlet</servlet-name>
			<url-pattern>/xxx/yyy.html</url-pattern>
		</servlet-mapping>
	
	2.四大域对象
		pageContext（称之为page域） 
		request（称之为request域）
		session（称之为session域）
		servletContext（称之为application域）
	
 */

public class JSP详解 {

}
