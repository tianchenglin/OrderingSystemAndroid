<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">setTimeout("history.go(-1)", 3000);  </script>
	<SCRIPT language=javascript>
		function go()
		{
		 window.history.back();
		}
		setTimeout("go()",3000);
	</SCRIPT>

  </head>
  <body>
  <center>错误查询,末找到相关记录,3秒后返回上一页</center>
  </body>
</html>
