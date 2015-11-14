<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8" />
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
<title>��¼</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Login" />
<meta name="keywords"
	content="html5, css3, form, switch, animation, :target, pseudo-class" />
<meta name="author" content="Guloop" />
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/logindemo.css" />
<link rel="stylesheet" type="text/css" href="css/logincss.css" />
<link rel="stylesheet" type="text/css" href="css/loginstyle.css" />
<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
</head>
<body>
	<%
		String username = "";
		String password = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals("username")) {
					username = c.getValue();
				}
				if (c.getName().equals("password")) {
					password = c.getValue();
				}
			}
		}
	%>
	<div class="container">
		<div id="menu_out">
			<div id="menu_in">
				<div id="menu">
					<UL id="nav">
						<li><a href="#" onmouseover="javascript:qiehuan(0)"
							id="mynav0" class="nav_off"><span>myGuloop</span>
						</a>
						</li>
					</UL>
				</div>
			</div>
		</div>
		<div style="height: 40px;"></div>
		<section>
		<div id="container_demo">
			<div id="wrapper">
				<div id="login" class="animate form">
					<form action="loginAction!checkLogin" method="post">
						<h1>��¼</h1>
						<p>
							<label for="username" class="uname" data-icon="u"> �û��� </label>
							<input id="username" name="staff.SAccount" required="required"
								type="text" value="<%=username%>" />
						</p>
						<p>
							<label for="password" class="youpasswd" data-icon="p"> ����
							</label> <input id="password" name="staff.SPwd" required="required"
								type="password" value="<%=password%>" />
						</p>
						<p class="keeplogin">
							<input type="checkbox" name="loginkeeping" id="loginkeeping"
								value="loginkeeping" /> <label for="loginkeeping">�´��Զ���¼</label>
						</p>
						<p class="login button">
							<input type="submit" value="��¼" />
						</p>
						<p class="change_link"></p>
					</form>
				</div>
			</div>
		</div>
		</section>
	</div>
</body>
</html>