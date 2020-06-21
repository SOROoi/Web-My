<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传主页面</title>
<style type="text/css">
	body{
		text-align: center;
	}
</style>
</head>
<body>
	<!-- 方式一：传统方式 文件上传 -->
		<h3>传统方式：文件上传</h3>
		<form action="upload/1" method="post" enctype="multipart/form-data">
			选择文件：<input type="file" name="upload">
			<input type="submit" value="上传">
		</form>
		
		--------------------------------------------------------------------------------<br/>
		
	<!-- 方式二：springMVC 文件上传 -->
		<h3>springMVC：文件上传</h3>
		<form action="upload/2" method="post" enctype="multipart/form-data">
			选择文件：<input type="file" name="upload">
			<input type="submit" value="上传">
		</form>
		
		--------------------------------------------------------------------------------<br/>
		
	<!-- 方式三：springMVC 跨服务器上传 -->
		<h3>springMVC：跨服务器上传</h3>
		<form action="upload/3" method="post" enctype="multipart/form-data">
			选择文件：<input type="file" name="upload">
			<input type="submit" value="上传">
		</form>
		
		--------------------------------------------------------------------------------<br/>
		<br/><br/>
	
	
	<!-- springMVC异常处理 -->
		<h3>springMVC异常处理</h3>
		<a href="con/1">异常处理</a>
</body>
</html>