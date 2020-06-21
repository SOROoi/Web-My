<%@ page language="java" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <title>My JSP 'form.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
   <script type="text/javascript">
	  var iscommitted=false;
	  function dosubmit(){
	  	if(!iscommitted){
	  		iscommitted=true;
	  		document.getElementById("tbn").disabled="disabled";
	  		return true;
	  	}else{ 
	  	return false;
	  	}
  	}
  </script>
  
  <body>
    <form action="/day07/servlet/doFormServlet" method="post" onsubmit="return dosubmit()">
      用户名：<input type="text" name="username"/>
      <input id="btn" type="submit" value="提交"/>
      <input name="token" type="hidden" value="${token}"/>
    </form>
  </body>
</html>
