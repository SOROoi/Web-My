<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>

	<h3>入门程序</h3>
	
	<%--请求参数 转为基本类型/String --%>
	<a href="controller/hello?username=王大锤">入门程序</a><br/>
	
	<%--请求参数 转为JavaBean --%>
	<form action="controller/form" method="post">
		用户名：<input type="text" name="username"/><br/>
		密码：<input type="text" name="userword"/><br/>
		账户名：<input type="text" name="account.aname"/><br/>
		账号密码：<input type="text" name="account.aword"/><br/>
		朋友姓名：<input type="text" name="friends[0].name"/><br/>
		朋友年龄：<input type="text" name="friends[0].age"/><br/>
		数学成绩：<input type="text" name="mathCore['数学成绩']"/><br/>
		<input type="submit" value="提交"/>
	</form>
	
	<%--请求参数 转为自定义类型(如Date) --%>
	<form action="controller/form2" method="post">
		用户名：<input type="text" name="username"/><br/>
		密码：<input type="text" name="userword"/><br/>
		生日：<input type="text" name="birthday"/><br/>
		<input type="submit" value="提交"/>
	</form>
	
	<%--控制器方法中 获得原生Servlet对象 --%>
	<a href="controller/getServlet?tips=天王盖地虎">获得原生Servlet对象</a><br>
	
	<%------------------------------常用的注解 测试---------------------------------------------%>
	------------------------常用的注解 测试-------------------------------<br>
	
	<%--@RequestParam 注解 --%>
    <a href="anno/testRequestParam?name=哈哈">RequestParam</a><br>

	<%--@RequestBody 注解 --%>
    <form action="anno/testRequestBody" method="post">
        用户姓名：<input type="text" name="username" /><br/>
        用户年龄：<input type="text" name="age" /><br/>
        <input type="submit" value="提交" />
    </form>

	<%--@PathVariable 注解 --%>
    <a href="anno/testPathVariable/10">testPathVariable</a><br>

	<%--@RequestHeader 注解 （用处不大）--%>
    <a href="anno/testRequestHeader">RequestHeader</a><br>

	<%--@CookieValue 注解  --%>
    <a href="anno/testCookieValue">CookieValue</a><br>

	<%--@ModelAttribute 注解  --%>
    <form action="anno/testModelAttribute" method="post">
        用户姓名：<input type="text" name="username" /><br/>
        <input type="submit" value="提交" />
    </form>

	<%--@SessionAttributes 注解  --%>
    <a href="anno/testSessionAttributes">testSessionAttributes</a>
    <a href="anno/getSessionAttributes">getSessionAttributes</a>
    <a href="anno/delSessionAttributes">delSessionAttributes</a>
	
	<br>
	
	<a href="controller/test">测试转发/重定向</a>
	<a href="controller/test2">测试request.getRequestURL()/request.getRequestURI()</a>
</body>
</html>