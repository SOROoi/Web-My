<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>主页面</title>
	<script src="js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
		//页面加载函数	按钮绑定单击事件
		$(function(){
			$("#btn").click(function(){
				//alert("hello btn");		
				//发送ajax请求
				$.post("con1/testAjax",{ name: "John", age: "18" },function(data){
					alert("2333");
					alert(data.name);
					alert(data.age);
				},"json" );
			});
		});
		
	</script>
</head>
<body>
	<!-- Controller1类  测试-->

		<!-- 测试controller方法 返回值为String -->
		<a href="con1/1">1.返回值String</a><br/>
		
		<!-- 测试controller方法 返回值为void 无return -->
		<a href="con1/2">2.返回值void 无return</a><br/>
		
		<!-- 测试controller方法 返回值为void 无return -->
		<a href="con1/3">3.返回值void 有return</a><br/>
		
		<!-- 测试controller方法 返回值为ModelAndView -->
		<a href="con1/4">4.返回值ModelAndView</a><br/>
		
		<!-- 测试controller方法 关键字进行请求转发 -->
		<a href="con1/5">5.forward请求转发</a><br/>
		
		<!-- 测试controller方法 关键字进行重定向 -->
		<a href="con1/6">6.redirect重定向</a><br/>
		
	<!-- ajax异步请求 服务器返回json数据  测试-->
		
		<!-- 发送 ajax请求的 按钮 -->
		<button id="btn">发送ajax请求</button>
</body>
</html>