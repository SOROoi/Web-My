package cookie存用户数据;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*				Cookie--客户端技术--存储用户数据
 
			1.cookie 有时限，存于浏览器磁盘，浏览器携带对应web应用的Cookies 访问应用。
			2.浏览器最多存300个,一个站点20个,最大4K。
			3.未设置maxAge 的cookie 生命为1个会话时间，伴随浏览器内存消失而消失。
			4.Cookie 由request获取，由response写入浏览器。


	0. Cookie简介
			Cookie是客户端技术，程序把每个用户的数据以cookie的形式写给用户各自的浏览器。当用户使用浏览器再去访问服务器中的web资源时，就会带
		  着各自的数据去。这样，web资源处理的就是用户各自的数据了。

	1. Cookie API
			javax.servlet.http.Cookie类用于创建一个Cookie，response接口也中定义了一个addCookie方法，它用于在其响应头中增加一个相应的
		Set-Cookie头字段。 同样，request接口中也定义了一个getCookies方法，它用于获取客户端提交的Cookie。
	
	2. Cookie类的方法：
			1.创建Cookie、设定Cookie的方法
				public Cookie(String name,String value)		----创建一个Cookie对象
				setValue(String newValue)			 		----设定Cookie对象的value值，
				setMaxAge(int expiry)			 			----设置让浏览器存储该cookie的时间，单位为秒。若为0则立即删除
				setPath(String uri)							----设置携带该Cookie访问的资源目录，(默认主机名下)
				setDomain(String pattern)					----设置携带该Cookie访问的域名(如sina.com)(IE会丢弃携带该信息的Cookie)

			2.获取方法
				getName()									----获取该Cookie的name名
				getValue()									----获取Cookie对象的value值
				getMaxAge()									----获取Cookie对象的缓存时间（秒）
				getPath()									----获取携带该Cookie访问的资源目录
				getDomain()									----获取携带该Cookie访问的域名
				
	3.获取浏览器带来的Cookie
			1.只能通过 request.getCookie()获得
				Cookie[] cookies = request.getCookie(); //该Cookie[]中的每一条cookie数据是以"name=value"形式储存的
				
			2.浏览器的Cookie数据是怎样的？
				Cookie: BAIDUID=BDDD94886CEAC642CBC0253CEEC8E500:FG=1; BIDUPSID=BDDD94886CEAC642CBC0253CEEC8E500; PSTM=1555145717;
				 delPer=0; BD_HOME=0; H_PS_PSSID=26522_1421_21091_18559_29064_28519_29099_28724_28963_28834_28585; 
				 BD_UPN=13314752
			
	4.向浏览器写Cookie
			response.addCookie(Cookie cookie); 
			
			
*/

/*				Cookie细节
	1. Cookie标识信息
			一个Cookie只能标识一种信息，它至少含有一个标识该信息的名称（NAME）和设置值（VALUE）。 
			
	2. Cookie储存个数上限
			1.一个WEB站点可以给一个WEB浏览器发送多个Cookie，一个WEB浏览器也可以存储多个WEB站点提供的Cookie。
			2.浏览器一般只允许存放300个Cookie，每个站点最多存放20个Cookie，每个Cookie的大小限制为4KB。
			
	3. Cookie缓存时间--maxAge
			如果创建了一个cookie，并将他发送到浏览器，默认情况下它是一个会话级别的cookie(即存储在浏览器的内存中，一个会话生命后即删除),用户
		退出浏览器之后即被删除。若希望浏览器将该cookie存储在磁盘上，则需要使用maxAge，并给出一个以秒为单位的时间。将
			
			设为0，则命令浏览器删除该cookie：	cookie.setMaxAge(0);
		
	4.删除Cookie前提
			注意，删除cookie时--setMaxAge(0)。若设置了path，删除该路径上的Cookie; 不设置path，默认为删除当前request路径Cookie
	
				 
*/

public class MyCookie extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();	//获得浏览器携带的Cookie 数组
		for(Cookie cookie:cookies) {
			cookie.getName();		//获取该cookie的 name值
			cookie.getValue();		//获取该cookie的 value值
			cookie.getMaxAge();		//获取该cookie的 缓存时间(秒)
			
			cookie.getPath();		//获取该cookie访问的资源目录
			cookie.getDomain();		//获取该cookie访问的域名
		}
		Cookie co = new Cookie("小猪", "配棋");	//创建Cookie对象
		co.setMaxAge(30*60);		//设置缓存时间
		response.addCookie(co);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
