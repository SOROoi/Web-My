package maven;

/*							Maven项目构建工具
 * 
				(	1.是一个项目	
					2.有配置文件setting.xml	
					3.作为jar包仓库	
					4.使用者遵循 Maven 项目结构, pom.xml
					5.MVN 命令
					6.scope 范围		)
 
 	1.什么是Maven?
			是apache下的一个开源项目，是纯java开发，用来提供Java项目需要的jar包、管理java项目的.


	2.Maven下载、安装、环境
			1.下载：http://maven.apache.org/download.cgi#
			2.安装：下载完成后将安装包解压即可.
			3.配置环境变量：将/bin目录配置到环境变量中
			4.jdk要求:需求jdk1.7以上

	3.Maven好处
			将项目需要的jar包放置在Maven仓库中。1.需要什么jar即可导入; 2.减少项目的占用空间; 3.跨平台; 4.大型项目可提高开发效率

	4.Maven原理(依赖管理)		
	
			Maven项目(需要jar包)		 ——————查找—————>	Maven本地仓库(存放jar包)、私服	 ——————查找—————>	Maven中央仓库(apache维护) 
					|										|												|
			(提供jar坐标:公司组织、项目名、版本)			(本地仓库索引——>查找)								(找到后下载到本地仓库)
	
		  
		  
	5.Maven软件的核心配置
			conf/settings.xml文件
			1.配置本地仓库地址: 放入<localRepository>D:\Maven\repository</localRepository>
			2.配置中央仓库的镜像下载地址(阿里云)：	<mirror>
											       <id>alimaven</id>
											       <mirrorOf>central</mirrorOf>
											       <name>aliyun maven</name>
											        <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
											     </mirror>

			2.配置默认编译器：放入	<profile>
                                    <id>jdk-1.8</id>

                                    <activation>
                                        <activeByDefault>true</activeByDefault>
                                        <jdk>1.8</jdk>
                                    </activation>

                                    <properties>
                                        <maven.compiler.source>1.8</maven.compiler.source>
                                        <maven.compiler.target>1.8</maven.compiler.target>
                                        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
                                    </properties>
                                </profile>
			
	
	6.Maven项目目录结构
	
			web项目
			 |
			 |--- pom.xml
			 |
			 |--- target目录		(编译后文件目录)
			 |
			 |--- src目录		(编译前文件目录)
			 		|
			 		|--- main目录	(主目录)			
			 		|		|
			 		|		|--- java目录		(放置java代码)
			 		|		|--- resources目录	(放置配置文件properties、xml)
			 		|		|--- webapp目录
                    |               |
                    |               |--- WEB-INF目录
                    |                        |
                    |                        |--- classes目录
                    |                        |--- lib目录
                    |                        |--- web.xml文件
			 		|
			 		|--- test目录	(测试目录Junit)
			 				|
			 				|--- java目录		(纯java代码  Junit测试)
			 				|--- resources目录	(Junit测试所用的配置文件，如果里面没有配置文件默认从main里找)
	
	7.Maven项目的maven核心
			pom.xml文件
			1.打包方式：<packaging>jar</packaging>
			2.添加依赖：添加<dependency>标签:
							<dependencies>
								<dependency>
									......
								</dependency>	
							</dependencies>
							
			3.添加插件：添加<build>标签:
							<build>
								<plugins>
									<plugin>
										......
									</plugin>
								</plugins>
							</build>
		
		
		
	8.Maven常用命令:(命令行、项目目录中)
				mvn tomcat:run			一键启动,创建target
				mvn clean				清理编译好的文件，删除target文件夹
				
				mvn compile				编译 src\main目录中的java文件，创建target
				mvn test				编译并运行了 src\test目录中的java文件，创建target
				mvn package				执行compile、test，将项目打包为war文件,存放在target中	  (war文件名称，与pom.xml中配置有关)
				mvn install 			执行compile、test、package，将项目打包为war文件,发布到本地仓库
				mvn deploy				将项目打包，发布到私服
		
	9.Maven生命周期:
			1.Clean生命周期
				Clean
				
			2.Default生命周期
				Compile   test  package  install  deploy(发布到私服)
				
			3.Site生命周期
				Site (在target下生成site文件夹,其中生成项目的站点文档)
			
			4.不同生命周期的命令可以同时执行
				如：mvn clean package
				
	10.依赖范围scope
	 		scope选项：
	 			1.Compile：			    编译(compile)时需要，测试时(test)需要，运行时需要(打包时添加该jar)
	 			2.Provided:			    编译(compile)时需要，测试时(test)需要，运行时不需要(打包时不添加该jar)
	 			3.Runtime 数据库驱动包：编译(compile)时不需要，测试时(test)需要，运行时需要(打包时添加该jar)
	 			
	 			4.Test    junit.jar：  编译(compile)时不需要，测试时(test)需要，运行时不需要(打包时不添加该jar)
			
 */

/*							项目构建
	1.Eclipse前置准备
	 	1.安装插件：
	 		eclipse上安装 m2e插件。
	 	2.配置maven版本：
	 		eclipse中，preferences选项下 maven栏下 Installations选项，点击add，添加 Maven 源文件目录。(maven-xx-bin)
	 	3.绑定maven配置文件
	 		eclipse中，preferences选项下 maven栏下 User Settings选项，User Settings栏中绑定settings.xml的地址
 		
 	2.web项目创建
	 	1.创建项目	
	 		eclipse中, new ——> maven project ——> 勾选create a simple project(skip archetype selection-跳过骨架选项)。
	 		---如果不勾选，则不跳过骨架，创建出的项目目录是不全的	
	 		
	 		(非web项目 只进行这一步/并打包为jar 即可)
	 		
	 		
	 	2.输入项目坐标
	 		group Id：		公司或组织名称
	 		artifact Id：	模块名称或项目名称
	 		version：		版本					//0.0.1-snaoshot-快照版本	release-正式版本	
	 		packaging：		war包				//打包方式：jar(项目)、war(web项目)、pom(作为父工程)
	 		
	 		
	 	3.将项目变为 web项目	(转为web项目)	(打包方式为war，右键才会有Java EE Tools选项)
	 		选中项目，右键 ——> Java EE Tools ——> Generate Deployment Descriptor Stub   
	 		
	 		（红叉警告：缺少web.xml文件）
            
            
	 	4.更改编译版本	（setting.xml设定过  则可跳过）
	 		1.手动复制 /常用配置文件/pom.xml, 其中的<build>标签内容。黏贴到项目下pom.xml中。
	 		2. eclipse中, 右键项目 ——> Maven ——> update project
	 		
			(setting.xml设定：打开Maven软件setting.xml文件，在<profiles>标签下添加<profile>标签，配置默认的编译器版本)
			    如下：       			   <profile>
			                        <id>jdk-1.8</id>
			
			                        <activation>
			                            <activeByDefault>true</activeByDefault>
			                            <jdk>1.8</jdk>
			                        </activation>
			
			                        <properties>
			                            <maven.compiler.source>1.8</maven.compiler.source>
			                            <maven.compiler.target>1.8</maven.compiler.target>
			                            <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
			                        </properties>
			                    </profile>
            
	 	5.创建servlet
	 		在项目/src/main/java中，创建servlet
	 		
	 	6.pom.xml中添加jar包
	 		右键pom.xml ——> Maven ——> add dependency(添加依赖) ——> 索引查找关键字，scope选择 provided 则不会发生jar包冲突	
	 		
	 		(索引查找需要先创建索引)
	 		//创建索引：
	 		 	1.打开Maven仓库视图：点击 Window栏 ——> Show View ——> Other... ——> Maven Repositories
	 		 	2.创建索引：点击展开Local Repositories ——> 右键Local Repository ——> Rebuild Index
	 		 	
	 		(依赖范围scope)
	 		//scope选项：
	 			1.Compile：			    编译(compile)时需要，测试时(test)需要，运行时需要(打包时添加该jar)
	 			2.Provided:			    编译(compile)时需要，测试时(test)需要，运行时不需要(打包时不添加该jar)
	 			3.Runtime 数据库驱动包：编译(compile)时不需要，测试时(test)需要，运行时需要(打包时添加该jar)
	 			4.Test    junit.jar：  编译(compile)时不需要，测试时(test)需要，运行时不需要(打包时不添加该jar)
	 		
	 	7.运行项目
	 		右键项目 ——> Run As ——> Maven Build... ——> 输入tomcat:run 点击run	//运行指定命令(maven项目自带tomcat插件)
	 		右键项目 ——> Run As ——> Maven Build    ——>						//运行上一次执行的命令
 		
 		
	3. Maven项目中添加、修改 Tomcat 插件
 		1.添加插件: 右键项目 ——> Maven ——> Add  Plugin ——> 索引查找tomcat，选择后pom.xml中自动添加<plugin>及插件信息	//默认tomcat插件为：org.codehaus.mojo tomcat-maven-plugin 1.1
 		2.修改插件: 将pom.xml原有插件<plugin>注释或删除，Add  Plugin新插件
 		3.更改tomcat端口及访问路径：pom.xml中修改为 ——>	<plugin>
														<groupId>org.codehaus.mojo</groupId> 
														<artifactId>tomcat-maven-plugin</artifactId> 
														<version>1.1</version> 
														<configuration> 
															<port>8888</port> 	//修改端口
															<path>/first</path> //修改访问路径
														</configuration> 
													</plugin>
 */

/*							第一天总结
 	1.Maven的仓库
		本地仓库
		远程仓库（私服）
		中央仓库
 
 	2.添加依赖
		从网络上搜索
		在本地重建索引，以索引的方式搜索
  
  	3.依赖范围
		Compile   struts2 框架jar
		Provided   jsp-api.jar     重点
		Runtime   数据库驱动包
		Test   junit.jar 
  	
  	4. pom.xml内容
		<modelVersion>4.0.0</modelVersion>  //约束版本
		
		<groupId>wxt</groupId>		//坐标  GAV
		<artifactId>Web16_Maven</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<packaging>war</packaging>	//打包方式 jar、war、pom

		<dependencies>	//依赖jar包
			<dependency>
				<groupId>
				<artifactId>
				<version>
				<scope>
		 
		<build> 		// 里面放的是插件
			<plugins>
		 		<plugin>
		 		
	5.解决依赖传递导致的jar版本冲突
		1.第一声明优先原则：版本不同的jar，放在前面的jar生效
		2.路径近者优先原则：自己添加一个需要的jar
		3.排除原则：依赖中增加<exclusions>标签排除其中某个jar
				<dependency>
			  		<groupId>org.apache.struts</groupId>
			  		<artifactId>struts2-spring-plugin</artifactId>
			  		<version>2.3.24</version>
			  		<exclusions>
			  		  <exclusion>
			  		    <groupId>org.springframework</groupId>
			  		    <artifactId>spring-beans</artifactId>
			  		  </exclusion>
			  		</exclusions>
			  	</dependency>
		4.版本锁定原则：增加<dependencyManagement>标签锁定版本
				<properties>
					<spring.version>4.2.4.RELEASE</spring.version>
					<hibernate.version>5.0.7.Final</hibernate.version>
					<struts.version>2.3.24</struts.version>
				</properties>
			
				<!-- 锁定版本，struts2-2.3.24、spring4.2.4、hibernate5.0.7 -->
				<dependencyManagement>
					<dependencies>
						<dependency>
							<groupId>org.springframework</groupId>
							<artifactId>spring-context</artifactId>
							<version>${spring.version}</version>
						</dependency>
					</dependencies>
				</dependencyManagement>
 */

/*							第二天.分模块开发、私服
	1.分模块开发
		创建父工程			Maven project	package：pom
		创建dao子模块			Maven model		package：jar
		创建service子模块		Maven model		package：jar
		创建	web子模块		Maven model		package：war
		
		子模块继承的父模块jar包，并不是父模块所有的jar包，由父模块jar的依赖范围(scope)决定
		
		分模块开发细节：
			1.必须要有一个父工程，该父工程打成war包，并且该父工程只提供一个pom.xml文件，为其子工程提供jar包；
			2.子工程如entity，service，dao模块都打包成jar包，web模块需要打包成war包
			3.注意模块和模块之间的依赖关系，如service层必须依赖dao模块jar包，并且要在resource里配置dao和service两模块
			   的配置文件(applicationContext-serivce.xml、applicationContext-dao.xml).
		
	2.私服(nexus)
		1.安装、启动私服
			1.解压安装包到文件夹。其中sonatype-work文件夹就是私服的仓库
			2.打开cmd(进入到安装包文件夹的bin目录中)，执行： nexus.bat install, 即可完成安装
			3.在cmd中执行：nexus.bat start，则可启动私服nexus
			3.启动后则“服务”中nexus服务启动
			
		2.打开私服
			1.浏览器访问启动私服的服务器IP地址：xx.xx.xx/8081/nexus, 如本机为：localhost:8081/nexus
			2.登陆nexus。	用户名：admin	密码：admin123 
			
		3.私服仓库
			1.仓库类型：virtual 虚拟仓库
					   proxy   代理
					   hosted  宿主仓库/本地仓库
					   group
					   
		4.配置私服上传地址(见常用配置文件)
			1.项目pom.xml中，添加私服地址： <distributionManagement>
										  	<repository>
										  		<id>releases</id>
											<url>http://localhost:8081/nexus/content/repositories/releases/</url>
										  	</repository> 
										  	<snapshotRepository>
										  		<id>snapshots</id>
											<url>http://localhost:8081/nexus/content/repositories/snapshots/</url>
										  	</snapshotRepository> 
										  </distributionManagement>
										  
			2.maven软件setting.xml中，<servers>下添加私服账号密码：<server>
																  <id>releases</id>
																  <username>admin</username>
																  <password>admin123</password>
																</server>
																<server>
																  <id>snapshots</id>
																  <username>admin</username>
																  <password>admin123</password>
																</server>
			3.项目上传到私服,maven build... ——> deploy
		
		5.配置私服下载地址(见常用配置文件)
			1.maven软件setting.xml中，<profiles>下添加私服下载地址：
													<profile>   
														<!--profile的id-->
													    <id>dev</id>   
													    <repositories>   
													    ...
														<pluginRepositories>  
													    ...
													</profile>  

			2.激活<profile>，setting.xml中添加：<activeProfiles>
												  <activeProfile>dev</activeProfile>
											  </activeProfiles>
		6.项目修改后上传私服
			1.项目修改后需要修改版本号，再上传到私服，否则无法拿到该版本jar包
 */
public class Maven详解 {

}
