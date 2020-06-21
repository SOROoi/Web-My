package p1_response和request对象.java;

/*				防盗链		(--见/Web05_Response__Request/src/request/RequestDemo.java)
 	
 	1.为了防止：
 			1.用户直接粘贴url访问，不看广告
 			2.别的网站引用url访问，不看广告

	2.防盗链：
			原理：利用请求头referer判断来源页。从本网站来源的，才可查看。
											非本网站来源，重定向，跳转到首页。
 
 	3.例：
			String referer=request.getHeader("referer");
			if(referer==null || !referer.startsWith("http://localhost")){	
				response.sendRedirect("/day06/index.jsp");			
				return;
			}
			response.getOutputStream().write("bbb".getBytes());
		 
			if中的条件：	 1.referer==null							用户直接粘贴url访问
						 2.!referer.startsWith("http://localhost")	别的网站引用url访问
		 
 */

public class 防盗链 {

}
