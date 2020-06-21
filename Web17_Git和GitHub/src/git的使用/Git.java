package git的使用;

/*						Git安装
 	1.下载、安装
 		下载安装包，点击安装。(--见/Web17_Git和GitHub/src/教案/学会Git玩转Github.doc)
 
 	2.安装后-基本信息设置
 		1.设置用户名
			git config --global user.name 'SOROoi'
		2.设置用户名邮箱
			git config --global user.email '545772909@qq.com'
		3.查看设置
			git config --list
			
 
 */

/*						Git本地仓库同步到远程库
 	本地有更新，上传到github仓库：
 	
 	0.将GitHub上的项目下载到本地
 		git clone https://github.com/用户名/仓库名.git

	1.(进入项目文件夹) 变成git可以管理的本地仓库								--变成git可以管理的本地仓库(目录中的文件需要commit后才真正存入本地仓库)
		git init	(每创建一个本地仓库，执行一次)
		
	2.把文件添加到暂存区中,不要忘记后面的小数点“.”，意为添加文件夹下的所有文件	--添加到暂存区
		git add .
		
	3.用命令 git commit -m添加到本地仓库，末尾提交说明					--把暂存区文件提交到本地仓库
		git commit -m '描述'
		
	 .查看目录中 是否还有文件 未添加到仓库					--查看本地仓库状态(未添加仓库的文件名 是红色--工作区/绿色--暂存区)
		git status
		
	4.连接 GitHub 远程库	(GitHub添加本地SSH密钥后	即可连接)
		git remote add origin git@github.com:用户名/远程库名.git		
			如：git remote add origin git@github.com:SOROoi/Demo.git
		
		(清除连接的远程库：git remote rm origin)
		(查看远程库详情：	git remote -v)
		
	5.同步到 GitHub 仓库，把当前分支master同步到远程仓库。第一次同步执行此命令。
		git push -u origin master
	   
	        后续同步：
	    git push origin master
		
	
						GitHub添加本地SSH密钥(SSH加密传输)
	1.创建SSH Key:
		1.在用户主目录(C:\Users\asus pc)下，看看有没有.ssh目录，
		
			a.如果有，再看看这个目录下有没有id_rsa和id_rsa.pub这两个文件。都有，则直接跳到下一步。
	
			b.如果没有，打开Shell（Windows下打开Git Bash），创建SSH Key：
			  ssh-keygen -t rsa -C "youremail@example.com"

			(把邮件地址换成自己的，然后一直按回车，使用默认值即可，由于这个Key也不是用于军事目的，所以也无需设置密码。
			如果一切顺利的话，可以在用户主目录里找到.ssh目录，里面有id_rsa和id_rsa.pub两个文件，这两个就是SSH Key的秘钥对。
			id_rsa	    是私钥，不能泄露出去，
			id_rsa.pub是公钥，可以放心地告诉任何人。)					
	
	2.添加SSH Key:
		1.登陆GitHub，打开“settings”，“SSH Keys”页面：
		2.点击“New SSH Key”，填上任意Title，在Key文本框  粘贴(C:\Users\asus pc\.ssh)下id_rsa.pub文件的内容：
	
  */


/*					下载项目、查看、撤销命令
 	
 	0.将GitHub上的项目下载到本地
 		git clone https://github.com/用户名/仓库名.git
 	
  	1.查看工作区、暂存区的文件：	红色/工作区文件	绿色/暂存区文件
  		git status	
  		
  	2.查看本地仓库的文件
  		git ls-files
  
  	3.查看提交记录
  		git log
  		
  	4.撤销暂存区文件：如果不指定文件名，则撤销add的所有文件
  		git reset HEAD 文件
  		
  	5.撤销工作区内容
		git checkout -- 文件
		
		(命令git checkout -- readme.txt意思就是，把readme.txt文件在工作区的修改全部撤销，这里有两种情况：
		一种是readme.txt自修改后还没有被放到暂存区，现在，撤销修改就回到和版本库一模一样的状态；
		一种是readme.txt已经添加到暂存区后，又作了修改，现在，撤销修改就回到添加到暂存区后的状态。
		总之，就是让这个文件回到最近一次git commit或git add时的状态。)
		
 */

public class Git {

}
