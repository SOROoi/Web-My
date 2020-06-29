package p1_oracle安装;

/*	day1			    	Oracle数据库的安装
 	1.安装在什么系统
 		1.Windows XP：	可直接安装。
 		2.Windows 7/8:	需以管理员身份运行setup，以兼容性模式启动

 	2.安装前准备：
 		1.安装包：如 Oracle 10G 安装包，可在D:/我的资料/软件安装包、百度云盘中找到。
 		2.系统：当前系统的是否为Windows，Windows下可点击安装包下setup进行安装；若为Linux则网上另找安装教程，略。
	
	3.安装流程：
		见‘ 安装.pdf ’有详细步骤
	
 */
 
  
 /* day1					连接Oracle数据库
 	1.如何连接数据库
 		Oracle相当于远程服务器，我们需要用客户端连接它
        
        服务器本地连接数据库(命令行)：sqlplus sys/sys as sysdba
 		
 	2.客户端（客户端文件，见本机D：/Oracle客户端）
 		1.命令行客户端--OracleInstanceClient(文件夹)
 			1.该客户端是以命令行的形式连接数据库的
 			2.使用方法：命令行进入该文件夹，并执行文件夹中的sqlplus命令,连接数据库。可将此目录路径添加至环境变量，则命令行中可直接输入命令。
 				      例：sqlplus scott/tiger@192.168.61.100:1521/orcl

		2.图形化客户端--PLSQL Developer(第三方开发,以OracleInstanceClient为基础)
			1.该客户端需要安装，不能安装在有空格和中文的路径下。
			2.需要配置环境变量，将服务器端oracle\product\10.2.0\db_2\NETWORK\ADMIN下tnsnames.ora复制到本机，并把该文件路径添加到系统
			变量TNS_ADMIN中。
			3.需要输入破解密码，见文件夹安装文档
			4.打开软件--Tools--Preferences,需要添加Oracle Home和OCI library路径，即命令行客户端的路径和命令行客户端中oci.dll路径
			5.使用方法：安装后双击运行图标
		
		3.图形化客户端--sqldeveloper(Oracle开发)
	
    3.oracle中的注释
        1.  --这是oracle中的单行注释
        2.  /*这是oracle中的多行注释* /
 */

 
 /* day2                    Oracle数据库、DQL、函数、条件表达式
    1.数据库结构
        1.数据库  ---> 数据库实例  --->  表空间(逻辑单位)(用户)  ---> 数据文件(物理单位)
        2.通常情况下,ORacle数据库只会有一个数据库实例ORCL
        
    2.新建一个项目:
        MySQL : 创建一个数据库,创建相应的表
        Oracle: 数据库实例，创建一个表空间,创建用户,用户去创建表
         
    3.Oracle和MYSQL的差别
        0. 都遵循SQL标准,都是关系型数据库
        1. Oracle是多用户的, MYSQL是多数据库的
        2. 不同厂商,不同的数据库产品,但是有自己的方言
        3. 使用自己的方言,也能够完成相同的功能
        4. Oracle安全级别要高,MYSQL开源免费
        5. Oracle默认不开启自动提交，MySQL默认开启自动提交
        6. Oracle中 sql语句中，表名需要大写
        
    4.SQL基本查询:
        4.1.SQL : 结构化查询语言
           
            SQL的分类以及每类常见的操作符：
                DDL : 数据定义语言 create drop alter truncate
                DML : 数据操纵语言 insert delete update
                DCL : 数据控制语言 安全 授权 grant revoke
                DQL : 数据查询语言 select from子句 where子句
                
            查询语句的结构:
                select [列名] [*] from 表名 [where 条件] [group by 分组条件] [having 过滤] [order by 排序]
            
        4.2 dual表：oracle中的虚表 ,伪表, 主要是用来补齐语法结构：
                select 1+1 from dual;
            
        4.3 as别名查询：使用as关键字,可以省略，别名中不能有特殊字符或者关键字, 如果有就加双引号
                select ename "姓     名", sal 工资 from emp;
        
        4.4 distinct去除重复数据
            
            4.5 单列去除重复
                select distinct job from emp;
                
            4.6 多列去除重复:     每一列都相同才能够算作是重复
                select distinct job,deptno from emp;
        
        4.7 查询中四则运算 (注意: null值, 代表不确定的、不可预知的内容, 不可以做四则运算)
            
            4.8 加法运算
                select 1+1 from dual;
                
            4.9 乘法运算
                select sal*12 from emp;
                
            4.10 加法和乘法
                select sal*12 + comm from emp;
                
            4.11 nvl 函数: 如果a参数为null  就返回b参数
                select sal*12 + nvl(comm,0) from emp;       --查询员工年薪+奖金
 
        4.12 字符串拼接:
            1.java:                          +	拼接
            2.Oracle特有的连接符:             ||	拼接
            3.Mysql、Oracle中共同拼接函数：  	concat(str1,str2) 
            (在Oracle 中 ,双引号主要是别名的时候使用, 单引号是使用的值, 是字符)
            
            4.13 使用||拼接符：
                select '姓名:' || ename from emp;
                
            4.14 使用函数拼接：
                select concat('姓名:',ename) from emp;
        
        4.15 条件查询：[where后面的写法]
            1.关系运算符:  > >= = < <=  != <>
            2.逻辑运算符:  and or not
            3.其它运算符:
                           like 模糊查询
                           between..and.. 在某个区间内
                           in() 在某个集合内
                           not in() 不在某个集合里
                           >any() 大于任何一个
                           >all() 大于所有
                           is null  判断为空
                           is not null 判断不为空
                           
            4.16 查询每月能得到奖金的员工信息：
                select * from emp where comm is not null;
                
            4.17 查询工资在1500--3000之间的员工信息：
                select * from emp where sal between 1500 and 3000;
                select * from emp where sal >= 1500 and sal <= 3000;
            
            4.18 查询名字在某个范围的员工信息('JONES','SCOTT','FORD')：
                select * from emp where ename in ('JONES','SCOTT','FORD');
                
        4.19 like查找字符：查找特殊字符, 需要使用escape转义
            _表示一个字符
            %表示多个字符
            
            4.20 查询员工姓名第三个字符是O的员工信息：
                select * from emp where ename like '__O%';
                
            4.21 查询员工姓名中,包含%的员工信息：
                select * from emp where ename like '%\%%' escape '\';
                select * from emp where ename like '%#%%' escape '#';
                
        4.22 order by排序：
            升序:         asc   (ascend)
            降序:         desc  (descend)
            对null排序:   nulls first;   nulls last;
            同时排列多列, 用逗号隔开
            
            4.23 查询员工信息,按照奖金由高到低排序
                select * from emp order by comm desc nulls last;
                
            4.24 查询部门编号和工资  按照部门升序排序, 工资降序排序
                select deptno,sal from emp order by deptno asc, sal desc;
                
        4.25 group by分组:
            格式: select 分组的条件,分组之后的操作 from 表名 group by 分组条件 having 过滤条件;
                                  (分组后操作只能为函数)
            
            4.26 分组统计所有部门的平均工资,找出平均工资大于2000的部门
                select deptno,avg(sal) from emp group by deptno having avg(sal) > 2000;
                
        4.99 select语句的编写顺序、执行顺序：
			1.编写顺序: 
				select.. from.. where.. group by.. having.. order by
						
			2.执行顺序:
				from.. where.. group by.. having.. select.. order by
                
            3.where和having的区别：
                having：可以接聚合函数，出现在分组之后
				where：不可接聚合函数，出现在分组之前	
                
    5. 函数
        函数有返回值
        单行函数: 对单行中的某个值进行处理
                     数值函数
                     字符函数
                     日期函数
                     转换函数
                     通用函数
        多行函数: 对某一列的所有值进行处理
                     max()  
                     min()  
                     count()  
                     sum()  
                     avg() 
        (多行函数的执行会忽略为空值的行)
           
        5.1 多行函数(运算):
                sum()：      -求和
                count()：    -求数量
                avg()：      -求平均值
                
                    5.1.2 统计员工的薪水总和:
                        select sum(sal) from emp;
                        
                    5.1.3 统计员工人数:
                        select count(1) from emp;
                        
                    5.1.4 统计员工的平均奖金：
                        select avg(comm) from emp;           --错误,统计忽略了没有奖金的员工
                        select sum(comm)/count(1) from emp;  --正确
                
        5.2 数值函数(运算)：
                ceil(i)：    -向上取整
                floor(i)：   -向下取整
                round(i,b)： -四舍五入,保留b位小数
                trunc(i,b)： -截断,保留b位小数
                mod(i,b):    -求i/b余数
                
                abs(i):		-求绝对值
                sqrt(i):	-求平方根
                sin(i):		-求三角函数，i为弧度
                
                    5.2.1 对i向上取整: 
                        select ceil(45.926) from dual;   --46
                            
                    5.2.2 对i向下取整:
                        select floor(45.926) from dual;  --45
                        
                    5.2.3 对i四舍五入,保留b位小数：  
                        select round(45.926,2) from dual; --45.93        
                        select round(45.926,1) from dual; -- 45.9
                        select round(45.926,0) from dual; --46
                        select round(45.926,-1) from dual; --50
                        select round(45.926,-2) from dual; --0
                        select round(65.926,-2) from dual; --100
                        
                    5.2.4 对i截断,保留b位小数：
                        select trunc(45.926,2) from dual; --45.92     
                        select trunc(45.926,1) from dual; -- 45.9
                        select trunc(45.926,0) from dual; --45
                        select trunc(45.926,-1) from dual; --40
                        select trunc(45.926,-2) from dual; --0
                        select trunc(65.926,-2) from dual; --0
                        
                    5.2.4 求i/b 的余数： 
                        select mod(9,3) from dual; --0        
                        select mod(9,4) from dual; --1
                
        5.3 字符函数(单行)
                substr(str,i,n):    --对str字符串进行截取,从索引i开始,截取n个字符).  
                						注意:起始索引不管写0、1都是从第一个字符开始截取                      
                							i若为-1，表示倒数第一个字符
                length(str)：       --获取str的长度
                trim(str)：         --去除str两端的空格
                ltrim(c1,c2)	--从c1中去除左第一个c2字符
                rtrim(c1,c2)
                replace(str,s1,s2)  --将str中的s1替换为s2
                
                upper(str)			--将str转为大写
                lower(str)			--将str转为小写
                initcap(str)		--将str首字母大写
                concat(str1,str2)	--将str1、str2拼接
                
                    5.3.1 截取'abcdefg'中3个字符：
                        select substr('abcdefg',0,3) from dual; --abc
                        select substr('abcdefg',1,3) from dual; --abc
                        select substr('abcdefg',2,3) from dual; --bcd
                        
                    5.3.2 获取'abcdefg'的长度:
                        select length('abcdefg') from dual;
                        
                    5.3.3 去除'  hello  '两端的空格:
                        select trim('  hello  ') from dual;
                    
                    5.3.4 将'hello'中的'l'替换为'a':
                        select replace('hello','l','a') from dual;
            
        5.4 日期函数(单行)   
                sysdate:                          --查询今天的日期  
                add_months(sysdate,3):            --查询三个月后今天的日期
                sysdate + 3：                 		    --查询3天后的日期
                months_between(sysdate,hiredate)  --查询两个日期之间的月份
                next_day(sysdate,'monday')		--下一个'str'(星期几)的日期
                last_day(sysdate)				--这个月最后一天的日期
                extract(year from sysdate)		--返回日期对应 年/月/日 (year month day)
            
                    5.4.1 查询今天的日期
                        select sysdate from dual;
                
                    5.4.2 查询3个月后的今天的日期
                        select add_months(sysdate,3) from dual;
                    
                    5.4.3 查询3天后的日期
                        select sysdate + 3 from dual;
                        
                    5.4.4 查询员工入职的天数
                        select sysdate - hiredate from  emp;
                        select ceil(sysdate - hiredate) from  emp;
                    
                    5.4.5 查询员工入职的周数
                        select (sysdate - hiredate)/7 from emp;
                    
                    5.4.6 查询员工入职的月数
                        select months_between(sysdate,hiredate) from emp;
                    
                    5.4.7 查询员工入职的年份数
                        select months_between(sysdate,hiredate)/12 from emp;
                 
            （   Java里面      yy表示年 MM表示月 dd表示日 HH表示24小时制   hh表示12小时制 mm表示分钟 ss表示秒 YYYY表示当天所在的周属于的年份(一周从周日开始，周六结束，只要本周跨年，那么这周就算入下一年)
                 Oracle里面    yy表示年 mm表示月 dd表示日 hh24表示24小时制 hh表示12小时制 mi表示分钟 ss表示秒
                 
                 mm与m同等，它们的区别为是否有前导零：H,m,s表示非零开始，HH,mm,ss表示从零开始。
                 比如凌晨1点2分，HH:mm显示为01:02，H:m显示为1:2    ）
            
        5.5 转换函数  
                to_number(str,'$9999'):                  --字符转数字     
                to_date('2017-04-10','yyyy-mm-dd')       --字符转日期
                to_char(i,'$99999'):                     --数字转字符      
                to_char(sysdate,'yyyy-mm-dd hh:mi:ss')   --日期转字符
                
                    5.5.1 字符转数字：
                        select 100+'10' from dual;                 		--110  默认转换
                        select 100 + to_number('10') from dual;    		--110
                        select to_number('$1000','$9999') from dual;	--1000	去除其他符号转为数字
                    
                    5.5.2 字符转日期
                        select to_date('2017-04-10','yyyy-mm-dd') from dual;
                        
                    5.5.3 查询1981年到1985年入职的员工信息
                        select * from emp where hiredate between to_date('1981','yyyy') and to_date('1985','yyyy');   
                        
                    5.5.4 数字转字符：	(9表示数字，0表示用0补齐，$美元符号，S加正负号)
                        select to_char(sal,'$9,999.99') from emp;
                        select to_char(sal,'L9,999.99') from emp;  --转换为本地货币符号
                                                                    //to_char(1210.73, '9999.9')    返回 '1210.7' 
                                                                    //to_char(1210.73, '9,999.99')  返回 '1,210.73' 
                                                                    //to_char(1210.73, '$9,999.99') 返回 '$1,210.73' 
                                                                    //to_char(21, '000099')         返回 '000021' 
                                                                    //to_char(852,'xxxx')           返回16进制'354'
                    
                    5.5.5 日期转字符：
                        select to_char(sysdate,'yyyy-mm-dd hh:mi:ss') from dual;
                        select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual;  --24小时制
                        
                        5.5.5.1 只想要年
                            select to_char(sysdate,'yyyy') from dual;  --2017
                        
                        5.5.5.2 只想要日
                            select to_char(sysdate,'d') from dual;   --2       代表一个星期中第几天
                            select to_char(sysdate,'dd') from dual;  --10      代表一个月中的第几天
                            select to_char(sysdate,'ddd') from dual; --100     代表一年中的第几天
                            select to_char(sysdate,'day') from dual;  --monday  代表星期几
                            select to_char(sysdate,'dy') from dual;   --mon     代表星期几,简写
                    
        5.6 通用函数:
               nvl(参数1,参数2)：              --如果 参数1 = null, 就返回参数2, 否则返回参数1
               nvl2(参数1,参数2,参数3)：       --如果 参数1 = null, 就返回参数3, 否则返回参数2
               nullif(参数1,参数2)：           --如果 参数1 = 参数2, 就返回null, 否则返回参数1
               coalesce(参数1，参数2):         --返回第一个不为null的值    

                    5.6.1 例：
                        select nvl2(null,5,6) from dual;    --6;
                        select nvl2(1,5,6) from dual;       --5;

                        select nullif(5,6) from dual;       --5
                        select nullif(6,6) from dual;       --null

                        select coalesce(null,null,3,5,6) from dual;  --3
        
    6.条件表达式：将属性中的值替换为自定义值
            1.通用写法: (MySQL、Oracle中皆可使用)                  
                        case 属性
                            when 值1 then 值
                            when 值2 then 值
                            else
                                默认值
                            end
            2.Oracle特有写法:
                        decode(属性,if1,then1,if2,then2,else1)
                
                6.1 给表中姓名取一个中文名：
                        select 
                            case ename
                                when  'SMITH' then '刘备'
                                when  'ALLEN' then '诸葛亮'
                                else
                                    '路人甲'
                                end
                        from emp;
                        
                        select decode(ename,'SMITH','刘备','ALLEN','诸葛亮','路人乙') from emp;
 */         

 
 /* day2                    Oracle多表查询、子查询、rownum行号
 
    1.多表查询(即笛卡儿积)：
        1.即查询多张表，并将多张表的所有记录每每合并在一起，产生的总记录数等于多张表的记录数的乘积。
        2.笛卡尔积: 实际上是两张表的乘积,但是在实际开发中没有太大意义
        
        格式: select * from 表1,表2 
 
    2.多表查询内外连接
        1.实际就是过滤，从多表查询产生的记录中筛选出需要的记录，并显示
        
        2.1 内联接:    查询结果仅包含符合查询条件的记录
        
                   隐式内联接: 
                       等值内联接:    where e1.deptno = d1.deptno;
                       不等值内联接:  where e1.deptno <> d1.deptno;
                       自联接:        自己连接自己
                   显示内联接:
                       select * from 表1 inner join 表2 on 连接条件
                       inner 关键字可以省略
                       
                    2.1.1 查询员工编号,员工姓名,经理的编号,经理的姓名
                        select e1.empno,e1.ename,e1.mgr,m1.ename            --显示表x的哪个属性
                        from emp e1, emp m1                                 --查询哪些表
                        where e1.mgr= m1.empno;                             --从记录中过滤出需要的表
                        
                    2.1.2 查询员工编号,员工姓名,员工的部门名称,经理的编号,经理的姓名
                        select e1.empno,e1.ename,d1.dname,e1.mgr,m1.ename
                        from emp e1, emp m1,dept d1 
                        where e1.mgr= m1.empno and e1.deptno = d1.deptno;
                        
                    2.1.3 查询员工编号,员工姓名,员工的部门名称,经理的编号,经理的姓名,经理的部门名称
                        select e1.empno,e1.ename,d1.dname,e1.mgr,m1.ename,d2.dname
                        from emp e1, emp m1,dept d1,dept d2 
                        where 
                             e1.mgr= m1.empno 
                         and e1.deptno = d1.deptno
                         and m1.deptno = d2.deptno;
                    
                    2.1.4 查询员工编号,员工姓名,员工的部门名称,员工的工资等级,经理的编号,经理的姓名,经理的部门名称
                        select e1.empno,e1.ename,d1.dname,s1.grade,e1.mgr,m1.ename,d2.dname
                        from emp e1, emp m1,dept d1,dept d2,salgrade s1 
                        where 
                             e1.mgr= m1.empno 
                         and e1.deptno = d1.deptno
                         and m1.deptno = d2.deptno
                         and e1.sal between s1.losal and s1.hisal;
                    
                    2.1.5 查询员工编号,员工姓名,员工的部门名称,员工的工资等级,经理的编号,经理的姓名,经理的部门名称,经理的工资等级
                        select e1.empno,e1.ename,d1.dname,s1.grade,e1.mgr,m1.ename,d2.dname,s2.grade
                        from emp e1, emp m1,dept d1,dept d2,salgrade s1,salgrade s2 
                        where 
                             e1.mgr= m1.empno 
                         and e1.deptno = d1.deptno
                         and m1.deptno = d2.deptno
                         and e1.sal between s1.losal and s1.hisal 
                         and m1.sal between s2.losal and s2.hisal;
                    
                    2.1.6 查询员工编号,员工姓名,员工的部门名称,员工的工资等级,经理的编号,经理的姓名,经理的部门名称,经理的工资等级
                            --将工资等级 1,2,3,4 显示成 中文的 一级 二级 三级...
                        select e1.empno,
                               e1.ename,
                               d1.dname,
                               case s1.grade
                                 when 1 then '一级'
                                 when 2 then '二级'
                                 when 3 then '三级'
                                 when 4 then '四级'
                                 else
                                     '五级'
                                 end "等级",
                               e1.mgr,
                               m1.ename,
                               d2.dname,
                               decode(s2.grade,1,'一级',2,'二级',3,'三级',4,'四级','五级') "等级"
                        from emp e1, emp m1,dept d1,dept d2,salgrade s1,salgrade s2 
                        where 
                             e1.mgr= m1.empno 
                         and e1.deptno = d1.deptno
                         and m1.deptno = d2.deptno
                         and e1.sal between s1.losal and s1.hisal 
                         and m1.sal between s2.losal and s2.hisal;
                    
                    2.1.7 查询员工姓名和员工部门所处的位置
                        select e1.ename,d1.loc 
                        from emp e1,dept d1 
                        where e1.deptno = d1.deptno;
                        
                        显示内连接
                        select * from emp e1 inner join dept d1 on e1.deptno = d1.deptno;
                    
        2.2 外联接：    显示 左/右 表所有记录，查询结果包含符合查询条件的记录，同时也包含不符合条件的记录。
                        分为：左/右外连接。
        
                    (标准,通用写法)
                    左外连接: select * from 表1 left outer join 表2 on 连接条件 				--outer 可省略  
                              --显示左表中所有的记录,若右表没有对应记录,右表显示空
                              
                    右外连接: select * from 表1 right outer join 表2 on 连接条件 			--outer 可省略  
                              --显示右表中的所有记录,若左表没有对应记录,左表显示空
                       
                             
                   
                    Oracle中的外连接: (+) 该表如果没有对应的记录就显示空值
                      select * from emp e1,dept d1 where e1.deptno = d1.deptno(+);  --左外连接
                    
                    2.2.1 例：
                        select * from emp e1 left outer join dept d1 on e1.deptno = d1.deptno;
                        insert into emp(empno,ename) values(9527,'HUAAN');
                        select * from emp e1,dept d1 where e1.deptno = d1.deptno(+);

                        select * from emp e1 right outer join dept d1 on e1.deptno = d1.deptno;
                        select * from emp e1,dept d1 where e1.deptno(+) = d1.deptno;
                    
    3.子查询
         查询语句中嵌套查询语句; 用来解决复杂的查询语句
                
                3.1 单行子查询:
                      单行子查询比较符: > >= = < <= <> !=       --使用单行子查询,代表子查询结果只有一行数据
                
                    3.1.1 查询最高工资的员工信息：
                            1.查询出最高工资：
                              select max(sal) from emp;
                            2.工资等于最高工资：
                              select * from emp where sal = (select max(sal) from emp);             --!重要:将子查询结果当作条件,再次查询
                        
                    3.1.2 查询出比雇员7654的工资高,同时和7788从事相同工作的员工信息:
                            1.雇员7654的工资:
                              select sal from emp where empno = 7654;
                            2.7788从事的工作:
                              select job from emp where empno = 7788;
                            3.两个条件合并:
                              select * from emp where sal > 1250 and job = 'ANALYST';

                              select * from emp where sal > (select sal from emp where empno = 7654) and job = (select job from emp where empno = 7788);
                        
                    3.1.3 查询每个部门最低工资的员工信息和他所在的部门信息:   
                            1.查询每个部门的最低工资,分组统计
                              select deptno,min(sal) minsal from emp group by deptno;
                            2.员工工资等于他所处部门的最低工资
                              select * 
                              from emp e1,
                                   (select deptno,min(sal) minsal from emp group by deptno) t1      --!重要:将子查询结果当作表,再次查询
                              where e1.deptno = t1.deptno and e1.sal = t1.minsal; 
                            3.查询部门相关信息
                              select * 
                              from emp e1,
                                   (select deptno,min(sal) minsal from emp group by deptno) t1,
                                   dept d1 
                              where e1.deptno = t1.deptno and e1.sal = t1.minsal and e1.deptno = d1.deptno;    
                
                3.2 多行子查询
                        多行数据比较符:   			  in()             --在其中都匹配               ！使用多行子查询,代表子查询结果存有多行数据
                                          not in()         --不在其中的都匹配
                                          >any()           --只要大于其中一个的就匹配
                                          >all()           --大于其中所有的才匹配
                                          exists()         --子查询结果存在,则执行where前语句
                                                              exists修饰的部分，整体当作布尔值来处理:
                                                              当查询语句有结果的时候, 就是返回true
                                                                                      否则返回的是false
                                                              数据量比较大的时候是非常高效的   
                                          not exists()     --子查询结果不存在,则执行where前语句
                                          
                                          通常情况下, 数据库中不要出现null  最好的做法是创建表时，加上Not null约束
                                          null值并不代表不占空间, char(100) null 100个字符
                
                    3.2.1 (in)查询领导信息:
                            1.查询所有经理的编号
                              select distinct mgr from emp;         --distinct去除重复数据
                            2.结果
                              select * from emp where empno in (select mgr from emp);
                        
                    3.2.2 (not in)查询不是领导的信息:
                            --错误写法
                              select * from emp where empno not in (select mgr from emp);           --子查询结果中有空值，父查询会发生错误
                              select * from emp where empno <>all(select mgr from emp);
                            --正确写法
                              select * from emp where empno not in (select mgr from emp where mgr is not null);
                        
                    3.2.3 (>any)查询出比10号部门其中一个员工薪资高的员工信息:
                          select * from emp where sal >any (select sal from emp where deptno = 10);
                        
                    3.2.4 (>all)查询出比20号部门所有员工薪资高的员工信息:
                            1.20号最高工资 5000
                              select max(sal) from emp where deptno =20;
                            2.员工信息
                              select * from emp where sal > (select max(sal) from emp where deptno =20); 
                              select * from emp where sal >all (select sal from emp where deptno = 20);   
                    
                    3.2.5 (exists)查询有员工的部门的信息
                            select * from dept d1 where exists(select * from emp e1 where e1.deptno = d1.deptno);
                    
    4. rownum 行号
            rownum : 伪列, 系统自动生成的一列,每查询出记录之后，就会添加一个行号
            rownum是Oracle中特有的用来表示行号的, 默认值/起始值是 1 ,在每查询出结果之后,再添加1
            rownum不能做大于2判断,可以做小于号判断
            
            格式：
            select rownum,emp.* from emp where rownum < 6;
            
            SQL执行顺序:
            from .. where ..group by..having .. select..rownum..order by
            
                因执行顺序为select...order by ;所以select中加rownum,接order by 会先加行号再排序，产生乱序.  
                如:select rownum,emp.* from emp order by sal;
                
                4.1 找到员工表中工资最高的前三名
                    select rownum, t1.* from (select e1.* from emp e1 order by sal desc) t1 where rownum < 4;
                    
                4.2 找到员工表中薪水大于本部门平均薪水的员工
                    select * from emp e1,(select deptno,avg(sal) avgsal from emp group by deptno) s1 
                    where e1.deptno = s1.deptno and e1.sal > s1.avgsal;
                    
                    关联子查询方式(:子查询语句无法单独执行) (前面都是非关联子查询)
                    select * from emp e where sal > (select avg(sal) from emp e2 group by deptno having e.deptno=e2.deptno);
                
                4.3 统计每年入职的员工个数
                    select emp.*,to_char(hiredate,'yyyy') dt from emp;
                    select t1.dt,count(*) from (select emp.*,to_char(hiredate,'yyyy') dt from emp) t1 group by t1.dt;
                
                4.4 统计每年入职的员工个数
                    1.先查询每年对应的入职员工个数
                        select to_char(hiredate,'yyyy') dt,count(*) n from emp group by to_char(hiredate,'yyyy');

                    2.将表中数据排列为需要的格式
                        select sum(t1.n) Total,
                        sum(case t1.dt
                        when '1987' then t1.n
                        end) "1987",
                        sum(case t1.dt
                        when '1980' then t1.n
                        end) "1980",
                        sum(case t1.dt
                        when '1982' then t1.n
                        end) "1982",sum(case t1.dt
                        when '1981' then t1.n
                        end) "1981"
                        from (select to_char(hiredate,'yyyy') dt,count(*) n from emp group by to_char(hiredate,'yyyy')) t1;
                        
    5. rowid
            伪列，每行记录所存放的真实物理地址
            格式：select rowid,e.* from emp e;
                
                5.1 去除表中重复记录(删除表中记录，只保留rowid最小的那行记录)
                    create table p(
                           name varchar2(10)
                    );

                    insert into p values('黄伟福');
                    insert into p values('黄伟福');
                    insert into p values('黄伟福');
                    insert into p values('赵洪');
                    insert into p values('赵洪');
                    insert into p values('杨华');
                    insert into p values('杨华');
                    insert into p values('杨华');
                    
                    select rowid,p.* from p;
                    delete from p p1 where rowid > (select min(rowid) from p p2 where p1.name = p2.name);
                    
    6. 分页查询
            在oracle中只能使用子查询来做分页查询  

                6.1 查询第6-10记录
                    1.加行号查询表emp
                        select rownum hanghao, emp.* from emp;
                    2.将加了行号的表作为被查询表，查询6-10行
                        select * from (select rownum hanghao, emp.* from emp) tt where tt.hanghao between 6 and 10;
                    
    7.集合运算 
            并集: 两个查询结果进行合并           
                  union:            去除重复的,并且排序
                  union all:        不会去除重复的
                  
            交集：两个结果共有的部分
                  intersect:        第一个结果和第二个结果共有的部分
            
            差集：两个结果相减
                  minus:            第一个结果减去第二个结果
       
            为什么使用集合运算符？  答：所有的查询结果可能不是来自同一张表, 有时需要多表查询 
         
                7.1 (union)工资大于1500,或者20号部门下的员工
                      select * from emp where sal > 1500
                      union
                      select * from emp where deptno = 20;
                
                7.2 (intersect)工资大于1500,并且20号部门下的员工
                      select * from emp where sal > 1500
                      intersect
                      select * from emp where deptno = 20;
                
                7.3 (minus)1981年入职员工(不包括总裁和经理)
                      select * from emp where to_char(hiredate,'yyyy')='1981'
                      minus
                      select * from emp where job = 'PRESIDENT' or job = 'MANAGER';
                      
                      或(select * from emp where to_char(hiredate,'yyyy') = 1981 and job not in('MANAGER','PRESIDENT');)
           
            集合运算中的规则:
             1.列的类型要一致
             2.按照顺序写
             3.列的数量要一致,如果不足,用空值填充
                
                7.4 类型一致，按照顺序写
                      select ename,sal from emp where sal > 1500
                      union
                      select ename,sal from emp where deptno = 20;
                    
                7.5 列的数量要一致; 如果不足,用空值或同类型值填充
                      select ename,sal,deptno from emp where sal > 1500
                      union
                      select ename,sal,null   from emp where deptno = 20;
                    或
                      select ename,sal,deptno from emp where sal > 1500
                      union
                      select ename,sal,66 from emp where deptno = 20;

 */
 
 
 /* day3                    Oracle DDL、DML、事务、视图view、序列sequence、索引index

 
    0.启动Oracle数据库服务器：XP虚拟机中 ——> 打开服务选项 ——> 启动OracleOraDb10g_home1TNSListener服务 和 OracleServiceORCL服务。
        
        用户：system  密码：root
        用户：wxt     密码：wxt
      
      查看当前用户的表空间:  select username,default_tablespace from user_users;
        
    
    1.Oracle体系结构:
        数据库 ---> 一个数据库实例ORCL ---> 表空间 (用户创建的表) ---> 数据文件 
        地球   ---> 中国               ---> 省份  (人民)          ---> 土地山川河流
 
    2.表空间: 通常我们新建一个项目,就会去新建表空间,在表空间中创建用户来创建表
            
        2.1 创建表空间：1.切换到system帐号下创建
                        2.创建一个表空间 --- 汉东
                  语法:
                          create tablespace 表空间的名称           -- create tablespace handong
                          datafile '文件的路径(服务器上)'          -- datafile 'E:\handong.dbf'
                          size 大小                                -- size 100m
                          autoextend on  (自动扩展)                -- autoextend on 
                          next 每次扩展的大小                      -- next 10m;
    
        2.2.创建用户 
                  语法：  create user 用户名                       -- create user dakang
                          identified by 密码                       -- identified by dakang
                          default tablespace 表空间的名称          -- default tablespace handong;
                          
        2.3 给用户授权  
                  语法：  grant 角色 | 权限  to 用户             
                    -- grant connect to dakang;  //授予 connect角色，登陆权限
                    -- grant dba to dakang;      //授予 dba角色，所有权限
                    -- grant resource to B;      //授予 resource角色，一般为开发人员权限
                    -- grant create view to B;   //授予 创建视图权限 
                    -- grant select any table to B;   //授予 查询任一表权限 
                                                                                                
        2.4 删除表空间
                  语法:   drop tablespace handong;
                  
    3. DDL操作表
        
        3.1 创建表: 1.切换到对应用户
                    2.创建表
                    
                  语法：  create table 表名( 
                             列名  列的类型 [列的约束],
                             列名  列的类型  [列的约束]      
                           );
              
              CREATE TABLE product(
				id varchar2(32) default SYS_GUID() PRIMARY KEY,		
				productNum VARCHAR2(50) NOT NULL,
				DepartureTime timestamp,
				productPrice Number,
				productStatus INT,
				CONSTRAINT product UNIQUE (id, productNum)
			  );
			  		--SYS_GUID(): 生成32位的唯一编码，生成唯一主键。插入时无需id列，类型为varchar2(32).
                  
                  数据类型:
                          varchar               在Oracle中,目前是支持的, 但是不保证以后还支持
                          varchar2(长度)        可变字符长度         varchar2(10)  hello  占5个字符
                          char(长度)				固定长度字符		char(10)      hello  占10个字符,用空格填充
                          
                          number(总长,小数长)		数字类型             	小数长度不能大于等于总长度
                          int                   整数                  1，2，3..
                          int(M)				M为显示宽度
                          
                          date                  日期         	yyyy-mm-dd
                          timestamp             时间                 yyyy-mm-dd hh24:mi:ss 	 
                          
                          LONG/CLOB             char lager object    存放一本小说
                          BLOB                  binary lager object  java 存进去,  再读取出来
                          
                        oracle本来就没有int类型，为了与别的数据库兼容，新增了int类型作为number类型的子集。
                            int类型只能存储整数;
                            number可以存储浮点数，也可以存储整数；
                            number(8,1)存储小数位为1位，总长度为8的浮点数，如果小数位数不足，则用0补全；
                            number(8)存储总长度为8的整数；
                            int相当于number(22),存储总长度为22的整数。
                          
                  表的五大约束
                           列的约束: 约束主要是用来约束表中数据的规则
                          1. 主键约束: primary key 不能为空, 必须唯一
                          2. 非空约束：not null
                           
                          3. 唯一约束：unique
                                    1.写法一：(表级约束)
							            : 列1 数据类型,
							            : constraint 约束名 unique(列1),
                                            
			                            name varchar2(20),
			                            constraint un_name unique(name),
                                    
	                                   . 问题：Oracle中唯一性约束：CONSTRAINT product UNIQUE (id, productNum) 的含义？
	                                   . 含义：此唯一约束表示，id 和 productNum ，不能都相同。
	                                            (唯一性约束指表中一个字段或者多个字段联合起来能够唯一标识一条记录的约束。联合字段中，可以包含空值。)
                                    
                                    
                          4. 检查约束：check(条件)  在mysql中是可以写的,但是mysql直接忽略了检查约束
                                     -- check( gender in ('男','女','人妖'))
                                     
                                     
                          5. 外键约束:主要是用来约束从表A中的记录,必须是存在于主表B中
                           
                                   1. 写法一：(列级约束)
										：外键列1 数据类型 references 主表(主表列),
			                                            
										depart_no number references depart(no),
                                            
                                   2. 写法二：(表级约束)
				 						：外键列1 数据类型,
										：constraint 约束名 foreign key(外键列1) references 主表(主表列) [on delete cascade],
			                                            
										depart_no varchar(10),
										constraint fk_deptno foreign key (depart_no) references depart(no),
                                           
                                   3. 注意：1.主表列 必须为主表的主键。
                                          2.外键列 数据类型必须和主表主键一致。
                                          3.外键列字段的值必须来源于主表中相应字段的值，或者为null
                                          4.先创建主表，再创建从表
                                          5.[on delete cascade]:级联删除，主表中对应字段的记录被删除时，同时删除从表中对应的记录。
          
                3.1.2 使用子查询方式-创建表:
			                          语法:   create table 表名 as 查询语句; 
			                          注意:   只会复制表结构和表中的数据,不会复制列的约束     
			                                  如果查询语句有结果, 就是复制 表结构和数据
			                                  如果查询语句没有结果, 就是复制 表结构              
                                
        3.2 修改表的列:
                   3.2.1 添加列:
                            alter table 表 add 列1 varchar2(11);
                            alter table 表 add (     mobile varchar2(11),
                                                      sex    varchar2(2)
                                                 );
                            
                   3.2.2 添加外键约束：
                            alter table 表 add foreign key(本表列1) references 他表(他表列1);  --本表列1的值必须来自其他表列1的值
                            (--alter table product add foreign key(cno) references category(cid);)
                   
                   3.2.2 修改列名
                            alter table 表 rename column 列1 to 列2;
                            
                   3.2.3 修改列的类型
                            alter table 表 modify 列1 varchar2(4);
                            
                   3.2.4 删除列
                            alter table 表 drop column 列1;
                            
        3.3 删除表:
                    truncate table 表1;			    --先删除"表1"，再创建"表1"，清除数据
        
                    drop table 表;                             --删除表 (表中记录被外键关联无法删除)
                    
                    drop table 表 cascade constraint;          --强制删除表(不建议使用): 先删除外键关联表的外键约束,然后再删除自己
                    (drop table category cascade constraint;)  (--先删除product的外键约束,再删除category )
                    
                    级联删除
                        1.表product添加外键约束,使用级联约束  ,在删除category时,使用级联删除
                            alter table 表product add foreign key(列cno) references 表category(列cid) on delete cascade;

                            insert into category values(2,'电脑办公');
                            insert into product values(11,'外星人',2);

                        2.级联删除 : 首先去从表中找有没有 关联数据, 如果在从表中找到关联数据,先删除从表中关联数据,然后再删除表中的数据
                            delete from category where cid = 2;

        3.4 重命名表 
                    rename 表 to 表x;
                    
        3.5 查看表
                    select * from tabs;		        --查看当前用户下所有表		
                    
                    select * from user_tab_columns where table_name='大写表名';       --查看表的结构 
                    
					表：	DBA_TABLES >= ALL_TABLES >= USER_TABLES (tabs = user_tables)
						
						DBA_TABLES 意为DBA拥有的或可以访问的所有的关系表。
						ALL_TABLES 意为用户拥有的或可以访问的所有的关系表。
						USER_TABLES 意为用户所拥有的所有的关系表。
                    
    4. DML操作表中数据
        
        4.1 插入数据:
                4.11 直接插入数据
                    insert into 表名(列1,列2) values(值1,值2);
                    insert into 表名 values(值1，值2，值3);
                    insert into 表名 values(值1，值2，值3),
                                           (值1，值2，值3);

                4.12 子查询插入数据
                    insert into 表名 查询语句
                    
                        4.12.1 将emp中10号部门的员工信息,插入到emp1中
                            insert into emp1 select * from emp where deptno = 10;   

        4.2 删除数据:
               delete from 表名  [where 条件]
                        
                        4.3.1 删除一条记录
                            delete from emp1 where empno=7839;
              
              ----------delete和truncate 区别---------
                delete:                 truncate:
                 DML                     DDL
		                 逐条删除                先删除表再创建表
		                 支持事务操作            不支持事务操作,
		                                         行效率要高  
        
        4.3 更新数据:
                update 表名 set 列名 = 列的值  [where 条件]
                
                        4.2.1 更新一条记录
                            update emp1 set ename='HUAAN' where ename = 'KING';
                                                     
    5. 事务
            事务：就是一系列的操作,要么都成功,要么都失败
            
            四大特性: 原子性,隔离性,持久性,一致性
              
            不考虑隔离级别: 脏读,不可重复读,虚读
            MYSQL隔离级别:  READ UNCOMMITTED , READ COMMITTED, REPEATABLE READ, SERIALIZABLE
            ORACLE隔离级别:                    READ COMMITTED,                  SERIALIZABLE, READ ONLY 
            (默认隔离级别: READ COMMITTED)
            
            Oracle默认不开启自动提交，
            MySQL 默认开启自动提交。
                            
            1.事务的保存点/回滚点:    savepoint 回滚点;
            2.回滚:                 rollback to 回滚点;	
            						rollback;
            3.提交 :                 commit;
            
            5.1 开启事务插入数据
                create table louti(
                   lou number primary key    
                );
                ------------------------------
                declare

                begin
                  insert into louti values(1);
                  insert into louti values(2);
                  insert into louti values(3);
                  insert into louti values(4);
                  insert into louti values(5);
                  savepoint dangban;
                  insert into louti values(6);
                  commit;
                exception  --捕获异常
                  when others then
                     rollback to dangban;
                     commit;
                end;
                ------------------------------
                select * from louti;
                
                
    6. 视图view:
            视图：1.是对查询结果的一个封装
                  2.能够封装复杂的查询结果，屏蔽表中的细节
                  3.视图数据,都是来自于它查询的那张表，原表数据改变视图也会变化
                   (视图本身不存储任何数据)
                  
            语法: create [or replace] view 视图的名称 as 查询语句 [ with read only]  
            
                  (or replace:若数据库中已经存在这个名字的视图，就替代它)
                  (with read only:只可读取，不可更改)
                  
            注意: 通常不要通过视图去修改,视图创建的时候,通常要加上with read only
            
            6.1.1 创建一个视图
                    create or replace view view_test1 as select ename,job,mgr from emp;
            
               .2 创建一个只读视图
                    create or replace view view_test2 as select ename,job,mgr from emp with read only;
                
               .3 创建视图封装复杂的查询语句
                    create view view_test3 as select
                          sum(cc) "TOTAL",
                          sum(case yy when '1980' then cc end) "1980",
                          sum(case yy when '1981' then cc end) "1981",
                          sum(case yy when '1982' then cc end) "1982",
                          sum(case yy when '1987' then cc end) "1987"
                    from
                          (select  to_char(hiredate,'yyyy') yy,count(1) cc from emp group by  to_char(hiredate,'yyyy')) tt;
                      
            6.2 通过视图修改数据
                update view_test1 set ename='SMITH2' where ename = 'SMITH';
                
            6.3 查看视图
                select * from view_test1;
                
            6.4 同义词的概念(可以对数据库中对象-table-view，定义同义词名)
                create synonym dept for view_test1;

                create synonym yuangong for view_test2;

                select * from yuangong;

                select * from dept;
                
            6.5 删除视图
                drop view view_test1;
                
    7.序列sequence:
           1. 序列:生成类似于 auto_increment 这种ID自动增长 1,2,3,4,5....的数据
                  (auto_increment, mysql中的)

           2. 语法:
                   create sequence 序列名
                   start with 几开始
                   increment by 每次增长多少
                   maxvalue 最大值 | nomaxvalue
                   minvalue 最小值 | nominvalue
                   cycle | nocycle  是否循环    1,2,3,1,2,3
                   cache 缓存的数量3 | nocache  1,2,3,4,5,6 
               
           3. 从序列获取值
                   currval : 当前值		--currval 需要在调用nextval之后才能使用   
                   nextval : 下一个值
              								
           4. 注意:  
                   1.永不回头,往下取数据, 无论发生异常/回滚。
                   2.在PLSQL中只能通过 select s.nextval into xx from dual; 获取或赋值。
                   
                        
            7.1 创建一个 1,3,5,7,9......30 的序列:
                    create sequence seq_test1
                    start with 1                    --从1开始
                    increment by 2                  --每次增长2
                    maxvalue 30                     --最大值到30
                    cycle                           --循环
                    cache 10;                       --不缓存
                    
            7.2 序列用的最多的写法:
                    create sequence seq_test2;      --默认从1开始、每次增长1、无最大值、不循环、不缓存
                    
            7.3 获取序列值
                    select seq_test1.nextval from dual;
                    select seq_test1.currval from dual;
                    
            7.4 删除序列
                    drop sequence seq_test1;
                    
    8. 索引index
           1. 索引:相当于是表的目录,能够提高我们的查询效率
                1. 如果某一列,你经常用来作为查询条件,那么就有必要创建索引,数据量比较大的情况

           2. 语法: create index 索引的名称 on 表名(列,列);   

           3. 注意: 主键约束自带主键索引, 唯一约束自带唯一索引

           4. 索引原理: btree   --balance Tree(平衡二叉树)  --记录对应rowid
                1. 如果某列作为查询条件的时候,可以提高查询效率,但是修改的时候,会变慢
                2. 索引创建好之后,过一段时间,DBA都需要去做重构索引
                 
           5. SQL调优:
                1. 调优原则：
                	1.查看执行计划：	(PL/SQL中按F5)
                		Cost：CPU调用次数		Cardinality:影响行数
               		2.分析里面的 Cost 和 影响行数, 想办法降低 
               		
               	2. 方案：创建索引
                 
                8.1 五百万数据测试
                
                    0.创建表
                        create table wubaiwan(
                              name varchar2(30),
                              address varchar2(20) 
                        );

                        insert into wubaiwan values('')

                    1.插入500000万条数据
                        declare

                        begin
                             for i in 1..5000000 loop
                               insert into wubaiwan values('姓名'||i,'地址'||i);
                             end loop;
                             commit;  
                        end;

                    2.在没有添加索引的情况下,去查询  name='姓名3000000'   --2.985s
                        select * from wubaiwan where name='姓名3000000';

                    3.创建索引 name 再去查询 name='姓名3000000'
                        create index ind_wubaiwan on wubaiwan(name);
                        select * from wubaiwan where name='姓名3000000';  --0.016s

                    4.在没有添加复合索引的情况下,再去查询 name='姓名3000000' and '地址3000000'
                        select * from wubaiwan where name='姓名3000000' and address='地址3000000'; --0.032s

                    5.创建复合索引的情况下, 再去查询
                        create index ind_wubaiwan2 on wubaiwan(name,address);
                        select * from wubaiwan where name='姓名3000000' and address='地址3000000'; --0.015s
                        
                8.2 删除索引
                        drop index ind_wubaiwan;
                        
    9.
 */ 
 
 
 /* day3                    PLSQL编程(Oracle特有)
 
    1.PLSQL编程 
            1.PLSQL: procedure Language 过程语言 Oracle对SQL的一个扩展
                   
                   1.让我们能够像在java中一样写 if else else if 条件, 还可以编写循环逻辑 for while
                   2.PLSQL中可以直接执行 update/insert/delete 语句，
                   3.不能直接 select语句显示查询结果(会报错)，需通过 select查询出结果后 into赋值。
             
            2.语法:  
                            declare                                     --声明变量
                                age number := &aaa;      			--&用来定义临时变量，每当&aaa出现，都会要求您为它提供一个值  
                                vsal emp.sal%type;                      --引用型的变量  
                                vrow emp%rowtype;                       --记录型变量          
                            begin                                       --业务逻辑
                                dbms_output.put_line(变量);             --相当于java中 syso 
                                select sal into 变量 from.. where..;    --查询结果赋值给变量
                                (commit;)   (当代码中出现DML语句后，需要使用commit提交数据)
                            end;
                   
            ------------------------------------------------------------------
            3.PL条件判断:   begin
                                if 	  then
                                
                                elsif then
                                  
                                else 
                                
                                end if;
                            end;
            
            ------------------------------------------------------------------
            4.while 循环：  begin  
                                while 条件 loop
                                
                                end loop;
                            end;
            ------------------------------------------------------------------
            5.for循环：     begin
                                for 变量  in [reverse] 变量起始值..变量结束值 loop         --每执行完一次循环自带i++
                                                                                           --reverse为翻转变量
                                end loop;
                            end;
            ------------------------------------------------------------------
            6.loop循环：    loop
                                exit when 条件;
                            end loop;
            
            
              1.1 查询7369的工资,并且打印出来
                                                declare
                                                  vsal emp.sal%type;
                                                begin
                                                  --将查询出的结果赋值给vsal
                                                  select sal into vsal from emp where empno = 7369;
                                                  
                                                  dbms_output.put_line(vsal);
                                                end;
                    
              1.2 查询7369的员工信息,并且打印出来
                                                declare
                                                  vrow emp%rowtype;      
                                                begin
                                                  select * into vrow from emp where empno = 7369;
                                                  
                                                  dbms_output.put_line('姓名:'||vrow.ename || '工资'|| vrow.sal);
                                                end;
                    
              1.3 根据不同年纪,输出相关内容：
                                                declare
                                                   age number := &aaa;      --&用来定义临时变量，每当&aaa出现，都会要求您为它提供一个值
                                                begin
                                                  if age < 18 then
                                                     dbms_output.put_line('小屁孩');
                                                  elsif age>=18 and age <=24 then
                                                     dbms_output.put_line('年轻人');
                                                  elsif age>24 and age < 40 then
                                                    dbms_output.put_line('老司机');
                                                  else 
                                                      dbms_output.put_line('老年人');    
                                                  end if;
                                                end;
                    
              1.4 while循环输出1~10：
                                                declare
                                                  i number :=1;
                                                begin
                                                  while i<=10 loop
                                                    dbms_output.put_line(i);
                                                    i := i+1;    
                                                  end loop;
                                                end;
                                                
              1.5 for循环输出1~10：
                                                declare

                                                begin
                                                  for i in reverse 1..10 loop
                                                    dbms_output.put_line(i);
                                                  end loop;
                                                end;
                                                
              1.6 loop循环输出1~10：
                                                declare
                                                   i number :=1;
                                                begin
                                                   loop
                                                     exit when i>10; 
                                                      dbms_output.put_line(i);  
                                                     i := i+1;
                                                   end loop;
                                                end;
                                                
            2. 输出菱形：
                           *
                          ***
                         *****
                          ***
                           * 
                    2.1 分析：
                            输出所有点：  
                                   x : [-m,m]
                                   y : [-m,m]
               
                            满足条件的为*: abs(y)+abs(x) <=m
                    
                    2.2 代码:  
                            declare
                               m number := 10;
                            begin
                               for y in reverse -m..m loop
                                 for x in -m..m loop
                                   if abs(y) + abs(x) <= m then
                                     dbms_output.put('*');
                                   else
                                     dbms_output.put(' ');
                                   end if;      
                                 end loop;
                                 dbms_output.new_line();
                               end loop;  
                            end;
                            
            3. 使用PLSQL输出三角形,只要是三个角
                            
                    3.1 代码：
                            declare
                               m number := 10;
                            begin
                               for x in reverse -m..m loop
                                 for y in -m..m loop
                                   if abs(y) + abs(x) <= m and x>=0 then
                                     dbms_output.put('*');
                                   else
                                     dbms_output.put(' ');
                                   end if;      
                                 end loop;
                                 dbms_output.new_line();
                               end loop;  
                            end;
            
 */
 
 
 /* day4                    游标cursor、异常exception、存储过程procedure、存储函数function
 
    1.游标(光标)
	        游标: 是用来操作查询结果集，可带参、也可不带参，相当于是JDBC中ResultSet
	       
	       
	        声明游标: cursor 游标名[(参数名 参数类型)] is 查询结果集;    (括号中参数为传入参数，需要在open游标时传入)
                    例：cursor youbiao(dno number) is select * from emp where deptno = dno;
	
	        调用游标-传统:		--繁琐，不推荐
                declare
                   cursor c1 is select * from emp;	--0. 声明游标
                begin
                   open 游标名						--1. 打开游标             		
                   	fetch 游标名 into 变量			--2. 从游标获取一行数据    
                   	c1%found 						--3. 找到数据
                   	c1%notfound						-- . 没有找到数据 
                   close 游标名						--4. 关闭游标     
	            end;
                  
	        调用游标-for循环:		--简单，推荐
	           0.不需要声明额外变量、打开游标、关闭游标。
				declare
                  cursor vrows is select * from emp;	--声明一个游标
                begin
                  for vrow in vrows loop
                     dbms_output.put_line('姓名:'||vrow.ename ||' 工资: ' || vrow.sal || '工作:'|| vrow.job);
                  end loop;
                end;
	           
	            
	        系统引用游标
	           1. 声明游标 : 游标名 sys_refcursor
	           2. 打开游标: open 游标名 for 结果集
	           3. 从游标中取数据
	           4. 关闭游标
	               
           
            1.1 输出员工表中所有的员工姓名和工资(不带参数游标)
                    declare
                       cursor vrows is select * from emp;       --声明游标
                       vrow emp%rowtype;                        --声明变量,记录一行数据
                    begin
                       open vrows;                              --1.打开游标  
                       loop                                     --2.循环
                           fetch vrows into vrow;               --3.从游标中取一行数据        
                           exit when vrows%notfound;            --4.游标中没有数据则退出循环
                           dbms_output.put_line('姓名:'||vrow.ename ||' 工资: ' || vrow.sal);
                       end loop;
                       close vrows;                             --5.关闭游标
                    end;
 
            1.2 输出指定部门下的员工姓名和工资(带参数游标)
                    declare
                       cursor vrows(dno number) is select * from emp where deptno = dno;    --声明游标
                       vrow emp%rowtype;                                                    --声明变量
                    begin
                      open vrows(10);               --1.打开游标 , 指定10号部门
                      loop                          --2.循环遍历,取数据
                         fetch vrows into vrow;
                         exit when vrows%notfound;    
                          dbms_output.put_line('姓名:'||vrow.ename ||' 工资: ' || vrow.sal);
                      end loop;
                      close vrows;
                    end;
                    
            1.4 for循环-遍历游标(简单方便)  			（模板）
                  1.不带参数：
                    declare
                      cursor vrows is select * from emp;	--声明一个游标
                    begin
                      for vrow in vrows loop
                         dbms_output.put_line('姓名:'||vrow.ename ||' 工资: ' || vrow.sal || '工作:'|| vrow.job);
                      end loop;
                    end;
                   
                  2.带参数：
                    declare
					 cursor c1(eno number) is select * from emp where empno = eno;
					begin
					  for r in c1(7369) loop
					    dbms_output.put_line(r.empno);
					  end loop;
					end;
					select * from emp;         
                    
            1.3 输出员工表中所有的员工姓名和工资(系统引用游标)
                    declare
                      --声明系统引用游标
                      vrows sys_refcursor;
                      --声明一个变量
                      vrow emp%rowtype;
                    begin
                      --1.打开游标
                      open vrows for select * from emp;
                      --2.取数据
                      loop
                        fetch vrows into vrow;
                        exit when vrows%notfound;
                         dbms_output.put_line('姓名:'||vrow.ename ||' 工资: ' || vrow.sal);
                      end loop;
                      close vrows;
                    end;
                    
           
                    
            1.5 按照员工工作给所有员工涨工资,总裁涨1000,经理涨800,其他人涨400

	                    游标 : 所有员工
	                    声明一个记录一行数据   

                    declare
                       --声明游标
                       cursor vrows is select * from emp;
                       --声明一个变量
                       vrow emp%rowtype; 
                    begin
                      --1.打开游标
                      open vrows;
                      --2.循环取数据
                      loop
                           --取数据
                           fetch vrows into vrow;
                           --退出条件
                           exit when vrows%notfound;  
                           --根据不同的职位,涨工资 总裁涨1000,经理涨800,其他人涨400
                           if vrow.job = 'PRESIDENT' then
                              update emp set sal = sal + 1000 where empno = vrow.empno;
                           elsif vrow.job = 'MANAGER' then
                              update emp set sal = sal + 800 where empno = vrow.empno;
                           else
                              update emp set sal = sal + 400 where empno = vrow.empno; 
                           end if;       
                      end loop;
                      --3.关闭游标
                      close vrows;
                      --4.提交事务
                      commit;
                    end;
                    
    2. 例外:(意外)程序运行的过程发生异常,相当于是JAVA中的异常，定义在 begin 	end; 中。
   
	        语法：
	            declare
	               --声明变量
	            begin
	               	--业务逻辑
	            exception		--处理异常
	               when 异常1 then
	                 ...
	               when 异常2 then
	                 ...
	               when others then
	                 ...处理其它异常
	            end;
	
	        异常名：
	            zero_divide : 除零异常
	            value_error : 类型转换异常
	            too_many_rows : 查询出多行记录,但是赋值给了rowtype记录一行数据变量
	            no_data_found : 没有找到数据
	           
	
	        自定义异常:
                        declare
                            x异常名  exception;  --自定义异常
                        begin
                
                        raise 异常名;           --抛出异常
            
            2.1 PLSQL处理异常(系统内异常)
                    declare
                       vi number;
                       vrow emp%rowtype;
                    begin
                       --vi := 8/0;  
                       --vi := 'aaa';
                       --select * into vrow from emp;
                       select * into vrow from emp where empno=1234567;
                    exception
                      when zero_divide then
                        dbms_output.put_line('发生了除零异常');
                      when value_error then
                         dbms_output.put_line('发生了类型转换异常');
                      when too_many_rows then
                        dbms_output.put_line(' 查询出多行记录,但是赋值给了rowtype记录一行数据变量');
                      when no_data_found then
                        dbms_output.put_line('没有找到数据异常');
                      when others then
                         dbms_output.put_line('发生了其它异常' || sqlerrm);     
                    end;
                
            2.2 查询指定编号的员工,如果没有找到,则抛出自定义的异常(自定义异常)
                    declare
                      --声明游标
                      cursor vrows is select * from emp where empno=8888;   
                      --声明一个记录型变量
                      vrow emp%rowtype;
                      --声明一个自定义异常
                      no_emp exception;  
                    begin
                      --1.打开游标
                      open vrows;
                      --2.取数据
                      fetch vrows into vrow;
                      --3.判断游标是否有数据
                      if vrows%notfound then
                        raise no_emp;
                      end if;
                      close vrows;
                    exception
                      when no_emp then  
                        dbms_output.put_line('发生了自定义的异常');
                    end;
    
    
    3. 存储过程procedure
        
	        存储过程: 1.实际上是封装在数据库中的一段,已经编译好了的 PLSQL代码，调用即可执行
	                  2.客户端取调用存储过程,执行效率就会非常高效
	                  3.一般不在存储过程/函数中 commit 和 rollback
	       
	        创建存储过程：
	                create [or replace] procedure 过程名(参数列表)	
	                is | as									--声明变量部分	is/as在存储过程中没有区别
	                
	                begin									--业务逻辑 
	                
	                end;  
	                
	               	--参数列表为：	参数名 in|out 参数类型,参数名 in|out 参数类型
	               					in是传入参数，out是输出参数
	                
	                
	    Oracle中调用：
	                方式1：
                    call p1(7788,10);
	
    
	                方式2： 用的最多的方式
                    declare

                    begin
                      proc_1(7788,-100);    --储存过程名(参数1，参数2)
                    end;
                
            3.1 给指定员工vempno 涨薪vnum,并打印涨薪前和涨薪后的工资
                    create or replace procedure proc_updatesal(vempno in number,vnum in number)
                    is
                       --声明变量.记录当前工资
                       vsal number;    
                    begin
                      --查询当前的工资
                      select sal into vsal from emp where empno = vempno;
                      --输出涨薪前的工资
                      dbms_output.put_line('涨薪前:'||vsal);
                      --更新工资
                      update emp set sal = vsal + vnum where empno = vempno;
                      --输出涨薪后的工资
                      dbms_output.put_line('涨薪后:'||(vsal+vnum));
                      --提交
                      commit;
                    end;
                    
            3.2 查询指定员工的年薪--存储过程来实现
                    create or replace procedure proc_updatesal(vempno in number,vsal out number)
					is
					   
					begin
					  select sal*12 + nvl(comm,0) into vsal from temp where empno = vempno;
					end;


                    declare
					  vsal number;
					begin
					  proc_updatesal(7788,vsal);
					  dbms_output.put_line('7788年薪为'||vsal);
					end;

    4. 存储函数function
        
	        存储函数: 实际上是一段封装在数据库中的一段，已经编译好的 PLSQL代码片断，函数不可直接调用。
	        
	        创建函数: (编译)
	             create [or replace] function 函数名(参数列表) 	--参数列表为：参数名 in 参数类型
	             return 参数类型								
	             is | as			--定义变量
	             
	             begin
	             
	               return xx;		--函数必须有return
	             end;
	       
	             
	        存储过程和函数的区别:
	             1.过程可以无返回值，也可以多个返回值；函数必须有返回值，且只能返回一个,
	             2.过程参数列表有 in,out,inout, 函数只有 in,
	             3.过程可返回 记录集、值；函数只能返回 表对象、值。
            
            4.1 查询指定员工的年薪
                    create or replace function func_getsal(vempno in number) return number
					is
						--声明变量.保存年薪
					   vsal number;
					begin
					  select sal*12+nvl(comm,0) into vsal from temp where empno = vempno;
					  return vsal;	--返回年薪
					end;


                    --调用存储函数
                    declare
                      vsal number;
                    begin
                      vsal := func_getsal(7788);
                      dbms_output.put_line(vsal);
                    end;
    
 */
 
 /* day4				----JDBC调用Oracle储存过程(--见/Web15_Oracle数据库/src/p2_代码_JDBC调用Oracle中存储过程/Demo1.java)
    
    JDBC连接Oracle数据库:
            1.导入驱动包
            2.注册驱动
            	Class.forName("oracle.jdbc.driver.OracleDriver");
            3.获取连接
	            String url = "jdbc:oracle:thin:@192.168.61.100:1521:orcl";
				String user = "wxt";
				String password = "wxt";
				Connection con = DriverManager.getConnection(url, user, password);
				
				
					----------JDBC执行Oracle中存储过程-----------
	0.JDBC执行Oracle中存储过程:
	
 	1.存储过程简介：
 		1.存储过程是数据库中的函数，是一串sql语句
 		2.存储过程将数据处理的过程隐藏在数据库中，防止暴露(常用于银行金融类)
 		3.java通过JDBC获得存储过程，实现数据处理(逻辑由数据库提供)
 	
 	2.存储过程的使用：
 		1.数据库中编写存储过程：略，见MySQL文档
 		2.通过con.prepareCall(String sql),得到CallableStatement对象st，用st执行存储过程：
 			sql 中内容为： "{call 存储过程名(?,?,..)}"		//?代表定义存储过程时的参数，?的数量要对应存储过程的参数数量
 		3.设置传入值?--st.setInt(1, 7788);
 		4.设置返回值?--st.registerOutParameter(2, OracleTypes.NUMBER)	 //registerOutParameter(i,s)方法中，
 															  //s代表数据库中值的类型，在oracle.jdbc.driver.OracleTypes的字段中选择
 		5.执行存储过程--st.execute()
 		6.使用st得到输出--st.getInt(2)		//得到返回值-第2个?
 
 	3.例：	String sql = "{call proc_updatesal(?,?)}";
			CallableStatement cstate = con.prepareCall(sql);	//2.
			cstate.setInt(1, 7788);	//3.
			cstate.registerOutParameter(2, OracleTypes.NUMBER);		//4.
			cstate.execute();			//5.
			
			int i = cstate.getInt(2);	//6. 
			
			
					----------JDBC执行Oracle中存储函数-----------
	0.JDBC执行Oracle中存储函数:
	
	1.例：	String sql = "{?= call func_getsal(?)}";
			CallableStatement cstate = con.prepareCall(sql);
			cstate.setInt(2, 7788);
			cstate.registerOutParameter(1, OracleTypes.NUMBER);	//
			cstate.execute();
			int i = cstate.getInt(1);
						
 */
 

/*  day4					触发器trigger、模拟ID自增长
 
 	1. 触发器
	 		触发器: 对某张表 执行了 insert | update | delete 这些操作前/后, 可以触发一系列其它的动作/业务逻辑
	 				(触发器中不能 commit;	rollback;)
	 		
			作用 : 
				     在动作执行之前或者之后,触发业务处理逻辑
				     插入数据,做一些校验
			        
			语法:
			       create [or replace] trigger 触发器的名称
			       before | after
			       insert | update | delete 
			       on 表名
			       [for each row]		//加入此行为 行级触发器  不加为 语句级触发器
			       declare
				       
			       begin
				         
			       end;
			       
			 触发器的分类:
				语句级触发器:   不管影响多少行, 都只会执行一次
				
				行级触发器:     影响多少行,就触发多少次
		                :old  代表旧的记录, 更新前的记录
		                :new  代表的是新的记录         
		                
		                
            (new old 关键字)
                new 指的是新记录的指针
                old 指的是旧记录指针
                这两个变量只有在使用了关键字 "FOR EACH ROW"时才存在.且update语句两个都有,而insert只有:new ,delect 只有:old;
		              
		              
		              
				1.1 新员工入职之后,输出一句话: 欢迎加入黑马程序员
						create or replace trigger tri_test1
						after
						insert
						on emp
						declare
						
						begin
						  dbms_output.put_line('欢迎加入黑马程序员');
						end;
						
						insert into emp(empno,ename) values(9527,'HUAAN');
						
				1.2 数据校验, 若为星期六,老板不在, 不能办理新员工入职（使用触发器，满足条件不提交操作）
						create or replace trigger tri_test2
						before
						insert 
						on emp
						declare
						 	//声明变量
						 vday varchar2(10);
						begin
						  	//查询当前
						  select trim(to_char(sysdate,'day')) into vday from dual;
						  	//判断当前日期:
						  if vday = 'saturday' then
						     dbms_output.put_line('老板不在,不能办理入职');
						     	//抛出系统异常
						     raise_application_error(-20001,'老板不在,不能办理入职');	//抛出异常，不执行插入操作
						  end if;
						end;
						
						insert into emp(empno,ename) values(9528,'HUAAN2');
						
				1.3 更新所有的工资 输出一句话（行级触发器）
						create or replace trigger tri_test3
						after
						update
						on emp 
						for each row
						declare
						
						begin
						  dbms_output.put_line('更新了数据');
						end;
						
						update emp set sal = sal+10;
						
				1.4 判断员工涨工资后的工资一定要大于涨工资前的工资(行级触发器，某条件不提交)
						create or replace trigger tri_updatesal
						before
						update
						on emp
						for each row
						declare
						
						begin
						  if :old.sal > :new.sal then
						    raise_application_error(-20002,'旧的工资不能大于新的工资');
						  end if;
						end;
						
						update emp set sal = sal - 100;
			
	2. 模拟mysql中ID的自增属性 auto_increment 
		  
		  insert into person(null,'张三');  
		   
		   触发器:pid=1  insert  pid=1
		   
		   序列 : create sequence seq_person_pid;
		   
		   
		   2.1 模拟ID自增长
		   			create table person(
					    pid number primary key,
					    pname varchar2(20)   
					);
					
					create sequence seq_person_pid;
					
					--触发器
					create or replace trigger tri_add_person_pid
					before
					insert
					on person
					for each row    --行级触发器
					declare
					
					begin
					  dbms_output.put_line(:new.pname);             --:new.列名   表示当前最新被引用到的记录 的列名，表名无须显示
					  	
					  select seq_person_pid.nextval into :new.pid from dual;    --给新记录 pid 赋值
					end;
					
					insert into person values(null,'张三'); 		--执行多次，触发器自动增加pid
					
					select * from person;
           

 */

public class Oracle详解 {

}
