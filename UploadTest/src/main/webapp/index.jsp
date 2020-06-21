<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下载主页面</title>
</head>
<body>
	<!-- <a href="con/upload">测试按钮</a> -->
	
	<form action="con/upload" method="post" enctype="multipart/form-data">
		名称：<input type="text" name="picname"/><br/>
		图片：<input type="file" name="upload"/><br/>	
		<input type="submit" value="上传"/>
	</form>
</body>
</html>