<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSM整合-主页面</title>
<style type="text/css">
	body{
		text-align: center;
	}
</style>
</head>
<body>
	<!-- 测试 springmvc 环境 -->
	<a href="con/testMvc">测试springmvc环境</a><br/><br/>
	
	
	-----------------------------------------------------------------------------------------------<br/>
	<!-- 测试：添加用户 -->
	<form action="con/zen" method="post">
		用户名：	<input type="text" name="name"><br/>
		工资：	<input type="text" name="money">
		<input type="submit" value="添加用户">
	</form>
	-----------------------------------------------------------------------------------------------
	<!-- 测试：删除用户 -->
	<form action="con/shan" method="post">
		用户ID	<input type="text" name="id">
		<input type="submit" value="删除用户">
	</form>
	-----------------------------------------------------------------------------------------------
	<!-- 测试：修改用户 -->
	<form action="con/gai" method="post">
		用户ID：		<input type="text" name="id"><br/>
		新用户名：	<input type="text" name="name"><br/>
		新工资：		<input type="text" name="money">
		<input type="submit" value="修改用户信息">
	</form>
	-----------------------------------------------------------------------------------------------
	<!-- 测试：查询单一用户 -->
	<form action="con/chaUser" method="post">
		用户ID：<input type="text" name="id"><br/>
		<input type="submit" value="查询用户">
	</form>
	-----------------------------------------------------------------------------------------------
	<!-- 测试：查询所有用户 -->
	<form action="con/chaAll" method="post">
		<input type="submit" value="查询所有用户">
	</form>
	
</body>
</html>