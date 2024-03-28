package p1_web开发介绍;

/*				0.web资源介绍
 
	Internet上供外界访问的Web资源分为：
			静态web资源（如html 页面）：指web页面中供人们浏览的数据始终是不变。
						(静态web资源开发技术: Html)
						
			动态web资源：指web页面中供人们浏览的数据是由程序产生的，不同时间点访问web页面看到的内容各不相同。
						(常用动态web资源开发技术：JSP/Servlet、ASP、PHP等,
						在Java中，动态web资源开发技术统称为Javaweb)
	
	WEB服务器
			学习web开发，需要先安装一台web服务器，然后再在web服务器中开发相应的web资源，供用户使用浏览器访问。
			

	常见WEB服务器
			WebLogic: BEA公司的产品，是目前应用最广泛的Web服务器，支持J2EE规范，而且不断的完善以适应新的开发要求.(收费)
			WebSphere：IBM公司，支持J2EE规范。(收费)
			Tomcat：该服务器支持全部JSP以及Servlet规范。(免费)
*/


/*				1.Tomcat服务器

	1.下载Tomcat服务器
			Tomcat官方站点：百度一下你就知道
			
			1.获取Tomcat安装程序包
				tar.gz文件是Linux操作系统下的安装版本
				zip文件是Windows系统下的压缩版本 
				
			2.安装Tomcat
				将Tomcat安装包 解压,粘贴到C盘目录下即可。(留一份原件，服务器损坏时替换)
				注意：不可将安装包置入含有"空格","中文"的文件夹中！！
				
			3.启动Tomcat
				运行C:\apache-tomcat-8.5.39\bin 包下的startup.bat，
				打开浏览器输入http://localhost:8080, 若出现tomcat提示网页，则启动成功。
				(localhost为本机ip地址，也可写成127.0.0.1)
				(Tomcat服务器默认端口8080)
				
				启动Tomcat前置条件：
					1.我的电脑——环境变量中，必须添加了Java_Home的路径(路径为jdk包的路径)
					2.8080端口未被其他程序占用 (查看正在运行的端口：360流量防火墙——网络连接)
				
				(Catalina_home环境变量的设置问题:
					若此环境变量设置了路径a，则运行startup.bat，将会启动路径a下的服务器，故一般不配置Catalina_home环境变量)

	2.URL.主机名.域名
			http://www.sina.com/	--整体为URL (因使用http协议，未写端口时，默认端口为80)(等同于：http://www.sina.com：80/)
			 |		|	
			协议	   互联网
				   www.sina.com		--为主机名
				   	   sina.com		--为域名
					
									--https协议端口为443
									
			协议 + 主机名 + 端口 + 路径path + 参数 + 锚点(html文档#)
	-------------------------------------------------------------------------------------------------------------------				
	1.访问 确切的服务器资源 顺序： (域名)DNS查找IP ———— IP地址(找到该IP的电脑) ———— 端口号下(找到该电脑下对应port 已启动 的服务器程序) 
								———— 不同主机名 对应资源
								
		主机名(mail.163.com) =	子域名(mail，代表邮箱服务器) + 主域名(163.com)		
	
	2.一个端口下 可以有多个 Host name。
	3.一个 Host name代表一个应用服务器。
	
	4.tomcat服务器(server.xml)中有三个端口，若使用多个tomcat 时需要配置这三个端口。
										分别用于：	8080：启动tomcat
													8005：关闭tomcat
													8009: 用于监听其他服务器转发过来的请求
	
	5.server.xml中的属性。
		protocol: 	HTTP/1.1 协议		--用于监听浏览器发送的请求. 设置成80 后可以直接使用http://localhost 访问。
					AJP/1.3   协议		--用于监听其他服务器转发过来的请求.
		
		connectionTimeout: 连接超时时间
		
		redirectPort: 如果发送的是https 请求. 就将请求转发到8443 端口.
	-------------------------------------------------------------------------------------------------------------------
					
					
					
	3.如何将Tomcat服务器默认端口8080 设置为80
			打开conf包下的server.xml配置文件，将内容<Connector port="8080"中的"8080"改为"80",保存后重启服务器
					
	4.Tomcat目录结构，体系结构
			见图片
			
	5.虚似目录的映射
			Web应用开发好后，若想供外界访问，需要把web应用所在目录交给web服务器管理
			(通过修改conf下server.xml配置文件)
			
			映射：使得http://localhost:8080/itcast目录，对应电脑上g:\news目录
				(使得外界访问http://localhost:8080/itcast/1.html时，实际打开电脑上g:\news\1.html文件)						
			
			(方式一：需重启服务器，不符合现实中情况)
					1.打开Tomcat文件夹下conf\server.xml配置文件,
					2.在</host>标签前加一行，写入<Context path="/itcast" docBase="g:\news"/> (xml需保存为UTF-8编码才有效哦)
					保存后重启服务器，外界即可访问到/itcast/1.html
					
			(不常用：方式二：不重启服务器，添加新的对外访问路径/itcast，映射为电脑中g:\news)
					1.打开Tomcat文件夹下conf\Catalina\localhost文件夹,
					2.在其中创建任意XML文件，
						如 itcast.xml, 此时文件名itcast就成为对外访问路径，
						如 a#b#c.xml, 此时文件名a/b/c就成为对外访问路径，
					3.在itcast.xml文档中写入:<Context docBase="g:\news"/> (xml需保存为UTF-8编码才有效哦)
					保存后外界即可访问到/itcast/1.html 或 /a/b/c/1.html
					
	6.http://localhost:8080/默认打开tomcat页面，如何映射成其他页面(其他应用资源的首页)
			(方式一  创建xml)
			1.打开Tomcat文件夹下conf\Catalina\localhost文件夹,
			2.创建ROOT.xml,此配置文件可绑定默认页面文件
			3.在ROOT.xml中写入<Context docBase="g:\news"/>  (xml需保存为UTF-8编码才有效哦)
			4.修改的该应用资源的web.xml，为该应用资源配置首页.(复制conf下web.xml开头21行，结尾8行，将<welcome-file>标签中内容改为1.html)
			保存后重启服务器，g:\news变为http://localhost:8080/默认目录，浏览器访问http://localhost:8080/即可
			
			(方式二  修改server.xml)
			1.打开Tomcat文件夹下conf\server.xml	(xml需保存为UTF-8编码才有效哦)
			2.</host>标签前加一行，写入<Context path="" docBase="g:\news"/>	写成path="" 就代表映射默认目录
			3.修改的该应用资源的web.xml，为该应用资源配置首页.(复制conf下web.xml开头21行，结尾8行，将<welcome-file>标签中内容改为1.html)
			
	7.虚似目录的映射(tomcat自动映射)
			tomcat服务器自动管理webapps目录下的所有web应用，并把它映射成虚似目录。
			换句话说，tomcat服务器webapps目录中的web应用，外界可以直接访问。	
			
	8.WEB应用的组成结构
			开发web应用时，不同类型的文件有严格的存放规则，否则不仅可能会使web应用无法访问，还会导致web服务器启动报错。
			
			mail	(web应用所在目录)
			  | 
			  |---- html, jsp, css, js文件等		(这些文件一般放在web应用根目录下，根目录根目录下的文件外界可以直接访问)
			  |
			  |---- WEB-INF目录		(java类，jar包，web应用的配置文件存在这个目录下，该目录下的文件外界无法非法直接访问，由web服务器负责调用)
			  			|
			  			|---- classes 目录	--- (java类)
			  			|---- lib 目录	  	---	(java类运行所需jar包)
			  			|---- web.xml 文件	--- (web应用的配置文件)
	
			!.web应用中，web.xml文件是其中最重要的一个文件，它用于对web应用中的web资源进行配置。
			   但凡涉及到对web资源进行配置，都需要通过web.xml文件.		
			 (Web.xml文件必须放在web应用\WEB-INF目录下)
			   
			   例如: 通过web.xml文件配置网站首页。(复制conf下web.xml开头21行，结尾8行，将<welcome-file>标签中内容改为1.html)
	
	9.浏览器访问网站流程(见图片)	
			1.输入URL：www.baidu.com，
			2.查找 Windows本地 Hosts文件，该URL对应的IP地址，
					若有：则跳转到该 IP地址.
					若无：则使用 DNS服务(域名解析).
			3.DNS服务(域名解析),查找该URL对应IP地址，
					若有：则跳转到该 IP地址.
					若无：404NotFound(错误弹窗)
			  
	10.Windows本地Hosts文件地址
			C:\Windows\System32\drivers\etc\HOSTS
			
	11.配置虚拟主机	
			1.如需在WEB服务器中配置一个网站，需使用Host元素进行配置server.xml，例：
				<Host name=”site1” appBase=”c:\app”></Host>
				
			2.配置的主机(网站)要想被外部访问，必须在DNS服务器或windows系统中注册。
			
	12.如何使，在本地访问http://www.baidu.com 变为访问本地 g:\news\1.html 呢？(配置缺省虚拟主机)
			1.修改服务器 server.xml配置文件，在原</Host>标签后加入：
								<Host name="www.baidu.com" appBase="g:\news"> --appBase地址一般为虚拟主机管理的根目录，
																			  --所有该主机名下的web应用资源都放在此目录中。
									<Context path="" docBase="g:\news\mail"/>  --docBase地址为虚拟主机根目录下web应用目录，
																		  	   --当path=""时，docBase对应该主机名默认web应用
								</Host>
			2.修改服务器 server.xml配置文件，将内容<Connector port="8080"中的"8080"改为"80";
			3.重启服务器Tomcat;
			4.修改Windows本地Hosts文件，将本机IP和www.baidu.com添加进HOST文件中
			5.在 g:\news目录(web应用目录)中，创建WEB-INF文件夹，在其中创建web.xml文件，
			    将<welcome-file>标签中内容改为1.html
			6.访问http://www.baidu.com 即可。
			
			(重点：配置服务器server.xml文件的 	端口号   		--Connector标签 				port
				  						   	主机名映射	--Host标签 					name + appBase
				  						   	WEB应用映射	--Host标签中Context标签		path + docBase   )
				  						   	
			(重点：配置缺省虚拟主机需要配置		默认WEB应用映射	--Host标签中Context标签	path="" docBase="web应用目录" 
			  								web应用目录 	--目录创WEB-INF、web.xml文件	复制头尾并将<welcome-file>标签中内容改为1.html)
*/

/* 				其他问题
 	1.打包web应用变为 war
 			命令行中，cd命令进入放置web应用的目录，再输入：jar -cvf news.war news		即可。md
 			
 			!. war包放在tomcat的webapps目录下不能自动解压问题?
 			        原因：1.包中有带中文名的文件
					 2.服务器未启动
					 
	2.配context元素的reloadable元素，可让tomcat自动加载更新后的web应用
	
	3.主机名www.baidu.com有两个作用
			1.通过主机名查找IP地址，连接服务器
			2.向服务器发送主机名，表示需要访问服务器中的该主机名
			
			若浏览器中输入的为IP地址，则默认访问服务器中localhost主机名
			
	4.Tomcat体系结构
				   							Engine  Host  Context
			浏览器----http 协议----Connector----|------|-----|
			浏览器----https协议----Connector----|------|-----|
			见图片。
			
	5.拓展：
			删除tomcat中的应用资源时，应同时删除tomcat的 conf/Catalina/localhost目录下，应用资源的xml配置文件。否则服务器启动会报错
						
	6.配置https单向加密连接器
			1.在命令行输入 keytool -genkey -alias tomcat -keyalg RSA
			2.输入密钥库keystore密码: 如123456
			3.输入需要加密的网站名：如localhost
			4.其它输入项可回车跳过
			  完成后在命令行对应目录下产生.keystore文件(数字证书),将其剪切到conf目录下
			5.在服务器server.xml文件中，将注释的8443端口的<Connector />标签粘贴到下一行，标签内添加 keystoreFile="conf\.keystore"
			 (至此为服务器端的操作)																keystorePass="123456"
			6.外界访问https://localhost:8443	即可
			
			(Warning:
				JKS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore 
				C:\Users\asus pc\.keystore -destkeystore C:\Users\asus pc\.keystore -deststoretype pkcs12"
				 迁移到行业标准格式 PKCS12。)
				 
	7.Tomcat管理平台
			conf目录下tomcat-users.xml 为用户配置，可在此配置进入管理平台的用户名、密码
			
			操作：在tomcat-users.xml 末尾处,  添加一个管理员身份role标签，
									     	user标签roles属性增加值"manager-gui"， (8.5需要manager-gui才能访问管理平台页面)
									                取消该uer注释，即可
			如：				          
			  <role rolename="tomcat"/>
			  <role rolename="role1"/>
			  <role rolename="manager-gui"/>		----添加一个管理员身份role标签
			  
			  <user username="tomcat" password="tomcat" roles="tomcat,manager-gui"/>	----roles属性增加值"manager-gui"
			  <!--
			  <user username="both" password="<must-be-changed>" roles="tomcat,role1"/>	--这两行password格式不对,需注释
			  <user username="role1" password="<must-be-changed>" roles="role1"/>
			  -->
			
 */

/*				网络中常用协议及其端口号：
 * 
				协议			端口号		全称
				HTTP		80端口		HyperText Transport Protocol，		超文本传输协议
				FTP		 	21端口		TransferProtocol 					文件传输协议
				SMTP		25端口		Simple Mail Transfer Protocol，		简单邮件传输协议
				POP3		110端口		POP2、POP3							邮局协议3,都是主要用于接收邮件的
				HTTPS		443端口		Hypertext Transfer Protocol Secure，	超文本传输安全协议
					
				Telnet		23端口		远程登录								远程登录
				DNS			53端口		Domain Name Server，					域名服务器，主要用于域名解析
				POP2		109端口		Post Office Protocol Version 2，		邮局协议2
				RPC			111端口		Remote Procedure Call，				远程过程调用
				IMAP		143端口		Internet Message Access Protocol，	网络消息访问协议
				SNMP		161端口		Simple Network Management Protocol，	简单网络管理协议
				
 */

/*				服务器端server.xml的配置内容
 * 
		1.服务器端口号：
						<Connector port="8080"中的"8080"改为"80"
						
		2.服务器主机名--对应管理着的所有web应用资源：
						<Host name="www.baidu.com" appBase="g:\news"> --appBase地址：所有该主机名下的web应用资源都放在此目录中。
						
		3.浏览器只输入主机名时，对应的默认web应用映射:
						<Host>
							<Context path="" docBase="g:\news\mail"/>  --当path=""时，该主机名默认 docBase对应web应用
																		  	   --docBase地址为默认web应用目录名
						</Host>
		
		4.手动建立映射
						<host>
							<Context path="/itcast" docBase="g:\news"/>		 --docBase地址为实际路径
						</Host>
						
		案例：	
				<Connector port="80"
				<Host name="www.baidu.com" appBase="g:\news">
					<Context path="" docBase="g:\news\mail"/>
					<Context path="/itcast" docBase="g:\map"/>
				</Host>
	------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------------------					
	web应用中web.xml的配置内容
		1.web应用主页：(若未找到index.html，则按顺序往下找)
						  <welcome-file-list>
						    <welcome-file>index.html</welcome-file>
						    <welcome-file>index.htm</welcome-file>
						    <welcome-file>index.jsp</welcome-file>
						    <welcome-file>default.html</welcome-file>
						    <welcome-file>default.htm</welcome-file>
						    <welcome-file>default.jsp</welcome-file>
						  </welcome-file-list>
						  
		2.servlet映射：
						  <servlet>
						    <servlet-name>hi</servlet-name>							--servlet名
						    <servlet-class>包名.类名</servlet-class> (默认classes下)	--实际路径
						  </servlet>
						  <servlet-mapping>
						    <servlet-name>hi</servlet-name>							--servlet名
						    <url-pattern>/hello</url-pattern>						--映射url名
						  </servlet-mapping>
						
		案例：	
				  <servlet>
				    <servlet-name>hi</servlet-name>
				    <servlet-class>show.HelloServlet</servlet-class>
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
	------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------------------------------------------

 */
public class Tomcat服务器 {

}
