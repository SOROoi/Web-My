package session存用户数据;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*				Session--服务器技术--存储用户数据

	1. Session 简介
			在WEB开发中，服务器可以为每个用户浏览器创建一个会话对象（session对象），注意：一个浏览器独占一个session对象(默认情况下)。因
		此，在需要保存用户数据时，服务器程序可以把用户数据写到用户浏览器独占的session中，当用户使用浏览器访问其它程序时，其它程序可以从用
		户的session中取出该用户的数据，为用户服务。
			服务器session默认保存30分钟，客户端Cookie(JSESSIONID,id)默认生命周期为一个会话、默认保存在该会话的浏览器内存中
	
	2. Session 的生命周期
			1.创建Session:
				服务器Servlet程序调用requset.getSession(),创建session及其id,同时自动： 
												1.向客户端发送服务器Session的ID的默认Cookie(JSESSIONID,id)	--以下称为IDcookie
												2.若用超链接携带IDcookie,使用URL重写。浏览器无IDcookie，将IDcookis写在URL后。
												浏览器有IDcookie,则不重写。获得Session对象。
				(服务器session默认保存30分钟，客户端IDcookis默认生命周期为一个会话、默认保存在该会话的浏览器内存中)
			
			2.客户端发出请求时，背后会带着 数据、Cookie、Cookie(JSESSIONID,id)访问资源，
			
			3.若Servlet调用getSession(false),将会自动查找	1.客户端的IDcookie			与服务器中的Session匹配,匹配成功返回Session对象
														2.或超链接后?传来的IDcookie
			
			4.若Servlet向浏览器写回一个自定义IDcookie(JSESSIONID,session.getId()),可以长时间保存用户数据,浏览器依旧可匹配服务器session数据
	
			5. session保存时间结束(默认30分钟)、调用session.invalidata()删除session，session销毁。
	
	3. getSession()与getSession(false)
			1.getSession():
						查找客户端IDcookie和超链接后?传来的IDcookie,与服务器id匹配，无匹配：创建一个新的Session对象	
																			      有匹配：获得Session对象
																			  
			2.getSession(false):	
						只获得Session对象，若无匹配id，返回null
				
	3.创建Session
			1.Session对象由服务器创建，只有访问过服务器servlet才会创建，保存关于用户的数据
			2.Servlet调用request.getSession()创建session，多次调用只会获得同一个session(有了就不创建，变为获取)
			
	4.获取Session
			Session对象由服务器获取，调用request.getSession(false)，只会获取session对象; 获取不到，返回null
			
	5.使用Session对象
			session.setAttribute(String name,Object value)						----向session中存入名为name的value数据
			session.getAttribute(String name)				---Object			----获取session中名为name的value数据(Object类型)
			session.getId()									---String			----获取此session的id值(用以与客户端Cookie-id匹配)
			session.invalidate()												----删除此session
			
			session.getAttributeNames()						---Enumeration		----获取session中所有数据的name迭代器Enumeration
			
	 6.IE禁用Cookie后的session处理
			1.禁用Cookie后果：
				浏览器本地无法存储与Session匹配对应的IDcookie,服务器无法得到与用户交互数据的session,无法处理用户想要的请求
				
			2.解决方案：URL重写
				1.解决场所：Servlet中
				2.解决时机：在创建session后，即requst.getSession()后
				3.解决方法：
							response. encodeRedirectURL(java.lang.String url) 		//用于对sendRedirect方法后的url地址进行重写。
							
							response. encodeURL(java.lang.String url)			//用于对表单action和超链接的url地址进行重写 
							
	7.校验session、数据是否为null
			在使用session编写Servlet程序时，需要时刻注意：
				1.用reques.getSession(false)得到的session是否为null。若为null，是直接访问url过来的,需处理
				2.恶意访问者通常不带数据直接访问，此时要做出防范，对数据为空的情况做出处理
				
	8.session案例－防止表单重复提交
	----(见/Web06_Cookie_Session/src/防止重复提交表单form/DoFormServlet.java)
			1.标识号
					表单页面由servlet程序生成，servlet为每次产生的表单页面分配一个唯一的随机标识号，并在FORM表单的一个隐藏字段中设置这个标识
				号,同时在当前用户的Session域中保存这个标识号。
				
			2.逻辑
					当用户提交FORM表单时，负责处理表单提交的serlvet得到表单提交的标识号，并与session中存储的标识号比较，如果相同则处理表单
				提交,处理完后清除当前用户的Session域中存储的标识号。
			
			3.在下列情况下，服务器程序将拒绝用户提交的表单请求：
					1.存储Session域中的表单标识号与表单提交的标识号不同
					2.当前用户的Session中不存在表单标识号
					3.用户提交的表单数据中没有标识号字段
					
			4.编写工具类生成表单标识号：TokenProcessor
				
	9.session案例一次性校验码
	----(见G:\学习资料\JavaWeb学习资料\方立勋30天\Day7-Cookie和Session\11-利用session校验图片认证码.avi)
			
			逻辑：1.用户点击验证码图片，向服务器发送请求，
				2.服务器生成验证码X，存入session中,
				3.服务器生成验证码图片，发送回客户端
				4.用户提交表单，获取验证码与session中的检验是否一致
			
			1.一次性验证码的主要目的就是为了限制人们利用工具软件来暴力猜测密码。 
			
			2.服务器程序接收到表单数据后，首先判断用户是否填写了正确的验证码，只有该验证码与服务器端保存的验证码匹配时，服务器程序才开始正常的
			表单处理流程。 
			
			3.密码猜测工具要逐一尝试每个密码的前题条件是先输入正确的验证码,而验证码是一次性有效的,就阻断了密码猜测工具的自动地处理过程。
						 
	 10. Session和Cookie的主要区别在于：
			1.Cookie是把用户的数据写给用户的浏览器。
			2.Session把用户的数据写到服务器用户对应的session中
		
*/

public class MySession extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session=request.getSession();	//创建-获得Session对象
		session.setAttribute("帅哥", new Object());	//向session 域中存入 String-Object 数据
		Object o = session.getAttribute("帅哥");		//从session 域中获取	对应Object 对象
		session.getId();	//获取该session对象的id值
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
