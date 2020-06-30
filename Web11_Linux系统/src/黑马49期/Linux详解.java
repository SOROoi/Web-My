package 黑马49期;

/*						Linux卸载 jdk
	
	参考自：https://www.cnblogs.com/lxcy/p/8503446.html
	
	1.卸载JDK(分情况卸载)
		0.查看jdk版本：
			java -version
	
		1.卸载系统自带的jdk版本： 
			1.查看自带的jdk： 	 
				rpm -qa|grep -i java 
			.可能看到如下类似的信息： 
				java-1.6.0-openjdk-1.6.0.35-1.13.7.1.el6_6.i686
				java-1.7.0-openjdk-1.7.0.79-2.5.5.4.el6.i686
			
			2.使用rpm -e --nodeps 命令删除： 
				rpm -e –nodeps java-1.7.0-openjdk-1.7.0.79-2.5.5.4.el6.i686


		2.卸载 rpm安装的jkd版本	(若后续用rpm指令更新jdk，则无需删除 rpm安装的旧jdk，rpm安装默认卸载旧版本)
			1.查看安装的jdk： 	
				rpm -qa|grep jdk 
			.可能看到如下类似的信息： 
				jdk-1.6.0_22-fcs 
		
		   	2.卸载： rpm -e --nodeps jdk-1.6.0_22-fcs  


		3.卸载自解压jdk	(通过tar.gz解压方式 安装的jdk，用此方式卸载)		--------------------------常用
		  　 	1.查看环境变量JAVA_HOME,确定安装目录：
		  		cat /etc/profile
		
			2.找到了安装 Java 的文件夹，可以删除此文件夹。
				rm -rf /usr/local/src/Java
				
 */


/*						Linux安装 jdk
	
	1. tar.gz解压方式安装(需卸载已有jdk)
	
		0.Oracle官网下载 xx.tar.gz安装包
		 .将安装包传输到 linux系统的目录，如：/usr/local/src/Java
		 
		1.解压jdk:
			tar -zxvf jdk-8u251-linux-x64.tar.gz
			
		2.配置环境变量：
			vim /etc/profile
			
		 .在底部添加：
			JAVA_HOME=/usr/local/src/java/jdk1.8.0_251
			CLASSPATH=.:$JAVA_HOME/lib.tools.jar
			PATH=$JAVA_HOME/bin:$PATH
			export JAVA_HOME CLASSPATH PATH
			
		3.使更改的配置立即生效：
			source /etc/profile
		
		4.查看jdk版本：
			java -version
			
 */


/*						Linux安装 redis
 
  	1.下载安装包
  		1.官网：https://redis.io
		2.中文网：http://www.redis.net.cn/
	
	
	2.安装步骤：
		可参考：	/Web11_Linux系统/src/安装/p2_Linux软件安装&Redis入门.pdf
				https://www.cnblogs.com/john-xiong/p/12098827.html
		
		
		0.将安装包 redis.tar.gz传输至Linux中
		
		1.解压：
			tar -xzvf redis.tar.gz
			
		2.安装：
			cd redis								--进入 解压文件夹
			make									--编译 redis		(gcc套件	将.c文件编译为.o文件)								
			make install PREFIX=/usr/local/redis	--安装到指定文件夹
			
		3.复制配置文件到安装目录下:
			cp redis.conf /usr/local/redis			--redis启动需要,包含端口信息
		
		4.将redis配置为后台启动：
			vi /usr/local/redis/redis.conf 			--将daemonize no 改成daemonize yes
		
		
  		5.指定配置文件,启动 redis:
  			/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf 
  			
  		 .查看服务是否启动,redis默认端口6379：
  			ps -ef | grep -i redis
  			
  		6.关闭 redis：
  			/usr/local/redis/bin/redis-cli shutdown
  			
  			
  		( 将redis加入到开机启动:
  			vi /etc/rc.local 
		    在里面添加内容：
		    /usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf (意思就是开机调用这段开启redis的命令)
		 )
		 
	3.常用命令：
		redis-server /usr/local/redis/redis.conf 		--启动redis
		redis-cli shutdown  							--停止redis
		ps -ef | grep -i redis							--查看服务是否启动，6379端口
		
		redis-cli										--连接本地redis服务器
		redis-cli -h 127.0.0.1 -p 6379					--连接远程redis服务器
		(连接后窗口)ping									--测试客户端与redis连接是否正常,正常则回复pong
		(连接后窗口)exit/quit								--断开连接 (或Ctrl+C)
		
		# 卸载redis：
		rm -rf /usr/local/redis 			--删除安装目录
		rm -rf /usr/bin/redis-* 			--删除所有redis相关命令脚本
		rm -rf /root/download/redis-4.0.4 	--删除redis解压文件夹
  			
  	4.重要的3个可执行文件：(安装目录bin包下)
		redis-server：	Redis服务器程序
		redis-cli：		Redis客户端程序，它是一个命令行操作工具。也可以使用telnet根据其纯文本协议操作。
		redis-benchmark：Redis性能测试工具，测试Redis在你的系统及配置下的读写性能
	
	5.开启远程访问
		1.修改redis 配置文件：
			vim redis.conf		
		
		2.将第70行的bind注释，第90行将protected-mode改为no，
			设置密码，
			取消第502行的注释，并修改密码，
			requirepass
		
		3.最后重启redis：

 */

public class Linux详解 {

}
